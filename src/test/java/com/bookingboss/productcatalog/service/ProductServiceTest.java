package com.bookingboss.productcatalog.service;

import com.bookingboss.productcatalog.dto.ProductDTO;
import com.bookingboss.productcatalog.model.Product;
import com.bookingboss.productcatalog.repository.ProductRepository;
import com.bookingboss.productcatalog.service.impl.ProductServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetProducts() {
        //given
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1L, "iPhone 7+", 6, 6000.00));
        productList.add(new Product(2L, "iPhone 6+", 3, 4500.00));
        productList.add(new Product(3L, "Samsung s3", 3, 2950.00));

        //when
        when(productRepository.findAll()).thenReturn(productList);
        ProductDTO productDTO = productService.getProducts();

        //then
        assertEquals(3, productDTO.getProducts().size());
    }

    @Test
    public void testGetProduct() throws Exception {
        //given
        Product product = new Product(1L, "iPhone 7+", 6, 6000.00);

        //when
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        ProductDTO productDTO = productService.getProduct(1L);
        List<Product> resultProduct = productDTO.getProducts();

        //then
        assertEquals(1, resultProduct.size());
        assertEquals(new Long(1), resultProduct.get(0).getId());
        assertEquals("iPhone 7+", resultProduct.get(0).getName());
        assertEquals(new Integer(6), resultProduct.get(0).getQuantity());
        assertEquals(new Double(6000.00), resultProduct.get(0).getSaleAmount());
    }

    @Test
    public void testCreateProduct() {
        //given
        Product product = new Product(3L, "Nike Shoe", 10, 2000.00);

        //when
        when(productRepository.save(product)).thenReturn(product);
        ProductDTO productDTO = productService.saveProduct(product);

        //then
        Product savedProduct = productDTO.getProducts().get(0);
        assertEquals(new Long(3), savedProduct.getId());
        assertEquals("Nike Shoe", savedProduct.getName());
        assertEquals(new Integer(10), savedProduct.getQuantity());
        assertEquals(new Double(2000.00), savedProduct.getSaleAmount());
    }

    @Test
    public void testEntityNotFoundException() throws EntityNotFoundException {
        exceptionRule.expect(EntityNotFoundException.class);
        exceptionRule.expectMessage("Entity not found");
        productService.getProduct(20L);
    }
}
