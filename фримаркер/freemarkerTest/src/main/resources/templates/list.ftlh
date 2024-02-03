<#import "macro.ftlh" as macro>

<!DOCTYPE html>
<html lang="ru">
<body>
<@macro.test/>
<p> Лист: </p>
<ul>
<#list list as item>
    <li>${item.login} ${item.password}</li>
</#list>
</ul>
<img src="/static/favicon.ico"/>
<p>${msg}</p>
</body>
</html>