<?php
namespace Api\Controller;
use Think\Controller;
class ApiController extends Controller {
    public static function jsonOut($data=['status'=>404,'message'=>'No Such Api']){
      header("Content-Type:application/json;charset=utf-8");
      echo json_encode($data);
    }

    public static function dataOut($data=['status'=>404,'message'=>'No Such Api']){
      header("Content-Type:text/html;charset=utf-8");
      echo "<pre>";
      print_r($data);
      echo "</pre>";
    }

    public static function getRawData(){
      return file_get_contents("php://input");
    }

    public static function getRawArray(){
      return json_decode(file_get_contents("php://input"), true);
    }

    public static function getPostData(){
      return $_POST;
    }

    public static function getGetData(){
      return $_GET;
    }

    public static function getRequestData(){
      return $_REQUEST; //
    }
}
