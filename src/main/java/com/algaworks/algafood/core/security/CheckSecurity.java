package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {

    @interface ControleDeAcesso {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and @apiSecurity.usuarioAutenticadoIgual(#usuarioId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeAlterarPropriaSenha{
        }

        @PreAuthorize("""
            hasAuthority('SCOPE_WRITE') and 
            (@apiSecurity.usuarioAutenticadoIgual(#usuarioId) or
            hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES'))
        """)
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeAlterarUsuario{
        }

        @PreAuthorize("@apiSecurity.podeEditarControleDeAcesso()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar{
        }

        @PreAuthorize("@apiSecurity.podeConsultarControleDeAcesso()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar{
        }

    }

    @interface Estados {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar {
        }

        @PreAuthorize("@apiSecurity.podeConsultarEstados()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar {
        }

    }

    @interface Cidades {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar {
        }

        @PreAuthorize("@apiSecurity.podeConsultarCidades()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar {
        }

    }

    @interface Cozinhas {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar {
        }

        @PreAuthorize("@apiSecurity.podeConsultarCozinhas()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar {
        }

    }

    @interface FormasPagamento {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar {
        }

        @PreAuthorize("@apiSecurity.podeConsultarFormasPagamento()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar {
        }

    }

    @interface Restaurantes {

        @PreAuthorize("@apiSecurity.podeGerenciarCadastroRestaurantes()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeGerenciarCadastro {
        }

        @PreAuthorize("@apiSecurity.podeGerenciarFuncionamentoRestaurantes(#restauranteId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeGerenciarFuncionamento {
        }

        @PreAuthorize("@apiSecurity.podeConsultarRestaurantes()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar {
        }

    }

    @interface Pedidos {

        @PreAuthorize("@apiSecurity.podePesquisarPedidos()")
        @PostAuthorize("@apiSecurity.podePesquisarPedidos(returnObject.cliente.id, returnObject.restaurante.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeBuscar {
        }

        @PreAuthorize("@apiSecurity.podePesquisarPedidos(#filtro.clienteId, #filtro.restauranteId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodePesquisar {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeCriar {
        }


        @PreAuthorize("@apiSecurity.podeGerenciarPedidos(#codigoPedido)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeGerenciarPedidos {
        }

    }

    @interface Estatisticas {

        @PreAuthorize("@apiSecurity.podeConsultarEstatisticas()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar {
        }

    }

}
