<?php

require_once('connect.php');

$stmt = $conn->prepare("SELECT name, price, thumbnail FROM menu");

$stmt ->execute();
$stmt -> bind_result($name, $price, $thumbnail);

$products = array();

while($stmt ->fetch()){

    $temp = array();
	
	$temp['name'] = $name;
	$temp['price'] = $price;
	$temp['thumbnail'] = $thumbnail;
	
	array_push($products,$temp);
}

echo json_encode(array('data' => $products));

?>