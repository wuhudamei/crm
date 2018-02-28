var vueIndex = null;
+(function (RocoUtils) {
    $('#regulationMenu').addClass('active');
    //$('#csList').addClass('active');
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
            },{
                path: '/',
                name: '用户列表',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            form: {
                keyword: '',
                autoOrder: 'Y',
                storeCode: '',
                status:''
            },
            //post 提交
            user: {
                id: '',
                status: '',
                jobNum: '',
                storeCode:''
            },
            stores:null,
            //列表来源
            source: '',
            //同步按钮
            synName: '同步',
            synClass: 'btn-primary',
            synBtn: false,
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/employee/list',
                    method: 'get',
                    dataType: 'json',
                    cache: false, //去缓存
                    pagination: true, //是否分页
                    sidePagination: 'server', //服务端分页
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        return _.extend({source: self.source}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', //空数据的默认显示字符
                    striped: true, //斑马线
                    maintainSelected: true, //维护checkbox选项
                    sortName: 'id', //默认排序列名
                    sortOrder: 'desc', //默认排序方式
                    columns: [{
                        field: 'empName',
                        title: '名称',
                        align: 'center',
                        formatter: function (value, row, index) {
                            return "<a href='/admin/employee/detail?id=" + row.id +"&keyword=" + self.form.keyword
                                +"&storeCode=" + self.form.storeCode +"&source="+ self.source + "'>" + value + "</a>";
                        }
                    }, {
                        field: 'mobile',
                        title: '手机号',
                        align: 'center'
                    }, {
                        field: 'jobNum',
                        title: '员工唯一ID',
                        align: 'center',
                        visible:false
                    }, {
                        field: 'orgCode',
                        title: '员工编号',
                        align: 'center'
                    }, {
                        field: 'autoOrder',
                        title: '自动接单',
                        align: 'center',
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
                        field: 'busyThreshold',
                        title: '忙碌阈值',
                        align: 'center'
                    }, {
                        field: 'position',
                        title: '岗位',
                        align: 'center'
                    }, {
                        field: 'parentName',
                        title: '上级',
                        align: 'center'
                    }, {
                        field: 'orgName',
                        title: '门店',
                        align: 'center'
                    }, {
                        field: 'storeCode',
                        title: '门店编号',
                        align: 'center',
                        visible:false
                    },{
                        field: 'sort',
                        title: '排序',
                        align: 'center'
                    }
                    , {
                        field: 'status',
                        title: '状态',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var label = "";
                            if (value == "1") {
                                label = '启用';
                            } else if (value == "0") {
                                label = "禁用";
                            }
                            return label;
                        }
                    },
                        {
                            field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                            title: "操作",
                            align: 'center',
                            formatter: function (value, row, index) {
                                var fragment = '';
                                if (RocoUtils.hasPermission('employee:edit')){
                                    fragment += ('<button data-handle="operate-edit" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-primary">编辑</button>');
                                }
                                if (RocoUtils.hasPermission('employee:permission')){
                                    fragment += ('<button data-handle="operate-permission" data-id="' + row.jobNum + '" type="button" class="m-r-xs btn btn-xs btn-primary">设置数据权限</button>');
                                }
                                if (RocoUtils.hasPermission('employee:orderSource')) {
                                    fragment += ('<button data-handle="operate-orderSource" data-id="' + row.jobNum + '" type="button" class="m-r-xs btn btn-xs btn-primary">设置接单来源</button>');
                                }
                                fragment += ('<button data-handle="operate-schedule" data-id="' + row.jobNum + '" type="button" class="m-r-xs btn btn-xs btn-warning">日程</button>');
                                if (RocoUtils.hasPermission('employee:reward')) {
                                    fragment += ('<button data-handle="operate-reward" data-id="' + row.jobNum + '" type="button" class="m-r-xs btn btn-xs btn-warning">设置奖励</button>');
                                }
                                //只有管理员是才能 启用/禁用
                                if(self.source == "list"){
                                    if (row.status == '1') {
                                        fragment += ('<button data-handle="operate-disable" ' +
                                        'data-id="' + row.id + '"  data-num="' + row.jobNum + '" data-store="' + row.storeCode + '"  type="button" class="m-r-xs btn btn-xs btn-danger">禁用</button>');
                                    }
                                    if (row.status == '0') {
                                        fragment += ('<button data-handle="operate-enabled" data-id="' + row.id + '" data-num="' + row.jobNum + '" type="button" class="m-r-xs btn btn-xs btn-primary">启用</button>');
                                    }
                                }
                                if (RocoUtils.hasPermission('employee:delete')) {
                                    fragment += ('<button data-handle="operate-delete" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-danger">删除</button>');
                                }
                                return fragment;
                            }
                        }]
                });

            },
            bindEvent:function(){
                var self = this;
                //编辑
                self.$dataTable.on('click', '[data-handle="operate-edit"]', function (e) {
                    var id = $(this).data('id');
                    window.location.href = '/admin/employee/edit?id=' + id+"&keyword="
                        + self.form.keyword+"&storeCode="+self.form.storeCode
                        + "&source=" + self.source;
                });
                //删除
                self.$dataTable.on('click', '[data-handle="operate-delete"]', function (e) {
                    var id = $(this).data('id');
                    swal({
                        title:'确定删除该员工信息，删除信息后不可恢复' ,
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        closeOnConfirm: false
                    }, function (isConfirm) {
                        if (isConfirm) {
                            self.$http.get('/api/employee/delete/'+id).then(function (res) {
                                if (res.data.code == 1) {
                                    self.$toastr.success('删除成功');
                                    self.$dataTable.bootstrapTable('refresh');
                                }
                            }).catch(function () {
                                swal("操作失败！", "", "error");
                            }).finally(function () {
                                swal.close();
                            });
                        }
                    });

                });
                //禁用启用
                self.$dataTable.on('click', '[data-handle="operate-disable"]', function (e) {
                    //设置参数
                    self.user.id = $(this).data('id');
                    self.user.jobNum= $(this).data('num');
                    self.user.storeCode= $(this).data('store');
                    self.user.status = '0';
                    self.updateStatus (0);
                });
                self.$dataTable.on('click', '[data-handle="operate-enabled"]', function (e) {
                    //设置参数
                    self.user.id = $(this).data('id');
                    self.user.jobNum= $(this).data('num');
                    self.user.status = '1';
                    self.updateStatus (1);
                });
                //日程
                self.$dataTable.on('click', '[data-handle="operate-schedule"]', function (e) {
                    var id = $(this).data('id');
                    window.location.href = '/admin/schedule/list?id=' + id+"&keyword="+self.form.keyword
                        +"&storeCode="+self.form.storeCode + "&source=" + self.source;

                })
                //设置奖励
                self.$dataTable.on('click', '[data-handle="operate-reward"]', function (e) {
                    var id = $(this).data('id');
                    window.location.href = '/admin/employee/rewardList?id=' + id+"&keyword="
                        +self.form.keyword+"&storeCode="+self.form.storeCode + "&source="+ self.source;;

                })
                //设置数据权限
                self.$dataTable.on('click', '[data-handle="operate-permission"]', function (e) {
                    var id = $(this).data('id');
                    var _$modal = $('#permissionModal').clone();
                    var $modal = _$modal.modal({
                        height: 350,
                        maxHeight: 250,
                        backdrop: 'static',
                        keyboard: false
                    });
                    permissionModal($modal, id);
                })
                //设置接单来源
                self.$dataTable.on('click', '[data-handle="operate-orderSource"]', function (e) {
                    var id = $(this).data('id');
                    var _$modal = $('#orderModal').clone();
                    var $modal = _$modal.modal({
                        height: 200,
                        maxHeight: 250,
                        backdrop: 'static',
                        keyboard: false
                    });
                    orderModal($modal, id);
                })
            },
            //禁用启用的方法
            updateStatus:function (status) {
                var title=""
                if(status==0){
                    title="你确定将该用户改为禁用状态吗?";
                }
                if(status==1){
                    title="你确定将该用户改为启用状态吗?";
                }
                swal({
                    title:title ,
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    closeOnConfirm: false
                }, function (isConfirm) {
                    if (isConfirm) {
                        self.$http.post('/api/employee/update', self.user).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                self.$dataTable.bootstrapTable('refresh');
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
                //this.$dataTable.bootstrapTable('selectPage', 1);
                this.$dataTable.bootstrapTable('refresh');
            },
            createBtnClickHandler: function (e) {
                window.location.href = '/admin/employee/add';
            },
            //获取门店列表
            storeList:function(){
                self=this;
                self.$http.post('/api/dataPermission/empDataPermission', {permissionType:"MD",jobNum:RocoUser.account},{emulateJSON: true}).then(function (res) {
                    if (res.data.code == 1) {
                        self.stores=res.data.data;
                        var keyword = RocoUtils.parseQueryString()["keyword"];
                        var storeCode = RocoUtils.parseQueryString()["storeCode"];
                        //设置门店
                        //若有传过来的则设置
                        if(storeCode!=null && storeCode!=''){
                            self.form.storeCode=storeCode;
                        }
                        //默认设置查询为下拉框的第一
                        else if(self.stores!=null && self.stores.length > 0){
                            self.form.storeCode= self.stores[0].code;
                        }
                        //设置搜索关键字
                        if(keyword!=null&&keyword!=""){
                            self.form.keyword=keyword;
                        }
                        //然后画表
                        this.drawTable();
                    } else {

                    }
                }).catch(function () {

                }).finally(function () {

                });

            },
            //初始化参数
            loadParams: function () {
                var self = this;
                self.source = RocoUtils.parseQueryString()["source"];
                if(!self.source){
                    self.$toastr.error("参数source缺失,请重新尝试!");
                }
                if(self.source == "list"){
                    //管理员
                    $('#csList').addClass('active');
                    self.breadcrumbs[2].name = "用户管理";
                }else{
                    //普通人员查看列表
                    $('#commonList').addClass('active');
                }
            },
            //同步用户
            synUser: function () {
                var self = this;
                self.synName = "同步中...";
                self.synClass = "btn-warning";
                self.synBtn = true;

                self.$http.get('/api/employee/synUser').then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        self.$toastr.success('操作成功');
                        //刷新列表
                        setTimeout(function () {
                            self.query();
                        },1000);
                    }
                    self.synName = "同步";
                    self.synClass = "btn-primary";
                    self.synBtn = false;
                });
            },
        },
        created: function () {
            this.fUser = window.RocoUser;
            this.loadParams();
        },
        ready: function () {
            this.storeList();
            var self = this
            window.setTimeout(function () {
                self.bindEvent()
            },1000)

        }
    });
    //设置权限的弹窗
    function permissionModal($el, id) {
        //获取node
        var el = $el.get(0);

        //创建Vue对象编译节点
        var vueModal = new Vue({
                el: el,
                minxins: [RocoVueMixins.ModalMixin],
                $modal: $el, //模式窗体 jQuery 对象
                data: {
                    permissions: {},
                    selectedPermissions: {}
                },
                created: function () {
                    this.getRoles(id);
                },
                ready: function () {
                },
                methods: {
                    //查询用户角色信息
                    getRoles: function (jobNum) {
                        var self = this;
                        self.$http.get('/api/dataPermission/list?jobNum=' + jobNum).then(function (res) {
                            if (res.data.code == 1) {
                                self.permissions = res.data.data;
                                self.setCheckedPermissions();//将该角色已有的权限添加到已选列表中
                            }
                        }).catch(function () {

                        }).finally(function () {

                        });
                    },
                    setCheckedPermissions: function () {
                        var self = this;
                        if (self.permissions) {
                            for (var module in self.permissions) {
                                if (self.permissions[module]) {
                                    var permissions = self.permissions[module];
                                    $(permissions).each(function (index, _item) {
                                        if (_item.checked == true) {
                                            self.selectedPermissions[_item.code] =
                                                {
                                                    code: _item.code,
                                                    moduleCode: _item.moduleCode
                                                };
                                        }
                                    });
                                }
                            }
                        }
                    },
                    //所有权限选择
                    selAllCb: function (permissions, e) {
                        var self = this;
                        _.each(permissions,
                            function (permission, index, array) {
                                self.checkAll(permission, e);
                            });
                    },
                    checkAll: function (permission, e) {
                        var self = this;
                        var checked = e.target.checked;
                        _.each(permission,
                            function (content, index, array) {
                                content.checked = checked;
                                self.checkSub(content, e);
                            });
                    },
                    // 单个权限选择
                    checkSub: function (content, e) {
                        var self = this;
                        var checked = e.target.checked;
                        if (checked) {
                            self.selectedPermissions[content.code] =
                                {
                                    code: content.code,
                                    moduleCode: content.moduleCode
                                };
                        } else {
                            self.selectedPermissions[content.code] = null;
                        }
                    },
                    submit: function () {
                        var self = this;
                        var permissions = [];
                        for (var key in self.selectedPermissions) {
                            if (self.selectedPermissions[key]) {
                                permissions.push(self.selectedPermissions[key]);
                            }
                        }
                       /* if (permissions.length == 0) {
                            Vue.toastr.warning('请至少选择一个数据权限');
                            return false;
                        }*/
                        var data = {
                            jobNum: id,
                            permissions: JSON.stringify(permissions)
                        };
                        //修改权限
                        self.$http.post('/api/dataPermission/update', data, {emulateJSON: true}).then(function (res) {
                         if (res.data.code == 1) {
                         self.$toastr.success('操作成功');
                         $el.modal('hide');
                         self.$destroy();
                         }
                         }).finally(function () {
                            self.disabled = false;
                            vueIndex.query();
                            vueIndex.storeList();
                         });
                    }
                }
            }
        );
        //创建的vue对象应该被返回
        return vueModal;
    }

    //设置来源弹窗
    function orderModal($el, id) {
        //获取node
        var el = $el.get(0);

        //创建Vue对象编译节点
        var vueModalSource = new Vue({
                el: el,
                minxins: [RocoVueMixins.ModalMixin],
                $modal: $el, //模式窗体 jQuery 对象
                data: {
                    orderSources: {},
                    selectedOrderSources: {}
                },
                created: function () {
                    this.getRoles(id);
                },
                ready: function () {
                },
                methods: {
                    //查询用户接单来源
                    getRoles: function (jobNum) {
                        var self = this;
                        self.$http.get('/api/orderSource/list?jobNum=' + jobNum+"&dicCode="+"SJLY").then(function (res) {
                            if (res.data.code == 1) {
                                self.orderSources = res.data.data;
                                self.setSelectedOrderSources();//将该来源已有的添加到已选列表中
                            }
                        }).catch(function () {

                        }).finally(function () {

                        });
                    },
                    setSelectedOrderSources: function () {
                        var self = this;
                        if (self.orderSources) {
                            for (var module in self.orderSources) {
                                if (self.orderSources[module]) {
                                    var permissions = self.orderSources[module];
                                    $(permissions).each(function (index, _item) {
                                        if (_item.checked == true) {
                                            self.selectedOrderSources[_item.orderSource] ={
                                                orderSource: _item.orderSource
                                            };
                                        }
                                    });
                                }
                            }
                        }
                    },
                    //所有来源选择
                    selAllC: function (orderSources, e) {
                        var self = this;
                        _.each(orderSources,
                            function (orderSource, index, array) {
                                self.selAllCbC(orderSource, e);
                            });
                    },
                    selAllCbC: function (orderSource, e) {
                        var self = this;
                        var checked = e.target.checked;
                        _.each(orderSource,
                            function (content, index, array) {
                                content.checked = checked;
                                self.checkSubC(content, e);
                            });
                    },
                    // 单个来源选择
                    checkSubC: function (content, e) {
                        var self = this;
                        var checked = e.target.checked;
                        if (checked) {
                            self.selectedOrderSources[content.orderSource] =
                            {
                                orderSource: content.orderSource
                            };
                        } else {
                            self.selectedOrderSources[content.orderSource] = null;
                        }
                    },
                    submit: function () {
                        var self = this;
                        var orderSources = [];
                        for (var key in self.selectedOrderSources) {
                            if (self.selectedOrderSources[key]) {
                                orderSources.push(self.selectedOrderSources[key]);
                            }
                        }
                       /* if (orderSources.length == 0) {
                            Vue.toastr.warning('请至少选择一个接单来源');
                            return false;
                        }*/
                        var data = {
                            jobNum: id,
                            orderSources: JSON.stringify(orderSources)
                        };
                        //修改接单来源
                        self.$http.post('/api/orderSource/update', data, {emulateJSON: true}).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                $el.modal('hide');
                                self.$destroy();
                            }
                            else{
                                self.$toastr.error(res.data.message);
                                $el.modal('hide');
                                self.$destroy();
                            }
                        }).finally(function () {
                            self.disabled = false;
                            vueIndex.query();
                        });
                    }
                }
            }
        );
        //创建的vue对象应该被返回
        return vueModalSource;
    }
})
(this.RocoUtils);
