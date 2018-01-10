/*   1:    */ package com.asinfo.as2.produccion.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.produccion.MaquinaDao;
/*   4:    */ import com.asinfo.as2.entities.produccion.Maquina;
/*   5:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMaquina;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.EJB;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ 
/*  11:    */ @Stateless
/*  12:    */ public class ServicioMaquinaImpl
/*  13:    */   implements ServicioMaquina
/*  14:    */ {
/*  15:    */   @EJB
/*  16:    */   private MaquinaDao maquinaDao;
/*  17:    */   
/*  18:    */   public void guardar(Maquina maquina)
/*  19:    */   {
/*  20: 42 */     this.maquinaDao.guardar(maquina);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void eliminar(Maquina maquina)
/*  24:    */   {
/*  25: 52 */     this.maquinaDao.eliminar(maquina);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Maquina buscarPorId(int idMaquina)
/*  29:    */   {
/*  30: 63 */     return (Maquina)this.maquinaDao.buscarPorId(Integer.valueOf(idMaquina));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<Maquina> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 73 */     return this.maquinaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public List<Maquina> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  39:    */   {
/*  40: 83 */     return this.maquinaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int contarPorCriterio(Map<String, String> filters)
/*  44:    */   {
/*  45: 93 */     return this.maquinaDao.contarPorCriterio(filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Maquina cargarDetalle(int idMaquina)
/*  49:    */   {
/*  50:103 */     return this.maquinaDao.cargarDetalle(idMaquina);
/*  51:    */   }
/*  52:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.configuracion.servicio.impl.ServicioMaquinaImpl
 * JD-Core Version:    0.7.0.1
 */