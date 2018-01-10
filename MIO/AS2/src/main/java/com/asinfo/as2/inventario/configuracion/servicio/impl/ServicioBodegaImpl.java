/*   1:    */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.BodegaDao;
/*   4:    */ import com.asinfo.as2.dao.GenericoDao;
/*   5:    */ import com.asinfo.as2.dao.UbicacionDao;
/*   6:    */ import com.asinfo.as2.entities.Bodega;
/*   7:    */ import com.asinfo.as2.entities.UbicacionBodega;
/*   8:    */ import com.asinfo.as2.entities.UsuarioBodega;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.ejb.Stateless;
/*  16:    */ 
/*  17:    */ @Stateless
/*  18:    */ public class ServicioBodegaImpl
/*  19:    */   implements ServicioBodega
/*  20:    */ {
/*  21:    */   @EJB
/*  22:    */   private BodegaDao bodegaDao;
/*  23:    */   @EJB
/*  24:    */   private GenericoDao<UbicacionBodega> ubicacionBodegaDao;
/*  25:    */   @EJB
/*  26:    */   private UbicacionDao ubicacionDao;
/*  27:    */   
/*  28:    */   public void guardar(Bodega bodega)
/*  29:    */   {
/*  30: 54 */     for (UbicacionBodega ubicacionBodega : bodega.getUbicacionesBodega()) {
/*  31: 55 */       this.ubicacionBodegaDao.guardar(ubicacionBodega);
/*  32:    */     }
/*  33: 58 */     this.ubicacionDao.guardar(bodega.getUbicacion());
/*  34: 59 */     this.bodegaDao.guardar(bodega);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void eliminar(Bodega bodega)
/*  38:    */   {
/*  39: 69 */     this.bodegaDao.eliminar(bodega);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Bodega buscarPorId(Integer id)
/*  43:    */   {
/*  44: 80 */     return (Bodega)this.bodegaDao.buscarPorId(id);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Bodega cargarDetalle(Bodega bodega)
/*  48:    */   {
/*  49: 90 */     return this.bodegaDao.cargarDetalle(bodega);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public List<Bodega> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  53:    */   {
/*  54:100 */     return this.bodegaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public List<Bodega> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  58:    */   {
/*  59:110 */     return this.bodegaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public List<Bodega> obtenerBodegaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  63:    */   {
/*  64:120 */     if (filters == null) {
/*  65:121 */       filters = new HashMap();
/*  66:    */     }
/*  67:124 */     return this.bodegaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int contarPorCriterio(Map<String, String> filters)
/*  71:    */   {
/*  72:135 */     return this.bodegaDao.contarPorCriterio(filters);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Bodega buscarPorCodigo(String codigo)
/*  76:    */     throws ExcepcionAS2
/*  77:    */   {
/*  78:145 */     return this.bodegaDao.buscarPorCodigo(codigo);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public List<Bodega> obtenerListaComboPorUsuario(int idUsuario, int idOrganizacion)
/*  82:    */   {
/*  83:155 */     return this.bodegaDao.obtenerListaComboPorUsuario(idUsuario, idOrganizacion);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public List<UsuarioBodega> obtenerListaComboPorUsuarioBodega(int idUsuario, int idOrganizacion)
/*  87:    */   {
/*  88:165 */     return this.bodegaDao.obtenerListaComboPorUsuarioBodega(idUsuario, idOrganizacion);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public List<Bodega> obtenerListaComboPorUsuario(int idUsuario, int idOrganizacion, int idSucursal)
/*  92:    */   {
/*  93:170 */     return this.bodegaDao.obtenerListaComboPorUsuario(idUsuario, idOrganizacion, idSucursal);
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioBodegaImpl
 * JD-Core Version:    0.7.0.1
 */