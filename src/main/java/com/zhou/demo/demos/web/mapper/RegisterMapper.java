package com.zhou.demo.demos.web.mapper;

import com.zhou.demo.demos.web.model.RegisterInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/11/8 上午10:31
 */
@Mapper
public interface RegisterMapper {
    Integer insertDbDorGenId(RegisterInfo info);
}
