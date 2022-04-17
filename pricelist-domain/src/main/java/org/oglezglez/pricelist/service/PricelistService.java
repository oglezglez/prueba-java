package org.oglezglez.pricelist.service;

import java.time.OffsetDateTime;

import org.oglezglez.pricelist.model.Pricelist;

public interface PricelistService {
    Pricelist getPrice(String brandId, String productId);
    Pricelist getPrice(String brandId, String productId, OffsetDateTime date);
}
