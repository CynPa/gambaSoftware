/*   1:    */ package com.asinfo.as2.mantenimiento.procesos.impl.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.mantenimiento.old.ArticuloServicioDao;
/*   4:    */ import com.asinfo.as2.dao.mantenimiento.old.HistoricoArticuloServicioDao;
/*   5:    */ import com.asinfo.as2.entities.mantenimiento.old.HistoricoArticuloServicio;
/*   6:    */ import com.asinfo.as2.mantenimiento.procesos.old.ServicioHistoricoArticuloServicio;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.ejb.EJB;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ 
/*  12:    */ @Stateless
/*  13:    */ public class ServicioHistoricoArticuloServicioImpl
/*  14:    */   implements ServicioHistoricoArticuloServicio
/*  15:    */ {
/*  16:    */   @EJB
/*  17:    */   private HistoricoArticuloServicioDao historicoArticuloServicioDao;
/*  18:    */   @EJB
/*  19:    */   private ArticuloServicioDao articuloServicioDao;
/*  20:    */   
/*  21:    */   public void guardar(HistoricoArticuloServicio historicoArticuloServicio)
/*  22:    */   {
/*  23: 46 */     this.articuloServicioDao.guardar(historicoArticuloServicio.getArticuloServicioHijo());
/*  24: 47 */     this.historicoArticuloServicioDao.guardar(historicoArticuloServicio);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void eliminar(HistoricoArticuloServicio historicoArticuloServicio)
/*  28:    */   {
/*  29: 57 */     this.historicoArticuloServicioDao.eliminar(historicoArticuloServicio);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public HistoricoArticuloServicio buscarPorId(int idHistoricoArticuloServicio)
/*  33:    */   {
/*  34: 68 */     return (HistoricoArticuloServicio)this.historicoArticuloServicioDao.buscarPorId(Integer.valueOf(idHistoricoArticuloServicio));
/*  35:    */   }
/*  36:    */   
/*  37:    */   public List<HistoricoArticuloServicio> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  38:    */   {
/*  39: 79 */     return this.historicoArticuloServicioDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public List<HistoricoArticuloServicio> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  43:    */   {
/*  44: 89 */     return this.historicoArticuloServicioDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int contarPorCriterio(Map<String, String> filters)
/*  48:    */   {
/*  49: 99 */     return this.historicoArticuloServicioDao.contarPorCriterio(filters);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public HistoricoArticuloServicio obtenerHistoricoArticuloServicioActual(int idArticuloServicio)
/*  53:    */   {
/*  54:109 */     return this.historicoArticuloServicioDao.obtenerHistoricoArticuloServicioActual(idArticuloServicio);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public HistoricoArticuloServicio cargarDetalle(int idArticuloServicio)
/*  58:    */   {
/*  59:119 */     return this.historicoArticuloServicioDao.cargarDetalle(idArticuloServicio);
/*  60:    */   }
/*  61:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.procesos.impl.old.ServicioHistoricoArticuloServicioImpl
 * JD-Core Version:    0.7.0.1
 */