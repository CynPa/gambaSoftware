/*   1:    */ package com.asinfo.as2.rs.datosbase.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ 
/*   8:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   9:    */ public class ClienteResponseDto
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   private int idCliente;
/*  13:    */   private Integer idEmpresa;
/*  14:    */   private int idOrganizacion;
/*  15:    */   private int idSucursal;
/*  16:    */   private String nombreFiscal;
/*  17:    */   private String nombreComercial;
/*  18:    */   private String identificacion;
/*  19:    */   private String tipoIdentificacion;
/*  20:    */   private String direccion;
/*  21:    */   private Integer idClientePadre;
/*  22:    */   private String horarioVisita;
/*  23:    */   private String horarioDespacho;
/*  24:    */   private String diasVisita;
/*  25:    */   private Integer idListaPrecios;
/*  26:    */   private Integer idListaDescuentos;
/*  27:    */   private String codigoSubcliente;
/*  28:    */   private String nombreSubcliente;
/*  29:    */   private String email;
/*  30: 49 */   private boolean activo = true;
/*  31:    */   private Integer idDispositivoSincronizacion;
/*  32: 53 */   private List<DireccionEmpresaResponseDto> listaDireccionEmpresa = new ArrayList();
/*  33:    */   private TipoIdentificacionResponseDto tipoIdentificacionResponse;
/*  34:    */   private Integer diasDespacho;
/*  35:    */   private Boolean excentoImpuestos;
/*  36: 61 */   private int hashCode = 0;
/*  37:    */   
/*  38:    */   public int getHashCode()
/*  39:    */   {
/*  40: 64 */     this.hashCode = hashCode();
/*  41: 65 */     return this.hashCode;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int hashCode()
/*  45:    */   {
/*  46: 70 */     int hash = 1;
/*  47: 71 */     hash += hash * 17 + (this.idCliente + "").hashCode();
/*  48: 72 */     hash += hash * 22 + (this.idEmpresa + "").hashCode();
/*  49: 73 */     hash += hash * 41 + (this.idOrganizacion + "").hashCode();
/*  50: 74 */     hash += hash * 36 + (this.nombreFiscal + "").hashCode();
/*  51: 75 */     hash += hash * 11 + (this.identificacion + "").hashCode();
/*  52: 76 */     hash += hash * 15 + (this.tipoIdentificacion + "").hashCode();
/*  53: 77 */     hash += hash * 13 + (this.direccion + "").hashCode();
/*  54: 78 */     hash += hash * 12 + (this.idClientePadre + "").hashCode();
/*  55: 79 */     hash += hash * 10 + (this.horarioVisita + "").hashCode();
/*  56: 80 */     hash += hash * 7 + (this.horarioDespacho + "").hashCode();
/*  57: 81 */     hash += hash * 4 + (this.idListaPrecios + "").hashCode();
/*  58: 82 */     hash += hash * 3 + (this.codigoSubcliente + "").hashCode();
/*  59: 83 */     hash += hash * 9 + (this.nombreSubcliente + "").hashCode();
/*  60: 84 */     hash += hash * 18 + (this.idListaDescuentos + "").hashCode();
/*  61: 85 */     hash += hash * 18 + (this.diasDespacho + "").hashCode();
/*  62: 86 */     hash += hash * 18 + (this.excentoImpuestos + "").hashCode();
/*  63: 88 */     for (DireccionEmpresaResponseDto direccionEmpresaResponseDto : this.listaDireccionEmpresa) {
/*  64: 89 */       hash += hash * 23 + (direccionEmpresaResponseDto != null ? direccionEmpresaResponseDto.hashCode() : 0);
/*  65:    */     }
/*  66: 92 */     hash += hash * 26 + (this.tipoIdentificacionResponse != null ? this.tipoIdentificacionResponse.hashCode() : 0);
/*  67:    */     
/*  68: 94 */     hash += hash * 18 + (this.email + "").hashCode();
/*  69:    */     
/*  70: 96 */     return hash;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdCliente()
/*  74:    */   {
/*  75:100 */     return this.idCliente;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdCliente(int idCliente)
/*  79:    */   {
/*  80:104 */     this.idCliente = idCliente;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdOrganizacion()
/*  84:    */   {
/*  85:108 */     return this.idOrganizacion;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdOrganizacion(int idOrganizacion)
/*  89:    */   {
/*  90:112 */     this.idOrganizacion = idOrganizacion;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getNombreFiscal()
/*  94:    */   {
/*  95:116 */     return this.nombreFiscal;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setNombreFiscal(String nombreFiscal)
/*  99:    */   {
/* 100:120 */     this.nombreFiscal = nombreFiscal;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getIdentificacion()
/* 104:    */   {
/* 105:124 */     return this.identificacion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setIdentificacion(String identificacion)
/* 109:    */   {
/* 110:128 */     this.identificacion = identificacion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getDireccion()
/* 114:    */   {
/* 115:132 */     return this.direccion;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setDireccion(String direccion)
/* 119:    */   {
/* 120:136 */     this.direccion = direccion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Integer getIdClientePadre()
/* 124:    */   {
/* 125:140 */     return this.idClientePadre;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setIdClientePadre(Integer idClientePadre)
/* 129:    */   {
/* 130:144 */     this.idClientePadre = idClientePadre;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String getHorarioVisita()
/* 134:    */   {
/* 135:148 */     return this.horarioVisita;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setHorarioVisita(String horarioVisita)
/* 139:    */   {
/* 140:152 */     this.horarioVisita = horarioVisita;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String getHorarioDespacho()
/* 144:    */   {
/* 145:156 */     return this.horarioDespacho;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setHorarioDespacho(String horarioDespacho)
/* 149:    */   {
/* 150:160 */     this.horarioDespacho = horarioDespacho;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String getTipoIdentificacion()
/* 154:    */   {
/* 155:164 */     return this.tipoIdentificacion;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setTipoIdentificacion(String tipoIdentificacion)
/* 159:    */   {
/* 160:168 */     this.tipoIdentificacion = tipoIdentificacion;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String getDiasVisita()
/* 164:    */   {
/* 165:172 */     return this.diasVisita;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setDiasVisita(String diasVisita)
/* 169:    */   {
/* 170:176 */     this.diasVisita = diasVisita;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Integer getIdListaPrecios()
/* 174:    */   {
/* 175:180 */     return this.idListaPrecios;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setIdListaPrecios(Integer idListaPrecios)
/* 179:    */   {
/* 180:184 */     this.idListaPrecios = idListaPrecios;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public String getCodigoSubcliente()
/* 184:    */   {
/* 185:188 */     return this.codigoSubcliente;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setCodigoSubcliente(String codigoSubcliente)
/* 189:    */   {
/* 190:192 */     this.codigoSubcliente = codigoSubcliente;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public String getNombreSubcliente()
/* 194:    */   {
/* 195:196 */     return this.nombreSubcliente;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setNombreSubcliente(String nombreSubcliente)
/* 199:    */   {
/* 200:200 */     this.nombreSubcliente = nombreSubcliente;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public Integer getIdEmpresa()
/* 204:    */   {
/* 205:204 */     return this.idEmpresa;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setIdEmpresa(Integer idEmpresa)
/* 209:    */   {
/* 210:208 */     this.idEmpresa = idEmpresa;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public boolean isActivo()
/* 214:    */   {
/* 215:212 */     return this.activo;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setActivo(boolean activo)
/* 219:    */   {
/* 220:216 */     this.activo = activo;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public int getIdSucursal()
/* 224:    */   {
/* 225:220 */     return this.idSucursal;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setIdSucursal(int idSucursal)
/* 229:    */   {
/* 230:224 */     this.idSucursal = idSucursal;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public String getNombreComercial()
/* 234:    */   {
/* 235:228 */     return this.nombreComercial;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setNombreComercial(String nombreComercial)
/* 239:    */   {
/* 240:232 */     this.nombreComercial = nombreComercial;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public List<DireccionEmpresaResponseDto> getListaDireccionEmpresa()
/* 244:    */   {
/* 245:236 */     return this.listaDireccionEmpresa;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setListaDireccionEmpresa(List<DireccionEmpresaResponseDto> listaDireccionEmpresa)
/* 249:    */   {
/* 250:240 */     this.listaDireccionEmpresa = listaDireccionEmpresa;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setHashCode(int hashCode)
/* 254:    */   {
/* 255:244 */     this.hashCode = hashCode;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public TipoIdentificacionResponseDto getTipoIdentificacionResponse()
/* 259:    */   {
/* 260:248 */     return this.tipoIdentificacionResponse;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setTipoIdentificacionResponse(TipoIdentificacionResponseDto tipoIdentificacionResponse)
/* 264:    */   {
/* 265:252 */     this.tipoIdentificacionResponse = tipoIdentificacionResponse;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public Integer getIdListaDescuentos()
/* 269:    */   {
/* 270:256 */     return this.idListaDescuentos;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setIdListaDescuentos(Integer idListaDescuentos)
/* 274:    */   {
/* 275:260 */     this.idListaDescuentos = idListaDescuentos;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public String getEmail()
/* 279:    */   {
/* 280:264 */     return this.email;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setEmail(String email)
/* 284:    */   {
/* 285:268 */     this.email = email;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public Integer getIdDispositivoSincronizacion()
/* 289:    */   {
/* 290:272 */     return this.idDispositivoSincronizacion;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 294:    */   {
/* 295:276 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public Integer getDiasDespacho()
/* 299:    */   {
/* 300:280 */     return this.diasDespacho;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void setDiasDespacho(Integer diasDespacho)
/* 304:    */   {
/* 305:284 */     this.diasDespacho = diasDespacho;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public Boolean getExcentoImpuestos()
/* 309:    */   {
/* 310:288 */     return this.excentoImpuestos;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setExcentoImpuestos(Boolean excentoImpuestos)
/* 314:    */   {
/* 315:292 */     this.excentoImpuestos = excentoImpuestos;
/* 316:    */   }
/* 317:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.ClienteResponseDto
 * JD-Core Version:    0.7.0.1
 */