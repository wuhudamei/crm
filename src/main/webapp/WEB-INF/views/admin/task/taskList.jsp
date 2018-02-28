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
							<label class="sr-only" for="mechandiser"></label> <input
								v-model="form.mechandiser" id="mechandiser" name="mechandiser" type="text"
								placeholder="客服经理" class="form-control" />
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<label class="sr-only" for="storeCode"></label> <select
								v-model="form.storeCode" id="storeCode" name="storeCode"
								class="form-control">
								<option v-if="stores.length == 0" value="">--请选择门店--</option>
								<option v-for="store in stores" :value="store.code">{{store.name}}</option>
							</select>
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<label class="sr-only" for="dataSourceCode"></label> <select
								v-model="form.dataSourceCode" id="dataSourceCode"
								name="dataSourceCode" class="form-control">
								<option v-if="dataSources.length == 0" value="">--请选择渠道--</option>
								<option v-for="dataSource in dataSources"
									:value="dataSource.code">{{dataSource.name}}</option>
							</select>
						</div>
					</div>

					<div class="col-md-2" v-if="ps">
						<div class="form-group">
							<label class="sr-only" for="dataSourceCode"></label> <select
								v-model="form.promoteSource" id="promoteSource"
								name="dataSourceCode" class="form-control">
							<option  value="">--请选择推广来源--</option>
							<option v-for="item in promoteSources"
									:value="item.code">{{item.name}}</option>
						</select>
						</div>
					</div>

					<div class="col-md-2" v-if="ps">
						<div class="form-group">
							<label class="sr-only" for="dataSourceCode"></label> <select
								v-model="form.status" id="status"
								name="dataSourceCode" class="form-control">
							<option  value="">--请选择状态--</option>
							<option v-for="item in statuss"
									:value="item.code">{{item.name}}</option>
						</select>
						</div>
					</div>

					<div class="col-md-2">
						<div class="form-group">
							<input v-model="form.startDate" id="startDate" name="startDate"
								type="text" class="datepicker form-control" placeholder="开始时间"
								readonly="readonly" />
						</div>
					</div>

					<div class="col-md-2">
						<div class="form-group">
							<input v-model="form.endDate" id="endDate" name="endDate"
								type="text" class="datepicker form-control" placeholder="结束时间"
								readonly="readonly" />
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

				<div class="col-md-5"></div>

				<shiro:hasPermission name="task:export">
					<div class="col-md-1">
						<div class="form-group">
							<button id="exportData" type="submit"
								class="btn btn-block btn-primary" alt="导出" title="导出"
								@click="exportData">
								<i class="fa ">导出</i>
							</button>
						</div>
					</div>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="task:import">
					<form name="import" method="post" id="importData"
						v-if="form.distributeStatus === 'N'">
						<div class="col-md-2">
							<div class="form-group">
								<input type="file" name="file" class="form-control"
									placeholder="选择文件"> <input type="hidden"
									name="storeCode" :value="form.storeCode"> <input
									type="hidden" name="dataSourceCode" :value="form.dataSourceCode">
							</div>
						</div>
						<div class="col-md-1">
							<div class="form-group">
								<input type="button" @click="import" value="导入"
									class="btn btn-default">
							</div>
						</div>
					</form>
					<div class="col-md-2">
						<div class="form-group">
							<a href="${ctx}/static/download/importTemplate.xlsx">下载导入模板</a>
						</div>
					</div>
				</shiro:hasPermission>

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
<script src="${ctx}/static/js/containers/task/taskList.js"></script>
