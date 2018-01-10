/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.ComponenteCosto;
/*   4:    */ import com.asinfo.as2.entities.CuentaContable;
/*   5:    */ import com.asinfo.as2.entities.DetalleComponenteCosto;
/*   6:    */ import com.asinfo.as2.entities.DetalleComponenteCostoDistribucion;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.ejb.Stateless;
/*   9:    */ import javax.persistence.EntityManager;
/*  10:    */ import javax.persistence.Query;
/*  11:    */ import javax.persistence.TypedQuery;
/*  12:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  13:    */ import javax.persistence.criteria.CriteriaQuery;
/*  14:    */ import javax.persistence.criteria.Join;
/*  15:    */ import javax.persistence.criteria.JoinType;
/*  16:    */ import javax.persistence.criteria.Path;
/*  17:    */ import javax.persistence.criteria.Root;
/*  18:    */ 
/*  19:    */ @Stateless
/*  20:    */ public class ComponenteCostoDao
/*  21:    */   extends AbstractDaoAS2<ComponenteCosto>
/*  22:    */ {
/*  23:    */   public ComponenteCostoDao()
/*  24:    */   {
/*  25: 37 */     super(ComponenteCosto.class);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public ComponenteCosto cargarDetalle(int idComponenteCosto)
/*  29:    */   {
/*  30: 42 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  31:    */     
/*  32:    */ 
/*  33: 45 */     CriteriaQuery<ComponenteCosto> cqCabecera = criteriaBuilder.createQuery(ComponenteCosto.class);
/*  34: 46 */     Root<ComponenteCosto> fromCabecera = cqCabecera.from(ComponenteCosto.class);
/*  35:    */     
/*  36: 48 */     Path<Integer> pathId = fromCabecera.get("idComponenteCosto");
/*  37: 49 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idComponenteCosto)));
/*  38: 50 */     CriteriaQuery<ComponenteCosto> selectComponenteCosto = cqCabecera.select(fromCabecera);
/*  39:    */     
/*  40: 52 */     ComponenteCosto componenteCosto = (ComponenteCosto)this.em.createQuery(selectComponenteCosto).getSingleResult();
/*  41:    */     
/*  42:    */ 
/*  43: 55 */     CriteriaQuery<DetalleComponenteCosto> cqDetalle = criteriaBuilder.createQuery(DetalleComponenteCosto.class);
/*  44: 56 */     Root<DetalleComponenteCosto> fromDetalle = cqDetalle.from(DetalleComponenteCosto.class);
/*  45:    */     
/*  46: 58 */     fromDetalle.fetch("componenteCosto", JoinType.INNER);
/*  47: 59 */     fromDetalle.fetch("cuentaContable", JoinType.INNER);
/*  48: 60 */     fromDetalle.fetch("cuentaContableCierre", JoinType.LEFT);
/*  49:    */     
/*  50: 62 */     Path<Integer> pathIdDetalle = fromDetalle.join("componenteCosto").get("idComponenteCosto");
/*  51: 63 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idComponenteCosto)));
/*  52: 64 */     CriteriaQuery<DetalleComponenteCosto> selectDetalleComponenteCosto = cqDetalle.select(fromDetalle);
/*  53:    */     
/*  54: 66 */     List<DetalleComponenteCosto> listaDetalleComponenteCosto = this.em.createQuery(selectDetalleComponenteCosto).getResultList();
/*  55: 67 */     componenteCosto.setListaDetalleComponenteCosto(listaDetalleComponenteCosto);
/*  56:    */     
/*  57:    */ 
/*  58:    */ 
/*  59: 71 */     CriteriaQuery<DetalleComponenteCostoDistribucion> cqDetalleDistribucion = criteriaBuilder.createQuery(DetalleComponenteCostoDistribucion.class);
/*  60: 72 */     Root<DetalleComponenteCostoDistribucion> fromDetalleDistribucion = cqDetalleDistribucion.from(DetalleComponenteCostoDistribucion.class);
/*  61:    */     
/*  62: 74 */     fromDetalleDistribucion.fetch("componenteCosto", JoinType.INNER);
/*  63: 75 */     fromDetalleDistribucion.fetch("rutaFabricacion", JoinType.INNER);
/*  64: 76 */     fromDetalleDistribucion.fetch("dimensionContable1", JoinType.LEFT);
/*  65: 77 */     fromDetalleDistribucion.fetch("dimensionContable2", JoinType.LEFT);
/*  66: 78 */     fromDetalleDistribucion.fetch("dimensionContable3", JoinType.LEFT);
/*  67: 79 */     fromDetalleDistribucion.fetch("dimensionContable4", JoinType.LEFT);
/*  68: 80 */     fromDetalleDistribucion.fetch("dimensionContable5", JoinType.LEFT);
/*  69:    */     
/*  70: 82 */     Path<Integer> pathIdDetalleDistribucion = fromDetalleDistribucion.join("componenteCosto").get("idComponenteCosto");
/*  71: 83 */     cqDetalleDistribucion.where(criteriaBuilder.equal(pathIdDetalleDistribucion, Integer.valueOf(idComponenteCosto)));
/*  72:    */     
/*  73: 85 */     CriteriaQuery<DetalleComponenteCostoDistribucion> selectDetalleComponenteCostoDistribucion = cqDetalleDistribucion.select(fromDetalleDistribucion);
/*  74:    */     
/*  75:    */ 
/*  76: 88 */     List<DetalleComponenteCostoDistribucion> listaDetalleComponenteCostoDistribucion = this.em.createQuery(selectDetalleComponenteCostoDistribucion).getResultList();
/*  77: 89 */     componenteCosto.setListaDetalleComponenteCostoDistribucion(listaDetalleComponenteCostoDistribucion);
/*  78:    */     
/*  79: 91 */     return componenteCosto;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public List<CuentaContable> obtenerCuentasContableUtilizadas(int idOrganizacion, ComponenteCosto componenteCosto, boolean invertido)
/*  83:    */   {
/*  84: 97 */     StringBuilder sql = new StringBuilder();
/*  85: 98 */     sql.append(" SELECT cuc ");
/*  86: 99 */     sql.append(" FROM DetalleComponenteCosto dcc ");
/*  87:100 */     sql.append(" INNER JOIN dcc.componenteCosto cc ");
/*  88:101 */     sql.append(" INNER JOIN dcc.cuentaContable cuc ");
/*  89:102 */     sql.append(" WHERE cc.idOrganizacion = :idOrganizacion");
/*  90:103 */     if (!invertido) {
/*  91:104 */       sql.append(" AND cc.idComponenteCosto = :idComponenteCosto");
/*  92:    */     } else {
/*  93:106 */       sql.append(" AND cc.idComponenteCosto != :idComponenteCosto");
/*  94:    */     }
/*  95:109 */     Query query = this.em.createQuery(sql.toString());
/*  96:110 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  97:111 */     query.setParameter("idComponenteCosto", Integer.valueOf(componenteCosto.getId()));
/*  98:    */     
/*  99:113 */     return query.getResultList();
/* 100:    */   }
/* 101:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ComponenteCostoDao
 * JD-Core Version:    0.7.0.1
 */