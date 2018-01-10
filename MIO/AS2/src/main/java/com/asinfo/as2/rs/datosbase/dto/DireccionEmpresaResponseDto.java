/*   1:    */ package com.asinfo.as2.rs.datosbase.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ 
/*   6:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   7:    */ public class DireccionEmpresaResponseDto
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private int idOrganizacion;
/*  11:    */   private int idSucursal;
/*  12:    */   private Integer idDireccionCliente;
/*  13:    */   private int idEmpresa;
/*  14:    */   private boolean indicadorDireccionPrincipal;
/*  15: 21 */   private boolean activo = true;
/*  16:    */   private CiudadResponseDto ciudad;
/*  17:    */   private UbicacionResponseDto ubicacion;
/*  18:    */   private Integer idDispositivoSincronizacion;
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
/*  32: 41 */     hash += hash * 11 + (this.idDireccionCliente + "").hashCode();
/*  33: 42 */     hash += hash * 13 + (this.idEmpresa + "").hashCode();
/*  34: 43 */     hash += hash * 15 + (this.indicadorDireccionPrincipal + "").hashCode();
/*  35: 44 */     hash += hash * 25 + (this.ciudad != null ? this.ciudad.getHashCode() : 0);
/*  36: 45 */     hash += hash * 39 + (this.ubicacion != null ? this.ubicacion.getHashCode() : 0);
/*  37: 46 */     return hash;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int getIdOrganizacion()
/*  41:    */   {
/*  42: 50 */     return this.idOrganizacion;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setIdOrganizacion(int idOrganizacion)
/*  46:    */   {
/*  47: 54 */     this.idOrganizacion = idOrganizacion;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getIdSucursal()
/*  51:    */   {
/*  52: 58 */     return this.idSucursal;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setIdSucursal(int idSucursal)
/*  56:    */   {
/*  57: 62 */     this.idSucursal = idSucursal;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Integer getIdDireccionCliente()
/*  61:    */   {
/*  62: 66 */     return this.idDireccionCliente;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdDireccionCliente(Integer idDireccionCliente)
/*  66:    */   {
/*  67: 70 */     this.idDireccionCliente = idDireccionCliente;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdEmpresa()
/*  71:    */   {
/*  72: 74 */     return this.idEmpresa;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdEmpresa(int idEmpresa)
/*  76:    */   {
/*  77: 78 */     this.idEmpresa = idEmpresa;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean isIndicadorDireccionPrincipal()
/*  81:    */   {
/*  82: 82 */     return this.indicadorDireccionPrincipal;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIndicadorDireccionPrincipal(boolean indicadorDireccionPrincipal)
/*  86:    */   {
/*  87: 86 */     this.indicadorDireccionPrincipal = indicadorDireccionPrincipal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean isActivo()
/*  91:    */   {
/*  92: 90 */     return this.activo;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setActivo(boolean activo)
/*  96:    */   {
/*  97: 94 */     this.activo = activo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public CiudadResponseDto getCiudad()
/* 101:    */   {
/* 102: 98 */     return this.ciudad;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setCiudad(CiudadResponseDto ciudad)
/* 106:    */   {
/* 107:102 */     this.ciudad = ciudad;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public UbicacionResponseDto getUbicacion()
/* 111:    */   {
/* 112:106 */     return this.ubicacion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setUbicacion(UbicacionResponseDto ubicacion)
/* 116:    */   {
/* 117:110 */     this.ubicacion = ubicacion;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setHashCode(int hashCode)
/* 121:    */   {
/* 122:114 */     this.hashCode = hashCode;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Integer getIdDispositivoSincronizacion()
/* 126:    */   {
/* 127:118 */     return this.idDispositivoSincronizacion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 131:    */   {
/* 132:122 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 133:    */   }
/* 134:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.DireccionEmpresaResponseDto
 * JD-Core Version:    0.7.0.1
 */