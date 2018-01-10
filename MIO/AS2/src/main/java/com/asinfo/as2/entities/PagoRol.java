/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.listener.PagoRolListener;
/*   4:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   5:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.persistence.Column;
/*  11:    */ import javax.persistence.Entity;
/*  12:    */ import javax.persistence.EntityListeners;
/*  13:    */ import javax.persistence.EnumType;
/*  14:    */ import javax.persistence.Enumerated;
/*  15:    */ import javax.persistence.FetchType;
/*  16:    */ import javax.persistence.GeneratedValue;
/*  17:    */ import javax.persistence.GenerationType;
/*  18:    */ import javax.persistence.Id;
/*  19:    */ import javax.persistence.JoinColumn;
/*  20:    */ import javax.persistence.ManyToOne;
/*  21:    */ import javax.persistence.OneToMany;
/*  22:    */ import javax.persistence.Table;
/*  23:    */ import javax.persistence.TableGenerator;
/*  24:    */ import javax.persistence.Temporal;
/*  25:    */ import javax.persistence.TemporalType;
/*  26:    */ import javax.persistence.Transient;
/*  27:    */ import javax.validation.constraints.Min;
/*  28:    */ import javax.validation.constraints.NotNull;
/*  29:    */ 
/*  30:    */ @Entity
/*  31:    */ @Table(name="pago_rol", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"anio", "mes", "id_quincena", "id_organizacion"})})
/*  32:    */ @EntityListeners({PagoRolListener.class})
/*  33:    */ public class PagoRol
/*  34:    */   extends EntidadBase
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @Id
/*  38:    */   @TableGenerator(name="pago_rol", initialValue=0, allocationSize=50)
/*  39:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="pago_rol")
/*  40:    */   @Column(name="id_pago_rol")
/*  41:    */   private int idPagoRol;
/*  42:    */   @Column(name="id_organizacion", nullable=false)
/*  43:    */   private int idOrganizacion;
/*  44:    */   @Column(name="id_sucursal", nullable=false)
/*  45:    */   private int idSucursal;
/*  46:    */   @Column(name="anio", nullable=false)
/*  47:    */   @Min(0L)
/*  48: 75 */   private int anio = FuncionesUtiles.getAnio(new Date());
/*  49:    */   @Column(name="mes", nullable=false)
/*  50:    */   @Min(0L)
/*  51:    */   private int mes;
/*  52:    */   @Temporal(TemporalType.DATE)
/*  53:    */   @Column(name="fecha", nullable=false)
/*  54:    */   @NotNull
/*  55:    */   private Date fecha;
/*  56:    */   @Temporal(TemporalType.DATE)
/*  57:    */   @Column(name="fecha_contabilizar", nullable=true)
/*  58:    */   private Date fechaContabilizar;
/*  59:    */   @Column(name="estado", nullable=false)
/*  60:    */   @Enumerated(EnumType.ORDINAL)
/*  61:    */   private Estado estado;
/*  62:    */   @Column(name="dias_mes", nullable=false)
/*  63:    */   @Min(0L)
/*  64:    */   private int diasMes;
/*  65:    */   @Column(name="indicador_saldo_inicial", nullable=false)
/*  66:    */   private boolean indicadorSaldoInicial;
/*  67:    */   @Column(name="indicador_finiquito", nullable=false)
/*  68:    */   private boolean indicadorFiniquito;
/*  69:    */   @Column(name="nombre_empleado_finiquito", nullable=true)
/*  70:    */   private String nombreEmpleadoFiniquito;
/*  71:    */   @Transient
/*  72:    */   private String nombreMes;
/*  73:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  74:    */   @JoinColumn(name="id_documento", nullable=false)
/*  75:    */   @NotNull
/*  76:    */   private Documento documento;
/*  77:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  78:    */   @JoinColumn(name="id_quincena", nullable=false)
/*  79:    */   @NotNull
/*  80:    */   private Quincena quincena;
/*  81:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  82:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  83:    */   private Asiento asiento;
/*  84:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="pagoRol")
/*  85:128 */   private List<PagoRolEmpleado> listaPagoRolEmpleado = new ArrayList();
/*  86:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="pagoRol")
/*  87:131 */   private List<Utilidad> listaUtilidades = new ArrayList();
/*  88:    */   @Column(name="indicador_generado_por_utilidad", nullable=true)
/*  89:    */   private Boolean indicadorGeneradoPorUtilidad;
/*  90:    */   @Column(name="indicador_reprocesar", nullable=true)
/*  91:    */   private Boolean indicadorReprocesar;
/*  92:    */   
/*  93:    */   public List<Utilidad> getListaUtilidades()
/*  94:    */   {
/*  95:146 */     return this.listaUtilidades;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setListaUtilidades(List<Utilidad> listaUtilidades)
/*  99:    */   {
/* 100:150 */     this.listaUtilidades = listaUtilidades;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int getIdPagoRol()
/* 104:    */   {
/* 105:171 */     return this.idPagoRol;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setIdPagoRol(int idPagoRol)
/* 109:    */   {
/* 110:181 */     this.idPagoRol = idPagoRol;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int getIdOrganizacion()
/* 114:    */   {
/* 115:190 */     return this.idOrganizacion;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setIdOrganizacion(int idOrganizacion)
/* 119:    */   {
/* 120:200 */     this.idOrganizacion = idOrganizacion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public int getIdSucursal()
/* 124:    */   {
/* 125:209 */     return this.idSucursal;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setIdSucursal(int idSucursal)
/* 129:    */   {
/* 130:219 */     this.idSucursal = idSucursal;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public int getAnio()
/* 134:    */   {
/* 135:228 */     return this.anio;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setAnio(int anio)
/* 139:    */   {
/* 140:238 */     this.anio = anio;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public int getMes()
/* 144:    */   {
/* 145:247 */     return this.mes;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setMes(int mes)
/* 149:    */   {
/* 150:257 */     this.mes = mes;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public Date getFecha()
/* 154:    */   {
/* 155:266 */     return this.fecha;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setFecha(Date fecha)
/* 159:    */   {
/* 160:276 */     this.fecha = fecha;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Estado getEstado()
/* 164:    */   {
/* 165:285 */     return this.estado;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setEstado(Estado estado)
/* 169:    */   {
/* 170:295 */     this.estado = estado;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Documento getDocumento()
/* 174:    */   {
/* 175:304 */     return this.documento;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setDocumento(Documento documento)
/* 179:    */   {
/* 180:314 */     this.documento = documento;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public Quincena getQuincena()
/* 184:    */   {
/* 185:323 */     return this.quincena;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setQuincena(Quincena quincena)
/* 189:    */   {
/* 190:333 */     this.quincena = quincena;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Asiento getAsiento()
/* 194:    */   {
/* 195:342 */     return this.asiento;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setAsiento(Asiento asiento)
/* 199:    */   {
/* 200:352 */     this.asiento = asiento;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public List<PagoRolEmpleado> getListaPagoRolEmpleado()
/* 204:    */   {
/* 205:361 */     return this.listaPagoRolEmpleado;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public List<PagoRolEmpleado> getListaPagoRolEmpleadoNegativo()
/* 209:    */   {
/* 210:365 */     List<PagoRolEmpleado> listaPagoRolEmpleadoNegativo = new ArrayList();
/* 211:    */     try
/* 212:    */     {
/* 213:367 */       for (PagoRolEmpleado pagoRolEmpleado : getListaPagoRolEmpleado()) {
/* 214:368 */         if (pagoRolEmpleado.getValorAPagar().compareTo(BigDecimal.ZERO) < 0) {
/* 215:369 */           listaPagoRolEmpleadoNegativo.add(pagoRolEmpleado);
/* 216:    */         }
/* 217:    */       }
/* 218:    */     }
/* 219:    */     catch (Exception localException1) {}
/* 220:376 */     return listaPagoRolEmpleadoNegativo;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setListaPagoRolEmpleado(List<PagoRolEmpleado> listaPagoRolEmpleado)
/* 224:    */   {
/* 225:386 */     this.listaPagoRolEmpleado = listaPagoRolEmpleado;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public int getDiasMes()
/* 229:    */   {
/* 230:395 */     return this.diasMes;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setDiasMes(int diasMes)
/* 234:    */   {
/* 235:405 */     this.diasMes = diasMes;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public Date getFechaContabilizar()
/* 239:    */   {
/* 240:414 */     return this.fechaContabilizar;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setFechaContabilizar(Date fechaContabilizar)
/* 244:    */   {
/* 245:424 */     this.fechaContabilizar = fechaContabilizar;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public int getId()
/* 249:    */   {
/* 250:434 */     return this.idPagoRol;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public boolean isIndicadorSaldoInicial()
/* 254:    */   {
/* 255:443 */     return this.indicadorSaldoInicial;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setIndicadorSaldoInicial(boolean indicadorSaldoInicial)
/* 259:    */   {
/* 260:453 */     this.indicadorSaldoInicial = indicadorSaldoInicial;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public String getNombreMes()
/* 264:    */   {
/* 265:462 */     this.nombreMes = FuncionesUtiles.nombreMes(getMes() - 1);
/* 266:463 */     return this.nombreMes;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setNombreMes(String nombreMes)
/* 270:    */   {
/* 271:473 */     this.nombreMes = nombreMes;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public boolean isIndicadorFiniquito()
/* 275:    */   {
/* 276:482 */     return this.indicadorFiniquito;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setIndicadorFiniquito(boolean indicadorFiniquito)
/* 280:    */   {
/* 281:492 */     this.indicadorFiniquito = indicadorFiniquito;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public String getNombreEmpleadoFiniquito()
/* 285:    */   {
/* 286:496 */     return this.nombreEmpleadoFiniquito;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setNombreEmpleadoFiniquito(String nombreEmpleadoFiniquito)
/* 290:    */   {
/* 291:500 */     this.nombreEmpleadoFiniquito = nombreEmpleadoFiniquito;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public Boolean getIndicadorGeneradoPorUtilidad()
/* 295:    */   {
/* 296:507 */     if (this.indicadorGeneradoPorUtilidad == null) {
/* 297:508 */       this.indicadorGeneradoPorUtilidad = Boolean.valueOf(false);
/* 298:    */     }
/* 299:510 */     return this.indicadorGeneradoPorUtilidad;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setIndicadorGeneradoPorUtilidad(Boolean indicadorGeneradoPorUtilidad)
/* 303:    */   {
/* 304:517 */     this.indicadorGeneradoPorUtilidad = indicadorGeneradoPorUtilidad;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public Boolean getIndicadorReprocesar()
/* 308:    */   {
/* 309:521 */     return this.indicadorReprocesar;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setIndicadorReprocesar(Boolean indicadorReprocesar)
/* 313:    */   {
/* 314:525 */     this.indicadorReprocesar = indicadorReprocesar;
/* 315:    */   }
/* 316:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.PagoRol
 * JD-Core Version:    0.7.0.1
 */