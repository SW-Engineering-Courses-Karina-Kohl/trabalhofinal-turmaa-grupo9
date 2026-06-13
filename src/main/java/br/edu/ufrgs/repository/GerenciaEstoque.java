package br.edu.ufrgs.repository;

import br.edu.ufrgs.model.Categoria;
import br.edu.ufrgs.model.Produto;
import java.util.ArrayList;
import java.util.List;

public class GerenciaEstoque {

    // --- Atributos ---
    private List<Produto> listaProdutos;
    private List<Categoria> listaCategorias;

    // --- Construtor ---
    public GerenciaEstoque() {
        this.listaProdutos = new ArrayList<>();
        this.listaCategorias = new ArrayList<>();
    }

    // --- Métodos Públicos (+) ---

    public void carregarCategorias(String caminhoArquivo) {
        LeitorCSV leitor = new LeitorCSV(caminhoArquivo);
        this.listaCategorias = leitor.lerCategorias();
    }

    public void carregarProdutos(String caminhoArquivo) {
        LeitorCSV leitor = new LeitorCSV(caminhoArquivo);
        this.listaProdutos = leitor.lerProdutos(this.listaCategorias);
    }

    public void verificarNecessidadeReposicao() {
        for (Produto produto : listaProdutos) {
            if (precisaReposicao(produto)) {
                int sugestao = calcularSugestaoCompra(produto);
                String prioridade = definirPrioridade(produto);

                // Define os dados diretamente no objeto
                produto.setSugestaoCompra(sugestao);
                produto.setPrioridade(prioridade);
            } else {
                produto.setSugestaoCompra(0);
                produto.setPrioridade("Normal");
            }
        } // <-- O FOR ACABA AQUI

        // A LINHA ENTRA EXATAMENTE AQUI, NO FINALZINHO DO MÉTODO:
        GeradorSaidaCSV gerador = new GeradorSaidaCSV("saida.csv");
        gerador.exportar(listaProdutos);
    }

    // --- Métodos Privados (-) ---

    private int calcularSugestaoCompra(Produto produto) {
        // Usa o Lote Padrão de Compra definido na configuração da categoria do produto
        if (produto.getCategoria() != null) {
            return produto.getCategoria().getLotePadraoCompra();
        }
        return 0;
    }

    private String definirPrioridade(Produto produto) {
        // Prioridade Alta se zerado, Média se apenas precisa de pedido
        if (estaZerado(produto)) {
            return "Alta";
        }
        return "Média";
    }

    private Categoria buscarCategoriaPorNome(String nome) {
        for (Categoria cat : listaCategorias) {
            if (cat.getNome().equalsIgnoreCase(nome)) {
                return cat;
            }
        }
        return null;
    }

    private boolean estaZerado(Produto produto) {
        return produto.getQtdAtual() == 0;
    }

    private boolean precisaReposicao(Produto produto) {
        // Retorna true se a quantidade atual estiver abaixo ou igual ao Ponto de Pedido
        if (produto.getCategoria() != null) {
            return produto.getQtdAtual() <= produto.getCategoria().getPontoPedido();
        }
        return false;
    }

    // Método extra para auxiliar a equipe no acesso aos dados processados
    public List<Produto> getListaProdutos() {
        return listaProdutos;
    }
}
