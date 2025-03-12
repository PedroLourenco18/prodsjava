package br.com.pedroloureco.prodsjava.domain.product;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Data
public class ProductInputDTO {
    @NotBlank(message = "Name can not be null")
    @Size(max = 100, message = "Name is too long, the max length is 100 characters")
    private String name;

    @NotBlank(message = "Description can not be null")
    @Size(max = 100, message = "Description is too long, the max length is 300 characters")
    private String description;

    @NotNull(message = "Price can not be null")
    @Positive(message = "Price must be positive")
    @Digits(integer = 18, fraction = 2, message = "Price format is invalid")
    private BigDecimal price;
}
