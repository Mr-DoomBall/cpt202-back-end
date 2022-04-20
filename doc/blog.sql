CREATE DATABASE /*!32312 IF NOT EXISTS*/`blog` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog` (
  `blog_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '博客id',
  `user_id` int(11) NOT NULL COMMENT '发布用户id',
  `content` varchar(2000) DEFAULT NULL COMMENT '内容',
  `images` varchar(200) DEFAULT NULL COMMENT '图片',
  `like_num` int(11) DEFAULT NULL COMMENT '赞的次数',
  `dislike_num` int(11) DEFAULT NULL COMMENT '踩的次数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`blog_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='博客表';


insert  into `t_blog`(`blog_id`,`user_id`,`content`,`images`,`like_num`,`dislike_num`,`create_time`) values (2,1,'testsscom','D://test.jpg',NULL,NULL,'2022-04-19 23:49:13');


DROP TABLE IF EXISTS `t_friend`;

CREATE TABLE `t_friend` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `friend_id` int(11) NOT NULL COMMENT '朋友id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='朋友关系表';


DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(60) DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `sex` char(1) DEFAULT '2' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像路径',
  `intro` varchar(200) DEFAULT NULL COMMENT '个人简介',
  `online_status` tinyint(1) DEFAULT '0' COMMENT '在线状态 0：离线  1：在线',
  `status` tinyint(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `login_ip` varchar(128) DEFAULT NULL COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户信息表';


insert  into `t_user`(`user_id`,`user_name`,`email`,`phone`,`password`,`sex`,`avatar`,`intro`,`online_status`,`status`,`login_ip`,`login_date`,`create_time`) values (1,'tom','test@163.com',NULL,'31cae9037ee88ae90bd4102757195a02','1','http://localhost:8088/20220419/93ea56946c2e6bb1796101333573dc09.jpg','自我介绍',0,0,NULL,NULL,NULL);


