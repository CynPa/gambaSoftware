/*  1:   */ package com.asinfo.as2.utils;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.EntidadBase;
/*  4:   */ import java.io.File;
/*  5:   */ import java.io.FileInputStream;
/*  6:   */ import java.io.IOException;
/*  7:   */ import java.io.InputStream;
/*  8:   */ import java.util.ArrayList;
/*  9:   */ import java.util.List;
/* 10:   */ import java.util.Properties;
/* 11:   */ 
/* 12:   */ public class EjbUtil
/* 13:   */ {
/* 14:   */   public static <T extends EntidadBase> List<T> getEntidadesNoEliminadas(List<T> listaEntidadades)
/* 15:   */   {
/* 16:36 */     List<T> lista = new ArrayList();
/* 17:38 */     for (T entidad : listaEntidadades) {
/* 18:39 */       if (!entidad.isEliminado()) {
/* 19:40 */         lista.add(entidad);
/* 20:   */       }
/* 21:   */     }
/* 22:43 */     return lista;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static final String obtenerValorArchivoProperties(String clave, String rutaArchivo, String nombreArchivo)
/* 26:   */     throws IOException
/* 27:   */   {
/* 28:57 */     Properties properties = obtenerArchivoProperties(rutaArchivo, nombreArchivo);
/* 29:58 */     Object campo = properties.get(clave);
/* 30:59 */     return campo != null ? campo.toString() : null;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static final Properties obtenerArchivoProperties(String rutaArchivo, String nombreArchivo)
/* 34:   */     throws IOException
/* 35:   */   {
/* 36:71 */     Properties archivoProperties = new Properties();
/* 37:72 */     InputStream inputStream = null;
/* 38:73 */     inputStream = new FileInputStream(rutaArchivo + File.separator + nombreArchivo);
/* 39:74 */     archivoProperties.load(inputStream);
/* 40:   */     
/* 41:76 */     return archivoProperties;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public static final String obtenerValorWebService(String clave)
/* 45:   */     throws IOException
/* 46:   */   {
/* 47:87 */     String rutaWebService = System.getenv("AS2_HOME") + File.separator + "config";
/* 48:88 */     return obtenerValorArchivoProperties(clave, rutaWebService, "ws_configuracion.properties");
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.EjbUtil
 * JD-Core Version:    0.7.0.1
 */