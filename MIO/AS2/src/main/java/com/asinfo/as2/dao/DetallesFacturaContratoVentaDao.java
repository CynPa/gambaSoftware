/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.ContratoVenta;
/*   4:    */ import com.asinfo.as2.entities.ContratoVentaFacturaContratoVenta;
/*   5:    */ import com.asinfo.as2.entities.DetalleContratoVenta;
/*   6:    */ import com.asinfo.as2.entities.DetallesFacturaContratoVenta;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.Stateless;
/*  11:    */ import javax.persistence.EntityManager;
/*  12:    */ import javax.persistence.TypedQuery;
/*  13:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  14:    */ import javax.persistence.criteria.CriteriaQuery;
/*  15:    */ import javax.persistence.criteria.Expression;
/*  16:    */ import javax.persistence.criteria.Fetch;
/*  17:    */ import javax.persistence.criteria.JoinType;
/*  18:    */ import javax.persistence.criteria.Path;
/*  19:    */ import javax.persistence.criteria.Predicate;
/*  20:    */ import javax.persistence.criteria.Root;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class DetallesFacturaContratoVentaDao
/*  24:    */   extends AbstractDaoAS2<DetallesFacturaContratoVenta>
/*  25:    */ {
/*  26:    */   public DetallesFacturaContratoVentaDao()
/*  27:    */   {
/*  28: 41 */     super(DetallesFacturaContratoVenta.class);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public List<DetallesFacturaContratoVenta> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  32:    */   {
/*  33: 51 */     List<DetallesFacturaContratoVenta> listaDetallesFacturaContratoVenta = new ArrayList();
/*  34:    */     
/*  35: 53 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  36: 54 */     CriteriaQuery<DetallesFacturaContratoVenta> criteriaQuery = criteriaBuilder.createQuery(DetallesFacturaContratoVenta.class);
/*  37: 55 */     Root<DetallesFacturaContratoVenta> from = criteriaQuery.from(DetallesFacturaContratoVenta.class);
/*  38:    */     
/*  39: 57 */     Fetch<Object, Object> contratoVenta = from.fetch("contratoVenta", JoinType.LEFT);
/*  40: 58 */     Fetch<Object, Object> empresa = contratoVenta.fetch("empresa", JoinType.LEFT);
/*  41: 59 */     Fetch<Object, Object> cliente = empresa.fetch("cliente", JoinType.LEFT);
/*  42: 60 */     contratoVenta.fetch("agenteComercial", JoinType.LEFT);
/*  43: 61 */     contratoVenta.fetch("subempresa", JoinType.LEFT);
/*  44: 62 */     Fetch<Object, Object> direccionEmpresa = contratoVenta.fetch("direccionEmpresa", JoinType.LEFT);
/*  45: 63 */     direccionEmpresa.fetch("ubicacion", JoinType.LEFT);
/*  46: 64 */     contratoVenta.fetch("canal", JoinType.LEFT);
/*  47: 65 */     contratoVenta.fetch("zona", JoinType.LEFT);
/*  48: 66 */     contratoVenta.fetch("condicionPago", JoinType.LEFT);
/*  49:    */     
/*  50: 68 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  51: 69 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  52:    */     
/*  53: 71 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  54:    */     
/*  55: 73 */     CriteriaQuery<DetallesFacturaContratoVenta> select = criteriaQuery.select(from);
/*  56:    */     
/*  57: 75 */     TypedQuery<DetallesFacturaContratoVenta> typedQuery = this.em.createQuery(select);
/*  58: 76 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  59:    */     
/*  60: 78 */     listaDetallesFacturaContratoVenta = typedQuery.getResultList();
/*  61: 81 */     for (DetallesFacturaContratoVenta dfcv : listaDetallesFacturaContratoVenta)
/*  62:    */     {
/*  63: 83 */       CriteriaQuery<DetalleContratoVenta> cqDetalle = criteriaBuilder.createQuery(DetalleContratoVenta.class);
/*  64: 84 */       Root<DetalleContratoVenta> fromDetalle = cqDetalle.from(DetalleContratoVenta.class);
/*  65: 85 */       fromDetalle.fetch("producto", JoinType.LEFT);
/*  66:    */       
/*  67: 87 */       cqDetalle.where(criteriaBuilder.equal(fromDetalle.join("contratoVenta"), dfcv.getContratoVenta()));
/*  68: 88 */       CriteriaQuery<DetalleContratoVenta> selectContratoVenta = cqDetalle.select(fromDetalle);
/*  69:    */       
/*  70: 90 */       List<DetalleContratoVenta> listaDetalleContratoVenta = this.em.createQuery(selectContratoVenta).getResultList();
/*  71: 91 */       dfcv.getContratoVenta().setListaDetalleContratoVenta(listaDetalleContratoVenta);
/*  72:    */     }
/*  73: 94 */     return listaDetallesFacturaContratoVenta;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public DetallesFacturaContratoVenta cargarDetalle(DetallesFacturaContratoVenta detallesFacturaContratoVenta)
/*  77:    */   {
/*  78:103 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  79:    */     
/*  80:    */ 
/*  81:106 */     CriteriaQuery<DetallesFacturaContratoVenta> cqCabecera = criteriaBuilder.createQuery(DetallesFacturaContratoVenta.class);
/*  82:107 */     Root<DetallesFacturaContratoVenta> fromCabecera = cqCabecera.from(DetallesFacturaContratoVenta.class);
/*  83:    */     
/*  84:109 */     Fetch<Object, Object> contratoVenta = fromCabecera.fetch("contratoVenta", JoinType.LEFT);
/*  85:110 */     Fetch<Object, Object> empresa = contratoVenta.fetch("empresa", JoinType.LEFT);
/*  86:111 */     empresa.fetch("cliente", JoinType.LEFT);
/*  87:    */     
/*  88:113 */     Path<Integer> pathId = fromCabecera.get("idDetallesFacturaContratoVenta");
/*  89:114 */     cqCabecera.where(criteriaBuilder.equal(pathId, Integer.valueOf(detallesFacturaContratoVenta.getIdDetallesFacturaContratoVenta())));
/*  90:115 */     CriteriaQuery<DetallesFacturaContratoVenta> selectDetalleFacturaContratoVenta = cqCabecera.select(fromCabecera);
/*  91:    */     
/*  92:117 */     DetallesFacturaContratoVenta auxDetalleFacturaContratoVenta = (DetallesFacturaContratoVenta)this.em.createQuery(selectDetalleFacturaContratoVenta).getSingleResult();
/*  93:    */     
/*  94:119 */     this.em.detach(auxDetalleFacturaContratoVenta);
/*  95:    */     
/*  96:    */ 
/*  97:122 */     CriteriaQuery<ContratoVentaFacturaContratoVenta> cqDetalleContratoVentaFacturaContratoVenta = criteriaBuilder.createQuery(ContratoVentaFacturaContratoVenta.class);
/*  98:123 */     Root<ContratoVentaFacturaContratoVenta> fromDetalleContratoVentaFacturaContratoVenta = cqDetalleContratoVentaFacturaContratoVenta.from(ContratoVentaFacturaContratoVenta.class);
/*  99:    */     
/* 100:125 */     cqDetalleContratoVentaFacturaContratoVenta.where(criteriaBuilder.equal(fromDetalleContratoVentaFacturaContratoVenta.join("detallesFacturaContratoVenta"), auxDetalleFacturaContratoVenta));
/* 101:126 */     CriteriaQuery<ContratoVentaFacturaContratoVenta> selectContratoVentaFacturaContratoVenta = cqDetalleContratoVentaFacturaContratoVenta.select(fromDetalleContratoVentaFacturaContratoVenta);
/* 102:    */     
/* 103:128 */     List<ContratoVentaFacturaContratoVenta> listaContratoVentaFacturaContratoVenta = this.em.createQuery(selectContratoVentaFacturaContratoVenta).getResultList();
/* 104:129 */     auxDetalleFacturaContratoVenta.setListaContratoVentaFacturaContratoVenta(listaContratoVentaFacturaContratoVenta);
/* 105:    */     
/* 106:    */ 
/* 107:132 */     return auxDetalleFacturaContratoVenta;
/* 108:    */   }
/* 109:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetallesFacturaContratoVentaDao
 * JD-Core Version:    0.7.0.1
 */