CREATE TABLE IF NOT EXISTS user_t(
chat_id int NOT NULL,
first_name varchar(50) DEFAULT 'UNKNOWN',
user_name varchar(50),
registered_at datetime DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT pk_id PRIMARY KEY (chat_id)
);


CREATE TABLE IF NOT EXISTS reminder_message(
id int AUTO_INCREMENT,
chat_id int NOT NULL,
text varchar(1000) DEFAULT 'No text...',
time_to_remind datetime NOT NULL,
created_at datetime DEFAULT CURRENT_TIMESTAMP,

CONSTRAINT pk_rid PRIMARY KEY (id),
CONSTRAINT fk_user_reminder FOREIGN KEY (chat_id)
REFERENCES user_t (chat_id)
ON DELETE CASCADE
);


