/*   1:    */ package com.asinfo.as2.rs.datosbase.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ 
/*   6:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   7:    */ public class BodegaResponseDto
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private Integer idOrganizacion;
/*  11:    */   private Integer idSucursal;
/*  12:    */   private Integer idBodega;
/*  13:    */   private String codigo;
/*  14:    */   private String nombre;
/*  15:    */   private Boolean activo;
/*  16:    */   private Boolean predeterminado;
/*  17: 25 */   private int hashCode = 0;
/*  18:    */   
/*  19:    */   public int getHashCode()
/*  20:    */   {
/*  21: 28 */     this.hashCode = hashCode();
/*  22: 29 */     return this.hashCode;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public int hashCode()
/*  26:    */   {
/*  27: 34 */     int hash = 1;
/*  28: 35 */     hash += hash * 12 + (this.idOrganizacion + "").hashCode();
/*  29: 36 */     hash += hash * 36 + (this.idSucursal + "").hashCode();
/*  30: 37 */     hash += hash * 26 + (this.idBodega + "").hashCode();
/*  31: 38 */     hash += hash * 23 + (this.codigo + "").hashCode();
/*  32: 39 */     hash += hash * 18 + (this.nombre + "").hashCode();
/*  33: 40 */     hash += hash * 12 + (this.activo + "").hashCode();
/*  34: 41 */     hash += hash * 11 + (this.predeterminado + "").hashCode();
/*  35:    */     
/*  36: 43 */     return hash;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Integer getIdOrganizacion()
/*  40:    */   {
/*  41: 47 */     return this.idOrganizacion;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setIdOrganizacion(Integer idOrganizacion)
/*  45:    */   {
/*  46: 51 */     this.idOrganizacion = idOrganizacion;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Integer getIdSucursal()
/*  50:    */   {
/*  51: 55 */     return this.idSucursal;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIdSucursal(Integer idSucursal)
/*  55:    */   {
/*  56: 59 */     this.idSucursal = idSucursal;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Integer getIdBodega()
/*  60:    */   {
/*  61: 63 */     return this.idBodega;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIdBodega(Integer idBodega)
/*  65:    */   {
/*  66: 67 */     this.idBodega = idBodega;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getCodigo()
/*  70:    */   {
/*  71: 71 */     return this.codigo;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setCodigo(String codigo)
/*  75:    */   {
/*  76: 75 */     this.codigo = codigo;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getNombre()
/*  80:    */   {
/*  81: 79 */     return this.nombre;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setNombre(String nombre)
/*  85:    */   {
/*  86: 83 */     this.nombre = nombre;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Boolean getActivo()
/*  90:    */   {
/*  91: 90 */     return this.activo;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setActivo(Boolean activo)
/*  95:    */   {
/*  96: 98 */     this.activo = activo;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Boolean getPredeterminado()
/* 100:    */   {
/* 101:102 */     return this.predeterminado;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setPredeterminado(Boolean predeterminado)
/* 105:    */   {
/* 106:106 */     this.predeterminado = predeterminado;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setHashCode(int hashCode)
/* 110:    */   {
/* 111:114 */     this.hashCode = hashCode;
/* 112:    */   }
/* 113:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.BodegaResponseDto
 * JD-Core Version:    0.7.0.1
 */