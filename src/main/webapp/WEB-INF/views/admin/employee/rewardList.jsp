<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>客服奖励</title>
<!-- 面包屑 -->
<div id="container" class="wrapper wrapper-content">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <button  @click="rewardAdd" type="button" data-dismiss="modal"
                     class="btn btn-primary pull-left m-b">新增</button>

            <button  @click="submitClickBack" type="button" data-dismiss="modal"
                     class="btn btn-primary pull-right m-b">返回</button>
            <table v-el:data-table id="dataTable" width="100%"
                   class="table table-striped table-bordered table-hover">
            </table>
        </div>
    </div>
    <!-- ibox end -->
</div>
<!-- container end-->


<script src="${ctx}/static/js/containers/employee/rewardList.js"></script>
