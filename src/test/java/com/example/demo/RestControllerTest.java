package com.example.demo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import org.springframework.web.client.RestTemplate;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class RestControllerTest {
    @Autowired
    private BookingRepository bookingRepository;

    @LocalServerPort
    private int port;

    private String serverUrl;

    private RestTemplate restTemplate = new RestTemplate();

    @BeforeEach
    public void initServerURL() {
        bookingRepository.deleteAll();
        this.serverUrl = "http://localhost:" + port;
    }
    private ResponseEntity<List<HotelBooking>> executeBookingRequest(String url, HttpMethod method) {
        return restTemplate.exchange(serverUrl + url, method, null, new ParameterizedTypeReference<List<HotelBooking>>() {
        });
    }
    @Test
    public void whenGetAllBookingsWithEmptyDB_thenReturn200AndCorrectResponse() {
        ResponseEntity<List<HotelBooking>> response = executeBookingRequest("/bookings/all", HttpMethod.GET);
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        assertEquals(0, response.getBody().size());

    }


    @Test
    public void whenGetFindBookingsWithoutPathVariable_thenReturn404() {
        HttpClientErrorException response = assertThrows(HttpClientErrorException.class, () -> executeBookingRequest("/bookings/affordable", HttpMethod.GET));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }


    @Test
    public void whenRemoveBookingsWithoutPathVariable_thenReturn404() throws Exception {
        HttpClientErrorException response = assertThrows(HttpClientErrorException.class, () -> executeBookingRequest("/bookings/delete", HttpMethod.GET));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }


}
