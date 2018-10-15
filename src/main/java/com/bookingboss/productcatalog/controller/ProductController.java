package com.bookingboss.productcatalog.controller;

import com.bookingboss.productcatalog.dto.ProductDTO;
import com.bookingboss.productcatalog.error.ErrorCode;
import com.bookingboss.productcatalog.exception.EmptyProductException;
import com.bookingboss.productcatalog.exception.IdNotFoundException;
import com.bookingboss.productcatalog.service.ProductService;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;


/**
 * Product controller provides different REST end points related to product
 */

@RestController
@RequestMapping("/api")
public class ProductController {

    @Value("exception.empty.product") String exceptionEmptyProduct;

    @Autowired
    ProductService productService;

    /**
     * Get all the products.
     *
     * @return ProductDTO with list of product objects.
     */
    @GetMapping("/products")
    public ResponseEntity<ProductDTO> getProducts() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    /**
     * Get one product from product id.
     *
     * @param id of product.
     * @return ProductDTO which includes the product object.
     */
    @GetMapping(path = "/products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable(value = "id") Long id) throws Exception {
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    /**
     * Create products.
     *
     * @param productDTO includes the new product(s) details.
     * @return ProductDTO which includes the saved product object(s).
     */
    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) throws Exception {
        if (productDTO != null && productDTO.getProducts().size() == 0) {
            throw new EmptyProductException("Empty product object", ErrorCode.EMPTY_PRODUCT_OBJECT);
        }
        return new ResponseEntity<>(productService.saveProduct(productDTO.getProducts().get(0)), HttpStatus.CREATED);
    }

    /**
     * Update a product.
     *
     * @param productDTO includes the new product(s) details.
     * @param id
     * @return ProductDTO which includes the saved product object(s).
     */
    @PutMapping(path="/products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable(value = "id") Long id, @RequestBody ProductDTO productDTO) throws Exception {
        if(productService.getProduct(id) == null) {
            throw new IdNotFoundException("Invalid product id");
        }

        if (productDTO != null && productDTO.getProducts().size() == 0) {
            throw new EmptyProductException("Empty product object", ErrorCode.EMPTY_PRODUCT_OBJECT);
        }

        return new ResponseEntity<>(productService.saveProduct(productDTO.getProducts().get(0)), HttpStatus.OK);
    }}
