package com.zhou.demo.demos.web;

import com.zhou.demo.demos.web.result.Result;
import com.zhou.demo.security.dto.DemoDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laurence
 */
@RestController
public class DemoController {

    @PostMapping("/demo")
    public Result<DemoDto> demo(@RequestBody DemoDto demoDto) {
        return Result.success(demoDto);
    }

    @PostMapping("/api/sm2demo")
    public Result<DemoDto> sm2Demo(@RequestBody DemoDto demoDto) {
        return Result.success(demoDto);
    }

}
