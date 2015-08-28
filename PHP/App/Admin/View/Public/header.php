<?php
/**
 * Created by PhpStorm.
 * User: Lu
 * Date: 2015-08-09
 * Time: 17:55
 */
?>
<div class="ui inverted menu">
    <div class="header item">干柴【GanChai.org】</div>
    <a href="<?php echo $adminPath; ?>" class="active item">控制面板</a>

    <div class="ui dropdown item">
        文档管理
        <i class="dropdown icon"></i>
        <div class="menu">
            <div class="item">文档
                <div class="menu">
                    <a href="<?php echo $adminPath; ?>/article/list" class="item">列表</a>
                    <a href="<?php echo $adminPath; ?>/article/add" class="item">新增</a>
                    <div class="divider"></div>
                    <a href="<?php echo $adminPath; ?>/article/recycle" class="item">废弃</a>
                </div>
            </div>
            <a href="<?php echo $adminPath; ?>/article/post" class="item">供稿</a>
            <div class="divider"></div>
            <div class="item">分类
                <div class="menu">
                    <a href="<?php echo $adminPath; ?>/article/category/list" class="item">列表</a>
                    <a href="<?php echo $adminPath; ?>/article/category/add" class="item">新增</a>
                </div>
            </div>
        </div>
    </div>
    <div class="ui dropdown item">
        图片管理
        <i class="dropdown icon"></i>
        <div class="menu">
            <div class="item">全部</div>
            <div class="divider"></div>
            <div class="item">相册</div>
        </div>
    </div>
    <a class="item">附件管理</a>
    <div class="ui dropdown item">
        项目
        <i class="dropdown icon"></i>
        <div class="menu">
            <div class="item">干柴</div>
            <div class="item">帮助</div>
            <div class="divider"></div>
            <div class="item">源码</div>
            <div class="divider"></div>
            <div class="item">作者</div>
        </div>
    </div>
    <div class="right menu">
        <div class="item">
            <div class="ui transparent inverted icon input">
                <i class="search icon"></i>
                <input type="text" name="q" placeholder="搜索">
            </div>
        </div>
        <a href="http://www.html5.org.cn" target="_blank" class="item">H5</a>
    </div>
</div>
