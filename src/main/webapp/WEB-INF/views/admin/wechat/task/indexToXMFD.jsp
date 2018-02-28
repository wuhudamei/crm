<%@ page contentType="text/html;charset=UTF-8"%>
<title>小美返单</title>
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
  .mySpan{
    position: absolute;
    top: 25%;
    right: 30px;
  }
</style>
<div id="container" class="wrapper wrapper-content animated fadeInRight">
    <div class="row" class="list-group">
      <div class="col-xs-2" style="left: 8%"><a href="#" onclick="window.history.go(-1)"><h3> < </h3> </a></div>
      <div class="col-xs-10" style="left: 20%"><h3>小美返单</h3></div>
    </div>


  <div class="content">

    <div class="container-fluid ">
      <div class="list-group">
        <div class=" row" >
            <div class="col-xs-12">
              <a href="#"  onclick="addTask()">
                <img src="/static/img/add-user.png" alt="" width="8%" height="8%">
                &nbsp;&nbsp;&nbsp;&nbsp;我要推荐
                <span class="mySpan"> > </span>
              </a>
            </div>
          </div>
      </div>
      <div class="list-group">
        <div class=" row" >
            <div class="col-xs-12">
              <a  href="#"  onclick="taskList()">
                <img src="/static/img/user-group.png" alt="" width="10%" height="10%">
                &nbsp;&nbsp;推荐记录
                <span class="mySpan"> > </span>
              </a>
            </div>
        </div>
      </div>

    </div>
  </div>
</div>
<!--隐藏域中存放4个参数-->
<input id="introducer" hidden value="${introducer}"/>
<input id="introducerTel" hidden value="${introducerTel}"/>
<input id="introducerId" hidden value="${introducerId}"/>
<input id="store" hidden value="${store}"/>

<script >
      //定义变量
      var introducer = '';
      var introducerTel = '';
      var introducerId = '';
      var store = '';

      //将数据存入sessionStorage中
      $(function () {
          introducer = sessionStorage.getItem("introducer") || '';
          introducerTel = sessionStorage.getItem("introducerTel") || '';
          introducerId = sessionStorage.getItem("introducerId") || '';
          store = sessionStorage.getItem("store") || '';
          if(!introducer || !introducerTel || !introducerId || !store){
              //有一个为空,就重新设置
              sessionStorage.setItem("introducer",$("#introducer").val());
              sessionStorage.setItem("introducerTel",$("#introducerTel").val());
              sessionStorage.setItem("introducerId",$("#introducerId").val());
              sessionStorage.setItem("store",$("#store").val());
          }
      })
      
      function addTask(){
          window.location.href = "/api/web/addTaskFromXMFD";
      }

      function taskList(){
          window.location.href = "/api/web/taskListFromXMFD";
      }
</script>
