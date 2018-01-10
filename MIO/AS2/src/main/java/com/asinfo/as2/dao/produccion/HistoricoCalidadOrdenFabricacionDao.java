/*   1:    */ package com.asinfo.as2.dao.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.calidad.VariableCalidad;
/*   5:    */ import com.asinfo.as2.entities.produccion.HistoricoCalidadOrdenFabricacion;
/*   6:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   7:    */ import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
/*   8:    */ import java.math.BigDecimal;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ import javax.persistence.EntityManager;
/*  13:    */ import javax.persistence.Query;
/*  14:    */ import javax.persistence.TypedQuery;
/*  15:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  16:    */ import javax.persistence.criteria.CriteriaQuery;
/*  17:    */ import javax.persistence.criteria.Expression;
/*  18:    */ import javax.persistence.criteria.Fetch;
/*  19:    */ import javax.persistence.criteria.JoinType;
/*  20:    */ import javax.persistence.criteria.Predicate;
/*  21:    */ import javax.persistence.criteria.Root;
/*  22:    */ 
/*  23:    */ @Stateless
/*  24:    */ public class HistoricoCalidadOrdenFabricacionDao
/*  25:    */   extends AbstractDaoAS2<HistoricoCalidadOrdenFabricacion>
/*  26:    */ {
/*  27:    */   public HistoricoCalidadOrdenFabricacionDao()
/*  28:    */   {
/*  29: 49 */     super(HistoricoCalidadOrdenFabricacion.class);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public List<HistoricoCalidadOrdenFabricacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  33:    */   {
/*  34: 61 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  35: 62 */     CriteriaQuery<HistoricoCalidadOrdenFabricacion> criteriaQuery = criteriaBuilder.createQuery(HistoricoCalidadOrdenFabricacion.class);
/*  36:    */     
/*  37: 64 */     Root<HistoricoCalidadOrdenFabricacion> from = criteriaQuery.from(HistoricoCalidadOrdenFabricacion.class);
/*  38: 65 */     from.fetch("transformacion", JoinType.LEFT);
/*  39: 66 */     from.fetch("lote", JoinType.LEFT);
/*  40:    */     
/*  41: 68 */     Fetch<Object, Object> ordenFabricacion = from.fetch("ordenFabricacion", JoinType.LEFT);
/*  42: 69 */     ordenFabricacion.fetch("producto", JoinType.LEFT);
/*  43: 70 */     ordenFabricacion.fetch("sucursal", JoinType.LEFT);
/*  44:    */     
/*  45: 72 */     Fetch<Object, Object> fetchDetalleIngresoFabricacion = from.fetch("detalleIngresoFabricacion", JoinType.LEFT);
/*  46: 73 */     fetchDetalleIngresoFabricacion.fetch("unidadConversion", JoinType.LEFT);
/*  47: 74 */     fetchDetalleIngresoFabricacion.fetch("bodegaOrigen", JoinType.LEFT);
/*  48:    */     
/*  49: 76 */     Fetch<Object, Object> fetchMovimientoIngresoFabricacion = fetchDetalleIngresoFabricacion.fetch("movimientoInventario", JoinType.LEFT);
/*  50: 77 */     fetchMovimientoIngresoFabricacion.fetch("documento", JoinType.LEFT);
/*  51:    */     
/*  52: 79 */     Fetch<Object, Object> fetchOrdenFabricacion = fetchMovimientoIngresoFabricacion.fetch("ordenFabricacion", JoinType.LEFT);
/*  53: 80 */     fetchOrdenFabricacion.fetch("producto", JoinType.LEFT);
/*  54: 81 */     fetchOrdenFabricacion.fetch("bodega", JoinType.LEFT);
/*  55: 82 */     fetchOrdenFabricacion.fetch("sucursal", JoinType.LEFT);
/*  56:    */     
/*  57: 84 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  58:    */     
/*  59: 86 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  60: 87 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  61:    */     
/*  62: 89 */     CriteriaQuery<HistoricoCalidadOrdenFabricacion> select = criteriaQuery.select(from);
/*  63: 90 */     TypedQuery<HistoricoCalidadOrdenFabricacion> typedQuery = this.em.createQuery(select);
/*  64: 91 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  65:    */     
/*  66: 93 */     return typedQuery.getResultList();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public BigDecimal obtenerSumaCantidadPorOrdenFabricacionEstado(OrdenFabricacion ordenFabricacion, EstadoControlCalidad estadoControlCalidad)
/*  70:    */   {
/*  71: 98 */     StringBuilder sql = new StringBuilder();
/*  72: 99 */     sql.append(" SELECT SUM(h.cantidad) FROM HistoricoCalidadOrdenFabricacion h ");
/*  73:100 */     sql.append(" INNER JOIN h.ordenFabricacion ofa ");
/*  74:101 */     sql.append(" LEFT  JOIN h.detalleIngresoFabricacion df ");
/*  75:102 */     sql.append(" WHERE ofa = :ordenFabricacion ");
/*  76:103 */     sql.append(" AND   h.estado = :estadoControlCalidad ");
/*  77:    */     
/*  78:105 */     Query query = this.em.createQuery(sql.toString());
/*  79:    */     
/*  80:107 */     query.setParameter("ordenFabricacion", ordenFabricacion);
/*  81:108 */     query.setParameter("estadoControlCalidad", estadoControlCalidad);
/*  82:    */     
/*  83:110 */     BigDecimal cantidad = (BigDecimal)query.getSingleResult();
/*  84:    */     
/*  85:112 */     return cantidad == null ? BigDecimal.ZERO : cantidad;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public List<VariableCalidad> getListaVariableCalidadPromedioSubordenes(List<OrdenFabricacion> listaOrdenFabricacion)
/*  89:    */   {
/*  90:119 */     StringBuilder sql = new StringBuilder();
/*  91:120 */     sql.append(" SELECT new VariableCalidad(vc.idVariableCalidad, AVG(vcof.valorNir)) ");
/*  92:121 */     sql.append(" FROM VariableCalidadOrdenFabricacion vcof ");
/*  93:122 */     sql.append(" INNER JOIN vcof.variableCalidadProducto vcp ");
/*  94:123 */     sql.append(" INNER JOIN vcp.variableCalidad vc ");
/*  95:124 */     sql.append(" INNER JOIN vcof.historicoCalidadOrdenFabricacion hcof ");
/*  96:125 */     sql.append(" INNER JOIN hcof.ordenFabricacion ofa ");
/*  97:126 */     sql.append(" WHERE ofa in (:listaOrdenFabricacion)  ");
/*  98:127 */     sql.append(" GROUP BY vc.idVariableCalidad ");
/*  99:    */     
/* 100:129 */     Query query = this.em.createQuery(sql.toString());
/* 101:130 */     query.setParameter("listaOrdenFabricacion", listaOrdenFabricacion);
/* 102:131 */     return query.getResultList();
/* 103:    */   }
/* 104:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.produccion.HistoricoCalidadOrdenFabricacionDao
 * JD-Core Version:    0.7.0.1
 */