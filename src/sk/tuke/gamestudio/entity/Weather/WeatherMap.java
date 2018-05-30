
package sk.tuke.gamestudio.entity.Weather;

import java.util.List;

public class WeatherMap {

    public Coord coord;
    public List<Weather> weather = null;
    public String base;
    public Main main;
    public Integer visibility;
    public Wind wind;
    public Clouds clouds;
    public Integer dt;
    public Sys sys;
    public Integer id;
    public String name;
    public Integer cod;

    public Main getMain() {
        return main;
    }

    public String getName() {
        return name;
    }

    public Coord getCoord() {
        return coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Integer getDt() {
        return dt;
    }

    public Sys getSys() {
        return sys;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCod() {
        return cod;
    }
}
