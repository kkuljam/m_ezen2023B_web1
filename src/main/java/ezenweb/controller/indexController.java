package ezenweb.controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {
@GetMapping("/")
    public String Index(){
    return "ezenweb/index";
}




}
