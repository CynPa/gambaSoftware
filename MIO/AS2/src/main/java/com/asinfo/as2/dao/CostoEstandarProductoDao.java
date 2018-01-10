/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CostoEstandarProducto;
/*   4:    */ import com.asinfo.as2.entities.Producto;
/*   5:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.ejb.Stateless;
/*   9:    */ import javax.persistence.EntityManager;
/*  10:    */ import javax.persistence.Query;
/*  11:    */ 
/*  12:    */ @Stateless
/*  13:    */ public class CostoEstandarProductoDao
/*  14:    */   extends AbstractDaoAS2<CostoEstandarProducto>
/*  15:    */ {
/*  16:    */   public CostoEstandarProductoDao()
/*  17:    */   {
/*  18: 38 */     super(CostoEstandarProducto.class);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public List<CostoEstandarProducto> obtenerCostoEstandar(Producto producto, Date fechaDesde, Date fechaHasta)
/*  22:    */   {
/*  23: 44 */     int anioDesde = FuncionesUtiles.getAnio(fechaDesde);
/*  24: 45 */     int anioHasta = FuncionesUtiles.getAnio(fechaDesde);
/*  25: 46 */     int mesDesde = FuncionesUtiles.getMes(fechaHasta);
/*  26: 47 */     int mesHasta = FuncionesUtiles.getMes(fechaHasta);
/*  27:    */     
/*  28: 49 */     StringBuilder sql = new StringBuilder();
/*  29: 50 */     sql.append(" SELECT cep FROM CostoEstandarProducto cep ");
/*  30: 51 */     sql.append(" JOIN  FETCH cep.rangoCostoEstandarProducto rcep ");
/*  31: 52 */     sql.append(" INNER JOIN  cep.producto p ");
/*  32: 53 */     sql.append(" WHERE rcep.anio BETWEEN :anioDesde AND :anioHasta ");
/*  33: 54 */     sql.append(" AND   rcep.mes  BETWEEN :mesDesde  AND :mesHasta  ");
/*  34: 55 */     sql.append(" AND   p = :producto ");
/*  35:    */     
/*  36: 57 */     Query query = this.em.createQuery(sql.toString());
/*  37: 58 */     query.setParameter("anioDesde", Integer.valueOf(anioDesde));
/*  38: 59 */     query.setParameter("anioHasta", Integer.valueOf(anioHasta));
/*  39: 60 */     query.setParameter("mesDesde", Integer.valueOf(mesDesde));
/*  40: 61 */     query.setParameter("mesHasta", Integer.valueOf(mesHasta));
/*  41: 62 */     query.setParameter("producto", producto);
/*  42:    */     
/*  43: 64 */     return query.getResultList();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int eliminarCostoProductoNoInventarioProducto(int idOrganizacion, Date fechaDesde, Date fechaHasta, boolean costeoPorBodega)
/*  47:    */   {
/*  48: 69 */     StringBuilder sql = new StringBuilder();
/*  49: 70 */     sql.append(" DELETE FROM CostoProducto cp ");
/*  50: 71 */     sql.append(" WHERE cp.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  51: 72 */     sql.append(" AND cp.idOrganizacion = :idOrganizacion");
/*  52: 73 */     sql.append(" AND NOT EXISTS ( SELECT 1 FROM InventarioProducto ip ");
/*  53: 74 */     sql.append(" \tWHERE cp.producto = ip.producto AND cp.fecha = ip.fecha ");
/*  54: 75 */     sql.append(" \tAND ip.idOrganizacion = :idOrganizacion");
/*  55: 76 */     if (costeoPorBodega) {
/*  56: 77 */       sql.append(" AND cp.bodega = ip.bodega ");
/*  57:    */     }
/*  58: 79 */     sql.append(" \tAND (ip.fecha between :fechaDesde AND :fechaHasta) ) ");
/*  59: 80 */     Query query = this.em.createQuery(sql.toString());
/*  60: 81 */     query.setParameter("fechaDesde", fechaDesde);
/*  61: 82 */     query.setParameter("fechaHasta", fechaHasta);
/*  62: 83 */     query.setParameter("fechaDesde", fechaDesde);
/*  63: 84 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  64:    */     
/*  65: 86 */     return query.executeUpdate();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int actualizaIndicadorGeneraCostoTransferenciaIngreso(int idOrganizacion, Date fechaDesde, Date fechaHasta, Producto producto)
/*  69:    */   {
/*  70: 90 */     StringBuilder sql = new StringBuilder();
/*  71: 91 */     sql.append(" UPDATE InventarioProducto ip set ip.indicadorGeneraCosto = false ");
/*  72: 92 */     sql.append(" WHERE ip.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  73: 93 */     sql.append(" AND ip.idOrganizacion = :idOrganizacion");
/*  74: 94 */     sql.append(" AND ip.indicadorGeneraCosto = true AND ip.operacion = 1 ");
/*  75: 95 */     sql.append(" AND ip.indicadorTransferencia = true ");
/*  76: 96 */     if (producto != null) {
/*  77: 97 */       sql.append(" AND ip.producto = :producto ");
/*  78:    */     }
/*  79: 99 */     Query query = this.em.createQuery(sql.toString());
/*  80:100 */     query.setParameter("fechaDesde", fechaDesde);
/*  81:101 */     query.setParameter("fechaHasta", fechaHasta);
/*  82:102 */     query.setParameter("fechaDesde", fechaDesde);
/*  83:103 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  84:105 */     if (producto != null) {
/*  85:106 */       query.setParameter("producto", producto);
/*  86:    */     }
/*  87:108 */     return query.executeUpdate();
/*  88:    */   }
/*  89:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CostoEstandarProductoDao
 * JD-Core Version:    0.7.0.1
 */