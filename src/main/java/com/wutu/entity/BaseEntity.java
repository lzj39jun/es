package com.wutu.entity;

import javax.persistence.*;

@MappedSuperclass
public class BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = LONG+"'ID'")
    private Long id;

    public Long getId() {
        return id;
    }

    public static final String STRING = "varchar(255) comment ";
    public static final String STRING_1000 = "varchar(1000) comment ";
    public static final String STRING_2000 = "varchar(2000) comment ";
    public static final String STRING_3000 = "varchar(3000) comment ";
    public static final String STRING_4000 = "varchar(4000) comment ";
    public static final String STRING_5000 = "varchar(5000) comment ";
    public static final String STRING_TEXT = "longtext comment ";
    public static final String LONG = "bigint comment ";
    public static final String INTEGER = "int comment ";
    public static final String FLOAT = "float comment ";
    public static final String DOUBLE = "double comment ";
    public static final String BOOLEAN = "tinyint(1) comment ";
}
