package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io"
	"log"
	"net/http"
)

const (
	apiUrlYandexGPT = "https://llm.api.cloud.yandex.net/foundationModels/v1/completion"
	outputFile      = "response.json" // имя файла для сохранения ответа
	apiKeyYandexGPT = "Api-Key"
)

// Recipe структура для рецепта
type Recipe struct {
	RecipeID    int    `json:"recipe_id"`
	Name        string `json:"name"`
	Description string `json:"description"`
	Ingredients []struct {
		ProductID int     `json:"product_id"`
		Quantity  float64 `json:"quantity"`
	} `json:"ingredients"`
	Price    float64 `json:"price"`
	Mealtime string  `json:"mealtime"`
}

type YandexGPTRequest struct {
	ModelUri          string            `json:"modelUri"`
	CompletionOptions CompletionOptions `json:"completionOptions"`
	Messages          []Message         `json:"messages"`
}

type CompletionOptions struct {
	Stream      bool    `json:"stream"`
	Temperature float64 `json:"temperature"`
	MaxTokens   string  `json:"maxTokens"`
}

type Message struct {
	Role string `json:"role"`
	Text string `json:"text"`
}

type YandexGPTResponse struct {
	Result struct {
		Alternatives []struct {
			Message struct {
				Role string `json:"role"`
				Text string `json:"text"`
			} `json:"message"`
			Status string `json:"status"`
		} `json:"alternatives"`
		Usage struct {
			InputTextTokens  string `json:"inputTextTokens"`
			CompletionTokens string `json:"completionTokens"`
			TotalTokens      string `json:"totalTokens"`
		} `json:"usage"`
		ModelVersion string `json:"modelVersion"`
	} `json:"result"`
}

func main() {

	// тест-драйв
	recipeID := 1
	apiUrlRecipe := fmt.Sprintf("http://our_server/v1/recipes/%d/description", recipeID)

	// получение рецепта через GET-запрос к API
	respRecipe, err := http.Get(apiUrlRecipe)
	if err != nil {
		log.Fatalf("Ошибка при выполнении GET-запроса: %v", err)
	}
	defer respRecipe.Body.Close()

	if respRecipe.StatusCode == http.StatusOK {
		body, err := io.ReadAll(respRecipe.Body)
		if err != nil {
			log.Fatalf("Ошибка при чтении ответа: %v", err)
		}

		var result map[string]interface{}
		err = json.Unmarshal(body, &result)
		if err != nil {
			log.Fatalf("Ошибка при разборе JSON: %v", err)
		}

		// description := result["description"].(string)
		// log.Fatalf("Описание рецепта: %s\n", description)
	} else if respRecipe.StatusCode == http.StatusNotFound {

		// преобразуем в json
		requestBody, err := json.Marshal(YandexGPTRequest{
			ModelUri: "gpt://00/yandexgpt-lite",
			CompletionOptions: CompletionOptions{
				Stream:      false,
				Temperature: 0.7,
				MaxTokens:   "250",
			},
			Messages: []Message{
				{
					Role: "system",
					Text: "Предложи рецепт блюда в один абзац в пару предложений из посуды и индгредиентов, которые имеются",
				},
				{
					Role: "user",
					Text: "Посуда: сковородка, Блюдо: Плов, Ингредиенты: рис, морковь, курица",
				},
			},
		})
		if err != nil {
			log.Fatalf("Failed to marshal request body: %v\n", err)
			return
		}

		// создаем новый http запрос к apiUrl через буфер
		request, err := http.NewRequest("POST", apiUrlYandexGPT, bytes.NewBuffer(requestBody))
		if err != nil {
			log.Fatalf("Failed to create request: %v\n", err)
			return
		}

		request.Header.Set("Content-Type", "application/json")
		request.Header.Set("Authorization", apiKeyYandexGPT)

		// создаем нового клиента
		client := &http.Client{}
		// отправляем запрос и получаем ответ
		resp, err := client.Do(request)
		if err != nil {
			log.Fatalf("Failed to send request: %v\n", err)
			return
		}
		defer resp.Body.Close()

		if resp.StatusCode != http.StatusOK {
			body, _ := io.ReadAll(resp.Body)
			log.Fatalf("Failed with status code: %d, response: %s\n", resp.StatusCode, string(body))
			return
		}

		body, err := io.ReadAll(resp.Body)
		if err != nil {
			log.Fatalf("Failed to read response body: %v\n", err)
			return
		}

		var response YandexGPTResponse
		if err := json.Unmarshal(body, &response); err != nil {
			log.Fatalf("Failed to unmarshal response body: %v\n", err)
			return
		}

		// ответ в переменную text
		text := response.Result.Alternatives[0].Message.Text

		// Создаём JSON объект для записи в файл
		output := map[string]string{
			"text": text,
		}

		outputData, err := json.MarshalIndent(output, "", "  ")
		if err != nil {
			log.Fatalf("Failed to marshal output data: %v\n", err)
			return
		}

		// // Сохраняем JSON объект в файл
		// file, err := os.Create(outputFile)
		// if err != nil {
		// 	log.Fatalf("Failed to create file: %v\n", err)
		// 	return
		// }
		// defer file.Close()

		// if _, err := file.Write(outputData); err != nil {
		// 	log.Fatalf("Failed to write to file: %v\n", err)
		// 	return
		// }

		putRequest, err := http.NewRequest("PUT", apiUrlRecipe, bytes.NewBuffer(outputData))
		if err != nil {
			log.Fatalf("Failed to create PUT request: %v\n", err)
			return
		}

		putRequest.Header.Set("Content-Type", "application/json")

		putResp, err := client.Do(putRequest)
		if err != nil {
			log.Fatalf("Failed to send PUT request: %v\n", err)
			return
		}
		defer putResp.Body.Close()

		// fmt.Println("put запрос отправлен")

	}
}
