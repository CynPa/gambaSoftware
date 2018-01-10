/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Empleado;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*   7:    */ import com.asinfo.as2.util.AppUtil;
/*   8:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   9:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.faces.bean.ManagedBean;
/*  17:    */ import javax.faces.bean.ViewScoped;
/*  18:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  19:    */ import net.sf.jasperreports.engine.JRException;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ 
/*  22:    */ @ManagedBean
/*  23:    */ @ViewScoped
/*  24:    */ public class ReporteRubroAsignadoBean
/*  25:    */   extends AbstractBaseReportBean
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @EJB
/*  29:    */   private ServicioReporteNomina servicioReporteNomina;
/*  30:    */   @EJB
/*  31:    */   private ServicioEmpleado servicioEmpleado;
/*  32:    */   private Empleado empleado;
/*  33: 46 */   private boolean activo = true;
/*  34:    */   
/*  35:    */   protected JRDataSource getJRDataSource()
/*  36:    */   {
/*  37: 51 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  38: 52 */     JRDataSource ds = null;
/*  39:    */     try
/*  40:    */     {
/*  41: 55 */       listaDatosReporte = this.servicioReporteNomina.getReporteRubroAsignado(AppUtil.getOrganizacion().getIdOrganizacion(), this.empleado, this.activo);
/*  42:    */       
/*  43: 57 */       String[] fields = { "f_nombreFiscal", "f_nombreComercial", "f_rubro", "f_identificacion", "f_valorRubro", "f_codigoRubro" };
/*  44:    */       
/*  45: 59 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  46:    */     }
/*  47:    */     catch (Exception e)
/*  48:    */     {
/*  49: 61 */       e.printStackTrace();
/*  50:    */     }
/*  51: 63 */     return ds;
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected String getCompileFileName()
/*  55:    */   {
/*  56: 68 */     return "reporteRubroAsignado";
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String execute()
/*  60:    */   {
/*  61:    */     try
/*  62:    */     {
/*  63: 78 */       super.prepareReport();
/*  64:    */     }
/*  65:    */     catch (JRException e)
/*  66:    */     {
/*  67: 80 */       LOG.info("Error JRException");
/*  68: 81 */       e.printStackTrace();
/*  69: 82 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  70:    */     }
/*  71:    */     catch (IOException e)
/*  72:    */     {
/*  73: 84 */       LOG.info("Error IOException");
/*  74: 85 */       e.printStackTrace();
/*  75: 86 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  76:    */     }
/*  77: 89 */     return null;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public List<Empleado> autocompletarEmpleado(String consulta)
/*  81:    */   {
/*  82: 94 */     Map<String, String> filters = new HashMap();
/*  83: 95 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  84: 96 */     filters.put("empresa.activo", "true");
/*  85: 97 */     filters.put("apellidos", consulta);
/*  86: 98 */     return this.servicioEmpleado.obtenerListaCombo("", true, filters);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Empleado getEmpleado()
/*  90:    */   {
/*  91:103 */     return this.empleado;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setEmpleado(Empleado empleado)
/*  95:    */   {
/*  96:107 */     this.empleado = empleado;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isActivo()
/* 100:    */   {
/* 101:114 */     return this.activo;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setActivo(boolean activo)
/* 105:    */   {
/* 106:122 */     this.activo = activo;
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteRubroAsignadoBean
 * JD-Core Version:    0.7.0.1
 */