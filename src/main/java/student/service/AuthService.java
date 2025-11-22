package student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import student.dto.AuthResponse;
import student.dto.SignInRequest;
import student.dto.SignUpRequest;
import student.entity.User;
import student.exception.UserAlreadyExistsException;
import student.exception.InvalidCredentialsException;
import student.repo.UserRepo;
import student.util.JwtUtil;

/**
 * Service for authentication operations (signup and signin).
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * Sign up a new user.
     * Creates a new user account with encrypted password.
     */
    public AuthResponse signUp(SignUpRequest request) {
        // Check if user already exists
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        // Create new user
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .build();

        user = userRepo.save(user);

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .message("User registered successfully")
                .email(user.getEmail())
                .fullName(user.getFullName())
                .build();
    }

    /**
     * Sign in an existing user.
     * Validates credentials and returns JWT token.
     */
    public AuthResponse signIn(SignInRequest request) {
        // Find user by email
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .message("Login successful")
                .email(user.getEmail())
                .fullName(user.getFullName())
                .build();
    }
}

