package com.udemy_sergii_java.spring_boot_es.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@ApiModel
public class SearchRequestModel {

    @ApiParam(value = "Page number", defaultValue = "1", required = false)
    private Integer page = 1;

    @ApiParam(value = "Page size", defaultValue = "10", required = false)
    private Integer size = 10;

    @ApiParam(value = "Hotel name", required = false)
    private String hotel;

    @ApiParam(value = "City name", required = true)
    private String city;

    @ApiParam(value = "Latitude", required = false)
    private Float lat;

    @ApiParam(value = "Longitude", required = false)
    private Float lng;

    @ApiParam(value = "Free places", required = false)
    private Boolean fpn;

    @ApiParam(value = "Hotel age", required = false)
    private Integer age;

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

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public Boolean getFpn() {
        return fpn;
    }

    public void setFpn(Boolean fpn) {
        this.fpn = fpn;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
