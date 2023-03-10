package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {

    @interface Cozinhas {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
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

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar {
        }

    }

    @interface Restaurantes {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeGerenciarCadastro {
        }

        @PreAuthorize("""
                hasAuthority('SCOPE_WRITE')
                and (hasAuthority('EDITAR_RESTAURANTES') or
                     @apiSecurity.gerenciaRestaurante(#restauranteId))
                """)
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeGerenciarFuncionamento {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar {
        }

    }

    @interface Pedidos {

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("""
            hasAuthority('CONSULTAR_PEDIDOS') or
            @apiSecurity.getUsuarioId() == returnObject.cliente.id or
            @apiSecurity.gerenciaRestaurante(returnObject.restaurante.id)
        """)
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeBuscar {
        }

        @PreAuthorize("""
            hasAuthority('SCOPE_READ') and isAuthenticated() and
            (hasAuthority('CONSULTAR_PEDIDOS') or
            @apiSecurity.getUsuarioId() == #filtro.clienteId or
            @apiSecurity.gerenciaRestaurante(#filtro.restauranteId))
        """)
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodePesquisar {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeCriar {
        }

        @PreAuthorize("""
                hasAuthority('SCOPE_WRITE')
                and (hasAuthority('GERENCIAR_PEDIDOS') or
                     @apiSecurity.gerenciaRestauranteDoPedido(#codigoPedido))
                """)
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeGerenciarPedidos {
        }

    }

}
