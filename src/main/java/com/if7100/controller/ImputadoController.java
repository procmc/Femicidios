package com.if7100.controller;

import com.if7100.entity.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ImputadoController {

	private ImputadoService imputadoService;

	@Autowired
	private PaisesService paisesService;
	@Autowired
	private UsuarioRepository usuarioRepository;

	private UsuarioService usuarioService;

	// instancias para control de acceso
	private Perfil perfil;
	private PerfilService perfilService;
	// instancias para control de bitacora
	private BitacoraService bitacoraService;
	private Usuario usuario;

	private OrientacionSexualService orientacionSexualService;

	private OrientacionSexual orientacionSexual;
	private IdentidadGeneroService identidadGeneroService;

	private IdentidadGenero identidadGenero;

	private NivelEducativo nivelEducativo;

	private NivelEducativoService nivelEducativoService;

	private SituacionJuridica situacionJuridica;

	private SituacionJuridicaService situacionJuridicaService;

	private Organismo organismo;

	private OrganismoService organismoService;

	public ImputadoController(BitacoraService bitacoraService, PaisesService paisesService,
			ImputadoService imputadoService, PerfilService perfilService, UsuarioRepository usuarioRepository,
			IdentidadGeneroService identidadGeneroService, OrientacionSexualService orientacionSexualService,
			NivelEducativoService nivelEducativoService, SituacionJuridicaService situacionJuridicaService,
			OrganismoService organismoService) {
		super();
		this.imputadoService = imputadoService;
		this.paisesService = paisesService;
		this.perfilService = perfilService;
		this.usuarioRepository = usuarioRepository;
		this.bitacoraService = bitacoraService;
		this.identidadGeneroService = identidadGeneroService;
		this.orientacionSexualService = orientacionSexualService;
		this.nivelEducativoService = nivelEducativoService;
		this.situacionJuridicaService = situacionJuridicaService;
		this.organismoService = organismoService;
	}

	@GetMapping("/imputados/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException, java.io.IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=imputados_filtrados.pdf";
        response.setHeader(headerKey, headerValue);

        // Crear el documento PDF usando iText 7
        PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDoc = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDoc);

        // Agregar márgenes al documento (izquierda, derecha, arriba, abajo)
        document.setMargins(20, 20, 20, 20);

        // Agregar un título en negrita y centrado al documento
        Paragraph title = new Paragraph("Reporte de imputado")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18)
                .setBold();
        document.add(title);

        // Agregar un subtítulo
        Paragraph subTitle = new Paragraph("Imputado filtrados por país")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12)
                .setItalic();
        document.add(subTitle);

        // Espacio después del título
        document.add(new Paragraph("\n"));

        // Crear una tabla con columnas ajustadas dinámicamente
        Table table = new Table(UnitValue.createPercentArray(new float[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 2 }));
        table.setWidth(UnitValue.createPercentValue(100)); // Ajustar al 100% del ancho de la página

        // Agregar encabezado de tabla con estilo
        String[] headers = { "ID", "DNI", "Nombre", "Edad", "Sexo",
		"Orientación sexual", "Nacionalidad", "Ocupación", "Situación juridica", "Antecedentes" };

        for (String header : headers) {
            table.addHeaderCell(new Cell().add(new Paragraph(header).setBold())
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY));
        }

        // Obtener la lista de imputados filtrados por país
        Integer codigoPaisUsuario = this.usuario.getCodigoPais();
        List<Imputado> imputados = imputadoService.findByCodigoPais(codigoPaisUsuario);

        // Recorrer los imputados y agregarlos a la tabla
        for (Imputado imputado : imputados) {

            table.addCell(new Cell().add(new Paragraph(String.valueOf(imputado.getCI_Id())))
                    .setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add(new Paragraph(imputado.getCVDni()))
                    .setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add(new Paragraph(imputado.getCVNombre()))
                    .setTextAlignment(TextAlignment.CENTER));

			table.addCell(new Cell().add(new Paragraph(imputado.getCIEdad()))
                    .setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add(new Paragraph(String.valueOf(imputado.getCVSexo())))
                    .setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add(new Paragraph(String.valueOf(imputado.getCVOrientacionSexual())))//
                    .setTextAlignment(TextAlignment.CENTER));
          
			table.addCell(new Cell().add(new Paragraph(imputado.getCVNacionalidad()))
                    .setTextAlignment(TextAlignment.CENTER));

            // Continuar con el resto de los datos
            table.addCell(new Cell().add(new Paragraph(imputado.getCVOcupacion()))
                    .setTextAlignment(TextAlignment.CENTER));

			table.addCell(new Cell().add(new Paragraph(imputado.getCVSituacionJuridica()))
                    .setTextAlignment(TextAlignment.CENTER));
					
			table.addCell(new Cell().add(new Paragraph(imputado.getCVAntecedentes()))
                    .setTextAlignment(TextAlignment.CENTER));
        }

        // Asegurar que la tabla ocupe el espacio disponible sin distorsionarse
        table.setAutoLayout();

        // Agregar la tabla al documento
        document.add(table);

        // Cerrar el documento
        document.close();
    }

	@GetMapping("/imputados/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException, java.io.IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		this.validarPerfil();

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=imputados_filtrados.xlsx";
		response.setHeader(headerKey, headerValue);

		// Crear un nuevo libro de trabajo de Excel
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Imputados Filtrados");

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
		String[] headers = { "ID", "DNI", "Nombre", "Género", "Orientacion sexual",
				"Sexo", "Edad", "Lugar de nacimiento", "Nacionalidad", "Educación", "Ocupación", "Domicilio",
				"Lugar de residencia",
				"Condicion migratoria", "Etnia", "Situación juridica", "Estado conyugal",
				"Permiso de portación de armas", "Pertenencia de fuerza de seguridad",
				"Antecedentes", "Suicidio", "Organismo generador", "País" };
		for (int i = 0; i < headers.length; i++) {
			XSSFCell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(headerStyle); // Aplicar estilo de encabezado
		}

		// Obtener la lista de imputados filtrados por país
		Integer codigoPaisUsuario = this.usuario.getCodigoPais();
		List<Imputado> imputados = imputadoService.findByCodigoPais(codigoPaisUsuario);
		Paises pais = paisesService.getPaisByID(codigoPaisUsuario);

		// Rellenar las filas con los datos
		int rowNum = 1;
		for (Imputado imputado : imputados) {
			XSSFRow row = sheet.createRow(rowNum++);

			// ID
			row.createCell(0).setCellValue(imputado.getCI_Id());
			row.getCell(0).setCellStyle(cellStyle); // Aplicar estilo de celda

			// Tipo de Víctima
			row.createCell(1).setCellValue(imputado.getCVDni());
			row.getCell(1).setCellStyle(cellStyle);

			// Tipo de Relación
			row.createCell(2).setCellValue(imputado.getCVNombre());
			row.getCell(2).setCellStyle(cellStyle);

			// Modalidad
			row.createCell(3).setCellValue(imputado.getCVGenero());
			row.getCell(3).setCellStyle(cellStyle);

			// Agresión Sexual
			row.createCell(4).setCellValue(imputado.getCVOrientacionSexual());
			row.getCell(4).setCellStyle(cellStyle);

			// Denuncia previa
			row.createCell(5).setCellValue(String.valueOf(imputado.getCVSexo()));
			row.getCell(5).setCellStyle(cellStyle);

			// Generador
			row.createCell(6).setCellValue(imputado.getCIEdad());
			row.getCell(6).setCellStyle(cellStyle);

			// Lugar de nacimiento
			row.createCell(7).setCellValue(imputado.getCVLugarNacimiento());
			row.getCell(7).setCellStyle(cellStyle);

			// Proceso Judicial
			row.createCell(8).setCellValue(imputado.getCVNacionalidad());
			row.getCell(8).setCellStyle(cellStyle);

			// País
			row.createCell(9).setCellValue(imputado.getCVEducacion());
			row.getCell(9).setCellStyle(cellStyle);

			// ocupacion
			row.createCell(10).setCellValue(imputado.getCVOcupacion());
			row.getCell(10).setCellStyle(cellStyle);

			// Observaciones
			row.createCell(11).setCellValue(imputado.getCVDomicilio());
			row.getCell(11).setCellStyle(cellStyle);

			// Lugar de residencia
			row.createCell(12).setCellValue(imputado.getCVLugarResidencia());
			row.getCell(12).setCellStyle(cellStyle);

			// Condicion migratoria
			row.createCell(13).setCellValue(imputado.getCVCondicionMigratoria());
			row.getCell(13).setCellStyle(cellStyle);

			// Etnia
			row.createCell(14).setCellValue(imputado.getCVEtnia());
			row.getCell(14).setCellStyle(cellStyle);

			// Situacion juridica
			row.createCell(15).setCellValue(imputado.getCVSituacionJuridica());
			row.getCell(15).setCellStyle(cellStyle);

			// Estado conyugal
			row.createCell(16).setCellValue(imputado.getCVEstadoConyugal());
			row.getCell(16).setCellStyle(cellStyle);

			// Permiso de armas
			row.createCell(17).setCellValue(imputado.getCVPermisoPortacionArmas());
			row.getCell(17).setCellStyle(cellStyle);

			// Pertenencia fuerza seguridad
			row.createCell(18).setCellValue(imputado.getCVPertenenciaFuerzaSeguridad());
			row.getCell(18).setCellStyle(cellStyle);

			// Situacion juridica
			row.createCell(19).setCellValue(imputado.getCVAntecedentes());
			row.getCell(19).setCellStyle(cellStyle);

			// Suicidio
			row.createCell(20).setCellValue(imputado.getCVSuicidio());
			row.getCell(20).setCellStyle(cellStyle);

			// Organismo generador
			row.createCell(21).setCellValue(imputado.getCVGenerador());
			row.getCell(21).setCellStyle(cellStyle);

			// Pais
			row.createCell(22).setCellValue(pais.getSpanish());
			row.getCell(22).setCellStyle(cellStyle);

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

	@GetMapping("/imputados")
	public String ListImputados(Model model) {

		return "redirect:/imputado/1";
	}

	@GetMapping("/imputado/{pg}")
	public String listImputado(Model model, @PathVariable Integer pg) {
		this.validarPerfil();

		// Obtener el código de país del usuario logueado
		Integer codigoPaisUsuarioLogueado = this.usuario.getCodigoPais();

		// Filtrar imputados por el código de país del usuario logueado
		List<Imputado> imputadosFiltrados = imputadoService.findByCodigoPais(codigoPaisUsuarioLogueado);

		int numeroTotalElementos = imputadosFiltrados.size();

		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		int tamanoPagina = pageable.getPageSize();
		int numeroPagina = pageable.getPageNumber();

		List<Imputado> imputadosPaginados = imputadosFiltrados.stream()
				.skip((long) numeroPagina * tamanoPagina)
				.limit(tamanoPagina)
				.collect(Collectors.toList());

		List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
				.boxed()
				.toList();

		// Enviar datos al modelo
		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("imputados", imputadosPaginados);

		return "imputados/imputados";
	}

	@GetMapping("/imputados/new")
	public String CreateUsuarioForm(Model model) {

		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {
				// String descripcion="Elimino en XXX/Crea en XXX/ Actualiza en XXX";
				// Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(),
				// this.usuario.getCVNombre(),this.perfil.getCVRol(),descripcion);
				// bitacoraService.saveBitacora(bitacora);
				model.addAttribute("paises", paisesService.getAllPaises());
				model.addAttribute("orientacionSexual", orientacionSexualService.getAllOrientacionesSexuales());
				model.addAttribute("identidadGenero", identidadGeneroService.getAllIdentidadGenero());
				model.addAttribute("nivelEducativo", nivelEducativoService.getAllNivelEducativo());
				model.addAttribute("situacionJuridica", situacionJuridicaService.getAllSituacionJuridica());
				model.addAttribute("listaOrganismo", organismoService.getAllOrganismos());
				model.addAttribute("imputado", new Imputado());

				// Obtener lista de países y enviarla al modelo
				List<Paises> paises = paisesService.getAllPaises();
				model.addAttribute("paises", paises);

				return "imputados/create_imputado";

			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/imputados")
	public String SaveImputado(@ModelAttribute Imputado imputado, Model model) {
		try {
			imputadoService.saveImputado(imputado);
			bitacoraService.saveBitacora(new Bitacora(this.usuario.getCI_Id(),
					this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en Imputado"));
			return "redirect:/imputados";
		} catch (DataIntegrityViolationException e) {
			String mensaje = "No se puede guardar el imputado debido a un error de integridad de datos.";
			model.addAttribute("error_message", mensaje);
			model.addAttribute("error", true);
			return CreateUsuarioForm(model);
		}
	}

	@GetMapping("/imputados/{id}")
	public String deleteUsuario(@PathVariable int id) {

		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {

				imputadoService.deleteImputadoById(id);
				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCI_Id(),
						this.usuario.getCVNombre(), this.perfil.getCVRol(), "Eliminó en Imputado"));
				return "redirect:/imputados";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@GetMapping("/imputados/edit/{id}")
	public String editImputadosForm(HttpServletResponse response, Model model, @PathVariable int id) {

		try {
			this.validarPerfil();
			// Evitar que el navegador cachee la página de edición
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "0");

			if (!this.perfil.getCVRol().equals("Consulta")) {

				List<Paises> paises = paisesService.getAllPaises(); // Obtiene la lista de países
				model.addAttribute("paises", paises); // Envía la lista de países al modelo

				model.addAttribute("paises", paisesService.getAllPaises());
				model.addAttribute("orientacionSexual", orientacionSexualService.getAllOrientacionesSexuales());
				model.addAttribute("identidadGenero", identidadGeneroService.getAllIdentidadGenero());
				model.addAttribute("nivelEducativo", nivelEducativoService.getAllNivelEducativo());
				model.addAttribute("situacionJuridica", situacionJuridicaService.getAllSituacionJuridica());
				model.addAttribute("listaOrganismo", organismoService.getAllOrganismos());
				model.addAttribute("imputado", imputadoService.getImputadoById(id));

				return "imputados/edit_imputado";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}

	}

	@PostMapping("/imputados/{id}")
	public String updateUsuario(@PathVariable int id, @ModelAttribute Imputado imputado, Model model) {
		Imputado existingImputado = imputadoService.getImputadoById(id);

		existingImputado.setCodigoPais(imputado.getCodigoPais());// actualiza codigo pais
		existingImputado.setCVDni(imputado.getCVDni());
		existingImputado.setCVGenero(imputado.getCVGenero());
		existingImputado.setCVNombre(imputado.getCVNombre());
		existingImputado.setCVOrientacionSexual(imputado.getCVOrientacionSexual());
		existingImputado.setCVSexo(imputado.getCVSexo());
		existingImputado.setCVLugarNacimiento(imputado.getCVLugarNacimiento());
		existingImputado.setCIEdad(imputado.getCIEdad());
		existingImputado.setCVNacionalidad(imputado.getCVNacionalidad());
		existingImputado.setCVAntecedentes(imputado.getCVAntecedentes());
		existingImputado.setCVEducacion(imputado.getCVEducacion());
		existingImputado.setCVEstadoConyugal(imputado.getCVEstadoConyugal());
		existingImputado.setCVLugarResidencia(imputado.getCVLugarResidencia());
		existingImputado.setCVCondicionMigratoria(imputado.getCVCondicionMigratoria());
		existingImputado.setCVEtnia(imputado.getCVEtnia());
		existingImputado.setCVGenerador(imputado.getCVGenerador());
		existingImputado.setCVOcupacion(imputado.getCVOcupacion());
		existingImputado.setCVSituacionJuridica(imputado.getCVSituacionJuridica());
		existingImputado.setCVPertenenciaFuerzaSeguridad(imputado.getCVPertenenciaFuerzaSeguridad());
		existingImputado.setCVPermisoPortacionArmas(imputado.getCVPermisoPortacionArmas());
		existingImputado.setCVSuicidio(imputado.getCVSuicidio());
		existingImputado.setCVDomicilio(imputado.getCVDomicilio());

		imputadoService.updateImputado(existingImputado);
		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCI_Id(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualizó en Imputado"));
		return "redirect:/imputados";
	}

}
