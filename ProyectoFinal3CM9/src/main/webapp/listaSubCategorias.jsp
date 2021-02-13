<%-- 
    Document   : listaSubCategorias
    Created on : 21 ene 2021, 17:06:31
    Author     : DANIEL
--%>

<%@page import="com.ipn.mx.utilerias.LoginManagerVF"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp?de=listaDeSubCategorias.jsp" %>
<!DOCTYPE html>
<html>
    <%LoginManagerVF a = new LoginManagerVF();
      if(!a.isAdm(request, response)){
        response.sendRedirect("index.jsp");
        }
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listado de SubCategor&iacute;as</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" >
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
                            <a class="nav-link" href="CategoriaServlet?accion=listaDeCategorias">Categor&iacute;as<span class="sr-only">(current)</span></a>
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
            <div class="table-responsive">
                <h1 class='mt-2 mb-3'>Lista de SubCategor&iacute;as</h1>
                <a class= 'btn btn-outline-info float-right mb-4' href= "subCategoriaServlet?accion=nuevo&idc=<c:out value="${idc}"/>">Nuevo</a>
                <a class= 'btn btn-outline-info float-left mb-4' href= 'subCategoriaServlet?accion=graficar'>Graficar</a>
                <table class="table table-dark table-striped table-hover">
                    <thead class="thead-dark">
                        <tr>
                            <th>Clave</th>
                            <th>Nombre</th>
                            <th>Descripción</th>
                            <th>Eliminar</th>
                            <th>Actualizar</th>
                            <th>Productos</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="subcategorias" 
                                   items="${listaDeSubCategorias}">
                            <tr>
                                <td class="text-center">
                                    <a class="btn btn-primary btn-xs" href="subCategoriaServlet?accion=ver&id=<c:out value="${subcategorias.entidad.idSubCategoria}"/>">
                                        <c:out value="${subcategorias.entidad.idSubCategoria}"/>
                                    </a>
                                </td>
                                <td>
                                    <c:out value="${subcategorias.entidad.nombreSubCategoria}"/>
                                </td>
                                <td>
                                    <c:out value="${subcategorias.entidad.descripcionSubCategoria}"/>
                                </td>
                                <td>
                                    <a class="btn btn-outline-danger btn-xs" href="subCategoriaServlet?accion=eliminar&id=<c:out value="${subcategorias.entidad.idSubCategoria}"/>&idc=<c:out value="${subcategorias.entidad.idCategoria}"/>">
                                        Eliminar
                                        <i class='bx bx-trash'></i> 
                                    </a>
                                </td>
                                <td>
                                    <a class="btn btn-outline-warning btn-xs"
                                       href="subCategoriaServlet?accion=actualizar&id=<c:out value="${subcategorias.entidad.idSubCategoria}"/>">
                                        Actualizar
                                        <i class='bx bx-pencil' ></i>
                                    </a>
                                </td>
                                <td>
                                    <a class="btn btn-outline-info btn-xs"
                                       href="ProductoServlet?accion=listaDeProductos&idc=<c:out value="${subcategorias.entidad.idSubCategoria}"/>">
                                        Productos
                                        <i class='bx bx-pencil' ></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" ></script>
    </body>
</html>