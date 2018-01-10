/*   1:    */ package com.asinfo.as2.rs.datosbase.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ 
/*   6:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   7:    */ public class CatalogoGenericoResponseDto
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private Integer idCatalogo;
/*  11:    */   private Integer idOrganizacion;
/*  12:    */   private Integer idSucursal;
/*  13:    */   private String codigo;
/*  14:    */   private String nombre;
/*  15:    */   private String descripcion;
/*  16:    */   private Boolean activo;
/*  17:    */   private Boolean predeterminado;
/*  18: 19 */   private int hashCode = 0;
/*  19:    */   
/*  20:    */   public int getHashCode()
/*  21:    */   {
/*  22: 22 */     this.hashCode = hashCode();
/*  23: 23 */     return this.hashCode;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int hashCode()
/*  27:    */   {
/*  28: 28 */     int hash = 1;
/*  29: 29 */     hash += hash * 41 + (this.idOrganizacion + "").hashCode();
/*  30: 30 */     hash += hash * 36 + (this.idSucursal + "").hashCode();
/*  31: 31 */     hash += hash * 11 + (this.idCatalogo + "").hashCode();
/*  32: 32 */     hash += hash * 13 + (this.codigo + "").hashCode();
/*  33: 33 */     hash += hash * 15 + (this.descripcion + "").hashCode();
/*  34: 34 */     hash += hash * 15 + (this.predeterminado + "").hashCode();
/*  35: 35 */     hash += hash * 15 + (this.nombre + "").hashCode();
/*  36: 36 */     hash += hash * 15 + (this.activo + "").hashCode();
/*  37: 37 */     return hash;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Integer getIdCatalogo()
/*  41:    */   {
/*  42: 41 */     return this.idCatalogo;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setIdCatalogo(Integer idCatalogo)
/*  46:    */   {
/*  47: 45 */     this.idCatalogo = idCatalogo;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Integer getIdOrganizacion()
/*  51:    */   {
/*  52: 49 */     return this.idOrganizacion;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setIdOrganizacion(Integer idOrganizacion)
/*  56:    */   {
/*  57: 53 */     this.idOrganizacion = idOrganizacion;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Integer getIdSucursal()
/*  61:    */   {
/*  62: 57 */     return this.idSucursal;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdSucursal(Integer idSucursal)
/*  66:    */   {
/*  67: 61 */     this.idSucursal = idSucursal;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getCodigo()
/*  71:    */   {
/*  72: 65 */     return this.codigo;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setCodigo(String codigo)
/*  76:    */   {
/*  77: 69 */     this.codigo = codigo;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getNombre()
/*  81:    */   {
/*  82: 73 */     return this.nombre;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setNombre(String nombre)
/*  86:    */   {
/*  87: 77 */     this.nombre = nombre;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getDescripcion()
/*  91:    */   {
/*  92: 81 */     return this.descripcion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setDescripcion(String descripcion)
/*  96:    */   {
/*  97: 85 */     this.descripcion = descripcion;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Boolean getActivo()
/* 101:    */   {
/* 102: 89 */     return this.activo;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setActivo(Boolean activo)
/* 106:    */   {
/* 107: 93 */     this.activo = activo;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Boolean getPredeterminado()
/* 111:    */   {
/* 112: 97 */     return this.predeterminado;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setPredeterminado(Boolean predeterminado)
/* 116:    */   {
/* 117:101 */     this.predeterminado = predeterminado;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setHashCode(int hashCode)
/* 121:    */   {
/* 122:105 */     this.hashCode = hashCode;
/* 123:    */   }
/* 124:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.CatalogoGenericoResponseDto
 * JD-Core Version:    0.7.0.1
 */