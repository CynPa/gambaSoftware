/*   1:    */ package com.asinfo.as2.datosbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CategoriaEmpresaDao;
/*   4:    */ import com.asinfo.as2.dao.GenericoDao;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresaRemote;
/*   7:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   8:    */ import com.asinfo.as2.entities.DocumentoDigitalizadoCategoriaEmpresa;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.ejb.EJB;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ 
/*  15:    */ @Stateless
/*  16:    */ public class ServicioCategoriaEmpresaImpl
/*  17:    */   implements ServicioCategoriaEmpresa, ServicioCategoriaEmpresaRemote
/*  18:    */ {
/*  19:    */   @EJB
/*  20:    */   private CategoriaEmpresaDao categoriaEmpresaDao;
/*  21:    */   @EJB
/*  22:    */   private transient GenericoDao<DocumentoDigitalizadoCategoriaEmpresa> documentoDigitalizadoCategoriaEmpresaDao;
/*  23:    */   
/*  24:    */   public void guardar(CategoriaEmpresa entidad)
/*  25:    */   {
/*  26: 42 */     this.categoriaEmpresaDao.guardar(entidad);
/*  27: 43 */     for (DocumentoDigitalizadoCategoriaEmpresa documento : entidad.getListaDocumentoDigitalizadoCategoriaEmpresa()) {
/*  28: 44 */       this.documentoDigitalizadoCategoriaEmpresaDao.guardar(documento);
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void eliminar(CategoriaEmpresa entidad)
/*  33:    */   {
/*  34: 55 */     this.categoriaEmpresaDao.eliminar(entidad);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public CategoriaEmpresa buscarPorId(Integer id)
/*  38:    */   {
/*  39: 65 */     return (CategoriaEmpresa)this.categoriaEmpresaDao.buscarPorId(id);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public List<CategoriaEmpresa> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  43:    */   {
/*  44: 75 */     return this.categoriaEmpresaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public List<CategoriaEmpresa> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  48:    */   {
/*  49: 85 */     return this.categoriaEmpresaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int contarPorCriterio(Map<String, String> filters)
/*  53:    */   {
/*  54: 95 */     return this.categoriaEmpresaDao.contarPorCriterio(filters);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public CategoriaEmpresa cargarDetalle(int id)
/*  58:    */   {
/*  59:105 */     return this.categoriaEmpresaDao.cargarDetalle(id);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public CategoriaEmpresa buscarPorNombre(String nombre)
/*  63:    */   {
/*  64:115 */     return this.categoriaEmpresaDao.buscarPorNombre(nombre);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public CategoriaEmpresa buscarPorCodigo(String codigo)
/*  68:    */   {
/*  69:127 */     return this.categoriaEmpresaDao.buscarPorCodigo(codigo);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public CategoriaEmpresa buscarPorCodigo(int idOrganizacion, String codigo)
/*  73:    */   {
/*  74:132 */     Map<String, String> filters = new HashMap();
/*  75:133 */     filters.put("codigo", codigo);
/*  76:134 */     filters.put("idOrganizacion", "" + idOrganizacion);
/*  77:135 */     List<CategoriaEmpresa> categoriaEmpresa = obtenerListaCombo("codigo", false, filters);
/*  78:137 */     if (categoriaEmpresa.size() > 0) {
/*  79:138 */       return (CategoriaEmpresa)categoriaEmpresa.get(0);
/*  80:    */     }
/*  81:140 */     return null;
/*  82:    */   }
/*  83:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioCategoriaEmpresaImpl
 * JD-Core Version:    0.7.0.1
 */