<%-- 
    Document   : clienteForm
    Created on : 24 ene 2021, 14:56:02
    Author     : DANIEL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" />
        <title>Registro de Cliente</title>
    </head>
    <body>
        <div class="container">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <a class="navbar-brand" href="index.jsp">Home</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
                    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">

                        <li class="nav-item active">
                            <a class="nav-link" href="home.jsp">Categorias</a>
                        </li>
                    </ul>
                </div>
            </nav>

            <div class="card-header bg-primary">
                <h1>
                    Datos del Cliente
                </h1>
            </div>

            <div class="card-body">
                <form 
                    action="UsuarioServlet?accion=guardar" 
                    method="POST" 
                    name="formUsuario" 
                    id="formUsuario">

                    <input 
                        class="form-control"
                        type="hidden" 
                        name="id" 
                        id="id" 
                        value=""/>

                    <div class="form-group row">
                        <div class="col-sm-2">
                            <label class="col-sm-2 col-form-label">Nombre:</label>
                        </div>

                        <div class="col-sm-10">
                            <input 
                                id="txtnombre"
                                type="text" 
                                name="txtnombre" 
                                class="form-control"
                                required="requiered" 
                                placeholder="Nombre del usuario"
                                value=""/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-2">
                            <label class="col-sm-2 col-form-label">Apellido paterno:</label>
                        </div>

                        <div class="col-sm-10">
                            <input 
                                type="text" 
                                name="txtpaterno" 
                                id="txtpaterno"
                                required="requiered" 
                                placeholder="Apellido paterno del usuario"
                                class="form-control"
                                value=""/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-2">
                            <label class="col-sm-2 col-form-label">Apellido materno:</label>
                        </div>

                        <div class="col-sm-10">
                            <input 
                                type="text" 
                                name="txtmaterno" 
                                id="txtmaterno"
                                required="requiered" 
                                placeholder="Apellido materno del usuario"
                                class="form-control"
                                value=""/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-2">
                            <label class="col-sm-2 col-form-label">Nickname:</label>
                        </div>

                        <div class="col-sm-10">
                            <input 
                                type="text" 
                                name="txtusuario" 
                                id="txtusuario"
                                required="requiered" 
                                placeholder="Nickname del usuario"
                                class="form-control"
                                value=""/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-2">
                            <label class="col-sm-2 col-form-label">email:</label>
                        </div>

                        <div class="col-sm-10">
                            <input 
                                type="text" 
                                name="txtemail" 
                                id="txtemail"
                                required="requiered" 
                                placeholder="Email del usuario"
                                class="form-control"
                                value=""/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-10">
                            <input 
                                type="hidden" 
                                name="txttipo" 
                                id="txttipo"
                                value="C"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-2">
                            <label class="col-sm-2 col-form-label">Clave:</label>
                        </div>

                        <div class="col-sm-10">
                            <input 
                                type="text" 
                                name="txtclave" 
                                id="txtclave"
                                required="requiered" 
                                placeholder="Clave del usuario"
                                class="form-control"
                                value=""/>
                        </div>
                    </div>
                    <button type="submit" class="form-group row offset-md-0 btn btn-outline-primary col-md-12">
                        Guardar                               
                    </button>
                </form>
            </div>
        </div>




    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" ></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</body>
</html>
