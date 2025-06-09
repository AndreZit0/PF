package utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.io.IOException;
import java.util.Properties;


/**
 * Clase utilitaria para el envío de correos electrónicos utilizando el servicio SMTP de Gmail.
 */

public class EmailSender {

    /**
     * Envía un correo electrónico de bienvenida a un nuevo cliente registrado en la farmacia.
     *
     * @param recipientEmail Dirección de correo electrónico del destinatario.
     * @param customerName   Nombre del cliente.
     */

    public static void sendEmail(String recipientEmail, String customerName) {
        final String senderEmail = "seguimientoaprendicesetapaprod@gmail.com"; //correo de Gmail
        final String appPassword = "pysq zoxi jcge acty"; // contraseña de aplicación de Gmail

        // Configuración de propiedades para la conexión SMTP

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Autenticación con el servidor SMTP

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });

        try {
            // Creación del mensaje de correo electrónico

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Bienvenido a SAEP");
            //message.setText("Hello " + customerName + ",\n\nYour pharmacy registration has been successful, thank you for joining!\n\n\nRegards,\nML Pharmacy");

            // Cuerpo del correo en formato HTML
            MimeBodyPart textPart = new MimeBodyPart();
            // 🖼️ Mensaje en formato HTML con una imagen de internet
            String htmlMessage = "<h1>Hola " + customerName + ",</h1>"
                    + "<p>Tu registro en el aplicativo SAEP (Seguimiento Aprendices a Etapa Productiva) ha sido exitoso!</p>"
                    + "<img src='cid:logo' width='250' height='200'alt='Logo SAEP'>"
                    + "<p>Saludos,<br>Equipo de Seguimiento a la Etapa Productiva</p>";
            textPart.setContent(htmlMessage, "text/html");

            // Adjuntar imagen al correo

            MimeBodyPart imagePart = new MimeBodyPart();
            String imagePath = "C:\\Users\\crist\\IdeaProjects\\PF\\src\\utils\\logo.png";

            try {
                imagePart.attachFile(new File(imagePath));
            } catch (IOException e) {
                System.out.println("Error al adjuntar la imagen: " + e.getMessage());
                return; // Sale del método si no puede adjuntar la imagen
            }

            imagePart.setContentID("<logo>"); // Identificador de la imagen
            imagePart.setDisposition(MimeBodyPart.INLINE);

            // Crear el cuerpo del mensaje con las partes de texto e imagen

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(imagePart);

            message.setContent(multipart);

            // Envío del correo electrónico

            Transport.send(message);
            System.out.println("Correo enviado exitosamente: " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error al enviar email.");
        }
    }
}
