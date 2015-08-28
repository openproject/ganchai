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
    <title>干货集中营</title>
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

<!-- Cards List -->
<div class="ui four cards">

    <?php
    foreach($data['list'] as $k=>$v){
    ?>
    <div class="ui card">
        <div class="content">
            <div class="header">
                <img src="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/examples/assets/images/wireframe/square-image.png" class="ui avatar right spaced image">
                <?php echo $v['title']; ?>
            </div>
            <div class="description">
                <?php echo $v['summary']; ?>
            </div>
        </div>
        <div class="ui two bottom inverted buttons">
            <div class="ui button">
                Edit
            </div>
            <div class="ui button">
                Remove
            </div>
        </div>
    </div>
    <?php } ?>

    <div class="card">
        <div class="blurring dimmable image">
            <div class="ui dimmer transition hidden">
                <div class="content">
                    <div class="center">
                        <a href="<?php echo $adminPath ?>/article/add" class="ui inverted button">添加新文档</a>
                    </div>
                </div>
            </div>
            <img src="<?php echo $rootPath ?>/Public/Assets/libs/semantic/examples/assets/images/wireframe/image-text.png">
        </div>
        <div class="extra content">
            <a><i class="spinner loading icon"></i> 文档回收站 </a>
        </div>
    </div>

</div>
<?php
echo $data['page'];
?>

<script>
    $(document).ready(function () {
        $(".card .image").dimmer({
            on:"hover"
        })
    });
</script>

</body>
</html>