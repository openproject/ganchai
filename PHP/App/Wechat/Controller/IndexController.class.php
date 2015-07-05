<?php
namespace Wechat\Controller;
use Wechat\Model\WechatAccountModel;
use Weixin\Wechat;
class IndexController extends WechatController {
    private function wechatObj(){
        $id = intval($this::getParam(2));
        if($id < 1) die("No Access");
        $wechatAccount = new WechatAccountModel();
        $userInfo = $wechatAccount->where("uid = '{$id}'")->field('appid, token, encodingaeskey')->find();
        if(!$userInfo) die("No Access");
        //$options = ['appid'=>'wx326760c3f7a0a320', 'token'=>'street', 'encodingaeskey'=>'M9teMlgV0643cH48coJ4iPPm7LSq9zH4upj52F3DR6d'];
        $wechat = new Wechat($userInfo);
        $wechat->valid();
        return $wechat;
    }

    public function indexAction(){
      $wechat = $this->wechatObj();
      $type = $wechat->getRev()->getRevType();
      switch($type) {
        case Wechat::MSGTYPE_TEXT:$wechat->text("宝贝，不要调戏我~")->reply();exit;break;
        case Wechat::MSGTYPE_EVENT:break;
        case Wechat::MSGTYPE_IMAGE:break;
        default:$wechat->text("您的消息已经收到啦，么么哒~")->reply();
      }
    }

    public function testAction(){
        $id = intval($this::getParam(2));
        if($id < 1) die("No Access");
        $wechatAccount = new WechatAccountModel();
        $userInfo = $wechatAccount->where("uid = '{$id}'")->field('appid, token, encodingaeskey')->find();
        echo $wechatAccount->getLastSql();
        $this::dataOut($userInfo);
    }
}
