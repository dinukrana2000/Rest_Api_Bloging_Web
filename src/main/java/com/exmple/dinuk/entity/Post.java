package com.exmple.dinuk.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Date date;
    private String title;
    private String content;
    private String author;

    public Date getDate() {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(this.date.toInstant(), ZoneId.systemDefault());
        ZonedDateTime adjustedTime = zonedDateTime.minusHours(5).minusMinutes(30);
        return Date.from(adjustedTime.toInstant());
    }

}
