package net.springboot.repository;


import net.springboot.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.productType LIKE %:type%")
    Page<Product> findByProductTypeContaining(@Param("type") String paramString, Pageable paramPageable);

    @Query("SELECT p FROM Product p WHERE p.skinProblem LIKE %:type%")
    Page<Product> findBySkinProblemContaining(@Param("type") String paramString, Pageable paramPageable);

    @Query("SELECT p FROM Product p WHERE p.nursingStage LIKE %:type%")
    Page<Product> findByNursingStageContaining(@Param("type") String paramString, Pageable paramPageable);

    @Query("SELECT p FROM Product p WHERE p.professional = 'Yes'")
    Page<Product> findByProfessionalTrue(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.professional != 'Yes'")
    Page<Product> findAllNormalProducts(Pageable pageable);
}
