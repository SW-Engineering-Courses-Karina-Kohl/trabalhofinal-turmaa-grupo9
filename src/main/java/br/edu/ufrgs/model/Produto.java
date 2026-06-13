package br.edu.ufrgs.model;

/**
 * Representa um produto no inventário com estado de estoque.
 * 
 * Atributos:
 * - Identificação: código e descrição (imutáveis)
 * - Estado: quantidade atual (mutável)
 * - Referência: categoria que define parâmetros de estoque
 * - Cálculos: sugestão de compra e prioridade (definidos pelo motor de reposição)
 */
public class Produto {

    /** Código único e imutável do produto. */
    private final String codigo;
    
    /** Descrição do produto. Imutável. */
    private final String descricao;
    
    /** Quantidade atual em estoque. Pode ser ajustada. */
    private int qtdAtual;
    
    /** Referência à categoria que define parâmetros de estoque. Imutável. */
    private final Categoria categoria;
    
    /** Quantidade sugerida para compra (calculada pela regra de reposição). */
    private int sugestaoCompra;
    
    /** Prioridade de reposição: Alta, Média ou Normal. */
    private String prioridade;

    /**
     * Cria um produto com validação de dados obrigatórios.
     * Segue o padrão de Falha Rápida (fail-fast) lançando exceção se dados inválidos.
     * 
     * @param codigo Código único não vazio
     * @param descricao Descrição não vazia
     * @param qtdAtual Quantidade maior ou igual a zero
     * @param categoria Categoria obrigatória (não nula)
     * @throws IllegalArgumentException Se algum parâmetro for inválido
     */
    public Produto(String codigo, String descricao, int qtdAtual, Categoria categoria){
        // Validação: código obrigatório e não vazio
        if(codigo == null || codigo.isEmpty()){
            throw new IllegalArgumentException("Código inválido.");
        }
        // Validação: descrição obrigatória e não vazia
        if(descricao == null || descricao.isEmpty()){
            throw new IllegalArgumentException("Descrição inválida.");
        }
        // Validação: quantidade não pode ser negativa
        if(qtdAtual < 0){
            throw new IllegalArgumentException("Quantidade inválida.");
        }
        // Validação: categoria obrigatória
        if(categoria == null){
            throw new IllegalArgumentException("Categoria não inserida.");
        }
        
        // Atribuição (tudo validado)
        this.codigo = codigo;
        this.descricao = descricao;
        this.qtdAtual = qtdAtual;
        this.categoria = categoria;
        this.sugestaoCompra = 0;
    }

    // ===== Getters (Valores Imutáveis) =====

    /** @return Código único do produto */
    public String getCodigo(){
        return codigo;
    }

    /** @return Descrição do produto */
    public String getDescricao(){
        return descricao;
    }

    /** @return Quantidade atual em estoque */
    public int getQtdAtual(){
        return qtdAtual;
    }

    /** @return Categoria do produto */
    public Categoria getCategoria(){
        return categoria;
    }

    // ===== Setters com Validação =====

    /**
     * Ajusta a quantidade atual do produto.
     * Garante que a quantidade não seja negativa.
     * 
     * @param qtdAtual Quantidade a definir
     * @throws IllegalArgumentException Se qtdAtual for negativo
     */
    public void setQtdAtual(int qtdAtual){
        if(qtdAtual < 0){
            throw new IllegalArgumentException("Quantidade inválida.");
        }
        this.qtdAtual = qtdAtual;
    }

    /** @return Quantidade sugerida para compra */
    public int getSugestaoCompra(){
        return sugestaoCompra;
    }

    /**
     * Define a sugestão de compra calculada pelo motor de reposição.
     * Garante que não seja negativa.
     * 
     * @param sugestaoCompra Quantidade a sugerir
     * @throws IllegalArgumentException Se sugestaoCompra for negativo
     */
    public void setSugestaoCompra(int sugestaoCompra){
        if (sugestaoCompra < 0) {
            throw new IllegalArgumentException("Inválido: valor negativo.");
        }
        this.sugestaoCompra = sugestaoCompra;
    }

    /** @return Prioridade de reposição */
    public String getPrioridade(){
        return prioridade;
    }

    /**
     * Define a prioridade de reposição.
     * Valores esperados: Alta, Média, Normal.
     * 
     * @param prioridade Prioridade a definir
     * @throws IllegalArgumentException Se prioridade for nula ou vazia
     */
    public void setPrioridade(String prioridade){
        if(prioridade == null || prioridade.isEmpty()){
            throw new IllegalArgumentException("Prioridade inválida.");
        }
        this.prioridade = prioridade;
    }
}