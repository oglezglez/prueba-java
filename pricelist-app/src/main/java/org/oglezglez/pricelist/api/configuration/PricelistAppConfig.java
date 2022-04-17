package org.oglezglez.pricelist.api.configuration;

import org.oglezglez.pricelist.outbound.PricelistProvider;
import org.oglezglez.pricelist.service.PricelistService;
import org.oglezglez.pricelist.service.PricelistServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PricelistAppConfig {
    @Bean
	public PricelistService pricelistService(PricelistProvider prov) {
		return new PricelistServiceImpl(prov);
	}

}
