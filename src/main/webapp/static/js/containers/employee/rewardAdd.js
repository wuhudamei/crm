var vueIndexAdd = null;
+(function (RocoUtils) {
    $('#regulationMenu').addClass('active');
    vueIndexAdd = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '奖励添加',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            //添加的实体
            employeeOrderReward: {
                jobNo: null,
                rewardOrderNum: null,
                rewardDate: null
            }
        },
        methods: {
            // 日历初始化
            activeDatepicker: function () {
                var self = this;
                $('#rewardDate', self._$el).datetimepicker({
                    startDate: moment().add(1,'days').format('l')
                });
            },
            //提交
            submitClickHandler: function () {
                var self=this;
                var jobNo = RocoUtils.getQueryString("jobNo");
                self.employeeOrderReward.jobNo=jobNo;
                self.$validate(true, function () {
                    if (self.$validation.valid) {
                        self.disabled = true;
                        self.$http.post('/api/orderReward/add', self.employeeOrderReward).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                var keyword = RocoUtils.parseQueryString()["keyword"];
                                var storeCode = RocoUtils.parseQueryString()["storeCode"];
                                window.location.href = '/admin/employee/rewardList?id='+jobNo+"&&keyword="+keyword+"&&storeCode="+storeCode;
                            }
                        }).catch(function () {

                        }).finally(function () {
                        });
                    }
                })
            },

            //返回
            back:function () {
                window.history.go(-1);
            }

        },
        created: function () {
        },
        ready: function () {
            this.activeDatepicker();
        }
    });
})
(this.RocoUtils);
