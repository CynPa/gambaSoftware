/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.PartidaArancelaria;
/*  4:   */ import com.asinfo.as2.entities.Producto;
/*  5:   */ import com.asinfo.as2.entities.Unidad;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ 
/*  9:   */ @Stateless
/* 10:   */ public class PartidaArancelariaDao
/* 11:   */   extends AbstractDaoAS2<PartidaArancelaria>
/* 12:   */ {
/* 13:   */   public PartidaArancelariaDao()
/* 14:   */   {
/* 15:31 */     super(PartidaArancelaria.class);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public PartidaArancelaria cargarDetalle(int idPartidaArancelaria)
/* 19:   */   {
/* 20:41 */     PartidaArancelaria partidaArancelaria = (PartidaArancelaria)buscarPorId(Integer.valueOf(idPartidaArancelaria));
/* 21:42 */     partidaArancelaria.getListaDetallePartidaArancelaria().size();
/* 22:43 */     partidaArancelaria.getListaProducto().size();
/* 23:44 */     for (Producto producto : partidaArancelaria.getListaProducto()) {
/* 24:45 */       producto.getUnidad().getId();
/* 25:   */     }
/* 26:47 */     return partidaArancelaria;
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PartidaArancelariaDao
 * JD-Core Version:    0.7.0.1
 */