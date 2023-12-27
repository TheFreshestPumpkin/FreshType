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
                <a href="/user/${user.id}">${user.name}</a>
                <a href="/dialogues">Мои чаты</a>
                <a href="/allUsers">Найти собеседника</a>
                <a href="/groups/${user.id}">Группы</a>
            </form>
        <#else>
            <a href="/login">Войти</a></h1>
        </#if>
        </div>
        <div class="main">
            <#list posts?sort_by("pubTime") as post>
                <div class="post-container">
                    <div class="post-header">
                        <div class="post-author--no-cover">
                            <h3>${post.groupAuthor.groupName}</h3>
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