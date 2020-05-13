package com.xiehui.es;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Document(indexName = "users", type = "userInfo")
@Data
public class UserInfo {
    @Id
    private Long id;
    @Field(type = FieldType.keyword)
    private String userName;
    @Field(type = FieldType.keyword)
    private String email;
    @Field(type = FieldType.keyword, index = false)
    private String image;
    @Field(type = FieldType.text, analyzer = "ik_max_word")
    private String remark;

}