/*  1:   */ package com.asinfo.as2.rs.datosbase.dto;
/*  2:   */ 
/*  3:   */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  7:   */ public class ConfiguracionResponseDto
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private Integer idOrganizacion;
/* 11:   */   private Integer idEmpresa;
/* 12:   */   private Integer numeroDecimalesPrecioVenta;
/* 13:   */   private String formatoDinero;
/* 14:   */   private String formatoFecha;
/* 15:   */   private Boolean cierreCajaPorDenominacionFormaCobro;
/* 16:23 */   private int hashCode = 0;
/* 17:   */   
/* 18:   */   public int getHashCode()
/* 19:   */   {
/* 20:26 */     this.hashCode = hashCode();
/* 21:27 */     return this.hashCode;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int hashCode()
/* 25:   */   {
/* 26:32 */     int hash = 1;
/* 27:33 */     hash += hash * 17 + (this.idEmpresa + "").hashCode();
/* 28:34 */     hash += hash * 22 + (this.numeroDecimalesPrecioVenta + "").hashCode();
/* 29:35 */     hash += hash * 41 + (this.formatoDinero + "").hashCode();
/* 30:36 */     hash += hash * 36 + (this.formatoFecha + "").hashCode();
/* 31:37 */     hash += hash * 64 + (this.idOrganizacion + "").hashCode();
/* 32:38 */     hash += hash * 87 + (this.cierreCajaPorDenominacionFormaCobro + "").hashCode();
/* 33:   */     
/* 34:40 */     return hash;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public Integer getIdEmpresa()
/* 38:   */   {
/* 39:44 */     return this.idEmpresa;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setIdEmpresa(Integer idEmpresa)
/* 43:   */   {
/* 44:48 */     this.idEmpresa = idEmpresa;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Integer getNumeroDecimalesPrecioVenta()
/* 48:   */   {
/* 49:52 */     return this.numeroDecimalesPrecioVenta;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setNumeroDecimalesPrecioVenta(Integer numeroDecimalesPrecioVenta)
/* 53:   */   {
/* 54:56 */     this.numeroDecimalesPrecioVenta = numeroDecimalesPrecioVenta;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public String getFormatoDinero()
/* 58:   */   {
/* 59:60 */     return this.formatoDinero;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void setFormatoDinero(String formatoDinero)
/* 63:   */   {
/* 64:64 */     this.formatoDinero = formatoDinero;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public String getFormatoFecha()
/* 68:   */   {
/* 69:68 */     return this.formatoFecha;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void setFormatoFecha(String formatoFecha)
/* 73:   */   {
/* 74:72 */     this.formatoFecha = formatoFecha;
/* 75:   */   }
/* 76:   */   
/* 77:   */   public Integer getIdOrganizacion()
/* 78:   */   {
/* 79:76 */     return this.idOrganizacion;
/* 80:   */   }
/* 81:   */   
/* 82:   */   public void setIdOrganizacion(Integer idOrganizacion)
/* 83:   */   {
/* 84:80 */     this.idOrganizacion = idOrganizacion;
/* 85:   */   }
/* 86:   */   
/* 87:   */   public Boolean getCierreCajaPorDenominacionFormaCobro()
/* 88:   */   {
/* 89:84 */     return this.cierreCajaPorDenominacionFormaCobro;
/* 90:   */   }
/* 91:   */   
/* 92:   */   public void setCierreCajaPorDenominacionFormaCobro(Boolean cierreCajaPorDenominacionFormaCobro)
/* 93:   */   {
/* 94:88 */     this.cierreCajaPorDenominacionFormaCobro = cierreCajaPorDenominacionFormaCobro;
/* 95:   */   }
/* 96:   */   
/* 97:   */   public void setHashCode(int hashCode)
/* 98:   */   {
/* 99:96 */     this.hashCode = hashCode;
/* :0:   */   }
/* :1:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.ConfiguracionResponseDto
 * JD-Core Version:    0.7.0.1
 */