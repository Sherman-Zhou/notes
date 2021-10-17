package com.udemy_sergii_java.spring_boot_es;

import com.udemy_sergii_java.spring_boot_es.model.elasticsearch.Comment;
import com.udemy_sergii_java.spring_boot_es.model.elasticsearch.HotelBookingDocument;
import com.udemy_sergii_java.spring_boot_es.repository.HotelBookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.join.JoinField;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.Arrays;

@Component
@Order(1)
class SpringBootEsInitLoader implements CommandLineRunner {

    @Autowired
    HotelBookingRepository hotelBookingRepository;

    @Value("${load.init.data}")
    private Boolean loadInitData;

    private static final Logger logger = LoggerFactory.getLogger(SpringBootEsInitLoader.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info("CommandLineRunner#run(String[])");

        try {
            if (loadInitData) {
                HotelBookingDocument goldenHotel = new HotelBookingDocument();
                goldenHotel.setId("1");
                goldenHotel.setHotelID(1);
                goldenHotel.setName("Golden star hotel");
                goldenHotel.setCityNameEn("Warsaw");
                goldenHotel.setLocation(new GeoPoint(52.21, 21.01));
                goldenHotel.setAge(7);
                goldenHotel.setStarts(5);
                goldenHotel.setFreePlacesAtNow(true);
                goldenHotel.setRating(4.85);
                goldenHotel.setRelation(new JoinField<>("hotel"));

                Comment comment = new Comment();
                comment.setContent("Some content");
                comment.setCreatedDate(Instant.now());
                comment.setHotelID(1);
                comment.setStarts(5);
                goldenHotel.setComments(Arrays.asList(comment));

                hotelBookingRepository.save(goldenHotel);

                HotelBookingDocument booking = new HotelBookingDocument();
                booking.setId("2");
                booking.setPrice(100.00);
                booking.setCreatedDate(Instant.now());
                booking.setRelation(new JoinField<>("booking", goldenHotel.getId()));
                hotelBookingRepository.save(booking);

                HotelBookingDocument silverHotel = new HotelBookingDocument();
                silverHotel.setId("3");
                silverHotel.setHotelID(3);
                silverHotel.setName("Silver star hotel");
                silverHotel.setCityNameEn("Warsaw");
                silverHotel.setLocation(new GeoPoint(52.13, 21.01));
                silverHotel.setAge(7);
                silverHotel.setStarts(4);
                silverHotel.setFreePlacesAtNow(true);
                silverHotel.setRating(4.6);
                silverHotel.setRelation(new JoinField<>("hotel"));
                hotelBookingRepository.save(silverHotel);
            }
        } catch (Throwable t) {
            logger.error(t.toString());
        }

        if (args.length > 0) {
            logger.info("first command line parameter: '{}'", args[0]);
        }
    }
}
