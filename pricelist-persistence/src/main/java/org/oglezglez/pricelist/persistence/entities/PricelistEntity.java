package org.oglezglez.pricelist.persistence.entities;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRICELIST")
public class PricelistEntity {
                                                                                                                          
    @Column(name="BRAND_ID")
    private String brandId;

    @Column(name="START_DATE")
    private OffsetDateTime startDate;

    @Column(name="END_DATE")
    private OffsetDateTime endDate;

    @Column(name="PRODUCT_ID")
    private String productId;

    @Column(name="PRIORITY")
    private Integer priority;

    @Column(name="PRICE")
    private Double price;

    @Column(name="CURR")
    private String currency;

    @Id
    @Column(name="PRICE_LIST")
    private String priceList;
}
