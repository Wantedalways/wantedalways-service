package com.wantedalways.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_depart")
public class SysUserDepart implements Serializable {

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
     * 部门id
     */
    @TableField("depart_id")
    private String departId;

    /**
     * 是否为部门负责人（0，否；1，是）
     */
    @TableField("is_leader")
    private Boolean leader;

    /**
     * 排序
     */
    @TableField("order")
    private Integer order;

    /**
     * 是否为主部门（0，否；1，是）
     */
    @TableField("is_major")
    private Boolean major;


}
