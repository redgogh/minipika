package com.minipika.test;

/*
 * Creates on 2019/11/13.
 */

import com.minipika.mapper.user_mapper;

/**
 * @author lts
 * @email jiakesiws@gmail.com
 */
public class minipika_test {

  public static void main(String[] args) {
    System.out.println(user_mapper.mapper.findUser("name1").getUsername());
  }

}
