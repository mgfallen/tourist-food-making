FROM openapitools/openapi-generator-cli

COPY swagger/swagger.yaml /app/swagger.yaml

VOLUME /app

WORKDIR /app

CMD ["generate", "-i", "swagger.yaml", "-g", "html2", "-o", "swagger_html"]
