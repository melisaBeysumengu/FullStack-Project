package obss.project.finalproject.repository;

import obss.project.finalproject.model.Product;
import obss.project.finalproject.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Product findProductById(Long id);

    List<Product> findAll();

    List<Product> findAllByTitle(String title);

    //User findProductBySeller_id(Long id);

    //ResponseEntity<?> deleteProductById(Long id);

    void removeProductById(Long id);

    Boolean existsProductById(Long id);




}