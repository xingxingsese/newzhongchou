<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <link rel="stylesheet" href="${ctx}/static/css/main.css">
    <link rel="stylesheet" href="${ctx}/static/ztree/zTreeStyle.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor: pointer;
        }

        table tbody tr:nth-child(odd) {
            background: #F4F4F4;
        }

        table tbody td:nth-child(even) {
            color: #C00;
        }
    </style>
</head>

<body>
<%
    pageContext.setAttribute("title", "JAVA代码生成");
%>
<!--顶部导航  -->
<%@ include file="/include/top-nav.jsp" %>

<div class="container-fluid">
    <div class="row">
        <!--  侧栏导航-->
        <%@ include file="/include/side-bar.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>

                <div class="panel-body">

                    <button id="addTempletes" type="button" class="btn btn-primary"
                            style="float: left; margin-right: 10px;">
                        <i class="glyphicon glyphicon-plus"></i> 添加模版
                    </button>

                    <button id="deleRoleBtn" type="button" class="btn btn-danger"
                            style="float: left; margin-right: 10px;">
                        <i class=" glyphicon glyphicon-remove"></i> 删除
                    </button>

                    <button type="button" class="btn btn-primary"
                            style="float: left; margin-right: 10px;" id="openRoleAddmodelBtn">
                        <i class="glyphicon glyphicon-plus"></i> java代码生成
                    </button>
                    <br>
                    <hr style="clear: both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="allCheckBtn" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>

                            <tbody id="content"></tbody>

                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <ul class="pagination" id="pageInfoBar">

                                    </ul>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--模态框  java代码生成 -->
<div class="modal fade" id="runTempletesCode" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="roleAddModalTitle">模版代码</h4>
            </div>
            <!--模态框中的内容  -->
            <div class="modal-body">
                <form id="roleAddForm" action="${ctx}/role/add" method="post">
                    <div class="form-group">
                        <label>项目路径</label>
                        <input type="text" class="form-control" id="projectPath_input" name="projectPath"
                               placeholder="项目路径">
                        <label>类名</label>
                        <input type="text" class="form-control" id="className_input" name="className"
                               placeholder="类名">
                        <label>mapper名称</label>
                        <input type="text" class="form-control" id="mapClass_input" name="mapClass"
                               placeholder="mapper名称">
                        <label>输出路径</label>
                        <input type="text" class="form-control" id="outPath_input" name="outPath"
                               placeholder="输出路径">
                        <%--<textarea cols="210" rows="30">输入模版代码</textarea>--%>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="saveRoleAddBtn">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 添加模版 -->
<div class="modal fade" id="addTempletemodal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">添加模版</h4>
            </div>

            <!--模态框中的内容  -->
            <div class="modal-body">
                <form id="roleUpdateForm" name="templetesForm" action="${ctx}/freemarker/templete/mock" method="post">
                    <div class="form-group">
                        <input type="hidden" name="userId" id="userId" value="${loginUser.id }">

                        <label>模版名称</label>
                        <input type="text" class="form-control" id="templetesName" name="templetesName"
                               placeholder="模版名称">
                        <label>模版内容</label>
                        <textarea id="templetesText" cols="75" rows="30" placeholder="输入模版代码"></textarea>


                    </div>
                </form>
            </div>
        </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="strTestBtn">保存</button>
            </div>
        </div>
    </div>
</div>

<%@ include file="/include/common-js.jsp" %>

<!--模态框  -->
<!-- 添加模版 -->
<script type="text/javascript">
    /*  点击添加模版按钮打开模态框*/
    $("#addTempletes").click(function () {

        //点击背景框不会关闭模态框
        $("#addTempletemodal").modal({
            backdrop: 'static',
            show: true
        });
    });
    //点击保存,提交表单给服务器保存数据
    $("#strTestBtn").click(function () {
       var str = $("#templetesText").val();
       var templetesName = $("#templetesName").val();
       var userId = ${loginUser.id}
       var userName = ${loginUser.name}

        //ajax提交携带数据,只需要把数据组装成对象,放在参数位置即可
        $.post("${ctx}/freemarker/templete/mock",
        {
            "templateText": str,
            "templateName":templetesName,
            "userId":userId,
            "author":userName
        }
        , function (data) {
            if (data == "ok") {
                layer.msg("保存成功");
            } else {
                layer.msg("生成失败");
            }
            //关闭模态框
            $("#addTempletemodal").modal('hide');
        },"json");
    });
</script>

<!--模态框  -->
<!-- java代码生成 -->
<script type="text/javascript">
    /*  点击新增打开模态框*/
    $("#openRoleAddmodelBtn").click(function () {

        //点击背景框不会关闭模态框
        $("#runTempletesCode").modal({
            backdrop: 'static',
            show: true
        });
    });
    //点击保存,提交表单给服务器保存数据
    $("#saveRoleAddBtn").click(function () {
        //找到表单提交
        //提交表单,href,等等这些都属于页面跳转方式,而不是ajax
        //为了防止页面跳转,所以我们要使用ajax方式,
        //$("#roleAddForm input:eq(0)").val();//最好不要用eq方式找数据,这样input顺序必须按这个来
        var projectPath = $("#roleAddForm input[name='projectPath']").val();
        var className = $("#roleAddForm input[name='className']").val();
        var mapClass = $("#roleAddForm input[name='mapClass']").val();
        var outPath = $("#roleAddForm input[name='outPath']").val();
        //ajax提交携带数据,只需要把数据组装成对象,放在参数位置即可
        $.post("${ctx}/freemarker/jsonbuildcode", {
            "projectPath": projectPath,
            "className": className,
            "outPath": outPath,
            "mapClass": mapClass
        }, function (data) {
            if (data == "ok") {
                layer.msg("生成成功");
            } else {
                layer.msg("生成失败");
            }
            //关闭模态框
            $("#runTempletesCode").modal('hide');
        });
    });
</script>
</body>
</html>
