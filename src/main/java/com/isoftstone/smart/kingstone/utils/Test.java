package com.isoftstone.smart.kingstone.utils;

import java.io.IOException;
import java.sql.Timestamp;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.isoftstone.smart.kingstone.entity.Customer;

public class Test {

  public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {

    Customer customer = new Customer();
    customer.setId("123");
    customer.setAge(new Timestamp(System.currentTimeMillis()));
    ObjectMapper om = new ObjectMapper();
    System.out.println(om.writeValueAsString(customer));
  }
}
