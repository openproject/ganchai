<?php
namespace Admin\Controller;
use Think\Controller;

class AdminController extends Controller {
    public function __construct(){
        parent::__construct();
        if(!$this->checkLogin()){
            $current = __SELF__;
            $target = __ROOT__.'/'.MODULE_NAME.'/user/login';
            if(!$this->startsWith($current, $target)){
                header("Location:".$target);
            }
        }
    }

    public function dataOut($data = ['status'=>404, 'message'=>'No Data']){
        echo "<pre>";
        print_r($data);
        echo "</pre>";
    }

    public function setSession($k, $v){
        session($k, $v);
    }

    public function destroySession()
    {
        session('[destroy]');
    }

    public function checkSession(){
        //
    }

    public function checkLogin(){
        if(!session('?user')) return false;
        $user = session('user');
        if(array_key_exists("uid", $user) && $user['uid'] > 0 && array_key_exists("username", $user) && strlen($user['username']) > 4 && array_key_exists("rid", $user) && $user['rid'] > 0) return true;
        return false;
    }

    /**
     * 字符串$haystack是否以$needle开头
     * @param $haystack
     * @param $needle
     * @return bool
     */
    public function startsWith($haystack, $needle)
    {
        return $needle === "" || strrpos($haystack, $needle, -strlen($haystack)) !== FALSE;
    }

    /**
     * 字符串$haystack是否以$needle结尾
     * @param $haystack
     * @param $needle
     * @return bool
     */
    public function endsWith($haystack, $needle)
    {
        return $needle === "" || (($temp = strlen($haystack) - strlen($needle)) >= 0 && strpos($haystack, $needle, $temp) !== FALSE);
    }
}