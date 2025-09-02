jsp<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Wordle Game</title>
</head>
<body>
    <h1>The word is hidden...</h1>
    <p>${status}</p>
    <form method="post">
        <input type="text" name="guess" maxlength="5" />
        <input type="submit" value="Guess" />
    </form>
</body>
</html>