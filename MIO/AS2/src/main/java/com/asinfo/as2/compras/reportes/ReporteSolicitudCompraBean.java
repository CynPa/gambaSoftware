/*   1:    */ package com.asinfo.as2.compras.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioSolicitudCompra;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.SolicitudCompra;
/*   6:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   7:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.faces.bean.ManagedBean;
/*  14:    */ import javax.faces.bean.ViewScoped;
/*  15:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  16:    */ import net.sf.jasperreports.engine.JRException;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ 
/*  19:    */ @ManagedBean
/*  20:    */ @ViewScoped
/*  21:    */ public class ReporteSolicitudCompraBean
/*  22:    */   extends AbstractBaseReportBean
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @EJB
/*  26:    */   private ServicioSolicitudCompra servicioSolicitudCompra;
/*  27:    */   private SolicitudCompra solicitudCompra;
/*  28:    */   public static final String COMPILE_FILE_NAME = "reporteSolicitudCompra";
/*  29: 42 */   private final String COMPILE_FILE_NAME_CONSOLIDACION = "reporteConsolidacionCompra";
/*  30: 43 */   public static final String[] fields = { "f_fecha", "f_numero", "f_descripcionSolicitudCompra", "f_empleado", "f_estado", "f_sucursal", "f_codigoProducto", "f_nombreProducto", "f_unidad", "f_cantidadPorAprobar", "f_cantidadOriginal", "f_descripcionDetalle", "f_numeroConsolidacion", "f_empleadoDetalle" };
/*  31:    */   
/*  32:    */   protected JRDataSource getJRDataSource()
/*  33:    */   {
/*  34: 49 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  35: 50 */     JRDataSource ds = null;
/*  36:    */     try
/*  37:    */     {
/*  38: 52 */       if (this.solicitudCompra.isIndicadorConsolidado()) {
/*  39: 54 */         listaDatosReporte = this.servicioSolicitudCompra.getReporteConsolidacionCompra(this.servicioSolicitudCompra.cargarDetalle(this.solicitudCompra.getId()));
/*  40:    */       } else {
/*  41: 56 */         listaDatosReporte = this.servicioSolicitudCompra.getReporteSolicitudCompra(this.solicitudCompra.getId());
/*  42:    */       }
/*  43: 59 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  44:    */     }
/*  45:    */     catch (Exception e)
/*  46:    */     {
/*  47: 61 */       e.printStackTrace();
/*  48:    */     }
/*  49: 63 */     return ds;
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected String getCompileFileName()
/*  53:    */   {
/*  54: 68 */     if (this.solicitudCompra.isIndicadorConsolidado()) {
/*  55: 69 */       return "reporteConsolidacionCompra";
/*  56:    */     }
/*  57: 71 */     return "reporteSolicitudCompra";
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String execute()
/*  61:    */   {
/*  62:    */     try
/*  63:    */     {
/*  64: 79 */       super.prepareReport();
/*  65:    */     }
/*  66:    */     catch (JRException e)
/*  67:    */     {
/*  68: 81 */       LOG.info("Error JRException");
/*  69: 82 */       e.printStackTrace();
/*  70: 83 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  71:    */     }
/*  72:    */     catch (IOException e)
/*  73:    */     {
/*  74: 85 */       LOG.info("Error IOException");
/*  75: 86 */       e.printStackTrace();
/*  76: 87 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  77:    */     }
/*  78: 90 */     return null;
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected Map<String, Object> getReportParameters()
/*  82:    */   {
/*  83: 95 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  84: 96 */     reportParameters.put("ReportTitle", "Solicitud Compra");
/*  85: 97 */     if (this.solicitudCompra.isIndicadorConsolidado())
/*  86:    */     {
/*  87: 98 */       reportParameters.put("SUBREPORT_DIR", getPathReportes());
/*  88: 99 */       String[] fields = { "f_empresa", "f_codigoProducto", "f_nombreProducto", "f_unidad", "f_cantidad", "f_precio", "f_total", "f_indicadorEnPedido" };
/*  89:    */       
/*  90:    */ 
/*  91:102 */       List<Object[]> listaProductosConsolidados = this.servicioSolicitudCompra.getReporteProductosConsolidados(this.solicitudCompra.getId());
/*  92:103 */       JRDataSource ds1 = new QueryResultDataSource(listaProductosConsolidados, fields);
/*  93:    */       
/*  94:105 */       reportParameters.put("listaProductosConsolidados", ds1);
/*  95:    */     }
/*  96:107 */     return reportParameters;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public SolicitudCompra getSolicitudCompra()
/* 100:    */   {
/* 101:111 */     return this.solicitudCompra;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setSolicitudCompra(SolicitudCompra solicitudCompra)
/* 105:    */   {
/* 106:115 */     this.solicitudCompra = solicitudCompra;
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.ReporteSolicitudCompraBean
 * JD-Core Version:    0.7.0.1
 */