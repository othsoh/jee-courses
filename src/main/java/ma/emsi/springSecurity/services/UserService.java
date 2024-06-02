package ma.emsi.springSecurity.services;

import ma.emsi.springSecurity.entities.Role;
import ma.emsi.springSecurity.entities.User;
import ma.emsi.springSecurity.repositories.RoleRepository;
import ma.emsi.springSecurity.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findUserByEmail(email);
    }
    public User getUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public User createUser(User user, String roleName){
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(roleName);
        if (role != null) {
            roles.add(role);
        } else {
            Role newRole = new Role();
            newRole.setName(roleName);
            roles.add(roleRepository.save(newRole));
        }
        user.setRoles(roles);
        User newUser = userRepository.save(user);
        userRepository.flush();
        return newUser;
    }
}