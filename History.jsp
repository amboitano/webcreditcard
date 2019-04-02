<%-- 
    Document   : History
    Created on : Nov 25, 2018, 2:27:43 PM
    Author     : acmor
--%>

<%@page import="business.CreditCard"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account Log</title>
    </head>
    <body>
        <h2>Credit Card Log for: ${card.accountId}</h2>
        <br>
        <%-- replace this code with JSTL forEach --%>
        <%
            CreditCard card =
                    (CreditCard) request.getSession().getAttribute("card");
            
            for (String s : card.getCreditHistory()) {
                
            %>
            <%= s %><br>
            <% } %>

         %>
    </body>
</html>
