-- DDL

-- 部门表
CREATE TABLE `sys_department` (
`id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
`department_name` varchar(64) NOT NULL DEFAULT '' COMMENT '部门名称',
`department_alias_name` varchar(64) NOT NULL DEFAULT '' COMMENT '部门别名',
`type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '类型，1：技术；2：业务；3：行政',
`status` varchar(32) NOT NULL DEFAULT '' COMMENT '状态，0：启用，1:停用',
`create_uid` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人uid',
`create_user` varchar(64) NOT NULL DEFAULT '0' COMMENT '创建人',
`create_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:01' COMMENT '创建时间',
`update_uid` varchar(64) NOT NULL DEFAULT '0' COMMENT '修改人uid',
`update_user` varchar(32) NOT NULL DEFAULT '' COMMENT '修改人',
`update_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:01' COMMENT '修改时间',
`delete_tag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
`version` int(11) NOT NULL DEFAULT '1' COMMENT '版本号',
`last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
PRIMARY KEY (`id`) USING BTREE,
KEY `idx_sys_department_1` (`department_name`) USING BTREE,
KEY `idx_sys_department_2` (`department_alias_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='部门表';

-- 团队表
CREATE TABLE `sys_team` (
`id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
`team_name` varchar(64) NOT NULL DEFAULT '' COMMENT '项目名称',
`team_alias_name` varchar(64) NOT NULL DEFAULT '' COMMENT '项目别名',
`type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '类型，1：技术；2：业务；3：行政',
`status` varchar(32) NOT NULL DEFAULT '' COMMENT '状态，0：启用，1:停用',
`department_id` int NOT NULL COMMENT '所属部门id',
`create_uid` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人uid',
`create_user` varchar(64) NOT NULL DEFAULT '0' COMMENT '创建人',
`create_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:01' COMMENT '创建时间',
`update_uid` varchar(64) NOT NULL DEFAULT '0' COMMENT '修改人uid',
`update_user` varchar(32) NOT NULL DEFAULT '' COMMENT '修改人',
`update_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:01' COMMENT '修改时间',
`delete_tag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
`version` int(11) NOT NULL DEFAULT '1' COMMENT '版本号',
`last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
PRIMARY KEY (`id`),
KEY `idx_sys_team_1` (`team_name`) USING BTREE,
KEY `idx_sys_team_2` (`team_alias_name`) USING BTREE,
KEY `idx_sys_team_3` (`department_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='团队表';

-- 用户表
CREATE TABLE `sys_user` (
`id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
`user_name` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名称',
`phone` varchar(64) NOT NULL DEFAULT '' COMMENT '用户手机',
`password` varchar(64) NOT NULL DEFAULT '' COMMENT '用户密码',
`status` varchar(32) NOT NULL DEFAULT '' COMMENT '状态，0：启用，1:停用',
`department_id` int NOT NULL DEFAULT '0' COMMENT '所属部门id',
`team_id` int NOT NULL DEFAULT '0' COMMENT '所属团队id',
`create_uid` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人uid',
`create_user` varchar(64) NOT NULL DEFAULT '0' COMMENT '创建人',
`create_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:01' COMMENT '创建时间',
`update_uid` varchar(64) NOT NULL DEFAULT '0' COMMENT '修改人uid',
`update_user` varchar(32) NOT NULL DEFAULT '' COMMENT '修改人',
`update_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:01' COMMENT '修改时间',
`delete_tag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
`version` int(11) NOT NULL DEFAULT '1' COMMENT '版本号',
`last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
PRIMARY KEY (`id`) USING BTREE,
KEY `idx_sys_user_1` (`user_name`) USING BTREE,
KEY `idx_sys_user_2` (`phone`) USING BTREE,
KEY `idx_sys_user_3` (`department_id`) USING BTREE,
KEY `idx_sys_user_4` (`team_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

-- 生产大货订单表
CREATE TABLE `produce_order` (
`id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
`state` varchar(32) NOT NULL DEFAULT '' COMMENT '生产状态',
`order_number` varchar(30) NOT NULL DEFAULT '' COMMENT '订单号',
`sku` varchar(64) NOT NULL DEFAULT '' COMMENT 'sku',
`reference_sku` varchar(64) NOT NULL DEFAULT '' COMMENT '参考sku',
`purchase_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '生产单价',
`reference_image_url` varchar(256) NOT NULL DEFAULT '' COMMENT '参考图片地址',
`material_type_enum` smallint NOT NULL DEFAULT '0' COMMENT '面料品类',
`three_category_id` smallint NOT NULL DEFAULT '0' COMMENT '三级分类',
`special_technology_tag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0：无特殊工艺，1：特殊工艺，',
`special_technology_text` varchar(100) NOT NULL DEFAULT '' COMMENT '特殊工艺描述',
`first_order` tinyint NOT NULL DEFAULT '0' COMMENT '是否首单，0：否，1：是',
`urgent` tinyint NOT NULL DEFAULT '0' COMMENT '是否紧急，0：否，1：是',
`supplier` varchar(50) NOT NULL DEFAULT '' COMMENT '供应商',
`supplier_id` int NOT NULL DEFAULT '0' COMMENT '供应商id',
`merchandiser` varchar(20) NOT NULL DEFAULT '' COMMENT '跟单员',
`accept_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:01' COMMENT '接单时间',
`cut_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:01' COMMENT '裁剪时间',
`sew_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:01' COMMENT '车缝时间',
`sort_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:01' COMMENT '后整时间',
`create_uid` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人uid',
`create_user` varchar(64) NOT NULL DEFAULT '0' COMMENT '创建人',
`create_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:01' COMMENT '创建时间',
`update_uid` varchar(64) NOT NULL DEFAULT '0' COMMENT '修改人uid',
`update_user` varchar(32) NOT NULL DEFAULT '' COMMENT '修改人',
`update_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:01' COMMENT '修改时间',
`delete_tag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
`version` int(11) NOT NULL DEFAULT '1' COMMENT '版本号',
`last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生产大货订单表';

-- mq消息表
CREATE TABLE `sys_mq_message` (
`id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
`uuid` varchar(64) NOT NULL DEFAULT '' COMMENT 'uuid',
`message` mediumtext COMMENT '请求报文',
`service` varchar(64) NOT NULL DEFAULT '' COMMENT 'mq服务',
`queue` varchar(64) NOT NULL DEFAULT '' COMMENT 'mq队列',
`type` varchar(32) NOT NULL DEFAULT 'CONSUMER' COMMENT '消息类型，PRODUCER：发送，CONSUMER：接收',
`send_state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发送状态(0:等待发送，1：发送成功，2：死亡)',
`consume_state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '消费状态(0:等待消费，1：消费成功，2：死亡)',
`try_count` int NOT NULL DEFAULT '0' COMMENT '重试次数',
`try_time` timestamp NOT NULL DEFAULT '1970-01-01 08:00:01' COMMENT '重试时间',
`success_time` timestamp NOT NULL DEFAULT '1970-01-01 08:00:01' COMMENT '处理成功时间',
`error_log` varchar(1000) NOT NULL DEFAULT '' COMMENT '执行错误信息',
`create_user` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_user` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='mq消息表';