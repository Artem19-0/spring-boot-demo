package com.example.demo.service;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.example.demo.model.Currency;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;




public class CurrencyParser {

    public CurrencyParser() {}

    private static String CURRENCY_URL = "https://www.nbrb.by/Services/XmlExRates.aspx";


    private static Document loadDocument(String url) {
        Document doc = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            doc = factory.newDocumentBuilder().parse(new java.net.URL(url).openStream());
        } catch (java.net.ConnectException e) {
            System.err.print(" Oops! Something goes wrong! This is Belarus, baby! \nConnection timed out. ");
            System.err.print(CURRENCY_URL + " is not responsible. Please, try again later");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return doc;
    }




    public static void extractXML(String path){
        Document doc = loadDocument(CURRENCY_URL);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            FileWriter writer = new FileWriter(new File(path));
            StreamResult result = new StreamResult(writer);

            transformer.transform(source, result);

        } catch (Exception e) {
            System.out.println("Err");
            throw new RuntimeException(e);
        }
    }

    public static String getCurrency(String currencyCode)
    {
        boolean isCurrencyCodeNext = false;
        Document doc = loadDocument(CURRENCY_URL);

        if (doc != null) {
            NodeList nodes = doc.getFirstChild().getChildNodes();


            for (int i = 0; i < nodes.getLength(); i++) {
                Node parent = nodes.item(i);

                if (parent.getNodeType() == 1) {
                    NodeList childs = parent.getChildNodes();

                    for (int ii = 0; ii < childs.getLength(); ii++) {
                        Node child = childs.item(ii);
                        if (child.hasChildNodes()) {
                            if ((child.getNodeName().trim().equalsIgnoreCase("Rate")) && (isCurrencyCodeNext)) {
                                isCurrencyCodeNext = false;
                                return child.getFirstChild().getTextContent();
                            }

                            if (child.getFirstChild().getTextContent().trim().equalsIgnoreCase(currencyCode)) {
                                isCurrencyCodeNext = true;
                            }
                        }
                    }
                }
            }
        }
        return "0.0";
    }




    public static void main(String[] args)  {
//        String val = getCurrency("840");
//        System.out.println(val);


        getAllCurrencies();



    }

    public static List<Currency> getAllCurrencies(){


        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(new URL(CURRENCY_URL).toURI());
            HttpResponse response = client.execute(request);

            Reader reader = null;
            try {
                reader = new InputStreamReader(response.getEntity().getContent());

                StringBuffer sb = new StringBuffer();
                {
                    int read;
                    char[] cbuf = new char[1024];
                    while ((read = reader.read(cbuf)) != -1)
                        sb.append(cbuf, 0, read);
                }


                String result = sb.toString();
               // System.out.println(result);
                //1st get dailyExChanged from String
                String date = result.substring(result.indexOf("Date=") + 6, result.indexOf("Date=") + 16);
                System.out.println(date);


                // box for saving currencies
                List<Currency> currencies = new ArrayList<>();

                // 2nd  for each Currency
                for (String curr: result.split("<Currency")){
                    if (!curr.startsWith(" Id=")){
                        continue;
                    }
                    String target = curr.substring(curr.indexOf("<NumCode>") , curr.indexOf("</Currency>"));
                    target = "<Currency>" + target + "</Currency>";
//                    System.out.println(target);
                    XmlMapper xmlMapper = new XmlMapper();
                    Currency currency = xmlMapper.readValue(target , Currency.class);

                    //3rd populate dates
                    currency.setUpdatedTs(new Timestamp(System.currentTimeMillis()));
                    SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy"); // , Locale.ENGLISH


                    long mils = formatter.parse(date.replace("/", "-")).getTime();
                    currency.setDailyTs(new Timestamp(mils));

                    System.out.println(currency);
                    currencies.add(currency);
                }
                return currencies;


            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }catch (Exception e) {
            System.out.printf("Currencies parsing error " + e.getCause());
            throw new RuntimeException("Currencies error", e);
        }

    }

}
