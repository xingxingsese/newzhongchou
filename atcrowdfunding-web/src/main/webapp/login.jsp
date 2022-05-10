<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctx}/static/css/login.css">

</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top " role="navigation">

    <div class="navbar-header">
        <div><a class="navbar-brand" style="text-align:right;  font-size:32px;">何以解忧愁</a></div>
    </div>
</nav>
<%--页面展示图片--%>
 <style type="text/css">
     body{
         background-image: url(${ctx}/static/img/yejing.jpg); /*图片地址*/
         background-size: cover;
         background-repeat: no-repeat; /*图像不重复显示*/
     }
 </style>




<div class="container">
    <form id="loginForm" class="form-signin" role="form" action="${ctx }/doLogin" method="post">
        <c:if test="${!empty msg}">
            <div class="alert alert-warning alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert">
                    <span aria-hidden="true">&times;</span></button>
                <strong>登录失败</strong> ${msg}
            </div>
        </c:if>
        <h2 class="form-signin-heading" style="color:white;"><i class="glyphicon glyphicon-log-in" ></i> 用户登录</h2>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" name="username" value="${username}" placeholder="请输入登录账号" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="password" class="form-control" name="password" placeholder="请输入登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>

        <div class="checkbox" style="color:white;">
            <label>
                <input type="checkbox" value="remember-me"> 记住我
            </label>
            <br>
            <label>
                忘记密码
            </label>
            <label style="float:right">
                <a href="${ctx}/static/reg.html">我要注册</a>
            </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()"> 登录</a>
    </form>
</div>


<script src="${ctx}/static/jquery/jquery-2.1.1.min.js"></script>
<script src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
<script>
    function dologin() {
        $("#loginForm").submit();
    }
</script>


</body>
</html>