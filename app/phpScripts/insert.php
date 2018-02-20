<?php
$designation = $_POST['Designation'];
$name = $_POST['Name'];
$phone =$_POST['Phone'];
$email =$_POST['Email'];
$state =$_POST['State'];
$kvk = $_POST['Kvk'];
$password = $_POST['Password'];

include("ataridbconfig.php");
$result = @mysql_query("INSERT INTO atariuser VALUES(null,'$designation','$name','$phone','$email','$state','$kvk','$password')");	

$response = array();
 if($result){
 	$last_id = @mysql_insert_id();
$response['UserId'] = $last_id;
 	
 	$response['success']=1;
 	$response['message']="Record Inserted Sucessfully";
 }else{
 	$response['success']=0;
 	$response['message']="Insertion Failure";
 }
 
 echo json_encode($response);

?>