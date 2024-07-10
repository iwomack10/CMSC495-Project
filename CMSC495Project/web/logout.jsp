<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="styles.css" rel="stylesheet" type="text/css">
        <title>Logout</title>
    </head>
    <body>
       <div id="main">
         <%@include file="WEB-INF/jspf/menus.jspf" %>
            <p></p>
            <p></p>
        <%-- Invalidate Session --%>
        <% session.invalidate();
        %>
        <h2>Thank you for visiting our Web Site.</h2>
        </div>
    </body>
</html>
