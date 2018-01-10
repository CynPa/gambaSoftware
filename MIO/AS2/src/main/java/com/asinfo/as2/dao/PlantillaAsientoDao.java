/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DetallePlantillaAsiento;
/*  4:   */ import com.asinfo.as2.entities.PlantillaAsiento;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.TypedQuery;
/*  9:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 10:   */ import javax.persistence.criteria.CriteriaQuery;
/* 11:   */ import javax.persistence.criteria.Join;
/* 12:   */ import javax.persistence.criteria.JoinType;
/* 13:   */ import javax.persistence.criteria.Order;
/* 14:   */ import javax.persistence.criteria.Path;
/* 15:   */ import javax.persistence.criteria.Root;
/* 16:   */ 
/* 17:   */ @Stateless
/* 18:   */ public class PlantillaAsientoDao
/* 19:   */   extends AbstractDaoAS2<PlantillaAsiento>
/* 20:   */ {
/* 21:   */   public PlantillaAsientoDao()
/* 22:   */   {
/* 23:38 */     super(PlantillaAsiento.class);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public PlantillaAsiento cargarDetalle(Integer idPlantillaAsiento)
/* 27:   */   {
/* 28:42 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 29:   */     
/* 30:   */ 
/* 31:45 */     CriteriaQuery<PlantillaAsiento> cqCabecera = criteriaBuilder.createQuery(PlantillaAsiento.class);
/* 32:46 */     Root<PlantillaAsiento> fromCabecera = cqCabecera.from(PlantillaAsiento.class);
/* 33:   */     
/* 34:48 */     Path<Integer> pathId = fromCabecera.get("idPlantillaAsiento");
/* 35:49 */     cqCabecera.where(criteriaBuilder.equal(pathId, idPlantillaAsiento));
/* 36:50 */     CriteriaQuery<PlantillaAsiento> selectPlantillaAsiento = cqCabecera.select(fromCabecera);
/* 37:   */     
/* 38:52 */     PlantillaAsiento plantillaAsiento = (PlantillaAsiento)this.em.createQuery(selectPlantillaAsiento).getSingleResult();
/* 39:53 */     this.em.detach(plantillaAsiento);
/* 40:   */     
/* 41:   */ 
/* 42:56 */     CriteriaQuery<DetallePlantillaAsiento> cqDetalle = criteriaBuilder.createQuery(DetallePlantillaAsiento.class);
/* 43:57 */     Root<DetallePlantillaAsiento> fromDetalle = cqDetalle.from(DetallePlantillaAsiento.class);
/* 44:58 */     fromDetalle.fetch("cuentaContable", JoinType.LEFT);
/* 45:59 */     fromDetalle.fetch("dimensionContable1", JoinType.LEFT);
/* 46:60 */     fromDetalle.fetch("dimensionContable2", JoinType.LEFT);
/* 47:61 */     fromDetalle.fetch("dimensionContable3", JoinType.LEFT);
/* 48:62 */     fromDetalle.fetch("dimensionContable4", JoinType.LEFT);
/* 49:63 */     fromDetalle.fetch("dimensionContable5", JoinType.LEFT);
/* 50:   */     
/* 51:65 */     Path<Integer> pathIdDetalle = fromDetalle.join("plantillaAsiento").get("idPlantillaAsiento");
/* 52:66 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, idPlantillaAsiento));
/* 53:67 */     cqDetalle.orderBy(new Order[] { criteriaBuilder.asc(fromDetalle.get("idDetallePlantillaAsiento")) });
/* 54:   */     
/* 55:69 */     CriteriaQuery<DetallePlantillaAsiento> selectDetalle = cqDetalle.select(fromDetalle);
/* 56:70 */     List<DetallePlantillaAsiento> listaDetallePlantillaAsiento = this.em.createQuery(selectDetalle).getResultList();
/* 57:71 */     plantillaAsiento.setListaDetallePlantillaAsiento(listaDetallePlantillaAsiento);
/* 58:   */     
/* 59:73 */     return plantillaAsiento;
/* 60:   */   }
/* 61:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PlantillaAsientoDao
 * JD-Core Version:    0.7.0.1
 */