/*   1:    */ package com.asinfo.as2.rs.financiero.cobros.dto;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ 
/*   8:    */ public class CrearCobroRequestDto
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = 1L;
/*  12:    */   private Integer idCobro;
/*  13:    */   private String numeroCobro;
/*  14:    */   private String codigoMovil;
/*  15:    */   private String nombreUsuario;
/*  16:    */   private Integer idUsuario;
/*  17:    */   private Integer idOrganizacion;
/*  18:    */   private Integer idSucursal;
/*  19:    */   private Integer idPuntoVenta;
/*  20:    */   private String fecha;
/*  21:    */   private Integer idCliente;
/*  22: 35 */   private BigDecimal valorTotal = BigDecimal.ZERO;
/*  23: 37 */   private String nota = "";
/*  24:    */   private String urlApp;
/*  25:    */   private Integer idDispositivoSincronizacion;
/*  26: 43 */   private List<DetalleCobroRequestDto> listaDetalleCobro = new ArrayList();
/*  27: 45 */   private List<DetalleFormaCobroRequestDto> listaDetalleFormaCobro = new ArrayList();
/*  28:    */   
/*  29:    */   public String getCodigoMovil()
/*  30:    */   {
/*  31: 48 */     return this.codigoMovil;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setCodigoMovil(String codigoMovil)
/*  35:    */   {
/*  36: 52 */     this.codigoMovil = codigoMovil;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Integer getIdUsuario()
/*  40:    */   {
/*  41: 56 */     return this.idUsuario;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setIdUsuario(Integer idUsuario)
/*  45:    */   {
/*  46: 60 */     this.idUsuario = idUsuario;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Integer getIdOrganizacion()
/*  50:    */   {
/*  51: 64 */     return this.idOrganizacion;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIdOrganizacion(Integer idOrganizacion)
/*  55:    */   {
/*  56: 68 */     this.idOrganizacion = idOrganizacion;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Integer getIdSucursal()
/*  60:    */   {
/*  61: 72 */     return this.idSucursal;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIdSucursal(Integer idSucursal)
/*  65:    */   {
/*  66: 76 */     this.idSucursal = idSucursal;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Integer getIdPuntoVenta()
/*  70:    */   {
/*  71: 80 */     return this.idPuntoVenta;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdPuntoVenta(Integer idPuntoVenta)
/*  75:    */   {
/*  76: 84 */     this.idPuntoVenta = idPuntoVenta;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getFecha()
/*  80:    */   {
/*  81: 88 */     return this.fecha;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setFecha(String fecha)
/*  85:    */   {
/*  86: 92 */     this.fecha = fecha;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Integer getIdCliente()
/*  90:    */   {
/*  91: 96 */     return this.idCliente;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdCliente(Integer idCliente)
/*  95:    */   {
/*  96:100 */     this.idCliente = idCliente;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getNota()
/* 100:    */   {
/* 101:104 */     return this.nota;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setNota(String nota)
/* 105:    */   {
/* 106:108 */     this.nota = nota;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public List<DetalleCobroRequestDto> getListaDetalleCobro()
/* 110:    */   {
/* 111:112 */     return this.listaDetalleCobro;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setListaDetalleCobro(List<DetalleCobroRequestDto> listaDetalleCobro)
/* 115:    */   {
/* 116:116 */     this.listaDetalleCobro = listaDetalleCobro;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public List<DetalleFormaCobroRequestDto> getListaDetalleFormaCobro()
/* 120:    */   {
/* 121:120 */     return this.listaDetalleFormaCobro;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setListaDetalleFormaCobro(List<DetalleFormaCobroRequestDto> listaDetalleFormaCobro)
/* 125:    */   {
/* 126:124 */     this.listaDetalleFormaCobro = listaDetalleFormaCobro;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public BigDecimal getValorTotal()
/* 130:    */   {
/* 131:128 */     return this.valorTotal;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setValorTotal(BigDecimal valorTotal)
/* 135:    */   {
/* 136:132 */     this.valorTotal = valorTotal;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String getUrlApp()
/* 140:    */   {
/* 141:136 */     return this.urlApp;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setUrlApp(String urlApp)
/* 145:    */   {
/* 146:140 */     this.urlApp = urlApp;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Integer getIdDispositivoSincronizacion()
/* 150:    */   {
/* 151:144 */     return this.idDispositivoSincronizacion;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 155:    */   {
/* 156:148 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Integer getIdCobro()
/* 160:    */   {
/* 161:152 */     return this.idCobro;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setIdCobro(Integer idCobro)
/* 165:    */   {
/* 166:156 */     this.idCobro = idCobro;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String getNumeroCobro()
/* 170:    */   {
/* 171:160 */     return this.numeroCobro;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setNumeroCobro(String numeroCobro)
/* 175:    */   {
/* 176:164 */     this.numeroCobro = numeroCobro;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String getNombreUsuario()
/* 180:    */   {
/* 181:168 */     return this.nombreUsuario;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setNombreUsuario(String nombreUsuario)
/* 185:    */   {
/* 186:172 */     this.nombreUsuario = nombreUsuario;
/* 187:    */   }
/* 188:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.financiero.cobros.dto.CrearCobroRequestDto
 * JD-Core Version:    0.7.0.1
 */