package com.minipika.test;

/*
 * Creates on 2019/11/13.
 */

import com.minipika.mapper.UserMapper;

/**
 * @author lts
 */
public class MinipikaTest {

  public static void main(String[] args) {
    System.out.println(UserMapper.mapper.findUser("name1"));
  }

}
