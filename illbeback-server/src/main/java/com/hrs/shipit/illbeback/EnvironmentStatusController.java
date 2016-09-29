package com.hrs.shipit.illbeback;

import com.hrs.shipit.illbeback.model.EnvironmentStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by asi03 on 2016-09-29.
 */
@RestController
public class EnvironmentStatusController {

    @RequestMapping("/environment")
    public EnvironmentStatus greeting(@RequestParam(value="name", defaultValue="") String name) {
        return new EnvironmentStatus("detpuw1", false);
    }
}
