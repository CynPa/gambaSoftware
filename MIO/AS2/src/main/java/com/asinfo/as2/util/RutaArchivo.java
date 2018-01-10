/*  1:   */ package com.asinfo.as2.util;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.utils.ParametrosSistema;
/*  4:   */ import java.io.File;
/*  5:   */ 
/*  6:   */ public class RutaArchivo
/*  7:   */ {
/*  8:   */   private static String uploadDir;
/*  9:   */   
/* 10:   */   public static String getUploadDir(int idOrganizacion, String directorio)
/* 11:   */   {
/* 12:28 */     uploadDir = ParametrosSistema.getAS2_HOME(idOrganizacion) + File.separator + "imagenes" + File.separator + directorio + File.separator;
/* 13:   */     
/* 14:30 */     return uploadDir;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public static String getDirectorioUpload(int idOrganizacion, String carpetaDirectorio, String carpeta)
/* 18:   */   {
/* 19:35 */     uploadDir = ParametrosSistema.getAS2_HOME(idOrganizacion) + File.separator + carpetaDirectorio + File.separator + carpeta + File.separator;
/* 20:   */     
/* 21:37 */     File file = new File(uploadDir);
/* 22:38 */     if (!file.exists()) {
/* 23:39 */       file.mkdirs();
/* 24:   */     }
/* 25:42 */     return uploadDir;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static void setUploadDir(String uploadDir)
/* 29:   */   {
/* 30:53 */     uploadDir = uploadDir;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.util.RutaArchivo
 * JD-Core Version:    0.7.0.1
 */