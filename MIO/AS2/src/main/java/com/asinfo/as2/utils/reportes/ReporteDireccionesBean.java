/*  1:   */ package com.asinfo.as2.utils.reportes;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.enumeraciones.ExportOption;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.Map;
/*  6:   */ import javax.faces.bean.ManagedBean;
/*  7:   */ import javax.faces.bean.ViewScoped;
/*  8:   */ import net.sf.jasperreports.engine.JRDataSource;
/*  9:   */ 
/* 10:   */ @ManagedBean
/* 11:   */ @ViewScoped
/* 12:   */ public class ReporteDireccionesBean
/* 13:   */   extends AbstractBaseReportBean
/* 14:   */ {
/* 15:33 */   private final String COMPILE_FILE_NAME = "reporte_direcciones_empresa";
/* 16:   */   
/* 17:   */   protected String getCompileFileName()
/* 18:   */   {
/* 19:37 */     return "reporte_direcciones_empresa";
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected Map<String, Object> getReportParameters()
/* 23:   */   {
/* 24:42 */     Map<String, Object> reportParameters = new HashMap();
/* 25:   */     
/* 26:44 */     reportParameters.put("ReportTitle", "Direccion Empresas");
/* 27:   */     
/* 28:46 */     return reportParameters;
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected JRDataSource getJRDataSource()
/* 32:   */   {
/* 33:52 */     ReporteDireccionesDataSource dataSource = new ReporteDireccionesDataSource();
/* 34:   */     
/* 35:54 */     return dataSource;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String execute()
/* 39:   */   {
/* 40:   */     try
/* 41:   */     {
/* 42:59 */       setExportOption(ExportOption.PDF);
/* 43:60 */       super.prepareReport();
/* 44:   */     }
/* 45:   */     catch (Exception e)
/* 46:   */     {
/* 47:63 */       e.printStackTrace();
/* 48:   */     }
/* 49:66 */     return null;
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.reportes.ReporteDireccionesBean
 * JD-Core Version:    0.7.0.1
 */