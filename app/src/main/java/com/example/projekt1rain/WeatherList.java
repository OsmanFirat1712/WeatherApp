package com.example.projekt1rain;

public class WeatherList {
    private String city;
    private int icon;
    private double temperature;

    public WeatherList(String city,int icon,double temperature){
        this.city = city;
        this.icon = icon;
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
