package com.gary.finalproj.controllers;

import com.gary.finalproj.data.Note;
import com.gary.finalproj.tools.JDBCConnector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class NoteBooks {

    private static GsonBuilder builder;
    private static Gson gson;

    static{
        builder = new GsonBuilder();
        gson = builder.create();
    }

    @CrossOrigin
    @RequestMapping(value = "/insertNote", method = RequestMethod.POST)
    public String insertNote(@RequestParam int user_id, @RequestParam String note) {
        JdbcTemplate jdbcTemplate = JDBCConnector.getJdbcTemplate();

        Map<Object, Object> map = new HashMap<>();

        int affectedRows = 0;

        map.put("httpStatus", HttpStatus.OK);

        try{
            affectedRows = jdbcTemplate.update(
                    "INSERT INTO notebooks (user_id, note) VALUES (?, ?)", user_id, note);
        }catch (Exception ex){
            System.out.println("MySQL Server is down.");
            System.err.println("MySQL Server is down.");
            map.put("httpStatus", HttpStatus.BAD_REQUEST);
        }

        map.put("affectedRows", affectedRows);

        return gson.toJson(map);
    }

    @CrossOrigin
    @RequestMapping(value = "/queryNote", method = RequestMethod.GET)
    public String queryNote(@RequestParam int user_id, @RequestParam String query) {

        JdbcTemplate jdbcTemplate = JDBCConnector.getJdbcTemplate();
        Map<Object, Object> map = new HashMap<>();

        List<Note> notes = new ArrayList<>();;
        Note note;

        String queryStr = "SELECT * FROM notebooks WHERE user_id = ? AND note LIKE ?";
        SqlRowSet sqlRowSet;

        try{
            sqlRowSet = jdbcTemplate.queryForRowSet(queryStr, user_id, "%" + query + "%");
        }catch(Exception ex){
            map.put("notes", null);
            map.put("httpStatus", HttpStatus.BAD_REQUEST);
            return gson.toJson(map);
        }

        while (sqlRowSet.next()) {
            note = new Note();
            note.setId(sqlRowSet.getInt("id"));
            note.setUser_id(sqlRowSet.getInt("user_id"));
            note.setNote(sqlRowSet.getString("note"));
            note.setTime(sqlRowSet.getString("created_at"));

            notes.add(note);
        }

        map.put("notes", notes);
        map.put("httpStatus", HttpStatus.OK);

        return gson.toJson(map);
    }

}// public class NoteBooks
