<?php
$rootPath = $Think.__ROOT__;
$adminPath = $Think.__ROOT__.'/'.$Think.MODULE_NAME;
?>
<!DOCTYPE html>
<html>
<head>
    <!-- Standard Meta -->
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

    <!-- Site Properities -->
    <title>管理员登录</title>
    <link rel="stylesheet" type="text/css" href="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/components/reset.css">
    <link rel="stylesheet" type="text/css" href="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/components/site.css">

    <link rel="stylesheet" type="text/css" href="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/components/container.css">
    <link rel="stylesheet" type="text/css" href="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/components/grid.css">
    <link rel="stylesheet" type="text/css" href="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/components/header.css">
    <link rel="stylesheet" type="text/css" href="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/components/image.css">
    <link rel="stylesheet" type="text/css" href="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/components/menu.css">

    <link rel="stylesheet" type="text/css" href="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/components/divider.css">
    <link rel="stylesheet" type="text/css" href="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/components/segment.css">
    <link rel="stylesheet" type="text/css" href="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/components/form.css">
    <link rel="stylesheet" type="text/css" href="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/components/input.css">
    <link rel="stylesheet" type="text/css" href="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/components/button.css">
    <link rel="stylesheet" type="text/css" href="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/components/list.css">
    <link rel="stylesheet" type="text/css" href="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/components/message.css">
    <link rel="stylesheet" type="text/css" href="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/components/icon.css">

    <script src="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/assets/library/jquery.min.js"></script>
    <script src="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/components/form.js"></script>
    <script src="<?php echo $rootPath; ?>/Public/Assets/libs/semantic/dist/components/transition.js"></script>

    <style type="text/css">
        body {
            background-color: #DADADA;
        }
        body > .grid {
            height: 100%;
        }
        .image {
            margin-top: -100px;
        }
        .column {
            max-width: 450px;
        }
    </style>
    <script>
        $(document)
                .ready(function() {
                    $('.ui.form')
                            .form({
                                fields: {
                                    email: {
                                        identifier  : 'email',
                                        rules: [
                                            {
                                                type   : 'empty',
                                                prompt : 'Please enter your e-mail'
                                            },
                                            {
                                                type   : 'email',
                                                prompt : 'Please enter a valid e-mail'
                                            }
                                        ]
                                    },
                                    password: {
                                        identifier  : 'password',
                                        rules: [
                                            {
                                                type   : 'empty',
                                                prompt : 'Please enter your password'
                                            },
                                            {
                                                type   : 'length[6]',
                                                prompt : 'Your password must be at least 6 characters'
                                            }
                                        ]
                                    }
                                }
                            })
                    ;
                })
        ;
    </script>
</head>
<body>

<div class="ui middle aligned center aligned grid">
    <div class="column">
        <h2 class="ui teal image header">
            <img src="<?php echo $rootPath; ?>/Public/Assets/img/logo-128x28.png" class="image">
            <div class="content">
                干柴管理员登录
            </div>
        </h2>
        <form class="ui large form" method="post">
            <div class="ui stacked segment">
                <div class="field">
                    <div class="ui left icon input">
                        <i class="user icon"></i>
                        <input type="text" name="username" placeholder="用户名">
                    </div>
                </div>
                <div class="field">
                    <div class="ui left icon input">
                        <i class="lock icon"></i>
                        <input type="password" name="password" placeholder="密码">
                    </div>
                </div>
                <button class="ui fluid large teal submit button">登录</button>
            </div>

            <div class="ui error message"></div>

        </form>

        <div class="ui message">
            忘记密码? <a href="#">::>_<:: </a>
        </div>
    </div>
</div>

</body>

</html>