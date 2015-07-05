<?php
/**
 * Created by PhpStorm.
 * User: U.PC
 * Date: 2015/6/28
 * Time: 11:27
 */

namespace Home\Controller;
use Think\Controller;

class PagesController extends Controller {

    public function indexAction(){
        $this->display();
    }

    public function aboutAction(){
        $this->display();
    }

    public function authorAction(){
        $this->display();
    }

    public function downloadAction(){
        $this->display();
    }
}