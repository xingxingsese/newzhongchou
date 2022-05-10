<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctx}/static/css/carousel.css">
</head>

<body>
<div class="navbar-wrapper">
    <div class="container">
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="${ctx}/static/index.html" style="font-size:32px;">何以解忧愁</a>
                </div>
                <div id="navbar" class="navbar-collapse collapse" style="float:right;">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="${ctx}/login.jsp">登录</a></li>
                        <li><a href="${ctx}/static/reg.html">注册</a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
</div>

<%--页面展示图片--%>
<style type="text/css">
    body{
        background-image: url(${ctx}/static/img/chuan.jpg); /*图片地址*/
        background-size: cover;
        background-repeat: no-repeat; /*图像不重复显示*/
    }
</style>





<script src="${ctx}/static/jquery/jquery-2.1.1.min.js"></script>
<script src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/static/script/docs.min.js"></script>
<script src="${ctx}/static/script/back-to-top.js"></script>

</body>
</html>