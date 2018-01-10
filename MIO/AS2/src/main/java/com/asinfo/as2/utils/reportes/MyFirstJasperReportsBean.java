/*  1:   */ package com.asinfo.as2.utils.reportes;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ import javax.faces.bean.ManagedBean;
/*  6:   */ import javax.faces.bean.ViewScoped;
/*  7:   */ import net.sf.jasperreports.engine.JRDataSource;
/*  8:   */ 
/*  9:   */ @ManagedBean
/* 10:   */ @ViewScoped
/* 11:   */ public class MyFirstJasperReportsBean
/* 12:   */   extends AbstractBaseReportBean
/* 13:   */ {
/* 14:31 */   private final String COMPILE_FILE_NAME = "my_first_report";
/* 15:   */   
/* 16:   */   protected String getCompileFileName()
/* 17:   */   {
/* 18:35 */     return "my_first_report";
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected Map<String, Object> getReportParameters()
/* 22:   */   {
/* 23:40 */     Map<String, Object> reportParameters = new HashMap();
/* 24:   */     
/* 25:42 */     reportParameters.put("ReportTitle", "Hello JasperReports");
/* 26:   */     
/* 27:44 */     return reportParameters;
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected JRDataSource getJRDataSource()
/* 31:   */   {
/* 32:50 */     MyFirstJasperReportsDataSource dataSource = new MyFirstJasperReportsDataSource();
/* 33:   */     
/* 34:52 */     return dataSource;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String execute()
/* 38:   */   {
/* 39:   */     try
/* 40:   */     {
/* 41:57 */       super.prepareReport();
/* 42:   */     }
/* 43:   */     catch (Exception e)
/* 44:   */     {
/* 45:60 */       e.printStackTrace();
/* 46:   */     }
/* 47:63 */     return null;
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.reportes.MyFirstJasperReportsBean
 * JD-Core Version:    0.7.0.1
 */