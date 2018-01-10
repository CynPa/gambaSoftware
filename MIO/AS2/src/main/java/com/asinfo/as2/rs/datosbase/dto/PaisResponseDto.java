/*   1:    */ package com.asinfo.as2.rs.datosbase.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ 
/*   6:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   7:    */ public class PaisResponseDto
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private int idOrganizacion;
/*  11:    */   private int idSucursal;
/*  12:    */   private Integer idPais;
/*  13:    */   private String codigo;
/*  14:    */   private String codigoIso;
/*  15:    */   private String nombre;
/*  16: 23 */   private Boolean activo = Boolean.valueOf(true);
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
/*  29: 37 */     hash += hash * 41 + (this.idOrganizacion + "").hashCode();
/*  30: 38 */     hash += hash * 36 + (this.idSucursal + "").hashCode();
/*  31: 39 */     hash += hash * 11 + (this.idPais + "").hashCode();
/*  32: 40 */     hash += hash * 15 + (this.codigoIso + "").hashCode();
/*  33: 41 */     hash += hash * 23 + (this.codigo + "").hashCode();
/*  34: 42 */     hash += hash * 13 + (this.nombre + "").hashCode();
/*  35: 43 */     hash += hash * 12 + (this.activo + "").hashCode();
/*  36: 44 */     hash += hash * 10 + (this.predeterminado + "").hashCode();
/*  37:    */     
/*  38: 46 */     return hash;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getIdOrganizacion()
/*  42:    */   {
/*  43: 50 */     return this.idOrganizacion;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setIdOrganizacion(int idOrganizacion)
/*  47:    */   {
/*  48: 54 */     this.idOrganizacion = idOrganizacion;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getIdSucursal()
/*  52:    */   {
/*  53: 58 */     return this.idSucursal;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setIdSucursal(int idSucursal)
/*  57:    */   {
/*  58: 62 */     this.idSucursal = idSucursal;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Integer getIdPais()
/*  62:    */   {
/*  63: 66 */     return this.idPais;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setIdPais(Integer idPais)
/*  67:    */   {
/*  68: 70 */     this.idPais = idPais;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String getCodigoIso()
/*  72:    */   {
/*  73: 74 */     return this.codigoIso;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setCodigoIso(String codigoIso)
/*  77:    */   {
/*  78: 78 */     this.codigoIso = codigoIso;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getNombre()
/*  82:    */   {
/*  83: 82 */     return this.nombre;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setNombre(String nombre)
/*  87:    */   {
/*  88: 86 */     this.nombre = nombre;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Boolean getActivo()
/*  92:    */   {
/*  93: 90 */     return this.activo;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setActivo(Boolean activo)
/*  97:    */   {
/*  98: 94 */     this.activo = activo;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Boolean getPredeterminado()
/* 102:    */   {
/* 103: 98 */     return this.predeterminado;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setPredeterminado(Boolean predeterminado)
/* 107:    */   {
/* 108:102 */     this.predeterminado = predeterminado;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setHashCode(int hashCode)
/* 112:    */   {
/* 113:106 */     this.hashCode = hashCode;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getCodigo()
/* 117:    */   {
/* 118:110 */     return this.codigo;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setCodigo(String codigo)
/* 122:    */   {
/* 123:114 */     this.codigo = codigo;
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.PaisResponseDto
 * JD-Core Version:    0.7.0.1
 */