var vueIndexAdd = null;
+(function (RocoUtils) {
    $('#regulationMenu').addClass('active');
    $('#csAdd').addClass('active');
    vueIndexAdd = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '客服添加',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            employee: {
                id: null,
                jobNum: null,
                empName: null,
                mobile: null,
                autoOrder: null,
                busyThreshold: null,
                storeCode: null,
                position: null,
                parentId: null,
                status: null
            }

        },
        methods: {
            //修改的查询
            editFlag: function () {
                var id = RocoUtils.getQueryString("id");
                self = this;
                //如果是添加 不执行
                if (id == undefined || id == '' || id == null) {
                    self.employee.autoOrder = '';
                    self.employee.storeCode = '';
                    self.employee.status = '';
                } else {
                    self.$http.get('/api/employee/getOneById?id=' + id).then(function (res) {
                        self.breadcrumbs[1].name = '客服修改'
                        if (res.data.code == 1) {
                            self.employee.id = res.data.data.id;
                            self.employee.jobNum = res.data.data.jobNum;
                            self.employee.empName = res.data.data.empName;
                            self.employee.mobile = res.data.data.mobile;
                            self.employee.autoOrder = res.data.data.autoOrder;
                            self.employee.busyThreshold = res.data.data.busyThreshold;
                            self.employee.storeCode = res.data.data.storeCode;
                            self.employee.position = res.data.data.position;
                            self.employee.parentId = res.data.data.parentId;
                            self.employee.status = res.data.data.status;
                        } else {
                            self.$toastr.success(res.data.message);
                        }
                    }).catch(function () {
                    }).finally(function () {
                    });
                }
            },
            //提交
            submitClickHandler: function () {
                self.$validate(true, function () {
                    if (self.$validation.valid) {
                        self.disabled = true;
                        self.$http.post('/api/employee/add', self.employee).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                window.location.href = '/admin/employee/list';
                            }
                        }).catch(function () {

                        }).finally(function () {
                        });
                    }
                })
            },
            //弹窗
            showModel: function () {
                var _$modal = $('#customerModal').clone();
                var $modal = _$modal.modal({
                    width: 580,
                    maxWidth: 580,
                    height: 260,
                    maxHeight: 260,
                    backdrop: 'static',
                    keyboard: false
                });
                modal($modal);
            },
            showAllList: function () {
                showList();//查询客服列表
            },

        },
        created: function () {

        },
        ready: function () {
            this.editFlag();
        }
    });
})
(this.RocoUtils);

function showList() {
    var _modal = $('#customerModal').clone();
    var $el = _modal.modal({
        height: 600,
        maxHeight: 600,
        backdrop: 'static',
    });
    $el.on('shown.bs.modal',
        function () {
            var el = $el.get(0);
            var vueModal = new Vue({
                el: el,
                http: {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                },
                mixins: [RocoVueMixins.ModalMixin],
                $modal: $el,
                created: function () {
                },
                data: {
                    employee: null, //添加选中的客服
                    modalModel: null, //模式窗体模型
                    _$el: null, //自己的 el $对象
                    _$dataTable: null //datatable $对象
                },
                methods: {
                    drawTable: function () {
                        var self = this;
                        self.$dataTable = $('#customerTable', self._$el).bootstrapTable({
                            url: '/api/employee/listCollection',
                            method: 'get',
                            dataType: 'json',
                            cache: false,
                            pagination: true,//不分页
                            sidePagination: 'server',
                            mobileResponsive: true,
                            undefinedText: '-',
                            striped: true,
                            maintainSelected: true,
                            pageSize: 5,
                            sortOrder: 'desc',
                            columns: [{
                                checkbox: true,
                                align: 'center',
                                width: '5%',
                                radio: true
                            }, {
                                field: 'jobNum',
                                title: '员工号'

                            }, {
                                field: 'empName',
                                title: '姓名'

                            }, {
                                field: 'mobile',
                                title: '手机号'

                            }, {
                                field: 'autoOrder',
                                title: '自动接单'

                            }, {
                                field: 'orgName',
                                title: '门店'
                            }, {
                                field: 'busyThreshold',
                                title: '忙碌阈值'

                            }, {
                                field: 'position',
                                title: '岗位'

                            }, {
                                field: 'parentId',
                                title: '上级'

                            }
                            ]
                        });

                        self.$dataTable.on('check.bs.table', function (row, data) {
                            var commitCustomerCheck = {
                                jobNum: data.jobNum,
                                empName: data.empName,
                                mobile: data.mobile,
                                autoOrder: data.autoOrder,
                                busyThreshold: data.busyThreshold,
                                storeCode: data.storeCode,
                                position: data.position,
                                parentId: data.parentId,
                                status: data.status
                            };
                            self.employee = commitCustomerCheck;
                        });
                    },
                    commitCustomer: function () {
                        var self = this;
                        vueIndexAdd.employee = self.employee;
                        $el.modal('hide');
                        this.$destroy();
                    }
                },
                ready: function () {
                    this.drawTable();
                }
            });
            // 创建的Vue对象应该被返回
            return vueModal;
        });

}