<?php
require_once('connect.php');

if(isset($_POST["email"]) and isset($_POST["code_otp"])){
	$email = $_POST["email"];
	$code_otp = $_POST["code_otp"];
	$check_email = "SELECT * FROM users WHERE email = '$email'";
	$res = mysqli_query($conn, $check_email);
	if (mysqli_num_rows($res) > 0) {
		$fetch = mysqli_fetch_assoc($res);
		if($fetch["code_otp"] === $code_otp){
			$update_status = "UPDATE users SET status = 0 WHERE email = '$email'";
			$check = mysqli_query($conn, $update_status);
			if($check){
				echo "Done";
			}
			else{
				echo "Fail";
			}
		}
		else{
			echo "OTP is not correct";
		}
	}
	else {
		echo "Email is emty";
	}
}	
?>