 var vueIndex
+(function () {
  $('#customerMenu').addClass('active');
  //$('#addTask').addClass('active');
  	vueIndex = new Vue({
	    el: '#container',
	    created: function () {
        },
	    ready: function () {
	    	this.activeDatepicker();
	    	this.initFunction();
	    	this.promoteSourceList();
	    	//this.typeList();
	    	this.activeCollapse();
	    	//获取当前用户所拥有的数据权限
			this.loadStoreAuthority();
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
                name: '新增任务',
                active: true // 激活面包屑的
            }],
		      //表格数据
		      form: {
		    	  customerName: '',
		    	  customerMobile: '',
		    	  homePhone: '',
		    	  reserveMobile: '',
		    	  dataSource: '',
		    	  houseLayout: '',
		    	  houseLayout1: 1,
		    	  houseLayout2: 0,
		    	  houseLayout3: 0,
	    		  houseArea: null,
	    		  hoursing: '',
	    		  houseType: '',
	    		  elevator: '',
	    		  renovationTime: '',
	    		  address: '',
	    		  //跟单员
	    		  mechandiser: '',
	    		  //跟单员姓名
	    		  mechandiserName: '',
	    		  promoteSource: '',
	    		  taskTag: '',
	    		  taskLevel: '',
	    		  provinceCode: '',
	    		  cityCode: '',
	    		  areaCode: '',
	    		  provinceName: '',
	    		  cityName: '',
	    		  areaName: '',
	    		  //任务阶段
	    		  newTaskType: '',
    			  //展开/隐藏房屋信息标记
    			  showHouseFlag: false,
				  //门店
                  store: '',
				  //推荐人及电话
                  introducer: '',
                  introducerTel: ''
		      },
		      //推广来源集合
		      promoteSources:[],
		      submitting: false,
		      province: province,
		      city: city,
		      district: district/*,
		      types: []*/,
		      //版块对象
		      $collapse: null,
              //当前用户的数据权限集合
              stores: ''
		      
	    },
	    methods: {
    	  selectChange: function(){
    		  this.form.cityCode = '';
			  this.form.areaCode = '';
    	  },
	      //初始化获取路径中的数据来源
	      initFunction: function(){
	    	  var self = this;
	    	  var href = window.location.href;
	    	  if(href.indexOf("dataSource=") != -1){
	    		  self.form.dataSource = RocoUtils.parseQueryString()['dataSource'];
	    				//初始化面包屑
				if(self.form.dataSource == 'MDQT'){
					$('#addTaskMDQT').addClass('active');
					//如果是前台,就默认选中接待
                   self.form.newTaskType = "RECEPTION";
				}else if(self.form.dataSource == 'SCB'){
                    $('#addTaskSCB').addClass('active');
                    //其他给邀约
                    self.form.newTaskType = "INVITATION";
                }else if(self.form.dataSource == 'HWZX'){
					$('#addTaskHWZX').addClass('active');
                    self.form.newTaskType = "INVITATION";
				}else if(self.form.dataSource == 'XMT'){
					$('#addTaskXMT').addClass('active');
                    self.form.newTaskType = "INVITATION";
				}else if(self.form.dataSource == 'XMFD'){
                    $('#addTaskXMFD').addClass('active');
                    self.form.newTaskType = "INVITATION";
                }
    		 }
	    	  //默认闭合房屋信息版块
	    	  $('#collapseOne').collapse('hide');
	      },
		  //获取当前用户所拥有的数据权限
		  loadStoreAuthority: function () {
			var self = this;
              self.$http.post('/api/dataPermission/empDataPermission', {permissionType:"MD",
				  		jobNum:RocoUser.account},{emulateJSON: true}).then(function (res) {
                  if (res.data.code == 1) {
                      self.stores = res.data.data;
                      if(self.stores){
						  if(self.stores.length == 1){
                            //只有一个时,默认给选中
							self.form.store = self.stores[0].code;
                        }
					  }
                  }
              });
		  },
	      activeCollapse: function(){
	          var self = this;
	          self.$collapse = $(this.$els.myCollapse).collapse();
	          self.$collapse.on('hidden.bs.collapse',function(){
	        	  self.form.showHouseFlag = false;	
	          });
	          self.$collapse.on('shown.bs.collapse',function(){
	        	  self.form.showHouseFlag = true;
	          });
	      },	
	      closeWin: function () {
	    	  window.history.go(-1);
	      },
	      //保存!
	      saveTask: function () {
	          var self = this;
	          if (!self.form.dataSource) {
	              Vue.toastr.error('请求参数异常,请重新点击链接尝试!');
	              return;
	          }
	          //校验客户姓名
	          if (!self.form.customerName) {
	              Vue.toastr.error('客户名称不能为空!');
	              return;
	          }
              //if(! (/^1(3|4|5|7|8)\d{9}$/.test(self.form.customerMobile)) ){
			  if(self.form.customerMobile){
	          	if(self.form.customerMobile.length == 11 ){
	          		if(self.form.customerMobile.indexOf("-") == -1){
						if(!(/^1(3|4|5|7|8|9)\d{9}$/.test(self.form.customerMobile))){
                            Vue.toastr.error('请输入正确的客户手机号码!');
							return
						}
					}else{
	          			if(!(/^([0-9]{3,4}-)?[0-9]{7,8}$/.test(self.form.customerMobile))){
                            Vue.toastr.error('请输入正确的客户固定电话号码!');
                            return
						}
					}
				}else{
                    if(!(/^([0-9]{3,4}-)?[0-9]{7,8}$/.test(self.form.customerMobile))){
                        Vue.toastr.error('请输入正确的客户固定电话号码!');
                        return
                    }
				}
			  }
	          //校验推广来源
	          if (!self.form.promoteSource) {
	              Vue.toastr.error('请选择推广来源!');
	              return;
	          }
	          //如果选择的是接待RECEPTION,就必须选择跟单员
	          if (self.form.newTaskType == 'RECEPTION' && !self.form.mechandiser ) {
	              Vue.toastr.error('任务阶段选择"接待"时,必须选择跟单员!');
	              return;
	          }
	          //校验固定电话
	          if (self.form.homePhone && !(/^([0-9]{3,4}-)?[0-9]{7,8}$/.test(self.form.homePhone))) {
	              Vue.toastr.error('请输入正确的固定电话号码!');
	              return;
	          }
              //校验推广来源
              if (!self.form.store) {
                  Vue.toastr.error('请选择门店!');
                  return;
              }
	          var provinceCode = document.querySelector('.provinceCode');
	          var cityCode = document.querySelector('.cityCode');
	          var areaCode = document.querySelector('.areaCode');
	          this.form.provinceName = provinceCode.options[provinceCode.selectedIndex].text;
	          this.form.cityName = cityCode.options[cityCode.selectedIndex].text;
	          this.form.areaName = areaCode.options[areaCode.selectedIndex].text;
	          self.$validate(true);
	          
	          //如果打开了房屋信息模块,就进行数据校验
	          if(self.form.showHouseFlag){
	        	  if(self.form.houseLayout1 <= 0){
	        		  Vue.toastr.error('房屋户型中,卧室数量不能为空,且必须大于零!');
	        		  return;
	        	  } 
	        	  if(self.form.houseLayout2 < 0){
	        		  Vue.toastr.error('房屋户型中,客厅数量不能为空,且必须大于等于零!');
	        		  return;
	        	  } 
	        	  if(self.form.houseLayout3 < 0){
	        		  Vue.toastr.error('房屋户型中,卫生间数量不能为空,且必须大于等于零!');
	        		  return;
	        	  } 
	        	  if(self.form.houseArea <= 0){
	        		  Vue.toastr.error('房屋面积不能为空,且必须大于零!');
	        		  return;
	        	  } 
	        	  if(!self.form.provinceCode){
	        		  Vue.toastr.error('房屋地址中,省份不能为空!');
	        		  return;
	        	  } 
	        	  if(!self.form.cityCode){
	        		  Vue.toastr.error('房屋地址中,城市不能为空!');
	        		  return;
	        	  } 
	        	  if(!self.form.areaCode){
	        		  Vue.toastr.error('房屋地址中,区县不能为空!');
	        		  return;
	        	  } 
	        	  if(!self.form.address){
	        		  Vue.toastr.error('房屋地址中,详细地址不能为空!');
	        		  return;
	        	  } 
	          }
	          
	          self.submitting = true;
	          
	          self.form.houseLayout = self.form.houseLayout1 + "室" + self.form.houseLayout2 + "厅" 
	          							+ self.form.houseLayout3 + "卫";
	          var formData = self.form;
	          self.$http.post('/api/backTask/addTask', formData, {
	              emulateJSON: true
	          }).then(function (res) {
	                  if (res.data.code == '1') {
	                    Vue.toastr.success(res.data.message);
	                    self.submitting = false;
	                    setTimeout(function () {
	                    	
	                    	if(self.form.mechandiser && self.form.mechandiser != ''){
	          	        	  //选择了跟单员,说明已派发,跳转到已派发页面
	          	        	  window.location.href = '/admin/task/taskList?distributeStatus=Y&store=' + RocoUser.storeCode
	          					+ '&dataSource=' + self.form.dataSource;
	          	          	}else{
	          	        	  window.location.href = '/admin/task/taskList?distributeStatus=N&store=' + RocoUser.storeCode
	          					+ '&dataSource=' + self.form.dataSource;
	          	          	}
                    	 }, 1500);
	                  } else {
	                    //Vue.toastr.error(res.data.message);
	                	  alert(res.data.message);
	                	  window.location.href = '/api/customer/customerList';
	                  }
	                });
	      },
	      
          //获取推广来源数据
	      promoteSourceList : function(){
              var self=this;
              self.$http.get('/api/dict/findSubDictListByCode?code=TGLY').then(function (res) {
                  if (res.data.code == 1) {
                      self.promoteSources = res.data.data;
                  } else {
                  }
              }).catch(function () {

              }).finally(function () {

              });
          }/*,
          //获取任务阶段数据
	      typeList : function(){
              var self=this;
              self.$http.get('/api/dict/findSubDictListByCode?code=XRWZT').then(function (res) {
                  if (res.data.code == 1) {
                      self.types = res.data.data;
                      if(res.data.data.length > 0 ){
                      	//self.form.newTaskType = res.data.data[0].code;
                    	  //默认显示:邀约
                    	  self.form.newTaskType = 'XRWYY';
                      }
                  } else {
                  }
              }).catch(function () {

              }).finally(function () {

              });
          }*/,
          // 日历初始化
          activeDatepicker: function () {
            var self = this;
            $('#renovationTime', self._$el).datetimepicker({});
          },
          
          //选择跟单员
          selectMechandiser : function () {
          	var self = this;
          	if(!self.form.store){
                Vue.toastr.error('请先选择门店');
                return;
			}
        	  chooseMechandiser(self.form.store);
	      }
	      
	    }
  });
  
  	//选择跟单员
    function chooseMechandiser(store) {
       var arr = [{
            checkbox: true,
            align: 'center',
            width: '5%'
          },
          {
  	          field: 'orgCode',
  	          title: '编号',
  	          width: '10%',
  	          orderable: false
  	        },
           {
               field: 'jobNum',
               title: '唯一编号',
			   visible:false
           },
           {
  	          field: 'empName',
  	          title: '客服姓名',
  	          width: '10%',
  	          orderable: false
  	        },
              {
                field: 'mobile',
                title: '手机号',
                width: '10%',
                orderable: false
              }
        ];
      var getUrl = '/api/employee/listCollection?store=' + store
      				+ '&dataSource=' + vueIndex.form.dataSource;
      //var getUrl = '/api/employee/listCollection';
      var $modal = $('#modalBrand').clone();
      $modal.modal({
        height: 410,
        maxHeight: 500
      });
      $modal.on('shown.bs.modal',
        function () {
          var vueModal2 = new Vue({
            el: $modal.get(0),
            mixins: [RocoVueMixins.DataTableMixin],
            data: {
              form: {
                keyword: '',
              },
              $dataTable: null,
              selectedRows: {},
              //选中列
              modalModel: null,
              //模式窗体模型
              _$el: null,
              //自己的 el $对象
            },
            created: function () {},
            ready: function () {
            	this.drawTable();
            },
            methods: {
          	//确认选择
      	    choose: function () {
      	        if (this.selectedRows.jobNum != undefined) {
      	        	vueIndex.form.mechandiser = this.selectedRows.jobNum;
      	        	vueIndex.form.mechandiserName = this.selectedRows.empName;
      	        }else{
      	        	vueIndex.form.mechandiser = '';
      	        	vueIndex.form.mechandiserName = '';
      	        }
      	        $modal.modal('hide');
      	    },
              query: function () {
            	  this.$dataTable.bootstrapTable('selectPage', 1);
              },
              drawTable: function () {
                var self = this;
                self.$dataTable = $('#dataTableBrand', self._$el).bootstrapTable({
                  url: getUrl,
                  method: 'get',
                  dataType: 'json',
                  cache: false,
                  //去缓存
                  pagination: true,
                  //是否分页
                  sidePagination: 'server',
                  singleSelect: true,
                  pageSize:5,
                  //服务端分页
                  queryParams: function (params) {
                	  // 将table 参数与搜索表单参数合并
                	  return _.extend({},params, self.form);
                  },
                  mobileResponsive: true,
                  undefinedText: '-',
                  //空数据的默认显示字符
                  striped: true,
                  //斑马线
                  maintainSelected: true,
                  //维护checkbox选项
                  sortOrder: 'desc',
                  //默认排序方式
                  columns: arr,
                  onLoadSuccess:function(rows,total){
                	  var array = []
                	  if(vueIndex.form.mechandiser && vueIndex.form.mechandiser != ''){
                		  array.push(vueIndex.form.mechandiser) 
                	  }
                	  self.$dataTable.bootstrapTable('checkBy', {
                		  //通过jobNum去比对选中
                          field: 'jobNum',
                          values: array
                        });
                  }
                  
                });
                self.checkEventHandler();
              },
              
              // 点击单选框 事件
              checkEventHandler: function() {
                var self = this;
                if (self.$dataTable) {
                  // 单选选中
                  self.$dataTable.on('check.bs.table', function(e, row, $element) {
                    self.selectedRows.jobNum = row.jobNum;
                    self.selectedRows.empName = row.empName;
                    self.selectedRows = _.assign({}, self.selectedRows);
                  });

                  // 单选取消
                  self.$dataTable.on('uncheck.bs.table', function(e, row, $element) {
                	  self.selectedRows.jobNum = undefined;
                      self.selectedRows.empName = undefined;
                    self.selectedRows = _.assign({}, self.selectedRows);
                  });

                }
              }
              
              
            }
          });
        });
    }
})();
