FROM openapitools/openapi-generator-cli

COPY swagger/swagger.yaml /app/swagger.yaml

VOLUME /app

WORKDIR /app

RUN java -jar openapi-generator-cli.jar generate -i /app/swagger.yaml -g html2 -o /app/swagger_html --additional-properties=title=API\ Docs,\version=1.0.0,\description=API\ Documentation

RUN mv /app/swagger_html/index.html /app/swagger_html/swagger/index.html
