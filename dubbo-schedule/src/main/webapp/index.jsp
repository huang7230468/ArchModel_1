<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/3
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $.ajax({
                type : 'post',
                dataType : 'json',
                url:'http://127.0.0.1:8080/web/task/allTasks',
                success:function (r) {
                      console.log("返回数据",r);
                }
            })

        })


    </script>
</head>
<body>
        hi,我是首页！！！
</body>
</html>
