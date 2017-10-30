package ant.dubbo.web.controller;

import ant.dubbo.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String index(HttpServletRequest request, HttpServletResponse response) {

        String name = "";
        if (request.getParameter("name") != null)
            name = request.getParameter("name");

        String result = userService.getName(name);
        return result;
    }
}
