/*  1:   */ package com.asinfo.as2.util;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Organizacion;
/*  4:   */ import java.io.BufferedInputStream;
/*  5:   */ import java.io.FileInputStream;
/*  6:   */ import java.io.FileNotFoundException;
/*  7:   */ import java.io.IOException;
/*  8:   */ import javax.servlet.ServletException;
/*  9:   */ import javax.servlet.ServletOutputStream;
/* 10:   */ import javax.servlet.http.HttpServlet;
/* 11:   */ import javax.servlet.http.HttpServletRequest;
/* 12:   */ import javax.servlet.http.HttpServletResponse;
/* 13:   */ import javax.servlet.http.HttpSession;
/* 14:   */ 
/* 15:   */ public class DynamicImageServlet
/* 16:   */   extends HttpServlet
/* 17:   */ {
/* 18:   */   private static final long serialVersionUID = 1L;
/* 19:   */   
/* 20:   */   private int getIdOrganizacion(HttpServletRequest request)
/* 21:   */   {
/* 22:30 */     return ((Organizacion)request.getSession().getAttribute("com.asinfo.as2.organizacion")).getId();
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected void doGet(HttpServletRequest request, HttpServletResponse response)
/* 26:   */     throws ServletException, IOException
/* 27:   */   {
/* 28:   */     try
/* 29:   */     {
/* 30:44 */       String file = request.getParameter("file");
/* 31:46 */       if (!file.isEmpty())
/* 32:   */       {
/* 33:48 */         String prefijoArchivo = file.substring(0, 3);
/* 34:   */         
/* 35:50 */         BufferedInputStream in = null;
/* 36:54 */         if (prefijoArchivo.equals("pro")) {
/* 37:55 */           in = new BufferedInputStream(new FileInputStream(RutaArchivo.getUploadDir(getIdOrganizacion(request), "producto") + file));
/* 38:   */         }
/* 39:59 */         if (prefijoArchivo.equals("log")) {
/* 40:60 */           in = new BufferedInputStream(new FileInputStream(RutaArchivo.getUploadDir(getIdOrganizacion(request), "logo") + file));
/* 41:   */         }
/* 42:65 */         if (prefijoArchivo.equals("act")) {
/* 43:66 */           in = new BufferedInputStream(new FileInputStream(RutaArchivo.getUploadDir(getIdOrganizacion(request), "activos") + file));
/* 44:   */         }
/* 45:71 */         if (prefijoArchivo.equals("emp")) {
/* 46:72 */           in = new BufferedInputStream(new FileInputStream(RutaArchivo.getUploadDir(getIdOrganizacion(request), "empleado") + file));
/* 47:   */         }
/* 48:77 */         if (prefijoArchivo.equals("hue")) {
/* 49:78 */           in = new BufferedInputStream(new FileInputStream(RutaArchivo.getUploadDir(getIdOrganizacion(request), "huellas_digitales") + file));
/* 50:   */         }
/* 51:81 */         byte[] bytes = null;
/* 52:82 */         if (in != null)
/* 53:   */         {
/* 54:84 */           bytes = new byte[in.available()];
/* 55:85 */           in.read(bytes);
/* 56:86 */           in.close();
/* 57:   */           
/* 58:   */ 
/* 59:89 */           response.getOutputStream().write(bytes);
/* 60:   */         }
/* 61:   */       }
/* 62:   */     }
/* 63:   */     catch (FileNotFoundException e)
/* 64:   */     {
/* 65:95 */       throw new FileNotFoundException("Archivo no encontrado");
/* 66:   */     }
/* 67:   */     catch (IOException e)
/* 68:   */     {
/* 69:98 */       e.printStackTrace();
/* 70:   */     }
/* 71:   */   }
/* 72:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.util.DynamicImageServlet
 * JD-Core Version:    0.7.0.1
 */