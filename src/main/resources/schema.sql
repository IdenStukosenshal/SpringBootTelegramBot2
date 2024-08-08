CREATE TABLE IF NOT EXISTS user_t2(
chatId int NOT NULL,
first_name varchar(50) DEFAULT 'UNKNOWN',
user_name varchar(50) DEFAULT 'UNKNOWN',
registered_at datetime DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT pk_id PRIMARY KEY (chatId)
);


CREATE TABLE IF NOT EXISTS reminder_msg(
id int AUTO_INCREMENT,
chatId int NOT NULL,
text varchar(1000) DEFAULT 'No text...',
time_to_remind datetime NOT NULL,
created_at datetime DEFAULT CURRENT_TIMESTAMP,

CONSTRAINT pk_rid PRIMARY KEY (id),
CONSTRAINT fk_user_reminder FOREIGN KEY (chatId)
REFERENCES user_t2 (chatId)
ON DELETE CASCADE
);


