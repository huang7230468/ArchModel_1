package ant.dubbo.web.controller;

import ant.dubbo.api.taskTimer.ITaskService;
import ant.dubbo.dto.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * @author
 * @create 2018-01-10 21:59
 **/
@Controller
@RequestMapping(value = "/task")
public class TaskController {
    @Autowired
    ITaskService taskService ;
    /**
     * RequestMapping解释
     *  value / path: 指定请求URI 可以是具体的字符串或是变量或者含有正则的一类值
     *  method :  指定请求的method类型
     *  produces : 指定返回内容类型
     *  consumes : 指定处理请求的提交内容类型
     *  params  : 指定request中必须包含某些参数值，才处理
     *  headers : 指定request请求中必须包含指定的header值，才处理
     * @return
     */
    @RequestMapping(value = "/allTasks"  ,method = RequestMethod.POST )
    @ResponseBody
    public ResultMsg getALlTask() throws IOException {
        return  taskService.queryAllTask() ;
    }
}
