/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Producto;
/*  4:   */ import com.asinfo.as2.entities.UnidadConversion;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.Query;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class UnidadConversionDao
/* 12:   */   extends AbstractDaoAS2<UnidadConversion>
/* 13:   */ {
/* 14:   */   public UnidadConversionDao()
/* 15:   */   {
/* 16:30 */     super(UnidadConversion.class);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public UnidadConversion getUnidadConversion(int idUnidadOrigen, int idUnidadDestino)
/* 20:   */   {
/* 21:35 */     String query = " SELECT uc FROM UnidadConversion uc  WHERE uc.unidadOrigen.idUnidad = :idUnidadOrigen  AND uc.unidadDestino.idUnidad = :idUnidadDestino ";
/* 22:   */     
/* 23:   */ 
/* 24:   */ 
/* 25:   */ 
/* 26:   */ 
/* 27:   */ 
/* 28:   */ 
/* 29:43 */     UnidadConversion unidadConversion = (UnidadConversion)this.em.createQuery(query).setParameter("idUnidadOrigen", Integer.valueOf(idUnidadOrigen)).setParameter("idUnidadDestino", Integer.valueOf(idUnidadDestino)).getSingleResult();
/* 30:44 */     return unidadConversion;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<UnidadConversion> obtenerUnidadConversionConProducto()
/* 34:   */   {
/* 35:49 */     String sql = " SELECT u FROM UnidadConversion u  WHERE u.producto != null ";
/* 36:   */     
/* 37:51 */     return this.em.createQuery(sql).getResultList();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public List<UnidadConversion> obtenerUnidadConversionConProducto(Producto producto)
/* 41:   */   {
/* 42:56 */     String sql = " SELECT u FROM UnidadConversion u  WHERE u.producto = :producto ";
/* 43:   */     
/* 44:58 */     return this.em.createQuery(sql).setParameter("producto", producto).getResultList();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public List<UnidadConversion> obtenerUnidadConversionConSubcategoriaProducto()
/* 48:   */   {
/* 49:63 */     String sql = " SELECT u FROM UnidadConversion u  WHERE u.subcategoriaProducto != null ";
/* 50:   */     
/* 51:65 */     return this.em.createQuery(sql).getResultList();
/* 52:   */   }
/* 53:   */   
/* 54:   */   public List<UnidadConversion> obtenerUnidadConversionSinProductoSubcategoriaProducto()
/* 55:   */   {
/* 56:70 */     String sql = " SELECT u FROM UnidadConversion u  WHERE u.subcategoriaProducto = null AND u.producto = null";
/* 57:   */     
/* 58:72 */     return this.em.createQuery(sql).getResultList();
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.UnidadConversionDao
 * JD-Core Version:    0.7.0.1
 */