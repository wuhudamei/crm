var vueIndex
+(function () {
    //$('#customerMenu').addClass('active');
    //$('#addTask').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        created: function () {
        },
        ready: function () {
            this.initFunction();
        },
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '',
                name: '小美返单'
            }, {
                path: '/',
                name: '	我要推荐',
                active: true // 激活面包屑的
            }],
            //表格数据
            form: {
                keyword:'',
                introducerId: ''
            },
            //客户列表
            taskDistributeList: null,
            submitting: false,
            //版块对象
            $collapse: null

        },
        methods: {
            //初始化获取路径中的数据来源
            initFunction: function(){
                var self = this;
                //获取推荐人id
                self.form.introducerId = $("#introducerId").val();
                //获取客户列表数据
                self.search();
            },
            closeWin: function () {
                window.history.go(-1);
            },
            //列表搜索
            search: function () {
                var self = this;
                self.$http.get('/api/web/findCustomerFromIntroducerId?introducerId='
                    + self.form.introducerId ).then(function (res) {
                        console.log(res);
                    if(res.data.code == '1'){
                        self.taskDistributeList = res.data.data;
                    }
                });
            },
            goBack: function () {
                window.history.go(-1);
            }
        }
    });

})();
