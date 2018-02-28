<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>客服管理</title>
<!-- 面包屑 -->
<div id="container" class="wrapper wrapper-content">
  <div id="breadcrumb">
    <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
  </div>
  <!-- ibox start -->
  <div class="ibox">
    <div class="ibox-content">
      <div class="row">
        <div class="col-sm-12">
          <div class="ibox">
              <div class="row">
                <div class="col-sm-8 col-sm-offset-2">
                  <validator name="validation">
                    <form v-cloak name="cusEdit" novalidate class="form-horizontal">
                      <div class="form-group"
                           :class="{'has-error':($validation.empName.invalid && $validation.touched)}">
                        <label for="empName"
                               class="col-sm-3 control-label">名称</label>
                        <div class="col-sm-8">
                          <input
                                  v-validate:emp-name="{  required:true }"
                                  v-model="employee.empName"
                                  readonly
                                  @click="showAllList"
                                  id="empName"
                                  name="empName"
                                  class="form-control"
                                  placeholder="请输入名称">
                          <div v-if="$validation.empName.invalid && $validation.touched"
                               class="help-absolute">
                            <span v-if="$validation.empName.invalid">请输入名称</span>
                          </div>
                        </div>
                      </div>
                      <div class="form-group"
                           :class="{'has-error':($validation.mobile.invalid && $validation.touched)}">
                        <label for="mobile"
                               class="col-sm-3 control-label">手机号</label>
                        <div class="col-sm-8">
                          <input
                                  v-validate:mobile="{  required:true }"
                                  v-model="employee.mobile"
                                  id="mobile"
                                  name="mobile"
                                  readonly
                                  @click="showAllList"
                                  class="form-control"
                                  placeholder="请输入手机号码">
                          <div v-if="$validation.mobile.invalid && $validation.touched"
                               class="help-absolute">
                            <span v-if="$validation.mobile.invalid">请输入手机号码</span>
                          </div>
                        </div>
                      </div>
                      <div class="form-group"
                           :class="{'has-error':($validation.jobNum.invalid && $validation.touched)}">
                        <label for="jobNum"
                               class="col-sm-3 control-label">员工编号</label>
                        <div class="col-sm-8">
                          <input
                                  v-validate:job-num="{  required:true }"
                                  v-model="employee.jobNum"
                                  id="jobNum"
                                  name="jobNum" readonly
                                  @click="showAllList"
                                  readonly
                                  @click="showAllList"
                                  class="form-control"
                                  placeholder="请输入员工编号">
                          <div v-if="$validation.jobNum.invalid && $validation.touched"
                               class="help-absolute">
                            <span v-if="$validation.jobNum.invalid">请输入员工编号</span>
                          </div>
                        </div>
                      </div>
                      <div class="form-group"
                           :class="{'has-error':($validation.aotuOrder.invalid && $validation.touched)}">
                        <label for="aotuOrder"
                               class="col-sm-3 control-label">自动接单</label>
                        <div class="col-sm-8">
                          <select
                                  v-validate:aotu-order="{required:true}"
                                  v-model="employee.aotuOrder"
                                  id="aotuOrder"
                                  disabled="true"
                                  name="aotuOrder"
                                  data-tabindex="2"
                                  class="form-control">
                            <option value="">请选择是否</option>
                            <option value="Y">是</option>
                            <option value="N">否</option>
                          </select>
                          <div v-if="$validation.aotuOrder.invalid && $validation.touched"
                               class="help-absolute">
                            <span v-if="$validation.aotuOrder.invalid">请输入是否</span>
                          </div>
                        </div>
                      </div>
                      <div class="form-group"
                           :class="{'has-error':($validation.busyThreshold.invalid && $validation.touched)}">
                        <label for="busyThreshold"
                               class="col-sm-3 control-label">忙碌阈值</label>
                        <div class="col-sm-8">
                          <input
                                  v-validate:busy-threshold="{  required:true }"
                                  v-model="employee.busyThreshold"
                                  id="busyThreshold"
                                  readonly
                                  @click="showAllList"
                                  name="busyThreshold"
                                  class="form-control"
                                  placeholder="请输入忙碌阈值">
                          <div v-if="$validation.busyThreshold.invalid && $validation.touched"
                               class="help-absolute">
                            <span v-if="$validation.busyThreshold.invalid">请输入忙碌阈值</span>
                          </div>
                        </div>
                      </div>
                      <div class="form-group"
                           :class="{'has-error':($validation.position.invalid && $validation.touched)}">
                        <label for="position"
                               class="col-sm-3 control-label">岗位</label>
                        <div class="col-sm-8">
                          <input
                                  v-validate:position="{  required:true }"
                                  v-model="employee.position"
                                  id="position"
                                  readonly
                                  @click="showAllList"
                                  name="position"
                                  class="form-control"
                                  placeholder="请输入岗位">
                          <div v-if="$validation.position.invalid && $validation.touched"
                               class="help-absolute">
                            <span v-if="$validation.position.invalid">请输入岗位</span>
                          </div>
                        </div>
                      </div>
                      <div class="form-group"
                           :class="{'has-error':($validation.parentId.invalid && $validation.touched)}">
                        <label for="parentId"
                               class="col-sm-3 control-label">上级</label>
                        <div class="col-sm-8">
                          <input
                                  v-validate:parent-id="{  required:true }"
                                  v-model="employee.parentId"
                                  id="parentId"
                                  name="parentId"
                                  readonly
                                  @click="showAllList"
                                  class="form-control"
                                  placeholder="请输入上级">
                          <div v-if="$validation.parentId.invalid && $validation.touched"
                               class="help-absolute">
                            <span v-if="$validation.parentId.invalid">请输入上级</span>
                          </div>
                        </div>
                      </div>
                      <div class="form-group"
                           :class="{'has-error':($validation.parentId.invalid && $validation.touched)}">
                        <label for="status"
                               class="col-sm-3 control-label">状态</label>
                        <div class="col-sm-8">
                          <select
                                  v-validate:status="{required:true}"
                                  v-model="employee.status"
                                  id="status"
                                  disabled="true"
                                  @click="showAllList"
                                  name="status"
                                  data-tabindex="2"
                                  class="form-control">
                            <option value="">请选择是否</option>
                            <option value="0">禁用</option>
                            <option value="1">启用</option>
                          </select>
                          <div v-if="$validation.status.invalid && $validation.touched"
                               class="help-absolute">
                            <span v-if="$validation.status.invalid">请选择状态</span>
                          </div>
                        </div>
                      </div>
                      <div class="form-group"
                           :class="{'has-error':($validation.storeCode.invalid && $validation.touched)}">
                        <label for="storeCode"
                               class="col-sm-3 control-label">门店</label>
                        <div class="col-sm-8">
                          <select
                                  v-validate:org-id="{required:true}"
                                  v-model="employee.storeCode"
                                  id="storeCode"
                                  disabled="true"
                                  @click="showAllList"
                                  name="storeCode"
                                  data-tabindex="2"
                                  class="form-control">
                            <option value="">请选门店</option>
                            <option value="2">北京</option>
                            <option value="3">杭州</option>
                          </select>
                          <div v-if="$validation.status.invalid && $validation.touched"
                               class="help-absolute">
                            <span v-if="$validation.status.invalid">请选择门店</span>
                          </div>
                        </div>
                      </div>
                      <div class="form-group">
                        <div class="col-sm-8 col-sm-offset-3">
                          <button @click="submitClickHandler" :disabled="submitting"
                                  type="button" class="btn btn-danger">提交
                          </button>
                        </div>
                      </div>
                    </form>
                  </validator>
                </div>
              </div>
            </div>


          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- ibox end -->
</div>
<!-- container end-->
<!--添加的列表-->
<div id="customerModal" class="modal fade" tabindex="-1" data-width="800" >
  <div class="ibox-title">
    <h5>选取上级</h5>
  </div>
  <div class="ibox-content">
    <div class="row">

  <table id="customerTable" width="100%" class="table table-striped table-bordered table-hover">
    </table>
  <div class="modal-footer">
    <button type="button" data-dismiss="modal" class="btn">关闭</button>
    <button type="button" @click="commitCustomer" class="btn btn-primary">确定</button>
  </div>
    </div>
  </div>
</div>
<script src="${ctx}/static/js/containers/employee/add.js"></script>
