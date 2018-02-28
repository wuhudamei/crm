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
.col-sm-2{
	padding-right:3px; 
}
.col-sm-1{
	padding:0;

}

</style>
</head>
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
					
						<!-- 客户姓名 -->
						<div class="col-sm-6 m-t-lg"
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
						<div class="col-sm-6 m-t-lg"
							:class="{'has-error':($validation.customerMobile.invalid && $validation.touched)}">
							<label for="customerMobile" class="col-sm-3 control-label">
								<font color="red">*</font> 客户手机号: 
							</label>
							<div class="col-sm-8">
								<input v-validate:customer-Mobile="{  required:true }"
									v-model="form.customerMobile" id="customerMobile"
									name="customerMobile" maxlength="12" minlength="7" class="form-control"
									placeholder="请输入客户手机号">
								<div
									v-if="$validation.customerMobile.invalid && $validation.touched"
									class="help-absolute">
									<span v-if="$validation.customerMobile.invalid">请输入客户手机号</span>
								</div>
							</div>
						</div>
						
						<!-- 推广来源-->
						<div class="col-sm-6 m-t-lg" :class="{'has-error':($validation.promoteSource.invalid && $validation.touched)}">
							<label for="promoteSource" class="col-sm-3 control-label">
								<font color="red">*</font> 推广来源: 
							</label>
							<div class="col-sm-8">
								<select v-model="form.promoteSource" id="promoteSource"
									v-validate:promote-Source="{required:true}"
									name="promoteSource" class="form-control" 
									placeholder="--请选择推广来源--" >
									<option value="">--请选择推广来源--</option>
									<option v-for="promoteSource in promoteSources" :value="promoteSource.code">{{promoteSource.name}}</option>
								</select>
								<div v-if="$validation.promoteSource.invalid && $validation.touched"
		                               class="help-absolute">
		                            <span v-if="$validation.promoteSource.invalid">请选择推广来源</span>
	                            </div>
                          </div>
						</div>
						
						<!-- 任务阶段:从数据字典中加载-->
						<!-- <div class="col-sm-6 m-t-lg" :class="{'has-error':($validation.newTaskType.invalid && $validation.touched)}">
							<label for="newTaskType" class="col-sm-3 control-label">
								<font color="red">*</font> 任务阶段: 
							</label>
							<div class="col-sm-8">
								<select v-model="form.newTaskType" id="newTaskType"
									v-validate:newTaskType="{required:true}"
									name="newTaskType" class="form-control" 
									placeholder="--请选择任务阶段--" >
									<option v-for="type in types" :value="type.code">{{type.name}}</option>
								</select>
								<div v-if="$validation.newTaskType.invalid && $validation.touched"
		                               class="help-absolute">
		                            <span v-if="$validation.newTaskType.invalid">请选择任务阶段</span>
	                            </div>
                          </div>
						</div> -->
						<!-- 任务阶段,写死数据 -->
						<div class="col-sm-6 m-t-lg" :class="{'has-error':($validation.newTaskType.invalid && $validation.touched)}">
							<label for="newTaskType" class="col-sm-3 control-label">
								<font color="red">*</font> 任务阶段: 
							</label>
							<div class="col-sm-8">
								<select v-model="form.newTaskType" id="newTaskType"
									v-validate:newTaskType="{required:true}"
									name="newTaskType" class="form-control" 
									placeholder="--请选择任务阶段--" >
									<option value="RECEPTION" :selected="!form.dataSource">接待</option>
									<option value="INVITATION" >邀约</option>
								</select>
								<div v-if="$validation.newTaskType.invalid && $validation.touched"
		                               class="help-absolute">
		                            <span v-if="$validation.newTaskType.invalid">请选择任务阶段</span>
	                            </div>
                          </div>
						</div>
						
						
						
						<!-- 固定电话 -->
						<div class="col-sm-6 m-t-lg" >
							<label for="homePhone" class="col-sm-3 control-label">
								固定电话: 
							</label>
							<div class="col-sm-8">
									<input v-model="form.homePhone" id="homePhone"
										name="homePhone" class="form-control"
										placeholder="请输入固定电话" maxlength="12" type="text"/>
							</div>
						</div>

						<!-- 选择门店 -->
						<div class="col-sm-6 m-t-lg" :class="{'has-error':($validation.store.invalid && $validation.touched)}">
							<label for="store" class="col-sm-3 control-label">
								<font color="red">*</font> 门店:
							</label>
							<div class="col-sm-8">
								<select v-model="form.store" id="store"
										v-validate:store="{required:true}"
										name="store" class="form-control"
										placeholder="--请选择门店--" >
									<option value="" >--请选择门店--</option>
									<option v-for="store of stores" :value="store.code">{{store.name}}</option>
								</select>
								<div v-if="$validation.store.invalid && $validation.touched"
									 class="help-absolute">
									<span v-if="$validation.store.invalid">请选择门店</span>
								</div>
							</div>
						</div>

						<!-- 跟单员-->
						<div class="col-sm-6 m-t-lg">
							<label for="mechandiser" class="col-sm-3 control-label">
								跟单员: 
							</label>
							<div class="col-sm-8">
								<input id="mechandiserName" v-model="form.mechandiserName" type="text" 
                  					placeholder="--选择跟单员--" class="datepicker form-control" readonly
                  					@click="selectMechandiser"/>
							</div>
						</div>

						<!--市场部和小美返单展示 推荐人和推荐人电话 -->
						<div class="col-sm-6 m-t-lg" v-if="form.dataSource == 'XMFD' || form.dataSource == 'SCB'">
							<label for="introducer" class="col-sm-3 control-label">
								推荐人:
							</label>
							<div class="col-sm-8">
								<input v-model="form.introducer" id="introducer"
									   name="introducer" class="form-control"
									   placeholder="请输入推荐人姓名" maxlength="12" type="text"/>
							</div>
						</div>
						<div class="col-sm-6 m-t-lg" v-if="form.dataSource == 'XMFD' || form.dataSource == 'SCB'">
							<label for="introducerTel" class="col-sm-3 control-label">
								推荐人电话:
							</label>
							<div class="col-sm-8">
								<input v-model="form.introducerTel" id="introducerTel"
									   name="introducerTel" class="form-control"
									   placeholder="请输入推荐人电话" maxlength="11" type="text"/>
							</div>
						</div>


						<!-- 备注-->
						<div class="col-sm-6 m-t-lg">
							<label for="remark" class="col-sm-3 control-label">
								备注:
							</label>
							<div class="col-sm-8">
								<textarea id="remark" v-model="form.remark"
									   placeholder="备注信息" class="datepicker form-control"
									   rows="3" maxlength="1000">
								</textarea>
							</div>
						</div>


				<div style="clear:both;" v-el:my-collapse class="col-sm-12 m-t-lg panel-group" id="accordion" role="tablist" aria-multiselectable="true">
				  <div class="panel panel-default">
				    <div class="panel-heading" role="tab" id="headingOne">
				      <h4 class="panel-title">
				        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
				           <span v-show="!form.showHouseFlag">+</span> 
				           <span v-show="form.showHouseFlag">-</span>
				           	房屋信息
				        </a>
				      </h4>
				    </div>
				    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
				      <div class="panel-body">
						      
				      	<!-- 房屋户型 -->
						<div class="col-sm-6 m-t-lg" 
							 :class="{'has-error':($validation.touched && $validation.houseLayout1.invalid ||
							$validation.houseLayout2.invalid || $validation.houseLayout3.invalid )}">
							<label for="houseLayout" class="col-sm-3 control-label">
								<font color="red">*</font>房屋户型: 
							</label>
							<div class="col-sm-2">
								<input type="number" max="9" min="1" step="1" class="form-control" number
									v-model="form.houseLayout1" id="houseLayout"
									name="houseLayout" class="form-control" 
									v-validate:house-Layout1="{  required:true, min: 1 }">
								<div v-if="$validation.houseLayout1.invalid && $validation.touched"
	                               class="help-absolute">
	                            	<span v-if="$validation.houseLayout1.invalid">数量小于1</span>
                           	 	</div>
							</div>
							<div class="col-sm-1 control-label" style="text-align:left;" >
								&nbsp;室
							</div>
							<div class="col-sm-2">
								<input type="number" max="9" min="0" step="1" number
									v-model="form.houseLayout2" id="houseLayout"
									name="houseLayout" class="form-control" 
									v-validate:house-Layout2="{  required:true, min: 0 }">
								<div v-if="$validation.houseLayout2.invalid && $validation.touched"
                              		 class="help-absolute">
                            		<span v-if="$validation.houseLayout2.invalid">数量小于0</span>
                           	 	</div>
							</div>
							<div class="col-sm-1 control-label" style="text-align:left;" >
								&nbsp;厅
							</div>
							<div class="col-sm-2">
								<input type="number" max="9" min="0" step="1" number
									v-model="form.houseLayout3" id="houseLayout"
									name="houseLayout" class="form-control" 
									v-validate:house-Layout3="{required:true, min: 0}">
								<div v-if="$validation.houseLayout3.invalid && $validation.touched"
                             		 	class="help-absolute">
                           			<span v-if="$validation.houseLayout3.invalid">数量小于0</span>
                           	 	</div>
							</div>
							<div class="col-sm-1 control-label" style="text-align:left;" >
								&nbsp;卫
							</div>
						</div>
						
						<!-- 房屋面积 -->
						<div class="col-sm-6 m-t-lg" 
							:class="{'has-error':($validation.touched && $validation.houseArea.invalid)}" >
							<label for="houseArea" class="col-sm-3 control-label">
								<font color="red">*</font>房屋面积: 
							</label>
							<div class="col-sm-4">
									<input v-model="form.houseArea" id="houseArea" number
										name="houseArea" class="form-control"
										placeholder="输入房屋面积" type="number"  min="0" step="1"
										v-validate:house-Area="{  required:true, min: 0 }">
									<div v-if="$validation.houseArea.invalid && $validation.touched"
                             		 	class="help-absolute">
                           				<span v-if="$validation.houseArea.invalid">请输入房屋面积</span>
	                           	 	</div>
							</div>
							<div class="col-sm-1 control-label" style="text-align:left;" >
								㎡
							</div>
						</div>
	
						<!-- 是否期房 -->
						<div class="col-sm-6 m-t-lg" style="height:34px;" >
							<label for="hoursing" class="col-sm-3 control-label">
								期房: 
							</label>
							<div class="col-sm-1 control-label" >
								<label for="houseType" style="font-weight: normal;">
									<input v-model="form.hoursing" id="hoursing"
										name="hoursing" type="radio" value="1">
									&nbsp;是
								</label>
							</div>
							<div class="col-sm-1 control-label" >
								<label for="houseType" style="font-weight: normal;">
									<input v-model="form.hoursing" id="hoursing"
										name="hoursing" type="radio" value="0" checked="checked">
									&nbsp;否
								</label>
							</div>
						</div>
						
						<!-- 新旧房 -->
						<div class="col-sm-6 m-t-lg"  style="height:34px;">
							<label for="houseType" class="col-sm-3 control-label">
								新旧房: 
							</label>
							<div class="col-sm-1 control-label" >
								<label for="houseType" style="font-weight: normal;">
									<input v-model="form.houseType" id="houseType"
										name="houseType" type="radio" value="1" checked="checked">
									&nbsp;新
								</label>
							</div>
							<div class="col-sm-1 control-label" >
								<label for="houseType" style="font-weight: normal;">
									<input v-model="form.houseType" id="houseType"
										name="houseType" type="radio" value="0">
								&nbsp;旧
								</label>
							</div>
						</div>
						
						<!-- 电梯 -->
						<div class="col-sm-6 m-t-lg"  style="height:34px;">
							<label for="elevator" class="col-sm-3 control-label">
								电梯: 
							</label>
							<div class="col-sm-1 control-label" >
								<label for="houseType" style="font-weight: normal;">
									<input v-model="form.elevator" id="elevator"
										name="elevator" type="radio" value="1" checked="checked">
									&nbsp;有
								</label>
							</div>
							<div class="col-sm-1 control-label" >
								<label for="houseType" style="font-weight: normal;">
									<input v-model="form.elevator" id="elevator"
										name="elevator" type="radio" value="0">
									&nbsp;无
								</label>
							</div>
						</div>
						
						<!-- 装修时间 -->
						<div class="col-sm-6 m-t-lg" >
							<label for="renovationTime" class="col-sm-3 control-label">
								装修时间: 
							</label>
							<div class="col-sm-8">
								<input id="renovationTime" v-model="form.renovationTime" type="text" 
                  					placeholder="--选择装修时间--" class="datepicker form-control" readonly/>
							</div>
						</div>
						
						<!-- 省市县 -->
						<div class="col-sm-9 m-t-lg" 
							 :class="{'has-error':($validation.touched && $validation.provinceCode.invalid ||
							$validation.cityCode.invalid || $validation.areaCode.invalid || $validation.address.invalid)}">
							<label for="address" class="col-sm-2 control-label">
								<font color="red">*</font>房屋地址:&nbsp;&nbsp;&nbsp;&nbsp;
							</label>
							<div class="col-sm-3">	
								<select @change="selectChange" class="form-control provinceCode" name="provinceCode" 
									v-model="form.provinceCode" required 
									v-validate:province-Code="{required:true}">
	                                 <option value="">省份</option>
	                                 <option :value="item.ProID" v-for="item of province" :key="$index">{{item.ProName}}</option>
	                             </select>
                             	<div v-if="$validation.provinceCode.invalid && $validation.touched"
                             		 	class="help-absolute">
                          				<span v-if="$validation.provinceCode.invalid">请输入省份</span>
                           	 	</div>
                             </div>
                             <div class="col-sm-3">	
	                             <select name="cityCode" class="form-control cityCode" 
	                             		v-model="form.cityCode" 
	                             		v-validate:city-Code="{required:true}">
	                                    <option value="">市</option>
	                                    <option :value="item.CityID" v-for="item of city" :key="$index" v-if="item.ProID == form.provinceCode">{{item.CityName}}</option>
	                              </select>
	                              <div v-if="$validation.cityCode.invalid && $validation.touched"
	                             		 	class="help-absolute">
                           				<span v-if="$validation.cityCode.invalid">请输入城市</span>
                           	 	  </div>	
                              </div>
                              <div class="col-sm-3">	
	                              <select name="areaCode" class="form-control areaCode" 
	                              	v-model="form.areaCode" required
	                              	v-validate:area-Code="{required:true}">
	                                  <option value="">区</option>
	                                  <option :value="item.Id" v-for="item of district" :key="$index" v-if="item.CityID === form.cityCode">{{item.DisName}}</option>
	                              </select>
	                              <div v-if="$validation.areaCode.invalid && $validation.touched"
	                             		 	class="help-absolute">
                           				<span v-if="$validation.areaCode.invalid">请输入区县</span>
                           	 	  </div>
                           	  </div>
						</div>
						<!-- 详细地址 -->
						<div class="col-sm-9 m-t-lg" :class="{'has-error':($validation.touched && $validation.provinceCode.invalid ||
							$validation.cityCode.invalid || $validation.areaCode.invalid || $validation.address.invalid)}">
							<label for="address" class="col-sm-2 control-label" >
							</label>
							<div class="col-sm-6">
								<input v-model="form.address" id="address"
									name="address" maxlength="500" class="form-control"
									placeholder="请输入详细地址" type="text"
									v-validate:address="{required:true}"/>
								<div v-if="$validation.address.invalid && $validation.touched"
                             		 	class="help-absolute">
                        			<span v-if="$validation.address.invalid">请输入详细信息</span>
                         	 	</div>
							</div>
						</div>
					</div>
						      
						      </div>
						    </div>
						  
				</div>
						<!-- /.panel-group -->
						
						
						
						
						<!-- 取消/保存草稿/提交申请 -->
						<div class="col-sm-12 m-t-lg">
							<div class="col-sm-10" style="text-align: center;">
								<button type="button" @click="closeWin"
									class="btn btn-default m-r-lg">取消</button>
								<button type="button" @click="saveTask"
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

<!-- 选择跟单员 -->
  <div id="modalBrand" class="modal fade" tabindex="-1" data-width="600" v-cloak>
    <div class="modal-body">
      <div class="row" >
        <form id="searchForm" @submit.prevent="query">
          <div class="col-md-5">
            <div class="form-group">
              <input
                  v-model="form.keyword"
                  type="text"
                  placeholder="员工姓名|手机号" class="form-control" />
            </div>
          </div>
          
          <div class="col-md-2">
            <div class="form-group">
              <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default" alt="搜索"
                      title="搜索">
                <i class="fa fa-search"></i>
              </button>
            </div>
          </div>
        </form>
      </div>
      <table id="dataTableBrand" width="100%" class="table table-striped table-bordered table-hover" />
      </table>
    </div>
    <div class="modal-footer">
      <button type="button" data-dismiss="modal" class="btn">关闭</button>
      <button type="button" @click="choose" class="btn btn-primary">选择</button>
    </div>
  </div>
<script src="/static/js/containers/customer/city.js"></script>
<script src="/static/js/containers/customer/addTask.js?v=asd12"></script>