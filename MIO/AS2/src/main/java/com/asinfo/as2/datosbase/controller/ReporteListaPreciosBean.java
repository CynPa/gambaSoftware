/*   1:    */ package com.asinfo.as2.datosbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   5:    */ import com.asinfo.as2.entities.ListaPrecios;
/*   6:    */ import com.asinfo.as2.entities.VersionListaPrecios;
/*   7:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   8:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.faces.bean.ManagedBean;
/*  15:    */ import javax.faces.bean.ViewScoped;
/*  16:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  17:    */ import net.sf.jasperreports.engine.JRException;
/*  18:    */ import org.apache.log4j.Logger;
/*  19:    */ 
/*  20:    */ @ManagedBean
/*  21:    */ @ViewScoped
/*  22:    */ public class ReporteListaPreciosBean
/*  23:    */   extends AbstractBaseReportBean
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 770222109067650882L;
/*  26:    */   @EJB
/*  27:    */   private ServicioListaPrecios servicioListaPrecios;
/*  28: 34 */   private final String COMPILE_FILE_NAME = "reporteListaPrecios";
/*  29:    */   private ListaPrecios listaPrecios;
/*  30: 37 */   private VersionListaPrecios versionlistaPrecios = null;
/*  31:    */   
/*  32:    */   protected JRDataSource getJRDataSource()
/*  33:    */   {
/*  34: 42 */     this.versionlistaPrecios = getVersionListaPrecios();
/*  35: 43 */     List listaDatosReporte = new ArrayList();
/*  36: 44 */     JRDataSource ds = null;
/*  37:    */     try
/*  38:    */     {
/*  39: 46 */       if (this.versionlistaPrecios != null) {
/*  40: 47 */         listaDatosReporte = this.servicioListaPrecios.getReporteListaPrecios(this.versionlistaPrecios.getIdVersionListaPrecios());
/*  41:    */       }
/*  42: 50 */       String[] fields = { "f_codigo", "f_producto", "f_precioCompra", "f_precioU", "f_precioCliente" };
/*  43:    */       
/*  44: 52 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  45:    */     }
/*  46:    */     catch (Exception e)
/*  47:    */     {
/*  48: 54 */       e.printStackTrace();
/*  49:    */     }
/*  50: 56 */     return ds;
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected String getCompileFileName()
/*  54:    */   {
/*  55: 61 */     return "reporteListaPrecios";
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected Map<String, Object> getReportParameters()
/*  59:    */   {
/*  60: 66 */     String tipo = getListaPrecios().isIndicadorVenta() == true ? "Venta" : getListaPrecios().isIndicadorCompra() == true ? "Compra" : "-";
/*  61:    */     
/*  62: 68 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  63: 69 */     reportParameters.put("ReportTitle", "Lista de Precios");
/*  64: 70 */     reportParameters.put("p_codigo", getListaPrecios().getCodigo());
/*  65: 71 */     reportParameters.put("p_nombre", getListaPrecios().getNombre());
/*  66: 72 */     reportParameters.put("p_descripcion", getListaPrecios().getDescripcion());
/*  67: 73 */     reportParameters.put("p_tipo", tipo);
/*  68: 74 */     this.versionlistaPrecios = getVersionListaPrecios();
/*  69: 75 */     if (this.versionlistaPrecios != null)
/*  70:    */     {
/*  71: 76 */       reportParameters.put("p_versionCodigo", this.versionlistaPrecios.getCodigo());
/*  72: 77 */       reportParameters.put("p_versionNombre", this.versionlistaPrecios.getNombre());
/*  73: 78 */       reportParameters.put("p_versionDesde", this.versionlistaPrecios.getValidoDesde());
/*  74: 79 */       reportParameters.put("p_versionHasta", this.versionlistaPrecios.getValidoHasta());
/*  75:    */     }
/*  76: 81 */     return reportParameters;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String execute()
/*  80:    */   {
/*  81:    */     try
/*  82:    */     {
/*  83: 90 */       super.prepareReport();
/*  84:    */     }
/*  85:    */     catch (JRException e)
/*  86:    */     {
/*  87: 93 */       LOG.info("Error JRException");
/*  88: 94 */       e.printStackTrace();
/*  89: 95 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  90:    */     }
/*  91:    */     catch (IOException e)
/*  92:    */     {
/*  93: 97 */       LOG.info("Error IOException");
/*  94: 98 */       e.printStackTrace();
/*  95: 99 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  96:    */     }
/*  97:102 */     return "";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public ListaPrecios getListaPrecios()
/* 101:    */   {
/* 102:106 */     if (this.listaPrecios == null) {
/* 103:107 */       this.listaPrecios = new ListaPrecios();
/* 104:    */     }
/* 105:110 */     return this.listaPrecios;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setListaPrecios(ListaPrecios listaPrecios)
/* 109:    */   {
/* 110:114 */     this.listaPrecios = listaPrecios;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public VersionListaPrecios getVersionListaPrecios()
/* 114:    */   {
/* 115:123 */     this.versionlistaPrecios = this.servicioListaPrecios.getUltimaVersionListaPrecios(getListaPrecios().getIdListaPrecios());
/* 116:124 */     return this.versionlistaPrecios;
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.ReporteListaPreciosBean
 * JD-Core Version:    0.7.0.1
 */