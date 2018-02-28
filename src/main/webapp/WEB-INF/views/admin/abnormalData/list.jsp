<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>异常信息管理</title>
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
                            <label class="sr-only" for="abnormalContent"></label>
                            <input
                                    v-model="form.abnormalContent"
                                    id="abnormalContent"
                                    name="abnormalContent"
                                    type="text"
                                    placeholder="异常信息" class="form-control"/>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="form-group">
                            <label class="sr-only" for="sourceCode"></label>
                            <select v-model="form.sourceCode"
                                     id="sourceCode"
                                     name="sourceCode"
                                     class="form-control">
                            <option value="">请选择接口</option>
                            <option v-for="interface in nameLables" :value="interface.sourceCode">{{interface.sourceName}}</option>
                        </select>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="form-group">
                            <label class="sr-only" for="abnormalType"></label>
                            <select v-model="form.abnormalType"
                                    id="abnormalType"
                                    name="abnormalType"
                                    class="form-control">
                                <option value="">请选择异常类型</option>
                                <option v-for="type in types" :value="type.code">{{type.name}}</option>
                            </select>
                        </div>
                    </div>
                <%--    <div class="col-md-2">
                        <div class="form-group">
                            <label class="sr-only" for="status"></label>
                            <select v-model="form.status"
                                    id="status"
                                    name="status"
                                    class="form-control">
                                <option value="">请选择状态</option>
                                <option value="1">处理</option>
                                <option value="0">未处理</option>

                            </select>
                        </div>
                    </div>--%>
                    <div class="col-md-1">
                        <div class="form-group">
                            <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default"
                                    alt="搜索"
                                    title="搜索">
                                <i class="fa fa-search"></i>
                            </button>
                        </div>
                    </div>
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


<script src="${ctx}/static/js/containers/abnormalData/list.js"></script>
