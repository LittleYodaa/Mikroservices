package pl.patrykkawula.ecommerce.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.patrykkawula.ecommerce.customer.CustomerClient;
import pl.patrykkawula.ecommerce.customer.CustomerResponse;
import pl.patrykkawula.ecommerce.exception.BusinessException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;

    public Integer createdOrder(@Valid OrderRequest request) {
        //check customer --> OpenFeign
        CustomerResponse customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exist with the provided ID"));

        //purchase the products --> product microservice (RestTemplate)

        //persist order

        //persist order lines

        //start payment process

        //send the order confirmation --> notification microservice(kafka)
    }
}
