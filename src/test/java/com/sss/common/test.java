package com.sss.common;

import com.sss.common.jwt.MyJwtUtil;

import java.util.Map;

/**
 * @author: wyy-sss
 * @date: 2019-09-23 10:36
 **/
public class test {
    public static void main(String[] args) {
        String aaa = MyJwtUtil.createToken("aaa", "1");
        Map<String, Object> map = MyJwtUtil.verifyToken(aaa);
        for (String s : map.keySet()) {
            System.out.println(map.get(s));
        }
    }
}
