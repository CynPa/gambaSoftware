/*   1:    */ package com.asinfo.as2.rs.financiero.cobros.dto;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ 
/*   9:    */ public class LiquidacionAnticipoClienteResponseDto
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   private int idOrganizacion;
/*  13:    */   private int idSucursal;
/*  14:    */   private Integer idLiquidacionAnticipoCliente;
/*  15:    */   private String numeroAS2;
/*  16:    */   private Estado estado;
/*  17:    */   private BigDecimal valor;
/*  18:    */   private Integer idAnticipoCliente;
/*  19:    */   private String fecha;
/*  20:    */   private String descripcion;
/*  21:    */   private Integer idFacturaCliente;
/*  22:    */   private int idCabeceraAs2;
/*  23:    */   private Integer idDispositivoSincronizacion;
/*  24: 37 */   private List<DetalleLiquidacionAnticipoResponseDto> listaDetalleLiquidacionAnticipoResponseDto = new ArrayList();
/*  25: 39 */   private int hashCode = 0;
/*  26:    */   
/*  27:    */   public int getHashCode()
/*  28:    */   {
/*  29: 42 */     return this.hashCode;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public int getIdOrganizacion()
/*  33:    */   {
/*  34: 46 */     return this.idOrganizacion;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setIdOrganizacion(int idOrganizacion)
/*  38:    */   {
/*  39: 50 */     this.idOrganizacion = idOrganizacion;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int getIdSucursal()
/*  43:    */   {
/*  44: 54 */     return this.idSucursal;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setIdSucursal(int idSucursal)
/*  48:    */   {
/*  49: 58 */     this.idSucursal = idSucursal;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Integer getIdLiquidacionAnticipoCliente()
/*  53:    */   {
/*  54: 62 */     return this.idLiquidacionAnticipoCliente;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setIdLiquidacionAnticipoCliente(Integer idLiquidacionAnticipoCliente)
/*  58:    */   {
/*  59: 66 */     this.idLiquidacionAnticipoCliente = idLiquidacionAnticipoCliente;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getNumeroAS2()
/*  63:    */   {
/*  64: 70 */     return this.numeroAS2;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setNumeroAS2(String numeroAS2)
/*  68:    */   {
/*  69: 74 */     this.numeroAS2 = numeroAS2;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Estado getEstado()
/*  73:    */   {
/*  74: 78 */     return this.estado;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setEstado(Estado estado)
/*  78:    */   {
/*  79: 82 */     this.estado = estado;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public BigDecimal getValor()
/*  83:    */   {
/*  84: 86 */     return this.valor;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setValor(BigDecimal valor)
/*  88:    */   {
/*  89: 90 */     this.valor = valor;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Integer getIdAnticipoCliente()
/*  93:    */   {
/*  94: 94 */     return this.idAnticipoCliente;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setIdAnticipoCliente(Integer idAnticipoCliente)
/*  98:    */   {
/*  99: 98 */     this.idAnticipoCliente = idAnticipoCliente;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getFecha()
/* 103:    */   {
/* 104:102 */     return this.fecha;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setFecha(String fecha)
/* 108:    */   {
/* 109:106 */     this.fecha = fecha;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getDescripcion()
/* 113:    */   {
/* 114:110 */     return this.descripcion;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setDescripcion(String descripcion)
/* 118:    */   {
/* 119:114 */     this.descripcion = descripcion;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Integer getIdFacturaCliente()
/* 123:    */   {
/* 124:118 */     return this.idFacturaCliente;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setIdFacturaCliente(Integer idFacturaCliente)
/* 128:    */   {
/* 129:122 */     this.idFacturaCliente = idFacturaCliente;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public int getIdCabeceraAs2()
/* 133:    */   {
/* 134:126 */     return this.idCabeceraAs2;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setIdCabeceraAs2(int idCabeceraAs2)
/* 138:    */   {
/* 139:130 */     this.idCabeceraAs2 = idCabeceraAs2;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Integer getIdDispositivoSincronizacion()
/* 143:    */   {
/* 144:134 */     return this.idDispositivoSincronizacion;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 148:    */   {
/* 149:138 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public List<DetalleLiquidacionAnticipoResponseDto> getListaDetalleLiquidacionAnticipoResponseDto()
/* 153:    */   {
/* 154:142 */     return this.listaDetalleLiquidacionAnticipoResponseDto;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setListaDetalleLiquidacionAnticipoResponseDto(List<DetalleLiquidacionAnticipoResponseDto> listaDetalleLiquidacionAnticipoResponseDto)
/* 158:    */   {
/* 159:147 */     this.listaDetalleLiquidacionAnticipoResponseDto = listaDetalleLiquidacionAnticipoResponseDto;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setHashCode(int hashCode)
/* 163:    */   {
/* 164:151 */     this.hashCode = hashCode;
/* 165:    */   }
/* 166:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.dto.LiquidacionAnticipoClienteResponseDto
 * JD-Core Version:    0.7.0.1
 */