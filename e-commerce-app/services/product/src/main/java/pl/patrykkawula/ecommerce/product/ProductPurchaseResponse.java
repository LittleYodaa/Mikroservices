package pl.patrykkawula.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        @NotNull(message = "Product id is mandatory")
        Integer productId,
        @NotNull(message = "Product name is mandatory")
        String name,
        @NotNull(message = "Product description is mandatory")
        String description,
        @Positive(message = "Price should be positive")
        BigDecimal price,
        @NotNull(message = "Quantity is mandatory")
        double quantity
) {
}
