package com.tqs.lab2_2;

import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class ProductFinderServiceTest {
    @Mock
    private ISimpleHttpClient httpClientMock;

    @InjectMocks
    private ProductFinderService productFinderService;

    @DisplayName("Test mock findProductDetails")
    @Test
    public void findProductDetails() throws IOException, ParseException {

        String json = """
                {
                    "id": 3,
                    "title": "Mens Cotton Jacket",
                    "price": 55.99,
                    "description": "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.",
                    "category": "men's clothing",
                    "image": "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
                    "rating": {
                      "rate": 4.7,
                      "count": 500
                    }
                }""";

        when(httpClientMock.doHttpGet("https://fakestoreapi.com/products/3")).thenReturn(json);
        when(httpClientMock.doHttpGet("https://fakestoreapi.com/products/300")).thenReturn(null);

        Product product = productFinderService.findProductDetails(3).orElseThrow(() -> new AssertionError("Product not found"));

        assertEquals(3, product.getId());
        assertEquals("Mens Cotton Jacket", product.getTitle());
        assertEquals(55.99, product.getPrice());
        assertEquals("great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.", product.getDescription());
        assertEquals("men's clothing", product.getCategory());
        assertEquals("https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg", product.getImage());
        assertEquals(4.7, product.getRating().getRate());
        assertEquals(500, product.getRating().getCount());

        Product productNull = productFinderService.findProductDetails(300).orElse(null);

        assertNull(productNull);

        verify(httpClientMock, times(2)).doHttpGet(anyString());
    }
}