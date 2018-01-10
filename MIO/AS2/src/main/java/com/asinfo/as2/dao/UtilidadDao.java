/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetalleUtilidad;
/*   4:    */ import com.asinfo.as2.entities.Utilidad;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.Stateless;
/*   9:    */ import javax.persistence.EntityManager;
/*  10:    */ import javax.persistence.Query;
/*  11:    */ import javax.persistence.TypedQuery;
/*  12:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  13:    */ import javax.persistence.criteria.CriteriaQuery;
/*  14:    */ import javax.persistence.criteria.Expression;
/*  15:    */ import javax.persistence.criteria.Fetch;
/*  16:    */ import javax.persistence.criteria.Join;
/*  17:    */ import javax.persistence.criteria.JoinType;
/*  18:    */ import javax.persistence.criteria.Path;
/*  19:    */ import javax.persistence.criteria.Predicate;
/*  20:    */ import javax.persistence.criteria.Root;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class UtilidadDao
/*  24:    */   extends AbstractDaoAS2<Utilidad>
/*  25:    */ {
/*  26:    */   public UtilidadDao()
/*  27:    */   {
/*  28: 44 */     super(Utilidad.class);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public List<Utilidad> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  32:    */   {
/*  33: 53 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  34: 54 */     CriteriaQuery<Utilidad> criteriaQuery = criteriaBuilder.createQuery(Utilidad.class);
/*  35: 55 */     Root<Utilidad> from = criteriaQuery.from(Utilidad.class);
/*  36:    */     
/*  37: 57 */     from.fetch("pagoRol", JoinType.LEFT);
/*  38: 58 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  39: 59 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  40: 60 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  41:    */     
/*  42: 62 */     CriteriaQuery<Utilidad> select = criteriaQuery.select(from);
/*  43: 63 */     TypedQuery<Utilidad> typedQuery = this.em.createQuery(select);
/*  44: 64 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  45:    */     
/*  46: 66 */     return typedQuery.getResultList();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Utilidad cargarDetalle(int idUtilidad)
/*  50:    */   {
/*  51: 76 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  52:    */     
/*  53: 78 */     CriteriaQuery<Utilidad> cqCabecera = criteriaBuilder.createQuery(Utilidad.class);
/*  54: 79 */     Root<Utilidad> fromCabecera = cqCabecera.from(Utilidad.class);
/*  55:    */     
/*  56: 81 */     fromCabecera.fetch("pagoRol", JoinType.LEFT);
/*  57:    */     
/*  58: 83 */     Path<Integer> pathId = fromCabecera.get("idUtilidad");
/*  59: 84 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idUtilidad)));
/*  60: 85 */     CriteriaQuery<Utilidad> selectUtilidad = cqCabecera.select(fromCabecera);
/*  61:    */     
/*  62: 87 */     Utilidad utilidad = (Utilidad)this.em.createQuery(selectUtilidad).getSingleResult();
/*  63:    */     
/*  64: 89 */     CriteriaQuery<DetalleUtilidad> cqDetalleUtilidad = criteriaBuilder.createQuery(DetalleUtilidad.class);
/*  65: 90 */     Root<DetalleUtilidad> fromDetalle = cqDetalleUtilidad.from(DetalleUtilidad.class);
/*  66:    */     
/*  67: 92 */     fromDetalle.fetch("empleado", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*  68:    */     
/*  69: 94 */     Path<Integer> pathUtilidad = fromDetalle.join("utilidad").get("idUtilidad");
/*  70:    */     
/*  71: 96 */     cqDetalleUtilidad.where(criteriaBuilder.equal(pathUtilidad, Integer.valueOf(idUtilidad)));
/*  72:    */     
/*  73: 98 */     CriteriaQuery<DetalleUtilidad> selectDetalleUtilidad = cqDetalleUtilidad.select(fromDetalle);
/*  74:    */     
/*  75:100 */     List<DetalleUtilidad> listaDetalleUtilidad = this.em.createQuery(selectDetalleUtilidad).getResultList();
/*  76:101 */     utilidad.setListaDetalleUtilidad(listaDetalleUtilidad);
/*  77:    */     
/*  78:103 */     return utilidad;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Utilidad obtenerUtilidadPorAnio(int idOrganizacion, int anio)
/*  82:    */   {
/*  83:108 */     StringBuilder sql = new StringBuilder();
/*  84:109 */     sql.append("SELECT u FROM Utilidad u WHERE u.anio=:anio AND idOrganizacion=:idOrganizacion");
/*  85:    */     
/*  86:111 */     Query query = this.em.createQuery(sql.toString());
/*  87:112 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  88:113 */     query.setParameter("anio", Integer.valueOf(anio));
/*  89:    */     
/*  90:115 */     Utilidad utilidad = query.getResultList().isEmpty() ? null : (Utilidad)query.getResultList().get(0);
/*  91:    */     
/*  92:117 */     return utilidad;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getDiasFalta(Date fechaDesde, Date fechaHasta, int idEmpleado)
/*  96:    */   {
/*  97:123 */     StringBuilder sql = new StringBuilder();
/*  98:    */     
/*  99:125 */     sql.append(" SELECT SUM(pre.diasFalta)");
/* 100:126 */     sql.append(" FROM PagoRolEmpleado pre");
/* 101:127 */     sql.append(" JOIN pre.pagoRol pr ");
/* 102:128 */     sql.append(" WHERE pr.fecha BETWEEN :fechaDesde AND :fechaHasta");
/* 103:129 */     sql.append(" AND pre.empleado.idEmpleado = :idEmpleado");
/* 104:    */     
/* 105:131 */     Query query = this.em.createQuery(sql.toString());
/* 106:132 */     query.setParameter("fechaDesde", fechaDesde);
/* 107:133 */     query.setParameter("fechaHasta", fechaHasta);
/* 108:134 */     query.setParameter("idEmpleado", Integer.valueOf(idEmpleado));
/* 109:    */     
/* 110:136 */     Long diasFalta = (Long)query.getSingleResult();
/* 111:    */     
/* 112:138 */     return diasFalta == null ? 0 : diasFalta.intValue();
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void eliminarDetalleUtilidad(int idUtilidad)
/* 116:    */   {
/* 117:142 */     Query query = this.em.createQuery("DELETE FROM DetalleUtilidad du WHERE du.utilidad.idUtilidad=:idUtilidad and du.empleado IS NOT NULL");
/* 118:143 */     query.setParameter("idUtilidad", Integer.valueOf(idUtilidad));
/* 119:144 */     query.executeUpdate();
/* 120:    */   }
/* 121:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.UtilidadDao
 * JD-Core Version:    0.7.0.1
 */