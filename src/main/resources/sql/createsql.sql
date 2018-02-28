
--日期时间
--修改人：Tony
--描  述：
-- 订单状态表：
--执行状态:
CREATE TABLE `crm_task_allowauto_rules` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `store_code` varchar(50) DEFAULT NULL COMMENT '门店编号',
  `data_source_code` varchar(50) DEFAULT NULL COMMENT '数据来源编号',
  `promote_code` varchar(50) DEFAULT NULL COMMENT '推广渠道编号',
  `creator_id` varchar(50) DEFAULT NULL COMMENT '创建人id',
  `creator_name` varchar(50) DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;