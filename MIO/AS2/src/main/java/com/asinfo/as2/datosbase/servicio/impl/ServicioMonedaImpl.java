/*  1:   */ package com.asinfo.as2.datosbase.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.MonedaDao;
/*  4:   */ import com.asinfo.as2.datosbase.servicio.ServicioMoneda;
/*  5:   */ import com.asinfo.as2.datosbase.servicio.ServicioMonedaRemoto;
/*  6:   */ import com.asinfo.as2.entities.Moneda;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import javax.ejb.Stateless;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class ServicioMonedaImpl
/* 14:   */   implements ServicioMoneda, ServicioMonedaRemoto
/* 15:   */ {
/* 16:   */   @EJB
/* 17:   */   private MonedaDao monedaDao;
/* 18:   */   
/* 19:   */   public void guardar(Moneda moneda)
/* 20:   */   {
/* 21:41 */     this.monedaDao.guardar(moneda);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void eliminar(Moneda moneda)
/* 25:   */   {
/* 26:51 */     this.monedaDao.eliminar(moneda);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Moneda buscarPorId(int id)
/* 30:   */   {
/* 31:61 */     return (Moneda)this.monedaDao.buscarPorId(Integer.valueOf(id));
/* 32:   */   }
/* 33:   */   
/* 34:   */   public List<Moneda> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 35:   */   {
/* 36:67 */     return this.monedaDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public List<Moneda> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 40:   */   {
/* 41:77 */     return this.monedaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int contarPorCriterio(Map<String, String> filters)
/* 45:   */   {
/* 46:87 */     return this.monedaDao.contarPorCriterio(filters);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public Moneda obtenerPorCodigo(String codigo)
/* 50:   */   {
/* 51:97 */     return this.monedaDao.obtenerPorCodigo(codigo);
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioMonedaImpl
 * JD-Core Version:    0.7.0.1
 */