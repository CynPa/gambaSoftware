/*  1:   */ package com.asinfo.as2.finaciero.contabilidad.reportes.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Organizacion;
/*  4:   */ import com.asinfo.as2.entities.presupuesto.Presupuesto;
/*  5:   */ import com.asinfo.as2.financiero.presupuesto.procesos.servicio.ServicioPresupuesto;
/*  6:   */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  7:   */ import com.asinfo.as2.util.AppUtil;
/*  8:   */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  9:   */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/* 10:   */ import java.util.ArrayList;
/* 11:   */ import java.util.List;
/* 12:   */ import java.util.Map;
/* 13:   */ import javax.ejb.EJB;
/* 14:   */ import javax.faces.bean.ManagedBean;
/* 15:   */ import javax.faces.bean.RequestScoped;
/* 16:   */ import net.sf.jasperreports.engine.JRDataSource;
/* 17:   */ 
/* 18:   */ @ManagedBean
/* 19:   */ @RequestScoped
/* 20:   */ public class ReportePresupuestoNormalBean
/* 21:   */   extends AbstractBaseReportBean
/* 22:   */ {
/* 23:   */   private static final long serialVersionUID = 2208238692001035925L;
/* 24:   */   @EJB
/* 25:   */   private ServicioPresupuesto servicioPresupuesto;
/* 26:   */   private Presupuesto presupuesto;
/* 27:   */   
/* 28:   */   protected JRDataSource getJRDataSource()
/* 29:   */   {
/* 30:34 */     List<Object[]> listaDatosReporte = new ArrayList();
/* 31:35 */     JRDataSource ds = null;
/* 32:   */     
/* 33:37 */     String[] fields = { "f_ejercicioNombre", "f_dimensionCodigo", "f_dimensionNombre", "f_cuentaContableCodigo", "f_cuentaContableNombre", "f_valorEnero", "f_valorFebrero", "f_valorMarzo", "f_valorAbril", "f_valorMayo", "f_valorJunio", "f_valorJulio", "f_valorAgosto", "f_valorSeptiembre", "f_valorOctubre", "f_valorNoviembre", "f_valorDiciembre" };
/* 34:   */     
/* 35:   */ 
/* 36:40 */     listaDatosReporte = this.servicioPresupuesto.getReportePresupuesto(getPresupuesto().getId(), AppUtil.getOrganizacion().getId(), 
/* 37:41 */       AppUtil.getUsuarioEnSesion().getIdUsuario());
/* 38:42 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 39:43 */     return ds;
/* 40:   */   }
/* 41:   */   
/* 42:   */   protected String getCompileFileName()
/* 43:   */   {
/* 44:48 */     return "reportePresupuesto";
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String execute()
/* 48:   */   {
/* 49:   */     try
/* 50:   */     {
/* 51:54 */       super.prepareReport();
/* 52:   */     }
/* 53:   */     catch (Exception e)
/* 54:   */     {
/* 55:56 */       e.printStackTrace();
/* 56:   */     }
/* 57:58 */     return null;
/* 58:   */   }
/* 59:   */   
/* 60:   */   protected Map<String, Object> getReportParameters()
/* 61:   */   {
/* 62:63 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 63:64 */     reportParameters.put("ReportTitle", "Presupuesto");
/* 64:   */     
/* 65:66 */     return reportParameters;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public Presupuesto getPresupuesto()
/* 69:   */   {
/* 70:70 */     return this.presupuesto;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public void setPresupuesto(Presupuesto presupuesto)
/* 74:   */   {
/* 75:74 */     this.presupuesto = presupuesto;
/* 76:   */   }
/* 77:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.controller.ReportePresupuestoNormalBean
 * JD-Core Version:    0.7.0.1
 */