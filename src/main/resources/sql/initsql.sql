
--给客户表的customer_no字段创建唯一索引
CREATE UNIQUE INDEX customer_no ON crm_customer(customer_no(20));

INSERT INTO `crm_apply_position` VALUES ('总监');


INSERT INTO `crm_data_permission_param` VALUES ('1', 'MD');
INSERT INTO `crm_data_permission_param` VALUES ('3', 'SJLY');


INSERT INTO `crm_data_source` VALUES ('0', '市场部', 'SCB', 'N', '1', '2017-06-07 00:00:00', '2017-06-16 11:42:21', '市场部会买客户信息，市场部也负责邀约', null, null);
INSERT INTO `crm_data_source` VALUES ('2', '话务中心', 'HWZX', 'Y', '1', null, '2017-06-16 11:41:11', '话务中心收集客户信息', 'himm893ereffdjmk', '90khje53rtfg897tyfgfdguyreafdg76t');
INSERT INTO `crm_data_source` VALUES ('3', '门店前台', 'MDQT', 'N', '1', null, '2017-06-19 16:08:13', '各个门店的前台可以创建自然进店的客户', null, null);
INSERT INTO `crm_data_source` VALUES ('4', '小美返单', 'XMFD', 'Y', '1', null, '2017-06-19 16:09:33', '产业工人推荐的客户源', 'ui6jkd89675leas67', 'reom,.fgil89wsjhk783jk2890jhk');
INSERT INTO `crm_data_source` VALUES ('5', '新媒体', 'XMT', 'Y', '1', null, '2017-06-19 16:11:50', '美得你北京店新媒体部门负责的官网', 'jiy789667_yhlpfaq3', 'kerEsejk9flE6348@Eekl23');


