<?php

require_once('connect.php');

$email = $_POST["email"];

$stmt = $conn->prepare("SELECT list_name, code_table, total, date FROM history WHERE email = '$email'");

$stmt ->execute();
$stmt -> bind_result($list_name, $code_table, $total, $date);

$products = array();

while($stmt ->fetch()){

    $temp = array();
	
	$temp['list_name'] = $list_name;
	$temp['code_table'] = $code_table;
	$temp['total'] = $total;
	$temp['date'] = $date;
	
	array_push($products,$temp);
}

echo json_encode(array('data' => $products));

?>