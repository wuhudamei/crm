var vueIndex = null;
+(function (RocoUtils) {
    $('#dataSourceMenu').addClass('active');
    $('#sourceList').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '来源管理',
            }, {
                path: '/',
                name: '来源列表',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            form: {
                keyword: ''
            },
            dataSource: {
                id: '',
                status: ''
            }
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                //搜索框关键字
                var keyword = RocoUtils.getQueryString("keyword");
                if(keyword==null){
                    keyword="";
                }
                self.form.keyword = keyword;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/data/list',
                    method: 'get',
                    dataType: 'json',
                    cache: false, //去缓存
                    pagination: true, //是否分页
                    sidePagination: 'server', //服务端分页
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', //空数据的默认显示字符
                    striped: true, //斑马线
                    maintainSelected: true, //维护checkbox选项
                    sortName: 'id', //默认排序列名
                    sortOrder: 'desc', //默认排序方式
                    columns: [{
                        field: 'sourceName',
                        title: '名称',
                        align: 'center'
                    }, {
                        field: 'sourceCode',
                        title: '代码',
                        align: 'center'
                    }, {
                        field: 'remark',
                        title: '备注',
                        align: 'center',
                    }, {
                        field: 'autoDistribute',
                        title: '自动派发',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var label = '';
                            if (value == 'Y') {
                                label = "是";
                            }
                            if (value == 'N') {
                                label = "否";
                            }
                            return label;
                        }
                    }, {
                        field: 'status',
                        title: '状态',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var label = '';
                            if (value == 1) {
                                label = "启用";
                            }
                            if (value == 0) {
                                label = "未启用";
                            }
                            return label;
                        }

                    }, {
                        field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            fragment += ('<button data-handle="operate-edit" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-default">编辑</button>');
                            if (row.status == '1') {
                                fragment += ('<button data-handle="operate-disable" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-default">禁用</button>');
                            }
                            if (row.status == '0') {
                                fragment += ('<button data-handle="operate-enabled" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-default">启用</button>');
                            }
                            return fragment;
                        }
                    }]
                });
                //编辑
                self.$dataTable.on('click', '[data-handle="operate-edit"]', function (e) {
                    var id = $(this).data('id');
                    window.location.href = "/admin/dataSource/edit?id=" + id + "&&keyword=" + vueIndex.form.keyword;
                });
                //禁用启用
                self.$dataTable.on('click', '[data-handle="operate-disable"]', function (e) {
                    self.dataSource.id = $(this).data('id');
                    self.dataSource.status = '0';
                    self.updateStatus(0);
                });
                self.$dataTable.on('click', '[data-handle="operate-enabled"]', function (e) {
                    self.dataSource.id = $(this).data('id');
                    self.dataSource.status = '1';
                    self.updateStatus(1);
                });
            },
            //禁用启用的方法
            updateStatus: function (status) {
                var self = this
                var title = ""
                if (status == 0) {
                    title = "你确定将该来源改为禁用状态吗?";
                }
                if (status == 1) {
                    title = "你确定将该来源改为启用状态吗?";
                }
                swal({
                    title: title,
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    closeOnConfirm: false
                }, function (isConfirm) {
                    if (isConfirm) {
                        self.$http.post('/api/data/update', self.dataSource).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                self.$dataTable.bootstrapTable('refresh');
                            } else {

                            }
                        }).catch(function () {
                            swal("操作失败！", "", "error");
                        }).finally(function () {
                            swal.close();
                        });
                    }
                });

            },
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            createBtnClickHandler: function (e) {
                window.location.href = '/admin/dataSource/edit';
            }

        },
        created: function () {
            this.fUser = window.RocoUser;
        },
        ready: function () {
            this.drawTable();
        }
    });
})
(this.RocoUtils);