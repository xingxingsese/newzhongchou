<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div class="layui-header">
    <div class="layui-logo" onclick="location.href='${PATH }/main.html'">武林秘籍管理系统</div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-left">
        <li class="layui-nav-item"><a href="">控制台</a></li>
        <li class="layui-nav-item"><a href="">商品管理</a></li>
        <li class="layui-nav-item"><a href="">用户</a></li>
        <li class="layui-nav-item"><a href="javascript:;">其它系统</a>
            <dl class="layui-nav-child">
                <dd>
                    <a href="">邮件管理</a>
                </dd>
                <dd>
                    <a href="">消息管理</a>
                </dd>
                <dd>
                    <a href="">授权管理</a>
                </dd>
            </dl>
        </li>
    </ul>
    <ul class="layui-nav layui-layout-right">
        <li class="layui-nav-item"><a href="javascript:;"> <img
                src="http://t.cn/RCzsdCq" class="layui-nav-img"> 张无忌
        </a>
            <dl class="layui-nav-child">
                <dd>
                    <a href="">基本资料</a>
                </dd>
                <dd>
                    <a href="">安全设置</a>
                </dd>
            </dl>
        </li>
        <!-- login?logout：退出成功默认会来到这个地址作为退出页面的展示地址 -->
        <li class="layui-nav-item"><a href="${PATH }/gogogo">退出</a></li>
    </ul>
</div>