/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.impl.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.mantenimiento.old.DetalleListaVerificacionDao;
/*   4:    */ import com.asinfo.as2.dao.mantenimiento.old.ListaVerificacionDao;
/*   5:    */ import com.asinfo.as2.entities.mantenimiento.old.DetalleListaVerificacion;
/*   6:    */ import com.asinfo.as2.entities.mantenimiento.old.ListaVerificacion;
/*   7:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioListaVerificacion;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioListaVerificacionImpl
/*  15:    */   implements ServicioListaVerificacion
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private ListaVerificacionDao listaVerificacionDao;
/*  19:    */   @EJB
/*  20:    */   private DetalleListaVerificacionDao detallleListaVerificacionDao;
/*  21:    */   
/*  22:    */   public void guardar(ListaVerificacion listaVerificacion)
/*  23:    */   {
/*  24: 47 */     for (DetalleListaVerificacion detalleListaVerificacion : listaVerificacion.getListaDetalleListaVerificacion()) {
/*  25: 48 */       this.detallleListaVerificacionDao.guardar(detalleListaVerificacion);
/*  26:    */     }
/*  27: 50 */     this.listaVerificacionDao.guardar(listaVerificacion);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void eliminar(ListaVerificacion listaVerificacion)
/*  31:    */   {
/*  32: 60 */     this.listaVerificacionDao.eliminar(listaVerificacion);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public ListaVerificacion buscarPorId(int idListaVerificacion)
/*  36:    */   {
/*  37: 71 */     return (ListaVerificacion)this.listaVerificacionDao.buscarPorId(Integer.valueOf(idListaVerificacion));
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<ListaVerificacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  41:    */   {
/*  42: 81 */     return this.listaVerificacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<ListaVerificacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  46:    */   {
/*  47: 91 */     return this.listaVerificacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int contarPorCriterio(Map<String, String> filters)
/*  51:    */   {
/*  52:101 */     return this.listaVerificacionDao.contarPorCriterio(filters);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public ListaVerificacion cargarDetalle(int idListaVerificacion)
/*  56:    */   {
/*  57:111 */     return this.listaVerificacionDao.cargarDetalle(idListaVerificacion);
/*  58:    */   }
/*  59:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.impl.old.ServicioListaVerificacionImpl
 * JD-Core Version:    0.7.0.1
 */