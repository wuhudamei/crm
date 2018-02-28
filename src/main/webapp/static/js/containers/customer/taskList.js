var vueIndex = null;
+(function (RocoUtils) {
	$('#customerMenu').addClass('active');
    $('#contacts').addClass('active');
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
                name: '任务列表',
                active: true // 激活面包屑的
            }],
            $dataTable: null,
            form: {
            	communicateType: ''
            },
            //返回参数
            param: null
        },
        methods: {
        	//返回列表页面
            goList:function () {
            	var self = this;
            	window.location.href = "/api/customer/customerList?" + self.param;
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/backTask/findTaskByCustomerNo',
                    method: 'get',
                    dataType: 'json',
                    cache: false, // 去缓存
                    pagination: true, // 是否分页
                    sidePagination: 'server', // 服务端分页
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                    	//取出路径中的参数
                    	var customerNo = null;
                    	if(window.location.href.indexOf("customerNo=") != -1){
                    		customerNo = window.location.href.split("customerNo=")[1];
                    	}
                        return _.extend({"customerNo" : customerNo}, params, self.form);
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
                        field: 'taskNo',
                        title: '任务编号',
                        align: 'center'
                    }, 
                    {
                        field: 'invitationCode',
                        title: '邀约码',
                        align: 'center'
                    }, 
                    {
                        field: 'callTime',
                        title: '来电时间',
                        align: 'center'
                    },
                    {
                        field: 'mechandiser',
                        title: '跟单员',
                        align: 'center'
                    },
                    {
                        field: 'promoteSource',
                        title: '推广来源',
                        align: 'center'
                    },
                    {
                        field: 'type',
                        title: '任务类型',
                        align: 'center',
                        formatter: function (value) {
                        	if(value == 'INVITATION'){
                        		return "<font color='green'>邀约</font>";
                        	}else if(value = 'REPEAT'){
                        		return "<font color='blue'>重复</font>";
                        	}else if(value = 'ABNORMAL'){
                        		return "<font color='red'>异常</font>";
                        	}else{
                        		return "<font color='red'>其他</font>";
                        	}
                        }
                    },
                    {
                        field: 'distributeStatus',
                        title: '派发状态',
                        align: 'center',
                        formatter: function (value) {
                        	if(value == 'Y'){
                        		return "<font color='green'>已派发</font>";
                        	}else{
                        		return "<font color='red'>未派发</font>";
                        	}
                        }
                    },
                    {
                        field: 'status',
                        title: '任务状态',
                        align: 'center',
                        formatter: function (value) {
                        	if(value == '1'){
                        		return "<font color='green'>有效</font>";
                        	}else if(value == '0'){
                        		return "<font color='red'>无效</font>";
                        	}else{
                        		return "<font color='red'>冻结</font>";
                        	}
                        }
                    },
                    {
                        field: 'distributeModel',
                        title: '派发方式',
                        align: 'center',
                        formatter: function (value) {
                        	if(value == 'SYSTEM'){
                        		return "<font color='green'>系统</font>";
                        	}else if(value == 'HAND'){
                        		return "<font color='blue'>人工</font>";
                        	}
                        }
                    },
                    {
                        field: 'distributeTime',
                        title: '派发时间',
                        align: 'center'
                    }
                    /*,{
                        field: 'status',
                        title: '状态',
                        align: 'center',
                        formatter: function (value, row, index) {
                        	if(value == '1'){
                        		return "<font color='green'>启用</font>";
                        	}else{
                        		return "<font color='red'>禁用</font>";
                        	}
                        }
                    }, 
                    	{
                        field: '', // 将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            if(row.status == '0'){
                            	fragment = '<button data-handle="operate-edit" data-id="' + row.id + '"  data-status="' + row.status + '" data-name="' + row.name + '" type="button" class="m-r-xs btn btn-xs btn-primary">启用</button>' ;
                            }else if(row.status == '1'){
                            	fragment = '<button data-handle="operate-edit" data-id="' + row.id + '" " data-status="' + row.status + '" data-name="' + row.name + '" type="button" class="m-r-xs btn btn-xs btn-danger">禁用</button>' ;
                            }
                            return fragment;
                        }
                    }*/]
                });
            },
            
            query: function () {
            	this.$dataTable.bootstrapTable('selectPage',1);
                this.$dataTable.bootstrapTable('refresh');
            },
            
            //记录列表的搜索记录
            recordParam:function () {
            	 var self = this;
            	 var param = window.location.href.split("?")[1];
            	 self.param = param;
            }
        },
        created: function () {
        	//记录列表的搜索记录
        	this.recordParam();
        },
        ready: function () {
            this.drawTable();
        }
    });
    
})
(this.RocoUtils);