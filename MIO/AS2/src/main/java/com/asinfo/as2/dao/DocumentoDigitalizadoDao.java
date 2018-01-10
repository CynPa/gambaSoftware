/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CategoriaDocumentoDigitalizado;
/*  4:   */ import com.asinfo.as2.entities.DocumentoDigitalizado;
/*  5:   */ import java.util.List;
/*  6:   */ import java.util.Map;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ import javax.persistence.EntityManager;
/*  9:   */ import javax.persistence.TypedQuery;
/* 10:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 11:   */ import javax.persistence.criteria.CriteriaQuery;
/* 12:   */ import javax.persistence.criteria.Expression;
/* 13:   */ import javax.persistence.criteria.JoinType;
/* 14:   */ import javax.persistence.criteria.Predicate;
/* 15:   */ import javax.persistence.criteria.Root;
/* 16:   */ 
/* 17:   */ @Stateless
/* 18:   */ public class DocumentoDigitalizadoDao
/* 19:   */   extends AbstractDaoAS2<DocumentoDigitalizado>
/* 20:   */ {
/* 21:   */   public DocumentoDigitalizadoDao()
/* 22:   */   {
/* 23:21 */     super(DocumentoDigitalizado.class);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public DocumentoDigitalizado cargarDetalles(int idDocumentoDigitalizado)
/* 27:   */   {
/* 28:25 */     DocumentoDigitalizado documentoDigitalizado = (DocumentoDigitalizado)buscarPorId(Integer.valueOf(idDocumentoDigitalizado));
/* 29:26 */     documentoDigitalizado.getCategoriaDocumentoDigitalizado().getIdCategoriaDocumentoDigitalizado();
/* 30:27 */     return documentoDigitalizado;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<DocumentoDigitalizado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:32 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 36:33 */     CriteriaQuery<DocumentoDigitalizado> criteriaQuery = criteriaBuilder.createQuery(DocumentoDigitalizado.class);
/* 37:34 */     Root<DocumentoDigitalizado> from = criteriaQuery.from(DocumentoDigitalizado.class);
/* 38:35 */     from.fetch("categoriaDocumentoDigitalizado", JoinType.LEFT);
/* 39:   */     
/* 40:37 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 41:   */     
/* 42:39 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 43:40 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 44:   */     
/* 45:42 */     CriteriaQuery<DocumentoDigitalizado> select = criteriaQuery.select(from);
/* 46:43 */     TypedQuery<DocumentoDigitalizado> typedQuery = this.em.createQuery(select);
/* 47:   */     
/* 48:45 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 49:   */     
/* 50:47 */     return typedQuery.getResultList();
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DocumentoDigitalizadoDao
 * JD-Core Version:    0.7.0.1
 */