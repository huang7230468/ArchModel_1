package ant.dubbo.web.controller;

import ant.dubbo.api.taskTimer.ITaskService;
import ant.dubbo.dto.ResultMsg;
import ant.dubbo.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

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
    @RequestMapping(value = "/allTasks" ,method = RequestMethod.POST)
    @ResponseBody
    //@CrossOrigin
    public ResultMsg getALlTask() throws IOException {
        return  taskService.queryAllTask() ;
    }

    /**
     * @RequestParam 表示入参
     * @PathVariable 表示路径中的入参
     * consumes ： 默认接受application/Json 类型 ，这个需要注意
     * @return
     */
    @RequestMapping(value = "/new1" ,method = RequestMethod.POST ,consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public ResultMsg createTask(@RequestParam(value = "name") String taskName,
                                @RequestParam(value = "group") String group,
                                @RequestParam(value = "trigger") String trigger ,
                                @RequestParam(value = "planExeTimes") String planExeTimes ,
                                @RequestParam(value = "cron") String cron ){
        return taskService.createTask(taskName,group,trigger,cron);
    }

    @RequestMapping(value = "/new" ,method = RequestMethod.POST )
    @ResponseBody
    public ResultMsg createTask(@Valid @RequestBody Task task ,BindingResult result){
        if(result.hasErrors()){
            System.out.println("错误"+result.getFieldError().toString());
        }
        return taskService.createTask(task.getName(),task.getGroup(),task.getTrigger(),task.getCron());
    }

    @RequestMapping(value = "/new2" ,method = RequestMethod.POST )
    @ResponseBody
    public ResultMsg createTask( @RequestBody Map<String,String> map){

        return taskService.createTask(map.get("name"),map.get("group"),map.get("trigger"),map.get("cron"));
    }

    @RequestMapping(value = "/{taskId}/remove")
    @ResponseBody
    public ResultMsg deleteTask(@PathVariable("taskId") String taskId){
        return taskService.removeTask(taskId);
    }

}
