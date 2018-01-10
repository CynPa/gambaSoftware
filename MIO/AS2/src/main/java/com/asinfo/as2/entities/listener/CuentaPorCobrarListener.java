/*  1:   */ package com.asinfo.as2.entities.listener;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*  4:   */ import com.asinfo.as2.entities.FacturaCliente;
/*  5:   */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  6:   */ import java.util.Date;
/*  7:   */ import javax.persistence.PrePersist;
/*  8:   */ import javax.persistence.PreUpdate;
/*  9:   */ 
/* 10:   */ public class CuentaPorCobrarListener
/* 11:   */ {
/* 12:   */   @PrePersist
/* 13:   */   @PreUpdate
/* 14:   */   void actualizarDiasPlazo(Object entity)
/* 15:   */   {
/* 16:37 */     if ((entity instanceof CuentaPorCobrar))
/* 17:   */     {
/* 18:38 */       CuentaPorCobrar cuentaPorCobrar = (CuentaPorCobrar)entity;
/* 19:39 */       if (cuentaPorCobrar.getFacturaCliente() != null)
/* 20:   */       {
/* 21:40 */         Date fechaFactura = cuentaPorCobrar.getFacturaCliente().getFecha();
/* 22:41 */         Date fechaVencimiento = cuentaPorCobrar.getFechaVencimiento();
/* 23:42 */         int diasPlazo = FuncionesUtiles.diferenciasDeFechas(fechaFactura, fechaVencimiento) - 1;
/* 24:43 */         cuentaPorCobrar.setDiasPlazo(diasPlazo);
/* 25:   */       }
/* 26:   */     }
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.listener.CuentaPorCobrarListener
 * JD-Core Version:    0.7.0.1
 */