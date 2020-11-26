package com.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    final String value = "default";


    public boolean getClock(String key, Long time, TimeUnit timeUnit) {
        Boolean abc = stringRedisTemplate.opsForValue().setIfAbsent(key, value, time, timeUnit);
        if(abc){
           while (true){
               Thread thread = new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           Thread.sleep(5000);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                       stringRedisTemplate.expire(key,time,TimeUnit.SECONDS);
                   }
               });
               thread.setDaemon(true);
               thread.start();

           }

        }
        return abc;
    }

    public void addTime(String key, Long time, TimeUnit timeUnit) {
        Boolean expire = stringRedisTemplate.expire(key, time, timeUnit);
    }

    public void unclock(String key) {
        stringRedisTemplate.delete(key);
    }


}
