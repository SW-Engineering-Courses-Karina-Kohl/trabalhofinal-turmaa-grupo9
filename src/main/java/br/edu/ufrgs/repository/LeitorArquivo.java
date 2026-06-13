package br.edu.ufrgs.repository;

import java.util.List;

/**
 * Contrato genérico para classes que leem arquivos e retornam coleções de objetos.
 * 
 * Permite extensão futura para diferentes tipos de leitores (XML, JSON, etc.)
 * mantendo uma interface consistente.
 */
public interface LeitorArquivo {
    /**
     * Lê um arquivo a partir do caminho especificado.
     * 
     * @param caminho Caminho do arquivo a ler
     * @return Lista de objetos lidos do arquivo
     */
    List<?> ler(String caminho);
}