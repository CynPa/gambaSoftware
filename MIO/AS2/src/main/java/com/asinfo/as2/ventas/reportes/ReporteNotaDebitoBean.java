/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Documento;
/*   5:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   6:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*   7:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   8:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   9:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.RequestScoped;
/*  17:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  18:    */ import net.sf.jasperreports.engine.JRException;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ 
/*  21:    */ @ManagedBean
/*  22:    */ @RequestScoped
/*  23:    */ public class ReporteNotaDebitoBean
/*  24:    */   extends AbstractBaseReportBean
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @EJB
/*  28:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  29:    */   private FacturaCliente notaDebito;
/*  30: 33 */   private String COMPILE_FILE_NAME = "reporteNotaDebitoCliente";
/*  31:    */   
/*  32:    */   protected JRDataSource getJRDataSource()
/*  33:    */   {
/*  34: 43 */     List listaDatosReporte = new ArrayList();
/*  35: 44 */     JRDataSource ds = null;
/*  36:    */     try
/*  37:    */     {
/*  38: 46 */       boolean indicadorDetallado = false;
/*  39: 47 */       if (getNotaDebito().getDocumento().isIndicadorDocumentoElectronico()) {
/*  40: 48 */         indicadorDetallado = true;
/*  41:    */       }
/*  42: 50 */       listaDatosReporte = this.servicioFacturaCliente.getReporteFacturaCliente(getNotaDebito().getIdFacturaCliente(), TipoOrganizacion.TIPO_ORGANIZACION_GENERAL, 1, indicadorDetallado, true, 
/*  43: 51 */         getNotaDebito().getIdOrganizacion());
/*  44:    */       
/*  45: 53 */       ds = new QueryResultDataSource(listaDatosReporte, ReporteFacturaClienteBean.fields);
/*  46:    */     }
/*  47:    */     catch (Exception e)
/*  48:    */     {
/*  49: 55 */       LOG.info("Error " + e);
/*  50: 56 */       e.printStackTrace();
/*  51:    */     }
/*  52: 58 */     return ds;
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected String getCompileFileName()
/*  56:    */   {
/*  57: 68 */     if (!this.notaDebito.getDocumento().getReporte().isEmpty()) {
/*  58: 69 */       this.COMPILE_FILE_NAME = this.notaDebito.getDocumento().getReporte();
/*  59:    */     }
/*  60: 71 */     return this.COMPILE_FILE_NAME;
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected Map<String, Object> getReportParameters()
/*  64:    */   {
/*  65: 81 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  66: 82 */     reportParameters.put("ReportTitle", "Nota de Debito");
/*  67: 83 */     return reportParameters;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String execute()
/*  71:    */   {
/*  72: 93 */     setDocumentoElectronico(this.notaDebito.getDocumento().isIndicadorDocumentoElectronico());
/*  73:    */     try
/*  74:    */     {
/*  75: 96 */       super.prepareReport();
/*  76:    */     }
/*  77:    */     catch (JRException e)
/*  78:    */     {
/*  79: 98 */       LOG.info("Error JRException");
/*  80: 99 */       e.printStackTrace();
/*  81:100 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  82:    */     }
/*  83:    */     catch (IOException e)
/*  84:    */     {
/*  85:102 */       LOG.info("Error IOException");
/*  86:103 */       e.printStackTrace();
/*  87:104 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  88:    */     }
/*  89:106 */     return "";
/*  90:    */   }
/*  91:    */   
/*  92:    */   public FacturaCliente getNotaDebito()
/*  93:    */   {
/*  94:110 */     if (this.notaDebito == null) {
/*  95:111 */       this.notaDebito = new FacturaCliente();
/*  96:    */     }
/*  97:113 */     return this.notaDebito;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setNotaDebito(FacturaCliente notaDebito)
/* 101:    */   {
/* 102:117 */     this.notaDebito = notaDebito;
/* 103:    */   }
/* 104:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteNotaDebitoBean
 * JD-Core Version:    0.7.0.1
 */