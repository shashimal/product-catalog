package com.bookingboss.productcatalog.controller;

import com.bookingboss.productcatalog.dto.ProductDTO;
import com.bookingboss.productcatalog.model.Product;
import com.bookingboss.productcatalog.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = ProductController.class, secure = false)
public class ProductControllerTest {
    @Mock
    private ProductController productController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testGetProducts() throws Exception {
        //given
        String date = "2018-10-14T12:08:47.475";
        LocalDateTime localDate = LocalDateTime.parse(date);

        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1L, "iPhone 7+", 6, 6000.00));
        productList.add(new Product(2L, "iPhone 6+", 3, 4500.00));
        productList.add(new Product(3L, "Samsung s3", 3, 2950.00));

        ProductDTO productDTO = new ProductDTO("1", localDate, productList);

        //when
        when(productService.getProducts()).thenReturn(productDTO);

        //then
        mockMvc.perform(get("/api/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[0].name", is("iPhone 7+")))
                .andExpect(jsonPath("$.products[0].quantity", is(6)))
                .andExpect(jsonPath("$.products[0].saleAmount", is(6000.00)))
                .andExpect(jsonPath("$.products[1].name", is("iPhone 6+")))
                .andExpect(jsonPath("$.products[1].quantity", is(3)))
                .andExpect(jsonPath("$.products[1].saleAmount", is(4500.00)))
                .andExpect(jsonPath("id", is("1")))
                .andExpect(jsonPath("timestamp", is("2018-10-14T12:08:47.475")));
    }

    @Test
    public void testGetProduct() throws Exception {
        //given
        String date = "2018-10-14T12:08:47.475";
        LocalDateTime localDate = LocalDateTime.parse(date);

        List<Product> productList = new ArrayList<>();
        productList.add(new Product(3L, "Samsung s3", 3, 2950.00));

        ProductDTO productDTO = new ProductDTO("1", localDate, productList);

        //when
        when(productService.getProduct(3L)).thenReturn(productDTO);

        //then
        mockMvc.perform(get("/api/products/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[0].name", is("Samsung s3")))
                .andExpect(jsonPath("$.products[0].quantity", is(3)))
                .andExpect(jsonPath("$.products[0].saleAmount", is(2950.00)))
                .andExpect(jsonPath("id", is("1")))
                .andExpect(jsonPath("timestamp", is("2018-10-14T12:08:47.475")));
    }

    @Test
    public void testCreateProduct() throws Exception {
        //given
        String date = "2018-10-14T12:08:47.475";
        LocalDateTime localDate = LocalDateTime.parse(date);

        Map<String, List<Product>> productsMap = new HashMap<>();
        List<Product> productList = new ArrayList<>();

        Product product = new Product(3L, "Nike Shoe", 10, 2000.00);
        productList.add(product);
        productsMap.put("products", productList);

        ProductDTO productDTO = new ProductDTO("1", localDate, productList);

        //when
        when(productService.saveProduct(product)).thenReturn(productDTO);

        //then
        mockMvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productsMap)))
                .andExpect(jsonPath("$.products[0].name", is("Nike Shoe")))
                .andExpect(jsonPath("$.products[0].quantity", is(10)))
                .andExpect(jsonPath("$.products[0].saleAmount", is(2000.00)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateProduct() throws Exception {
        //given

        //Original Product
        String date1 = "2018-10-14T12:08:47.475";
        LocalDateTime localDate1 = LocalDateTime.parse(date1);

        List<Product> productList1 = new ArrayList<>();
        Product originalProduct = new Product(3L, "Nike Shoe", 10, 2000.00);
        productList1.add(originalProduct);

        ProductDTO productDTO1 = new ProductDTO("1", localDate1, productList1);

        //Updated Product
        Product product = new Product(3L, "Nike Shoe Updated", 20, 3000.00);
        String date = "2018-10-15T12:08:47.475";
        LocalDateTime localDate = LocalDateTime.parse(date);

        Map<String, List<Product>> productsMap = new HashMap<>();
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productsMap.put("products", productList);
        ProductDTO productDTO = new ProductDTO("1", localDate, productList);

        //when
        when(productService.getProduct(3L)).thenReturn(productDTO1);
        when(productService.saveProduct(product)).thenReturn(productDTO);

        //then
        mockMvc.perform(put("/api/products/3").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productsMap)))
                .andExpect(jsonPath("$.products[0].id", is(3)))
                .andExpect(jsonPath("$.products[0].name", is("Nike Shoe Updated")))
                .andExpect(jsonPath("$.products[0].quantity", is(20)))
                .andExpect(jsonPath("$.products[0].saleAmount", is(3000.00)))
                .andExpect(status().isOk());
    }

    @Test
    public void testIdNotFoundException() {
        try {

            Map<String, List<Product>> productsMap = new HashMap<>();
            List<Product> productList = new ArrayList<>();

            Product product = new Product(5L, "Nike Shoe", 10, 2000.00);
            productList.add(product);
            productsMap.put("products", productList);

            mockMvc.perform(put("/api/products/5").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productsMap)))
                    .andExpect(jsonPath("message",is("Invalid product id")))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void testEmptyProductExceptionWithPUT() {
        try {
            //given
            String date = "2018-10-14T12:08:47.475";
            LocalDateTime localDate = LocalDateTime.parse(date);

            List<Product> productList1 = new ArrayList<>();
            Product originalProduct = new Product(3L, "Nike Shoe", 10, 2000.00);
            productList1.add(originalProduct);

            ProductDTO productDTO1 = new ProductDTO("1", localDate, productList1);

            Map<String, List<Product>> productsMap = new HashMap<>();
            List<Product> productList = new ArrayList<>();
            productsMap.put("products", productList);

            //when
            when(productService.getProduct(3L)).thenReturn(productDTO1);

            //then
            mockMvc.perform(put("/api/products/3").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productsMap)))
                    .andExpect(jsonPath("message",is("Empty product object")))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void testEmptyProductExceptionWithPOST() {
        try {
            //given
            String date = "2018-10-14T12:08:47.475";
            LocalDateTime localDate = LocalDateTime.parse(date);

            List<Product> productList1 = new ArrayList<>();
            Product originalProduct = new Product(3L, "Nike Shoe", 10, 2000.00);
            productList1.add(originalProduct);

            ProductDTO productDTO1 = new ProductDTO("1", localDate, productList1);

            Map<String, List<Product>> productsMap = new HashMap<>();
            List<Product> productList = new ArrayList<>();
            productsMap.put("products", productList);

            //when
            when(productService.getProduct(3L)).thenReturn(productDTO1);

            //then
            mockMvc.perform(post("/api/products/").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productsMap)))
                    .andExpect(jsonPath("message",is("Empty product object")))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void testHandleHttpMessageNotReadableException() {
        Map<String, List<Product>> productsMap = new HashMap<>();
        List<Product> productList = new ArrayList<>();
        productsMap.put("products", productList);
        try {
            mockMvc.perform(post("/api/products/").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productsMap)))
                    .andExpect(jsonPath("message",is("Empty product object")))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            fail(e.toString());
        }
    }
}
