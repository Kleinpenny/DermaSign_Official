package net.springboot.service;

import java.util.List;
import net.springboot.model.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
    List<Product> getAllProducts();

    void saveProduct(Product paramProduct);

    Product getProductById(long paramLong);

    void deleteProductById(long paramLong);

    Page<Product> findPaginated(int paramInt1, int paramInt2, String paramString1, String paramString2);

    Page<Product> typeFindPaginated(int paramInt1, int paramInt2, String paramString1, String paramString2);
}
