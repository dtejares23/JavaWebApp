<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    
    <!--JS Script of Loader -->
	<script type="text/javascript" src="resources/scripts/loader.js"></script>
	<!--CSS Script of Loader -->
	<link rel="stylesheet" type="text/css" href="resources/scripts/global.css" media="screen" />

</head>
<body>
    <h2>Login</h2>
    <form method="POST" action="/login">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <button type="submit">Login</button>
    </form>
    <!-- 
    <p th: if="${param.error}" style="color:red;">
    	Invalid credentials. Please try again.
    </p>
     -->
</body>
</html>

