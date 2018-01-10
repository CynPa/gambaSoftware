/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetallePedidoProveedor;
/*   4:    */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*   5:    */ import com.asinfo.as2.entities.ImpuestoProductoPedidoProveedor;
/*   6:    */ import com.asinfo.as2.entities.RegistroPeso;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ import javax.persistence.EntityManager;
/*  13:    */ import javax.persistence.Query;
/*  14:    */ import javax.persistence.TypedQuery;
/*  15:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  16:    */ import javax.persistence.criteria.CriteriaQuery;
/*  17:    */ import javax.persistence.criteria.Expression;
/*  18:    */ import javax.persistence.criteria.Fetch;
/*  19:    */ import javax.persistence.criteria.Join;
/*  20:    */ import javax.persistence.criteria.JoinType;
/*  21:    */ import javax.persistence.criteria.Path;
/*  22:    */ import javax.persistence.criteria.Predicate;
/*  23:    */ import javax.persistence.criteria.Root;
/*  24:    */ 
/*  25:    */ @Stateless
/*  26:    */ public class DetallePedidoProveedorDao
/*  27:    */   extends AbstractDaoAS2<DetallePedidoProveedor>
/*  28:    */ {
/*  29:    */   public DetallePedidoProveedorDao()
/*  30:    */   {
/*  31: 31 */     super(DetallePedidoProveedor.class);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public List<DetallePedidoProveedor> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  35:    */   {
/*  36: 42 */     List<DetallePedidoProveedor> listaDetallePedidoProveedor = new ArrayList();
/*  37:    */     
/*  38: 44 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  39: 45 */     CriteriaQuery<DetallePedidoProveedor> criteriaQuery = criteriaBuilder.createQuery(DetallePedidoProveedor.class);
/*  40: 46 */     Root<DetallePedidoProveedor> from = criteriaQuery.from(DetallePedidoProveedor.class);
/*  41:    */     
/*  42: 48 */     from.fetch("producto", JoinType.LEFT);
/*  43: 49 */     Fetch<Object, Object> pedidoProveedor = from.fetch("pedidoProveedor", JoinType.LEFT);
/*  44: 50 */     Fetch<Object, Object> empresa = pedidoProveedor.fetch("empresa", JoinType.LEFT);
/*  45: 51 */     empresa.fetch("proveedor", JoinType.LEFT);
/*  46:    */     
/*  47: 53 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  48:    */     
/*  49:    */ 
/*  50:    */ 
/*  51: 57 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  52: 58 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  53:    */     
/*  54: 60 */     CriteriaQuery<DetallePedidoProveedor> select = criteriaQuery.select(from);
/*  55:    */     
/*  56: 62 */     TypedQuery<DetallePedidoProveedor> typedQuery = this.em.createQuery(select);
/*  57: 63 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  58:    */     
/*  59: 65 */     listaDetallePedidoProveedor = typedQuery.getResultList();
/*  60:    */     
/*  61:    */ 
/*  62: 68 */     CriteriaQuery<DetalleRecepcionProveedor> cqDetalleRecepcionProveedor = criteriaBuilder.createQuery(DetalleRecepcionProveedor.class);
/*  63: 69 */     Root<DetalleRecepcionProveedor> fromDetalleRecepcionProveedor = cqDetalleRecepcionProveedor.from(DetalleRecepcionProveedor.class);
/*  64: 70 */     fromDetalleRecepcionProveedor.fetch("recepcionProveedor", JoinType.LEFT);
/*  65:    */     
/*  66: 72 */     cqDetalleRecepcionProveedor.where(fromDetalleRecepcionProveedor.join("detallePedidoProveedor").in(listaDetallePedidoProveedor));
/*  67:    */     
/*  68: 74 */     CriteriaQuery<DetalleRecepcionProveedor> selectDetalleRecepcionProveedor = cqDetalleRecepcionProveedor.select(fromDetalleRecepcionProveedor);
/*  69: 75 */     TypedQuery<DetalleRecepcionProveedor> typedQuery2 = this.em.createQuery(selectDetalleRecepcionProveedor);
/*  70: 76 */     List<DetalleRecepcionProveedor> listaDetalleRecepcionProveedor = typedQuery2.getResultList();
/*  71:    */     
/*  72: 78 */     Map<Integer, List<DetalleRecepcionProveedor>> hmRecepcionProveedor = new HashMap();
/*  73: 79 */     for (DetalleRecepcionProveedor drp : listaDetalleRecepcionProveedor)
/*  74:    */     {
/*  75: 80 */       List<DetalleRecepcionProveedor> recepcionProveedor = (List)hmRecepcionProveedor.get(Integer.valueOf(drp.getDetallePedidoProveedor().getId()));
/*  76: 81 */       if (recepcionProveedor == null)
/*  77:    */       {
/*  78: 82 */         recepcionProveedor = new ArrayList();
/*  79: 83 */         hmRecepcionProveedor.put(Integer.valueOf(drp.getDetallePedidoProveedor().getId()), recepcionProveedor);
/*  80:    */       }
/*  81: 85 */       recepcionProveedor.add(drp);
/*  82:    */     }
/*  83: 89 */     Object cqRegistroPeso = criteriaBuilder.createQuery(RegistroPeso.class);
/*  84: 90 */     Root<RegistroPeso> fromRegistroPeso = ((CriteriaQuery)cqRegistroPeso).from(RegistroPeso.class);
/*  85:    */     
/*  86: 92 */     ((CriteriaQuery)cqRegistroPeso).where(fromRegistroPeso.join("detallePedidoProveedor").in(listaDetallePedidoProveedor));
/*  87:    */     
/*  88: 94 */     CriteriaQuery<RegistroPeso> selectRegistroPeso = ((CriteriaQuery)cqRegistroPeso).select(fromRegistroPeso);
/*  89: 95 */     TypedQuery<RegistroPeso> typedQuery3 = this.em.createQuery(selectRegistroPeso);
/*  90: 96 */     List<RegistroPeso> listaRegistroPeso = typedQuery3.getResultList();
/*  91:    */     
/*  92: 98 */     Map<Integer, List<RegistroPeso>> hmRegistroPeso = new HashMap();
/*  93: 99 */     for (RegistroPeso rp : listaRegistroPeso)
/*  94:    */     {
/*  95:100 */       List<RegistroPeso> registroPeso = (List)hmRegistroPeso.get(Integer.valueOf(rp.getDetallePedidoProveedor().getId()));
/*  96:101 */       if (registroPeso == null)
/*  97:    */       {
/*  98:102 */         registroPeso = new ArrayList();
/*  99:103 */         hmRegistroPeso.put(Integer.valueOf(rp.getDetallePedidoProveedor().getId()), registroPeso);
/* 100:    */       }
/* 101:105 */       registroPeso.add(rp);
/* 102:    */     }
/* 103:108 */     for (DetallePedidoProveedor detallePedidoProveedor : listaDetallePedidoProveedor)
/* 104:    */     {
/* 105:109 */       List<DetalleRecepcionProveedor> recepcionProveedor = (List)hmRecepcionProveedor.get(Integer.valueOf(detallePedidoProveedor.getId()));
/* 106:110 */       detallePedidoProveedor.setListaDetalleRecepcionProveedor(recepcionProveedor);
/* 107:111 */       List<RegistroPeso> registroPeso = (List)hmRegistroPeso.get(Integer.valueOf(detallePedidoProveedor.getId()));
/* 108:112 */       detallePedidoProveedor.setListaRegistroPeso(registroPeso);
/* 109:    */     }
/* 110:114 */     return listaDetallePedidoProveedor;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public List<DetalleRecepcionProveedor> obtenerDetalleRecepcionProveedor(DetallePedidoProveedor detallePedidoProveedor)
/* 114:    */   {
/* 115:120 */     StringBuilder sql = new StringBuilder();
/* 116:121 */     sql.append(" SELECT drp ");
/* 117:122 */     sql.append(" FROM DetalleRecepcionProveedor drp ");
/* 118:123 */     sql.append(" INNER JOIN drp.detallePedidoProveedor dpp ");
/* 119:124 */     sql.append(" WHERE dpp.idDetallePedidoProveedor = :idDetallePedidoProveedor");
/* 120:    */     
/* 121:126 */     Query query = this.em.createQuery(sql.toString()).setParameter("idDetallePedidoProveedor", Integer.valueOf(detallePedidoProveedor.getId()));
/* 122:127 */     return query.getResultList();
/* 123:    */   }
/* 124:    */   
/* 125:    */   public DetallePedidoProveedor cargarDetalle(int idDetallePedidoProveedor)
/* 126:    */   {
/* 127:131 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 128:    */     
/* 129:133 */     CriteriaQuery<DetallePedidoProveedor> cqDetalle = criteriaBuilder.createQuery(DetallePedidoProveedor.class);
/* 130:134 */     Root<DetallePedidoProveedor> fromDetalle = cqDetalle.from(DetallePedidoProveedor.class);
/* 131:135 */     fromDetalle.fetch("unidadCompra", JoinType.LEFT);
/* 132:    */     
/* 133:137 */     fromDetalle.fetch("producto", JoinType.LEFT).fetch("categoriaImpuesto", JoinType.LEFT);
/* 134:    */     
/* 135:139 */     fromDetalle.fetch("dimensionContable1", JoinType.LEFT);
/* 136:140 */     fromDetalle.fetch("dimensionContable2", JoinType.LEFT);
/* 137:141 */     fromDetalle.fetch("dimensionContable3", JoinType.LEFT);
/* 138:142 */     fromDetalle.fetch("dimensionContable4", JoinType.LEFT);
/* 139:143 */     fromDetalle.fetch("dimensionContable5", JoinType.LEFT);
/* 140:    */     
/* 141:145 */     Path<Integer> pathIdDetalle = fromDetalle.get("idDetallePedidoProveedor");
/* 142:146 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idDetallePedidoProveedor)));
/* 143:147 */     CriteriaQuery<DetallePedidoProveedor> selectDetalle = cqDetalle.select(fromDetalle);
/* 144:    */     
/* 145:149 */     DetallePedidoProveedor detallePedidoProveedor = (DetallePedidoProveedor)this.em.createQuery(selectDetalle).getSingleResult();
/* 146:    */     
/* 147:    */ 
/* 148:152 */     CriteriaQuery<ImpuestoProductoPedidoProveedor> cqImpuesto = criteriaBuilder.createQuery(ImpuestoProductoPedidoProveedor.class);
/* 149:153 */     Root<ImpuestoProductoPedidoProveedor> fromImpuesto = cqImpuesto.from(ImpuestoProductoPedidoProveedor.class);
/* 150:    */     
/* 151:155 */     fromImpuesto.fetch("impuesto", JoinType.LEFT);
/* 152:    */     
/* 153:157 */     Path<Integer> pathIdImpuesto = fromImpuesto.join("detallePedidoProveedor").get("idDetallePedidoProveedor");
/* 154:158 */     cqImpuesto.where(criteriaBuilder.equal(pathIdImpuesto, Integer.valueOf(idDetallePedidoProveedor)));
/* 155:    */     
/* 156:160 */     CriteriaQuery<ImpuestoProductoPedidoProveedor> selectImpuesto = cqImpuesto.select(fromImpuesto);
/* 157:    */     
/* 158:162 */     List<ImpuestoProductoPedidoProveedor> listaImpuestoProductoPedidoProveedor = this.em.createQuery(selectImpuesto).getResultList();
/* 159:    */     
/* 160:164 */     detallePedidoProveedor.setListaImpuestoProductoPedidoProveedor(listaImpuestoProductoPedidoProveedor);
/* 161:    */     
/* 162:166 */     return detallePedidoProveedor;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List<DetallePedidoProveedor> obtenerDetallePedidoProveedor(int idPedidoProveedor)
/* 166:    */   {
/* 167:171 */     StringBuilder sql = new StringBuilder();
/* 168:172 */     sql.append(" SELECT dpp ");
/* 169:173 */     sql.append(" FROM DetallePedidoProveedor dpp ");
/* 170:174 */     sql.append(" INNER JOIN dpp.pedidoProveedor pp ");
/* 171:175 */     sql.append(" WHERE pp.idPedidoProveedor = :idPedidoProveedor");
/* 172:    */     
/* 173:177 */     Query query = this.em.createQuery(sql.toString()).setParameter("idPedidoProveedor", Integer.valueOf(idPedidoProveedor));
/* 174:178 */     return query.getResultList();
/* 175:    */   }
/* 176:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetallePedidoProveedorDao
 * JD-Core Version:    0.7.0.1
 */