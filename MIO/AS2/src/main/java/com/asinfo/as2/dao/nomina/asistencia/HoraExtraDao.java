/*   1:    */ package com.asinfo.as2.dao.nomina.asistencia;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.Rubro;
/*   5:    */ import com.asinfo.as2.entities.nomina.asistencia.HoraExtra;
/*   6:    */ import com.asinfo.as2.enumeraciones.PorCientoHoraExtra;
/*   7:    */ import java.util.Calendar;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ import javax.persistence.EntityManager;
/*  13:    */ import javax.persistence.Query;
/*  14:    */ import javax.persistence.TemporalType;
/*  15:    */ import javax.persistence.TypedQuery;
/*  16:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  17:    */ import javax.persistence.criteria.CriteriaQuery;
/*  18:    */ import javax.persistence.criteria.Expression;
/*  19:    */ import javax.persistence.criteria.JoinType;
/*  20:    */ import javax.persistence.criteria.Path;
/*  21:    */ import javax.persistence.criteria.Predicate;
/*  22:    */ import javax.persistence.criteria.Root;
/*  23:    */ 
/*  24:    */ @Stateless
/*  25:    */ public class HoraExtraDao
/*  26:    */   extends AbstractDaoAS2<HoraExtra>
/*  27:    */ {
/*  28:    */   public HoraExtraDao()
/*  29:    */   {
/*  30: 45 */     super(HoraExtra.class);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<HoraExtra> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  34:    */   {
/*  35: 49 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  36: 50 */     CriteriaQuery<HoraExtra> criteriaQuery = criteriaBuilder.createQuery(HoraExtra.class);
/*  37: 51 */     Root<HoraExtra> from = criteriaQuery.from(HoraExtra.class);
/*  38: 52 */     from.fetch("rubro", JoinType.LEFT);
/*  39:    */     
/*  40: 54 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  41:    */     
/*  42: 56 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  43:    */     
/*  44: 58 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  45:    */     
/*  46: 60 */     CriteriaQuery<HoraExtra> select = criteriaQuery.select(from);
/*  47:    */     
/*  48: 62 */     TypedQuery<HoraExtra> typedQuery = this.em.createQuery(select);
/*  49: 63 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  50: 64 */     return typedQuery.getResultList();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public HoraExtra cargarDetalle(HoraExtra horaExtra)
/*  54:    */   {
/*  55: 68 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  56:    */     
/*  57:    */ 
/*  58: 71 */     CriteriaQuery<HoraExtra> cqCabecera = criteriaBuilder.createQuery(HoraExtra.class);
/*  59: 72 */     Root<HoraExtra> fromCabecera = cqCabecera.from(HoraExtra.class);
/*  60:    */     
/*  61: 74 */     fromCabecera.fetch("rubro", JoinType.LEFT);
/*  62:    */     
/*  63: 76 */     Path<Integer> pathId = fromCabecera.get("idHoraExtra");
/*  64: 77 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(horaExtra.getId())));
/*  65: 78 */     CriteriaQuery<HoraExtra> select = cqCabecera.select(fromCabecera);
/*  66:    */     
/*  67: 80 */     HoraExtra horaExtraNew = (HoraExtra)this.em.createQuery(select).getSingleResult();
/*  68:    */     
/*  69: 82 */     return horaExtraNew;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public List<HoraExtra> buscarHoraExtra(int idOrganizacion, PorCientoHoraExtra porCiento, Date horaDesde, Date horaHasta, Boolean indicadorDiaFestivo, Boolean indicadorDentroHorario, boolean indicadorDiaDescanso, boolean isDiaComplentario)
/*  73:    */   {
/*  74: 89 */     if ((horaDesde == null) || (horaHasta == null)) {
/*  75: 90 */       return null;
/*  76:    */     }
/*  77: 92 */     StringBuilder sql = new StringBuilder();
/*  78: 93 */     sql.append(" SELECT he FROM HoraExtra he ");
/*  79: 94 */     sql.append(" LEFT JOIN FETCH he.rubro ru ");
/*  80: 95 */     sql.append(" WHERE he.idOrganizacion=:idOrganizacion ");
/*  81:    */     
/*  82: 97 */     sql.append(" AND he.horaDesde <= CAST (:horaHasta AS time)");
/*  83: 98 */     sql.append(" AND (he.horaHasta > CAST (:horaDesde AS time) OR he.horaHasta = CAST (:hora00 AS time) ) ");
/*  84: 99 */     sql.append(" AND he.indicadorDiaDescanso = :indicadorDiaDescanso");
/*  85:100 */     sql.append(" AND he.indicadorDiaComplementario = :indicadorDiaComplementario");
/*  86:101 */     if (porCiento != null) {
/*  87:102 */       sql.append(" AND he.porCiento = :porCiento");
/*  88:    */     }
/*  89:104 */     if (indicadorDiaFestivo != null) {
/*  90:105 */       sql.append(" AND he.indicadorDiaFestivo = :indicadorDiaFestivo ");
/*  91:    */     }
/*  92:107 */     if (indicadorDentroHorario != null) {
/*  93:108 */       sql.append(" AND he.indicadorDentroHorario = :indicadorDentroHorario ");
/*  94:    */     }
/*  95:110 */     sql.append(" ORDER BY he.porCiento DESC ");
/*  96:    */     
/*  97:112 */     Query query = this.em.createQuery(sql.toString());
/*  98:    */     
/*  99:114 */     Calendar hora00Cal = Calendar.getInstance();
/* 100:115 */     hora00Cal.set(11, 0);
/* 101:116 */     hora00Cal.set(12, 0);
/* 102:117 */     hora00Cal.set(13, 0);
/* 103:118 */     hora00Cal.set(14, 0);
/* 104:    */     
/* 105:120 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 106:121 */     query.setParameter("horaDesde", horaDesde, TemporalType.TIME);
/* 107:122 */     query.setParameter("horaHasta", horaHasta, TemporalType.TIME);
/* 108:123 */     query.setParameter("hora00", hora00Cal.getTime(), TemporalType.TIME);
/* 109:124 */     query.setParameter("indicadorDiaDescanso", Boolean.valueOf(indicadorDiaDescanso));
/* 110:125 */     query.setParameter("indicadorDiaComplementario", Boolean.valueOf(isDiaComplentario));
/* 111:126 */     if (porCiento != null) {
/* 112:127 */       query.setParameter("porCiento", porCiento);
/* 113:    */     }
/* 114:129 */     if (indicadorDiaFestivo != null) {
/* 115:130 */       query.setParameter("indicadorDiaFestivo", indicadorDiaFestivo);
/* 116:    */     }
/* 117:132 */     if (indicadorDentroHorario != null) {
/* 118:133 */       query.setParameter("indicadorDentroHorario", indicadorDentroHorario);
/* 119:    */     }
/* 120:137 */     return query.getResultList();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Rubro buscarRubroHoraExtra(int idOrganizacion, PorCientoHoraExtra porCiento)
/* 124:    */   {
/* 125:147 */     StringBuilder sql = new StringBuilder();
/* 126:148 */     sql.append(" SELECT ru FROM HoraExtra he ");
/* 127:149 */     sql.append(" INNER JOIN he.rubro ru ");
/* 128:150 */     sql.append(" WHERE he.idOrganizacion=:idOrganizacion ");
/* 129:151 */     sql.append(" AND he.porCiento = :porCiento");
/* 130:    */     
/* 131:153 */     Query query = this.em.createQuery(sql.toString());
/* 132:    */     
/* 133:155 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 134:156 */     query.setParameter("porCiento", porCiento);
/* 135:157 */     query.setMaxResults(1);
/* 136:    */     
/* 137:159 */     List<Rubro> lista = query.getResultList();
/* 138:160 */     if (lista.size() > 0) {
/* 139:161 */       return (Rubro)lista.get(0);
/* 140:    */     }
/* 141:163 */     return null;
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.nomina.asistencia.HoraExtraDao
 * JD-Core Version:    0.7.0.1
 */