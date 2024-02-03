<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<p> Лист: </p>
<ul>
    <#list list as item>
        <li>${item.login} ${item.password}</li>
    </#list>
</ul>
</body>
</html>