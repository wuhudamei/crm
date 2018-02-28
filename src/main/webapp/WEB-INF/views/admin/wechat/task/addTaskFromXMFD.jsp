<%@ page contentType="text/html;charset=UTF-8"%>
<head>
	<title>我要推荐</title>
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
.col-sm-2{
	padding-right:3px; 
}
.col-sm-1{
	padding:0;

}

</style>
</head>
<div id="container" class="wrapper wrapper-content animated fadeInRight">
	<%--<div id="breadcrumb">
		<bread-crumb :crumbs="breadcrumbs"></bread-crumb>
	</div>--%>
		<div class="row" class="list-group">
			<div class="col-xs-2" style="left: 8%"><a href="#" @click="goBack"><h3> < </h3> </a></div>
			<div class="col-xs-10" style="left: 20%"><h3>我要推荐</h3></div>
		</div>
	<!-- ibox start -->
	<div class="ibox">

		<div class="ibox-content">

			<div class="row">
				<div class="col-sm-11 col-xs-12 col-sm-offset-1">
					<validator name="validation" v-model="validation">
					<form novalidate class="form-horizontal">
					
						<!-- 客户姓名 -->
						<div class="col-sm-6 m-t-lg col-xs-12"
							:class="{'has-error':($validation.customerName.invalid && $validation.touched)}">
							<label for="customerName" class="col-sm-3 control-label">
								<font color="red">*</font> 客户姓名:
							</label>
							<div class="col-sm-8">
								<input v-validate:customer-Name="{  required:true }"
									v-model="form.customerName" id="customerName"
									name="customerName" maxlength="20" class="form-control"
									placeholder="请输入客户姓名">
								<div
									v-if="$validation.customerName.invalid && $validation.touched"
									class="help-absolute">
									<span v-if="$validation.customerName.invalid">请输入客户姓名</span>
								</div>
							</div>
						</div>
						<!-- 客户手机号 -->
						<div class="col-sm-6 m-t-lg col-xs-12"
							:class="{'has-error':($validation.customerMobile.invalid && $validation.touched)}">
							<label for="customerMobile" class="col-sm-3 control-label">
								<font color="red">*</font> 客户手机号: 
							</label>
							<div class="col-sm-8">
								<input v-validate:customer-Mobile="{  required:true }"
									v-model="form.customerMobile" id="customerMobile"
									name="customerMobile" maxlength="11" class="form-control"
									placeholder="请输入客户手机号">
								<div
									v-if="$validation.customerMobile.invalid && $validation.touched"
									class="help-absolute">
									<span v-if="$validation.customerMobile.invalid">请输入客户手机号</span>
								</div>
							</div>
						</div>
						


						<!-- 省市县 -->
						<div class="col-sm-9 m-t-lg col-xs-12">
							<label for="address" class="col-sm-2 control-label">
								房屋地址:&nbsp;&nbsp;&nbsp;&nbsp;
							</label>
							<div class="col-sm-3">
								<select @change="selectChange" class="form-control provinceCode" name="provinceCode"
									v-model="form.provinceCode" required >
	                                 <option value="">省份</option>
	                                 <option :value="item.ProID" v-for="item of province" :key="$index">{{item.ProName}}</option>
	                             </select>
                             </div>
                             <div class="col-sm-3">
	                             <select name="cityCode" class="form-control cityCode"
	                             		v-model="form.cityCode" >
	                                    <option value="">市</option>
	                                    <option :value="item.CityID" v-for="item of city" :key="$index" v-if="item.ProID == form.provinceCode">{{item.CityName}}</option>
	                              </select>
                              </div>
                              <div class="col-sm-3">
	                              <select name="areaCode" class="form-control areaCode"
	                              	v-model="form.areaCode" required ">
	                                  <option value="">区</option>
	                                  <option :value="item.Id" v-for="item of district" :key="$index" v-if="item.CityID === form.cityCode">{{item.DisName}}</option>
	                              </select>
                           	  </div>
							<!-- 详细地址 -->
							<div class="col-sm-9 col-sm-offset-2 col-xs-12">
								<input v-model="form.address" id="address"
									   name="address" maxlength="500" class="form-control"
									   placeholder="请输入详细地址" type="text"/>
							</div>
						</div>

						<!--文字提示-->
						<div class="col-sm-9 m-t-lg col-xs-12">
							<p style="color: red">
								返单奖励须知：<br />
								1.公司员工将以上信息填写保存后，将上传给公司营销部。<br />
								2.公司“小美返单”会将信息进行验证，确认推荐信息是否有效。<br />
								3.有效信息“小美"会邀约客户进店，成功进店客户会给推荐员工兑现推荐奖励。<br />
								4.成功进店信息在签约成功，进入施工阶段公司会给予推荐员工返单奖励。<br />
								5.如果推荐人提供的潜在客户在两个月内，仍未与美得你公司签订预定合同，则此潜在客户将视为无效推荐。
							</p>
						</div>


						<!-- 取消/保存草稿/提交申请 -->
						<div class="col-sm-12 m-t-lg">
							<div class="col-sm-10" style="text-align: center;">
								<button type="button" @click="saveTask"
										class="btn btn-primary m-r-lg" :disabled="submitting">提交
								</button>
								<button type="button" @click="goBack"
									class="btn btn-default m-r-lg">取消
								</button>
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
<script src="/static/js/containers/customer/city.js"></script>
<script src="/static/js/wx/task/addTaskFromXMFD.js?v=asd12"></script>