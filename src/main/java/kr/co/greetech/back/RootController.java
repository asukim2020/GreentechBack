package kr.co.greetech.back;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @RequestMapping
    public String defaultResult() {
        return "OK";
    }
}
