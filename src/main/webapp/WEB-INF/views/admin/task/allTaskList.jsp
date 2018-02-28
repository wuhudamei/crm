<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<title>任务列表</title>
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
							<label class="sr-only" for="keyword"></label> <input
								v-model="form.keyword" id="keyword" name="keyword" type="text"
								placeholder="姓名|手机号|邀约码" class="form-control" />
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<label class="sr-only" for="storeCode"></label>
							<select
								v-model="form.storeCode" id="storeCode" name="storeCode"
								class="form-control">
								<option value="" selected="selected">--请选择门店--</option>
								<option v-for="store in stores" :value="store.code">{{store.name}}</option>
							</select>
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<label class="sr-only" for="storeCode"></label>
							<select
								v-model="form.taskStatus" id="taskStatusSel" name="taskStatus"
								class="form-control">
								<option value="" selected="selected">--请选择状态--</option>
								<option value="1" >有效</option>
								<option value="2" >冻结</option>
								<option value="0" >无效</option>
							</select>
						</div>
					</div>

					<div class="col-md-1">
						<div class="form-group">
							<button id="searchBtn" type="submit"
								class="btn btn-block btn-outline btn-default" alt="搜索"
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
<!--客服列表-->
<div id="customerModal" class="modal fade" tabindex="-1"
	data-width="800" style="top: 30%">
	<div class="ibox-title">
		<h5>选取客服</h5>
	</div>

	<div class="ibox-content">
		<div class="row">
			<form id="searchEmployee" @submit.prevent="queryEmployee">
				<div class="col-md-2">
					<div class="form-group">
						<label class="sr-only" for="keyword"></label> <input
							v-model="form.keyword" id="keywords" name="keywords" type="text"
							placeholder="姓名/手机号" class="form-control" />
					</div>
				</div>
				<div class="col-md-1">
					<div class="form-group">
						<button id="searchEmployeeBtn" type="submit"
							class="btn btn-block btn-outline btn-default" alt="搜索" title="搜索">
							<i class="fa fa-search"></i>
						</button>
					</div>
				</div>
			</form>
			<table id="customerTable" width="100%"
				class="table table-striped table-bordered table-hover">
			</table>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn">关闭</button>
				<button type="button" v-on:click="commitCustomer"
					class="btn btn-primary">确定</button>
			</div>
		</div>
	</div>
</div>
<script src="${ctx}/static/js/containers/task/jquery.form.js"></script>
<script src="${ctx}/static/js/containers/task/allTaskList.js"></script>
