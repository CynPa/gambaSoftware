/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.AnticipoCliente;
/*   4:    */ import com.asinfo.as2.entities.CierreCaja;
/*   5:    */ import com.asinfo.as2.entities.DetalleCierreCaja;
/*   6:    */ import com.asinfo.as2.entities.DetalleDenominacionFormaCobro;
/*   7:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*   8:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ import javax.persistence.EntityManager;
/*  13:    */ import javax.persistence.NoResultException;
/*  14:    */ import javax.persistence.Query;
/*  15:    */ import javax.persistence.TypedQuery;
/*  16:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  17:    */ import javax.persistence.criteria.CriteriaQuery;
/*  18:    */ import javax.persistence.criteria.Expression;
/*  19:    */ import javax.persistence.criteria.Fetch;
/*  20:    */ import javax.persistence.criteria.Join;
/*  21:    */ import javax.persistence.criteria.JoinType;
/*  22:    */ import javax.persistence.criteria.Path;
/*  23:    */ import javax.persistence.criteria.Predicate;
/*  24:    */ import javax.persistence.criteria.Root;
/*  25:    */ 
/*  26:    */ @Stateless
/*  27:    */ public class CierreCajaDao
/*  28:    */   extends AbstractDaoAS2<CierreCaja>
/*  29:    */ {
/*  30:    */   public CierreCajaDao()
/*  31:    */   {
/*  32: 49 */     super(CierreCaja.class);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List<CierreCaja> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  36:    */   {
/*  37: 54 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  38: 55 */     CriteriaQuery<CierreCaja> criteriaQuery = criteriaBuilder.createQuery(CierreCaja.class);
/*  39: 56 */     Root<CierreCaja> from = criteriaQuery.from(CierreCaja.class);
/*  40:    */     
/*  41: 58 */     from.fetch("usuario", JoinType.LEFT);
/*  42: 59 */     from.fetch("caja", JoinType.LEFT);
/*  43:    */     
/*  44: 61 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  45: 62 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  46: 63 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  47:    */     
/*  48: 65 */     CriteriaQuery<CierreCaja> select = criteriaQuery.select(from);
/*  49:    */     
/*  50: 67 */     TypedQuery<CierreCaja> typedQuery = this.em.createQuery(select);
/*  51: 68 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  52:    */     
/*  53: 70 */     return typedQuery.getResultList();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public CierreCaja cargarDetalle(int idCierreCaja)
/*  57:    */   {
/*  58: 75 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  59: 76 */     CriteriaQuery<CierreCaja> cqCabecera = criteriaBuilder.createQuery(CierreCaja.class);
/*  60: 77 */     Root<CierreCaja> fromCabecera = cqCabecera.from(CierreCaja.class);
/*  61:    */     
/*  62: 79 */     Path<Integer> pathId = fromCabecera.get("idCierreCaja");
/*  63: 80 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idCierreCaja)));
/*  64: 81 */     CriteriaQuery<CierreCaja> selectCierreCaja = cqCabecera.select(fromCabecera);
/*  65:    */     
/*  66: 83 */     CierreCaja cierreCaja = (CierreCaja)this.em.createQuery(selectCierreCaja).getSingleResult();
/*  67:    */     
/*  68:    */ 
/*  69: 86 */     CriteriaQuery<DetalleCierreCaja> cqDetalle = criteriaBuilder.createQuery(DetalleCierreCaja.class);
/*  70: 87 */     Root<DetalleCierreCaja> fromDetalle = cqDetalle.from(DetalleCierreCaja.class);
/*  71:    */     
/*  72: 89 */     fromDetalle.fetch("cierreCaja", JoinType.LEFT);
/*  73: 90 */     Fetch<Object, Object> detalleFormaCobro = fromDetalle.fetch("detalleFormaCobro", JoinType.LEFT);
/*  74: 91 */     detalleFormaCobro.fetch("formaPago", JoinType.LEFT);
/*  75: 92 */     detalleFormaCobro.fetch("cuentaBancariaOrganizacion", JoinType.LEFT).fetch("cuentaBancaria", JoinType.LEFT).fetch("banco", JoinType.LEFT);
/*  76: 93 */     detalleFormaCobro.fetch("cobro", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*  77:    */     
/*  78: 95 */     Fetch<Object, Object> anticipoCliente = fromDetalle.fetch("anticipoCliente", JoinType.LEFT);
/*  79: 96 */     anticipoCliente.fetch("formaPago", JoinType.LEFT);
/*  80: 97 */     anticipoCliente.fetch("cuentaBancariaOrganizacion", JoinType.LEFT).fetch("cuentaBancaria", JoinType.LEFT).fetch("banco", JoinType.LEFT);
/*  81: 98 */     anticipoCliente.fetch("empresa", JoinType.LEFT);
/*  82:    */     
/*  83:100 */     Path<Integer> pathIdDetalle = fromDetalle.join("cierreCaja").get("idCierreCaja");
/*  84:101 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idCierreCaja)));
/*  85:102 */     CriteriaQuery<DetalleCierreCaja> selectDetalleCierreCaja = cqDetalle.select(fromDetalle);
/*  86:    */     
/*  87:104 */     List<DetalleCierreCaja> listaDetalleCierreCaja = this.em.createQuery(selectDetalleCierreCaja).getResultList();
/*  88:105 */     cierreCaja.setListaDetalleCierreCaja(listaDetalleCierreCaja);
/*  89:    */     
/*  90:    */ 
/*  91:108 */     CriteriaQuery<DetalleDenominacionFormaCobro> cqDetalleDenominacion = criteriaBuilder.createQuery(DetalleDenominacionFormaCobro.class);
/*  92:109 */     Root<DetalleDenominacionFormaCobro> fromDetalleDenominacion = cqDetalleDenominacion.from(DetalleDenominacionFormaCobro.class);
/*  93:    */     
/*  94:111 */     fromDetalleDenominacion.fetch("cierreCaja", JoinType.LEFT);
/*  95:112 */     Fetch<Object, Object> denominacion = fromDetalleDenominacion.fetch("denominacionFormaCobro", JoinType.LEFT);
/*  96:113 */     denominacion.fetch("formaPago", JoinType.LEFT);
/*  97:114 */     Path<Integer> pathIdDetalleDenominacion = fromDetalleDenominacion.join("cierreCaja").get("idCierreCaja");
/*  98:115 */     cqDetalleDenominacion.where(criteriaBuilder.equal(pathIdDetalleDenominacion, Integer.valueOf(idCierreCaja)));
/*  99:116 */     CriteriaQuery<DetalleDenominacionFormaCobro> selectDetalleDenominacion = cqDetalleDenominacion.select(fromDetalleDenominacion);
/* 100:    */     
/* 101:118 */     List<DetalleDenominacionFormaCobro> listaDetalleDenominacionFormaCobro = this.em.createQuery(selectDetalleDenominacion).getResultList();
/* 102:119 */     cierreCaja.setListDetalleDenominacionFormaCobro(listaDetalleDenominacionFormaCobro);
/* 103:120 */     return cierreCaja;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public List<DetalleFormaCobro> obtenerListaDetalleCierreCaja(int idOrganizacion, String nombreUsuario, Integer idCaja)
/* 107:    */   {
/* 108:126 */     StringBuilder sql = new StringBuilder();
/* 109:127 */     sql.append("SELECT dfc from DetalleFormaCobro dfc ");
/* 110:128 */     sql.append("JOIN FETCH dfc.cuentaBancariaOrganizacion cbo ");
/* 111:129 */     sql.append("JOIN FETCH cbo.cuentaBancaria cb ");
/* 112:130 */     sql.append("JOIN FETCH cb.banco b ");
/* 113:131 */     sql.append("JOIN FETCH dfc.formaPago f ");
/* 114:132 */     sql.append("JOIN FETCH dfc.cobro c ");
/* 115:133 */     sql.append("JOIN FETCH c.empresa e ");
/* 116:134 */     sql.append("JOIN FETCH dfc.caja ca ");
/* 117:135 */     sql.append("WHERE c.idOrganizacion = :idOrganizacion ");
/* 118:136 */     sql.append("AND NOT EXISTS (SELECT dcc FROM DetalleCierreCaja dcc WHERE dcc.detalleFormaCobro = dfc) ");
/* 119:137 */     sql.append("AND dfc.usuarioCreacion = :nombreUsuario ");
/* 120:138 */     if (idCaja != null) {
/* 121:139 */       sql.append("AND dfc.caja.idCaja = :idCaja ");
/* 122:    */     }
/* 123:141 */     sql.append("AND c.estado != :estado ");
/* 124:142 */     sql.append("ORDER BY f.nombre, c.numero ");
/* 125:    */     
/* 126:144 */     Query query = this.em.createQuery(sql.toString());
/* 127:145 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 128:146 */     query.setParameter("nombreUsuario", nombreUsuario);
/* 129:147 */     if (idCaja != null) {
/* 130:148 */       query.setParameter("idCaja", idCaja);
/* 131:    */     }
/* 132:150 */     query.setParameter("estado", Estado.ANULADO);
/* 133:151 */     return query.getResultList();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public List<AnticipoCliente> obtenerListaDetalleCierreCajaAC(int idOrganizacion, String nombreUsuario, Integer idCaja)
/* 137:    */   {
/* 138:162 */     StringBuilder sql = new StringBuilder();
/* 139:163 */     sql.append("SELECT ac from AnticipoCliente ac ");
/* 140:164 */     sql.append("JOIN FETCH ac.cuentaBancariaOrganizacion cbo ");
/* 141:165 */     sql.append("JOIN FETCH cbo.cuentaBancaria cb ");
/* 142:166 */     sql.append("JOIN FETCH cb.banco b ");
/* 143:167 */     sql.append("JOIN FETCH ac.empresa e ");
/* 144:168 */     sql.append("JOIN FETCH ac.formaPago f ");
/* 145:169 */     sql.append("JOIN FETCH ac.caja ca ");
/* 146:170 */     sql.append("WHERE ac.idOrganizacion = :idOrganizacion ");
/* 147:171 */     sql.append("AND NOT EXISTS (SELECT dcc FROM DetalleCierreCaja dcc WHERE dcc.anticipoCliente = ac) ");
/* 148:172 */     sql.append("AND ac.usuarioCreacion = :nombreUsuario ");
/* 149:173 */     if (idCaja != null) {
/* 150:174 */       sql.append("AND ca.idCaja = :idCaja ");
/* 151:    */     }
/* 152:176 */     sql.append("AND ac.estado = :estadoContabilizado ");
/* 153:177 */     sql.append("AND ac.indicadorSaldoInicial = false ");
/* 154:178 */     sql.append("AND (ac.documentoDevolucion = NULL OR ac.notaCreditoCliente IS NULL)");
/* 155:179 */     sql.append("ORDER BY ac.numero ");
/* 156:    */     
/* 157:181 */     Query query = this.em.createQuery(sql.toString());
/* 158:182 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 159:183 */     query.setParameter("nombreUsuario", nombreUsuario);
/* 160:184 */     if (idCaja != null) {
/* 161:185 */       query.setParameter("idCaja", idCaja);
/* 162:    */     }
/* 163:187 */     query.setParameter("estadoContabilizado", Estado.CONTABILIZADO);
/* 164:188 */     return query.getResultList();
/* 165:    */   }
/* 166:    */   
/* 167:    */   public boolean isCierreCaja(AnticipoCliente anticipoCliente)
/* 168:    */   {
/* 169:194 */     Query query = this.em.createQuery("SELECT ac FROM DetalleCierreCaja dca JOIN  dca.anticipoCliente ac WHERE ac = :anticipoCliente ");
/* 170:195 */     query.setParameter("anticipoCliente", anticipoCliente);
/* 171:    */     try
/* 172:    */     {
/* 173:198 */       query.getSingleResult();
/* 174:199 */       return true;
/* 175:    */     }
/* 176:    */     catch (NoResultException e) {}
/* 177:201 */     return false;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean isCierreCaja(DetalleFormaCobro detalleFormaCobro)
/* 181:    */   {
/* 182:207 */     Query query = this.em.createQuery("SELECT ac FROM DetalleCierreCaja dca JOIN  dca.detalleFormaCobro ac WHERE ac = :detalleFormaCobro ");
/* 183:208 */     query.setParameter("detalleFormaCobro", detalleFormaCobro);
/* 184:    */     try
/* 185:    */     {
/* 186:211 */       query.getSingleResult();
/* 187:212 */       return true;
/* 188:    */     }
/* 189:    */     catch (NoResultException e) {}
/* 190:214 */     return false;
/* 191:    */   }
/* 192:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CierreCajaDao
 * JD-Core Version:    0.7.0.1
 */