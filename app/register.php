<?php

	require_once "connect.php";

	
	$name = $_POST["name"];
    $email = $_POST["email"];
    $password = $_POST["password"];
	$code_otp = '';
	$status = 1;
    if(empty($name)){
        echo "Name Is Empty";
    }else if(empty($email)){
        echo "Email Is Empty";
    }else if(empty($password)){
        echo "Password Is Empty";
    }
	else{ 
        $sql = "SELECT * FROM users WHERE email = '$email'";
        $result = mysqli_query($conn, $sql);
		
        if(mysqli_num_rows($result)>0){
            echo "Already have an ID";
        }else{
         $sql1 = "INSERT INTO users(name,email,password,code_otp,status) VALUES ('$name','$email','$password', '$code_otp', $status)";
			$ketqua =  mysqli_query($conn, $sql1);
            
            if($ketqua){
                echo "Data Inserted";
            }else{			
                echo "Something Wrong";
            }
 
        } 
    }
	

?>