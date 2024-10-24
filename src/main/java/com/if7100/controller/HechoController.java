package com.if7100.controller;

import com.if7100.entity.Hecho;
import com.if7100.entity.Imputado;
import com.if7100.entity.Paises;
import com.if7100.entity.Perfil;
import com.if7100.entity.ProcesoJudicial;
import com.if7100.entity.TipoVictima;
import com.if7100.entity.Usuario;
import com.if7100.entity.Victima;
import com.if7100.entity.Bitacora;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.*;
import com.if7100.util.ExportExcelUtil;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;

import java.util.stream.Collectors;

@Controller
public class HechoController {

    private HechoService hechoService;

    private PaisesService paisesService;
    private ModalidadService modalidadService;
    private TipoVictimaService tipoVictimaService;
    private TipoRelacionService tipoRelacionService;

    private VictimaService victimaService;

    private ProcesoJudicialService procesoJudicialService;

    private OrganismoService organismoService;

    // instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private Perfil perfil;
    private PerfilService perfilService;
    // instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;

    // public HechoController(HechoService hechoService) {
    // super();
    // this.hechoService = hechoService;
    // }

    public HechoController(HechoService hechoService, PaisesService paisesService, ModalidadService modalidadService,
            TipoVictimaService tipoVictimaService, TipoRelacionService tipoRelacionService,
            VictimaService victimaService, ProcesoJudicialService procesoJudicialService,
            OrganismoService organismoService, PerfilService perfilService, UsuarioRepository usuarioRepository,
            BitacoraService bitacoraService) {
        super();
        this.hechoService = hechoService;
        this.paisesService = paisesService;
        this.modalidadService = modalidadService;
        this.tipoVictimaService = tipoVictimaService;
        this.tipoRelacionService = tipoRelacionService;
        this.victimaService = victimaService;
        this.procesoJudicialService = procesoJudicialService;
        this.organismoService = organismoService;
        this.perfilService = perfilService;
        this.usuarioRepository = usuarioRepository;
        this.bitacoraService = bitacoraService;
    }

    @GetMapping("/hechos/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException, java.io.IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=hechos_filtrados.pdf";
        response.setHeader(headerKey, headerValue);

        // Crear el documento PDF usando iText 7
        PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDoc = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDoc);

        // Agregar márgenes al documento (izquierda, derecha, arriba, abajo)
        document.setMargins(20, 20, 20, 20);

