package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("/login")
    String verify(@RequestBody String userinfo) {
        return null;
    }
}
