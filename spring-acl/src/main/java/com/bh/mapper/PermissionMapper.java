package com.bh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.entity.Permission;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/22 11:07
 * @Description: TODO
 */
public interface PermissionMapper extends BaseMapper<Permission> {


    List<String> selectPermissionValueByUserId(String id);

    List<String> selectAllPermissionValue();

    List<Permission> selectPermissionByUserId(String userId);
}
