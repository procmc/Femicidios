/**
 * 
 */
package com.if7100.controller;

import com.if7100.entity.*;
import com.if7100.service.BitacoraService;
import com.if7100.service.IdentidadGeneroService;
import com.if7100.service.NivelEducativoService;
import com.if7100.service.OrganismoService;
import com.if7100.service.OrientacionSexualService;
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
import java.util.stream.Collectors;
import java.util.List;
import java.util.stream.Stream;

import com.if7100.service.PerfilService;

/**
 * @author Hadji
 *
 */


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

import com.if7100.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.IntStream;

@Controller
public class VictimaController {
	
	 @Autowired
 private PaisesService paisesService;

	private VictimaService victimaService;
	
	//instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private Perfil perfil;
    private PerfilService perfilService;
  //instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;
    //Instancias para el control de organismo
    private Organismo organismo;
    private OrganismoService organismoService;
    //Instancias el control de Orientacion Sexual
    private OrientacionSexualService orientacionSexualService;
    private OrientacionSexual orientacionSexual;
  //Instancias el control de Genero
    private IdentidadGeneroService identidadGeneroService;
    private IdentidadGenero identidadGenero;
  //Instancias el control de Nivel educativo
    private NivelEducativo nivelEducativo;
    private NivelEducativoService nivelEducativoService;

	//Constructor con todos las instancias
	public VictimaController(VictimaService victimaService, UsuarioRepository usuarioRepository,
			PerfilService perfilService, BitacoraService bitacoraService, OrganismoService organismoService,
			OrientacionSexualService orientacionSexualService, IdentidadGeneroService identidadGeneroService,
			NivelEducativoService nivelEducativoService) {
		super();
		this.victimaService = victimaService;
		this.usuarioRepository = usuarioRepository;
		this.perfilService = perfilService;
		this.bitacoraService = bitacoraService;
		this.organismoService = organismoService;
		this.orientacionSexualService = orientacionSexualService;
		this.identidadGeneroService = identidadGeneroService;
		this.nivelEducativoService = nivelEducativoService;
	}

	@GetMapping("/victima/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException, java.io.IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=victimas_filtrados.pdf";
        response.setHeader(headerKey, headerValue);

        // Crear el documento PDF usando iText 7
        PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDoc = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDoc);

        // Agregar márgenes al documento (izquierda, derecha, arriba, abajo)
        document.setMargins(20, 20, 20, 20);

