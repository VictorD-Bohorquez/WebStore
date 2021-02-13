<%@page import="com.ipn.mx.modelo.dao.subCategoriaDAO"%>
<%@page import="java.util.List"%>
<select 
    id="txtsub"
    name="txtsub" 
    class="form-control"
    required="requiered" 
    >
    <%  subCategoriaDAO a = new subCategoriaDAO();
        int x = Integer.parseInt(request.getParameter("txtcat"));
        List cat = a.readNames(x);
        List ids = a.readIDs(x);
    %>
    <option value="">Elige Subcategoria</option>
    <%for (int i = 0; i < cat.size(); i++) {%>
    <option value="<%=ids.get(i)%>"><%=cat.get(i)%></option>
    <%}%>
</select>  
