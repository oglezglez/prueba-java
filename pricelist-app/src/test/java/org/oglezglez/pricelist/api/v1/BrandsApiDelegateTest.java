package org.oglezglez.pricelist.api.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.oglezglez.pricelist.error.PricelistNotFoundException;
import org.oglezglez.pricelist.model.Pricelist;
import org.oglezglez.pricelist.service.PricelistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BrandsApiDelegateTest {
    private PricelistService prices =mock(PricelistService.class);
    private BrandsApiDelegate delegate = new BrandsApiDelegateImpl(prices);

    @Test
    public void pricesByDateTest() throws Exception {
        when(prices.getPrice(any(),any(), any())).
        thenAnswer(new Answer<Pricelist>() {
            @Override
            public Pricelist answer(InvocationOnMock invocation) throws Throwable {
                return Pricelist.of(
                    invocation.getArgument(0, String.class),
                    invocation.getArgument(1, String.class),
                    "price-list",
                    invocation.getArgument(2, OffsetDateTime.class).minus(1, ChronoUnit.MONTHS),
                    invocation.getArgument(2, OffsetDateTime.class).plus(1, ChronoUnit.MONTHS),
                    10.00,
                    Currency.getInstance("EUR"),
                    1);
            }
        });

        ResponseEntity<org.oglezglez.pricelist.model.v1.Pricelist> response = delegate.pricelistByDate("1", "35455", OffsetDateTime.now());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getBrandId());
        assertEquals("35455", response.getBody().getProductId());
        assertEquals("price-list", response.getBody().getPriceList());
        assertTrue(OffsetDateTime.now().isAfter(response.getBody().getStartDate()));
        assertTrue(OffsetDateTime.now().isBefore(response.getBody().getEndDate()));
    }

    @Test
    public void pricesByDateWithoutDateTest() throws Exception {
        when(prices.getPrice(anyString(), anyString())).
        thenAnswer(new Answer<Pricelist>() {
            @Override
            public Pricelist answer(InvocationOnMock invocation) throws Throwable {
                return Pricelist.of(
                    invocation.getArgument(0, String.class),
                    invocation.getArgument(1, String.class),
                    "price-list",
                    OffsetDateTime.now().minus(1, ChronoUnit.MONTHS),
                    OffsetDateTime.now().plus(1, ChronoUnit.MONTHS),
                    10.00,
                    Currency.getInstance("EUR"),
                    1);
            }
        });
        ResponseEntity<org.oglezglez.pricelist.model.v1.Pricelist> response = delegate.pricelistByDate("1", "35455", null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getBrandId());
        assertEquals("35455", response.getBody().getProductId());
        assertEquals("price-list", response.getBody().getPriceList());
        assertTrue(OffsetDateTime.now().isAfter(response.getBody().getStartDate()));
        assertTrue(OffsetDateTime.now().isBefore(response.getBody().getEndDate()));
    }

    @Test
    public void pricesByDateNotFoundTest() throws Exception {
        when(prices.getPrice(any(),any())).
        thenReturn(null);
        assertThrows(
            PricelistNotFoundException.class,
            () -> delegate.pricelistByDate("1", "productId", null)
        );
     }

}
