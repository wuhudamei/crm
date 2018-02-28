<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>来源管理</title>
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
                           :class="{'has-error':($validation.sourceName.invalid && $validation.touched)}">
                        <label for="sourceName"
                               class="col-sm-3 control-label">来源名称</label>
                        <div class="col-sm-8">
                          <input
                                  v-validate:source-name="{  required:true }"
                                  v-model="dataSource.sourceName"
                                  id="sourceName"
                                  name="sourceName"
                                  maxlength="8"
                                  class="form-control"
                                  placeholder="请输入名称">
                          <div v-if="$validation.sourceName.invalid && $validation.touched"
                               class="help-absolute">
                            <span v-if="$validation.sourceName.invalid">请输入名称</span>
                          </div>
                        </div>
                      </div>
                      <div class="form-group"
                           :class="{'has-error':($validation.sourceCode.invalid && $validation.touched)}">
                        <label for="sourceCode"
                               class="col-sm-3 control-label">代码</label>
                        <div class="col-sm-8">
                          <input
                                  v-validate:source-code="{  required:true }"
                                  v-model="dataSource.sourceCode"
                                  id="sourceCode"
                                  maxlength="8"
                                  name="sourceCode"
                                  class="form-control"
                                  placeholder="请输入代码">
                          <div v-if="$validation.sourceCode.invalid && $validation.touched"
                               class="help-absolute">
                            <span v-if="$validation.sourceCode.invalid">请输入代码</span>
                          </div>
                        </div>
                      </div>
                      <div class="form-group"
                           :class="{'has-error':($validation.remark.invalid && $validation.touched)}">
                        <label for="remark"
                               class="col-sm-3 control-label">备注</label>
                        <div class="col-sm-8">
                          <input
                                  v-model="dataSource.remark"
                                  id="remark"
                                  name="remark"
                                  class="form-control"
                                  placeholder="备注">
                          <div v-if="$validation.remark.invalid && $validation.touched"
                               class="help-absolute">
                            <span v-if="$validation.remark.invalid">备注</span>
                          </div>
                        </div>
                      </div>
                      <div class="form-group"
                           :class="{'has-error':($validation.autoDistribute.invalid && $validation.touched)}">
                        <label for="autoDistribute"
                               class="col-sm-3 control-label">自动派发</label>
                        <div class="col-sm-8">
                          <select
                                  v-validate:auto-distribute="{required:true}"
                                  v-model="dataSource.autoDistribute"
                                  id="autoDistribute"
                                  name="autoDistribute"
                                  data-tabindex="2"
                                  class="form-control">
                            <option value="">请选择是否</option>
                            <option value="Y">是</option>
                            <option value="N">否</option>
                          </select>
                          <div v-if="$validation.autoDistribute.invalid && $validation.touched"
                               class="help-absolute">
                            <span v-if="$validation.autoDistribute.invalid">请选择是否</span>
                          </div>
                        </div>
                      </div>
                      <div class="form-group"
                                   :class="{'has-error':($validation.status.invalid && $validation.touched)}">
                      <label for="status"
                             class="col-sm-3 control-label">状态</label>
                      <div class="col-sm-8">
                        <select
                                v-validate:status="{required:true}"
                                v-model="dataSource.status"
                                id="status"
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

                      <div class="form-group">
                        <div class="col-sm-8 col-sm-offset-3">
                          <button @click="submitClickHandler" :disabled="submitting"
                                  type="button" class="btn btn-danger">提交
                          </button>

                          <button  @click="submitClickBack" type="button" data-dismiss="modal"
                                   class="btn">返回</button>
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

<script src="${ctx}/static/js/containers/dataSource/edit.js"></script>
