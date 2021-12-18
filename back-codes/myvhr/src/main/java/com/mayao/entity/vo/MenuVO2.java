package com.mayao.entity.vo;

import com.mayao.entity.Menu;
import com.mayao.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * menu传递给前端的数据
 * Created by mayao on 2021/11/12 4:49 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MenuVO2 {
    private Integer id;

    private String url;

    private String path;

    private String component;

    private String name;

    private String iconCls;

    private Integer parentId;

    private Boolean enabled;

    private Boolean keepAlive;

    private Boolean requireAuth;

    private List<Menu> children;

    private List<Role> roles;
}
