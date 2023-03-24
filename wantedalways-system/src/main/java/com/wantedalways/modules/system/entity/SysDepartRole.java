package com.wantedalways.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 部门角色关系表
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_depart_role")
public class SysDepartRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 部门id
     */
    @TableField("depart_id")
    private String departId;

    /**
     * 角色id
     */
    @TableField("role_id")
    private String roleId;


}
