<?php
/**
 * Created by PhpStorm.
 * User: U.PC
 * Date: 2015/6/28
 * Time: 1:24
 */
namespace Wechat\Controller;
use Think\Controller;
class WechatController extends Controller {
    public static function getParam($i){
        $ret = false;
        if(is_array(I('path.')) && array_key_exists($i, I('path.'))){
            $ret = I('path.')[$i];
        }
        return $ret;
    }

    public static function dataOut($data=['status'=>404,'message'=>'No Such Api']){
        header("Content-Type:text/html;charset=utf-8");
        echo "<pre>";
        print_r($data);
        echo "</pre>";
    }
}