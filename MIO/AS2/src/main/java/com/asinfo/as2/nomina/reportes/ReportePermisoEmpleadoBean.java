/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.PermisoEmpleado;
/*   5:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   6:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPermisoEmpleado;
/*   7:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   8:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.faces.bean.ManagedBean;
/*  14:    */ import javax.faces.bean.ViewScoped;
/*  15:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  16:    */ import org.apache.log4j.Logger;
/*  17:    */ 
/*  18:    */ @ManagedBean
/*  19:    */ @ViewScoped
/*  20:    */ public class ReportePermisoEmpleadoBean
/*  21:    */   extends AbstractBaseReportBean
/*  22:    */ {
/*  23:    */   @EJB
/*  24:    */   private ServicioPermisoEmpleado servicioPermisoEmpleado;
/*  25:    */   private PermisoEmpleado permisoEmpleado;
/*  26: 44 */   private final String COMPILE_FILE_NAME = "permisoEmpleado";
/*  27:    */   
/*  28:    */   protected JRDataSource getJRDataSource()
/*  29:    */   {
/*  30: 54 */     List listaDatosReporte = new ArrayList();
/*  31: 55 */     JRDataSource ds = null;
/*  32:    */     try
/*  33:    */     {
/*  34: 57 */       listaDatosReporte = this.servicioPermisoEmpleado.getPersmisoEmpleado(this.permisoEmpleado.getId());
/*  35: 58 */       String[] fields = { "codigo", "identificacion", "apellidos", "nombres", "estadoCivil", "departamento", "cargoEmpleado", "titulo", "horaDesde", "horaHasta", "horas", "nota", "tipoPermiso", "f_fechaPermiso", "diaDesde", "diaHasta", "f_numero", "f_horasDetalle", "f_descripcionDetalle", "f_fechaPermisoDetalle", "f_usuarioElaboracion", "f_usuarioAprobacion" };
/*  36:    */       
/*  37:    */ 
/*  38: 61 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  39:    */     }
/*  40:    */     catch (ExcepcionAS2 e)
/*  41:    */     {
/*  42: 63 */       LOG.info("Error " + e);
/*  43: 64 */       e.printStackTrace();
/*  44:    */     }
/*  45: 66 */     return ds;
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected String getCompileFileName()
/*  49:    */   {
/*  50: 77 */     return "permisoEmpleado";
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected Map<String, Object> getReportParameters()
/*  54:    */   {
/*  55: 83 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  56: 84 */     reportParameters.put("ReportTitle", getLanguageController().getMensaje(""));
/*  57:    */     
/*  58: 86 */     return reportParameters;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String execute()
/*  62:    */   {
/*  63:    */     try
/*  64:    */     {
/*  65: 96 */       super.prepareReport();
/*  66:    */     }
/*  67:    */     catch (Exception e)
/*  68:    */     {
/*  69: 98 */       e.printStackTrace();
/*  70:    */     }
/*  71:101 */     return null;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String cargarEmpleado()
/*  75:    */   {
/*  76:106 */     return "";
/*  77:    */   }
/*  78:    */   
/*  79:    */   public PermisoEmpleado getPermisoEmpleado()
/*  80:    */   {
/*  81:115 */     return this.permisoEmpleado;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setPermisoEmpleado(PermisoEmpleado permisoEmpleado)
/*  85:    */   {
/*  86:125 */     this.permisoEmpleado = permisoEmpleado;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getCOMPILE_FILE_NAME()
/*  90:    */   {
/*  91:133 */     return "permisoEmpleado";
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReportePermisoEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */