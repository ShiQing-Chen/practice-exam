
CREATE DATABASE `practice_exam`;

USE `practice_exam`;

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `login_name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '登录账户',
  `login_pass` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '加密密码',
  `nick_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像地址',
  `user_type` smallint(6) DEFAULT NULL COMMENT '用户类型:1管理员，2教师，3学生',
  `gender` smallint(6) DEFAULT NULL COMMENT '性别:1男，2女',
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户手机号码',
  `user_status` smallint(6) NOT NULL COMMENT '状态0:未启用;1:已启用',
  `refresh_token` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '刷新token',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_login_name` (`login_name`),
  UNIQUE KEY `uk_mobile` (`mobile`),
  UNIQUE KEY `uk_refresh_token` (`refresh_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '基础用户信息表';

DROP TABLE IF EXISTS `teacher_info`;

CREATE TABLE `teacher_info` (
  `teacher_id` bigint(20) NOT NULL COMMENT '教师ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `course_id` bigint(20) NOT NULL COMMENT '课程',
  `teacher_number` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '教师工号',
  `teacher_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '教师名称',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`teacher_id`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  UNIQUE KEY `uk_teacher_number` (`teacher_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '教师信息表';

DROP TABLE IF EXISTS `student_info`;

CREATE TABLE `student_info` (
  `student_id` bigint(20) NOT NULL COMMENT '学生ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `student_number` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学生学号',
  `student_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学生姓名',
  `class_id` bigint(20) NOT NULL COMMENT '班级id',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`student_id`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  UNIQUE KEY `uk_student_number` (`student_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '学生信息表';

DROP TABLE IF EXISTS `class_info`;

CREATE TABLE `class_info` (
  `class_id` bigint(20) NOT NULL COMMENT '班级ID',
  `grade` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '年级',
  `major_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '专业',
  `class_name` varchar(50) DEFAULT NULL COMMENT '班级名称',
  `teacher_id` bigint(20) DEFAULT NULL COMMENT '教师id',
  `student_num` int(11) DEFAULT NULL COMMENT '学生数量',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '班级信息表';

DROP TABLE IF EXISTS `course_info`;

CREATE TABLE `course_info` (
  `course_id` bigint(20) NOT NULL COMMENT '课程ID',
  `course_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程名称',
  `cover` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '封面地址',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `uk_course_name` (`course_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '课程信息表';

DROP TABLE IF EXISTS `notice_info`;

CREATE TABLE `notice_info` (
  `notice_id` bigint(20) NOT NULL COMMENT '通知ID',
  `main_title` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主标题',
  `sub_title` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '副标题',
  `notice_content` varchar(1500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '通知内容',
  `notice_status` smallint(6) DEFAULT 1 COMMENT '通知状态 1草稿 2发布',
  `publish_user_id` bigint(20) DEFAULT NULL COMMENT '发布用户id',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '通知专栏表';

DROP TABLE IF EXISTS `material_info`;

CREATE TABLE `material_info` (
  `material_id` bigint(20) NOT NULL COMMENT '资料ID',
  `main_title` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主标题',
  `sub_title` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '副标题',
  `material_content` varchar(1500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资料内容',
  `material_status` smallint(6) DEFAULT 1 COMMENT '资料状态 1草稿 2发布',
  `publish_user_id` bigint(20) DEFAULT NULL COMMENT '发布用户id',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '资料信息表';

DROP TABLE IF EXISTS `article_info`;

CREATE TABLE `article_info` (
  `article_id` bigint(20) NOT NULL COMMENT '帖子ID',
  `article_title` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '帖子标题',
  `article_content` varchar(1500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '帖子内容',
  `article_type` smallint(6) DEFAULT NULL COMMENT '帖子类型',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '帖子信息表';

DROP TABLE IF EXISTS `article_comment`;

CREATE TABLE `article_comment` (
  `comment_id` bigint(20) NOT NULL COMMENT '评论ID',
  `article_id` bigint(20) NOT NULL COMMENT '帖子ID',
  `comment_content` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '帖子内容',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建用户名称',
  `create_user_avatar` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建用户头像',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `order_number` int(11) DEFAULT NULL COMMENT '评论序号',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '帖子评论信息表';

DROP TABLE IF EXISTS `message_info`;

CREATE TABLE `message_info` (
  `message_id` bigint(20) NOT NULL COMMENT '消息ID',
  `message_content` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '消息内容',
  `message_type` smallint(6) DEFAULT NULL COMMENT '消息类型',
  `accept_user_id` bigint(20) DEFAULT NULL COMMENT '接受用户id',
  `message_status` smallint(6) DEFAULT NULL COMMENT '消息状态',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`message_id`),
  key `idx_message_type` (`message_type`),
  key `idx_accept_user_id` (`accept_user_id`),
  key `idx_create_user_id` (`create_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '消息信息表';

DROP TABLE IF EXISTS `system_message_log`;

CREATE TABLE `system_message_log` (
  `message_id` bigint(20) NOT NULL COMMENT '消息ID',
  `message_content` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '消息内容',
  `accept_user_type` smallint(6) DEFAULT NULL COMMENT '接受用户类型',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间'
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '消息消息发布记录表';


DROP TABLE IF EXISTS `question_info`;

CREATE TABLE `question_info` (
  `question_id` bigint(20) NOT NULL COMMENT '试题ID',
  `question_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '试题编号',
  `course_id` bigint(20) NOT NULL COMMENT '课程ID',
  `question_type` smallint(6) DEFAULT NULL COMMENT '试题类型 1选择题 2操作题',
  `question_status` smallint(6) DEFAULT 1 COMMENT '试题状态 1草稿 2待审核 3审核未通过 4审核通过',
  `question_difficulty` smallint(6) DEFAULT 1 COMMENT '试题难易程度 1简单 2中等 3困难',
  `question_title` varchar(1000) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '题干',
  `question_answer` varchar(800) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '答案',
  `question_analyze` varchar(800) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '解析',
  `question_choice_a` varchar(800) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '选项A',
  `question_choice_b` varchar(800) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '选项B',
  `question_choice_c` varchar(800) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '选项C',
  `question_choice_d` varchar(800) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '选项D',
  `review_user_id` bigint(20) DEFAULT NULL COMMENT '审核用户id',
  `review_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`question_id`),
    key `idx_question_code` (`question_code`),
    key `idx_course_id` (`course_id`),
    key `idx_question_type` (`question_type`),
    key `idx_question_status` (`question_status`),
    key `idx_question_difficulty` (`question_difficulty`),
    key `idx_create_user_id` (`create_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '试题信息表';


DROP TABLE IF EXISTS `question_review_log`;

CREATE TABLE `question_review_log` (
  `review_id` bigint(20) NOT NULL COMMENT '审核记录ID',
  `question_id` bigint(20) NOT NULL COMMENT '试题ID',
  `review_result` smallint(6) NOT NULL COMMENT '审核结果 1通过 2不通过',
  `review_opinion` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审核意见',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`review_id`),
    key `idx_question_id` (`question_id`),
    key `idx_create_user_id` (`create_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '试题审核记录';


DROP TABLE IF EXISTS `paper_info`;

CREATE TABLE `paper_info` (
  `paper_id` bigint(20) NOT NULL COMMENT '试卷ID',
  `paper_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '试卷名称',
  `do_time` int DEFAULT NULL COMMENT '检测时长 (分钟)',
  `paper_type` smallint(6) DEFAULT NULL COMMENT '试卷类型 1考试 2测试 3练习',
  `paper_status` smallint(6) DEFAULT 1 COMMENT '试卷状态 1草稿 2发布',
  `publish_user_id` bigint(20) DEFAULT NULL COMMENT '发布用户id',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`paper_id`),
    key `idx_paper_type` (`paper_type`),
    key `idx_paper_status` (`paper_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '试卷信息表';

DROP TABLE IF EXISTS `paper_class`;
CREATE TABLE `paper_class` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `paper_id` bigint(20) NOT NULL COMMENT '试卷ID',
  `class_id` bigint(20) NOT NULL COMMENT '班级ID',
    PRIMARY KEY (`id`),
  UNIQUE KEY `uk_paper_class` (`paper_id`,`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '试卷班级关系表';