package com.itwasjoke.place.security;

import com.itwasjoke.place.DTO.UserRequestDTO;
import com.itwasjoke.place.entity.Couple;
import com.itwasjoke.place.entity.User;
import com.itwasjoke.place.security.jwt.JwtAuthenticationResponse;
import com.itwasjoke.place.security.jwt.JwtService;
import com.itwasjoke.place.service.CoupleService;
import com.itwasjoke.place.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final CoupleService coupleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserService userService,
            CoupleService coupleService,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.coupleService = coupleService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public JwtAuthenticationResponse signUp(UserRequestDTO userRequestDTO){
        String password = passwordEncoder.encode(userRequestDTO.password());
        User user = userService.createUser(userRequestDTO, password);
        var jwt = "Bearer " + jwtService.generateToken(user);
        Couple couple;
        String hash = userRequestDTO.invitationCode();
        if (hash != null){
            couple = coupleService.confirmCouple(hash, user);
        } else {
            couple = coupleService.createCouple(user);
        }
        hash = couple.getHash();
        return new JwtAuthenticationResponse(jwt, hash, coupleService.coupleConfirmed(hash));
    }

    public JwtAuthenticationResponse confirmCouple(String username, String hash){
        User user = userService.getUser(username);
        Couple couple = coupleService.confirmCouple(hash, user);
        var jwt = "Bearer " + jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt, couple.getHash(), true);
    }

    public JwtAuthenticationResponse signIn(String login, String password){
        User user = userService.getUser(login);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                login,
                password
        ));
        String jwt = "Bearer "+jwtService.generateToken(user);
        Couple couple1 = user.getCouple1();
        Couple couple2 = user.getCouple2();
        String hash = null;
        if (couple1 != null){
            hash = couple1.getHash();
        }
        if (couple2 != null){
            hash = couple2.getHash();
        }
        return new JwtAuthenticationResponse(jwt, hash, true);
    }

}
