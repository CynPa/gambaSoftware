/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Documento;
/*   4:    */ import com.asinfo.as2.entities.DocumentoGastoImportacion;
/*   5:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*   6:    */ import com.asinfo.as2.entities.GastoImportacion;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ import javax.persistence.EntityManager;
/*  11:    */ import javax.persistence.Query;
/*  12:    */ import javax.persistence.TypedQuery;
/*  13:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  14:    */ import javax.persistence.criteria.CriteriaQuery;
/*  15:    */ import javax.persistence.criteria.Expression;
/*  16:    */ import javax.persistence.criteria.Predicate;
/*  17:    */ import javax.persistence.criteria.Root;
/*  18:    */ 
/*  19:    */ @Stateless
/*  20:    */ public class DocumentoGastoImportacionDao
/*  21:    */   extends AbstractDaoAS2<DocumentoGastoImportacion>
/*  22:    */ {
/*  23:    */   public DocumentoGastoImportacionDao()
/*  24:    */   {
/*  25: 42 */     super(DocumentoGastoImportacion.class);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public List<DocumentoGastoImportacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  29:    */   {
/*  30: 52 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  31: 53 */     CriteriaQuery<DocumentoGastoImportacion> criteriaQuery = criteriaBuilder.createQuery(DocumentoGastoImportacion.class);
/*  32: 54 */     Root<DocumentoGastoImportacion> from = criteriaQuery.from(DocumentoGastoImportacion.class);
/*  33:    */     
/*  34: 56 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  35: 57 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  36:    */     
/*  37: 59 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  38:    */     
/*  39: 61 */     CriteriaQuery<DocumentoGastoImportacion> select = criteriaQuery.select(from);
/*  40: 62 */     TypedQuery<DocumentoGastoImportacion> typedQuery = this.em.createQuery(select);
/*  41: 63 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  42:    */     
/*  43: 65 */     return typedQuery.getResultList();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public List<DocumentoGastoImportacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  47:    */   {
/*  48: 74 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  49: 75 */     CriteriaQuery<DocumentoGastoImportacion> criteriaQuery = criteriaBuilder.createQuery(DocumentoGastoImportacion.class);
/*  50: 76 */     Root<DocumentoGastoImportacion> from = criteriaQuery.from(DocumentoGastoImportacion.class);
/*  51:    */     
/*  52: 78 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  53: 79 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  54:    */     
/*  55: 81 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  56:    */     
/*  57: 83 */     CriteriaQuery<DocumentoGastoImportacion> select = criteriaQuery.select(from);
/*  58: 84 */     TypedQuery<DocumentoGastoImportacion> typedQuery = this.em.createQuery(select);
/*  59:    */     
/*  60: 86 */     return typedQuery.getResultList();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public DocumentoGastoImportacion cargarDetalle(int idDocumentoGastoImportacion)
/*  64:    */   {
/*  65: 96 */     DocumentoGastoImportacion documentoGastoImportacion = (DocumentoGastoImportacion)buscarPorId(Integer.valueOf(idDocumentoGastoImportacion));
/*  66: 98 */     if (documentoGastoImportacion.getDocumento() != null) {
/*  67: 99 */       documentoGastoImportacion.getDocumento().getId();
/*  68:    */     }
/*  69:101 */     if (documentoGastoImportacion.getGastoImportacion() != null)
/*  70:    */     {
/*  71:102 */       documentoGastoImportacion.getGastoImportacion().getId();
/*  72:103 */       documentoGastoImportacion.getGastoImportacion().getTipoProrrateo();
/*  73:    */     }
/*  74:106 */     return documentoGastoImportacion;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public List<DocumentoGastoImportacion> obtenerListaDocumentoGastoImportacion(Documento documento, boolean indicadorGastoObligatorio)
/*  78:    */   {
/*  79:118 */     StringBuilder sql = new StringBuilder();
/*  80:119 */     sql.append("SELECT dgi ");
/*  81:120 */     sql.append(" FROM DocumentoGastoImportacion dgi");
/*  82:121 */     sql.append(" INNER JOIN dgi.gastoImportacion gi");
/*  83:122 */     sql.append(" INNER JOIN dgi.documento d");
/*  84:123 */     sql.append(" WHERE dgi.documento = :documento");
/*  85:124 */     sql.append(" AND dgi.indicadorGastoObligatorio = :indicadorGastoObligatorio");
/*  86:125 */     Query query2 = this.em.createQuery(sql.toString()).setParameter("documento", documento).setParameter("indicadorGastoObligatorio", Boolean.valueOf(indicadorGastoObligatorio));
/*  87:    */     
/*  88:127 */     return query2.getResultList();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public List<DocumentoGastoImportacion> obtenerListaDocumentoGastoImportacion(FacturaProveedor facturaProveedor, boolean indicadorGastoObligatorio, List<GastoImportacion> lista)
/*  92:    */   {
/*  93:134 */     String sql1 = "SELECT fp.documento FROM FacturaProveedorImportacion fpi INNER JOIN fpi.facturaProveedor fp where fp = :facturaProveedor";
/*  94:135 */     Query query1 = this.em.createQuery(sql1).setParameter("facturaProveedor", facturaProveedor);
/*  95:136 */     Documento documento = (Documento)query1.getSingleResult();
/*  96:    */     
/*  97:138 */     String sql2 = "SELECT dgi FROM DocumentoGastoImportacion dgi INNER JOIN dgi.gastoImportacion gi INNER JOIN dgi.documento d WHERE dgi.documento = :documento AND dgi.indicadorGastoObligatorio = :indicadorGastoObligatorio";
/*  98:141 */     if (!lista.isEmpty()) {
/*  99:142 */       sql2 = sql2 + " AND dgi.gastoImportacion NOT IN ( :lista ) ";
/* 100:    */     }
/* 101:144 */     Query query2 = this.em.createQuery(sql2).setParameter("documento", documento).setParameter("indicadorGastoObligatorio", Boolean.valueOf(indicadorGastoObligatorio));
/* 102:146 */     if (!lista.isEmpty()) {
/* 103:147 */       query2.setParameter("lista", lista);
/* 104:    */     }
/* 105:150 */     return query2.getResultList();
/* 106:    */   }
/* 107:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DocumentoGastoImportacionDao
 * JD-Core Version:    0.7.0.1
 */