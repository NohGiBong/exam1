package com.busanit.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BlogDto {
    private Long bno;

    private String name;

    private String title;

    private String content;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;
}
