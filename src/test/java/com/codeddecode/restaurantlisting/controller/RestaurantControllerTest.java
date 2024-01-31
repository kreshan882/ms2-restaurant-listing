package com.codeddecode.restaurantlisting.controller;


import com.codeddecode.restaurantlisting.dto.RestaurantDTO;
import com.codeddecode.restaurantlisting.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestaurantControllerTest {  //RestaurantController+ Test (suffix)

    @InjectMocks  // not going to actual class when use InjectMocks| this is the one need to test
    RestaurantController restaurantController; 

    @Mock   // all the auto wired service need to MOCK
    RestaurantService restaurantService;

    @BeforeEach // run this before each test case run
    public void setUp() {
        MockitoAnnotations.openMocks(this); //in order for Mock and InjectMocks annotations to take effect, you need to call MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchAllRestaurants(){  //test(prefix))+FetchAllRestaurants()
        
    	// A1: Arrange: create test data
        List<RestaurantDTO> mockRestaurants = Arrays.asList(
                new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"),
                new RestaurantDTO(2, "Restaurant 2", "Address 2", "city 2", "Desc 2")
        );
        when(restaurantService.findAllRestaurants()).thenReturn(mockRestaurants); //add clone data to findAllRestaurants()

        // A2: Act: Call the method need to test
        ResponseEntity<List<RestaurantDTO>> response = restaurantController.fetchAllRestaurants(); // call not call reyal testing function, 

        // A3: Assertions: Verify the response (go inside the fetchAllRestaurants(), then only update @Mock RestaurantService with our data)
        assertEquals(HttpStatus.OK, response.getStatusCode()); // from restaurantController.fetchAllRestaurants()
        assertEquals(mockRestaurants, response.getBody()); //restaurantController.fetchAllRestaurants() -> restaurantService.findAllRestaurants()

        // Verify findAllRestaurants() was called only 1 time,
        verify(restaurantService, times(1)).findAllRestaurants();
    }

    @Test
    public void testSaveRestaurant() {
    	//A1
        // Create a mock restaurant to be saved
        RestaurantDTO mockRestaurant =  new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");

        // Mock the service behavior
        when(restaurantService.addRestaurantInDB(mockRestaurant)).thenReturn(mockRestaurant);

        //A2
        // Call the controller method
        ResponseEntity<RestaurantDTO> response = restaurantController.saveRestaurant(mockRestaurant);

        //A3
        // Verify the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockRestaurant, response.getBody());

        // Verify that the service method was called
        verify(restaurantService, times(1)).addRestaurantInDB(mockRestaurant);
    }

    @Test
    public void testFindRestaurantById() {
    	//A1
        // Create a mock restaurant ID
        Integer mockRestaurantId = 1;

        // Create a mock restaurant to be returned by the service
        RestaurantDTO mockRestaurant = new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");

        // Mock the service behavior
        when(restaurantService.fetchRestaurantById(mockRestaurantId)).thenReturn(new ResponseEntity<>(mockRestaurant, HttpStatus.OK));

        //A2 : Act reyal call
        // Call the controller method
        ResponseEntity<RestaurantDTO> response = restaurantController.findRestaurantById(mockRestaurantId);

        //A3
        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRestaurant, response.getBody());

        // Verify that the service method was called
        verify(restaurantService, times(1)).fetchRestaurantById(mockRestaurantId);
    }
}
