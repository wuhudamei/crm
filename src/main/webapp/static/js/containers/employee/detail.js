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
                name: '客服详情',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            employee: {
                id: null,
                jobNum:null,
                empName:null,
                mobile:null,
                autoOrder:null,
                busyThreshold:null,
                storeCode:null,
                position:null,
                parentId:null,
                parentName:null,
                status: null
            },
            //url参数
            params: ''

        },
        methods: {
            //查询
            editFlag:function () {
                var id = RocoUtils.getQueryString("id");
                self = this;
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
                        self.employee.status = res.data.data.status;

                    }
                })
            },
            //返回
            submitClickBack: function () {
                var self = this;
                //搜索框的参数
                window.location.href = "/admin/employee/list?" + self.params;
            },
            //加载参数
            loadParams: function () {
                var self = this;
                var href = window.location.href;
                self.params = href.substring(href.indexOf("?"));
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

