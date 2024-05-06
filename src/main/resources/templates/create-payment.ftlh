<!DOCTYPE html>
<html>
<head>
    <title>Create Payment</title>
</head>
<body>
    <h1>Create Payment</h1>
    <form action="/payments/create" method="post">
        <div>
            <label for="booking">Booking:</label>
            <select id="booking" name="bookingID">
                <#list bookings as booking>
                    <option value="${booking.id}">${booking.id}</option>
                </#list>
            </select>
        </div>
        <button type="submit">Pay</button>
    </form>
</body>
</html>
