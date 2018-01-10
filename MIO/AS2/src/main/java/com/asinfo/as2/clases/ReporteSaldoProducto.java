/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.TableGenerator;
/*  12:    */ import javax.persistence.Transient;
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="tmp_reporte_saldo_producto")
/*  16:    */ public class ReporteSaldoProducto
/*  17:    */ {
/*  18:    */   @Id
/*  19:    */   @TableGenerator(name="reporte_saldo_producto", initialValue=0, allocationSize=50)
/*  20:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="reporte_saldo_producto")
/*  21:    */   @Column(name="id_tmp_reporte_saldo_producto")
/*  22:    */   private Integer idReporteSaldoProducto;
/*  23:    */   @Transient
/*  24:    */   private Integer idProducto;
/*  25:    */   @Transient
/*  26:    */   private String codigoProducto;
/*  27:    */   @Transient
/*  28:    */   private String codigoAlternoProducto;
/*  29:    */   @Transient
/*  30:    */   private String nombreProducto;
/*  31:    */   @Transient
/*  32:    */   private Integer idSubcategoriaProducto;
/*  33:    */   @Transient
/*  34:    */   private String codigoSubcategoriaProducto;
/*  35:    */   @Transient
/*  36:    */   private String nombreSubcategoriaProducto;
/*  37:    */   @Transient
/*  38:    */   private Integer idCategoriaProducto;
/*  39:    */   @Transient
/*  40:    */   private String codigoCategoriaProducto;
/*  41:    */   @Transient
/*  42:    */   private String nombreCategoriaProducto;
/*  43:    */   @Transient
/*  44:    */   private Integer idBodega;
/*  45:    */   @Transient
/*  46:    */   private String codigoBodega;
/*  47:    */   @Transient
/*  48:    */   private String nombreBodega;
/*  49:    */   @Transient
/*  50:    */   private Date fecha;
/*  51:    */   @Transient
/*  52: 90 */   private BigDecimal saldo = BigDecimal.ZERO;
/*  53:    */   @Transient
/*  54: 93 */   private BigDecimal saldoComprometido = BigDecimal.ZERO;
/*  55:    */   @Transient
/*  56: 96 */   private BigDecimal costoUnitario = BigDecimal.ZERO;
/*  57:    */   @Transient
/*  58:    */   private Integer idUnidad;
/*  59:    */   @Transient
/*  60:    */   private String codigoUnidad;
/*  61:    */   @Transient
/*  62:    */   private String nombreUnidad;
/*  63:    */   @Transient
/*  64:    */   private Integer idUnidadVenta;
/*  65:    */   @Transient
/*  66:    */   private String codigoUnidadVenta;
/*  67:    */   @Transient
/*  68:    */   private String nombreUnidadVenta;
/*  69:    */   @Transient
/*  70:    */   private Integer idUnidadAlmacenamiento;
/*  71:    */   @Transient
/*  72:    */   private String codigoUnidadAlmacenamiento;
/*  73:    */   @Transient
/*  74:    */   private String nombreUnidadAlmacenamiento;
/*  75:    */   @Transient
/*  76:    */   private Integer idLote;
/*  77:    */   @Transient
/*  78:    */   private String codigoLote;
/*  79:    */   @Transient
/*  80:    */   private String valorAtributo1;
/*  81:    */   @Transient
/*  82:    */   private String valorAtributo2;
/*  83:    */   @Transient
/*  84:    */   private String valorAtributo3;
/*  85:    */   @Transient
/*  86:    */   private String valorAtributo4;
/*  87:    */   @Transient
/*  88:    */   private String valorAtributo5;
/*  89:    */   @Transient
/*  90:    */   private String valorAtributo6;
/*  91:    */   @Transient
/*  92:    */   private String valorAtributo7;
/*  93:    */   @Transient
/*  94:    */   private String valorAtributo8;
/*  95:    */   @Transient
/*  96:    */   private String valorAtributo9;
/*  97:    */   @Transient
/*  98:    */   private String valorAtributo10;
/*  99:    */   @Transient
/* 100:    */   private String nombreComercialProducto;
/* 101:    */   
/* 102:    */   public ReporteSaldoProducto() {}
/* 103:    */   
/* 104:    */   public ReporteSaldoProducto(Integer idProducto, String codigoProducto, String codigoAlternoProducto, String nombreProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idBodega, String codigoBodega, String nombreBodega, Date fecha, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento)
/* 105:    */   {
/* 106:201 */     this.idProducto = idProducto;
/* 107:202 */     this.codigoProducto = codigoProducto;
/* 108:203 */     this.codigoAlternoProducto = codigoAlternoProducto;
/* 109:204 */     this.nombreProducto = nombreProducto;
/* 110:205 */     this.idSubcategoriaProducto = idSubcategoriaProducto;
/* 111:206 */     this.codigoSubcategoriaProducto = codigoSubcategoriaProducto;
/* 112:207 */     this.nombreSubcategoriaProducto = nombreSubcategoriaProducto;
/* 113:208 */     this.idCategoriaProducto = idCategoriaProducto;
/* 114:209 */     this.codigoCategoriaProducto = codigoCategoriaProducto;
/* 115:210 */     this.nombreCategoriaProducto = nombreCategoriaProducto;
/* 116:211 */     this.idBodega = idBodega;
/* 117:212 */     this.codigoBodega = codigoBodega;
/* 118:213 */     this.nombreBodega = nombreBodega;
/* 119:214 */     this.fecha = fecha;
/* 120:    */     
/* 121:216 */     this.idUnidad = idUnidad;
/* 122:217 */     this.codigoUnidad = codigoUnidad;
/* 123:218 */     this.nombreUnidad = nombreUnidad;
/* 124:    */     
/* 125:220 */     this.idUnidadVenta = idUnidadVenta;
/* 126:221 */     this.codigoUnidadVenta = codigoUnidadVenta;
/* 127:222 */     this.nombreUnidadVenta = nombreUnidadVenta;
/* 128:    */     
/* 129:224 */     this.idUnidadAlmacenamiento = idUnidadAlmacenamiento;
/* 130:225 */     this.codigoUnidadAlmacenamiento = codigoUnidadAlmacenamiento;
/* 131:226 */     this.nombreUnidadAlmacenamiento = nombreUnidadAlmacenamiento;
/* 132:    */     
/* 133:228 */     this.saldo = BigDecimal.ZERO;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public ReporteSaldoProducto(Integer idProducto, String codigoProducto, String codigoAlternoProducto, String nombreProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idBodega, String codigoBodega, String nombreBodega, Date fecha, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, String codigoLote, Integer idLote, BigDecimal saldo)
/* 137:    */   {
/* 138:264 */     this(idProducto, codigoProducto, codigoAlternoProducto, nombreProducto, idSubcategoriaProducto, codigoSubcategoriaProducto, nombreSubcategoriaProducto, idCategoriaProducto, codigoCategoriaProducto, nombreCategoriaProducto, idBodega, codigoBodega, nombreBodega, fecha, idUnidad, codigoUnidad, nombreUnidad, idUnidadVenta, codigoUnidadVenta, nombreUnidadVenta, idUnidadAlmacenamiento, codigoUnidadAlmacenamiento, nombreUnidadAlmacenamiento);
/* 139:    */     
/* 140:    */ 
/* 141:    */ 
/* 142:268 */     this.codigoLote = codigoLote;
/* 143:269 */     this.idLote = idLote;
/* 144:270 */     this.saldo = saldo;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public ReporteSaldoProducto(Integer idProducto, String codigoProducto, String codigoAlternoProducto, String nombreProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idBodega, String codigoBodega, String nombreBodega, Date fecha, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, String valorAtributo1, String valorAtributo2)
/* 148:    */   {
/* 149:279 */     this(idProducto, codigoProducto, codigoAlternoProducto, nombreProducto, idSubcategoriaProducto, codigoSubcategoriaProducto, nombreSubcategoriaProducto, idCategoriaProducto, codigoCategoriaProducto, nombreCategoriaProducto, idBodega, codigoBodega, nombreBodega, fecha, idUnidad, codigoUnidad, nombreUnidad, idUnidadVenta, codigoUnidadVenta, nombreUnidadVenta, idUnidadAlmacenamiento, codigoUnidadAlmacenamiento, nombreUnidadAlmacenamiento);
/* 150:    */     
/* 151:    */ 
/* 152:    */ 
/* 153:283 */     this.valorAtributo1 = valorAtributo1;
/* 154:284 */     this.valorAtributo2 = valorAtributo2;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public ReporteSaldoProducto(Integer idProducto, String codigoProducto, String codigoAlternoProducto, String nombreProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idBodega, String codigoBodega, String nombreBodega, Date fecha, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, String valorAtributo1)
/* 158:    */   {
/* 159:292 */     this(idProducto, codigoProducto, codigoAlternoProducto, nombreProducto, idSubcategoriaProducto, codigoSubcategoriaProducto, nombreSubcategoriaProducto, idCategoriaProducto, codigoCategoriaProducto, nombreCategoriaProducto, idBodega, codigoBodega, nombreBodega, fecha, idUnidad, codigoUnidad, nombreUnidad, idUnidadVenta, codigoUnidadVenta, nombreUnidadVenta, idUnidadAlmacenamiento, codigoUnidadAlmacenamiento, nombreUnidadAlmacenamiento);
/* 160:    */     
/* 161:    */ 
/* 162:    */ 
/* 163:296 */     this.valorAtributo1 = valorAtributo1;
/* 164:297 */     this.valorAtributo2 = valorAtributo1;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public ReporteSaldoProducto(Integer idProducto, String codigoProducto, String codigoAlternoProducto, String nombreProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idBodega, String codigoBodega, String nombreBodega, Date fecha, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, String valorAtributo1, String valorAtributo2, String nombreComercialProducto)
/* 168:    */   {
/* 169:306 */     this(idProducto, codigoProducto, codigoAlternoProducto, nombreProducto, idSubcategoriaProducto, codigoSubcategoriaProducto, nombreSubcategoriaProducto, idCategoriaProducto, codigoCategoriaProducto, nombreCategoriaProducto, idBodega, codigoBodega, nombreBodega, fecha, idUnidad, codigoUnidad, nombreUnidad, idUnidadVenta, codigoUnidadVenta, nombreUnidadVenta, idUnidadAlmacenamiento, codigoUnidadAlmacenamiento, nombreUnidadAlmacenamiento, valorAtributo1, valorAtributo2);
/* 170:    */     
/* 171:    */ 
/* 172:    */ 
/* 173:310 */     this.nombreComercialProducto = nombreComercialProducto;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public ReporteSaldoProducto(Integer idProducto, String codigoProducto, String codigoAlternoProducto, String nombreProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idBodega, String codigoBodega, String nombreBodega, Date fecha, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, String codigoLote, Integer idLote, BigDecimal saldo, String valorAtributo1)
/* 177:    */   {
/* 178:318 */     this(idProducto, codigoProducto, codigoAlternoProducto, nombreProducto, idSubcategoriaProducto, codigoSubcategoriaProducto, nombreSubcategoriaProducto, idCategoriaProducto, codigoCategoriaProducto, nombreCategoriaProducto, idBodega, codigoBodega, nombreBodega, fecha, idUnidad, codigoUnidad, nombreUnidad, idUnidadVenta, codigoUnidadVenta, nombreUnidadVenta, idUnidadAlmacenamiento, codigoUnidadAlmacenamiento, nombreUnidadAlmacenamiento, codigoLote, idLote, saldo);
/* 179:    */     
/* 180:    */ 
/* 181:    */ 
/* 182:322 */     this.valorAtributo1 = valorAtributo1;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public ReporteSaldoProducto(Integer idProducto, String codigoProducto, String codigoAlternoProducto, String nombreProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idBodega, String codigoBodega, String nombreBodega, Date fecha, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, String codigoLote, Integer idLote, BigDecimal saldo, String valorAtributo1, String valorAtributo2)
/* 186:    */   {
/* 187:331 */     this(idProducto, codigoProducto, codigoAlternoProducto, nombreProducto, idSubcategoriaProducto, codigoSubcategoriaProducto, nombreSubcategoriaProducto, idCategoriaProducto, codigoCategoriaProducto, nombreCategoriaProducto, idBodega, codigoBodega, nombreBodega, fecha, idUnidad, codigoUnidad, nombreUnidad, idUnidadVenta, codigoUnidadVenta, nombreUnidadVenta, idUnidadAlmacenamiento, codigoUnidadAlmacenamiento, nombreUnidadAlmacenamiento, codigoLote, idLote, saldo);
/* 188:    */     
/* 189:    */ 
/* 190:    */ 
/* 191:335 */     this.valorAtributo1 = valorAtributo1;
/* 192:336 */     this.valorAtributo2 = valorAtributo2;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public ReporteSaldoProducto(Integer idProducto, String codigoProducto, String codigoAlternoProducto, String nombreProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idBodega, String codigoBodega, String nombreBodega, Date fecha, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, String codigoLote, Integer idLote, BigDecimal saldo, String valorAtributo1, String valorAtributo2, String valorAtributo3)
/* 196:    */   {
/* 197:345 */     this(idProducto, codigoProducto, codigoAlternoProducto, nombreProducto, idSubcategoriaProducto, codigoSubcategoriaProducto, nombreSubcategoriaProducto, idCategoriaProducto, codigoCategoriaProducto, nombreCategoriaProducto, idBodega, codigoBodega, nombreBodega, fecha, idUnidad, codigoUnidad, nombreUnidad, idUnidadVenta, codigoUnidadVenta, nombreUnidadVenta, idUnidadAlmacenamiento, codigoUnidadAlmacenamiento, nombreUnidadAlmacenamiento, codigoLote, idLote, saldo);
/* 198:    */     
/* 199:    */ 
/* 200:    */ 
/* 201:349 */     this.valorAtributo1 = valorAtributo1;
/* 202:350 */     this.valorAtributo2 = valorAtributo2;
/* 203:351 */     this.valorAtributo3 = valorAtributo3;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public ReporteSaldoProducto(Integer idProducto, String codigoProducto, String codigoAlternoProducto, String nombreProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idBodega, String codigoBodega, String nombreBodega, Date fecha, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, String codigoLote, Integer idLote, BigDecimal saldo, String valorAtributo1, String valorAtributo2, String valorAtributo3, String valorAtributo4)
/* 207:    */   {
/* 208:360 */     this(idProducto, codigoProducto, codigoAlternoProducto, nombreProducto, idSubcategoriaProducto, codigoSubcategoriaProducto, nombreSubcategoriaProducto, idCategoriaProducto, codigoCategoriaProducto, nombreCategoriaProducto, idBodega, codigoBodega, nombreBodega, fecha, idUnidad, codigoUnidad, nombreUnidad, idUnidadVenta, codigoUnidadVenta, nombreUnidadVenta, idUnidadAlmacenamiento, codigoUnidadAlmacenamiento, nombreUnidadAlmacenamiento, codigoLote, idLote, saldo);
/* 209:    */     
/* 210:    */ 
/* 211:    */ 
/* 212:364 */     this.valorAtributo1 = valorAtributo1;
/* 213:365 */     this.valorAtributo2 = valorAtributo2;
/* 214:366 */     this.valorAtributo3 = valorAtributo3;
/* 215:367 */     this.valorAtributo4 = valorAtributo4;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public ReporteSaldoProducto(Integer idProducto, String codigoProducto, String codigoAlternoProducto, String nombreProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idBodega, String codigoBodega, String nombreBodega, Date fecha, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, String codigoLote, Integer idLote, BigDecimal saldo, String valorAtributo1, String valorAtributo2, String valorAtributo3, String valorAtributo4, String valorAtributo5)
/* 219:    */   {
/* 220:376 */     this(idProducto, codigoProducto, codigoAlternoProducto, nombreProducto, idSubcategoriaProducto, codigoSubcategoriaProducto, nombreSubcategoriaProducto, idCategoriaProducto, codigoCategoriaProducto, nombreCategoriaProducto, idBodega, codigoBodega, nombreBodega, fecha, idUnidad, codigoUnidad, nombreUnidad, idUnidadVenta, codigoUnidadVenta, nombreUnidadVenta, idUnidadAlmacenamiento, codigoUnidadAlmacenamiento, nombreUnidadAlmacenamiento, codigoLote, idLote, saldo);
/* 221:    */     
/* 222:    */ 
/* 223:    */ 
/* 224:380 */     this.valorAtributo1 = valorAtributo1;
/* 225:381 */     this.valorAtributo2 = valorAtributo2;
/* 226:382 */     this.valorAtributo3 = valorAtributo3;
/* 227:383 */     this.valorAtributo4 = valorAtributo4;
/* 228:384 */     this.valorAtributo5 = valorAtributo5;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public ReporteSaldoProducto(Integer idProducto, String codigoProducto, String codigoAlternoProducto, String nombreProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idBodega, String codigoBodega, String nombreBodega, Date fecha, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, String codigoLote, Integer idLote, BigDecimal saldo, String valorAtributo1, String valorAtributo2, String valorAtributo3, String valorAtributo4, String valorAtributo5, String valorAtributo6)
/* 232:    */   {
/* 233:393 */     this(idProducto, codigoProducto, codigoAlternoProducto, nombreProducto, idSubcategoriaProducto, codigoSubcategoriaProducto, nombreSubcategoriaProducto, idCategoriaProducto, codigoCategoriaProducto, nombreCategoriaProducto, idBodega, codigoBodega, nombreBodega, fecha, idUnidad, codigoUnidad, nombreUnidad, idUnidadVenta, codigoUnidadVenta, nombreUnidadVenta, idUnidadAlmacenamiento, codigoUnidadAlmacenamiento, nombreUnidadAlmacenamiento, codigoLote, idLote, saldo);
/* 234:    */     
/* 235:    */ 
/* 236:    */ 
/* 237:397 */     this.valorAtributo1 = valorAtributo1;
/* 238:398 */     this.valorAtributo2 = valorAtributo2;
/* 239:399 */     this.valorAtributo3 = valorAtributo3;
/* 240:400 */     this.valorAtributo4 = valorAtributo4;
/* 241:401 */     this.valorAtributo5 = valorAtributo5;
/* 242:402 */     this.valorAtributo6 = valorAtributo6;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public ReporteSaldoProducto(Integer idProducto, String codigoProducto, String codigoAlternoProducto, String nombreProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idBodega, String codigoBodega, String nombreBodega, Date fecha, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, String codigoLote, Integer idLote, BigDecimal saldo, String valorAtributo1, String valorAtributo2, String valorAtributo3, String valorAtributo4, String valorAtributo5, String valorAtributo6, String valorAtributo7)
/* 246:    */   {
/* 247:411 */     this(idProducto, codigoProducto, codigoAlternoProducto, nombreProducto, idSubcategoriaProducto, codigoSubcategoriaProducto, nombreSubcategoriaProducto, idCategoriaProducto, codigoCategoriaProducto, nombreCategoriaProducto, idBodega, codigoBodega, nombreBodega, fecha, idUnidad, codigoUnidad, nombreUnidad, idUnidadVenta, codigoUnidadVenta, nombreUnidadVenta, idUnidadAlmacenamiento, codigoUnidadAlmacenamiento, nombreUnidadAlmacenamiento, codigoLote, idLote, saldo);
/* 248:    */     
/* 249:    */ 
/* 250:    */ 
/* 251:415 */     this.valorAtributo1 = valorAtributo1;
/* 252:416 */     this.valorAtributo2 = valorAtributo2;
/* 253:417 */     this.valorAtributo3 = valorAtributo3;
/* 254:418 */     this.valorAtributo4 = valorAtributo4;
/* 255:419 */     this.valorAtributo5 = valorAtributo5;
/* 256:420 */     this.valorAtributo6 = valorAtributo6;
/* 257:421 */     this.valorAtributo7 = valorAtributo7;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public ReporteSaldoProducto(Integer idProducto, String codigoProducto, String codigoAlternoProducto, String nombreProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idBodega, String codigoBodega, String nombreBodega, Date fecha, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, String codigoLote, Integer idLote, BigDecimal saldo, String valorAtributo1, String valorAtributo2, String valorAtributo3, String valorAtributo4, String valorAtributo5, String valorAtributo6, String valorAtributo7, String valorAtributo8)
/* 261:    */   {
/* 262:430 */     this(idProducto, codigoProducto, codigoAlternoProducto, nombreProducto, idSubcategoriaProducto, codigoSubcategoriaProducto, nombreSubcategoriaProducto, idCategoriaProducto, codigoCategoriaProducto, nombreCategoriaProducto, idBodega, codigoBodega, nombreBodega, fecha, idUnidad, codigoUnidad, nombreUnidad, idUnidadVenta, codigoUnidadVenta, nombreUnidadVenta, idUnidadAlmacenamiento, codigoUnidadAlmacenamiento, nombreUnidadAlmacenamiento, codigoLote, idLote, saldo);
/* 263:    */     
/* 264:    */ 
/* 265:    */ 
/* 266:434 */     this.valorAtributo1 = valorAtributo1;
/* 267:435 */     this.valorAtributo2 = valorAtributo2;
/* 268:436 */     this.valorAtributo3 = valorAtributo3;
/* 269:437 */     this.valorAtributo4 = valorAtributo4;
/* 270:438 */     this.valorAtributo5 = valorAtributo5;
/* 271:439 */     this.valorAtributo6 = valorAtributo6;
/* 272:440 */     this.valorAtributo7 = valorAtributo7;
/* 273:441 */     this.valorAtributo8 = valorAtributo8;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public ReporteSaldoProducto(Integer idProducto, String codigoProducto, String codigoAlternoProducto, String nombreProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idBodega, String codigoBodega, String nombreBodega, Date fecha, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, String codigoLote, Integer idLote, BigDecimal saldo, String valorAtributo1, String valorAtributo2, String valorAtributo3, String valorAtributo4, String valorAtributo5, String valorAtributo6, String valorAtributo7, String valorAtributo8, String valorAtributo9)
/* 277:    */   {
/* 278:450 */     this(idProducto, codigoProducto, codigoAlternoProducto, nombreProducto, idSubcategoriaProducto, codigoSubcategoriaProducto, nombreSubcategoriaProducto, idCategoriaProducto, codigoCategoriaProducto, nombreCategoriaProducto, idBodega, codigoBodega, nombreBodega, fecha, idUnidad, codigoUnidad, nombreUnidad, idUnidadVenta, codigoUnidadVenta, nombreUnidadVenta, idUnidadAlmacenamiento, codigoUnidadAlmacenamiento, nombreUnidadAlmacenamiento, codigoLote, idLote, saldo);
/* 279:    */     
/* 280:    */ 
/* 281:    */ 
/* 282:454 */     this.valorAtributo1 = valorAtributo1;
/* 283:455 */     this.valorAtributo2 = valorAtributo2;
/* 284:456 */     this.valorAtributo3 = valorAtributo3;
/* 285:457 */     this.valorAtributo4 = valorAtributo4;
/* 286:458 */     this.valorAtributo5 = valorAtributo5;
/* 287:459 */     this.valorAtributo6 = valorAtributo6;
/* 288:460 */     this.valorAtributo7 = valorAtributo7;
/* 289:461 */     this.valorAtributo8 = valorAtributo8;
/* 290:462 */     this.valorAtributo9 = valorAtributo9;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public ReporteSaldoProducto(Integer idProducto, String codigoProducto, String codigoAlternoProducto, String nombreProducto, Integer idSubcategoriaProducto, String codigoSubcategoriaProducto, String nombreSubcategoriaProducto, Integer idCategoriaProducto, String codigoCategoriaProducto, String nombreCategoriaProducto, Integer idBodega, String codigoBodega, String nombreBodega, Date fecha, Integer idUnidad, String codigoUnidad, String nombreUnidad, Integer idUnidadVenta, String codigoUnidadVenta, String nombreUnidadVenta, Integer idUnidadAlmacenamiento, String codigoUnidadAlmacenamiento, String nombreUnidadAlmacenamiento, String codigoLote, Integer idLote, BigDecimal saldo, String valorAtributo1, String valorAtributo2, String valorAtributo3, String valorAtributo4, String valorAtributo5, String valorAtributo6, String valorAtributo7, String valorAtributo8, String valorAtributo9, String valorAtributo10)
/* 294:    */   {
/* 295:471 */     this(idProducto, codigoProducto, codigoAlternoProducto, nombreProducto, idSubcategoriaProducto, codigoSubcategoriaProducto, nombreSubcategoriaProducto, idCategoriaProducto, codigoCategoriaProducto, nombreCategoriaProducto, idBodega, codigoBodega, nombreBodega, fecha, idUnidad, codigoUnidad, nombreUnidad, idUnidadVenta, codigoUnidadVenta, nombreUnidadVenta, idUnidadAlmacenamiento, codigoUnidadAlmacenamiento, nombreUnidadAlmacenamiento, codigoLote, idLote, saldo);
/* 296:    */     
/* 297:    */ 
/* 298:    */ 
/* 299:475 */     this.valorAtributo1 = valorAtributo1;
/* 300:476 */     this.valorAtributo2 = valorAtributo2;
/* 301:477 */     this.valorAtributo3 = valorAtributo3;
/* 302:478 */     this.valorAtributo4 = valorAtributo4;
/* 303:479 */     this.valorAtributo5 = valorAtributo5;
/* 304:480 */     this.valorAtributo6 = valorAtributo6;
/* 305:481 */     this.valorAtributo7 = valorAtributo7;
/* 306:482 */     this.valorAtributo8 = valorAtributo8;
/* 307:483 */     this.valorAtributo9 = valorAtributo9;
/* 308:484 */     this.valorAtributo10 = valorAtributo10;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public Integer getIdReporteSaldoProducto()
/* 312:    */   {
/* 313:488 */     return this.idReporteSaldoProducto;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setIdReporteSaldoProducto(Integer idReporteSaldoProducto)
/* 317:    */   {
/* 318:492 */     this.idReporteSaldoProducto = idReporteSaldoProducto;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public Integer getIdProducto()
/* 322:    */   {
/* 323:496 */     return this.idProducto;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setIdProducto(Integer idProducto)
/* 327:    */   {
/* 328:500 */     this.idProducto = idProducto;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public String getCodigoProducto()
/* 332:    */   {
/* 333:504 */     return this.codigoProducto;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void setCodigoProducto(String codigoProducto)
/* 337:    */   {
/* 338:508 */     this.codigoProducto = codigoProducto;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public String getNombreProducto()
/* 342:    */   {
/* 343:512 */     return this.nombreProducto;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setNombreProducto(String nombreProducto)
/* 347:    */   {
/* 348:516 */     this.nombreProducto = nombreProducto;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public Integer getIdSubcategoriaProducto()
/* 352:    */   {
/* 353:520 */     return this.idSubcategoriaProducto;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setIdSubcategoriaProducto(Integer idSubcategoriaProducto)
/* 357:    */   {
/* 358:524 */     this.idSubcategoriaProducto = idSubcategoriaProducto;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public String getCodigoSubcategoriaProducto()
/* 362:    */   {
/* 363:528 */     return this.codigoSubcategoriaProducto;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void setCodigoSubcategoriaProducto(String codigoSubcategoriaProducto)
/* 367:    */   {
/* 368:532 */     this.codigoSubcategoriaProducto = codigoSubcategoriaProducto;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public String getNombreSubcategoriaProducto()
/* 372:    */   {
/* 373:536 */     return this.nombreSubcategoriaProducto;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public void setNombreSubcategoriaProducto(String nombreSubcategoriaProducto)
/* 377:    */   {
/* 378:540 */     this.nombreSubcategoriaProducto = nombreSubcategoriaProducto;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public Integer getIdCategoriaProducto()
/* 382:    */   {
/* 383:544 */     return this.idCategoriaProducto;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public void setIdCategoriaProducto(Integer idCategoriaProducto)
/* 387:    */   {
/* 388:548 */     this.idCategoriaProducto = idCategoriaProducto;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public String getCodigoCategoriaProducto()
/* 392:    */   {
/* 393:552 */     return this.codigoCategoriaProducto;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public void setCodigoCategoriaProducto(String codigoCategoriaProducto)
/* 397:    */   {
/* 398:556 */     this.codigoCategoriaProducto = codigoCategoriaProducto;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public String getNombreCategoriaProducto()
/* 402:    */   {
/* 403:560 */     return this.nombreCategoriaProducto;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public void setNombreCategoriaProducto(String nombreCategoriaProducto)
/* 407:    */   {
/* 408:564 */     this.nombreCategoriaProducto = nombreCategoriaProducto;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public Integer getIdBodega()
/* 412:    */   {
/* 413:568 */     return this.idBodega;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void setIdBodega(Integer idBodega)
/* 417:    */   {
/* 418:572 */     this.idBodega = idBodega;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public String getCodigoBodega()
/* 422:    */   {
/* 423:576 */     return this.codigoBodega;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public void setCodigoBodega(String codigoBodega)
/* 427:    */   {
/* 428:580 */     this.codigoBodega = codigoBodega;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public String getNombreBodega()
/* 432:    */   {
/* 433:584 */     return this.nombreBodega;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public void setNombreBodega(String nombreBodega)
/* 437:    */   {
/* 438:588 */     this.nombreBodega = nombreBodega;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public Date getFecha()
/* 442:    */   {
/* 443:592 */     return this.fecha;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void setFecha(Date fecha)
/* 447:    */   {
/* 448:596 */     this.fecha = fecha;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public BigDecimal getSaldo()
/* 452:    */   {
/* 453:600 */     return this.saldo;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public void setSaldo(BigDecimal saldo)
/* 457:    */   {
/* 458:604 */     this.saldo = saldo;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public BigDecimal getCostoUnitario()
/* 462:    */   {
/* 463:608 */     return this.costoUnitario;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public void setCostoUnitario(BigDecimal costoUnitario)
/* 467:    */   {
/* 468:612 */     this.costoUnitario = costoUnitario;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public Integer getIdUnidad()
/* 472:    */   {
/* 473:616 */     return this.idUnidad;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public void setIdUnidad(Integer idUnidad)
/* 477:    */   {
/* 478:620 */     this.idUnidad = idUnidad;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public String getCodigoUnidad()
/* 482:    */   {
/* 483:624 */     return this.codigoUnidad;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public void setCodigoUnidad(String codigoUnidad)
/* 487:    */   {
/* 488:628 */     this.codigoUnidad = codigoUnidad;
/* 489:    */   }
/* 490:    */   
/* 491:    */   public Integer getIdUnidadVenta()
/* 492:    */   {
/* 493:632 */     return this.idUnidadVenta;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public void setIdUnidadVenta(Integer idUnidadVenta)
/* 497:    */   {
/* 498:636 */     this.idUnidadVenta = idUnidadVenta;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public String getCodigoUnidadVenta()
/* 502:    */   {
/* 503:640 */     return this.codigoUnidadVenta;
/* 504:    */   }
/* 505:    */   
/* 506:    */   public void setCodigoUnidadVenta(String codigoUnidadVenta)
/* 507:    */   {
/* 508:644 */     this.codigoUnidadVenta = codigoUnidadVenta;
/* 509:    */   }
/* 510:    */   
/* 511:    */   public Integer getIdUnidadAlmacenamiento()
/* 512:    */   {
/* 513:648 */     return this.idUnidadAlmacenamiento;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public void setIdUnidadAlmacenamiento(Integer idUnidadAlmacenamiento)
/* 517:    */   {
/* 518:652 */     this.idUnidadAlmacenamiento = idUnidadAlmacenamiento;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public String getCodigoUnidadAlmacenamiento()
/* 522:    */   {
/* 523:656 */     return this.codigoUnidadAlmacenamiento;
/* 524:    */   }
/* 525:    */   
/* 526:    */   public void setCodigoUnidadAlmacenamiento(String codigoUnidadAlmacenamiento)
/* 527:    */   {
/* 528:660 */     this.codigoUnidadAlmacenamiento = codigoUnidadAlmacenamiento;
/* 529:    */   }
/* 530:    */   
/* 531:    */   public String getNombreUnidad()
/* 532:    */   {
/* 533:664 */     return this.nombreUnidad;
/* 534:    */   }
/* 535:    */   
/* 536:    */   public void setNombreUnidad(String nombreUnidad)
/* 537:    */   {
/* 538:668 */     this.nombreUnidad = nombreUnidad;
/* 539:    */   }
/* 540:    */   
/* 541:    */   public String getNombreUnidadVenta()
/* 542:    */   {
/* 543:672 */     return this.nombreUnidadVenta;
/* 544:    */   }
/* 545:    */   
/* 546:    */   public void setNombreUnidadVenta(String nombreUnidadVenta)
/* 547:    */   {
/* 548:676 */     this.nombreUnidadVenta = nombreUnidadVenta;
/* 549:    */   }
/* 550:    */   
/* 551:    */   public String getNombreUnidadAlmacenamiento()
/* 552:    */   {
/* 553:680 */     return this.nombreUnidadAlmacenamiento;
/* 554:    */   }
/* 555:    */   
/* 556:    */   public void setNombreUnidadAlmacenamiento(String nombreUnidadAlmacenamiento)
/* 557:    */   {
/* 558:684 */     this.nombreUnidadAlmacenamiento = nombreUnidadAlmacenamiento;
/* 559:    */   }
/* 560:    */   
/* 561:    */   public String getCodigoLote()
/* 562:    */   {
/* 563:693 */     return this.codigoLote;
/* 564:    */   }
/* 565:    */   
/* 566:    */   public void setCodigoLote(String codigoLote)
/* 567:    */   {
/* 568:703 */     this.codigoLote = codigoLote;
/* 569:    */   }
/* 570:    */   
/* 571:    */   public Integer getIdLote()
/* 572:    */   {
/* 573:712 */     return this.idLote;
/* 574:    */   }
/* 575:    */   
/* 576:    */   public void setIdLote(Integer idLote)
/* 577:    */   {
/* 578:722 */     this.idLote = idLote;
/* 579:    */   }
/* 580:    */   
/* 581:    */   public BigDecimal getSaldoComprometido()
/* 582:    */   {
/* 583:731 */     return this.saldoComprometido;
/* 584:    */   }
/* 585:    */   
/* 586:    */   public void setSaldoComprometido(BigDecimal saldoComprometido)
/* 587:    */   {
/* 588:741 */     this.saldoComprometido = saldoComprometido;
/* 589:    */   }
/* 590:    */   
/* 591:    */   public String getValorAtributo1()
/* 592:    */   {
/* 593:750 */     return this.valorAtributo1;
/* 594:    */   }
/* 595:    */   
/* 596:    */   public void setValorAtributo1(String valorAtributo1)
/* 597:    */   {
/* 598:760 */     this.valorAtributo1 = valorAtributo1;
/* 599:    */   }
/* 600:    */   
/* 601:    */   public String getValorAtributo2()
/* 602:    */   {
/* 603:769 */     return this.valorAtributo2;
/* 604:    */   }
/* 605:    */   
/* 606:    */   public void setValorAtributo2(String valorAtributo2)
/* 607:    */   {
/* 608:779 */     this.valorAtributo2 = valorAtributo2;
/* 609:    */   }
/* 610:    */   
/* 611:    */   public String getCodigoAlternoProducto()
/* 612:    */   {
/* 613:783 */     return this.codigoAlternoProducto;
/* 614:    */   }
/* 615:    */   
/* 616:    */   public void setCodigoAlternoProducto(String codigoAlternoProducto)
/* 617:    */   {
/* 618:787 */     this.codigoAlternoProducto = codigoAlternoProducto;
/* 619:    */   }
/* 620:    */   
/* 621:    */   public String getNombreComercialProducto()
/* 622:    */   {
/* 623:791 */     return this.nombreComercialProducto;
/* 624:    */   }
/* 625:    */   
/* 626:    */   public void setNombreComercialProducto(String nombreComercialProducto)
/* 627:    */   {
/* 628:795 */     this.nombreComercialProducto = nombreComercialProducto;
/* 629:    */   }
/* 630:    */   
/* 631:    */   public String getValorAtributo3()
/* 632:    */   {
/* 633:799 */     return this.valorAtributo3;
/* 634:    */   }
/* 635:    */   
/* 636:    */   public void setValorAtributo3(String valorAtributo3)
/* 637:    */   {
/* 638:803 */     this.valorAtributo3 = valorAtributo3;
/* 639:    */   }
/* 640:    */   
/* 641:    */   public String getValorAtributo4()
/* 642:    */   {
/* 643:807 */     return this.valorAtributo4;
/* 644:    */   }
/* 645:    */   
/* 646:    */   public void setValorAtributo4(String valorAtributo4)
/* 647:    */   {
/* 648:811 */     this.valorAtributo4 = valorAtributo4;
/* 649:    */   }
/* 650:    */   
/* 651:    */   public String getValorAtributo5()
/* 652:    */   {
/* 653:815 */     return this.valorAtributo5;
/* 654:    */   }
/* 655:    */   
/* 656:    */   public void setValorAtributo5(String valorAtributo5)
/* 657:    */   {
/* 658:819 */     this.valorAtributo5 = valorAtributo5;
/* 659:    */   }
/* 660:    */   
/* 661:    */   public String getValorAtributo6()
/* 662:    */   {
/* 663:823 */     return this.valorAtributo6;
/* 664:    */   }
/* 665:    */   
/* 666:    */   public void setValorAtributo6(String valorAtributo6)
/* 667:    */   {
/* 668:827 */     this.valorAtributo6 = valorAtributo6;
/* 669:    */   }
/* 670:    */   
/* 671:    */   public String getValorAtributo7()
/* 672:    */   {
/* 673:831 */     return this.valorAtributo7;
/* 674:    */   }
/* 675:    */   
/* 676:    */   public void setValorAtributo7(String valorAtributo7)
/* 677:    */   {
/* 678:835 */     this.valorAtributo7 = valorAtributo7;
/* 679:    */   }
/* 680:    */   
/* 681:    */   public String getValorAtributo8()
/* 682:    */   {
/* 683:839 */     return this.valorAtributo8;
/* 684:    */   }
/* 685:    */   
/* 686:    */   public void setValorAtributo8(String valorAtributo8)
/* 687:    */   {
/* 688:843 */     this.valorAtributo8 = valorAtributo8;
/* 689:    */   }
/* 690:    */   
/* 691:    */   public String getValorAtributo9()
/* 692:    */   {
/* 693:847 */     return this.valorAtributo9;
/* 694:    */   }
/* 695:    */   
/* 696:    */   public void setValorAtributo9(String valorAtributo9)
/* 697:    */   {
/* 698:851 */     this.valorAtributo9 = valorAtributo9;
/* 699:    */   }
/* 700:    */   
/* 701:    */   public String getValorAtributo10()
/* 702:    */   {
/* 703:855 */     return this.valorAtributo10;
/* 704:    */   }
/* 705:    */   
/* 706:    */   public void setValorAtributo10(String valorAtributo10)
/* 707:    */   {
/* 708:859 */     this.valorAtributo10 = valorAtributo10;
/* 709:    */   }
/* 710:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.ReporteSaldoProducto
 * JD-Core Version:    0.7.0.1
 */