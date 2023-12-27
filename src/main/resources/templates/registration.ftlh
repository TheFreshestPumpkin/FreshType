<!DOCTYPE html>
<html>
<head>
    <style><#include "regStyle.css"></style>
    <title>FreshType</title>
</head>
<body>
    <div class="form">
        <div class="title">Привет!</div>
        <div class="subtitle"><#if errorMessage??><h2>${errorMessage}</h2><#else>давай сделаем тебе аккаунт</#if></div>
        <form action="/registration" method="post">
            <div class="input-container ic1">
                <input id="name" class="input" type="text" name="name" placeholder=" " />
                <div class="cut"></div>
                <label for="name" class="placeholder">Имя</label>
            </div>
            <div class="input-container ic2">
                <input id="email" class="input" type="email" name="email" placeholder=" " />
                <div class="cut"></div>
                <label for="email" class="placeholder">Email</label>
            </div>
            <div class="input-container ic2">
                <input id="email" class="input" type="password" name="password" placeholder=" " />
                <div class="cut cut-short"></div>
                <label for="password" class="placeholder">Пароль</>
            </div>
            <div class="cut"></div>
            <input type="file" name="file"/>
            <div class="cut"></div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Зарегистрироваться"/>
        </form>
        <#if errorMessage??>
            <h2>${errorMessage}</h2>
        </#if>
    </div>
</body>
</html>