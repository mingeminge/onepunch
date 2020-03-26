package com.yzm.system.controller;

import com.yzm.common.vo.ResultVO;
import com.yzm.system.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 22:06 2019/12/21
 * ===========================
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping("/loginInfo")
    public ResultVO getLoginInfo() {
        Map<String, Object> loginInfo = indexService.getLoginInfo();
        return ResultVO.ok(loginInfo);
    }

    @GetMapping("/barChart/{name}")
    public ResultVO getBarChart(@PathVariable String name) {
        return ResultVO.ok(indexService.getLoginChart(name));
    }

    @GetMapping("/getRegionalDistribution")
    public ResultVO getRegionalDistribution(){
        return ResultVO.ok(indexService.getRegionalDistribution());
    }

}
