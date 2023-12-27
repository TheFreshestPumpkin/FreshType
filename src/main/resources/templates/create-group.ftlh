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
            <#if userAuth>
                <form action="/createGroup" method="post" enctype="multipart/form-data">
                    <input type="text" name="name" placeholder="Название паблика"/>
                    <input type="text" name="description" placeholder="Описание паблика..."/>
                    <input type="file" name="file1"/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="submit" value="Опубликовать"/>
                </form>
            </#if>
        </div>
    </body>
</HTML>