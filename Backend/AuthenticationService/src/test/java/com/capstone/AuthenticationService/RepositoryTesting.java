package com.capstone.AuthenticationService;

import com.capstone.AuthenticationService.model.User;
import com.capstone.AuthenticationService.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTesting {
    @Autowired
    private UserRepository userRepository;
    private User user,user2;
    ArrayList<User> userList=new ArrayList<>();

    @BeforeEach
    public void setup(){

        user=new User("bbc@gmail.com","bbc","bbc@123");
        user2=new User("ppq@gmail.com","ppq","ppq@123");

        userList.add(user);
        userList.add(user2);

    }

    @AfterEach
    public void tearDown(){

        user=null;

        user2=null;
        //userRepository.deleteAll();
    }
    @Test
    public void saveUserReturnTrue(){
        userRepository.save(user);
        assertEquals(user.getEmail(),userRepository.findById(user.getEmail()).get().getEmail());
    }
    @Test
    public void saveUserReturnFalse(){
        userRepository.save(user);
        assertNotEquals(user2.getEmail(),userRepository.findById(user.getEmail()).get().getEmail());

    }
    @Test
    public void findUserByEmailAndPasswordTrue(){
        userRepository.save(user);
        //userRepository.save(user2);
        assertEquals(user,userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()));
    }
    @Test
    public void getAllUsersReturnTrue(){
        userRepository.save(user);
        assertEquals(4,userRepository.findAll().size());
    }
    @Test
    public void deleteUserReturnTrue(){
        userRepository.save(user);
        userRepository.delete(user);
        assertEquals(Optional.empty(),userRepository.findById(user.getEmail()));

    }
}
