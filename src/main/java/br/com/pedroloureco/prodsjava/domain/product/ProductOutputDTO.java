package br.com.pedroloureco.prodsjava.domain.product;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductOutputDTO {
    private UUID id;

    private String name;

    private String description;

    private BigDecimal price;
}