INSERT INTO `crm_dictionary` VALUES ('1', 'dataDict', '数据字典', '0', '数据字典 根目录', '0');
INSERT INTO `crm_dictionary` VALUES ('2', 'MD', '门店', '1', '门店', '0');
INSERT INTO `crm_dictionary` VALUES ('3', 'BJ', '北京', '2', '北京', '0');
INSERT INTO `crm_dictionary` VALUES ('4', 'HZ', '杭州', '2', '杭州门店', '0');
INSERT INTO `crm_dictionary` VALUES ('5', 'SH', '上海', '2', '上海门店', '0');
INSERT INTO `crm_dictionary` VALUES ('17', 'GTFS', '沟通方式', '1', '沟通记录的沟通方式', '0');
INSERT INTO `crm_dictionary` VALUES ('18', 'YY', '邀约', '17', '沟通的方式', '0');
INSERT INTO `crm_dictionary` VALUES ('19', 'JD', '接待', '17', '沟通的方式', '0');
INSERT INTO `crm_dictionary` VALUES ('20', 'HF', '回访', '17', '沟通的方式', '0');
INSERT INTO `crm_dictionary` VALUES ('21', 'YCYY', '一次邀约', '18', '邀约', '0');
INSERT INTO `crm_dictionary` VALUES ('22', 'ECYY', '二次邀约', '18', '邀约', '0');
INSERT INTO `crm_dictionary` VALUES ('24', 'MORE', '多次邀约', '18', '邀约', '0');
INSERT INTO `crm_dictionary` VALUES ('25', 'IN', '进店', '19', '接待', '0');
INSERT INTO `crm_dictionary` VALUES ('26', 'MOBILE', '电话', '19', '接待', '0');
INSERT INTO `crm_dictionary` VALUES ('27', 'WEIXIN', '微信', '19', '接待', '0');
INSERT INTO `crm_dictionary` VALUES ('28', 'YYLF', '预约量房', '20', '回访', '0');
INSERT INTO `crm_dictionary` VALUES ('29', 'CUSTOMERINTENTION', '客户意向', '1', '沟通记录的客户意向', '0');
INSERT INTO `crm_dictionary` VALUES ('30', 'CUSTOMERINTENTIONDEE', '深度意向', '29', '深度', '0');
INSERT INTO `crm_dictionary` VALUES ('31', 'CUSTOMERINTENTIONMID', '中度意向', '29', '意向', '0');
INSERT INTO `crm_dictionary` VALUES ('32', 'TJ', '天津', '2', '天津门店', '0');
INSERT INTO `crm_dictionary` VALUES ('33', 'GZ', '广州', '2', '广州门店', '0');
INSERT INTO `crm_dictionary` VALUES ('34', 'HB', '河北', '2', '河北门店', '0');
INSERT INTO `crm_dictionary` VALUES ('36', 'LYYCLX', '来源异常类型', '1', '接口异常类型', '0');
INSERT INTO `crm_dictionary` VALUES ('37', 'CF', '重复', '36', '即现有任务相同且状态是正常的', '0');
INSERT INTO `crm_dictionary` VALUES ('38', 'CS', '测试', '36', '测试', '0');
INSERT INTO `crm_dictionary` VALUES ('39', 'SJHMCW', '手机号码错误', '36', '手机号码错误', '0');
INSERT INTO `crm_dictionary` VALUES ('40', 'QT', '其他', '36', '其他', '0');
INSERT INTO `crm_dictionary` VALUES ('46', 'TDYY', '退单原因', '1', 'returnreason', '0');
INSERT INTO `crm_dictionary` VALUES ('47', '太贵了', '太贵了', '46', '太贵了', '0');
INSERT INTO `crm_dictionary` VALUES ('48', '太便宜了', '考虑考虑', '46', '考虑考虑', '0');
INSERT INTO `crm_dictionary` VALUES ('49', 'KHJB', '客户级别', '1', '客户级别', '0');
INSERT INTO `crm_dictionary` VALUES ('53', 'TGLY', '推广来源', '1', '推广来源 ', '0');
INSERT INTO `crm_dictionary` VALUES ('54', 'A02', '微信朋友圈', '53', '新媒体:微信朋友圈是你的圈子!', '0');
INSERT INTO `crm_dictionary` VALUES ('56', 'GUAN', '联系人关系', '1', '关系', '0');
INSERT INTO `crm_dictionary` VALUES ('57', 'FUQIN', '父亲', '56', '父亲', '0');
INSERT INTO `crm_dictionary` VALUES ('58', 'MUQIN', '母亲', '56', '母亲', '0');
INSERT INTO `crm_dictionary` VALUES ('59', 'ZHANGFU', '丈夫', '56', '丈夫', '0');
INSERT INTO `crm_dictionary` VALUES ('60', 'QIZI', '妻子', '56', '妻子', '0');
INSERT INTO `crm_dictionary` VALUES ('61', 'XRWZT', '新任务状态', '1', '前台接待/市场部,新增任务时,需指定当前任务状态', '0');
INSERT INTO `crm_dictionary` VALUES ('62', 'XRWYY', '邀约', '61', '新任务邀约', '0');
INSERT INTO `crm_dictionary` VALUES ('63', 'XRWJD', '接待', '61', '新任务接待', '0');
INSERT INTO `crm_dictionary` VALUES ('65', 'KHBQ', '客户标签', '1', '客户标签列表', '0');
INSERT INTO `crm_dictionary` VALUES ('67', 'ZHD', '串单', '65', '一个客户有多个单', '0');
INSERT INTO `crm_dictionary` VALUES ('68', 'PT', '普通', '65', '普通客户', '0');
INSERT INTO `crm_dictionary` VALUES ('76', '测试验证', 'VIP', '65', '删除在新建操作', '0');
INSERT INTO `crm_dictionary` VALUES ('78', 'friend', '朋友', '56', '朋友关系啦', '0');
INSERT INTO `crm_dictionary` VALUES ('81', 'khgh', '客户关怀', '20', '客户关怀', '0');
INSERT INTO `crm_dictionary` VALUES ('82', 'BJFTD', '北京丰台点', '2', '北京西站莲花池边上的门店，北京第一个门店', '0');
INSERT INTO `crm_dictionary` VALUES ('84', 'A01', '搜索引擎', '53', '新媒体: 搜索引擎', '0');
INSERT INTO `crm_dictionary` VALUES ('85', 'A03', '齐家网', '53', '新媒体: 齐家网', '0');
INSERT INTO `crm_dictionary` VALUES ('86', 'A04', '房天下', '53', '新媒体: 房天下', '0');
INSERT INTO `crm_dictionary` VALUES ('87', 'A05', '微信计价器', '53', '新媒体: 微信计价器', '0');
INSERT INTO `crm_dictionary` VALUES ('88', 'B01', '天猫', '53', '电商: 天猫', '0');
INSERT INTO `crm_dictionary` VALUES ('89', 'B02', '建行团购会', '53', '电商: 建行团购会', '0');
INSERT INTO `crm_dictionary` VALUES ('90', 'C01', '广播视频', '53', '传统媒体: 广播视频', '0');
INSERT INTO `crm_dictionary` VALUES ('91', 'C02', '电视频道', '53', '传统媒体: 电视频道', '0');
INSERT INTO `crm_dictionary` VALUES ('92', 'C03', '户外', '53', '传统媒体: 户外', '0');
INSERT INTO `crm_dictionary` VALUES ('93', 'C04', '平面', '53', '传统媒体: 平面', '0');
INSERT INTO `crm_dictionary` VALUES ('94', 'C05', '地铁', '53', '传统媒体: 地铁', '0');
INSERT INTO `crm_dictionary` VALUES ('95', 'C06', '公交', '53', '传统媒体: 公交', '0');
INSERT INTO `crm_dictionary` VALUES ('96', 'D01', '市场地推', '53', '市场拓展: 市场地推', '0');
INSERT INTO `crm_dictionary` VALUES ('97', 'D02', '展会', '53', '市场拓展: 展会', '0');
INSERT INTO `crm_dictionary` VALUES ('98', 'D03', '小区活动', '53', '市场拓展: 小区活动', '0');
INSERT INTO `crm_dictionary` VALUES ('99', 'D04', '大客户', '53', '市场拓展: 大客户', '0');
INSERT INTO `crm_dictionary` VALUES ('100', 'D05', '市场部回线', '53', '市场拓展: 市场部回线', '0');
INSERT INTO `crm_dictionary` VALUES ('101', 'E01', '老客户介绍', '53', '转介绍: 老客户介绍', '0');
INSERT INTO `crm_dictionary` VALUES ('102', 'E02', '员工介绍', '53', '转介绍 :员工介绍', '0');
INSERT INTO `crm_dictionary` VALUES ('103', 'E03', '产业工人介绍', '53', '转介绍: 产业工人介绍', '0');
INSERT INTO `crm_dictionary` VALUES ('104', 'F01', '自然进店', '53', '自然进店: 自然进店', '0');
INSERT INTO `crm_dictionary` VALUES ('105', 'G01', '客服地推', '53', '客服地推:客服地推', '0');
INSERT INTO `crm_dictionary` VALUES ('106', 'Z01', '其他', '53', '其他: 其他', '0');
INSERT INTO `crm_dictionary` VALUES ('107', 'Z99', '未知', '53', '未知: 未知', '0');
INSERT INTO `crm_dictionary` VALUES ('108', 'cshi ', '测试', '53', 'cs ', '0');
INSERT INTO `crm_dictionary` VALUES ('109', '123', '三次邀约', '18', '参数的', '0');
INSERT INTO `crm_dictionary` VALUES ('110', '12', '一直邀约一直邀约', '18', '2', '0');
INSERT INTO `crm_dictionary` VALUES ('111', 'A', 'A级', '49', '客户级别', '0');
INSERT INTO `crm_dictionary` VALUES ('112', 'B', 'B级', '49', 'B级', '0');
INSERT INTO `crm_dictionary` VALUES ('113', 'C', 'C级', '49', 'C级', '0');
INSERT INTO `crm_dictionary` VALUES ('114', 'N', 'N级', '49', 'N级', '0');


