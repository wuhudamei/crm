var vueIndex = null;
+(function (RocoUtils) {
    $('#reportCenter').addClass('active');
    $('#reportPersonal').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '报表中心',
            }, {
                path: '/',
                name: '个人报表',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            //门店下拉
            stores: {},
            form: {
                startDate: '',
                endDate: '',
                storeCode: ''
            }
        },
        methods: {
            // 日历初始化
            activeDatepicker: function () {
                var self = this;
                $('#startDate', self._$el).datetimepicker({});
                $('#endDate', self._$el).datetimepicker({});
                var start;
                var end;
                $('#startDate')
                    .datetimepicker()
                    .on('changeDate', function (ev) {
                        start = ev.date;
                        var shoudEnd = start;
                        shoudEnd.setDate(31 + start.getDate());
                        $('#endDate').datetimepicker('setEndDate', shoudEnd);
                    });
                $('#endDate')
                    .datetimepicker()
                    .on('changeDate', function (ev) {
                        end = ev.date;
                        var shoudStart = end;
                        shoudStart.setDate(end.getDate() - 31);
                        $('#startDate').datetimepicker('setStartDate', shoudStart);
                    });
                //设置默认时间
                var date2 = new Date();
                $('#endDate').datetimepicker( 'setDate' , date2 );
                $('#startDate').datetimepicker( 'setDate', date2 );
                self.form.startDate=$('#startDate').val();
                self.form.endDate=$('#endDate').val();
            },
            //获取门店列表
            storeList: function () {
                var self = this;
                self.$http.post('/api/dataPermission/empDataPermission', {
                    permissionType: "MD",
                    jobNum: RocoUser.account
                }, {emulateJSON: true}).then(function (res) {
                    if (res.data.code == 1) {
                        self.stores = res.data.data;
                        if(res.data.data.length>0){
                            self.form.storeCode=res.data.data[0].code;

                        }
                    }
                }).catch(function () {

                }).finally(function () {
                    this.drawTable();
                });
            },
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                //搜索框关键字
                var keyword = RocoUtils.getQueryString("keyword");
                if (keyword == null) {
                    keyword = "";
                }
                self.form.keyword = keyword;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/reportCenter/person',
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
                        field: 'empName',
                        title: '姓名',
                        align: 'center'
                    }, {
                        field: 'cumulativeDispatch',
                        title: '累计派单量',
                        align: 'center'
                    }, {
                        field: 'currentInvitations',
                        title: '当前待邀约量',
                        align: 'center',
                    }, {
                        field: 'cumulativeUnsolicitedQuantity',
                        title: '累计已邀约量',
                        align: 'center'
                    }, {
                        field: 'totalCustomersReceived',
                        title: '累计接待客户数',
                        align: 'center',
                    }, {
                        field: 'cumulativeSmallNumber',
                        title: '累计小定数',
                        align: 'center',
                    }, {
                        field: 'cumulativeBigNumber',
                        title: '累计大定数',
                        align: 'center',
                    }, {
                        field: 'cumulativeBack',
                        title: '累计退单数',
                        align: 'center',
                    },]
                })
            },
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
                this.$dataTable.bootstrapTable('refresh');
            }
        },
        created: function () {
            this.fUser = window.RocoUser;
        },
        ready: function () {
            this.activeDatepicker();
            this.storeList();
        }
    });
})
(this.RocoUtils);