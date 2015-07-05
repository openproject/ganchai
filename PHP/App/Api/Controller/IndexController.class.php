<?php
namespace Api\Controller;
class IndexController extends ApiController {
    public function indexAction(){
        $this::jsonOut();
    }

    public function rawAction()
    {
      $this::dataOut($this::getRawArray());
    }
}