/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoSRI;
/*   4:    */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*   5:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.EnumType;
/*  12:    */ import javax.persistence.Enumerated;
/*  13:    */ import javax.persistence.FetchType;
/*  14:    */ import javax.persistence.GeneratedValue;
/*  15:    */ import javax.persistence.GenerationType;
/*  16:    */ import javax.persistence.Id;
/*  17:    */ import javax.persistence.JoinColumn;
/*  18:    */ import javax.persistence.ManyToOne;
/*  19:    */ import javax.persistence.OneToMany;
/*  20:    */ import javax.persistence.Table;
/*  21:    */ import javax.persistence.TableGenerator;
/*  22:    */ import javax.validation.constraints.NotNull;
/*  23:    */ import javax.validation.constraints.Size;
/*  24:    */ 
/*  25:    */ @Entity
/*  26:    */ @Table(name="documento", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})}, indexes={@javax.persistence.Index(columnList="id_tipo_comprobante")})
/*  27:    */ public class Documento
/*  28:    */   extends EntidadBase
/*  29:    */   implements Serializable
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @Id
/*  33:    */   @TableGenerator(name="documento", initialValue=0, allocationSize=50)
/*  34:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="documento")
/*  35:    */   @Column(name="id_documento", unique=true, nullable=false)
/*  36:    */   private int idDocumento;
/*  37:    */   @Column(name="documento_base", nullable=false)
/*  38:    */   @Enumerated(EnumType.ORDINAL)
/*  39:    */   private DocumentoBase documentoBase;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  41:    */   @JoinColumn(name="id_tipo_asiento", nullable=true)
/*  42:    */   private TipoAsiento tipoAsiento;
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  44:    */   @JoinColumn(name="id_secuencia", nullable=true)
/*  45:    */   private Secuencia secuencia;
/*  46:    */   @Column(name="id_organizacion")
/*  47:    */   private int idOrganizacion;
/*  48:    */   @Column(name="id_sucursal")
/*  49:    */   private int idSucursal;
/*  50:    */   @Column(name="codigo", nullable=true, length=10)
/*  51:    */   @NotNull
/*  52:    */   @Size(max=10)
/*  53:    */   private String codigo;
/*  54:    */   @Column(name="nombre", nullable=false)
/*  55:    */   @NotNull
/*  56:    */   @Size(min=2, max=50)
/*  57:    */   private String nombre;
/*  58:    */   @Column(name="operacion", nullable=false)
/*  59:    */   private short operacion;
/*  60:    */   @Column(name="indicador_genera_costo", nullable=false)
/*  61:    */   private boolean indicadorGeneraCosto;
/*  62:    */   @Column(name="indicador_costo_estandar", nullable=false)
/*  63:    */   private boolean indicadorCostoEstandar;
/*  64:    */   @Column(name="indicador_documento_tributario", nullable=false)
/*  65:    */   private boolean indicadorDocumentoTributario;
/*  66:    */   @Column(name="descripcion", nullable=true, length=500)
/*  67:    */   @Size(max=500)
/*  68:    */   private String descripcion;
/*  69:    */   @Column(name="activo", nullable=false)
/*  70: 94 */   private boolean activo = true;
/*  71:    */   @Column(name="predeterminado", nullable=false)
/*  72:    */   private boolean predeterminado;
/*  73:    */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  74:    */   @JoinColumn(name="id_tipo_comprobante", nullable=true)
/*  75:    */   private TipoComprobanteSRI tipoComprobanteSRI;
/*  76:    */   @Column(name="reporte", length=50, nullable=false)
/*  77:    */   @Size(min=2, max=50)
/*  78:    */   private String reporte;
/*  79:    */   @OneToMany(mappedBy="documento", fetch=FetchType.LAZY)
/*  80:108 */   private List<AutorizacionDocumentoSRI> listaAutorizacionDocumentoSRI = new ArrayList();
/*  81:    */   @Column(name="indicador_documento_exterior", nullable=false)
/*  82:    */   private boolean indicadorDocumentoExterior;
/*  83:    */   @Column(name="indicador_documento_electronico", nullable=false)
/*  84:    */   private boolean indicadorDocumentoElectronico;
/*  85:    */   @OneToMany(mappedBy="documento", fetch=FetchType.LAZY)
/*  86:117 */   private List<DocumentoGastoImportacion> listaDocumentoGastoImportacion = new ArrayList();
/*  87:    */   @Column(name="indicador_bloqueo_secuencia", nullable=false)
/*  88:    */   private boolean indicadorBloqueoSecuencia;
/*  89:    */   @Column(name="indicador_contabilizar", nullable=false)
/*  90:123 */   private boolean indicadorContabilizar = true;
/*  91:    */   
/*  92:    */   public Documento() {}
/*  93:    */   
/*  94:    */   public Documento(int idDocumento, String nombre)
/*  95:    */   {
/*  96:135 */     this.idDocumento = idDocumento;
/*  97:136 */     this.nombre = nombre;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int getId()
/* 101:    */   {
/* 102:141 */     return this.idDocumento;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int getIdDocumento()
/* 106:    */   {
/* 107:150 */     return this.idDocumento;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setIdDocumento(int idDocumento)
/* 111:    */   {
/* 112:160 */     this.idDocumento = idDocumento;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public DocumentoBase getDocumentoBase()
/* 116:    */   {
/* 117:169 */     return this.documentoBase;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 121:    */   {
/* 122:179 */     this.documentoBase = documentoBase;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public TipoAsiento getTipoAsiento()
/* 126:    */   {
/* 127:188 */     return this.tipoAsiento;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setTipoAsiento(TipoAsiento tipoAsiento)
/* 131:    */   {
/* 132:198 */     this.tipoAsiento = tipoAsiento;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Secuencia getSecuencia()
/* 136:    */   {
/* 137:207 */     return this.secuencia;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setSecuencia(Secuencia secuencia)
/* 141:    */   {
/* 142:217 */     this.secuencia = secuencia;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int getIdOrganizacion()
/* 146:    */   {
/* 147:226 */     return this.idOrganizacion;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setIdOrganizacion(int idOrganizacion)
/* 151:    */   {
/* 152:236 */     this.idOrganizacion = idOrganizacion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public int getIdSucursal()
/* 156:    */   {
/* 157:245 */     return this.idSucursal;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setIdSucursal(int idSucursal)
/* 161:    */   {
/* 162:255 */     this.idSucursal = idSucursal;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String getNombre()
/* 166:    */   {
/* 167:264 */     return this.nombre;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setNombre(String nombre)
/* 171:    */   {
/* 172:274 */     this.nombre = nombre;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String getDescripcion()
/* 176:    */   {
/* 177:283 */     return this.descripcion;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setDescripcion(String descripcion)
/* 181:    */   {
/* 182:293 */     this.descripcion = descripcion;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public boolean isActivo()
/* 186:    */   {
/* 187:302 */     return this.activo;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setActivo(boolean activo)
/* 191:    */   {
/* 192:312 */     this.activo = activo;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public boolean isPredeterminado()
/* 196:    */   {
/* 197:321 */     return this.predeterminado;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setPredeterminado(boolean predeterminado)
/* 201:    */   {
/* 202:331 */     this.predeterminado = predeterminado;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public short getOperacion()
/* 206:    */   {
/* 207:335 */     return this.operacion;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setOperacion(short operacion)
/* 211:    */   {
/* 212:339 */     this.operacion = operacion;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public String toString()
/* 216:    */   {
/* 217:344 */     return this.nombre;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public boolean isIndicadorGeneraCosto()
/* 221:    */   {
/* 222:353 */     return this.indicadorGeneraCosto;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setIndicadorGeneraCosto(boolean indicadorGeneraCosto)
/* 226:    */   {
/* 227:363 */     this.indicadorGeneraCosto = indicadorGeneraCosto;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public boolean isIndicadorDocumentoTributario()
/* 231:    */   {
/* 232:372 */     return this.indicadorDocumentoTributario;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setIndicadorDocumentoTributario(boolean indicadorDocumentoTributario)
/* 236:    */   {
/* 237:382 */     this.indicadorDocumentoTributario = indicadorDocumentoTributario;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public List<AutorizacionDocumentoSRI> getListaAutorizacionDocumentoSRI()
/* 241:    */   {
/* 242:391 */     return this.listaAutorizacionDocumentoSRI;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setListaAutorizacionDocumentoSRI(List<AutorizacionDocumentoSRI> listaAutorizacionDocumentoSRI)
/* 246:    */   {
/* 247:401 */     this.listaAutorizacionDocumentoSRI = listaAutorizacionDocumentoSRI;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public TipoComprobanteSRI getTipoComprobanteSRI()
/* 251:    */   {
/* 252:410 */     return this.tipoComprobanteSRI;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setTipoComprobanteSRI(TipoComprobanteSRI tipoComprobanteSRI)
/* 256:    */   {
/* 257:420 */     this.tipoComprobanteSRI = tipoComprobanteSRI;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public String getReporte()
/* 261:    */   {
/* 262:424 */     return this.reporte;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setReporte(String reporte)
/* 266:    */   {
/* 267:428 */     this.reporte = reporte;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public boolean isIndicadorDocumentoExterior()
/* 271:    */   {
/* 272:437 */     return this.indicadorDocumentoExterior;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setIndicadorDocumentoExterior(boolean indicadorDocumentoExterior)
/* 276:    */   {
/* 277:447 */     this.indicadorDocumentoExterior = indicadorDocumentoExterior;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public List<DocumentoGastoImportacion> getListaDocumentoGastoImportacion()
/* 281:    */   {
/* 282:456 */     return this.listaDocumentoGastoImportacion;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setListaDocumentoGastoImportacion(List<DocumentoGastoImportacion> listaDocumentoGastoImportacion)
/* 286:    */   {
/* 287:466 */     this.listaDocumentoGastoImportacion = listaDocumentoGastoImportacion;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public String getCodigo()
/* 291:    */   {
/* 292:475 */     return this.codigo;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setCodigo(String codigo)
/* 296:    */   {
/* 297:485 */     this.codigo = codigo;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public boolean isIndicadorCostoEstandar()
/* 301:    */   {
/* 302:494 */     return this.indicadorCostoEstandar;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setIndicadorCostoEstandar(boolean indicadorCostoEstandar)
/* 306:    */   {
/* 307:504 */     this.indicadorCostoEstandar = indicadorCostoEstandar;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public boolean isIndicadorDocumentoElectronico()
/* 311:    */   {
/* 312:508 */     return this.indicadorDocumentoElectronico;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setIndicadorDocumentoElectronico(boolean indicadorDocumentoElectronico)
/* 316:    */   {
/* 317:512 */     this.indicadorDocumentoElectronico = indicadorDocumentoElectronico;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public boolean isIndicadorBloqueoSecuencia()
/* 321:    */   {
/* 322:519 */     return this.indicadorBloqueoSecuencia;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setIndicadorBloqueoSecuencia(boolean indicadorBloqueoSecuencia)
/* 326:    */   {
/* 327:526 */     this.indicadorBloqueoSecuencia = indicadorBloqueoSecuencia;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public boolean isIndicadorContabilizar()
/* 331:    */   {
/* 332:530 */     return this.indicadorContabilizar;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setIndicadorContabilizar(boolean indicadorContabilizar)
/* 336:    */   {
/* 337:534 */     this.indicadorContabilizar = indicadorContabilizar;
/* 338:    */   }
/* 339:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Documento
 * JD-Core Version:    0.7.0.1
 */