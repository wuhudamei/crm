<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<title>测试接口</title>
<meta name="keywords" content="">
<meta name="description" content="">
</head>
<body id="app" class="fixed-sidebar full-height-layout gray-bg">
<div id="app"></div>
<script>
 var vm = new Vue({
     el: '#app',
     ready: function () {
         /*
         var data = {
//             id: 1, // 有则更新无则添加
             customerNo: '100',
             taskNo: '100',
             communicateMode: '邀约',
             communicateType: '预约量房',
             invalidFlag: '1',
             invitationStore: '1',
             storeTime: '2017-06-01 00:01',
             amontRoomTime: '2017-06-09 00:01:11',
             customerIntention: '同意',
             customerTag: '富豪更新',
             remark: '成交',
             currentStatus: "TALKSUCCESS"
         }
         this.$http.post(ctx + '/api/customer/communicate', data).then(function (res) {
             console.log(res)
         })
         */

         /*
         this.$http.post(ctx + '/wx/customer/findByTaskNo?taskNo=100').then(function (res) {
             console.log(res)
         })
         */

         /*
         var data = {
             id: 1, // 有则更新无则添加
             customerNo: '100',
             taskNo: '100',
             houseLayout: '3-3-1',
             houseArea: 20.3,
             houseType: '1',
             hoursing: '1',
             elevator: '1',
             renovationTime: '2017-06-09 00:01:11',
             provinceCode: '001',
             provinceName: '北京',
             cityCode: '002',
             cityName: '北京',
             areaCode: '003',
             areaName: '通州',
             address: '九棵树test'
         }
         this.$http.post(ctx + '/wx/customer/house', data).then(function (res) {
             console.log(res)
         })
         */

         /*
         this.$http.post(ctx + '/wx/customer/houseListByTaskNo?taskNo=100').then(function (res) {
             console.log(res)
         })
         */

         /*
         var data = {
             customerNo: "001",
             name: "Tony",
             mobile: "18900000000",
             relationship: "父子",
             remark: "这只是一个测试"
         }

         this.$http.post(ctx + '/api/contacts/add', data).then(function (res) {
             console.log(res)
         })
         */

         /*
         var data = {
             orderId: "001",
             taskNo: "001",
             backReason: "不搞了",
             backRemark: "你们的服务员太难看了",
             debitAmount: 100.3,
             amount: 100,
             store: "北京"
         }

         this.$http.post(ctx + '/api/order/returnOrder', data).then(function (res) {
             console.log(res)
         })
         */

         /*
         this.$http.get(ctx + '/api/schedule/search?date=2017-06-08').then(function (res) {
             console.log(res)
         })
         */

         /*
         var data = {
             title: "我是测试日程",
             content: "我是测试日程的内容",
             scheduleTime: "2017-06-20 12:12:12"
         }
         this.$http.post(ctx + '/api/schedule/add', data).then(function (res) {
             console.log(res)
         })
         */

         /*
         var data = {
             id: 1,
             taskNo: "100",
         }
         this.$http.get(ctx + '/api/schedule/invalidateagree', {params:data}).then(function (res) {
             console.log(res)
         })
         */

         /*
         this.$http.get(ctx + '/api/order/orderdetail?customerNo=002&taskNo=1').then(function (res) {
             var obj = JSON.parse(res.data.data[0].order);
             console.log(obj);
         })
         */

         var data = {
             "order": {
                 "activity": "1",
                 "discount": "1折",
                 "roomNum": "3",
                 "hallNum": "2",
                 "toiletNum": "1",
                 "floorArea": 118,
                 "isNew": "0",
                 "isLift": "1",
                 "city": "市",
                 "district": "区",
                 "address": "123123",
                 "comboType": "1"
             },
             "placeOrder": {
                 "comboType": "1",
                 "planMeasureTime": "2017-06-21 8:06:43",
                 "removingRepairFee": "1",
                 "remotingFee": "1",
                 "carryFee": "1",
                 "budgetAmount": "1",
                 "imprest": "1"
             },
             "remark": {"remarkContent": "1"},
             "taskNo": "2b8e223d7cb3450ebf55d11f7e683003",
             "customerNo": "SCB17062153922",
             "houseId": "44"
         }

         this.$http.post(ctx + '/api/order', data).then(function (res) {
             var obj = JSON.parse(res.data);
             console.log(obj);
         })

     }
 })
</script>
</body>
</html>