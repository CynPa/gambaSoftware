/*   1:    */ package com.asinfo.as2.finaciero.SRI.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Organizacion;
/*   4:    */ import com.asinfo.as2.finaciero.SRI.reportes.dataSource.ReporteRelacionDependenciaDataSource;
/*   5:    */ import com.asinfo.as2.util.AppUtil;
/*   6:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.faces.bean.ManagedBean;
/*   9:    */ import javax.faces.bean.ManagedProperty;
/*  10:    */ import javax.faces.bean.RequestScoped;
/*  11:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  12:    */ 
/*  13:    */ @ManagedBean
/*  14:    */ @RequestScoped
/*  15:    */ public class ReporteRelacionDependenciaBean
/*  16:    */   extends AbstractBaseReportBean
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 7586483161985123702L;
/*  19: 41 */   private ReporteRelacionDependenciaDataSource reporteRelacionDependenciaDataSource = new ReporteRelacionDependenciaDataSource();
/*  20: 42 */   private String COMPILE_FILE_NAME = "formulario107";
/*  21:    */   @ManagedProperty("#{relacionDependenciaBean}")
/*  22:    */   private RelacionDependenciaBean relacionDependenciaBean;
/*  23:    */   
/*  24:    */   protected JRDataSource getJRDataSource()
/*  25:    */   {
/*  26: 54 */     return this.reporteRelacionDependenciaDataSource;
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected String getCompileFileName()
/*  30:    */   {
/*  31: 64 */     return this.COMPILE_FILE_NAME;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String execute()
/*  35:    */   {
/*  36:    */     try
/*  37:    */     {
/*  38: 69 */       this.reporteRelacionDependenciaDataSource.setListaRelacionDependencia(this.relacionDependenciaBean.getListaRelacionDependencia());
/*  39: 70 */       super.prepareReport();
/*  40:    */     }
/*  41:    */     catch (Exception e)
/*  42:    */     {
/*  43: 73 */       e.printStackTrace();
/*  44:    */     }
/*  45: 76 */     return "";
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected Map<String, Object> getReportParameters()
/*  49:    */   {
/*  50: 81 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  51:    */     
/*  52: 83 */     reportParameters.put("p_anio", Integer.valueOf(this.relacionDependenciaBean.getAnio()));
/*  53: 84 */     reportParameters.put("p_rucContador", AppUtil.getOrganizacion().getIdentificacionContador());
/*  54: 85 */     reportParameters.put("rucOrganizacion", AppUtil.getOrganizacion().getIdentificacion());
/*  55: 86 */     return reportParameters;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public ReporteRelacionDependenciaDataSource getReporteRelacionDependenciaDataSource()
/*  59:    */   {
/*  60: 95 */     return this.reporteRelacionDependenciaDataSource;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setReporteRelacionDependenciaDataSource(ReporteRelacionDependenciaDataSource reporteRelacionDependenciaDataSource)
/*  64:    */   {
/*  65:105 */     this.reporteRelacionDependenciaDataSource = reporteRelacionDependenciaDataSource;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public RelacionDependenciaBean getRelacionDependenciaBean()
/*  69:    */   {
/*  70:114 */     return this.relacionDependenciaBean;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setRelacionDependenciaBean(RelacionDependenciaBean relacionDependenciaBean)
/*  74:    */   {
/*  75:124 */     this.relacionDependenciaBean = relacionDependenciaBean;
/*  76:    */   }
/*  77:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.reportes.ReporteRelacionDependenciaBean
 * JD-Core Version:    0.7.0.1
 */