<!DOCTYPE html>
<html>
<head>
    <title>Guest List</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/style.css"> <!-- Ensure you have this CSS file for styling -->
</head>
<body>
    <h1>Guest List</h1>
    <table border="1">
        <tr>
            <th>Full Name</th>
            <th>Phone Number</th>
            <th>Email</th>
            <th>Address</th>
        </tr>
        <#list guests as guest>
        <tr>
            <td>${guest.fullName}</td>
            <td>${guest.phoneNumber}</td>
            <td>${guest.email}</td>
            <td>${guest.address}</td>
        </tr>
        </#list>
    </table>
    <br>
    <form action="/guests" method="post">
        <label for="fullName">Full Name:</label>
        <input type="text" id="fullName" name="fullName" required>
        <label for="phoneNumber">Phone Number:</label>
        <input type="text" id="phoneNumber" name="phoneNumber" required>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" required>
        <button type="submit">Add Guest</button>
    </form>
</body>
</html>
