<%-- 
    Document   : subCategoriaForm
    Created on : 21 ene 2021, 18:12:21
    Author     : DANIEL
--%>

<%@page import="com.ipn.mx.utilerias.LoginManagerVF"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp?de=listaDeCategorias.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%LoginManagerVF a = new LoginManagerVF();
        if (!a.isAdm(request, response)) {
            response.sendRedirect("index.jsp");
        }
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>P&aacute;gina de formulario</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" />
    </head>
    <body>
        <div class = "container">
            <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
                <a class="navbar-brand" href="home.jsp">Inicio</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
                    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                        <li class="nav-item active">
                            <a class="nav-link" href="CategoriaServlet?accion=listaDeCategorias">Categor&iacute;as</a>
                        </li>
                        <li class="nav-item active">
                            <a class="nav-link" href="UsuarioServlet?accion=listaDeUsuarios">Usuarios</a>
                        </li>
                        <li class="nav-item active">
                            <a class="nav-link" href="ProductosForm.jsp">Nuevo Producto</a>
                        </li>
                        <li class="nav-item active">
                            <a class="nav-link" href="logout">LogOut</a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
        <div class="container">
        <div class="card-header text-white bg-dark">
            <h1>
                Datos de subCategoria
            </h1>
        </div>

        <div class="card-body">
            <form 
                action="subCategoriaServlet?accion=guardar" 
                method="POST" 
                name="formCategoria" 
                id="formCategoria">

                <input 
                    class="form-control"
                    type="hidden" 
                    name="idcp" 
                    id="idcp" 
                    value="<c:out value="${idcp}"/>"/>
                <input 
                    <input 
                    class="form-control"
                    type="hidden" 
                    name="id" 
                    id="id" 
                    value="<c:out value="${dto.entidad.idSubCategoria}"/>"/>
                <input 
                    class="form-control"
                    type="hidden" 
                    name="idc" 
                    id="idc" 
                    value="<c:out value="${dto.entidad.idCategoria}"/>"/>

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
                            placeholder="Nombre de categoria"
                            value="<c:out value="${dto.entidad.nombreSubCategoria}"/>"/>
                    </div>
                </div>

                <div class="form-group row">
                    <div class="col-sm-2">
                        <label class="col-sm-2 col-form-label">Descripci&oacute;n:</label>
                    </div>

                    <div class="col-sm-10">
                        <input 
                            type="text" 
                            name="txtdescripcion" 
                            id="txtdescripcion"
                            required="requiered" 
                            placeholder="Descripci&oacute;n de categoria"
                            class="form-control"
                            value="<c:out value="${dto.entidad.descripcionSubCategoria}"/>"/>
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
