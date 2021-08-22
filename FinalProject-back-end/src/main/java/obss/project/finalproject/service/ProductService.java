package obss.project.finalproject.service;

import obss.project.finalproject.dto.ProductRequest;
import obss.project.finalproject.exception.ResourceNotFoundException;
import obss.project.finalproject.model.Product;
import obss.project.finalproject.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProduct(Long id) throws ResourceNotFoundException;

    Product save(Product product);

    Boolean deleteProduct(Long id);

    Product updateProduct(Long id, ProductRequest newInformation);

}
