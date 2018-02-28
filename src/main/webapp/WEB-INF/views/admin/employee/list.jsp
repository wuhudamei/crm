<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>用户管理</title>
<!-- 面包屑 -->
<div id="container" class="wrapper wrapper-content">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div class="row">
                <form id="searchForm" @submit.prevent="query">
                    <div class="col-md-2">
                        <div class="form-group">
                            <label class="sr-only" for="keyword"></label>
                            <input
                                    v-model="form.keyword"
                                    id="keyword"
                                    name="keyword"
                                    type="text"
                                    placeholder="姓名/手机号/工号" class="form-control"/>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                            <label class="sr-only" for="autoOrder"></label>
                            <select v-model="form.autoOrder"
                                    id="autoOrder"
                                    name="autoOrder"
                                    class="form-control">
                                <option value="">--自动接单--</option>
                                <option value="Y">是</option>
                                <option value="N">否</option>
                            </select>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                            <label class="sr-only" for="storeCode"></label>
                            <select v-model="form.storeCode"
                                    id="storeCode"
                                    name="storeCode"
                                    class="form-control">
                                <option  v-if="stores== null || stores==''" value="">请选择门店</option>
                                <option v-for="store in stores" :value="store.code" >{{store.name}}</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="form-group">
                            <label class="sr-only" for="statusSel"></label>
                            <select v-model="form.status"
                                    id="statusSel"
                                    name="status"
                                    class="form-control">
                                <option  value="">全部状态</option>
                                <option  value="1" selected="selected">启用</option>
                                <option  value="0">禁用</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-1">
                        <div class="form-group">
                            <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default"
                                    alt="搜索"
                                    title="搜索">
                                <i class="fa fa-search"></i>
                            </button>
                        </div>
                    </div>


                    <div class="col-md-2"></div>
                    <div class="col-md-1">
                        <div class="form-group">
                            <button id="synUser" type="submit" class="btn"
                                    alt="用户同步" :class="synClass" :disabled="synBtn"
                                    title="同步" @click="synUser">{{synName}}
                            </button>
                        </div>
                    </div>
               <%--     <!-- 将剩余栅栏的长度全部给button -->
                    <div class="col-md-9 text-right">

                            <div class="form-group">
                                <button @click="createBtnClickHandler" id="createBtn" type="button"
                                        class="btn btn-outline btn-primary">新增
                                </button>
                            </div>

                    </div>--%>
                </form>
            </div>
            <!-- <div class="columns columns-right btn-group pull-right"></div> -->
            <table v-el:data-table id="dataTable" width="100%"
                   class="table table-striped table-bordered table-hover">
            </table>
        </div>
    </div>
    <!-- ibox end -->
</div>
<!-- container end-->
<!-- 数据权限列表 -->
<div id="permissionModal" class="modal fade" tabindex="-1" data-width="600">
    <form name="createMirror" novalidate class="form-inline" role="form">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3>设置数据权限</h3>
        </div>

        <div class="modal-body">
            <div class="form-group">
                <div class="row ">
                    <div class="col-md-3 form-group role-item ellips">
                        <label>
                            <input @click="selAllCb(permissions,$event)" id="selAllCb"
                                   type="checkbox"
                                   data-checkbox="sub">
                            所有权限</label>
                    </div>
                </div>
                <div v-for="permission in permissions">
                    <div class="row ">
                        <div class="col-md-4 col-xs-4 form-group role-item ellips">
                            <label>
                                <input @click="checkAll(permission,$event)" style="margin-left:40px"
                                       type="checkbox"
                                       data-checkbox="select"> {{$key}}</label>
                        </div>
                    </div>

                    <div class="row" style="margin-left:60px">
                        <div v-for="content in permission"
                             class="col-md-4 col-xs-4  form-group role-item ellips">
                            <label>
                                <input @click="checkSub(content,$event)" type="checkbox"
                                       :checked="content.checked"
                                       data-checkbox="sub">{{content.name}}</label>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal-footer">
            <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
            <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
        </div>
    </form>
</div>
<!-- 接单来源列表-->
    <div id="orderModal" class="modal fade" tabindex="-1" data-width="600">
        <form name="orderMirror" novalidate class="form-inline" role="form">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3>设置接单来源 </h3>
            </div>

            <div class="modal-body">
                <div class="form-group">
                    <div class="row ">
                        <div class="col-md-3 form-group role-item ellips">
                            <label>
                                <input @click="selAllC(orderSources,$event)" id="selAllCbC"
                                       type="checkbox"
                                       data-checkbox="sub">
                                全选</label>
                        </div>
                    </div>


                    <div v-for="permission in orderSources">
                        <%--<div class="row ">
                            <div class="col-md-3 col-xs-3 form-group role-item ellips">
                                <label>
                                    <input @click="selAllCbC(permission,$event)" style="margin-left:40px"
                                           type="checkbox"
                                           data-checkbox="select"> {{$key}}</label>
                            </div>
                        </div>--%>
                        <div class="row" style="margin-left:60px">
                            <div v-for="content in permission"
                                 class="col-md-4 col-xs-4  form-group role-item ellips">
                                <label>
                                    <input @click="checkSubC(content,$event)" type="checkbox"
                                           :checked="content.checked"
                                           data-checkbox="sub">{{content.orderSourceName}}</label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
                <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
            </div>
        </form>
    </div>
<script src="${ctx}/static/js/containers/employee/list.js"></script>
