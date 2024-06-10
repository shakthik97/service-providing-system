package edu.icet.ServiceBookingSystem.controller;

import edu.icet.ServiceBookingSystem.dto.AuthenticationRequest;
import edu.icet.ServiceBookingSystem.dto.SignupRequestDTO;
import edu.icet.ServiceBookingSystem.dto.UserDto;
import edu.icet.ServiceBookingSystem.entity.User;
import edu.icet.ServiceBookingSystem.reposiory.UserRepository;
import edu.icet.ServiceBookingSystem.services.authentications.AuthService;
import edu.icet.ServiceBookingSystem.services.jwt.UserDetailsServiceImpl;
import edu.icet.ServiceBookingSystem.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController

public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;
    public static final String TOKEN_PREFIX = "Bearer";

    public static final String HEADER_STRING = "Authorization";

    @PostMapping("/client/sign-up")
    public ResponseEntity<?> signupClient(@RequestBody SignupRequestDTO signupRequestDTO){
        if(authService.presentByEmail(signupRequestDTO.getEmail())){
            return new ResponseEntity<>("Client already exists with this Email!", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto createdUser = authService.signupClient(signupRequestDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @PostMapping("/company/sign-up")
    public ResponseEntity<?> signupCompany(@RequestBody SignupRequestDTO signupRequestDTO){
        if(authService.presentByEmail(signupRequestDTO.getEmail())){
            return new ResponseEntity<>("Company already exists with this Email!", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto createdUser = authService.signupClient(signupRequestDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                          HttpServletResponse response) throws JSONException, IOException {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),authenticationRequest.getPassword()
            ));

        } catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password", e);
        }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

    final String jwt = jwtUtil.generateToken(userDetails.getUsername());
    User user = userRepository.findFirstByEmail(authenticationRequest.getUsername());

    response.getWriter().write(new JSONObject()
            .put("userId", user.getId())
            .put("role", user.getRole())
            .toString()
    );

    response.addHeader("Access-Control-Expose-Headers", "Authorization");
    response.addHeader("Access-Control_allow-Headers", "Authorization," +
            "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-Header");

    response.addHeader(HEADER_STRING, TOKEN_PREFIX);

    }
}
