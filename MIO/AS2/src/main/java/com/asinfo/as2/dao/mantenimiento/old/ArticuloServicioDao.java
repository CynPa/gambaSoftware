/*   1:    */ package com.asinfo.as2.dao.mantenimiento.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.Organizacion;
/*   5:    */ import com.asinfo.as2.entities.mantenimiento.old.ArticuloServicio;
/*   6:    */ import com.asinfo.as2.entities.mantenimiento.old.HistoricoArticuloServicio;
/*   7:    */ import com.asinfo.as2.enumeraciones.TipoArticuloServicioEnum;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import java.util.Set;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ import javax.persistence.EntityManager;
/*  16:    */ import javax.persistence.Query;
/*  17:    */ import javax.persistence.TypedQuery;
/*  18:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  19:    */ import javax.persistence.criteria.CriteriaQuery;
/*  20:    */ import javax.persistence.criteria.Expression;
/*  21:    */ import javax.persistence.criteria.Join;
/*  22:    */ import javax.persistence.criteria.JoinType;
/*  23:    */ import javax.persistence.criteria.Path;
/*  24:    */ import javax.persistence.criteria.Predicate;
/*  25:    */ import javax.persistence.criteria.Root;
/*  26:    */ 
/*  27:    */ @Stateless
/*  28:    */ public class ArticuloServicioDao
/*  29:    */   extends AbstractDaoAS2<ArticuloServicio>
/*  30:    */ {
/*  31:    */   public ArticuloServicioDao()
/*  32:    */   {
/*  33: 49 */     super(ArticuloServicio.class);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public ArticuloServicio cargarDetalle(int idArticuloServicio)
/*  37:    */   {
/*  38: 60 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  39:    */     
/*  40: 62 */     CriteriaQuery<ArticuloServicio> cqCabecera = criteriaBuilder.createQuery(ArticuloServicio.class);
/*  41:    */     
/*  42: 64 */     Root<ArticuloServicio> fromCabecera = cqCabecera.from(ArticuloServicio.class);
/*  43:    */     
/*  44: 66 */     fromCabecera.fetch("grupoArticuloServicio", JoinType.LEFT);
/*  45: 67 */     fromCabecera.fetch("categoriaArticuloServicio", JoinType.LEFT);
/*  46: 68 */     fromCabecera.fetch("tipoCicloOperacion", JoinType.LEFT);
/*  47:    */     
/*  48: 70 */     Path<Integer> pathId = fromCabecera.get("idArticuloServicio");
/*  49: 71 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idArticuloServicio)));
/*  50: 72 */     CriteriaQuery<ArticuloServicio> selectArticuloServicio = cqCabecera.select(fromCabecera);
/*  51:    */     
/*  52: 74 */     ArticuloServicio articuloServicio = (ArticuloServicio)this.em.createQuery(selectArticuloServicio).getSingleResult();
/*  53:    */     
/*  54: 76 */     CriteriaQuery<HistoricoArticuloServicio> cqHistoricoArticuloServicio = criteriaBuilder.createQuery(HistoricoArticuloServicio.class);
/*  55: 77 */     Root<HistoricoArticuloServicio> fromHistoricoArticuloServicio = cqHistoricoArticuloServicio.from(HistoricoArticuloServicio.class);
/*  56: 78 */     fromHistoricoArticuloServicio.fetch("articuloServicioHijo", JoinType.LEFT);
/*  57:    */     
/*  58: 80 */     CriteriaQuery<HistoricoArticuloServicio> selectHistoricoArticuloServicio = cqHistoricoArticuloServicio.select(fromHistoricoArticuloServicio);
/*  59:    */     
/*  60: 82 */     List<Predicate> predicates = new ArrayList();
/*  61:    */     
/*  62: 84 */     pathId = fromHistoricoArticuloServicio.join("articuloServicioPadre").get("idArticuloServicio");
/*  63: 85 */     predicates.add(criteriaBuilder.equal(pathId, String.valueOf(idArticuloServicio)));
/*  64: 86 */     selectHistoricoArticuloServicio.where((Predicate[])predicates.toArray(new Predicate[predicates.size()]));
/*  65:    */     
/*  66: 88 */     List<HistoricoArticuloServicio> listaHistoricoArticuloServicio = this.em.createQuery(selectHistoricoArticuloServicio).getResultList();
/*  67: 89 */     articuloServicio.setListaHistoricoArticuloServicioHijo(listaHistoricoArticuloServicio);
/*  68:    */     
/*  69: 91 */     return articuloServicio;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public List<ArticuloServicio> obtenerArticulosDisponibles()
/*  73:    */   {
/*  74: 97 */     String sql = " SELECT a FROM ArticuloServicio a  WHERE a.idArticuloServicio NOT IN  (SELECT h.articuloServicioHijo FROM HistoricoArticuloServicio h WHERE h.fechaHasta = null) AND a.idOrganizacion = :idOrganizacion AND a.tipoArticuloServicio != :tipoArticuloServicioEnum";
/*  75:    */     
/*  76:    */ 
/*  77:100 */     int idOrganizacion = AppUtil.getOrganizacion().getIdOrganizacion();
/*  78:    */     
/*  79:102 */     Query query = this.em.createQuery(sql).setParameter("idOrganizacion", Integer.valueOf(idOrganizacion)).setParameter("tipoArticuloServicioEnum", TipoArticuloServicioEnum.ESTRUCTURA);
/*  80:    */     
/*  81:104 */     return query.getResultList();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public List<ArticuloServicio> autocompletarArticuloServicio(String sortField, boolean sortOrder, Map<String, String> filters)
/*  85:    */   {
/*  86:109 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  87:110 */     CriteriaQuery<ArticuloServicio> criteriaQuery = criteriaBuilder.createQuery(ArticuloServicio.class);
/*  88:111 */     Root<ArticuloServicio> from = criteriaQuery.from(ArticuloServicio.class);
/*  89:    */     
/*  90:113 */     Predicate disjunction = criteriaBuilder.disjunction();
/*  91:115 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/*  92:    */     {
/*  93:116 */       String filterProperty = (String)it.next();
/*  94:118 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/*  95:    */       {
/*  96:119 */         String filterValue = (String)filters.get(filterProperty);
/*  97:    */         
/*  98:121 */         disjunction.getExpressions().add(criteriaBuilder
/*  99:122 */           .like(criteriaBuilder.lower(from.get(filterProperty).as(String.class)), "%" + filterValue + "%"));
/* 100:    */       }
/* 101:    */     }
/* 102:127 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 103:    */     
/* 104:129 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 105:130 */     empresiones.clear();
/* 106:131 */     empresiones.add(disjunction);
/* 107:132 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 108:    */     
/* 109:134 */     CriteriaQuery<ArticuloServicio> select = criteriaQuery.select(from);
/* 110:    */     
/* 111:136 */     TypedQuery<ArticuloServicio> typedQuery = this.em.createQuery(select);
/* 112:    */     
/* 113:138 */     return typedQuery.getResultList();
/* 114:    */   }
/* 115:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.mantenimiento.old.ArticuloServicioDao
 * JD-Core Version:    0.7.0.1
 */