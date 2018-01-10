/*   1:    */ package com.asinfo.as2.produccion.servlet;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   4:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoInventario;
/*   5:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   6:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   7:    */ import com.asinfo.as2.utils.reportes.ReportConfigUtil;
/*   8:    */ import java.io.File;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.servlet.ServletException;
/*  16:    */ import javax.servlet.ServletOutputStream;
/*  17:    */ import javax.servlet.annotation.WebServlet;
/*  18:    */ import javax.servlet.http.HttpServlet;
/*  19:    */ import javax.servlet.http.HttpServletRequest;
/*  20:    */ import javax.servlet.http.HttpServletResponse;
/*  21:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  22:    */ import net.sf.jasperreports.engine.JRException;
/*  23:    */ import net.sf.jasperreports.engine.JRExporter;
/*  24:    */ import net.sf.jasperreports.engine.JRExporterParameter;
/*  25:    */ import net.sf.jasperreports.engine.JasperFillManager;
/*  26:    */ import net.sf.jasperreports.engine.JasperPrint;
/*  27:    */ import net.sf.jasperreports.engine.JasperReport;
/*  28:    */ import net.sf.jasperreports.engine.export.JRPdfExporter;
/*  29:    */ import net.sf.jasperreports.engine.util.JRLoader;
/*  30:    */ 
/*  31:    */ @WebServlet(name="ServletReporteEtiquetaIngresoFabricacion", urlPatterns={"/servlet/reporteEtiquetaIngresoFabricacion"})
/*  32:    */ public class ServletReporteEtiquetaIngresoFabricacion
/*  33:    */   extends HttpServlet
/*  34:    */ {
/*  35:    */   @EJB
/*  36:    */   private ServicioReporteMovimientoInventario servicioReporteMovimientoInventario;
/*  37:    */   @EJB
/*  38:    */   private ServicioGenerico<OrganizacionConfiguracion> servicioOrganizacionConfiguracion;
/*  39: 43 */   String pathReportes = System.getenv("AS2_HOME");
/*  40: 44 */   String COMPILE_DIR = File.separator + "reportes" + File.separator;
/*  41:    */   
/*  42:    */   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
/*  43:    */     throws ServletException, IOException
/*  44:    */   {
/*  45: 48 */     response.setContentType("application/pdf");
/*  46: 49 */     response.setHeader("Cache-Control", "no-cache");
/*  47: 50 */     response.setHeader("Pragma", "no-cache");
/*  48: 51 */     response.setDateHeader("Expires", 0L);
/*  49: 52 */     ServletOutputStream out = response.getOutputStream();
/*  50:    */     
/*  51: 54 */     Integer idOrganizacion = Integer.valueOf(request.getParameter("idOrganizacion"));
/*  52: 55 */     Integer idDocumento = Integer.valueOf(request.getParameter("idDocumento"));
/*  53: 56 */     String numero = request.getParameter("numero");
/*  54: 57 */     Integer idLote = Integer.valueOf(request.getParameter("idLote"));
/*  55:    */     
/*  56: 59 */     HashMap<String, String> filters = new HashMap();
/*  57: 60 */     filters.put("organizacion.idOrganizacion", "=" + idOrganizacion);
/*  58: 61 */     List<OrganizacionConfiguracion> lista = this.servicioOrganizacionConfiguracion.obtenerListaCombo(OrganizacionConfiguracion.class, null, false, filters);
/*  59:    */     
/*  60:    */ 
/*  61: 64 */     int numeroAtributos = ((OrganizacionConfiguracion)lista.get(0)).getNumeroAtributos();
/*  62:    */     
/*  63: 66 */     List<String> fieldList = new ArrayList();
/*  64: 67 */     fieldList.add("f_numero");
/*  65: 68 */     fieldList.add("f_fecha");
/*  66: 69 */     fieldList.add("f_codigoLote");
/*  67: 70 */     fieldList.add("f_nombreComercialProducto");
/*  68: 71 */     fieldList.add("f_cantidad");
/*  69: 72 */     fieldList.add("f_nombreUnidad");
/*  70: 73 */     fieldList.add("f_descripcionProducto");
/*  71: 74 */     fieldList.add("f_proveedor");
/*  72: 75 */     fieldList.add("f_nombreProducto");
/*  73: 76 */     fieldList.add("f_codigoUnidad");
/*  74: 77 */     fieldList.add("f_fechaCreacion");
/*  75: 78 */     fieldList.add("f_maquina");
/*  76: 79 */     fieldList.add("f_responsable");
/*  77: 81 */     if (numeroAtributos > 0)
/*  78:    */     {
/*  79: 83 */       fieldList.add("f_codigoAtributoOFA");
/*  80: 84 */       fieldList.add("f_nombreAtributoOFA");
/*  81: 85 */       fieldList.add("f_codigoValorAtributoOFA");
/*  82: 86 */       fieldList.add("f_nombreValorAtributoOFA");
/*  83: 88 */       for (int i = 1; i <= numeroAtributos - 1; i++)
/*  84:    */       {
/*  85: 89 */         fieldList.add("f_codigoAtributo" + i);
/*  86: 90 */         fieldList.add("f_nombreAtributo" + i);
/*  87: 91 */         fieldList.add("f_codigoValorAtributo" + i);
/*  88: 92 */         fieldList.add("f_nombreValorAtributo" + i);
/*  89:    */       }
/*  90:    */     }
/*  91: 96 */     String[] fields = (String[])fieldList.toArray(new String[fieldList.size()]);
/*  92:    */     
/*  93: 98 */     List<Object[]> listaDatosReporte = this.servicioReporteMovimientoInventario.getDatosImpresionEtiquetaLote(idOrganizacion.intValue(), idDocumento.intValue(), numero, idLote
/*  94: 99 */       .intValue(), numeroAtributos);
/*  95:    */     
/*  96:101 */     JRDataSource jrDataSource = new QueryResultDataSource(listaDatosReporte, fields);
/*  97:    */     try
/*  98:    */     {
/*  99:105 */       ReportConfigUtil.compileReport(this.pathReportes, this.COMPILE_DIR, "reporteLoteEtiquetaIngresoFabricacion", false);
/* 100:    */       
/* 101:    */ 
/* 102:108 */       JasperReport reporte = (JasperReport)JRLoader.loadObjectFromFile(this.pathReportes + this.COMPILE_DIR + "reporteLoteEtiquetaIngresoFabricacion.jasper");
/* 103:    */       
/* 104:110 */       Map<String, Object> reportParameters = new HashMap();
/* 105:    */       
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:115 */       JasperPrint print = JasperFillManager.fillReport(reporte, reportParameters, jrDataSource);
/* 110:    */       
/* 111:117 */       JRExporter exporter = new JRPdfExporter();
/* 112:118 */       exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
/* 113:119 */       exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
/* 114:120 */       exporter.exportReport();
/* 115:    */     }
/* 116:    */     catch (JRException e)
/* 117:    */     {
/* 118:124 */       e.printStackTrace();
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   protected void doGet(HttpServletRequest request, HttpServletResponse response)
/* 123:    */     throws ServletException, IOException
/* 124:    */   {
/* 125:130 */     processRequest(request, response);
/* 126:    */   }
/* 127:    */   
/* 128:    */   protected void doPost(HttpServletRequest request, HttpServletResponse response)
/* 129:    */     throws ServletException, IOException
/* 130:    */   {
/* 131:135 */     processRequest(request, response);
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.servlet.ServletReporteEtiquetaIngresoFabricacion
 * JD-Core Version:    0.7.0.1
 */