package com.gary.finalproj.controllers;

import com.gary.finalproj.data.UserInfo;
import com.gary.finalproj.tools.AESUtils;
import com.gary.finalproj.tools.JDBCConnector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class JDBCController {
    private final static String KEYFILEPATH = "./keyFile.key";

    private static GsonBuilder builder;
    private static Gson gson;

    static{
        builder = new GsonBuilder();
        gson = builder.create();
    }

    @CrossOrigin
    @RequestMapping(value = "/helloworld", method = RequestMethod.GET)
    public String printCryptTest() {
        AESUtils aesUtils = new AESUtils();

        String encryptedStr = aesUtils.encrypt("Hello World!", KEYFILEPATH);
        return ("Decrypt = " + aesUtils.decrypt(encryptedStr, KEYFILEPATH));
    }

    @CrossOrigin
    @RequestMapping(value = "/printAllUsers", method = RequestMethod.GET)
    public String printAllUsers() {
        JdbcTemplate jdbcTemplate = JDBCConnector.getJdbcTemplate();
        Map<Object, Object> map = new HashMap<>();

        List<UserInfo> users = new ArrayList<>();;
        UserInfo userInfo;

        String queryStr = "SELECT * from user_info;";
        SqlRowSet sqlRowSet;

        try{
            sqlRowSet = jdbcTemplate.queryForRowSet(queryStr);
        }catch(Exception ex){
            map.put("users", null);
            map.put("httpStatus", HttpStatus.BAD_REQUEST);
            return gson.toJson(map);
        }

        while (sqlRowSet.next()) {
            userInfo = new UserInfo();
            userInfo.setUser_id(sqlRowSet.getInt("user_id"));
            userInfo.setFirstName(sqlRowSet.getString("first_name"));
            userInfo.setLastName(sqlRowSet.getString("last_name"));
            userInfo.setAddress(sqlRowSet.getString("addr"));
            userInfo.setPhone(sqlRowSet.getString("phone"));
            userInfo.setEmail(sqlRowSet.getString("email"));
            userInfo.setTime(sqlRowSet.getString("created_at"));

            users.add(userInfo);
        }

        map.put("users", users);
        map.put("httpStatus", HttpStatus.OK);

        return gson.toJson(map);
    }

    @CrossOrigin
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@RequestBody UserInfo userInfo) {
        JdbcTemplate jdbcTemplate = JDBCConnector.getJdbcTemplate();

        Map<Object, Object> map = new HashMap<>();

        int affectedRows = 0;

        map.put("httpStatus", HttpStatus.OK);

        try{
            affectedRows = jdbcTemplate.update(
                    "INSERT INTO user_info (first_name, last_name, addr, email) VALUES (?, ?, ?, ?)",
                            userInfo.getFirstName(),
                            userInfo.getLastName(),
                            userInfo.getAddress(),
                            userInfo.getEmail() );
        }catch (Exception ex){
            System.out.println("MySQL Server is down.");
            System.err.println("MySQL Server is down.");
            map.put("httpStatus", HttpStatus.BAD_REQUEST);
        }

        map.put("affectedRows", affectedRows);

        return gson.toJson(map);
    }

    @CrossOrigin
    @RequestMapping(value = "/deleteUser/{user_id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long user_id) {
        JdbcTemplate jdbcTemplate = JDBCConnector.getJdbcTemplate();

        Map<Object, Object> map = new HashMap<>();
        int affectedRow = 0;

        map.put("affectedRows", affectedRow);
        map.put("httpStatus", HttpStatus.OK);

        try{
            affectedRow = jdbcTemplate.update("DELETE from user_info where user_id = ?", user_id);
            map.put("affectedRows", affectedRow);
        }catch(Exception ex){
            System.out.println("MySQL Server is down.");
            System.err.println("MySQL Server is down.");
            map.put("httpStatus", HttpStatus.BAD_REQUEST);
        }

        return gson.toJson(map);
    }

} // public class JDBCController()
