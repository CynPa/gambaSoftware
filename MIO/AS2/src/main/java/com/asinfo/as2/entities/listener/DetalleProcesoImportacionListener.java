/*  1:   */ package com.asinfo.as2.entities.listener;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DetalleProcesoImportacion;
/*  4:   */ import com.asinfo.as2.entities.FacturaProveedorImportacion;
/*  5:   */ import com.asinfo.as2.entities.ProcesoImportacion;
/*  6:   */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  7:   */ import java.util.Collections;
/*  8:   */ import java.util.List;
/*  9:   */ import javax.persistence.PrePersist;
/* 10:   */ import javax.persistence.PreUpdate;
/* 11:   */ 
/* 12:   */ public class DetalleProcesoImportacionListener
/* 13:   */ {
/* 14:   */   @PrePersist
/* 15:   */   @PreUpdate
/* 16:   */   void actualizarEstadoProcesoImportacion(Object entity)
/* 17:   */   {
/* 18:36 */     if ((entity instanceof DetalleProcesoImportacion))
/* 19:   */     {
/* 20:37 */       StringBuilder estadoProcesoImportacion = new StringBuilder();
/* 21:38 */       DetalleProcesoImportacion detalleProcesoImportacion = (DetalleProcesoImportacion)entity;
/* 22:39 */       List<DetalleProcesoImportacion> lista = detalleProcesoImportacion.getFacturaProveedorImportacion().getListaDetalleProcesoImportacion();
/* 23:40 */       FuncionesUtiles.ordenaLista(lista, "fecha");
/* 24:41 */       Collections.reverse(lista);
/* 25:42 */       if (!lista.isEmpty())
/* 26:   */       {
/* 27:43 */         DetalleProcesoImportacion dpi = (DetalleProcesoImportacion)lista.get(0);
/* 28:44 */         estadoProcesoImportacion.append(dpi.getProcesoImportacion().getNombre());
/* 29:   */         
/* 30:46 */         detalleProcesoImportacion.getFacturaProveedorImportacion().setEstadoProcesoImportacion(estadoProcesoImportacion.toString());
/* 31:   */       }
/* 32:   */     }
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.listener.DetalleProcesoImportacionListener
 * JD-Core Version:    0.7.0.1
 */