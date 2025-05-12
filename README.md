# Task Management API

## Это RESTful API для управления задачами с асинхронной обработкой событий через Apache Kafka. При изменении статуса задачи система автоматически отправляет email-уведомления.

## Ключевые функции:
CRUD для задач (создание, чтение, обновление, удаление)
Асинхронные уведомления через Kafka при изменении статуса
Email-оповещения (Spring Mail)
Логирование операций (Spring AOP)
Глобальная обработка ошибок

## Технологии:
Backend: Spring Boot, Spring Data JPA, PostgreSQL
Асинхронность: Kafka (Docker)
Уведомления: Spring Mail
Дополнительно: Lombok, AOP

## Как работает:
Пользователь обновляет статус задачи через REST API.
Продюсер отправляет событие в Kafka.
Консьюмер обрабатывает событие и вызывает NotificationService.
На почту приходит уведомление об изменении статуса.

## Как запускать:
## 1 Запускаем Kafka:
docker-compose up (Kafka + Zookeeper)

## 2 Запустите Spring Boot:
Запуск Spring Boot приложения в SpringBootRestfulApiApplication или mvn spring-boot:run

## Основные компоненты

| Класс                   | Описание                                                    |
|-------------------------|-------------------------------------------------------------|
| `Task`                 | Модель данных задачи (JPA Entity)                           |
| `TaskDTO`                 | Объект для передачи данных о задаче между слоями приложения |
| `TaskStatusUpdateDTO`       | Объект для передачи информации об изменении статуса задачи  |
| `TaskRepository`       | JPA репозиторий для работы с БД                             |
| `TaskService`          | Сервисная логика задач, отправляет сообщения в Kafka        |
| `TaskController`       | REST контроллер для работы с задачами                       |
| `GlobalExceptionHandler` | Глобальный обработчик исключений для REST контроллеров      |
| `TaskUpdateConsumer` | Consumer Kafka для обработки изменений статусов задач       |
| `NotificationService` | Сервис для отправки email-уведомлений                       |
| `KafkaConfig` | Конфигурация Kafka Producer и Consumer                      |
| `LoggingAspect` | Аспекты для логирования                                     |
| `SpringBootRestfulApiApplication` | Запускаем                                                   |
