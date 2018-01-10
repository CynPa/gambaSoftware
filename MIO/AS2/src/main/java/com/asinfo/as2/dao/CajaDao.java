/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.DetalleInterfazContable;
/*   4:    */ import com.asinfo.as2.entities.Caja;
/*   5:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   6:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   7:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   8:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ import javax.persistence.EntityManager;
/*  15:    */ import javax.persistence.NoResultException;
/*  16:    */ import javax.persistence.Query;
/*  17:    */ import javax.persistence.TypedQuery;
/*  18:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  19:    */ import javax.persistence.criteria.CriteriaQuery;
/*  20:    */ import javax.persistence.criteria.Expression;
/*  21:    */ import javax.persistence.criteria.JoinType;
/*  22:    */ import javax.persistence.criteria.Predicate;
/*  23:    */ import javax.persistence.criteria.Root;
/*  24:    */ 
/*  25:    */ @Stateless
/*  26:    */ public class CajaDao
/*  27:    */   extends AbstractDaoAS2<Caja>
/*  28:    */ {
/*  29:    */   public CajaDao()
/*  30:    */   {
/*  31: 51 */     super(Caja.class);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public List<DetalleInterfazContable> interfazContableDeposito(InterfazContableProceso interfazContableProceso)
/*  35:    */     throws ExcepcionAS2Financiero
/*  36:    */   {
/*  37: 56 */     List<DetalleInterfazContable> listaDetalleInterfazContable = new ArrayList();
/*  38:    */     try
/*  39:    */     {
/*  40: 60 */       StringBuilder sql1 = new StringBuilder();
/*  41: 61 */       sql1.append(" SELECT new DetalleInterfazContable(ccb.idCuentaContable,'',   CONCAT ('DEPOSITO CAJA: ', icp.observacion,' ',icp.documentoReferencia  ) ,");
/*  42:    */       
/*  43: 63 */       sql1.append(" icp.documentoReferencia,fp.idFormaPago,ROUND(SUM(dcc.valor),2)) ");
/*  44: 64 */       sql1.append(" FROM DetalleCierreCaja dcc ");
/*  45: 65 */       sql1.append(" INNER JOIN dcc.interfazContableProceso icp ");
/*  46: 66 */       sql1.append(" INNER JOIN icp.cuentaBancariaOrganizacion cbo ");
/*  47: 67 */       sql1.append(" INNER JOIN cbo.cuentaContableBanco ccb ");
/*  48: 68 */       sql1.append(" INNER JOIN icp.formaPago fp ");
/*  49: 69 */       sql1.append(" WHERE icp.idInterfazContableProceso = :idInterfazContableProceso ");
/*  50: 70 */       sql1.append(" GROUP BY ccb.idCuentaContable, icp.documentoReferencia,fp.idFormaPago,icp.observacion");
/*  51:    */       
/*  52: 72 */       Query query = this.em.createQuery(sql1.toString());
/*  53: 73 */       query.setParameter("idInterfazContableProceso", Integer.valueOf(interfazContableProceso.getIdInterfazContableProceso()));
/*  54:    */       
/*  55: 75 */       listaDetalleInterfazContable.addAll(query.getResultList());
/*  56:    */       
/*  57:    */ 
/*  58: 78 */       StringBuilder sql2 = new StringBuilder();
/*  59: 79 */       sql2.append(" SELECT new DetalleInterfazContable(ccfc.idCuentaContable,'',   CONCAT ('DEPOSITO CAJA: ', coalesce(ca.codigo,''),' ',icp.observacion,' ',co.numero) ,'',fp.idFormaPago,-ROUND(SUM(dcc.valor),2)) ");
/*  60:    */       
/*  61: 81 */       sql2.append(" FROM DetalleCierreCaja dcc ");
/*  62: 82 */       sql2.append(" INNER JOIN dcc.interfazContableProceso icp ");
/*  63: 83 */       sql2.append(" INNER JOIN dcc.cierreCaja cc ");
/*  64: 84 */       sql2.append(" INNER JOIN dcc.detalleFormaCobro dfc ");
/*  65: 85 */       sql2.append(" INNER JOIN dfc.cuentaContableFormaCobro ccfc ");
/*  66: 86 */       sql2.append(" INNER JOIN dfc.cobro co ");
/*  67: 87 */       sql2.append(" INNER JOIN icp.formaPago fp ");
/*  68: 88 */       sql2.append(" INNER JOIN dfc.cuentaBancariaOrganizacion cbo ");
/*  69: 89 */       sql2.append(" LEFT OUTER JOIN cc.caja ca ");
/*  70: 90 */       sql2.append(" WHERE fp.indicadorRetencionIva = false AND fp.indicadorRetencionFuente = false ");
/*  71: 91 */       sql2.append(" AND icp.idInterfazContableProceso = :idInterfazContableProceso ");
/*  72: 92 */       sql2.append(" GROUP BY ccfc.idCuentaContable,fp.idFormaPago, ca.codigo, icp.observacion, co.numero ");
/*  73:    */       
/*  74: 94 */       query = this.em.createQuery(sql2.toString());
/*  75: 95 */       query.setParameter("idInterfazContableProceso", Integer.valueOf(interfazContableProceso.getIdInterfazContableProceso()));
/*  76:    */       
/*  77: 97 */       listaDetalleInterfazContable.addAll(query.getResultList());
/*  78:    */       
/*  79:    */ 
/*  80:100 */       StringBuilder sql3 = new StringBuilder();
/*  81:101 */       sql3.append(" SELECT new DetalleInterfazContable(ccb.idCuentaContable,'',   CONCAT('DEPOSITO CAJA: ',coalesce(ca.codigo,''),' ',icp.observacion) ,'',fp.idFormaPago,-ROUND(SUM(dcc.valor),2)) ");
/*  82:    */       
/*  83:103 */       sql3.append(" FROM DetalleCierreCaja dcc ");
/*  84:104 */       sql3.append(" INNER JOIN dcc.interfazContableProceso icp ");
/*  85:105 */       sql3.append(" INNER JOIN dcc.cierreCaja cc ");
/*  86:106 */       sql3.append(" INNER JOIN dcc.anticipoCliente ac ");
/*  87:107 */       sql3.append(" INNER JOIN icp.formaPago fp ");
/*  88:108 */       sql3.append(" INNER JOIN ac.cuentaBancariaOrganizacion cbo ");
/*  89:109 */       sql3.append(" INNER JOIN cbo.cuentaContableBanco ccb ");
/*  90:110 */       sql3.append(" LEFT OUTER JOIN cc.caja ca ");
/*  91:111 */       sql3.append(" WHERE fp.indicadorRetencionIva = false AND fp.indicadorRetencionFuente = false ");
/*  92:112 */       sql3.append(" AND icp.idInterfazContableProceso = :idInterfazContableProceso ");
/*  93:113 */       sql3.append(" GROUP BY ccb.idCuentaContable,fp.idFormaPago, ca.codigo, icp.observacion ");
/*  94:    */       
/*  95:115 */       query = this.em.createQuery(sql3.toString());
/*  96:116 */       query.setParameter("idInterfazContableProceso", Integer.valueOf(interfazContableProceso.getIdInterfazContableProceso()));
/*  97:    */       
/*  98:118 */       listaDetalleInterfazContable.addAll(query.getResultList());
/*  99:    */       
/* 100:120 */       return listaDetalleInterfazContable;
/* 101:    */     }
/* 102:    */     catch (IllegalArgumentException e)
/* 103:    */     {
/* 104:123 */       e.printStackTrace();
/* 105:124 */       throw new ExcepcionAS2Financiero("msg_error_parametrizacion_contable", "cuentaContableBanco");
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public List<Caja> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 110:    */   {
/* 111:130 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 112:131 */     CriteriaQuery<Caja> criteriaQuery = criteriaBuilder.createQuery(Caja.class);
/* 113:132 */     Root<Caja> from = criteriaQuery.from(Caja.class);
/* 114:133 */     from.fetch("puntoDeVenta", JoinType.LEFT);
/* 115:    */     
/* 116:135 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 117:136 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 118:    */     
/* 119:138 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 120:    */     
/* 121:140 */     CriteriaQuery<Caja> select = criteriaQuery.select(from);
/* 122:141 */     TypedQuery<Caja> typedQuery = this.em.createQuery(select);
/* 123:    */     
/* 124:143 */     return typedQuery.getResultList();
/* 125:    */   }
/* 126:    */   
/* 127:    */   public List obtenerReporteCaja(Date fechaDesde, Date fechaHasta, int idUsuario, Estado estadoCaja)
/* 128:    */   {
/* 129:148 */     String sql = " SELECT e.nombreFiscal, e.identificacion,  ac.fecha, ac.numero, ac.valor, ac.documentoReferencia , ac.descripcion,  fp.nombre, cb.numero, b.nombre  FROM AnticipoCliente ac  INNER JOIN ac.empresa e  INNER JOIN ac.formaPago fp  INNER JOIN ac.cuentaBancariaOrganizacion cbo  INNER JOIN cbo.cuentaBancaria cb  INNER JOIN cb.banco b  WHERE ac.idAnticipoCliente = :idAnticipo ";
/* 130:    */     
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:153 */     Query query = this.em.createQuery(sql);
/* 135:154 */     query.setParameter("fechaDesde", fechaDesde);
/* 136:155 */     query.setParameter("fechaHasta", fechaHasta);
/* 137:156 */     query.setParameter("idUsuario", Integer.valueOf(idUsuario));
/* 138:157 */     query.setParameter("estadoCaja", estadoCaja);
/* 139:    */     
/* 140:159 */     return query.getResultList();
/* 141:    */   }
/* 142:    */   
/* 143:    */   public List getReporteCaja(Date fechaDesde, Date fechaHasta, String nombreUsuario, int idFormaPago)
/* 144:    */   {
/* 145:165 */     String sql = " SELECT fp.nombre, e.nombreComercial, c.numero, c.descripcion, dfc.valor, dfc.documentoReferencia, dfc.usuarioCreacion, c.fecha FROM DetalleCierreCaja dcc JOIN dcc.detalleFormaCobro dfc  JOIN dfc.cobro c JOIN dfc.formaPago fp JOIN c.empresa e WHERE c.fecha BETWEEN :fechaDesde AND :fechaHasta  AND (fp.idFormaPago = :idFormaPago OR :idFormaPago=0)  AND (dfc.usuarioCreacion = :nombreUsuario OR :nombreUsuario='')  ";
/* 146:    */     
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:174 */     Query query = this.em.createQuery(sql);
/* 155:175 */     query.setParameter("fechaDesde", fechaDesde);
/* 156:176 */     query.setParameter("fechaHasta", fechaHasta);
/* 157:177 */     query.setParameter("idFormaPago", Integer.valueOf(idFormaPago));
/* 158:178 */     query.setParameter("nombreUsuario", nombreUsuario);
/* 159:    */     
/* 160:180 */     return query.getResultList();
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Caja buscarCaja(Map<String, String> filters)
/* 164:    */     throws ExcepcionAS2
/* 165:    */   {
/* 166:    */     try
/* 167:    */     {
/* 168:187 */       CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 169:188 */       CriteriaQuery<Caja> cq = cb.createQuery(Caja.class);
/* 170:189 */       Root<Caja> from = cq.from(Caja.class);
/* 171:190 */       from.fetch("puntoDeVenta", JoinType.LEFT);
/* 172:    */       
/* 173:192 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, cb, from);
/* 174:193 */       cq.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 175:    */       
/* 176:195 */       CriteriaQuery<Caja> select = cq.select(from);
/* 177:196 */       return (Caja)this.em.createQuery(select).getSingleResult();
/* 178:    */     }
/* 179:    */     catch (NoResultException e)
/* 180:    */     {
/* 181:199 */       throw new ExcepcionAS2("msg_info_empresa_no_encontrada", " " + (String)filters.get("nombre"));
/* 182:    */     }
/* 183:    */   }
/* 184:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CajaDao
 * JD-Core Version:    0.7.0.1
 */