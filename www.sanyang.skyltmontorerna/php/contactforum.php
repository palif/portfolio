<?php

	if (isset($_POST['submit'])) {
	
		$to = "info@skyltmontorerna.se";
		$name = $_POST['name'];
		$mail = $_POST['email'];
		$telephone = $_POST['telephone'];
		$message = $_POST['message'];
		
		/*
$headers = "From: " . $mail . "\r\n";
		$headers .= "Reply-To: ". $mail . "\r\n";
		$headers .= "CC: susan@example.com\r\n";
		$headers .= "MIME-Version: 1.0\r\n";
		$headers .= "Content-Type: text/html; charset=UTF-8\r\n";
*/		
		$headers = "From: " . $mail. "\r\n";
		$headers .= "Content-Type: text/html; charset=UTF-8\r\n";
		
		if($name == ""){
			header('refresh:0.1;url=http://skyltmontorerna.se/contact');
			echo '<script language="javascript"> alert("Please enter a name") </script>';
			exit;
		}
		if($mail == ""){
			header('refresh:0.1;url=http://skyltmontorerna.se/contact');
			echo '<script language="javascript"> alert("Please enter e-mail adress") </script>';
			exit;
		}
		if($message == ""){
			header('refresh:0.1;url=http://skyltmontorerna.se/contact');
			echo '<script language="javascript"> alert("Please enter message") </script>';
			exit;
		}
		if($telephone == ""){
			header('refresh:0.1;url=http://skyltmontorerna.se/contact');
			echo '<script language="javascript"> alert("Please enter telephone number") </script>';
			exit;
		}
		
		$text = "You have recieved an e-mail from: ".$name."<br><br>Telephone number: ".$telephone."<br><br>".$message;
	
		if(mail($to, "Recieved from 'Skyltmontorerna' ", $text, $headers)){
			header('refresh:0.1;url=http://skyltmontorerna.se/contact');
			echo '<script language="javascript"> alert("Message succesfully sent!") </script>';
			exit;
		} else {
			header('refresh:0.1;url=http://skyltmontorerna.se/contact');
			echo '<script language="javascript"> alert("Message failed!") </script>';
			exit;
		}
		
	} else {
		header('refresh:0.1;url=http://skyltmontorerna.se/contact');
		echo '<script language="javascript"> alert("Error!") </script>';
		exit;
	}

?>