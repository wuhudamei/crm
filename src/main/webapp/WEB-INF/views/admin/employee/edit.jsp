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
                                        <div class="form-group">
                                            <label for="empName"
                                                   class="col-sm-3 control-label">名称</label>
                                            <div class="col-sm-8"
                                                 :class="{'has-error':($validation.empname.invalid && $validation.touched)}">
                                                <input
                                                        v-validate:empname="{
                                                      required:{rule:true,message:'请输入姓名'},
                                                      maxlength:{rule:20,message:'姓名最长不能超过20个字符'}
                                                     }"
                                                        type="text"
                                                        maxlength="20"
                                                        v-model="employee.empName"
                                                        id="empName"
                                                        name="empName"
                                                        class="form-control"
                                                        placeholder="请输入名称">
                                                <span v-cloak v-if="$validation.empname.invalid" class="help-absolute">
                            <span v-for="error in $validation.empname.errors">
                              {{error.message}} {{($index !== ($validation.empname.errors.length -1)) ? ',':''}}
                            </span>
                          </span>
                                            </div>
                                        </div>
                                        <div class="form-group"
                                             :class="{'has-error':($validation.mobile.invalid && $validation.touched)}">
                                            <label for="mobile"
                                                   class="col-sm-3 control-label">手机号</label>
                                            <div class="col-sm-8"
                                                 :class="{'has-error':($validation.mobile.invalid && $validation.touched)}">
                                                <input
                                                        v-validate:mobile="{
                                                  required:{rule:true,message:'请输入手机号'},
                                                  tel:{rule:true,message:'请输入正确的手机号'},
                                                 }"
                                                        v-model="employee.mobile"
                                                        maxlength="11"
                                                        id="mobile"
                                                        name="mobile"
                                                        class="form-control"
                                                        placeholder="请输入手机号码">
                                                <span v-cloak v-if="$validation.mobile.invalid && $validation.touched  "
                                                      class="help-absolute">
                    <span v-for="error in $validation.mobile.errors">
                      {{error.message}} {{($index !== ($validation.mobile.errors.length -1)) ? ',':''}}
                    </span>
                  </span>
                                            </div>
                                        </div>
                                        <%--            <div class="form-group"
                                                         :class="{'has-error':($validation.jobNum.invalid && $validation.touched)}">
                                                      <label for="jobNum"
                                                             class="col-sm-3 control-label">员工编号</label>
                                                      <div class="col-sm-8">
                                                        <input
                                                                v-validate:job-num="{  required:true }"
                                                                v-model="employee.jobNum"
                                                                id="jobNum"
                                                                name="jobNum"
                                                                class="form-control"
                                                                placeholder="请输入员工编号">
                                                        <div v-if="$validation.jobNum.invalid && $validation.touched"
                                                             class="help-absolute">
                                                          <span v-if="$validation.jobNum.invalid">请输入员工编号</span>
                                                        </div>
                                                      </div>
                                                    </div>--%>
                                        <div class="form-group"
                                             :class="{'has-error':($validation.autoOrder.invalid && $validation.touched)}">
                                            <label for="autoOrder"
                                                   class="col-sm-3 control-label">自动接单</label>
                                            <div class="col-sm-8">
                                                <select
                                                        v-validate:auto-order="{required:true}"
                                                        v-model="employee.autoOrder"
                                                        id="autoOrder"
                                                        name="autoOrder"
                                                        data-tabindex="2"
                                                        class="form-control">
                                                    <option value="">请选择是否</option>
                                                    <option value="Y">是</option>
                                                    <option value="N">否</option>
                                                </select>
                                                <div v-if="$validation.autoOrder.invalid && $validation.touched"
                                                     class="help-absolute">
                                                    <span v-if="$validation.autoOrder.invalid">请选择是否</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group"
                                             :class="{'has-error':($validation.busythreshold.invalid && $validation.touched)}">
                                            <label for="busyThreshold"
                                                   class="col-sm-3 control-label">忙碌阈值</label>
                                            <div class="col-sm-8">
                                                <input
                                                        v-validate:busythreshold="{  required:true,maxlength:{rule:3,message:'忙碌阈值最多999'}}"
                                                        v-model="employee.busyThreshold"
                                                        id="busyThreshold"
                                                        name="busyThreshold"
                                                        type="number"
                                                        min="1"
                                                        max="1000"
                                                        class="form-control"
                                                        placeholder="请输入忙碌阈值">
                                                <span v-cloak
                                                      v-if="$validation.busythreshold.invalid && $validation.touched "
                                                      class="help-absolute">
                    <span v-for="error in $validation.busythreshold.errors">
                      {{error.message}} {{($index !== ($validation.busythreshold.errors.length -1)) ? ',':''}}
                    </span>
                  </span>
                                            </div>
                                        </div>
                                        <div class="form-group"
                                             :class="{'has-error':($validation.sort.invalid && $validation.touched)}">
                                            <label for="sort"
                                                   class="col-sm-3 control-label">排序</label>
                                            <div class="col-sm-8">
                                                <input
                                                        v-validate:sort="{  required:true}"
                                                        v-model="employee.sort"
                                                        id="sort"
                                                        name="sort"
                                                        type="number"
                                                        min="1"
                                                        class="form-control"
                                                        placeholder="请输入排序">
                                                <span v-cloak v-if="$validation.sort.invalid " class="help-absolute">
                    <span v-for="error in $validation.sort.errors">
                      {{error.message}} {{($index !== ($validation.sort.errors.length -1)) ? ',':''}}
                    </span>
                  </span>
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
                                             :class="{'has-error':($validation.parentName.invalid && $validation.touched)}">
                                            <label for="parentName"
                                                   class="col-sm-3 control-label">上级</label>
                                            <div class="col-sm-8">
                                                <input

                                                        v-model="employee.parentName"
                                                        id="parentName"
                                                        readonly
                                                        @click="showAllList"
                                                        name="parentName"
                                                        class="form-control"
                                                        placeholder="请选择上级">
                                                <div v-if="$validation.parentName.invalid && $validation.touched"
                                                     class="help-absolute">
                                                    <span v-if="$validation.parentName.invalid">请选择上级</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group"
                                             :class="{'has-error':($validation.storeCode.invalid && $validation.touched)}">
                                            <label for="storeCode"
                                                   class="col-sm-3 control-label">门店</label>
                                            <div class="col-sm-8">
                                                <select
                                                        v-validate:store-code="{required:true}"
                                                        v-model="employee.storeCode"
                                                        id="storeCode"
                                                        name="storeCode"
                                                        data-tabindex="2"
                                                        class="form-control">
                                                    <option value="" >请选门店</option>
                                                    <option v-for="store in stores" :value="store.code">{{store.name}}
                                                    </option>
                                                </select>
                                                <div v-if="$validation.storeCode.invalid && $validation.touched"
                                                     class="help-absolute">
                                                    <span v-if="$validation.storeCode.invalid">请选择门店</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-8 col-sm-offset-3">
                                                <button @click="submitClickHandler" :disabled="submitting"
                                                        type="button" class="btn btn-danger">提交
                                                </button>
                                                <button @click="submitClickBack" type="button" data-dismiss="modal"
                                                        class="btn">返回
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

<!--添加的列表-->
<div id="customerModal" class="modal fade" tabindex="-1" data-width="800" style="top:30%">
    <div class="ibox-title">
        <h5>选取上级</h5>
    </div>
    <div class="ibox-content">
        <div class="row">
            <form id="searchEmployee" @submit.prevent="queryEmployee">
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="sr-only" for="keywords"></label>
                        <input
                                v-model="form.keyword"
                                id="keywords"
                                name="keywords"
                                type="text"
                                placeholder="姓名/手机号" class="form-control"/>
                    </div>
                </div>
                <div class="col-md-1">
                    <div class="form-group">
                        <button id="searchEmployeeBtn" type="submit" class="btn btn-block btn-outline btn-default"
                                alt="搜索"
                                title="搜索">
                            <i class="fa fa-search"></i>
                        </button>
                    </div>
                </div>
            </form>
            <table id="customerTable" width="100%" class="table table-striped table-bordered table-hover">
            </table>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn">关闭</button>
                <button type="button" @click="commitCustomer" class="btn btn-primary">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- container end-->
<script src="${ctx}/static/js/containers/employee/edit.js"></script>
