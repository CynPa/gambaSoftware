/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetalleCierreCaja;
/*   4:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*   5:    */ import java.math.BigDecimal;
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
/*  18:    */ import javax.persistence.criteria.Predicate;
/*  19:    */ import javax.persistence.criteria.Root;
/*  20:    */ 
/*  21:    */ @Stateless
/*  22:    */ public class DetalleFormaCobroDao
/*  23:    */   extends AbstractDaoAS2<DetalleFormaCobro>
/*  24:    */ {
/*  25:    */   public DetalleFormaCobroDao()
/*  26:    */   {
/*  27: 45 */     super(DetalleFormaCobro.class);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void actualizarValorProtestado(int idDetalleFormaCobro, BigDecimal valorProtestado)
/*  31:    */   {
/*  32: 49 */     StringBuffer sql = new StringBuffer();
/*  33: 50 */     sql.append("UPDATE DetalleFormaCobro d SET d.valorProtestado = :valorProtestado ");
/*  34: 51 */     sql.append(" WHERE d.idDetalleFormaCobro = :idDetalleFormaCobro ");
/*  35:    */     
/*  36: 53 */     Query query = this.em.createQuery(sql.toString());
/*  37: 54 */     query.setParameter("valorProtestado", valorProtestado);
/*  38: 55 */     query.setParameter("idDetalleFormaCobro", Integer.valueOf(idDetalleFormaCobro));
/*  39:    */     
/*  40: 57 */     query.executeUpdate();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List<DetalleFormaCobro> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  44:    */   {
/*  45: 69 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  46: 70 */     CriteriaQuery<DetalleFormaCobro> criteriaQuery = criteriaBuilder.createQuery(DetalleFormaCobro.class);
/*  47: 71 */     Root<DetalleFormaCobro> from = criteriaQuery.from(DetalleFormaCobro.class);
/*  48:    */     
/*  49: 73 */     from.fetch("formaPago", JoinType.LEFT);
/*  50: 74 */     from.fetch("banco", JoinType.LEFT);
/*  51: 75 */     Fetch<Object, Object> cobro = from.fetch("cobro", JoinType.LEFT);
/*  52: 76 */     Fetch<Object, Object> asiento = cobro.fetch("asiento", JoinType.LEFT);
/*  53: 77 */     cobro.fetch("empresa", JoinType.LEFT);
/*  54: 78 */     asiento.fetch("tipoAsiento", JoinType.LEFT);
/*  55:    */     
/*  56: 80 */     Fetch<Object, Object> detalleCierreCaja = from.fetch("detalleCierreCaja", JoinType.LEFT);
/*  57: 81 */     detalleCierreCaja.fetch("cierreCaja", JoinType.LEFT);
/*  58:    */     
/*  59: 83 */     detalleCierreCaja.fetch("interfazContableProceso", JoinType.LEFT).fetch("asiento", JoinType.LEFT).fetch("tipoAsiento", JoinType.LEFT);
/*  60:    */     
/*  61: 85 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  62:    */     
/*  63: 87 */     boolean detalleCierreCajaIsNull = false;
/*  64: 88 */     if (filters.containsKey("detalleCierreCaja"))
/*  65:    */     {
/*  66: 89 */       detalleCierreCajaIsNull = true;
/*  67: 90 */       filters.remove("detalleCierreCaja");
/*  68:    */     }
/*  69: 92 */     boolean interfazContableProcesoIsNull = false;
/*  70: 93 */     if (filters.containsKey("detalleCierreCaja.interfazContableProceso"))
/*  71:    */     {
/*  72: 94 */       interfazContableProcesoIsNull = true;
/*  73: 95 */       filters.remove("detalleCierreCaja.interfazContableProceso");
/*  74:    */     }
/*  75: 98 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  76:100 */     if (detalleCierreCajaIsNull)
/*  77:    */     {
/*  78:101 */       Expression<DetalleCierreCaja> dc = from.join("detalleCierreCaja", JoinType.LEFT).get("idDetalleCierreCaja");
/*  79:102 */       expresiones.add(criteriaBuilder.isNull(dc));
/*  80:    */     }
/*  81:104 */     if (interfazContableProcesoIsNull)
/*  82:    */     {
/*  83:106 */       Expression<DetalleCierreCaja> ip = from.join("detalleCierreCaja", JoinType.INNER).join("interfazContableProceso", JoinType.LEFT).get("idInterfazContableProceso");
/*  84:107 */       expresiones.add(criteriaBuilder.isNull(ip));
/*  85:    */     }
/*  86:110 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  87:    */     
/*  88:112 */     CriteriaQuery<DetalleFormaCobro> select = criteriaQuery.select(from);
/*  89:    */     
/*  90:114 */     TypedQuery<DetalleFormaCobro> typedQuery = this.em.createQuery(select);
/*  91:115 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  92:    */     
/*  93:117 */     return typedQuery.getResultList();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int obtenerSecuencialVoucher(int idOrganizacion)
/*  97:    */   {
/*  98:121 */     StringBuilder sql = new StringBuilder();
/*  99:    */     
/* 100:123 */     sql.append(" select max(dfc.secuencial) ");
/* 101:124 */     sql.append(" from  DetalleFormaCobro dfc ");
/* 102:125 */     sql.append(" WHERE dfc.idOrganizacion = :idOrganizacion ");
/* 103:    */     
/* 104:127 */     Query query = this.em.createQuery(sql.toString());
/* 105:128 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 106:    */     
/* 107:130 */     Integer a = (Integer)query.getSingleResult();
/* 108:131 */     if (a == null) {
/* 109:132 */       return 0;
/* 110:    */     }
/* 111:134 */     return a.intValue();
/* 112:    */   }
/* 113:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetalleFormaCobroDao
 * JD-Core Version:    0.7.0.1
 */