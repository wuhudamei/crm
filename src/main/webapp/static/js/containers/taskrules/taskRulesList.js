var vueIndex = null;
+(function (RocoUtils) {
    $('#taskrules').addClass('active');
    $('#autoDistributed').addClass('active');
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
                    name: '规则配置'
                },
                {
                    path: '/',
                    name: '自动派单',
                    active: true //激活面包屑的
                }],
            $dataTable: null,
            //搜索框
            form: {
                storeCode: '',
                dataSourceCode: '',
                promoteCode: '',
            },
            //门店
            stores: null,
            //来源
            dataSources: null,
            //渠道
            promoteCodes: null
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/taskRules/list',
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
                        }, {
                            field: 'storeCode',
                            title: '门店',
                            align: 'center',
                            formatter: function (value, row, index) {
                                var label = '';
                                for(var i=0,len=self.stores.length; i<len; i++){
                                    if (value == self.stores[i].code) {
                                        label=self.stores[i].name;
                                    }
                                }
                                return label;
                            },
                        }, {
                            field: 'dataSourceCode',
                            title: '数据来源',
                            align: 'center',
                            formatter: function (value, row, index) {
                                var label = '';
                                for(var i=0,len=self.dataSources.length; i<len; i++){
                                    if (value == self.dataSources[i].code) {
                                        label=self.dataSources[i].name;
                                    }
                                }
                                return label;
                            },
                        },
                        {
                            field: 'promoteCode',
                            title: '推广渠道',
                            align: 'center',
                            formatter: function (value, row, index) {
                                var label = '';
                                for(var i=0,len=self.promoteCodes.length; i<len; i++){
                                    if (value == self.promoteCodes[i].code) {
                                        label=self.promoteCodes[i].name;
                                    }
                                }
                                return label;
                            },
                        },
                        {
                            field: 'creatorName',
                            title: '创建人',
                            align: 'center'
                        },{
                            field: 'createTime',
                            title: '创建时间',
                            align: 'center',
                        }, {
                            field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                            title: "操作",
                            align: 'center',
                            formatter: function (value, row, index) {
                                var fragment = '';
                                if (RocoUtils.hasPermission('taskRules:edit')) {
                                    fragment += ('<button data-handle="operate-delete" data-id="' + row.id + '",type="button" class="m-r-xs btn btn-xs btn-primary">删除</button>');
                                }
                                return fragment;
                            }
                        }]
                });
                //删除
                self.$dataTable.on('click', '[data-handle="operate-delete"]', function (e) {
                    //获取数据
                    var id = $(this).data('id');
                    //弹窗
                    swal({
                        title: "提示",
                        text:'确定删除该条数据，删除后不可恢复',
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        closeOnConfirm: false
                        //提交
                    }, function (isConfirm) {
                        if (isConfirm) {
                            self.$http.get('/api/taskRules/delete/'+id).then(function (res) {
                                if (res.data.code == 1) {
                                    self.$toastr.success('操作成功');
                                    vueIndex.$dataTable.bootstrapTable('refresh');
                                }
                            }).catch(function () {
                                swal("操作失败！", "", "error");
                            }).finally(function () {
                                swal.close();
                            });
                        }
                    });

                });
            },
            query: function () {
               // this.$dataTable.bootstrapTable('selectPage', 1);
                this.$dataTable.bootstrapTable('refresh');
            },
            //新增弹窗列表
            addModal: function () {
                addRules();
            },
            //获取门店列表和来源列表（下拉框）
            storeList: function () {
                var self = this;
                self.$http.post('/api/dataPermission/empDataPermission', {
                    permissionType: "MD",
                    jobNum: RocoUser.account
                }, {emulateJSON: true}).then(function (res) {
                    if (res.data.code == 1) {
                        self.stores = res.data.data;
                        //获取来源列表
                        self.$http.post('/api/dataPermission/empDataPermissionWithDataSource', {
                                permissionType: "SJLY",
                                jobNum: RocoUser.account
                            },
                            {emulateJSON: true}).then(function (res) {
                            if (res.data.code == 1) {
                                self.dataSources = res.data.data;

                            }
                            //获取渠道列表
                            self.$http.get('/api/dict/findSubDictListByCode?code=TGLY').then(function (res) {
                                if (res.data.code == 1) {
                                    //设置来源--从请求路径中获取
                                    self.promoteCodes = res.data.data;
                                    //然后画表
                                    this.drawTable();
                                }
                            })
                        }).catch(function () {
                        }).finally(function () {
                        });
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
function addRules() {
    var _modal = $('#addModal').clone();
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
                mixins: [RocoVueMixins.ModalMixin],
                $modal: $el,
                created: function () {
                },
                data: {
                    taskAllowautoRules:{
                        storeCode: '',
                        dataSourceCode: '',
                        promoteCodes: '',
                    },
                    modalModel: null, //模式窗体模型
                    _$el: null, //自己的 el $对象
                    _$dataTable: null,//datatable $对象
                    //门店
                    stores: vueIndex.stores,
                    //来源
                    dataSources: vueIndex.dataSources,
                    //渠道
                    promoteCodes: vueIndex.promoteCodes
                },
                methods: {
                    commitAdd: function () {
                        var self = this;
                        self.$validate(true, function () {
                            if (self.$validation.valid) {
                                self.$http.post('/api/taskRules/add', self.taskAllowautoRules).then(function (res) {
                                    if (res.data.code == 1) {
                                        $el.modal('hide');
                                        self.$destroy();
                                        self.$toastr.success('操作成功');
                                        vueIndex.$dataTable.bootstrapTable('refresh');
                                    }
                                }).catch(function () {
                                }).finally(function () {
                                });
                            }
                        })
                    }
                },
                ready: function () {
                }
            });
            // 创建的Vue对象应该被返回
            return vueModal;
        });

}