webpackJsonp([4],{14:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=o(172),a=[o(173)],i=[o(187),o(188)];t.default=s.createStore(a,i)},15:function(e,t,o){o(183),o(186).shim()},175:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=o(14);t.default={name:"footer",props:{active:{type:Number,default:0}},data:function(){return{}},methods:{goBack:function(){s.default.remove("indextab"),window.location.href="appindex.html#/tab/0"}},created:function(){this.activeIndex=this.active}}},176:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default={name:"modal",props:{show:{type:Boolean,default:!1},time:{type:Number,default:1e3},auto:{type:Boolean,default:!0}},data:function(){return{}},watch:{auto:function(e){var t=this;this.visiable&&e&&window.setTimeout(function(){t.$emit("update:show",!1)},this.time)},visiable:function(e){var t=this;this.auto&&e&&window.setTimeout(function(){t.$emit("update:show",!1)},this.time)}},computed:{visiable:function(){return this.show}}}},184:function(e,t){},185:function(e,t){},189:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("transition",{attrs:{name:"router-fade",mode:"out-in"}},[o("div",{directives:[{name:"show",rawName:"v-show",value:e.visiable,expression:"visiable"}],class:["tanboxbg",e.visiable?"":"hidden"]},[o("div",{staticClass:"tanbox"},[e._t("default")],2)])])},staticRenderFns:[]}},190:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{staticClass:"pos-bottom"},[o("div",{staticClass:"go-back",on:{click:e.goBack}},[o("i",{staticClass:"fa fa-reply",attrs:{"aria-hidden":"true"}})])])},staticRenderFns:[]}},193:function(e,t){e.exports=rocoui},194:function(e,t){},203:function(e,t){e.exports=window.jQuery},23:function(e,t,o){function s(e){o(184)}var a=o(4)(o(176),o(189),s,null,null);e.exports=a.exports},237:function(e,t,o){function s(e){return o(a(e))}function a(e){var t=i[e];if(!(t+1))throw new Error("Cannot find module '"+e+"'.");return t}var i={"./af":53,"./af.js":53,"./ar":60,"./ar-dz":54,"./ar-dz.js":54,"./ar-kw":55,"./ar-kw.js":55,"./ar-ly":56,"./ar-ly.js":56,"./ar-ma":57,"./ar-ma.js":57,"./ar-sa":58,"./ar-sa.js":58,"./ar-tn":59,"./ar-tn.js":59,"./ar.js":60,"./az":61,"./az.js":61,"./be":62,"./be.js":62,"./bg":63,"./bg.js":63,"./bm":64,"./bm.js":64,"./bn":65,"./bn.js":65,"./bo":66,"./bo.js":66,"./br":67,"./br.js":67,"./bs":68,"./bs.js":68,"./ca":69,"./ca.js":69,"./cs":70,"./cs.js":70,"./cv":71,"./cv.js":71,"./cy":72,"./cy.js":72,"./da":73,"./da.js":73,"./de":76,"./de-at":74,"./de-at.js":74,"./de-ch":75,"./de-ch.js":75,"./de.js":76,"./dv":77,"./dv.js":77,"./el":78,"./el.js":78,"./en-au":79,"./en-au.js":79,"./en-ca":80,"./en-ca.js":80,"./en-gb":81,"./en-gb.js":81,"./en-ie":82,"./en-ie.js":82,"./en-nz":83,"./en-nz.js":83,"./eo":84,"./eo.js":84,"./es":87,"./es-do":85,"./es-do.js":85,"./es-us":86,"./es-us.js":86,"./es.js":87,"./et":88,"./et.js":88,"./eu":89,"./eu.js":89,"./fa":90,"./fa.js":90,"./fi":91,"./fi.js":91,"./fo":92,"./fo.js":92,"./fr":95,"./fr-ca":93,"./fr-ca.js":93,"./fr-ch":94,"./fr-ch.js":94,"./fr.js":95,"./fy":96,"./fy.js":96,"./gd":97,"./gd.js":97,"./gl":98,"./gl.js":98,"./gom-latn":99,"./gom-latn.js":99,"./gu":100,"./gu.js":100,"./he":101,"./he.js":101,"./hi":102,"./hi.js":102,"./hr":103,"./hr.js":103,"./hu":104,"./hu.js":104,"./hy-am":105,"./hy-am.js":105,"./id":106,"./id.js":106,"./is":107,"./is.js":107,"./it":108,"./it.js":108,"./ja":109,"./ja.js":109,"./jv":110,"./jv.js":110,"./ka":111,"./ka.js":111,"./kk":112,"./kk.js":112,"./km":113,"./km.js":113,"./kn":114,"./kn.js":114,"./ko":115,"./ko.js":115,"./ky":116,"./ky.js":116,"./lb":117,"./lb.js":117,"./lo":118,"./lo.js":118,"./lt":119,"./lt.js":119,"./lv":120,"./lv.js":120,"./me":121,"./me.js":121,"./mi":122,"./mi.js":122,"./mk":123,"./mk.js":123,"./ml":124,"./ml.js":124,"./mr":125,"./mr.js":125,"./ms":127,"./ms-my":126,"./ms-my.js":126,"./ms.js":127,"./mt":128,"./mt.js":128,"./my":129,"./my.js":129,"./nb":130,"./nb.js":130,"./ne":131,"./ne.js":131,"./nl":133,"./nl-be":132,"./nl-be.js":132,"./nl.js":133,"./nn":134,"./nn.js":134,"./pa-in":135,"./pa-in.js":135,"./pl":136,"./pl.js":136,"./pt":138,"./pt-br":137,"./pt-br.js":137,"./pt.js":138,"./ro":139,"./ro.js":139,"./ru":140,"./ru.js":140,"./sd":141,"./sd.js":141,"./se":142,"./se.js":142,"./si":143,"./si.js":143,"./sk":144,"./sk.js":144,"./sl":145,"./sl.js":145,"./sq":146,"./sq.js":146,"./sr":148,"./sr-cyrl":147,"./sr-cyrl.js":147,"./sr.js":148,"./ss":149,"./ss.js":149,"./sv":150,"./sv.js":150,"./sw":151,"./sw.js":151,"./ta":152,"./ta.js":152,"./te":153,"./te.js":153,"./tet":154,"./tet.js":154,"./th":155,"./th.js":155,"./tl-ph":156,"./tl-ph.js":156,"./tlh":157,"./tlh.js":157,"./tr":158,"./tr.js":158,"./tzl":159,"./tzl.js":159,"./tzm":161,"./tzm-latn":160,"./tzm-latn.js":160,"./tzm.js":161,"./uk":162,"./uk.js":162,"./ur":163,"./ur.js":163,"./uz":165,"./uz-latn":164,"./uz-latn.js":164,"./uz.js":165,"./vi":166,"./vi.js":166,"./x-pseudo":167,"./x-pseudo.js":167,"./yo":168,"./yo.js":168,"./zh-cn":169,"./zh-cn.js":169,"./zh-hk":170,"./zh-hk.js":170,"./zh-tw":171,"./zh-tw.js":171};s.keys=function(){return Object.keys(i)},s.resolve=a,e.exports=s,s.id=237},24:function(e,t,o){var s=o(4)(o(26),o(29),null,null,null);e.exports=s.exports},25:function(e,t,o){var s=o(4)(o(34),o(38),null,null,null);e.exports=s.exports},26:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=o(14);t.default={name:"footer",props:{active:{type:Number,default:0}},data:function(){return{items:[{name:"groups",text:"我的客户"},{name:"schedule",text:"日程"},{name:"upstage",text:"我的"}],activeIndex:null}},methods:{handleClick:function(e){this.activeIndex=e;var t="appindex.html";t=1===this.activeIndex?"calendar.html":2===this.activeIndex?"my.html":"appindex.html#/tab/0",s.default.remove("indextab"),this.$emit("footer-click",e),window.location.href=t}},created:function(){this.activeIndex=this.active}}},27:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default={props:{show:{type:Boolean,required:!1,default:function(){return!1}}}}},28:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{directives:[{name:"show",rawName:"v-show",value:e.show,expression:"show"}],staticClass:"loading"},[o("p",[o("img",{attrs:{src:"/static/img/loading.gif"}}),e._v(" "),o("br"),e._v(" "),e._t("default",[e._v("正在加载，请稍后...")])],2)])},staticRenderFns:[]}},29:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("footer",[o("div",{staticClass:"row foot-nav"},e._l(e.items,function(t,s){return o("div",{staticClass:"col-xs-4 foot-list"},[o("a",{attrs:{href:"javascript:void(0);"},on:{click:function(t){e.handleClick(s)}}},[o("span",{class:[t.name,s===e.activeIndex?"on":""]}),o("br"),e._v("\n            "+e._s(t.text)+"\n            ")])])}))])},staticRenderFns:[]}},34:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default={name:"header",data:function(){return{}}}},38:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("header",[o("h3",[e._t("default")],2)])},staticRenderFns:[]}},395:function(e,t,o){function s(e){o(748)}var a=o(4)(o(446),o(828),s,null,null);e.exports=a.exports},41:function(e,t,o){function s(e){o(185)}var a=o(4)(o(175),o(190),s,"data-v-da81a624",null);e.exports=a.exports},417:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=o(17),a=o(395),i=o.n(a),n=o(217);o(15),s.a.use(n.a);var r=new n.a({routes:[]});s.a.config.productionTip=!1,new s.a({el:"#app",router:r,template:"<CommunicationRecord/>",components:{CommunicationRecord:i.a}})},446:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=o(193),a=(o.n(s),o(6)),i=o.n(a),n=o(1),r=o.n(n),l=o(17),m=o(9),c=o.n(m),d=o(41),u=o.n(d),f=o(24),v=o.n(f),p=o(25),h=o.n(p),M=o(39),b=o.n(M),j=o(23),_=o.n(j),g=o(203),y=o.n(g),k=o(48);o.n(k);o(15),l.a.use(b.a),t.default={data:function(){return{minDate:r()(),storeVisible:!1,roomVisible:!1,popStore:r()(),popRoom:r()(),modal:!1,msg:"",showInvalid:!0,submitting:!1,formstate:{},touched:!1,selectDate:[],formModel:{orderId:"",orderNum:"",taskNo:"",customerNo:"",communicateMode:"",communicateType:"",invalidFlag:"1",invitationStore:"1",storeTime:r()().format("YYYY-MM-DD HH")+":00",amontRoomTime:r()().format("YYYY-MM-DD HH:mm"),customerIntention:"",customerTag:"",remark:"",currentStatus:""},selectedList:[],customerIntention:[],levelFirst:null,levelSecond:[],levels:[]}},created:function(){this.formModel.customerNo=this.query.customerNo,this.formModel.taskNo=this.query.taskNo,this.fetchAll(),this.fetchSelect(),this.fetchSelectDate()},mounted:function(){},methods:{firstClick:function(e){this.formModel.communicateMode=e,this.formModel.communicateType=""},handleEvent:function(){var e=this;if(this.formstate.$invalid)return this.touched=!0,!1;if("YY"!==this.formModel.communicateMode&&this.formModel.invitationStore&&(this.formModel.invitationStore="",this.formModel.storeTime=""),"YY"===this.formModel.communicateMode&&"1"===this.formModel.invitationStore&&""===this.formModel.storeTime)return!1;if("YYLF"!==this.formModel.communicateType)this.formModel.amontRoomTime="";else if("YYLF"===this.formModel.communicateType&&""===this.formModel.amontRoomTime)return!1;"YY"===this.formModel.communicateMode&&"1"===this.formModel.invitationStore?this.formModel.currentStatus="TALKSUCCESS":"IN"===this.formModel.communicateType?this.formModel.currentStatus="INTOSHOP":this.formModel.currentStatus="TALKING",this.$nextTick(function(){e.submitting||e.doSubmit()})},activeDatetimepicker:function(){var e=this;console.log(y()(e.$refs.storeTime)),y()(e.$refs.storeTime).datetimepicker({minView:1,format:"yyyy-mm-dd hh",todayBtn:!0,startDate:o.i(k.dateYYYYMMDDHHMMFormat)(new Date)}).on("changeDate",function(t){console.log(t),e.formModel.storeTime=r()(t.date).format("YYYY-MM-DD HH")}),y()(e.$refs.amontRoomTime).datetimepicker({minView:0,format:"yyyy-mm-dd hh:ii",todayBtn:!0,startDate:o.i(k.dateYYYYMMDDHHMMFormat)(new Date)}).on("changeDate",function(t){e.formModel.amontRoomTime=o.i(k.dateYYYYMMDDHHMMFormat)(t.date)})},doSubmit:function(){var e=this;this.submitting=!0,i.a.post("/api/customer/communicate",e.formModel).then(function(t){"1"===t.data.code&&setTimeout(function(){window.location.href="customerDetail.html#/customer/record??id="+e.query.taskNo+"&customerNo="+e.query.customerNo},1e3),e.modal=!0,e.msg=t.data.message}).finally(function(t){e.submitting=!1})},fetchSelectDate:function(){var e=this;i.a.get("/api/dict/findChildrenByCode?code=KHBQ").then(function(t){"1"===t.data.code&&(e.selectDate=t.data.data)})},fetchSelect:function(){var e=this;i.a.get("/api/customer/orderListWithHouse?taskNo="+this.query.taskNo).then(function(t){"1"===t.data.code&&(e.selectedList=t.data.data,0!==e.selectedList.length&&(e.showInvalid=!1,e.formModel.invalidFlag="1",e.formModel.orderId=e.selectedList[0].orderId,e.formModel.orderNum=e.selectedList[0].orderNum))})},fetchLevels:function(){return i.a.get("/api/dict/findChildrenByCode?code=KHJB")},fetchType:function(){return i.a.get("/api/dict/findChildrenByCode?code=GTFS")},fetchCustomer:function(){return i.a.get("/api/dict/findChildrenByCode?code=CUSTOMERINTENTION")},fetchAll:function(){var e=this;this.submitting=!0,i.a.all([this.fetchType(),this.fetchCustomer(),this.fetchLevels()]).then(i.a.spread(function(t,o,s){"1"===t.data.code&&(e.levelFirst=t.data.data,e.levelFirst.forEach(function(t){t.children.forEach(function(o){o.proId=t.code,e.levelSecond.push(o)})}),e.formModel.communicateMode=e.levelFirst[0].code),"1"===o.data.code&&(e.customerIntention=o.data.data),"1"===s.data.code&&(e.levels=s.data.data)})).finally(function(t){e.submitting=!1})}},components:{Loading:c.a,FooterComponent:v.a,HeaderComponent:h.a,Modal:_.a,GoBack:u.a,PopupDatePicker:s.PopupDatePicker},computed:{invalidDisabled:function(){return("YYLF"===this.formModel.communicateType||"YY"===this.formModel.communicateMode&&"1"===this.formModel.invitationStore)&&(this.formModel.invalidFlag="1",!0)},query:function(){return this.$route.query}},watch:{popStore:function(e){this.formModel.storeTime=r()(e).format("YYYY-MM-DD HH")+":00"},popRoom:function(e){this.formModel.amontRoomTime=r()(e).format("YYYY-MM-DD HH:mm")}}}},48:function(e,t){function o(e){return 0===e||6===e}function s(e){var t=new Date;return t.getFullYear()+"-"+t.getMonth()+"-"+t.getDate()==e.getFullYear()+"-"+e.getMonth()+"-"+e.getDate()}function a(e){return 24*e*60*60*1e3}function i(e,t){return e.getFullYear()+"-"+e.getMonth()+"-"+e.getDate()==t.getFullYear()+"-"+t.getMonth()+"-"+t.getDate()}function n(e){return e>=10?e:"0"+e}function r(e){return e.getFullYear()+"-"+n(e.getMonth()+1)+"-"+n(e.getDate())}function l(e){return e.getFullYear()+"-"+n(e.getMonth()+1)+"-"+n(e.getDate())+" "+n(e.getHours())+":"+n(e.getMinutes())}e.exports={isWeekEnd:o,isToday:s,dayToMilliseconds:a,isSelectedDate:i,dateYYYYMMDDFormat:r,dateYYYYMMDDHHMMFormat:l}},748:function(e,t){},828:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{staticClass:"calendar-test-wrapper wrap"},[o("div",{staticClass:"content"},[o("div",{staticClass:"container"},[o("vue-form",{attrs:{state:e.formstate},on:{submit:function(t){t.preventDefault(),e.handleEvent(t)}}},[o("div",{staticClass:"nc-form no-back"},[o("div",{staticClass:"form-group",class:[(e.touched||e.formstate.$touched)&&""===e.formModel.communicateMode?"has-error":""]},[o("label",{attrs:{for:"communicateMode"}},[e._v("*沟通分类")]),e._v(" "),o("br"),e._v(" "),o("div",{staticClass:"btn-box"},e._l(e.levelFirst,function(t,s){return o("button",{key:t.code,staticClass:"btn btn-default",class:{"btn-success":e.formModel.communicateMode===t.code},attrs:{type:"button"},on:{click:function(o){e.firstClick(t.code)}}},[e._v(e._s(t.text))])}))]),e._v(" "),o("div",{staticClass:"form-group",class:[(e.formstate.$touched||e.formstate.$submitted)&&""===e.formModel.communicateType?"has-error":""]},[o("label",{attrs:{for:"communicateType"}},[e._v("*沟通类型")]),e._v(" "),o("div",{staticClass:"btn-box"},e._l(e.levelSecond,function(t,s){return t.proId===e.formModel.communicateMode?o("button",{key:t.code,staticClass:"btn btn-default",class:{"btn-success":e.formModel.communicateType===t.code},attrs:{type:"button"},on:{click:function(o){e.formModel.communicateType=t.code}}},[e._v(e._s(t.text))]):e._e()})),e._v(" "),o("span",{directives:[{name:"show",rawName:"v-show",value:(e.formstate.$touched||e.formstate.$submitted)&&""===e.formModel.communicateType,expression:"(formstate.$touched || formstate.$submitted) && formModel.communicateType === ''"}],staticClass:"help-block"},[e._v("请选择沟通类型")])]),e._v(" "),e.showInvalid?o("div",{staticClass:"form-group"},[o("label",{attrs:{for:"invalidFlag"}},[e._v("无效客户")]),e._v(" "),o("br"),e._v(" "),o("label",{staticClass:"radio-inline"},[o("input",{directives:[{name:"model",rawName:"v-model",value:e.formModel.invalidFlag,expression:"formModel.invalidFlag"}],attrs:{disabled:e.invalidDisabled,type:"radio",name:"invalidFlag",value:"0"},domProps:{checked:e._q(e.formModel.invalidFlag,"0")},on:{change:function(t){e.$set(e.formModel,"invalidFlag","0")}}}),e._v(" 是\n            ")]),e._v(" "),o("label",{staticClass:"radio-inline"},[o("input",{directives:[{name:"model",rawName:"v-model",value:e.formModel.invalidFlag,expression:"formModel.invalidFlag"}],attrs:{disabled:e.invalidDisabled,type:"radio",name:"invalidFlag",value:"1"},domProps:{checked:e._q(e.formModel.invalidFlag,"1")},on:{change:function(t){e.$set(e.formModel,"invalidFlag","1")}}}),e._v(" 否\n            ")])]):e._e(),e._v(" "),o("div",{directives:[{name:"show",rawName:"v-show",value:"YY"===e.formModel.communicateMode,expression:"formModel.communicateMode === 'YY'"}],staticClass:"form-group"},[o("label",{attrs:{for:"exampleInputPassword1"}},[e._v("*邀约到店")]),e._v(" "),o("br"),e._v(" "),o("label",{staticClass:"radio-inline"},[o("input",{directives:[{name:"model",rawName:"v-model",value:e.formModel.invitationStore,expression:"formModel.invitationStore"}],attrs:{type:"radio",name:"invitationStore",value:"1"},domProps:{checked:e._q(e.formModel.invitationStore,"1")},on:{change:function(t){e.$set(e.formModel,"invitationStore","1")}}}),e._v(" 是\n            ")]),e._v(" "),o("label",{staticClass:"radio-inline"},[o("input",{directives:[{name:"model",rawName:"v-model",value:e.formModel.invitationStore,expression:"formModel.invitationStore"}],attrs:{type:"radio",name:"invitationStore",value:"0"},domProps:{checked:e._q(e.formModel.invitationStore,"0")},on:{change:function(t){e.$set(e.formModel,"invitationStore","0")}}}),e._v(" 否\n            ")])]),e._v(" "),o("div",{directives:[{name:"show",rawName:"v-show",value:"YY"===e.formModel.communicateMode&&"1"===e.formModel.invitationStore,expression:"formModel.communicateMode === 'YY' && formModel.invitationStore === '1'"}],staticClass:"form-group",class:[(e.formstate.$touched||e.formstate.$submitted)&&"1"==e.formModel.invitationStore&&""===e.formModel.storeTime?"has-error":""]},[o("label",{attrs:{for:"storeTime"}},[e._v("*到店时间")]),e._v(" "),o("br"),e._v(" "),o("input",{directives:[{name:"model",rawName:"v-model",value:e.formModel.storeTime,expression:"formModel.storeTime"}],ref:"storeTime",staticClass:"form-control",attrs:{readonly:"",id:"storeTime",name:"storeTime",type:"text"},domProps:{value:e.formModel.storeTime},on:{click:function(t){e.storeVisible=!0},input:function(t){t.target.composing||e.$set(e.formModel,"storeTime",t.target.value)}}}),e._v(" "),o("PopupDatePicker",{attrs:{minDate:e.minDate,visible:e.storeVisible,mode:"DATETIME",title:"选择时间"},on:{"update:visible":function(t){e.storeVisible=t}},model:{value:e.popStore,callback:function(t){e.popStore=t},expression:"popStore"}}),e._v(" "),(e.formstate.$touched||e.formstate.$submitted)&&"1"==e.formModel.invitationStore&&""===e.formModel.storeTime?o("span",{staticClass:"help-block"},[e._v("请选择到店时间")]):e._e()],1),e._v(" "),o("div",{directives:[{name:"show",rawName:"v-show",value:"YYLF"===e.formModel.communicateType,expression:"formModel.communicateType === 'YYLF'"}],staticClass:"form-group",class:[(e.formstate.$touched||e.formstate.$submitted)&&"YYLF"===e.formModel.communicateType&&""===e.formModel.amontRoomTime?"has-error":""]},[o("label",{attrs:{for:"amontRoomTime"}},[e._v("量房时间")]),e._v(" "),o("input",{directives:[{name:"model",rawName:"v-model",value:e.formModel.amontRoomTime,expression:"formModel.amontRoomTime"}],ref:"amontRoomTime",staticClass:"form-control",attrs:{readonly:"",id:"amontRoomTime",name:"amontRoomTime",type:"text"},domProps:{value:e.formModel.amontRoomTime},on:{click:function(t){e.roomVisible=!0},input:function(t){t.target.composing||e.$set(e.formModel,"amontRoomTime",t.target.value)}}}),e._v(" "),o("PopupDatePicker",{attrs:{visible:e.roomVisible,minDate:e.minDate,mode:"DATETIME",title:"选择时间"},on:{"update:visible":function(t){e.roomVisible=t}},model:{value:e.popRoom,callback:function(t){e.popRoom=t},expression:"popRoom"}}),e._v(" "),(e.formstate.$touched||e.formstate.$submitted)&&"YYLF"===e.formModel.communicateType&&""===e.formModel.amontRoomTime?o("span",{staticClass:"help-block"},[e._v("请选择量房时间")]):e._e()],1),e._v(" "),o("label",{attrs:{for:"exampleInputEmail1"}},[e._v("客户级别")]),e._v(" "),o("br"),e._v(" "),o("div",{staticClass:"btn-box"},e._l(e.levels,function(t,s){return o("button",{key:t.code,staticClass:"btn btn-default",class:{"btn-success":e.formModel.customerIntention===t.code},attrs:{type:"button"},on:{click:function(o){e.formModel.customerIntention=t.code}}},[e._v(e._s(t.text))])})),e._v(" "),0!==e.selectedList.length?o("div",{staticClass:"form-group"},[o("label",{attrs:{for:"exampleInputEmail1"}},[e._v("选择订单")]),e._v(" "),o("select",{directives:[{name:"model",rawName:"v-model",value:e.formModel.orderId,expression:"formModel.orderId"}],staticClass:"form-control",on:{change:function(t){var o=Array.prototype.filter.call(t.target.options,function(e){return e.selected}).map(function(e){return"_value"in e?e._value:e.value});e.$set(e.formModel,"orderId",t.target.multiple?o:o[0])}}},e._l(e.selectedList,function(t,s){return o("option",{domProps:{value:t.id}},[e._v(e._s(t.provinceName||"")+e._s(t.cityName||"")+e._s(t.areaName||"")+e._s(t.address||""))])}))]):e._e(),e._v(" "),o("div",{staticClass:"form-group"},[o("label",{attrs:{for:"exampleInputPassword1"}},[e._v("客户标签")]),e._v(" "),o("br"),e._v(" "),o("div",{staticClass:"btn-box"},e._l(e.selectDate,function(t,s){return o("button",{key:t.code,staticClass:"btn btn-default",class:{"btn-success":e.formModel.customerTag===t.code},attrs:{type:"button"},on:{click:function(o){e.formModel.customerTag=t.code}}},[e._v(e._s(t.text))])}))]),e._v(" "),o("div",{staticClass:"form-group",class:[(e.formstate.$touched||e.formstate.$submitted)&&e.formstate.remark.$invalid?"has-error":""]},[o("label",{attrs:{for:"remark"}},[e._v("*沟通内容")]),e._v(" "),o("validate",[o("textarea",{directives:[{name:"model",rawName:"v-model",value:e.formModel.remark,expression:"formModel.remark"}],staticClass:"form-control",attrs:{id:"remark",name:"remark",maxlength:"250",required:""},domProps:{value:e.formModel.remark},on:{input:function(t){t.target.composing||e.$set(e.formModel,"remark",t.target.value)}}}),e._v(" "),o("field-messages",{attrs:{name:"remark"}},[o("div",{directives:[{name:"show",rawName:"v-show",value:e.formstate.$touched||e.formstate.$submitted,expression:"formstate.$touched || formstate.$submitted"}],attrs:{slot:"required"},slot:"required"},[o("span",{staticClass:"help-block"},[e._v("请填写沟通内容")])]),e._v(" "),o("div",{directives:[{name:"show",rawName:"v-show",value:e.formstate.$touched||e.formstate.$submitted,expression:"formstate.$touched || formstate.$submitted"}],attrs:{slot:"maxlength"},slot:"maxlength"},[o("span",{staticClass:"help-block"},[e._v("不能输入超过250个字")])])])],1)],1),e._v(" "),o("button",{staticClass:"btn btn-default",attrs:{disabled:e.submitting,type:"submit"}},[e._v("保存")])])])],1)]),e._v(" "),o("go-back"),e._v(" "),o("Loading",{attrs:{show:e.submitting}}),e._v(" "),o("modal",{attrs:{show:e.modal},on:{"update:show":function(t){e.modal=t}}},[o("div",{staticClass:"text-center"},[e._v("\n      "+e._s(e.msg)+"\n    ")])])],1)},staticRenderFns:[]}},9:function(e,t,o){var s=o(4)(o(27),o(28),null,null,null);e.exports=s.exports}},[417]);
//# sourceMappingURL=communicationRecord.4910bf8c5ce603bcd6d1.js.map