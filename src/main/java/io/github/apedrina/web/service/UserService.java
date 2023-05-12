package io.github.apedrina.web.service;

import io.github.apedrina.web.controller.payload.request.SignupRequest;
import io.github.apedrina.web.model.ERole;
import io.github.apedrina.web.model.Role;
import io.github.apedrina.web.model.User;
import io.github.apedrina.web.repository.RoleRepository;
import io.github.apedrina.web.repository.UserRepository;
import io.github.apedrina.web.vo.RoleVO;
import io.github.apedrina.web.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleService roleService;

    public void addUser(UserVO userVO) {
        var roles = userVO.getRoles().stream().map(role -> {
            return Role.builder()
                    .name(ERole.valueOf(role.getName().toString())).build();
        }).collect(Collectors.toSet());

        var userEntity = User.builder()
                .username(userVO.getUsername())
                .email(userVO.getEmail())
                .roles(roles)
                .build();

        userRepository.save(userEntity);


    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);

    }

    public UserVO register(SignupRequest signUpRequest) {
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail()).password(encoder.encode(signUpRequest.getPassword()))
                .build();

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            roleService.figureOutRoles(strRoles, roles);
        }

        user.setRoles(roles);
        User savedUser = userRepository.save(user);

        var rolesVO = savedUser.getRoles().stream().map(role -> {
            return RoleVO.builder()
                    .name(ERole.valueOf(role.getName().toString())).build();
        }).collect(Collectors.toSet());

        return UserVO.builder()
                .password(savedUser.getPassword())
                .username(savedUser.getUsername())
                .roles(rolesVO)
                .id(savedUser.getId())
                .build();

    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);

    }

    public List<UserVO> getAll() {
        return userRepository.findAll().stream().map(user -> {
            return UserVO.builder()
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .build();
        }).collect(Collectors.toList());

    }
}
