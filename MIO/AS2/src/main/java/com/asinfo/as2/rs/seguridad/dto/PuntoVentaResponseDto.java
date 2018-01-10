/*   1:    */ package com.asinfo.as2.rs.seguridad.dto;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.rs.datosbase.dto.CajaResponseDto;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ 
/*   8:    */ public class PuntoVentaResponseDto
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private Integer idOrganizacion;
/*  12:    */   private Integer idPuntoVenta;
/*  13:    */   private String codigo;
/*  14:    */   private String nombre;
/*  15:    */   private Integer idSucursal;
/*  16:    */   private Boolean activo;
/*  17:    */   private Boolean predeterminado;
/*  18: 26 */   private List<CajaResponseDto> listaCaja = new ArrayList();
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
/*  30: 38 */     hash += hash * 41 + (this.idOrganizacion + "").hashCode();
/*  31: 39 */     hash += hash * 56 + (this.idPuntoVenta + "").hashCode();
/*  32: 40 */     hash += hash * 36 + (this.idSucursal + "").hashCode();
/*  33: 41 */     hash += hash * 15 + (this.codigo + "").hashCode();
/*  34: 42 */     hash += hash * 13 + (this.nombre + "").hashCode();
/*  35: 43 */     hash += hash * 12 + (this.activo + "").hashCode();
/*  36: 44 */     hash += hash * 10 + (this.predeterminado + "").hashCode();
/*  37: 46 */     for (CajaResponseDto cajaResponseDto : this.listaCaja) {
/*  38: 47 */       hash += hash * 23 + (cajaResponseDto != null ? cajaResponseDto.hashCode() : 0);
/*  39:    */     }
/*  40: 50 */     return hash;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Integer getIdPuntoVenta()
/*  44:    */   {
/*  45: 57 */     return this.idPuntoVenta;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setIdPuntoVenta(Integer idPuntoVenta)
/*  49:    */   {
/*  50: 65 */     this.idPuntoVenta = idPuntoVenta;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String getCodigo()
/*  54:    */   {
/*  55: 72 */     return this.codigo;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setCodigo(String codigo)
/*  59:    */   {
/*  60: 80 */     this.codigo = codigo;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getNombre()
/*  64:    */   {
/*  65: 87 */     return this.nombre;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setNombre(String nombre)
/*  69:    */   {
/*  70: 95 */     this.nombre = nombre;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Integer getIdOrganizacion()
/*  74:    */   {
/*  75: 99 */     return this.idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdOrganizacion(Integer idOrganizacion)
/*  79:    */   {
/*  80:103 */     this.idOrganizacion = idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Integer getIdSucursal()
/*  84:    */   {
/*  85:107 */     return this.idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdSucursal(Integer idSucursal)
/*  89:    */   {
/*  90:111 */     this.idSucursal = idSucursal;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Boolean getActivo()
/*  94:    */   {
/*  95:115 */     return this.activo;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setActivo(Boolean activo)
/*  99:    */   {
/* 100:119 */     this.activo = activo;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Boolean getPredeterminado()
/* 104:    */   {
/* 105:123 */     return this.predeterminado;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setPredeterminado(Boolean predeterminado)
/* 109:    */   {
/* 110:127 */     this.predeterminado = predeterminado;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public List<CajaResponseDto> getListaCaja()
/* 114:    */   {
/* 115:131 */     return this.listaCaja;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setListaCaja(List<CajaResponseDto> listaCaja)
/* 119:    */   {
/* 120:135 */     this.listaCaja = listaCaja;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setHashCode(int hashCode)
/* 124:    */   {
/* 125:139 */     this.hashCode = hashCode;
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.seguridad.dto.PuntoVentaResponseDto
 * JD-Core Version:    0.7.0.1
 */