--美得你智装系统数据库变更记录
--说明：
--（1）每次只要涉及数据库更新，都需要编写更新脚本，并记录在变更记录中
--（2）标明修改的内容和修改人、时间、脚本等信息

--日期时间
--修改人：Andy
--描  述： 
-- 数据字典表  新增排序字段
--执行状态:已执行
ALTER TABLE crm_dictionary ADD COLUMN  sort int(5) COMMENT '排序' after deleted;

--日期时间
--修改人：Tony
--描  述：
-- 订单状态表：
--执行状态:
CREATE TABLE `crm_orderinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_no` varchar(20) NOT NULL COMMENT '客户编号',
  `task_no` varchar(50) NOT NULL COMMENT '任务编号',
  `order_num` varchar(20) NOT NULL COMMENT '订单编号',
  `order_id` varchar(100) NOT NULL COMMENT '订单ID',
  `house_id` int(11) NOT NULL COMMENT '房屋iD',
  `store_code` varchar(20) DEFAULT NULL COMMENT '门店编号',
  `deposit_finish` varchar(20) DEFAULT NULL COMMENT '大定是否完成 0：未完成 1：完成',
  `deposit_ableback` varchar(20) DEFAULT NULL COMMENT '定金是否可以退 0:不可；1：可以',
  `deposit_amount` decimal(18,2) DEFAULT NULL COMMENT '已交定金金额',
  `create_time` datetime DEFAULT NULL COMMENT '订单插入时间',
  PRIMARY KEY (`id`),
  KEY `TASK_NO_INDEX` (`task_no`) USING BTREE,
  KEY `ORDER_NUM_INDEX` (`order_num`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=467 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;

-- 数据初始化
insert into crm_orderinfo (house_id, customer_no, task_no, order_id, order_num, store_code, create_time)
select cch.id house_id,cch.customer_no, cch.task_no, cch.order_id, cch.order_num
,ctd.store store_code, NOW()
from crm_customer_house cch
inner join crm_task_distribute ctd on cch.task_no = ctd.task_no
where cch.order_num is not null


--2017-07-21
--修改人：邹志财(Andy)
--描  述： 修改员工表字段
--修改相关表员工号长度 为50
--执行状态: 执行

ALTER TABLE crm_employee ADD COLUMN  dep_code VARCHAR(10) COMMENT '3位部门码,与集团码联合组成8位员工号码(频繁变动)' after job_num;
ALTER TABLE crm_employee ADD COLUMN  org_code VARCHAR(15) COMMENT '5位集团码,以便以后扩展给予8位长度' after dep_code;


ALTER TABLE crm_employee MODIFY COLUMN job_num VARCHAR(50);
ALTER TABLE crm_employee_data_permission MODIFY COLUMN job_num VARCHAR(50);
ALTER TABLE crm_employee_order_source MODIFY COLUMN job_num VARCHAR(50);
ALTER TABLE crm_schedule MODIFY COLUMN job_num VARCHAR(50);
ALTER TABLE crm_employee_order_reward MODIFY COLUMN job_no VARCHAR(50);


ALTER TABLE crm_task_distribute ADD COLUMN creator VARCHAR(50);

--2017-08-09
--修改人：郭涛(Paul)
--描  述： 任务派发表 新增字段
-- 介绍人id varchar为50
--执行状态: 已执行
alter TABLE crm_task_distribute add column introducer_id varchar(50) COMMENT "介绍人id(字符串)";

--2017-10-20
--修改人：郭涛(Paul)
--描  述： 任务派发表 新增字段
-- 备注 varchar为1000
--执行状态: 未执行
alter table crm_task_distribute add remark varchar(1000) COMMENT '新建任务时的备注';



--2018-01-09
--修改人：tobi(刘鹏飞)
--描  述： crm订单概要信息增加关闭订单信息
-- 备注
--执行状态:
ALTER TABLE `crm_orderinfo`
ADD COLUMN `deposit_time`  datetime NULL COMMENT '交定金的时间，转为大定后不在覆盖更新该时间' AFTER `create_time`,
ADD COLUMN `order_closed`  tinyint NULL COMMENT '订单是否关闭，0否1是' AFTER `deposit_time`,
ADD COLUMN `order_close_time`  datetime NULL COMMENT '订单关闭时间' AFTER `order_closed`;