INSERT INTO `crm_distribute_rule` VALUES ('1', '001', '轮流派发', '按顺序,平均分配', '1', '2017-06-05 00:00:00');

INSERT INTO `crm_remind_rule` VALUES ('1', '001', '按时提醒', '每天12点提醒吃饭', '1', '2017-06-05 00:00:00');



INSERT INTO `crm_employee_data_permission` VALUES ('431', 'SJLY', 'bj000001', 'SCB', '2017-06-30 14:59:27', '26');
INSERT INTO `crm_employee_data_permission` VALUES ('432', 'SJLY', 'bj000001', 'HWZX', '2017-06-30 14:59:27', '26');
INSERT INTO `crm_employee_data_permission` VALUES ('433', 'SJLY', 'bj000001', 'MDQT', '2017-06-30 14:59:27', '26');
INSERT INTO `crm_employee_data_permission` VALUES ('434', 'SJLY', 'bj000001', 'XMFD', '2017-06-30 14:59:27', '26');
INSERT INTO `crm_employee_data_permission` VALUES ('435', 'SJLY', 'bj000001', 'XMT', '2017-06-30 14:59:27', '26');
INSERT INTO `crm_employee_data_permission` VALUES ('436', 'MD', 'bj000001', 'BJ', '2017-06-30 14:59:27', '26');
INSERT INTO `crm_employee_data_permission` VALUES ('437', 'MD', 'bj000001', 'HZ', '2017-06-30 14:59:27', '26');
INSERT INTO `crm_employee_data_permission` VALUES ('438', 'MD', 'bj000001', 'SH', '2017-06-30 14:59:27', '26');
INSERT INTO `crm_employee_data_permission` VALUES ('439', 'MD', 'bj000001', 'TJ', '2017-06-30 14:59:27', '26');
INSERT INTO `crm_employee_data_permission` VALUES ('440', 'MD', 'bj000001', 'GZ', '2017-06-30 14:59:27', '26');
INSERT INTO `crm_employee_data_permission` VALUES ('441', 'MD', 'bj000001', 'HB', '2017-06-30 14:59:27', '26');
INSERT INTO `crm_employee_data_permission` VALUES ('442', 'MD', 'bj000001', 'BJFTAD', '2017-06-30 14:59:27', '26');


update crm_employee set parent_id='bj000001' where job_num != 'bj000001';
