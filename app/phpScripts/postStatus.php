<?php
$userid = $_POST['UserId'];
$status =$_POST['Status'];
$picture = $_POST['Picture'];



include("ataridbconfig.php");
$result = @mysql_query("INSERT INTO userpost (UserId, Status, Picture) values ('$userid','$status','$picture')");

$response = array();
 if($result){
 	
 	$response['success']=1;
 	$response['message']="Record Inserted Sucessfully";
 }else{
 	$response['success']=0;
 	$response['message']="Insertion Failure";
 }
 
 echo json_encode($response);

?>