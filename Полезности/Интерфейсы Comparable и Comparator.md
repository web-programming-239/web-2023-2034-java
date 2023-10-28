# Сортировка как не тривиальная проблема.

## Примитивы в массиве
Вспомним как сортировать что-то в java не ручками.

```java
int[] numbers = new int[]{7, 5, 4, 8, 10, -45};
char[] chars = new char[]{'X', 'A', 'C', 'B'};

Arrays.sort(numbers);
Arrays.sort(chars);

System.out.println(Arrays.toString(numbers));
System.out.println(Arrays.toString(chars));
```

Вывод:

```java
[-45, 4, 5, 7, 8, 10]
[A, B, C, X]
```

Как это работает? Класс **Array** имеет перегруженный метод **sort()**

![img.png](img.png)

Т.е. сортировка возможна для всех примитивов

## Объекты в массиве

```java
String[] strings = new String[]{"asda", "jggh", "aaaa"};
  
Arrays.sort(strings);

System.out.println(Arrays.toString(strings));
```

Вывод:

```java
[aaaa, asda, jggh]
```

**String** - объект, для него не существует отдельного метода сортировки. Но есть:

![img_1.png](img_1.png)

Т.е. **sort** может получить массив любых объектов, но как он их сортирует? Если с примитивами все понятно,
для каждого определены операторы сравнения (**>**, **==**, **<**), то с объектами не все так очевидно. 

Если мы залезем в цепочку сортировок, то в конце концом мы упремся в строчку

```java
pivot.compareTo(a[mid])
```

Нас тут интересует функция **compareTo**, откуда берется этот метод, если мы перейдем по этому методу, то попадем в
**Comparable.java**, в нем лежит интерфейс **Comparable** с единственным методом **compareTo**.

> Параметр **T** в угловых скобках называется универсальным параметром, так как вместо него можно подставить любой тип.
> При этом пока мы не знаем, какой именно это будет тип: String, int или какой-то другой.
> Причем буква **T** выбрана условно, это может и любая другая буква или набор символов. 
> [Generics](https://metanit.com/java/tutorial/3.11.php)

```java 
package java.lang;
import java.util.*;

public interface Comparable<T> {
 
    public int compareTo(T o);
}

```

Заметим что он возвращает **int**.

Теперь зайдем в класс **String**, он реализует **Comparable\<String>**.

```java 
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence,
               Constable, ConstantDesc 
```

Т.е. мы можем 

```java
String a = "A";
String b = "a";
String c = b.toUpperCase(); // "A"

System.out.println(b.compareTo(a)); // "a" > "A"
System.out.println(a.compareTo(b)); // "A" < "a"
System.out.println(a.compareTo(c)); // "A" == "A"
```

Вывод:

```java
32
-32
0
```

**compareTo** возвращает "расстояние" сравниваемых объектов

Как это использовать?

Опишем класс **Human**

```java
class Human {
    int age, salary, childrenAmount;
    boolean isMale;
    String surname;
}
```

А в **main** создадим массив людей - **humans**.

```java 
Arrays.sort(humans);
```

Будет ошибка запуска

![img_3.png](img_3.png)

Давайте реализуем для **Human** Интерфейс **Comparable\<Human>**

```java 
class Human implements Comparable<Human>{

    ....
    
    @Override
    public int compareTo(Human o) {
        return 0;
    }
}
```

Теперь остается лишь описать правило сравнения. Оно вытекает из задачи, решаемой сортировкой, например, мы хотим 
сделать социальные выплаты малоимущим, многодетным, молодым.

```java 
@Override
public int compareTo(Human o) {
    double salaryWeight = -1;
    double ageWeight = 1;
    double childrenAmountWeight = 100;
    return (int)(salaryWeight * (-this.salary + o.salary)
            + ageWeight * (-this.age + o.age)
            + childrenAmountWeight *
             (-this.childrenAmount + o.childrenAmount));
}

```

Вот пример реализации с весами, но зачастую в **compareTo** не прописываются такие сложные сравнения для больших 
объектов, скорее всего вам часто нужно будет сортировать по фамилии например

```java 
@Override
public int compareTo(Human o) {
    return this.surname.compareTo(o.surname);
}
```

## Comparator

Для более со специфичных задач, как выше, или, например, отсортировать по заданным параметрам, пишут свой компаратор
реализующий интерфейс **Comparator\<E>**.

```java 
class HumanChildrenComparator implements Comparator<Human>{
 
    @Override
    public int compare(Human a, Human b){
        return a.childrenAmount - b.childrenAmount;
    }
}
```

Метод **sort** имеет еще одну перегрузку

![img_2.png](img_2.png)


```java
Arrays.sort(humans, new HumanChildrenComparator()); 
```

## Сортируем не только массивы

```java
ArrayList<Human> humanArrayList = new ArrayList<>();
LinkedList<Human> humanLinkedList = new LinkedList<>();

....

humanArrayList.sort(new HumanChildrenComparator());
humanLinkedList.sort(new HumanChildrenComparator());
```

У **ArrayList** и **LinkedList** есть единственный метод **sort**, принимающий компаратор, если передать **null**,
то у класса объекта листа должен быть реализован интерфейс **Comparable**.

Также можно по аналогии с **Arrays** использовать **Collections**

```java
Collections.sort(humanArrayList);
```



