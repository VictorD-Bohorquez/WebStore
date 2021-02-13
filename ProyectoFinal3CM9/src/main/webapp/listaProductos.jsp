<%-- 
    Document   : listaProductos
    Created on : 13 dic 2020, 1:48:25
    Author     : DANIEL
--%>

<%@page import="com.ipn.mx.utilerias.LoginManagerVF"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp?de=listaDeProductos.jsp" %>
<!DOCTYPE html>
<html>
    <%LoginManagerVF a = new LoginManagerVF();
      if(!a.isAdm(request, response)){
        response.sendRedirect("index.jsp");
        }
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listado de Productos</title>
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
            <h1 class='mt-2 mb-3'>Lista de Productos</h1>
            <a class= 'btn btn-outline-info float-right mb-4' href= "ProductoServlet?accion=nuevo&idc=<c:out value="${idc}"/>">Nuevo</a>
            <a class= 'btn btn-outline-info float-left mb-4' href= "ProductoServlet?accion=graficar&idc=<c:out value="${idc}"/>">Graficar</a>
            <table class="table table-dark table-striped table-hover">
                <thead class="thead-dark">
                    <tr>
                        <th>Clave</th>
                        <th>Imagen</th>
                        <th>Nombre</th>
                        <th>Descripción</th>
                        <th>Precio</th>
                        <th>Existencia</th>
                        <th>Eliminar</th>
                        <th>Actualizar</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="productos" 
                               items="${listaDeProductos}">
                        <tr>
                            <td class="text-center">
                                <a class="btn btn-primary btn-xs" href="ProductoServlet?accion=ver&id=<c:out value="${productos.entidad.idProducto}"/>">
                                    <c:out value="${productos.entidad.idProducto}"/>
                                </a>
                            </td>
                            <td>         
                                <img src="${productos.entidad.imagen}" width="50" height="70">
                            </td>
                            <td>
                                <c:out value="${productos.entidad.nombreProducto}"/>
                            </td>
                            <td>
                                <c:out value="${productos.entidad.descripcionProducto}"/>
                            </td>
                            <td>
                                <c:out value="${productos.entidad.precio}"/>
                            </td>
                            <td>
                                <c:out value="${productos.entidad.existencia}"/>
                            </td>
                            <td>
                                <a class="btn btn-outline-danger btn-xs" href="ProductoServlet?accion=eliminar&id=<c:out value="${productos.entidad.idProducto}"/>&idc=<c:out value="${productos.entidad.idSubCategoria}"/>">
                                    Eliminar
                                    <i class='bx bx-trash'></i> 
                                </a>
                            </td>
                            <td>
                                <a class="btn btn-outline-warning btn-xs"
                                   href="ProductoServlet?accion=actualizar&id=<c:out value="${productos.entidad.idProducto}"/>&idc=<c:out value="${productos.entidad.idSubCategoria}"/>">
                                    Actualizar
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

