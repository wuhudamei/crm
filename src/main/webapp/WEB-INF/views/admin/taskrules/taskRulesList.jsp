<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
                            <label class="sr-only" for="storeCode"></label> <select
                                v-model="form.storeCode" id="storeCode" name="storeCode"
                                class="form-control">
                            <option  value="">--请选择门店--</option>
                            <option v-for="store in stores" :value="store.code">{{store.name}}</option>
                        </select>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="form-group">
                            <label class="sr-only" for="dataSourceCode"></label> <select
                                v-model="form.dataSourceCode" id="dataSourceCode"
                                name="dataSourceCode" class="form-control">
                            <option  value="">--请选择渠道--</option>
                            <option v-for="dataSource in dataSources"
                                    :value="dataSource.code">{{dataSource.name}}
                            </option>
                        </select>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="form-group">
                            <label class="sr-only" for="promoteCode"></label> <select
                                v-model="form.promoteCode" id="promoteCode"
                                name="promoteCode" class="form-control">
                            <option  value="">--请选择推广来源--</option>
                            <option v-for="promoteCode in promoteCodes"
                                    :value="promoteCode.code">{{promoteCode.name}}
                            </option>
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

                <div class="col-md-2"></div>

                <shiro:hasPermission name="taskRules:add">
                    <div class="col-md-1">
                        <div class="form-group">
                            <button id="add" type="button"
                                    class="btn btn-block btn-primary"
                                    @click="addModal">
                                <i class="fa ">新增</i>
                            </button>
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
<!--新增弹窗-->
<div id="addModal" class="modal fade" tabindex="-1"
     data-width="800" style="top: 30%">
    <div class="ibox-title">
        <h5>添加派发规则</h5>
    </div>

    <div class="ibox-content">
        <div class="row">
            <validator name="validation">
            <form id="addform" class="form-horizontal" v-cloak  novalidate>
                <div class="form-group" :class="{'has-error':$validation.storecode.invalid  && $validation.touched }">
                    <label for="storeCode1" class="col-sm-2 control-label" >所属门店：</label>
                    <div class="col-sm-5" >
                    <select
                        v-model="taskAllowautoRules.storeCode" id="storeCode1" name="storeCode"
                        class="form-control"
                        v-validate:storecode="{ required:{rule:true,message:'请选择门店'} }">
                        <option value="">--请选择门店--</option>
                        <option v-for="store in stores" :value="store.code">{{store.name}}</option>
                    </select>
                        <span v-cloak v-if="$validation.storecode.invalid && $validation.touched" class="help-absolute">
                            <span v-for="error in $validation.storecode.errors">
                              {{error.message}} {{($index !== ($validation.storecode.errors.length -1)) ? ',':''}}
                            </span>
                          </span>
                    </div>
                </div>
                <div class="form-group" :class="{'has-error':($validation.datasourcecode.invalid && $validation.touched)}">
                    <label for="dataSourceCode1" class="col-sm-2 control-label" >数据来源：</label>
                    <div class="col-sm-5" >
                        <select
                                v-model="taskAllowautoRules.dataSourceCode" id="dataSourceCode1" name="dataSourceCode"
                                class="form-control"
                                v-validate:datasourcecode="{ required:{rule:true,message:'请选择渠道'} }">
                            <option  value="">--请选择渠道--</option>
                            <option v-for="dataSource in dataSources"
                                    :value="dataSource.code">{{dataSource.name}}
                            </option>
                        </select>
                        <span v-cloak v-if="$validation.datasourcecode.invalid && $validation.touched" class="help-absolute">
                            <span v-for="error in $validation.datasourcecode.errors">
                              {{error.message}} {{($index !== ($validation.datasourcecode.errors.length -1)) ? ',':''}}
                            </span>
                          </span>
                    </div>
                </div>
                <div class="form-group" :class="{'has-error':($validation.promotecodes.invalid && $validation.touched)}">
                    <label for="promoteCode1" class="col-sm-2 control-label" >推广来源：</label>
                    <div class="col-sm-5" >
                        <select multiple
                                v-model="taskAllowautoRules.promoteCodes" id="promoteCode1" name="promoteCode"
                                class="form-control"
                                v-validate:promotecodes="{ required:{rule:true,message:'请选择推广来源'} }">
                            <option  value="">--请选择推广来源--</option>
                            <option v-for="promoteCode in promoteCodes"
                                    :value="promoteCode.code">{{promoteCode.name}}
                            </option>
                        </select>
                        <span v-cloak v-if="$validation.promotecodes.invalid&& $validation.touched" class="help-absolute">
                            <span v-for="error in $validation.promotecodes.errors">
                              {{error.message}} {{($index !== ($validation.promotecodes.errors.length -1)) ? ',':''}}
                            </span>
                          </span>
                    </div>
                </div>
            </form>
            </validator>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn">关闭</button>
                <button type="button" v-on:click="commitAdd"
                        class="btn btn-primary">确定
                </button>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/static/js/containers/task/jquery.form.js"></script>
<script src="${ctx}/static/js/containers/taskrules/taskRulesList.js"></script>
