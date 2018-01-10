/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.OneToMany;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.persistence.Temporal;
/*  19:    */ import javax.persistence.TemporalType;
/*  20:    */ import javax.persistence.Transient;
/*  21:    */ import javax.validation.constraints.Digits;
/*  22:    */ import javax.validation.constraints.Min;
/*  23:    */ import javax.validation.constraints.NotNull;
/*  24:    */ import javax.validation.constraints.Size;
/*  25:    */ 
/*  26:    */ @Entity
/*  27:    */ @Table(name="detalle_prestamo")
/*  28:    */ public class DetallePrestamo
/*  29:    */   extends EntidadBase
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = -1220964633105888307L;
/*  32:    */   @Id
/*  33:    */   @TableGenerator(name="detalle_prestamo", initialValue=0, allocationSize=50)
/*  34:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_prestamo")
/*  35:    */   @Column(name="id_detalle_prestamo")
/*  36:    */   private int idDetallePrestamo;
/*  37:    */   @Column(name="numero_cuota", nullable=false)
/*  38:    */   @Min(0L)
/*  39:    */   private int numeroCuota;
/*  40:    */   @Column(name="fecha_cuota", nullable=false)
/*  41:    */   @Temporal(TemporalType.DATE)
/*  42:    */   private Date fechaCuota;
/*  43:    */   @Column(name="fecha_descuento", nullable=true)
/*  44:    */   @Temporal(TemporalType.DATE)
/*  45:    */   private Date fechaDescuento;
/*  46:    */   @Column(name="cuota", scale=12, precision=2, nullable=false)
/*  47:    */   @NotNull
/*  48:    */   @Digits(integer=12, fraction=2)
/*  49:    */   @Min(0L)
/*  50:    */   private BigDecimal cuota;
/*  51:    */   @Column(name="capital_cuota", scale=12, precision=2, nullable=false)
/*  52:    */   @NotNull
/*  53:    */   @Digits(integer=12, fraction=2)
/*  54:    */   @Min(0L)
/*  55:    */   private BigDecimal capitalCuota;
/*  56:    */   @Column(name="procentaje_interes_cuota", scale=12, precision=2, nullable=false)
/*  57:    */   @NotNull
/*  58:    */   @Digits(integer=12, fraction=2)
/*  59:    */   @Min(0L)
/*  60:    */   private BigDecimal porcentajeInteresCuota;
/*  61:    */   @Column(name="interes_cuota", scale=12, precision=2, nullable=false)
/*  62:    */   @NotNull
/*  63:    */   @Digits(integer=12, fraction=2)
/*  64:    */   @Min(0L)
/*  65:    */   private BigDecimal interesCuota;
/*  66:    */   @Column(name="procentaje_interes_mora_cuota", scale=12, precision=2, nullable=true)
/*  67:    */   @Digits(integer=12, fraction=2)
/*  68:    */   @Min(0L)
/*  69:    */   private BigDecimal porcentajeInteresMoraCuota;
/*  70:    */   @Column(name="interes_mora_cuota", scale=12, precision=2, nullable=true)
/*  71:    */   @Digits(integer=12, fraction=2)
/*  72:    */   @Min(0L)
/*  73:    */   private BigDecimal interesMoraCuota;
/*  74:    */   @Column(name="saldo_capital_cuota", scale=12, precision=2, nullable=false)
/*  75:    */   @NotNull
/*  76:    */   @Digits(integer=12, fraction=2)
/*  77:    */   @Min(0L)
/*  78:    */   private BigDecimal saldoCapitalCuota;
/*  79:    */   @Column(name="saldo_interes_cuota", scale=12, precision=2, nullable=false)
/*  80:    */   @NotNull
/*  81:    */   @Digits(integer=12, fraction=2)
/*  82:    */   @Min(0L)
/*  83:    */   private BigDecimal saldoInteresCuota;
/*  84:    */   @Column(name="saldo_interes_mora_cuota", scale=12, precision=2, nullable=true)
/*  85:    */   @Digits(integer=12, fraction=2)
/*  86:    */   @Min(0L)
/*  87:    */   private BigDecimal saldoInteresMoraCuota;
/*  88:    */   @Column(name="descripcion", nullable=true, length=200)
/*  89:    */   @Size(max=200)
/*  90:    */   private String descripcion;
/*  91:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  92:    */   @JoinColumn(name="id_prestamo")
/*  93:    */   private Prestamo prestamo;
/*  94:    */   @OneToMany(mappedBy="detallePrestamo", fetch=FetchType.LAZY)
/*  95:135 */   private List<DetallePagoCuotaPrestamo> listaDetallePagoCuotaPrestamo = new ArrayList();
/*  96:    */   @Transient
/*  97:    */   private BigDecimal capitalTotal;
/*  98:    */   @Transient
/*  99:    */   private BigDecimal saldoTotal;
/* 100:    */   
/* 101:    */   public int getId()
/* 102:    */   {
/* 103:166 */     return this.idDetallePrestamo;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getIdDetallePrestamo()
/* 107:    */   {
/* 108:175 */     return this.idDetallePrestamo;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setIdDetallePrestamo(int idDetallePrestamo)
/* 112:    */   {
/* 113:185 */     this.idDetallePrestamo = idDetallePrestamo;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int getNumeroCuota()
/* 117:    */   {
/* 118:194 */     return this.numeroCuota;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setNumeroCuota(int numeroCuota)
/* 122:    */   {
/* 123:204 */     this.numeroCuota = numeroCuota;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public BigDecimal getCapitalCuota()
/* 127:    */   {
/* 128:213 */     return this.capitalCuota;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setCapitalCuota(BigDecimal capitalCuota)
/* 132:    */   {
/* 133:223 */     this.capitalCuota = capitalCuota;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public BigDecimal getPorcentajeInteresCuota()
/* 137:    */   {
/* 138:232 */     return this.porcentajeInteresCuota;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setPorcentajeInteresCuota(BigDecimal porcentajeInteresCuota)
/* 142:    */   {
/* 143:242 */     this.porcentajeInteresCuota = porcentajeInteresCuota;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public BigDecimal getInteresCuota()
/* 147:    */   {
/* 148:251 */     return this.interesCuota;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setInteresCuota(BigDecimal interesCuota)
/* 152:    */   {
/* 153:261 */     this.interesCuota = interesCuota;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public BigDecimal getPorcentajeInteresMoraCuota()
/* 157:    */   {
/* 158:270 */     return this.porcentajeInteresMoraCuota;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setPorcentajeInteresMoraCuota(BigDecimal porcentajeInteresMoraCuota)
/* 162:    */   {
/* 163:280 */     this.porcentajeInteresMoraCuota = porcentajeInteresMoraCuota;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public BigDecimal getInteresMoraCuota()
/* 167:    */   {
/* 168:289 */     return this.interesMoraCuota;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setInteresMoraCuota(BigDecimal interesMoraCuota)
/* 172:    */   {
/* 173:299 */     this.interesMoraCuota = interesMoraCuota;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public BigDecimal getSaldoCapitalCuota()
/* 177:    */   {
/* 178:308 */     return this.saldoCapitalCuota;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setSaldoCapitalCuota(BigDecimal saldoCapitalCuota)
/* 182:    */   {
/* 183:318 */     this.saldoCapitalCuota = saldoCapitalCuota;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public BigDecimal getSaldoInteresCuota()
/* 187:    */   {
/* 188:327 */     return this.saldoInteresCuota;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setSaldoInteresCuota(BigDecimal saldoInteresCuota)
/* 192:    */   {
/* 193:337 */     this.saldoInteresCuota = saldoInteresCuota;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public BigDecimal getSaldoInteresMoraCuota()
/* 197:    */   {
/* 198:346 */     return this.saldoInteresMoraCuota;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setSaldoInteresMoraCuota(BigDecimal saldoInteresMoraCuota)
/* 202:    */   {
/* 203:356 */     this.saldoInteresMoraCuota = saldoInteresMoraCuota;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public Prestamo getPrestamo()
/* 207:    */   {
/* 208:365 */     return this.prestamo;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setPrestamo(Prestamo prestamo)
/* 212:    */   {
/* 213:375 */     this.prestamo = prestamo;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public BigDecimal getCuota()
/* 217:    */   {
/* 218:384 */     return this.cuota;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setCuota(BigDecimal cuota)
/* 222:    */   {
/* 223:394 */     this.cuota = cuota;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public BigDecimal getCapitalTotal()
/* 227:    */   {
/* 228:403 */     return this.capitalTotal;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setCapitalTotal(BigDecimal capitalTotal)
/* 232:    */   {
/* 233:413 */     this.capitalTotal = capitalTotal;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public BigDecimal getSaldoTotal()
/* 237:    */   {
/* 238:422 */     return this.saldoTotal;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setSaldoTotal(BigDecimal saldoTotal)
/* 242:    */   {
/* 243:432 */     this.saldoTotal = saldoTotal;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public Date getFechaCuota()
/* 247:    */   {
/* 248:441 */     return this.fechaCuota;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setFechaCuota(Date fechaCuota)
/* 252:    */   {
/* 253:451 */     this.fechaCuota = fechaCuota;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public List<DetallePagoCuotaPrestamo> getListaDetallePagoCuotaPrestamo()
/* 257:    */   {
/* 258:460 */     return this.listaDetallePagoCuotaPrestamo;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setListaDetallePagoCuotaPrestamo(List<DetallePagoCuotaPrestamo> listaDetallePagoCuotaPrestamo)
/* 262:    */   {
/* 263:470 */     this.listaDetallePagoCuotaPrestamo = listaDetallePagoCuotaPrestamo;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public Date getFechaDescuento()
/* 267:    */   {
/* 268:474 */     return this.fechaDescuento;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void setFechaDescuento(Date fechaDescuento)
/* 272:    */   {
/* 273:478 */     this.fechaDescuento = fechaDescuento;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public String getDescripcion()
/* 277:    */   {
/* 278:482 */     return this.descripcion;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void setDescripcion(String descripcion)
/* 282:    */   {
/* 283:486 */     this.descripcion = descripcion;
/* 284:    */   }
/* 285:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetallePrestamo
 * JD-Core Version:    0.7.0.1
 */