/*   1:    */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CategoriaProductoDao;
/*   4:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   5:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   6:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   7:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*   8:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProductoRemoto;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ 
/*  14:    */ @Stateless
/*  15:    */ public class ServicioCategoriaProductoImpl
/*  16:    */   implements ServicioCategoriaProducto, ServicioCategoriaProductoRemoto
/*  17:    */ {
/*  18:    */   @EJB
/*  19:    */   private CategoriaProductoDao categoriaProductoDao;
/*  20:    */   
/*  21:    */   public void guardar(CategoriaProducto categoriaProducto)
/*  22:    */   {
/*  23: 46 */     this.categoriaProductoDao.guardar(categoriaProducto);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void eliminar(CategoriaProducto categoriaProducto)
/*  27:    */   {
/*  28: 55 */     this.categoriaProductoDao.eliminar(categoriaProducto);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public CategoriaProducto buscarPorId(int id)
/*  32:    */   {
/*  33: 67 */     return (CategoriaProducto)this.categoriaProductoDao.buscarPorId(Integer.valueOf(id));
/*  34:    */   }
/*  35:    */   
/*  36:    */   public List<CategoriaProducto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  37:    */   {
/*  38: 73 */     return this.categoriaProductoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public List<CategoriaProducto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  42:    */   {
/*  43: 86 */     return this.categoriaProductoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int contarPorCriterio(Map<String, String> filters)
/*  47:    */   {
/*  48: 94 */     return this.categoriaProductoDao.contarPorCriterio(filters);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public CategoriaProducto buscarPorCodigo(String codigo)
/*  52:    */     throws ExcepcionAS2
/*  53:    */   {
/*  54:105 */     return this.categoriaProductoDao.buscarPorCodigo(codigo);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public List<SubcategoriaProducto> obtenerListaSubcategoriaProducto(int idOrganizacion, CategoriaProducto categoriaProducto, String consulta)
/*  58:    */   {
/*  59:110 */     return this.categoriaProductoDao.obtenerListaSubcategoriaProducto(idOrganizacion, categoriaProducto, consulta);
/*  60:    */   }
/*  61:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioCategoriaProductoImpl
 * JD-Core Version:    0.7.0.1
 */