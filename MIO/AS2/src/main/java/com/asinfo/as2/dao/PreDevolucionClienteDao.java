/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetallePreDevolucionCliente;
/*   4:    */ import com.asinfo.as2.entities.PreDevolucionCliente;
/*   5:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ import javax.persistence.EntityManager;
/*  11:    */ import javax.persistence.Query;
/*  12:    */ import javax.persistence.TemporalType;
/*  13:    */ import javax.persistence.TypedQuery;
/*  14:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  15:    */ import javax.persistence.criteria.CriteriaQuery;
/*  16:    */ import javax.persistence.criteria.Fetch;
/*  17:    */ import javax.persistence.criteria.JoinType;
/*  18:    */ import javax.persistence.criteria.Root;
/*  19:    */ 
/*  20:    */ @Stateless
/*  21:    */ public class PreDevolucionClienteDao
/*  22:    */   extends AbstractDaoAS2<PreDevolucionCliente>
/*  23:    */ {
/*  24:    */   public PreDevolucionClienteDao()
/*  25:    */   {
/*  26: 41 */     super(PreDevolucionCliente.class);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public List<PreDevolucionCliente> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  30:    */   {
/*  31: 48 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  32: 49 */     CriteriaQuery<PreDevolucionCliente> criteriaQuery = criteriaBuilder.createQuery(PreDevolucionCliente.class);
/*  33: 50 */     Root<PreDevolucionCliente> from = criteriaQuery.from(PreDevolucionCliente.class);
/*  34: 51 */     from.fetch("transportista", JoinType.LEFT);
/*  35: 52 */     from.fetch("motivoNotaCreditoCliente", JoinType.LEFT);
/*  36: 53 */     from.fetch("sucursal", JoinType.LEFT);
/*  37: 54 */     from.fetch("empresa", JoinType.LEFT);
/*  38: 55 */     from.fetch("subempresa", JoinType.LEFT);
/*  39:    */     
/*  40: 57 */     agregarFiltros(filters, criteriaBuilder, criteriaQuery, from);
/*  41: 58 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  42:    */     
/*  43: 60 */     CriteriaQuery<PreDevolucionCliente> select = criteriaQuery.select(from);
/*  44: 61 */     TypedQuery<PreDevolucionCliente> typedQuery = this.em.createQuery(select);
/*  45: 62 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  46:    */     
/*  47: 64 */     return typedQuery.getResultList();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public PreDevolucionCliente cargarDetalle(int idPreDevolucionCliente)
/*  51:    */   {
/*  52: 69 */     CriteriaBuilder cbP = this.em.getCriteriaBuilder();
/*  53: 70 */     CriteriaQuery<PreDevolucionCliente> cqP = cbP.createQuery(PreDevolucionCliente.class);
/*  54:    */     
/*  55: 72 */     Root<PreDevolucionCliente> fromP = cqP.from(PreDevolucionCliente.class);
/*  56:    */     
/*  57: 74 */     fromP.fetch("empresa", JoinType.LEFT);
/*  58: 75 */     fromP.fetch("empresa", JoinType.LEFT).fetch("tipoIdentificacion", JoinType.LEFT);
/*  59: 76 */     fromP.fetch("subempresa", JoinType.LEFT);
/*  60: 77 */     fromP.fetch("subempresa", JoinType.LEFT).fetch("empresa", JoinType.LEFT);
/*  61: 78 */     fromP.fetch("sucursal", JoinType.LEFT);
/*  62: 79 */     fromP.fetch("transportista", JoinType.LEFT);
/*  63: 80 */     fromP.fetch("motivoNotaCreditoCliente", JoinType.LEFT);
/*  64: 81 */     cqP.where(cbP.equal(fromP.get("idPreDevolucionCliente"), Integer.valueOf(idPreDevolucionCliente)));
/*  65: 82 */     PreDevolucionCliente preDevolucionClienteDB = (PreDevolucionCliente)this.em.createQuery(cqP.select(fromP)).getSingleResult();
/*  66:    */     
/*  67: 84 */     this.em.detach(preDevolucionClienteDB);
/*  68: 85 */     CriteriaQuery<DetallePreDevolucionCliente> cqDetallePreDevolucionCliente = cbP.createQuery(DetallePreDevolucionCliente.class);
/*  69: 86 */     Root<DetallePreDevolucionCliente> detallePre = cqDetallePreDevolucionCliente.from(DetallePreDevolucionCliente.class);
/*  70: 87 */     detallePre.fetch("bodega", JoinType.LEFT);
/*  71: 88 */     detallePre.fetch("lote", JoinType.LEFT);
/*  72: 89 */     detallePre.fetch("unidad", JoinType.LEFT);
/*  73: 90 */     detallePre.fetch("producto", JoinType.LEFT);
/*  74: 91 */     cqDetallePreDevolucionCliente.where(cbP.equal(detallePre.get("preDevolucionCliente"), preDevolucionClienteDB));
/*  75: 92 */     preDevolucionClienteDB.setListaDetallePreDevolucionCliente(this.em.createQuery(cqDetallePreDevolucionCliente).getResultList());
/*  76: 93 */     return preDevolucionClienteDB;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public List<PreDevolucionCliente> buscarPreDevolucionesPorTransportista(int idOrganizacion, boolean productoBueno, int idUsuario, Date fecha)
/*  80:    */   {
/*  81: 99 */     StringBuilder sql = new StringBuilder();
/*  82:100 */     sql.append("SELECT pdc");
/*  83:101 */     sql.append(" FROM PreDevolucionCliente pdc");
/*  84:102 */     sql.append(" INNER JOIN pdc.transportista t");
/*  85:103 */     sql.append(" INNER JOIN pdc.motivoNotaCreditoCliente mncc");
/*  86:104 */     sql.append(" WHERE pdc.idOrganizacion = :idOrganizacion");
/*  87:105 */     sql.append(" AND t.idUsuario=:idUsuario");
/*  88:106 */     sql.append(" AND mncc.indicadorReversaTransformacion=:productoBueno");
/*  89:107 */     sql.append(" AND pdc.estado=:estado");
/*  90:108 */     sql.append(" AND pdc.fecha=:fecha");
/*  91:109 */     Query query = this.em.createQuery(sql.toString());
/*  92:110 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  93:111 */     query.setParameter("productoBueno", Boolean.valueOf(productoBueno));
/*  94:112 */     query.setParameter("idUsuario", Integer.valueOf(idUsuario));
/*  95:113 */     query.setParameter("estado", Estado.ELABORADO);
/*  96:114 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/*  97:115 */     return query.getResultList();
/*  98:    */   }
/*  99:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PreDevolucionClienteDao
 * JD-Core Version:    0.7.0.1
 */