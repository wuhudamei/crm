var vueIndex = null;
+(function (RocoUtils) {
    $('#customerMenu').addClass('active');
    $('#allTaskList').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            },
                {
                    path: '/',
                    name: '客户管理'
                },
                {
                    path: '/',
                    name: '任务派发',
                    active: true //激活面包屑的
                }],
            $dataTable: null,
            //搜索框
            form: {
                keyword: '',
                storeCode: '',
                taskStatus:''
            },
            //任务（ 用于post 提交的 客服列表查询）
            task: {
                taskNo: null,//任务编号
                num: null,//客服编号
                name: null,//客服名字
                store: null,//所在门店编号
                dataSource: null//来源编号
            },
            //门店
            stores: null
        },
        methods: {
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/task/list',
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
                    columns: [
                        {
                            field: 'id',
                            title: '主键',
                            align: 'center',
                            visible: false
                        }
                        , {
                            field: 'customerName',
                            title: '客户姓名',
                            align: 'center',
                        }, {
                            field: 'customerMobile',
                            title: '手机号',
                            align: 'center',
                            formatter:function(value){
                            	return value.substring(0,3) + "****" + value.substring(7,11);
                            }
                        }, {
                            field: 'customerNo',
                            title: '客户编号',
                            align: 'center'
                        }, {
                            field: 'taskNo',
                            title: '任务编号',
                            align: 'center',
                            visible: false

                        }, {
                            field: 'invitationCode',
                            title: '邀约码',
                            align: 'center'

                        }, {
                            field: 'createTime',
                            title: '进线时间',
                            align: 'center'
                        }, {
                            field: 'promoteSourceName',
                            title: '推广渠道',
                            align: 'center'
                        }, {
                            field: 'store',
                            title: '门店编号',
                            align: 'center',
                            visible: false
                        }, {
                            field: 'dataSource',
                            title: '来源编号',
                            align: 'center',
                            visible: false
                        }, {
                            field: 'status',
                            title: '有效性',
                            align: 'center',
                            formatter: function (value, row, index) {
                                var label = "";
                                if (value == "1") {
                                    label = "<font color='green'>有效</font>";
                                } else if (value == "0") {
                                    label = "<font color='red'>无效</font>";
                                }else if (value == "2") {
                                    label = "<font color='blue'>冻结</font>";
                                }
                                return label;
                            }
                        }, {
                            field: 'distributeTime',
                            title: '派发日期',
                            align: 'center'
                        }, {
                            field: 'communicateTime',
                            title: '沟通时间',
                            align: 'center',
                            formatter: function (value, row, index) {
                                var label = "";
                                label += '<a href="/api/customer/communicateList?from=task&customerNo='+ row.customerNo + '">' + (value ? value : '') + '</a>'
                                return label;
                            }
                        },
                        {
                            field: 'taskLevel',
                            title: '客户级别',
                            align: 'center'
                        },
                        {
                            field: 'mechandiser',
                            title: '跟单员编号',
                            align: 'center',
                            visible: false
                        }, {
                            field: 'distributeStatus',
                            title: '派发情况',
                            align: 'center',
                            formatter: function (value, row, index) {
                                var label = "";
                                if (value == "Y") {
                                    label = "<font color='green'>已派发</font>";
                                } else if (value == "N") {
                                    label = "<font color='blue'>未派发</font>";
                                }
                                return label;
                            }
                        },{
                            field: 'empName',
                            title: '客服',
                            align: 'center'
                        }, {
                            field: 'isShop',
                            title: '是否有进店的选项',
                            align: 'center',
                            visible: false
                        }, {
                            field: 'creator',
                            title: '创建人',
                            align: 'center'
                        },{
                            field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                            title: "操作",
                            align: 'center',
                            formatter: function (value, row, index) {
                                var fragment = '';
                                // if (RocoUtils.hasPermission('role:edit'))
                                /* fragment += ('<button data-handle="operate-edit" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-default">无效</button>');*/

                                if (row.distributeStatus == 'Y') {
                                    fragment += ('<button data-handle="operate-distributed" data-id="' + row.taskNo + '"+ data-num="' + row.mechandiser + '"+ data-name="'
                                    + row.empName + '" data-store="' + row.store + '"  data-data-source="' + row.dataSource + '"   type="button" class="m-r-xs btn btn-xs btn-default">转派</button>');
                                }
                                if (row.distributeStatus == 'N' && row.distributeModel=='HAND') {
                                    fragment += ('<button data-handle="operate-distributed" data-id="' + row.taskNo + '"+ data-num="'
                                    + row.mechandiser + '"+ data-name="' + row.empName + '" data-store="' + row.store + '"  data-data-source="' + row.dataSource + '"   type="button" class="m-r-xs btn btn-xs btn-default">派发</button>');
                                }
                                if (row.isShop == true) {
                                    fragment += ('<button data-handle="operate-goStore" data-id="' + row.taskNo + '"+ data-customer="'
                                    + row.customerNo + '"+ data-mechandiser="' + row.mechandiser + '"  ,type="button" class="m-r-xs btn btn-xs btn-default">进店</button>');
                                }
                                if (RocoUtils.hasPermission('task:tovalid') && (row.status == "0" || row.status == "2")) {
                                    fragment += ('<button data-handle="operate-effect" data-id="' + row.taskNo + '"  ,type="button" class="m-r-xs btn btn-xs btn-default">有效</button>');
                                }
                                return fragment;
                            }
                        }]
                });
                //派发转派
                self.$dataTable.on('click', '[data-handle="operate-distributed"]', function (e) {
                    //获取数据
                    self.task.taskNo = $(this).data('id');
                    self.task.num = $(this).data('num');
                    self.task.name = $(this).data('name');
                    self.task.store = $(this).data('store');
                    self.task.dataSource = $(this).data('dataSource');
                    self.showAllList();
                });
                //进店
                self.$dataTable.on('click', '[data-handle="operate-goStore"]', function (e) {
                    //获取数据
                    var taskNo = $(this).data('id');
                    var customerNo = $(this).data('customer');
                    var mechandiser = $(this).data('mechandiser');
                    //获取来源列表
                    self.$http.post('/api/task/goStore', {
                            taskNo: taskNo,
                            customerNo: customerNo,
                            mechandiser: mechandiser
                        },
                        {emulateJSON: true}).then(function (res) {
                        if (res.data.code == 1) {
                            self.$toastr.success('操作成功');
                            this.$dataTable.bootstrapTable('refresh');
                        }
                    }).catch(function () {
                    }).finally(function () {
                    });

                });
                //任务设为有效
                self.$dataTable.on('click', '[data-handle="operate-effect"]', function (e) {
                    //获取数据
                    var taskNo = $(this).data('id');
                    swal({
                        title: "提示",
                        text: "确定要将该线索调整为有效吗",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        closeOnConfirm: false
                        //提交
                    }, function (isConfirm) {
                        if(isConfirm) {
                            self.$http.post('/api/task/chstatus', {
                                    taskNo: taskNo
                                },
                                {emulateJSON: true}).then(function (res) {
                                if (res.data.code == 1) {
                                    self.$toastr.success('操作成功');
                                    this.$dataTable.bootstrapTable('refresh');
                                }
                            }).catch(function () {
                            }).finally(function () {
                                swal.close();
                            });
                        }
                    });
                });
            },
            query: function () {
            	this.$dataTable.bootstrapTable('selectPage', 1);
                this.$dataTable.bootstrapTable('refresh');
            },
            //弹窗列表
            showAllList: function () {
                showList();//查询客服列表
            },
            //获取门店列表和来源列表（下拉框）
            storeList: function () {
                self = this;
                self.$http.post('/api/dataPermission/empDataPermission', {
                    permissionType: "MD",
                    jobNum: RocoUser.account
                }, {emulateJSON: true}).then(function (res) {
                    if (res.data.code == 1) {
                        self.stores = res.data.data;
                        //然后画表
                        this.drawTable();
                    } else {

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
            this.storeList();
        }
    });

})
(this.RocoUtils);

