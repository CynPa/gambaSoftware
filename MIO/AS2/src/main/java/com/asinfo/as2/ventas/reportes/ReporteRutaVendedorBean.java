/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   5:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
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
/*  21:    */ public class ReporteRutaVendedorBean
/*  22:    */   extends AbstractBaseReportBean
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @EJB
/*  26:    */   private ServicioUsuario servicioUsuario;
/*  27:    */   private EntidadUsuario entidadUsuario;
/*  28:    */   public static final String COMPILE_FILE_NAME = "reporteRutaVendedor";
/*  29: 58 */   public static final String[] fields = { "f_nombreSector", "f_codigoSector", "f_usuarioNombres", "f_usuarioApellidos", "f_diaSemana" };
/*  30:    */   
/*  31:    */   protected JRDataSource getJRDataSource()
/*  32:    */   {
/*  33: 68 */     List listaDatosReporte = new ArrayList();
/*  34: 69 */     JRDataSource ds = null;
/*  35:    */     try
/*  36:    */     {
/*  37: 72 */       listaDatosReporte = this.servicioUsuario.listaRepoteRutaVendedor(getEntidadUsuario());
/*  38:    */       
/*  39: 74 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  40:    */     }
/*  41:    */     catch (Exception e)
/*  42:    */     {
/*  43: 76 */       e.printStackTrace();
/*  44:    */     }
/*  45: 78 */     return ds;
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected String getCompileFileName()
/*  49:    */   {
/*  50: 88 */     return "reporteRutaVendedor";
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected Map<String, Object> getReportParameters()
/*  54:    */   {
/*  55: 99 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  56:100 */     reportParameters.put("ReportTitle", "Ruta Vendedor");
/*  57:101 */     return reportParameters;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String execute()
/*  61:    */   {
/*  62:    */     try
/*  63:    */     {
/*  64:110 */       super.prepareReport();
/*  65:    */     }
/*  66:    */     catch (JRException e)
/*  67:    */     {
/*  68:113 */       LOG.info("Error JRException");
/*  69:114 */       e.printStackTrace();
/*  70:115 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  71:    */     }
/*  72:    */     catch (IOException e)
/*  73:    */     {
/*  74:117 */       LOG.info("Error IOException");
/*  75:118 */       e.printStackTrace();
/*  76:119 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  77:    */     }
/*  78:122 */     return "";
/*  79:    */   }
/*  80:    */   
/*  81:    */   public EntidadUsuario getEntidadUsuario()
/*  82:    */   {
/*  83:126 */     return this.entidadUsuario;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setEntidadUsuario(EntidadUsuario entidadUsuario)
/*  87:    */   {
/*  88:130 */     this.entidadUsuario = entidadUsuario;
/*  89:    */   }
/*  90:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteRutaVendedorBean
 * JD-Core Version:    0.7.0.1
 */