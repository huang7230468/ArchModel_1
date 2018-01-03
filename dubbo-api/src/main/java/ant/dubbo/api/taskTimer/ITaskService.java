package ant.dubbo.api.taskTimer;

import ant.dubbo.dto.ResultMsg;

public interface ITaskService {

    public ResultMsg<?> createTask(String taskName, String taskClassName, String tiggerName, String cron);
}
