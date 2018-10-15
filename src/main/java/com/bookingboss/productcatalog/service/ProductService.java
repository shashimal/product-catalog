package com.bookingboss.productcatalog.service;

import com.bookingboss.productcatalog.dto.ProductDTO;
import com.bookingboss.productcatalog.model.Product;

import java.util.List;

/**
 *
 */
public interface ProductService {

    /**
     * Get list of product information
     *
     * @return ProductDTO object
     */
    ProductDTO getProducts();

    /**
     * Get one product from product id
     *
     * @param id of product
     * @return ProductDTO object
     */
    ProductDTO getProduct(Long id) throws Exception;

    /**
     * Create product object.
     *
     * @param product object.
     * @return ProductDTO with saved product details.
     */
    ProductDTO saveProduct(Product product);

}
