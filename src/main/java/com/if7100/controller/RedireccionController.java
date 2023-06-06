package com.if7100.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RedireccionController {

    @PostMapping("/redireccionarEntidades")
    public String redireccionarEntidades(@RequestParam("opcion") String opcion) {

        switch (opcion) {
            case "hecho" -> {
                return "redirect:/hechos";
            }
            case "imputado" -> {
                return "redirect:/imputados";
            }
            case "organismo" -> {
                return "redirect:/organismos";
            }
            case "lugar" -> {
                return "redirect:/lugares";
            }
            case "procesoJudicial" -> {
                return "redirect:/procesosJudiciales";
            }
            case "bitacoras" -> {
                return "redirect:/bitacoras";
            }
            case "victima" -> {
                return "redirect:/victima";
            }
            case "perfiles" -> {
                return "redirect:/perfiles";
            }
            case "usuarios" -> {
                return "redirect:/usuarios";
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
            case "orientacionSexual" -> {
                return "redirect:/orientacionesSexuales";
            }
            case "modalidad" -> {
                return "redirect:/modalidades";
            }
            case "nivelEducativo" -> {
                return "redirect:/nivelEducativo";
            }
            case "situacionJuridica" -> {
                return "redirect:/situacionesjuridicas";
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
            case "tipoOrganismo" -> {
                return "redirect:/tipoOrganismo";
            }
            default -> {
                return "redirect:/error";
            }

        }

    }

    @PostMapping("/redireccionarAccionesHecho")
    public String redireccionarAccionesHecho(@RequestParam("opcion") String opcion) {

        String[] prueba = opcion.split("-");
        String accion = prueba[1];
        String id = prueba[0];

        switch (accion) {
            case "agregarLugar" -> {
                return "redirect:/lugar/new/" + id;
            }
            case "agregarImputado" -> {
                return "redirect:/hechosimputado/new/" + id;
            }
            case "agregarOrganismo" -> {
                return "redirect:/hechosorganismo/new/" + id;
            }
            case "mostrarLugar" -> {
                return "redirect:/lugar/" + id;
            }
            case "mostrarImputado" -> {
                return "redirect:/hechosimputado/" + id;
            }
            case "mostrarOrganismo" -> {
                return "redirect:/hechosorganismo/" + id;
            }
            default -> {
                return "redirect:/error";
            }

        }

    }

    @PostMapping("/redireccionarRelaciones")
    public String redireccionarRelaciones(@RequestParam("opcion") String opcion) {

        switch (opcion) {
            case "hechoimputado" -> {
                return "redirect:/hechoimputado";
            }
            case "hechoorganismo" -> {
                return "redirect:/hechoorganismo";
            }
            default -> {
                return "redirect:/error";
            }

        }

    }

}