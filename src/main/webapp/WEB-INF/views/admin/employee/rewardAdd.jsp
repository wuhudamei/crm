<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>奖励添加</title>
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
                           :class="{'has-error':($validation.rewardOrderNum.invalid && $validation.touched)}">
                        <label for="rewardOrderNum"
                               class="col-sm-3 control-label">奖励单数</label>
                        <div class="col-sm-8">
                          <input
                                  v-validate:reward-order-num="{  required:true }"
                                  v-model="employeeOrderReward.rewardOrderNum"
                                  type="number"
                                  max="999"
                                  min="1"
                                  id="rewardOrderNum"
                                  name="rewardOrderNum"
                                  class="form-control"
                                  placeholder="请输入奖励单数">
                          <div v-if="$validation.rewardOrderNum.invalid && $validation.touched"
                               class="help-absolute">
                            <span v-if="$validation.rewardOrderNum.invalid">请输入奖励单数</span>
                          </div>
                        </div>
                      </div>
                      <div class="form-group"
                           :class="{'has-error':($validation.rewardDate.invalid && $validation.touched)}">
                        <label for="rewardDate"
                               class="col-sm-3 control-label">奖励时间</label>
                        <div class="col-sm-8">
                          <input
                                  v-validate:reward-date="{  required:true }"
                                  v-model="employeeOrderReward.rewardDate"
                                  id="rewardDate"
                                  name="rewardDate"
                                  class="datepicker form-control"
                                  readonly
                                  type="text"
                                  placeholder="请选择奖励时间">
                          <div v-if="$validation.rewardDate.invalid && $validation.touched"
                               class="help-absolute">
                            <span v-if="$validation.rewardDate.invalid">请选择奖励时间</span>
                          </div>
                        </div>
                      </div>
                      <div class="form-group">
                        <div class="col-sm-8 col-sm-offset-3">
                          <button @click="submitClickHandler" :disabled="submitting"
                                  type="button" class="btn btn-primary">提交
                          </button>

                          <button @click="back"
                                  type="button" class="btn btn-primary">返回
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
<script src="${ctx}/static/js/containers/employee/rewardAdd.js"></script>
