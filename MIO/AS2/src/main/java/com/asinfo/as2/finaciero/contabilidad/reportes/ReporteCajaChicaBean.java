/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.CajaChica;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCompraCajaChica;
/*   7:    */ import com.asinfo.as2.util.AppUtil;
/*   8:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   9:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  10:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.faces.bean.ManagedBean;
/*  17:    */ import javax.faces.bean.RequestScoped;
/*  18:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  19:    */ import net.sf.jasperreports.engine.JRException;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ 
/*  22:    */ @ManagedBean
/*  23:    */ @RequestScoped
/*  24:    */ public class ReporteCajaChicaBean
/*  25:    */   extends AbstractBaseReportBean
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @EJB
/*  29:    */   private ServicioCompraCajaChica servicioCompraCajaChica;
/*  30:    */   private CajaChica cajaChica;
/*  31:    */   
/*  32:    */   protected JRDataSource getJRDataSource()
/*  33:    */   {
/*  34: 52 */     List listaDatosReporte = new ArrayList();
/*  35: 53 */     JRDataSource ds = null;
/*  36:    */     try
/*  37:    */     {
/*  38: 57 */       listaDatosReporte = this.servicioCompraCajaChica.getListaReporteCompraCajaChica(FuncionesUtiles.obtenerFechaInicial(), 
/*  39: 58 */         FuncionesUtiles.obtenerFechaFinal(), "", "", getCajaChica().getId(), -1, AppUtil.getOrganizacion().getIdOrganizacion(), true);
/*  40:    */       
/*  41: 60 */       String[] fields = { "f_codigoCajaChica", "f_nombreCajaChica", "f_numeroFactura", "f_fechaFactura", "f_indicadorFactura", "f_empresa", "f_subTotal", "f_descuento", "f_impuestos", "f_descripcion", "f_codigoEmpresa", "f_nombreCategoriaEmpresa", "f_identificacionEmpresa", "f_numeroCompra", "f_SRIfechaEmision", "f_SRIautorizacion", "f_SRIautorizacionRetencion", "f_SRIbaseImponibleTarifaCero", "f_SRIbaseImponibleDiferenteCero", "f_SRIclaveAccesoRetencion", "f_descuentoSolidario" };
/*  42:    */       
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47: 66 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  48:    */     }
/*  49:    */     catch (Exception e)
/*  50:    */     {
/*  51: 68 */       LOG.info("Error " + e);
/*  52: 69 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  53:    */     }
/*  54: 71 */     return ds;
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected String getCompileFileName()
/*  58:    */   {
/*  59: 81 */     return "reporteComprasResumido";
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected Map<String, Object> getReportParameters()
/*  63:    */   {
/*  64: 92 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  65: 93 */     reportParameters.put("ReportTitle", "Caja Chica");
/*  66: 94 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.cajaChica.getFechaDesde()));
/*  67: 95 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.cajaChica.getFechaHasta()));
/*  68: 96 */     reportParameters.put("NumeroDesde", "");
/*  69: 97 */     reportParameters.put("NumeroHasta", "");
/*  70: 98 */     reportParameters.put("Total", "Total");
/*  71: 99 */     reportParameters.put("indicadorFecha", Boolean.valueOf(false));
/*  72:100 */     reportParameters.put("indicadorCajaChica", Boolean.valueOf(true));
/*  73:101 */     return reportParameters;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String execute()
/*  77:    */   {
/*  78:    */     try
/*  79:    */     {
/*  80:110 */       super.prepareReport();
/*  81:    */     }
/*  82:    */     catch (JRException e)
/*  83:    */     {
/*  84:113 */       LOG.info("Error JRException");
/*  85:114 */       e.printStackTrace();
/*  86:115 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  87:    */     }
/*  88:    */     catch (IOException e)
/*  89:    */     {
/*  90:117 */       LOG.info("Error IOException");
/*  91:118 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  92:    */     }
/*  93:121 */     return "";
/*  94:    */   }
/*  95:    */   
/*  96:    */   public CajaChica getCajaChica()
/*  97:    */   {
/*  98:130 */     if (this.cajaChica == null) {
/*  99:131 */       this.cajaChica = new CajaChica();
/* 100:    */     }
/* 101:133 */     return this.cajaChica;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setCajaChica(CajaChica cajaChica)
/* 105:    */   {
/* 106:143 */     this.cajaChica = cajaChica;
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.ReporteCajaChicaBean
 * JD-Core Version:    0.7.0.1
 */