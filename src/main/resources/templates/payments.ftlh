<h1>Payments:</h1>
<ul>
    <#list payments as payment>
        <li>
            Payment: <a href="/payments/${payment.id}">${payment.id}</a>
        </li>
    </#list>
</ul>

