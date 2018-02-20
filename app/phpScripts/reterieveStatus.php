<?php


include("ataridbconfig.php");

$result = @mysql_query("Select * from user_posts ORDER BY Date DESC LIMIT 20 OFFSET 0");	
$response =array();

 if(@mysql_num_rows($result)>0){
 	$response['students'] = array();

		while($row=@mysql_fetch_array($result)){
			array_push($response['students'], $row);
		}

 	$response['message']="Sucessful";
 }
 else{
 	$response['message']="Failure";
 }
 echo json_encode($response);

?>
