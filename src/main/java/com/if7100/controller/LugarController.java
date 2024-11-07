package com.if7100.controller;

import com.if7100.entity.*;
import com.if7100.service.*;
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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
import org.springframework.web.bind.annotation.*;

import com.if7100.repository.UsuarioPerfilRepository;
/**
 * @author Julio Jarquin
 * Fecha: 20 de abril del 2023
 */
import com.if7100.repository.UsuarioRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LugarController {

	private LugarService lugarService;
	private UsuarioPerfilService usuarioPerfilService;
	private TipoLugarService tipoLugarService;

	private HechoService hechoService;

	private PaisesService paisesService;
	// instancias para control de acceso
	private UsuarioRepository usuarioRepository;
	private UsuarioPerfilRepository usuarioPerfilRepository;
	private Perfil perfil;
	private PerfilService perfilService;
	// instancias para control de bitacora
	private BitacoraService bitacoraService;
	private Usuario usuario;

	public LugarController(BitacoraService bitacoraService, PaisesService paisesService, LugarService lugarService,
			TipoLugarService tipoLugarService, HechoService hechoService, PerfilService perfilService,
			UsuarioRepository usuarioRepository, UsuarioPerfilService usuarioPerfilService, UsuarioPerfilRepository usuarioPerfilRepository) {
		super();
		this.lugarService = lugarService;
		this.paisesService = paisesService;
		this.tipoLugarService = tipoLugarService;
		this.hechoService = hechoService;
		this.perfilService = perfilService;
		this.usuarioRepository = usuarioRepository;
		this.bitacoraService = bitacoraService;
		this.usuarioPerfilService = usuarioPerfilService;
		this.usuarioPerfilRepository = usuarioPerfilRepository;

	}

	@GetMapping("/lugar/pdf")
	public void exportToPDF(HttpServletResponse response) throws IOException, java.io.IOException {
		this.validarPerfil();
		response.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=lugares_filtrados.pdf";
		response.setHeader(headerKey, headerValue);

		// Crear el documento PDF usando iText 7
		PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());
		PdfDocument pdfDoc = new PdfDocument(pdfWriter);
		Document document = new Document(pdfDoc);

		// Agregar márgenes al documento (izquierda, derecha, arriba, abajo)
		document.setMargins(20, 20, 20, 20);

		// Agregar un título en negrita y centrado al documento
		Paragraph title = new Paragraph("Reporte de lugar")
				.setTextAlignment(TextAlignment.CENTER)
				.setFontSize(18)
				.setBold();
		document.add(title);

		// Agregar un subtítulo
		Paragraph subTitle = new Paragraph("lugares filtrados por país")
				.setTextAlignment(TextAlignment.CENTER)
				.setFontSize(12)
				.setItalic();
		document.add(subTitle);

		// Espacio después del título
		document.add(new Paragraph("\n"));

		// Crear una tabla con columnas ajustadas dinámicamente
		Table table = new Table(UnitValue.createPercentArray(new float[] { 1, 1, 1, 1, 1, 1, 1, 2 }));
		table.setWidth(UnitValue.createPercentValue(100)); // Ajustar al 100% del ancho de la página

		// Agregar encabezado de tabla con estilo
		String[] headers = { "ID", "Hecho", "Descripción", "Tipo de lugar", "Código postal",
				"Provincia", "Cantón", "Distrito" };
		for (String header : headers) {
			table.addHeaderCell(new Cell().add(new Paragraph(header).setBold())
					.setTextAlignment(TextAlignment.CENTER)
					.setBackgroundColor(ColorConstants.LIGHT_GRAY));
		}

		// Obtener la lista de hechos filtrados por país
		Integer codigoPaisUsuario = this.usuario.getOrganizacion().getCodigoPais();
		List<Lugar> lugares = lugarService.getLugarByCodigoPaisUsuario(codigoPaisUsuario);

		// Recorrer los hechos y agregarlos a la tabla
		for (Lugar lugar : lugares) {

			table.addCell(new Cell().add(new Paragraph(String.valueOf(lugar.getCI_Codigo())))
					.setTextAlignment(TextAlignment.CENTER));

			table.addCell(new Cell().add(new Paragraph(String.valueOf(lugar.getCIHecho())))
					.setTextAlignment(TextAlignment.CENTER));

			table.addCell(new Cell().add(new Paragraph(lugar.getCV_Descripcion()))
					.setTextAlignment(TextAlignment.CENTER));

			table.addCell(new Cell()
					.add(new Paragraph(tipoLugarService.getTipoLugarByCodigo(lugar.getCITipoLugar()).getCVTitulo()))
					.setTextAlignment(TextAlignment.CENTER));

			table.addCell(new Cell().add(new Paragraph(String.valueOf(lugar.getCI_Codigo_Postal())))
					.setTextAlignment(TextAlignment.CENTER));

			table.addCell(new Cell().add(new Paragraph(String.valueOf(lugar.getCVProvincia())))
					.setTextAlignment(TextAlignment.CENTER));

			table.addCell(new Cell().add(new Paragraph(String.valueOf(lugar.getCVCanton())))
					.setTextAlignment(TextAlignment.CENTER));

			table.addCell(new Cell().add(new Paragraph(lugar.getCVDistrito()))
					.setTextAlignment(TextAlignment.CENTER));
			
		}

		// Asegurar que la tabla ocupe el espacio disponible sin distorsionarse
		table.setAutoLayout();

		// Agregar la tabla al documento
		document.add(table);

		// Cerrar el documento
		document.close();
	}

	@GetMapping("/lugar/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException, java.io.IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		
		this.validarPerfil();
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=lugares_filtrados.xlsx";
		response.setHeader(headerKey, headerValue);

		// Crear un nuevo libro de trabajo de Excel
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Lugares Filtrados");

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
		String[] headers = { "ID", "Hecho", "Descripción", "Tipo de lugar", "Dirección",
				"Ciudad", "Código postal",
				"Provincia", "Cantón", "Distrito" };
		for (int i = 0; i < headers.length; i++) {
			XSSFCell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(headerStyle); // Aplicar estilo de encabezado
		}

		// Obtener la lista de lugares filtrados por país
		Integer codigoPaisUsuario = this.usuario.getOrganizacion().getCodigoPais();

		List<Lugar> lugares = lugarService.getLugarByCodigoPaisUsuario(codigoPaisUsuario);

		// Rellenar las filas con los datos
		int rowNum = 1;
		for (Lugar lugar : lugares) {
			XSSFRow row = sheet.createRow(rowNum++);

			// ID
			row.createCell(0).setCellValue(lugar.getCI_Codigo());
			row.getCell(0).setCellStyle(cellStyle); // Aplicar estilo de celda

			// Tipo de Víctima
			row.createCell(1)
					.setCellValue(lugar.getCIHecho());
			row.getCell(1).setCellStyle(cellStyle);

			// Tipo de Relación
			row.createCell(2)
					.setCellValue(lugar.getCV_Descripcion());
			row.getCell(2).setCellStyle(cellStyle);

			// Modalidad
			row.createCell(3).setCellValue(tipoLugarService.getTipoLugarByCodigo(lugar.getCITipoLugar()).getCVTitulo());
			row.getCell(3).setCellStyle(cellStyle);

			// Agresión Sexual
			row.createCell(4).setCellValue(lugar.getCV_Direccion());
			row.getCell(4).setCellStyle(cellStyle);

			// Denuncia previa
			row.createCell(5).setCellValue(lugar.getCV_Ciudad());
			row.getCell(5).setCellStyle(cellStyle);

			// Generador
			row.createCell(6).setCellValue(lugar.getCI_Codigo_Postal());
			row.getCell(6).setCellStyle(cellStyle);

			// Proceso Judicial
			row.createCell(7).setCellValue(lugar.getCVProvincia());
			row.getCell(7).setCellStyle(cellStyle);

			// País
			row.createCell(8).setCellValue(lugar.getCVCanton());
			row.getCell(8).setCellStyle(cellStyle);

			// Observaciones
			row.createCell(9).setCellValue(lugar.getCVDistrito());
			row.getCell(9).setCellStyle(cellStyle);
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

	// Eliminar Lugar
	@GetMapping("/lugares/{Id}")
	public String deleteLugar(@PathVariable Integer Id, HttpSession session) {
		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
				
				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en lugares", this.usuario.getOrganizacion().getCodigoPais()));

				
				lugarService.deleteLugarById(Id);
				return "redirect:/lugares";
			} else {
				return "SinAcceso";
			}
		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@GetMapping("/hecholugares/{id}/{idLugar}")
	public String deleteLugar(@PathVariable Integer id, @PathVariable Integer idLugar) {
		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
				lugarService.deleteLugarById(id);
				
				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en lugares", this.usuario.getOrganizacion().getCodigoPais()));


				return "redirect:/hecholugar/".concat(String.valueOf(idLugar));
			} else {
				return "SinAcceso";
			}
		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	// Editar Lugar
	@GetMapping("/lugares/edit/{Id}")
	public String editLugarForm(@PathVariable Integer Id, Model model) {

		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				model.addAttribute("lugar", lugarService.getLugarById(Id));
				model.addAttribute("hechos", hechoService.getAllHechos());
				model.addAttribute("tiposLugares", tipoLugarService.getAllTipoLugares());
				return "lugares/edit_lugar";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/lugares/{id}")
	public String updateLugar(@PathVariable Integer id, @ModelAttribute Lugar lugar, Model model) {
		try {
			this.validarPerfil();
			Lugar existingLugar = lugarService.getLugarById(id);
			existingLugar.setCI_Codigo(id);
			existingLugar.setCIHecho(lugar.getCIHecho());
			existingLugar.setCV_Descripcion(lugar.getCV_Descripcion());
			existingLugar.setCITipoLugar(lugar.getCITipoLugar());
			existingLugar.setCV_Direccion(lugar.getCV_Direccion());
			existingLugar.setCV_Ciudad(lugar.getCV_Ciudad());
			existingLugar.setCI_Codigo_Postal(lugar.getCI_Codigo_Postal());
			existingLugar.setCVProvincia(lugar.getCVProvincia());
			existingLugar.setCVCanton(lugar.getCVCanton());
			existingLugar.setCVDistrito(lugar.getCVDistrito());

			lugarService.updateLugar(existingLugar);
			
			bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en lugares", this.usuario.getOrganizacion().getCodigoPais()));

			
			return "redirect:/lugares";
		} catch (DataIntegrityViolationException e) {
			String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
			model.addAttribute("error_message", mensaje);
			model.addAttribute("error", true);
			return editLugarForm(id, model);
		}
	}

	// @PostMapping("/lugares")
	// public String saveLugar(@ModelAttribute("lugar") Lugar lugar, Model model) {
	// try {
	// lugarService.saveLugar(lugar);
	// String descripcion="Creo en Lugar";
	// Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(),
	// this.usuario.getCVNombre(), this.perfil.getCVRol(), descripcion);
	// bitacoraService.saveBitacora(bitacora);
	// return "redirect:/lugar/" + lugar.getCIHecho();
	// } catch (DataIntegrityViolationException e) {
	// String mensaje = "No se puede guardar el lugar debido a un error de
	// integridad de datos.";
	// model.addAttribute("error_message", mensaje);
	// model.addAttribute("error", true);
	// return createLugarForm(model, lugar.getCIHecho());
	// }
	// }

	// ESTE METODO ESTA BUENO PERO SOLO CUANDO ENTRA POR VER LUGARES// ESTE SI ESTA
	// PROBADO
	// Nuevo Lugar
	// @GetMapping("/lugares/new/{Id}")
	// public String createLugarForm(Model model, @PathVariable Integer Id) {
	//
	// try {
	// this.validarPerfil();
	// if (!this.perfil.getCVRol().equals("Consulta")) {
	//
	// Lugar lugar = new Lugar();
	// lugar.setCIHecho(Id);
	// model.addAttribute("lugar", lugar);
	// model.addAttribute("tipoLugar", tipoLugarService.getAllTipoLugares());
	// return "lugares/create_lugar";
	// } else {
	// return "SinAcceso";
	// }
	//
	// } catch (Exception e) {
	// return "SinAcceso";
	// }
	// }

	@GetMapping("/lugares")
	public String listLugares(Model model) {
		this.validarPerfil();
		return "redirect:/lugar/1";
	}

	@GetMapping("/lugar/{pg}")
	public String listLugar(Model model, @PathVariable Integer pg) {

		this.validarPerfil();

		Organizacion organizacion = this.usuario.getOrganizacion();

		// Obtener los procesos judiciales filtrados por el código de país
		List<Lugar> lugaresFiltrados = lugarService.getLugarByCodigoPaisUsuario(organizacion.getCodigoPais());

		int numeroTotalElementos = lugaresFiltrados.size();

		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		int tamanoPagina = pageable.getPageSize();
		int numeroPagina = pageable.getPageNumber();

		List<Lugar> lugaresPaginados = lugaresFiltrados.stream()
				.skip((long) numeroPagina * tamanoPagina)
				.limit(tamanoPagina)
				.collect(Collectors.toList());

		List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
				.boxed()
				.toList();

		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("lugares", lugaresPaginados);
		model.addAttribute("lugar", true);
		model.addAttribute("tipoLugares", lugarService.getAllTipoLugars());
		return "lugares/lugares";
	}

	@GetMapping("/hecholugar/{id}")
	public String listHechoLugares(Model model, @PathVariable Integer id) {
		this.validarPerfil();
		return "redirect:/hecholugar/".concat(String.valueOf(id)).concat("/1");
	}

	@GetMapping("/hecholugar/{id}/{pg}")
	public String listHechoLugar(Model model, @PathVariable Integer id, @PathVariable Integer pg) {
		this.validarPerfil();

		Organizacion organizacion = this.usuario.getOrganizacion();

		// Obtener los procesos judiciales filtrados por el código de país
		List<Lugar> lugaresFiltrados = lugarService.getLugarByCodigoPaisUsuario(organizacion.getCodigoPais());

		int numeroTotalElementos = lugaresFiltrados.size();

		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		int tamanoPagina = pageable.getPageSize();
		int numeroPagina = pageable.getPageNumber();

		List<Lugar> lugaresPaginados = lugaresFiltrados.stream()
				.skip((long) numeroPagina * tamanoPagina)
				.limit(tamanoPagina)
				.collect(Collectors.toList());

		List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
				.boxed()
				.toList();

		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("lugares", lugaresPaginados);
		model.addAttribute("lugar", true);
		model.addAttribute("tipoLugares", lugarService.getAllTipoLugars());
		return "lugares/lugares";
	}

	// Este Metodo esta bueno pero solo sirve si selecciona ver la lista de lugares
	// de un hecho y desde la misma ventana de lugar se agrega un lugar de un hecho
	// en especifico

	// Nuevo Lugar
	@GetMapping("/lugares/new")
	public String createLugarForm(Model model) {
		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
				Lugar lugar = new Lugar();
				model.addAttribute("lugar", lugar);
				// model.addAttribute("paises", paisesService.getAllPaises());
				model.addAttribute("hechos", hechoService.getAllHechos());
				model.addAttribute("tiposLugares", tipoLugarService.getAllTipoLugares());
				return "lugares/create_lugar";
			} else {
				return "SinAcceso";
			}
		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/lugar")
	public String saveLugar(@ModelAttribute Lugar lugar, Model model) {
		try {
			this.validarPerfil();
			lugarService.saveLugar(lugar);
			
			bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en lugares", this.usuario.getOrganizacion().getCodigoPais()));

			
			return "redirect:/lugares";
		} catch (DataIntegrityViolationException e) {
			String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
			model.addAttribute("error_message", mensaje);
			model.addAttribute("error", true);
			return createLugarForm(model);
		}

	}

	@PostMapping("/hecholugar")
	public String saveHechoLugar(@ModelAttribute Lugar lugar, Model model) {
		try {
			this.validarPerfil();
			lugarService.saveLugar(lugar);
			
			bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en lugares", this.usuario.getOrganizacion().getCodigoPais()));

			
			return "redirect:/hecholugar/".concat(String.valueOf(lugar.getCIHecho()));
		} catch (DataIntegrityViolationException e) {
			String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
			model.addAttribute("error_message", mensaje);
			model.addAttribute("error", true);
			return createHechoLugarForm(model, lugar.getCIHecho());
		}

	}

	@GetMapping("/hecholugar/new/{Id}")
	public String createHechoLugarForm(Model model, @PathVariable Integer Id) {
		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
				Lugar lugar = new Lugar();
				lugar.setCIHecho(Id);
				model.addAttribute("lugar", lugar);
				model.addAttribute("hechos", hechoService.getAllHechos());
				model.addAttribute("tiposLugares", tipoLugarService.getAllTipoLugares());
				return "lugares/create_lugares";
			} else {
				return "SinAcceso";
			}
		} catch (Exception e) {
			return "SinAcceso";
		}

	}

}
