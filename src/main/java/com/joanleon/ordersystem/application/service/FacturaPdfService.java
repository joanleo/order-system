package com.joanleon.ordersystem.application.service;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.joanleon.ordersystem.application.port.out.FacturaRepositoryPort;
import com.joanleon.ordersystem.domain.exception.FacturaNoEncontradaException;
import com.joanleon.ordersystem.domain.model.DetalleFactura;
import com.joanleon.ordersystem.domain.model.Factura;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class FacturaPdfService {
    
    private final FacturaRepositoryPort facturaRepository;
    
    // Colores corporativos
    private static final DeviceRgb COLOR_PRIMARIO = new DeviceRgb(41, 128, 185);
    private static final DeviceRgb COLOR_SECUNDARIO = new DeviceRgb(52, 73, 94);
    private static final DeviceRgb COLOR_FONDO = new DeviceRgb(236, 240, 241);
    
    public byte[] generarPdf(Long facturaId) {
        Factura factura = facturaRepository.findById(facturaId)
            .orElseThrow(() -> new FacturaNoEncontradaException(facturaId));
        
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);
            document.setMargins(40, 40, 40, 40);
            
            // Encabezado
            agregarEncabezado(document, factura);
            
            // Información del cliente
            agregarInfoCliente(document, factura);
            
            // Tabla de productos
            agregarTablaProductos(document, factura);
            
            // Totales
            agregarTotales(document, factura);
            
            // Información de pago
            agregarInfoPago(document, factura);
            
            // Pie de página
            agregarPiePagina(document);
            
            document.close();
            return baos.toByteArray();
            
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF de la factura", e);
        }
    }
    
    private void agregarEncabezado(Document document, Factura factura) {
        // Título principal
        Paragraph titulo = new Paragraph("FACTURA DE VENTA")
            .setFontSize(24)
            .setBold()
            .setFontColor(COLOR_PRIMARIO)
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginBottom(10);
        document.add(titulo);
        
        // Tabla con información de la empresa y factura
        Table headerTable = new Table(UnitValue.createPercentArray(new float[]{60, 40}))
            .useAllAvailableWidth()
            .setMarginBottom(20);
        
        // Información de la empresa (lado izquierdo)
        Cell empresaCell = new Cell()
            .setBorder(Border.NO_BORDER)
            .add(new Paragraph("Order System S.A.S.")
                .setFontSize(14)
                .setBold()
                .setFontColor(COLOR_SECUNDARIO))
            .add(new Paragraph("NIT: 900.123.456-7")
                .setFontSize(10))
            .add(new Paragraph("Calle 123 #45-67")
                .setFontSize(10))
            .add(new Paragraph("Cali, Valle del Cauca")
                .setFontSize(10))
            .add(new Paragraph("Tel: (602) 555-1234")
                .setFontSize(10))
            .add(new Paragraph("www.ordersystem.com")
                .setFontSize(10));
        
        // Información de la factura (lado derecho)
        Cell facturaInfoCell = new Cell()
            .setBorder(Border.NO_BORDER)
            .setBackgroundColor(COLOR_FONDO)
            .setPadding(10)
            .add(new Paragraph("Factura N°: " + factura.getNumeroFactura())
                .setFontSize(12)
                .setBold()
                .setFontColor(COLOR_PRIMARIO))
            .add(new Paragraph("Fecha: " + factura.getFechaEmision().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .setFontSize(10))
            .add(new Paragraph("Vencimiento: " + factura.getFechaVencimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .setFontSize(10))
            .add(new Paragraph("Estado: " + factura.getEstado().getDescripcion())
                .setFontSize(10)
                .setBold());
        
        headerTable.addCell(empresaCell);
        headerTable.addCell(facturaInfoCell);
        
        document.add(headerTable);
    }
    
    private void agregarInfoCliente(Document document, Factura factura) {
        Paragraph tituloCliente = new Paragraph("DATOS DEL CLIENTE")
            .setFontSize(12)
            .setBold()
            .setFontColor(COLOR_SECUNDARIO)
            .setMarginTop(10)
            .setMarginBottom(5);
        document.add(tituloCliente);
        
        Table clienteTable = new Table(1)
            .useAllAvailableWidth()
            .setBackgroundColor(COLOR_FONDO)
            .setPadding(10)
            .setMarginBottom(20);
        
        Cell clienteCell = new Cell()
            .setBorder(Border.NO_BORDER)
            .add(new Paragraph("Nombre: " + factura.getCliente().getNombre())
                .setFontSize(10)
                .setBold())
            .add(new Paragraph("Email: " + factura.getCliente().getEmail())
                .setFontSize(10))
            .add(new Paragraph("Teléfono: " + factura.getCliente().getTelefono())
                .setFontSize(10));
        
        clienteTable.addCell(clienteCell);
        document.add(clienteTable);
    }
    
    private void agregarTablaProductos(Document document, Factura factura) {
        Paragraph tituloProductos = new Paragraph("DETALLE DE PRODUCTOS")
            .setFontSize(12)
            .setBold()
            .setFontColor(COLOR_SECUNDARIO)
            .setMarginBottom(10);
        document.add(tituloProductos);
        
        Table table = new Table(UnitValue.createPercentArray(new float[]{10, 40, 15, 15, 20}))
            .useAllAvailableWidth();
        
        // Encabezados
        String[] headers = {"#", "Producto", "Cantidad", "Precio Unit.", "Subtotal"};
        for (String header : headers) {
            Cell cell = new Cell()
                .add(new Paragraph(header)
                    .setBold()
                    .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(COLOR_PRIMARIO)
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(8);
            table.addHeaderCell(cell);
        }
        
        // Filas de productos
        int contador = 1;
        for (DetalleFactura detalle : factura.getDetalles()) {
            table.addCell(createCell(String.valueOf(contador++), TextAlignment.CENTER));
            table.addCell(createCell(detalle.getProducto().getNombre(), TextAlignment.LEFT));
            table.addCell(createCell(String.valueOf(detalle.getCantidad()), TextAlignment.CENTER));
            table.addCell(createCell(formatMoney(detalle.getPrecioUnitario()), TextAlignment.RIGHT));
            table.addCell(createCell(formatMoney(detalle.getSubtotal()), TextAlignment.RIGHT));
        }
        
        document.add(table);
    }
    
    private void agregarTotales(Document document, Factura factura) {
        Table totalesTable = new Table(UnitValue.createPercentArray(new float[]{70, 30}))
            .useAllAvailableWidth()
            .setMarginTop(20);
        
        // Subtotal
        totalesTable.addCell(createTotalLabelCell("Subtotal:"));
        totalesTable.addCell(createTotalValueCell(formatMoney(factura.getSubtotal())));
        
        // Descuento (si aplica)
        if (factura.getDescuento() != null && factura.getDescuento().compareTo(BigDecimal.ZERO) > 0) {
            totalesTable.addCell(createTotalLabelCell("Descuento:"));
            totalesTable.addCell(createTotalValueCell("- " + formatMoney(factura.getDescuento())));
        }
        
        // IVA
        totalesTable.addCell(createTotalLabelCell("IVA (19%):"));
        totalesTable.addCell(createTotalValueCell(formatMoney(factura.getIva())));
        
        // Total
        Cell totalLabelCell = new Cell()
            .setBorder(Border.NO_BORDER)
            .setBackgroundColor(COLOR_PRIMARIO)
            .setPadding(10)
            .add(new Paragraph("TOTAL:")
                .setBold()
                .setFontSize(14)
                .setFontColor(ColorConstants.WHITE)
                .setTextAlignment(TextAlignment.RIGHT));
        
        Cell totalValueCell = new Cell()
            .setBorder(Border.NO_BORDER)
            .setBackgroundColor(COLOR_PRIMARIO)
            .setPadding(10)
            .add(new Paragraph(formatMoney(factura.getTotal()))
                .setBold()
                .setFontSize(14)
                .setFontColor(ColorConstants.WHITE)
                .setTextAlignment(TextAlignment.RIGHT));
        
        totalesTable.addCell(totalLabelCell);
        totalesTable.addCell(totalValueCell);
        
        document.add(totalesTable);
    }
    
    private void agregarInfoPago(Document document, Factura factura) {
        Table pagoTable = new Table(UnitValue.createPercentArray(new float[]{70, 30}))
            .useAllAvailableWidth()
            .setMarginTop(10);
        
        // Monto pagado
        pagoTable.addCell(createTotalLabelCell("Pagado:"));
        pagoTable.addCell(createTotalValueCell(formatMoney(factura.getMontoPagado())));
        
        // Saldo pendiente
        Cell saldoLabelCell = new Cell()
            .setBorder(Border.NO_BORDER)
            .setBackgroundColor(COLOR_FONDO)
            .setPadding(10)
            .add(new Paragraph("Saldo Pendiente:")
                .setBold()
                .setFontSize(12)
                .setTextAlignment(TextAlignment.RIGHT));
        
        Cell saldoValueCell = new Cell()
            .setBorder(Border.NO_BORDER)
            .setBackgroundColor(COLOR_FONDO)
            .setPadding(10)
            .add(new Paragraph(formatMoney(factura.getSaldoPendiente()))
                .setBold()
                .setFontSize(12)
                .setFontColor(factura.getSaldoPendiente().compareTo(BigDecimal.ZERO) > 0 ? 
                    new DeviceRgb(231, 76, 60) : new DeviceRgb(39, 174, 96))
                .setTextAlignment(TextAlignment.RIGHT));
        
        pagoTable.addCell(saldoLabelCell);
        pagoTable.addCell(saldoValueCell);
        
        document.add(pagoTable);
        
        // Método de pago
        if (factura.getMetodoPago() != null) {
            Paragraph metodoPago = new Paragraph("Método de pago: " + factura.getMetodoPago())
                .setFontSize(10)
                .setMarginTop(10)
                .setItalic();
            document.add(metodoPago);
        }
    }
    
    private void agregarPiePagina(Document document) {
        Paragraph pie = new Paragraph("\nGracias por su compra")
            .setFontSize(10)
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginTop(30)
            .setItalic()
            .setFontColor(COLOR_SECUNDARIO);
        
        Paragraph terminos = new Paragraph("Esta factura es válida como documento tributario")
            .setFontSize(8)
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginTop(5);
        
        document.add(pie);
        document.add(terminos);
    }
    
    // Métodos auxiliares
    private Cell createCell(String text, TextAlignment alignment) {
        return new Cell()
            .add(new Paragraph(text).setFontSize(10))
            .setTextAlignment(alignment)
            .setPadding(5);
    }
    
    private Cell createTotalLabelCell(String text) {
        return new Cell()
            .setBorder(Border.NO_BORDER)
            .setPadding(5)
            .add(new Paragraph(text)
                .setFontSize(11)
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT));
    }
    
    private Cell createTotalValueCell(String text) {
        return new Cell()
            .setBorder(Border.NO_BORDER)
            .setPadding(5)
            .add(new Paragraph(text)
                .setFontSize(11)
                .setTextAlignment(TextAlignment.RIGHT));
    }
    
    private String formatMoney(BigDecimal amount) {
        return String.format("$%,.2f", amount);
    }
}