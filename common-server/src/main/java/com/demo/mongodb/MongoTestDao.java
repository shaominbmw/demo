package com.demo.mongodb;//package com.demo.mongodbDemo;

import com.alibaba.fastjson.JSON;
import com.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MongoTestDao {


    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     */
    public void saveTest(MongoTest test) {
//        mongoTemplate.save(test);
//        User user1 = new User("小明",12);
//        User user2 = new User("小红",15);
//        User user3 = new User("小黑",12);
//        ArrayList<User> list = new ArrayList<>();
//        list.add(user1);
//        list.add(user2);
//        list.add(user3);
//        for (User user : list) {
//            mongoTemplate.insert(user);
//        }
//        mongoTemplate.save(user1,"test");
//        mongoTemplate.save("{\n" +
//                "        \"name\": \"小明\",\n" +
//                "        \"age\": 139\n" +
//                "    }","test");
    }

    /**
     * 根据用户名查询对象
     * @return
     */
    public  List<User> findTestByName(String name) {

        Query query=new Query(Criteria.where("name").regex("明"));
        List<String> mgt= mongoTemplate.find(query,String.class,"test");
        for (String map : mgt) {
            Map map1 = JSON.parseObject(map, Map.class);
            System.out.println("map1 = " + map1);
        }

//        List<User> mgt =  mongoTemplate.findAll(User.class,"user");

        return null;
    }

    /**
     * 更新对象
     */
    public void updateTest(MongoTest test) {
        Query query=new Query(Criteria.where("id").is(test.getId()));
        Update update= new Update().set("age", test.getAge()).set("name", test.getName());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,MongoTest.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,TestEntity.class);
    }

    /**
     * 删除对象
     * @param id
     */
    public void deleteTestById(Integer id) {
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,MongoTest.class);
    }

    public static void main(String[] args) {

    }

    public void save(String s1) {
        mongoTemplate.save(s1,"test");
    }
}