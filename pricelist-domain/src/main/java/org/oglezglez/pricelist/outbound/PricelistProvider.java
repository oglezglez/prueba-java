package org.oglezglez.pricelist.outbound;

import java.time.OffsetDateTime;
import java.util.List;

import org.oglezglez.pricelist.model.Pricelist;

public interface PricelistProvider {
    List<Pricelist> getPriceByBrandProductAndDate(String brandId, String productId, OffsetDateTime date);
}
