package org.oglezglez.pricelist.api.v1;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.oglezglez.pricelist.model.v1.Pricelist;
import org.oglezglez.pricelist.error.PricelistNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@WebMvcTest(BrandsApiController.class)
public class BrandsApiControllerTest {
    private final MockMvc mockMvc;

    @MockBean
    private BrandsApiDelegate delegate;

    public BrandsApiControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void pricelistByDateTest() throws Exception {
        when(delegate.pricelistByDate(anyString(), anyString(), any())).
        thenAnswer(new Answer<ResponseEntity<Pricelist>>() {
            @Override
            public ResponseEntity<Pricelist> answer(InvocationOnMock invocation) throws Throwable {
                Pricelist result = (new Pricelist())
                    .brandId(invocation.getArgument(0, String.class))
                    .productId(invocation.getArgument(1, String.class))
                    .priceList("price-list")
                    .startDate(invocation.getArgument(2, OffsetDateTime.class).minus(1, ChronoUnit.MONTHS))
                    .endDate(invocation.getArgument(2, OffsetDateTime.class).plus(1, ChronoUnit.MONTHS))
                    .price(BigDecimal.valueOf(10.00));
                return ResponseEntity.ok().body(result);
            }
        });
        mockMvc.perform(MockMvcRequestBuilders
            .get("/brands/1/products/35455/pricelist?date=2020-06-14T10:00:00+02:00")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(7)))
            .andExpect(jsonPath("$.price-list", is("price-list")))
            .andExpect(jsonPath("$.brand-id", is("1")))
            .andExpect(jsonPath("$.product-id", is("35455")));

    }

    @Test
    public void pricelistByDateNotFoundTest() throws Exception {
        when(delegate.pricelistByDate(anyString(), anyString(), any())).
        thenThrow(new PricelistNotFoundException("not found"));

        mockMvc.perform(MockMvcRequestBuilders
            .get("/brands/1/products/35455/pricelist?date=2020-06-14T10:00:00+02:00")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void pricelistByDateBadRequestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .get("/brands/1/products/35455/pricelist?date=2020-13-14T10:00:00+02:00")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void pricelistByDateServerErrorTest() throws Exception {
        when(delegate.pricelistByDate(anyString(), anyString(), any())).
        thenThrow(new RuntimeException("great exception"));

        mockMvc.perform(MockMvcRequestBuilders
            .get("/brands/1/products/35455/pricelist?date=2020-06-14T10:00:00+02:00")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());

    }

    @Test
    public void pricelistByDateWithoutDateTest() throws Exception {
        when(delegate.pricelistByDate(anyString(), anyString(), any())).
        thenAnswer(new Answer<ResponseEntity<Pricelist>>() {
            @Override
            public ResponseEntity<Pricelist> answer(InvocationOnMock invocation) throws Throwable {
                Pricelist result = (new Pricelist())
                    .brandId(invocation.getArgument(0, String.class))
                    .productId(invocation.getArgument(1, String.class))
                    .priceList("price-list")
                    .startDate(OffsetDateTime.now().minus(1, ChronoUnit.MONTHS))
                    .endDate(OffsetDateTime.now().plus(1, ChronoUnit.MONTHS))
                    .price(BigDecimal.valueOf(10.00));
                return ResponseEntity.ok().body(result);
            }
        });

        mockMvc.perform(MockMvcRequestBuilders
            .get("/brands/1/products/35455/pricelist")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(7)))
            .andExpect(jsonPath("$.price-list", is("price-list")))
            .andExpect(jsonPath("$.brand-id", is("1")))
            .andExpect(jsonPath("$.product-id", is("35455")));

    }
}