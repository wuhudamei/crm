var vueIndex = null;
+(function (RocoUtils) {
    $('#dataSourceMenu').addClass('active');
    $('#sourceMonitor').addClass('active');
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
                name: '异常信息管理',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            form: {
                abnormalContent: '',
                abnormalType: '',
                sourceCode: '',
                status: ''
            },
            //下拉异常类型
            types: {},
            //下拉接口名字
            nameLables: {}

        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/abnormal/list',
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
                        field: 'abnormalContent',
                        title: '异常信息',
                        align: 'center'
                    }, {
                        field: 'abnormalType',
                        title: '异常类型',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var label = '';
                            if (value == 'INVITATION') {
                                label = '邀约'
                            }
                            if (value == 'REPEAT') {
                                label = '重复'
                            }
                            if (value == 'ABNORMAL') {
                                label = '异常'
                            }
                            if (value == 'OTHER') {
                                label = '其他'
                            }
                            return label;
                        }
                    }, {
                        field: 'sourceName',
                        title: '接口名称',
                        align: 'center',
                    }, {
                        field: 'createTime',
                        title: '日期',
                    }, {
                        field: 'status',
                        title: '状态',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var label = '';
                            if (value == 0) {
                                label = "未处理";
                            }
                            if (value == 1) {
                                label = "已处理";
                            }
                            return label;
                        },
                        visible: false

                    }, {
                        field: 'remark',
                        title: '备注',
                        visible: false
                    }, {
                        field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            if (row.status == 0) {
                                fragment += ('<button data-handle="operate-edit" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-default">处理</button>');
                            }
                            return fragment;
                        },
                        visible: false
                    }]
                });
                //编辑
                self.$dataTable.on('click', '[data-handle="operate-edit"]', function (e) {
                    var id = $(this).data('id');
                    window.location.href = '#' + id;
                });
            },
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            createBtnClickHandler: function (e) {
                window.location.href = '/admin/dataSource';
            },
            //获取异常类型下拉框
            getType: function () {
                var self = this;
                //INVITATION("邀约"), REPEAT("重复"), ABNORMAL("异常"), OTHER("其他")
                self.types = [ {
                    code: 'REPEAT',
                    name: '重复'
                },{
                    code: 'ABNORMAL',
                    name: '异常'
                },{
                    code: 'OTHER',
                    name: '其他'
                }];

            },
            //获取接口下拉框
            getIntetface: function () {
                var self = this;
                self.$http.get('/api/data/getNameLabel').then(function (res) {
                    if (res.data.code == 1) {
                        self.nameLables = res.data.data;
                    }
                }).catch(function () {

                }).finally(function () {

                });
            }

        },
        created: function () {
            this.fUser = window.RocoUser;
        },
        ready: function () {
            this.drawTable();
            this.getType();
            this.getIntetface();
        }
    });
})
(this.RocoUtils);