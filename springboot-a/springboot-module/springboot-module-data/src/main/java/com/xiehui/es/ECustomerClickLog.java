package com.xiehui.es;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Document(indexName = "customerclicklog", type = "clickLogInfo")
@Data
public class ECustomerClickLog implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -907605338326897955L;
    /** 日志标识 */
    @Id
    private Long id;
    /** ip地址 */
    @Field(type = FieldType.keyword)
    private String ip;
    /** 来源 */
    @Field(type = FieldType.keyword)
    private String plateFrom;
    /** 主渠道 */
    @Field(type = FieldType.Integer)
    private Integer channel;
    /** 子渠道 */
    @Field(type = FieldType.Integer)
    private Integer subChannel;
    /** 落地页标识 */
    @Field(type = FieldType.Long)
    private Long hrefId;
    /** 创建时间 */
    @Field(type = FieldType.Date)
    private Date createdTime;
    /** 用户标识 */
    @Field(type = FieldType.Long)
    private Long customerId;

}
