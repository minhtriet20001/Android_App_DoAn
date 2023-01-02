<?php

require_once('connect.php');

$list = $_POST["list_food"];
$table = $_POST["table"];
$sum = $_POST["Sum_money"];
$date =  $_POST["date"];
$email = $_POST["email"];

$stmt = $conn->prepare("INSERT INTO history(list_name,code_table,total,date,email) VALUES ('$list','$table','$sum','$date','$email')");

$stmt ->execute();

if($stmt){
	echo "Done";
}
else{
	echo "Fail";
}
?>