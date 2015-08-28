<?php
/**
 * Created by PhpStorm.
 * User: Lu
 * Date: 2015-08-01
 * Time: 11:00
 */

namespace Admin\Controller;


class UserController extends AdminController{

    public function loginAction(){
        if(IS_POST){
            $username = $_POST['username'];
            $password = md5($_POST['password']);
            $bool = $this->doLogin($username, $password);
            $target = __ROOT__.'/'.MODULE_NAME.'/Index';
            if($bool) redirect($target);
        }
        $this->display();
    }

    private function doLogin($username, $password){
        $mUser = M("user");
        $user = $mUser->field('id uid, username, role_id rid')->where("username = '{$username}' and password = '{$password}'")->find();
        if($user) {
            $this->setSession("user", $user);
            return true;
        }
        return false;
    }
}