package com.mayao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2021-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Department extends Model<Department> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 部门名称
     */
    private String name;

    @TableField("parentId")
    private Integer parentId;

    @TableField("depPath")
    private String depPath;

    private Boolean enabled;

    @TableField("isParent")
    private Boolean isParent;

    @TableField(exist = false)
    private List<Department> children;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
