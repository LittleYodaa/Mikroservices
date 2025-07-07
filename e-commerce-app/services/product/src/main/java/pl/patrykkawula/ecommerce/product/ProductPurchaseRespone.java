package pl.patrykkawula.ecommerce.product;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRespone(
        @NotNull(message = "Product is mandatory")
        Integer productId,
        @NotNull(message = "Quantity is mandatory")
        double quantity
) {
}
