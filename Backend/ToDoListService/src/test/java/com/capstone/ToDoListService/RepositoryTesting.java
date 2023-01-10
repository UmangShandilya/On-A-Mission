package com.capstone.ToDoListService;


import com.capstone.ToDoListService.model.Priority;
import com.capstone.ToDoListService.model.Tasks;
import com.capstone.ToDoListService.model.User;
import com.capstone.ToDoListService.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class RepositoryTesting {
    @Autowired
    UserRepository userTaskRepository;

    User user,user1;

    Tasks tasks,tasks1;
    @BeforeEach
    public void set(){
        tasks=new Tasks(10,"project","2022-12-06","Todo Tracker","SE", Priority.HIGH,false);
        tasks1=new Tasks(11,"project1","2022-11-30","Todo Tracker1","SE",Priority.HIGH,true);

        user=new User("hi@gmail.com","hai","hai@123",List.of(tasks));
        user1=new User("hi2","hai2","hi1233",List.of(tasks1));
    }
    @AfterEach
    public void delete(){
        tasks=null;
        user=null;
    }
    @Test
    public void saveUserReturnTrue(){

        userTaskRepository.insert(user);
        User user1=userTaskRepository.findById(user.getEmail()).get();
        assertEquals(user1,user);
    }

    @Test
    public void deleteUserReturnTrue(){
        userTaskRepository.save(user);
        userTaskRepository.delete(user);
        assertEquals(Optional.empty(),userTaskRepository.findById(user.getEmail()));

    }
    @Test
    public void deleteUserReturnFalse(){
        userTaskRepository.save(user);
        userTaskRepository.delete(user);
        assertNotEquals(user,userTaskRepository.findById(user.getEmail()));

    }

    @Test
    public void getAllUsersReturnFalse(){

        userTaskRepository.save(user);
        //userTaskRepository.save(user2);
        assertNotEquals(user,userTaskRepository.findAll());
    }

}
