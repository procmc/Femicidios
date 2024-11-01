package com.if7100.controller;

import com.if7100.entity.Bitacora;
import com.if7100.entity.Imputado;
import com.if7100.entity.Organizacion;
import com.if7100.entity.Paises;
import com.if7100.entity.Usuario;
import com.if7100.service.BitacoraService;
import com.if7100.service.PaisesService;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.entity.Perfil;
import com.if7100.entity.ProcesoJudicial;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.PerfilService;
import com.if7100.service.ProcesoJudicialService;
import com.itextpdf.io.IOException;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ProcesoJudicialController {

    private PaisesService paisesService;
    private ProcesoJudicialService procesoJudicialService;
    // instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private Perfil perfil;
    private PerfilService perfilService;
    // instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;

    public ProcesoJudicialController(BitacoraService bitacoraService, ProcesoJudicialService procesoJudicialService,
            PerfilService perfilService, UsuarioRepository usuarioRepository, PaisesService paisesService) {
        super();
        this.procesoJudicialService = procesoJudicialService;
        this.perfilService = perfilService;
        this.usuarioRepository = usuarioRepository;
        this.bitacoraService = bitacoraService;
        this.paisesService = paisesService;

    }

    @GetMapping("/procesojudicial/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException, java.io.IOException {

        this.validarPerfil();
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=procesosjudiciales_filtrados.pdf";
        response.setHeader(headerKey, headerValue);

        // Crear el documento PDF usando iText 7
        PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDoc = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDoc);

        // Agregar márgenes al documento (izquierda, derecha, arriba, abajo)
        document.setMargins(20, 20, 20, 20);

        // Agregar un título en negrita y centrado al documento
        Paragraph title = new Paragraph("Reporte de Procesos Judiciales")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18)
                .setBold();
        document.add(title);

        // Agregar un subtítulo
        Paragraph subTitle = new Paragraph("Procesos judiciales filtrados por país")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12)
                .setItalic();
        document.add(subTitle);

        // Espacio después del título
        document.add(new Paragraph("\n"));

        // Crear una tabla con columnas ajustadas dinámicamente
        Table table = new Table(UnitValue.createPercentArray(new float[] { 1, 1, 1, 1, 1, 2 }));
        table.setWidth(UnitValue.createPercentValue(100)); // Ajustar al 100% del ancho de la página

        // Agregar encabezado de tabla con estilo
        String[] headers = { "ID", "Estado", "Fecha de apertura", "Cantidad de personas imputadas", "Agravantes",
                "Tipo de delito" };

        for (String header : headers) {
            table.addHeaderCell(new Cell().add(new Paragraph(header).setBold())
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY));
        }

        // Obtener la lista de imputados filtrados por país
        Integer codigoPaisUsuario = this.usuario.getOrganizacion().getCodigoPais();
        List<ProcesoJudicial> procesoJudiciales = procesoJudicialService
                .findProcesoJudicialByCICodigoPais(codigoPaisUsuario);

        // Recorrer los imputados y agregarlos a la tabla
        for (ProcesoJudicial procesoJudicial : procesoJudiciales) {

            table.addCell(new Cell().add(new Paragraph(String.valueOf(procesoJudicial.getCI_Id())))
                    .setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add(new Paragraph(procesoJudicial.getCVEstado()))
                    .setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add(new Paragraph(procesoJudicial.getCDFechaApertura().toString()))
                    .setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add(new Paragraph(String.valueOf(procesoJudicial.getCIPersonasImputadas())))
                    .setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add(new Paragraph(procesoJudicial.getCVAgravantes()))
                    .setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add(new Paragraph(procesoJudicial.getCVTipoDelito()))//
                    .setTextAlignment(TextAlignment.CENTER));

        }

        // Asegurar que la tabla ocupe el espacio disponible sin distorsionarse
        table.setAutoLayout();

        // Agregar la tabla al documento
        document.add(table);

        // Cerrar el documento
        document.close();
    }

    @GetMapping("/procesojudicial/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException, java.io.IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        this.validarPerfil();
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=procesosjudiciales_filtrados.xlsx";
        response.setHeader(headerKey, headerValue);

        // Crear un nuevo libro de trabajo de Excel
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("procesos judiciales Filtrados");

        // Crear el estilo para el encabezado (negrita y color de fondo)
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        // Crear el estilo para las celdas (bordes y alineación)
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Crear el estilo para las fechas
        XSSFCellStyle dateCellStyle = workbook.createCellStyle();
        short dateFormat = workbook.createDataFormat().getFormat("yyyy-mm-dd");
        dateCellStyle.cloneStyleFrom(cellStyle);
        dateCellStyle.setDataFormat(dateFormat);

        // Crear la fila de encabezado
        XSSFRow headerRow = sheet.createRow(0);
        String[] headers = { "ID", "Estado", "Fecha de apertura", "Cantidad de personas imputadas", "Agravantes",
                "Tipo de delito", };
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle); // Aplicar estilo de encabezado
        }

        // Obtener la lista de procesos judiciales filtrados por país
        Integer codigoPaisUsuario = this.usuario.getOrganizacion().getCodigoPais();
        List<ProcesoJudicial> procesoJudiciales = procesoJudicialService
                .findProcesoJudicialByCICodigoPais(codigoPaisUsuario);
        // Paises pais = paisesService.getPaisByID(codigoPaisUsuario);

        // Rellenar las filas con los datos
        int rowNum = 1;
        for (ProcesoJudicial procesoJudicial : procesoJudiciales) {
            XSSFRow row = sheet.createRow(rowNum++);

            // ID
            row.createCell(0).setCellValue(procesoJudicial.getCI_Id());
            row.getCell(0).setCellStyle(cellStyle); // Aplicar estilo de celda

            // Tipo de Víctima
            row.createCell(1).setCellValue(procesoJudicial.getCVEstado());
            row.getCell(1).setCellStyle(cellStyle);

            // Fecha (formato de fecha)
            XSSFCell fechaCell = row.createCell(2);
            fechaCell.setCellValue(procesoJudicial.getCDFechaApertura());
            fechaCell.setCellStyle(dateCellStyle);

            // Modalidad
            row.createCell(3).setCellValue(procesoJudicial.getCIPersonasImputadas());
            row.getCell(3).setCellStyle(cellStyle);

            // Agresión Sexual
            row.createCell(4).setCellValue(procesoJudicial.getCVAgravantes());
            row.getCell(4).setCellStyle(cellStyle);

            // Denuncia previa
            row.createCell(5).setCellValue(procesoJudicial.getCVTipoDelito());
            row.getCell(5).setCellStyle(cellStyle);

        }

        // Ajustar automáticamente el ancho de las columnas
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Escribir el archivo Excel a la respuesta
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        }
        workbook.close();
    }

    private void validarPerfil() {

        try {
            Usuario usuario = new Usuario();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String username = authentication.getName();

            this.usuario = new Usuario(usuarioRepository.findByCVCedula(username));

            this.perfil = new Perfil(
                    perfilService.getPerfilById(usuarioRepository.findByCVCedula(username).getCIPerfil()));

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private Pageable initPages(int pg, int paginasDeseadas, int numeroTotalElementos) {
        int numeroPagina = pg - 1;
        if (numeroTotalElementos < 10) {
            paginasDeseadas = 1;
        }
        if (numeroTotalElementos < 1) {
            numeroTotalElementos = 1;
        }
        int tamanoPagina = (int) Math.ceil(numeroTotalElementos / (double) paginasDeseadas);
        return PageRequest.of(numeroPagina, tamanoPagina);
    }

    @GetMapping("/procesosJudiciales")
    public String listStudents(Model model) {
        this.validarPerfil();
        return "redirect:/procesojudicial/1";
    }

    @GetMapping("/procesojudicial/{pg}")
    public String listProcesoJudicial(Model model, @PathVariable Integer pg) {

        validarPerfil();

        Organizacion organizacion = this.usuario.getOrganizacion();

        // Obtener los procesos judiciales filtrados por el código de país
        List<ProcesoJudicial> procesosJudicialesFiltrados = procesoJudicialService
                .findProcesoJudicialByCICodigoPais(organizacion.getCodigoPais());

        int numeroTotalElementos = procesosJudicialesFiltrados.size();

        Pageable pageable = initPages(pg, 5, numeroTotalElementos);

        int tamanoPagina = pageable.getPageSize();
        int numeroPagina = pageable.getPageNumber();

        List<ProcesoJudicial> procesoJudicialesPaginados = procesosJudicialesFiltrados.stream()
                .skip((long) numeroPagina * tamanoPagina)
                .limit(tamanoPagina)
                .collect(Collectors.toList());

        List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
                .boxed()
                .toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("procesosJudiciales", procesoJudicialesPaginados);

        return "procesosJudiciales/procesosJudiciales";

    }

    @GetMapping("/procesosJudiciales/new")
    public String createProcesoJudicialForm(Model model) {

        try {
            this.validarPerfil();
            if (!this.perfil.getCVRol().equals("Consulta")) {

                model.addAttribute("procesoJudicial", new ProcesoJudicial());

                System.out.println("useres ");
                System.out.println(this.usuario.getCI_Id());
                return "procesosJudiciales/create_procesosJudiciales";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }

    }

    @PostMapping("/procesosJudiciales")
    public String saveProcesoJudicial(@ModelAttribute ProcesoJudicial procesoJudicial, Model model) {

        try {
            this.validarPerfil();
            procesoJudicial.setCICodigoPais(this.usuario.getOrganizacion().getCodigoPais());
            procesoJudicialService.saveProcesoJudicial(procesoJudicial);

            bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                    this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en proceso judicial"));

            return "redirect:/procesosJudiciales";

        } catch (DataIntegrityViolationException e) {
            String mensaje = "No se puede guardar el proceso judicial debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            System.out.println("Erroring de mierda: " + e.getMessage());
            return createProcesoJudicialForm(model);
        }
    }

    @GetMapping("/procesosJudiciales/{id}")
    public String deleteProcesoJudicial(@PathVariable int id) {

        try {
            this.validarPerfil();
            if (!this.perfil.getCVRol().equals("Consulta")) {

                procesoJudicialService.deleteProcesoJudicialById(id);

                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                    this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en proceso judicial"));

                return "redirect:/procesosJudiciales";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

    @GetMapping("/procesosJudiciales/edit/{id}")
    public String editProcesoJudicialForm(Model model, @PathVariable int id) {

        try {
            this.validarPerfil();
            if (!this.perfil.getCVRol().equals("Consulta")) {

                model.addAttribute("procesoJudicial", procesoJudicialService.getProcesoJudicialById(id));
                model.addAttribute("paises", paisesService.getAllPaises());

                return "procesosJudiciales/edit_procesosJudiciales";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

    @PostMapping("/procesosJudiciales/{id}")
    public String updateProcesoJudicial(@PathVariable int id, @ModelAttribute ProcesoJudicial procesoJudicial,
            Model model) {

        this.validarPerfil();
        ProcesoJudicial existingProcesoJudicial = procesoJudicialService.getProcesoJudicialById(id);
        existingProcesoJudicial.setCI_Id(id);
        existingProcesoJudicial.setCICodigoPais(procesoJudicial.getCICodigoPais());
        existingProcesoJudicial.setCVEstado(procesoJudicial.getCVEstado());
        // existingProcesoJudicial.setCDFechaApertura(procesoJudicial.getCDFechaApertura());
        existingProcesoJudicial.setCIPersonasImputadas(procesoJudicial.getCIPersonasImputadas());
        existingProcesoJudicial.setCVAgravantes(procesoJudicial.getCVAgravantes());
        existingProcesoJudicial.setCVTipoDelito(procesoJudicial.getCVTipoDelito());

        procesoJudicialService.updateProcesoJudicial(existingProcesoJudicial);

        bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                    this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en proceso judicial"));

        return "redirect:/procesosJudiciales";
    }

}
