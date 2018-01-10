/*   1:    */ package com.asinfo.as2.rs.ventas.dto;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.OrigenEnum;
/*   4:    */ import com.asinfo.as2.rs.financiero.cobros.dto.SaldosFacturaResponseDto;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ 
/*   9:    */ public class FacturaClienteResponseDto
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   private int idOrganizacion;
/*  13:    */   private int idSucursal;
/*  14:    */   private Integer idFacturaCliente;
/*  15:    */   private String numero;
/*  16:    */   private String fecha;
/*  17:    */   private String direccionFactura;
/*  18:    */   private Integer idEmpresa;
/*  19:    */   private Integer idPuntoVenta;
/*  20:    */   private Integer idDireccionEmpresa;
/*  21:    */   private Integer idDispositivoSincronizacion;
/*  22:    */   private String descripcion;
/*  23:    */   private String urlApp;
/*  24:    */   private String codigoMovil;
/*  25:    */   private Integer idMotivoNotaCreditoCliente;
/*  26:    */   private Integer idFacturaPadre;
/*  27:    */   private Boolean indicadorDevolucion;
/*  28:    */   private Integer idUsuarioCreacion;
/*  29:    */   private String nombreUsuarioCreacion;
/*  30:    */   private OrigenEnum origen;
/*  31: 51 */   private List<DetalleFacturaClienteResponseDto> detalleFacturaClienteResponse = new ArrayList();
/*  32: 53 */   private List<SaldosFacturaResponseDto> listaSaldosFacturaResponseDto = new ArrayList();
/*  33: 55 */   private int hashCode = 0;
/*  34:    */   
/*  35:    */   public int getHashCode()
/*  36:    */   {
/*  37: 58 */     this.hashCode = hashCode();
/*  38: 59 */     return this.hashCode;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int hashCode()
/*  42:    */   {
/*  43: 64 */     int hash = 1;
/*  44: 65 */     hash += hash * 41 + (this.idOrganizacion + "").hashCode();
/*  45: 66 */     hash += hash * 45 + (this.idSucursal + "").hashCode();
/*  46: 67 */     hash += hash * 16 + (this.idFacturaCliente + "").hashCode();
/*  47: 68 */     hash += hash * 56 + (this.fecha + "").hashCode();
/*  48: 69 */     hash += hash * 53 + (this.direccionFactura + "").hashCode();
/*  49: 70 */     hash += hash * 25 + (this.idEmpresa + "").hashCode();
/*  50: 71 */     hash += hash * 14 + (this.idPuntoVenta + "").hashCode();
/*  51: 72 */     hash += hash * 13 + (this.idDireccionEmpresa + "").hashCode();
/*  52: 73 */     hash += hash * 13 + (this.descripcion + "").hashCode();
/*  53: 74 */     hash += hash * 13 + (this.descripcion + "").hashCode();
/*  54: 75 */     hash += hash * 13 + (this.idMotivoNotaCreditoCliente + "").hashCode();
/*  55: 76 */     hash += hash * 13 + (this.idFacturaPadre + "").hashCode();
/*  56: 77 */     hash += hash * 13 + (this.codigoMovil + "").hashCode();
/*  57: 78 */     hash += hash * 13 + (this.indicadorDevolucion + "").hashCode();
/*  58: 80 */     for (DetalleFacturaClienteResponseDto detalleFacturaClienteResponseDto : this.detalleFacturaClienteResponse) {
/*  59: 81 */       hash += hash * 24 + (detalleFacturaClienteResponseDto != null ? detalleFacturaClienteResponseDto.hashCode() : 0);
/*  60:    */     }
/*  61: 83 */     for (SaldosFacturaResponseDto saldosFacturaResponseDto : this.listaSaldosFacturaResponseDto) {
/*  62: 84 */       hash += hash * 24 + (saldosFacturaResponseDto != null ? saldosFacturaResponseDto.hashCode() : 0);
/*  63:    */     }
/*  64: 87 */     return hash;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getIdOrganizacion()
/*  68:    */   {
/*  69: 91 */     return this.idOrganizacion;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setIdOrganizacion(int idOrganizacion)
/*  73:    */   {
/*  74: 95 */     this.idOrganizacion = idOrganizacion;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getIdSucursal()
/*  78:    */   {
/*  79: 99 */     return this.idSucursal;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIdSucursal(int idSucursal)
/*  83:    */   {
/*  84:103 */     this.idSucursal = idSucursal;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Integer getIdFacturaCliente()
/*  88:    */   {
/*  89:107 */     return this.idFacturaCliente;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setIdFacturaCliente(Integer idFacturaCliente)
/*  93:    */   {
/*  94:111 */     this.idFacturaCliente = idFacturaCliente;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String getFecha()
/*  98:    */   {
/*  99:115 */     return this.fecha;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setFecha(String fecha)
/* 103:    */   {
/* 104:119 */     this.fecha = fecha;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getDireccionFactura()
/* 108:    */   {
/* 109:123 */     return this.direccionFactura;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setDireccionFactura(String direccionFactura)
/* 113:    */   {
/* 114:127 */     this.direccionFactura = direccionFactura;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Integer getIdEmpresa()
/* 118:    */   {
/* 119:131 */     return this.idEmpresa;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setIdEmpresa(Integer idEmpresa)
/* 123:    */   {
/* 124:135 */     this.idEmpresa = idEmpresa;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Integer getIdPuntoVenta()
/* 128:    */   {
/* 129:139 */     return this.idPuntoVenta;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setIdPuntoVenta(Integer idPuntoVenta)
/* 133:    */   {
/* 134:143 */     this.idPuntoVenta = idPuntoVenta;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Integer getIdDireccionEmpresa()
/* 138:    */   {
/* 139:147 */     return this.idDireccionEmpresa;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setIdDireccionEmpresa(Integer idDireccionEmpresa)
/* 143:    */   {
/* 144:151 */     this.idDireccionEmpresa = idDireccionEmpresa;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public List<DetalleFacturaClienteResponseDto> getDetalleFacturaClienteResponse()
/* 148:    */   {
/* 149:155 */     return this.detalleFacturaClienteResponse;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setDetalleFacturaClienteResponse(List<DetalleFacturaClienteResponseDto> detalleFacturaClienteResponse)
/* 153:    */   {
/* 154:159 */     this.detalleFacturaClienteResponse = detalleFacturaClienteResponse;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Integer getIdDispositivoSincronizacion()
/* 158:    */   {
/* 159:163 */     return this.idDispositivoSincronizacion;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 163:    */   {
/* 164:167 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String getDescripcion()
/* 168:    */   {
/* 169:171 */     return this.descripcion;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setDescripcion(String descripcion)
/* 173:    */   {
/* 174:175 */     this.descripcion = descripcion;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public List<SaldosFacturaResponseDto> getListaSaldosFacturaResponseDto()
/* 178:    */   {
/* 179:179 */     return this.listaSaldosFacturaResponseDto;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setListaSaldosFacturaResponseDto(List<SaldosFacturaResponseDto> listaSaldosFacturaResponseDto)
/* 183:    */   {
/* 184:183 */     this.listaSaldosFacturaResponseDto = listaSaldosFacturaResponseDto;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public String getNumero()
/* 188:    */   {
/* 189:187 */     return this.numero;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setNumero(String numero)
/* 193:    */   {
/* 194:191 */     this.numero = numero;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String getUrlApp()
/* 198:    */   {
/* 199:195 */     return this.urlApp;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setUrlApp(String urlApp)
/* 203:    */   {
/* 204:199 */     this.urlApp = urlApp;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public Integer getIdMotivoNotaCreditoCliente()
/* 208:    */   {
/* 209:203 */     return this.idMotivoNotaCreditoCliente;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setIdMotivoNotaCreditoCliente(Integer idMotivoNotaCreditoCliente)
/* 213:    */   {
/* 214:207 */     this.idMotivoNotaCreditoCliente = idMotivoNotaCreditoCliente;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public Integer getIdFacturaPadre()
/* 218:    */   {
/* 219:211 */     return this.idFacturaPadre;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setIdFacturaPadre(Integer idFacturaPadre)
/* 223:    */   {
/* 224:215 */     this.idFacturaPadre = idFacturaPadre;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public String getCodigoMovil()
/* 228:    */   {
/* 229:219 */     return this.codigoMovil;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setCodigoMovil(String codigoMovil)
/* 233:    */   {
/* 234:223 */     this.codigoMovil = codigoMovil;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Boolean getIndicadorDevolucion()
/* 238:    */   {
/* 239:227 */     return this.indicadorDevolucion;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setIndicadorDevolucion(Boolean indicadorDevolucion)
/* 243:    */   {
/* 244:231 */     this.indicadorDevolucion = indicadorDevolucion;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public Integer getIdUsuarioCreacion()
/* 248:    */   {
/* 249:235 */     return this.idUsuarioCreacion;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setIdUsuarioCreacion(Integer idUsuarioCreacion)
/* 253:    */   {
/* 254:239 */     this.idUsuarioCreacion = idUsuarioCreacion;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public String getNombreUsuarioCreacion()
/* 258:    */   {
/* 259:243 */     return this.nombreUsuarioCreacion;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setNombreUsuarioCreacion(String nombreUsuarioCreacion)
/* 263:    */   {
/* 264:247 */     this.nombreUsuarioCreacion = nombreUsuarioCreacion;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public OrigenEnum getOrigen()
/* 268:    */   {
/* 269:251 */     return this.origen;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setOrigen(OrigenEnum origen)
/* 273:    */   {
/* 274:255 */     this.origen = origen;
/* 275:    */   }
/* 276:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.ventas.dto.FacturaClienteResponseDto
 * JD-Core Version:    0.7.0.1
 */