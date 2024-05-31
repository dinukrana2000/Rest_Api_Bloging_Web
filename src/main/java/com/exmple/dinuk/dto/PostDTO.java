package com.exmple.dinuk.dto;

import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDTO {

    @NotBlank(message = "{post.username.mandatory}")
    private String username;

    private ZonedDateTime date;

    @NotBlank(message = "{post.title.mandatory}")
    @Size(min = 1, max = 100, message = "{post.title.size}")
    private String title;

    @NotBlank(message = "{post.content.mandatory}")
    @Size(min = 1, max = 200, message = "{post.content.size}")
    private String content;

    @NotBlank(message = "{post.author.mandatory}")
    @Size(min = 1, max = 50, message = "{post.author.size}")
    private String author;

    private String message;

    @PrePersist
    protected void onCreate() {
        date = ZonedDateTime.now(ZoneId.of("Asia/Colombo"));
    }




}
