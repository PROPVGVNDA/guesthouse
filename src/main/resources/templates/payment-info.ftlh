<!DOCTYPE html>
<html>
<head>
    <title>Payment Details</title>
</head>
<body>
    <h1>Payment Details</h1>
    <div>
        <strong>Payment ID</strong> ${payment.id}<br>
        <strong>Booking ID</strong> ${payment.booking.id}<br>
        <strong>Booking Price</strong> ${payment.booking.price}<br>
    </div>
    <div>
        <a href="/bookings">Return to payments list</a>
    </div>
</body>
</html>
