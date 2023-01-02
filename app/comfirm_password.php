<?php
require_once('connect.php');


if(isset($_POST["new_password"]) and isset($_POST["email"])){
	$email = $_POST["email"];
	$new_password = $_POST["new_password"];
	$check_email = "SELECT * FROM users WHERE email = '$email'";
	$res = mysqli_query($conn, $check_email);
	if (mysqli_num_rows($res) > 0) {
		$update_password = "UPDATE users SET password = '$new_password' WHERE email = '$email'";
		$update_status = "UPDATE users SET status = 1 WHERE email = '$email'";
		$check_password = mysqli_query($conn, $update_password);
		$check_status = mysqli_query($conn, $update_status);
		if($check_password and $check_status){
			echo "Done";
		}
		else{
			echo "Fail";
		}
	}
	else {
		echo "Email is not correct";
	}
}	

?>