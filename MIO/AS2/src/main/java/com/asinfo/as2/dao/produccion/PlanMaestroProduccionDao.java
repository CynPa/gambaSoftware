/*   1:    */ package com.asinfo.as2.dao.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.produccion.DetallePlanMaestroProduccion;
/*   5:    */ import com.asinfo.as2.entities.produccion.PlanMaestroProduccion;
/*   6:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ import javax.persistence.EntityManager;
/*  11:    */ import javax.persistence.Query;
/*  12:    */ import javax.persistence.TypedQuery;
/*  13:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  14:    */ import javax.persistence.criteria.CriteriaQuery;
/*  15:    */ import javax.persistence.criteria.Fetch;
/*  16:    */ import javax.persistence.criteria.Join;
/*  17:    */ import javax.persistence.criteria.JoinType;
/*  18:    */ import javax.persistence.criteria.Path;
/*  19:    */ import javax.persistence.criteria.Root;
/*  20:    */ 
/*  21:    */ @Stateless
/*  22:    */ public class PlanMaestroProduccionDao
/*  23:    */   extends AbstractDaoAS2<PlanMaestroProduccion>
/*  24:    */ {
/*  25:    */   public PlanMaestroProduccionDao()
/*  26:    */   {
/*  27: 42 */     super(PlanMaestroProduccion.class);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public PlanMaestroProduccion cargarDetalle(int id)
/*  31:    */   {
/*  32: 47 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  33:    */     
/*  34:    */ 
/*  35: 50 */     CriteriaQuery<PlanMaestroProduccion> cqCabecera = criteriaBuilder.createQuery(PlanMaestroProduccion.class);
/*  36: 51 */     Root<PlanMaestroProduccion> fromCabecera = cqCabecera.from(PlanMaestroProduccion.class);
/*  37:    */     
/*  38: 53 */     Path<Integer> pathId = fromCabecera.get("idPlanMaestroProduccion");
/*  39: 54 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(id)));
/*  40: 55 */     CriteriaQuery<PlanMaestroProduccion> cqPlanMaestroProduccion = cqCabecera.select(fromCabecera);
/*  41:    */     
/*  42: 57 */     PlanMaestroProduccion planMaestroProduccion = (PlanMaestroProduccion)this.em.createQuery(cqPlanMaestroProduccion).getSingleResult();
/*  43:    */     
/*  44:    */ 
/*  45: 60 */     CriteriaQuery<DetallePlanMaestroProduccion> cqDetalle = criteriaBuilder.createQuery(DetallePlanMaestroProduccion.class);
/*  46: 61 */     Root<DetallePlanMaestroProduccion> fromDetalle = cqDetalle.from(DetallePlanMaestroProduccion.class);
/*  47:    */     
/*  48: 63 */     fromDetalle.fetch("planMaestroProduccion", JoinType.LEFT);
/*  49: 64 */     Fetch<Object, Object> producto = fromDetalle.fetch("producto", JoinType.LEFT);
/*  50: 65 */     fromDetalle.fetch("rutaFabricacion", JoinType.LEFT);
/*  51: 66 */     Fetch<Object, Object> subCategoria = producto.fetch("subcategoriaProducto", JoinType.LEFT);
/*  52: 67 */     subCategoria.fetch("categoriaProducto", JoinType.LEFT);
/*  53:    */     
/*  54: 69 */     Path<Integer> pathIdPlanMaestroProduccion = fromDetalle.join("planMaestroProduccion").get("idPlanMaestroProduccion");
/*  55: 70 */     cqDetalle.where(criteriaBuilder.equal(pathIdPlanMaestroProduccion, Integer.valueOf(id)));
/*  56: 71 */     CriteriaQuery<DetallePlanMaestroProduccion> selectDetalle = cqDetalle.select(fromDetalle);
/*  57:    */     
/*  58: 73 */     List<DetallePlanMaestroProduccion> listaDetallePlanMaestroProduccion = this.em.createQuery(selectDetalle).getResultList();
/*  59: 74 */     planMaestroProduccion.setListaDetallePlanMaestroProduccion(listaDetallePlanMaestroProduccion);
/*  60:    */     
/*  61: 76 */     return planMaestroProduccion;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public List<PlanMaestroProduccion> buscarPlanesMaestrosProduccionPorRangoFecha(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/*  65:    */   {
/*  66: 81 */     StringBuilder sql = new StringBuilder();
/*  67: 82 */     sql.append(" SELECT pmp FROM PlanMaestroProduccion pmp ");
/*  68: 83 */     sql.append(" WHERE ((pmp.fechaInicio < :fechaHasta AND pmp.fechaFin > :fechaHasta )");
/*  69: 84 */     sql.append(" OR (pmp.fechaInicio < :fechaDesde AND pmp.fechaFin > :fechaDesde ))");
/*  70: 85 */     sql.append(" AND ( pmp.idOrganizacion =:idOrganizacion )");
/*  71:    */     
/*  72: 87 */     Query query = this.em.createQuery(sql.toString());
/*  73: 88 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  74: 89 */     query.setParameter("fechaDesde", fechaDesde);
/*  75: 90 */     query.setParameter("fechaHasta", fechaHasta);
/*  76:    */     
/*  77: 92 */     return query.getResultList();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public List<DetallePlanMaestroProduccion> obtenerDetallesPlanMaestro(PlanMaestroProduccion planMaestroProduccion, Mes mes)
/*  81:    */   {
/*  82: 97 */     StringBuilder sql = new StringBuilder();
/*  83: 98 */     sql.append(" SELECT dpmp FROM DetallePlanMaestroProduccion dpmp ");
/*  84: 99 */     sql.append(" INNER JOIN FETCH dpmp.planMaestroProduccion pmp ");
/*  85:100 */     sql.append(" INNER JOIN FETCH dpmp.producto pro ");
/*  86:101 */     sql.append(" LEFT JOIN FETCH dpmp.rutaFabricacion rfa ");
/*  87:102 */     sql.append(" WHERE (dpmp.meses LIKE :mes)");
/*  88:103 */     sql.append(" AND (pmp.idPlanMaestroProduccion = :idPlanMaestroProduccion)");
/*  89:    */     
/*  90:105 */     Query query = this.em.createQuery(sql.toString());
/*  91:106 */     query.setParameter("mes", "%" + mes.ordinal() + "%");
/*  92:107 */     query.setParameter("idPlanMaestroProduccion", Integer.valueOf(planMaestroProduccion.getId()));
/*  93:    */     
/*  94:109 */     return query.getResultList();
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.produccion.PlanMaestroProduccionDao
 * JD-Core Version:    0.7.0.1
 */