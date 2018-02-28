var vueIndex = null;
+(function (RocoUtils) {
	$('#customerMenu').addClass('active');
    $('#customer').addClass('active');
    vueIndex = new Vue({
        el: '#container',
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
                name: '客户库',
                active: true // 激活面包屑的
            }],
            $dataTable: null,
            form: {
            	keyword: '',
            	store: '',
            	dataSource: ''
            },
            stores: [],
            dataSources: []
        },
        methods: {
            query: function () {
            	this.$dataTable.bootstrapTable('selectPage', 1);
            	this.$dataTable.bootstrapTable('refresh');
            },
            drawTable: function () {
                var self = this;
                //加载返回来的,之前的搜索记录
//                
                
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/customer/list',
                    method: 'get',
                    dataType: 'json',
                    cache: false, // 去缓存
                    pagination: true, // 是否分页
                    sidePagination: 'server', // 服务端分页
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', // 空数据的默认显示字符
                    striped: true, // 斑马线
                    maintainSelected: true, // 维护checkbox选项
                    sortName: 'id', // 默认排序列名
                    sortOrder: 'desc', // 默认排序方式
                    columns: [
                    {
                        field: 'customerNo',
                        title: '客户编号',
                        align: 'center'
                    }, {
                        field: 'customerMobile',
                        title: '手机号',
                        align: 'center',
                        formatter:function(value){
                        	return value.substring(0,3) + "****" + value.substring(7,11);
                        }
                    }, 
                    {
                        field: 'customerName',
                        title: '客户姓名',
                        align: 'center'
                    }, 
                    {
                        field: 'customerTag',
                        title: '客户标签',
                        align: 'center'
                    },
                    {
                        field: 'taskLevel',
                        title: '客户类型',
                        align: 'center'
                    }
                    ,{
                        field: 'currentStatus',
                        title: '客户状态',
                        align: 'center',
                        formatter: function (value) {
                        	//NEWCUSTOMER("待邀约"),TALKING("邀约中"),TALKSUCCESS("邀约成功"),
                        	//INTOSHOP("进店"), ORDERSUCCESS("已生单"), BACKORDER("退单"), INVALID("无效"), FREEZE("冻结");
                        	if(value == 'NEWCUSTOMER'){
                        		return "待邀约";
                        	}else if(value == 'TALKING'){
                        		return "邀约中";
                        	}else if(value == 'TALKSUCCESS'){
                        		return "邀约成功";
                        	}else if(value == 'INTOSHOP'){
                        		return "进店";
                        	}else if(value == 'ORDERSUCCESS'){
                        		return "已生单";
                        	}else if(value == 'BACKORDER'){
                        		return "退单";
                        	}else if(value == 'INVALID'){
                        		return "无效";
                        	}else if(value == 'FREEZE'){
                        		return "冻结";
                        	}else if(value == 'TALKING'){
                        		return "邀约中";
                        	}
                        }
                    }, 
                    	{
                        field: '', // 将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                        	var fragment = '<a href="/api/customer/editCustomer?keyword='+ self.form.keyword +'&store='+ self.form.store 
                    				+ '&dataSource='+ self.form.dataSource +'&customerNo='+ row.customerNo + '" class="m-r-xs btn btn-xs btn-primary">编辑</a>'
                            		+ '<a href="/api/customer/communicateList?keyword='+ self.form.keyword +'&store='+ self.form.store 
                            		+ '&dataSource='+ self.form.dataSource +'&customerNo='+ row.customerNo + '" class="m-r-xs btn btn-xs btn-primary">沟通记录</a>'
                            		+ '<a href="/api/customer/order?keyword='+ self.form.keyword +'&store='+ self.form.store 
                            		+ '&dataSource='+ self.form.dataSource +'&customerNo='+ row.customerNo + '&custIdInOrder='+ row.custIdInOrder +'" class="m-r-xs btn btn-xs btn-primary">订单</a>'
                            		+ '<a href="/api/customer/contacts?keyword='+ self.form.keyword +'&store='+ self.form.store 
                            		+ '&dataSource='+ self.form.dataSource +'&customerNo='+ row.customerNo + '" class="m-r-xs btn btn-xs btn-primary">联系人</a>'
                            		+ '<a href="/api/customer/customerHouse?keyword='+ self.form.keyword +'&store='+ self.form.store 
                            		+ '&dataSource='+ self.form.dataSource +'&customerNo='+ row.customerNo + '" class="m-r-xs btn btn-xs btn-primary">房屋</a>'
                            		+ '<a href="/api/customer/taskList?keyword='+ self.form.keyword +'&store='+ self.form.store 
                            		+ '&dataSource='+ self.form.dataSource +'&customerNo='+ row.customerNo + '" class="m-r-xs btn btn-xs btn-primary">任务</a>' ;
                            		
                            return fragment;
                        }
                    }]
                });
                
            },
            
            //获取门店列表
            storeList:function(){
                self=this;
                self.$http.post('/api/dataPermission/empDataPermission', {permissionType:"MD",jobNum:RocoUser.account},
                		{emulateJSON: true}).then(function (res) {
                    if (res.data.code == 1) {
                        self.stores = res.data.data;
                        if(res.data.data.length > 0 && self.form.store == '' || self.form.store == undefined ){
                        	self.form.store = res.data.data[0].code;
                        }
                        this.dataSourceList();
                        
                    } else {

                    }
                }).catch(function () {

                }).finally(function () {

                });

            },
            
            //获取来源列表
            dataSourceList : function(){
                var self=this;
                self.$http.post('/api/dataPermission/empDataPermissionWithDataSource', {jobNum:RocoUser.account},
                		{emulateJSON: true}).then(function (res) {
                    if (res.data.code == 1) {
                        self.dataSources = res.data.data;
                        if(res.data.data.length > 0 && self.form.dataSource == '' || self.form.dataSource == undefined){
                        	self.form.dataSource = res.data.data[0].code;
                        }
                    } else {
                    }
                    //加载列表数据
                    this.drawTable();
                }).catch(function () {

                }).finally(function () {

                });

            },
            //加载返回来的,之前的搜索记录
            loadParam : function(){
                var self = this;
                /*var href = window.location.href;
                var arr = href.split("&customerNo=")[0];
                
                self.form.dataSource = arr.split("&dataSource=")[1] || '';
                var arr1 = arr.split("&dataSource=")[0];
                
                self.form.store = arr1.split("&store=")[1] || '';
                var arr2 = arr1.split("&store=")[0];
                
                var keyword = arr2.split("keyword=")[1];
                self.form.keyword = keyword || '';*/
                
                self.form.dataSource = RocoUtils.parseQueryString()['dataSource'] || '';
                self.form.keyword = RocoUtils.parseQueryString()['keyword'] || '';
                self.form.store = RocoUtils.parseQueryString()['store'] || '';
            }
        },
        created: function () {
        	this.loadParam();
        },
        ready: function () {
        	this.storeList();
        	
        }
    });
    
})
(this.RocoUtils);