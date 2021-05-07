package com.dms.dto;

import java.sql.Timestamp;

public class DirectoryDto {
    private Long id;
    private UserDto author;
    private String name;
    private String status;
    private Timestamp creation_DT;
    private boolean freeAccess;

}
