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

/**
 * Servlet responsável por receber upload de arquivo CSV de inventário,
 * processar o estoque e encaminhar os resultados para a página JSP.
 * 
 * Fluxo de negócio:
 * 1. Recebe o arquivo CSV enviado pelo formulário multipart
 * 2. Persiste o arquivo no disco (pasta /uploads)
 * 3. Carrega as categorias do arquivo de configuração
 * 4. Carrega os produtos do arquivo enviado
 * 5. Aplica regras de reposição aos produtos
 * 6. Gera o CSV de saída com sugestões de compra
 * 7. Encaminha os dados para a visão (JSP)
 */
@WebServlet("/inventario")
@MultipartConfig
public class ServletInventario extends HttpServlet {

    /** Caminho relativo do arquivo CSV de saída gerado após o processamento. */
    private static final String CAMINHO_SAIDA = "/saida/saida_reposicao.csv";

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Garante que parâmetros com acentuação sejam lidos corretamente.
        request.setCharacterEncoding("UTF-8");

        try {
            // Recebe o arquivo enviado pelo campo 'arquivoInventario' do formulário multipart.
            Part arquivoInventario = request.getPart("arquivoInventario");

            // Validação: garante que um arquivo foi selecionado e não está vazio.
            if (arquivoInventario == null || arquivoInventario.getSize() == 0) {
                throw new IllegalArgumentException("Selecione um arquivo CSV de inventario.");
            }

            // Obtém o caminho físico da pasta /uploads dentro da aplicação web.
            String pastaUploads = getServletContext().getRealPath("/uploads");

            // Cria um objeto File para verificar se a pasta existe.
            File pasta = new File(pastaUploads);

            // Se a pasta não existir, cria-a (inclusve diretórios pais se necessário).
            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            Path diretorioSaida = Paths.get(CAMINHO_SAIDA).getParent();
            if (diretorioSaida != null) {
                Files.createDirectories(diretorioSaida);
            }

            // Extrai o nome original do arquivo enviado.
            String nomeArquivo = arquivoInventario.getSubmittedFileName();

            // Monta o caminho completo onde o arquivo será persistido.
            String caminhoCompleto = pastaUploads + File.separator + nomeArquivo;

            // Persiste o arquivo no disco do servidor.
            arquivoInventario.write(caminhoCompleto);

            // Instancia o gerenciador de estoque que contém a lógica de negócio.
            GerenciaEstoque gerencia = new GerenciaEstoque();

            String caminhoCategorias = getServletContext().getRealPath("config_estoque.csv");

            // Carrega as categorias (estoque de segurança, lote padrão, ponto de pedido).
            gerencia.carregarCategorias(caminhoCategorias);

            // Carrega os produtos do arquivo enviado e associa cada um à sua categoria.
            gerencia.carregarProdutos(caminhoCompleto);

            List<Produto> listaProdutos = gerencia.verificarNecessidadeReposicao();

            // Gera o arquivo final com as sugestões de compra
            GeradorSaidaCSV gerador = new GeradorSaidaCSV(CAMINHO_SAIDA);
            gerador.exportar(listaProdutos);
            
            request.setAttribute("produtos", gerencia.getListaProdutos());

            for (Produto produto : gerencia.getListaProdutos()) {
                System.out.println(produto.getDescricao());
            }
        } catch (Exception e) {
            // Captura qualquer erro ocorrido durante o processamento e o repassa para a visão.
            request.setAttribute("erro", e.getMessage());
        }

        // Encaminha o request para a página index.jsp para exibir o resultado ou erro.
        request.getRequestDispatcher("index.jsp")
                .forward(request, response);
    }
}