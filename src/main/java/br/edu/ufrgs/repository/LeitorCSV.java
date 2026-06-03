package br.edu.ufrgs.repository;

import br.edu.ufrgs.model.Categoria;
import br.edu.ufrgs.model.Produto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LeitorCSV implements LeitorArquivo {

    private String nomeArquivo;

    public LeitorCSV(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    @Override
    public List<?> ler(String caminho) {
        this.nomeArquivo = caminho;
        return new ArrayList<>();
    }

    public List<Categoria> lerCategorias() {

        List<Categoria> categorias = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader(nomeArquivo))) {

            br.readLine();

            String linha;

            while ((linha = br.readLine()) != null) {
                System.out.println("Lendo linha: " + linha);
                String[] dados = linha.split(",");

                Categoria categoria = new Categoria(
                        dados[0].trim(),
                        Integer.parseInt(dados[1].trim()),
                        Integer.parseInt(dados[2].trim()),
                        Integer.parseInt(dados[3].trim())
                );

                categorias.add(categoria);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categorias;
    }

    public List<Produto> lerProdutos(List<Categoria> categorias) {

        List<Produto> produtos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader(nomeArquivo))) {

            br.readLine();

            String linha;

            while ((linha = br.readLine()) != null) {
                
                String[] dados = linha.split(",");
                
                String nomeCategoria = dados[2].trim();

                System.out.println("Procurando categoria: " + nomeCategoria);
                if (nomeCategoria == null) {
                    System.out.println("Categoria não encontrada: " + nomeCategoria);
                    continue; // ou throw
                }

                Categoria categoriaEncontrada = null;

                for (Categoria categoria : categorias) {
                    if (categoria.getNome().equalsIgnoreCase(nomeCategoria)) {
                        categoriaEncontrada = categoria;
                        break;
                    }
                }

                Produto produto = new Produto(
                        dados[0].trim(),
                        dados[1].trim(),
                        Integer.parseInt(dados[3].trim()),
                        categoriaEncontrada
                );

                produtos.add(produto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtos;
    }
}


