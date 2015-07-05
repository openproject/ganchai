<?php
/**
 * Created by PhpStorm.
 * User: U.PC
 * Date: 2015/6/28
 * Time: 2:41
 */

namespace Wechat\Controller;

class MenuController extends WechatController {
    private $appid = 'wxdab7e361390899c1';
    private $appsecret = 'a48ec7e856f4e703b26e32855e01cd8d';

    public function createAction(){
        $key = $this->access_token();
        $url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=".$key;
        $menu = '{
			"button":[{
				"name":"博客汇",
				"sub_button":[{
					"type":"view",
					"name":"技术",
					"url":"http://www.wrox.cn/channel/tech/"
				},{
					"type":"view",
					"name":"设计",
					"url":"http://www.wrox.cn/channel/design/"
				},{
					"type":"view",
					"name":"招聘",
					"url":"http://www.wrox.cn/channel/job/"
				},{
					"type":"view",
					"name":"资讯",
					"url":"http://www.wrox.cn/channel/info/"
				},{
					"type":"view",
					"name":"扯蛋",
					"url":"http://www.wrox.cn/channel/other/"
				}]
			},{
				"type":"view",
				"name":"博客",
				"url":"http://www.nianyue.com.cn"
			},{
				"name":"更多",
				"sub_button":[{
					"type":"view",
					"name":"干柴",
					"url":"https://github.com/openproject/AndroidDigest"
				},{
					"type":"click",
					"name":"点击事件",
					"key":"fenxiang"
				}]
			}]
		}';
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);
        curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, FALSE);
        curl_setopt($ch, CURLOPT_USERAGENT, 'Mozilla/5.0 (compatible; MSIE 5.01; Windows NT 5.0)');
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
        curl_setopt($ch, CURLOPT_AUTOREFERER, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $menu);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

        $info = curl_exec($ch);

        if (curl_errno($ch)) {
            echo 'Errno'.curl_error($ch);
        }

        curl_close($ch);

        var_dump($info);
    }

    public function getAction(){
        echo "get";
    }

    private function access_token(){
        $ch = curl_init("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={$this->appid}&secret={$this->appsecret}");
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true) ; // 获取数据返回
        curl_setopt($ch, CURLOPT_BINARYTRANSFER, true) ; // 在启用 CURLOPT_RETURNTRANSFER 时候将获取数据返回
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);
        curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, FALSE);
        $output = curl_exec($ch);
        $wxarray = json_decode($output,true);
        return $wxarray["access_token"];
    }

}