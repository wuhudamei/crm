var createOrEditProductVue 
+(function () {
  $('#customerMenu').addClass('active');
  //$('#supplierAdd').addClass('active');
  //编辑时的对象
  var model = {};
  var vueIndex = new Vue({
	  created: function () {
	        //通过id查找
	        this.getCustomer();
	      },
      methods: {
    	//获取要编辑的customerNo并且通过customerNo去查找对象
    	  getCustomer:function(){
              var self = this;
              var link = location.href;
              if(link.indexOf("customerNo=") != -1){
              	//需要编辑
              	var customerNo = RocoUtils.getQueryString("customerNo");
              	self.$http.get('/api/customer/getCustomerByCustNoPc?customerNo=' 
              			+ customerNo).then(function (res) {
  	              if (res.data.code == 1) {
  	                  model = res.data.data;
  	                  toEditModal(model);
  	              }
  	            });
              }else{
            	  Vue.toastr.error("客户编号不能为空!");
              }
              
            }
      }
  });
  
  function toEditModal(model){
	  createOrEditProductVue = new Vue({
	    el: '#container',
	    created: function () {
	    	//记录列表的搜索记录
        	this.recordParam();
        },
	    ready: function () {
	    },
	    computed:{
	    },
	    data: {
	    	// 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '',
                name: '客户管理'
            }, {
                path: '/',
                name: '修改客户信息',
                active: true // 激活面包屑的
            }],
		      //表格数据
		      form: {
		    	  customerName: model.customerName,
		    	  customerNo: model.customerNo,
		    	  customerMobile: model.customerMobile,
		    	  homePhone: model.homePhone,
		    	  reserveMobile: model.reserveMobile
		      },
		      submitting: false,
		      //返回参数
		      param: null
	    },
	    methods: {
	    
	      //返回列表页面
	      closeWin : function () {
          	var self = this;
          	window.location.href = "/api/customer/customerList?" + self.param;
          },
	      //保存!
	      saveCustomer: function () {
	          var self = this;
	          //校验客户姓名
	          if (!self.form.customerName) {
	              Vue.toastr.error('客户名称不能为空!');
	              return;
	          }
	          //校验备用电话格式
	          if (self.form.reserveMobile && !(/^1(3|4|5|7|8)\d{9}$/.test(self.form.reserveMobile))) {
	              Vue.toastr.error('请输入正确的备用手机号码!');
	              return;
	          }
	          //校验固定电话
	          if (self.form.homePhone && !(/^([0-9]{3,4}-)?[0-9]{7,8}$/.test(self.form.homePhone))) {
	              Vue.toastr.error('请输入正确的固定电话号码!');
	              return;
	          }
	          self.submitting = true;
	         // self.$validate(true);
	          var formData = {
	        		  customerNo: model.customerNo,
	        		  customerName : self.form.customerName,
	        		  reserveMobile : self.form.reserveMobile,
	        		  homePhone : self.form.homePhone,
	          }
	          
	          self.$http.post('/api/customer/updateCustomer', formData).then(function (res) {
	                  if (res.data.code == '1') {
	                    Vue.toastr.success(res.data.message);
	                    setTimeout(function () {
	                        self.closeWin();
	                      }, 1500);
	                  } else {
	                    Vue.toastr.error(res.data.message);
	                  }
	                })
	      },
	      
	      //记录列表的搜索记录
          recordParam:function () {
          	 var self = this;
          	 var param = window.location.href.split("?")[1];
          	 self.param = param;
          }
	        
	    }
  });
  
  }
})();
