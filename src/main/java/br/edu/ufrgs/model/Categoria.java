package br.edu.ufrgs.model;

/**
 * Modelo que representa a configuração de estoque de uma categoria de produto.
 * 
 * Armazena os parâmetros de estoque definidos pela empresa para cada categoria,
 * usados pelo motor de reposição para calcular quando e quanto comprar.
 */
public class Categoria {

    /** Nome da categoria (ex: Ferragens, Metais, etc). */
    private String nome;
    
    /** Estoque mínimo de segurança reservado. Pode ser usado em cálculos futuros. */
    private int estoqueSeguranca;
    
    /** Quantidade padrão a comprar quando dispara uma reposição. */
    private int lotePadraoCompra;
    
    /** Quantidade limiar: quando atingida, dispara a necessidade de reposição. */
    private int pontoPedido;

    /**
     * Cria uma categoria com parâmetros de estoque.
     * 
     * @param nome Nome da categoria
     * @param estoqueSeguranca Estoque de segurança
     * @param lotePadraoCompra Lote padrão de compra
     * @param pontoPedido Ponto de pedido que dispara reposição
     */
    public Categoria(String nome,
                     int estoqueSeguranca,
                     int lotePadraoCompra,
                     int pontoPedido) {

        this.nome = nome;
        this.estoqueSeguranca = estoqueSeguranca;
        this.lotePadraoCompra = lotePadraoCompra;
        this.pontoPedido = pontoPedido;
    }

    // ===== Getters (Valores Imutáveis) =====

    /** @return Nome da categoria */
    public String getNome() {
        return nome;
    }

    /** @return Estoque de segurança */
    public int getEstoqueSeguranca() {
        return estoqueSeguranca;
    }

    /** @return Lote padrão de compra quando reposicionar */
    public int getLotePadraoCompra() {
        return lotePadraoCompra;
    }

    /** @return Ponto de pedido que dispara reposição */
    public int getPontoPedido() {
        return pontoPedido;
    }
}
