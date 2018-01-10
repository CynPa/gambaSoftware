/*   1:    */ package com.asinfo.as2.rs.inventario.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ 
/*   9:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*  10:    */ public class ProductoResponseDto
/*  11:    */   implements Serializable
/*  12:    */ {
/*  13:    */   private Integer idProducto;
/*  14:    */   private String codigo;
/*  15:    */   private String nombre;
/*  16:    */   private Integer idOrganizacion;
/*  17:    */   private Integer idSucursal;
/*  18:    */   private String categoriaProducto;
/*  19:    */   private String subcategoriaProducto;
/*  20:    */   private String codigoAlterno;
/*  21:    */   private String codigoComercial;
/*  22:    */   private String codigoBarras;
/*  23:    */   private String tipoProducto;
/*  24:    */   private boolean indicadorImpuestos;
/*  25:    */   private String unidad;
/*  26:    */   private String unidadVenta;
/*  27: 42 */   private Integer multiploPedido = Integer.valueOf(0);
/*  28:    */   private String presentacionProducto;
/*  29: 46 */   private BigDecimal cantidadUnidades = BigDecimal.ONE;
/*  30:    */   private boolean indicadorPorcentajeIce;
/*  31: 50 */   private BigDecimal ice = BigDecimal.ZERO;
/*  32: 52 */   private String codigoIce = "";
/*  33: 54 */   private BigDecimal cantidadProduccion = BigDecimal.ZERO;
/*  34: 56 */   private List<ListaProductoMaterialResponseDto> listaProductoMaterial = new ArrayList();
/*  35: 58 */   private int hashCode = 0;
/*  36:    */   
/*  37:    */   public int getHashCode()
/*  38:    */   {
/*  39: 61 */     this.hashCode = hashCode();
/*  40: 62 */     return this.hashCode;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int hashCode()
/*  44:    */   {
/*  45: 67 */     int hash = 1;
/*  46: 68 */     hash += hash * 17 + (this.idProducto + "").hashCode();
/*  47: 69 */     hash += hash * 22 + (this.codigo + "").hashCode();
/*  48: 70 */     hash += hash * 41 + (this.nombre + "").hashCode();
/*  49: 71 */     hash += hash * 36 + (this.idOrganizacion + "").hashCode();
/*  50: 72 */     hash += hash * 36 + (this.idSucursal + "").hashCode();
/*  51: 73 */     hash += hash * 40 + (this.categoriaProducto + "").hashCode();
/*  52: 74 */     hash += hash * 3 + (this.subcategoriaProducto + "").hashCode();
/*  53: 75 */     hash += hash * 6 + (this.codigoAlterno + "").hashCode();
/*  54: 76 */     hash += hash * 22 + (this.codigoComercial + "").hashCode();
/*  55: 77 */     hash += hash * 10 + (this.codigoBarras + "").hashCode();
/*  56: 78 */     hash += hash * 11 + (this.tipoProducto + "").hashCode();
/*  57: 79 */     hash += hash * 14 + (this.indicadorImpuestos + "").hashCode();
/*  58: 80 */     hash += hash * 13 + (this.unidad + "").hashCode();
/*  59: 81 */     hash += hash * 44 + (this.unidadVenta + "").hashCode();
/*  60: 82 */     hash += hash * 24 + (this.multiploPedido + "").hashCode();
/*  61: 83 */     hash += hash * 4 + (this.presentacionProducto + "").hashCode();
/*  62: 84 */     hash += hash * 7 + (this.cantidadUnidades + "").hashCode();
/*  63: 85 */     hash += hash * 88 + (this.indicadorPorcentajeIce + "").hashCode();
/*  64: 86 */     hash += hash * 90 + (this.ice + "").hashCode();
/*  65: 87 */     hash += hash * 28 + (this.codigoIce + "").hashCode();
/*  66: 88 */     hash += hash * 28 + (this.cantidadProduccion + "").hashCode();
/*  67: 90 */     for (ListaProductoMaterialResponseDto listaProductoMaterialResponseDto : this.listaProductoMaterial) {
/*  68: 91 */       hash += hash * 28 + (listaProductoMaterialResponseDto + "").hashCode();
/*  69:    */     }
/*  70: 94 */     return hash;
/*  71:    */   }
/*  72:    */   
/*  73: 97 */   private List<ImpuestoResponseDto> listaImpuesto = new ArrayList();
/*  74:    */   
/*  75:    */   public Integer getIdProducto()
/*  76:    */   {
/*  77:100 */     return this.idProducto;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdProducto(Integer idProducto)
/*  81:    */   {
/*  82:104 */     this.idProducto = idProducto;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getCodigo()
/*  86:    */   {
/*  87:108 */     return this.codigo;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setCodigo(String codigo)
/*  91:    */   {
/*  92:112 */     this.codigo = codigo;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getNombre()
/*  96:    */   {
/*  97:116 */     return this.nombre;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setNombre(String nombre)
/* 101:    */   {
/* 102:120 */     this.nombre = nombre;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Integer getIdOrganizacion()
/* 106:    */   {
/* 107:124 */     return this.idOrganizacion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setIdOrganizacion(Integer idOrganizacion)
/* 111:    */   {
/* 112:128 */     this.idOrganizacion = idOrganizacion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String getCategoriaProducto()
/* 116:    */   {
/* 117:132 */     return this.categoriaProducto;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setCategoriaProducto(String categoriaProducto)
/* 121:    */   {
/* 122:136 */     this.categoriaProducto = categoriaProducto;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String getSubcategoriaProducto()
/* 126:    */   {
/* 127:140 */     return this.subcategoriaProducto;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setSubcategoriaProducto(String subcategoriaProducto)
/* 131:    */   {
/* 132:144 */     this.subcategoriaProducto = subcategoriaProducto;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String getCodigoAlterno()
/* 136:    */   {
/* 137:148 */     return this.codigoAlterno;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setCodigoAlterno(String codigoAlterno)
/* 141:    */   {
/* 142:152 */     this.codigoAlterno = codigoAlterno;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String getCodigoComercial()
/* 146:    */   {
/* 147:156 */     return this.codigoComercial;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setCodigoComercial(String codigoComercial)
/* 151:    */   {
/* 152:160 */     this.codigoComercial = codigoComercial;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String getCodigoBarras()
/* 156:    */   {
/* 157:164 */     return this.codigoBarras;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setCodigoBarras(String codigoBarras)
/* 161:    */   {
/* 162:168 */     this.codigoBarras = codigoBarras;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String getTipoProducto()
/* 166:    */   {
/* 167:172 */     return this.tipoProducto;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setTipoProducto(String tipoProducto)
/* 171:    */   {
/* 172:176 */     this.tipoProducto = tipoProducto;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public boolean isIndicadorImpuestos()
/* 176:    */   {
/* 177:180 */     return this.indicadorImpuestos;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setIndicadorImpuestos(boolean indicadorImpuestos)
/* 181:    */   {
/* 182:184 */     this.indicadorImpuestos = indicadorImpuestos;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String getUnidad()
/* 186:    */   {
/* 187:188 */     return this.unidad;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setUnidad(String unidad)
/* 191:    */   {
/* 192:192 */     this.unidad = unidad;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String getUnidadVenta()
/* 196:    */   {
/* 197:196 */     return this.unidadVenta;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setUnidadVenta(String unidadVenta)
/* 201:    */   {
/* 202:200 */     this.unidadVenta = unidadVenta;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public Integer getMultiploPedido()
/* 206:    */   {
/* 207:204 */     return this.multiploPedido;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setMultiploPedido(Integer multiploPedido)
/* 211:    */   {
/* 212:208 */     this.multiploPedido = multiploPedido;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public String getPresentacionProducto()
/* 216:    */   {
/* 217:212 */     return this.presentacionProducto;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setPresentacionProducto(String presentacionProducto)
/* 221:    */   {
/* 222:216 */     this.presentacionProducto = presentacionProducto;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public BigDecimal getCantidadUnidades()
/* 226:    */   {
/* 227:220 */     return this.cantidadUnidades;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setCantidadUnidades(BigDecimal cantidadUnidades)
/* 231:    */   {
/* 232:224 */     this.cantidadUnidades = cantidadUnidades;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public List<ImpuestoResponseDto> getListaImpuesto()
/* 236:    */   {
/* 237:228 */     return this.listaImpuesto;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setListaImpuesto(List<ImpuestoResponseDto> listaImpuesto)
/* 241:    */   {
/* 242:232 */     this.listaImpuesto = listaImpuesto;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public boolean isIndicadorPorcentajeIce()
/* 246:    */   {
/* 247:236 */     return this.indicadorPorcentajeIce;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setIndicadorPorcentajeIce(boolean indicadorPorcentajeIce)
/* 251:    */   {
/* 252:240 */     this.indicadorPorcentajeIce = indicadorPorcentajeIce;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public BigDecimal getIce()
/* 256:    */   {
/* 257:244 */     return this.ice;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setIce(BigDecimal ice)
/* 261:    */   {
/* 262:248 */     this.ice = ice;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public String getCodigoIce()
/* 266:    */   {
/* 267:252 */     return this.codigoIce;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setCodigoIce(String codigoIce)
/* 271:    */   {
/* 272:256 */     this.codigoIce = codigoIce;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public BigDecimal getCantidadProduccion()
/* 276:    */   {
/* 277:260 */     return this.cantidadProduccion;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setCantidadProduccion(BigDecimal cantidadProduccion)
/* 281:    */   {
/* 282:264 */     this.cantidadProduccion = cantidadProduccion;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public List<ListaProductoMaterialResponseDto> getListaProductoMaterial()
/* 286:    */   {
/* 287:268 */     return this.listaProductoMaterial;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setListaProductoMaterial(List<ListaProductoMaterialResponseDto> listaProductoMaterial)
/* 291:    */   {
/* 292:272 */     this.listaProductoMaterial = listaProductoMaterial;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public Integer getIdSucursal()
/* 296:    */   {
/* 297:276 */     return this.idSucursal;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setIdSucursal(Integer idSucursal)
/* 301:    */   {
/* 302:280 */     this.idSucursal = idSucursal;
/* 303:    */   }
/* 304:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.dto.ProductoResponseDto
 * JD-Core Version:    0.7.0.1
 */