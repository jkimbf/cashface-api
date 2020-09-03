package swmaestro.revivers.cashface.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import swmaestro.revivers.cashface.domain.User;
import swmaestro.revivers.cashface.domain.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User input) {
        String email = input.getEmail();
        Optional<User> existed = userRepository.findByEmail(email);

        if (existed.isPresent()) {
            throw new EmailExistedException(email);
        }

        String encodedPassword = passwordEncoder.encode(input.getPassword());

        User user = User.builder()
                .userType(input.getUserType())
                .name(input.getName())
                .email(input.getEmail())
                .password(encodedPassword)
                .gender(input.getGender())
                .age(input.getAge())
                .nationality(input.getNationality())
                .totalPoints(input.getTotalPoints())
                .createdDate(input.getCreatedDate())
                .build();

        return userRepository.save(user);
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotExistedException(email));
    }
}
