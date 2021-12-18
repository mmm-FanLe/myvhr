package com.mayao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2021-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MailSendLog implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("msgId")
    private String msgId;

    @TableField("empId")
    private Integer empId;

    /**
     * 0发送中，1发送成功，2发送失败
     */
    private Integer status;

    @TableField("routeKey")
    private String routeKey;

    private String exchange;

    /**
     * 重试次数
     */
    private Integer count;

    /**
     * 第一次重试时间
     */
    @TableField("tryTime")
    private Date tryTime;

    @TableField("createTime")
    private Date createTime;

    @TableField("updateTime")
    private Date updateTime;


}
