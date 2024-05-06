<h1>Bookings:</h1>
<ul>
    <#list bookings as booking>
        <li>
            Booking: <a href="/bookings/${booking.id}">${booking.id}</a>
        </li>
    </#list>
</ul>

