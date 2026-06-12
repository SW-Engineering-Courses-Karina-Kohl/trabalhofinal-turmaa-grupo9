package br.edu.ufrgs.model;

// Classe Produto representa um produto do sistema de controle.
public class Produto {

    // Atributos de cada produto: código único, descrição, quantidade atual, categoria, sugestão de compra (pode ser 0) e prioridade.
    private final String codigo;
    private final String descricao;
    private int qtdAtual;
    private final Categoria categoria;
    private int sugestaoCompra;
    private String prioridade;

    // Construtor do produto: valida os dados de entrada e inicializa os atributos.
    public Produto(String codigo, String descricao, int qtdAtual, Categoria categoria){
        if(codigo == null || codigo.isEmpty()){
            throw new IllegalArgumentException("Código inválido.");
        }
        if(descricao == null || descricao.isEmpty()){
            throw new IllegalArgumentException("Descrição inválida.");
        }
        if(qtdAtual < 0){
            throw new IllegalArgumentException("Quantidade inválida.");
        }
        if(categoria == null){
            throw new IllegalArgumentException("Categoria não inserida.");
        }
        this.codigo = codigo;
        this.descricao = descricao;
        this.qtdAtual = qtdAtual;
        this.categoria = categoria;
        this.sugestaoCompra = 0;
    }

    public String getCodigo(){
        return codigo;
    }

    public String getDescricao(){
        return descricao;
    }

    public int getQtdAtual(){
        return qtdAtual;
    }

    public Categoria getCategoria(){
        return categoria;
    }

    // Valida quantidades válidas para quantidade, sugestão de compra e prioridade.
    public void setQtdAtual(int qtdAtual){
        if(qtdAtual < 0){
            throw new IllegalArgumentException("Quantidade inválida.");
        }
        this.qtdAtual = qtdAtual;
    }

    public int getSugestaoCompra(){
        return sugestaoCompra;
    }

    public void setSugestaoCompra(int sugestaoCompra){
        if (sugestaoCompra < 0) {
            throw new IllegalArgumentException("Inválido: valor negativo.");
        }
        this.sugestaoCompra = sugestaoCompra;
    }

    public String getPrioridade(){
        return prioridade;
    }

    public void setPrioridade(String prioridade){
        if(prioridade == null || prioridade.isEmpty()){
            throw new IllegalArgumentException("Prioridade inválida.");
        }
        this.prioridade = prioridade;
    }
}