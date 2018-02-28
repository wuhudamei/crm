var vueIndex = null;
+(function (RocoUtils) {
    $('#reportCenter').addClass('active');
    $('#reportSource').addClass('active');
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
                name: '来源报表',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            //来源下拉
            nameLables: {},
            //门店下拉
            stores: {},
            form: {
                sourceCode: '',
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
                    .on('changeDate', function(ev){
                        start= ev.date;
                        var shoudEnd=start;
                        shoudEnd.setDate(31+start.getDate());
                        $('#endDate').datetimepicker('setEndDate', shoudEnd);
                    });
                $('#endDate')
                    .datetimepicker()
                    .on('changeDate', function(ev){
                        end=ev.date;
                        var shoudStart=end;
                        shoudStart.setDate(end.getDate()-31);
                        $('#startDate').datetimepicker('setStartDate', shoudStart);
                    });
                //设置默认时间
                var date2 = new Date();
                $('#endDate').datetimepicker( 'setDate' , date2 );
                //7 天前的时间
                date2.setTime(date2-7*24*60*60*1000);
                $('#startDate').datetimepicker( 'setDate', date2 );
                self.form.startDate=$('#startDate').val();
                self.form.endDate=$('#endDate').val();

            },
            //获取来源下拉框
            getIntetface: function () {
                var self = this;
                self.$http.get('/api/data/getNameLabel').then(function (res) {
                    if (res.data.code == 1) {
                        self.nameLables = res.data.data;
                    }
                }).catch(function () {

                }).finally(function () {

                });
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
                    url: '/api/reportCenter/source',
                    method: 'get',
                    dataType: 'json',
                    cache: false, //去缓存
                    pagination: false, //是否分页
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
                        title: '来源名称',
                        align: 'center'
                    }, {
                        field: 'countLine',
                        title: '进线数量',
                        align: 'center'
                    }, {
                        field: 'countShop',
                        title: '进店数量',
                        align: 'center',
                    }, {
                        field: 'bigSet',
                        title: '大定数量',
                        align: 'center'
                    }, {
                        field: 'invitationRate',
                        title: '邀约率',
                        align: 'center',
                    }, {
                        field: 'transformationRate',
                        title: '线索转化率',
                        align: 'center',
                    }, {
                        field: 'goShopRate',
                        title: '进店转化率',
                        align: 'center',
                    }, {
                        field: 'sourceRate',
                        title: '来源贡献率',
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
            this.getIntetface();
            this.activeDatepicker();
            this.storeList();
        }
    });
})
(this.RocoUtils);