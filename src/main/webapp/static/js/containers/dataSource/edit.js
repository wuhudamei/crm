var vueIndexAdd = null;
+(function (RocoUtils) {
    $('#dataSourceMenu').addClass('active');
    $('#sourceAdd').addClass('active');
    vueIndexAdd = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '来源管理',
            }, {
                path: '/',
                name: '来源修改',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            dataSource: {
                id: null,
                sourceName: null,
                sourceCode: null,
                autoDistribute: null,
                status: null,
                createTime: null,
                updateTime: null,
                remark: null
            },
            //添加修改的标记 1 修改 2添加
            flag: 1

        },
        methods: {
            //修改的查询
            editFlag: function () {
                var id = RocoUtils.getQueryString("id");
                self = this;
                if (id != null && id != '' && id != undefined) {
                    self.$http.get('/api/data/getOneById?id=' + id).then(function (res) {
                        if (res.data.code == 1) {
                            self.dataSource.id = res.data.data.id;
                            self.dataSource.sourceName = res.data.data.sourceName;
                            self.dataSource.sourceCode = res.data.data.sourceCode;
                            self.dataSource.remark = res.data.data.remark;
                            self.dataSource.autoDistribute = res.data.data.autoDistribute;
                            self.dataSource.status = res.data.data.status;
                            self.dataSource.createTime = res.data.data.createTime;
                            self.dataSource.updateTime = res.data.data.updateTime;
                        }
                    })
                } else { //添加
                    self.breadcrumbs[2].name = '来源添加';
                    self.flag = 2;
                    self.dataSource = {autoDistribute: '',
                                            status:''}
                }
            },

            //提交
            submitClickHandler: function () {
                var url = '';
                if (self.flag == 2) {
                    url = "/api/data/add";
                }
                if (self.flag == 1) {
                    url = "/api/data/update";
                }
                self.$validate(true, function () {
                    if (self.$validation.valid) {
                        self.disabled = true;
                        self.$http.post(url, self.dataSource).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                //搜索框的参数
                                var keyword = RocoUtils.getQueryString("keyword");
                                if (keyword==null){
                                    keyword="";
                                }
                                window.location.href = '/admin/dataSource/list?keyword='+keyword;
                            }
                        }).catch(function () {

                        }).finally(function () {

                        });
                    }
                });

            },
            //返回
            submitClickBack:function () {
                //搜索框的参数
                var keyword = RocoUtils.getQueryString("keyword");
                if(keyword==null){keyword="";}
                window.location.href = "/admin/dataSource/list?keyword="+keyword;
            }
        },
        created: function () {

        },
        ready: function () {
            this.editFlag();
        }
    });
})
(this.RocoUtils);

