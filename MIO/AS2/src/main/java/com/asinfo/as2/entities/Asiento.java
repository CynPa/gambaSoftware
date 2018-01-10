/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Date;
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
/*  20:    */ import javax.persistence.OrderBy;
/*  21:    */ import javax.persistence.Table;
/*  22:    */ import javax.persistence.TableGenerator;
/*  23:    */ import javax.persistence.Temporal;
/*  24:    */ import javax.persistence.TemporalType;
/*  25:    */ import javax.persistence.Transient;
/*  26:    */ import javax.validation.constraints.Digits;
/*  27:    */ import javax.validation.constraints.NotNull;
/*  28:    */ import javax.validation.constraints.Size;
/*  29:    */ 
/*  30:    */ @Entity
/*  31:    */ @Table(name="asiento", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_sucursal", "id_tipo_asiento", "numero"})}, indexes={@javax.persistence.Index(columnList="id_organizacion,estado,fecha")})
/*  32:    */ public class Asiento
/*  33:    */   extends EntidadBase
/*  34:    */   implements Serializable
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @Id
/*  38:    */   @TableGenerator(name="asiento", initialValue=0, allocationSize=50)
/*  39:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="asiento")
/*  40:    */   @Column(name="id_asiento")
/*  41:    */   private int idAsiento;
/*  42:    */   @Column(name="id_organizacion", nullable=false)
/*  43:    */   private int idOrganizacion;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_tipo_asiento", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private TipoAsiento tipoAsiento;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_documento_origen", nullable=true)
/*  50:    */   private Documento documentoOrigen;
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_periodo")
/*  53:    */   private Periodo periodo;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  56:    */   private Sucursal sucursal;
/*  57:    */   @Column(name="estado", nullable=false)
/*  58:    */   @Enumerated(EnumType.ORDINAL)
/*  59:    */   private Estado estado;
/*  60:    */   @Column(name="numero", nullable=false, length=20, insertable=true, updatable=false)
/*  61:    */   @NotNull
/*  62:    */   @Size(max=20)
/*  63:    */   private String numero;
/*  64:    */   @Temporal(TemporalType.DATE)
/*  65:    */   @Column(name="fecha", nullable=false)
/*  66:    */   @NotNull
/*  67:    */   private Date fecha;
/*  68:    */   @Column(name="valor", precision=12, scale=2)
/*  69:    */   @Digits(integer=12, fraction=2)
/*  70: 85 */   private BigDecimal valor = BigDecimal.ZERO;
/*  71:    */   @Column(name="concepto", length=1000)
/*  72:    */   @Size(max=1000)
/*  73:    */   private String concepto;
/*  74:    */   @Column(name="concepto2", length=1000)
/*  75:    */   @Size(max=1000)
/*  76:    */   private String concepto2;
/*  77:    */   @Column(name="indicador_automatico", nullable=false)
/*  78:    */   private boolean indicadorAutomatico;
/*  79:    */   @Column(name="indicador_automatico_editado", nullable=true)
/*  80:    */   private boolean indicadorAutomaticoEditado;
/*  81:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="asiento")
/*  82:    */   @OrderBy("idDetalleAsiento")
/*  83:103 */   private List<DetalleAsiento> listaDetalleAsiento = new ArrayList();
/*  84:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  85:    */   @JoinColumn(name="id_proyecto", nullable=true)
/*  86:    */   private DimensionContable proyecto;
/*  87:    */   @Column(name="documento_referencia", nullable=true, length=30)
/*  88:    */   @Size(max=30)
/*  89:    */   private String documentoReferencia;
/*  90:    */   @Transient
/*  91:    */   private BigDecimal totalDebe;
/*  92:    */   @Transient
/*  93:    */   private BigDecimal totalHaber;
/*  94:    */   @Transient
/*  95:    */   private Date fechaChequePosfechado;
/*  96:    */   @Transient
/*  97:    */   private String notaPosfechado;
/*  98:    */   @Transient
/*  99:127 */   private BigDecimal valorOriginal = BigDecimal.ZERO;
/* 100:    */   @Transient
/* 101:    */   private String styleClass;
/* 102:    */   
/* 103:    */   public List<String> getCamposAuditables()
/* 104:    */   {
/* 105:143 */     ArrayList<String> lista = new ArrayList();
/* 106:    */     
/* 107:145 */     lista.add("tipoAsiento");
/* 108:146 */     lista.add("estado");
/* 109:147 */     lista.add("numero");
/* 110:148 */     lista.add("fecha");
/* 111:149 */     lista.add("concepto");
/* 112:    */     
/* 113:151 */     return lista;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int getIdAsiento()
/* 117:    */   {
/* 118:155 */     return this.idAsiento;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setIdAsiento(int idAsiento)
/* 122:    */   {
/* 123:159 */     this.idAsiento = idAsiento;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public TipoAsiento getTipoAsiento()
/* 127:    */   {
/* 128:163 */     if (this.tipoAsiento == null) {
/* 129:164 */       this.tipoAsiento = new TipoAsiento();
/* 130:    */     }
/* 131:166 */     return this.tipoAsiento;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setTipoAsiento(TipoAsiento tipoAsiento)
/* 135:    */   {
/* 136:170 */     this.tipoAsiento = tipoAsiento;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Periodo getPeriodo()
/* 140:    */   {
/* 141:174 */     if (this.periodo == null) {
/* 142:175 */       this.periodo = new Periodo();
/* 143:    */     }
/* 144:177 */     return this.periodo;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setPeriodo(Periodo periodo)
/* 148:    */   {
/* 149:181 */     this.periodo = periodo;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public Sucursal getSucursal()
/* 153:    */   {
/* 154:185 */     return this.sucursal;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setSucursal(Sucursal sucursal)
/* 158:    */   {
/* 159:189 */     this.sucursal = sucursal;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Estado getEstado()
/* 163:    */   {
/* 164:193 */     return this.estado;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setEstado(Estado estado)
/* 168:    */   {
/* 169:197 */     this.estado = estado;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public String getNumero()
/* 173:    */   {
/* 174:201 */     return this.numero;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setNumero(String numero)
/* 178:    */   {
/* 179:205 */     this.numero = numero;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public Date getFecha()
/* 183:    */   {
/* 184:209 */     return this.fecha;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setFecha(Date fecha)
/* 188:    */   {
/* 189:213 */     this.fecha = fecha;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public String getConcepto()
/* 193:    */   {
/* 194:217 */     return this.concepto;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setConcepto(String concepto)
/* 198:    */   {
/* 199:221 */     this.concepto = concepto;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public List<DetalleAsiento> getListaDetalleAsiento()
/* 203:    */   {
/* 204:230 */     return this.listaDetalleAsiento;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setListaDetalleAsiento(List<DetalleAsiento> listaDetalleAsiento)
/* 208:    */   {
/* 209:240 */     this.listaDetalleAsiento = listaDetalleAsiento;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public Documento getDocumentoOrigen()
/* 213:    */   {
/* 214:244 */     return this.documentoOrigen;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setDocumentoOrigen(Documento documentoOrigen)
/* 218:    */   {
/* 219:248 */     this.documentoOrigen = documentoOrigen;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public int getId()
/* 223:    */   {
/* 224:258 */     return getIdAsiento();
/* 225:    */   }
/* 226:    */   
/* 227:    */   public String toString()
/* 228:    */   {
/* 229:263 */     return this.numero;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public BigDecimal getValor()
/* 233:    */   {
/* 234:272 */     return this.valor;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setValor(BigDecimal valor)
/* 238:    */   {
/* 239:282 */     this.valor = valor;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public int getIdOrganizacion()
/* 243:    */   {
/* 244:291 */     return this.idOrganizacion;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setIdOrganizacion(int idOrganizacion)
/* 248:    */   {
/* 249:301 */     this.idOrganizacion = idOrganizacion;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public boolean isIndicadorAutomatico()
/* 253:    */   {
/* 254:305 */     return this.indicadorAutomatico;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setIndicadorAutomatico(boolean indicadorAutomatico)
/* 258:    */   {
/* 259:309 */     this.indicadorAutomatico = indicadorAutomatico;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public BigDecimal getTotalDebe()
/* 263:    */   {
/* 264:316 */     return this.totalDebe;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void setTotalDebe(BigDecimal totalDebe)
/* 268:    */   {
/* 269:324 */     this.totalDebe = totalDebe;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public BigDecimal getTotalHaber()
/* 273:    */   {
/* 274:331 */     return this.totalHaber;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void setTotalHaber(BigDecimal totalHaber)
/* 278:    */   {
/* 279:339 */     this.totalHaber = totalHaber;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public DimensionContable getProyecto()
/* 283:    */   {
/* 284:346 */     return this.proyecto;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setProyecto(DimensionContable proyecto)
/* 288:    */   {
/* 289:354 */     this.proyecto = proyecto;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public String getDocumentoReferencia()
/* 293:    */   {
/* 294:358 */     return this.documentoReferencia;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void setDocumentoReferencia(String documentoReferencia)
/* 298:    */   {
/* 299:362 */     this.documentoReferencia = documentoReferencia;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public Date getFechaChequePosfechado()
/* 303:    */   {
/* 304:369 */     return this.fechaChequePosfechado;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void setFechaChequePosfechado(Date fechaPagoChequePosfechado)
/* 308:    */   {
/* 309:377 */     this.fechaChequePosfechado = fechaPagoChequePosfechado;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public String getNotaPosfechado()
/* 313:    */   {
/* 314:384 */     return this.notaPosfechado;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void setNotaPosfechado(String notaPosfechado)
/* 318:    */   {
/* 319:392 */     this.notaPosfechado = notaPosfechado;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public BigDecimal getValorOriginal()
/* 323:    */   {
/* 324:396 */     return this.valorOriginal;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void setValorOriginal(BigDecimal valorOriginal)
/* 328:    */   {
/* 329:400 */     this.valorOriginal = valorOriginal;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public boolean isIndicadorAutomaticoEditado()
/* 333:    */   {
/* 334:404 */     return this.indicadorAutomaticoEditado;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void setIndicadorAutomaticoEditado(boolean indicadorAutomaticoEditado)
/* 338:    */   {
/* 339:408 */     this.indicadorAutomaticoEditado = indicadorAutomaticoEditado;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public String getStyleClass()
/* 343:    */   {
/* 344:412 */     if (isIndicadorAutomaticoEditado()) {
/* 345:413 */       this.styleClass = "color_verde";
/* 346:    */     }
/* 347:415 */     return this.styleClass;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void setStyleClass(String styleClass)
/* 351:    */   {
/* 352:419 */     this.styleClass = styleClass;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public String getConcepto2()
/* 356:    */   {
/* 357:423 */     return this.concepto2;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void setConcepto2(String concepto2)
/* 361:    */   {
/* 362:427 */     this.concepto2 = concepto2;
/* 363:    */   }
/* 364:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Asiento
 * JD-Core Version:    0.7.0.1
 */