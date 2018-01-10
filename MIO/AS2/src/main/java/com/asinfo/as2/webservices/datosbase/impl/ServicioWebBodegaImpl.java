/*  1:   */ package com.asinfo.as2.webservices.datosbase.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  4:   */ import com.asinfo.as2.webservices.datosbase.ServicioWebBodega;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.ejb.EJB;
/*  8:   */ import javax.jws.WebService;
/*  9:   */ 
/* 10:   */ @WebService(endpointInterface="com.asinfo.as2.webservices.datosbase.ServicioWebBodega")
/* 11:   */ public class ServicioWebBodegaImpl
/* 12:   */   implements ServicioWebBodega
/* 13:   */ {
/* 14:   */   @EJB
/* 15:   */   private transient ServicioBodega servicioBodega;
/* 16:   */   
/* 17:   */   public List<com.asinfo.pos.model.Bodega> getListaBodegaPos()
/* 18:   */   {
/* 19:42 */     List<com.asinfo.pos.model.Bodega> lista = new ArrayList();
/* 20:43 */     List<com.asinfo.as2.entities.Bodega> listaBodegaAS2 = this.servicioBodega.obtenerListaCombo(null, false, null);
/* 21:46 */     for (com.asinfo.as2.entities.Bodega bodega : listaBodegaAS2)
/* 22:   */     {
/* 23:47 */       com.asinfo.pos.model.Bodega bodegaDato = new com.asinfo.pos.model.Bodega();
/* 24:48 */       bodegaDato.setCodigo(bodega.getCodigo());
/* 25:49 */       bodegaDato.setNombre(bodega.getNombre());
/* 26:50 */       bodegaDato.setIdBodegaAS2(bodega.getIdBodega());
/* 27:51 */       lista.add(bodegaDato);
/* 28:   */     }
/* 29:54 */     return lista;
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.datosbase.impl.ServicioWebBodegaImpl
 * JD-Core Version:    0.7.0.1
 */