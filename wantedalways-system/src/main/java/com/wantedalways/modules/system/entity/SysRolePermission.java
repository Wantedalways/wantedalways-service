package com.wantedalways.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.*;

/**
 * <p>
 * 角色权限关系表
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_permission")
public class SysRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 角色id
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 权限id
     */
    @TableField("permission_id")
    private String permissionId;


}
