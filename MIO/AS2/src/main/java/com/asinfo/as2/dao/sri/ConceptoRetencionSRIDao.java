/*   1:    */ package com.asinfo.as2.dao.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.Organizacion;
/*   5:    */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*   6:    */ import com.asinfo.as2.enumeraciones.TipoConceptoRetencion;
/*   7:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import java.math.BigDecimal;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ import javax.persistence.EntityManager;
/*  16:    */ import javax.persistence.NoResultException;
/*  17:    */ import javax.persistence.NonUniqueResultException;
/*  18:    */ import javax.persistence.Query;
/*  19:    */ import javax.persistence.TypedQuery;
/*  20:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  21:    */ import javax.persistence.criteria.CriteriaQuery;
/*  22:    */ import javax.persistence.criteria.Expression;
/*  23:    */ import javax.persistence.criteria.JoinType;
/*  24:    */ import javax.persistence.criteria.Predicate;
/*  25:    */ import javax.persistence.criteria.Root;
/*  26:    */ 
/*  27:    */ @Stateless
/*  28:    */ public class ConceptoRetencionSRIDao
/*  29:    */   extends AbstractDaoAS2<ConceptoRetencionSRI>
/*  30:    */ {
/*  31:    */   public ConceptoRetencionSRIDao()
/*  32:    */   {
/*  33: 50 */     super(ConceptoRetencionSRI.class);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public List<ConceptoRetencionSRI> getConceptoListaRetencionPorFecha(Date fecha)
/*  37:    */     throws ExcepcionAS2Financiero
/*  38:    */   {
/*  39: 62 */     List<ConceptoRetencionSRI> listaConceptoRetencionSRI = new ArrayList();
/*  40:    */     try
/*  41:    */     {
/*  42: 65 */       StringBuilder sql = new StringBuilder();
/*  43: 66 */       sql.append(" SELECT co FROM ConceptoRetencionSRI co ");
/*  44: 67 */       sql.append(" LEFT JOIN FETCH co.cuentaContable cc ");
/*  45: 68 */       sql.append(" WHERE (:fecha BETWEEN co.fechaDesde AND co.fechaHasta) OR (co.fechaDesde<=:fecha AND co.fechaHasta IS NULL) ");
/*  46: 69 */       sql.append(" AND co.idOrganizacion=:idOrganizacion ");
/*  47:    */       
/*  48: 71 */       Query query = this.em.createQuery(sql.toString());
/*  49: 72 */       query.setParameter("fecha", fecha);
/*  50: 73 */       query.setParameter("idOrganizacion", Integer.valueOf(AppUtil.getOrganizacion().getId()));
/*  51:    */       
/*  52: 75 */       listaConceptoRetencionSRI = query.getResultList();
/*  53:    */     }
/*  54:    */     catch (NoResultException e)
/*  55:    */     {
/*  56: 78 */       throw new ExcepcionAS2Financiero("", "No existen conceptos de retencion en la fecha dada", e);
/*  57:    */     }
/*  58: 81 */     return listaConceptoRetencionSRI;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public List<ConceptoRetencionSRI> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  62:    */   {
/*  63: 91 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  64: 92 */     CriteriaQuery<ConceptoRetencionSRI> criteriaQuery = criteriaBuilder.createQuery(ConceptoRetencionSRI.class);
/*  65: 93 */     Root<ConceptoRetencionSRI> from = criteriaQuery.from(ConceptoRetencionSRI.class);
/*  66:    */     
/*  67: 95 */     from.fetch("cuentaContable", JoinType.LEFT);
/*  68: 96 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  69:    */     
/*  70: 98 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  71: 99 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  72:    */     
/*  73:101 */     CriteriaQuery<ConceptoRetencionSRI> select = criteriaQuery.select(from);
/*  74:    */     
/*  75:103 */     TypedQuery<ConceptoRetencionSRI> typedQuery = this.em.createQuery(select);
/*  76:    */     
/*  77:105 */     return typedQuery.getResultList();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public List<ConceptoRetencionSRI> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  81:    */   {
/*  82:116 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  83:117 */     CriteriaQuery<ConceptoRetencionSRI> criteriaQuery = criteriaBuilder.createQuery(ConceptoRetencionSRI.class);
/*  84:118 */     Root<ConceptoRetencionSRI> from = criteriaQuery.from(ConceptoRetencionSRI.class);
/*  85:    */     
/*  86:120 */     from.fetch("cuentaContable", JoinType.LEFT);
/*  87:    */     
/*  88:122 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  89:    */     
/*  90:124 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  91:125 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  92:    */     
/*  93:127 */     CriteriaQuery<ConceptoRetencionSRI> select = criteriaQuery.select(from);
/*  94:    */     
/*  95:129 */     TypedQuery<ConceptoRetencionSRI> typedQuery = this.em.createQuery(select);
/*  96:130 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  97:    */     
/*  98:132 */     return typedQuery.getResultList();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public ConceptoRetencionSRI getConceptoRetencionPorIdYFecha(int idConceptoRetencionSRI, Date fecha)
/* 102:    */     throws ExcepcionAS2Financiero
/* 103:    */   {
/* 104:145 */     ConceptoRetencionSRI conceptoRetencionSRI = new ConceptoRetencionSRI();
/* 105:    */     try
/* 106:    */     {
/* 107:148 */       StringBuilder sql = new StringBuilder();
/* 108:149 */       sql.append(" SELECT co FROM ConceptoRetencionSRI co ");
/* 109:150 */       sql.append(" LEFT JOIN FETCH co.cuentaContable cc ");
/* 110:151 */       sql.append(" WHERE co.idConceptoRetencionSRI = :idConceptoRetencionSRI ");
/* 111:152 */       sql.append(" AND co.idOrganizacion = :idOrganizacion ");
/* 112:153 */       sql.append(" AND ((:fecha BETWEEN co.fechaDesde AND co.fechaHasta) OR (co.fechaDesde<=:fecha AND co.fechaHasta IS NULL)) ");
/* 113:    */       
/* 114:155 */       Query query = this.em.createQuery(sql.toString());
/* 115:156 */       query.setParameter("fecha", fecha);
/* 116:157 */       query.setParameter("idConceptoRetencionSRI", Integer.valueOf(idConceptoRetencionSRI));
/* 117:158 */       query.setParameter("idOrganizacion", Integer.valueOf(AppUtil.getOrganizacion().getId()));
/* 118:    */       
/* 119:160 */       conceptoRetencionSRI = (ConceptoRetencionSRI)query.getSingleResult();
/* 120:    */     }
/* 121:    */     catch (NonUniqueResultException e)
/* 122:    */     {
/* 123:162 */       throw new ExcepcionAS2Financiero("Existe mas de un concepto de retencion en el periodo", e);
/* 124:    */     }
/* 125:    */     catch (NoResultException e)
/* 126:    */     {
/* 127:164 */       throw new ExcepcionAS2Financiero("No existe el concepto de retencion en la fecha dada", e);
/* 128:    */     }
/* 129:167 */     return conceptoRetencionSRI;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public List<ConceptoRetencionSRI> conceptoRetencionIVAPorcentaje(int idOrganizacion)
/* 133:    */   {
/* 134:173 */     StringBuilder sql = new StringBuilder();
/* 135:174 */     sql.append(" SELECT co FROM ConceptoRetencionSRI co ");
/* 136:175 */     sql.append(" WHERE co.idOrganizacion =:idOrganizacion ");
/* 137:176 */     sql.append(" AND co.tipoConceptoRetencion =:tipoConceptoRetencion ");
/* 138:    */     
/* 139:178 */     Query query = this.em.createQuery(sql.toString());
/* 140:179 */     query.setParameter("tipoConceptoRetencion", TipoConceptoRetencion.IVA);
/* 141:180 */     query.setParameter("idOrganizacion", Integer.valueOf(AppUtil.getOrganizacion().getId()));
/* 142:    */     
/* 143:182 */     return query.getResultList();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public ConceptoRetencionSRI conceptoRetencionPorcentajeYCodigo(BigDecimal porcentajeRetencion, String codigoConcepto, int idOrganizacion)
/* 147:    */   {
/* 148:187 */     StringBuilder sql = new StringBuilder();
/* 149:188 */     sql.append(" SELECT co FROM ConceptoRetencionSRI co ");
/* 150:189 */     sql.append(" WHERE co.idOrganizacion = :idOrganizacion ");
/* 151:190 */     sql.append(" AND co.codigo = :codigo ");
/* 152:191 */     sql.append(" AND co.porcentaje = :porcentaje ");
/* 153:    */     
/* 154:193 */     Query query = this.em.createQuery(sql.toString());
/* 155:194 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 156:195 */     query.setParameter("codigo", codigoConcepto);
/* 157:196 */     query.setParameter("porcentaje", porcentajeRetencion);
/* 158:197 */     return (ConceptoRetencionSRI)query.getSingleResult();
/* 159:    */   }
/* 160:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.sri.ConceptoRetencionSRIDao
 * JD-Core Version:    0.7.0.1
 */