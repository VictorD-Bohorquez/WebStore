<%-- 
    Document   : index
    Created on : 24/11/2020, 11:14:23 AM
    Author     : DANIEL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp?de=index.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Página de Inicio</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" >
    </head>
    <body>
    <div id="login">
        <h3 class="text-center text-white pt-5">Login form</h3>
        <div class="container">
            <div id="login-row" class="row justify-content-center align-items-center">
                <div id="login-column" class="col-md-6">
                    <div id="login-box" class="col-md-12">
                        <form id="login-form" class="form" action="login" method="post">
                            <h3 class="text-center text-info">Login</h3>
                            <div class="form-group">
                                <div><label for="username" class="text-info">Email:</label><br></div>
                                <div>
                                    <input 
                                        type="text" 
                                        name="idu" 
                                        id="idu"
                                        required="requiered" 
                                        placeholder="Email de usuario"
                                        class="form-control"
                                        />
                                </div>
                            </div>
                            <div class="form-group">
                                <div><label for="password" class="text-info">Contraseña:</label><br></div>
                                <div>
                                    <input 
                                        type="password" 
                                        name="password" 
                                        id="password"
                                        required="requiered" 
                                        placeholder="Contraseña"
                                        class="form-control"
                                        />
                                </div>
                            </div>
                            <div class="form-group">
                                <input type="submit" name="submit" class="btn btn-info btn-md" value="Entrar">
                            </div>
                            <div id="register-link" class="text-right">
                                <a href="clienteForm.jsp" class="text-info">Registrate</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" ></script>
</html>
