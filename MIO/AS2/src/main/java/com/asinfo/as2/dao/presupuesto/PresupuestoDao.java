/*   1:    */ package com.asinfo.as2.dao.presupuesto;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.presupuesto.DetallePresupuesto;
/*   5:    */ import com.asinfo.as2.entities.presupuesto.DetallePresupuestoCentroCosto;
/*   6:    */ import com.asinfo.as2.entities.presupuesto.Presupuesto;
/*   7:    */ import com.asinfo.as2.enumeraciones.ProcesoPeriodoEnum;
/*   8:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.Comparator;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ import javax.persistence.EntityManager;
/*  16:    */ import javax.persistence.NoResultException;
/*  17:    */ import javax.persistence.Query;
/*  18:    */ import javax.persistence.TemporalType;
/*  19:    */ import javax.persistence.TypedQuery;
/*  20:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  21:    */ import javax.persistence.criteria.CriteriaQuery;
/*  22:    */ import javax.persistence.criteria.Expression;
/*  23:    */ import javax.persistence.criteria.Join;
/*  24:    */ import javax.persistence.criteria.JoinType;
/*  25:    */ import javax.persistence.criteria.Order;
/*  26:    */ import javax.persistence.criteria.Path;
/*  27:    */ import javax.persistence.criteria.Predicate;
/*  28:    */ import javax.persistence.criteria.Root;
/*  29:    */ 
/*  30:    */ @Stateless
/*  31:    */ public class PresupuestoDao
/*  32:    */   extends AbstractDaoAS2<Presupuesto>
/*  33:    */ {
/*  34:    */   public PresupuestoDao()
/*  35:    */   {
/*  36: 53 */     super(Presupuesto.class);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public List<Presupuesto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  40:    */   {
/*  41: 62 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  42: 63 */     CriteriaQuery<Presupuesto> criteriaQuery = criteriaBuilder.createQuery(Presupuesto.class);
/*  43: 64 */     Root<Presupuesto> from = criteriaQuery.from(Presupuesto.class);
/*  44: 65 */     from.fetch("periodo", JoinType.LEFT);
/*  45: 66 */     from.fetch("ejercicio", JoinType.LEFT);
/*  46:    */     
/*  47: 68 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  48: 69 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  49:    */     
/*  50: 71 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  51:    */     
/*  52: 73 */     CriteriaQuery<Presupuesto> select = criteriaQuery.select(from);
/*  53: 74 */     TypedQuery<Presupuesto> typedQuery = this.em.createQuery(select);
/*  54: 75 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  55:    */     
/*  56: 77 */     return typedQuery.getResultList();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Presupuesto cargarDetalle(int idPresupuesto)
/*  60:    */   {
/*  61: 87 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  62: 88 */     CriteriaQuery<Presupuesto> cq = cb.createQuery(Presupuesto.class);
/*  63:    */     
/*  64: 90 */     Root<Presupuesto> from = cq.from(Presupuesto.class);
/*  65: 91 */     from.fetch("ejercicio", JoinType.LEFT);
/*  66:    */     
/*  67: 93 */     cq.where(cb.equal(from.get("idPresupuesto"), Integer.valueOf(idPresupuesto)));
/*  68: 94 */     Presupuesto presupuesto = (Presupuesto)this.em.createQuery(cq.select(from)).getSingleResult();
/*  69: 95 */     this.em.detach(presupuesto);
/*  70:    */     
/*  71: 97 */     CriteriaQuery<DetallePresupuesto> cqDetalle = cb.createQuery(DetallePresupuesto.class);
/*  72: 98 */     Root<DetallePresupuesto> detalle = cqDetalle.from(DetallePresupuesto.class);
/*  73: 99 */     detalle.fetch("cuentaContable", JoinType.LEFT);
/*  74:100 */     detalle.fetch("dimensionContable", JoinType.LEFT);
/*  75:    */     
/*  76:102 */     cqDetalle.where(cb.equal(detalle.get("presupuesto"), presupuesto));
/*  77:103 */     cqDetalle.orderBy(new Order[] { cb.asc(detalle.get("idDetallePresupuesto")) });
/*  78:    */     
/*  79:105 */     List<DetallePresupuesto> listaDetalle = this.em.createQuery(cqDetalle).getResultList();
/*  80:106 */     presupuesto.setListaDetallePresupuesto(listaDetalle);
/*  81:109 */     for (DetallePresupuesto dp : listaDetalle)
/*  82:    */     {
/*  83:111 */       int idDetallePresupuesto = dp.getId();
/*  84:    */       
/*  85:113 */       CriteriaQuery<DetallePresupuestoCentroCosto> cqCentro = cb.createQuery(DetallePresupuestoCentroCosto.class);
/*  86:114 */       Root<DetallePresupuestoCentroCosto> fromCentro = cqCentro.from(DetallePresupuestoCentroCosto.class);
/*  87:    */       
/*  88:116 */       fromCentro.fetch("centroCosto", JoinType.LEFT);
/*  89:    */       
/*  90:118 */       Path<Integer> pathIdCentro = fromCentro.join("detallePresupuesto").get("idDetallePresupuesto");
/*  91:119 */       cqCentro.where(cb.equal(pathIdCentro, Integer.valueOf(idDetallePresupuesto)));
/*  92:120 */       CriteriaQuery<DetallePresupuestoCentroCosto> selectCentro = cqCentro.select(fromCentro);
/*  93:    */       
/*  94:122 */       List<DetallePresupuestoCentroCosto> listaDetallePresupuestoCentroCosto = this.em.createQuery(selectCentro).getResultList();
/*  95:123 */       dp.setListaDetallePresupuestoCentroCosto(listaDetallePresupuestoCentroCosto);
/*  96:    */     }
/*  97:126 */     return presupuesto;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Presupuesto obtenerPresupuesto(int idPresupuesto, int idEjercicio, int idPeriodo, int idOrganizacion)
/* 101:    */   {
/* 102:132 */     Presupuesto presupuesto = null;
/* 103:    */     
/* 104:134 */     StringBuilder sql = new StringBuilder();
/* 105:135 */     sql.append(" SELECT pr FROM Presupuesto pr ");
/* 106:136 */     sql.append(" INNER JOIN pr.periodo p ");
/* 107:137 */     sql.append(" INNER JOIN pr.ejercicio e ");
/* 108:138 */     sql.append(" WHERE e.idEjercicio=:idEjercicio");
/* 109:139 */     sql.append(" AND p.idPeriodo=:idPeriodo ");
/* 110:140 */     sql.append(" AND pr.idOrganizacion=:idOrganizacion ");
/* 111:141 */     sql.append(" AND pr.idPresupuesto!=:idPresupuesto ");
/* 112:    */     
/* 113:143 */     Query query = this.em.createQuery(sql.toString());
/* 114:144 */     query.setParameter("idPresupuesto", Integer.valueOf(idPresupuesto));
/* 115:145 */     query.setParameter("idEjercicio", Integer.valueOf(idEjercicio));
/* 116:146 */     query.setParameter("idPeriodo", Integer.valueOf(idPeriodo));
/* 117:147 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 118:    */     
/* 119:149 */     List<Presupuesto> lista = query.getResultList();
/* 120:151 */     if (!lista.isEmpty()) {
/* 121:152 */       presupuesto = (Presupuesto)lista.get(0);
/* 122:    */     }
/* 123:155 */     return presupuesto;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public List<Object[]> getReportePresupuesto(int idPresupuesto, int idOrganizacion, int idUsuario)
/* 127:    */   {
/* 128:160 */     StringBuilder sql = new StringBuilder();
/* 129:161 */     sql.append("SELECT e.nombre, dc.codigo, dc.nombre, cc.codigo, cc.nombre,");
/* 130:162 */     sql.append(" dpr.valorEnero, dpr.valorFebrero, dpr.valorMarzo, dpr.valorAbril, dpr.valorMayo, dpr.valorJunio,");
/* 131:163 */     sql.append(" dpr.valorJulio, dpr.valorAgosto, dpr.valorSeptiembre, dpr.valorOctubre, dpr.valorNoviembre, dpr.valorDiciembre");
/* 132:164 */     sql.append(" FROM DetallePresupuesto dpr");
/* 133:165 */     sql.append(" INNER JOIN dpr.presupuesto pr");
/* 134:166 */     sql.append(" INNER JOIN pr.ejercicio e");
/* 135:167 */     sql.append(" INNER JOIN dpr.dimensionContable dc");
/* 136:168 */     sql.append(" INNER JOIN dpr.cuentaContable cc");
/* 137:169 */     sql.append(" WHERE pr.idOrganizacion=:idOrganizacion");
/* 138:170 */     sql.append(" AND pr.idPresupuesto=:idPresupuesto");
/* 139:171 */     sql.append(" AND dc.idDimensionContable in");
/* 140:172 */     sql.append(" (SELECT dcu.idDimensionContable FROM UsuarioDimensionContable udc");
/* 141:173 */     sql.append(" INNER JOIN udc.dimensionContable dcu");
/* 142:174 */     sql.append(" INNER JOIN udc.entidadUsuario eu");
/* 143:175 */     sql.append(" WHERE eu.idUsuario=:idUsuario)");
/* 144:    */     
/* 145:177 */     Query query = this.em.createQuery(sql.toString());
/* 146:178 */     query.setParameter("idPresupuesto", Integer.valueOf(idPresupuesto));
/* 147:179 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 148:180 */     query.setParameter("idUsuario", Integer.valueOf(idUsuario));
/* 149:181 */     List<Object[]> list = query.getResultList();
/* 150:    */     
/* 151:183 */     Collections.sort(list, new Comparator()
/* 152:    */     {
/* 153:    */       public int compare(Object[] o1, Object[] o2)
/* 154:    */       {
/* 155:186 */         return ((String)o1[1] + "_" + (String)o1[3]).compareTo((String)o2[1] + "_" + (String)o2[3]);
/* 156:    */       }
/* 157:190 */     });
/* 158:191 */     return list;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public Presupuesto buscarPresupuestoPorEjercicio(int idEjercicio, int idOrganizacion)
/* 162:    */   {
/* 163:195 */     StringBuilder sql = new StringBuilder();
/* 164:196 */     sql.append("SELECT pr ");
/* 165:197 */     sql.append(" FROM Presupuesto pr");
/* 166:198 */     sql.append(" INNER JOIN pr.ejercicio e");
/* 167:199 */     sql.append(" WHERE pr.idOrganizacion=:idOrganizacion");
/* 168:200 */     sql.append(" AND e.idEjercicio=:idEjercicio");
/* 169:    */     
/* 170:202 */     Query query = this.em.createQuery(sql.toString());
/* 171:203 */     query.setParameter("idEjercicio", Integer.valueOf(idEjercicio));
/* 172:204 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 173:205 */     query.setMaxResults(1);
/* 174:206 */     return (Presupuesto)query.getSingleResult();
/* 175:    */   }
/* 176:    */   
/* 177:    */   public Presupuesto buscarPorFecha(Date fecha, int idOrganizacion)
/* 178:    */     throws ExcepcionAS2Financiero
/* 179:    */   {
/* 180:    */     try
/* 181:    */     {
/* 182:211 */       StringBuffer sql = new StringBuffer();
/* 183:212 */       sql.append("SELECT e.idEjercicio");
/* 184:213 */       sql.append(" FROM Periodo p");
/* 185:214 */       sql.append(" INNER JOIN p.ejercicio e");
/* 186:215 */       sql.append(" WHERE :fecha BETWEEN p.fechaDesde AND p.fechaHasta ");
/* 187:216 */       sql.append(" AND p." + ProcesoPeriodoEnum.CONTABILIDAD.getNombreIndicador() + " = true");
/* 188:217 */       sql.append(" AND p.idOrganizacion = :idOrganizacion");
/* 189:    */       
/* 190:219 */       Query query = this.em.createQuery(sql.toString());
/* 191:220 */       query.setParameter("fecha", fecha, TemporalType.DATE);
/* 192:221 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 193:222 */       query.setMaxResults(1);
/* 194:223 */       Integer idEjercicio = (Integer)query.getSingleResult();
/* 195:224 */       return buscarPresupuestoPorEjercicio(idEjercicio.intValue(), idOrganizacion);
/* 196:    */     }
/* 197:    */     catch (NoResultException e)
/* 198:    */     {
/* 199:226 */       String mensaje = "\nVerificar Proceso: ";
/* 200:227 */       mensaje = mensaje + " " + ProcesoPeriodoEnum.CONTABILIDAD.getNombreIndicador().replace("indicadorCierre", "");
/* 201:228 */       throw new ExcepcionAS2Financiero("msg_periodo_no_encontrado", mensaje);
/* 202:    */     }
/* 203:    */   }
/* 204:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.presupuesto.PresupuestoDao
 * JD-Core Version:    0.7.0.1
 */