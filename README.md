# Spring Boot Backend
Run: `mvn spring-boot:run`


## Azure endpoints
- `POST /ai/chat` -> Azure OpenAI Chat Completions
- `POST /ai/vision/analyze` -> Computer Vision 4.0 (JSON body: `{ "url": "https://..." }`)
- `POST /ai/face/detect` -> Face API (JSON body: `{ "url": "https://..." }`)
- `POST /ai/speech/tts` -> retorna áudio WAV
- `POST /ai/doc/analyze` -> Document Intelligence (prebuilt-document) com `{ "url": "https://..." }`

Configure as chaves e endpoints em `application.yml` (seção `azure:`).
