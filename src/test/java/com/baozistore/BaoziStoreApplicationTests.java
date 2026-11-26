package com.baozistore;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.jayway.jsonpath.JsonPath;

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
    void testCrudClienteCompleto() throws Exception {
        String clienteJson = "{\"nome\":\"Pedro5279807\",\"clienteDesde\":\"2026-11-26\"}";
        MvcResult postResult = mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Pedro5279807"))
                .andExpect(jsonPath("$.clienteDesde").value("2026-11-26"))
                .andReturn();

        Number id = JsonPath.read(postResult.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].nome", hasItem("Pedro5279807")));

        mockMvc.perform(get("/clientes/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Pedro5279807"));

        String clienteAtualizadoJson = "{\"nome\":\"Pedro5279807 Atualizado\",\"clienteDesde\":\"2026-11-26\"}";
        mockMvc.perform(put("/clientes/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteAtualizadoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Pedro5279807 Atualizado"));

        mockMvc.perform(delete("/clientes/" + id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/clientes/" + id))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/clientes/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrudProdutoCompleto() throws Exception {
        String produtoJson = "{\"nome\":\"Baozi Tradicional de Carne\",\"preco\":7.50,\"estoque\":true}";
        MvcResult postResult = mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(produtoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Baozi Tradicional de Carne"))
                .andExpect(jsonPath("$.preco").value(7.50))
                .andExpect(jsonPath("$.estoque").value(true))
                .andReturn();

        Number id = JsonPath.read(postResult.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].nome", hasItem("Baozi Tradicional de Carne")));

        mockMvc.perform(get("/produtos/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Baozi Tradicional de Carne"));

        String produtoAtualizadoJson = "{\"nome\":\"Baozi Tradicional de Carne\",\"preco\":8.00,\"estoque\":false}";
        mockMvc.perform(put("/produtos/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(produtoAtualizadoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.preco").value(8.00))
                .andExpect(jsonPath("$.estoque").value(false));

        mockMvc.perform(delete("/produtos/" + id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/produtos/" + id))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/produtos/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrudPedidoCompleto() throws Exception {
        MvcResult clienteResult = mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Pedro5279807\",\"clienteDesde\":\"2026-11-26\"}"))
                .andExpect(status().isCreated())
                .andReturn();
        Number clienteId = JsonPath.read(clienteResult.getResponse().getContentAsString(), "$.id");

        MvcResult produtoResult = mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Baozi Tradicional de Carne\",\"preco\":7.50,\"estoque\":true}"))
                .andExpect(status().isCreated())
                .andReturn();
        Number produtoId = JsonPath.read(produtoResult.getResponse().getContentAsString(), "$.id");

        String pedidoJson = String.format("{\"clienteId\":%s,\"produtoId\":%s,\"quantidade\":3}", clienteId, produtoId);
        MvcResult pedidoResult = mockMvc.perform(post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pedidoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.clienteId").value(clienteId.longValue()))
                .andExpect(jsonPath("$.produtoId").value(produtoId.longValue()))
                .andExpect(jsonPath("$.quantidade").value(3))
                .andReturn();

        Number pedidoId = JsonPath.read(pedidoResult.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", hasItem(pedidoId.intValue())));

        mockMvc.perform(get("/pedidos/" + pedidoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantidade").value(3));

        String pedidoAtualizadoJson = String.format("{\"clienteId\":%s,\"produtoId\":%s,\"quantidade\":5}", clienteId, produtoId);
        mockMvc.perform(put("/pedidos/" + pedidoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(pedidoAtualizadoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantidade").value(5));

        mockMvc.perform(delete("/pedidos/" + pedidoId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/pedidos/" + pedidoId))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/produtos/" + produtoId))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/clientes/" + clienteId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testNotFoundPaths() throws Exception {
        mockMvc.perform(get("/clientes/99999")).andExpect(status().isNotFound());
        mockMvc.perform(put("/clientes/99999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Inexistente\",\"clienteDesde\":\"2026-11-26\"}"))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/produtos/99999")).andExpect(status().isNotFound());
        mockMvc.perform(put("/produtos/99999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Inexistente\",\"preco\":1.00,\"estoque\":false}"))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/pedidos/99999")).andExpect(status().isNotFound());
        mockMvc.perform(put("/pedidos/99999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"clienteId\":1,\"produtoId\":1,\"quantidade\":1}"))
                .andExpect(status().isNotFound());
    }
}
