### Основы
```java
public class Main {  
    public static void main(String[] args) {  
        // код пишем здесь
    }  
}
```

### Типизация
В Java тип переменной фиксируется при её объявлении и не может меняться в будущем
```java
int a;
Sysytem.out.print(a);
String b = "test";
a = 15;
a = "test"; // нельзя
```
### Операции
```java
int i = 10;
i % 3 // остаток от делания

```
### Какие типы бывают
Глобально, все в java делится на **примитивы** и **объекты**
Примитивных типов немного:
```java

boolean bool = true;
int a = 10;
long aa = 1010; // тоже самое, что и int, но вмещает числа побольше
float b = 1.5;
double bb = 1.515; // тоже самое, что и float, но вмещает числа с большей точностью
char c = 'a';

// есть еще несколько, но мы их опустим
```
Все остальные типы (их названия **всегда** начинаются с заглавной буквы - ссылочные)
### Строки
```java
String a = "abc";
System.out.println(a + "d"); // abcd

char b = a.charAt(1) // 'b'
System.out.println(a.replace("b", "e")); // aec

a.replace("b", "e")
System.out.println(a); // abc, replace не меняет исходную строку

System.out.println(a.substring(0, 1)); // ab
a.length()
```

### Массивы
```java
int[] arr1 = new int[3]; // нужно обязательно указать размер массива
int[] arr2 = new int[]{1, 2, 3}; // или сразу перечислить его элементы

String[] strs = new String[]{"abc", "cba"}; // можно создать массив любых элементов

System.out.print(arr1[0]); // обращение к элементам через []
arr1[0] = 10; // присваивание тоже

// можно делать многомерные массивы
int[][] matrix = new int[10][10];

arr1.lentgth // длина массива
```

```java
List<Integer> arr1 = new ArrayList<>(); 

int a = 10;
arr1.add(5);
arr1.add(new Integer(a)); // несмотря на то, что хранятся в массиве Integer, а не просто int,
// работать со значениями можно как обычно
System.out.print(arr1.get(0)); // 5
System.out.print(arr1.get(1)); // ошибка
System.out.print(arr1.size()); // 1 

arr1.get(0) = 10; // так нельзя, мы не можем присвоить результату выполнения функции значение 10
arr1.set(0, 10);

// еще один способ создания List'а
List<Integer> arr2 = List.of(1, 2, 3) // обратите внимание, что List.of() возвращает неизменяемый List
arr1.add(4) // ошибка
```
### If
```java
if (a == 10) {
	System.out.println("a");  
} else if (a == 15) {
	System.out.println("b");  
} else {
	System.out.println("c");  
}

```

### Циклы

```java
for (int i = 0; i < 10; i++) {  
    System.out.println("test" + i);  
}
```

```java
var arr1 = new int[]{1, 2, 3};
for (int i = 0; i < arr1.length; i++) {  
    System.out.println("test" + arr1[i]);  
}
// можно солиднее

for (int el : arr1) {  
    System.out.println("test" + el);  
}
```

```java
// для List аналогично
List<Integer> arr2 = new List.of(1, 2, 3);

for (int i = 0; i < arr2.size(); i++) {  
    System.out.println("test" + arr1.get(i));  
}
// можно солиднее

for (int el : arr2) {  
    System.out.println("test" + el);  
}
```

```java
// заполнение массива
var arr1 = new int[10];
for (int i = 0; i < 10; i++) {  
    arr[i] = i % 3;
}
```

```java
int i = 0;
while (i < 10) {
	System.out.println(i);  
	i++;
}
```

```java
Scanner sc = new Scanner(System.in);
int a = sc.nextInt(); // возьмёт следующее число из консоли

while (sc.hasNextInt()) {  // считывает всё, что есть
    System.out.println(sc.nextInt());  
}
```

### Ввод вывод
```java
System.out.println(10);
// можно ввести сокращение sout, Idea предложит заменить его System.out.println()
```
считать число
```java
Scanner in = new Scanner(System.in);
int k = in.nextInt();
```
считать массив:
```java
Scanner scanner = new Scanner(System.in);  
String line = scanner.nextLine();  // считываем строку
  
String[] rawArr = line.split(" "); // бъем строку на массив строк по пробелу
int[] arr = new int[rawArr.length]; // создаем массив такой же длины, но типа int[]
  
for (int i = 0; i < arr.length; i++) {  
    arr[i] = Integer.parseInt(rawArr[i]); // кажыдй элемент 1 массива приводим к типу int и кладем во 2 
}
  
System.out.println(Arrays.toString(arr)); // выводим массив, чтобы посмотреть, что в нем  лежит

```
или
```java
Scanner scanner = new Scanner(System.in);
List<Integer> list = new ArrayList<>();
while (scanner.hasNextInt()) {
	list.add(scanner.nextInt())
} // программа закончит ввод, как только наткнется на любое не число
```


