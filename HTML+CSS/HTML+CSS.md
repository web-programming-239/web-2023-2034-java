## Postion
устанавливает способ позиционирования элемента относительно окна браузера или других объектов на веб-странице.
https://developer.mozilla.org/en-US/docs/Web/CSS/position

## Сетки
![](attachments/Pasted%20image%2020230929034223.png)
_Слева без сетки, так делать не надо, надо как справа - с сеткой_

Для этого используют:
### Flexbox
_[что это в такое в целом](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_flexible_box_layout/Basic_concepts_of_flexbox)_

Сетка для расположения элементов в один ряд (или столбец), например, как здесь:
![](attachments/Pasted%20image%2020230929034745.png)
вместе с ним вам понадобятся [justify](https://developer.mozilla.org/en-US/docs/Web/CSS/justify-content) и [align-items](https://developer.mozilla.org/en-US/docs/Web/CSS/align-items)

### Grid
это контейнер-сетка, позволяет располагать элементы в двумерной системе
Примеры:
![](attachments/Pasted%20image%2020230929034926.png)![](attachments/Pasted%20image%2020230929034938.png)
![](attachments/Pasted%20image%2020230929034943.png)
![](attachments/Pasted%20image%2020230929034947.png)

### Float
![](attachments/Pasted%20image%2020230929035014.png)


## Псевдоклассы в CSS
Используются для применения правил к элементам, находящимся в определенном состоянии (наприме, когда на элемент наведен курсор)
Почитать [тут](https://developer.mozilla.org/ru/docs/Web/CSS/Pseudo-classes)

## Единицы измерения
Пиксели - это плохо, так как на разных устройствах количество пикселей на экране разное, следовательно, ваш сайт будет вести себя непредсказуемо. Чтобы задавать размеры не абсолютно, а относительно (размеров экрана, других элементов или даже шрифта) можно использовать другие [единицы измерения](https://learn.javascript.ru/css-units)

![](attachments/Pasted%20image%2020230930161708.png)
