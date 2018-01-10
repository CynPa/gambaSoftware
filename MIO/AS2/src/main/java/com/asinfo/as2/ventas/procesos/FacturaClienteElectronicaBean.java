/*  1:   */ package com.asinfo.as2.ventas.procesos;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.FacturaCliente;
/*  4:   */ import javax.faces.bean.ManagedBean;
/*  5:   */ import javax.faces.bean.ViewScoped;
/*  6:   */ 
/*  7:   */ @ManagedBean
/*  8:   */ @ViewScoped
/*  9:   */ public class FacturaClienteElectronicaBean
/* 10:   */   extends FacturaClienteBean
/* 11:   */ {
/* 12:   */   private static final long serialVersionUID = 1L;
/* 13:   */   
/* 14:   */   public String limpiar()
/* 15:   */   {
/* 16:16 */     super.limpiar();
/* 17:   */     
/* 18:18 */     this.facturaCliente.setIndicadorGeneraCxC(false);
/* 19:19 */     this.facturaCliente.setIndicadorSaldoInicial(true);
/* 20:   */     
/* 21:21 */     return "";
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.FacturaClienteElectronicaBean
 * JD-Core Version:    0.7.0.1
 */