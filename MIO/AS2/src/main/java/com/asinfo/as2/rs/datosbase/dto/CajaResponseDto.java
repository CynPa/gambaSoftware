/*   1:    */ package com.asinfo.as2.rs.datosbase.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ 
/*   6:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   7:    */ public class CajaResponseDto
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private Integer idOrganizacion;
/*  11:    */   private Integer idSucursal;
/*  12:    */   private Integer idCaja;
/*  13:    */   private Integer idPuntoDeVenta;
/*  14:    */   private String codigo;
/*  15:    */   private String nombre;
/*  16:    */   private Boolean activo;
/*  17:    */   private Boolean predeterminado;
/*  18: 27 */   private int hashCode = 0;
/*  19:    */   
/*  20:    */   public int getHashCode()
/*  21:    */   {
/*  22: 30 */     this.hashCode = hashCode();
/*  23: 31 */     return this.hashCode;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int hashCode()
/*  27:    */   {
/*  28: 36 */     int hash = 1;
/*  29: 37 */     hash += hash * 12 + (this.idOrganizacion + "").hashCode();
/*  30: 38 */     hash += hash * 36 + (this.idSucursal + "").hashCode();
/*  31: 39 */     hash += hash * 21 + (this.idCaja + "").hashCode();
/*  32: 40 */     hash += hash * 14 + (this.idPuntoDeVenta + "").hashCode();
/*  33: 41 */     hash += hash * 23 + (this.codigo + "").hashCode();
/*  34: 42 */     hash += hash * 18 + (this.nombre + "").hashCode();
/*  35: 43 */     hash += hash * 12 + (this.activo + "").hashCode();
/*  36: 44 */     hash += hash * 11 + (this.predeterminado + "").hashCode();
/*  37:    */     
/*  38: 46 */     return hash;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Integer getIdOrganizacion()
/*  42:    */   {
/*  43: 50 */     return this.idOrganizacion;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setIdOrganizacion(Integer idOrganizacion)
/*  47:    */   {
/*  48: 54 */     this.idOrganizacion = idOrganizacion;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Integer getIdSucursal()
/*  52:    */   {
/*  53: 58 */     return this.idSucursal;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setIdSucursal(Integer idSucursal)
/*  57:    */   {
/*  58: 62 */     this.idSucursal = idSucursal;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Integer getIdCaja()
/*  62:    */   {
/*  63: 66 */     return this.idCaja;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setIdCaja(Integer idCaja)
/*  67:    */   {
/*  68: 70 */     this.idCaja = idCaja;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Integer getIdPuntoDeVenta()
/*  72:    */   {
/*  73: 74 */     return this.idPuntoDeVenta;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIdPuntoDeVenta(Integer idPuntoDeVenta)
/*  77:    */   {
/*  78: 78 */     this.idPuntoDeVenta = idPuntoDeVenta;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getCodigo()
/*  82:    */   {
/*  83: 82 */     return this.codigo;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setCodigo(String codigo)
/*  87:    */   {
/*  88: 86 */     this.codigo = codigo;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getNombre()
/*  92:    */   {
/*  93: 90 */     return this.nombre;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setNombre(String nombre)
/*  97:    */   {
/*  98: 94 */     this.nombre = nombre;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Boolean getActivo()
/* 102:    */   {
/* 103:101 */     return this.activo;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setActivo(Boolean activo)
/* 107:    */   {
/* 108:109 */     this.activo = activo;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Boolean getPredeterminado()
/* 112:    */   {
/* 113:113 */     return this.predeterminado;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setPredeterminado(Boolean predeterminado)
/* 117:    */   {
/* 118:117 */     this.predeterminado = predeterminado;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setHashCode(int hashCode)
/* 122:    */   {
/* 123:125 */     this.hashCode = hashCode;
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.CajaResponseDto
 * JD-Core Version:    0.7.0.1
 */