        // Agregar un título en negrita y centrado al documento
        Paragraph title = new Paragraph("Reporte de victimas")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18)
                .setBold();
        document.add(title);

        // Agregar un subtítulo
        Paragraph subTitle = new Paragraph("Victimas filtradas por país")
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
        String[] headers = { "ID", "DNI", "Nombre", "Edad", "Género",
		"Orientación sexual", "Nacionalidad", "Ocupación", "Medidas de protección", "Denuncia previa" };

        for (String header : headers) {
            table.addHeaderCell(new Cell().add(new Paragraph(header).setBold())
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY));
        }

        // Obtener la lista de imputados filtrados por país
        Integer codigoPaisUsuario = this.usuario.getCodigoPais();
        List<Victima> victimas = victimaService.findVictimasByCodigoPaisHecho(codigoPaisUsuario);

        // Recorrer los imputados y agregarlos a la tabla
        for (Victima victima : victimas) {

            table.addCell(new Cell().add(new Paragraph(String.valueOf(victima.getCI_Id())))
                    .setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add(new Paragraph(victima.getCVDNI()))
                    .setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add(new Paragraph(victima.getCVNombre()+" "+victima.getCVApellidoPaterno()+" "+victima.getCVApellidoMaterno()))
                    .setTextAlignment(TextAlignment.CENTER));

			table.addCell(new Cell().add(new Paragraph(String.valueOf(victima.getCIEdad())))
                    .setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add(new Paragraph(identidadGeneroService.getIdentidadGeneroById(victima.getCVGenero()).getNombre()))
                    .setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add(new Paragraph(orientacionSexualService.getOrientacionSexualByCodigo(victima.getCVOrientaSex()).getCVTitulo()))//
                    .setTextAlignment(TextAlignment.CENTER));
          
			table.addCell(new Cell().add(new Paragraph(victima.getCVNacionalidad()))
                    .setTextAlignment(TextAlignment.CENTER));

            // Continuar con el resto de los datos
            table.addCell(new Cell().add(new Paragraph(victima.getCVOcupacion()))
                    .setTextAlignment(TextAlignment.CENTER));

			table.addCell(new Cell().add(new Paragraph(victima.getCVMedidasProteccion()))
                    .setTextAlignment(TextAlignment.CENTER));
					
			table.addCell(new Cell().add(new Paragraph(victima.getCVDenunciasPrevias()))
                    .setTextAlignment(TextAlignment.CENTER));
        }

        // Asegurar que la tabla ocupe el espacio disponible sin distorsionarse
        table.setAutoLayout();

        // Agregar la tabla al documento
        document.add(table);

        // Cerrar el documento
        document.close();
    }


	@GetMapping("/victima/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException, java.io.IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        this.validarPerfil();
		String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=victima_filtrados.xlsx";
        response.setHeader(headerKey, headerValue);

        // Crear un nuevo libro de trabajo de Excel
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Victimas Filtrados");

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
        String[] headers = { "ID", "DNI", "Nombre", "Primer apellido", "Segundo apellido",
                "Edad", "Género",
                "Lugar de nacimiento", "Orientación sexual", "Nacionalidad", "Educación", "Ocupación", "Domicilio", "Lugar de residencia",
			"Discapacidad","Condición migratoria", "Etnia", "Medidas de protección", "Denuncia previa", "Hijos", "Organismo generador"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle); // Aplicar estilo de encabezado
        }

        // Obtener la lista de victimas filtrados por país
        Integer codigoPaisUsuario = this.usuario.getCodigoPais();
        List<Victima> victimas = victimaService.findVictimasByCodigoPaisHecho(codigoPaisUsuario);

        // Rellenar las filas con los datos
        int rowNum = 1;
        for (Victima victima : victimas) {
            XSSFRow row = sheet.createRow(rowNum++);

            // ID
            row.createCell(0).setCellValue(victima.getCI_Id());
            row.getCell(0).setCellStyle(cellStyle); // Aplicar estilo de celda

            // Tipo de Víctima
            row.createCell(1).setCellValue(victima.getCVDNI());
            row.getCell(1).setCellStyle(cellStyle);

            // Tipo de Relación
            row.createCell(2).setCellValue(victima.getCVNombre());
            row.getCell(2).setCellStyle(cellStyle);

            // Modalidad
            row.createCell(3).setCellValue(victima.getCVApellidoPaterno());
            row.getCell(3).setCellStyle(cellStyle);

            // Agresión Sexual
            row.createCell(4).setCellValue(victima.getCVApellidoMaterno());
            row.getCell(4).setCellStyle(cellStyle);

            // Denuncia previa
            row.createCell(5).setCellValue(victima.getCIEdad());
            row.getCell(5).setCellStyle(cellStyle);

            // Generador
            row.createCell(6).setCellValue(identidadGeneroService.getIdentidadGeneroById(victima.getCVGenero()).getNombre());
            row.getCell(6).setCellStyle(cellStyle);

            // Proceso Judicial
            row.createCell(7).setCellValue(victima.getCVLugarNac());
            row.getCell(7).setCellStyle(cellStyle);

            // País
            row.createCell(8).setCellValue(orientacionSexualService.getOrientacionSexualByCodigo(victima.getCVOrientaSex()).getCVTitulo());
            row.getCell(8).setCellStyle(cellStyle);

			 // Observaciones
			 row.createCell(9).setCellValue(victima.getCVNacionalidad());
			 row.getCell(9).setCellStyle(cellStyle);

			  // Observaciones
			  row.createCell(10).setCellValue(nivelEducativoService.getNivelEducativoById(victima.getCIEducacion()).getCVTitulo());
			  row.getCell(10).setCellStyle(cellStyle);

			   // Observaciones
			 row.createCell(11).setCellValue(victima.getCVOcupacion());
			 row.getCell(11).setCellStyle(cellStyle);

			  // Observaciones
			  row.createCell(12).setCellValue(victima.getCVDomicilio());
			  row.getCell(12).setCellStyle(cellStyle);

			   // Observaciones
			 row.createCell(13).setCellValue(victima.getCVLugarResidencia());
			 row.getCell(13).setCellStyle(cellStyle);

			  // Observaciones
			  row.createCell(14).setCellValue(victima.getCVDiscapacidad());
			  row.getCell(14).setCellStyle(cellStyle);
			  
			   // Observaciones
			 row.createCell(15).setCellValue(victima.getCVCondicionMigratoria());
			 row.getCell(15).setCellStyle(cellStyle);

			  // Observaciones
			  row.createCell(16).setCellValue(victima.getCVEtnia());
			  row.getCell(16).setCellStyle(cellStyle);

			   // Observaciones
			 row.createCell(17).setCellValue(victima.getCVMedidasProteccion());
			 row.getCell(17).setCellStyle(cellStyle);

			  // Observaciones
			  row.createCell(18).setCellValue(victima.getCVDenunciasPrevias());
			  row.getCell(18).setCellStyle(cellStyle);

			   // Observaciones
			 row.createCell(19).setCellValue(victima.getCIHijos());
			 row.getCell(19).setCellStyle(cellStyle);

			  // Observaciones
			  row.createCell(20).setCellValue(victima.getCVGenerador());
			  row.getCell(20).setCellStyle(cellStyle);
           
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
			Usuario usuario=new Usuario();

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String username = authentication.getName();
		    this.usuario= new Usuario(usuarioRepository.findByCVCedula(username));

			this.perfil = new Perfil(perfilService.getPerfilById(usuarioRepository.findByCVCedula(username).getCIPerfil()));
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	private Pageable initPages(int pg, int paginasDeseadas, int numeroTotalElementos){
		int numeroPagina = pg-1;
		if (numeroTotalElementos < 10){
			paginasDeseadas = 1;
		}
		if (numeroTotalElementos < 1){
			numeroTotalElementos = 1;
		}
		int tamanoPagina = (int) Math.ceil(numeroTotalElementos / (double) paginasDeseadas);
		return PageRequest.of(numeroPagina, tamanoPagina);
	}

	@GetMapping("/victimas")
	public String listVictimas(Model model) {
		return "redirect:/victima/1";
	}

	@GetMapping("/victima/{pg}")
	public String listVictima(Model model, @PathVariable Integer pg){
		
		this.validarPerfil();

		// Obtener el código de país del usuario logueado
		Integer codigoPaisUsuarioLogueado = this.usuario.getCodigoPais();
		
		// Filtrar Victimas por el código de país del usuario logueado
		//List<Victima> victimasFiltradas = victimaService.findByCodigoPais(codigoPaisUsuarioLogueado);
        List<Victima> victimasFiltradas = victimaService.findVictimasByCodigoPaisHecho(codigoPaisUsuarioLogueado);


		int numeroTotalElementos = victimaService.getAllVictima().size();

		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		int tamanoPagina = pageable.getPageSize();
        int numeroPagina = pageable.getPageNumber();

		//Page<Victima> victimaPage = victimaService.getAllVictimaPage(pageable);

		/*List<Integer> nPaginas = IntStream.rangeClosed(1, victimaPage.getTotalPages())
				.boxed()
				.toList();*/

		List<Victima> victimasPaginados = victimasFiltradas.stream()
			.skip((long) numeroPagina * tamanoPagina)
			.limit(tamanoPagina)
			.collect(Collectors.toList());

		List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
			.boxed()
			.toList();

		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("victimas", victimasPaginados);
		model.addAttribute("identidadgenero", victimaService.getAllIdentidadGeneros());
		model.addAttribute("orientacionSexual", victimaService.getAllOrientacionSexuales());
		model.addAttribute("nivelEducativo", nivelEducativoService.getAllNivelEducativo());

		return "victimas/victima";
	}
	
	@GetMapping("/victimas/new")
	public String createVictimaForm (Model model) {
		
		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				Victima victima = new Victima();
				
				model.addAttribute("orientacionSexual",orientacionSexualService.getAllOrientacionesSexuales());
				model.addAttribute("identidadGenero",identidadGeneroService.getAllIdentidadGenero());
				model.addAttribute("nivelEducativo",nivelEducativoService.getAllNivelEducativo());
				model.addAttribute("listaOrganismo",organismoService.getAllOrganismos());
				model.addAttribute("victima", victima);
				model.addAttribute("nivelEducativo", nivelEducativoService.getAllNivelEducativo());

				// Obtener lista de países y enviarla al modelo
				//List<Paises> paises = paisesService.getAllPaises();
				//model.addAttribute("paises", paises);
				
				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCI_Id(),
						this.usuario.getCVNombre(),this.perfil.getCVRol(),"Crea en Victima"));
				return "victimas/create_victima";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	
	
	@PostMapping("/victimas")
	public String saveVictima (@ModelAttribute("victima") Victima victima) {
		
		victimaService.saveVictima(victima);
		return "redirect:/victimas";
	}
	

	@GetMapping("/victimas/{Id}")
	public String deleteVictima (@PathVariable Integer Id) {
		
		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				String descripcion = "Elimino una victima";
				Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), descripcion, this.perfil.getCVRol());
				bitacoraService.saveBitacora(bitacora);
				
				victimaService.deleteVictimaById(Id);
				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCI_Id(),
						this.usuario.getCVNombre(),this.perfil.getCVRol(),"Eliminó en Victima"));
				return "redirect:/victimas";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	
	
	@GetMapping("/victimas/edit/{id}")
	public String editVictimaForm (@PathVariable Integer id, Model model) {
		
		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				//List<Paises> paises = paisesService.getAllPaises();  // Obtiene la lista de países
				//model.addAttribute("paises", paises);  // Envía la lista de países al modelo

				//model.addAttribute("orientacionSexual",orientacionSexualService.getAllOrientacionesSexuales());
				model.addAttribute("identidadGenero",identidadGeneroService.getAllIdentidadGenero());
				model.addAttribute("nivelEducativo",nivelEducativoService.getAllNivelEducativo());
				model.addAttribute("listaOrganismo",organismoService.getAllOrganismos());
				model.addAttribute("victima", victimaService.getVictimaById(id));
				model.addAttribute("nivelEducativo", nivelEducativoService.getAllNivelEducativo());
				model.addAttribute("orientacionSexual", victimaService.getAllOrientacionSexuales());

				return "victimas/edit_victima";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}	
	}
	
	
	@PostMapping("/victimas/{id}")
	public String updateVictima (@PathVariable Integer id, 
								 @ModelAttribute("victima") Victima victima,
								 Model model) {
		
		Victima existingVictima = victimaService.getVictimaById(id);
		//existingVictima.setCodigoPais(victima.getCodigoPais());//actualiza codigo pais
		existingVictima.setCI_Id(id);
		existingVictima.setCVDNI(victima.getCVDNI());
		existingVictima.setCVNombre(victima.getCVNombre());
		existingVictima.setCVApellidoPaterno(victima.getCVApellidoPaterno());
		existingVictima.setCVApellidoMaterno(victima.getCVApellidoMaterno());
		existingVictima.setCVEdad(victima.getCIEdad());
		existingVictima.setCVGenero(victima.getCVGenero());
		existingVictima.setCVLugarNac(victima.getCVLugarNac());
		existingVictima.setCVOrientaSex(victima.getCVOrientaSex());
		existingVictima.setCVNacionalidad(victima.getCVNacionalidad());
		existingVictima.setCIEducacion(victima.getCIEducacion());
		existingVictima.setCVOcupacion(victima.getCVOcupacion());
		existingVictima.setCVDomicilio(victima.getCVDomicilio());
		existingVictima.setCVLugarResidencia(victima.getCVLugarResidencia());
		existingVictima.setCVDiscapacidad(victima.getCVDiscapacidad());
		existingVictima.setCVCondicionMigratoria(victima.getCVCondicionMigratoria());
		existingVictima.setCVEtnia(victima.getCVEtnia());
		existingVictima.setCVMedidasProteccion(victima.getCVMedidasProteccion());
		existingVictima.setCVDenunciasPrevias(victima.getCVDenunciasPrevias());
		existingVictima.setCIHijos(victima.getCIHijos());
		existingVictima.setCVGenerador(victima.getCVGenerador());
		
		victimaService.updateVictima(existingVictima);
		
		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCI_Id(),
				 this.usuario.getCVNombre(),this.perfil.getCVRol(),"Actualizó en Victima"));
		 
		return "redirect:/victimas";
		
	}
	
}
