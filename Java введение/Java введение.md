# Общее
### Компиляция 
- Как вы запускаете свои программы (на языках, на которых вы писали до этого)?
- откуда берутся `.exe` файлы, почему их можно запустить просто нажав мышкой на ярлык, ничего дополнительно не скачивая?

Программа написана текстом, а компьютер понимает только бинарные инструкции, значит, чтобы программа запускалась, она в какой-то момент времени должна быть преобразована в понятный компьютеру код. В общем случае, этот процесс преобразования программы во что-то другое называется **компиляцией**.
**Java** - компилируемый язык программирования. Хотя `.exe`  файл собрать не так просто, как, например, из программы на **c++**

Этап **компиляции** очень важен для **Java**, например, **компилятор** может выявить некоторые ошибки в программе, даже не запуская её.

### Создание проекта

Чтобы создать проект, нужно открыть IntelliJ Idea -> file -> new Project
Отрывшееся окно будет выглядеть примерно так:
![](attachments/Pasted%20image%2020231005143018.png)
_В строке JDK у вас может быть другая версия, главное не выбирайте ниже 11_

Созданный проект будет выглядеть примерно так:
![](attachments/Pasted%20image%2020231005204623.png)
_Создавать новые классы нужно в папке src_
_В папке out лежат скомпилированные файлы. В ближайшем будущем они вам не понадобятся, но если очень хочется, можно посмотреть_
### Hello World!
В Java любая программа - это класс (что это такое мы поговорим на занятии по *ООП*) . Чтобы программу можно было запустить, этот класс должен иметь специальный метод `main`.
Таким образом, минимальная программа на Java выглядит так:
```java
public class HelloWorld {  
    public static void main(String[] args) {  
         System.out.println("Hello world!");
    }  
}
```

Название объявляемого класса должно совпадать с названием файла, в котором этот класс находится.
Чтобы приведенная выше программа запустилась, она должна находиться в файле `HelloWorld.java`
### Запуск
Так как мы работает в IntelliJ Idea, запустить программу можно просто нажав на зелёный треугольник. Но, на самом деле, так как **Java - компилируемый язык**, запуск программы будет состоять из двух частей
1) компиляция
2) выполнение скомпилированного кода

Ошибка может возникнуть на любом из этих этапов. При возникновении **ошибки компиляции** программа не будет запущена вообще

![](attachments/Pasted%20image%2020231005205254.png)
![](attachments/Pasted%20image%2020231005205426.png)
_Какая из этих ошибок произошла на этапе компиляции, а какая на этапе выполнения?_


Подходить к решению этих ошибок нужно по-разному. В случае ошибки компиляции вы не можете использовать отладочные принты или дебаггер

В ближайшее время мы с таким не столкнёмся, но когда мы будем пытаться собрать большой проект каким-нибудь gradle'ом, ошибки компиляции могут начать появляться там, где их видеть не хотелось бы
# Синтаксис
### Типизация
В Java тип переменной фиксируется при её объявлении и не может меняться в будущем
```java
int a;
Sysytem.out.print(a);
String b = "test";
a = 15;
a = "test"; // нельзя
```

Начиная с Java 10 (вы же поставили 11 или выше, правда?) можно делать так
```java
// часто встречаются сложные конструкции типа
Scanner scanner = new Scanner(System.in);

// Это можно немного упростить
var scanner = new Scanner(System.in);
```
Это **не делает** java динамически типизированным языком, так как тип переменной `scanner` будет выведен **компилятором** на этапе **компиляции**, таким образом, во время **выполнения** кода тип переменной будет зафиксирован

```java
var scanner = new Scanner(System.in);
scanner = "test"; // так делать всё еще нельзя
// переменная scanner неявно имеет тип Scanner
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

```java
Scanner sc = new Scanner(System.in);
List<Integer> lst = new ArrayList<>();
int[] arr = new int[]{1, 2, 3} 
// Чуть позже мы увидим, что это такое, пока что для нас важно, что переменные sc, lst и arr - ссылочные

