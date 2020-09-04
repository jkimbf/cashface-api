package swmaestro.revivers.cashface.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainPageController {

    @GetMapping("/")
    public String HelloWorld() {
        return "Team Revivers 입니당.";
    }

}
