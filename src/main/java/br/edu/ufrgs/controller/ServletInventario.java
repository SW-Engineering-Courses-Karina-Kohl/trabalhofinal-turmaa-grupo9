package br.edu.ufrgs.controller;

import br.edu.ufrgs.model.Categoria;
import br.edu.ufrgs.model.Produto;
import br.edu.ufrgs.repository.GeradorSaidaCSV;
import br.edu.ufrgs.repository.GerenciaEstoque;
import br.edu.ufrgs.repository.LeitorCSV;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig; //n
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part; //n

import java.io.File; //n
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;


@WebServlet("/inventario")
@MultipartConfig
public class ServletInventario extends HttpServlet {

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

            // -----
            // pasta de uploads (pode ser getRealPath ou um diretório absoluto)
            String pastaUploads = getServletContext().getRealPath("/uploads");
            File pasta = new File(pastaUploads);
            if (!pasta.exists()) pasta.mkdirs();

            // nome seguro do arquivo
            String nomeArquivo = Paths.get(arquivoInventario.getSubmittedFileName()).getFileName().toString();
            String caminhoCompleto = pastaUploads + File.separator + nomeArquivo;

            // salva no disco o arquivo enviado
            arquivoInventario.write(caminhoCompleto);

            LeitorCSV leitorCategorias =
            new LeitorCSV("src/main/java/config_estoque.csv");
            
            List<Categoria> categorias =
            leitorCategorias.lerCategorias();
            
            System.out.println("Categorias carregadas: "
            + categorias.size());
        
            LeitorCSV leitorProdutos =
            new LeitorCSV(caminhoCompleto);

            List<Produto> produtos =
            leitorProdutos.lerProdutos(categorias);

            GerenciaEstoque gerencia = new GerenciaEstoque();
            
            gerencia.carregarCategorias("src/main/java/config_estoque.csv");
            gerencia.carregarProdutos(caminhoCompleto);

            produtos = gerencia.verificarNecessidadeReposicao();

            
            // Gera o arquivo final com as sugestões de compra
            GeradorSaidaCSV gerador = new GeradorSaidaCSV("saida.csv");
            
            gerador.exportar(produtos);



        } catch (Exception e) {
            request.setAttribute("erro", e.getMessage());
        }

        request.getRequestDispatcher("index.jsp")
                .forward(request, response);
    }
}
