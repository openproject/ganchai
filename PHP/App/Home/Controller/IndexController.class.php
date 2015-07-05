<?php
namespace Home\Controller;
use Think\Controller;
class IndexController extends Controller {
    public function indexAction(){
        $this->assign("data","dataTTT");
        $this->display();
    }
}
