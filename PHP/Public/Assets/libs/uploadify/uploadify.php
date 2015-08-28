<?php
/*
Uploadify
Copyright (c) 2012 Reactive Apps, Ronnie Garcia
Released under the MIT License <http://www.opensource.org/licenses/mit-license.php> 
*/
header("Content-Type:text/json;charset='utf-8'");
// Define a destination
$targetFolder = '/uploads/image'; // Relative to the root

$verifyToken = md5('unique_salt' . $_POST['timestamp']);

if (!empty($_FILES) && $_POST['token'] == $verifyToken) {
	$tempFile = $_FILES['Filedata']['tmp_name'];
	$targetPath = $_SERVER['DOCUMENT_ROOT'] . $targetFolder;
	$fileExt = pathinfo($_FILES['Filedata']['name']);
	$targetFile = rtrim($targetPath,'/') . '/'.UUID::v4().".".$fileExt['extension'];
	
	// Validate the file type
	$fileTypes = array('jpg','jpeg','gif','png'); // File extensions
	$fileParts = pathinfo($_FILES['Filedata']['name']);
	
	if (in_array($fileParts['extension'],$fileTypes)) {
		move_uploaded_file($tempFile,$targetFile);
		$ret = [
			"error"=>0,
			"message"=>"Success",
			'localurl' => $targetFile,
			'aliurl'=>"http://www.baidu.com/".$targetFile,
			"id"=>100
		];
		echo json_encode($ret, true);
	} else {
		echo 'Invalid file type.';
	}
}


class UUID
{
	/**
	 * @param $namespace
	 * @param $name
	 * @return bool|string
	 */
	public static function v3($namespace, $name)
	{
		if(!self::is_valid($namespace)) return false;

		// Get hexadecimal components of namespace
		$nhex = str_replace(array('-','{','}'), '', $namespace);

		// Binary Value
		$nstr = '';

		// Convert Namespace UUID to bits
		for($i = 0; $i < strlen($nhex); $i+=2)
		{
			$nstr .= chr(hexdec($nhex[$i].$nhex[$i+1]));
		}

		// Calculate hash value
		$hash = md5($nstr . $name);

		return sprintf('%08s-%04s-%04x-%04x-%12s',

			// 32 bits for "time_low"
			substr($hash, 0, 8),

			// 16 bits for "time_mid"
			substr($hash, 8, 4),

			// 16 bits for "time_hi_and_version",
			// four most significant bits holds version number 3
			(hexdec(substr($hash, 12, 4)) & 0x0fff) | 0x3000,

			// 16 bits, 8 bits for "clk_seq_hi_res",
			// 8 bits for "clk_seq_low",
			// two most significant bits holds zero and one for variant DCE1.1
			(hexdec(substr($hash, 16, 4)) & 0x3fff) | 0x8000,

			// 48 bits for "node"
			substr($hash, 20, 12)
		);
	}

	/**
	 *
	 * Generate v4 UUID
	 *
	 * Version 4 UUIDs are pseudo-random.
	 */
	public static function v4()
	{
		return sprintf('%04x%04x-%04x-%04x-%04x-%04x%04x%04x',

			// 32 bits for "time_low"
			mt_rand(0, 0xffff), mt_rand(0, 0xffff),

			// 16 bits for "time_mid"
			mt_rand(0, 0xffff),

			// 16 bits for "time_hi_and_version",
			// four most significant bits holds version number 4
			mt_rand(0, 0x0fff) | 0x4000,

			// 16 bits, 8 bits for "clk_seq_hi_res",
			// 8 bits for "clk_seq_low",
			// two most significant bits holds zero and one for variant DCE1.1
			mt_rand(0, 0x3fff) | 0x8000,

			// 48 bits for "node"
			mt_rand(0, 0xffff), mt_rand(0, 0xffff), mt_rand(0, 0xffff)
		);
	}

	/**
	 * @param $namespace
	 * @param $name
	 * @return bool|string
	 */
	public static function v5($namespace, $name)
	{
		if(!self::is_valid($namespace)) return false;

		// Get hexadecimal components of namespace
		$nhex = str_replace(array('-','{','}'), '', $namespace);

		// Binary Value
		$nstr = '';

		// Convert Namespace UUID to bits
		for($i = 0; $i < strlen($nhex); $i+=2)
		{
			$nstr .= chr(hexdec($nhex[$i].$nhex[$i+1]));
		}

		// Calculate hash value
		$hash = sha1($nstr . $name);

		return sprintf('%08s-%04s-%04x-%04x-%12s',

			// 32 bits for "time_low"
			substr($hash, 0, 8),

			// 16 bits for "time_mid"
			substr($hash, 8, 4),

			// 16 bits for "time_hi_and_version",
			// four most significant bits holds version number 5
			(hexdec(substr($hash, 12, 4)) & 0x0fff) | 0x5000,

			// 16 bits, 8 bits for "clk_seq_hi_res",
			// 8 bits for "clk_seq_low",
			// two most significant bits holds zero and one for variant DCE1.1
			(hexdec(substr($hash, 16, 4)) & 0x3fff) | 0x8000,

			// 48 bits for "node"
			substr($hash, 20, 12)
		);
	}

	public static function is_valid($uuid) {
		return preg_match('/^\{?[0-9a-f]{8}\-?[0-9a-f]{4}\-?[0-9a-f]{4}\-?'.'[0-9a-f]{4}\-?[0-9a-f]{12}\}?$/i', $uuid) === 1;
	}
}