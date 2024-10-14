package d2a.sn.document.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {
    private final String API_URL = "https://api.brevo.com/v3/smtp/email";
    // Injecting the API key from application properties or environment variables

    private String  API_KEY;
    // Remplacez par votre clé API

    public void sendEmail(String toEmail,String userName, String subject, String content) {

        RestTemplate restTemplate = new RestTemplate();

        //  les entetes
        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", API_KEY);
        headers.set("Content-Type", "application/json");

        // le corps de la requête
        Map<String, Object> emailBody = new HashMap<>();
        emailBody.put("sender", new HashMap<String, String>() {{
            put("name", "G_document");
            put("email", "gestioncourrier2024@gmail.com");
        }});
        emailBody.put("to", new Object[]{new HashMap<String, String>() {{
            put("email", toEmail);
            put("name", userName);
        }}});
        emailBody.put("subject", subject);
        emailBody.put("htmlContent", content);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(emailBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, request, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Email envoyé avec succès");
        } else {
            System.out.println("Erreur lors de l'envoi de l'email : " + response.getBody());
        }
    }
}

