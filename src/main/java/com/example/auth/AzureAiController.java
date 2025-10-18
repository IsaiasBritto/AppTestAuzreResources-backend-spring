package com.example.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/ai")
public class AzureAiController {

    private final WebClient http;

    public AzureAiController(WebClient http) {
        this.http = http;
    }

    @Value("${azure.openai.endpoint}") String openaiEndpoint;
    @Value("${azure.openai.key}") String openaiKey;
    @Value("${azure.openai.deployment}") String openaiDeployment;
    @Value("${azure.openai.apiVersion:2024-08-01-preview}") String openaiApiVersion;

    @Value("${azure.vision.endpoint}") String visionEndpoint;
    @Value("${azure.vision.key}") String visionKey;

    @Value("${azure.face.endpoint}") String faceEndpoint;
    @Value("${azure.face.key}") String faceKey;

    @Value("${azure.speech.region}") String speechRegion;
    @Value("${azure.speech.key}") String speechKey;

    @Value("${azure.docintel.endpoint}") String diEndpoint;
    @Value("${azure.docintel.key}") String diKey;

    record ChatRequest(java.util.List<Map<String, String>> messages) {}

    @PostMapping("/chat")
    public Mono<ResponseEntity<String>> chat(@RequestBody ChatRequest body) {
        String url = openaiEndpoint + "/openai/deployments/" + openaiDeployment + "/chat/completions?api-version=" + openaiApiVersion;
        Map<String, Object> payload = Map.of(
            "messages", body.messages(),
            "temperature", 0.2,
            "max_tokens", 400
        );
        return http.post().uri(url)
            .header("api-key", openaiKey)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(payload)
            .retrieve().bodyToMono(String.class)
            .map(ResponseEntity::ok);
    }

    // Computer Vision 4.0 Image Analysis (URL mode)
    @PostMapping("/vision/analyze")
    public Mono<ResponseEntity<String>> analyze(@RequestBody Map<String, String> body) {
        String imageUrl = body.get("url");
        String url = visionEndpoint + "/computervision/imageanalysis:analyze?api-version=2024-02-01&features=read,caption,objects,tags";
        Map<String, Object> payload = Map.of("url", imageUrl);
        return http.post().uri(url)
            .header("Ocp-Apim-Subscription-Key", visionKey)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(payload)
            .retrieve().bodyToMono(String.class)
            .map(ResponseEntity::ok);
    }

    // Face Detect (URL mode)
    @PostMapping("/face/detect")
    public Mono<ResponseEntity<String>> faceDetect(@RequestBody Map<String, String> body) {
        String imageUrl = body.get("url");
        String url = faceEndpoint + "/face/v1.0/detect?returnFaceAttributes=age,gender,smile,facialHair,glasses,emotion";
        Map<String, Object> payload = Map.of("url", imageUrl);
        return http.post().uri(url)
            .header("Ocp-Apim-Subscription-Key", faceKey)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(payload)
            .retrieve().bodyToMono(String.class)
            .map(ResponseEntity::ok);
    }

    // Speech TTS (retorna áudio WAV)
    @PostMapping(value="/speech/tts", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Mono<byte[]> tts(@RequestBody Map<String, String> body) {
        String text = body.getOrDefault("text", "Olá mundo da Azure Speech!");
        String voice = body.getOrDefault("voice", "pt-BR-FranciscaNeural");
        String ssml = "<speak version='1.0' xml:lang='pt-BR'><voice name='" + voice + "'>" + text + "</voice></speak>";
        String url = "https://" + speechRegion + ".tts.speech.microsoft.com/cognitiveservices/v1";
        return http.post().uri(url)
            .header("Ocp-Apim-Subscription-Key", speechKey)
            .header("X-Microsoft-OutputFormat", "riff-24khz-16bit-mono-pcm")
            .contentType(MediaType.APPLICATION_XML)
            .body(BodyInserters.fromValue(ssml))
            .retrieve().bodyToMono(byte[].class);
    }

    // Document Intelligence OCR (prebuilt-document, file URL)
    @PostMapping("/doc/analyze")
    public Mono<ResponseEntity<String>> docAnalyze(@RequestBody Map<String, String> body) {
        String fileUrl = body.get("url");
        String url = diEndpoint + "/formrecognizer/documentModels/prebuilt-document:analyze?api-version=2024-02-29-preview";
        Map<String, Object> payload = Map.of("urlSource", fileUrl);
        return http.post().uri(url)
            .header("Ocp-Apim-Subscription-Key", diKey)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(payload)
            .retrieve().bodyToMono(String.class)
            .map(ResponseEntity::ok);
    }
}