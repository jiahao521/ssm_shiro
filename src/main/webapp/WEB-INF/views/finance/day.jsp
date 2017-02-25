<%--
  Created by IntelliJ IDEA.
  User: jiahao0
  Date: 2017/2/23
  Time: 9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>财务日报</title>
    <%@include file="../include/css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/datatables/jquery.dataTables.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../include/header.jsp"%>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="finance_day"/>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">
                <div class="box-body">
                    <form class="form-inline">
                        <div class="form-group">
                            <input type="text" id="date" class="form-control">
                        </div>
                    </form>
                </div>
            <div class="box box-solid box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title"><i class="fa fa-list"></i> 账单日报</h3>

                    <div class="box-tools pull-right">
                        <a href="javascript:;" id="exportCsv" class="btn btn-default"><i class="fa fa-file-o"></i> 导出Excel</a>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th></th>
                            <th>流水号</th>
                            <th>创建日期</th>
                            <th>账单类型</th>
                            <th>金额</th>
                            <th>业务模块</th>
                            <th>业务流水</th>
                            <th>状态</th>
                            <th>备注</th>
                            <th>#</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

</div>

<%@include file="../include/js.jsp"%>
<script src="/static/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/static/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/plugins/moment.js"></script>
<script src="/static/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="/static/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script src="/static/plugins/echarts.js"></script>
<script>
    $(function () {

            //租赁日期，默认今天
            $("#date").val(moment().format("YYYY-MM-DD"));

            $("#date").datepicker({

                format: "yyyy-mm-dd",
                language: "zh-CN",
                autoclose: true,
                startDate:moment().format("YYYY-MM-DD")
            }).on("changeDate",function(e) {

                var today = e.format(0,'yyyy-mm-dd');
                //重新加载数据，默认第一页，修改为全部
                // table.ajax.reload(false,null);
                table.ajax.reload();
                loadPie();
            });

        var table = $(".table").DataTable({
            "lengthChange": false,
            "pageLength": 25,
            "serverSide": true,
            "ajax":{
                "url":"/finance/day/load",
                "type":"get",
                "data":function(obj){
                    obj.day = $("#date").val()
                }
            },
            "searching":false,//不使用自带的搜索
            "order":[[0,'desc']],//默认排序方式,
            "ordering": false,
            "columns":[
                {"data":"id","name":"id"},
                {"data":"serialNumber"},
                {"data":"createDate"},
                {"data":"type"},
                {"data":"money"},
                {"data":"module"},
                {"data":"moduleSerialNumber"},
                {"data":"state"},
                {"data":"mark"},
                {"data":function (row) {
                    if(row.state == "未确认") {
                        return "<a href='javascript:;' class='confirm_btn' rel='"+row.id+"'>确认</a>";
                    } else {
                        return "";
                    }
                }}
            ],
            "columnDefs":[
                {targets:[0],visible: false}
            ],
            "language":{ //定义中文
                "search": "搜索:",
                "zeroRecords":    "没有匹配的数据",
                "lengthMenu":     "显示 _MENU_ 条数据",
                "info":           "显示从 _START_ 到 _END_ 条数据 共 _TOTAL_ 条数据",
                "infoFiltered":   "(从 _MAX_ 条数据中过滤得来)",
                "loadingRecords": "加载中...",
                "processing":     "处理中...",
                "paginate": {
                    "first":      "首页",
                    "last":       "末页",
                    "next":       "下一页",
                    "previous":   "上一页"
                }
            }
        });

        $(document).delegate(".confirm_btn","click",function(){
            var id = $(this).attr("rel");
            layer.confirm("确认已收(付)款?",function(index){
                layer.close(index);
                $.post("/finance/confirm/"+id).done(function(result){
                    if(result.status == "success") {
                        layer.msg("确认成功");
                        table.ajax.reload(false, null);
                    } else {
                        layer.msg(message);
                    }
                }).error(function(){
                    layer.msg("服务器异常");
                });

            });
        });
    });
</script>
</body>
</html>

