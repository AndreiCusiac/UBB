<?php

$hostname = '127.0.0.1';
$username = 'root';
$pass = '';
$database = 'mywebhw';

$con= new mysqli($hostname, $username, $pass, $database);
$con->set_charset("utf8");

$city = $_REQUEST["oras"];

$sql= "SELECT sosiri FROM trenuri WHERE plecari = '".$city."'";

$result = mysqli_query($con,$sql);

$data = array();

//while ($row = mysqli_fetch_object($result)) {
//    $data[] = $row['sosire'];
//}

while ($row = $result->fetch_assoc()) {
    $data[] = $row['sosire'];
}

echo json_encode($data);
