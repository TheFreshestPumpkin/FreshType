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
            <h3>Все пользователи</h3>
            <form action="/allUsers" method="get">
                Найти пользователя: <input type="text" name="name"><br>
                <input type="submit" value="Поиск"/>
            </form>
            <#list users as user>
                <div class="user">
                    <p>
                        <span class="name">${user.name}</span>
                        <a href="/user/${user.id}">Перейти</a></h1>
                    </p>
                </div>
            </#list>
        </div>
    </body>
</HTML>