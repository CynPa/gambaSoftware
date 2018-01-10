/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.ConciliacionBancaria;
/*   4:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   5:    */ import com.asinfo.as2.entities.FormaPago;
/*   6:    */ import com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion;
/*   7:    */ import com.asinfo.as2.entities.MovimientoBancario;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.financiero.contabilidad.reportes.servicio.impl.ServicioEstadoFinancieroImpl;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import java.math.BigDecimal;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.ejb.Stateless;
/*  18:    */ import javax.persistence.EntityManager;
/*  19:    */ import javax.persistence.Query;
/*  20:    */ import javax.persistence.TypedQuery;
/*  21:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  22:    */ import javax.persistence.criteria.CriteriaQuery;
/*  23:    */ import javax.persistence.criteria.Expression;
/*  24:    */ import javax.persistence.criteria.Fetch;
/*  25:    */ import javax.persistence.criteria.Join;
/*  26:    */ import javax.persistence.criteria.JoinType;
/*  27:    */ import javax.persistence.criteria.Path;
/*  28:    */ import javax.persistence.criteria.Predicate;
/*  29:    */ import javax.persistence.criteria.Root;
/*  30:    */ 
/*  31:    */ @Stateless
/*  32:    */ public class ConciliacionBancariaDao
/*  33:    */   extends AbstractDaoAS2<ConciliacionBancaria>
/*  34:    */ {
/*  35:    */   public ConciliacionBancariaDao()
/*  36:    */   {
/*  37: 50 */     super(ConciliacionBancaria.class);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public ConciliacionBancaria cargarDetalle(int idConciliacionBancaria)
/*  41:    */   {
/*  42: 56 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  43:    */     
/*  44:    */ 
/*  45: 59 */     CriteriaQuery<ConciliacionBancaria> cqCabecera = criteriaBuilder.createQuery(ConciliacionBancaria.class);
/*  46: 60 */     Root<ConciliacionBancaria> fromCabecera = cqCabecera.from(ConciliacionBancaria.class);
/*  47: 61 */     Fetch<Object, Object> asiento = fromCabecera.fetch("asiento", JoinType.LEFT);
/*  48: 62 */     asiento.fetch("documentoOrigen", JoinType.LEFT);
/*  49:    */     
/*  50: 64 */     Fetch<Object, Object> cuentaBancariaOrganizacion = fromCabecera.fetch("cuentaBancariaOrganizacion", JoinType.LEFT);
/*  51: 65 */     cuentaBancariaOrganizacion.fetch("cuentaContableBanco");
/*  52: 66 */     Fetch<Object, Object> cuentaBancaria = cuentaBancariaOrganizacion.fetch("cuentaBancaria", JoinType.LEFT);
/*  53: 67 */     cuentaBancaria.fetch("banco", JoinType.LEFT);
/*  54:    */     
/*  55: 69 */     Path<Integer> pathId = fromCabecera.get("idConciliacionBancaria");
/*  56: 70 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idConciliacionBancaria)));
/*  57: 71 */     CriteriaQuery<ConciliacionBancaria> selectConciliacionBancaria = cqCabecera.select(fromCabecera);
/*  58: 72 */     ConciliacionBancaria conciliacionBancaria = (ConciliacionBancaria)this.em.createQuery(selectConciliacionBancaria).getSingleResult();
/*  59:    */     
/*  60:    */ 
/*  61: 75 */     CriteriaQuery<MovimientoBancario> cqDetalle = criteriaBuilder.createQuery(MovimientoBancario.class);
/*  62: 76 */     Root<MovimientoBancario> fromDetalle = cqDetalle.from(MovimientoBancario.class);
/*  63:    */     
/*  64: 78 */     fromDetalle.fetch("documento", JoinType.LEFT);
/*  65: 79 */     fromDetalle.fetch("conceptoContable", JoinType.LEFT);
/*  66: 80 */     Fetch<Object, Object> detalleAsientoC = fromDetalle.fetch("detalleAsiento", JoinType.LEFT);
/*  67: 81 */     detalleAsientoC.fetch("asiento", JoinType.INNER);
/*  68: 82 */     fromDetalle.fetch("cuentaBancariaOrganizacion", JoinType.LEFT);
/*  69:    */     
/*  70: 84 */     Path<Integer> pathIdDetalle = fromDetalle.join("conciliacionBancaria").get("idConciliacionBancaria");
/*  71:    */     
/*  72: 86 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idConciliacionBancaria)));
/*  73: 87 */     CriteriaQuery<MovimientoBancario> selectMovimientoBancario = cqDetalle.select(fromDetalle);
/*  74:    */     
/*  75: 89 */     List<MovimientoBancario> listaMovimientoBancario = this.em.createQuery(selectMovimientoBancario).getResultList();
/*  76: 90 */     conciliacionBancaria.setListaMovimientoBancario(listaMovimientoBancario);
/*  77: 92 */     if (conciliacionBancaria.getCuentaBancariaOrganizacion() != null)
/*  78:    */     {
/*  79: 94 */       conciliacionBancaria.getCuentaBancariaOrganizacion().getListaFormaPago().size();
/*  80: 96 */       for (FormaPagoCuentaBancariaOrganizacion formaPagoCuenta : conciliacionBancaria.getCuentaBancariaOrganizacion().getListaFormaPago()) {
/*  81: 97 */         formaPagoCuenta.getFormaPago().getId();
/*  82:    */       }
/*  83:    */     }
/*  84:101 */     return conciliacionBancaria;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public List<MovimientoBancario> cargarDatosConciliar(ConciliacionBancaria conciliacionBancaria)
/*  88:    */   {
/*  89:114 */     StringBuilder sql = new StringBuilder();
/*  90:115 */     sql.append(" SELECT d FROM MovimientoBancario d ");
/*  91:116 */     sql.append(" LEFT JOIN FETCH d.detalleAsiento da ");
/*  92:117 */     sql.append(" LEFT JOIN FETCH da.asiento a ");
/*  93:118 */     sql.append(" LEFT JOIN FETCH a.tipoAsiento t ");
/*  94:119 */     sql.append(" LEFT JOIN FETCH d.documento do ");
/*  95:120 */     sql.append(" LEFT JOIN FETCH d.cuentaBancariaOrganizacion cb ");
/*  96:121 */     sql.append(" LEFT JOIN FETCH d.conciliacionBancaria c ");
/*  97:122 */     sql.append(" LEFT JOIN FETCH d.formaPago fp ");
/*  98:123 */     sql.append(" WHERE (c.idConciliacionBancaria =:idConciliacionBancaria OR c.idConciliacionBancaria = null) ");
/*  99:124 */     sql.append(" AND cb.idCuentaBancariaOrganizacion =:idCuentaBancariaOrganizacion ");
/* 100:125 */     sql.append(" AND (CASE WHEN a IS NULL THEN d.fecha ELSE a.fecha END) <=:fecha  ");
/* 101:126 */     sql.append(" AND (CASE WHEN a IS NOT NULL THEN a.estado ELSE 1 END) != :estadoAnulado ");
/* 102:127 */     sql.append(" ORDER BY a.fecha, d.documentoReferencia ");
/* 103:    */     
/* 104:129 */     Query query = this.em.createQuery(sql.toString());
/* 105:130 */     query.setParameter("idCuentaBancariaOrganizacion", Integer.valueOf(conciliacionBancaria.getCuentaBancariaOrganizacion().getId()));
/* 106:131 */     query.setParameter("idConciliacionBancaria", Integer.valueOf(conciliacionBancaria.getId()));
/* 107:132 */     query.setParameter("fecha", conciliacionBancaria.getFecha());
/* 108:133 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 109:    */     
/* 110:135 */     return query.getResultList();
/* 111:    */   }
/* 112:    */   
/* 113:    */   public List<ConciliacionBancaria> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 114:    */   {
/* 115:150 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 116:151 */     CriteriaQuery<ConciliacionBancaria> criteriaQuery = criteriaBuilder.createQuery(ConciliacionBancaria.class);
/* 117:152 */     Root<ConciliacionBancaria> from = criteriaQuery.from(ConciliacionBancaria.class);
/* 118:    */     
/* 119:154 */     Fetch<Object, Object> cuentaBancariaOrganizacion = from.fetch("cuentaBancariaOrganizacion", JoinType.LEFT);
/* 120:155 */     cuentaBancariaOrganizacion.fetch("cuentaContableBanco");
/* 121:156 */     Fetch<Object, Object> cuentaBancaria = cuentaBancariaOrganizacion.fetch("cuentaBancaria", JoinType.LEFT);
/* 122:157 */     cuentaBancaria.fetch("banco", JoinType.LEFT);
/* 123:158 */     from.fetch("sucursal", JoinType.LEFT);
/* 124:159 */     Fetch<Object, Object> asiento = from.fetch("asiento", JoinType.LEFT);
/* 125:160 */     asiento.fetch("tipoAsiento", JoinType.LEFT);
/* 126:    */     
/* 127:162 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 128:163 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 129:    */     
/* 130:165 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 131:    */     
/* 132:167 */     CriteriaQuery<ConciliacionBancaria> select = criteriaQuery.select(from);
/* 133:168 */     TypedQuery<ConciliacionBancaria> typedQuery = this.em.createQuery(select);
/* 134:    */     
/* 135:170 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 136:    */     
/* 137:172 */     return typedQuery.getResultList();
/* 138:    */   }
/* 139:    */   
/* 140:    */   public List getReporteConciliacionBancaria(Date fechaConciliacion)
/* 141:    */     throws ExcepcionAS2
/* 142:    */   {
/* 143:186 */     int idOrganizacion = AppUtil.getSucursal().getIdOrganizacion();
/* 144:    */     
/* 145:188 */     String sql = "SELECT a.fecha, a.numero, a.concepto, da.debe, da.haber, t.nombre FROM MovimientoBancario d  JOIN d.detalleAsiento da  JOIN da.asiento a  LEFT JOIN a.tipoAsiento t LEFT JOIN d.conciliacionBancaria c WHERE a.idOrganizacion = :idOrganizacion AND a.fecha <= :fechaConciliacion AND a.estado IN (:estado) AND (c.idConciliacionBancaria = null OR c.fecha > :fechaConciliacion)";
/* 146:    */     
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:193 */     Query query = this.em.createQuery(sql);
/* 151:194 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 152:195 */     query.setParameter("fechaConciliacion", fechaConciliacion);
/* 153:196 */     query.setParameter("estado", ServicioEstadoFinancieroImpl.getEstadosComprobantes(idOrganizacion));
/* 154:197 */     return query.getResultList();
/* 155:    */   }
/* 156:    */   
/* 157:    */   public List getReporteConciliacionBancaria(Date fechaConciliacion, int idCuentaBancariaOrganizacion)
/* 158:    */     throws ExcepcionAS2
/* 159:    */   {
/* 160:209 */     StringBuilder sql = new StringBuilder();
/* 161:210 */     sql.append(" SELECT ");
/* 162:211 */     sql.append(" (CASE WHEN a IS NULL THEN mb.fecha ELSE a.fecha END), ");
/* 163:212 */     sql.append(" (CASE WHEN a IS NULL THEN mb.documentoReferencia ELSE a.numero END) , ");
/* 164:213 */     sql.append(" (CASE WHEN a IS NULL THEN mb.descripcion ELSE a.concepto END), ");
/* 165:214 */     sql.append(" (CASE WHEN da IS NULL THEN (CASE WHEN mb.valor >= 0 THEN mb.valor ELSE 0 END) ELSE da.debe END), ");
/* 166:215 */     sql.append(" (CASE WHEN da IS NULL THEN (CASE WHEN mb.valor <  0 THEN ABS(mb.valor) ELSE 0 END) ELSE da.haber END), t.nombre, mb.documentoReferencia,cb.numero, b.nombre, fp.nombre, mb.beneficiario");
/* 167:216 */     sql.append(" FROM MovimientoBancario mb ");
/* 168:217 */     sql.append(" LEFT JOIN mb.detalleAsiento da ");
/* 169:218 */     sql.append(" LEFT JOIN da.asiento a ");
/* 170:219 */     sql.append(" LEFT JOIN a.tipoAsiento t ");
/* 171:220 */     sql.append(" LEFT JOIN mb.cuentaBancariaOrganizacion cbo ");
/* 172:221 */     sql.append(" LEFT JOIN cbo.cuentaBancaria cb ");
/* 173:222 */     sql.append(" LEFT JOIN cb.banco b ");
/* 174:223 */     sql.append(" LEFT JOIN mb.conciliacionBancaria c ");
/* 175:224 */     sql.append(" LEFT JOIN mb.formaPago fp ");
/* 176:225 */     sql.append(" WHERE (CASE WHEN a IS NOT NULL THEN a.estado ELSE 1 END) != :estadoAnulado ");
/* 177:226 */     sql.append(" AND (CASE WHEN a IS NULL THEN mb.fecha ELSE a.fecha END) <= :fechaConciliacion ");
/* 178:227 */     sql.append(" AND (c IS NULL OR c.fecha > :fechaConciliacion) ");
/* 179:228 */     sql.append(" and cbo.idCuentaBancariaOrganizacion=:idCuentaBancariaOrganizacion ");
/* 180:    */     
/* 181:230 */     Query query = this.em.createQuery(sql.toString());
/* 182:231 */     query.setParameter("fechaConciliacion", fechaConciliacion);
/* 183:232 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 184:233 */     query.setParameter("idCuentaBancariaOrganizacion", Integer.valueOf(idCuentaBancariaOrganizacion));
/* 185:    */     
/* 186:235 */     return query.getResultList();
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void cerrarConciliacionesAnteriores(ConciliacionBancaria conciliacionBancaria)
/* 190:    */   {
/* 191:246 */     String sql = "UPDATE ConciliacionBancaria c SET estado =:estadoAprobado WHERE c.idConciliacionBancaria!=:idConciliacionBancaria  AND c.cuentaBancariaOrganizacion.idCuentaBancariaOrganizacion=:idCuentaBancariaOrganizacion AND c.estado!=:estadoAprobado AND c.estado!=:estadoAnulado AND c.fecha <:fecha";
/* 192:    */     
/* 193:    */ 
/* 194:249 */     Query query = this.em.createQuery(sql);
/* 195:250 */     query.setParameter("idConciliacionBancaria", Integer.valueOf(conciliacionBancaria.getId()));
/* 196:251 */     query.setParameter("idCuentaBancariaOrganizacion", Integer.valueOf(conciliacionBancaria.getCuentaBancariaOrganizacion().getId()));
/* 197:252 */     query.setParameter("fecha", conciliacionBancaria.getFecha());
/* 198:253 */     query.setParameter("estadoAprobado", Estado.APROBADO);
/* 199:254 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 200:255 */     query.executeUpdate();
/* 201:    */   }
/* 202:    */   
/* 203:    */   public BigDecimal totalCreditoConciliado(ConciliacionBancaria conciliacionBancaria)
/* 204:    */   {
/* 205:266 */     StringBuilder sql = new StringBuilder();
/* 206:267 */     sql.append(" SELECT SUM(mb.valor) FROM MovimientoBancario mb ");
/* 207:268 */     sql.append(" INNER JOIN mb.conciliacionBancaria cb ");
/* 208:269 */     sql.append(" WHERE cb.idConciliacionBancaria =:idConciliacionBancaria ");
/* 209:270 */     sql.append(" AND   mb.valor > 0 ");
/* 210:    */     
/* 211:272 */     Query query = this.em.createQuery(sql.toString());
/* 212:273 */     query.setParameter("idConciliacionBancaria", Integer.valueOf(conciliacionBancaria.getId()));
/* 213:274 */     return (BigDecimal)query.getSingleResult();
/* 214:    */   }
/* 215:    */   
/* 216:    */   public BigDecimal totalDebitoConciliado(ConciliacionBancaria conciliacionBancaria)
/* 217:    */   {
/* 218:286 */     StringBuilder sql = new StringBuilder();
/* 219:287 */     sql.append(" SELECT -1*SUM(mb.valor) FROM MovimientoBancario mb ");
/* 220:288 */     sql.append(" INNER JOIN mb.conciliacionBancaria cb ");
/* 221:289 */     sql.append(" WHERE cb.idConciliacionBancaria =:idConciliacionBancaria ");
/* 222:290 */     sql.append(" AND   mb.valor < 0 ");
/* 223:    */     
/* 224:292 */     Query query = this.em.createQuery(sql.toString());
/* 225:293 */     query.setParameter("idConciliacionBancaria", Integer.valueOf(conciliacionBancaria.getId()));
/* 226:294 */     return (BigDecimal)query.getSingleResult();
/* 227:    */   }
/* 228:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ConciliacionBancariaDao
 * JD-Core Version:    0.7.0.1
 */