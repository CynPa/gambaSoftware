/*   1:    */ package com.asinfo.as2.rs.datosbase.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ 
/*   7:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   8:    */ public class ProvinciaResponseDto
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private int idOrganizacion;
/*  12:    */   private int idSucursal;
/*  13:    */   private Integer idProvincia;
/*  14:    */   private String codigo;
/*  15:    */   private String nombre;
/*  16: 22 */   private Boolean activo = Boolean.valueOf(true);
/*  17:    */   private Boolean predeterminado;
/*  18:    */   private PaisResponseDto paisResponse;
/*  19:    */   private BigDecimal latitud;
/*  20:    */   private BigDecimal longitud;
/*  21: 31 */   private int hashCode = 0;
/*  22:    */   
/*  23:    */   public int getHashCode()
/*  24:    */   {
/*  25: 34 */     this.hashCode = hashCode();
/*  26: 35 */     return this.hashCode;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int hashCode()
/*  30:    */   {
/*  31: 40 */     int hash = 1;
/*  32: 41 */     hash += hash * 41 + (this.idOrganizacion + "").hashCode();
/*  33: 42 */     hash += hash * 36 + (this.idSucursal + "").hashCode();
/*  34: 43 */     hash += hash * 11 + (this.idProvincia + "").hashCode();
/*  35: 44 */     hash += hash * 15 + (this.codigo + "").hashCode();
/*  36: 45 */     hash += hash * 13 + (this.nombre + "").hashCode();
/*  37: 46 */     hash += hash * 12 + (this.activo + "").hashCode();
/*  38: 47 */     hash += hash * 10 + (this.predeterminado + "").hashCode();
/*  39: 48 */     hash += hash * 53 + (this.paisResponse != null ? this.paisResponse.hashCode() : 0);
/*  40: 49 */     hash += hash * 47 + (this.latitud + "").hashCode();
/*  41: 50 */     hash += hash * 47 + (this.longitud + "").hashCode();
/*  42:    */     
/*  43: 52 */     return hash;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getIdOrganizacion()
/*  47:    */   {
/*  48: 56 */     return this.idOrganizacion;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setIdOrganizacion(int idOrganizacion)
/*  52:    */   {
/*  53: 60 */     this.idOrganizacion = idOrganizacion;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdSucursal()
/*  57:    */   {
/*  58: 64 */     return this.idSucursal;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdSucursal(int idSucursal)
/*  62:    */   {
/*  63: 68 */     this.idSucursal = idSucursal;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Integer getIdProvincia()
/*  67:    */   {
/*  68: 72 */     return this.idProvincia;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdProvincia(Integer idProvincia)
/*  72:    */   {
/*  73: 76 */     this.idProvincia = idProvincia;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getCodigo()
/*  77:    */   {
/*  78: 80 */     return this.codigo;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setCodigo(String codigo)
/*  82:    */   {
/*  83: 84 */     this.codigo = codigo;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getNombre()
/*  87:    */   {
/*  88: 88 */     return this.nombre;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setNombre(String nombre)
/*  92:    */   {
/*  93: 92 */     this.nombre = nombre;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Boolean getActivo()
/*  97:    */   {
/*  98: 96 */     return this.activo;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setActivo(Boolean activo)
/* 102:    */   {
/* 103:100 */     this.activo = activo;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Boolean getPredeterminado()
/* 107:    */   {
/* 108:104 */     return this.predeterminado;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setPredeterminado(Boolean predeterminado)
/* 112:    */   {
/* 113:108 */     this.predeterminado = predeterminado;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setHashCode(int hashCode)
/* 117:    */   {
/* 118:112 */     this.hashCode = hashCode;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public PaisResponseDto getPaisResponse()
/* 122:    */   {
/* 123:116 */     return this.paisResponse;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setPaisResponse(PaisResponseDto paisResponse)
/* 127:    */   {
/* 128:120 */     this.paisResponse = paisResponse;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public BigDecimal getLatitud()
/* 132:    */   {
/* 133:124 */     return this.latitud;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setLatitud(BigDecimal latitud)
/* 137:    */   {
/* 138:128 */     this.latitud = latitud;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public BigDecimal getLongitud()
/* 142:    */   {
/* 143:132 */     return this.longitud;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setLongitud(BigDecimal longitud)
/* 147:    */   {
/* 148:136 */     this.longitud = longitud;
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.ProvinciaResponseDto
 * JD-Core Version:    0.7.0.1
 */