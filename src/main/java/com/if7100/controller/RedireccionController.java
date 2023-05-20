package com.if7100.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RedireccionController {

    @PostMapping("/redireccionarEntidades")
    public String redireccionarEntidades(@RequestParam("opcion") String opcion) {
//

        switch (opcion) {
            case "hecho" -> {
                return "redirect:/hechos";
            }
            case "imputado" -> {
                return "redirect:/imputados";
            }
            case "lugar" -> {
                return "redirect:/lugares";
            }
            case "victima" -> {
                return "redirect:/victima";
            }
            default -> {
                return "redirect:/error";
            }

        }
    }

    @PostMapping("/redireccionarCampos")
    public String redireccionarCampos(@RequestParam("opcion") String opcion) {

        switch (opcion) {
            case "identidadGenero" -> {
                return "redirect:/identidadgenero";
            }
            case "organismo" -> {
                return "redirect:/organismos";
            }
            case "orientacionSexual" -> {
                return "redirect:/orientacionesSexuales";
            }
            case "modalidad" -> {
                return "redirect:/modalidades";
            }
            case "nivelEducativo" -> {
                return "redirect:/nivelEducativo";
            }
            case "procesoJudicial" -> {
                return "redirect:/procesojudicial";
            }
            case "tipoLugar" -> {
                return "redirect:/tipolugares";
            }
            case "tipoRelacion" -> {
                return "redirect:/tiporelaciones";
            }
            case "tipoVictima" -> {
                return "redirect:/tipovictimas";
            }
            default -> {
                return "redirect:/error";
            }

        }

    }
}