package com.wajdigabsi.book.auth;


import com.wajdigabsi.book.email.EmailServices;
import com.wajdigabsi.book.email.EmailTemplateName;
import com.wajdigabsi.book.role.RoleRepository;
import com.wajdigabsi.book.security.JwtService;
import com.wajdigabsi.book.user.Token;
import com.wajdigabsi.book.user.TokenRepository;
import com.wajdigabsi.book.user.User;
import com.wajdigabsi.book.user.UserRepository;
import jakarta.mail.MessagingException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailServices emailServices;
    private final AuthenticationManager authenticationManager ;
    private final JwtService jwtService;

    @Value("${app.mailing.frontend.activation-url}")
    private String activationUrl;


    public void register(RegistrationRequest request) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("User role not found"));
            var user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .accountLocked(false)
                    .enabled(false) // par defaut le compte doit etre disables.
                    .roles(List.of(userRole))
                    .build();
            userRepository.save(user);
            sendValidationEmail(user);

    }
    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActiationToken(user);
        emailServices.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }
    private String generateAndSaveActiationToken(User user) {
        // generate token
        String generatedToken = generationActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
                tokenRepository.save(token);
                return generatedToken ;
    }
    private String generationActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBulder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
int randomIndex = secureRandom.nextInt(characters.length());  // 0..9
        codeBulder.append(characters.charAt(randomIndex));}
        return codeBulder.toString() ;
    }

    public AuthenticationResponse authenticate(@Valid AuthenticationReques request) {
        var  auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String,Object>();
        var user = ((User)auth.getPrincipal());
        claims.put("fullName", user.fullName());
        var jwToken = jwtService.generateToken(claims,user);
        return AuthenticationResponse.builder()
                .token(jwToken).build();
    }


//    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been send to the same email address");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

//    @Transactional                                                   version amélioré chatgpt
//    public void activateAccount(String token) {
//        Token savedToken = tokenRepository.findByToken(token)
//                .orElseThrow(() -> new InvalidTokenException("Invalid or expired token"));
//
//        // Vérifie si déjà validé
//        if (savedToken.getValidatedAt() != null) {
//            throw new InvalidTokenException("Account already activated");
//        }
//
//        // Vérifie expiration
//        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
//            try {
//                sendValidationEmail(savedToken.getUser());
//            } catch (MessagingException e) {
//                // Log l'erreur, mais ne bloque pas
//                log.error("Failed to resend activation email", e);
//            }
//            throw new TokenExpiredException("Activation link expired. A new one has been sent.");
//        }
//
//        // Active l'utilisateur
//        User user = savedToken.getUser();
//        user.setEnabled(true);
//        userRepository.save(user);
//
//        // Marque le token comme utilisé
//        savedToken.setValidatedAt(LocalDateTime.now());
//        tokenRepository.save(savedToken);
//    }  v
}
