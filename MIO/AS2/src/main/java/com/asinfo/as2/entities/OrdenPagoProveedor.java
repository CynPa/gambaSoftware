/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.EnumType;
/*  11:    */ import javax.persistence.Enumerated;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.OneToMany;
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.TableGenerator;
/*  21:    */ import javax.persistence.Temporal;
/*  22:    */ import javax.persistence.TemporalType;
/*  23:    */ import javax.persistence.Transient;
/*  24:    */ import javax.validation.constraints.Min;
/*  25:    */ import javax.validation.constraints.NotNull;
/*  26:    */ import javax.validation.constraints.Size;
/*  27:    */ 
/*  28:    */ @Entity
/*  29:    */ @Table(name="orden_pago_proveedor", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_documento", "numero"})})
/*  30:    */ public class OrdenPagoProveedor
/*  31:    */   extends EntidadBase
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 70211214498552263L;
/*  34:    */   @Id
/*  35:    */   @TableGenerator(name="orden_pago_proveedor", initialValue=0, allocationSize=50)
/*  36:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="orden_pago_proveedor")
/*  37:    */   @Column(name="id_orden_pago_proveedor", unique=true, nullable=false)
/*  38:    */   private int idOrdenPagoProveedor;
/*  39:    */   @Column(name="id_organizacion", nullable=false)
/*  40:    */   private int idOrganizacion;
/*  41:    */   @Column(name="id_sucursal", nullable=false)
/*  42:    */   private int idSucursal;
/*  43:    */   @Column(name="numero", nullable=false, length=20)
/*  44:    */   @NotNull
/*  45:    */   @Size(max=20)
/*  46:    */   private String numero;
/*  47:    */   @Temporal(TemporalType.DATE)
/*  48:    */   @Column(name="fecha_corte", nullable=false)
/*  49:    */   @NotNull
/*  50:    */   private Date fechaCorte;
/*  51:    */   @Column(name="valor_pago", precision=12, scale=2, nullable=false)
/*  52:    */   @Min(0L)
/*  53: 76 */   private BigDecimal valorPago = BigDecimal.ZERO;
/*  54:    */   @Column(name="valor_pendiente", precision=12, scale=2, nullable=false)
/*  55:    */   @Min(0L)
/*  56: 80 */   private BigDecimal valorPendiente = BigDecimal.ZERO;
/*  57:    */   @Column(name="valor_aprobado", precision=12, scale=2, nullable=false)
/*  58:    */   @Min(0L)
/*  59: 84 */   private BigDecimal valorAprobado = BigDecimal.ZERO;
/*  60:    */   @Column(name="valor_pagado", precision=12, scale=2, nullable=false)
/*  61:    */   @Min(0L)
/*  62: 88 */   private BigDecimal valorPagado = BigDecimal.ZERO;
/*  63:    */   @Enumerated(EnumType.ORDINAL)
/*  64:    */   @Column(name="estado", nullable=false)
/*  65:    */   @NotNull
/*  66:    */   private Estado estado;
/*  67:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  68:    */   @JoinColumn(name="id_documento", nullable=false)
/*  69:    */   @NotNull
/*  70:    */   private Documento documento;
/*  71:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  72:    */   @JoinColumn(name="id_documento_anticipo")
/*  73:    */   private Documento documentoAnticipo;
/*  74:    */   @Column(name="descripcion", nullable=true, length=200)
/*  75:    */   @Size(max=200)
/*  76:    */   private String descripcion;
/*  77:    */   @Column(name="usuario_revisor", nullable=true)
/*  78:    */   private String usuarioRevisor;
/*  79:    */   @Temporal(TemporalType.TIMESTAMP)
/*  80:    */   @Column(name="fecha_revision", nullable=true)
/*  81:    */   private Date fechaRevision;
/*  82:    */   @Column(name="usuario_cierre", nullable=true)
/*  83:    */   private String usuarioCierre;
/*  84:    */   @Temporal(TemporalType.TIMESTAMP)
/*  85:    */   @Column(name="fecha_cierre", nullable=true)
/*  86:    */   private Date fechaCierre;
/*  87:    */   @Column(name="pdf", nullable=true)
/*  88:    */   @Size(max=50)
/*  89:    */   private String pdf;
/*  90:    */   @OneToMany(mappedBy="ordenPagoProveedor", fetch=FetchType.LAZY)
/*  91:128 */   private List<DetalleOrdenPagoProveedor> listaDetalleOrdenPagoProveedor = new ArrayList();
/*  92:    */   @Transient
/*  93:    */   private Date fechaDesdeFiltro;
/*  94:    */   
/*  95:    */   public int getId()
/*  96:    */   {
/*  97:145 */     return this.idOrdenPagoProveedor;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int getIdOrdenPagoProveedor()
/* 101:    */   {
/* 102:149 */     return this.idOrdenPagoProveedor;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setIdOrdenPagoProveedor(int idOrdenPagoProveedor)
/* 106:    */   {
/* 107:153 */     this.idOrdenPagoProveedor = idOrdenPagoProveedor;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int getIdOrganizacion()
/* 111:    */   {
/* 112:157 */     return this.idOrganizacion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setIdOrganizacion(int idOrganizacion)
/* 116:    */   {
/* 117:161 */     this.idOrganizacion = idOrganizacion;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int getIdSucursal()
/* 121:    */   {
/* 122:165 */     return this.idSucursal;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setIdSucursal(int idSucursal)
/* 126:    */   {
/* 127:169 */     this.idSucursal = idSucursal;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String getNumero()
/* 131:    */   {
/* 132:173 */     return this.numero;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setNumero(String numero)
/* 136:    */   {
/* 137:177 */     this.numero = numero;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Date getFechaCorte()
/* 141:    */   {
/* 142:181 */     return this.fechaCorte;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setFechaCorte(Date fechaCorte)
/* 146:    */   {
/* 147:185 */     this.fechaCorte = fechaCorte;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public BigDecimal getValorPago()
/* 151:    */   {
/* 152:189 */     return this.valorPago;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setValorPago(BigDecimal valorPago)
/* 156:    */   {
/* 157:193 */     this.valorPago = valorPago;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Estado getEstado()
/* 161:    */   {
/* 162:197 */     return this.estado;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setEstado(Estado estado)
/* 166:    */   {
/* 167:201 */     this.estado = estado;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Documento getDocumento()
/* 171:    */   {
/* 172:205 */     return this.documento;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setDocumento(Documento documento)
/* 176:    */   {
/* 177:209 */     this.documento = documento;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Documento getDocumentoAnticipo()
/* 181:    */   {
/* 182:213 */     return this.documentoAnticipo;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setDocumentoAnticipo(Documento documentoAnticipo)
/* 186:    */   {
/* 187:217 */     this.documentoAnticipo = documentoAnticipo;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public List<DetalleOrdenPagoProveedor> getListaDetalleOrdenPagoProveedor()
/* 191:    */   {
/* 192:221 */     return this.listaDetalleOrdenPagoProveedor;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setListaDetalleOrdenPagoProveedor(List<DetalleOrdenPagoProveedor> listaDetalleOrdenPagoProveedor)
/* 196:    */   {
/* 197:225 */     this.listaDetalleOrdenPagoProveedor = listaDetalleOrdenPagoProveedor;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public String getDescripcion()
/* 201:    */   {
/* 202:229 */     return this.descripcion;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setDescripcion(String descripcion)
/* 206:    */   {
/* 207:233 */     this.descripcion = descripcion;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public BigDecimal getValorPendiente()
/* 211:    */   {
/* 212:237 */     return this.valorPendiente;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setValorPendiente(BigDecimal valorPendiente)
/* 216:    */   {
/* 217:241 */     this.valorPendiente = valorPendiente;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public BigDecimal getValorAprobado()
/* 221:    */   {
/* 222:245 */     return this.valorAprobado;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setValorAprobado(BigDecimal valorAprobado)
/* 226:    */   {
/* 227:249 */     this.valorAprobado = valorAprobado;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public BigDecimal getValorPagado()
/* 231:    */   {
/* 232:253 */     return this.valorPagado;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setValorPagado(BigDecimal valorPagado)
/* 236:    */   {
/* 237:257 */     this.valorPagado = valorPagado;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public BigDecimal getValorDiferencia()
/* 241:    */   {
/* 242:261 */     return this.valorPendiente.subtract(this.valorPago);
/* 243:    */   }
/* 244:    */   
/* 245:    */   public BigDecimal getValorDiferenciaAprobado()
/* 246:    */   {
/* 247:265 */     return this.valorPago.subtract(this.valorAprobado);
/* 248:    */   }
/* 249:    */   
/* 250:    */   public String getUsuarioRevisor()
/* 251:    */   {
/* 252:269 */     return this.usuarioRevisor;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setUsuarioRevisor(String usuarioRevisor)
/* 256:    */   {
/* 257:273 */     this.usuarioRevisor = usuarioRevisor;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public Date getFechaRevision()
/* 261:    */   {
/* 262:277 */     return this.fechaRevision;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setFechaRevision(Date fechaRevision)
/* 266:    */   {
/* 267:281 */     this.fechaRevision = fechaRevision;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public String getUsuarioCierre()
/* 271:    */   {
/* 272:285 */     return this.usuarioCierre;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setUsuarioCierre(String usuarioCierre)
/* 276:    */   {
/* 277:289 */     this.usuarioCierre = usuarioCierre;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public Date getFechaCierre()
/* 281:    */   {
/* 282:293 */     return this.fechaCierre;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setFechaCierre(Date fechaCierre)
/* 286:    */   {
/* 287:297 */     this.fechaCierre = fechaCierre;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public Date getFechaDesdeFiltro()
/* 291:    */   {
/* 292:304 */     return this.fechaDesdeFiltro;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setFechaDesdeFiltro(Date fechaDesdeFiltro)
/* 296:    */   {
/* 297:308 */     this.fechaDesdeFiltro = fechaDesdeFiltro;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public String getPdf()
/* 301:    */   {
/* 302:312 */     return this.pdf;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setPdf(String pdf)
/* 306:    */   {
/* 307:316 */     this.pdf = pdf;
/* 308:    */   }
/* 309:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.OrdenPagoProveedor
 * JD-Core Version:    0.7.0.1
 */