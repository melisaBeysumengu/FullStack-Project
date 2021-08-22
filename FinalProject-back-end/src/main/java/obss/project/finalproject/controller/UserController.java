package obss.project.finalproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import obss.project.finalproject.dto.ProductRequest;
import obss.project.finalproject.dto.SignupRequest;
import obss.project.finalproject.dto.UpdateUserRequest;
import obss.project.finalproject.model.Product;
import obss.project.finalproject.model.User;
import obss.project.finalproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public List<User> getAll() {
        return userService.getAll();
    }

    @DeleteMapping("/")
    public ResponseEntity<?> removeUser(@Valid @RequestBody String username) {
        return userService.removeUserByUsername(username);
    }

    @PutMapping("/{username}")
    public User updateUser(@PathVariable("username") String username, @Valid @RequestBody UpdateUserRequest signupRequest) {
        return userService.updateUser(username, signupRequest);
    }



}
