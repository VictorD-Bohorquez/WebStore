<%-- 
    Document   : error
    Created on : 20/11/2020, 10:49:36 AM
    Author     : DANIEL
--%>

<%@page isErrorPage="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" >
        <title>P&aacute;gina de Error</title>
    </head>
    <body>
        <%
            String de = request.getParameter("de");
        %>
        <div class="container">
            <h1>P&aacute;gina de errores</h1>
            <div class="alert alert-danger" role="alert">
                <p>
                    Ocurrio un error en : <%= de%>
                </p>
                <p>
                    El error es  : <%= exception%>
                </p>
            </div>
            <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" ></script>
            <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" ></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" ></script>
    </body>
</html>
