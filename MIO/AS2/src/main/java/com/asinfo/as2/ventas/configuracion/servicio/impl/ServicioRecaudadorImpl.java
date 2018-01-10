/*   1:    */ package com.asinfo.as2.ventas.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.RecaudadorDao;
/*   4:    */ import com.asinfo.as2.entities.Recaudador;
/*   5:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioRecaudador;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.EJB;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ 
/*  11:    */ @Stateless
/*  12:    */ public class ServicioRecaudadorImpl
/*  13:    */   implements ServicioRecaudador
/*  14:    */ {
/*  15:    */   @EJB
/*  16:    */   private transient RecaudadorDao recaudadorDao;
/*  17:    */   
/*  18:    */   public void guardar(Recaudador recaudador)
/*  19:    */   {
/*  20: 42 */     this.recaudadorDao.guardar(recaudador);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void eliminar(Recaudador recaudador)
/*  24:    */   {
/*  25: 52 */     this.recaudadorDao.eliminar(recaudador);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Recaudador buscarPorId(int idRecaudador)
/*  29:    */   {
/*  30: 62 */     return (Recaudador)this.recaudadorDao.buscarPorId(Integer.valueOf(idRecaudador));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<Recaudador> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 72 */     return this.recaudadorDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<Recaudador> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 82 */     return this.recaudadorDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int contarPorCriterio(Map<String, String> filters)
/*  44:    */   {
/*  45: 92 */     return this.recaudadorDao.contarPorCriterio(filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Recaudador cargarDetalle(int idRecaudador)
/*  49:    */   {
/*  50:102 */     return null;
/*  51:    */   }
/*  52:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.servicio.impl.ServicioRecaudadorImpl
 * JD-Core Version:    0.7.0.1
 */