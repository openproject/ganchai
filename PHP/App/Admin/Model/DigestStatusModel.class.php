<?php

/**
 * Created by PhpStorm.
 * User: Lu
 * Date: 2015-08-09
 * Time: 9:12
 */
namespace Admin\Model;
use Think\Model;
class DigestStatusModel extends Model {

    public function getList(){
        $data = $this->field("*")->order('id asc')->select();
        return $data;
    }

}