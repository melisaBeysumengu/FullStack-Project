package obss.project.finalproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import obss.project.finalproject.dto.MessageResponse;
import obss.project.finalproject.dto.ProductRequest;
import obss.project.finalproject.model.Product;
import obss.project.finalproject.model.User;
import obss.project.finalproject.security.MyUserDetails;
import obss.project.finalproject.service.ProductService;
import obss.project.finalproject.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private final ProductService productService;

    @Autowired
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/")
    public List<Product> getAllProducts() {

        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long id) {
        return productService.getProduct(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SELLER','ROLE_ADMIN')")
    public Product updateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SELLER','ROLE_ADMIN')")
    public Boolean deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return true;
    }

    // + " || hasRole('ROLE_SELLER')"
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_SELLER','ROLE_ADMIN')")
    public ResponseEntity<MessageResponse> postProduct(@Valid @RequestBody ProductRequest productRequest) {

        /*if(productService.getProduct(product.getId()) != null){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: This product already exists!"));
        }

        System.out.println(product.getTitle());*/

        //User user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MyUserDetails myUserDetails = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        //logger.info("username is: {}", myUserDetails.getUsername() );


        // User user = userService.findByUsername(myUserDetails.getUsername());

        //logger.info("user is: {}", myUserDetails.getId() );

        Product product = Product.builder().title(productRequest.getTitle()).price(productRequest.getPrice()).seller(userService.findByUsername(myUserDetails.getUsername())).build();

        if (productRequest.getPictureUrl() != null) {
            product.setPictureUrl(productRequest.getPictureUrl());
        }

        productService.save(product);

        return ResponseEntity.ok(new MessageResponse("Product created successfully!"));
    }

}