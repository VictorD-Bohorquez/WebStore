<%-- 
    Document   : listaProductosClientes
    Created on : 22 ene 2021, 3:40:33
    Author     : isaac
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
        <title>Productos</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" />
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
            <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                <li data-target="#carouselExampleIndicators" data-slide-to= "<c:out value="0"/>" class="active"></li>
                <ol class="carousel-indicators">
                    <c:forEach var="productos" items="${listaDeProductosClientes}">
                        <li data-target="#carouselExampleIndicators" data-slide-to= "<c:out value="${productos.entidad.idProducto}"/>"></li>
                        </c:forEach>
                </ol>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img class="d-block w-100" src="imagenes/BIEN.png" alt="First slide">
                    </div>
                    <c:forEach var="productos" items="${listaDeProductosClientes}">
                        <div class="carousel-item">
                            <img class="d-block w-100" src="${productos.entidad.imagen}" alt="First slide">
                        </div>
                    </c:forEach>
                </div>
                <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Anterior</span>
                </a>
                <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Siguiente</span>
                </a>
            </div>
        </div>

        <div class="container">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead class="thead-dark">
                        <tr>
                            <th class="text-center">Nombre</th>
                            <th class="text-center">Descripción</th>
                            <th class="text-center">Existencia</th>
                            <th class="text-center">Precio</th>
                            <th class="text-center">Imagen</th>
                            <th class="text-center">Comprar</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="productos" 
                                   items="${listaDeProductos}">
                            <tr>
                                <td class="text-center">
                                    <c:out value="${productos.entidad.nombreProducto}"/>
                                </td>
                                <td class="text-center">
                                    <c:out value="${productos.entidad.descripcionProducto}"/>
                                </td>
                                <td class="text-center">
                                    <c:out value="${productos.entidad.existencia}"/>
                                </td>
                                <td class="text-center">
                                    <c:out value="${productos.entidad.precio}"/>
                                </td>
                                <td class="text-center">
                                    <img class="img-fluid" src="${productos.entidad.imagen}" width="80" height="100">
                                </td>
                                <td class="align-middle text-center">
                                    <div class="btn btn-warning text-light align-middle text-center">
                                        <a  style="text-decoration: none"
                                            type="button" 
                                            class="text-light"
                                            href="CarritoServlet?accion=agregarProducto&id=<c:out value="${productos.entidad.idProducto}"/>&idc=<c:out value="${productos.entidad.idSubCategoria}"/>">
                                            <i class="fa fa-cart-plus text-dark mr-1" aria-hidden="true"></i>
                                            Agregar
                                        </a>
                                    </div >
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div> 
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" ></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    </body>
</html>
