### Исключения
Что будет, если мы попытаемся запустить такой код?
```java
System.out.println(1 / 0);
```

![](Pasted%20image%2020231110202656.png)
То, что мы получили называется **Исключением** (или **ошибкой**). Это специальный объект класса **Exception**, который используется, чтобы показать, что во время выполнения программы что-то пошло не так и непонятно, что делать дальше. При **выбрасывании исключения** выполнение программы останавливается.

Но бывает так, что мы, как программисты, знаем, что делать в таком случае и не хотим, чтобы программа падала. В таком случае можно использовать конструкцию **try catch**

```java
try {  
    System.out.println(1 / 0); 
} catch (Exception e) {  
      // этот код вызовется, если в блоке выше будет ошибка
      System.out.println("На ноль делить нельзя");
}

```

```java
try { 
	System.out.println(1);
    System.out.println(1 / 0);
    System.out.println(2);
} catch (Exception e) {  
      System.out.println(e);
}
```

что будет выведено в консоль?

```java
try {  
    System.out.println(1 / 0);  
} catch (ArrayIndexOutOfBoundsException e) {  
    System.out.println(1);  
} 
``` 
а в этом случае?


### Checked и unchecked exceptions
В будущем вам предстоит встретиться с подобными конструкциями:

```java
public static String readFile(String filename) throws FileNotFoundException {  
    // тут какой-то код
}
```

ключевое слово `throws` показывает, что этот метод может выбросить указанные исключения. Это обязывает обрабатывать эти исключения во всех метода, в которых используется метод `readFile`

например, такой код не скомпилируется
```java
public static void main(String[] args) {  
  Systerm.out.println(readFile("test.txt"));
}
```
Так как метод `readFile` кидает исключение, а в методе `main` мы его никак не обрабатываем
Есть 2 способа решения проблемы. Первый - обработать исключение
```java
public static void main(String[] args) {  
    try {  
        System.out.println(readFile("testFile"));  
    } catch (FileNotFoundException e) {  
        System.out.println("file not found");  
    }  
}
```
Второй - указать, что метод `main` тоже выбрасывает исключение
```java
public static void main(String[] args) throws FileNotFoundException {  
    System.out.println(readFile("testFile"));  
}
```
Не все исключения нужно так обрабатывать, некоторые можно не указывать явно в сигнатуре метода. Те, что нужно указывать называются **Checked exceptions** (они наследуются от класса `Exception`). Те, что не надо - **Unchecked exceptions** (они наследуются от класса `RuntimeException`)


### BufferedReader
До этого мы пользовались классом `Scanner` для считывания данных из консоли. Давайте посмотрим на другой метод - `BufferedReader`
```java
public static void main(String[] args) throws IOException {  
    BufferedReader r = new BufferedReader(new InputStreamReader(System.in));  
    String line = r.readLine();
    System.out.println(line);  
}
```

Такое же можно провернуть с файлами

```java
public static void main(String[] args) throws IOException {  
    BufferedReader r = new BufferedReader(new FileReader("test.txt"));  
    String line = r.readLine();
    System.out.println(line);  
}
```

Но файлы было бы неплохо закрывать, после завершения работы с ними:
```java
public static void main(String[] args) throws IOException {  
    BufferedReader r = new BufferedReader(new FileReader("test.txt"));  
    String line = r.readLine();
    System.out.println(line);  
    r.close();
}
```

Про последнюю строчку очень легко забыть, поэтому иногда используют такую конструкцию:

```java
try (BufferedReader r = new BufferedReader(new FileReader("test.txt"))) { 
	String line = r.readLine();
	System.out.println(line);  
} catch(IOException e) {
	System.out.println(e);  
}
```

то, что идёт в скобочках рядом с `try` будет закрыто автоматически после выхода из этого блока



### Практика

- Считайте из файла строку, попытайтесь преобразовать её в число. Аккуратно обработайте возможные ошибки

### Задания

- Напишите программу на Java, которая запрашивает у пользователя два числа и выводит их сумму, разность, произведение и частное (используйте `BufferedReader`, а не `Scanner`). В случае ошибки (например, пользователь ввел некорректное число, или )
- Создайте интерфейс "Фигура", который имеет методы:
	- 1) `public String toString()`, выводящий название фигуры 
	- 2) `public int area()`, возвращающий площадь фигуры
	- 3) `public int perimeter()`периметр площадь фигуры
	На основе этого класса создайте подклассы "Квадрат" и "Круг" и реализуйте методы для каждой фигуры.
1. Создать класс `BankAccount` с полями:
	- `balance` (тип данных double) - для хранения текущего баланса счёта.
2. Добавить конструктор класса BankAccount, который принимает начальный баланс и инициализирует им поле `balance`.
3. Реализовать методы для управления счётом:
	- `void deposit(double amount)` - метод для пополнения счёта на указанную сумму amount.
	- `void withdraw(double amount)` - метод для снятия указанной суммы amount со счёта. 
4. Добавить обработку ошибок:
	- При попытке снятия средств, превышающих текущий баланс, выводить сообщение об ошибке "Недостаточно средств для проведения операции".
	- Предотвращать отрицательный баланс на счёте.
5) Создать класс `BankAccountController`, для работы с `BankAccount` через консоль. должны быть методы:
	- `void serve()`, считывающий постоянно строку из консоли и поддерживающий команды:
		- stop - прекратить считывание (выход из цикла)
		- deposit *кол-во* - добавляет указанное количество денег на счёт
		- withdraw *кол-во* - снимает указанное количество денег
	- `void log(String filename)` - выводит все произведённые в метода `serve()` операции в файл, _filename_. Для вывода в файл можно использовать [BufferedWriter](https://javarush.com/groups/posts/593-bufferedreader-i-bufferedwritter) 