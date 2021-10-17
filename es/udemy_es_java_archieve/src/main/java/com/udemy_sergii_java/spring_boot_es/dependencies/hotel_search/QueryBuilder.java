package com.udemy_sergii_java.spring_boot_es.dependencies.hotel_search;

import com.udemy_sergii_java.spring_boot_es.dependencies.hotel_search.filters.CityNameFilter;
import com.udemy_sergii_java.spring_boot_es.dependencies.hotel_search.filters.GeoDistanceFilter;
import com.udemy_sergii_java.spring_boot_es.dependencies.hotel_search.filters.HotelAgeFilter;
import com.udemy_sergii_java.spring_boot_es.dependencies.hotel_search.filters.HotelNameFilter;
import com.udemy_sergii_java.spring_boot_es.model.criteria.HotelSearchCriteria;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

@Component
public class QueryBuilder implements QueryBuilderInterface {
    private NativeSearchQueryBuilder searchQueryBuilder;
    private PageRequest pageRequest;

    public QueryBuilder() {
        this.searchQueryBuilder = new NativeSearchQueryBuilder();
    }

    @Override
    public void createQuery(HotelSearchCriteria criteria) {
        this.setPageOffset(criteria);
        this.setFilters(criteria);
        this.setAggregation(criteria);
        this.setSorting(criteria);
        this.setFields(criteria);
    }

    @Override
    public NativeSearchQuery getSearch() {
        return this.searchQueryBuilder.build();
    }

    public PageRequest getPageRequest() {
        return this.pageRequest;
    }

    protected void setFilters(HotelSearchCriteria criteria) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (!criteria.getHotelName().isEmpty()) {
            boolQueryBuilder.must(HotelNameFilter.createFilter(criteria));
        }

        if (!criteria.getCityName().isEmpty()) {
            boolQueryBuilder.must(CityNameFilter.createFilter(criteria));
        }

        if (criteria.getHotelAge() >= 0) {
            boolQueryBuilder.should(HotelAgeFilter.createFilter(criteria));
        }

        boolQueryBuilder.filter(GeoDistanceFilter.createFilter(criteria));

        this.searchQueryBuilder.withQuery(boolQueryBuilder);
    }

    protected void setPageOffset(HotelSearchCriteria criteria) {
        this.pageRequest = PageRequest.of(criteria.getPage(), criteria.getSize());
        this.searchQueryBuilder.withPageable(this.pageRequest);
    }

    protected void setFields(HotelSearchCriteria criteria) {
        //choose fields you want to get from ElasticSearch
    }

    protected void setAggregation(HotelSearchCriteria criteria) {
        //add aggregations
    }

    protected void setSorting(HotelSearchCriteria criteria) {
        //add sorting
    }
}
