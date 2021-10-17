package com.udemy_sergii_java.spring_boot_es.repository;

import com.udemy_sergii_java.spring_boot_es.model.elasticsearch.HotelBookingDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface HotelBookingRepository extends ElasticsearchRepository<HotelBookingDocument, String> {
    List<HotelBookingDocument> findByName(String name);
}
