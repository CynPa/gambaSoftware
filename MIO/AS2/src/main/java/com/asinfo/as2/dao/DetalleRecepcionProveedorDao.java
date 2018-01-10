/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*  4:   */ import java.math.BigDecimal;
/*  5:   */ import javax.ejb.Stateless;
/*  6:   */ import javax.persistence.EntityManager;
/*  7:   */ import javax.persistence.Query;
/*  8:   */ 
/*  9:   */ @Stateless
/* 10:   */ public class DetalleRecepcionProveedorDao
/* 11:   */   extends AbstractDaoAS2<DetalleRecepcionProveedor>
/* 12:   */ {
/* 13:   */   public DetalleRecepcionProveedorDao()
/* 14:   */   {
/* 15:33 */     super(DetalleRecepcionProveedor.class);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void actualizarCantidadDevuelta(DetalleRecepcionProveedor detalleRecepcionProveedor, BigDecimal cantidadDevuelta)
/* 19:   */   {
/* 20:39 */     Query query = this.em.createQuery("UPDATE DetalleRecepcionProveedor drp SET drp.cantidadDevuelta=:cantidadDevuelta WHERE drp = :detalleRecepcionProveedor");
/* 21:40 */     query.setParameter("detalleRecepcionProveedor", detalleRecepcionProveedor);
/* 22:41 */     query.setParameter("cantidadDevuelta", cantidadDevuelta);
/* 23:42 */     query.executeUpdate();
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.DetalleRecepcionProveedorDao
 * JD-Core Version:    0.7.0.1
 */