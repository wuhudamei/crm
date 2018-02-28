<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>个人报表</title>
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
                            <input v-model="form.startDate" id="startDate" name="startDate"
                                   type="text" class="datepicker form-control" placeholder="开始时间"
                                   readonly="readonly"/>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                            <input v-model="form.endDate" id="endDate" name="endDate"
                                   type="text" class="datepicker form-control" placeholder="结束时间"
                                   readonly="readonly"/>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                            <label class="sr-only" for="storeCode"></label>
                            <select v-model="form.storeCode"
                                    id="storeCode"
                                    name="storeCode"
                                    class="form-control">
                                <option value="">请选择门店</option>
                                <option v-for="store in stores" :value="store.code">{{store.name}}</option>
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
                </form>
            </div>
            <table v-el:data-table id="dataTable" width="100%"
                   class="table table-striped table-bordered table-hover">
            </table>
        </div>
    </div>
    <!-- ibox end -->
</div>
<!-- container end-->


<script src="${ctx}/static/js/containers/reportCenter/personalList.js"></script>
