/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Comision;
/*   4:    */ import com.asinfo.as2.entities.PlanTarjetaCredito;
/*   5:    */ import com.asinfo.as2.entities.TarjetaCredito;
/*   6:    */ import com.asinfo.as2.entities.VersionComision;
/*   7:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   8:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.Stateless;
/*  14:    */ import javax.persistence.EntityManager;
/*  15:    */ import javax.persistence.Query;
/*  16:    */ import javax.persistence.TemporalType;
/*  17:    */ import javax.persistence.TypedQuery;
/*  18:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  19:    */ import javax.persistence.criteria.CriteriaQuery;
/*  20:    */ import javax.persistence.criteria.Expression;
/*  21:    */ import javax.persistence.criteria.Fetch;
/*  22:    */ import javax.persistence.criteria.JoinType;
/*  23:    */ import javax.persistence.criteria.Predicate;
/*  24:    */ import javax.persistence.criteria.Root;
/*  25:    */ 
/*  26:    */ @Stateless
/*  27:    */ public class TarjetaCreditoDao
/*  28:    */   extends AbstractDaoAS2<TarjetaCredito>
/*  29:    */ {
/*  30:    */   public TarjetaCreditoDao()
/*  31:    */   {
/*  32: 47 */     super(TarjetaCredito.class);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public TarjetaCredito cargarDetalle(int idTarjetaCredito)
/*  36:    */   {
/*  37: 52 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  38: 53 */     CriteriaQuery<TarjetaCredito> cq = cb.createQuery(TarjetaCredito.class);
/*  39:    */     
/*  40: 55 */     Root<TarjetaCredito> from = cq.from(TarjetaCredito.class);
/*  41: 56 */     from.fetch("tipoTarjetaCredito", JoinType.LEFT);
/*  42: 57 */     from.fetch("banco", JoinType.LEFT);
/*  43: 58 */     from.fetch("cuentaBancariaOrganizacion", JoinType.LEFT);
/*  44: 59 */     from.fetch("cuentaContableRetencionFuente", JoinType.LEFT);
/*  45: 60 */     from.fetch("cuentaContableRetencionIva", JoinType.LEFT);
/*  46: 61 */     from.fetch("cuentaContableIvaComision", JoinType.LEFT);
/*  47:    */     
/*  48: 63 */     Fetch<Object, Object> cuentaBancariaOrganizacion = from.fetch("cuentaBancariaOrganizacion", JoinType.LEFT);
/*  49: 64 */     Fetch<Object, Object> cuentaBancaria = cuentaBancariaOrganizacion.fetch("cuentaBancaria", JoinType.LEFT);
/*  50: 65 */     cuentaBancaria.fetch("banco", JoinType.LEFT);
/*  51:    */     
/*  52: 67 */     cq.where(cb.equal(from.get("idTarjetaCredito"), Integer.valueOf(idTarjetaCredito)));
/*  53: 68 */     TarjetaCredito tarjetaCredito = (TarjetaCredito)this.em.createQuery(cq.select(from)).getSingleResult();
/*  54: 69 */     this.em.detach(tarjetaCredito);
/*  55:    */     
/*  56: 71 */     CriteriaQuery<VersionComision> cqVersiones = cb.createQuery(VersionComision.class);
/*  57: 72 */     Root<VersionComision> detalle = cqVersiones.from(VersionComision.class);
/*  58: 73 */     cqVersiones.where(cb.equal(detalle.get("tarjetaCredito"), tarjetaCredito));
/*  59: 74 */     List<VersionComision> listaVersiones = this.em.createQuery(cqVersiones).getResultList();
/*  60: 75 */     tarjetaCredito.setListaVersionComision(listaVersiones);
/*  61: 77 */     for (Iterator localIterator1 = listaVersiones.iterator(); localIterator1.hasNext();)
/*  62:    */     {
/*  63: 77 */       vc = (VersionComision)localIterator1.next();
/*  64: 78 */       this.em.detach(vc);
/*  65: 79 */       vc.setTarjetaCredito(tarjetaCredito);
/*  66:    */       
/*  67: 81 */       CriteriaQuery<Comision> cqDetalleCaja = cb.createQuery(Comision.class);
/*  68: 82 */       Root<Comision> detalleCaja = cqDetalleCaja.from(Comision.class);
/*  69:    */       
/*  70: 84 */       detalleCaja.fetch("planTarjetaCredito", JoinType.LEFT);
/*  71:    */       
/*  72: 86 */       cqDetalleCaja.where(cb.equal(detalleCaja.get("versionComision"), vc));
/*  73:    */       
/*  74: 88 */       List<Comision> listaDetalleCajaPacking = this.em.createQuery(cqDetalleCaja).getResultList();
/*  75:    */       
/*  76: 90 */       vc.setListaComision(listaDetalleCajaPacking);
/*  77: 92 */       for (Comision com : listaDetalleCajaPacking)
/*  78:    */       {
/*  79: 93 */         this.em.detach(com);
/*  80: 94 */         com.setVersionComision(vc);
/*  81:    */       }
/*  82:    */     }
/*  83:    */     VersionComision vc;
/*  84: 99 */     return tarjetaCredito;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Comision getComision(TarjetaCredito tarjeta, PlanTarjetaCredito planTarjeta, Date fecha)
/*  88:    */     throws AS2Exception
/*  89:    */   {
/*  90:105 */     StringBuilder sql = new StringBuilder();
/*  91:106 */     sql.append(" SELECT co FROM Comision co");
/*  92:107 */     sql.append(" JOIN co.planTarjetaCredito ptc");
/*  93:108 */     sql.append(" JOIN co.versionComision vco");
/*  94:109 */     sql.append(" JOIN vco.tarjetaCredito tc");
/*  95:110 */     sql.append(" WHERE tc = :tarjeta");
/*  96:111 */     sql.append(" AND ptc = :planTarjeta");
/*  97:112 */     sql.append(" AND vco.fechaDesde <= :fecha");
/*  98:113 */     sql.append(" AND (vco.fechaHasta IS NULL OR :fecha <= vco.fechaHasta)");
/*  99:    */     
/* 100:115 */     Query query = this.em.createQuery(sql.toString());
/* 101:116 */     query.setParameter("tarjeta", tarjeta);
/* 102:117 */     query.setParameter("planTarjeta", planTarjeta);
/* 103:118 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 104:    */     
/* 105:120 */     List<Comision> lista = query.getResultList();
/* 106:122 */     if (lista.isEmpty()) {
/* 107:123 */       throw new AS2Exception("com.asinfo.as2.dao.TarjetaCreditoDao.CONFIGURAR_COMISION_TARJETA", new String[] { tarjeta.getNombre(), planTarjeta.getNombre() });
/* 108:    */     }
/* 109:124 */     if (lista.size() > 1) {
/* 110:125 */       throw new AS2Exception("com.asinfo.as2.dao.TarjetaCreditoDao.COMISION_TARJETA_DUPLICADA", new String[] { tarjeta.getNombre(), planTarjeta.getNombre(), FuncionesUtiles.dateToString(fecha) });
/* 111:    */     }
/* 112:127 */     return (Comision)lista.get(0);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public List<TarjetaCredito> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filtros)
/* 116:    */   {
/* 117:134 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/* 118:135 */     CriteriaQuery<TarjetaCredito> cq = cb.createQuery(TarjetaCredito.class);
/* 119:    */     
/* 120:137 */     Root<TarjetaCredito> from = cq.from(TarjetaCredito.class);
/* 121:138 */     from.fetch("tipoTarjetaCredito", JoinType.LEFT);
/* 122:139 */     from.fetch("cuentaBancariaOrganizacion", JoinType.LEFT);
/* 123:    */     
/* 124:141 */     Fetch<Object, Object> cuentaBancariaOrganizacion = from.fetch("cuentaBancariaOrganizacion", JoinType.LEFT);
/* 125:142 */     Fetch<Object, Object> cuentaBancaria = cuentaBancariaOrganizacion.fetch("cuentaBancaria", JoinType.LEFT);
/* 126:143 */     cuentaBancaria.fetch("banco", JoinType.LEFT);
/* 127:144 */     cuentaBancariaOrganizacion.fetch("cuentaContableBanco", JoinType.LEFT);
/* 128:145 */     cuentaBancariaOrganizacion.fetch("cuentaContableTransitoria", JoinType.LEFT);
/* 129:146 */     agregarOrdenamiento(sortField, sortOrder, cb, cq, from);
/* 130:    */     
/* 131:148 */     List<Expression<?>> expresiones = obtenerExpresiones(filtros, cb, from);
/* 132:    */     
/* 133:150 */     cq.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 134:151 */     return this.em.createQuery(cq.select(from)).getResultList();
/* 135:    */   }
/* 136:    */   
/* 137:    */   public List<PlanTarjetaCredito> getPlanTarjetaCredito(TarjetaCredito tarjetaCredito)
/* 138:    */   {
/* 139:156 */     StringBuilder sql = new StringBuilder();
/* 140:157 */     sql.append(" SELECT ptc FROM Comision co");
/* 141:158 */     sql.append(" JOIN co.planTarjetaCredito ptc");
/* 142:159 */     sql.append(" JOIN co.versionComision vco");
/* 143:160 */     sql.append(" JOIN vco.tarjetaCredito tc");
/* 144:161 */     sql.append(" WHERE tc.idTarjetaCredito = :idTarjetaCredito");
/* 145:    */     
/* 146:163 */     Query query = this.em.createQuery(sql.toString());
/* 147:164 */     query.setParameter("idTarjetaCredito", Integer.valueOf(tarjetaCredito.getId()));
/* 148:    */     
/* 149:166 */     List<PlanTarjetaCredito> lista = query.getResultList();
/* 150:167 */     return lista;
/* 151:    */   }
/* 152:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.TarjetaCreditoDao
 * JD-Core Version:    0.7.0.1
 */