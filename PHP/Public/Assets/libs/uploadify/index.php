<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>UploadiFive Test</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script src="jquery.uploadify.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="uploadify.css">
<style type="text/css">
body {
	font: 13px Arial, Helvetica, Sans-serif;
}
</style>
</head>

<body>
	<h1>Uploadify Demo</h1>
	<form>
		<div id="queue"></div>
		<input id="file_upload" name="file_upload" type="file" multiple="true">
	</form>

	<script type="text/javascript">
		<?php $timestamp = time();?>
		$(function() {
			$('#file_upload').uploadify({
				'formData'     : {
					'timestamp' : '<?php echo $timestamp;?>',
					'token'     : '<?php echo md5('unique_salt' . $timestamp);?>',
				},
				'swf'      : 'uploadify.swf',
				//'uploader' : 'myupload.php',
                'uploader' : '/upload/uploadify',
				'width':100,
                'height':30,
                'auto':true,
                'multi':false,
                'onUploadSuccess':function(file, data, response){
                    if(response){
                        //文件上传成功
                        var jsonData = $.parseJSON(data);
                        alert(jsonData.aliurl);
                        /*
                        if(data.error == 0){
                            alert(data.id);
                        } else {
                            alert("文件上传出错");
                        }
                        */
                    } else {
                        //文件上传失败
                        alert("文件上传失败");
                    }
                }
			});
		});
	</script>
</body>
</html>