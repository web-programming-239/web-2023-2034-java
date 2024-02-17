
### Начало
#### Установка
В `pom.xml` в dependencies нужно добавить новую зависимость:
```xml
<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <version>LATEST</version>
</dependency>
```

Теперь можно пользоваться

#### Подключение

Для того, чтобы сделать что-нибудь с базой данных, нужно открыть соединение:
```java
Connection connection = DriverManager.getConnection(
    "jdbc:mariadb://localhost:3306/database_name",
    "user", "password"
);
```
Помимо открывания соединения, в конце работы с базой данных его нужно закрыть
```java
connection.close()
```
Очень легко забыть. Чтобы не забывать, в джаве можно пользоваться такой конструкцией:
```java
try (Connection connection = DriverManager.getConnection(
    "jdbc:mariadb://localhost:3306/database_name",
    "user", "password"
)) {
	// Тут работаем с connection
}
// После выхода из блока try, соединиение закроется само
```

### Запросы
Теперь у нас есть `connection`, с помощью которого можно делать SQL запросы к базе
#### Простой Select
Просто достать всё из таблицы
```java
PreparedStatement statement = conn.prepareStatement("""  
        select * from Reservations """);  
ResultSet resultSet = statement.executeQuery();  
while (resultSet.next()) {  
      System.out.println(resultSet.getString("roomId")); 
      // внутри этого цикла мы работаем с одной конкретной строкой в таблице
      System.out.println(resultSet.getString(2)); 
      // Можем обращаться к её колонкам по названию, или по индексу
}
```

#### Усложнённый Select
Представим, что у нас есть таблица `users`:
![img](Pasted%20image%2020230302182938.png)

Мы хотим написать функцию
```java
public String getUserPassword(String username) {
// Тут нужно как-то достать пароль из базы
}
```

Как узнать пароль пользователя `user1`? 
```sql
SELECT * FROM users WHERE username = 'user1'
```
Если нам понадобится пароль другого пользователя, мы просто поменяем `user1` на `user2`
Тогда шаблон такого запроса можно записать как 
```sql
SELECT * FROM users WHERE username = ?
```
библиотека для работы с базой позволяет создавать такие шаблоны и подставлять в них значения

Функция получения пароля
```java
public String getUserPassword(String username) {
	try (Connection conn = DriverManager.getConnection("jdbc:mariadb://84.38.180.131:3306/database",  
        "username", "passsword")) {  
    PreparedStatement statement = conn.prepareStatement("""  
            select * from users where username = ?""");  
    statement.setString(1, username); // Почему-то индексация тут с 1
    ResultSet resultSet = statement.executeQuery();
    resultSet.next(); // Тут мы не используем while, так как нас интересует только 1 (и единственная) строка ответа  
    return resultSet.getString("password");  
}
}
```

Возможно, вам захочется сделать проще
```java
PreparedStatement statement = conn.prepareStatement(
"select * from users where username = " + username);
statement.executeQuery();
```
Но так делать очень плохо, потому что [SQL injection](https://learn.microsoft.com/ru-ru/sql/relational-databases/security/sql-injection?view=sql-server-ver16)

#### Insert, Update
Остальные запросы делаются аналогично, но вместо `executeQuery()` используется `executeUpdate()`
```java
public void updateUserPassword(String username, String password) throws SQLException {  
    try (Connection conn = DriverManager.getConnection("jdbc:mariadb://84.38.180.131:3306/lesson7",  
            "user", "password")) {  
        PreparedStatement statement = conn.prepareStatement("""  
                update users set password = ? where username = ? """);  
        statement.setString(1, password);  
        statement.setString(2, username);  
        statement.executeUpdate();  
    }  
}
```
Проблема может возникнуть с датами: везде в коде мы используем `java.util.Date`, а `statement.setDate()` хочет получить `java.sql.Date`

Поэтому нужно делать так:
```java
statement.setDate(1, new java.sql.Date(originalDate.getTime()));  
```


{
Попробуем написать что-нибудь
	есть Users и Tasks. 
	- уметь добавлять и то и другое
	- уметь обновлять
	- уметь удалять
	- уметь искать по пользователю
	- уметь отмечать сделанные
}


### Задания
1) Написать класс MariaDB, реализующий интерфейс Database. Запустить `DatabaseTest`, посмотреть, что всё нормально работает
2)  поддержать написанные методы в контроллере (особенно запросы `/add_room`, `/add_teacher`)
3) Если успеете, то в вебе тоже добавьте (например удаление бронирования - пусть каждую строчку можно будет удалить)