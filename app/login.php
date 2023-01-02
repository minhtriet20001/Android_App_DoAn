<?php

	require_once "connect.php";

	$email = mysqli_real_escape_string($conn, $_POST['email']);
    $password = mysqli_real_escape_string($conn, $_POST['password']);
 
	if(empty($email)){
        echo "Email Is Empty";
    }else if(empty($password)){
        echo "Password Is Empty";
    }else{
		$sql = "select * from users where email = '$email' and password = '$password'";
        $result =  mysqli_query($conn, $sql);
        if(mysqli_num_rows($result) > 0){
			$fetch = mysqli_fetch_assoc($result);
			if($fetch["status"] == 1){
				echo "Login Success";
			}
			else{
				echo "Password changed";
			}
        }else{
            echo "Failed";
        }
         
    }
?>