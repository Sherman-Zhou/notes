package com.udemy_sergii_java.spring_boot_es.model.criteria;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;

public class HotelSearchCriteria {
    public static final int SIZE_MIN = 1;
    public static final int SIZE_MAX = 20;

    private Integer page = 1;
    private Integer size = 10;
    private Boolean freePlacesAtNow = false;
    private String hotelName;
    private String cityName;
    private Integer hotelAge;
    private Integer hotelStars;
    private GeoPoint geoCoordinates;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getFreePlacesAtNow() {
        return freePlacesAtNow;
    }

    public void setFreePlacesAtNow(Boolean freePlacesAtNow) {
        this.freePlacesAtNow = freePlacesAtNow;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getHotelAge() {
        return hotelAge;
    }

    public void setHotelAge(Integer hotelAge) {
        this.hotelAge = hotelAge;
    }

    public Integer getHotelStars() {
        return hotelStars;
    }

    public void setHotelStars(Integer hotelStars) {
        this.hotelStars = hotelStars;
    }

    public GeoPoint getGeoCoordinates() {
        return geoCoordinates;
    }

    public void setGeoCoordinates(GeoPoint geoCoordinates) {
        this.geoCoordinates = geoCoordinates;
    }
}
