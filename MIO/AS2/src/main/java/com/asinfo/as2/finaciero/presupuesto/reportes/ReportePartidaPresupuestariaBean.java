/*  1:   */ package com.asinfo.as2.finaciero.presupuesto.reportes;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Organizacion;
/*  4:   */ import com.asinfo.as2.entities.presupuesto.PartidaPresupuestaria;
/*  5:   */ import com.asinfo.as2.financiero.presupuesto.configuracion.servicio.ServicioPartidaPresupuestaria;
/*  6:   */ import com.asinfo.as2.util.AppUtil;
/*  7:   */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  8:   */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  9:   */ import java.util.ArrayList;
/* 10:   */ import java.util.List;
/* 11:   */ import java.util.Map;
/* 12:   */ import javax.ejb.EJB;
/* 13:   */ import javax.faces.bean.ManagedBean;
/* 14:   */ import javax.faces.bean.RequestScoped;
/* 15:   */ import net.sf.jasperreports.engine.JRDataSource;
/* 16:   */ 
/* 17:   */ @ManagedBean
/* 18:   */ @RequestScoped
/* 19:   */ public class ReportePartidaPresupuestariaBean
/* 20:   */   extends AbstractBaseReportBean
/* 21:   */ {
/* 22:   */   private static final long serialVersionUID = 856813209108522106L;
/* 23:   */   @EJB
/* 24:   */   private ServicioPartidaPresupuestaria servicioPartidaPresupuestaria;
/* 25:   */   private PartidaPresupuestaria partidaPresupuestaria;
/* 26:   */   
/* 27:   */   protected JRDataSource getJRDataSource()
/* 28:   */   {
/* 29:34 */     List<Object[]> listaDatosReporte = new ArrayList();
/* 30:35 */     JRDataSource ds = null;
/* 31:36 */     String[] fields = { "f_nivelNombre", "f_grupoNombre", "f_partidaPadreNombre", "f_partidaCodigo", "f_partidaNombre", "f_partidaIndicadorMovimiento", "f_partidadescribcion", "f_cuentaContableCodigo", "f_cuentaContableNombre" };
/* 32:   */     
/* 33:   */ 
/* 34:39 */     listaDatosReporte = this.servicioPartidaPresupuestaria.getReportePartidaPresupuestaria(getPartidaPresupuestaria() == null ? 0 : getPartidaPresupuestaria().getId(), AppUtil.getOrganizacion().getId());
/* 35:40 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 36:41 */     return ds;
/* 37:   */   }
/* 38:   */   
/* 39:   */   protected String getCompileFileName()
/* 40:   */   {
/* 41:46 */     return "reportePartidaPresupuestaria";
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String execute()
/* 45:   */   {
/* 46:   */     try
/* 47:   */     {
/* 48:52 */       super.prepareReport();
/* 49:   */     }
/* 50:   */     catch (Exception e)
/* 51:   */     {
/* 52:54 */       e.printStackTrace();
/* 53:   */     }
/* 54:56 */     return null;
/* 55:   */   }
/* 56:   */   
/* 57:   */   protected Map<String, Object> getReportParameters()
/* 58:   */   {
/* 59:62 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 60:63 */     reportParameters.put("ReportTitle", "Partida Presupuestaria");
/* 61:   */     
/* 62:65 */     return reportParameters;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public PartidaPresupuestaria getPartidaPresupuestaria()
/* 66:   */   {
/* 67:69 */     return this.partidaPresupuestaria;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public void setPartidaPresupuestaria(PartidaPresupuestaria partidaPresupuestaria)
/* 71:   */   {
/* 72:73 */     this.partidaPresupuestaria = partidaPresupuestaria;
/* 73:   */   }
/* 74:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.presupuesto.reportes.ReportePartidaPresupuestariaBean
 * JD-Core Version:    0.7.0.1
 */