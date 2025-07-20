package pl.patrykkawula.ecommerce.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.patrykkawula.ecommerce.customer.CustomerClient;
import pl.patrykkawula.ecommerce.customer.CustomerResponse;
import pl.patrykkawula.ecommerce.exception.BusinessException;
import pl.patrykkawula.ecommerce.order.product.ProductClient;
import pl.patrykkawula.ecommerce.orderline.OrderLineRequest;
import pl.patrykkawula.ecommerce.orderline.OrderLineService;
import pl.patrykkawula.ecommerce.product.PurchaseRequest;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;

    public Integer createdOrder(@Valid OrderRequest request) {
        //check customer --> OpenFeign
        CustomerResponse customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exist with the provided ID"));

        //purchase the products --> product microservice (RestTemplate)
        productClient.purchaseProducts(request.products());

        //persist order
        Order order = orderRepository.save(orderMapper.toOrder(request));

        //persist order lines
        for (PurchaseRequest purchaseRequest: request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // todo start payment process

        //send the order confirmation --> notification microservice(kafka)

        return order.getId();
    }
}
