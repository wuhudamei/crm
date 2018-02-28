<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<head>
</head>
<div id="container" class="wrapper wrapper-content animated fadeInRight">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div class="row">
                <form id="searchForm" @submit.prevent="query">
                    <div class="col-md-2 form-group">
                        <label class="sr-only" for="storeCode"></label>
                        <select v-model="form.storeCode" id="storeCode" name="storeCode"
                                class="form-control">
                            <option  value="">--请选择门店--</option>
                            <option v-for="store in stores" :value="store.code">{{store.name}}</option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <label class="sr-only" for="sourceCode"></label>
                        <select v-model="form.sourceCode" id="sourceCode"
                            name="sourceCode" class="form-control">
                            <option  value="">--请选择渠道--</option>
                            <option v-for="dataSource in dataSources"
                                    :value="dataSource.code">{{dataSource.name}}
                            </option>
                        </select>
                    </div>
                    <div class="col-md-2 form-group">
                        <input v-model="form.keyword" type="text"
                               placeholder="客户电话/客户姓名" class="form-control"/>
                    </div>
                    <div class="col-md-2 form-group">
                        <button id="searchBtn" type="submit"
                                class="btn btn-block btn-outline btn-default" alt="搜索"
                                title="搜索">
                            <i class="fa fa-search"></i>
                        </button>
                    </div>
                </form>
            </div>
            <div class="panel-group table-responsive">
                <table width="100%" class="table table-striped table-bordered table-hover table-de panel-heading border-bottom-style">
                    <thead>
                    <tr align="center">
                        <td>序号</td>
                        <td>门店</td>
                        <td>数据来源</td>
                        <td>任务编号</td>
                        <td>进线时间</td>
                        <td>客户编号</td>
                        <td>客户姓名</td>
                        <td>客户手机号</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr align="center" v-for="(index,item) in taskInQueueList">
                        <td>{{index + 1}}</td>
                        <td>{{item.storeCode}}</td>
                        <td>{{item.sourceCode}}</td>
                        <td>{{item.taskNo}}</td>
                        <td>{{item.createTime | goDate}}</td>
                        <td>{{item.customerNo}}</td>
                        <td>{{item.cusName}}</td>
                        <td>{{item.cusMobile}}</td>
                    </tr>
                    <tr v-if="taskInQueueList.length == 0" align="center">
                        <td colspan="8">
                            没有找到匹配的记录
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <!-- ibox end -->
</div>
<script src="/static/js/containers/task/taskInQueueList.js"></script>