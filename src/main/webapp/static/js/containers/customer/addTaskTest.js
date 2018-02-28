 var vueIndex
+(function () {
  $('#customerMenu').addClass('active');
  $('#addTaskSCBTest').addClass('active');
  	vueIndex = new Vue({
	    el: '#container',
	    created: function () {
        },
	    ready: function () {
	    	this.promoteSourceList();
	    	//this.typeList();
	    	this.activeDatepicker();
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
                name: '模拟新增任务',
                active: true // 激活面包屑的
            }],
		      //表格数据
		      form: {
		    	  customerName: '',
		    	  customerMobile: '',
		    	  homePhone: '',
		    	  reserveMobile: '',
		    	  dataSource: '',
		    	  //数据来源的callId
		    	  callId: '',
		    	  houseLayout: '',
		    	  houseLayout1: 0,
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
	    		  newTaskType: ''
		      },
		      //推广来源集合
		      promoteSources:[],
		      submitting: false,
		      province: province,
		      city: city,
		      district: district/*,
		      types: []*/
		      
	    },
	    methods: {
    	  selectChange: function(){
    		  this.form.cityCode = '';
			  this.form.areaCode = '';
    	  },
	      closeWin: function () {
	    	  window.history.go(-1);
	      },
	      //保存!
	      saveTask: function () {
	          var self = this;
	          //校验客户姓名
	          if (!self.form.customerName) {
	              Vue.toastr.error('客户名称不能为空!');
	              return;
	          }
	          if(! (/^1(3|4|5|7|8)\d{9}$/.test(self.form.customerMobile)) ){ 
	              Vue.toastr.error('请输入正确客户的手机号码!');
	              return;
	          }
	          //校验推广来源
	          if (!self.form.promoteSource) {
	              Vue.toastr.error('请选择推广来源!');
	              return;
	          }
	          if (!self.form.callId) {
	              Vue.toastr.error('数据来源不能为空!');
	              return;
	          }
	          self.$validate(true);
	          
	          self.submitting = true;
	          
	          var formData = self.form;
	          var callTime = null;
	          var callAnswered = null;
	          var reqJson = null;
	          if(self.form.callId == 'himm893ereffdjmk'){
	        	  //给calltime
	        	  callTime = RocoUtils.formatDate(new Date(),'yyyy-MM-dd hh:mm:ss');
	        	  callAnswered = 'Y';
	        	  reqJson = '[{"customerName":"' + formData.customerName + '","customerMobile":"'+ formData.customerMobile 
			        	  +'","callTime":"'+ callTime +'",callAnswered:"'+ callAnswered 
			        	  +'","store":"'+ RocoUser.storeCode +'","promoteSource":"'+ formData.promoteSource +'"}]';
	          }else{
	        	  reqJson = '[{"customerName":"' + formData.customerName + '","customerMobile":"'+ formData.customerMobile 
			        	  +'","store":"'+ RocoUser.storeCode +'","promoteSource":"'+ formData.promoteSource +'"}]';
	          }
	          self.$http.post('/open/api/task/newTask', {'reqJson':reqJson, 'callId': formData.callId}, {
	              emulateJSON: true
	          }).then(function (res) {
	                  if (res.data.code == '1') {
	                    Vue.toastr.success(res.data.message);
	                    self.submitting = false;
/*	                    setTimeout(function () {
	                    	
	                    	if(self.form.mechandiser && self.form.mechandiser != ''){
	          	        	  	//选择了跟单员,说明已派发,跳转到已派发页面
	          	        	  	window.location.href = '/admin/task/taskList?distributeStatus=Y&store=' + RocoUser.storeCode
	          	        	  		+ '&dataSource=' + self.form.dataSource;
	          	          	}else{
		          	        	  window.location.href = '/admin/task/taskList?distributeStatus=N&store=' + RocoUser.storeCode
		          					+ '&dataSource=' + self.form.dataSource;
	          	          	}
                    	 }, 1500);
*/	                  } else {
	                    Vue.toastr.error(res.data.message);
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
        	  chooseMechandiser();
	      }
	      
	    }
  });
  
  	//选择跟单员
    function chooseMechandiser() {
       var arr = [{
            radio: true,
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
      /*var getUrl = '/api/employee/listCollection?store=' + RocoUser.storeCode
      				+ '&dataSource=' + vueIndex.form.dataSource;*/
      var getUrl = '/api/employee/listCollection';
      var $modal = $('#modalBrand').clone();
      $modal.modal({
        height: 360,
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
