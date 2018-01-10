/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetalleVersionPlanComision;
/*   4:    */ import com.asinfo.as2.entities.DetalleVersionPlanComisionRangoDias;
/*   5:    */ import com.asinfo.as2.entities.DetalleVersionPlanComisionSupervisor;
/*   6:    */ import com.asinfo.as2.entities.PlanComision;
/*   7:    */ import com.asinfo.as2.entities.VersionPlanComision;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ import javax.persistence.EntityManager;
/*  12:    */ import javax.persistence.TypedQuery;
/*  13:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  14:    */ import javax.persistence.criteria.CriteriaQuery;
/*  15:    */ import javax.persistence.criteria.Expression;
/*  16:    */ import javax.persistence.criteria.Join;
/*  17:    */ import javax.persistence.criteria.JoinType;
/*  18:    */ import javax.persistence.criteria.Order;
/*  19:    */ import javax.persistence.criteria.Path;
/*  20:    */ import javax.persistence.criteria.Predicate;
/*  21:    */ import javax.persistence.criteria.Root;
/*  22:    */ 
/*  23:    */ @Stateless
/*  24:    */ public class PlanComisionDao
/*  25:    */   extends AbstractDaoAS2<PlanComision>
/*  26:    */ {
/*  27:    */   public PlanComisionDao()
/*  28:    */   {
/*  29: 40 */     super(PlanComision.class);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public List<PlanComision> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filtros)
/*  33:    */   {
/*  34: 44 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  35: 45 */     CriteriaQuery<PlanComision> criteriaQuery = criteriaBuilder.createQuery(PlanComision.class);
/*  36: 46 */     Root<PlanComision> from = criteriaQuery.from(PlanComision.class);
/*  37:    */     
/*  38: 48 */     List<Expression<?>> expresiones = obtenerExpresiones(filtros, criteriaBuilder, from);
/*  39: 49 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  40:    */     
/*  41: 51 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  42:    */     
/*  43: 53 */     CriteriaQuery<PlanComision> select = criteriaQuery.select(from);
/*  44:    */     
/*  45: 55 */     TypedQuery<PlanComision> typedQuery = this.em.createQuery(select);
/*  46: 56 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  47:    */     
/*  48: 58 */     return typedQuery.getResultList();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public PlanComision cargarDetalle(int idPlanComision)
/*  52:    */   {
/*  53: 63 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  54:    */     
/*  55:    */ 
/*  56: 66 */     CriteriaQuery<PlanComision> cqCabecera = criteriaBuilder.createQuery(PlanComision.class);
/*  57: 67 */     Root<PlanComision> fromCabecera = cqCabecera.from(PlanComision.class);
/*  58:    */     
/*  59: 69 */     Path<Integer> pathId = fromCabecera.get("idPlanComision");
/*  60: 70 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idPlanComision)));
/*  61: 71 */     CriteriaQuery<PlanComision> selectPlanComision = cqCabecera.select(fromCabecera);
/*  62:    */     
/*  63: 73 */     PlanComision planComision = (PlanComision)this.em.createQuery(selectPlanComision).getSingleResult();
/*  64:    */     
/*  65:    */ 
/*  66: 76 */     CriteriaQuery<VersionPlanComision> cqVersion = criteriaBuilder.createQuery(VersionPlanComision.class);
/*  67: 77 */     Root<VersionPlanComision> fromVersion = cqVersion.from(VersionPlanComision.class);
/*  68:    */     
/*  69: 79 */     Path<Integer> pathIdVersion = fromVersion.join("planComision").get("idPlanComision");
/*  70: 80 */     cqVersion.where(criteriaBuilder.equal(pathIdVersion, Integer.valueOf(idPlanComision)));
/*  71: 81 */     CriteriaQuery<VersionPlanComision> selectVersion = cqVersion.select(fromVersion);
/*  72: 82 */     selectVersion.orderBy(new Order[] { criteriaBuilder.desc(fromVersion.get("predeterminado")) });
/*  73:    */     
/*  74: 84 */     List<VersionPlanComision> listaVersionPlanComision = this.em.createQuery(selectVersion).getResultList();
/*  75: 85 */     planComision.setListaVersionPlanComision(listaVersionPlanComision);
/*  76: 86 */     for (VersionPlanComision versionPlanComision : listaVersionPlanComision)
/*  77:    */     {
/*  78: 89 */       CriteriaQuery<DetalleVersionPlanComisionSupervisor> cqDetalleSupervisor = criteriaBuilder.createQuery(DetalleVersionPlanComisionSupervisor.class);
/*  79: 90 */       Root<DetalleVersionPlanComisionSupervisor> fromDetalleSupervisor = cqDetalleSupervisor.from(DetalleVersionPlanComisionSupervisor.class);
/*  80:    */       
/*  81: 92 */       fromDetalleSupervisor.fetch("empresa", JoinType.INNER);
/*  82: 93 */       fromDetalleSupervisor.fetch("agenteComercial", JoinType.INNER);
/*  83:    */       
/*  84: 95 */       Path<Integer> pathIdDetalleSupervisor = fromDetalleSupervisor.join("versionPlanComision").get("idVersionPlanComision");
/*  85: 96 */       cqDetalleSupervisor.where(criteriaBuilder.equal(pathIdDetalleSupervisor, Integer.valueOf(versionPlanComision.getId())));
/*  86: 97 */       CriteriaQuery<DetalleVersionPlanComisionSupervisor> selectDetalleSupervisor = cqDetalleSupervisor.select(fromDetalleSupervisor);
/*  87:    */       
/*  88:    */ 
/*  89:100 */       List<DetalleVersionPlanComisionSupervisor> listaDetalleVersionPlanComisionSupervisor = this.em.createQuery(selectDetalleSupervisor).getResultList();
/*  90:101 */       versionPlanComision.setListaDetalleVersionPlanComisionSupervisor(listaDetalleVersionPlanComisionSupervisor);
/*  91:    */       
/*  92:    */ 
/*  93:104 */       CriteriaQuery<DetalleVersionPlanComision> cqDetalle = criteriaBuilder.createQuery(DetalleVersionPlanComision.class);
/*  94:105 */       Root<DetalleVersionPlanComision> fromDetalle = cqDetalle.from(DetalleVersionPlanComision.class);
/*  95:    */       
/*  96:107 */       fromDetalle.fetch("categoriaProducto", JoinType.LEFT);
/*  97:108 */       fromDetalle.fetch("subcategoriaProducto", JoinType.LEFT);
/*  98:109 */       fromDetalle.fetch("producto", JoinType.LEFT);
/*  99:    */       
/* 100:111 */       Path<Integer> pathIdDetalle = fromDetalle.join("versionPlanComision").get("idVersionPlanComision");
/* 101:112 */       cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(versionPlanComision.getId())));
/* 102:113 */       CriteriaQuery<DetalleVersionPlanComision> selectDetalle = cqDetalle.select(fromDetalle);
/* 103:    */       
/* 104:115 */       List<DetalleVersionPlanComision> listaDetalleVersionPlanComision = this.em.createQuery(selectDetalle).getResultList();
/* 105:116 */       versionPlanComision.setListaDetalleVersionPlanComision(listaDetalleVersionPlanComision);
/* 106:117 */       for (DetalleVersionPlanComision detalleVersionPlanComision : listaDetalleVersionPlanComision)
/* 107:    */       {
/* 108:120 */         CriteriaQuery<DetalleVersionPlanComisionRangoDias> cqDetalleRangoDias = criteriaBuilder.createQuery(DetalleVersionPlanComisionRangoDias.class);
/* 109:121 */         Root<DetalleVersionPlanComisionRangoDias> fromDetalleRangoDias = cqDetalleRangoDias.from(DetalleVersionPlanComisionRangoDias.class);
/* 110:    */         
/* 111:123 */         fromDetalleRangoDias.fetch("rangoDiasCobro", JoinType.INNER);
/* 112:    */         
/* 113:125 */         Path<Integer> pathIdDetalleRangoDias = fromDetalleRangoDias.join("detalleVersionPlanComision").get("idDetalleVersionPlanComision");
/* 114:126 */         cqDetalleRangoDias.where(criteriaBuilder.equal(pathIdDetalleRangoDias, Integer.valueOf(detalleVersionPlanComision.getId())));
/* 115:127 */         CriteriaQuery<DetalleVersionPlanComisionRangoDias> selectDetalleRangoDias = cqDetalleRangoDias.select(fromDetalleRangoDias);
/* 116:    */         
/* 117:    */ 
/* 118:130 */         List<DetalleVersionPlanComisionRangoDias> listaDetalleVersionPlanComisionRangoDias = this.em.createQuery(selectDetalleRangoDias).getResultList();
/* 119:131 */         detalleVersionPlanComision.setListaDetalleVersionPlanComisionRangoDias(listaDetalleVersionPlanComisionRangoDias);
/* 120:    */       }
/* 121:    */     }
/* 122:135 */     return planComision;
/* 123:    */   }
/* 124:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PlanComisionDao
 * JD-Core Version:    0.7.0.1
 */