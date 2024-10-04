<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Registration</title>
</head>
<body>
    <h2>User Registration</h2>
    <form method="POST" action="/register">
        <label for="userId">User ID:</label>
        <input type="text" id="userId" name="userId" required><br><br>
        
        <label for="fullName">Full Name:</label>
        <input type="text" id="fullName" name="fullName" required><br><br>
        
        <label for="phoneNumber">Phone Number:</label>
        <input type="text" id="phoneNumber" name="phoneNumber" required><br><br>
        
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <label for="role">Role:</label>
        <input type="text" id="role" name="role" required value="TELLER"><br><br>
        
        <h3>Branch Information</h3>
        
        <label for="branchArea">Branch Area:</label>
        <input type="text" id="branchArea" name="branchArea" required><br><br>
        
        <label for="branchNemonic">Branch Nemonic:</label>
        <input type="text" id="branchNemonic" name="branchNemonic" required><br><br>
        
        <label for="branchCode">Branch Code:</label>
        <input type="text" id="branchCode" name="branchCode" required><br><br>
        
        <button type="submit">Register</button>
    </form>
</body>
</html>
