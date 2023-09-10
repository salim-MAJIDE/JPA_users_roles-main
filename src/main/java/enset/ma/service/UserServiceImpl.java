package enset.ma.service;

import enset.ma.entities.Role;
import enset.ma.entities.User;
import enset.ma.repositories.RoleRepository;
import enset.ma.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;

    @Override
    public User addNewUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    @Override
    public Role addNewRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user=findUserByUserName(username);
        Role role=findRoleByRoleName(roleName);
        if (user.getRoles()!=null){
            user.getRoles().add(role);
            role.getUsers().add(user);
        }


    }

    @Override
    public User authenticate(String username, String password) {
        User user=userRepository.findByUserName(username);
        if (user==null) throw  new RuntimeException("Bad credentials");
        if (user.getPassword().equals(password)){
            return user;
        }
        throw  new RuntimeException("Bad credentials");
    }
}
