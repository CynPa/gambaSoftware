/*   1:    */ package com.asinfo.as2.rs.seguridad.dto;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ 
/*   7:    */ public class OrganizacionResponseDto
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private Integer idOrganizacion;
/*  11:    */   private String identificacion;
/*  12:    */   private String representanteLegal;
/*  13:    */   private String razonSocial;
/*  14:    */   private String imagen;
/*  15:    */   private Boolean activo;
/*  16: 22 */   private List<SucursalResponseDto> listaSucursal = new ArrayList();
/*  17: 24 */   private int hashCode = 0;
/*  18:    */   
/*  19:    */   public int getHashCode()
/*  20:    */   {
/*  21: 27 */     this.hashCode = hashCode();
/*  22: 28 */     return this.hashCode;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public int hashCode()
/*  26:    */   {
/*  27: 33 */     int hash = 1;
/*  28: 34 */     hash += hash * 55 + (this.idOrganizacion + "").hashCode();
/*  29: 35 */     hash += hash * 17 + (this.identificacion + "").hashCode();
/*  30: 36 */     hash += hash * 22 + (this.representanteLegal + "").hashCode();
/*  31: 37 */     hash += hash * 41 + (this.razonSocial + "").hashCode();
/*  32: 38 */     hash += hash * 36 + (this.imagen + "").hashCode();
/*  33: 39 */     hash += hash * 11 + (this.activo + "").hashCode();
/*  34: 41 */     for (SucursalResponseDto sucursalResponseDto : this.listaSucursal) {
/*  35: 42 */       hash += hash * 24 + (sucursalResponseDto != null ? sucursalResponseDto.hashCode() : 0);
/*  36:    */     }
/*  37: 45 */     return hash;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Integer getIdOrganizacion()
/*  41:    */   {
/*  42: 52 */     return this.idOrganizacion;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setIdOrganizacion(Integer idOrganizacion)
/*  46:    */   {
/*  47: 60 */     this.idOrganizacion = idOrganizacion;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getIdentificacion()
/*  51:    */   {
/*  52: 67 */     return this.identificacion;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setIdentificacion(String identificacion)
/*  56:    */   {
/*  57: 75 */     this.identificacion = identificacion;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String getRepresentanteLegal()
/*  61:    */   {
/*  62: 82 */     return this.representanteLegal;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setRepresentanteLegal(String representanteLegal)
/*  66:    */   {
/*  67: 90 */     this.representanteLegal = representanteLegal;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getRazonSocial()
/*  71:    */   {
/*  72: 97 */     return this.razonSocial;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setRazonSocial(String razonSocial)
/*  76:    */   {
/*  77:105 */     this.razonSocial = razonSocial;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getImagen()
/*  81:    */   {
/*  82:112 */     return this.imagen;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setImagen(String imagen)
/*  86:    */   {
/*  87:120 */     this.imagen = imagen;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Boolean getActivo()
/*  91:    */   {
/*  92:127 */     return this.activo;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setActivo(Boolean activo)
/*  96:    */   {
/*  97:135 */     this.activo = activo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public List<SucursalResponseDto> getListaSucursal()
/* 101:    */   {
/* 102:139 */     return this.listaSucursal;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setListaSucursal(List<SucursalResponseDto> listaSucursal)
/* 106:    */   {
/* 107:143 */     this.listaSucursal = listaSucursal;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setHashCode(int hashCode)
/* 111:    */   {
/* 112:151 */     this.hashCode = hashCode;
/* 113:    */   }
/* 114:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.seguridad.dto.OrganizacionResponseDto
 * JD-Core Version:    0.7.0.1
 */