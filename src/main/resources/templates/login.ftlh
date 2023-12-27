<!DOCTYPE html>
<html>
<head>
    <style><#include "regStyle.css"></style>
    <title>FreshType</title>
</head>
<body>
     <div class="form">
        <div class="title">Привет!</div>
        <div class="subtitle">Скорее заходи в аккаунт</div>
        <form action="/login" method="post">
            <div class="input-container ic2">
                <input id="email" class="input" type="email" name="username" placeholder=" " />
                <div class="cut"></div>
                <label for="email" class="placeholder">Email</label>
            </div>
            <div class="input-container ic2">
                <input id="email" class="input" type="password" name="password" placeholder=" " />
                <div class="cut cut-short"></div>
                <label for="password" class="placeholder">Пароль</>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Войти"/>
        </form>
        <a href="/registration">Зарегистрироваться</a>
     </div>
</body>
</html>