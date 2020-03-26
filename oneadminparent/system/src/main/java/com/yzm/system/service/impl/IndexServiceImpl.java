package com.yzm.system.service.impl;

import com.yzm.system.mapper.IndexMapper;
import com.yzm.system.service.IndexService;
import com.yzm.system.service.dto.ChartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 22:05 2019/12/21
 * ===========================
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private IndexMapper indexMapper;

    @Override
    public Map<String, Object> getLoginInfo() {
        return indexMapper.getLoginInfo();
    }

    @Override
    public List<ChartDto> getLoginChart(String username) {
        return indexMapper.getLoginChart(username);
    }

    @Override
    public List<ChartDto> getRegionalDistribution() {
        return indexMapper.getRegionalDistribution();
    }
}
