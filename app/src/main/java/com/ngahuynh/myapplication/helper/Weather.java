package com.ngahuynh.myapplication.helper;

public class Weather {
    public String city, country, lastUpdated, temperature, cast, humidity, minTemp, maxTemp, sunrise, sunset;

    //Setters and Getters
    public String getCity() {
        return city;}
    public void setCity(String city) {
        this.city = city;}

    public String getCountry() {return country;}
    public void setCountry(String country) {this.country = country;}

    public String getLastUpdated() {return lastUpdated;}
    public void setLastUpdated(String lastUpdated) {this.lastUpdated = lastUpdated;}

    public String getTemp() {return temperature;}
    public void setTemp(String temperature) {this.temperature = temperature;}

    public String getCast() {return cast;}
    public void setCast(String cast) {this.cast = cast;}

    public String getHumidity() {return humidity;}
    public void setHumidity(String humidity) {this.humidity = humidity;}

    public String getMinTemp() {return minTemp;}
    public void setMinTemp(String minTemp) {this.minTemp = minTemp;}

    public String getMaxTemp() {return maxTemp;}
    public void setMaxTemp(String maxTemp) {this.maxTemp = maxTemp;}

    public String getSunrise() {return sunrise;}
    public void setSunrise(String sunrise) {this.sunrise = sunrise;}

    public String getSunset() {return sunset;}
    public void setSunset(String sunset) {this.sunset = sunset;}
}
