// PriceHistoryRepository.java
package com.xymzsfxy.backend.repository;

import com.xymzsfxy.backend.entity.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {
}