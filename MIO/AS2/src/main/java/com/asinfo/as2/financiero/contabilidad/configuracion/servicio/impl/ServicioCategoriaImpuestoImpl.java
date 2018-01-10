/*   1:    */ package com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CategoriaImpuestoDao;
/*   4:    */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*   5:    */ import com.asinfo.as2.entities.Impuesto;
/*   6:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   7:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*   8:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuestoRemoto;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.ejb.Stateless;
/*  13:    */ 
/*  14:    */ @Stateless
/*  15:    */ public class ServicioCategoriaImpuestoImpl
/*  16:    */   implements ServicioCategoriaImpuesto, ServicioCategoriaImpuestoRemoto
/*  17:    */ {
/*  18:    */   @EJB
/*  19:    */   private CategoriaImpuestoDao categoriaImpuestoDao;
/*  20:    */   
/*  21:    */   public void guardar(CategoriaImpuesto categoriaImpuesto)
/*  22:    */     throws ExcepcionAS2
/*  23:    */   {
/*  24: 46 */     if (categoriaImpuesto.getId() == 0)
/*  25:    */     {
/*  26: 47 */       List<Impuesto> lista = categoriaImpuesto.getListaImpuesto();
/*  27: 48 */       categoriaImpuesto.setListaImpuesto(null);
/*  28: 49 */       this.categoriaImpuestoDao.guardar(categoriaImpuesto);
/*  29: 50 */       categoriaImpuesto.setListaImpuesto(lista);
/*  30: 51 */       this.categoriaImpuestoDao.guardar(categoriaImpuesto);
/*  31:    */     }
/*  32:    */     else
/*  33:    */     {
/*  34: 54 */       this.categoriaImpuestoDao.guardar(categoriaImpuesto);
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void eliminar(CategoriaImpuesto categoriaImpuesto)
/*  39:    */     throws ExcepcionAS2
/*  40:    */   {
/*  41: 68 */     categoriaImpuesto = this.categoriaImpuestoDao.cargarDetalle(categoriaImpuesto.getId());
/*  42:    */     
/*  43: 70 */     categoriaImpuesto.setEliminado(true);
/*  44:    */     
/*  45: 72 */     guardar(categoriaImpuesto);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public CategoriaImpuesto buscarPorId(Integer id)
/*  49:    */   {
/*  50: 82 */     return (CategoriaImpuesto)this.categoriaImpuestoDao.buscarPorId(id);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public CategoriaImpuesto cargarDetalle(int idCategoriaImpuesto)
/*  54:    */   {
/*  55: 92 */     return this.categoriaImpuestoDao.cargarDetalle(idCategoriaImpuesto);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<CategoriaImpuesto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  59:    */   {
/*  60:104 */     return this.categoriaImpuestoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public List<CategoriaImpuesto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  64:    */   {
/*  65:115 */     return this.categoriaImpuestoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int contarPorCriterio(Map<String, String> filters)
/*  69:    */   {
/*  70:125 */     return this.categoriaImpuestoDao.contarPorCriterio(filters);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public CategoriaImpuesto buscarCodigo(int idOrganizacion, String codigo)
/*  74:    */     throws ExcepcionAS2
/*  75:    */   {
/*  76:130 */     return this.categoriaImpuestoDao.buscarCodigo(idOrganizacion, codigo);
/*  77:    */   }
/*  78:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioCategoriaImpuestoImpl
 * JD-Core Version:    0.7.0.1
 */