var vueInQueue = null;
+(function () {
    $('#regulationMenu').addClass('active');
    $('#employeeInQueueList').addClass('active');
    vueInQueue = new Vue({
        el: '#container',
        data: {
            breadcrumbs: [{
                path: '/',
                name: '主页'
            },
                {
                    path: '/',
                    name: '自动接单员工列表',
                    active: true
                }],
            form: {
                keyword: '',
                storeCode: '',
                sourceCode: ''
            },
            employeeInQueueList: null,
            //门店
            stores: null,
            //来源
            dataSources: null
        },
        created: function () {
        },
        ready: function () {
            this.fetchData();
            this.storeList();
        },
        filters: {
            goType: function (el) {
                if (el == 'Y') {
                    return '是';
                } else {
                    return '否';
                }
            }
        },
        methods: {
            query: function () {
                this.fetchData();
            },
            fetchData: function () {
                var self = this;
                self.$http.get('/api/employee/queueinfo?storeCode=' + self.form.storeCode +'&sourceCode=' + self.form.sourceCode + '&keyWord=' + self.form.keyword).then(function (res) {
                    if (res.data.code == 1) {
                        self.employeeInQueueList = res.data.data;
                    }
                }, function (res) {

                })
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
                        }).catch(function () {
                        }).finally(function () {
                        });
                    } else {

                    }
                }).catch(function () {
                }).finally(function () {
                });
            }
        }
    });
})();