String s1 = "abc"; // строка - это не притивный тип, в отличии от char
Integer i1 = 10; // Это не тоже самое, что int i1 = 10
// зачем это нужно, увидем чуть позже
```

Для примитивных типов в переменной хранится значение.
Для ссылочных - ссылка на объект

Из этого факта следуют 2 очень важных отличия **ссылочных** и **примитивных** типов данных:

1) Присваивание значения одной ссылочной переменной в другую копирует не объект, а ссылку на него
```java
int[] arr1 = new int[]{1, 2, 3};
int[] arr2 = arr1; // скопировалась только ссылка, а не сам массив

arr2[0] = 10;
System.out.print(arr1[0]); // 10, а не 1
```
2) Сравнение значений ссылочных переменных будет проверять, что обе переменные ссылаются на один и тот же объект, а не что эти объекты равны
```java
int[] arr1 = new int[]{1, 2, 3}; // ссылочная переменная
int[] arr2 = new int[]{1, 2, 3}; // ссылочная переменная
System.out.print(arr1 == arr2) // false

System.out.print(arr1) // [I@1b28cdfa
System.out.print(arr2) // [I@eed1f14
// при выводе ссылочной переменной мы получаем адрес ячейки памяти, на которую эта переменная ссылается

int i = 12658; // эта переменная не ссылочная

System.out.print(i)  // 12658
```
_при сравнении `arr1 == arr2` будет проверяться равенство ссылок (ссылки - это просто числа, адреса ячеек памяти)_

Ещё у всех ссылочных типов есть особое значение `null`
```java
int a = 0;
Sysytem.out.print(a); // 0

a = null; // так нельзя

int[] b = null; // а так - можно

```
`null` означает, что эта ссылка никуда не ведет, объекта, на который она должна ссылаться не существует

### Строки

```java
String a = "abc";
System.out.println(a + "d"); // abcd
System.out.println("value of 'a' is " + a);
System.out.println("value of 'a' is %s".format(a));

System.out.println(a + 5); // ?
System.out.println(Integer.parseInt("10") + 5); // ?
System.out.println("10" + String.valueOf(5)); // ?

char b = a.charAt(1) // 'b'

System.out.println(a.replace("b", "e")); // aec

a.replace("b", "e")
System.out.println(a); // abc, replace не меняет исходную строку

a.length()
```

https://metanit.com/java/tutorial/7.2.php
### Массивы

В Java есть удобные и неудобные массивы

Неудобные - это простые конструкции фиксированного размера, и поэтому они могут хранить только заданное количество элементов.
```java
int[] arr1 = new int[3]; // нужно обязательно указать размер массива
int[] arr2 = new int[]{1, 2, 3}; // или сразу перечислить его элементы

String[] strs = new String[]{"abc", "cba"}; // можно создать массив любых элементов

System.out.print(arr1[0]); // обращение к элементам через []
arr1[0] = 10; // присваивание тоже

