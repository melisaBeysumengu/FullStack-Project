package obss.project.finalproject.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import obss.project.finalproject.dto.MessageResponse;
import obss.project.finalproject.dto.ProductRequest;
import obss.project.finalproject.exception.ResourceNotFoundException;
import obss.project.finalproject.model.Product;
import obss.project.finalproject.repository.ProductRepository;
import obss.project.finalproject.repository.UserRepository;
import obss.project.finalproject.security.MyUserDetails;
import obss.project.finalproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    // productRepository constructor injection
    private ProductRepository productRepository;
    private UserRepository userRepository;

    /*public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }*/


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }


    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Boolean deleteProduct(Long id) {

        ResponseEntity<?> response = controlPoint(id);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return false;
        }

        productRepository.removeProductById(id);

        return true;
    }

    public ResponseEntity<?> controlPoint(Long id) {
        if (!productRepository.existsProductById(id)) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new MessageResponse("Error: Product does not exist!"));
        }

        if (!userRepository.existsByUsername(productRepository.findProductById(id).getSeller().getUsername())) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new MessageResponse("Error: User does not exist!"));
        }

        MyUserDetails myUserDetails = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (!productRepository.findProductById(id).getSeller().getUsername().equals(myUserDetails.getUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Error: You can not delete this product!"));
        }

        return ResponseEntity.ok(new MessageResponse("Checkpoint is passed successfully!"));
    }

    @Override
    public Product updateProduct(Long id, ProductRequest newInformation) {

        ResponseEntity<?> response = controlPoint(id);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ResourceNotFoundException(response.getBody().toString());
        }

        Product newProduct = productRepository.findProductById(id);
        newProduct.setPictureUrl(newInformation.getPictureUrl());
        newProduct.setPrice(newInformation.getPrice());
        newProduct.setTitle(newInformation.getTitle());

        return productRepository.save(newProduct);

    }



    /*@Override
    public User getProductBySellerId(Long id) {

        return productRepository.findProductBySeller_id(id);
    }*/
}
