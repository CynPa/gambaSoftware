/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   4:    */ import com.asinfo.as2.entities.ImpuestoProductoFacturaCliente;
/*   5:    */ import java.math.BigDecimal;
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
/*  20:    */ public class DetalleFacturaClienteDao
/*  21:    */   extends AbstractDaoAS2<DetalleFacturaCliente>
/*  22:    */ {
/*  23:    */   public DetalleFacturaClienteDao()
/*  24:    */   {
/*  25: 60 */     super(DetalleFacturaCliente.class);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public DetalleFacturaCliente cargarDetalle(int idDetalleFacturaCliente)
/*  29:    */   {
/*  30: 64 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  31:    */     
/*  32: 66 */     CriteriaQuery<DetalleFacturaCliente> cqDetalle = criteriaBuilder.createQuery(DetalleFacturaCliente.class);
/*  33: 67 */     Root<DetalleFacturaCliente> fromDetalle = cqDetalle.from(DetalleFacturaCliente.class);
/*  34: 68 */     fromDetalle.fetch("unidadVenta", JoinType.LEFT);
/*  35: 69 */     fromDetalle.fetch("bodega", JoinType.LEFT);
/*  36: 70 */     Fetch<Object, Object> producto = fromDetalle.fetch("producto", JoinType.LEFT);
/*  37: 71 */     producto.fetch("subcategoriaProducto", JoinType.LEFT);
/*  38: 72 */     producto.fetch("categoriaImpuesto", JoinType.LEFT);
/*  39: 73 */     fromDetalle.fetch("detalleDespachoCliente", JoinType.LEFT).fetch("producto", JoinType.LEFT);
/*  40: 74 */     fromDetalle.fetch("detalleFacturaClientePadre", JoinType.LEFT).fetch("detalleDespachoCliente", JoinType.LEFT);
/*  41:    */     
/*  42: 76 */     Path<Integer> pathId = fromDetalle.get("idDetalleFacturaCliente");
/*  43: 77 */     cqDetalle.where(criteriaBuilder.equal(pathId, Integer.valueOf(idDetalleFacturaCliente)));
/*  44: 78 */     CriteriaQuery<DetalleFacturaCliente> selectDetalleFactura = cqDetalle.select(fromDetalle);
/*  45:    */     
/*  46: 80 */     DetalleFacturaCliente detalleFacturaCliente = (DetalleFacturaCliente)this.em.createQuery(selectDetalleFactura).getSingleResult();
/*  47:    */     
/*  48:    */ 
/*  49: 83 */     this.em.detach(detalleFacturaCliente);
/*  50:    */     
/*  51: 85 */     CriteriaQuery<ImpuestoProductoFacturaCliente> cqImpuesto = criteriaBuilder.createQuery(ImpuestoProductoFacturaCliente.class);
/*  52: 86 */     Root<ImpuestoProductoFacturaCliente> fromImpuesto = cqImpuesto.from(ImpuestoProductoFacturaCliente.class);
/*  53:    */     
/*  54: 88 */     fromImpuesto.fetch("impuesto", JoinType.LEFT);
/*  55: 89 */     Fetch<Object, Object> contratoVentaFacturaContratoVenta = fromDetalle.fetch("contratoVentaFacturaContratoVenta", JoinType.LEFT);
/*  56: 90 */     contratoVentaFacturaContratoVenta.fetch("detallesFacturaContratoVenta", JoinType.LEFT);
/*  57:    */     
/*  58: 92 */     Path<Integer> pathIdImpuesto = fromImpuesto.join("detalleFacturaCliente").get("idDetalleFacturaCliente");
/*  59: 93 */     cqImpuesto.where(criteriaBuilder.equal(pathIdImpuesto, Integer.valueOf(idDetalleFacturaCliente)));
/*  60:    */     
/*  61: 95 */     CriteriaQuery<ImpuestoProductoFacturaCliente> selectImpuesto = cqImpuesto.select(fromImpuesto);
/*  62:    */     
/*  63: 97 */     List<ImpuestoProductoFacturaCliente> listaImpuestoProductoFacturaCliente = this.em.createQuery(selectImpuesto).getResultList();
/*  64:    */     
/*  65: 99 */     detalleFacturaCliente.setListaImpuestoProductoFacturaCliente(listaImpuestoProductoFacturaCliente);
/*  66:    */     
/*  67:101 */     return detalleFacturaCliente;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void actualizarDescuentoLineaNC(int idDFCPadre, BigDecimal descuentoLineaNC)
/*  71:    */   {
/*  72:106 */     String sql = " UPDATE DetalleFacturaCliente dfc  SET dfc.descuentoLineaNC = :valorDescuentoLineaNC \tWHERE dfc.idDetalleFacturaCliente = :detalleFacturaCliente";
/*  73:    */     
/*  74:    */ 
/*  75:109 */     Query query = this.em.createQuery(sql);
/*  76:110 */     query.setParameter("detalleFacturaCliente", Integer.valueOf(idDFCPadre));
/*  77:111 */     query.setParameter("valorDescuentoLineaNC", descuentoLineaNC);
/*  78:112 */     query.executeUpdate();
/*  79:    */   }
/*  80:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetalleFacturaClienteDao
 * JD-Core Version:    0.7.0.1
 */