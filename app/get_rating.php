<?php

require_once('connect.php');

$email = $_POST["email"];
$stmt = $conn->prepare("SELECT name, rate, comment, time FROM rating WHERE email = '$email'");

$stmt ->execute();
$stmt -> bind_result($name, $rate, $comment, $time);

$rating = array();

while($stmt ->fetch()){

    $temp = array();
	$temp['name'] = $name;
	$temp['rate'] = $rate;
	$temp['comment'] = $comment;
	$temp['time'] = $time;
	
	array_push($rating,$temp);
}

echo json_encode(array('data' => $rating));

?>