<?php
namespace Api\Controller;
class DigestController extends ApiController {
    public function indexAction(){
        $t = intval($t = $_GET['t']);
		$q = $_GET['q'];
		$where = "status = 1";
		if($q) $where = "status = 1 and (title like '%{$q}%' or summary like '{$q}')";
        if($t) $where .= " and type_id = '{$t}'";
		$digest = M('Digest');
		$count = $digest->where($where)->count();
		$currentPage = intval($_GET['p']) < 1 ? 1 : intval($_GET['p']);
		$limit = intval($_GET['size']) < 1 ? 10 : intval($_GET['size']);
		$Page = new \Think\Page($count, 10);
		$list = $digest->field('id, title, summary, thumbnail, visit, rating')->where($where)->order('id desc')->page($currentPage.','.$limit)->select();
		$data['count'] = $count;
		$data['currentPage'] = $currentPage;
		$data['allPages'] = ceil($count/$limit) < 1 ? 1 : ceil($count/$limit);
		$data['result'] = $list;
		$this::jsonOut(["status"=>1, "message"=>"Success", "data"=>$data]);
    }
	
	public function detailAction(){
		$id = intval($_GET['id']);
		if($id < 1) {$this::jsonOut(["status"=>2, "message"=>"参数错误"]);die();}
		$digest = M('Digest');
		$data = $digest->field('id, title, summary, thumbnail, visit, rating')->find($id);
		if(!$data) {$this::jsonOut(["status"=>3, "message"=>"没有该资源"]);die();}
		$this::jsonOut(["status"=>1, "message"=>"Success", "data"=>$data]);
    }
}