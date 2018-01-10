/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   4:    */ import com.asinfo.as2.entities.Sucursal;
/*   5:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.ejb.Stateless;
/*   9:    */ import javax.persistence.EntityManager;
/*  10:    */ import javax.persistence.NoResultException;
/*  11:    */ import javax.persistence.Query;
/*  12:    */ import javax.persistence.TypedQuery;
/*  13:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  14:    */ import javax.persistence.criteria.CriteriaQuery;
/*  15:    */ import javax.persistence.criteria.Expression;
/*  16:    */ import javax.persistence.criteria.JoinType;
/*  17:    */ import javax.persistence.criteria.Predicate;
/*  18:    */ import javax.persistence.criteria.Root;
/*  19:    */ 
/*  20:    */ @Stateless
/*  21:    */ public class PuntoDeVentaDao
/*  22:    */   extends AbstractDaoAS2<PuntoDeVenta>
/*  23:    */ {
/*  24:    */   public PuntoDeVentaDao()
/*  25:    */   {
/*  26: 45 */     super(PuntoDeVenta.class);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public PuntoDeVenta buscarPorId(Integer idPuntoDeVenta)
/*  30:    */   {
/*  31: 56 */     Query query = this.em.createQuery("SELECT p FROM PuntoDeVenta p left join fetch p.sucursal WHERE p.idPuntoDeVenta=:idPuntoDeVenta");
/*  32: 57 */     query.setParameter("idPuntoDeVenta", idPuntoDeVenta);
/*  33:    */     
/*  34: 59 */     return (PuntoDeVenta)query.getSingleResult();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public List<PuntoDeVenta> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  38:    */   {
/*  39: 64 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  40: 65 */     CriteriaQuery<PuntoDeVenta> criteriaQuery = criteriaBuilder.createQuery(PuntoDeVenta.class);
/*  41: 66 */     Root<PuntoDeVenta> from = criteriaQuery.from(PuntoDeVenta.class);
/*  42:    */     
/*  43: 68 */     from.fetch("sucursal", JoinType.LEFT);
/*  44:    */     
/*  45: 70 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  46: 71 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  47: 72 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  48:    */     
/*  49: 74 */     CriteriaQuery<PuntoDeVenta> select = criteriaQuery.select(from);
/*  50:    */     
/*  51: 76 */     TypedQuery<PuntoDeVenta> typedQuery = this.em.createQuery(select);
/*  52: 77 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  53:    */     
/*  54: 79 */     return typedQuery.getResultList();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public List<PuntoDeVenta> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  58:    */   {
/*  59: 85 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  60: 86 */     CriteriaQuery<PuntoDeVenta> criteriaQuery = criteriaBuilder.createQuery(PuntoDeVenta.class);
/*  61: 87 */     Root<PuntoDeVenta> from = criteriaQuery.from(PuntoDeVenta.class);
/*  62:    */     
/*  63: 89 */     from.fetch("sucursal", JoinType.LEFT);
/*  64:    */     
/*  65: 91 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  66: 92 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  67: 93 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  68:    */     
/*  69: 95 */     CriteriaQuery<PuntoDeVenta> select = criteriaQuery.select(from);
/*  70:    */     
/*  71: 97 */     TypedQuery<PuntoDeVenta> typedQuery = this.em.createQuery(select);
/*  72:    */     
/*  73: 99 */     return typedQuery.getResultList();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public PuntoDeVenta cargarDetalle(int id)
/*  77:    */   {
/*  78:104 */     PuntoDeVenta puntoDeVenta = buscarPorId(Integer.valueOf(id));
/*  79:    */     
/*  80:106 */     puntoDeVenta.getSucursal().getId();
/*  81:    */     
/*  82:    */ 
/*  83:109 */     puntoDeVenta.getListaCaja().size();
/*  84:    */     
/*  85:111 */     return puntoDeVenta;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public PuntoDeVenta buscarPuntoDeVenta(Map<String, String> filters)
/*  89:    */     throws ExcepcionAS2
/*  90:    */   {
/*  91:    */     try
/*  92:    */     {
/*  93:118 */       CriteriaBuilder cb = this.em.getCriteriaBuilder();
/*  94:119 */       CriteriaQuery<PuntoDeVenta> cq = cb.createQuery(PuntoDeVenta.class);
/*  95:120 */       Root<PuntoDeVenta> from = cq.from(PuntoDeVenta.class);
/*  96:    */       
/*  97:122 */       List<Expression<?>> empresiones = obtenerExpresiones(filters, cb, from);
/*  98:123 */       cq.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  99:    */       
/* 100:125 */       CriteriaQuery<PuntoDeVenta> select = cq.select(from);
/* 101:126 */       return cargarDetalle(((PuntoDeVenta)this.em.createQuery(select).getSingleResult()).getId());
/* 102:    */     }
/* 103:    */     catch (NoResultException e)
/* 104:    */     {
/* 105:129 */       String mensaje = filters.get("nombre") != null ? (String)filters.get("nombre") : (String)filters.get("codigoAlterno");
/* 106:130 */       throw new ExcepcionAS2("msg_info_punto_venta_no_encontrado", " " + mensaje);
/* 107:    */     }
/* 108:    */   }
/* 109:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PuntoDeVentaDao
 * JD-Core Version:    0.7.0.1
 */