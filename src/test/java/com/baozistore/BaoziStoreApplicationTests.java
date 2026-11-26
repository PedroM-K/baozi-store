package com.baozistore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class BaoziStoreApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void testCrudCliente() throws Exception {
        // POST
        String clienteJson = "{\"nome\":\"Pedro5279807\",\"clienteDesde\":\"2026-11-25\"}";
        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Pedro5279807"));

        // GET all
        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // GET {id}
        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Pedro5279807"));

        // DELETE
        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isNoContent());

        // GET {id} after delete -> 404
        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrudProduto() throws Exception {
        // POST
        String produtoJson = "{\"nome\":\"Baozi Tradicional de Carne\",\"preco\":7.50,\"estoque\":true}";
        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(produtoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Baozi Tradicional de Carne"))
                .andExpect(jsonPath("$.preco").value(7.50))
                .andExpect(jsonPath("$.estoque").value(true));

        // GET all
        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // GET {id}
        mockMvc.perform(get("/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Baozi Tradicional de Carne"));

        // DELETE
        mockMvc.perform(delete("/produtos/1"))
                .andExpect(status().isNoContent());

        // GET {id} after delete -> 404
        mockMvc.perform(get("/produtos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrudPedido() throws Exception {
        // POST
        String pedidoJson = "{\"clienteId\":1,\"produtoId\":1,\"quantidade\":3}";
        mockMvc.perform(post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pedidoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.quantidade").value(3));

        // GET all
        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // GET {id}
        mockMvc.perform(get("/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantidade").value(3));

        // DELETE
        mockMvc.perform(delete("/pedidos/1"))
                .andExpect(status().isNoContent());

        // GET {id} after delete -> 404
        mockMvc.perform(get("/pedidos/1"))
                .andExpect(status().isNotFound());
    }
}
