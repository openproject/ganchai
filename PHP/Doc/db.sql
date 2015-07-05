CREATE TABLE IF NOT EXISTS `gc_digest_type` (
    `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '自增主键',
    `t_id` int(11) DEFAULT 0 COMMENT '当前分类ID',
    `p_id` int(11) DEFAULT 0 COMMENT '父级分类ID',
    `sort` int(11) DEFAULT 0 COMMENT '排序',
    `name` VARCHAR(100) NOT NULL COMMENT '分类名称'
    );

CREATE TABLE IF NOT EXISTS `gc_digest` (
    `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '自增主键',
    `title` varchar(100) COMMENT '标题',
    `summary` text COMMENT '概要',
    `body` text COMMENT '内容',
    `thumbnail` varchar(255) COMMENT '截图',
    `type_id` int(11) DEFAULT 0 COMMENT '分类ID, 对应gc_digest_type的t_id',
    `source` varchar(255) COMMENT '来源',
    `download` varchar(255) COMMENT '下载文件(一般是APK)',
    `author` varchar(100) COMMENT '作者',
    `create_time` date COMMENT '创建时间',
    `update_time` date COMMENT '最后更新时间',
    `visit` int(11) COMMENT '访问次数',
    `rating` int(4) COMMENT '评星'
    );
