/*  1:   */ package com.asinfo.as2.datosbase.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.TipoContactoDao;
/*  4:   */ import com.asinfo.as2.datosbase.servicio.ServicioTipoContacto;
/*  5:   */ import com.asinfo.as2.entities.TipoContacto;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioTipoContactoImpl
/* 13:   */   implements ServicioTipoContacto
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private TipoContactoDao tipoContactoDao;
/* 17:   */   
/* 18:   */   public void guardar(TipoContacto tipoContacto)
/* 19:   */   {
/* 20:41 */     this.tipoContactoDao.guardar(tipoContacto);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(TipoContacto tipoContacto)
/* 24:   */   {
/* 25:52 */     this.tipoContactoDao.eliminar(tipoContacto);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public TipoContacto buscarPorId(int idTipoContacto)
/* 29:   */   {
/* 30:62 */     return (TipoContacto)this.tipoContactoDao.buscarPorId(Integer.valueOf(idTipoContacto));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<TipoContacto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:72 */     return this.tipoContactoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<TipoContacto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:82 */     return this.tipoContactoDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:92 */     return this.tipoContactoDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioTipoContactoImpl
 * JD-Core Version:    0.7.0.1
 */