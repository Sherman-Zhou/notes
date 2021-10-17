package com.udemy_sergii_java.spring_boot_es.dependencies.hotel_search.filters;

import com.udemy_sergii_java.spring_boot_es.model.criteria.HotelSearchCriteria;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

public class GeoDistanceFilter {
    public static AbstractQueryBuilder createFilter(HotelSearchCriteria criteria) {

        GeoPoint coordinates = criteria.getGeoCoordinates();
        GeoDistanceQueryBuilder geoDistanceQueryBuilder = QueryBuilders.geoDistanceQuery("location")
                .point(coordinates.getLat(), coordinates.getLon()).distance(30.00, DistanceUnit.KILOMETERS);

        return geoDistanceQueryBuilder;
    }
}
