/*   1:    */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.SubcategoriaProductoDao;
/*   4:    */ import com.asinfo.as2.dao.UnidadConversionDao;
/*   5:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   6:    */ import com.asinfo.as2.entities.UnidadConversion;
/*   7:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   8:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*   9:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProductoRemoto;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class ServicioSubcategoriaProductoImpl
/*  18:    */   implements ServicioSubcategoriaProducto, ServicioSubcategoriaProductoRemoto
/*  19:    */ {
/*  20:    */   @EJB
/*  21:    */   private SubcategoriaProductoDao categoriaProductoDao;
/*  22:    */   @EJB
/*  23:    */   private UnidadConversionDao unidadConversionDao;
/*  24:    */   @EJB
/*  25:    */   private SubcategoriaProductoDao subcategoriaProductoDao;
/*  26:    */   @EJB
/*  27:    */   private ServicioUnidadConversion servicioUnidadConversion;
/*  28:    */   
/*  29:    */   public void guardar(SubcategoriaProducto subcategoriaProducto)
/*  30:    */   {
/*  31: 51 */     for (UnidadConversion unidadConversion : subcategoriaProducto.getListaUnidadConversion())
/*  32:    */     {
/*  33: 52 */       this.servicioUnidadConversion.actualizaUnidadConversion(unidadConversion);
/*  34: 53 */       this.unidadConversionDao.guardar(unidadConversion);
/*  35:    */     }
/*  36: 55 */     this.categoriaProductoDao.guardar(subcategoriaProducto);
/*  37: 57 */     for (UnidadConversion unidadConversion : subcategoriaProducto.getListaUnidadConversion()) {
/*  38: 58 */       this.servicioUnidadConversion.actualizaUnidadConversion(unidadConversion);
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void eliminar(SubcategoriaProducto subcategoriaProducto)
/*  43:    */   {
/*  44: 69 */     this.categoriaProductoDao.eliminar(subcategoriaProducto);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public SubcategoriaProducto buscarPorId(int id)
/*  48:    */   {
/*  49: 79 */     return (SubcategoriaProducto)this.categoriaProductoDao.buscarPorId(Integer.valueOf(id));
/*  50:    */   }
/*  51:    */   
/*  52:    */   public SubcategoriaProducto cargarDetalle(int idSubcategoriaProducto)
/*  53:    */   {
/*  54: 90 */     return this.categoriaProductoDao.cargarDetalle(idSubcategoriaProducto);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public List<SubcategoriaProducto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  58:    */   {
/*  59:102 */     return this.categoriaProductoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public List<SubcategoriaProducto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  63:    */   {
/*  64:112 */     return this.categoriaProductoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int contarPorCriterio(Map<String, String> filters)
/*  68:    */   {
/*  69:122 */     return this.categoriaProductoDao.contarPorCriterio(filters);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public SubcategoriaProducto buscarPorCodigo(String codigo)
/*  73:    */     throws ExcepcionAS2
/*  74:    */   {
/*  75:132 */     return this.subcategoriaProductoDao.buscarPorCodigo(codigo);
/*  76:    */   }
/*  77:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioSubcategoriaProductoImpl
 * JD-Core Version:    0.7.0.1
 */