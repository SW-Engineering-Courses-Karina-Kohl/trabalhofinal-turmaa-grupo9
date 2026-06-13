<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="br.edu.ufrgs.model.Produto" %>
        <%@ page import="java.util.List" %>
            <html>

            <head>
            </head>

            <body>
                <h1>Inventário</h1>

                <form action="inventario" method="post" enctype="multipart/form-data">
                    <label for="arquivoInventario">Arquivo CSV do inventário</label>
                    <input id="arquivoInventario" type="file" name="arquivoInventario" accept=".csv" required>
                    <button type="submit">Enviar</button>
                </form>

                <% String erro=(String) request.getAttribute("erro"); List<Produto> produtos = (List<Produto>)
                        request.getAttribute("produtos");
                        %>

                        <% if (erro !=null && !erro.isBlank()) { %>
                            <p class="erro">
                                <%= erro %>
                            </p>
                            <% } %>

                                <% if (produtos !=null && !produtos.isEmpty()) { %>
                                    <table>
                                        <thead>
                                            <tr>
                                                <th>Codigo</th>
                                                <th>Descricao</th>
                                                <th>Categoria</th>
                                                <th>Quantidade atual</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% for (Produto produto : produtos) { %>
                                                <tr>
                                                    <td>
                                                        <%= produto.getCodigo() %>
                                                    </td>
                                                    <td>
                                                        <%= produto.getDescricao() %>
                                                    </td>
                                                    <td>
                                                        <%= produto.getCategoria().getNome() %>
                                                    </td>
                                                    <td>
                                                        <%= produto.getQtdAtual() %>
                                                    </td>
                                                </tr>
                                                <% } %>
                                        </tbody>
                                    </table>
                                    <% } %>
            </body>

            </html>