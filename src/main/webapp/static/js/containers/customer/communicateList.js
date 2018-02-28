var vueIndex = null;
+(function (RocoUtils) {
	$('#customerMenu').addClass('active');
    $('#communicateList').addClass('active');
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
                name: '沟通记录',
                active: true // 激活面包屑的
            }],
            $dataTable: null,
            form: {
            	communicateMode: '',
            	communicateType: ''
            },
            //返回参数
            param: null,
            communicateModes: null,
            communicateTypes: null
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/communicate/list',
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
                        field: 'communicateMode',
                        title: '沟通方式',
                        align: 'center'
                    }, {
                        field: 'communicateType',
                        title: '沟通类型',
                        align: 'center'
                    }, 
                    {
                        field: 'customerIntention',
                        title: '客户意向',
                        align: 'center'
                    }, 
                    {
                        field: 'remark',
                        title: '沟通结果',
                        align: 'center'
                    },
                    {
                        field: 'createUserName',
                        title: '沟通人',
                        align: 'center'
                    },
                    {
                        field: 'createTime',
                        title: '沟通时间',
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
            //返回列表页面
            goList:function () {
            	var self = this;
                var pObj = RocoUtils.parseQueryString();
            	if (pObj && pObj.from === 'task') {
                    window.history.go(-1);
                }
                else {
                    window.location.href = "/api/customer/customerList?" + self.param;
                }
            },
            //记录列表的搜索记录
            recordParam:function () {
            	 var self = this;
            	 var param = window.location.href.split("?")[1];
            	 self.param = param;
            },
            
            //沟通方式集合(一级菜单)
            communicateModeList: function(){
            	var self = this;
            	self.$http.get('/api/dict/findSubDictListByCode?code=GTFS').then(function (res) {
					if (res.data.code == '1') {
						self.communicateModes = res.data.data;
					}
        		});
            },
            //选择了沟通方式后,再去根据沟通方式,选择子菜单沟通类型
            selectType: function(){
            	var self = this;
            	if(self.form.communicateMode != undefined && self.form.communicateMode == ""){
            		self.form.communicateType = "";
            		self.communicateTypes = null;
            	}else{
            		self.$http.get('/api/dict/findSubDictListByCode?code=' + self.form.communicateMode).then(function (res) {
            			if (res.data.code == '1') {
            				self.communicateTypes = res.data.data;
            			}
            		});
            	}
            }
        },
        created: function () {
        	//记录列表的搜索记录
        	this.recordParam();
        },
        ready: function () {
            this.communicateModeList();
            this.drawTable();
        }
    });
    
})
(this.RocoUtils);