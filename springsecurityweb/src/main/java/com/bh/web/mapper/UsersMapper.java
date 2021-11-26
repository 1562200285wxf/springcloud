package com.bh.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.web.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/15 17:32
 * @Description: TODO
 */
@Repository
public interface UsersMapper extends BaseMapper<Users> {

    int insertBatch(@Param("list") List<Users> list);


}