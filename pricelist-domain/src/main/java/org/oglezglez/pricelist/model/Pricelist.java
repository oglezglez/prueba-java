package org.oglezglez.pricelist.model;

import java.time.OffsetDateTime;
import java.util.Currency;

import lombok.Value;

@Value(staticConstructor = "of")
public class Pricelist {
    private String brandId;
    private String productId;
    private String priceFamily;
    private OffsetDateTime fromDate;
    private OffsetDateTime toDate;
    private Double price;
    private Currency curr;
    private Integer priority;
}
