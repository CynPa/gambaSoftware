/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CargoEmpleado;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.TypedQuery;
/*  9:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 10:   */ import javax.persistence.criteria.CriteriaQuery;
/* 11:   */ import javax.persistence.criteria.Path;
/* 12:   */ import javax.persistence.criteria.Root;
/* 13:   */ 
/* 14:   */ @Stateless
/* 15:   */ public class CargoEmpleadoDao
/* 16:   */   extends AbstractDaoAS2<CargoEmpleado>
/* 17:   */ {
/* 18:   */   public CargoEmpleadoDao()
/* 19:   */   {
/* 20:39 */     super(CargoEmpleado.class);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public CargoEmpleado cargarDetalle(int idCargoEmpleado)
/* 24:   */   {
/* 25:49 */     return null;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public CargoEmpleado buscarPorNombre(String nombre)
/* 29:   */   {
/* 30:53 */     List<CargoEmpleado> cargoEmpleado = new ArrayList();
/* 31:54 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 32:55 */     CriteriaQuery<CargoEmpleado> criteriaQuery = criteriaBuilder.createQuery(CargoEmpleado.class);
/* 33:56 */     Root<CargoEmpleado> from = criteriaQuery.from(CargoEmpleado.class);
/* 34:   */     
/* 35:58 */     Path<String> pathNombreCargoEmpresa = from.get("nombre");
/* 36:59 */     criteriaQuery.where(criteriaBuilder.equal(pathNombreCargoEmpresa, nombre));
/* 37:60 */     CriteriaQuery<CargoEmpleado> select = criteriaQuery.select(from);
/* 38:61 */     TypedQuery<CargoEmpleado> typedQuery = this.em.createQuery(select);
/* 39:62 */     cargoEmpleado = typedQuery.getResultList();
/* 40:63 */     if (cargoEmpleado.size() > 0) {
/* 41:64 */       return (CargoEmpleado)cargoEmpleado.get(0);
/* 42:   */     }
/* 43:66 */     return null;
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CargoEmpleadoDao
 * JD-Core Version:    0.7.0.1
 */