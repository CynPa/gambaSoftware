/*  1:   */ package com.asinfo.as2.configuracionbase.servicio;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Configuracion;
/*  4:   */ import com.asinfo.as2.entities.TipoAsiento;
/*  5:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  6:   */ import com.asinfo.as2.utils.encriptacion.ExcepcionAS2Encriptacion;
/*  7:   */ import java.io.File;
/*  8:   */ import java.util.List;
/*  9:   */ import java.util.Map;
/* 10:   */ import javax.ejb.Local;
/* 11:   */ 
/* 12:   */ @Local
/* 13:   */ public abstract interface ServicioConfiguracion
/* 14:   */ {
/* 15:36 */   public static final String AS2_HOME = System.getenv("AS2_HOME");
/* 16:37 */   public static final String DIRECTORIO_SRI = AS2_HOME + File.separator + "sri";
/* 17:   */   
/* 18:   */   public abstract void cargarConfiguracion()
/* 19:   */     throws ExcepcionAS2;
/* 20:   */   
/* 21:   */   public abstract void guardar(Configuracion paramConfiguracion);
/* 22:   */   
/* 23:   */   public abstract void eliminar(Configuracion paramConfiguracion);
/* 24:   */   
/* 25:   */   public abstract Configuracion buscarPorId(Integer paramInteger);
/* 26:   */   
/* 27:   */   public abstract List<Configuracion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
/* 28:   */   
/* 29:   */   public abstract int contarPorCriterio(Map<String, String> paramMap);
/* 30:   */   
/* 31:   */   public abstract void cargarConfiguracion(Integer paramInteger);
/* 32:   */   
/* 33:   */   public abstract void actualizarTipoAsiento(TipoAsiento paramTipoAsiento, int paramInt);
/* 34:   */   
/* 35:   */   public abstract void encriptarClaves()
/* 36:   */     throws ExcepcionAS2Encriptacion;
/* 37:   */   
/* 38:   */   public abstract List<Configuracion> listaConfiguracionPorModulo(String paramString, int paramInt);
/* 39:   */   
/* 40:   */   public abstract void cargarVistasSQL()
/* 41:   */     throws ExcepcionAS2;
/* 42:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion
 * JD-Core Version:    0.7.0.1
 */