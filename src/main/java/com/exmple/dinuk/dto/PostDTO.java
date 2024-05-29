package com.exmple.dinuk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDTO {
    @NotBlank(message = "Username cannot be blank")
    private String username;


    private Date date=new Date();

    @NotBlank(message = "Title cannot be blank")
    @Size(min=1 ,max=100, message = "Title must be between 1 and 100 characters")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    @Size(min=1 ,max=200, message = "content must be between 1 and 200 characters")
    private String content;

    @NotBlank(message = "Author cannot be blank")
    @Size(min=1 ,max=50, message = "author must be between 1 and 50 characters")
    private String author;

    private String message;

    public Date getDate() {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(this.date.toInstant(), ZoneId.systemDefault());
        ZonedDateTime adjustedTime = zonedDateTime.plusHours(5).plusMinutes(30);
        return Date.from(adjustedTime.toInstant());
    }
}