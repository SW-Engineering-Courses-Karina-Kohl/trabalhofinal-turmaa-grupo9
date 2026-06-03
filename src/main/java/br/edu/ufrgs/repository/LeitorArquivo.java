package br.edu.ufrgs.repository;

import java.util.List;

public interface LeitorArquivo {
    List<?> ler(String caminho);
}