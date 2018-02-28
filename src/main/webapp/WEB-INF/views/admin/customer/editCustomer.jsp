<%@ page contentType="text/html;charset=UTF-8"%>
<head>
<style>
.progress {
	height: 0px;
	transition: all .6s ease;
}

.progress-uploading {
	margin-top: 2px;
	height: 20px;
}

.input-group-uploader {
	position: relative;
	display: table;
}
</style>
</head>
<title>修改客户信息</title>
<div id="container" class="wrapper wrapper-content animated fadeInRight">
	<div id="breadcrumb">
		<bread-crumb :crumbs="breadcrumbs"></bread-crumb>
	</div>
	<!-- ibox start -->
	<div class="ibox">

		<div class="ibox-content">

			<div class="row">
				<div class="col-sm-11 col-sm-offset-1">
					<validator name="validation" v-model="validation">
					<form novalidate class="form-horizontal">
					
						<!-- 客户编号 -->
						<div class="col-sm-6 m-t-lg">
							<label for="customerNo" class="col-sm-3 control-label">
								客户编号:
							</label>
							<div class="col-sm-8">
								<input v-model="form.customerNo" id="customerNo"
									name="customerNo" class="form-control" readonly="readonly">
							</div>
						</div>
						<div class="col-sm-6 m-t-lg">
							<label for="customerMobile" class="col-sm-3 control-label">
								手机号:
							</label>
							<div class="col-sm-8">
								<input v-model="form.customerMobile" id="customerMobile"
									name="customerMobile" class="form-control" readonly="readonly">
							</div>
						</div>
						
						<!-- 客户姓名 -->
						<div class="col-sm-6 m-t-lg"
							:class="{'has-error':($validation.customerName.invalid && $validation.touched)}">
							<label for="customerName" class="col-sm-3 control-label">
								<font color="red">*</font> 客户名称:
							</label>
							<div class="col-sm-8">
								<input v-validate:customer-name="{  required:true }"
									v-model="form.customerName" id="customerName"
									name="customerName" maxlength="20" class="form-control"
									placeholder="请输入客户名称">
								<div
									v-if="$validation.customerName.invalid && $validation.touched"
									class="help-absolute">
									<span v-if="$validation.customerName.invalid">请输入客户名称</span>
								</div>
							</div>
						</div>
						
						<!-- 备用电话 -->
						<div class="col-sm-6 m-t-lg">
							<label for="reserveMobile" class="col-sm-3 control-label">
								备用电话: 
							</label>
							<div class="col-sm-8">
								<input v-model="form.reserveMobile" id="reserveMobile"
									name="reserveMobile" class="form-control" type="number" maxlength="11">
							</div>
						</div>
						
						<!-- 固定电话 -->
						<div class="col-sm-6 m-t-lg">
							<label for="homePhone" class="col-sm-3 control-label">
								固定电话: 
							</label>
							<div class="col-sm-8">
								<input v-model="form.homePhone" id="homePhone"
									name="homePhone" class="form-control" type="text" maxlength="12">
							</div>
						</div>


						<!-- 取消/保存草稿/提交申请 -->
						<div class="col-sm-12 m-t-lg">
							<div class="col-sm-12" style="text-align: center;">
								<button type="button" @click="closeWin"
									class="btn btn-default m-r-lg">取消</button>
								<button type="button" @click="saveCustomer"
									class="btn btn-primary m-r-lg" :disabled="submitting">提交</button>
							</div>
						</div>
					</form>
					</validator>
				</div>
			</div>
		</div>
	</div>
	<!-- ibox end -->
</div>

<script src="/static/js/containers/customer/editCustomer.js?v=asdfdsf"></script>