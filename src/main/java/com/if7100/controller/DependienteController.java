/**
 * 
 */
package com.if7100.controller;

import com.if7100.entity.*;
import com.if7100.service.BitacoraService;
import com.if7100.service.DependienteService;
import com.if7100.service.DependienteVictimaService;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.if7100.service.PerfilService;
import com.if7100.service.TipoRelacionFamiliarService;
import com.if7100.service.VictimaService;
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

import com.if7100.repository.DependienteVictimaRepository;

/**
 * @author Hadji
 *
 */

import com.if7100.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class DependienteController {
	@Autowired
	private DependienteVictimaService dependienteVictimaService;

	private DependienteService dependienteService;

	// instancias para control de acceso
	private UsuarioRepository usuarioRepository;
	private Perfil perfil;
	private PerfilService perfilService;
	// instancias para control de bitacora
	private BitacoraService bitacoraService;
	private VictimaService victimaService;

	private Usuario usuario;

	// Instancias el control de Nivel educativo
	private TipoRelacionFamiliarService tipoRelacionFamiliarService;

	// Constructor con todos las instancias
	public DependienteController(DependienteService dependienteService, UsuarioRepository usuarioRepository,
			PerfilService perfilService, BitacoraService bitacoraService,
			TipoRelacionFamiliarService tipoRelacionFamiliarService,
			VictimaService victimaservice) {
		super();
		this.dependienteService = dependienteService;
		this.usuarioRepository = usuarioRepository;
		this.perfilService = perfilService;
		this.bitacoraService = bitacoraService;
		this.tipoRelacionFamiliarService = tipoRelacionFamiliarService;
		this.victimaService = victimaservice;
	}

	@GetMapping("/dependiente/pdf")
	public void exportToPDF(HttpServletResponse response) throws IOException, java.io.IOException {
		response.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=dependientes_filtrados.pdf";
		response.setHeader(headerKey, headerValue);

		// Crear el documento PDF usando iText 7
		PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());
		PdfDocument pdfDoc = new PdfDocument(pdfWriter);
		Document document = new Document(pdfDoc);

		// Agregar márgenes al documento (izquierda, derecha, arriba, abajo)
		document.setMargins(20, 20, 20, 20);

		// Agregar un título en negrita y centrado al documento
		Paragraph title = new Paragraph("Reporte de dependiente")
				.setTextAlignment(TextAlignment.CENTER)
				.setFontSize(18)
				.setBold();
		document.add(title);

		// Agregar un subtítulo
		Paragraph subTitle = new Paragraph("Dependientes filtrados por país")
				.setTextAlignment(TextAlignment.CENTER)
				.setFontSize(12)
				.setItalic();
		document.add(subTitle);

		// Espacio después del título
		document.add(new Paragraph("\n"));

		// Crear una tabla con columnas ajustadas dinámicamente
		Table table = new Table(UnitValue.createPercentArray(new float[] { 1, 1, 1, 2 }));
		table.setWidth(UnitValue.createPercentValue(100)); // Ajustar al 100% del ancho de la página

		// Agregar encabezado de tabla con estilo
		String[] headers = { "ID", "DNI", "Tipo de relación familiar", "Victima" };

		for (String header : headers) {
			table.addHeaderCell(new Cell().add(new Paragraph(header).setBold())
					.setTextAlignment(TextAlignment.CENTER)
					.setBackgroundColor(ColorConstants.LIGHT_GRAY));
		}

		// Obtener la lista de imputados filtrados por país
		Integer codigoPaisUsuario = this.usuario.getCodigoPais();

		// Obtener la lista de dependientes filtrados por país de la víctima
		List<DependienteVictima> todasRelaciones = dependienteVictimaService.getAllDependienteVictima();
		List<DependienteVictima> relacionesFiltradas = todasRelaciones.stream()
				.filter(dv -> dv.getVictima().getCICodigoPais() == codigoPaisUsuario)
				.collect(Collectors.toList());

		List<Dependiente> dependientesFiltrados = relacionesFiltradas.stream()
				.map(DependienteVictima::getDependiente)
				.distinct() // Para evitar duplicados en caso de múltiples víctimas
				.collect(Collectors.toList());

		Map<Integer, Victima> dependienteVictimaMap = relacionesFiltradas.stream()
				.collect(Collectors.toMap(dv -> dv.getDependiente().getCI_Codigo(), DependienteVictima::getVictima));

		// Recorrer los imputados y agregarlos a la tabla
		for (Dependiente dependiente : dependientesFiltrados) {

			table.addCell(new Cell().add(new Paragraph(String.valueOf(dependiente.getCI_Codigo())))
					.setTextAlignment(TextAlignment.CENTER));

			table.addCell(new Cell().add(new Paragraph(dependiente.getCVDNI()))
					.setTextAlignment(TextAlignment.CENTER));

			table.addCell(new Cell().add(new Paragraph(dependiente.getTipoRelacionFamiliar().getNombre()))
					.setTextAlignment(TextAlignment.CENTER));

			Victima victima = dependienteVictimaMap.get(dependiente.getCI_Codigo());
				if (victima != null) {
					table.addCell(new Cell().add(new Paragraph(victima.getCVNombre()))
					.setTextAlignment(TextAlignment.CENTER));
				} else {
					table.addCell(new Cell().add(new Paragraph("Sin víctima"))
					.setTextAlignment(TextAlignment.CENTER));
				}

		}

		// Asegurar que la tabla ocupe el espacio disponible sin distorsionarse
		table.setAutoLayout();

		// Agregar la tabla al documento
		document.add(table);

		// Cerrar el documento
		document.close();
	}

	@GetMapping("/dependiente/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException, java.io.IOException {

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=dependientes_filtrados.xlsx";
		response.setHeader(headerKey, headerValue);

		// Crear un nuevo libro de trabajo de Excel
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Dependientes Filtrados");

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
		String[] headers = { "ID", "DNI", "Tipo de relación familiar", "Victima" };

		for (int i = 0; i < headers.length; i++) {
			XSSFCell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(headerStyle); // Aplicar estilo de encabezado
		}

		Integer codigoPaisUsuario = this.usuario.getCodigoPais();

		// Obtener la lista de dependientes filtrados por país de la víctima
		List<DependienteVictima> todasRelaciones = dependienteVictimaService.getAllDependienteVictima();
		List<DependienteVictima> relacionesFiltradas = todasRelaciones.stream()
				.filter(dv -> dv.getVictima().getCICodigoPais() == codigoPaisUsuario)
				.collect(Collectors.toList());

		List<Dependiente> dependientesFiltrados = relacionesFiltradas.stream()
				.map(DependienteVictima::getDependiente)
				.distinct() // Para evitar duplicados en caso de múltiples víctimas
				.collect(Collectors.toList());

		Map<Integer, Victima> dependienteVictimaMap = relacionesFiltradas.stream()
				.collect(Collectors.toMap(dv -> dv.getDependiente().getCI_Codigo(), DependienteVictima::getVictima));

		// Rellenar las filas con los datos
		int rowNum = 1;
		for (Dependiente dependiente : dependientesFiltrados) {
			XSSFRow row = sheet.createRow(rowNum++);

			// ID
			row.createCell(0).setCellValue(dependiente.getCI_Codigo());
			row.getCell(0).setCellStyle(cellStyle); // Aplicar estilo de celda

			// DNI
			row.createCell(1).setCellValue(dependiente.getCVDNI());
			row.getCell(1).setCellStyle(cellStyle);

			// Tipo de Relación familiar
			row.createCell(2).setCellValue(dependiente.getTipoRelacionFamiliar().getNombre());
			row.getCell(2).setCellStyle(cellStyle);

			Victima victima = dependienteVictimaMap.get(dependiente.getCI_Codigo());
			if (victima != null) {
				row.createCell(3).setCellValue(victima.getCVNombre());
			} else {
				row.createCell(3).setCellValue("Sin víctima");
			}
			row.getCell(3).setCellStyle(cellStyle);

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

	private void modelAttributes(Model model) {

		model.addAttribute("tipoRelacionFamiliar", tipoRelacionFamiliarService.getAllTipoRelacionFamiliar());

	}

	@GetMapping("/dependientes")
	public String listdependientes(Model model) {
		return "redirect:/dependiente/1";
	}

	@GetMapping("/dependiente/{pg}")
	public String listDependiente(Model model, @PathVariable Integer pg) {
		// Validar el perfil, asumo que tienes la lógica correcta aquí
		this.validarPerfil();

		// Obtener el usuario logueado (ajusta según tu lógica)
		int codigoPaisUsuario = this.usuario.getCodigoPais(); // Obtenemos el código de país del usuario logueado

		// Obtener todas las relaciones dependiente-victima y filtrar según el código
		// del país de la víctima
		List<DependienteVictima> todasRelaciones = dependienteVictimaService.getAllDependienteVictima();
		List<DependienteVictima> relacionesFiltradas = todasRelaciones.stream()
				.filter(dv -> dv.getVictima().getCICodigoPais() == codigoPaisUsuario)
				.collect(Collectors.toList());

		// Filtrar los dependientes basados en las relaciones filtradas
		List<Dependiente> dependientesFiltrados = relacionesFiltradas.stream()
				.map(DependienteVictima::getDependiente)
				.distinct() // Para evitar duplicados en caso de múltiples víctimas
				.collect(Collectors.toList());

		// Obtener la cantidad total de elementos para la paginación
		int numeroTotalElementos = dependientesFiltrados.size();

		// Crear la paginación utilizando el servicio
		Pageable pageable = initPages(pg, 5, numeroTotalElementos);
		List<Dependiente> dependientesPaginados = dependientesFiltrados.stream()
				.skip((long) pageable.getPageNumber() * pageable.getPageSize())
				.limit(pageable.getPageSize())
				.collect(Collectors.toList());

		// Crear un mapa que relacione el ID del dependiente con la víctima
		// correspondiente
		Map<Integer, Victima> dependienteVictimaMap = relacionesFiltradas.stream()
				.collect(Collectors.toMap(dv -> dv.getDependiente().getCI_Codigo(), DependienteVictima::getVictima));

		// Añadir atributos al modelo
		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas",
				IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / pageable.getPageSize()))
						.boxed().toList());
		model.addAttribute("dependientes", dependientesPaginados); // Dependientes filtrados y paginados
		model.addAttribute("dependienteVictimaMap", dependienteVictimaMap); // Mapa de dependientes y sus víctimas

		// Retornar la vista de dependientes
		return "dependientes/dependiente";
	}

	@GetMapping("/dependientes/new")
	public String createdependienteForm(Model model) {

		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {

				Dependiente dependiente = new Dependiente();

				// Obtener todas las víctimas y agregarlas al modelo
				List<Victima> victimas = victimaService.getAllVictima();
				model.addAttribute("victimas", victimas);

				model.addAttribute("dependiente", dependiente);
				modelAttributes(model);
				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCI_Id(),
						this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en Dependiente"));
				return "dependientes/create_dependiente";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/dependientes")
	public String saveDependiente(@ModelAttribute Dependiente dependiente,
			@RequestParam("victima") Integer idVictima,
			Model model) {

		Victima victima = victimaService.getVictimaById(idVictima);

		if (victima != null) {

			dependienteService.saveDependiente(dependiente);

			DependienteVictima dependienteVictima = new DependienteVictima();
			dependienteVictima.setDependiente(dependiente);
			dependienteVictima.setVictima(victima);

			dependienteService.saveDependienteVictima(dependienteVictima);

			bitacoraService.saveBitacora(new Bitacora(this.usuario.getCI_Id(),
					this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea Dependiente con asociación a víctima"));

		} else {
			model.addAttribute("error", "La víctima seleccionada no es válida.");
			return "dependientes/create_dependiente";
		}

		return "redirect:/dependientes";
	}

	@GetMapping("/dependientes/{Id}")
	public String deleteDependiente(@PathVariable Integer Id) {

		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {

				String descripcion = "Elimino un dependiente(familiar)";
				Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), descripcion,
						this.perfil.getCVRol());
				bitacoraService.saveBitacora(bitacora);

				dependienteVictimaService.deleteByDependienteId(Id);

				dependienteService.deleteDependienteById(Id);

				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCI_Id(),
						this.usuario.getCVNombre(), this.perfil.getCVRol(), "Eliminó en Denpendiente(familiar)"));
				return "redirect:/dependientes";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@GetMapping("/dependientes/edit/{id}")
	public String editdependienteForm(@PathVariable Integer id, Model model) {

		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {

				model.addAttribute("dependiente", dependienteService.getDependienteById(id));

				// Obtener la relación de dependiente-víctima desde la tabla intermedia
				List<DependienteVictima> dependienteVictimaRelaciones = dependienteVictimaService
						.findBydependiente(dependienteService.getDependienteById(id));

				// Obtener todas las víctimas y agregarlas al modelo
				List<Victima> victimas = victimaService.getAllVictima();

				// Extraer las víctimas relacionadas con el dependiente
				List<Victima> victimasRelacionadas = dependienteVictimaRelaciones.stream()
						.map(DependienteVictima::getVictima)
						.collect(Collectors.toList());

				model.addAttribute("dependiente", dependienteService.getDependienteById(id));
				model.addAttribute("victimas", victimas);
				model.addAttribute("victimasRelacionadas", victimasRelacionadas);

				modelAttributes(model);
				return "dependientes/edit_dependiente";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al actualizar: " + e.getMessage());
			return "SinAcceso";
		}
	}

	@PostMapping("/dependientes/{id}")
	public String updatedependiente(@PathVariable Integer id,
			@ModelAttribute Dependiente dependiente, @RequestParam("victima") Integer idVictima,
			Model model) {

		Dependiente existingDependiente = dependienteService.getDependienteById(id);
		existingDependiente.setCI_Codigo(id);
		existingDependiente.setCVDNI(dependiente.getCVDNI());
		existingDependiente.setTipoRelacionFamiliar(dependiente.getTipoRelacionFamiliar());

		dependienteService.updateDependiente(existingDependiente);

		Victima victima = victimaService.getVictimaById(idVictima);

		DependienteVictima existingDependienteVictima = dependienteVictimaService.findBydependiente(existingDependiente)
				.get(0);
		existingDependienteVictima.setDependiente(existingDependiente);
		existingDependienteVictima.setVictima(victima);

		dependienteService.updateDependienteVictima(existingDependienteVictima);

		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCI_Id(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualizó en Dependiente"));

		return "redirect:/dependientes";

	}

}
