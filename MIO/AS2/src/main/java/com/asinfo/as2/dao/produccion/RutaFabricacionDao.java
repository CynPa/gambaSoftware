/*   1:    */ package com.asinfo.as2.dao.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.Producto;
/*   5:    */ import com.asinfo.as2.entities.produccion.OperacionProduccion;
/*   6:    */ import com.asinfo.as2.entities.produccion.ProductoRutaFabricacion;
/*   7:    */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ import javax.persistence.EntityManager;
/*  12:    */ import javax.persistence.Query;
/*  13:    */ import javax.persistence.TemporalType;
/*  14:    */ import javax.persistence.TypedQuery;
/*  15:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  16:    */ import javax.persistence.criteria.CriteriaQuery;
/*  17:    */ import javax.persistence.criteria.Fetch;
/*  18:    */ import javax.persistence.criteria.JoinType;
/*  19:    */ import javax.persistence.criteria.Order;
/*  20:    */ import javax.persistence.criteria.Root;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class RutaFabricacionDao
/*  24:    */   extends AbstractDaoAS2<RutaFabricacion>
/*  25:    */ {
/*  26:    */   public RutaFabricacionDao()
/*  27:    */   {
/*  28: 43 */     super(RutaFabricacion.class);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public RutaFabricacion cargarDetalle(int idRutaFabricacion)
/*  32:    */   {
/*  33: 53 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  34:    */     
/*  35:    */ 
/*  36: 56 */     CriteriaQuery<RutaFabricacion> cqCabecera = criteriaBuilder.createQuery(RutaFabricacion.class);
/*  37: 57 */     Root<RutaFabricacion> fromCabecera = cqCabecera.from(RutaFabricacion.class);
/*  38: 58 */     cqCabecera.where(criteriaBuilder.equal(fromCabecera.get("idRutaFabricacion"), Integer.valueOf(idRutaFabricacion)));
/*  39: 59 */     CriteriaQuery<RutaFabricacion> select = cqCabecera.select(fromCabecera);
/*  40:    */     
/*  41: 61 */     RutaFabricacion rutaFabricacion = (RutaFabricacion)this.em.createQuery(select).getSingleResult();
/*  42: 62 */     this.em.detach(rutaFabricacion);
/*  43:    */     
/*  44:    */ 
/*  45: 65 */     CriteriaQuery<OperacionProduccion> cqOperacion = criteriaBuilder.createQuery(OperacionProduccion.class);
/*  46: 66 */     Root<OperacionProduccion> fromOperacion = cqOperacion.from(OperacionProduccion.class);
/*  47: 67 */     fromOperacion.fetch("maquina", JoinType.LEFT);
/*  48: 68 */     fromOperacion.fetch("tareaProduccion", JoinType.LEFT);
/*  49: 69 */     fromOperacion.fetch("centroTrabajo", JoinType.LEFT);
/*  50: 70 */     cqOperacion.where(criteriaBuilder.equal(fromOperacion.get("rutaFabricacion"), rutaFabricacion));
/*  51: 71 */     CriteriaQuery<OperacionProduccion> selectOperacion = cqOperacion.select(fromOperacion);
/*  52: 72 */     cqOperacion.orderBy(new Order[] { criteriaBuilder.asc(fromOperacion.get("orden")) });
/*  53:    */     
/*  54: 74 */     List<OperacionProduccion> listaOperacionProduccion = this.em.createQuery(selectOperacion).getResultList();
/*  55: 75 */     rutaFabricacion.setListaOperacionProduccion(listaOperacionProduccion);
/*  56:    */     
/*  57:    */ 
/*  58: 78 */     CriteriaQuery<ProductoRutaFabricacion> cqProducto = criteriaBuilder.createQuery(ProductoRutaFabricacion.class);
/*  59: 79 */     Root<ProductoRutaFabricacion> fromProducto = cqProducto.from(ProductoRutaFabricacion.class);
/*  60: 80 */     fromProducto.fetch("producto", JoinType.LEFT).fetch("unidad", JoinType.LEFT);
/*  61: 81 */     cqProducto.where(criteriaBuilder.equal(fromProducto.get("rutaFabricacion"), rutaFabricacion));
/*  62: 82 */     CriteriaQuery<ProductoRutaFabricacion> selectProducto = cqProducto.select(fromProducto);
/*  63:    */     
/*  64: 84 */     List<ProductoRutaFabricacion> listaProductoRutaFabricacion = this.em.createQuery(selectProducto).getResultList();
/*  65: 85 */     rutaFabricacion.setListaProductoRutaFabricacion(listaProductoRutaFabricacion);
/*  66:    */     
/*  67: 87 */     return rutaFabricacion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public List<RutaFabricacion> autocompletarRutaFabricacion(Producto producto, String consulta)
/*  71:    */   {
/*  72: 92 */     StringBuilder sql = new StringBuilder();
/*  73: 93 */     sql.append(" SELECT rf FROM ProductoRutaFabricacion prf");
/*  74: 94 */     sql.append(" JOIN prf.rutaFabricacion rf");
/*  75: 95 */     sql.append(" WHERE prf.producto = :producto");
/*  76: 96 */     sql.append(" AND rf.nombre LIKE :consulta");
/*  77:    */     
/*  78: 98 */     Query query = this.em.createQuery(sql.toString());
/*  79: 99 */     query.setParameter("producto", producto);
/*  80:100 */     query.setParameter("consulta", "%" + consulta + "%");
/*  81:    */     
/*  82:102 */     return query.getResultList();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public List<OperacionProduccion> buscarOperacionesActivasPorRutaFabricacion(RutaFabricacion rutaFabricacion, Date fecha)
/*  86:    */   {
/*  87:107 */     StringBuilder sql = new StringBuilder();
/*  88:108 */     sql.append(" SELECT op ");
/*  89:109 */     sql.append(" FROM OperacionProduccion op ");
/*  90:110 */     sql.append(" INNER JOIN FETCH op.rutaFabricacion rf ");
/*  91:111 */     sql.append(" LEFT JOIN FETCH op.tareaProduccion tp ");
/*  92:112 */     sql.append(" LEFT JOIN FETCH op.centroTrabajo ct  ");
/*  93:113 */     sql.append(" LEFT JOIN FETCH op.maquina m ");
/*  94:114 */     sql.append(" WHERE rf.idRutaFabricacion =:idRutaFabricacion ");
/*  95:115 */     sql.append(" AND op.fechaDesde <= :fecha ");
/*  96:116 */     sql.append(" AND (op.fechaHasta IS NULL OR op.fechaHasta >= :fecha )");
/*  97:117 */     sql.append(" ORDER BY op.orden ");
/*  98:    */     
/*  99:119 */     Query query = this.em.createQuery(sql.toString());
/* 100:120 */     query.setParameter("idRutaFabricacion", Integer.valueOf(rutaFabricacion.getId()));
/* 101:121 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 102:    */     
/* 103:123 */     return query.getResultList();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public List<OperacionProduccion> getListaOperacionProduccionActiva(int idOrganizacion, RutaFabricacion rutaFabricacion, Date fecha)
/* 107:    */   {
/* 108:128 */     StringBuilder sql = new StringBuilder();
/* 109:129 */     sql.append(" SELECT op ");
/* 110:130 */     sql.append(" FROM OperacionProduccion op ");
/* 111:131 */     sql.append(" INNER JOIN FETCH op.rutaFabricacion rf ");
/* 112:132 */     sql.append(" LEFT JOIN FETCH op.tareaProduccion tp ");
/* 113:133 */     sql.append(" LEFT JOIN FETCH op.centroTrabajo ct  ");
/* 114:134 */     sql.append(" LEFT JOIN FETCH op.maquina m ");
/* 115:135 */     sql.append(" WHERE op.fechaDesde <= :fecha ");
/* 116:136 */     sql.append(" AND (op.fechaHasta IS NULL OR op.fechaHasta >= :fecha )");
/* 117:137 */     sql.append(" AND rf.idOrganizacion = :idOrganizacion ");
/* 118:138 */     if (rutaFabricacion != null) {
/* 119:139 */       sql.append(" AND rf.idRutaFabricacion = :idRutaFabricacion ");
/* 120:    */     }
/* 121:141 */     sql.append(" ORDER BY op.orden ");
/* 122:    */     
/* 123:143 */     Query query = this.em.createQuery(sql.toString());
/* 124:144 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 125:145 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 126:146 */     if (rutaFabricacion != null) {
/* 127:147 */       query.setParameter("idRutaFabricacion", Integer.valueOf(rutaFabricacion.getId()));
/* 128:    */     }
/* 129:150 */     return query.getResultList();
/* 130:    */   }
/* 131:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.produccion.RutaFabricacionDao
 * JD-Core Version:    0.7.0.1
 */