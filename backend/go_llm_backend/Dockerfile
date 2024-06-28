# Use the official Go image as the base image
FROM golang:1.22.4
WORKDIR /app
COPY go.mod . 
RUN go build -o go_llm_backend ./main.go
CMD ["./go_llm_backend"]



