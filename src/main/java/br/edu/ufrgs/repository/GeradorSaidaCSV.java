package br.edu.ufrgs.repository;

import br.edu.ufrgs.model.Produto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class GeradorSaidaCSV {

    private String nomeArquivoSaida;

    public GeradorSaidaCSV(String nomeArquivoSaida) {
        this.nomeArquivoSaida = nomeArquivoSaida;
    }

    public void exportar(List<Produto> produtos) {

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(nomeArquivoSaida))) {

            bw.write(gerarCabecalho());
            bw.newLine();

            for (Produto produto : produtos) {
                bw.write(formatarLinha(produto));
                bw.newLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String gerarCabecalho() {
        return "codigo,descricao,categoria,qtdAtual,sugestao_compra,prioridade";
    }

    private String formatarLinha(Produto produto) {

        return produto.getCodigo() + ","
                + produto.getDescricao() + ","
                + produto.getCategoria().getNome() + ","
                + produto.getQtdAtual() + ","
                + produto.getSugestaoCompra() + ","
                + produto.getPrioridade();
    }
}
