<h1>Rooms:</h1>
<ul>
    <#list rooms as room>
        <li>
            Room: <a href="/rooms/${room.number}">${room.number}</a>
        </li>
    </#list>
</ul>
<h2>Add a Room</h2>
<form action="/rooms/create" method="post" accept-charset="UTF-8">
    <div>
        <label for="type">Type:</label>
        <select name="type" id="type" required>
            <option value="STANDARD">Standard</option>
            <option value="DELUXE">Deluxe</option>
            <option value="SUITE">Suite</option>
        </select>
    </div>
    <button type="submit">Add</button>
</form>
