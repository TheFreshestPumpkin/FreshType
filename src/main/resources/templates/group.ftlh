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
            <#if user.id = owner.id>
                <form action="/group/${group.id}/addPost" method="post" enctype="multipart/form-data">
                    <input type="text" name="description" placeholder="Чем хотите поделиться с миром?"/>
                    <input type="file" name="file1"/>
                    <input type="file" name="file2"/>
                    <input type="file" name="file3"/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="submit" value="Опубликовать"/>
                </form>
            <#else>
                <#if isSubscribed>
                    <form action="/unsubscribe/${group.id}" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input type="submit" value="Отписаться"/>
                    </form>
                <#else>
                    <form action="/subscribe/${group.id}" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input type="submit" value="Подписаться"/>
                    </form>
                </#if>
            </#if>
            <div class="groupInfo">
                <span clas="groupName">${group.groupName}</span>
                <span clas="groupDescription">${group.description}</span>
                <span clas="groupTime">${group.createTime}</span>
                <img src="/images/${group.avatar.id}" height="100px"/>
            </div>
            <#list posts?reverse as post>
                <div class="post-container">
                    <div class="post-header">
                        <div class="post-author--no-cover">
                            <h3>${user.name}</h3>
                        </div>
                    </div>
                    <div class="post-body">
                        <div class="post-summary">
                            <p>${post.description}</p>
                        </div>
                        <#list post.images as image>
                            <br>
                            <img src="/images/${image.id}" height="100px"/>
                        </#list>
                    </div>
                    <div class="post-footer">
                        <ul>
                          <li class="published-date">${post.pubTime}</li>
                          <li class="shares"><span class="numero">${post.likesCount()}</span></li>
                        </ul>
                    </div>
                    <form action="/${user.id}/${post.id}/changeLike" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input type="submit" value="like"/>
                    </form>
                    <#list post.comments as comment>
                        <div class="comment">
                            <span class="commentAuth">${comment.author.name}</span>
                            <span class="commentTime">${comment.comTime}</span>
                            <span class="commentText">${comment.text}</span>
                        </div>
                    </#list>
                    <form id="{post.id}" action="/${user.id}/${post.id}/addComment" method="post">
                        <input type="text" name="text" placeholder="введите комментарий..."/>
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input type="submit" value="Прокомментировать"/>
                    </form>
                </div>
            </#list>
        </div>
    </body>
</HTML>