### Импорты
```java
public class Main {  
    public static void main(String[] args) {  
        Scanner scanner = new Scanner(System.in); // не работает, нужно импортировать
    }  
}
```

```java
import java.util.Scanner;

public class Main {  
    public static void main(String[] args) {  
        Scanner scanner = new Scanner(System.in); // не работает, нужно импортировать
    }  
}
```

### Функции и методы
```java
public class Main {  
	// функции пишутся внутри класса Main, но не внутри функции main
	// public static - пока что просто пишем, без лишних вопросов
	// int[] - возвращаемый тип. Если ваша функция ничего не возрващет, пишите void
	// myFunction - название функции
	// int[] arr1 - аргументы, которы функция принимает (их может быть несколько)
	public static int[] myFunction(int[] arr1) {  
	    for (int i = 0; i < 10; i++) {  
	        if (arr1[i] % 2 != 0) {  
	            arr1[i] = -1;  
	        }  
	    }  
	    return arr1;  
	}
	
	public static void myFunction2(int a, int b) {
		System.out.println(a + b);
	}
	
    public static void main(String[] args) {  
        // код пишем здесь
    }  
}
```



# Задания
1) создайте функцию, которая выводит числа от 1 до 10
2) создайте функцию, которая выводит числа от 1 до n
3) создайте функцию, которая выводит числа из массива (его нужно считать, дана длина массива)
4) создайте функцию, которая выводит числа из List'a (считайте массив неизвестной длины через List<>) 
5) принимает строку и индекс символа разделения, верните 2 половину
6) принимает строку и индекс символа разделения, верните 1 половину
7) создайте функцию, которая принимает массив и возвращает новый массив, в котором на первой позиции находится первый элемент, а на последней - первый. В главной функции выведете вначале измененный массив, а потом исходный (именно в таком порядке)

# Задания с прошлой практики
_То, что успели в прошлый раз, пропускайте_

1) Считайте строку из консоли, преобразуйте её в массив (в любой, который вам больше нравится) чисел. Найдите максимальный и минимальный элементы в массиве, выведете их.
2) Считайте строку из консоли, преобразуйте её в массив. Создайте копию этого массива, разверните её и выведете в консоль (создать копию - обязательно, решения без этого не считаются)
3) Проверьте правильность скобочной последовательности из скобок 3-х видов: `(), [], {}` (если хотите, можете использовать [стек](https://docs.oracle.com/javase/8/docs/api/java/util/Stack.html), работать с ним нужно примерно как с `List`)
4) Дана строка, состоящая из строчных латинских букв и пробелов. Проверьте, является ли она палиндромом без учета пробелов (например, "аргентина манит негра"). Использовать `reverse` нельзя.
5) Дана строка. Посчитайте, сколько раз встречается каждое слово в этой строке (слово - любая последовательность символов, не содержащая пробелов). Использовать `split` нельзя.
6) Дан массив целых чисел и ещё одно целое число. Удалите все вхождения этого числа из массива (пропусков быть не должно).
7) Если вы очень быстро все делали и добрались до сюда, то можете попробовать сделать парсер HTTP
Будем парсить протокол HTTP (пока что мы не знаем, что это такое, но позже обязательно с ним познакомимся) и формировать ответ. 
(я бы использовал `split`)

На вход в консоль будет подаваться запрос типа
```
GET *path* HTTP/1.1
Some-Header1: Test
Some-Header2: 123
...

*body*
```
Строится этот запрос по таким правилам:
- первая строка всегда такая, как в примере. Вместо _path_ будет адрес запроса, в зависимости от которого вам сформировать ответ
- далее идет **n** строк, сформированных по принципу _headerName_: _headerValue_
- после идет пустая строка
- после пустой строки идет "тело" запроса. 
Вам нужно сформировать и вывести в консоль ответ:
```
HTTP/1.1 200 OK
Server: my-test-server

answer: *answer*
Got request with headres: *headers*
and body: *body*
```

answer, в зависимости от path, должен быть равен
-  _root_, если path == "/"
- _users page_, если path == "/users"
- page of user \*name\*_, если path == "user/*name*"
- _achievments of user \*name\*_, если  path == "user/\*name\*/achievments"

body - тело запроса
headers - хедеры запроса в формате \[Some-Header1=Test, Some-Header2=123, ...\]

Например, для запроса
```
GET / HTTP/1.1
Header1: Test
Header2: 123


some data 
```
вы должны вернуть 

```
HTTP/1.1 200 OK
Server: my-test-server

answer: root
Got request with headres: [Header1=Test, Header2=123]
and body: some data
```

еще пример
```
GET /users/test-user1 HTTP/1.1
Header: aaaa

another data
data data data
```

```
HTTP/1.1 200 OK
Server: my-test-server

answer: page of user test-user1
Got request with headres: [Header=aaaa]
and body: some data
data data data
```

