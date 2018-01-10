/*   1:    */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.NivelInstruccionDao;
/*   4:    */ import com.asinfo.as2.entities.NivelInstruccion;
/*   5:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioNivelInstruccion;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.EJB;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ 
/*  11:    */ @Stateless
/*  12:    */ public class ServicioNivelInstruccionImpl
/*  13:    */   implements ServicioNivelInstruccion
/*  14:    */ {
/*  15:    */   @EJB
/*  16:    */   private transient NivelInstruccionDao nivelInstruccionDao;
/*  17:    */   
/*  18:    */   public void guardar(NivelInstruccion nivelInstruccion)
/*  19:    */   {
/*  20: 42 */     this.nivelInstruccionDao.guardar(nivelInstruccion);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void eliminar(NivelInstruccion nivelInstruccion)
/*  24:    */   {
/*  25: 53 */     this.nivelInstruccionDao.eliminar(nivelInstruccion);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public NivelInstruccion buscarPorId(int idNivelInstruccion)
/*  29:    */   {
/*  30: 63 */     return (NivelInstruccion)this.nivelInstruccionDao.buscarPorId(Integer.valueOf(idNivelInstruccion));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<NivelInstruccion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 74 */     return this.nivelInstruccionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<NivelInstruccion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 84 */     return this.nivelInstruccionDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int contarPorCriterio(Map<String, String> filters)
/*  44:    */   {
/*  45: 94 */     return this.nivelInstruccionDao.contarPorCriterio(filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public NivelInstruccion cargarDetalle(int idEntidad)
/*  49:    */   {
/*  50:105 */     return null;
/*  51:    */   }
/*  52:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioNivelInstruccionImpl
 * JD-Core Version:    0.7.0.1
 */