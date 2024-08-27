Простой telegram bot на Java Spring Boot.
Для взаимодействия в личном чате.

Для работы с ботом нужно написать BotFather в telegram, создать бота, назначив имя
и получив API Token. Указать их в application.properties.
Создать базу данных MySQL, прописать логин и пароль в application.properties.

Функции:
* посмотреть курсы валют(обращение к API сайта, использование OpenFeign),
* Remind me, отправить себе сообщение с задержкой по времени(работа с БД, использование Spring Data JDBC, MySQL)
