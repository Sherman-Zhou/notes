package com.udemy_sergii_java.spring_boot_es.dependencies.hotel_search;

import com.udemy_sergii_java.spring_boot_es.model.criteria.HotelSearchCriteria;
import com.udemy_sergii_java.spring_boot_es.model.elasticsearch.HotelBookingDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Component;

@Component
public class SearchService {

    @Autowired
    QueryBuilder queryBuilder;

    @Autowired
    ElasticsearchOperations operations;

    public SearchPage search(HotelSearchCriteria criteria) {
        queryBuilder.createQuery(criteria);
        NativeSearchQuery search = queryBuilder.getSearch();

        SearchHits<HotelBookingDocument> searchHits = operations.search(search, HotelBookingDocument.class);
        SearchPage<HotelBookingDocument> searchPage = SearchHitSupport.searchPageFor(
                searchHits,
                queryBuilder.getPageRequest()
        );

        return searchPage;
    }

    public String getRawJsonQuery(HotelSearchCriteria criteria) {
        queryBuilder.createQuery(criteria);
        NativeSearchQuery search = queryBuilder.getSearch();

        return search.getQuery().toString();
    }
}
