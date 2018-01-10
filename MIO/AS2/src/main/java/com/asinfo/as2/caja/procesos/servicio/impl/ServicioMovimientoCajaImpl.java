/*   1:    */ package com.asinfo.as2.caja.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.caja.procesos.servicio.ServicioMovimientoCaja;
/*   4:    */ import com.asinfo.as2.dao.MovimientoCajaDao;
/*   5:    */ import com.asinfo.as2.entities.MovimientoCaja;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.EJB;
/*   9:    */ 
/*  10:    */ public class ServicioMovimientoCajaImpl
/*  11:    */   implements ServicioMovimientoCaja
/*  12:    */ {
/*  13:    */   @EJB
/*  14:    */   private MovimientoCajaDao movimientoCajaDao;
/*  15:    */   
/*  16:    */   public void guardar(MovimientoCaja movimientoCaja)
/*  17:    */   {
/*  18: 42 */     this.movimientoCajaDao.guardar(movimientoCaja);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void eliminar(MovimientoCaja movimientoCaja)
/*  22:    */   {
/*  23: 55 */     this.movimientoCajaDao.eliminar(movimientoCaja);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public MovimientoCaja buscarPorId(int idMovimientoCaja)
/*  27:    */   {
/*  28: 68 */     return (MovimientoCaja)this.movimientoCajaDao.buscarPorId(Integer.valueOf(idMovimientoCaja));
/*  29:    */   }
/*  30:    */   
/*  31:    */   public List<MovimientoCaja> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  32:    */   {
/*  33: 79 */     return this.movimientoCajaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public List<MovimientoCaja> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  37:    */   {
/*  38: 90 */     return null;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int contarPorCriterio(Map<String, String> filters)
/*  42:    */   {
/*  43:101 */     return this.movimientoCajaDao.contarPorCriterio(filters);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public MovimientoCaja cargarDetalle(int idMovimientoCaja)
/*  47:    */   {
/*  48:114 */     return null;
/*  49:    */   }
/*  50:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.caja.procesos.servicio.impl.ServicioMovimientoCajaImpl
 * JD-Core Version:    0.7.0.1
 */