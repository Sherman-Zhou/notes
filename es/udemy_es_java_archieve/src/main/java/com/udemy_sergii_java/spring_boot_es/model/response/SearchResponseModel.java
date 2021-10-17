package com.udemy_sergii_java.spring_boot_es.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel
public class SearchResponseModel implements Serializable {
    @ApiModelProperty(notes = "Hotel name.",
            example = "Royal Beach", required = true)
    private String hotel;

    @ApiModelProperty(notes = "City name where hotel is located at English.",
            example = "London", required = true)
    private String city;

    @ApiModelProperty(notes = "Response status.",
            example = "200", required = false)
    private Integer status = 200;

    @ApiModelProperty(notes = "Error text.",
            example = "Exception", required = false)
    private String error = "";

    public SearchResponseModel(String hotel, String city) {
        this.hotel = hotel;
        this.city = city;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
