package com.company;

import com.company.functions.ArrayTabulatedFunction;
import com.company.functions.TabulatedFunction;
import com.company.util.TabulatedFunctionDoc;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "FuncGenerator", value = "/FuncGenerator")
public class FuncGenerator extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/generator.jsp");
        requestDispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double left = Double.parseDouble(request.getParameter("left"));
        double right = Double.parseDouble(request.getParameter("right"));
        Integer count = Integer.parseInt(request.getParameter("count"));
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(left,right,count);
        TabulatedFunctionDoc tb = new TabulatedFunctionDoc(func);
        request.getSession().setAttribute("func", tb);
        response.sendRedirect("http://localhost:8080/project/FuncEditor");
    }
}
