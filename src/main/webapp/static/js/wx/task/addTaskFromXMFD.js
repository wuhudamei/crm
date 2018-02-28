 var vueIndex
+(function () {
  //$('#customerMenu').addClass('active');
  //$('#addTask').addClass('active');
  	vueIndex = new Vue({
	    el: '#container',
	    created: function () {
        },
	    ready: function () {
	    	this.initFunction();
	    },
	    data: {
	    	// 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '',
                name: '小美返单'
            }, {
                path: '/',
                name: '	我要推荐',
                active: true // 激活面包屑的
            }],
		      //表格数据
		      form: {
		    	  customerName: '',
		    	  customerMobile: '',
		    	  dataSource: 'XMFD',
	    		  promoteSource: 'XMFD',
                  provinceCode: '',
                  cityCode: '',
                  areaCode: '',
                  provinceName: '',
                  cityName: '',
                  areaName: '',
                  address: '',
                  //推荐人姓名  电话
                  introducer: '',
                  introducerTel: '',
                  introducerId: '',
                  //来源门店
                  store: '',
                  //给是否展开了房屋信息 默认值
                  showHouseFlag: false
		      },
		      submitting: false,
		      province: province,
		      city: city,
		      district: district,
		      //版块对象
		      $collapse: null
		      
	    },
	    methods: {
    	  selectChange: function(){
    		  this.form.cityCode = '';
			  this.form.areaCode = '';
    	  },
	      //初始化获取路径中的数据来源
	      initFunction: function(){
	    	  var self = this;
	    	  //从sessionStorage中 获取推荐人姓名
              self.form.introducer = sessionStorage.getItem("introducer") || '';
              self.form.introducerTel = sessionStorage.getItem("introducerTel") || '';
              self.form.introducerId = sessionStorage.getItem("introducerId") || '';
              self.form.store = sessionStorage.getItem("store") || '';
	      },
	      goBack: function () {
              window.location.href = "/api/web/backToIndex";
	      },
	      //保存!
	      saveTask: function () {
	          var self = this;
	          //校验推荐人及电话
              if (!self.form.introducer || !self.form.introducerTel) {
                  Vue.toastr.error('未获取到推荐人或者推荐人手机号!');
                  return;
              }
	          //校验客户姓名
	          if (!self.form.customerName) {
	              Vue.toastr.error('客户名称不能为空!');
	              return;
	          }
	          if(! (/^1(3|4|5|7|8)\d{9}$/.test(self.form.customerMobile)) ){ 
	              Vue.toastr.error('请输入正确的客户手机号码!');
	              return;
	          }
	          var provinceCode = document.querySelector('.provinceCode');
	          var cityCode = document.querySelector('.cityCode');
	          var areaCode = document.querySelector('.areaCode');
	          this.form.provinceName = provinceCode.options[provinceCode.selectedIndex].text;
	          this.form.cityName = cityCode.options[cityCode.selectedIndex].text;
	          this.form.areaName = areaCode.options[areaCode.selectedIndex].text;
	          self.$validate(true);

	          self.submitting = true;

	          //判断是否填写了 房屋信息
			  if(self.form.provinceCode || self.form.cityCode || self.form.areaCode
			  		|| self.form.address){
                  self.form.showHouseFlag = true;
			  }

			  console.log(self.form);
	          var formData = self.form;

	          self.$http.post('/api/backTask/addTask', formData, {
	              emulateJSON: true
	          }).then(function (res) {
	                  if (res.data.code == '1') {
	                    Vue.toastr.success(res.data.message);
	                    self.submitting = false;
                          setTimeout(function () {
                              if(window.confirm("继续推荐?")){
                                  //清空所有数据,可继续提交
                                  self.form.customerName = "";
                                  self.form.customerMobile = "";
                                  self.form.provinceCode = "";
                                  self.form.cityCode = "";
                                  self.form.areaCode = "";
                                  self.form.provinceName = "";
                                  self.form.cityName = "";
                                  self.form.areaName = "";
                                  self.form.address = "";

                                  self.submitting = false;
                              }else{
                                  window.history.go(-1);
                              }
                          }, 1500);
	                  } else {
	                    //Vue.toastr.error(res.data.message);
	                	  alert(res.data.message);
	                  }
	                });
	      },

	    }
  });

})();
