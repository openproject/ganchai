<?php 
/**
CREATE TABLE  `app_ganchai`.`gc_version` (
`id` INT( 11 ) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT  '主键',
`vercode` VARCHAR( 16 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT  '版本号',
`vername` VARCHAR( 64 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT  '版本名',
`download` VARCHAR( 128 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT  '下载地址',
`log` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT  '更新日志',
`force` TINYINT( 2 ) NOT NULL DEFAULT  '1' COMMENT  '1强制升级；2不强制升级',
`create_time` DATETIME NOT NULL DEFAULT  '2015-06-29 17:57:57' COMMENT  '创建时间',
`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT  '更新时间'
) ENGINE = MYISAM CHARACTER SET utf8 COLLATE utf8_general_ci;
*/
namespace Api\Controller;
class VersionController extends ApiController {
    public function indexAction(){
		$digest = M('Version');
		$data = $digest->field('vercode, vername, download, log, update_time as time')->order('update_time desc, id desc')->find();
		if(!$data) {$this::jsonOut(["status"=>3, "message"=>"数据库不存在版本信息"]);die();}
		$this::jsonOut(["status"=>1, "message"=>"Success", "data"=>$data]);
    }
}