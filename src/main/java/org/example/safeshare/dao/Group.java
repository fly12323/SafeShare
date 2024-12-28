package org.example.safeshare.dao;

import lombok.Data;

@Data
public class Group {
    private Long id;
    private String name;
    private String description;
    private Long adminId; // 用户ID
}
