package obss.project.finalproject.controller;

import lombok.AllArgsConstructor;
import obss.project.finalproject.dto.MessageResponse;
import obss.project.finalproject.model.Product;
import obss.project.finalproject.model.User;
import obss.project.finalproject.security.MyUserDetails;
import obss.project.finalproject.service.ProductService;
import obss.project.finalproject.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/black-list")
public class BlackListController {

    private UserService userService;

    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);

    @GetMapping("/")
    public List<User> getBlackList() {
        MyUserDetails myUserDetails = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return userService.getBlackList(myUserDetails.getUsername());
    }

    @PutMapping("/")
    public ResponseEntity<?> addBlackList(@Valid @RequestBody String username) {
        MyUserDetails myUserDetails = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        /*logger.info("add into {}", myUserDetails.getUsername());
        logger.info("user id is {}", productId);
        logger.info("user id is {}", productService.getProduct(productId).toString());*/
        userService.addBlackList(myUserDetails.getUsername(), userService.findByUsername(username));
        return ResponseEntity.ok(new MessageResponse("User added into black-list successfully!"));
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteBlackList(@Valid @RequestBody String username) {
        MyUserDetails myUserDetails = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        userService.deleteBlackList(myUserDetails.getUsername(), userService.findByUsername(username));
        return ResponseEntity.ok(new MessageResponse("User removed from black-list successfully!"));
    }
}
