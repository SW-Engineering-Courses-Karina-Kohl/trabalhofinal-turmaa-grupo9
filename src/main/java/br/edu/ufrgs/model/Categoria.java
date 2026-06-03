package br.edu.ufrgs.model;

public class Categoria {

    private String nome;
    private int estoqueSeguranca;
    private int lotePadraoCompra;
    private int pontoPedido;

    public Categoria(String nome,
                     int estoqueSeguranca,
                     int lotePadraoCompra,
                     int pontoPedido) {

        this.nome = nome;
        this.estoqueSeguranca = estoqueSeguranca;
        this.lotePadraoCompra = lotePadraoCompra;
        this.pontoPedido = pontoPedido;
    }

    public String getNome() {
        return nome;
    }

    public int getEstoqueSeguranca() {
        return estoqueSeguranca;
    }

    public int getLotePadraoCompra() {
        return lotePadraoCompra;
    }

    public int getPontoPedido() {
        return pontoPedido;
    }
}
