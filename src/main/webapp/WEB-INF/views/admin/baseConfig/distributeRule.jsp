<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>派发规则</title>
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
                            <label class="sr-only" for="name"></label>
                            <input v-model="form.name" id="name"
                                    name="name" type="text"
                                    placeholder="规则名称" class="form-control"/>
                        </div>
                    </div>
                    <div class="col-md-1">
                        <div class="form-group">
                            <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default"
                                    alt="搜索" title="搜索">
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

<script src="${ctx}/static/js/containers/baseConfig/distributeRule.js"></script>
