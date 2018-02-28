<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>房屋列表</title>
<!-- 面包屑 -->
<div id="container" class="wrapper wrapper-content">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
        	
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

<script src="${ctx}/static/js/containers/customer/customerHouseList.js"></script>
