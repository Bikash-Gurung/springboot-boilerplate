<!doctype html>
<html lang="en">
<head>
    <meta charset=UTF-8" />
<#--    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">-->
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Welcome</title>
</head>
<body>
<#--Solution A-->
<#--    <#import "/spring.ftl" as spring />-->
<#--    <div style="margin-top: 10px">${spring.message("greeting", [], LOCALE)} ${USER_NAME}</div>-->
<#--    <br>-->

<#--    <div>${spring.message("content", [], LOCALE)}</b></div>-->
<#--    <br/>-->

<#--    <div>${spring.message("closingtext", [], LOCALE)}</div>-->

<#--Solution B-->
    <div style="margin-top: 10px">${GREETING} ${USER_NAME}</div>
    <br>

    <div>${CONTENT}</b></div>
    <br/>

    <div>${CLOSING_TEXT}</div>
</body>
</html>