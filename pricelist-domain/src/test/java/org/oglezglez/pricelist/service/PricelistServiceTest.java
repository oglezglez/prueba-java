package org.oglezglez.pricelist.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.oglezglez.pricelist.error.PricelistNotFoundException;
import org.oglezglez.pricelist.model.Pricelist;
import org.oglezglez.pricelist.outbound.PricelistProvider;

import lombok.Value;

public class PricelistServiceTest {
    private PricelistProvider repo =mock(PricelistProvider.class);
    private PricelistService service = new PricelistServiceImpl(repo);

    @Test
    public void testGetPriceWithDate(){
        when(repo.getPriceByBrandProductAndDate(anyString(), anyString(), any())).
        thenAnswer(new Answer<List<Pricelist>>(){
            public List<Pricelist> answer(InvocationOnMock invocation) {
                return List.of(Pricelist.of(
                    invocation.getArgument(0, String.class),
                    invocation.getArgument(1, String.class),
                    "price-family",
                    invocation.getArgument(2, OffsetDateTime.class).minus(1,ChronoUnit.MONTHS),
                    invocation.getArgument(2, OffsetDateTime.class).plus(1,ChronoUnit.MONTHS),
                    100.00,
                    Currency.getInstance("EUR"),
                    1
                ));
            }
        });

        TestCase tc = TestCase.of("1","35455", LocalDateTime.of(2020, 06, 14, 10, 0).atZone(ZoneId.systemDefault()).toOffsetDateTime());
        checkResult(tc, service.getPrice(tc.brandId, tc.productId, tc.date));
    }

    @Test
    public void testGetPriceWithoutDate(){
        when(repo.getPriceByBrandProductAndDate(anyString(), anyString(), any())).
        thenAnswer(new Answer<List<Pricelist>>(){
            public List<Pricelist> answer(InvocationOnMock invocation) {
                return List.of(Pricelist.of(
                    invocation.getArgument(0, String.class),
                    invocation.getArgument(1, String.class),
                    "price-family",
                    invocation.getArgument(2, OffsetDateTime.class).minus(1,ChronoUnit.MONTHS),
                    invocation.getArgument(2, OffsetDateTime.class).plus(1,ChronoUnit.MONTHS),
                    100.00,
                    Currency.getInstance("EUR"),
                    1
                ));
            }
        });

        TestCase tc = TestCase.of("1","35455", OffsetDateTime.now());
        checkResult(tc, service.getPrice(tc.brandId, tc.productId));
    }
    
    @Test
    public void testGetPriceWithSeveralResults(){
        when(repo.getPriceByBrandProductAndDate(anyString(), anyString(), any())).
        thenAnswer(new Answer<List<Pricelist>>(){
            public List<Pricelist> answer(InvocationOnMock invocation) {
                return List.of(
                    Pricelist.of(
                        invocation.getArgument(0, String.class),
                        invocation.getArgument(1, String.class),
                        "price-family",
                        invocation.getArgument(2, OffsetDateTime.class).minus(1,ChronoUnit.MONTHS),
                        invocation.getArgument(2, OffsetDateTime.class).plus(1,ChronoUnit.MONTHS),
                        100.00,
                        Currency.getInstance("EUR"),
                        0
                    ),
                    Pricelist.of(
                        invocation.getArgument(0, String.class),
                        invocation.getArgument(1, String.class),
                        "price-family",
                        invocation.getArgument(2, OffsetDateTime.class).minus(1,ChronoUnit.MONTHS),
                        invocation.getArgument(2, OffsetDateTime.class).plus(1,ChronoUnit.MONTHS),
                        200.00,
                        Currency.getInstance("EUR"),
                        1
                    )
                );
            }
        });

        TestCase tc = TestCase.of("1","35455", OffsetDateTime.now());
        checkResult(tc, service.getPrice(tc.brandId, tc.productId));
    }
    
    @Test
    public void testGetPriceWithoutResponse(){
        when(repo.getPriceByBrandProductAndDate(anyString(), anyString(), any())).
        thenReturn(List.of());

        assertThrows(PricelistNotFoundException.class, () -> service.getPrice("1", "productId", null));
    }
    
    @Value(staticConstructor = "of")
    static private class TestCase {
        String brandId;
        String productId;
        OffsetDateTime date;
    }

    private void checkResult(TestCase tc, Pricelist result) {
        assertNotNull(result);
        assertEquals(tc.brandId, result.getBrandId());
        assertEquals(tc.productId, result.getProductId());
        assertTrue(tc.date.isAfter(result.getFromDate()));
        assertTrue(tc.date.isBefore(result.getToDate()));
        assertEquals(1, result.getPriority());
    }
}
