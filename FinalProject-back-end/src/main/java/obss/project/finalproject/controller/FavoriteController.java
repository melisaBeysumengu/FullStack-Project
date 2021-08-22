package obss.project.finalproject.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import obss.project.finalproject.dto.MessageResponse;
import obss.project.finalproject.dto.ProductRequest;
import obss.project.finalproject.model.Product;
import obss.project.finalproject.security.MyUserDetails;
import obss.project.finalproject.service.ProductService;
import obss.project.finalproject.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Transient;
import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private UserService userService;

    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);

    @GetMapping("/")
    public List<Product> getFavorite() {
        MyUserDetails myUserDetails = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return userService.getFavoriteList(myUserDetails.getUsername());
    }

    @PutMapping("/")
    public ResponseEntity<?> addFavorite(@Valid @RequestBody Long productId) {
        MyUserDetails myUserDetails = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        logger.info("add into {}", myUserDetails.getUsername());
        logger.info("product id is {}", productId);
        logger.info("product id is {}", productService.getProduct(productId).toString());
        userService.addFavoriteList(myUserDetails.getUsername(), productService.getProduct(productId));
        return ResponseEntity.ok(new MessageResponse("Product added into favorite list successfully!"));
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteFavorite(@Valid @RequestBody long productId) {
        MyUserDetails myUserDetails = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        userService.deleteFavoriteList(myUserDetails.getUsername(), productService.getProduct(productId));
        return ResponseEntity.ok(new MessageResponse("Product deleted from favorite list successfully!"));
    }
}
