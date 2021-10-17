package com.udemy_sergii_java.spring_boot_es.model.elasticsearch;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

public class Comment {
    @Field(type=FieldType.Integer)
    private Integer hotelID = null;

    @Field(type=FieldType.Text)
    private String content = null;

    @Field(type=FieldType.Integer)
    private Integer starts = null;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Instant createdDate = null;

    public Integer getHotelID() {
        return hotelID;
    }

    public void setHotelID(Integer hotelID) {
        this.hotelID = hotelID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStarts() {
        return starts;
    }

    public void setStarts(Integer starts) {
        this.starts = starts;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
