/*  1:   */ package com.asinfo.as2.inventario.procesos.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.SerieProductoDao;
/*  4:   */ import com.asinfo.as2.entities.Producto;
/*  5:   */ import com.asinfo.as2.entities.SerieProducto;
/*  6:   */ import com.asinfo.as2.inventario.procesos.servicio.ServicioSerieProducto;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import javax.ejb.Stateless;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class ServicioSerieProductoImpl
/* 14:   */   implements ServicioSerieProducto
/* 15:   */ {
/* 16:   */   @EJB
/* 17:   */   private transient SerieProductoDao serieProductoDao;
/* 18:   */   
/* 19:   */   public void guardar(SerieProducto serie)
/* 20:   */   {
/* 21:40 */     this.serieProductoDao.guardar(serie);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public List<SerieProducto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 25:   */   {
/* 26:46 */     return this.serieProductoDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public List<SerieProducto> getListaSerieProducto(int idOrganizacion, Producto producto, List<String> codigos)
/* 30:   */   {
/* 31:56 */     return this.serieProductoDao.getListaSerieProducto(idOrganizacion, producto, codigos);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public SerieProducto buscarPorId(Object idSerieProducto)
/* 35:   */   {
/* 36:66 */     return this.serieProductoDao.buscarPorId(idSerieProducto);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public SerieProducto buscarPorCodigo(int idOrganizacion, Producto producto, String codigo)
/* 40:   */   {
/* 41:76 */     return this.serieProductoDao.buscarPorCodigo(idOrganizacion, producto, codigo);
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.impl.ServicioSerieProductoImpl
 * JD-Core Version:    0.7.0.1
 */