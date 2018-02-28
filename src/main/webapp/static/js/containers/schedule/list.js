var vueIndex = null;
+(function (RocoUtils) {
    $('#regulationMenu').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '用户日程',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            //url参数
            params: ''
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var jobNum = RocoUtils.getQueryString("id");
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/schedule/findByUser?jobNum='+jobNum,
                    method: 'get',
                    dataType: 'json',
                    cache: false, //去缓存
                    pagination: true, //是否分页
                    sidePagination: 'server', //服务端分页
                    mobileResponsive: true,
                    undefinedText: '-', //空数据的默认显示字符
                    striped: true, //斑马线
                    maintainSelected: true, //维护checkbox选项
                    sortName: 'id', //默认排序列名
                    sortOrder: 'desc', //默认排序方式
                    columns: [{
                        field: 'title',
                        title: '标题',
                        align: 'center',
                    }, {
                        field: 'content',
                        title: '内容',
                        align: 'center'
                    }, {
                        field: 'scheduleTime',
                        title: '计划时间',
                        align: 'center'
                    }, {
                        field: 'status',
                        title: '状态',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var label = "";
                            if (+moment(row.scheduleTime)<+new Date()) {
                                label = '已过期';
                            }else {
                                label = "未过期";
                            }
                            return label;
                        }
                    }, {
                        field: 'createTime',
                        title: '创建时间',
                        align: 'center'
                    },  {
                        field: 'type',
                        title: '创建类型',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var label = value;

                            return label;
                        }
                    }]
                });
            },
            //返回
            submitClickBack:function () {
                var self = this;
                window.location.href = "/admin/employee/list" + self.params;
            },
            //加载参数
            loadParams: function () {
                var self = this;
                var href = window.location.href;
                self.params = href.substring(href.indexOf("?"));
            }
        },
        created: function () {
            this.loadParams();
        },
        ready: function () {
            this.drawTable();
        }
    });
})
(this.RocoUtils);