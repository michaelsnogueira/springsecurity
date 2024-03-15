package br.com.nogueira.springsecurity.config;

import br.com.nogueira.springsecurity.entities.Role;
import br.com.nogueira.springsecurity.entities.User;
import br.com.nogueira.springsecurity.repository.RoleRepository;
import br.com.nogueira.springsecurity.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private BCryptPasswordEncoder passowrdEncoder;

    public AdminUserConfig(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passowrdEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passowrdEncoder = passowrdEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());
        var userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("User admin already exists");
                },
                () -> {
                    User admin = new User();
                    admin.setUsername("admin");
                    admin.setPassword(passowrdEncoder.encode("123"));
                    admin.setRoles(Set.of(roleAdmin));
                    userRepository.save(admin);
                }
        );
    }
}
