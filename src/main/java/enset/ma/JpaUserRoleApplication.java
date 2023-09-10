package enset.ma;

import enset.ma.entities.Role;
import enset.ma.entities.User;
import enset.ma.repositories.UserRepository;
import enset.ma.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class JpaUserRoleApplication {

    public static void main(String[] args) {

        SpringApplication.run(JpaUserRoleApplication.class, args);
    }
    @Bean
    CommandLineRunner start(UserService userService){
        return args -> {
            User u=new User();
            u.setUserName("user1");
            u.setPassword("123456");
            userService.addNewUser(u);
            User u2=new User();
            u2.setUserName("admin");
            u2.setPassword("12345lk");
            userService.addNewUser(u2);

            Stream.of("STUDENT","USER","ADMIN").forEach(r->{
                Role role=new Role();
                role.setRoleName(r);
                role.setDesc("blablabka");
                userService.addNewRole(role);
            });
            userService.addRoleToUser("user1","STUDENT");
            userService.addRoleToUser("user1","USER");
            userService.addRoleToUser("admin","USER");
            userService.addRoleToUser("admin","ADMIN");

            try {
                User user=userService.authenticate("user1","123456");
                System.out.println(user.toString());
                user.getRoles().forEach(r->{
                    System.out.println("Roles ==> "+r);
                });

            }catch(Exception e){
                e.printStackTrace();
            }
        };
    }

}
