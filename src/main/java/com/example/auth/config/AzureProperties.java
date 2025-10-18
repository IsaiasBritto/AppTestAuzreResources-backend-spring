package com.example.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "azure")
public class AzureProperties {

    private OpenAI openai = new OpenAI();
    private Vision vision = new Vision();
    private Face face = new Face();
    private Speech speech = new Speech();
    private DocIntel docintel = new DocIntel();

    public OpenAI getOpenai() { return openai; }
    public Vision getVision() { return vision; }
    public Face getFace() { return face; }
    public Speech getSpeech() { return speech; }
    public DocIntel getDocintel() { return docintel; }

    public static class OpenAI {
        private String endpoint;
        private String key;
        private String deployment;
        private String apiVersion;

        public String getEndpoint() { return endpoint; }
        public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
        public String getKey() { return key; }
        public void setKey(String key) { this.key = key; }
        public String getDeployment() { return deployment; }
        public void setDeployment(String deployment) { this.deployment = deployment; }
        public String getApiVersion() { return apiVersion; }
        public void setApiVersion(String apiVersion) { this.apiVersion = apiVersion; }
    }

    public static class Vision {
        private String endpoint;
        private String key;

        public String getEndpoint() { return endpoint; }
        public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
        public String getKey() { return key; }
        public void setKey(String key) { this.key = key; }
    }

    public static class Face {
        private String endpoint;
        private String key;

        public String getEndpoint() { return endpoint; }
        public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
        public String getKey() { return key; }
        public void setKey(String key) { this.key = key; }
    }

    public static class Speech {
        private String region;
        private String key;

        public String getRegion() { return region; }
        public void setRegion(String region) { this.region = region; }
        public String getKey() { return key; }
        public void setKey(String key) { this.key = key; }
    }

    public static class DocIntel {
        private String endpoint;
        private String key;

        public String getEndpoint() { return endpoint; }
        public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
        public String getKey() { return key; }
        public void setKey(String key) { this.key = key; }
    }
}
