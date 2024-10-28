package net.springboot.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.springboot.model.Product;
import net.springboot.repository.ProductRepository;
import net.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public void saveProduct(Product product) {
        this.productRepository.save(product);
    }

    public Product getProductById(long id) {
        Optional<Product> optional = this.productRepository.findById(Long.valueOf(id));
        Product product = null;
        if (optional.isPresent()) {
            product = optional.get();
        } else {
            throw new RuntimeException(" Product not found for id :: " + id);
        }
        return product;
    }

    public void deleteProductById(long id) {
        this.productRepository.deleteById(Long.valueOf(id));
    }

    public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(new String[] { sortField }).ascending() : Sort.by(new String[] { sortField }).descending();
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.productRepository.findAll((Pageable)pageRequest);
    }

    public Page<Product> typeFindPaginated(int pageNo, int pageSize, String topClass, String type) {
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize, Sort.by(new String[] { "id" }).ascending());
        if (Objects.equals(topClass, "all"))
            return this.productRepository.findAll((Pageable)pageRequest);
        if (Objects.equals(topClass, "Product Type"))
            return this.productRepository.findByProductTypeContaining(type, (Pageable)pageRequest);
        if (Objects.equals(topClass, "Skin Problem"))
            return this.productRepository.findBySkinProblemContaining(type, (Pageable)pageRequest);
        if (Objects.equals(topClass, "Nursing Stage"))
            return this.productRepository.findByNursingStageContaining(type, (Pageable)pageRequest);
        return this.productRepository.findAll((Pageable)pageRequest);
    }
}