<?php

require_once('connect.php');

$name = $_POST["name"];
$rate = $_POST["rate"];
$comment = $_POST["comment"];
$email = $_POST["email"];

$stmt = $conn->prepare("INSERT INTO rating(Email, Name, Rate, Comment, Time) VALUES ('$email','$name','$rate','$comment', Now())");
$stmt ->execute();

if($stmt){
	echo "Done";
}
else{
	echo "Fail";
}
?>