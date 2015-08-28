<?php

/**
 * Created by PhpStorm.
 * User: Lu
 * Date: 2015-08-09
 * Time: 9:12
 */
namespace Admin\Model;
use Think\Model;
class DigestModel extends Model {

    public function getList($page, $q="", $limit = 15){
        $data['page'] = $page;
        $data['keyword'] = $q;
        $data['count'] = $this->field("*")->where("title like '%{$q}%'")->count();
        $data['pages'] = ceil($data['count']/$limit);
        $data['list'] = $this->field("*")->order('update_time desc,id desc')->where("title like '%{$q}%'")->page("{$page},{$limit}")->select();
        return $data;
    }

    public function getDetail($id = 0) {
        $data = $this->field("*")->where("id = {$id}")->find();
        return $data;
    }

}