<?php
/**
 * Created by PhpStorm.
 * User: Lu
 * Date: 2015-08-01
 * Time: 12:30
 */
namespace Admin\Controller;
use Admin\Model\DigestModel;
use Admin\Model\DigestStatusModel;
use Admin\Model\DigestTypeModel;
use Think\Pager;

class ArticleController extends AdminController{

    public function indexAction(){
        $this->display();
    }

    public function listAction(){
        $page = I("get.p",1);
        $q = I("get.q","");
        $limit = 15;
        $mDigest = new DigestModel();
        $data = $mDigest->getList($page, $q, $limit);
        //$mPage = new Page($data['count'],$limit);
        $mPage = new Pager($data['count'],$limit);
        $data['page'] = $mPage->show();
        $this->assign("data", $data);
        $this->display("list");
    }

    public function editAction(){
        $id = intval(I("path.2",0));
        $mDigest = new DigestModel();
        $data = $mDigest->getDetail($id);
        $this::dataOut($data);
    }

    public function addAction(){
        $mStatus = new DigestStatusModel();
        $status = $mStatus->getList();

        $mTypes = new DigestTypeModel();
        $types = $mTypes->getList();

        if(IS_POST){
            $mArticle = M('digest');
            $post = $_POST;
            $post['create_time'] = date("Y-m-d H:i:s");
            $post['update_time'] = date("Y-m-d H:i:s");
            $post['visit'] = 1;
            $post['rating'] = 1;
            $save = $mArticle->add($post);
            if($save){
                $this->success('新增成功', 'list');
            } else {
                $this->error('新增失败');
            }
            return;
        }
        $data['types'] = $types;
        $data['status'] = $status;
        $this->assign($data);
        $this->display('add');
    }

    public function postAction(){
        $this->display();
    }

    public function categoryAction(){
        $this->display();
    }

    /**
     * BackUp
     */
    public function addAction2(){
        $mTypes = M('digest_type');
        $types = $mTypes
            ->field("id, t_id, p_id, name")
            ->where("p_id = 1")
            ->select();
        $mStatus = M('digest_status');
        $status = $mStatus
            ->field("id, s_id, name")
            ->select();
        if(IS_POST){
            $mArticle = M('digest');
            $post = $_POST;
            $post['create_time'] = date("Y-m-d H:i:s");
            $post['update_time'] = date("Y-m-d H:i:s");
            $post['visit'] = 1;
            $post['rating'] = 1;
            $this::dataOut($post);
            $save = $mArticle->add($post);
            if($save){
                echo "Success";
            } else {
                echo "Failed";
            }
            return;
        }
        $data['types'] = $types;
        $data['status'] = $status;
        $this->assign($data);
        $this->display('add', $mTypes);
    }
}
