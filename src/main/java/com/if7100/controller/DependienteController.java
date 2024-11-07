/**
 * 
 */
package com.if7100.controller;

import com.if7100.entity.*;
import com.if7100.service.BitacoraService;
import com.if7100.service.DependienteService;

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
import com.if7100.service.UsuarioPerfilService;
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

import com.if7100.repository.UsuarioPerfilRepository;

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

	private DependienteService dependienteService;

	// instancias para control de acceso
	private UsuarioRepository usuarioRepository;
	private UsuarioPerfilRepository usuarioPerfilRepository;
	private UsuarioPerfilService usuarioPerfilService;
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
			VictimaService victimaservice, UsuarioPerfilService usuarioPerfilService, UsuarioPerfilRepository usuarioPerfilRepository) {
		super();
		this.dependienteService = dependienteService;
		this.usuarioRepository = usuarioRepository;
		this.perfilService = perfilService;
		this.bitacoraService = bitacoraService;
		this.tipoRelacionFamiliarService = tipoRelacionFamiliarService;
		this.victimaService = victimaservice;
		this.usuarioPerfilService = usuarioPerfilService;
		this.usuarioPerfilRepository = usuarioPerfilRepository;
	}

	@GetMapping("/dependiente/pdf")
	public void exportToPDF(HttpServletResponse response) throws IOException, java.io.IOException {

		this.validarPerfil();
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

		Organizacion organizacion = this.usuario.getOrganizacion();

		List<Dependiente> dependientesFiltrados = dependienteService
				.getDependientesByCodigoPais(organizacion.getCodigoPais());

		// Recorrer los imputados y agregarlos a la tabla
		for (Dependiente dependiente : dependientesFiltrados) {

			table.addCell(new Cell().add(new Paragraph(String.valueOf(dependiente.getCI_Codigo())))
					.setTextAlignment(TextAlignment.CENTER));

			table.addCell(new Cell().add(new Paragraph(dependiente.getCVDNI()))
					.setTextAlignment(TextAlignment.CENTER));

			table.addCell(new Cell().add(new Paragraph(dependiente.getTipoRelacionFamiliar().getNombre()))
					.setTextAlignment(TextAlignment.CENTER));

			table.addCell(new Cell().add(new Paragraph(dependiente.getVictima().getCVDNI()))
					.setTextAlignment(TextAlignment.CENTER));

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

		this.validarPerfil();
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

		Integer codigoPaisUsuario = this.usuario.getOrganizacion().getCodigoPais();

		List<Dependiente> dependientesFiltrados = dependienteService
				.getDependientesByCodigoPais(codigoPaisUsuario);

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

			// victima
			row.createCell(2).setCellValue(dependiente.getVictima().getCVDNI());
			row.getCell(2).setCellStyle(cellStyle);

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

			List<UsuarioPerfil> usuarioPerfiles = usuarioPerfilRepository.findByUsuario(this.usuario);

            this.perfil = new Perfil(
                    perfilService.getPerfilById(usuarioPerfiles.get(0).getPerfil().getCI_Id()));

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
		this.validarPerfil();
		return "redirect:/dependiente/1";
	}

	@GetMapping("/dependiente/{pg}")
	public String listDependiente(Model model, @PathVariable Integer pg) {

		this.validarPerfil();

		// obtiene la organizacion del usuario
		Organizacion organizacion = this.usuario.getOrganizacion();

		List<Dependiente> dependientesFiltrados = dependienteService
				.getDependientesByCodigoPais(organizacion.getCodigoPais());

		// Obtener la cantidad total de elementos para la paginación
		int numeroTotalElementos = dependientesFiltrados.size();

		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		int tamanoPagina = pageable.getPageSize();
		int numeroPagina = pageable.getPageNumber();

		List<Dependiente> dependientesPaginados = dependientesFiltrados.stream()
				.skip((long) numeroPagina * tamanoPagina)
				.limit(tamanoPagina)
				.collect(Collectors.toList());

		List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
				.boxed()
				.toList();

		// Añadir atributos al modelo
		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("dependientes", dependientesPaginados); // Dependientes filtrados y paginados
		 model.addAttribute("victimas", dependienteService.getAllVictimasPage(pageable));
		model.addAttribute("tipoRelacionesfamiliares", dependienteService.getAllTipoRelacionesFamiliaresPage(pageable));
		// Retornar la vista de dependientes
		return "dependientes/dependiente";
	}

	@GetMapping("/dependientes/new")
	public String createdependienteForm(Model model) {

		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				Dependiente dependiente = new Dependiente();

				// Obtener todas las víctimas y agregarlas al modelo
				List<Victima> victimas = victimaService.getAllVictima();
				model.addAttribute("victimas", victimas);

				model.addAttribute("dependiente", dependiente);
				modelAttributes(model);

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

		this.validarPerfil();
		Victima victima = victimaService.getVictimaById(idVictima);

		if (victima != null) {

			dependienteService.saveDependiente(dependiente);

			bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
					this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en dependientes", this.usuario.getOrganizacion().getCodigoPais()));

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
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
						this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en dependientes", this.usuario.getOrganizacion().getCodigoPais()));

				dependienteService.deleteDependienteById(Id);

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
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				model.addAttribute("dependiente", dependienteService.getDependienteById(id));

				// Obtener todas las víctimas y agregarlas al modelo
				List<Victima> victimas = victimaService.getAllVictima();

				model.addAttribute("victimas", victimas);

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

		this.validarPerfil();
		Dependiente existingDependiente = dependienteService.getDependienteById(id);
		existingDependiente.setCI_Codigo(id);
		existingDependiente.setCVDNI(dependiente.getCVDNI());
		existingDependiente.setTipoRelacionFamiliar(dependiente.getTipoRelacionFamiliar());
		existingDependiente.setVictima(dependiente.getVictima());

		// dependienteService.updateDependiente(existingDependiente);

		Victima victima = victimaService.getVictimaById(idVictima);

		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en dependientes", this.usuario.getOrganizacion().getCodigoPais()));

		return "redirect:/dependientes";

	}

}
