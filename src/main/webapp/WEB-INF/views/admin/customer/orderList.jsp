<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>订单列表</title>
<!-- 面包屑 -->
<div id="container" class="wrapper wrapper-content">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
           <!--  <div class="row">
                <form id="searchForm" @submit.prevent="query">
                    <div class="col-md-2">
                        <div class="form-group">
                            <label class="sr-only" for="communicateType"></label>
                            <input v-model="form.communicateType" id="communicateType"
                                    name="communicateType" type="text"
                                    placeholder="沟通类型" class="form-control"/>
                        </div>
                    </div>
                    
                    <div class="col-md-2">
                    	<div class="form-group input-group">
			            	<input id="orderStartDate" v-model="form.orderStartDate" type="text"
			                  placeholder="--订单提报日期--" class="datepicker form-control" readonly/>
			            </div>
                    </div>
                    
                    <div class="col-md-2">
                    	<div class="form-group input-group">
			            	<input id="orderEndDate" v-model="form.orderEndDate" type="text"
			                  placeholder="--截止提报日期--" class="datepicker form-control" readonly/>
			            </div>
                    </div>
                    
                    <div class="col-md-1">
                        <div class="form-group">
                            <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default"
                                    alt="搜索" title="搜索">
                                <i class="fa fa-searchApplys"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div> -->
            <div class="row">
            	<div class="col-md-1" style="float: right;">
					<div class="btn-group" role="group" aria-label="...">
						<button type="button" class="btn btn-primary" @click="goList">返回</button>
						<i class="fa "></i>
					</div>
				</div>
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

<script src="${ctx}/static/js/containers/customer/orderList.js"></script>
