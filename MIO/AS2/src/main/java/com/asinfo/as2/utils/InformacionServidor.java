/*  1:   */ package com.asinfo.as2.utils;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ public class InformacionServidor
/*  6:   */ {
/*  7:   */   private String rutaAplicacion;
/*  8:23 */   private final String NOMBRE_CARPETA_REPORTES = "reportes";
/*  9:   */   private String separadorArchivo;
/* 10:   */   
/* 11:   */   public String getRutaAplicacion()
/* 12:   */   {
/* 13:35 */     this.rutaAplicacion = System.getProperty("jboss.server.home.url");
/* 14:36 */     return this.rutaAplicacion;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String getNombreCarpetaReportes()
/* 18:   */   {
/* 19:44 */     return "reportes";
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getSeparadorArchivo()
/* 23:   */   {
/* 24:52 */     this.separadorArchivo = System.getProperty("file.separator");
/* 25:53 */     System.out.println("Separador es -------" + this.separadorArchivo);
/* 26:54 */     return this.separadorArchivo;
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.InformacionServidor
 * JD-Core Version:    0.7.0.1
 */