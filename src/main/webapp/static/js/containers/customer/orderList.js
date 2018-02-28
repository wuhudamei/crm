var vueIndex = null;
+(function (RocoUtils) {
	$('#customerMenu').addClass('active');
    //$('#communicateList').addClass('active');
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
                name: '订单列表',
                active: true // 激活面包屑的
            }],
            $dataTable: null,
            form: {
            	orderStartDate: '',
            	orderEndDate: ''
            },
            //返回参数
            param: null
        },
        methods: {
        	activeDatepicker:function(){
    	        var self = this;
    	        $('#orderStartDate',self._$el).datetimepicker({
    	        	clearBtn:true
    	        });
    	        $('#orderEndDate',self._$el).datetimepicker({
    	        	clearBtn:true
    	        });
    	    },
    	    //返回列表页面
            goList:function () {
            	var self = this;
            	window.location.href = "/api/customer/customerList?" + self.param;
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/customer/findOrderListBycustomerNo',
                    method: 'get',
                    dataType: 'json',
                    cache: false, // 去缓存
                    pagination: true, // 是否分页
                    sidePagination: 'server', // 服务端分页
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                    	//取出路径中的参数
                    	var customerNo = null;
                    	var custIdInOrder = null;
                    	var result = null;
                    	if(window.location.href.indexOf("customerNo=") != -1 
                    			&& window.location.href.indexOf("&custIdInOrder=") != -1){
                    		
                			var arr = window.location.href.split("&custIdInOrder=");
                			custIdInOrder = arr[1];
                			result = arr[0];
                			customerNo = result.split("customerNo=")[1];
                    	}
                        return _.extend({"customerNo" : customerNo,"custIdInOrder" : custIdInOrder}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', // 空数据的默认显示字符
                    striped: true, // 斑马线
                    maintainSelected: true, // 维护checkbox选项
                    sortName: 'id', // 默认排序列名
                    sortOrder: 'desc', // 默认排序方式
                    columns: [
                    {
                        field: 'serviceName',
                        title: '下单客服',
                        align: 'center'
                    }, {
                        field: 'stylistName',
                        title: '设计师',
                        align: 'center'
                    }, 
                    {
                        field: 'supervisorName',
                        title: '监理',
                        align: 'center'
                    }, 
                    {
                        field: 'contractNo',
                        title: '合同编号',
                        align: 'center'
                    },
                    {
                        field: 'signFinishTime',
                        title: '合同日期',
                        align: 'center'/*,
                        formatter: function (value) {
                        	var time = new Date(value);
                        	return time.getFullYear() + "-" + time.getMonth() + 1 +"-" 
                        			+ time.getDate() + " " + time.getHours() + ":" 
                        			+ time.getMinutes() + ":" + time.getSeconds();
                        }*/
                    },
                    {
                        field: 'budgetAmount',
                        title: '预算总金额',
                        align: 'center'
                    },
                    {
                        field: 'imprest',
                        title: '预付款订金',
                        align: 'center'
                    },
                    {
                        field: 'orderTagName',
                        title: '串单标签',
                        align: 'center',
                        formatter: function (value) {
                        	if(value == undefined){
                        		return "无";
                        	}else{
                        		return value;
                        	}
                        }
                    },
                    {
                        field: 'orderStatus',
                        title: '订单状态',
                        align: 'center'
                    }/*,
                    {
                        field: '', // 将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                        	console.log(row);
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