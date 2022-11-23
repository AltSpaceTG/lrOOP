<%@ page import="com.company.functions.ArrayTabulatedFunction" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.company.util.TabulatedFunctionDoc" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 16.11.2022
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Output</title>
</head>
<body >
    <table  border=3 align=”center” bgcolor=”orange”>
        <%
            PrintWriter pout = new PrintWriter(response.getWriter());
            TabulatedFunctionDoc func = (TabulatedFunctionDoc) request.getSession().getAttribute("func");
            for(int i = 0;i < func.getPointsCount(); ++i) {
        %>
            <tr>
            <td> <%=func.getPoint(i).getX() %></td>
            <td> <%=func.getPoint(i).getY() %></td>
            </tr>
        <%
            }
        %>
    </table>

    <form method="post">
    <label>X:
        <input type="text" name="left"><br />
    </label>
    <label>Y:
        <input type="text" name="right"><br />
    </label>
    <button type="submit" name="add" value="add">Add</button>
    </form>

    <form method="post" >
    <label>Index:
        <input type="number" name="index"><br />
    </label>
    <button type="submit" name="delete" value="delete">Delete point</button>
    </form>

    <form method="post">
    <label>Save Document As:
        <input type="text" name="fileName"><br />
    </label>
    <button type="submit" name="save" value="save">Save</button>
    </form>

    <form method="post">
    <label>Open Document:
        <input type="text" name="fileName"><br />
    </label>
    <button type="submit" name="open" value="open">Open</button>
    </form>

</body>
</html>
