/*   1:    */ package com.asinfo.as2.produccion.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Documento;
/*   5:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   8:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoInventario;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  11:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  20:    */ import net.sf.jasperreports.engine.JRException;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class ReporteEtiquetaProduccionBomBean
/*  26:    */   extends AbstractBaseReportBean
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @EJB
/*  30:    */   private ServicioReporteMovimientoInventario servicioReporteMovimientoInventario;
/*  31:    */   private MovimientoInventario transferencia;
/*  32: 45 */   private final String COMPILE_FILE_NAME = "reporteLoteEtiquetaIngresoFabricacion";
/*  33:    */   
/*  34:    */   protected JRDataSource getJRDataSource()
/*  35:    */   {
/*  36: 50 */     JRDataSource ds = null;
/*  37:    */     try
/*  38:    */     {
/*  39: 53 */       int numeroAtributos = AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos();
/*  40:    */       
/*  41: 55 */       List<String> fieldList = new ArrayList();
/*  42: 56 */       fieldList.add("f_numero");
/*  43: 57 */       fieldList.add("f_fecha");
/*  44: 58 */       fieldList.add("f_codigoLote");
/*  45: 59 */       fieldList.add("f_nombreComercialProducto");
/*  46: 60 */       fieldList.add("f_cantidad");
/*  47: 61 */       fieldList.add("f_nombreUnidad");
/*  48: 62 */       fieldList.add("f_descripcionProducto");
/*  49: 63 */       fieldList.add("f_proveedor");
/*  50: 64 */       fieldList.add("f_nombreProducto");
/*  51: 65 */       fieldList.add("f_codigoUnidad");
/*  52: 66 */       fieldList.add("f_fechaCreacion");
/*  53: 67 */       fieldList.add("f_maquina");
/*  54: 68 */       fieldList.add("f_responsable");
/*  55: 70 */       if (numeroAtributos > 0)
/*  56:    */       {
/*  57: 72 */         fieldList.add("f_codigoAtributoOFA");
/*  58: 73 */         fieldList.add("f_nombreAtributoOFA");
/*  59: 74 */         fieldList.add("f_codigoValorAtributoOFA");
/*  60: 75 */         fieldList.add("f_nombreValorAtributoOFA");
/*  61: 77 */         for (int i = 1; i <= numeroAtributos - 1; i++)
/*  62:    */         {
/*  63: 78 */           fieldList.add("f_codigoAtributo" + i);
/*  64: 79 */           fieldList.add("f_nombreAtributo" + i);
/*  65: 80 */           fieldList.add("f_codigoValorAtributo" + i);
/*  66: 81 */           fieldList.add("f_nombreValorAtributo" + i);
/*  67:    */         }
/*  68:    */       }
/*  69: 85 */       String[] fields = (String[])fieldList.toArray(new String[fieldList.size()]);
/*  70:    */       
/*  71: 87 */       List<Object[]> listaDatosReporte = this.servicioReporteMovimientoInventario.getDatosImpresionEtiquetaLote(
/*  72: 88 */         getTransferencia().getIdOrganizacion(), getTransferencia().getDocumentoDestino().getId(), getTransferencia().getNumero(), 0, numeroAtributos);
/*  73:    */       
/*  74:    */ 
/*  75: 91 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  76:    */     }
/*  77:    */     catch (Exception e)
/*  78:    */     {
/*  79: 93 */       e.printStackTrace();
/*  80:    */     }
/*  81: 95 */     return ds;
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected String getCompileFileName()
/*  85:    */   {
/*  86:100 */     return "reporteLoteEtiquetaIngresoFabricacion";
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected Map<String, Object> getReportParameters()
/*  90:    */   {
/*  91:106 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  92:107 */     reportParameters.put("ReportTitle", "Lote Etiqueta");
/*  93:108 */     return reportParameters;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String execute()
/*  97:    */   {
/*  98:    */     try
/*  99:    */     {
/* 100:117 */       super.prepareReport();
/* 101:    */     }
/* 102:    */     catch (JRException e)
/* 103:    */     {
/* 104:120 */       LOG.info("Error JRException");
/* 105:121 */       e.printStackTrace();
/* 106:122 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 107:    */     }
/* 108:    */     catch (IOException e)
/* 109:    */     {
/* 110:124 */       LOG.info("Error IOException");
/* 111:125 */       e.printStackTrace();
/* 112:126 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 113:    */     }
/* 114:129 */     return "";
/* 115:    */   }
/* 116:    */   
/* 117:    */   public MovimientoInventario getTransferencia()
/* 118:    */   {
/* 119:133 */     return this.transferencia;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setTransferencia(MovimientoInventario transferencia)
/* 123:    */   {
/* 124:137 */     this.transferencia = transferencia;
/* 125:    */   }
/* 126:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.controller.ReporteEtiquetaProduccionBomBean
 * JD-Core Version:    0.7.0.1
 */