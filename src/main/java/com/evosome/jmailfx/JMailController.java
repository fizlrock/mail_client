package com.evosome.jmailfx;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class JMailController implements Initializable {

	public ListView<String> filesListView;
	public TextField serverTextField;
	public TextField loginTextField;
	public TextField passwordTextField;
	public TextField subjectTextField;
	public TextArea bodyTextField;
	public TextField recipientsTextField;
	private FileChooser fileChooser = new FileChooser();

	public JMailController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void onFileAddButtonClick(ActionEvent actionEvent) {
		File F = fileChooser.showOpenDialog(filesListView.getScene().getWindow());
		if (F != null) {
			filesListView.getItems().add(F.getAbsolutePath());
		}
	}

	public void onFileRemoveButtonClick(ActionEvent actionEvent) {
		if (!filesListView.getItems().isEmpty())
			filesListView.getItems().remove(0);
	}

	public void onSendButtonClick(ActionEvent actionEvent) throws MessagingException, IOException {
		serverTextField.textProperty().setValue("smtp.mail.ru");
		loginTextField.textProperty().setValue("fizlrock@mail.ru");
		passwordTextField.textProperty().setValue("HWpaY9QkjDMWnwuh6R3a");
		subjectTextField.textProperty().setValue("some theme");
		bodyTextField.textProperty().setValue("some message");
		recipientsTextField.textProperty().setValue("nirku0soft@yandex.ru");

		Properties prop = System.getProperties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.host", serverTextField.getText());
		prop.put("mail.smtp.port", "465");

		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(loginTextField.getText(), passwordTextField.getText());
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(loginTextField.getText()));

		message.setRecipients(
				Message.RecipientType.TO, InternetAddress.parse(recipientsTextField.getText()));

		message.setSubject(subjectTextField.getText());

		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(bodyTextField.getText(), "text/html; charset=utf-8");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);

		if (!filesListView.getItems().isEmpty()) {
			MimeBodyPart attachmentBodyPart = new MimeBodyPart();
			for (String f : filesListView.getItems()) {
				attachmentBodyPart.attachFile(new File(f));
			}
			multipart.addBodyPart(attachmentBodyPart);
		}

		message.setContent(multipart);

		Transport.send(message);

	}
}