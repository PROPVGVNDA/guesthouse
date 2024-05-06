<!DOCTYPE html>
<html>
<head>
    <title>Create Booking</title>
</head>
<body>
    <h1>Create Booking</h1>
    <form action="/bookings/create" method="post">
        <div>
            <label for="guest">Guest:</label>
            <select id="guest" name="guestNumber"> <!-- Changed from guestId to guestNumber -->
                <#list guests as guest>
                    <option value="${guest.phoneNumber}">${guest.fullName}</option>
                </#list>
            </select>
        </div>
        <div>
            <label for="room">Room:</label>
            <select id="room" name="roomId">
                <#list rooms as room>
                    <option value="${room.number}">${room.type} - Room ${room.number}</option>
                </#list>
            </select>
        </div>
        <div>
            <label for="startDate">Start Date:</label>
            <input type="date" id="startDate" name="startDate" required>
        </div>
        <div>
            <label for="endDate">End Date:</label>
            <input type="date" id="endDate" name="endDate" required>
        </div>
        <button type="submit">Book</button>
    </form>
</body>
</html>
