package br.edu.ufrgs.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/inventario")
public class ServletInventario extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        GerenciaEstoque gerencia = new GerenciaEstoque();

        gerencia.carregarProdutos("dados/inventario.csv");
        gerencia.carregarCategorias("dados/config_estoque.csv");

        var resultado = gerencia.verificarNecessidadeReposicao();

        request.setAttribute("resultado", resultado);

        request.getRequestDispatcher("index.jsp")
               .forward(request, response);
    }
}