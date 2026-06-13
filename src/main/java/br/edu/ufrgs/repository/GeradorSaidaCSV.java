package br.edu.ufrgs.repository;

import br.edu.ufrgs.model.Produto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

/**
 * Responsável por exportar uma lista de produtos em formato CSV.
 */
public class GeradorSaidaCSV {

    private String nomeArquivoSaida;

    /** Inicializa o gerador com o caminho de destino do arquivo CSV. */
    public GeradorSaidaCSV(String nomeArquivoSaida) {
        this.nomeArquivoSaida = nomeArquivoSaida;
    }

    /**
     * Gera o arquivo CSV com todos os produtos processados.
     * 
     * Formato de saída:
     * codigo,descricao,categoria,qtdAtual,sugestao_compra,prioridade
     * 
     * @param produtos Lista de produtos já com reposição calculada
     */
    public void exportar(List<Produto> produtos) {

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(nomeArquivoSaida))) {

            // Escreve o cabeçalho do CSV.
            bw.write(gerarCabecalho());
            bw.newLine();

            // Escreve cada produto em uma linha.
            for (Produto produto : produtos) {
                bw.write(formatarLinha(produto));
                bw.newLine();
            }

        } catch (Exception e) {
            // Em um sistema maior, seria melhor usar logging estruturado e lançar exceções específicas.
            e.printStackTrace();
        }
    }

    /**
     * Retorna o cabeçalho do arquivo CSV.
     */
    private String gerarCabecalho() {
        return "codigo,descricao,categoria,qtdAtual,sugestao_compra,prioridade";
    }

    /**
     * Formata uma linha CSV para um produto.
     * Usa vírgula como separador e ordem: código, descrição, categoria, etc.
     */
    private String formatarLinha(Produto produto) {

        return produto.getCodigo() + ","
                + produto.getDescricao() + ","
                + produto.getCategoria().getNome() + ","
                + produto.getQtdAtual() + ","
                + produto.getSugestaoCompra() + ","
                + produto.getPrioridade();
    }
}
