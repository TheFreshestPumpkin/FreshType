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
            <h3>Все паблики</h3>
            <form action="/allGroups" method="get">
                Найти мастера по имени: <input type="text" name="groupName"><br>
                <input type="submit" value="Поиск"/>
            </form>
            <#list groups as group>
                <div class="group">
                    <p>
                        <img src="/images/${group.avatar.id}" height="100px"/>
                        <span class="name">${group.groupName}</span>
                        <form action="/group/${group.id}" method="get">
                            <input type="hidden" name="_csrf" value="${_csrf.token}">
                            <input type="submit" value="Перейти"/>
                        </form>
                    </p>
                </div>
            </#list>
        </div>
    </body>
</HTML>