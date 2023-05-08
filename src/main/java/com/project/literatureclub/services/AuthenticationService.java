package com.project.literatureclub.services;


import com.project.literatureclub.dtos.AuthenticationRequest;
import com.project.literatureclub.dtos.AuthenticationResponse;
import com.project.literatureclub.dtos.RegisterUserRequest;
import com.project.literatureclub.entities.User;
import com.project.literatureclub.exceptions.IncorrectPasswordException;
import com.project.literatureclub.exceptions.InvalidPasswordException;
import com.project.literatureclub.exceptions.UserExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService authorService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterUserRequest request) throws IncorrectPasswordException {

        String email = request.getEmail();
        Optional<User> author1 = authorService.findByEmail(email);
        if (author1.isPresent()) {
            throw new UserExistsException();
        }
//        if(!patternMatches(email,"^(.+)@(\\S+) $.")){
//            throw new RuntimeException();
//        }
        String password = request.getPassword();
        if (patternMatches(password, "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")) {
            password = passwordEncoder.encode(password);
        } else {
            throw new InvalidPasswordException("The password is invalid");
        }
        User author = new User();
        author.setFirstName(request.getFirstname());
        author.setLastName(request.getLastname());
        author.setEmail(email);
        author.setPassword(password);
        author.setMainGenre(request.getMainGenre());
        author.setFollowerCount(0L);
        author.setDateOfBirth(request.getDateOfBirth());
        author.setRole(request.getRole());
        author.setDescription(request.getDescription());
        authorService.save(author);
        //var jwtToken = jwtService.generateToken(request.getRole().toString(),author);
        var jwtToken = jwtService.generateToken(request.getRole().toString(), author);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = authorService.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user.getRole().toString(), user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public static boolean patternMatches(String password, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(password)
                .matches();
    }

}
