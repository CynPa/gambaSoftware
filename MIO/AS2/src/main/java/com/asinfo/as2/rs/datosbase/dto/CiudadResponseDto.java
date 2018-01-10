/*   1:    */ package com.asinfo.as2.rs.datosbase.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ 
/*   6:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   7:    */ public class CiudadResponseDto
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private int idOrganizacion;
/*  11:    */   private int idSucursal;
/*  12:    */   private Integer idCiudad;
/*  13:    */   private String codigo;
/*  14:    */   private String codigoPostal;
/*  15:    */   private String nombre;
/*  16: 23 */   private Boolean activo = Boolean.valueOf(true);
/*  17:    */   private Boolean predeterminado;
/*  18:    */   private ProvinciaResponseDto provincia;
/*  19: 29 */   private int hashCode = 0;
/*  20:    */   
/*  21:    */   public int getHashCode()
/*  22:    */   {
/*  23: 32 */     this.hashCode = hashCode();
/*  24: 33 */     return this.hashCode;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int hashCode()
/*  28:    */   {
/*  29: 38 */     int hash = 1;
/*  30: 39 */     hash += hash * 41 + (this.idOrganizacion + "").hashCode();
/*  31: 40 */     hash += hash * 36 + (this.idSucursal + "").hashCode();
/*  32: 41 */     hash += hash * 11 + (this.idCiudad + "").hashCode();
/*  33: 42 */     hash += hash * 15 + (this.codigo + "").hashCode();
/*  34: 43 */     hash += hash * 37 + (this.codigoPostal + "").hashCode();
/*  35: 44 */     hash += hash * 13 + (this.nombre + "").hashCode();
/*  36: 45 */     hash += hash * 12 + (this.activo + "").hashCode();
/*  37: 46 */     hash += hash * 10 + (this.predeterminado + "").hashCode();
/*  38: 47 */     hash += hash * 23 + (this.provincia != null ? this.provincia.getHashCode() : 0);
/*  39:    */     
/*  40: 49 */     return hash;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int getIdOrganizacion()
/*  44:    */   {
/*  45: 53 */     return this.idOrganizacion;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setIdOrganizacion(int idOrganizacion)
/*  49:    */   {
/*  50: 57 */     this.idOrganizacion = idOrganizacion;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getIdSucursal()
/*  54:    */   {
/*  55: 61 */     return this.idSucursal;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setIdSucursal(int idSucursal)
/*  59:    */   {
/*  60: 65 */     this.idSucursal = idSucursal;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Integer getIdCiudad()
/*  64:    */   {
/*  65: 69 */     return this.idCiudad;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdCiudad(Integer idCiudad)
/*  69:    */   {
/*  70: 73 */     this.idCiudad = idCiudad;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getCodigo()
/*  74:    */   {
/*  75: 77 */     return this.codigo;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setCodigo(String codigo)
/*  79:    */   {
/*  80: 81 */     this.codigo = codigo;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getNombre()
/*  84:    */   {
/*  85: 85 */     return this.nombre;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setNombre(String nombre)
/*  89:    */   {
/*  90: 89 */     this.nombre = nombre;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Boolean getActivo()
/*  94:    */   {
/*  95: 93 */     return this.activo;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setActivo(Boolean activo)
/*  99:    */   {
/* 100: 97 */     this.activo = activo;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Boolean getPredeterminado()
/* 104:    */   {
/* 105:101 */     return this.predeterminado;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setPredeterminado(Boolean predeterminado)
/* 109:    */   {
/* 110:105 */     this.predeterminado = predeterminado;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setHashCode(int hashCode)
/* 114:    */   {
/* 115:109 */     this.hashCode = hashCode;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public ProvinciaResponseDto getProvincia()
/* 119:    */   {
/* 120:113 */     return this.provincia;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setProvincia(ProvinciaResponseDto provincia)
/* 124:    */   {
/* 125:117 */     this.provincia = provincia;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String getCodigoPostal()
/* 129:    */   {
/* 130:121 */     return this.codigoPostal;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setCodigoPostal(String codigoPostal)
/* 134:    */   {
/* 135:125 */     this.codigoPostal = codigoPostal;
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.CiudadResponseDto
 * JD-Core Version:    0.7.0.1
 */