/*  1:   */ package com.asinfo.as2.ws.ventas.model;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Date;
/*  5:   */ 
/*  6:   */ public class EstadoCuentaWSEntity
/*  7:   */   implements Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 1L;
/* 10:   */   private Long idEmpresa;
/* 11:   */   private String nombreEmpresa;
/* 12:   */   private Long idSubempresa;
/* 13:   */   private String nombreSubempresa;
/* 14:   */   private Date fechaDesde;
/* 15:   */   private Date fechaHasta;
/* 16:   */   private Long idOrganizacion;
/* 17:   */   private Boolean saldoDiferenteCero;
/* 18:   */   
/* 19:   */   public Long getIdEmpresa()
/* 20:   */   {
/* 21:20 */     return this.idEmpresa;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void setIdEmpresa(Long idEmpresa)
/* 25:   */   {
/* 26:24 */     this.idEmpresa = idEmpresa;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Long getIdSubempresa()
/* 30:   */   {
/* 31:28 */     return this.idSubempresa;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setIdSubempresa(Long idSubempresa)
/* 35:   */   {
/* 36:32 */     this.idSubempresa = idSubempresa;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Date getFechaDesde()
/* 40:   */   {
/* 41:36 */     return this.fechaDesde;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void setFechaDesde(Date fechaDesde)
/* 45:   */   {
/* 46:40 */     this.fechaDesde = fechaDesde;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public Date getFechaHasta()
/* 50:   */   {
/* 51:44 */     return this.fechaHasta;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void setFechaHasta(Date fechaHasta)
/* 55:   */   {
/* 56:48 */     this.fechaHasta = fechaHasta;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public Long getIdOrganizacion()
/* 60:   */   {
/* 61:52 */     return this.idOrganizacion;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void setIdOrganizacion(Long idOrganizacion)
/* 65:   */   {
/* 66:56 */     this.idOrganizacion = idOrganizacion;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public String getNombreEmpresa()
/* 70:   */   {
/* 71:60 */     return this.nombreEmpresa;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public void setNombreEmpresa(String nombreEmpresa)
/* 75:   */   {
/* 76:64 */     this.nombreEmpresa = nombreEmpresa;
/* 77:   */   }
/* 78:   */   
/* 79:   */   public String getNombreSubempresa()
/* 80:   */   {
/* 81:68 */     return this.nombreSubempresa;
/* 82:   */   }
/* 83:   */   
/* 84:   */   public void setNombreSubempresa(String nombreSubempresa)
/* 85:   */   {
/* 86:72 */     this.nombreSubempresa = nombreSubempresa;
/* 87:   */   }
/* 88:   */   
/* 89:   */   public Boolean isSaldoDiferenteCero()
/* 90:   */   {
/* 91:76 */     return this.saldoDiferenteCero;
/* 92:   */   }
/* 93:   */   
/* 94:   */   public void setSaldoDiferenteCero(Boolean saldoDiferenteCero)
/* 95:   */   {
/* 96:80 */     this.saldoDiferenteCero = saldoDiferenteCero;
/* 97:   */   }
/* 98:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.ventas.model.EstadoCuentaWSEntity
 * JD-Core Version:    0.7.0.1
 */