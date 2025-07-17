package Devmaster_Lesson4.service;

import Devmaster_Lesson4.dto.UsersDTO;
import Devmaster_Lesson4.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
    List<User>users= new ArrayList<User>();
    public  UsersService ()
    {
        users.add(new User(1L,"User1","pass1","MinhHa", LocalDate.parse("2003-10-06"),"minhha1192003@gmail.com","036772772",34,true));
        users.add(new User(3L,"User3","pass3","MinhHa2", LocalDate.parse("2003-10-08"),"minhha11092003@gmail.com","036772774",22,false));
        users.add(new User(2L,"User2","Pass2","MinhHa3",LocalDate.parse("2003-09-01"),"minnha1192003@gmail.com","0209299265",23,true));
    }
    public List<User>findAll()
    {
        return users;
    }
    public Boolean create(UsersDTO userDTO)
    {
        try
        {
            User user = new User();
            user.setId(users.stream().count()+1);
            user.setUserName(user.getUserName());
            user.setPassWord(user.getPassWord());
            user.setEmail(user.getEmail());
            user.setAge(user.getAge());
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
