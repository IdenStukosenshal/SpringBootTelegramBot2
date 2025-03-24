## Простой telegram bot на Java Spring Boot.  
Для взаимодействия в личном чате.

Для работы с ботом нужно написать BotFather в telegram, создать бота, назначив имя
и получив API Token.  
Также нужно получить свой chatId.  

### Запуск:

* Собрать jar-file:  
```
mvn clean package -DskipTests
```
* Собрать docker-image:  
```
docker build -t botImageName .
```
* Создать Docker-сеть для связи контейнеров:  
```
docker network create networkName
```
* Запустить в ней контейнер mysql  
Пример:  
```
docker run -d --name mysqlContainerName --network networkName -e MYSQL_ROOT_PASSWORD=rootPassword -e MYSQL_DATABASE=dbName -e MYSQL_USER=dbUserName -e MYSQL_PASSWORD=password mysql
```
* запустить контейнер с приложением, указав переменные окружения:  
Пример:  
```
docker run -d --name j-bot --network networkName -e SPRING_DATASOURCE_URL=jdbc:mysql://mysqlContainerName:3306/dbName -e SPRING_DATASOURCE_USERNAME=dbUserName -e SPRING_DATASOURCE_PASSWORD=password -e BOT_NAME=YourBotName -e BOT_TOKEN=YourToken -e BOT_OWNER=YourID botImageName
```

Функции:  
* посмотреть курсы валют(обращение к API сайта, использование OpenFeign),
* Remind me, отправить себе сообщение с задержкой по времени(работа с БД, использование Spring Data JDBC, MySQL)
