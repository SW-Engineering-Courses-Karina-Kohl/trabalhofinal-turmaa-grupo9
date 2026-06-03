
package br.edu.ufrgs.model;

public class Produto {

    private String codigo;
    private String descricao;
    private int qtdAtual;
    private Categoria categoria;

    public Produto(String codigo,
                   String descricao,
                   int qtdAtual,
                   Categoria categoria) {

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
        this.qtdAtual = qtdAtual;
    }
}