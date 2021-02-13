<%-- 
    Document   : listaSubCategoriasClientes
    Created on : 24 ene 2021, 18:52:23
    Author     : DANIEL
--%>

<%@page import="com.ipn.mx.utilerias.LoginManagerVF"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <%LoginManagerVF a = new LoginManagerVF();
      if(!a.isLogged(request, response)){
        response.sendRedirect("index.jsp");
        }
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listado de SubCategor&iacute;as</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" >
    </head>
    <body>
        <div class="container">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <a class="navbar-brand" href="inicioCliente.jsp">Inicio</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
                    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">

                        <li class="nav-item active">
                            <a class="nav-link" href="CategoriaServlet?accion=listaDeCategoriasUsuario">Categorias</a>
                        </li>
                        <li class="nav-item active">
                            <a class="nav-link" href="CarritoServlet?accion=verProductos">Carrito</a>
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
                <table class="table table-dark table-striped table-hover">
                    <thead class="thead-dark">
                        <tr>
                            <th>Nombre</th>
                            <th>Descripción</th>
                            <th>Productos</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="subcategorias" 
                                   items="${listaDeSubCategorias}">
                            <tr>
                                <td>
                                    <c:out value="${subcategorias.entidad.nombreSubCategoria}"/>
                                </td>
                                <td>
                                    <c:out value="${subcategorias.entidad.descripcionSubCategoria}"/>
                                </td>
                                <td>
                                    <a class="btn btn-success btn-xs"
                                       href="ProductoServlet?accion=listaDeProductosUsuarios&idc=<c:out value="${subcategorias.entidad.idSubCategoria}"/>">
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
