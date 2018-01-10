/*   1:    */ package com.asinfo.as2.compras.importaciones.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioPartidaArancelaria;
/*   5:    */ import com.asinfo.as2.dao.DetallePartidaArancelariaDao;
/*   6:    */ import com.asinfo.as2.dao.PartidaArancelariaDao;
/*   7:    */ import com.asinfo.as2.dao.ProductoDao;
/*   8:    */ import com.asinfo.as2.entities.DetallePartidaArancelaria;
/*   9:    */ import com.asinfo.as2.entities.PartidaArancelaria;
/*  10:    */ import com.asinfo.as2.entities.Producto;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class ServicioPartidaArancelariaImpl
/*  18:    */   implements ServicioPartidaArancelaria
/*  19:    */ {
/*  20:    */   @EJB
/*  21:    */   private PartidaArancelariaDao partidaArancelariaDao;
/*  22:    */   @EJB
/*  23:    */   private DetallePartidaArancelariaDao detallePartidaArancelariaDao;
/*  24:    */   @EJB
/*  25:    */   private ProductoDao productoDao;
/*  26:    */   
/*  27:    */   public void guardar(PartidaArancelaria partidaArancelaria)
/*  28:    */     throws ExcepcionAS2Compras
/*  29:    */   {
/*  30: 52 */     for (DetallePartidaArancelaria detallePartidaArancelaria : partidaArancelaria.getListaDetallePartidaArancelaria()) {
/*  31: 53 */       this.detallePartidaArancelariaDao.guardar(detallePartidaArancelaria);
/*  32:    */     }
/*  33: 56 */     this.partidaArancelariaDao.guardar(partidaArancelaria);
/*  34: 58 */     for (Producto producto : partidaArancelaria.getListaProducto())
/*  35:    */     {
/*  36: 59 */       if (producto.isEliminado())
/*  37:    */       {
/*  38: 60 */         producto.setPartidaArancelaria(null);
/*  39: 61 */         producto.setEliminado(false);
/*  40:    */       }
/*  41: 64 */       this.productoDao.guardar(producto);
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void eliminar(PartidaArancelaria partidaArancelaria)
/*  46:    */   {
/*  47: 77 */     this.partidaArancelariaDao.eliminar(partidaArancelaria);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public PartidaArancelaria buscarPorId(int idPartidaArancelaria)
/*  51:    */   {
/*  52: 88 */     return (PartidaArancelaria)this.partidaArancelariaDao.buscarPorId(Integer.valueOf(idPartidaArancelaria));
/*  53:    */   }
/*  54:    */   
/*  55:    */   public List<PartidaArancelaria> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  56:    */   {
/*  57:100 */     return this.partidaArancelariaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public List<PartidaArancelaria> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  61:    */   {
/*  62:111 */     return this.partidaArancelariaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int contarPorCriterio(Map<String, String> filters)
/*  66:    */   {
/*  67:121 */     return this.partidaArancelariaDao.contarPorCriterio(filters);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public PartidaArancelaria cargarDetalle(int idPartidaArancelaria)
/*  71:    */   {
/*  72:131 */     return this.partidaArancelariaDao.cargarDetalle(idPartidaArancelaria);
/*  73:    */   }
/*  74:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.configuracion.servicio.impl.ServicioPartidaArancelariaImpl
 * JD-Core Version:    0.7.0.1
 */