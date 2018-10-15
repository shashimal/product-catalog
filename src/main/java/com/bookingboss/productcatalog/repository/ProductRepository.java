package com.bookingboss.productcatalog.repository;

import com.bookingboss.productcatalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * Responsible for all the database operations of Product
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
