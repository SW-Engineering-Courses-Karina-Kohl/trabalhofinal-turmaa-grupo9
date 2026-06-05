
package br.edu.ufrgs.model;

public class Produto {

    private final String codigo;
    private final String descricao;
    private int qtdAtual;
    private final Categoria categoria;

    public Produto(String codigo, String descricao, int qtdAtual, Categoria categoria) {
        if(codigo == null||codigo.isEmpty()){
            throw new IllegalArgumentException("Código inválido.");
        }
        if(descricao == null||descricao.isEmpty()){
            throw new IllegalArgumentException("Descrição inválida.");
        }
        if(qtdAtual < 0){
            throw new IllegalArgumentException("Quantidade inserida inválida.");
        }
        if(categoria == null){
            throw new IllegalArgumentException("Categoria não inserida.");
        }
        this.codigo = codigo;
        this.descricao = descricao;
        this.qtdAtual = qtdAtual;
        this.categoria = categoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQtdAtual() {
        return qtdAtual;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setQtdAtual(int qtdAtual) {
        if(qtdAtual<0){
            throw new IllegalArgumentException("Quantidade inválida.");
        }
        this.qtdAtual = qtdAtual;
    }
}