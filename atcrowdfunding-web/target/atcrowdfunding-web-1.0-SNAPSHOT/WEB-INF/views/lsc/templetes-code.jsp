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

                    <button id="runTempletesCodeTest" type="button" class="btn btn-danger"
                            style="float: left; margin-right: 10px;">
                        <i class=" glyphicon glyphicon-remove"></i> TEST
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

<!-- modeltesttest -->
<div class="modal fade" id="runTempletesCode2" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">测试</h4>
            </div>

            <!--模态框中的内容  -->
            <div class="modal-body">
                <form id="roleUpdateForm" action="${ctx}/role/add" method="post">
                    <div class="form-group">
                        <textarea id="te" cols="75" rows="30">输入模版代码</textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="strTestBtn">修改</button>
            </div>
        </div>
    </div>
</div>

<!-- model权限维护模态框 -->
<div class="modal fade" id="rolePermissionModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">权限维护</h4>
            </div>

            <!--模态框中的内容  -->
            <div class="modal-body">

                <ul id="treeDemo" class="ztree"></ul>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="updataRolePermissionBtn">修改</button>
            </div>
        </div>
    </div>
</div>
<%@ include file="/include/common-js.jsp" %>


<!--模态框  -->
<!-- Modal添加 -->
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


<!--给修改按钮绑定单击事件 -->
<script type="text/javascript">
    <!-- 1 给修改按钮绑定单击事件 -->
    $("#content").on("click", ".roleItemUpdateBtn", function () {
        var rid = $(this).attr("rid");
        //2 查出当前被第点击的role的信息,并在model框中显示
        $.get("${ctx}/role/get?id=" + rid, function (data) {
            //3 获取data数据,显示到输入栏里
            $("#roleUpdateForm input[name='name']").val(data.name);
            $("#roleUpdateForm input[name='id']").val(data.id);

            //1弹出模态框
            $("#roleUpdateModal").modal({
                backdrop: 'static',
                show: true
            });
        });
    });

    //点击修改,发送请求数据进行修改
    $("#UpdateRoleAddBtn").click(function () {
        var id = $("#roleUpdateForm input[name='id']").val();
        var name = $("#roleUpdateForm input[name='name']").val();
        //给服务器发送请求
        $.post("${ctx}/role/update", {"id": id, "name": name}, function (date) {
            layer.msg("修改完成");
            //关闭model窗口
            $("#roleUpdateModal").modal("hide");
            //哪一页调的回到哪一页
            getAllRoles(globalPn, globalPs, golalCondition);
        })
    })
</script>
<!--权限维护模态框  -->
<script type="text/javascript">
    <!-- 1 给权限分配按钮绑定单击事件 -->
    $("#content").on("click", ".roleItemUpdateBtn", function () {
        var rid = $(this).attr("rid");
        //2 查出当前被第点击的role的信息,并在model框中显示
        $.get("${ctx}/role/get?id=" + rid, function (data) {
            //3 获取data数据,显示到输入栏里
            $("#roleUpdateForm input[name='name']").val(data.name);
            $("#roleUpdateForm input[name='id']").val(data.id);

            //1弹出模态框
            $("#roleUpdateModal").modal({
                backdrop: 'static',
                show: true
            });
        });
    });

    //点击修改,发送请求数据进行修改
    $("#UpdateRoleAddBtn").click(function () {
        var id = $("#roleUpdateForm input[name='id']").val();
        var name = $("#roleUpdateForm input[name='name']").val();
        //给服务器发送请求
        $.post("${ctx}/role/update", {"id": id, "name": name}, function (date) {
            layer.msg("修改完成");
            //关闭model窗口
            $("#roleUpdateModal").modal("hide");
            //哪一页调的回到哪一页
            getAllRoles(globalPn, globalPs, golalCondition);
        })
    })
</script>
<!--给角色权限树实现  -->
<script type="text/javascript">
    //加载权限树
    var ztreeObj = null;
    //文档加载完成后调用这个方法
    $(function () {
        //显示数据
        initPermissionTree();
    });

    //初始化树形结构配置显示在ul里面
    function initPermissionTree() {
        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    pIdKey: "pid"
                },
                key: {
                    url: "haha",
                    name: "title"
                }
            },
            view: {
                addDiyDom: showMyIcon,//调用这个方法显示自定义图标

            }, check: {//勾选框
                enable: true
            }
        };
        var zNodes = null;
        //找服务器要数据
        //以get方式在当前目录下给permission/list发送请求,成功后回调函数取得data
        //这个data里就是查询到的各种数据
        $.get("${ctx}/permission/list", function (data) {
            zNodes = data;
            //给数组添加一个数据
            zNodes.push({id: 0, title: "系统所有权限"});
            //注意:ajax是异步的,所以调用数据展示的代码必须放在ajax代码里面
            //初始化树
            ztreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            //将整个znode树展开
            ztreeObj.expandAll(true);
        });

        //自定义显示图标的回调函数
        function showMyIcon(treeId, treeNode) {
            //treeId 对应 zTree 的 treeId，便于用户操控
            //treeNode 需要显示自定义控件的节点 JSON 数据对象

            var tId = treeNode.tId;
            var iconSpan = $("<span class='" + treeNode.icon + "'></span>");
            //查询id为tid_ico的属性,然后把class内容都清空,然后添加数据库的内容
            $("#" + tId + "_ico").removeClass();//清除默认样式
            $("#" + tId + "_ico").after(iconSpan); //使用自己的图标
        }

        //=======================以上是权限树的方法==========

    }
</script>
<!-- 角色权限维护功能实现 -->
<script type="text/javascript">
    var globalRid = "";
    $("#content").on("click", ".rolePermissonAssignBtn", function () {
        //获取被点击按钮的id
        globalRid = $(this).attr("rid");
        //查出这个角色之前的permisson信息,回显在权限树中
        $.get("${ctx}//permission/role/get?rid=" + globalRid, function (data) {
            //查看之前先清空上次树的勾选状态,把所有节点全部取消勾选
            ztreeObj.checkAllNodes(false);
            $.each(data, function () {
                //查找出勾中的节点id
                var treeNode = ztreeObj.getNodeByParam("id", this.id, null);
                //把查找出来勾中节点的id给勾中
                ztreeObj.checkNode(treeNode, true, false);
            });

        });

        //打开模态框
        $("#rolePermissionModal").modal({
            show: true,
            backdrop: 'static'
        });
    });

    $("#updataRolePermissionBtn").click(function () {
        //上一次点击的角色idlayer.msg(globalRid);
        //获取选中的权限id
        var permissionIds = "";
        //getCheckedNodes(true)获取选中的权限id false获取为选中的id
        $.each(ztreeObj.getCheckedNodes(true), function () {
            if (this.id != 0) {
                permissionIds += this.id + ",";
            }
        });

        $.post("${ctx}/permission/role/assign", {"rid": globalRid, "permissionIds": permissionIds}, function (data) {
            if (data == "ok") {
                layer.msg("分配完成");
                $("#rolePermissionModal").modal("hide");
            }

        });
    });
</script>
</body>
</html>
