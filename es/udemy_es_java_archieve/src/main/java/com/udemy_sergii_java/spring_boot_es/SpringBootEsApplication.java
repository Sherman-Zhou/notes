package com.udemy_sergii_java.spring_boot_es;

import com.udemy_sergii_java.spring_boot_es.dependencies.hotel_search.SearchService;
import com.udemy_sergii_java.spring_boot_es.dependencies.hotel_search_criteria.HotelSearchCriteriaDirector;
import com.udemy_sergii_java.spring_boot_es.dependencies.hotel_search_criteria.HotelSearchCriteriaUrlBuilder;
import com.udemy_sergii_java.spring_boot_es.model.criteria.HotelSearchCriteria;
import com.udemy_sergii_java.spring_boot_es.model.elasticsearch.HotelBookingDocument;
import com.udemy_sergii_java.spring_boot_es.model.request.SearchRequestModel;
import com.udemy_sergii_java.spring_boot_es.model.response.SearchResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SpringBootApplication
@RestController
public class SpringBootEsApplication {

    @Autowired
    SearchService searchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<SearchResponseModel>> search(
            @Validated @ModelAttribute SearchRequestModel searchRequestModel
    ) {
        try {
            HotelSearchCriteriaUrlBuilder builder = new HotelSearchCriteriaUrlBuilder(searchRequestModel);
            HotelSearchCriteriaDirector director = new HotelSearchCriteriaDirector(builder);
            director.buildCriteria();

            HotelSearchCriteria criteria = director.getCriteria();

            SearchPage<HotelBookingDocument> searchPage = searchService.search(criteria);
            Iterator<SearchHit<HotelBookingDocument>> iterator = searchPage.iterator();
            List<SearchResponseModel> results = new ArrayList<>();

            while (iterator.hasNext()) {
                HotelBookingDocument hotel = iterator.next().getContent();
                results.add(new SearchResponseModel(hotel.getName(), hotel.getCityNameEn()));
            }

            return ResponseEntity.ok(results);

        } catch (Throwable t) {
            //implement Exceptions properly - current approach is only for learning purpose
            //you may apply to https://reflectoring.io/spring-boot-exception-handling/ for help
            List<SearchResponseModel> results = new ArrayList<>();
            SearchResponseModel response = new SearchResponseModel("", "");
            response.setError(t.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            results.add(response);

            return ResponseEntity.ok(results);
        }
    }

    @RequestMapping(value = "/show-raw-json", method = RequestMethod.GET)
    public String showRawJson(@Validated @ModelAttribute SearchRequestModel searchRequestModel) {
        try {
            HotelSearchCriteriaUrlBuilder builder = new HotelSearchCriteriaUrlBuilder(searchRequestModel);
            HotelSearchCriteriaDirector director = new HotelSearchCriteriaDirector(builder);
            director.buildCriteria();

            HotelSearchCriteria criteria = director.getCriteria();
            String rawJsonQuery = searchService.getRawJsonQuery(criteria);

            return rawJsonQuery;

        } catch (Throwable t) {
            //implement Exceptions properly - current approach is only for learning purpose
            //you may apply to https://reflectoring.io/spring-boot-exception-handling/ for help
            return t.getMessage();
        }
    }

    @RequestMapping(value = "/show-criteria", method = RequestMethod.GET)
    public String showCriteria(@Validated @ModelAttribute SearchRequestModel searchRequestModel) {
        try {
            HotelSearchCriteriaUrlBuilder builder = new HotelSearchCriteriaUrlBuilder(searchRequestModel);
            HotelSearchCriteriaDirector director = new HotelSearchCriteriaDirector(builder);
            director.buildCriteria();

            HotelSearchCriteria criteria = director.getCriteria();

            String criteriaParameters = String.valueOf(
                    "coordinates: " + criteria.getGeoCoordinates() + "\n" +
                            "hotel name: " + criteria.getHotelName() + "\n" +
                            "city name: " + criteria.getCityName() + "\n" +
                            "age: " + criteria.getHotelAge() + "\n" +
                            "free: " + criteria.getFreePlacesAtNow() + "\n" +
                            "star: " + criteria.getHotelStars() + "\n" +
                            "size: " + criteria.getSize() + "\n" +
                            "page: " + criteria.getPage()
            );

            return criteriaParameters;

        } catch (Throwable t) {
            //implement Exceptions properly - current approach is only for learning purpose
            //you may apply to https://reflectoring.io/spring-boot-exception-handling/ for help
            return t.getMessage();
        }
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        try {
            return String.valueOf("Test");
        } catch (Throwable t) {
            return t.getMessage();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootEsApplication.class, args);
    }
}
