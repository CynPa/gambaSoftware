/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetallePedidoCliente;
/*   4:    */ import com.asinfo.as2.entities.ImpuestoProductoPedidoCliente;
/*   5:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.ejb.Stateless;
/*   8:    */ import javax.persistence.EntityManager;
/*   9:    */ import javax.persistence.Query;
/*  10:    */ import javax.persistence.TypedQuery;
/*  11:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  12:    */ import javax.persistence.criteria.CriteriaQuery;
/*  13:    */ import javax.persistence.criteria.Fetch;
/*  14:    */ import javax.persistence.criteria.Join;
/*  15:    */ import javax.persistence.criteria.JoinType;
/*  16:    */ import javax.persistence.criteria.Path;
/*  17:    */ import javax.persistence.criteria.Root;
/*  18:    */ 
/*  19:    */ @Stateless
/*  20:    */ public class DetallePedidoClienteDao
/*  21:    */   extends AbstractDaoAS2<DetallePedidoCliente>
/*  22:    */ {
/*  23:    */   public DetallePedidoClienteDao()
/*  24:    */   {
/*  25: 31 */     super(DetallePedidoCliente.class);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public List<DetallePedidoCliente> buscarDetallePedidoClientePorProducto(int idProducto, int idOrganizacion)
/*  29:    */   {
/*  30: 36 */     StringBuffer sql = new StringBuffer();
/*  31: 37 */     sql.append(" SELECT dpc");
/*  32: 38 */     sql.append(" FROM DetallePedidoCliente dpc");
/*  33: 39 */     sql.append(" INNER JOIN FETCH dpc.producto prod");
/*  34: 40 */     sql.append(" INNER JOIN FETCH dpc.pedidoCliente pc");
/*  35: 41 */     sql.append(" INNER JOIN FETCH pc.empresa em");
/*  36: 42 */     sql.append(" WHERE prod.idProducto = :idProducto");
/*  37: 43 */     sql.append(" AND pc.idOrganizacion = :idOrganizacion");
/*  38: 44 */     sql.append(" AND (dpc.cantidad - dpc.cantidadEnviadaAProducir) > 0");
/*  39: 45 */     sql.append(" AND pc.estado = :estado");
/*  40:    */     
/*  41: 47 */     Query query = this.em.createQuery(sql.toString());
/*  42: 48 */     query.setParameter("idProducto", Integer.valueOf(idProducto));
/*  43: 49 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  44: 50 */     query.setParameter("estado", Estado.PROCESADO);
/*  45:    */     
/*  46: 52 */     return query.getResultList();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public DetallePedidoCliente cargarDetalle(int idDetallePedidoCliente)
/*  50:    */   {
/*  51: 57 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  52:    */     
/*  53:    */ 
/*  54: 60 */     CriteriaQuery<DetallePedidoCliente> cqDetalle = criteriaBuilder.createQuery(DetallePedidoCliente.class);
/*  55: 61 */     Root<DetallePedidoCliente> fromDetalle = cqDetalle.from(DetallePedidoCliente.class);
/*  56: 62 */     fromDetalle.fetch("unidadVenta", JoinType.LEFT);
/*  57: 63 */     Fetch<Object, Object> producto = fromDetalle.fetch("producto", JoinType.INNER);
/*  58: 64 */     producto.fetch("unidad", JoinType.LEFT);
/*  59:    */     
/*  60:    */ 
/*  61: 67 */     Fetch<Object, Object> fromCabecera = fromDetalle.fetch("pedidoCliente", JoinType.INNER);
/*  62: 68 */     Fetch<Object, Object> documento = fromCabecera.fetch("documento", JoinType.LEFT);
/*  63: 69 */     documento.fetch("secuencia", JoinType.LEFT);
/*  64: 70 */     fromCabecera.fetch("condicionPago", JoinType.LEFT);
/*  65: 71 */     fromCabecera.fetch("sucursal", JoinType.LEFT);
/*  66: 72 */     fromCabecera.fetch("bodega", JoinType.LEFT);
/*  67: 73 */     fromCabecera.fetch("transportista", JoinType.LEFT);
/*  68: 74 */     fromCabecera.fetch("subempresa", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*  69: 75 */     fromCabecera.fetch("contacto", JoinType.LEFT);
/*  70:    */     
/*  71: 77 */     Fetch<Object, Object> empresa = fromCabecera.fetch("empresa", JoinType.LEFT);
/*  72: 78 */     empresa.fetch("cliente", JoinType.LEFT);
/*  73:    */     
/*  74: 80 */     fromCabecera.fetch("agenteComercial", JoinType.LEFT);
/*  75: 81 */     fromCabecera.fetch("canal", JoinType.LEFT);
/*  76: 82 */     fromCabecera.fetch("zona", JoinType.LEFT);
/*  77: 83 */     fromCabecera.fetch("motivoPedidoCliente", JoinType.LEFT);
/*  78: 84 */     fromCabecera.fetch("proyecto", JoinType.LEFT);
/*  79:    */     
/*  80: 86 */     Fetch<Object, Object> direccion = fromCabecera.fetch("direccionEmpresa", JoinType.LEFT);
/*  81: 87 */     direccion.fetch("ubicacion", JoinType.LEFT);
/*  82:    */     
/*  83: 89 */     Path<Integer> pathIdDetalle = fromDetalle.get("idDetallePedidoCliente");
/*  84: 90 */     cqDetalle.where(criteriaBuilder.equal(pathIdDetalle, Integer.valueOf(idDetallePedidoCliente)));
/*  85: 91 */     CriteriaQuery<DetallePedidoCliente> selectDetallePedido = cqDetalle.select(fromDetalle);
/*  86:    */     
/*  87: 93 */     DetallePedidoCliente detallePedidoCliente = (DetallePedidoCliente)this.em.createQuery(selectDetallePedido).getSingleResult();
/*  88:    */     
/*  89:    */ 
/*  90: 96 */     CriteriaQuery<ImpuestoProductoPedidoCliente> cqImpuesto = criteriaBuilder.createQuery(ImpuestoProductoPedidoCliente.class);
/*  91: 97 */     Root<ImpuestoProductoPedidoCliente> fromImpuesto = cqImpuesto.from(ImpuestoProductoPedidoCliente.class);
/*  92:    */     
/*  93: 99 */     fromImpuesto.fetch("impuesto", JoinType.LEFT);
/*  94:    */     
/*  95:101 */     Path<Integer> pathIdImpuesto = fromImpuesto.join("detallePedidoCliente").get("idDetallePedidoCliente");
/*  96:102 */     cqImpuesto.where(criteriaBuilder.equal(pathIdImpuesto, Integer.valueOf(idDetallePedidoCliente)));
/*  97:    */     
/*  98:104 */     CriteriaQuery<ImpuestoProductoPedidoCliente> selectImpuesto = cqImpuesto.select(fromImpuesto);
/*  99:    */     
/* 100:106 */     List<ImpuestoProductoPedidoCliente> listaImpuestoProductoPedidoCliente = this.em.createQuery(selectImpuesto).getResultList();
/* 101:    */     
/* 102:108 */     detallePedidoCliente.setListaImpuestoProductoPedidoCliente(listaImpuestoProductoPedidoCliente);
/* 103:    */     
/* 104:110 */     return detallePedidoCliente;
/* 105:    */   }
/* 106:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetallePedidoClienteDao
 * JD-Core Version:    0.7.0.1
 */