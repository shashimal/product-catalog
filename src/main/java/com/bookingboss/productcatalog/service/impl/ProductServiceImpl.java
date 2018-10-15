package com.bookingboss.productcatalog.service.impl;

import com.bookingboss.productcatalog.dto.ProductDTO;
import com.bookingboss.productcatalog.model.Product;
import com.bookingboss.productcatalog.repository.ProductRepository;
import com.bookingboss.productcatalog.service.ProductService;
import com.bookingboss.productcatalog.utils.DateUtil;
import com.bookingboss.productcatalog.utils.UniqueIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of product service
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    /**
     * Get list of product information
     */
    @Override
    public ProductDTO getProducts() {
        List<Product> products = productRepository.findAll();
        return this.getProductDTO(products);
    }

    /**
     * Get one product from product id
     */
    @Override
    public ProductDTO getProduct(Long id) throws EntityNotFoundException {
        Optional<Product> product = productRepository.findById(id);

        if (!product.isPresent()) {
            throw new EntityNotFoundException("Entity not found");
        }

        List<Product> products = new ArrayList<>();
        products.add(product.get());
        return this.getProductDTO(products);
    }

    /**
     * Create product objects
     */
    @Override
    public ProductDTO saveProduct(Product product) {
        List<Product> savedProducts = null;
        if (product != null) {
            savedProducts = new ArrayList<>();
            Product savedProduct = productRepository.save(product);
            savedProducts.add(savedProduct);

        }
        return this.getProductDTO(savedProducts);
    }


    private ProductDTO getProductDTO(List<Product> products) {
        return new ProductDTO(UniqueIdUtil.generateUniqueId(), DateUtil.getTimestampInUTC(), products);
    }
}
