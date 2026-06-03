package br.edu.ufrgs.test;

import br.edu.ufrgs.model.Categoria;
import br.edu.ufrgs.model.Produto;
import br.edu.ufrgs.repository.GeradorSaidaCSV;
import br.edu.ufrgs.repository.LeitorCSV;

import java.util.List;

public class MainTesteCSV {

    public static void main(String[] args) {

        System.out.println("=== TESTE DE LEITURA CSV ===");

        LeitorCSV leitorCategorias =
                new LeitorCSV("src/main/java/config_estoque.csv");
                
        List<Categoria> categorias =
                leitorCategorias.lerCategorias();

        System.out.println("Categorias carregadas: "
                + categorias.size());

        LeitorCSV leitorProdutos =
                new LeitorCSV("src/main/java/inventario.csv");

        List<Produto> produtos =
                leitorProdutos.lerProdutos(categorias);

        System.out.println("Produtos carregados: "
                + produtos.size());

        System.out.println("\n=== PRODUTOS ===");

        for (Produto produto : produtos) {

            System.out.println(
                    produto.getCodigo()
                    + " | "
                    + produto.getDescricao()
                    + " | "
                    + produto.getCategoria().getNome()
                    + " | "
                    + produto.getQtdAtual()
            );
        }

        GeradorSaidaCSV gerador =
                new GeradorSaidaCSV("src/main/java/saida.csv");

        gerador.exportar(produtos);

        System.out.println("\nArquivo saida.csv gerado.");
    }
}