var vueModal = null;
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
            vueModal = new Vue({
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
                        id: '',
                        name: '',
                        orderNum:'',
                        busyThreshold:''
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
                        var num = vueIndex.task.num;
                        var store = vueIndex.task.store;
                        //var dataSource = vueIndex.task.dataSource;
                        var self = this;
                        self.$dataTable = $('#customerTable', self._$el).bootstrapTable({
                            url: '/api/employee/listCollection?jobNum=' + num + "&&store=" + store +"&&orderFlag=Y",
                            method: 'get',
                            dataType: 'json',
                            cache: false,
                            pagination: true,//不分页
                            sidePagination: 'server',
                            queryParams: function (params) {
                                // 将table 参数与搜索表单参数合并
                                return _.extend({}, params, self.form);
                            },
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
                                radio:true
                            }, {
                                field: 'jobNum',
                                title: '员工唯一ID',
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
                                field: 'orderNum',
                                title: '当日派发量'
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
                            self.employee.id = data.jobNum;
                            self.employee.name = data.empName;
                            self.employee.busyThreshold = data.busyThreshold;
                            self.employee.orderNum = data.orderNum;
                        });
                    },
                    commitCustomer: function () {
                        var self = this;

                        //如果没选
                        if (self.employee.name == null || self.employee.name == '') {
                            swal({
                                title: "提示",
                                text: "请选择派单员",
                                type: "warning",
                                showCancelButton: true,
                                confirmButtonColor: "#DD6B55",
                                confirmButtonText: "确定",
                                cancelButtonText: "取消",
                                closeOnConfirm: false
                            }, function (isConfirm) {
                                if (isConfirm) {
                                    swal.close();
                                }
                            });

                        } else {//选了
                            if(self.employee.busyThreshold<=self.employee.orderNum){
                                swal({
                                    title: "提示",
                                    text: "该派单员已经超过当日接单阈值",
                                    type: "warning",
                                    showCancelButton: true,
                                    confirmButtonColor: "#DD6B55",
                                    confirmButtonText: "确定",
                                    cancelButtonText: "取消",
                                    closeOnConfirm: false
                                }, function (isConfirm) {
                                    swal.close();
                                });
                            }else {

                            var name = vueIndex.task.name;
                            var text = "是否确定将任务"
                            if (name != null && name != '') {
                                text += "由【" + name + "】转";
                            }
                            text += "派给【" + self.employee.name+"】？";
                            //弹窗
                            swal({
                                title: "提示",
                                text: text,
                                type: "warning",
                                showCancelButton: true,
                                confirmButtonColor: "#DD6B55",
                                confirmButtonText: "确定",
                                cancelButtonText: "取消",
                                closeOnConfirm: false
                                //提交
                            }, function (isConfirm) {
                                if (isConfirm) {
                                    self.$http.post('/api/task/update', {
                                        jobNum: self.employee.id,
                                        taskNo: vueIndex.task.taskNo,
                                        oldJobNum: vueIndex.task.num,
                                        isWchat: false
                                    }, {emulateJSON: true}).then(function (res) {
                                        if (res.data.code == 1) {
                                            self.$toastr.success('操作成功');
                                            vueIndex.$dataTable.bootstrapTable('refresh');
                                        }
                                    }).catch(function () {
                                        swal("操作失败！", "", "error");
                                    }).finally(function () {
                                        swal.close();
                                        $el.modal('hide');
                                        this.$destroy();
                                    });
                                }
                            });
                        }

                    }
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