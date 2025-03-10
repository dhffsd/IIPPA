// ProductRepository.java
package com.xymzsfxy.backend.repository;

import com.xymzsfxy.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ProductRepository extends JpaRepository<Product, Long> {
}