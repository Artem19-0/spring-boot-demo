package com.example.demo.service;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.model.Forecast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class WeatherAPIService {


    public  List<Forecast> timelineRequestHttpClient(String location , int date) throws Exception {
        //set up the end point
        String apiEndPoint = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
//        String location = "Minsk,BLR";
        String startDate = null;
        String endDate = null;
        String unitGroup = "metric";
        String apiKey="7J4MRWPNPGNPAZJEZG6MHAYLD";

        StringBuilder requestBuilder = new StringBuilder(apiEndPoint);
        requestBuilder.append(URLEncoder.encode(location, StandardCharsets.UTF_8.toString()));
        if (startDate != null && !startDate.isEmpty()) {
            requestBuilder.append("/").append(startDate);

            if (endDate != null && !endDate.isEmpty()) {
                requestBuilder.append("/").append(endDate);
            }

        }


        URIBuilder builder = new URIBuilder(requestBuilder.toString());
        builder.setParameter("unitGroup", unitGroup).setParameter("key", apiKey);
        HttpGet get = new HttpGet(builder.build());
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(get);
        String rawResult = null;

        try {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                System.out.printf("Bad response status code:%d%n", response.getStatusLine().getStatusCode());
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                rawResult=EntityUtils.toString(entity, Charset.forName("utf-8"));
            }
        } finally {
            response.close();
        }
        return parseTimelineJson(rawResult , date);
    }



    private  List<Forecast> parseTimelineJson(String rawResult , int date) {
        if (rawResult==null || rawResult.isEmpty()) {
            System.out.printf("No raw data%n");
            return null;
        }

        List<Forecast> forecasts = new ArrayList<>();


        JSONObject timelineResponse = new JSONObject(rawResult);
        ZoneId zoneId = ZoneId.of(timelineResponse.getString("timezone"));
        System.out.printf("Weather data for: %s%n", timelineResponse.getString("resolvedAddress"));
        JSONArray values = timelineResponse.getJSONArray("days");
        System.out.printf("Date \tMaxTemp\tMinTemp\t  Precip\tSource%n");

        for (int i = 1; i <= date; i++) {

            JSONObject dayValue = values.getJSONObject(i);
            ZonedDateTime datetime=ZonedDateTime.ofInstant(Instant.ofEpochSecond(dayValue.getLong("datetimeEpoch")), zoneId);
            double maxtemp = dayValue.getDouble("tempmax");
            double mintemp = dayValue.getDouble("tempmin");
            double precip = dayValue.getDouble("precip");
            String source = dayValue.getString("source");

            forecasts.add(new Forecast(i, precip, maxtemp, mintemp, Date.from(datetime.toInstant()), source));
            System.out.printf("%s \t%.1f \t%.1f \t%.1f \t%s%n", datetime.format(DateTimeFormatter.ISO_LOCAL_DATE), maxtemp, mintemp, precip,source );

        }

        return forecasts;
    }



    public static void main(String[] args) throws Exception {
        WeatherAPIService w = new WeatherAPIService();
        List<Forecast> forecasts =  w.timelineRequestHttpClient("Tokyo" ,1);
        forecasts.forEach(forecast -> {
            System.out.println(forecast);
        });
    }


}


