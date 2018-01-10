/*  1:   */ package com.asinfo.as2.rs.financiero.cobros.dto;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class PlanTarjetaCreditoResponseDto
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private Integer idTarjetaCredito;
/*  9:   */   private int idOrganizacion;
/* 10:   */   private int idSucursal;
/* 11:   */   private String codigo;
/* 12:   */   private String nombre;
/* 13:   */   private boolean indicadorConInteres;
/* 14:   */   private Integer idPlanTarjetaCreditoAS2;
/* 15:21 */   private int hashCode = 0;
/* 16:   */   
/* 17:   */   public int getHashCode()
/* 18:   */   {
/* 19:24 */     this.hashCode = hashCode();
/* 20:25 */     return this.hashCode;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int hashCode()
/* 24:   */   {
/* 25:30 */     int hash = 1;
/* 26:31 */     hash += hash * 17 + (this.idTarjetaCredito + "").hashCode();
/* 27:32 */     hash += hash * 36 + (this.idOrganizacion + "").hashCode();
/* 28:33 */     hash += hash * 40 + (this.idSucursal + "").hashCode();
/* 29:34 */     hash += hash * 3 + (this.codigo + "").hashCode();
/* 30:35 */     hash += hash * 6 + (this.nombre + "").hashCode();
/* 31:36 */     hash += hash * 22 + (this.indicadorConInteres + "").hashCode();
/* 32:37 */     hash += hash * 30 + (this.idPlanTarjetaCreditoAS2 + "").hashCode();
/* 33:   */     
/* 34:39 */     return hash;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public int getIdOrganizacion()
/* 38:   */   {
/* 39:43 */     return this.idOrganizacion;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setIdOrganizacion(int idOrganizacion)
/* 43:   */   {
/* 44:47 */     this.idOrganizacion = idOrganizacion;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public int getIdSucursal()
/* 48:   */   {
/* 49:51 */     return this.idSucursal;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setIdSucursal(int idSucursal)
/* 53:   */   {
/* 54:55 */     this.idSucursal = idSucursal;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void setHashCode(int hashCode)
/* 58:   */   {
/* 59:59 */     this.hashCode = hashCode;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public Integer getIdTarjetaCredito()
/* 63:   */   {
/* 64:63 */     return this.idTarjetaCredito;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void setIdTarjetaCredito(Integer idTarjetaCredito)
/* 68:   */   {
/* 69:67 */     this.idTarjetaCredito = idTarjetaCredito;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public String getCodigo()
/* 73:   */   {
/* 74:71 */     return this.codigo;
/* 75:   */   }
/* 76:   */   
/* 77:   */   public void setCodigo(String codigo)
/* 78:   */   {
/* 79:75 */     this.codigo = codigo;
/* 80:   */   }
/* 81:   */   
/* 82:   */   public String getNombre()
/* 83:   */   {
/* 84:79 */     return this.nombre;
/* 85:   */   }
/* 86:   */   
/* 87:   */   public void setNombre(String nombre)
/* 88:   */   {
/* 89:83 */     this.nombre = nombre;
/* 90:   */   }
/* 91:   */   
/* 92:   */   public boolean isIndicadorConInteres()
/* 93:   */   {
/* 94:87 */     return this.indicadorConInteres;
/* 95:   */   }
/* 96:   */   
/* 97:   */   public void setIndicadorConInteres(boolean indicadorConInteres)
/* 98:   */   {
/* 99:91 */     this.indicadorConInteres = indicadorConInteres;
/* :0:   */   }
/* :1:   */   
/* :2:   */   public Integer getIdPlanTarjetaCreditoAS2()
/* :3:   */   {
/* :4:95 */     return this.idPlanTarjetaCreditoAS2;
/* :5:   */   }
/* :6:   */   
/* :7:   */   public void setIdPlanTarjetaCreditoAS2(Integer idPlanTarjetaCreditoAS2)
/* :8:   */   {
/* :9:99 */     this.idPlanTarjetaCreditoAS2 = idPlanTarjetaCreditoAS2;
/* ;0:   */   }
/* ;1:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.dto.PlanTarjetaCreditoResponseDto
 * JD-Core Version:    0.7.0.1
 */