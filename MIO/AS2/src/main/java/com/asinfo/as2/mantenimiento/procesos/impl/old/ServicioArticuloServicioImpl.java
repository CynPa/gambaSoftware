/*   1:    */ package com.asinfo.as2.mantenimiento.procesos.impl.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.mantenimiento.old.ArticuloServicioDao;
/*   4:    */ import com.asinfo.as2.dao.mantenimiento.old.HistoricoArticuloServicioDao;
/*   5:    */ import com.asinfo.as2.entities.mantenimiento.old.ArticuloServicio;
/*   6:    */ import com.asinfo.as2.entities.mantenimiento.old.HistoricoArticuloServicio;
/*   7:    */ import com.asinfo.as2.mantenimiento.procesos.old.ServicioArticuloServicio;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ 
/*  14:    */ @Stateless
/*  15:    */ public class ServicioArticuloServicioImpl
/*  16:    */   implements ServicioArticuloServicio
/*  17:    */ {
/*  18:    */   @EJB
/*  19:    */   private ArticuloServicioDao articuloServicioDao;
/*  20:    */   @EJB
/*  21:    */   private HistoricoArticuloServicioDao historicoArticuloServicioDao;
/*  22:    */   
/*  23:    */   public void guardar(ArticuloServicio articuloServicio)
/*  24:    */   {
/*  25: 48 */     for (HistoricoArticuloServicio historicoArticuloServicio : articuloServicio.getListaHistoricoArticuloServicioHijo())
/*  26:    */     {
/*  27: 49 */       this.articuloServicioDao.guardar(historicoArticuloServicio.getArticuloServicioHijo());
/*  28: 50 */       this.historicoArticuloServicioDao.guardar(historicoArticuloServicio);
/*  29:    */     }
/*  30: 52 */     this.articuloServicioDao.guardar(articuloServicio);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void eliminar(ArticuloServicio articuloServicio)
/*  34:    */   {
/*  35: 62 */     this.articuloServicioDao.eliminar(articuloServicio);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public ArticuloServicio buscarPorId(int idArticuloServicio)
/*  39:    */   {
/*  40: 73 */     return (ArticuloServicio)this.articuloServicioDao.buscarPorId(Integer.valueOf(idArticuloServicio));
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List<ArticuloServicio> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  44:    */   {
/*  45: 83 */     return this.articuloServicioDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public List<ArticuloServicio> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  49:    */   {
/*  50: 93 */     return this.articuloServicioDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int contarPorCriterio(Map<String, String> filters)
/*  54:    */   {
/*  55:103 */     return this.articuloServicioDao.contarPorCriterio(filters);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public ArticuloServicio cargarDetalle(int idArticuloServicio)
/*  59:    */   {
/*  60:113 */     return this.articuloServicioDao.cargarDetalle(idArticuloServicio);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public List<ArticuloServicio> obtenerArticulosDisponibles()
/*  64:    */   {
/*  65:123 */     return this.articuloServicioDao.obtenerArticulosDisponibles();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public List<ArticuloServicio> autocompletarArticuloServicio(String consulta)
/*  69:    */   {
/*  70:133 */     HashMap<String, String> filters = new HashMap();
/*  71:134 */     filters.put("codigo", consulta);
/*  72:135 */     filters.put("nombre", consulta);
/*  73:136 */     filters.put("descripcion", consulta);
/*  74:137 */     filters.put("numeroSerie", consulta);
/*  75:138 */     filters.put("numeroParte", consulta);
/*  76:139 */     filters.put("codigoBarras", consulta);
/*  77:140 */     return this.articuloServicioDao.autocompletarArticuloServicio("nombre", true, filters);
/*  78:    */   }
/*  79:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.procesos.impl.old.ServicioArticuloServicioImpl
 * JD-Core Version:    0.7.0.1
 */