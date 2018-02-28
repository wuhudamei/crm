<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="keywords" content="">
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp"/>
  <title>列表</title>
  <!-- style -->
  <link rel="stylesheet" href="${ctx}/static/crm/bootstrap/css/bootstrap.min.css">
  <style>

  #header {
      position: absolute;
      z-index: 2;
      top: 0;
      left: 0;
      width: 100%;
      height: 45px;
      line-height: 45px;
      background: #f60;
      padding: 0;
      color: #eee;
      font-size: 20px;
      text-align: center;
      font-weight: bold;
  }

  #footer {
      position: absolute;
      z-index: 2;
      bottom: 0;
      left: 0;
      width: 100%;
      height: 48px;
      background: #444;
      padding: 0;
      border-top: 1px solid #444;
      line-height: 48px;
      color: #fff;
      text-align: center;
  }

  #J_Scroll {
      position: absolute;
      z-index: 1;
      top: 45px;
      bottom: 48px;
      left: 0;
      width: 100%;
      overflow: hidden;
  }

  #J_Scroll li {
      width: 100%;
      padding: 0 10px;
      height: 40px;
      line-height: 40px;
      border-bottom: 1px solid #ccc;
      background-color: #fafafa;
  }

  .xs-plugin-pulldown-container {
      text-align: center;
      width: 100%;
      line-height: 50px;
  }

  .xs-plugin-pulldown-up .up {
      display: inline;
  }

  .xs-plugin-pulldown-up .down {
      display: none;
  }

  .xs-plugin-pulldown-down .up {
      display: none;
  }

  .xs-plugin-pulldown-down .down {
      display: inline;
  }

  .xs-plugin-pullup-container {
      line-height: 40px;
      text-align: center;
  }

  .lbl-ctn {
      width: 100%;
      position: absolute;
      -webkit-transform: translateX(0) translateZ(0);
      background: #fff;
      z-index: 1;
  }

  .lbl {
      width: 100%;
      display: inline-block;
      vertical-align: top;
      text-align: center;
      position: absolute;
      background: #fff;
      z-index: 1;
      left: 0;
  }

  .control {
      position: absolute;
      white-space: nowrap;
      width: 40%;
      z-index: 0;
      right: 0;
  }

  .btn {
      display: inline-block;
      color: #fff;
      width: 50%;
      text-align: center;
  }

  .btn-delete {
      background: red;
  }

  .btn-mark {
      background: #ccc;
  }

  .btn-marked {
      background: green;
  }
  </style>
  <script src="${ctx}/static/crm/vue/vue.min.js"></script>
  <script src="${ctx}/static/crm/jquery/jquery-2.2.4.min.js"></script>
  <script src="${ctx}/static/crm/xscroll/xscroll.min.js"></script>
  <script src="${ctx}/static/crm/xscroll/plugins/pulldown.min.js"></script>
</head>
<body>
  <div id="app">
  <div id="header">XScroll</div>
  <div ref="jScroll" id="J_Scroll">
      <div class="xs-container">
          <ul class="xs-content">
              <li v-for="item in items" class="xs-row">
                {{item.data.num}}
              </li>
          </ul>
      </div>
  </div>
  <div id="footer">infinity-scroll</div>
  </div>
  <script>
    new Vue({
      el:'#app',
      data:{
        items:null
      },
      mounted:function(){
        this.$nextTick(function(){
          this.activeXscroll();
        })
      },
      methods:{
        activeXscroll:function(){
          var self = this;
                  var xscroll = new XScroll({
            renderTo:self.$refs.jScroll,
            scrollbarX:false,
            lockX:true,
            lockY:false
        });

        var pulldown = new XScroll.Plugins.PullDown({
            autoRefresh:false,
            content:'下拉刷新',
            downContent: '下拉刷新',
            upContent:'释放刷新',
            loadingContent:'加载中...'
        });

        xscroll.plug(pulldown);

        pulldown.on("loading",function(e){
          $.ajax({
                    url:"/static/crm/data.json",
                    dataType:"json",
                    success:function(data){
                      self.items = data;
                      self.$nextTick(function(){
                        //scrollback to the top
                        pulldown.reset(function(){
                            //repaint
                            xscroll.render()
                        });
                      })
                    }
                })
        })

        xscroll.render();
        }
      }
    })
  </script>
</body>
</html>