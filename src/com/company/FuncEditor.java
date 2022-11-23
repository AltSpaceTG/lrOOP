package com.company;

import com.company.functions.ArrayTabulatedFunction;
import com.company.functions.FunctionPoint;
import com.company.functions.InappropriateFunctionPointException;
import com.company.util.TabulatedFunctionDoc;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "FuncEditor", value = "/FuncEditor")
public class FuncEditor extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        TabulatedFunctionDoc func = (TabulatedFunctionDoc) session.getAttribute("func");

        if(func == null) {
            response.sendRedirect("http://localhost:8080/project/FuncGenerator");
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/output.jsp");
            requestDispatcher.forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getParameter("add")!= null);
        System.out.println(request.getParameter("delete")!= null);
        System.out.println(request.getParameter("save")!= null);
        System.out.println(request.getParameter("open")!= null);

        HttpSession session = request.getSession();
        TabulatedFunctionDoc func = (TabulatedFunctionDoc) session.getAttribute("func");

        if(request.getParameter("add")!=null) {
            double left = Double.parseDouble(request.getParameter("left"));
            double right = Double.parseDouble(request.getParameter("right"));

            try {
                func.addPoint(new FunctionPoint(left,right));
            } catch (InappropriateFunctionPointException e) {
                throw new RuntimeException(e);
            }
        } else if (request.getParameter("delete")!= null) {
            int index = Integer.parseInt(request.getParameter("index"));

            func.deletePoint(index);
        } else if (request.getParameter("save")!= null) {
            String fileName = request.getParameter("fileName");
            System.out.println(fileName);
            if(fileName==null) {
                func.saveFunction();
            } else func.saveFunctionAs(fileName);
        } else if (request.getParameter("open")!=null) {
            String fileName = request.getParameter("fileName");
            func.loadFunction();
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/output.jsp");
        requestDispatcher.forward(request,response);
    }
}
