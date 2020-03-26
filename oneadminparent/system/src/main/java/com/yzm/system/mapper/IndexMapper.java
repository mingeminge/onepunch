package com.yzm.system.mapper;

import com.yzm.system.service.dto.ChartDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 21:59 2019/12/21
 * ===========================
 */
@Mapper
@Repository
public interface IndexMapper {

    Map<String, Object> getLoginInfo();

    List<ChartDto> getLoginChart(@Param("username") String username);

    List<ChartDto> getRegionalDistribution();
}
