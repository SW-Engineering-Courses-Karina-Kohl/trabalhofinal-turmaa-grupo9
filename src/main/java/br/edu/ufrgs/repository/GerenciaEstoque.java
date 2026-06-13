package br.edu.ufrgs.repository;

import br.edu.ufrgs.model.Categoria;
import br.edu.ufrgs.model.Produto;
import java.util.ArrayList;
import java.util.List;

/**
 * Orquestra o gerenciamento de estoque e a aplicação de regras de negócio.
 * 
 * Responsabilidades:
 * - Carregar categorias e produtos de arquivos CSV
 * - Aplicar lógica de reposição (RF02)
 * - Calcular sugestões de compra
 * - Definir prioridades
 * 
 */
public class GerenciaEstoque {

    /** Lista de produtos carregada do arquivo CSV de inventário. */
    private List<Produto> listaProdutos;
    
    /** Lista de categorias carregada do arquivo CSV de configuração. */
    private List<Categoria> listaCategorias;

    /** Inicializa as listas vazias. */
    public GerenciaEstoque() {
        this.listaProdutos = new ArrayList<>();
        this.listaCategorias = new ArrayList<>();
    }

    // ===== Métodos Públicos =====

    /**
     * Carrega a lista de categorias a partir de um arquivo CSV.
     * Cada categoria define: estoque de segurança, lote padrão de compra, ponto de pedido.
     */
    public void carregarCategorias(String caminhoArquivo) {
        LeitorCSV leitor = new LeitorCSV(caminhoArquivo);
        this.listaCategorias = leitor.lerCategorias();
    }

    /**
     * Carrega a lista de produtos a partir de um arquivo CSV.
     * Cada produto é associado à sua categoria já carregada.
     */
    public void carregarProdutos(String caminhoArquivo) {
        LeitorCSV leitor = new LeitorCSV(caminhoArquivo);
        this.listaProdutos = leitor.lerProdutos(this.listaCategorias);
    }

    /**
     * Aplica as regras de reposição a todos os produtos carregados.
     * 
     * Para cada produto:
     * - Verifica se a quantidade atual está abaixo ou igual ao ponto de pedido
     * - Se sim: calcula sugestão de compra e define prioridade
     * - Se não: mantém sugestão 0 e prioridade Normal
     * 
     * @return Lista de produtos processados e com reposição calculada
     */
    public List<Produto> verificarNecessidadeReposicao() {
        for (Produto produto : listaProdutos) {
            // RF02 - Motor de Reposição: Compara quantidade_atual com o ponto_de_pedido
            if (precisaReposicao(produto)) {
                // Calcula sugestão de compra e prioridade.
                int sugestao = calcularSugestaoCompra(produto);
                String prioridade = definirPrioridade(produto);

                // Integrado com a classe Produto (RF04)
                produto.setSugestaoCompra(sugestao);
                produto.setPrioridade(prioridade);
            } else {
                // Sem necessidade de reposição: sugestão 0, prioridade normal.
                produto.setSugestaoCompra(0);
                produto.setPrioridade("Normal");
            }
        }
        return listaProdutos;
    }

    // --- Métodos Privados (-) ---

    private int calcularSugestaoCompra(Produto produto) {
        // Usa o Lote Padrão de Compra definido na configuração da categoria do produto
        if (produto.getCategoria() != null) {
            return produto.getCategoria().getLotePadraoCompra();
        }
        return 0;
    }

    /**
     * Define a prioridade de reposição.
     * Alta: se o produto está zerado
     * Média: se apenas precisa de pedido
     */
    private String definirPrioridade(Produto produto) {
        // Prioridade Alta se zerado, Média se apenas precisa de pedido
        if (estaZerado(produto)) {
            return "Alta";
        }
        return "Média";
    }

    /**
     * Verifica se a quantidade atual do produto é zero.
     */
    private boolean estaZerado(Produto produto) {
        return produto.getQtdAtual() == 0;
    }

    /**
     * Verifica se o produto precisa de reposição.
     * Retorna true quando quantidade_atual <= ponto_de_pedido da categoria.
     */
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