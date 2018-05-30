package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.entity.Weather.WeatherMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

public class WeatherRestServiceClient {

    private static final String URL = "http://api.openweathermap.org/data/2.5/weather?q=Kosice&units=metric&appid=7bc67bf58ce6950bed61cefe26da2700";

    private Integer id;
    private String main;
    private String description;
    private String icon;

    public WeatherMap getForecast() {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<WeatherMap>() {
                    });
        } catch (Exception e) {
            System.err.println("Error loading forecast");
            return new WeatherMap();
        }
    }
}