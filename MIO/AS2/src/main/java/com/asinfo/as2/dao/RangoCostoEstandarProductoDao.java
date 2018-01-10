/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CostoEstandarProducto;
/*  4:   */ import com.asinfo.as2.entities.Producto;
/*  5:   */ import com.asinfo.as2.entities.RangoCostoEstandarProducto;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ 
/*  9:   */ @Stateless
/* 10:   */ public class RangoCostoEstandarProductoDao
/* 11:   */   extends AbstractDaoAS2<RangoCostoEstandarProducto>
/* 12:   */ {
/* 13:   */   public RangoCostoEstandarProductoDao()
/* 14:   */   {
/* 15:32 */     super(RangoCostoEstandarProducto.class);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public RangoCostoEstandarProducto cargarDetalle(int idRangoCostoEstandarProducto)
/* 19:   */   {
/* 20:42 */     RangoCostoEstandarProducto rangoCostoEstandarProducto = (RangoCostoEstandarProducto)buscarPorId(Integer.valueOf(idRangoCostoEstandarProducto));
/* 21:43 */     rangoCostoEstandarProducto.getListaCostoEstandarProducto().size();
/* 22:44 */     for (CostoEstandarProducto costoEstandarProducto : rangoCostoEstandarProducto.getListaCostoEstandarProducto()) {
/* 23:45 */       costoEstandarProducto.getProducto().getId();
/* 24:   */     }
/* 25:47 */     return rangoCostoEstandarProducto;
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.RangoCostoEstandarProductoDao
 * JD-Core Version:    0.7.0.1
 */