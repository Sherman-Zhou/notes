package com.udemy_sergii_java.spring_boot_es.dependencies.hotel_search_criteria;

import com.udemy_sergii_java.spring_boot_es.model.criteria.HotelSearchCriteria;
import com.udemy_sergii_java.spring_boot_es.model.request.SearchRequestModel;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

public class HotelSearchCriteriaUrlBuilder extends AbstractCriteriaBuilder {

    private SearchRequestModel searchRequestModel;
    private HotelSearchCriteria hotelSearchCriteria;

    public HotelSearchCriteriaUrlBuilder(SearchRequestModel searchRequestModel) {
        this.searchRequestModel = searchRequestModel;
        this.hotelSearchCriteria = new HotelSearchCriteria();
    }

    @Override
    public HotelSearchCriteria getCriteria() {
        return this.hotelSearchCriteria;
    }

    @Override
    public void createCriteria() {
        if (searchRequestModel.getPage() >= 1) {
            this.hotelSearchCriteria.setPage(searchRequestModel.getPage() - 1);
        }

        this.hotelSearchCriteria.setHotelAge(searchRequestModel.getAge());
        this.hotelSearchCriteria.setCityName(searchRequestModel.getCity());
        this.hotelSearchCriteria.setHotelName(searchRequestModel.getHotel());
        this.hotelSearchCriteria.setFreePlacesAtNow(searchRequestModel.getFpn());

        if (searchRequestModel.getSize() <= HotelSearchCriteria.SIZE_MAX &&
            searchRequestModel.getSize() >= HotelSearchCriteria.SIZE_MIN
        ) {
            this.hotelSearchCriteria.setSize(searchRequestModel.getSize());
        }

        if (searchRequestModel.getLat() != null && searchRequestModel.getLng() != null) {
            hotelSearchCriteria.setGeoCoordinates(new GeoPoint(
                    searchRequestModel.getLat(),
                    searchRequestModel.getLng()
            ));
        }
    }
}
