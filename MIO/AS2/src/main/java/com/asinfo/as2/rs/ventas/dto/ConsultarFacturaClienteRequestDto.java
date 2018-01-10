/*  1:   */ package com.asinfo.as2.rs.ventas.dto;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class ConsultarFacturaClienteRequestDto
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private int idOrganizacion;
/*  9:   */   private Integer idFacturaCliente;
/* 10:   */   private Integer idCobro;
/* 11:   */   private Integer idDespacho;
/* 12:   */   
/* 13:   */   public int getIdOrganizacion()
/* 14:   */   {
/* 15:17 */     return this.idOrganizacion;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setIdOrganizacion(int idOrganizacion)
/* 19:   */   {
/* 20:21 */     this.idOrganizacion = idOrganizacion;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Integer getIdFacturaCliente()
/* 24:   */   {
/* 25:25 */     return this.idFacturaCliente;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setIdFacturaCliente(Integer idFacturaCliente)
/* 29:   */   {
/* 30:29 */     this.idFacturaCliente = idFacturaCliente;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Integer getIdCobro()
/* 34:   */   {
/* 35:33 */     return this.idCobro;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void setIdCobro(Integer idCobro)
/* 39:   */   {
/* 40:37 */     this.idCobro = idCobro;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public Integer getIdDespacho()
/* 44:   */   {
/* 45:41 */     return this.idDespacho;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void setIdDespacho(Integer idDespacho)
/* 49:   */   {
/* 50:45 */     this.idDespacho = idDespacho;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.ventas.dto.ConsultarFacturaClienteRequestDto
 * JD-Core Version:    0.7.0.1
 */