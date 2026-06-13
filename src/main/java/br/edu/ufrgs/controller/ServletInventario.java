package br.edu.ufrgs.controller;

import br.edu.ufrgs.model.Produto;
import br.edu.ufrgs.repository.GeradorSaidaCSV;
import br.edu.ufrgs.repository.GerenciaEstoque;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig; //n
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part; //n

import java.io.File; //n
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/inventario")
@MultipartConfig
public class ServletInventario extends HttpServlet {

    private static final String CAMINHO_SAIDA = "/saida/saida_reposicao.csv";

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            Part arquivoInventario = request.getPart("arquivoInventario");

            if (arquivoInventario == null || arquivoInventario.getSize() == 0) {
                throw new IllegalArgumentException("Selecione um arquivo CSV de inventario.");
            }

            // Pasta onde o arquivo será salvo
            String pastaUploads = getServletContext().getRealPath("/uploads");

            File pasta = new File(pastaUploads);

            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            Path diretorioSaida = Paths.get(CAMINHO_SAIDA).getParent();
            if (diretorioSaida != null) {
                Files.createDirectories(diretorioSaida);
            }

            // Nome original do arquivo
            String nomeArquivo = arquivoInventario.getSubmittedFileName();

            // Caminho completo
            String caminhoCompleto = pastaUploads + File.separator + nomeArquivo;

            // Salva o arquivo
            arquivoInventario.write(caminhoCompleto);

            GerenciaEstoque gerencia = new GerenciaEstoque();

            String caminhoCategorias = getServletContext().getRealPath("config_estoque.csv");

            gerencia.carregarCategorias(caminhoCategorias);

            gerencia.carregarProdutos(caminhoCompleto);

            List<Produto> listaProdutos = gerencia.verificarNecessidadeReposicao(CAMINHO_SAIDA);

            // Gera o arquivo final com as sugestões de compra
            GeradorSaidaCSV gerador = new GeradorSaidaCSV(CAMINHO_SAIDA);
            gerador.exportar(listaProdutos);
            
            request.setAttribute("produtos", gerencia.getListaProdutos());

            for (Produto produto : gerencia.getListaProdutos()) {
                System.out.println(produto.getDescricao());
            }
        } catch (Exception e) {
            request.setAttribute("erro", e.getMessage());
        }

        request.getRequestDispatcher("index.jsp")
                .forward(request, response);
    }
}