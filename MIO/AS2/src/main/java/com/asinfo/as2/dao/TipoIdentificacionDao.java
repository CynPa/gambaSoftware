/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.ComprobanteSRICreditoTributarioSRI;
/*   4:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   5:    */ import com.asinfo.as2.entities.TipoIdentificacionComprobanteSRI;
/*   6:    */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*   7:    */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.List;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ import javax.persistence.EntityManager;
/*  13:    */ import javax.persistence.Query;
/*  14:    */ import javax.persistence.TypedQuery;
/*  15:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  16:    */ import javax.persistence.criteria.CriteriaQuery;
/*  17:    */ import javax.persistence.criteria.Expression;
/*  18:    */ import javax.persistence.criteria.JoinType;
/*  19:    */ import javax.persistence.criteria.Predicate;
/*  20:    */ import javax.persistence.criteria.Root;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class TipoIdentificacionDao
/*  24:    */   extends AbstractDaoAS2<TipoIdentificacion>
/*  25:    */ {
/*  26:    */   public TipoIdentificacionDao()
/*  27:    */   {
/*  28: 36 */     super(TipoIdentificacion.class);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public TipoIdentificacion obtenerTipoIdentificacion(HashMap<String, String> filters)
/*  32:    */   {
/*  33: 47 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  34: 48 */     CriteriaQuery<TipoIdentificacion> criteriaQuery = criteriaBuilder.createQuery(TipoIdentificacion.class);
/*  35: 49 */     Root<TipoIdentificacion> from = criteriaQuery.from(TipoIdentificacion.class);
/*  36:    */     
/*  37: 51 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  38: 52 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  39:    */     
/*  40: 54 */     CriteriaQuery<TipoIdentificacion> select = criteriaQuery.select(from);
/*  41:    */     
/*  42: 56 */     TypedQuery<TipoIdentificacion> typedQuery = this.em.createQuery(select);
/*  43:    */     
/*  44: 58 */     return (TipoIdentificacion)typedQuery.getSingleResult();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public TipoIdentificacion devuelvePrimerTipoIdentificacion()
/*  48:    */   {
/*  49: 67 */     String sql = "SELECT t FROM TipoIdentificacion t";
/*  50: 68 */     Query query = this.em.createQuery(sql);
/*  51: 69 */     return (TipoIdentificacion)query.getResultList().get(0);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public TipoIdentificacion buscarPorCodigo(int idOrganizacion, String codigo)
/*  55:    */   {
/*  56:    */     try
/*  57:    */     {
/*  58: 75 */       StringBuilder sql = new StringBuilder();
/*  59: 76 */       sql.append(" SELECT ti FROM TipoIdentificacion ti ");
/*  60: 77 */       sql.append(" WHERE ti.codigo = :codigo ");
/*  61: 78 */       sql.append(" AND ti.idOrganizacion = :idOrganizacion");
/*  62:    */       
/*  63: 80 */       Query query = this.em.createQuery(sql.toString());
/*  64: 81 */       query.setParameter("codigo", codigo);
/*  65: 82 */       query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  66:    */       
/*  67: 84 */       return (TipoIdentificacion)query.getSingleResult();
/*  68:    */     }
/*  69:    */     catch (Exception e) {}
/*  70: 87 */     return null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public TipoIdentificacion cargarDetalle(int idTipoIdentificacion)
/*  74:    */   {
/*  75: 93 */     CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  76: 94 */     CriteriaQuery<TipoIdentificacion> cq = cb.createQuery(TipoIdentificacion.class);
/*  77: 95 */     Root<TipoIdentificacion> from = cq.from(TipoIdentificacion.class);
/*  78: 96 */     cq.where(cb.equal(from.get("idTipoIdentificacion"), Integer.valueOf(idTipoIdentificacion)));
/*  79: 97 */     TipoIdentificacion ti = (TipoIdentificacion)this.em.createQuery(cq.select(from)).getSingleResult();
/*  80:    */     
/*  81: 99 */     CriteriaQuery<TipoIdentificacionComprobanteSRI> cqTICSRI = cb.createQuery(TipoIdentificacionComprobanteSRI.class);
/*  82:100 */     Root<TipoIdentificacionComprobanteSRI> fromTICSRI = cqTICSRI.from(TipoIdentificacionComprobanteSRI.class);
/*  83:101 */     fromTICSRI.fetch("tipoComprobanteSRI", JoinType.LEFT);
/*  84:102 */     cqTICSRI.where(cb.equal(fromTICSRI.get("tipoIdentificacion"), ti));
/*  85:103 */     List<TipoIdentificacionComprobanteSRI> listaTICSRI = this.em.createQuery(cqTICSRI.select(fromTICSRI)).getResultList();
/*  86:104 */     ti.setListaTipoIdentificacionComprobanteSRI(listaTICSRI);
/*  87:106 */     for (TipoIdentificacionComprobanteSRI tcsri : listaTICSRI)
/*  88:    */     {
/*  89:107 */       CriteriaQuery<ComprobanteSRICreditoTributarioSRI> cqCCTSRI = cb.createQuery(ComprobanteSRICreditoTributarioSRI.class);
/*  90:108 */       Root<ComprobanteSRICreditoTributarioSRI> fromCCTSRI = cqCCTSRI.from(ComprobanteSRICreditoTributarioSRI.class);
/*  91:109 */       fromCCTSRI.fetch("creditoTributarioSRI", JoinType.LEFT);
/*  92:    */       
/*  93:111 */       List<Predicate> listaExpresiones = new ArrayList();
/*  94:112 */       listaExpresiones.add(cb.equal(fromCCTSRI.get("tipoComprobanteSRI"), tcsri.getTipoComprobanteSRI()));
/*  95:113 */       listaExpresiones.add(cb.equal(fromCCTSRI.get("tipoIdentificacion"), ti));
/*  96:114 */       cqCCTSRI.where((Predicate[])listaExpresiones.toArray(new Predicate[listaExpresiones.size()]));
/*  97:115 */       List<ComprobanteSRICreditoTributarioSRI> listaCCTSRI = this.em.createQuery(cqCCTSRI.select(fromCCTSRI)).getResultList();
/*  98:116 */       tcsri.getTipoComprobanteSRI().setListaComprobanteSRICreditoTributarioSRI(listaCCTSRI);
/*  99:    */       
/* 100:118 */       List<CreditoTributarioSRI> lctsri = new ArrayList();
/* 101:119 */       for (ComprobanteSRICreditoTributarioSRI cctsri : listaCCTSRI) {
/* 102:120 */         lctsri.add(cctsri.getCreditoTributarioSRI());
/* 103:    */       }
/* 104:122 */       tcsri.getTipoComprobanteSRI().setListaCreditoTributarioSRI(lctsri);
/* 105:    */     }
/* 106:125 */     return ti;
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.TipoIdentificacionDao
 * JD-Core Version:    0.7.0.1
 */