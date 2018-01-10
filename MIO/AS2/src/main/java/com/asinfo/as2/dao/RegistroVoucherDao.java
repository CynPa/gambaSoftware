/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Cobro;
/*   4:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*   5:    */ import com.asinfo.as2.entities.PlanTarjetaCredito;
/*   6:    */ import com.asinfo.as2.entities.TarjetaCredito;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ import javax.persistence.EntityManager;
/*  12:    */ import javax.persistence.Query;
/*  13:    */ import javax.persistence.TypedQuery;
/*  14:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  15:    */ import javax.persistence.criteria.CriteriaQuery;
/*  16:    */ import javax.persistence.criteria.Expression;
/*  17:    */ import javax.persistence.criteria.Fetch;
/*  18:    */ import javax.persistence.criteria.JoinType;
/*  19:    */ import javax.persistence.criteria.Order;
/*  20:    */ import javax.persistence.criteria.Predicate;
/*  21:    */ import javax.persistence.criteria.Root;
/*  22:    */ 
/*  23:    */ @Stateless
/*  24:    */ public class RegistroVoucherDao
/*  25:    */   extends AbstractDaoAS2<Cobro>
/*  26:    */ {
/*  27:    */   public RegistroVoucherDao()
/*  28:    */   {
/*  29: 39 */     super(Cobro.class);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public List<Cobro> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  33:    */   {
/*  34: 49 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  35: 50 */     CriteriaQuery<Cobro> criteriaQuery = criteriaBuilder.createQuery(Cobro.class);
/*  36: 51 */     Root<Cobro> from = criteriaQuery.from(Cobro.class);
/*  37:    */     
/*  38: 53 */     from.fetch("asiento", JoinType.LEFT).fetch("tipoAsiento", JoinType.LEFT);
/*  39:    */     
/*  40:    */ 
/*  41: 56 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  42: 57 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  43:    */     
/*  44: 59 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  45:    */     
/*  46: 61 */     CriteriaQuery<Cobro> select = criteriaQuery.select(from);
/*  47:    */     
/*  48: 63 */     TypedQuery<Cobro> typedQuery = this.em.createQuery(select);
/*  49: 64 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  50:    */     
/*  51: 66 */     return typedQuery.getResultList();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Cobro cargarDetalle(Cobro cobro, boolean cobroTarjeta)
/*  55:    */   {
/*  56: 71 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  57: 72 */     CriteriaQuery<Cobro> cq = cb.createQuery(Cobro.class);
/*  58:    */     
/*  59: 74 */     Root<Cobro> from = cq.from(Cobro.class);
/*  60: 75 */     from.fetch("asiento", JoinType.LEFT);
/*  61: 76 */     from.fetch("formaPago", JoinType.LEFT);
/*  62: 77 */     from.fetch("sucursal", JoinType.LEFT);
/*  63: 78 */     if (cobroTarjeta) {
/*  64: 79 */       from.fetch("cuentaBancariaOrganizacion", JoinType.LEFT);
/*  65:    */     }
/*  66: 81 */     Fetch<Object, Object> documento = from.fetch("documento");
/*  67: 82 */     documento.fetch("secuencia", JoinType.LEFT);
/*  68: 83 */     documento.fetch("tipoAsiento", JoinType.LEFT);
/*  69:    */     
/*  70: 85 */     cq.where(cb.equal(from.get("idCobro"), Integer.valueOf(cobro.getId())));
/*  71: 86 */     cobro = (Cobro)this.em.createQuery(cq.select(from)).getSingleResult();
/*  72:    */     
/*  73: 88 */     CriteriaQuery<DetalleFormaCobro> cqdfc = cb.createQuery(DetalleFormaCobro.class);
/*  74: 89 */     Root<DetalleFormaCobro> detalleFC = cqdfc.from(DetalleFormaCobro.class);
/*  75: 90 */     detalleFC.fetch("puntoVenta", JoinType.LEFT);
/*  76: 91 */     detalleFC.fetch("planTarjetaCredito", JoinType.LEFT);
/*  77: 92 */     detalleFC.fetch("formaPago", JoinType.LEFT);
/*  78: 93 */     detalleFC.fetch("cobro", JoinType.LEFT);
/*  79: 94 */     if (!cobroTarjeta)
/*  80:    */     {
/*  81: 96 */       detalleFC.fetch("cobroTarjeta", JoinType.LEFT);
/*  82: 97 */       detalleFC.fetch("asiento", JoinType.LEFT);
/*  83:    */     }
/*  84:    */     else
/*  85:    */     {
/*  86:100 */       detalleFC.fetch("banco", JoinType.LEFT);
/*  87:    */     }
/*  88:103 */     Fetch<Object, Object> tarjeta = detalleFC.fetch("tarjetaCredito");
/*  89:104 */     tarjeta.fetch("tipoTarjetaCredito", JoinType.LEFT);
/*  90:105 */     tarjeta.fetch("cuentaContableIvaComision", JoinType.LEFT);
/*  91:106 */     tarjeta.fetch("cuentaContableRetencionFuente", JoinType.LEFT);
/*  92:107 */     tarjeta.fetch("cuentaContableIvaComision", JoinType.LEFT);
/*  93:108 */     tarjeta.fetch("cuentaContableRetencionIva", JoinType.LEFT);
/*  94:    */     
/*  95:110 */     Fetch<Object, Object> cuentaBO = tarjeta.fetch("cuentaBancariaOrganizacion", JoinType.LEFT);
/*  96:111 */     cuentaBO.fetch("cuentaContableBanco", JoinType.LEFT);
/*  97:112 */     cuentaBO.fetch("cuentaContableTransitoria", JoinType.LEFT);
/*  98:    */     
/*  99:114 */     cqdfc.where(cb.equal(detalleFC.get(cobroTarjeta ? "cobroTarjeta" : "cobro"), cobro));
/* 100:115 */     cqdfc.orderBy(new Order[] { cb.asc(detalleFC.get("idDetalleFormaCobro")) });
/* 101:116 */     List<DetalleFormaCobro> listaDetalleFormaCobro = this.em.createQuery(cqdfc.select(detalleFC)).getResultList();
/* 102:    */     
/* 103:118 */     this.em.detach(cobro);
/* 104:119 */     cobro.setListaDetalleFormaCobro(listaDetalleFormaCobro);
/* 105:120 */     for (DetalleFormaCobro detalle : listaDetalleFormaCobro)
/* 106:    */     {
/* 107:121 */       this.em.detach(detalle);
/* 108:123 */       if (cobroTarjeta) {
/* 109:124 */         detalle.setCobroTarjeta(cobro);
/* 110:    */       } else {
/* 111:126 */         detalle.setCobro(cobro);
/* 112:    */       }
/* 113:    */     }
/* 114:130 */     return cobro;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public List<DetalleFormaCobro> getVouchersNoCobrados(int idOrganizacion, List<TarjetaCredito> listaTarjetaCredito, List<PlanTarjetaCredito> listaPlanTarjeta, List<String> listaLote)
/* 118:    */   {
/* 119:143 */     StringBuilder sql = new StringBuilder();
/* 120:144 */     sql.append(" SELECT dfc FROM DetalleFormaCobro dfc");
/* 121:145 */     sql.append(" JOIN FETCH dfc.puntoVenta pv");
/* 122:146 */     sql.append(" JOIN FETCH dfc.planTarjetaCredito ptc");
/* 123:147 */     sql.append(" JOIN FETCH dfc.cobro co");
/* 124:148 */     sql.append(" JOIN co.documento do");
/* 125:149 */     sql.append(" JOIN FETCH dfc.tarjetaCredito tc");
/* 126:150 */     sql.append(" JOIN FETCH tc.tipoTarjetaCredito");
/* 127:151 */     sql.append(" LEFT JOIN FETCH dfc.formaPago fp");
/* 128:152 */     sql.append(" LEFT JOIN FETCH tc.cuentaBancariaOrganizacion cbo");
/* 129:153 */     sql.append(" LEFT JOIN FETCH tc.cuentaContableIvaComision ccic");
/* 130:154 */     sql.append(" LEFT JOIN FETCH tc.cuentaContableRetencionFuente ccrf");
/* 131:155 */     sql.append(" LEFT JOIN FETCH tc.cuentaContableRetencionIva ccri");
/* 132:156 */     sql.append(" LEFT JOIN FETCH cbo.cuentaBancaria");
/* 133:157 */     sql.append(" LEFT JOIN FETCH cbo.cuentaContableBanco");
/* 134:158 */     sql.append(" LEFT JOIN FETCH cbo.cuentaContableTransitoria");
/* 135:159 */     sql.append(" WHERE co.idOrganizacion = :idOrganizacion");
/* 136:160 */     sql.append(" AND co.estado != :estadoAnulado");
/* 137:161 */     sql.append(" AND dfc.cobroTarjeta IS NULL");
/* 138:162 */     sql.append(" AND fp.indicadorTarjetaCredito = true ");
/* 139:164 */     if (listaTarjetaCredito != null) {
/* 140:165 */       sql.append(" AND tc IN (:listaTarjetaCredito)");
/* 141:    */     }
/* 142:167 */     if (listaPlanTarjeta != null) {
/* 143:168 */       sql.append(" AND ptc IN (:listaPlanTarjeta)");
/* 144:    */     }
/* 145:171 */     if (listaLote != null) {
/* 146:172 */       sql.append(" AND dfc.lote IN( :listaLote )");
/* 147:    */     }
/* 148:175 */     Query query = this.em.createQuery(sql.toString());
/* 149:176 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 150:177 */     query.setParameter("estadoAnulado", Estado.ANULADO);
/* 151:178 */     if (listaTarjetaCredito != null) {
/* 152:179 */       query.setParameter("listaTarjetaCredito", listaTarjetaCredito);
/* 153:    */     }
/* 154:181 */     if (listaPlanTarjeta != null) {
/* 155:182 */       query.setParameter("listaPlanTarjeta", listaPlanTarjeta);
/* 156:    */     }
/* 157:184 */     if (listaLote != null) {
/* 158:185 */       query.setParameter("listaLote", listaLote);
/* 159:    */     }
/* 160:188 */     return query.getResultList();
/* 161:    */   }
/* 162:    */   
/* 163:    */   public List<String> getLotesPendientesPorCobrar(int idOrganizacion)
/* 164:    */   {
/* 165:193 */     StringBuilder sql = new StringBuilder();
/* 166:194 */     sql.append(" SELECT DISTINCT dfc.lote ");
/* 167:195 */     sql.append(" FROM DetalleFormaCobro dfc");
/* 168:196 */     sql.append(" JOIN dfc.cobro co");
/* 169:197 */     sql.append(" JOIN dfc.formaPago fp");
/* 170:198 */     sql.append(" JOIN co.documento do");
/* 171:199 */     sql.append(" WHERE co.idOrganizacion = :idOrganizacion");
/* 172:200 */     sql.append(" AND fp.indicadorTarjetaCredito = true ");
/* 173:201 */     sql.append(" AND dfc.cobroTarjeta IS NULL");
/* 174:    */     
/* 175:203 */     Query query = this.em.createQuery(sql.toString());
/* 176:204 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 177:205 */     return query.getResultList();
/* 178:    */   }
/* 179:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.RegistroVoucherDao
 * JD-Core Version:    0.7.0.1
 */