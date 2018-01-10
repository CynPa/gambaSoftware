/*   1:    */ package com.asinfo.as2.rs.financiero.cobros.dto;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public class MotivoNotaCreditoClienteResponseDto
/*   6:    */   implements Serializable
/*   7:    */ {
/*   8:    */   private Integer idMotivoNotaCreditoCliente;
/*   9:    */   private Integer idOrganizacion;
/*  10:    */   private Integer idSucursal;
/*  11:    */   private String codigo;
/*  12:    */   private String nombre;
/*  13:    */   private String descripcion;
/*  14:    */   private boolean activo;
/*  15:    */   private boolean predeterminado;
/*  16:    */   private boolean indicadorReversaTransformacion;
/*  17: 26 */   private int hashCode = 0;
/*  18:    */   
/*  19:    */   public int getHashCode()
/*  20:    */   {
/*  21: 29 */     this.hashCode = hashCode();
/*  22: 30 */     return this.hashCode;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public int hashCode()
/*  26:    */   {
/*  27: 35 */     int hash = 1;
/*  28: 36 */     hash += hash * 17 + (this.idMotivoNotaCreditoCliente + "").hashCode();
/*  29: 37 */     hash += hash * 22 + (this.idOrganizacion + "").hashCode();
/*  30: 38 */     hash += hash * 22 + (this.idSucursal + "").hashCode();
/*  31: 39 */     hash += hash * 41 + this.codigo.hashCode();
/*  32: 40 */     hash += hash * 36 + this.nombre.hashCode();
/*  33: 41 */     hash += hash * 77 + this.descripcion.hashCode();
/*  34: 42 */     hash += hash * 86 + (this.activo + "").hashCode();
/*  35: 43 */     hash += hash * 64 + (this.predeterminado + "").hashCode();
/*  36: 44 */     hash += hash * 96 + (this.indicadorReversaTransformacion + "").hashCode();
/*  37:    */     
/*  38: 46 */     return hash;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getCodigo()
/*  42:    */   {
/*  43: 50 */     return this.codigo;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setCodigo(String codigo)
/*  47:    */   {
/*  48: 54 */     this.codigo = codigo;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getNombre()
/*  52:    */   {
/*  53: 58 */     return this.nombre;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setNombre(String nombre)
/*  57:    */   {
/*  58: 62 */     this.nombre = nombre;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean isActivo()
/*  62:    */   {
/*  63: 66 */     return this.activo;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setActivo(boolean activo)
/*  67:    */   {
/*  68: 70 */     this.activo = activo;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Integer getIdOrganizacion()
/*  72:    */   {
/*  73: 74 */     return this.idOrganizacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIdOrganizacion(Integer idOrganizacion)
/*  77:    */   {
/*  78: 78 */     this.idOrganizacion = idOrganizacion;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setHashCode(int hashCode)
/*  82:    */   {
/*  83: 82 */     this.hashCode = hashCode;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Integer getIdMotivoNotaCreditoCliente()
/*  87:    */   {
/*  88: 86 */     return this.idMotivoNotaCreditoCliente;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIdMotivoNotaCreditoCliente(Integer idMotivoNotaCreditoCliente)
/*  92:    */   {
/*  93: 90 */     this.idMotivoNotaCreditoCliente = idMotivoNotaCreditoCliente;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Integer getIdSucursal()
/*  97:    */   {
/*  98: 94 */     return this.idSucursal;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setIdSucursal(Integer idSucursal)
/* 102:    */   {
/* 103: 98 */     this.idSucursal = idSucursal;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getDescripcion()
/* 107:    */   {
/* 108:102 */     return this.descripcion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setDescripcion(String descripcion)
/* 112:    */   {
/* 113:106 */     this.descripcion = descripcion;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean isPredeterminado()
/* 117:    */   {
/* 118:110 */     return this.predeterminado;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setPredeterminado(boolean predeterminado)
/* 122:    */   {
/* 123:114 */     this.predeterminado = predeterminado;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public boolean isIndicadorReversaTransformacion()
/* 127:    */   {
/* 128:118 */     return this.indicadorReversaTransformacion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setIndicadorReversaTransformacion(boolean indicadorReversaTransformacion)
/* 132:    */   {
/* 133:122 */     this.indicadorReversaTransformacion = indicadorReversaTransformacion;
/* 134:    */   }
/* 135:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.dto.MotivoNotaCreditoClienteResponseDto
 * JD-Core Version:    0.7.0.1
 */