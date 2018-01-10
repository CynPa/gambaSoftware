/*   1:    */ package com.asinfo.as2.rs.seguridad.dto;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.rs.datosbase.dto.BodegaResponseDto;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ 
/*   8:    */ public class SucursalResponseDto
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private Integer idOrganizacion;
/*  12:    */   private Integer idSucursal;
/*  13:    */   private String codigo;
/*  14:    */   private String nombre;
/*  15:    */   private Boolean activo;
/*  16:    */   private Boolean predeterminado;
/*  17: 24 */   private List<BodegaResponseDto> listaBodega = new ArrayList();
/*  18: 26 */   private List<PuntoVentaResponseDto> listaPuntoVenta = new ArrayList();
/*  19: 28 */   private int hashCode = 0;
/*  20:    */   
/*  21:    */   public int getHashCode()
/*  22:    */   {
/*  23: 31 */     this.hashCode = hashCode();
/*  24: 32 */     return this.hashCode;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int hashCode()
/*  28:    */   {
/*  29: 37 */     int hash = 1;
/*  30: 38 */     hash += hash * 55 + (this.idOrganizacion + "").hashCode();
/*  31: 39 */     hash += hash * 17 + (this.idSucursal + "").hashCode();
/*  32: 40 */     hash += hash * 22 + (this.codigo + "").hashCode();
/*  33: 41 */     hash += hash * 41 + (this.nombre + "").hashCode();
/*  34: 42 */     hash += hash * 36 + (this.activo + "").hashCode();
/*  35: 43 */     hash += hash * 11 + (this.predeterminado + "").hashCode();
/*  36: 45 */     for (PuntoVentaResponseDto puntoVentaResponseDto : this.listaPuntoVenta) {
/*  37: 46 */       hash += hash * 33 + (puntoVentaResponseDto != null ? puntoVentaResponseDto.hashCode() : 0);
/*  38:    */     }
/*  39: 49 */     for (BodegaResponseDto bodegaResponseDto : this.listaBodega) {
/*  40: 50 */       hash += hash * 23 + (bodegaResponseDto != null ? bodegaResponseDto.hashCode() : 0);
/*  41:    */     }
/*  42: 53 */     return hash;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Integer getIdSucursal()
/*  46:    */   {
/*  47: 60 */     return this.idSucursal;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setIdSucursal(Integer idSucursal)
/*  51:    */   {
/*  52: 68 */     this.idSucursal = idSucursal;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String getCodigo()
/*  56:    */   {
/*  57: 75 */     return this.codigo;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setCodigo(String codigo)
/*  61:    */   {
/*  62: 83 */     this.codigo = codigo;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getNombre()
/*  66:    */   {
/*  67: 90 */     return this.nombre;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setNombre(String nombre)
/*  71:    */   {
/*  72: 98 */     this.nombre = nombre;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public List<PuntoVentaResponseDto> getListaPuntoVenta()
/*  76:    */   {
/*  77:105 */     return this.listaPuntoVenta;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setListaPuntoVenta(List<PuntoVentaResponseDto> listaPuntoVenta)
/*  81:    */   {
/*  82:113 */     this.listaPuntoVenta = listaPuntoVenta;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Integer getIdOrganizacion()
/*  86:    */   {
/*  87:117 */     return this.idOrganizacion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdOrganizacion(Integer idOrganizacion)
/*  91:    */   {
/*  92:121 */     this.idOrganizacion = idOrganizacion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Boolean getActivo()
/*  96:    */   {
/*  97:125 */     return this.activo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setActivo(Boolean activo)
/* 101:    */   {
/* 102:129 */     this.activo = activo;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Boolean getPredeterminado()
/* 106:    */   {
/* 107:133 */     return this.predeterminado;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setPredeterminado(Boolean predeterminado)
/* 111:    */   {
/* 112:137 */     this.predeterminado = predeterminado;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public List<BodegaResponseDto> getListaBodega()
/* 116:    */   {
/* 117:141 */     return this.listaBodega;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setListaBodega(List<BodegaResponseDto> listaBodega)
/* 121:    */   {
/* 122:145 */     this.listaBodega = listaBodega;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setHashCode(int hashCode)
/* 126:    */   {
/* 127:149 */     this.hashCode = hashCode;
/* 128:    */   }
/* 129:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.seguridad.dto.SucursalResponseDto
 * JD-Core Version:    0.7.0.1
 */