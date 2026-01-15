package com.exemplo.pedidos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PedidoController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/pedidos")
    public List<Map<String, Object>> listarPedidos() {
        String sql = "SELECT TOP 10 codemp, codfil, numped, tipped, prcped, tnspro, tnsser, datemi, horemi, datprv " +
                "FROM sapiens_homologacao.e120ped WHERE YEAR(datemi) = 2025";
        return jdbcTemplate.queryForList(sql);
    }

    @PostMapping("/liberar-pedido")
    public String liberarPedido(@RequestParam String numped) {
        String sql = "UPDATE sapiens_homologacao.e120ped SET sitped = 'L' WHERE numped = ?";
        int rowsAffected = jdbcTemplate.update(sql, numped);
        return rowsAffected > 0 ? "Pedido liberado com sucesso" : "Erro ao liberar pedido";
    }
}