// можно делать многомерные массивы
int[][] matrix = new int[10][10];
```

Удобные - это реализации интерфейса List (что такое интерфейс мы поговорим, но не сегодня, пока что это неважно), самая часто используемая - ArrayList
```java
List<Integer> arr1 = new ArrayList<>();
```
В треугольных скобочках указывается тип хранимых элементов. Обратите внимание, что вместо `int` тут мы пишем `Integer`. 
Это - класс-обертка, такие есть для всех примитивов. Зачем они нужны и почему нельзя писать просто `int` мы пока что вникать не будем, просто запомните, что коллекции не могут хранит примитивы.

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

**Обратите внимание**, что массивы в любом их проявлении - **ссылочные** типы данных, со всеми вытекающими
```java
int[] arr1 = new int[]{1, 2, 3}
int[] arr2 = arr1 // скопировалась только ссылка, а не сам массив
```
### Map, он же словарь, он же json
Структура, умеющая по ключу хранить значение
В Java за это отвечает интерфейс `Map` и самая часто используемая реализация `HashMap`
```java
Map<String, Integer> map1 = new HashMap<>();
map1.put("test", 0);
System.out.print(map1.get("test")); // 0
System.out.print(map1.get("t")); // null
```
_Почему get возвращает null, хотя до этого мы не могли положить его в целочисленную переменную?_
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

вместо If можно использовать `switch`
```java
String a = "test";
switch (a) {
	case "t":
		System.out.println("t");
		break;
	case "test":
		System.out.println("test");
		break;
	default:
		System.out.println("default");
		break;
}
```
При внешней схожести операторов if и switch не забывайте, что выбор вариантов выполнения оператор множественного выбора switch основывает на КОНКРЕТНОМ ЗНАЧЕНИИ, тогда как в if может быть любое логическое выражение. Учитывайте данный факт, проектируя ваш код.
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
    System.out.println("test" + arr1[i]);  
}
// можно солиднее

for (int el : arr2) {  
    System.out.println("test" + el);  
}
```

```java
int i = 0;
while (i < 10) {
	System.out.println(i);  
	i++;
}
```

`continue` и `break`

```java
int i = 0;
while (true) {
	if (i >= 10) {
		break;
	}
	System.out.println(i);  
	i++;
}
```

```java
for (int i = 0; i < arr1.length; i++) {  
	if (i % 2 == 0) continue;
    System.out.println(arr1[i]);  
}
// 1 3 5 7 9
```
### Ввод

```java
Scanner sc = new Scanner(System.in);
int a = sc.nextInt(); // возьмёт следующее число из консоли

while (sc.hasNextInt()) {  // считывает всё, что есть
    System.out.println(sc.nextInt());  
} 
```


считать массив:
```java
Scanner scanner = new Scanner(System.in);  
String line = scanner.nextLine();  
  
String[] rawArr = line.split(" ");  
int[] arr = new int[rawArr.length];  
  
for (int i = 0; i < arr.length; i++) {  
    arr[i] = Integer.parseInt(rawArr[i]);  
  
}  
  
System.out.println(Arrays.toString(arr));

```


# Практика
1) Считать из консоли выражение типа `a somOp b`, где `a` и `b` - произвольный числа, `someOp` - `+ - * /`. Вывести результат 
2) Дан массив, состоящий из целых чисел. Нумерация элементов начинается с 0. Напишите программу, которая выведет элементы массива, номера которых четны (0, 2, 4...).
3) Дан массив, состоящий из целых чисел. Напишите программу, которая в данном массиве определит количество элементов, у которых два соседних элемента меньше текущего
# Задания
1) Считайте строку из консоли, преобразуйте её в массив (в любой, который вам больше нравится) чисел. Найдите максимальный и минимальный элементы в массиве, выведете их.
2) Считайте строку из консоли, преобразуйте её в массив. Создайте копию этого массива, разверните её и выведете в консоль (создать копию - обязательно, решения без этого не считаются)
3) Проверьте правильность скобочной последовательности из скобок 3-х видов: `(), [], {}` (если хотите, можете использовать [стек](https://docs.oracle.com/javase/8/docs/api/java/util/Stack.html), работать с ним нужно примерно как с `List`)
4) Дана строка, состоящая из строчных латинских букв и пробелов. Проверьте, является ли она палиндромом без учета пробелов (например, "аргентина манит негра"). Использовать `reverse` нельзя.
5) Дана строка. Посчитайте, сколько раз встречается каждое слово в этой строке (слово - любая последовательность символов, не содержащая пробелов). Использовать `split` нельзя.
6) Дан массив целых чисел и ещё одно целое число. Удалите все вхождения этого числа из массива (пропусков быть не должно).
7) Парсер HTTP 
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