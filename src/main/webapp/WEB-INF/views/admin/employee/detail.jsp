<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>客服管理</title>
<!-- 面包屑 -->
<div id="container" class="wrapper wrapper-content">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div class="row">
                <div class="col-sm-12">
                    <div class="ibox">
                        <div class="row">
                            <div class="col-sm-8 col-sm-offset-2">
                                    <form v-cloak name="cusEdit" novalidate class="form-horizontal">
                                        <div class="form-group">
                                            <label for="empName"
                                                   class="col-sm-3 control-label">名称</label>
                                            <div class="col-sm-8">
                                                <input
                                                        v-model="employee.empName"
                                                        id="empName"
                                                        name="empName"
                                                        readonly
                                                        class="form-control"
                                                >
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="mobile"
                                                   class="col-sm-3 control-label">手机号</label>
                                            <div class="col-sm-8">
                                                <input
                                                        v-model="employee.mobile"
                                                        id="mobile"
                                                        readonly
                                                        name="mobile"
                                                        class="form-control"
                                                >
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="jobNum"
                                                   class="col-sm-3 control-label">员工编号</label>
                                            <div class="col-sm-8">
                                                <input
                                                        v-model="employee.jobNum"
                                                        id="jobNum"
                                                        readonly
                                                        name="jobNum"
                                                        class="form-control"
                                                >

                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label
                                                    class="col-sm-3 control-label">自动接单</label>
                                            <div class="col-sm-8">
                                                <input v-if="employee.autoOrder=='Y'" value="是" readonly
                                                       class="form-control"/>
                                                <input v-if="employee.autoOrder=='N'" value="否" readonly
                                                       class="form-control"/>
                                            </div>
                                        </div>
                                        <div class="form-group" >
                                            <label for="busyThreshold"
                                                   class="col-sm-3 control-label">忙碌阈值</label>
                                            <div class="col-sm-8">
                                                <input
                                                        v-model="employee.busyThreshold"
                                                        id="busyThreshold"
                                                        readonly
                                                        name="busyThreshold"
                                                        class="form-control"
                                                >

                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="position"
                                                   class="col-sm-3 control-label">岗位</label>
                                            <div class="col-sm-8">
                                                <input
                                                        v-model="employee.position"
                                                        id="position"
                                                        readonly
                                                        name="position"
                                                        class="form-control"
                                                >
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="parentName"
                                                   class="col-sm-3 control-label">上级</label>
                                            <div class="col-sm-8">
                                                <input
                                                        v-model="employee.parentName"
                                                        id="parentName"
                                                        name="parentName"
                                                        readonly
                                                        class="form-control"
                                                >
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label
                                                    class="col-sm-3 control-label">状态</label>
                                            <div class="col-sm-8">
                                                <input v-if="employee.status=='0'" value="禁用" readonly
                                                       class="form-control"/>
                                                <input v-if="employee.status=='1'" value="启用" readonly
                                                       class="form-control"/>
                                            </div>
                                        </div>

                                        <div class="row row-margin-top-2">
                                            <div class="col-sm-8 col-sm-offset-4">
                                                <button @click="submitClickBack" type="button" data-dismiss="modal"
                                                        class="btn btn-primary">返回
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                            </div>
                        </div>
                    </div>


                </div>
            </div>
        </div>
    </div>
</div>
<!-- ibox end -->
</div>
<!-- container end-->
<!--添加的列表-->
<script src="${ctx}/static/js/containers/employee/detail.js"></script>
