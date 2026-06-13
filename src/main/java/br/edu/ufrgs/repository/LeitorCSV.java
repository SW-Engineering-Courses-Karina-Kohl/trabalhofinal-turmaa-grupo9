package br.edu.ufrgs.repository;

import br.edu.ufrgs.model.Categoria;
import br.edu.ufrgs.model.Produto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Leitor de arquivos CSV que converte cada linha em objetos de domínio.
 * 
 * Responsabilidade única: parsear CSV e transformar em Categorias ou Produtos.
 * Mantém a separação entre camada de I/O e lógica de negócio.
 */
public class LeitorCSV implements LeitorArquivo {

    /** Caminho do arquivo CSV a ser lido. */
    private String nomeArquivo;

    public LeitorCSV(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    @Override
    public List<?> ler(String caminho) {
        this.nomeArquivo = caminho;
        return new ArrayList<>();
    }
    
    /**
     * Lê o arquivo CSV de configuração de categorias.
     * 
     * Formato esperado do CSV:
     * nome,estoque_seguranca,lote_padrao_compra,ponto_pedido
     */
    public List<Categoria> lerCategorias() {
        List<Categoria> categorias = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            br.readLine(); // Descarta o cabeçalho

            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println("Lendo linha: " + linha);
                
                // Divide a linha por vírgula e cria objetos Categoria.
                String[] dados = linha.split(",");

                Categoria categoria = new Categoria(
                        dados[0].trim(),                    // nome
                        Integer.parseInt(dados[1].trim()), // estoque de segurança
                        Integer.parseInt(dados[2].trim()), // lote padrão de compra
                        Integer.parseInt(dados[3].trim())  // ponto de pedido
                );

                categorias.add(categoria);
            }
        } catch (Exception e) {
            // Em produção, seria melhor usar logging estruturado em vez de printStackTrace().
            e.printStackTrace();
        }

        return categorias;
    }

    /**
     * Lê o arquivo CSV de inventário e associa cada produto à sua categoria.
     * 
     * Formato esperado do CSV:
     * codigo,descricao,categoria,quantidade_atual
     * 
     * Se a categoria não for encontrada, o produto é ignorado (skip).
     */
    public List<Produto> lerProdutos(List<Categoria> categorias) {
        List<Produto> produtos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            br.readLine(); // Descarta o cabeçalho

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                String nomeCategoria = dados[2].trim();

                System.out.println("Procurando categoria: " + nomeCategoria);
                
                // Busca a categoria na lista já carregada.
                Categoria categoriaEncontrada = null;
                for (Categoria categoria : categorias) {
                    if (categoria.getNome().equalsIgnoreCase(nomeCategoria)) {
                        categoriaEncontrada = categoria;
                        break;
                    }
                }

                // Se a categoria não for encontrada, ignora este produto (skip).
                if (categoriaEncontrada == null) {
                    System.out.println("Categoria não encontrada: " + nomeCategoria);
                    continue;
                }

                // Cria o produto associado à categoria encontrada.
                Produto produto = new Produto(
                        dados[0].trim(),                    // código
                        dados[1].trim(),                    // descrição
                        Integer.parseInt(dados[3].trim()),  // quantidade atual
                        categoriaEncontrada                  // categoria (referência)
                );

                produtos.add(produto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtos;
    }
}


