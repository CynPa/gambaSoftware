/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Bodega;
/*   4:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   5:    */ import com.asinfo.as2.entities.DetalleTomaFisica;
/*   6:    */ import com.asinfo.as2.entities.Documento;
/*   7:    */ import com.asinfo.as2.entities.Lote;
/*   8:    */ import com.asinfo.as2.entities.MotivoAjusteInventario;
/*   9:    */ import com.asinfo.as2.entities.Producto;
/*  10:    */ import com.asinfo.as2.entities.Secuencia;
/*  11:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  12:    */ import com.asinfo.as2.entities.TomaFisica;
/*  13:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  14:    */ import java.math.BigDecimal;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.ejb.Stateless;
/*  19:    */ import javax.persistence.EntityManager;
/*  20:    */ import javax.persistence.TypedQuery;
/*  21:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  22:    */ import javax.persistence.criteria.CriteriaQuery;
/*  23:    */ import javax.persistence.criteria.Expression;
/*  24:    */ import javax.persistence.criteria.Join;
/*  25:    */ import javax.persistence.criteria.Path;
/*  26:    */ import javax.persistence.criteria.Predicate;
/*  27:    */ import javax.persistence.criteria.Root;
/*  28:    */ import javax.persistence.criteria.Selection;
/*  29:    */ 
/*  30:    */ @Stateless
/*  31:    */ public class TomaFisicaDao
/*  32:    */   extends AbstractDaoAS2<TomaFisica>
/*  33:    */ {
/*  34:    */   public TomaFisicaDao()
/*  35:    */   {
/*  36: 44 */     super(TomaFisica.class);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public TomaFisica cargarDetalle(int idTomaFisica)
/*  40:    */   {
/*  41: 54 */     TomaFisica tomaFisica = (TomaFisica)buscarPorId(Integer.valueOf(idTomaFisica));
/*  42: 55 */     tomaFisica.getDocumento().getId();
/*  43: 56 */     tomaFisica.getDocumento().getSecuencia().getId();
/*  44: 57 */     tomaFisica.getBodega().getId();
/*  45: 58 */     tomaFisica.getDocumentoAjusteEgreso().getId();
/*  46: 59 */     tomaFisica.getDocumentoAjusteIngreso().getId();
/*  47: 60 */     tomaFisica.getMotivoAjusteInventario().getId();
/*  48: 61 */     tomaFisica.getListaDetalleTomaFisica().size();
/*  49: 62 */     for (DetalleTomaFisica dtf : tomaFisica.getListaDetalleTomaFisica())
/*  50:    */     {
/*  51: 63 */       dtf.getProducto().getId();
/*  52: 64 */       dtf.getProducto().getSubcategoriaProducto().getCategoriaProducto().getId();
/*  53: 65 */       if (dtf.getProducto().isIndicadorLote()) {
/*  54: 66 */         dtf.getLote().getId();
/*  55:    */       }
/*  56: 68 */       BigDecimal diferencia = dtf.getCantidadTomaFisica().subtract(dtf.getCantidadSistema());
/*  57: 69 */       if (diferencia.compareTo(BigDecimal.ZERO) > 0)
/*  58:    */       {
/*  59: 70 */         dtf.setTraMovimientoIngreso(diferencia);
/*  60: 71 */         dtf.setTraMovimientoEgreso(BigDecimal.ZERO);
/*  61:    */       }
/*  62:    */       else
/*  63:    */       {
/*  64: 73 */         dtf.setTraMovimientoEgreso(diferencia.negate());
/*  65: 74 */         dtf.setTraMovimientoIngreso(BigDecimal.ZERO);
/*  66:    */       }
/*  67:    */     }
/*  68: 78 */     return tomaFisica;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public List<TomaFisica> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  72:    */   {
/*  73: 88 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  74: 89 */     CriteriaQuery<TomaFisica> criteriaQuery = criteriaBuilder.createQuery(TomaFisica.class);
/*  75: 90 */     Root<TomaFisica> from = criteriaQuery.from(TomaFisica.class);
/*  76:    */     
/*  77: 92 */     Path<Integer> pathIdTomaFisica = from.get("idTomaFisica");
/*  78: 93 */     Path<String> pathNumero = from.get("numero");
/*  79: 94 */     Path<String> pathDescripcion = from.get("descripcion");
/*  80: 95 */     Path<Date> pathFecha = from.get("fecha");
/*  81: 96 */     Path<String> pathBodega = from.join("bodega").get("nombre");
/*  82: 97 */     Path<Estado> pathEstado = from.get("estado");
/*  83:    */     
/*  84: 99 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  85:    */     
/*  86:101 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  87:102 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  88:    */     
/*  89:    */ 
/*  90:105 */     CriteriaQuery<TomaFisica> select = criteriaQuery.multiselect(new Selection[] { pathIdTomaFisica, pathNumero, pathDescripcion, pathFecha, pathBodega, pathEstado });
/*  91:    */     
/*  92:107 */     TypedQuery<TomaFisica> typedQuery = this.em.createQuery(select);
/*  93:108 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  94:    */     
/*  95:110 */     return typedQuery.getResultList();
/*  96:    */   }
/*  97:    */   
/*  98:    */   public List<TomaFisica> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  99:    */   {
/* 100:120 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 101:121 */     CriteriaQuery<TomaFisica> criteriaQuery = criteriaBuilder.createQuery(TomaFisica.class);
/* 102:122 */     Root<TomaFisica> from = criteriaQuery.from(TomaFisica.class);
/* 103:    */     
/* 104:124 */     Path<Integer> pathIdTomaFisica = from.get("idTomaFisicas");
/* 105:125 */     Path<String> pathNumero = from.get("numero");
/* 106:    */     
/* 107:127 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 108:    */     
/* 109:129 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 110:130 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 111:    */     
/* 112:132 */     CriteriaQuery<TomaFisica> select = criteriaQuery.multiselect(new Selection[] { pathIdTomaFisica, pathNumero });
/* 113:    */     
/* 114:134 */     TypedQuery<TomaFisica> typedQuery = this.em.createQuery(select);
/* 115:    */     
/* 116:136 */     return typedQuery.getResultList();
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.TomaFisicaDao
 * JD-Core Version:    0.7.0.1
 */