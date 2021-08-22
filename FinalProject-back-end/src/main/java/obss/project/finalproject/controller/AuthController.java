package obss.project.finalproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import obss.project.finalproject.dto.JwtResponse;
import obss.project.finalproject.dto.LoginRequest;
import obss.project.finalproject.dto.MessageResponse;
import obss.project.finalproject.dto.SignupRequest;
import obss.project.finalproject.model.Role;
import obss.project.finalproject.model.RoleType;
import obss.project.finalproject.model.User;
import obss.project.finalproject.security.JwtUtils;
import obss.project.finalproject.security.MyUserDetails;
import obss.project.finalproject.service.RoleService;
import obss.project.finalproject.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final RoleService roleService;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signin")
    public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return JwtResponse
                .builder()
                .token(jwt)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {

        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account

        User user = User
                .builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();

        Set<Role> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        logger.info("user role: {}", ((Role) strRoles.toArray()[0]).getName());

        if (strRoles == null || strRoles.iterator().next().getName() == RoleType.ROLE_USER) {
            Role userRole = roleService.findRoleByName(RoleType.ROLE_USER);
            roles.add(userRole);
        } else if (strRoles.iterator().next().getName() == RoleType.ROLE_ADMIN) {
            Role userRole = roleService.findRoleByName(RoleType.ROLE_ADMIN);
            roles.add(userRole);
        } else {
            Role userRole = roleService.findRoleByName(RoleType.ROLE_SELLER);
            roles.add(userRole);
        }

        user.setRoles(roles);
        user.setBlackList(new ArrayList<>());
        user.setFavoriteList(new ArrayList<>());
        userService.createNewUser(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
