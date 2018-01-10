/*   1:    */ package com.asinfo.as2.dao.presupuesto;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.Documento;
/*   5:    */ import com.asinfo.as2.entities.presupuesto.DetalleMovimientoPartidaPresupuestaria;
/*   6:    */ import com.asinfo.as2.entities.presupuesto.MovimientoPartidaPresupuestaria;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ import javax.persistence.EntityManager;
/*  13:    */ import javax.persistence.Query;
/*  14:    */ import javax.persistence.TypedQuery;
/*  15:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  16:    */ import javax.persistence.criteria.CriteriaQuery;
/*  17:    */ import javax.persistence.criteria.JoinType;
/*  18:    */ import javax.persistence.criteria.Order;
/*  19:    */ import javax.persistence.criteria.Root;
/*  20:    */ 
/*  21:    */ @Stateless
/*  22:    */ public class MovimientoPartidaPresupuestariaDao
/*  23:    */   extends AbstractDaoAS2<MovimientoPartidaPresupuestaria>
/*  24:    */ {
/*  25:    */   public MovimientoPartidaPresupuestariaDao()
/*  26:    */   {
/*  27: 35 */     super(MovimientoPartidaPresupuestaria.class);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public List<MovimientoPartidaPresupuestaria> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filtros)
/*  31:    */   {
/*  32: 41 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  33: 42 */     CriteriaQuery<MovimientoPartidaPresupuestaria> cq = cb.createQuery(MovimientoPartidaPresupuestaria.class);
/*  34: 43 */     Root<MovimientoPartidaPresupuestaria> from = cq.from(MovimientoPartidaPresupuestaria.class);
/*  35: 44 */     from.fetch("documento", JoinType.LEFT);
/*  36: 45 */     from.fetch("ejercicio", JoinType.LEFT);
/*  37:    */     
/*  38:    */ 
/*  39: 48 */     agregarOrdenamiento(sortField, sortOrder, cb, cq, from);
/*  40:    */     
/*  41:    */ 
/*  42: 51 */     agregarFiltros(filtros, cb, cq, from);
/*  43:    */     
/*  44:    */ 
/*  45: 54 */     TypedQuery<MovimientoPartidaPresupuestaria> typedQuery = this.em.createQuery(cq.select(from));
/*  46: 55 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  47:    */     
/*  48: 57 */     return typedQuery.getResultList();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public MovimientoPartidaPresupuestaria cargarDetalle(int idMovimientoPartidaPresupuestaria)
/*  52:    */   {
/*  53: 64 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  54: 65 */     CriteriaQuery<MovimientoPartidaPresupuestaria> cq = cb.createQuery(MovimientoPartidaPresupuestaria.class);
/*  55:    */     
/*  56: 67 */     Root<MovimientoPartidaPresupuestaria> from = cq.from(MovimientoPartidaPresupuestaria.class);
/*  57: 68 */     from.fetch("documento", JoinType.LEFT);
/*  58:    */     
/*  59: 70 */     from.fetch("ejercicio", JoinType.LEFT);
/*  60:    */     
/*  61: 72 */     cq.where(cb.equal(from.get("idMovimientoPartidaPresupuestaria"), Integer.valueOf(idMovimientoPartidaPresupuestaria)));
/*  62: 73 */     MovimientoPartidaPresupuestaria movimientoPartidaPresupuestaria = (MovimientoPartidaPresupuestaria)this.em.createQuery(cq.select(from)).getSingleResult();
/*  63: 74 */     this.em.detach(movimientoPartidaPresupuestaria);
/*  64:    */     
/*  65: 76 */     CriteriaQuery<DetalleMovimientoPartidaPresupuestaria> cqDetalle = cb.createQuery(DetalleMovimientoPartidaPresupuestaria.class);
/*  66: 77 */     Root<DetalleMovimientoPartidaPresupuestaria> detalle = cqDetalle.from(DetalleMovimientoPartidaPresupuestaria.class);
/*  67: 78 */     detalle.fetch("cuentaContableOrigen", JoinType.LEFT);
/*  68: 79 */     detalle.fetch("cuentaContableDestino", JoinType.LEFT);
/*  69: 80 */     detalle.fetch("dimensionContableOrigen", JoinType.LEFT);
/*  70: 81 */     detalle.fetch("dimensionContableDestino", JoinType.LEFT);
/*  71:    */     
/*  72: 83 */     cqDetalle.where(cb.equal(detalle.get("movimientoPartidaPresupuestaria"), movimientoPartidaPresupuestaria));
/*  73: 84 */     cqDetalle.orderBy(new Order[] { cb.asc(detalle.get("idDetalleMovimientoPartidaPresupuestaria")) });
/*  74:    */     
/*  75: 86 */     List<DetalleMovimientoPartidaPresupuestaria> listaDetalle = this.em.createQuery(cqDetalle).getResultList();
/*  76: 87 */     movimientoPartidaPresupuestaria.setListaDetalleMovimientoPartidaPresupuestaria(listaDetalle);
/*  77:    */     
/*  78: 89 */     return movimientoPartidaPresupuestaria;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public List<MovimientoPartidaPresupuestaria> cargarMovimientosPorAprobar(int idOrganizacion, Documento documento, Date fechaDesde, Date fechaHasta)
/*  82:    */   {
/*  83: 95 */     StringBuilder sql = new StringBuilder();
/*  84: 96 */     sql.append("SELECT mpp ");
/*  85: 97 */     sql.append(" FROM MovimientoPartidaPresupuestaria mpp");
/*  86: 98 */     sql.append(" INNER JOIN mpp.documento d");
/*  87: 99 */     sql.append(" WHERE mpp.idOrganizacion=:idOrganizacion");
/*  88:100 */     if (documento != null) {
/*  89:101 */       sql.append(" AND d=:documento");
/*  90:    */     }
/*  91:102 */     sql.append(" AND mpp.fecha BETWEEN :fechaDesde AND :fechaHasta");
/*  92:    */     
/*  93:104 */     Query query = this.em.createQuery(sql.toString());
/*  94:105 */     query.setParameter("fechaDesde", fechaDesde);
/*  95:106 */     query.setParameter("fechaHasta", fechaHasta);
/*  96:107 */     if (documento != null) {
/*  97:108 */       query.setParameter("documento", documento);
/*  98:    */     }
/*  99:109 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 100:110 */     return query.getResultList();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void actualizarEstado(int idMovimientoPartidaPresupuestaria, Estado estado)
/* 104:    */   {
/* 105:115 */     StringBuilder sql = new StringBuilder();
/* 106:116 */     sql.append(" UPDATE MovimientoPartidaPresupuestaria mpp SET estado=:estado ");
/* 107:117 */     sql.append(" WHERE mpp.idMovimientoPartidaPresupuestaria =:idMovimientoPartidaPresupuestaria");
/* 108:118 */     Query query = this.em.createQuery(sql.toString());
/* 109:119 */     query.setParameter("idMovimientoPartidaPresupuestaria", Integer.valueOf(idMovimientoPartidaPresupuestaria));
/* 110:120 */     query.setParameter("estado", estado);
/* 111:121 */     query.executeUpdate();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public List<Object[]> getReporteMovimientoPartidaPresupuestaria(int idMovimientoPartidaPresupuestaria, int idOrganizacion)
/* 115:    */   {
/* 116:127 */     StringBuilder sql = new StringBuilder();
/* 117:128 */     sql.append("SELECT mpp.numero, mpp.fecha, d.nombre, mpp.mesOrigen, mpp.mesDestino, e.nombre, mpp.descripcion,");
/* 118:129 */     sql.append(" cco.codigo, cco.nombre, dco.codigo, dco.nombre, dmpp.saldoOrigen,");
/* 119:130 */     sql.append(" ccd.codigo, ccd.nombre, dcd.codigo, dcd.nombre, dmpp.saldoDestino, dmpp.valor");
/* 120:131 */     sql.append(" FROM DetalleMovimientoPartidaPresupuestaria dmpp");
/* 121:132 */     sql.append(" INNER JOIN dmpp.cuentaContableOrigen cco");
/* 122:133 */     sql.append(" INNER JOIN dmpp.dimensionContableOrigen dco");
/* 123:134 */     sql.append(" LEFT JOIN dmpp.cuentaContableDestino ccd");
/* 124:135 */     sql.append(" LEFT JOIN dmpp.dimensionContableDestino dcd");
/* 125:136 */     sql.append(" INNER JOIN dmpp.movimientoPartidaPresupuestaria mpp");
/* 126:137 */     sql.append(" INNER JOIN mpp.documento d");
/* 127:138 */     sql.append(" INNER JOIN mpp.ejercicio e");
/* 128:139 */     sql.append(" WHERE mpp.idOrganizacion=:idOrganizacion");
/* 129:140 */     sql.append(" AND mpp.idMovimientoPartidaPresupuestaria=:idMovimientoPartidaPresupuestaria");
/* 130:    */     
/* 131:142 */     Query query = this.em.createQuery(sql.toString());
/* 132:143 */     query.setParameter("idMovimientoPartidaPresupuestaria", Integer.valueOf(idMovimientoPartidaPresupuestaria));
/* 133:144 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 134:145 */     return query.getResultList();
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.presupuesto.MovimientoPartidaPresupuestariaDao
 * JD-Core Version:    0.7.0.1
 */