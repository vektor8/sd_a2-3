package sd.utcn.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sd.utcn.server.dto.UserDto;
import sd.utcn.server.mapper.UserMapper;
import sd.utcn.server.model.Admin;
import sd.utcn.server.model.CustomUser;
import sd.utcn.server.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get a user by id or throw an exception if user doesn't exist
     * @param id
     * @return
     * @throws Exception
     */
    public UserDto getById(Long id) throws Exception {
        var optional = userRepository.findById(id);
        if (optional.isEmpty()) {
            log.error("User with id {} not found", id);
            throw new Exception("User doesn't exist");
        }
        var user = optional.get();
        return UserMapper.ToDto(user);
    }

    /**
     * Method from the UserDetailsService interface.
     * Gets a user based on its username
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optional = userRepository.findUserByEmail(username);
        if (optional.isEmpty()) {
            log.error("user with email {} doesn't exist", username);
            throw new UsernameNotFoundException("User doesn't exist");
        }
        var user = optional.get();
        Collection<SimpleGrantedAuthority> permissions = new ArrayList<>();
        if (user instanceof Admin)
            permissions.add(new SimpleGrantedAuthority("admin"));
        else {
            permissions.add(new SimpleGrantedAuthority("customer"));
        }
        return new CustomUser(user.getEmail(), user.getPasswordHash(), permissions, user.getId());
    }
}
