/*   1:    */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.MotivoAjusteInventarioDao;
/*   4:    */ import com.asinfo.as2.entities.MotivoAjusteInventario;
/*   5:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioMotivoAjusteInventario;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.ejb.EJB;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ 
/*  12:    */ @Stateless
/*  13:    */ public class ServicioMotivoAjusteInventarioImpl
/*  14:    */   implements ServicioMotivoAjusteInventario
/*  15:    */ {
/*  16:    */   @EJB
/*  17:    */   private transient MotivoAjusteInventarioDao motivoAjusteInventarioDao;
/*  18:    */   
/*  19:    */   public void guardar(MotivoAjusteInventario motivoAjusteInventario)
/*  20:    */   {
/*  21: 43 */     this.motivoAjusteInventarioDao.guardar(motivoAjusteInventario);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void eliminar(MotivoAjusteInventario motivoAjusteInventario)
/*  25:    */   {
/*  26: 53 */     this.motivoAjusteInventarioDao.eliminar(motivoAjusteInventario);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public MotivoAjusteInventario buscarPorId(int idMotivoAjusteInventario)
/*  30:    */   {
/*  31: 63 */     return (MotivoAjusteInventario)this.motivoAjusteInventarioDao.buscarPorId(Integer.valueOf(idMotivoAjusteInventario));
/*  32:    */   }
/*  33:    */   
/*  34:    */   public List<MotivoAjusteInventario> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  35:    */   {
/*  36: 75 */     return this.motivoAjusteInventarioDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public List<MotivoAjusteInventario> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  40:    */   {
/*  41: 86 */     return this.motivoAjusteInventarioDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int contarPorCriterio(Map<String, String> filters)
/*  45:    */   {
/*  46: 96 */     return this.motivoAjusteInventarioDao.contarPorCriterio(filters);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public MotivoAjusteInventario cargarDetalle(int idMotivoAjusteInventario)
/*  50:    */   {
/*  51:106 */     return this.motivoAjusteInventarioDao.cargarDetalle(idMotivoAjusteInventario);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public List<MotivoAjusteInventario> autoCompletarMotivoAjusteInventario(String consulta)
/*  55:    */   {
/*  56:111 */     HashMap<String, String> filters = new HashMap();
/*  57:112 */     filters.put("activo", "true");
/*  58:113 */     filters.put("nombre", consulta);
/*  59:114 */     filters.put("codigo", consulta);
/*  60:115 */     return this.motivoAjusteInventarioDao.obtenerListaCombo("nombre", true, filters);
/*  61:    */   }
/*  62:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioMotivoAjusteInventarioImpl
 * JD-Core Version:    0.7.0.1
 */