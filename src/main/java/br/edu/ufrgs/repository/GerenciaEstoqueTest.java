package br.edu.ufrgs.repository;

import br.edu.ufrgs.model.Categoria;
import br.edu.ufrgs.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GerenciaEstoqueTest{

    private GerenciaEstoque gerenciaEstoque;
    private Categoria categoriaDispositivos;

    @BeforeEach
    public void setUp(){
        // Cria uma categoria para testar com ponto de pedido 10 e lote padrão 50.
        gerenciaEstoque = new GerenciaEstoque();
        categoriaDispositivos = new Categoria("Dispositivos", 5, 50, 10);;
    }

    // Testa a prioridade alta para quando o estoque estiver zerado (quantidade atual = 0).
    @Test
    public void testPrioridadeAltaQuandoZerado(){
        Produto produtoZerado = new Produto("001", "Mouse", 0, categoriaDispositivos);
        
        // Adiciona o produto na lista manualmente e verifica a reposição.
        gerenciaEstoque.getListaProdutos().add(produtoZerado);
        gerenciaEstoque.verificarNecessidadeReposicao();

        List<Produto> resultado = gerenciaEstoque.getListaProdutos();
        assertEquals("Alta", resultado.get(0).getPrioridade(), "Produtos zerados devem ter prioridade Alta");
        assertEquals(50, resultado.get(0).getSugestaoCompra(), "A sugestão deve ser igual ao lote padrão");
    }

    // Testa a prioridade média para estoque abaixo do ponto de pedido mas não zerado (quantidade atual = 5).
    @Test
    public void testPrioridadeMediaQuandoPrecisaReposicao(){
        Produto produtoEstoqueBaixo = new Produto("002", "Teclado", 5, categoriaDispositivos);

        // Adiciona o produto na lista manualmente e verifica a reposição.
        gerenciaEstoque.getListaProdutos().add(produtoEstoqueBaixo);
        gerenciaEstoque.verificarNecessidadeReposicao();

        List<Produto> resultado = gerenciaEstoque.getListaProdutos();
        assertEquals("Média", resultado.get(0).getPrioridade(), "Produtos abaixo do ponto de pedido devem ter prioridade Média");
        assertEquals(50, resultado.get(0).getSugestaoCompra());
    }

    // Testa a prioridade normal para estoque acima do ponto de pedido (quantidade atual = 15).
    @Test
    public void testPrioridadeNormalQuandoEstoqueConfortavel(){
        Produto produtoOk = new Produto("003", "Monitor 24 polegadas", 15, categoriaDispositivos);
        
        gerenciaEstoque.getListaProdutos().add(produtoOk);
        gerenciaEstoque.verificarNecessidadeReposicao();

        List<Produto> resultado = gerenciaEstoque.getListaProdutos();
        assertEquals("Normal", resultado.get(0).getPrioridade(), "Produtos com estoque OK devem ter prioridade Normal");
        assertEquals(0, resultado.get(0).getSugestaoCompra(), "A sugestão de compra deve ser 0 para estoque normal");
    }
}