package com.exemplo.pedidos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

@Controller
public class PedidoWebController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String index(Model model) {
        String sql = "SELECT top 10 [codemp], [codfil], [numped], [tipped], [sitped] " +
                "FROM [sapiens_homologacao].[e120ped] " +
                "WHERE year(datemi) = 2025";
        List<Map<String, Object>> pedidos = jdbcTemplate.queryForList(sql);
        model.addAttribute("pedidos", pedidos);
        return "index";
    }

    @GetMapping("/detalhes")
    public String detalhes(@RequestParam String codemp,
                           @RequestParam String codfil,
                           @RequestParam String numped,
                           Model model) {
        // Busca informações do pedido
        String sqlPedido = "SELECT [codemp], [codfil], [numped], [tipped], [sitped] " +
                "FROM [sapiens_homologacao].[e120ped] " +
                "WHERE [codemp] = ? AND [codfil] = ? AND [numped] = ?";
        Map<String, Object> pedido = jdbcTemplate.queryForMap(sqlPedido, codemp, codfil, numped);
        model.addAttribute("pedido", pedido);

        // Busca itens do pedido
        String sqlItens = "SELECT [seqipd], [codpro], [qtdped], [preuni] " +
                "FROM [sapiens_homologacao].[e120ipd] " +
                "WHERE [codemp] = ? AND [codfil] = ? AND [numped] = ?";
        List<Map<String, Object>> itens = jdbcTemplate.queryForList(sqlItens, codemp, codfil, numped);
        model.addAttribute("itens", itens);

        return "detalhes";
    }

    @PostMapping("/liberar")
    public String liberar(@RequestParam String codemp,
                          @RequestParam String codfil,
                          @RequestParam String numped,
                          RedirectAttributes redirectAttributes) {
        try {
            String sql = "UPDATE [sapiens_homologacao].[e120ped] SET [obsped] = obsped + 'Teste Homologação' " +
                    "WHERE [codemp] = ? AND [codfil] = ? AND [numped] = ?";
            int rowsAffected = jdbcTemplate.update(sql, codemp, codfil, numped);
            if (rowsAffected > 0) {
                redirectAttributes.addAttribute("success", true);
            } else {
                redirectAttributes.addAttribute("error", true);
            }
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
        }
        return "redirect:/";
    }
}