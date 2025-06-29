package pl.patrykkawula.ecommerce.customer;

import lombok.Builder;

@Builder
public record CustomerResponse (
    String id,
    String firstname,
    String lastname,
    String email,
    Address address
) {

}
