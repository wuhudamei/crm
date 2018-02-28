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
                name: '用户管理',
            }, {
                path: '/',
                name: '用户奖励',
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
                    url: '/api/orderReward/list?jobNum=' + jobNum,
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
                        field: 'id',
                        title: '奖励id',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'rewardOrderNum',
                        title: '奖励单数',
                        align: 'center',
                    }, {
                        field: 'rewardDate',
                        title: '奖励时间',
                        align: 'center'
                    }, {
                        field: 'createTime',
                        title: '创建时间',
                        align: 'center'
                    }, {
                        field: 'status',
                        title: '操作',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var label = "";
                            if (moment(row.rewardDate).isAfter(moment(new Date()).format('YYYY-MM-DD'))) {
                                label = ('<button data-handle="operate-delete" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-default">删除</button>')
                            }
                            return label;
                        }
                    }]
                });
                self.$dataTable.on('click', '[data-handle="operate-delete"]', function (e) {

                    var id = $(this).data('id');
                    swal({
                        title: "确定删除该条奖励",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        closeOnConfirm: false
                    }, function (isConfirm) {
                        if (isConfirm) {
                            self.$http.get("/api/orderReward/delete?id=" + id).then(function (res) {
                                if (res.data.code == 1) {
                                    self.$toastr.success('删除成功');
                                    self.$dataTable.bootstrapTable('refresh');
                                }
                            }).catch(function () {
                                swal("删除失败！", "", "error");
                            }).finally(function () {
                                swal.close();
                            });
                        }
                    });
                });
            },
            //返回
            submitClickBack: function () {
                var self = this;
                window.location.href = "/admin/employee/list" + self.params;
            },
            //添加
            rewardAdd: function () {
                var keyword = RocoUtils.parseQueryString()["keyword"];
                var storeCode = RocoUtils.parseQueryString()["storeCode"];
                window.location.href="/admin/employee/rewardAdd?jobNo="+RocoUtils.getQueryString("id")+"&&keyword=" + keyword + "&&storeCode=" + storeCode;
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