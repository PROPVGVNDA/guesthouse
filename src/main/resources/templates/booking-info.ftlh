<!DOCTYPE html>
<html>
<head>
    <title>Booking Details</title>
</head>
<body>
    <h1>Деталі Booking</h1>
    <div>
        <strong>Booking ID:</strong> ${booking.id}<br>
        <strong>Guest Name:</strong> ${booking.guest.fullName}<br>
        <strong>Room Number:</strong> ${booking.room.number}<br>
        <strong>Room Type:</strong> ${booking.room.type}<br>
        <strong>Start Date:</strong> ${booking.startDate?string("yyyy-MM-dd")}<br>
        <strong>End Date:</strong> ${booking.endDate?string("yyyy-MM-dd")}<br>
        <strong>Status:</strong> ${booking.status}<br>
        <strong>Price:</strong> ${booking.price}<br>
    </div>
    <div>
        <a href="/bookings">Return to bookings list</a>
    </div>
</body>
</html>


