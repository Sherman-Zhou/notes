package com.udemy_sergii_java.spring_boot_es.model.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.join.JoinField;
import java.time.Instant;
import java.util.List;

@Document(indexName = "hotels")
@Setting(shards = 1, replicas = 0)
public class HotelBookingDocument {

    @Id
    @Field(type=FieldType.Integer)
    private String id = null;

    @Field(type=FieldType.Text)
    private String name = null;

    @Field(type=FieldType.Integer)
    private Integer hotelID = null;

    @Field(type=FieldType.Text)
    private String cityNameEn = null;

    private GeoPoint location = null;

    @Field(type=FieldType.Integer)
    private Integer age = null;

    @Field(type=FieldType.Boolean)
    private Boolean freePlacesAtNow = null;

    @Field(type=FieldType.Integer)
    private Integer starts = null;

    @Field(type=FieldType.Float)
    private Double rating = null;

    @Field(type=FieldType.Nested)
    private List<Comment> comments;

    @JoinTypeRelations(
            relations =
                    {
                            @JoinTypeRelation(parent = "hotel", children = "booking")
                    }
    )
    private JoinField<String> relation;

    private Double price = null;
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Instant createdDate = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String author) {
        this.name = author;
    }

    public Integer getHotelID() {
        return hotelID;
    }

    public void setHotelID(Integer hotelID) {
        this.hotelID = hotelID;
    }

    public String getCityNameEn() {
        return cityNameEn;
    }

    public void setCityNameEn(String cityNameEn) {
        this.cityNameEn = cityNameEn;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean getFreePlacesAtNow() {
        return freePlacesAtNow;
    }

    public void setFreePlacesAtNow(boolean freePlacesAtNow) {
        this.freePlacesAtNow = freePlacesAtNow;
    }

    public Integer getStarts() {
        return starts;
    }

    public void setStarts(Integer starts) {
        this.starts = starts;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public JoinField<String> getRelation() {
        return relation;
    }

    public void setRelation(JoinField<String> relation) {
        this.relation = relation;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
