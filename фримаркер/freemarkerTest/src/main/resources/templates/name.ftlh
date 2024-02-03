<#import "head.ftlh" as macro>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
</head>

<body style="font-size: large; color: blue;">
<@macro.tasks/>
<@macro.welcome/>
<p style="color: crimson">Hello, ${name}</p>


<#if name == "d">
    I love you, d!!!
<#elseif name == "kkk">
    Be careful, kkk
<#elseif name== "ttt">
    GO OUT!!!
</#if>

</body>
</html>