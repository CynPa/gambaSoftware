/*   1:    */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.QuincenaDao;
/*   4:    */ import com.asinfo.as2.entities.Quincena;
/*   5:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioQuincena;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.EJB;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ 
/*  11:    */ @Stateless
/*  12:    */ public class ServicioQuincenaImpl
/*  13:    */   implements ServicioQuincena
/*  14:    */ {
/*  15:    */   @EJB
/*  16:    */   private QuincenaDao quincenaDao;
/*  17:    */   
/*  18:    */   public void guardar(Quincena quincena)
/*  19:    */   {
/*  20: 44 */     this.quincenaDao.guardar(quincena);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void eliminar(Quincena quincena)
/*  24:    */   {
/*  25: 57 */     this.quincenaDao.eliminar(quincena);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Quincena buscarPorId(int idQuincena)
/*  29:    */   {
/*  30: 69 */     return (Quincena)this.quincenaDao.buscarPorId(Integer.valueOf(idQuincena));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<Quincena> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 80 */     return this.quincenaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<Quincena> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 91 */     return this.quincenaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int contarPorCriterio(Map<String, String> filters)
/*  44:    */   {
/*  45:102 */     return this.quincenaDao.contarPorCriterio(filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Quincena cargarDetalle(int idQuincena)
/*  49:    */   {
/*  50:113 */     return this.quincenaDao.cargarDetalle(idQuincena);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Quincena obtenerQuincenaPorCodigo(String codigo)
/*  54:    */   {
/*  55:120 */     return this.quincenaDao.obtenerQuincenaPorCodigo(codigo);
/*  56:    */   }
/*  57:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioQuincenaImpl
 * JD-Core Version:    0.7.0.1
 */