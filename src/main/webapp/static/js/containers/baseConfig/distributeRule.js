var vueIndex = null;
+(function (RocoUtils) {
	$('#baseConfig').addClass('active');
    $('#distributeRule').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '',
                name: '基础配置'
            }, {
                path: '/',
                name: '派发规则',
                active: true // 激活面包屑的
            }],
            $dataTable: null,
            form: {
                name: ''
            },
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/rule/distributeRule/list',
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
                    columns: [{
                        field: 'name',
                        title: '规则名称',
                        align: 'center'
                    }, {
                        field: 'description',
                        title: '说明',
                        align: 'center'
                    }, {
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
                            	fragment = '<button data-handle="operate-edit" data-id="' + row.id + '" data-status="' + row.status + '" data-name="' + row.name + '" type="button" class="m-r-xs btn btn-xs btn-primary">启用</button>' ;
                            }else if(row.status == '1'){
                            	fragment = '<button data-handle="operate-edit" data-id="' + row.id + '" " data-status="' + row.status + '"data-name="' + row.name + '" type="button" class="m-r-xs btn btn-xs btn-danger">禁用</button>' ;
                            }
                            /*
							 * if (RocoUtils.hasPermission('role:edit'))
							 * fragment += ('<button data-handle="operate-edit"
							 * data-id="' + row.id + '" type="button"
							 * class="m-r-xs btn btn-xs btn-default">编辑</button>');
							 * if (RocoUtils.hasPermission('role:auth'))
							 * fragment += ('<button
							 * data-handle="operate-setPermission" data-id="' +
							 * row.id + '" type="button" class="m-r-xs btn
							 * btn-xs btn-default">设置权限</button>'); if
							 * (RocoUtils.hasPermission('role:delete')) fragment += ('<button
							 * data-handle="operate-delete" data-id="' + row.id + '"
							 * type="button" class="m-r-xs btn btn-xs
							 * btn-danger">删除</button>');
							 */
                            return fragment;
                        }
                    }]
                });
                
                // 启用/禁用状态
                self.$dataTable.on('click', '[data-handle="operate-edit"]', function (e) {
                    var id = $(this).data('id');
                    var status = $(this).data('status');
                    var name = $(this).data('name');
                    
                    var title = '';
                    var text = '';
                    if(status == '0'){
                    	//需要启用
                    	title = "是否启用【" + name +"】?";
                    	text = "启用[" + name + "],同时将禁用其他派发规则";
                    }else {
                    	//需要禁用
                    	title = "是否禁用【" + name +"】?";
                    	text = "禁用[" + name + "]后,请手动启用需要的派发规则,否则系统中将没有派发规则";
                    }
                    swal({
                        title: title,
                        text: text,
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                      },function () {
		                    self.$http.get('/api/rule/distributeRule/updateStatusById?id=' + id + "&status=" + status).then(function (res) {
		                        if (res.data.code == 1) {
		                        	self.$toastr.success('操作成功');
		                            self.$dataTable.bootstrapTable('refresh');
		                        }else{
		                        	console.log(res);
		                        	Vue.toastr.error(res.data.message);
		                        }
		                    }).catch(function () {

		                    }).finally(function () {
		                      swal.close();
		                    });
                      });
                    
                    e.stopPropagation();
                });

            },
            
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            }
        },
        created: function () {
        },
        ready: function () {
            this.drawTable();
        }
    });
    
})
(this.RocoUtils);