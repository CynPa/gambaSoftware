/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   5:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   6:    */ import com.asinfo.as2.util.AppUtil;
/*   7:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   8:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   9:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioContratoVenta;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  18:    */ import net.sf.jasperreports.engine.JRException;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ 
/*  21:    */ @ManagedBean
/*  22:    */ @ViewScoped
/*  23:    */ public class ReporteContratoVentaDevengadoBean
/*  24:    */   extends AbstractBaseReportBean
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1599721133141713459L;
/*  27:    */   @EJB
/*  28:    */   private ServicioContratoVenta servicioContratoVenta;
/*  29: 38 */   private final String COMPILE_FILE_NAME = "reporteContratoVentaDevengado";
/*  30:    */   private InterfazContableProceso interfazContableProceso;
/*  31:    */   
/*  32:    */   protected JRDataSource getJRDataSource()
/*  33:    */   {
/*  34: 47 */     List listaDatosReporte = new ArrayList();
/*  35: 48 */     JRDataSource ds = null;
/*  36:    */     try
/*  37:    */     {
/*  38: 50 */       listaDatosReporte = this.servicioContratoVenta.getContratoVentaDevengado(getInterfazContableProceso(), AppUtil.getOrganizacion(), AppUtil.getSucursal());
/*  39:    */       
/*  40: 52 */       String[] fields = { "f_nombreFiscal", "f_numeroContratoVenta", "f_fechaEmision", "f_fechaVencimiento", "f_fecha", "f_valor", "f_valorContrato" };
/*  41: 53 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  42:    */     }
/*  43:    */     catch (ExcepcionAS2 e)
/*  44:    */     {
/*  45: 55 */       LOG.info("Error " + e);
/*  46: 56 */       e.printStackTrace();
/*  47:    */     }
/*  48: 58 */     return ds;
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected String getCompileFileName()
/*  52:    */   {
/*  53: 63 */     return "reporteContratoVentaDevengado";
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String execute()
/*  57:    */   {
/*  58:    */     try
/*  59:    */     {
/*  60: 69 */       super.prepareReport();
/*  61:    */     }
/*  62:    */     catch (JRException e)
/*  63:    */     {
/*  64: 71 */       LOG.info("Error JRException");
/*  65: 72 */       e.printStackTrace();
/*  66: 73 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  67:    */     }
/*  68:    */     catch (IOException e)
/*  69:    */     {
/*  70: 75 */       LOG.info("Error IOException");
/*  71: 76 */       e.printStackTrace();
/*  72: 77 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  73:    */     }
/*  74: 80 */     return null;
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected Map<String, Object> getReportParameters()
/*  78:    */   {
/*  79: 85 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  80: 86 */     reportParameters.put("ReportTitle", "Devengado");
/*  81:    */     
/*  82:    */ 
/*  83: 89 */     return reportParameters;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public InterfazContableProceso getInterfazContableProceso()
/*  87:    */   {
/*  88: 93 */     if (this.interfazContableProceso == null) {
/*  89: 94 */       this.interfazContableProceso = new InterfazContableProceso();
/*  90:    */     }
/*  91: 96 */     return this.interfazContableProceso;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/*  95:    */   {
/*  96:101 */     this.interfazContableProceso = interfazContableProceso;
/*  97:    */   }
/*  98:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteContratoVentaDevengadoBean
 * JD-Core Version:    0.7.0.1
 */