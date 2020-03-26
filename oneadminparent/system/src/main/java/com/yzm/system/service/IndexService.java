package com.yzm.system.service;

import com.yzm.system.service.dto.ChartDto;

import java.util.List;
import java.util.Map;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 22:05 2019/12/21
 * ===========================
 */
public interface IndexService {

    Map<String, Object> getLoginInfo();

    List<ChartDto> getLoginChart(String username);

    List<ChartDto> getRegionalDistribution();
}
