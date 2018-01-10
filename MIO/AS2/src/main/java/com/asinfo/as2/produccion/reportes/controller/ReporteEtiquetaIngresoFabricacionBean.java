/*   1:    */ package com.asinfo.as2.produccion.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   5:    */ import com.asinfo.as2.entities.Documento;
/*   6:    */ import com.asinfo.as2.entities.Lote;
/*   7:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  10:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoInventario;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  13:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  14:    */ import java.io.IOException;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  21:    */ import net.sf.jasperreports.engine.JRException;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class ReporteEtiquetaIngresoFabricacionBean
/*  27:    */   extends AbstractBaseReportBean
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private ServicioReporteMovimientoInventario servicioReporteMovimientoInventario;
/*  32:    */   private DetalleMovimientoInventario detalleMovimientoInventario;
/*  33: 52 */   private final String COMPILE_FILE_NAME = "reporteLoteEtiqueta";
/*  34:    */   
/*  35:    */   protected JRDataSource getJRDataSource()
/*  36:    */   {
/*  37: 58 */     JRDataSource ds = null;
/*  38:    */     try
/*  39:    */     {
/*  40: 61 */       String[] fields = new String[50];
/*  41:    */       
/*  42: 63 */       fields[0] = "f_numero";
/*  43: 64 */       fields[1] = "f_fecha";
/*  44: 65 */       fields[2] = "f_codigoLote";
/*  45: 66 */       fields[3] = "f_nombreComercialProducto";
/*  46: 67 */       fields[4] = "f_cantidad";
/*  47: 68 */       fields[5] = "f_nombreUnidad";
/*  48: 69 */       fields[6] = "f_descripcionProducto";
/*  49: 70 */       fields[7] = "f_proveedor";
/*  50: 71 */       fields[8] = "f_nombreProducto";
/*  51: 72 */       fields[9] = "f_codigoUnidad";
/*  52:    */       
/*  53: 74 */       fields[10] = "f_codigoAtributoOFA";
/*  54: 75 */       fields[11] = "f_nombreAtributoOFA";
/*  55: 76 */       fields[12] = "f_codigoValorAtributoOFA";
/*  56: 77 */       fields[13] = "f_nombreValorAtributoOFA";
/*  57:    */       
/*  58: 79 */       int index = 14;
/*  59: 80 */       for (int i = 1; i <= 9; i++)
/*  60:    */       {
/*  61: 81 */         fields[(index++)] = ("f_codigoAtributo" + i);
/*  62: 82 */         fields[(index++)] = ("f_nombreAtributo" + i);
/*  63: 83 */         fields[(index++)] = ("f_codigoValorAtributo" + i);
/*  64: 84 */         fields[(index++)] = ("f_nombreValorAtributo" + i);
/*  65:    */       }
/*  66: 87 */       List<Object[]> listaDatosReporte = this.servicioReporteMovimientoInventario.getDatosImpresionEtiquetaLote(AppUtil.getOrganizacion().getId(), 
/*  67: 88 */         getDetalleMovimientoInventario().getMovimientoInventario().getDocumento().getId(), 
/*  68: 89 */         getDetalleMovimientoInventario().getMovimientoInventario().getNumero(), getDetalleMovimientoInventario().getLote().getId(), 
/*  69: 90 */         AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/*  70:    */       
/*  71: 92 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  72:    */     }
/*  73:    */     catch (Exception e)
/*  74:    */     {
/*  75: 94 */       e.printStackTrace();
/*  76:    */     }
/*  77: 96 */     return ds;
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected String getCompileFileName()
/*  81:    */   {
/*  82:101 */     return "reporteLoteEtiqueta";
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected Map<String, Object> getReportParameters()
/*  86:    */   {
/*  87:107 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  88:108 */     reportParameters.put("ReportTitle", "Lote Etiqueta");
/*  89:109 */     return reportParameters;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String execute()
/*  93:    */   {
/*  94:    */     try
/*  95:    */     {
/*  96:118 */       super.prepareReport();
/*  97:    */     }
/*  98:    */     catch (JRException e)
/*  99:    */     {
/* 100:121 */       LOG.info("Error JRException");
/* 101:122 */       e.printStackTrace();
/* 102:123 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 103:    */     }
/* 104:    */     catch (IOException e)
/* 105:    */     {
/* 106:125 */       LOG.info("Error IOException");
/* 107:126 */       e.printStackTrace();
/* 108:127 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 109:    */     }
/* 110:130 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public DetalleMovimientoInventario getDetalleMovimientoInventario()
/* 114:    */   {
/* 115:134 */     return this.detalleMovimientoInventario;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setDetalleMovimientoInventario(DetalleMovimientoInventario detalleMovimientoInventario)
/* 119:    */   {
/* 120:138 */     this.detalleMovimientoInventario = detalleMovimientoInventario;
/* 121:    */   }
/* 122:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.controller.ReporteEtiquetaIngresoFabricacionBean
 * JD-Core Version:    0.7.0.1
 */