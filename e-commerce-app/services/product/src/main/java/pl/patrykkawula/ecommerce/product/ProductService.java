package pl.patrykkawula.ecommerce.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.patrykkawula.ecommerce.exception.ProductPurchaseException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Integer createProduct(ProductRequest request) {
        Product product = productMapper.toProduct(request);
        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseRespone> purchaseProducts(List<ProductPurchaseRequest> request) {
        List<Integer> productIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        List<Product> storedProducts = productRepository.findByIdInOrderById(productIds);

        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exists");
        }

        return null;
    }

    public ProductResponse findById(Integer productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with the ID:: " + productId));
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