        // Agregar un título en negrita y centrado al documento
        Paragraph title = new Paragraph("Reporte de Hechos")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18)
                .setBold();
        document.add(title);

        // Agregar un subtítulo
        Paragraph subTitle = new Paragraph("Hechos filtrados por país")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12)
                .setItalic();
        document.add(subTitle);

        // Espacio después del título
        document.add(new Paragraph("\n"));

        // Crear una tabla con columnas ajustadas dinámicamente
        Table table = new Table(UnitValue.createPercentArray(new float[] { 1, 1, 1, 1, 1, 1, 1, 1, 2 }));
        table.setWidth(UnitValue.createPercentValue(100)); // Ajustar al 100% del ancho de la página

        // Agregar encabezado de tabla con estilo
        String[] headers = { "ID", "Tipo de Víctima", "Tipo de Relación", "Modalidad", "Agresión Sexual",
                "Victima", "Proceso Judicial", "Fecha", "Observaciones" };
        for (String header : headers) {
            table.addHeaderCell(new Cell().add(new Paragraph(header).setBold())
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY));
        }

        // Obtener la lista de hechos filtrados por país
        Integer codigoPaisUsuario = this.usuario.getCodigoPais();
        List<Hecho> hechos = hechoService.findByCodigoPais(codigoPaisUsuario);

        // Recorrer los hechos y agregarlos a la tabla
        for (Hecho hecho : hechos) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(hecho.getCI_Id())))
                    .setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(tipoVictimaService.getTipoVictimaById(hecho.getCITipoVictima()).getCVTitulo()))
                    .setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(tipoRelacionService.getTipoRelacionById(hecho.getCITipoRelacion()).getCVTitulo()))
                    .setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(modalidadService.getModalidadById(hecho.getCIModalidad()).getCVTitulo()))
                    .setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(hecho.getCVAgresionSexual())))
                    .setTextAlignment(TextAlignment.CENTER));
          
            // Victima: Ajustar el contenido si es un objeto completo
            String victimaNombre = hecho.getVictima() != null ? hecho.getVictima().getCVDNI() : "No Disponible";
            table.addCell(new Cell().add(new Paragraph(victimaNombre))
                    .setTextAlignment(TextAlignment.CENTER));

            // Continuar con el resto de los datos
            table.addCell(new Cell().add(new Paragraph(String.valueOf(hecho.getProcesoJudicial().getCVEstado())))
                    .setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(hecho.getCDFecha().toString()))
                    .setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(hecho.getCVDetalles()))
                    .setTextAlignment(TextAlignment.LEFT));
        }

        // Asegurar que la tabla ocupe el espacio disponible sin distorsionarse
        table.setAutoLayout();

        // Agregar la tabla al documento
        document.add(table);

        // Cerrar el documento
        document.close();
    }


    @GetMapping("/hechos/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException, java.io.IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=hechos_filtrados.xlsx";
        response.setHeader(headerKey, headerValue);

        // Crear un nuevo libro de trabajo de Excel
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Hechos Filtrados");

        this.validarPerfil();

        
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
        String[] headers = { "ID", "Tipo de Víctima", "Tipo de Relación", "Modalidad", "Agresión Sexual",
                "Denuncia previa", "Generador",
                "Victima", "Proceso Judicial", "País", "Fecha", "Detalles" };
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle); // Aplicar estilo de encabezado
        }

        // Obtener la lista de hechos filtrados por país
        Integer codigoPaisUsuario = this.usuario.getCodigoPais();
        List<Hecho> hechos = hechoService.findByCodigoPais(codigoPaisUsuario);
        Paises pais = paisesService.getPaisByID(codigoPaisUsuario);

        // Rellenar las filas con los datos
        int rowNum = 1;
        for (Hecho hecho : hechos) {
            XSSFRow row = sheet.createRow(rowNum++);

            // ID
            row.createCell(0).setCellValue(hecho.getCI_Id());
            row.getCell(0).setCellStyle(cellStyle); // Aplicar estilo de celda

            // Tipo de Víctima
            row.createCell(1).setCellValue(tipoVictimaService.getTipoVictimaById(hecho.getCITipoVictima()).getCVTitulo());
            row.getCell(1).setCellStyle(cellStyle);

            // Tipo de Relación
            row.createCell(2).setCellValue(tipoRelacionService.getTipoRelacionById(hecho.getCITipoRelacion()).getCVTitulo());
            row.getCell(2).setCellStyle(cellStyle);

            // Modalidad
            row.createCell(3).setCellValue(modalidadService.getModalidadById(hecho.getCIModalidad()).getCVTitulo());
            row.getCell(3).setCellStyle(cellStyle);

            // Agresión Sexual
            row.createCell(4).setCellValue(String.valueOf(hecho.getCVAgresionSexual()));
            row.getCell(4).setCellStyle(cellStyle);

            // Denuncia previa
            row.createCell(5).setCellValue(String.valueOf(hecho.getCVDenunciaPrevia()));
            row.getCell(5).setCellStyle(cellStyle);

            // Generador
            row.createCell(6).setCellValue(organismoService.getOrganismoById(hecho.getCIIdGenerador()).getCVNombre());
            row.getCell(6).setCellStyle(cellStyle);

            // Victima
            String victimaNombre = hecho.getVictima() != null ? hecho.getVictima().getCVDNI() : "No Disponible";
            row.createCell(7).setCellValue(victimaNombre);
            row.getCell(7).setCellStyle(cellStyle);

            // Proceso Judicial
            row.createCell(8).setCellValue(hecho.getProcesoJudicial().getCVEstado());
            row.getCell(8).setCellStyle(cellStyle);

            // País
            row.createCell(9).setCellValue(pais.getSpanish());
            row.getCell(9).setCellStyle(cellStyle);

            // Fecha (formato de fecha)
            XSSFCell fechaCell = row.createCell(10);
            fechaCell.setCellValue(hecho.getCDFecha());
            fechaCell.setCellStyle(dateCellStyle);

            // Observaciones
            row.createCell(11).setCellValue(hecho.getCVDetalles());
            row.getCell(11).setCellStyle(cellStyle);
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

    @GetMapping("/hechos")
    public String listHechos(Model model) {
        return "redirect:/hecho/1";
    }

    @GetMapping("/hecho/{pg}")
    public String listHecho(Model model, @PathVariable Integer pg) {

        this.validarPerfil();

        Integer codigoPaisUsuario = this.usuario.getCodigoPais();
        List<Hecho> hechosFiltrados = hechoService.findByCodigoPais(codigoPaisUsuario);

        // Buscar el país por el código del país almacenado en Hecho
        Paises pais = paisesService.getPaisByID(codigoPaisUsuario);

        int numeroTotalElementos = hechosFiltrados.size();

        Pageable pageable = initPages(pg, 5, numeroTotalElementos);

        int tamanoPagina = pageable.getPageSize();
        int numeroPagina = pageable.getPageNumber();

        List<Hecho> hechosPaginados = hechosFiltrados.stream()
                .skip((long) numeroPagina * tamanoPagina)
                .limit(tamanoPagina)
                .collect(Collectors.toList());

        List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
                .boxed()
                .toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("hechos", hechosPaginados);
        model.addAttribute("nombrePais", pais.getSpanish());
        model.addAttribute("modalidades", hechoService.getAllModalidadesPage(pageable));
        model.addAttribute("organismos", hechoService.getAllOrganismosPage(pageable));
        model.addAttribute("tipoVictimas", hechoService.getAllTipoVictimasPage(pageable));
        model.addAttribute("tipoRelaciones", hechoService.getAllTipoRelacionesPage(pageable));
        model.addAttribute("victimas", hechoService.getAllVictimasPage(pageable));
        model.addAttribute("procesosJudiciales", hechoService.getAllProcesosJudicialesPage(pageable));
        model.addAttribute("hechos", hechosPaginados);
        return "hechos/hechos";
    }

    @GetMapping("/hechos/new")
    public String createHechoForm(Model model) {

        try {
            this.validarPerfil();
            if (!this.perfil.getCVRol().equals("Consulta")) {
                Hecho hecho = new Hecho();
                model.addAttribute("hecho", hecho);
                modelAttributes(model);

                // Obtener lista de países y enviarla al modelo
                List<Paises> paises = paisesService.getAllPaises();
                model.addAttribute("paises", paises);

                return "hechos/create_hecho";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

    private void modelAttributes(Model model) {
        model.addAttribute("modalidad", modalidadService.getAllModalidades());
        model.addAttribute("tipoVictima", tipoVictimaService.getAllTipoVictimas());
        model.addAttribute("tipoRelacion", tipoRelacionService.getAllTipoRelaciones());
        model.addAttribute("victima", victimaService.getAllVictima());
        model.addAttribute("ProcesoJudicial", procesoJudicialService.getAllProcesosJudiciales());
        model.addAttribute("organismo", organismoService.getAllOrganismos());
        model.addAttribute("paises", paisesService.getAllPaises());
    }

    @PostMapping("/hechos")
    public String saveHecho(@ModelAttribute("hecho") Hecho hecho, Model model) {
        try {
            hechoService.saveHecho(hecho);

            String descripcion = "Creo en Hechos: " + hecho.getCI_Id();
            Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),
                    this.perfil.getCVRol(), descripcion);
            bitacoraService.saveBitacora(bitacora);
            return "redirect:/hechos";
        } catch (DataIntegrityViolationException e) {
            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return createHechoForm(model);
        }
    }

    @GetMapping("/hechos/{id}")
    public String deleteHecho(@PathVariable Integer id, Model model) {

        try {
            this.validarPerfil();
            if (!this.perfil.getCVRol().equals("Consulta")) {

                try {
                    hechoService.deleteHechoById(id);
                    String descripcion = "Elimino en Hechos: " + id;
                    Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),
                            this.perfil.getCVRol(), descripcion);
                    bitacoraService.saveBitacora(bitacora);
                } catch (DataIntegrityViolationException e) {

                    String mensaje = "Error, No se puede eliminar un hecho si tiene un lugar registrado";
                    model.addAttribute("error_message", mensaje);
                    model.addAttribute("error", true);
                    return listHecho(model, 1);
                }
                return "redirect:/hechos";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

    @GetMapping("/hechos/edit/{id}")
    public String editHechoForm(@PathVariable Integer id, Model model) {

        try {
            this.validarPerfil();
            if (!this.perfil.getCVRol().equals("Consulta")) {

                model.addAttribute("victima", victimaService.getAllVictima());
                model.addAttribute("ProcesoJudicial", procesoJudicialService.getAllProcesosJudiciales());

                model.addAttribute("hecho", hechoService.getHechoById(id));
                modelAttributes(model);
                return "hechos/edit_hecho";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

    @PostMapping("/hechos/{id}")
    public String updateHecho(@PathVariable Integer id, @ModelAttribute("hecho") Hecho hecho, Model model) {
        try {
            Hecho existingHecho = hechoService.getHechoById(id);
            String descripcion = "Actualizo en Hechos, de: " + existingHecho.getCI_Id() + " | a: " + id;
            existingHecho.setCI_Id(id);
            existingHecho.setCodigoPais(hecho.getCodigoPais());
            existingHecho.setCITipoVictima(hecho.getCITipoVictima());
            existingHecho.setCITipoRelacion(hecho.getCITipoRelacion());
            existingHecho.setCIModalidad(hecho.getCIModalidad());

            existingHecho.setProcesoJudicial(hecho.getProcesoJudicial());
            existingHecho.setVictima(hecho.getVictima());
            // existingHecho.setCIIdVictima(hecho.getCIIdVictima());
            // existingHecho.setCIIdProceso(hecho.getCIIdProceso());
            existingHecho.setCIIdGenerador(hecho.getCIIdGenerador());
            existingHecho.setCVAgresionSexual(hecho.getCVAgresionSexual());
            existingHecho.setCVDenunciaPrevia(hecho.getCVDenunciaPrevia());
            existingHecho.setCDFecha(hecho.getCDFecha());
            existingHecho.setCVDetalles(hecho.getCVDetalles());

            hechoService.updateHecho(existingHecho);

            Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),
                    this.perfil.getCVRol(), descripcion);
            bitacoraService.saveBitacora(bitacora);
            return "redirect:/hechos";
        } catch (DataIntegrityViolationException e) {
            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return editHechoForm(id, model);
        }
    }

}
