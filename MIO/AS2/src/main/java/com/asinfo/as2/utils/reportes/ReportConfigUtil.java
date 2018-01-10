/*   1:    */ package com.asinfo.as2.utils.reportes;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.StringTokenizer;
/*   9:    */ import net.sf.jasperreports.crosstabs.JRCrosstab;
/*  10:    */ import net.sf.jasperreports.engine.JRAbstractExporter;
/*  11:    */ import net.sf.jasperreports.engine.JRBreak;
/*  12:    */ import net.sf.jasperreports.engine.JRChart;
/*  13:    */ import net.sf.jasperreports.engine.JRComponentElement;
/*  14:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  15:    */ import net.sf.jasperreports.engine.JRElementGroup;
/*  16:    */ import net.sf.jasperreports.engine.JREllipse;
/*  17:    */ import net.sf.jasperreports.engine.JRException;
/*  18:    */ import net.sf.jasperreports.engine.JRExporterParameter;
/*  19:    */ import net.sf.jasperreports.engine.JRExpression;
/*  20:    */ import net.sf.jasperreports.engine.JRFrame;
/*  21:    */ import net.sf.jasperreports.engine.JRGenericElement;
/*  22:    */ import net.sf.jasperreports.engine.JRImage;
/*  23:    */ import net.sf.jasperreports.engine.JRLine;
/*  24:    */ import net.sf.jasperreports.engine.JRRectangle;
/*  25:    */ import net.sf.jasperreports.engine.JRStaticText;
/*  26:    */ import net.sf.jasperreports.engine.JRSubreport;
/*  27:    */ import net.sf.jasperreports.engine.JRTextField;
/*  28:    */ import net.sf.jasperreports.engine.JRVisitor;
/*  29:    */ import net.sf.jasperreports.engine.JasperCompileManager;
/*  30:    */ import net.sf.jasperreports.engine.JasperFillManager;
/*  31:    */ import net.sf.jasperreports.engine.JasperPrint;
/*  32:    */ import net.sf.jasperreports.engine.JasperReport;
/*  33:    */ import net.sf.jasperreports.engine.design.JasperDesign;
/*  34:    */ import net.sf.jasperreports.engine.export.JRHtmlExporter;
/*  35:    */ import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
/*  36:    */ import net.sf.jasperreports.engine.util.JRElementsVisitor;
/*  37:    */ import net.sf.jasperreports.engine.util.JRSaver;
/*  38:    */ import net.sf.jasperreports.engine.xml.JRXmlLoader;
/*  39:    */ 
/*  40:    */ public class ReportConfigUtil
/*  41:    */ {
/*  42:    */   public static boolean compileReport(final String as2Home, final String compileDir, String filename, boolean tieneSubReportes)
/*  43:    */     throws JRException
/*  44:    */   {
/*  45: 62 */     String jasperFileName = getJasperFilePath(as2Home, compileDir, filename) + ".jasper";
/*  46:    */     
/*  47: 64 */     File jasperFile = new File(jasperFileName);
/*  48: 65 */     List<String> completedSubReports = new ArrayList(30);
/*  49:    */     try
/*  50:    */     {
/*  51: 68 */       String xmlFileName = jasperFileName.substring(0, jasperFileName.indexOf(".jasper")) + ".jrxml";
/*  52:    */       
/*  53: 70 */       JasperReport jasperReport = null;
/*  54: 71 */       if ((!jasperFile.exists()) || (tieneSubReportes))
/*  55:    */       {
/*  56: 72 */         JasperDesign jasperDesign = JRXmlLoader.load(xmlFileName);
/*  57: 73 */         jasperReport = JasperCompileManager.compileReport(jasperDesign);
/*  58: 74 */         JRSaver.saveObject(jasperReport, xmlFileName.replace(".jrxml", ".jasper"));
/*  59:    */       }
/*  60: 78 */       if (tieneSubReportes) {
/*  61: 79 */         JRElementsVisitor.visitReport(jasperReport, new JRVisitor()
/*  62:    */         {
/*  63:    */           public void visitBreak(JRBreak breakElement) {}
/*  64:    */           
/*  65:    */           public void visitChart(JRChart chart) {}
/*  66:    */           
/*  67:    */           public void visitCrosstab(JRCrosstab crosstab) {}
/*  68:    */           
/*  69:    */           public void visitElementGroup(JRElementGroup elementGroup) {}
/*  70:    */           
/*  71:    */           public void visitEllipse(JREllipse ellipse) {}
/*  72:    */           
/*  73:    */           public void visitFrame(JRFrame frame) {}
/*  74:    */           
/*  75:    */           public void visitImage(JRImage image) {}
/*  76:    */           
/*  77:    */           public void visitLine(JRLine line) {}
/*  78:    */           
/*  79:    */           public void visitRectangle(JRRectangle rectangle) {}
/*  80:    */           
/*  81:    */           public void visitStaticText(JRStaticText staticText) {}
/*  82:    */           
/*  83:    */           public void visitSubreport(JRSubreport subreport)
/*  84:    */           {
/*  85:    */             try
/*  86:    */             {
/*  87:123 */               String expression = subreport.getExpression().getText().replace(".jasper", "");
/*  88:124 */               StringTokenizer st = new StringTokenizer(expression, "\"/");
/*  89:125 */               String subReportName = null;
/*  90:126 */               while (st.hasMoreTokens()) {
/*  91:127 */                 subReportName = st.nextToken();
/*  92:    */               }
/*  93:129 */               if (this.val$completedSubReports.contains(subReportName)) {
/*  94:130 */                 return;
/*  95:    */               }
/*  96:131 */               this.val$completedSubReports.add(subReportName);
/*  97:132 */               ReportConfigUtil.compileReport(as2Home, compileDir, subReportName, true);
/*  98:    */             }
/*  99:    */             catch (Throwable e)
/* 100:    */             {
/* 101:134 */               e.printStackTrace();
/* 102:    */             }
/* 103:    */           }
/* 104:    */           
/* 105:    */           public void visitTextField(JRTextField textField) {}
/* 106:    */           
/* 107:    */           public void visitComponentElement(JRComponentElement componentElement) {}
/* 108:    */           
/* 109:    */           public void visitGenericElement(JRGenericElement element) {}
/* 110:    */         });
/* 111:    */       }
/* 112:151 */       return true;
/* 113:    */     }
/* 114:    */     catch (Exception e)
/* 115:    */     {
/* 116:153 */       e.printStackTrace();
/* 117:    */     }
/* 118:154 */     return false;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static JasperPrint fillReport(File reportFile, Map<String, Object> parameters, JRDataSource jrDataSource)
/* 122:    */     throws JRException
/* 123:    */   {
/* 124:161 */     parameters.put("BaseDir", reportFile.getParentFile());
/* 125:    */     
/* 126:163 */     JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile
/* 127:164 */       .getPath(), parameters, jrDataSource);
/* 128:    */     
/* 129:166 */     return jasperPrint;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public static String getJasperFilePath(String as2Home, String compileDir, String jasperFile)
/* 133:    */   {
/* 134:170 */     return as2Home + compileDir + jasperFile;
/* 135:    */   }
/* 136:    */   
/* 137:    */   private static void exportReport(JRAbstractExporter exporter, JasperPrint jasperPrint, PrintWriter out)
/* 138:    */     throws JRException
/* 139:    */   {
/* 140:175 */     exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
/* 141:176 */     exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
/* 142:    */     
/* 143:178 */     exporter.exportReport();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static void exportReportAsHtml(JasperPrint jasperPrint, PrintWriter out)
/* 147:    */     throws JRException
/* 148:    */   {
/* 149:183 */     JRHtmlExporter exporter = new JRHtmlExporter();
/* 150:184 */     exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
/* 151:    */     
/* 152:186 */     exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
/* 153:    */     
/* 154:    */ 
/* 155:189 */     exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "ISO-8859-9");
/* 156:    */     
/* 157:191 */     exportReport(exporter, jasperPrint, out);
/* 158:    */   }
/* 159:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.reportes.ReportConfigUtil
 * JD-Core Version:    0.7.0.1
 */