<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="br.edu.ufrgs.model.Produto" %>
        <%@ page import="java.util.List" %>
            <!DOCTYPE html>
            <html class="light" lang="pt-BR">

            <head>
                <meta charset="utf-8">
                <meta content="width=device-width, initial-scale=1.0" name="viewport">
                <title>CSV Engine | Estoque</title>
                <script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
                <link
                    href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700;900&amp;family=JetBrains+Mono&amp;display=swap"
                    rel="stylesheet">
                <link
                    href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&amp;display=swap"
                    rel="stylesheet">
                <link
                    href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&amp;display=swap"
                    rel="stylesheet">
                <style>
                    .material-symbols-outlined {
                        font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 24;
                    }

                    .hide-scrollbar::-webkit-scrollbar {
                        display: none;
                    }

                    .hide-scrollbar {
                        -ms-overflow-style: none;
                        scrollbar-width: none;
                    }

                    @keyframes fadeIn {
                        from {
                            opacity: 0;
                            transform: translateY(10px);
                        }

                        to {
                            opacity: 1;
                            transform: translateY(0);
                        }
                    }

                    .animate-fade-in {
                        animation: fadeIn 0.5s ease-out forwards;
                    }
                </style>
                <script id="tailwind-config">
                    tailwind.config = {
                        darkMode: "class",
                        theme: {
                            extend: {
                                "colors": {
                                    "surface-container-highest": "#d8e2ff",
                                    "secondary-container": "#9f8eff",
                                    "primary-fixed-dim": "#b2c5ff",
                                    "secondary-fixed-dim": "#c9bfff",
                                    "tertiary-container": "#a33500",
                                    "on-error-container": "#93000a",
                                    "surface": "#faf9ff",
                                    "inverse-on-surface": "#edf0ff",
                                    "on-secondary": "#ffffff",
                                    "on-error": "#ffffff",
                                    "on-primary-fixed": "#001848",
                                    "surface-container": "#e9edff",
                                    "surface-variant": "#d8e2ff",
                                    "error-container": "#ffdad6",
                                    "primary-container": "#0052cc",
                                    "surface-container-lowest": "#ffffff",
                                    "surface-dim": "#ccdaff",
                                    "on-primary": "#ffffff",
                                    "error": "#ba1a1a",
                                    "secondary-fixed": "#e5deff",
                                    "secondary": "#5e4db9",
                                    "surface-container-low": "#f1f3ff",
                                    "primary": "#003d9b",
                                    "on-secondary-container": "#341d8d",
                                    "on-secondary-fixed-variant": "#4633a0",
                                    "tertiary-fixed-dim": "#ffb59b",
                                    "surface-container-high": "#e1e8ff",
                                    "on-background": "#051a3e",
                                    "surface-bright": "#faf9ff",
                                    "primary-fixed": "#dae2ff",
                                    "on-surface-variant": "#434654",
                                    "on-tertiary-container": "#ffc6b2",
                                    "outline-variant": "#c3c6d6",
                                    "tertiary": "#7b2600",
                                    "on-secondary-fixed": "#1a0063",
                                    "tertiary-fixed": "#ffdbcf",
                                    "on-tertiary-fixed": "#380d00",
                                    "on-primary-container": "#c4d2ff",
                                    "inverse-surface": "#1d3054",
                                    "surface-tint": "#0c56d0",
                                    "background": "#faf9ff",
                                    "on-tertiary": "#ffffff",
                                    "on-primary-fixed-variant": "#0040a2",
                                    "inverse-primary": "#b2c5ff",
                                    "on-tertiary-fixed-variant": "#812800",
                                    "on-surface": "#051a3e",
                                    "outline": "#737685"
                                },
                                "borderRadius": {
                                    "DEFAULT": "0.25rem",
                                    "lg": "0.5rem",
                                    "xl": "0.75rem",
                                    "full": "9999px"
                                },
                                "spacing": {
                                    "container-max": "1440px",
                                    "xs": "4px",
                                    "xl": "32px",
                                    "gutter": "16px",
                                    "margin-desktop": "32px",
                                    "base": "4px",
                                    "margin-mobile": "16px",
                                    "sm": "8px",
                                    "xxl": "48px",
                                    "md": "16px",
                                    "lg": "24px"
                                },
                                "fontFamily": {
                                    "code-md": ["JetBrains Mono"],
                                    "body-sm": ["Inter"],
                                    "headline-lg": ["Inter"],
                                    "label-md": ["Inter"],
                                    "headline-sm": ["Inter"],
                                    "headline-md": ["Inter"],
                                    "body-md": ["Inter"],
                                    "body-lg": ["Inter"]
                                },
                                "fontSize": {
                                    "code-md": ["13px", { "lineHeight": "20px", "fontWeight": "400" }],
                                    "body-sm": ["12px", { "lineHeight": "16px", "fontWeight": "400" }],
                                    "headline-lg": ["30px", { "lineHeight": "38px", "letterSpacing": "-0.02em", "fontWeight": "600" }],
                                    "label-md": ["12px", { "lineHeight": "16px", "letterSpacing": "0.05em", "fontWeight": "600" }],
                                    "headline-sm": ["16px", { "lineHeight": "24px", "fontWeight": "600" }],
                                    "headline-md": ["20px", { "lineHeight": "28px", "fontWeight": "600" }],
                                    "body-md": ["14px", { "lineHeight": "20px", "fontWeight": "400" }],
                                    "body-lg": ["16px", { "lineHeight": "24px", "fontWeight": "400" }]
                                }
                            },
                        },
                    }
                </script>
            </head>

            <body class="bg-background text-on-background font-body-md min-h-screen flex flex-col">
                <!-- TopNavBar -->
                <header
                    class="w-full top-0 sticky bg-surface dark:bg-surface-dim border-b border-outline-variant dark:border-outline z-50">
                    <div class="flex justify-between items-center h-16 px-margin-desktop max-w-container-max mx-auto">
                        <div class="flex items-center gap-xl">
                            <span
                                class="font-headline-md text-headline-md font-bold text-primary dark:text-primary-fixed-dim">CSV
                                Engine</span>
                            <nav class="hidden md:flex gap-lg">
                                <a class="font-body-md text-body-md text-primary dark:text-primary-fixed-dim border-b-2 border-primary dark:border-primary-fixed-dim pb-1"
                                    href="#">Estoque</a>


                            </nav>
                        </div>
                        <div class="flex items-center gap-md">


                        </div>
                    </div>
                </header>
                <main class="flex-grow">
                    <!-- Hero Section -->
                    <section class="relative py-xxl px-margin-desktop overflow-hidden">
                        <div class="max-w-4xl mx-auto text-center relative z-10 animate-fade-in">
                            <h1 class="font-headline-lg text-headline-lg text-on-surface mb-md">
                                Tenha controle do seu Estoque
                            </h1>
                            <p class="font-body-lg text-body-lg text-on-surface-variant mb-xl max-w-2xl mx-auto">
                                Ferramenta para gerenciamento de estoque.
                            </p>
                            <form action="inventario" method="post" enctype="multipart/form-data"
                                class="flex flex-col items-center gap-md">
                                <label for="arquivoInventario"
                                    class="cursor-pointer group flex flex-col items-center justify-center w-64 h-64 border-2 border-dashed border-outline-variant rounded-xl bg-surface-container-lowest hover:border-primary hover:bg-surface-container transition-all duration-300">
                                    <span
                                        class="material-symbols-outlined text-primary text-5xl mb-sm group-hover:scale-110 transition-transform">upload_file</span>
                                    <span class="font-headline-sm text-headline-sm text-on-surface">Selecionar
                                        CSV</span>
                                    <span class="font-body-sm text-body-sm text-on-surface-variant mt-xs">ou arraste
                                        o arquivo aqui</span>
                                </label>
                                <input id="arquivoInventario" type="file" name="arquivoInventario" accept=".csv"
                                    class="hidden" required>
                                <button type="submit"
                                    class="flex items-center gap-xs font-body-md text-body-md px-lg py-md rounded-lg bg-primary text-on-primary hover:bg-primary-container shadow-sm transition-all duration-200 active:scale-95">
                                    <span class="material-symbols-outlined"
                                        style="font-variation-settings: &quot;FILL&quot; 1;">upload</span>
                                    Enviar
                                </button>
                            </form>
                        </div>
                        <!-- Decorative background element -->
                        <div
                            class="absolute top-0 right-0 -translate-y-1/2 translate-x-1/4 w-96 h-96 bg-primary/5 rounded-full blur-3xl -z-0">
                        </div>
                        <div
                            class="absolute bottom-0 left-0 translate-y-1/2 -translate-x-1/4 w-96 h-96 bg-secondary/5 rounded-full blur-3xl -z-0">
                        </div>
                    </section>
                    <!-- Table Section (Hidden initially) -->
                    <% String erro=(String) request.getAttribute("erro"); List<Produto> produtos = (List
                        <Produto>)
                            request.getAttribute("produtos");
                            %>

                            <% if (erro !=null && !erro.isBlank()) { %>
                                <p class="erro">
                                    <%= erro %>
                                </p>
                                <% } %>

                                    <% if (produtos !=null && !produtos.isEmpty()) { %>
                                        <section
                                            class="py-xl px-margin-desktop bg-surface-container-low animate-fade-in"
                                            id="data-section">
                                            <div class="max-w-container-max mx-auto">
                                                <div
                                                    class="flex flex-col md:flex-row justify-between items-end md:items-center mb-lg gap-md">
                                                    <div>
                                                        <h2 class="font-headline-md text-headline-md text-on-surface">
                                                            Dados Processados</h2>
                                                        <p class="font-body-sm text-body-sm text-on-surface-variant">
                                                            Visualização dos
                                                            produtos do seu inventário.</p>
                                                    </div>
                                                </div>
                                                <!-- Data Table Card -->

                                                <div
                                                    class="bg-surface-container-lowest rounded-xl border border-outline-variant overflow-hidden shadow-sm">
                                                    <div class="overflow-x-auto hide-scrollbar">
                                                        <table class="w-full text-left border-collapse">
                                                            <thead>
                                                                <tr
                                                                    class="bg-surface-container-high border-b border-outline-variant">
                                                                    <th
                                                                        class="px-lg py-md font-label-md text-label-md text-on-surface-variant uppercase tracking-wider">
                                                                        Código</th>
                                                                    <th
                                                                        class="px-lg py-md font-label-md text-label-md text-on-surface-variant uppercase tracking-wider">
                                                                        Descrição</th>
                                                                    <th
                                                                        class="px-lg py-md font-label-md text-label-md text-on-surface-variant uppercase tracking-wider">
                                                                        Categoria</th>
                                                                    <th
                                                                        class="px-lg py-md font-label-md text-label-md text-on-surface-variant uppercase tracking-wider text-center">
                                                                        Quantidade atual</th>
                                                                </tr>
                                                            </thead>

                                                            <% for (Produto produto : produtos) { %>
                                                                <tr
                                                                    class="hover:bg-surface-container-low transition-colors group">
                                                                    <td
                                                                        class="px-lg py-md font-body-md text-body-md text-on-surface">
                                                                        <%= produto.getCodigo() %>
                                                                    </td>
                                                                    <td
                                                                        class="px-lg py-md font-code-md text-code-md text-on-surface-variant">
                                                                        <%= produto.getDescricao() %>
                                                                    <td
                                                                        class="px-lg py-md font-body-md text-body-md text-on-surface-variant">
                                                                        <%= produto.getCategoria().getNome() %>
                                                                    </td>
                                                                    <td class="px-lg py-md text-center">
                                                                        <span <% if (produto.getQtdAtual()==0) { %>
                                                                            style="background-color:#dc3545;color:#fff;"
                                                                            <% } else if (produto.getQtdAtual() <
                                                                                produto.getCategoria().getEstoqueSeguranca())
                                                                                { %>
                                                                                style="background-color:#ffc107;color:#000;"
                                                                                <% } %>
                                                                                    class="inline-flex items-center
                                                                                    px-sm py-xs rounded-full
                                                                                    bg-green-100 text-green-700
                                                                                    font-label-md text-label-md">
                                                                                    <%= produto.getQtdAtual() %>
                                                                        </span>
                                                                    </td>
                                                                </tr>
                                                                <% } %>
                                                                    </tbody>
                                                        </table>
                                                        <% } %>
                                                    </div>
                                                </div>
                                            </div>
                                        </section>
                                        <!-- Features Bento Grid -->
                                        <!-- <section class="py-xxl px-margin-desktop">
                        <div class="max-w-container-max mx-auto grid grid-cols-1 md:grid-cols-3 gap-lg">




                        </div>
                    </section> -->
                </main>
                <!-- Footer -->
                <footer
                    class="w-full mt-auto bg-surface-container-low dark:bg-surface-container-lowest border-t border-outline-variant dark:border-outline">
                    <div
                        class="flex flex-col md:flex-row justify-between items-center py-lg px-margin-desktop max-w-container-max mx-auto">
                        <div class="flex items-center gap-sm mb-md md:mb-0">
                            <span
                                class="font-label-md text-label-md font-black text-on-surface dark:text-on-surface uppercase tracking-widest">Autores:
                                Thiago Farias, Henri Brustolin, Alexandre e Allan</span>

                        </div>
                        <nav class="flex gap-lg">

                        </nav>
                    </div>
                </footer>
            </body>

            </html>