var vueIndexAdd = null;
+(function (RocoUtils) {
    $('#regulationMenu').addClass('active');
    vueIndexAdd = new Vue({
        el: '#container',
        validators: {
            tel: function (val) {
                return /^1[34578]\d{9}$/.test(val)
            }
        },
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '用户编辑',
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
                parentName: null,
                //status: null,
                sort: null
            },
            stores: null,
            //url参数
            params: ''
        },
        methods: {
            //弹窗列表
            showAllList: function () {
                showList();//查询客服列表
            },
            //修改的查询
            editFlag: function () {
                var id = RocoUtils.getQueryString("id");
                self = this;
                //获取门店
                self.$http.post('/api/dataPermission/empDataPermission', {
                    permissionType: "MD",
                    jobNum: null
                }, {emulateJSON: true}).then(function (res) {
                    if (res.data.code == 1) {
                        self.stores = res.data.data;
                        if (self.stores.length == 0) {
                            self.employee.storeCode = '';
                        }
                    }
                    if (self.employee.storeCode == null) {
                        self.employee.storeCode = '';
                    }
                    //编辑之查询
                    self.$http.get('/api/employee/getOneById?id=' + id).then(function (res) {
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
                            self.employee.parentName = res.data.data.parentName;
                            //self.employee.status = res.data.data.status;
                            self.employee.sort = res.data.data.sort;
                        } else {
                            self.$toastr.success(res.data.message);
                        }
                    }).catch(function () {
                    }).finally(function () {
                    });
                })
            },
            //提交
            submitClickHandler: function () {
                self.$validate(true, function () {
                    if (self.$validation.valid) {
                        self.disabled = true;
                        self.$http.post('/api/employee/update', self.employee).then(function (res) {
                            if (res.data.code == 1) {
                                //搜索框的参数
                                self.$toastr.success('操作成功');
                                window.location.href = "/admin/employee/list" + self.params;
                            }
                        }).catch(function () {

                        }).finally(function () {

                        });
                    }
                })
            },
            //返回
            submitClickBack: function () {
                var self = this;
                //搜索框的参数
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
            this.editFlag();
        }
    });
})
(this.RocoUtils);
//弹窗的js
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
                    employee: {
                        parentId: '',
                        parentName: ''
                    }, //添加选中的客服
                    modalModel: null, //模式窗体模型
                    _$el: null, //自己的 el $对象
                    _$dataTable: null,//datatable $对象
                    form: {}
                },
                methods: {
                    queryEmployee: function () {
                        this.$dataTable.bootstrapTable('selectPage', 1);
                    },
                    drawTable: function () {
                        var id = vueIndexAdd.employee.id;
                        var self = this;
                        self.$dataTable = $('#customerTable', self._$el).bootstrapTable({
                            url: '/api/employee/listCollection?id=' + id ,
                            method: 'get',
                            dataType: 'json',
                            cache: false,
                            pagination: true,//不分页
                            sidePagination: 'server',
                            mobileResponsive: true,
                            undefinedText: '-',
                            queryParams: function (params) {
                                // 将table 参数与搜索表单参数合并
                                return _.extend({}, params, self.form);
                            },
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
                                title: '员工号唯一编号',
                                visible:false

                            },  {
                                field: 'orgCode',
                                title: '员工号'
                            },{
                                field: 'empName',
                                title: '姓名'

                            }, {
                                field: 'mobile',
                                title: '手机号'

                            }, {
                                field: 'autoOrder',
                                title: '自动接单',
                                formatter: function (value, row, index) {
                                    var label = "";
                                    if (value == "Y") {
                                        label = '是';
                                    } else if (value == "N") {
                                        label = "否";
                                    }
                                    return label;
                                }
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
                                field: 'parentName',
                                title: '上级'
                            }, {
                                field: 'id',
                                title: 'id',
                                visible: false
                            }
                            ]
                        });
                        self.$dataTable.on('check.bs.table', function (row, data) {
                            self.employee.parentId = data.jobNum;
                            self.employee.parentName = data.empName;
                        });
                    },
                    commitCustomer: function () {
                        var self = this;
                        vueIndexAdd.employee.parentId = self.employee.parentId;
                        vueIndexAdd.employee.parentName = self.employee.parentName;
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

