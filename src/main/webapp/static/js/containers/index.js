var vueIndex = null;
var vueMsgList = null;
var vueContract = null;
+(function (Vue, $, _, moment, RocoUtils) {
    $('#dashboardMenu').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        mixins: [RocoVueMixins.DataTableMixin],
        data: {
            messages:[],
            noticeMessage:[],// 公告
            _$dataTable: null, // datatable $对象
            form:{
            	limit:5
            }
        },
        methods: {},
        created: function () {
        },
        ready: function () {
        }
    });


})(Vue, jQuery, _, moment, RocoUtils);

