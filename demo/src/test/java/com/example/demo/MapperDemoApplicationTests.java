//package com.example.demo;
//
//import com.example.demo.mapper.UserMapper;
//import com.example.demo.pojo.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//
//@SpringBootTest
//class MapperDemoApplicationTests {
//@Autowired
//private UserMapper userMapper;
//    @Test
//    public void testSelectlist(){
//        //selectList()根据MP内置的条件构造器查询一个list集合，null表示没有条件，即查询所有
//        List<User> users = userMapper.selectList(null);
//        users.forEach(System.out::println);
//    }
//    @Test
//    public void testinsert() {
//        //插入数据
//        User user = new User();
//        user.setAge(12);
//        user.setEmail("65454@12");
//        user.setName("历史");
//        int insert = userMapper.insert(user);
//        System.out.println(user);
//    }
//
//    @Test
//    public void testDeleteBatchIds(){
////通过多个id批量删除
////DELETE FROM user WHERE id IN ( ? , ? , ? )
//        List<Long> idList = Arrays.asList(1L, 2L, 3L);
//        int result = userMapper.deleteBatchIds(idList);
//        System.out.println("受影响行数："+result);
//    }
//    @Test
//    public void testDeleteById() {
//        //通过id删除用户信息
//        //DELETE FROM user WHERE id=?
//        int i = userMapper.deleteById(1720427825151434754L);
//        System.out.println("受影响行数："+i);
//    }
//    @Test
//    public void testDeleteByMap() {
////根据map集合中所设置的条件删除记录
////DELETE FROM user WHERE name = ? AND age = ?
//        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
//        objectObjectHashMap.put("name","Billie");
//        objectObjectHashMap.put("age",24);
//        userMapper.deleteByMap(objectObjectHashMap);
//    }
//    @Test
//    public void testUpdateById() {
//        User useres = new User();
//         useres.setUid(4L);
//         useres.setName("admin");
//         useres.setAge(22);
////根据id进行更新
////UPDATE user SET name=?, age=? WHERE id=?
//        int i = userMapper.updateById(useres);
//        System.out.println("受影响行数："+i);
//    }
//    @Test
//    public void testslectMapById() {
//
////        userMapper.selectMapById(1L);
//
//    }
//
//    }
