<?php
	use PHPMailer\PHPMailer\PHPMailer;
	use PHPMailer\PHPMailer\Exception;
	require 'PHPMailer/src/Exception.php';
	require 'PHPMailer/src/PHPMailer.php';
	require 'PHPMailer/src/SMTP.php';
	require_once('connect.php');
	
	if(isset($_POST["email"])){
		$email = $_POST["email"];
		$check_email = "SELECT * FROM users WHERE email = '$email'";
		$res = mysqli_query($conn, $check_email);
		if (mysqli_num_rows($res) > 0) {
			$code = rand(999999, 111111);
			$update_code = "UPDATE users SET code_otp = '$code' WHERE email = '$email'";
			$check_update = mysqli_query($conn, $update_code);
			if($check_update){
				$subject = "Email OTP Code";
				$message = "Your OTP code is $code";
				if(Send_Mail($email, $subject, $message)){
					echo "Done";
				}
				else{
					echo "Fail";
				}
			}
			else{
				echo "Fail update";
			}
		}
		else{
			echo "Email is not exist";
		}
	}
	
function Send_Mail($to, $subject, $body)
{
    $mail = new PHPMailer(true);                              // Passing `true` enables exceptions
    try {
        //Server settings
        //$mail->SMTPDebug = 2;                                 // Enable verbose debug output
        $mail->isSMTP();                                      // Set mailer to use SMTP
        $mail->Host = 'smtp.gmail.com';  // Specify main and backup SMTP servers
        $mail->SMTPAuth = true;                               // Enable SMTP authentication
        $mail->Username = 'ntanh3108@gmail.com';                 // SMTP username
        $mail->Password = 'wqtkndglqmwgplzw';                           // SMTP password
        $mail->SMTPSecure = 'tls';                            // Enable TLS encryption, `ssl` also accepted
        $mail->Port = 587;                                    // TCP port to connect to

        //Recipients
        $mail->setFrom('ntanh3108@gmail.com', 'Handsome Admin');
        $mail->addAddress($to, 'User');     // Add a recipient
        /*$mail->addAddress('ellen@example.com');               // Name is optional
            $mail->addReplyTo('info@example.com', 'Information');
            $mail->addCC('cc@example.com');
            $mail->addBCC('bcc@example.com');*/

        //Attachments
        //$mail->addAttachment('/var/tmp/file.tar.gz');         // Add attachments
        //$mail->addAttachment('/tmp/image.jpg', 'new.jpg');    // Optional name

        //Content
        $mail->isHTML(true);                                  // Set email format to HTML
        $mail->Subject = $subject;
        $mail->Body   = $body;
        //$mail->AltBody = 'This is the body in plain text for non-HTML mail clients';

        $mail->send();
        return true;
    } catch (Exception $e) {
        return false;
    }
}
?>