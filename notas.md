Show de bola — você tem dois endereços para testar o **Spring**:

## 1) Local (quando rodar no seu PC)

```
http://localhost:8080
```

Exemplos:

```bash
# Chat (Azure OpenAI)
curl -X POST http://localhost:8080/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"messages":[{"role":"user","content":"Diga olá em pt-BR"}]}'

# Vision
curl -X POST http://localhost:8080/ai/vision/analyze \
  -H "Content-Type: application/json" \
  -d '{"url":"https://aka.ms/azai/sample-image"}'
```

## 2) Publicado no Azure App Service

```
https://app-spring-dev-IS-001.azurewebsites.net
```

Exemplos:

```bash
# Chat (Azure OpenAI)
curl -X POST https://app-spring-dev-IS-001.azurewebsites.net/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"messages":[{"role":"user","content":"Diga olá em pt-BR"}]}'

# Vision
curl -X POST https://app-spring-dev-IS-001.azurewebsites.net/ai/vision/analyze \
  -H "Content-Type: application/json" \
  -d '{"url":"https://aka.ms/azai/sample-image"}'
```

(Se você já tiver exigência de JWT nas rotas `/ai/**`, adicione o header `Authorization: Bearer <seu_accessToken>`.)

### Endpoint de login (para o app Android trocar o ID Token do Google)

```bash
curl -X POST https://app-spring-dev-IS-001.azurewebsites.net/auth/google \
  -H "Content-Type: application/json" \
  -d '{"idToken":"<ID_TOKEN_DO_GOOGLE>"}'
```

Resposta esperada: `{"accessToken":"...","refreshToken":"...","expiresIn":3600}`.

Se você também publicar o backend **FastAPI**, o endereço ficará:

```
https://app-fastapi-dev-IS-001.azurewebsites.net
```

(e lá você tem ainda `GET /health` para teste rápido). Quer que eu gere o Web App Python agora e te passe o teste também?
