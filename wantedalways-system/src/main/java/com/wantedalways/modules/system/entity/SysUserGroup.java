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
 * 用户用户组关系表
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_group")
public class SysUserGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户组id
     */
    @TableField("group_id")
    private String groupId;


}
