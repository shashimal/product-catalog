package com.bookingboss.productcatalog.dto;

import com.bookingboss.productcatalog.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    String id;
    LocalDateTime  timestamp;
    List<Product> products;
}
