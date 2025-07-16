package pl.patrykkawula.ecommerce.orderline;

import jakarta.persistence.*;
import lombok.*;
import pl.patrykkawula.ecommerce.order.Order;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class OrderLine {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private Integer productId;
    private double quantity;
}
