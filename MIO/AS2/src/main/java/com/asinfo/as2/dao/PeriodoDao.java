/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Periodo;
/*   4:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   5:    */ import com.asinfo.as2.enumeraciones.ProcesoPeriodoEnum;
/*   6:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ import javax.persistence.EntityManager;
/*  12:    */ import javax.persistence.NoResultException;
/*  13:    */ import javax.persistence.NonUniqueResultException;
/*  14:    */ import javax.persistence.Query;
/*  15:    */ import javax.persistence.TemporalType;
/*  16:    */ import javax.persistence.TypedQuery;
/*  17:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  18:    */ import javax.persistence.criteria.CriteriaQuery;
/*  19:    */ import javax.persistence.criteria.Expression;
/*  20:    */ import javax.persistence.criteria.JoinType;
/*  21:    */ import javax.persistence.criteria.Predicate;
/*  22:    */ import javax.persistence.criteria.Root;
/*  23:    */ 
/*  24:    */ @Stateless
/*  25:    */ public class PeriodoDao
/*  26:    */   extends AbstractDaoAS2<Periodo>
/*  27:    */ {
/*  28:    */   public PeriodoDao()
/*  29:    */   {
/*  30: 46 */     super(Periodo.class);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Periodo buscarPorFecha(Date fecha, int idOrganizacion, DocumentoBase documentoBase)
/*  34:    */     throws ExcepcionAS2Financiero
/*  35:    */   {
/*  36:    */     try
/*  37:    */     {
/*  38: 59 */       StringBuffer sql = new StringBuffer();
/*  39: 60 */       sql.append("SELECT p FROM Periodo p");
/*  40: 61 */       sql.append(" WHERE :fecha BETWEEN p.fechaDesde AND p.fechaHasta");
/*  41: 62 */       if ((documentoBase != null) && (documentoBase.getProcesoPeriodo() != null)) {
/*  42: 63 */         sql.append(" AND p." + documentoBase.getProcesoPeriodo().getNombreIndicador() + " = true ");
/*  43:    */       }
/*  44: 65 */       sql.append(" AND p." + ProcesoPeriodoEnum.CONTABILIDAD.getNombreIndicador() + " = true ");
/*  45: 66 */       sql.append(" AND p.idOrganizacion = :idOrganizacion");
/*  46:    */       
/*  47: 68 */       Query query = this.em.createQuery(sql.toString());
/*  48: 69 */       query.setParameter("fecha", fecha, TemporalType.DATE);
/*  49: 70 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  50:    */       
/*  51: 72 */       return (Periodo)query.getSingleResult();
/*  52:    */     }
/*  53:    */     catch (NoResultException e)
/*  54:    */     {
/*  55: 75 */       String mensaje = "\nVerificar Proceso: ";
/*  56: 76 */       if ((documentoBase != null) && (documentoBase.getProcesoPeriodo() != null)) {
/*  57: 77 */         mensaje = mensaje + documentoBase.getProcesoPeriodo().getNombreIndicador().replace("indicadorCierre", "") + " o";
/*  58:    */       }
/*  59: 79 */       if (mensaje.contains("Contabilidad")) {
/*  60: 80 */         mensaje = mensaje.replace(" o", "");
/*  61:    */       } else {
/*  62: 82 */         mensaje = mensaje + " " + ProcesoPeriodoEnum.CONTABILIDAD.getNombreIndicador().replace("indicadorCierre", "");
/*  63:    */       }
/*  64: 84 */       throw new ExcepcionAS2Financiero("msg_periodo_no_encontrado", mensaje);
/*  65:    */     }
/*  66:    */     catch (NonUniqueResultException e)
/*  67:    */     {
/*  68: 86 */       String mensaje = "\nVerificar Proceso: ";
/*  69: 87 */       if ((documentoBase != null) && (documentoBase.getProcesoPeriodo() != null)) {
/*  70: 88 */         mensaje = mensaje + documentoBase.getProcesoPeriodo().getNombreIndicador().replace("indicadorCierre", "") + " o";
/*  71:    */       }
/*  72: 90 */       if (mensaje.contains("Contabilidad")) {
/*  73: 91 */         mensaje = mensaje.replace(" o", "");
/*  74:    */       } else {
/*  75: 93 */         mensaje = mensaje + " " + ProcesoPeriodoEnum.CONTABILIDAD.getNombreIndicador().replace("indicadorCierre", "");
/*  76:    */       }
/*  77: 95 */       throw new ExcepcionAS2Financiero("msg_periodo_duplicado", mensaje);
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void validar(Date fechaDesde, Date fechaHasta, int idOrganizacion)
/*  82:    */     throws ExcepcionAS2Financiero
/*  83:    */   {
/*  84:100 */     StringBuffer sql = new StringBuffer();
/*  85:101 */     sql.append("SELECT p FROM Periodo p ");
/*  86:102 */     sql.append("INNER JOIN p.ejercicio e ");
/*  87:103 */     sql.append("WHERE ");
/*  88:104 */     sql.append("( ");
/*  89:105 */     sql.append(" \tp.fechaDesde BETWEEN :fechaDesde AND :fechaHasta ");
/*  90:106 */     sql.append("\tOR p.fechaHasta BETWEEN :fechaDesde AND :fechaHasta ");
/*  91:107 */     sql.append(" ) AND p." + ProcesoPeriodoEnum.CONTABILIDAD.getNombreIndicador() + "  = :activo ");
/*  92:108 */     sql.append("AND e.idOrganizacion = :idOrganizacion ");
/*  93:109 */     sql.append("ORDER BY p.fechaDesde ASC");
/*  94:110 */     Query query = this.em.createQuery(sql.toString());
/*  95:111 */     query.setParameter("fechaDesde", fechaDesde);
/*  96:112 */     query.setParameter("fechaHasta", fechaHasta);
/*  97:113 */     query.setParameter("activo", Boolean.valueOf(false));
/*  98:114 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  99:115 */     List<Periodo> lista = query.getResultList();
/* 100:116 */     if (lista.size() > 0) {
/* 101:117 */       throw new ExcepcionAS2Financiero("msg_periodo_cerrado");
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Periodo obtenerPeriodoActual(int idOrganizacion, DocumentoBase documentoBase)
/* 106:    */     throws ExcepcionAS2Financiero
/* 107:    */   {
/* 108:129 */     Periodo periodo = null;
/* 109:    */     try
/* 110:    */     {
/* 111:131 */       StringBuffer sql = new StringBuffer();
/* 112:132 */       sql.append("SELECT p FROM Periodo p");
/* 113:133 */       sql.append(" WHERE idOrganizacion = :idOrganizacion");
/* 114:134 */       if (documentoBase != null)
/* 115:    */       {
/* 116:135 */         sql.append(" AND p." + documentoBase.getProcesoPeriodo().getNombreIndicador() + " = true");
/* 117:    */       }
/* 118:    */       else
/* 119:    */       {
/* 120:137 */         String mensaje = "\nProceso: Inventario";
/* 121:138 */         throw new ExcepcionAS2Financiero("msg_periodo_no_encontrado", mensaje);
/* 122:    */       }
/* 123:140 */       sql.append(" ORDER BY p.fechaDesde ASC");
/* 124:141 */       Query query = this.em.createQuery(sql.toString());
/* 125:142 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 126:143 */       query.setMaxResults(1);
/* 127:144 */       return (Periodo)query.getSingleResult();
/* 128:    */     }
/* 129:    */     catch (NoResultException e)
/* 130:    */     {
/* 131:149 */       String mensaje = "";
/* 132:150 */       if ((documentoBase != null) && (documentoBase.getProcesoPeriodo() != null)) {
/* 133:151 */         mensaje = "\nProceso: " + documentoBase.getProcesoPeriodo().getNombreIndicador().replace("indicadorCierre", "");
/* 134:    */       }
/* 135:153 */       throw new ExcepcionAS2Financiero("msg_periodo_no_encontrado", mensaje);
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   public List<Periodo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 140:    */   {
/* 141:158 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 142:159 */     CriteriaQuery<Periodo> criteriaQuery = criteriaBuilder.createQuery(Periodo.class);
/* 143:160 */     Root<Periodo> from = criteriaQuery.from(Periodo.class);
/* 144:161 */     from.fetch("ejercicio", JoinType.LEFT);
/* 145:    */     
/* 146:163 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 147:164 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 148:    */     
/* 149:166 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 150:    */     
/* 151:168 */     CriteriaQuery<Periodo> select = criteriaQuery.select(from);
/* 152:169 */     TypedQuery<Periodo> typedQuery = this.em.createQuery(select);
/* 153:    */     
/* 154:171 */     return typedQuery.getResultList();
/* 155:    */   }
/* 156:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PeriodoDao
 * JD-Core Version:    0.7.0.1
 */