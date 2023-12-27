<#assign userAuth=user.email??>
<!DOCTYPE html>
<html>
    <head>
        <#include "styles.css">
        <title>FreshType</title>
    </head>

    <body>
        <div class="sidenav">
            <#if userAuth>
                <form action="/logout" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input class="exit" type="submit" value="Выйти"/>
                </form>
            <#else>
                <a href="/login">Войти</a></h1>
            </#if>
            <#if user.id != user.id>
                <form action="/dialogue/${user.id}/${user.id}" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="submit" value="Написать"/>
                </form>
            <#else>
                <a href="/">${user.name}</a>
                <a href="/dialogues">Мои чаты</a>
                <a href="/allUsers">Найти собеседника</a>
                <a href="/groups/${user.id}">Группы</a>
            </#if>
        </div>
        <div class="main">
            <#if user.id == watchingUser.id>
                <form action="/toCreateGroup" method="get">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="submit" value="Создать паблик"/>
                </form>
            </#if>
            <p><h3>Вы подписаны на паблики:</h3></p>
            <#list subgroups as group>
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
            <p><h3>Ваши паблики:</h3></p>
            <#list owngroups as group>
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
            <a href="/allGroups">Найти группы</a>
        </div>
    </body>
</HTML>