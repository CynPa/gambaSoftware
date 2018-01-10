/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
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
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.persistence.Temporal;
/*  18:    */ import javax.persistence.TemporalType;
/*  19:    */ import javax.persistence.Transient;
/*  20:    */ import javax.validation.constraints.NotNull;
/*  21:    */ import javax.validation.constraints.Size;
/*  22:    */ import org.hibernate.annotations.ColumnDefault;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="periodo")
/*  26:    */ public class Periodo
/*  27:    */   extends EntidadBase
/*  28:    */   implements Serializable
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @Id
/*  32:    */   @TableGenerator(name="periodo", initialValue=0, allocationSize=50)
/*  33:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="periodo")
/*  34:    */   @Column(name="id_periodo", unique=true, nullable=false)
/*  35:    */   private int idPeriodo;
/*  36:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_ejercicio")
/*  38:    */   private Ejercicio ejercicio;
/*  39:    */   @Column(name="id_organizacion")
/*  40:    */   private int idOrganizacion;
/*  41:    */   @Column(name="id_sucursal")
/*  42:    */   private int idSucursal;
/*  43:    */   @Column(name="nombre", nullable=false, length=50)
/*  44:    */   @NotNull
/*  45:    */   @Size(min=2, max=50)
/*  46:    */   private String nombre;
/*  47:    */   @Temporal(TemporalType.DATE)
/*  48:    */   @Column(name="fecha_desde", nullable=false, length=23)
/*  49:    */   @NotNull
/*  50:    */   private Date fechaDesde;
/*  51:    */   @Temporal(TemporalType.DATE)
/*  52:    */   @Column(name="fecha_hasta", nullable=false, length=23)
/*  53:    */   @NotNull
/*  54:    */   private Date fechaHasta;
/*  55:    */   @Column(name="descripcion", length=200)
/*  56:    */   @Size(max=200)
/*  57:    */   private String descripcion;
/*  58:    */   @Transient
/*  59:    */   private boolean activo;
/*  60:    */   @Column(name="indicador_cierre_cobros", nullable=false)
/*  61:    */   @NotNull
/*  62:    */   @ColumnDefault("'0'")
/*  63:    */   private boolean indicadorCierreCobros;
/*  64:    */   @Column(name="indicador_cierre_pagos", nullable=false)
/*  65:    */   @NotNull
/*  66:    */   @ColumnDefault("'0'")
/*  67:    */   private boolean indicadorCierrePagos;
/*  68:    */   @Column(name="indicador_cierre_contabilidad", nullable=false)
/*  69:    */   @NotNull
/*  70:    */   @ColumnDefault("'0'")
/*  71:    */   private boolean indicadorCierreContabilidad;
/*  72:    */   @Column(name="indicador_cierre_presupuesto", nullable=false)
/*  73:    */   @NotNull
/*  74:    */   @ColumnDefault("'0'")
/*  75:    */   private boolean indicadorCierrePresupuesto;
/*  76:    */   @Column(name="indicador_cierre_activos_fijos", nullable=false)
/*  77:    */   @NotNull
/*  78:    */   @ColumnDefault("'0'")
/*  79:    */   private boolean indicadorCierreActivosFijos;
/*  80:    */   @Column(name="indicador_cierre_compras", nullable=false)
/*  81:    */   @NotNull
/*  82:    */   @ColumnDefault("'0'")
/*  83:    */   private boolean indicadorCierreCompras;
/*  84:    */   @Column(name="indicador_cierre_ventas", nullable=false)
/*  85:    */   @NotNull
/*  86:    */   @ColumnDefault("'0'")
/*  87:    */   private boolean indicadorCierreVentas;
/*  88:    */   @Column(name="indicador_cierre_produccion", nullable=false)
/*  89:    */   @NotNull
/*  90:    */   @ColumnDefault("'0'")
/*  91:    */   private boolean indicadorCierreProduccion;
/*  92:    */   @Column(name="indicador_cierre_inventario", nullable=false)
/*  93:    */   @NotNull
/*  94:    */   @ColumnDefault("'0'")
/*  95:    */   private boolean indicadorCierreInventario;
/*  96:    */   @Column(name="indicador_cierre_nomina", nullable=false)
/*  97:    */   @NotNull
/*  98:    */   @ColumnDefault("'0'")
/*  99:    */   private boolean indicadorCierreNomina;
/* 100:    */   
/* 101:    */   public int getIdPeriodo()
/* 102:    */   {
/* 103:134 */     return this.idPeriodo;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setIdPeriodo(int idPeriodo)
/* 107:    */   {
/* 108:144 */     this.idPeriodo = idPeriodo;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Ejercicio getEjercicio()
/* 112:    */   {
/* 113:153 */     return this.ejercicio;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setEjercicio(Ejercicio ejercicio)
/* 117:    */   {
/* 118:163 */     this.ejercicio = ejercicio;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getIdOrganizacion()
/* 122:    */   {
/* 123:172 */     return this.idOrganizacion;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setIdOrganizacion(int idOrganizacion)
/* 127:    */   {
/* 128:182 */     this.idOrganizacion = idOrganizacion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public int getIdSucursal()
/* 132:    */   {
/* 133:191 */     return this.idSucursal;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setIdSucursal(int idSucursal)
/* 137:    */   {
/* 138:201 */     this.idSucursal = idSucursal;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Date getFechaDesde()
/* 142:    */   {
/* 143:210 */     return this.fechaDesde;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setFechaDesde(Date fechaDesde)
/* 147:    */   {
/* 148:220 */     this.fechaDesde = fechaDesde;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Date getFechaHasta()
/* 152:    */   {
/* 153:229 */     return this.fechaHasta;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setFechaHasta(Date fechaHasta)
/* 157:    */   {
/* 158:239 */     this.fechaHasta = fechaHasta;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String getDescripcion()
/* 162:    */   {
/* 163:248 */     return this.descripcion;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setDescripcion(String descripcion)
/* 167:    */   {
/* 168:258 */     this.descripcion = descripcion;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean isActivo()
/* 172:    */   {
/* 173:267 */     return this.activo;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setActivo(boolean activo)
/* 177:    */   {
/* 178:277 */     this.activo = activo;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String getNombre()
/* 182:    */   {
/* 183:286 */     return this.nombre;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setNombre(String nombre)
/* 187:    */   {
/* 188:296 */     this.nombre = nombre;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean isIndicadorCierreVentas()
/* 192:    */   {
/* 193:300 */     return this.indicadorCierreVentas;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setIndicadorCierreVentas(boolean indicadorCierreVentas)
/* 197:    */   {
/* 198:304 */     this.indicadorCierreVentas = indicadorCierreVentas;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public boolean isIndicadorCierreProduccion()
/* 202:    */   {
/* 203:308 */     return this.indicadorCierreProduccion;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setIndicadorCierreProduccion(boolean indicadorCierreProduccion)
/* 207:    */   {
/* 208:312 */     this.indicadorCierreProduccion = indicadorCierreProduccion;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public boolean isIndicadorCierreInventario()
/* 212:    */   {
/* 213:316 */     return this.indicadorCierreInventario;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setIndicadorCierreInventario(boolean indicadorCierreInventario)
/* 217:    */   {
/* 218:320 */     this.indicadorCierreInventario = indicadorCierreInventario;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public boolean isIndicadorCierreNomina()
/* 222:    */   {
/* 223:324 */     return this.indicadorCierreNomina;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setIndicadorCierreNomina(boolean indicadorCierreNomina)
/* 227:    */   {
/* 228:328 */     this.indicadorCierreNomina = indicadorCierreNomina;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public boolean isIndicadorCierreCompras()
/* 232:    */   {
/* 233:332 */     return this.indicadorCierreCompras;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setIndicadorCierreCompras(boolean indicadorCierreCompras)
/* 237:    */   {
/* 238:336 */     this.indicadorCierreCompras = indicadorCierreCompras;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public boolean isIndicadorCierreCobros()
/* 242:    */   {
/* 243:340 */     return this.indicadorCierreCobros;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setIndicadorCierreCobros(boolean indicadorCierreCobros)
/* 247:    */   {
/* 248:344 */     this.indicadorCierreCobros = indicadorCierreCobros;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public boolean isIndicadorCierrePagos()
/* 252:    */   {
/* 253:348 */     return this.indicadorCierrePagos;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setIndicadorCierrePagos(boolean indicadorCierrePagos)
/* 257:    */   {
/* 258:352 */     this.indicadorCierrePagos = indicadorCierrePagos;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public boolean isIndicadorCierreContabilidad()
/* 262:    */   {
/* 263:356 */     return this.indicadorCierreContabilidad;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setIndicadorCierreContabilidad(boolean indicadorCierreContabilidad)
/* 267:    */   {
/* 268:360 */     this.indicadorCierreContabilidad = indicadorCierreContabilidad;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public boolean isIndicadorCierrePresupuesto()
/* 272:    */   {
/* 273:364 */     return this.indicadorCierrePresupuesto;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setIndicadorCierrePresupuesto(boolean indicadorCierrePresupuesto)
/* 277:    */   {
/* 278:368 */     this.indicadorCierrePresupuesto = indicadorCierrePresupuesto;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public boolean isIndicadorCierreActivosFijos()
/* 282:    */   {
/* 283:372 */     return this.indicadorCierreActivosFijos;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setIndicadorCierreActivosFijos(boolean indicadorCierreActivosFijos)
/* 287:    */   {
/* 288:376 */     this.indicadorCierreActivosFijos = indicadorCierreActivosFijos;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public List<String> getCamposAuditables()
/* 292:    */   {
/* 293:380 */     List<String> lista = new ArrayList();
/* 294:381 */     lista.add("nombre");
/* 295:382 */     lista.add("fecha_desde");
/* 296:383 */     lista.add("fechaHasta");
/* 297:384 */     lista.add("descripcion");
/* 298:385 */     lista.add("activo");
/* 299:386 */     lista.add("indicadorCierreFinanciero");
/* 300:387 */     lista.add("indicadorCierreVentas");
/* 301:388 */     lista.add("indicadorCierreProduccion");
/* 302:389 */     lista.add("indicadorCierreRrhh");
/* 303:390 */     lista.add("indicadorCierreInventario");
/* 304:391 */     lista.add("indicadorCierreNomina");
/* 305:392 */     lista.add("indicadorCierreCompras");
/* 306:393 */     lista.add("indicadorCierreCrm");
/* 307:394 */     return lista;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public int getId()
/* 311:    */   {
/* 312:404 */     return getIdPeriodo();
/* 313:    */   }
/* 314:    */   
/* 315:    */   public String toString()
/* 316:    */   {
/* 317:409 */     return this.nombre;
/* 318:    */   }
/* 319:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Periodo
 * JD-Core Version:    0.7.0.1
 */