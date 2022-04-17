package org.oglezglez.pricelist.persistence.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.oglezglez.pricelist.model.Pricelist;
import org.oglezglez.pricelist.persistence.entities.PricelistEntity;
import org.oglezglez.pricelist.persistence.repositories.PricelistRepository;

public class PricelistProviderImplTest {
    private final PricelistProviderImpl provider;
    private final PricelistRepository repo;

    public PricelistProviderImplTest() {
        this.repo = mock(PricelistRepository.class);
        this.provider = new PricelistProviderImpl(repo);
    }

    @Test
    public void getPriceByBrandProductAndDateTest() {
        when(this.repo.getPriceByBrandAndProductAndDate(anyString(), anyString(), any())).
            then((invocation) -> 
                List.of(new PricelistEntity(
                    invocation.getArgument(0, String.class), 
                    invocation.getArgument(2, OffsetDateTime.class).minus(1, ChronoUnit.MONTHS),
                    invocation.getArgument(2, OffsetDateTime.class).plus(1, ChronoUnit.MONTHS), 
                    invocation.getArgument(1, String.class), 
                    0, 
                    10.00, 
                    "EUR",
                    "1")
                )
            );
        List<Pricelist> result = provider.getPriceByBrandProductAndDate("1", "product-id", OffsetDateTime.now());
        assertNotNull(result);
        assertTrue(!result.isEmpty());
        assertEquals("1", result.get(0).getBrandId());
        assertEquals("product-id", result.get(0).getProductId());
        assertTrue(OffsetDateTime.now().isAfter(result.get(0).getFromDate()));
        assertTrue(OffsetDateTime.now().isBefore(result.get(0).getToDate()));
    }
}
