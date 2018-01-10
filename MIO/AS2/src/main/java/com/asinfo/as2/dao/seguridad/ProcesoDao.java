/*   1:    */ package com.asinfo.as2.dao.seguridad;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.seguridad.EntidadProceso;
/*   5:    */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ import javax.persistence.EntityManager;
/*  11:    */ import javax.persistence.Query;
/*  12:    */ import javax.persistence.TypedQuery;
/*  13:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  14:    */ import javax.persistence.criteria.CriteriaQuery;
/*  15:    */ import javax.persistence.criteria.Expression;
/*  16:    */ import javax.persistence.criteria.JoinType;
/*  17:    */ import javax.persistence.criteria.Predicate;
/*  18:    */ import javax.persistence.criteria.Root;
/*  19:    */ 
/*  20:    */ @Stateless
/*  21:    */ public class ProcesoDao
/*  22:    */   extends AbstractDaoAS2<EntidadProceso>
/*  23:    */ {
/*  24:    */   public ProcesoDao()
/*  25:    */   {
/*  26: 44 */     super(EntidadProceso.class);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public List<EntidadProceso> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  30:    */   {
/*  31: 54 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  32: 55 */     CriteriaQuery<EntidadProceso> criteriaQuery = criteriaBuilder.createQuery(EntidadProceso.class);
/*  33: 56 */     Root<EntidadProceso> from = criteriaQuery.from(EntidadProceso.class);
/*  34: 57 */     from.fetch("modulo", JoinType.LEFT);
/*  35: 58 */     from.fetch("sistema", JoinType.LEFT);
/*  36:    */     
/*  37: 60 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  38: 61 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  39: 62 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  40:    */     
/*  41: 64 */     CriteriaQuery<EntidadProceso> select = criteriaQuery.select(from);
/*  42:    */     
/*  43: 66 */     TypedQuery<EntidadProceso> typedQuery = this.em.createQuery(select);
/*  44: 67 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  45:    */     
/*  46: 69 */     return typedQuery.getResultList();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public List<EntidadProceso> obtenerLista(List<Integer> lista, EntidadSistema sistema)
/*  50:    */   {
/*  51: 81 */     List<EntidadProceso> listaEntidadProceso = new ArrayList();
/*  52: 83 */     if ((lista != null) && (!lista.isEmpty()))
/*  53:    */     {
/*  54: 84 */       StringBuilder sql = new StringBuilder();
/*  55: 85 */       sql.append("SELECT p FROM EntidadProceso p ");
/*  56: 86 */       sql.append(" WHERE idProceso in(:lista) ");
/*  57: 87 */       sql.append(" AND indicadorMostrarMenu=true ");
/*  58: 88 */       sql.append(" AND p.activo = true ");
/*  59: 89 */       sql.append(" AND p.sistema = :sistema");
/*  60: 90 */       sql.append(" ORDER BY p.modulo.orden,p.orden ");
/*  61:    */       
/*  62: 92 */       Query query = this.em.createQuery(sql.toString());
/*  63: 93 */       query.setParameter("lista", lista);
/*  64: 94 */       query.setParameter("sistema", sistema);
/*  65: 95 */       listaEntidadProceso = query.getResultList();
/*  66:    */     }
/*  67: 98 */     return listaEntidadProceso;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public List<EntidadProceso> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filtros)
/*  71:    */   {
/*  72:103 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  73:104 */     CriteriaQuery<EntidadProceso> cq = cb.createQuery(this.claseEntidad);
/*  74:105 */     Root<EntidadProceso> from = cq.from(this.claseEntidad);
/*  75:    */     
/*  76:    */ 
/*  77:108 */     agregarFiltros(filtros, cb, cq, from);
/*  78:    */     
/*  79:    */ 
/*  80:111 */     agregarOrdenamiento(sortField, sortOrder, cb, cq, from);
/*  81:    */     
/*  82:113 */     return this.em.createQuery(cq).getResultList();
/*  83:    */   }
/*  84:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.seguridad.ProcesoDao
 * JD-Core Version:    0.7.0.1
 */