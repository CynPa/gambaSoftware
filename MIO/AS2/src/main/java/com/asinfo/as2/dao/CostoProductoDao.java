/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Bodega;
/*   4:    */ import com.asinfo.as2.entities.CostoProducto;
/*   5:    */ import com.asinfo.as2.entities.Producto;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import java.util.Date;
/*   8:    */ import javax.ejb.Stateless;
/*   9:    */ import javax.persistence.EntityManager;
/*  10:    */ import javax.persistence.NoResultException;
/*  11:    */ import javax.persistence.Query;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class CostoProductoDao
/*  15:    */   extends AbstractDaoAS2<CostoProducto>
/*  16:    */ {
/*  17:    */   public CostoProductoDao()
/*  18:    */   {
/*  19: 37 */     super(CostoProducto.class);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public CostoProducto buscarYActualizar(CostoProducto costoProducto)
/*  23:    */   {
/*  24: 52 */     StringBuilder sql = new StringBuilder();
/*  25: 53 */     sql.append(" SELECT p FROM CostoProducto p ");
/*  26: 54 */     sql.append(" WHERE p.producto.idProducto=:idProducto ");
/*  27: 55 */     sql.append(" AND p.fecha=:fecha ");
/*  28: 56 */     if (costoProducto.getBodega() != null) {
/*  29: 57 */       sql.append(" AND p.bodega = :bodega");
/*  30:    */     }
/*  31: 59 */     Query query = this.em.createQuery(sql.toString());
/*  32: 60 */     query.setParameter("idProducto", Integer.valueOf(costoProducto.getProducto().getId()));
/*  33: 61 */     query.setParameter("fecha", costoProducto.getFecha());
/*  34: 62 */     if (costoProducto.getBodega() != null) {
/*  35: 63 */       query.setParameter("bodega", costoProducto.getBodega());
/*  36:    */     }
/*  37:    */     try
/*  38:    */     {
/*  39: 67 */       CostoProducto costo = (CostoProducto)query.getSingleResult();
/*  40: 68 */       costo.setSaldo(costoProducto.getSaldo());
/*  41: 69 */       costo.setCosto(costoProducto.getCosto());
/*  42: 70 */       return costo;
/*  43:    */     }
/*  44:    */     catch (NoResultException e) {}
/*  45: 72 */     return costoProducto;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void guardar(CostoProducto costoProducto)
/*  49:    */   {
/*  50: 79 */     if (costoProducto.getSaldo().equals(BigDecimal.ZERO)) {
/*  51: 80 */       costoProducto.setEliminado(true);
/*  52:    */     }
/*  53: 82 */     super.guardar(costoProducto);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void borrarPorRangoDeFechas(Producto producto, Date fechaDesde, Date fechaHasta, Bodega bodega)
/*  57:    */   {
/*  58: 87 */     StringBuilder sql = new StringBuilder();
/*  59: 88 */     sql.append(" DELETE FROM CostoProducto p ");
/*  60: 89 */     sql.append(" WHERE p.producto.idProducto=:idProducto ");
/*  61: 90 */     sql.append(" AND p.fecha BETWEEN :fechaDesde AND :fechaHasta ");
/*  62: 91 */     if (bodega != null) {
/*  63: 92 */       sql.append(" AND p.bodega.idBodega = :idBodega");
/*  64:    */     }
/*  65: 94 */     Query query = this.em.createQuery(sql.toString());
/*  66: 95 */     query.setParameter("idProducto", Integer.valueOf(producto.getId()));
/*  67: 96 */     query.setParameter("fechaDesde", fechaDesde);
/*  68: 97 */     query.setParameter("fechaHasta", fechaHasta);
/*  69: 98 */     if (bodega != null) {
/*  70: 99 */       query.setParameter("idBodega", Integer.valueOf(bodega.getId()));
/*  71:    */     }
/*  72:102 */     query.executeUpdate();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void actualizarVersionCosteoProducto(Producto producto, Integer versionCosteo)
/*  76:    */   {
/*  77:106 */     if (versionCosteo != null)
/*  78:    */     {
/*  79:107 */       StringBuilder sql = new StringBuilder();
/*  80:108 */       sql.append(" UPDATE Producto p set p.versionCosteo = :versionCosteo ");
/*  81:109 */       sql.append(" WHERE p.idProducto=:idProducto ");
/*  82:    */       
/*  83:111 */       Query query = this.em.createQuery(sql.toString());
/*  84:112 */       query.setParameter("idProducto", Integer.valueOf(producto.getId()));
/*  85:113 */       query.setParameter("versionCosteo", versionCosteo);
/*  86:    */       
/*  87:115 */       query.executeUpdate();
/*  88:116 */       this.em.flush();
/*  89:    */     }
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.CostoProductoDao
 * JD-Core Version:    0.7.0.1
 */