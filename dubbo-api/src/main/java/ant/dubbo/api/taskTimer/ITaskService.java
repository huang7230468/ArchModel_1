package ant.dubbo.api.taskTimer;

import ant.dubbo.dto.ResultMsg;
/**
 * 任务接口类
 *
 * @author hh
 * @create 2018-01-01 22:03
 **/
public interface ITaskService {
    /**
     * 返回所有任务
     * @return
     */
    public ResultMsg<?> queryAllTask();
    /**
     * @param taskName      任务名称
     * @param taskClassName 任务类名
     * @param tiggerName    触发器名（任务方法名）
     * @param cron          任务cron表达式
     * @return
     */
    public ResultMsg<?> createTask(String taskName, String taskClassName, String tiggerName, String cron);
    /**
     * 修改任务cron表达式
     * @param taskId 任务id
     * @param cron   cron表达式
     * @return
     */
    public ResultMsg<?> modifyTask(String taskId,String cron);

    /**
     * 移除任务
     * @param taskId 任务id
     * @return
     */
    public ResultMsg<?> removeTask(String taskId) ;
}
