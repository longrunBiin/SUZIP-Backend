package Fo.Suzip.service;

import Fo.Suzip.domain.User;
import Fo.Suzip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(Long userId) {
        return null;
//                userRepository.findById(userId);
    }
}

