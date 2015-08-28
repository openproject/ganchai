<?php
$rootPath = $Think.__ROOT__;
$adminPath = $Think.__ROOT__.'/'.$Think.MODULE_NAME;
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title>添加干货</title>
    <!--- Site CSS -->
    <link rel="stylesheet" type="text/css" href="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/semantic.min.css">
    <script src="<?php echo $rootPath; ?>/Public/Assets/libs/jquery/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/semantic.min.js"></script>
    <!--- Example CSS -->
    <style>
        body {
            padding: 1em;
        }
        .ui.menu {
            margin: 3em 0em;
        }
        .ui.menu:last-child {
            margin-bottom: 110px;
        }
    </style>

    <!--- Example Javascript -->
    <script>
        $(document)
            .ready(function() {
                $('.ui.menu .ui.dropdown').dropdown({
                    on: 'hover'
                });
                $('.ui.menu a.item')
                    .on('click', function() {
                        $(this)
                            .addClass('active')
                            .siblings()
                            .removeClass('active')
                        ;
                    })
                ;
            })
        ;
    </script>
</head>
<body>
<?php include_once dirname(dirname(__file__))."/Public/header.php" ?>

<form class="ui two column stackable grid" method="post">
    <div class="column">
        <div class="ui form">
            <div class="field">
                <label>标题</label>
                <input type="text" name="title" placeholder="文档标题">
            </div>
            <div class="field">
                <label>作者</label>
                <input type="text" name="author" placeholder="文章作者">
            </div>
            <div class="field">
                <label>分类</label>
                <select name="type_id">
                    <?php foreach($types as $tk=>$tv){ ?>
                        <option value="<?php echo $tv['t_id']; ?>"><?php echo $tv['name']; ?></option>
                    <?php } ?>
                </select>
            </div>
            <div class="field">
                <label>摘要</label>
                <textarea type="text" rows="2" name="summary" placeholder="文档摘要"></textarea>
            </div>
            <div class="field">
                <label>内容</label>
                <textarea type="text" name="body" placeholder="主体内容"></textarea>
            </div>
            <button class="ui primary button" type="submit">保存</button>
        </div>
    </div>

    <div class="column">
        <div class="ui form">
            <div class="field">
                <label>缩略图</label>
                <input type="text" name="thumbnail" placeholder="缩略图地址">
            </div>

            <div class="field">
                <label>来源</label>
                <input type="text" name="source" placeholder="来源">
            </div>

            <div class="field">
                <label>状态</label>
                <select name="status">
                    <?php foreach($status as $sk=>$sv){ ?>
                        <option value="<?php echo $sv['s_id']; ?>"><?php echo $sv['name']; ?></option>
                    <?php } ?>
                </select>
            </div>

            <div class="field">
                <label>下载地址</label>
                <input type="text" name="download" placeholder="下载地址">
            </div>

            <div class="field">
                <label>笑话网址</label>
                <input type="text" name="enjoy_url" placeholder="段子网址">
            </div>

            <div class="field">
                <label>状态文字</label>
                <input type="text" name="enjoy_text" placeholder="段子文字">
            </div>

            <div class="field">
                <label>笑话图片</label>
                <input type="text" name="enjoy_image" placeholder="段子图片地址">
            </div>
        </div>
    </div>
</form>

</body>
</html>
