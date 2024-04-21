package com.example.demo.test;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class GEOTest {
    public static void main(String[] args) {
        File database = new File("C:\\Users\\User\\Downloads\\GeoLite2-City_20231117.tar\\GeoLite2-City_20231117\\GeoLite2-City_20231117\\GeoLite2-City.mmdb");
        try {
            DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
            CityResponse response = dbReader.city(InetAddress.getByName("192.168.0.105"));
            System.out.println(response.getCity().getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (GeoIp2Exception e) {
            throw new RuntimeException(e);
        }
    }
}
