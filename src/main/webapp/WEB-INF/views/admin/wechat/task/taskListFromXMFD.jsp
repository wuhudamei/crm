<%@ page contentType="text/html;charset=UTF-8"%>
<title>返单记录</title>
<header>

</header>
<link href="${ctx}/static/css/zTreeStyle.css" rel="stylesheet">
<style>
  .list-group{
    margin-bottom: 10px;
    margin-top: 10px;
    border-radius: 10px;
    position: relative;
    padding: 10px 15px;
    background-color: #fff;
    border: 1px solid #ddd;
  }
  .radius{
    border-radius: 10px;
  }
</style>
<div id="container" class="wrapper wrapper-content animated fadeInRight">
  <div class="row" class="list-group">
    <div class="col-xs-2" style="left: 8%"><a href="#" @click="goBack"><h3> < </h3> </a></div>
    <div class="col-xs-10" style="left: 20%"><h3>返单记录</h3></div>
  </div>
  <div class="content">

   <%-- <div class="container-fluid radius search">
      <div class="row">
        <i class="add2"></i>
        <div class="col-xs-11 col-xs-offset-1">
          <div class="input-group">
            <input v-model="keyword" type="text" class="form-control" placeholder="客户姓名/手机号">
            <span class="input-group-btn">
                                <button @click="search" class="btn btn-default" type="button">搜索</button>
                            </span>
          </div><!-- /input-group -->
        </div><!-- /.col-lg-6 -->
      </div>
    </div>--%>

    <div class="container-fluid ">
      <div class="list-group" v-for="taskDistribute in taskDistributeList">
        <div class=" row" >
          <div class="col-xs-4" style="text-align: right">客户姓名：</div><div class="col-xs-8" style="text-align: left">{{taskDistribute.customerName}}</div>
        </div>
        <div class=" row" >
          <div class="col-xs-4" style="text-align: right">手机号：</div><div class="col-xs-8" style="text-align: left">{{taskDistribute.customerMobile}}</div>
        </div>
        <div class="row" >
          <div class="col-xs-4" style="text-align: right">当前状态：</div><div class="col-xs-8" style="text-align: left">{{taskDistribute.currentStatus}}</div>
        </div>
        <div class="row" >
          <div class="col-xs-4" style="text-align: right">上报日期：</div><div class="col-xs-8" style="text-align: left">{{taskDistribute.createTime}}</div>
        </div>
      </div>

    </div>
  </div>
</div>
<script src="/static/js/wx/task/taskListFromXMFD.js"></script>
