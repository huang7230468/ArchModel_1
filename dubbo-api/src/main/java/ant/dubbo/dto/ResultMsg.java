package ant.dubbo.dto;

/**
 * @author
 * @create 2018-01-03 22:35
 **/
public class ResultMsg<T> {

    private boolean success;

    private T data ;

    private  String error ;

    public ResultMsg(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public ResultMsg(boolean success, T data, String error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
