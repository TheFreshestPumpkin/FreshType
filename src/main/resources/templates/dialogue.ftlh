<#assign userAuth=user.email??>
<!DOCTYPE html>
<html>
    <head>
        <#include "styles.css">
        <title>FreshType</title>
    </head>

    <body>
        <#if userAuth>
            <div class="sidenav">
                <#if userAuth>
                    <form action="/logout" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input class="exit" type="submit" value="Выйти"/>
                    </form>
                <#else>
                    <a href="/login">Войти</a></h1>
                </#if>
                <a href="/">${user.name}</a>
                <a href="/dialogues">Мои чаты</a>
                <a href="/allUsers">Найти собеседника</a>
                <a href="/groups/${user.id}">Группы</a>
            </div>
        <#else>
            <a href="/login">Войти</a></h1>
        </#if>
        <div class="main">
            <#list messages as message>
                <div class="message">
                    <p>
                        <span class="mesTime">${message.mesTime}</span>
                        <span class="mesText">${message.text}</span>
                    </p>
                    <#list message.images as image>
                        <br>
                        <img src="/images/${image.id}" height="100px"/>
                    </#list>
                </div>
            </#list>
            <form action="/dialogue/${dialogue.id}/sendMessage" method="post" enctype="multipart/form-data">
                <input type="text" name="description" placeholder="Написать сообщение"/>
                <input type="file" name="file1"/>
                <input type="file" name="file2"/>
                <input type="file" name="file3"/>
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="submit" value="Отправить сообщение"/>
            </form>
            <hr>
        </div>
    </body>
</HTML>