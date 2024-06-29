FROM python
WORKDIR /app
COPY requirements.txt .

# Устанавливаем зависимости
RUN pip install --no-cache-dir -r requirements.txt
# копируем остальную часть исходного кода в рабочую директорию
COPY . .
# определяем команду для запуска бота
CMD ["python", "bot.py"]
