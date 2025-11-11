package com.vuminhha.decorstore.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Service
public class AiService {


    @Value("${ai.api.key}")
    private String apiKey;

    @Value("${ai.api.url}")
    private String apiUrl;

    public String askAi(String prompt) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        String requestBody = """
            {
              "contents": [
                {
                  "parts": [
                    { "text": "%s" }
                  ]
                }
              ]
            }
            """.formatted(prompt);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "?key=" + apiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse JSON bằng Jackson
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());

        // Nếu API trả về error
        if (root.has("error")) {
            return "API Error: " + root.get("error").get("message").asText();
        }

        // Lấy text từ Gemini
        return root.get("candidates")
                .get(0)
                .get("content")
                .get("parts")
                .get(0)
                .get("text")
                .asText();
    }
}
