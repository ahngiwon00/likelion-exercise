package com;

import com.dao.AWSUserDaoImpl;
import com.dao.AwsConnectionMaker;
import com.dao.UserDao;
import com.dao.UserDaoFactory;
import com.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class) //테스트코드에서 스프링을 쓰기 위해서 사용
@ContextConfiguration(classes = UserDaoFactory.class)//테스트코드에서 테스트용 DI 정보를 어디서 가져와야 하는지 지정할때 사용하는 어노테이션
class UserDaoTest {

    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp(){
        this.userDao = context.getBean("awsUserDao", UserDao.class);
        this.user1 = new User("11", "Ahn", "1234");
        this.user2 = new User("12", "So", "123");
        this.user3 = new User("13", "Min", "12");
        System.out.println("before each");
    }
    @Autowired
    ApplicationContext context;
    /*의존관계 주입(DI)을 할 때 사용하는 어노테이션(Annotation)이며, IoC컨테이너에 존재하는 빈(Bean)을 찾아 주입하는 역할을 한다. */
    @Test
    void addAndSelect() throws Exception {
        //UserDao userDao = new UserDao(new AwsConnectionMaker());
        //UserDao userDao = new UserDaoFactory().awsUserDao();
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1,userDao.getCount());
        User user = userDao.get(user1.getId());

        assertEquals(user1.getName(), user.getName());
        assertEquals(user1.getPassword(), user.getPassword());

    }
    @Test
    void count() throws SQLException, ClassNotFoundException {


        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1,userDao.getCount());
        userDao.add(user2);
        assertEquals(2,userDao.getCount());
        userDao.add(user3);
        assertEquals(3,userDao.getCount());

    }

    @Test
    void get(){
        assertThrows(EmptyResultDataAccessException.class, () -> userDao.get("12"));
    }


}