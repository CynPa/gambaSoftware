/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   4:    */ import com.asinfo.as2.entities.DocumentoDigitalizadoCategoriaEmpresa;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.Stateless;
/*   9:    */ import javax.persistence.EntityManager;
/*  10:    */ import javax.persistence.TypedQuery;
/*  11:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  12:    */ import javax.persistence.criteria.CriteriaQuery;
/*  13:    */ import javax.persistence.criteria.Fetch;
/*  14:    */ import javax.persistence.criteria.JoinType;
/*  15:    */ import javax.persistence.criteria.Order;
/*  16:    */ import javax.persistence.criteria.Root;
/*  17:    */ 
/*  18:    */ @Stateless
/*  19:    */ public class CategoriaEmpresaDao
/*  20:    */   extends AbstractDaoAS2<CategoriaEmpresa>
/*  21:    */ {
/*  22:    */   public CategoriaEmpresaDao()
/*  23:    */   {
/*  24: 40 */     super(CategoriaEmpresa.class);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public CategoriaEmpresa cargarDetalle(int idCategoriaEmpresa)
/*  28:    */   {
/*  29: 53 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  30:    */     
/*  31:    */ 
/*  32: 56 */     CriteriaQuery<CategoriaEmpresa> cqCabecera = criteriaBuilder.createQuery(CategoriaEmpresa.class);
/*  33: 57 */     Root<CategoriaEmpresa> fromCabecera = cqCabecera.from(CategoriaEmpresa.class);
/*  34: 58 */     fromCabecera.fetch("cuentaContableCliente", JoinType.LEFT);
/*  35: 59 */     fromCabecera.fetch("cuentaContableProveedor", JoinType.LEFT);
/*  36: 60 */     fromCabecera.fetch("cuentaContableAnticipoCliente", JoinType.LEFT);
/*  37: 61 */     fromCabecera.fetch("cuentaContableAnticipoClienteNotaCredito", JoinType.LEFT);
/*  38: 62 */     fromCabecera.fetch("cuentaContableAnticipoProveedorNotaCredito", JoinType.LEFT);
/*  39:    */     
/*  40: 64 */     fromCabecera.fetch("cuentaContableAnticipoProveedor", JoinType.LEFT);
/*  41: 65 */     fromCabecera.fetch("cuentaContableSueldoPorPagar", JoinType.LEFT);
/*  42: 66 */     fromCabecera.fetch("cuentaContableIvaPresuntivo", JoinType.LEFT);
/*  43: 67 */     fromCabecera.fetch("cuentaContable2X1000", JoinType.LEFT);
/*  44: 68 */     fromCabecera.fetch("cuentaContable3X1000", JoinType.LEFT);
/*  45:    */     
/*  46: 70 */     cqCabecera.where(criteriaBuilder.equal(fromCabecera.get("idCategoriaEmpresa"), Integer.valueOf(idCategoriaEmpresa)));
/*  47: 71 */     CriteriaQuery<CategoriaEmpresa> select = cqCabecera.select(fromCabecera);
/*  48:    */     
/*  49: 73 */     CategoriaEmpresa categoriaEmpresa = (CategoriaEmpresa)this.em.createQuery(select).getSingleResult();
/*  50:    */     
/*  51:    */ 
/*  52: 76 */     CriteriaQuery<DocumentoDigitalizadoCategoriaEmpresa> cqDocumento = criteriaBuilder.createQuery(DocumentoDigitalizadoCategoriaEmpresa.class);
/*  53: 77 */     Root<DocumentoDigitalizadoCategoriaEmpresa> fromDocumento = cqDocumento.from(DocumentoDigitalizadoCategoriaEmpresa.class);
/*  54: 78 */     fromDocumento.fetch("documentoDigitalizado", JoinType.LEFT).fetch("categoriaDocumentoDigitalizado", JoinType.LEFT);
/*  55: 79 */     cqDocumento.where(criteriaBuilder.equal(fromDocumento.get("categoriaEmpresa"), categoriaEmpresa));
/*  56: 80 */     CriteriaQuery<DocumentoDigitalizadoCategoriaEmpresa> selectDocumento = cqDocumento.select(fromDocumento);
/*  57: 81 */     cqDocumento.orderBy(new Order[] { criteriaBuilder.asc(fromDocumento.get("idDocumentoDigitalizadoCategoriaEmpresa")) });
/*  58:    */     
/*  59: 83 */     List<DocumentoDigitalizadoCategoriaEmpresa> listaDocumentoDigitalizadoCategoriaEmpresa = this.em.createQuery(selectDocumento).getResultList();
/*  60: 84 */     categoriaEmpresa.setListaDocumentoDigitalizadoCategoriaEmpresa(listaDocumentoDigitalizadoCategoriaEmpresa);
/*  61: 85 */     return categoriaEmpresa;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public CategoriaEmpresa buscarPorNombre(String nombre)
/*  65:    */   {
/*  66: 90 */     Map<String, String> filters = new HashMap();
/*  67: 91 */     filters.put("nombre", nombre);
/*  68: 92 */     List<CategoriaEmpresa> categoriaEmpresa = obtenerListaCombo("nombre", false, filters);
/*  69: 94 */     if (categoriaEmpresa.size() > 0) {
/*  70: 95 */       return (CategoriaEmpresa)categoriaEmpresa.get(0);
/*  71:    */     }
/*  72: 97 */     return null;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public CategoriaEmpresa buscarPorCodigo(String codigo)
/*  76:    */   {
/*  77:102 */     Map<String, String> filters = new HashMap();
/*  78:103 */     filters.put("codigo", codigo);
/*  79:104 */     List<CategoriaEmpresa> categoriaEmpresa = obtenerListaCombo("codigo", false, filters);
/*  80:106 */     if (categoriaEmpresa.size() > 0) {
/*  81:107 */       return (CategoriaEmpresa)categoriaEmpresa.get(0);
/*  82:    */     }
/*  83:109 */     return null;
/*  84:    */   }
/*  85:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CategoriaEmpresaDao
 * JD-Core Version:    0.7.0.1
 */