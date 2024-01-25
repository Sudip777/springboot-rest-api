package com.sudip.restwebservices.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;


@Component
public class UserDaoService {
    // JPA/Hibernate => Database
    //UserDaoService -> Static List

    private static  List <User> users = new ArrayList<>();
    private static int usersCount = 0;

    static {
        users.add(new User(++usersCount, "Sud-ip",LocalDate.now().minusYears(23)));
        users.add(new User(++usersCount, "Ramesh",LocalDate.now().minusYears(30)));

        users.add(new User(++usersCount, "Sujan",LocalDate.now().minusYears(26)));

    }


    // Display all users
     public List<User> findAll(){
        return users;
     }

     public User save(User user){
        user.setId(usersCount++);
        users.add(user);
        return user;
    }

    // Display single user based on id
     public User findOne(int id){
         Predicate<? super User> predicate = user -> Objects.equals(user.getId(), id);
         return users.stream().filter(predicate).findFirst().orElse(null);

     }

    // Deleting single user based on id
    public User deleteById(int id){
        Predicate<? super User> predicate = user -> Objects.equals(user.getId(), id);
        users.removeIf(predicate);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

}
