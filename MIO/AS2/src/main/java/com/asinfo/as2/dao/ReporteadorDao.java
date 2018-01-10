/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetalleReporteador;
/*   4:    */ import com.asinfo.as2.entities.DetalleReporteadorVariable;
/*   5:    */ import com.asinfo.as2.entities.Reporteador;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.ejb.Stateless;
/*   9:    */ import javax.persistence.EntityManager;
/*  10:    */ import javax.persistence.Query;
/*  11:    */ import javax.persistence.TypedQuery;
/*  12:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  13:    */ import javax.persistence.criteria.CriteriaQuery;
/*  14:    */ import javax.persistence.criteria.Expression;
/*  15:    */ import javax.persistence.criteria.Join;
/*  16:    */ import javax.persistence.criteria.JoinType;
/*  17:    */ import javax.persistence.criteria.Order;
/*  18:    */ import javax.persistence.criteria.Path;
/*  19:    */ import javax.persistence.criteria.Predicate;
/*  20:    */ import javax.persistence.criteria.Root;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class ReporteadorDao
/*  24:    */   extends AbstractDaoAS2<Reporteador>
/*  25:    */ {
/*  26:    */   public ReporteadorDao()
/*  27:    */   {
/*  28: 45 */     super(Reporteador.class);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Reporteador cargarDetalle(int idReporteador)
/*  32:    */   {
/*  33: 49 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  34:    */     
/*  35:    */ 
/*  36: 52 */     CriteriaQuery<Reporteador> cqCabecera = criteriaBuilder.createQuery(Reporteador.class);
/*  37: 53 */     Root<Reporteador> fromCabecera = cqCabecera.from(Reporteador.class);
/*  38:    */     
/*  39: 55 */     Path<Integer> pathId = fromCabecera.get("idReporteador");
/*  40: 56 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(idReporteador)));
/*  41: 57 */     CriteriaQuery<Reporteador> selectReporteador = cqCabecera.select(fromCabecera);
/*  42:    */     
/*  43: 59 */     Reporteador reporteador = (Reporteador)this.em.createQuery(selectReporteador).getSingleResult();
/*  44:    */     
/*  45:    */ 
/*  46: 62 */     CriteriaQuery<DetalleReporteadorVariable> cqDetalleVariable = criteriaBuilder.createQuery(DetalleReporteadorVariable.class);
/*  47: 63 */     Root<DetalleReporteadorVariable> fromDetalleVariable = cqDetalleVariable.from(DetalleReporteadorVariable.class);
/*  48:    */     
/*  49: 65 */     fromDetalleVariable.fetch("cuentaContable", JoinType.LEFT);
/*  50: 66 */     fromDetalleVariable.fetch("reporteador", JoinType.INNER);
/*  51:    */     
/*  52: 68 */     Path<Integer> pathIdReporteador = fromDetalleVariable.join("reporteador").get("idReporteador");
/*  53: 69 */     cqDetalleVariable.where(criteriaBuilder.equal(pathIdReporteador, Integer.valueOf(reporteador.getId())));
/*  54: 70 */     CriteriaQuery<DetalleReporteadorVariable> selectDetalleCC = cqDetalleVariable.select(fromDetalleVariable);
/*  55:    */     
/*  56: 72 */     List<DetalleReporteadorVariable> listaDetalleReporteadorVariable = this.em.createQuery(selectDetalleCC).getResultList();
/*  57: 73 */     reporteador.setListaDetalleReporteadorVariable(listaDetalleReporteadorVariable);
/*  58:    */     
/*  59: 75 */     List<DetalleReporteador> listaDetalleReporteador = cargarDetalleReporteador(reporteador, null);
/*  60: 76 */     reporteador.setListaDetalleReporteador(listaDetalleReporteador);
/*  61:    */     
/*  62: 78 */     return reporteador;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public List<DetalleReporteador> cargarDetalleReporteador(Reporteador reporteador, DetalleReporteador detalleReporteadorPadre)
/*  66:    */   {
/*  67: 82 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  68:    */     
/*  69:    */ 
/*  70: 85 */     CriteriaQuery<DetalleReporteador> cqDetalle = criteriaBuilder.createQuery(DetalleReporteador.class);
/*  71: 86 */     Root<DetalleReporteador> fromDetalle = cqDetalle.from(DetalleReporteador.class);
/*  72:    */     
/*  73: 88 */     fromDetalle.fetch("reporteador", JoinType.INNER);
/*  74: 89 */     fromDetalle.fetch("detalleReporteadorPadre", JoinType.LEFT);
/*  75: 90 */     fromDetalle.fetch("detalleReporteadorVariable", JoinType.LEFT);
/*  76:    */     
/*  77: 92 */     List<Expression<Boolean>> expresiones = new ArrayList();
/*  78:    */     
/*  79: 94 */     Path<Integer> pathIdReporteador = fromDetalle.join("reporteador").get("idReporteador");
/*  80: 95 */     expresiones.add(criteriaBuilder.equal(pathIdReporteador, Integer.valueOf(reporteador.getId())));
/*  81: 96 */     if (detalleReporteadorPadre == null)
/*  82:    */     {
/*  83: 97 */       expresiones.add(criteriaBuilder.isNull(fromDetalle.get("detalleReporteadorPadre")));
/*  84:    */     }
/*  85:    */     else
/*  86:    */     {
/*  87: 99 */       Path<Integer> pathIdDetallePadre = fromDetalle.join("detalleReporteadorPadre").get("idDetalleReporteador");
/*  88:100 */       expresiones.add(criteriaBuilder.equal(pathIdDetallePadre, Integer.valueOf(detalleReporteadorPadre.getId())));
/*  89:    */     }
/*  90:102 */     cqDetalle.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  91:    */     
/*  92:    */ 
/*  93:105 */     CriteriaQuery<DetalleReporteador> selectDetalleReporteador = cqDetalle.select(fromDetalle).orderBy(new Order[] {criteriaBuilder.asc(fromDetalle.get("orden")) });
/*  94:    */     
/*  95:107 */     List<DetalleReporteador> listaDetalleReporteador = this.em.createQuery(selectDetalleReporteador).getResultList();
/*  96:109 */     for (DetalleReporteador detalleReporteador : listaDetalleReporteador)
/*  97:    */     {
/*  98:110 */       List<DetalleReporteador> listaDetalleReporteadorHijo = cargarDetalleReporteador(reporteador, detalleReporteador);
/*  99:111 */       detalleReporteador.setListaDetalleReporteadorHijo(listaDetalleReporteadorHijo);
/* 100:    */     }
/* 101:114 */     return listaDetalleReporteador;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public DetalleReporteadorVariable buscarVariablePorCodigo(String codigoVariable, int idOrganizacion)
/* 105:    */   {
/* 106:119 */     StringBuffer sql = new StringBuffer();
/* 107:120 */     sql.append("SELECT drv FROM DetalleReporteadorVariable drv ");
/* 108:121 */     sql.append(" WHERE drv.codigo=:codigoVariable ");
/* 109:122 */     sql.append(" AND drv.idOrganizacion = :idOrganizacion");
/* 110:123 */     Query query = this.em.createQuery(sql.toString());
/* 111:124 */     query.setParameter("codigoVariable", codigoVariable);
/* 112:125 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 113:    */     
/* 114:127 */     DetalleReporteadorVariable detalleReporteadorVariable = (DetalleReporteadorVariable)query.getSingleResult();
/* 115:    */     
/* 116:129 */     return detalleReporteadorVariable;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public List<DetalleReporteadorVariable> getVariablesSinCuentaContable(Reporteador reporteador)
/* 120:    */   {
/* 121:134 */     StringBuffer sql = new StringBuffer();
/* 122:135 */     sql.append(" SELECT drv FROM DetalleReporteadorVariable drv ");
/* 123:136 */     sql.append(" INNER JOIN FETCH drv.reporteador r ");
/* 124:137 */     sql.append(" WHERE drv.cuentaContable is null ");
/* 125:138 */     sql.append(" AND drv.reporteador = :reporteador");
/* 126:139 */     sql.append(" AND drv.indicadorFormula = false");
/* 127:    */     
/* 128:141 */     Query query = this.em.createQuery(sql.toString());
/* 129:142 */     query.setParameter("reporteador", reporteador);
/* 130:    */     
/* 131:144 */     return query.getResultList();
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ReporteadorDao
 * JD-Core Version:    0.7.0.1
 */