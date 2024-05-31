package com.exmple.dinuk.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity //its like database table
@NoArgsConstructor
@AllArgsConstructor
@Data //getters and setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private Timestamp date;
    private String title;
    private String content;
    private String author;



    @PrePersist
    protected void onCreate() {
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Asia/Colombo"));
        date = Timestamp.from(zdt.toInstant());
    }

}
