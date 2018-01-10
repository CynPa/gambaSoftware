/*  1:   */ package com.asinfo.as2.finaciero.contabilidad.reportes.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DimensionContable;
/*  4:   */ import com.asinfo.as2.entities.Organizacion;
/*  5:   */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable;
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
/* 19:   */ public class ReporteDimensionContableBean
/* 20:   */   extends AbstractBaseReportBean
/* 21:   */ {
/* 22:   */   private static final long serialVersionUID = 8145726040928835494L;
/* 23:   */   @EJB
/* 24:   */   private ServicioDimensionContable servicioDimensionContable;
/* 25:   */   private DimensionContable dimensionContable;
/* 26:   */   
/* 27:   */   protected JRDataSource getJRDataSource()
/* 28:   */   {
/* 29:34 */     List<Object[]> listaDatosReporte = new ArrayList();
/* 30:35 */     JRDataSource ds = null;
/* 31:36 */     String[] fields = { "f_dimensionCodigo", "f_dimensionNombre", "f_dimensionFechaDesde", "f_dimensionFechaHasta", "f_dimensionTipoAcceso", "f_dimensionDescripcion", "f_dimensionPadreNombre", "f_cuentaContableCodigo", "f_cuentaContableNombre" };
/* 32:   */     
/* 33:   */ 
/* 34:39 */     listaDatosReporte = this.servicioDimensionContable.getReporteDimensionContable(getDimensionContable() == null ? 0 : getDimensionContable().getId(), AppUtil.getOrganizacion().getId());
/* 35:40 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/* 36:41 */     return ds;
/* 37:   */   }
/* 38:   */   
/* 39:   */   protected String getCompileFileName()
/* 40:   */   {
/* 41:46 */     return "reporteDimensionContable";
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
/* 60:63 */     reportParameters.put("ReportTitle", "Dimension Contable");
/* 61:   */     
/* 62:65 */     return reportParameters;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public DimensionContable getDimensionContable()
/* 66:   */   {
/* 67:69 */     return this.dimensionContable;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public void setDimensionContable(DimensionContable dimensionContable)
/* 71:   */   {
/* 72:73 */     this.dimensionContable = dimensionContable;
/* 73:   */   }
/* 74:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.controller.ReporteDimensionContableBean
 * JD-Core Version:    0.7.0.1
 */