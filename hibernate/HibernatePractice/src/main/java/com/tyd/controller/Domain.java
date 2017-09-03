package com.tyd.controller;

import com.tyd.entity.UserEntity;
import com.tyd.pojo.UserPoJo;
import com.tyd.service.UserService;
import com.tyd.service.impl.UserServiceImpl;
import com.tyd.util.BeanMapper;
import org.junit.Test;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Abbot
 * Date: 2017-09-03
 * Time: 19:30
 * Description:
 */
public class Domain
{

    UserService userService = new UserServiceImpl();
    BeanMapper beanMapper = new BeanMapper();
    /**
     * 测试添加
     */
    @Test
    public void testAddObj()
    {
        UserPoJo userPoJo = new UserPoJo("x","y","z","a","man");
        UserEntity userEntity = new UserEntity();
        beanMapper.copy(userPoJo,userEntity);
        userService.addObj(userEntity);
    }

    /**
     * 测试删除
     */
    @Test
    public void testDeleteObj()
    {
        UserService userService = new UserServiceImpl();
        userService.deleteObj(1);
    }

    @Test
    public void  testUpdate()
    {
        UserPoJo userPoJo = new UserPoJo(3,"a","b","c","d","e");
        UserEntity userEntity = new UserEntity();
        beanMapper.copy(userPoJo,userEntity);
        userService.updateObj(userEntity);
    }

    @Test
    public void testFindAll()
    {
        List<UserPoJo> all = userService.findAll();
        System.out.println(all);
    }

    @Test
    public void testFindByName()
    {
        List<UserPoJo> b = userService.findByName("a");
        System.out.println(b);
    }

    @Test
    public void testFindByAddress()
    {
        List<UserPoJo> z = userService.findByAddress("z");
        System.out.println(z);
    }

    @Test
    public void testFindByAge()
    {
        List<UserPoJo> byAge = userService.findByAge("3");
        System.out.println(byAge);
    }

    @Test
    public void testFindById()
    {
        UserPoJo byId = userService.findById(2);

        System.out.println(byId);

    }

}
