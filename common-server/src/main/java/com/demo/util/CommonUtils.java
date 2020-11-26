package com.demo.util;

import java.util.*;

/**
 * @ClassName UUIDUtils
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-09 13:21
 **/
public class CommonUtils {
    /**
     * 获取uuid
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取x内的随机数不含0
     *
     * @param x
     * @return
     */
    public static Integer getRandom(int x) {
        Random random = new Random();
        return random.nextInt(x) + 1;
    }

    public static void main(String[] args) {

    }
}
