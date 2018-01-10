/*  1:   */ package com.asinfo.as2.dao.mantenimiento.old;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.mantenimiento.old.ListaVerificacion;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ 
/*  8:   */ @Stateless
/*  9:   */ public class ListaVerificacionDao
/* 10:   */   extends AbstractDaoAS2<ListaVerificacion>
/* 11:   */ {
/* 12:   */   public ListaVerificacionDao()
/* 13:   */   {
/* 14:31 */     super(ListaVerificacion.class);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public ListaVerificacion cargarDetalle(int idListaVerificacion)
/* 18:   */   {
/* 19:41 */     ListaVerificacion listaVerificacion = (ListaVerificacion)buscarPorId(Integer.valueOf(idListaVerificacion));
/* 20:42 */     listaVerificacion.getListaDetalleListaVerificacion().size();
/* 21:   */     
/* 22:44 */     return listaVerificacion;
/* 23:   */   }
/* 24:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.mantenimiento.old.ListaVerificacionDao
 * JD-Core Version:    0.7.0.1
 */