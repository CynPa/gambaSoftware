/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.util.AppUtil;
/*   8:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   9:    */ import com.asinfo.as2.utils.reportes.AbstractClientReportBean;
/*  10:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  11:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Calendar;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.annotation.PostConstruct;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  22:    */ import net.sf.jasperreports.engine.JRException;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class ReporteComportamientoCobroClienteBean
/*  28:    */   extends AbstractClientReportBean
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = -3323908391222764008L;
/*  31:    */   @EJB
/*  32:    */   private transient ServicioReporteVenta servicioReporteVenta;
/*  33: 56 */   private static String COMPILE_FILE_NAME = "reporteComportamientoCobroCliente";
/*  34:    */   
/*  35:    */   protected String getCompileFileName()
/*  36:    */   {
/*  37: 65 */     return COMPILE_FILE_NAME;
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected Map<String, Object> getReportParameters()
/*  41:    */   {
/*  42: 75 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  43: 76 */     reportParameters.put("ReportTitle", "Reporte Comportamiento Cobro Cliente");
/*  44: 77 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/*  45: 78 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/*  46: 79 */     reportParameters.put("p_categoriaEmpresa", this.categoriaEmpresa != null ? this.categoriaEmpresa.getNombre() : "TODOS");
/*  47: 80 */     reportParameters.put("p_empresa", this.empresa != null ? this.empresa.getNombreFiscal() : "TODOS");
/*  48:    */     
/*  49: 82 */     return reportParameters;
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected JRDataSource getJRDataSource()
/*  53:    */   {
/*  54: 93 */     List listaDatosReporte = new ArrayList();
/*  55: 94 */     JRDataSource jrDataSource = null;
/*  56:    */     try
/*  57:    */     {
/*  58: 97 */       listaDatosReporte = this.servicioReporteVenta.getReporteComportamientoCobroCliente(AppUtil.getOrganizacion().getId(), this.fechaDesde, this.fechaHasta, this.categoriaEmpresa, this.empresa);
/*  59:    */       
/*  60:    */ 
/*  61:100 */       String[] fields = { "f_nombreCliente", "f_identificacionCliente", "f_fechaFactura", "f_numeroFactura", "f_fechaVencimiento", "f_fechaPago", "f_valorPago", "f_recaudador", "f_agenteComercial", "f_formaPago", "f_indicadorChequePosfechado", "f_numero", "f_estado", "f_fechaCobro" };
/*  62:    */       
/*  63:    */ 
/*  64:    */ 
/*  65:104 */       jrDataSource = new QueryResultDataSource(listaDatosReporte, fields);
/*  66:    */     }
/*  67:    */     catch (Exception e)
/*  68:    */     {
/*  69:107 */       e.printStackTrace();
/*  70:    */     }
/*  71:109 */     return jrDataSource;
/*  72:    */   }
/*  73:    */   
/*  74:    */   @PostConstruct
/*  75:    */   public void init()
/*  76:    */   {
/*  77:114 */     Calendar calfechaDesde = Calendar.getInstance();
/*  78:115 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  79:116 */     this.fechaDesde = calfechaDesde.getTime();
/*  80:117 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String execute()
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:127 */       super.prepareReport();
/*  88:    */     }
/*  89:    */     catch (JRException e)
/*  90:    */     {
/*  91:129 */       e.printStackTrace();
/*  92:130 */       LOG.info("Error JRException");
/*  93:131 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  94:    */     }
/*  95:    */     catch (IOException e)
/*  96:    */     {
/*  97:133 */       e.printStackTrace();
/*  98:134 */       LOG.info("Error IOException");
/*  99:135 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 100:    */     }
/* 101:    */     catch (Exception e)
/* 102:    */     {
/* 103:137 */       e.printStackTrace();
/* 104:    */     }
/* 105:140 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void actualizarCategoriaCliente()
/* 109:    */   {
/* 110:144 */     this.empresa = null;
/* 111:    */   }
/* 112:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteComportamientoCobroClienteBean
 * JD-Core Version:    0.7.0.1
 */