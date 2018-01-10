/*  1:   */ package com.asinfo.as2.utils.reportes;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.ObjectOutputStream;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.servlet.ServletException;
/*  7:   */ import javax.servlet.ServletOutputStream;
/*  8:   */ import javax.servlet.http.HttpServletRequest;
/*  9:   */ import javax.servlet.http.HttpServletResponse;
/* 10:   */ import net.sf.jasperreports.engine.JasperPrint;
/* 11:   */ import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;
/* 12:   */ 
/* 13:   */ public class PrintServlet
/* 14:   */   extends BaseHttpServlet
/* 15:   */ {
/* 16:   */   private static final long serialVersionUID = 10200L;
/* 17:   */   
/* 18:   */   public void service(HttpServletRequest request, HttpServletResponse response)
/* 19:   */     throws IOException, ServletException
/* 20:   */   {
/* 21:26 */     List<JasperPrint> jasperPrintList = BaseHttpServlet.getJasperPrintList(request);
/* 22:28 */     if (jasperPrintList == null) {
/* 23:29 */       throw new ServletException("No JasperPrint documents found on the HTTP session.");
/* 24:   */     }
/* 25:31 */     JasperPrint jasperPrint = (JasperPrint)jasperPrintList.get(0);
/* 26:32 */     response.setContentType("application/octet-stream");
/* 27:33 */     ServletOutputStream ouputStream = response.getOutputStream();
/* 28:   */     
/* 29:35 */     ObjectOutputStream oos = new ObjectOutputStream(ouputStream);
/* 30:36 */     oos.writeObject(jasperPrint);
/* 31:37 */     oos.flush();
/* 32:38 */     oos.close();
/* 33:   */     
/* 34:40 */     ouputStream.flush();
/* 35:41 */     ouputStream.close();
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.reportes.PrintServlet
 * JD-Core Version:    0.7.0.1
 */