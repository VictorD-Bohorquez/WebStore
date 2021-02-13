<%-- 
    Document   : carrito
    Created on : 25 ene 2021, 4:13:48
    Author     : DANIEL
--%>

<%@page import="com.ipn.mx.utilerias.LoginManagerVF"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp?de=listaDeProductosClientes.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%LoginManagerVF a = new LoginManagerVF();
      if(!a.isLogged(request, response)){
        response.sendRedirect("index.jsp");
        }
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Carrito</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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

            <c:choose >
                <c:when test="${carritoProductos.size() >= 1}">
                    <div class="table-responsive">
                        <table class="table table-dark table-striped table-hover">
                            <thead class="thead-dark">
                                <tr>
                                    <th>Nombre</th>
                                    <th>Descripción</th>
                                    <th>Precio</th>
                                    <th>Cantidad</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="cantidad" value="${cantidad}"></c:set>

                                <c:forEach var="productos" 
                                           items="${carritoProductos}">
                                    <tr>
                                        <td class="text-center">
                                            <c:out value="${productos.entidad.nombreProducto}"/>
                                        </td>
                                        <td class="text-center">
                                            <c:out value="${productos.entidad.descripcionProducto}"/>
                                        </td>
                                        <td>
                                            <c:out value="${productos.entidad.precio}"/>
                                        </td>
                                        <td>
                                            <c:out value="${cantidad.get(carritoProductos.indexOf(productos))}"/>
                                        </td>
                                        <td>
                                            <div class="btn-group btn-group-sm" role="group">
                                                <a class="btn  btn-danger btn-xs" href="CarritoServlet?accion=actualizar&id=<c:out value="${carritoProductos.indexOf(productos)}"/>&cant=1">
                                                    <i class="fa fa-chevron-up" aria-hidden="true"></i>
                                                </a>
                                                <a class="btn  btn-danger btn-xs" href="CarritoServlet?accion=actualizar&id=<c:out value="${carritoProductos.indexOf(productos)}"/>&cant=-1">
                                                    <i class="fa fa-chevron-down" aria-hidden="true"></i>
                                                </a>
                                            </div>

                                        </td>
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td class="text-right" colspan="4">Total: </td>
                                    <td class="d-flex justify-content-end mr-3" >
                                        <c:out value="${total}"/>
                                    </td>

                                </tr>   

                            </tbody>
                        </table>
                    </div>

                    <div>
                        <a class="btn  btn-success btn-xs "
                                href="CarritoServlet?accion=comprar&cant=<c:out value="${total}"/>">
                            Comprar
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-warning" role="alert">
                        Aún no cuentas con articulos en tu carrito de compra
                    </div>
                    <div class="d-flex justify-content-end">
                        <a class="btn  btn-ligth btn-xs " href="inicioCliente.jsp">
                            Volver a la tienda
                            <i class="fa fa-shopping-cart" aria-hidden="true"></i>
                        </a>
                    </div>

                </c:otherwise>
            </c:choose>



        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" ></script>
    </body>
</html>
