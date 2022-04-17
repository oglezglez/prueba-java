package org.oglezglez.pricelist.persistence.respositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.oglezglez.pricelist.persistence.PersistenceConfiguration;
import org.oglezglez.pricelist.persistence.entities.PricelistEntity;
import org.oglezglez.pricelist.persistence.repositories.PricelistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import lombok.Value;

@SpringBootTest(classes = PersistenceConfiguration.class)
class PricelistRepositoryTest {

  @Autowired
  private PricelistRepository repository;

  @Test
  void injectedComponentsAreNotNull() {
    assertNotNull(repository);
  }

  @Test
  @Sql("/pricelist.sql")
  void getPriceByBrandAndProductAndDateTest() {
    List.of(
      TestCase.of("1","35455", LocalDateTime.of(2020, 06, 14, 10, 0).atZone(ZoneId.systemDefault()).toOffsetDateTime()),
      TestCase.of("1","35455", LocalDateTime.of(2020, 06, 14, 16, 0).atZone(ZoneId.systemDefault()).toOffsetDateTime()),
      TestCase.of("1","35455", LocalDateTime.of(2020, 06, 14, 21, 0).atZone(ZoneId.systemDefault()).toOffsetDateTime()),
      TestCase.of("1","35455", LocalDateTime.of(2020, 06, 15, 10, 0).atZone(ZoneId.systemDefault()).toOffsetDateTime()),
      TestCase.of("1","35455", LocalDateTime.of(2020, 06, 16, 21, 0).atZone(ZoneId.systemDefault()).toOffsetDateTime())
  ).stream()
  .forEach(tc -> {
    List<PricelistEntity> result = repository.getPriceByBrandAndProductAndDate(tc.brandId, tc.productId, tc.date);
    assertNotNull(result);
    assertTrue(!result.isEmpty());
    assertEquals(tc.brandId, result.get(0).getBrandId());
    assertEquals(tc.productId, result.get(0).getProductId());
    assertTrue(tc.date.isAfter(result.get(0).getStartDate()));
    assertTrue(tc.date.isBefore(result.get(0).getEndDate()));
});

  }

  @Value(staticConstructor = "of")
  static private class TestCase {
      String brandId;
      String productId;
      OffsetDateTime date;
  }
}