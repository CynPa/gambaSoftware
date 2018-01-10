/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.AprobacionPagoRol;
/*   4:    */ import com.asinfo.as2.entities.nomina.asistencia.TurnoDepartamento;
/*   5:    */ import com.asinfo.as2.enumeraciones.TipoDepartamento;
/*   6:    */ import java.math.BigDecimal;
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
/*  22:    */ import javax.persistence.Transient;
/*  23:    */ import javax.validation.constraints.NotNull;
/*  24:    */ import javax.validation.constraints.Size;
/*  25:    */ 
/*  26:    */ @Entity
/*  27:    */ @Table(name="departamento", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  28:    */ public class Departamento
/*  29:    */   extends EntidadBase
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 289343794708306987L;
/*  32:    */   @Id
/*  33:    */   @TableGenerator(name="departamento", initialValue=0, allocationSize=50)
/*  34:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="departamento")
/*  35:    */   @Column(name="id_departamento")
/*  36:    */   private int idDepartamento;
/*  37:    */   @Column(name="id_organizacion", nullable=false)
/*  38:    */   private int idOrganizacion;
/*  39:    */   @Column(name="id_sucursal", nullable=false)
/*  40:    */   private int idSucursal;
/*  41:    */   @Column(name="codigo", nullable=false)
/*  42:    */   @Size(min=2, max=10)
/*  43:    */   private String codigo;
/*  44:    */   @Column(name="nombre", nullable=false, length=50)
/*  45:    */   @NotNull
/*  46:    */   @Size(min=2, max=50)
/*  47:    */   private String nombre;
/*  48:    */   @Column(name="descripcion", nullable=true)
/*  49:    */   @Size(max=200)
/*  50:    */   private String descripcion;
/*  51:    */   @Column(name="activo", nullable=false)
/*  52:    */   private boolean activo;
/*  53:    */   @Column(name="predeterminado", nullable=false)
/*  54:    */   private boolean predeterminado;
/*  55:    */   @Enumerated(EnumType.ORDINAL)
/*  56:    */   @Column(name="tipo_departamento", nullable=false)
/*  57:    */   @NotNull
/*  58:    */   private TipoDepartamento tipoDepartamento;
/*  59:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  60:    */   @JoinColumn(name="id_empleado")
/*  61:    */   private Empleado supervisor;
/*  62:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  63:    */   @JoinColumn(name="id_empleado2")
/*  64:    */   private Empleado supervisor2;
/*  65:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="departamento")
/*  66:105 */   private List<CentroTrabajo> listaCentroTrabajo = new ArrayList();
/*  67:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="departamento")
/*  68:108 */   private List<DocumentoDigitalizadoDepartamento> listaDocumentoDigitalizadoDepartamento = new ArrayList();
/*  69:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="departamento")
/*  70:111 */   private List<DepartamentoRubro> listaDepartamentoRubro = new ArrayList();
/*  71:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="departamento")
/*  72:114 */   private List<TurnoDepartamento> listaTurnoDepartamento = new ArrayList();
/*  73:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="departamento")
/*  74:    */   private List<RubroDepartamentoCuentaContable> listaRubroDepartamentoCuentaContable;
/*  75:    */   @Transient
/*  76:124 */   private List<AprobacionPagoRol> listaAprobacionPagoRol = new ArrayList();
/*  77:    */   @Transient
/*  78:127 */   private List<AprobacionPagoRol> listaAprobacionPagoRolFiltrado = new ArrayList();
/*  79:    */   @Transient
/*  80:    */   private BigDecimal totalEgresos;
/*  81:    */   @Transient
/*  82:    */   private BigDecimal totalIngresos;
/*  83:    */   @Transient
/*  84:    */   private BigDecimal totalIngresosT2;
/*  85:    */   @Transient
/*  86:    */   private BigDecimal totalIngresosT1;
/*  87:    */   @Transient
/*  88:    */   private int numeroEmpleadosPorAprobar;
/*  89:    */   
/*  90:    */   public Departamento() {}
/*  91:    */   
/*  92:    */   public Departamento(int idDepartamento, String codigo, String nombre)
/*  93:    */   {
/*  94:160 */     this.idDepartamento = idDepartamento;
/*  95:161 */     this.nombre = nombre;
/*  96:162 */     this.codigo = codigo;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getIdDepartamento()
/* 100:    */   {
/* 101:175 */     return this.idDepartamento;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setIdDepartamento(int idDepartamento)
/* 105:    */   {
/* 106:185 */     this.idDepartamento = idDepartamento;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int getIdOrganizacion()
/* 110:    */   {
/* 111:194 */     return this.idOrganizacion;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setIdOrganizacion(int idOrganizacion)
/* 115:    */   {
/* 116:204 */     this.idOrganizacion = idOrganizacion;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public int getIdSucursal()
/* 120:    */   {
/* 121:213 */     return this.idSucursal;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setIdSucursal(int idSucursal)
/* 125:    */   {
/* 126:223 */     this.idSucursal = idSucursal;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String getNombre()
/* 130:    */   {
/* 131:232 */     return this.nombre;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setNombre(String nombre)
/* 135:    */   {
/* 136:242 */     this.nombre = nombre;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String getCodigo()
/* 140:    */   {
/* 141:251 */     return this.codigo;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setCodigo(String codigo)
/* 145:    */   {
/* 146:261 */     this.codigo = codigo;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String getDescripcion()
/* 150:    */   {
/* 151:270 */     return this.descripcion;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setDescripcion(String descripcion)
/* 155:    */   {
/* 156:280 */     this.descripcion = descripcion;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public boolean isActivo()
/* 160:    */   {
/* 161:289 */     return this.activo;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setActivo(boolean activo)
/* 165:    */   {
/* 166:299 */     this.activo = activo;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public boolean isPredeterminado()
/* 170:    */   {
/* 171:308 */     return this.predeterminado;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setPredeterminado(boolean predeterminado)
/* 175:    */   {
/* 176:318 */     this.predeterminado = predeterminado;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public TipoDepartamento getTipoDepartamento()
/* 180:    */   {
/* 181:327 */     return this.tipoDepartamento;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setTipoDepartamento(TipoDepartamento tipoDepartamento)
/* 185:    */   {
/* 186:337 */     this.tipoDepartamento = tipoDepartamento;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public List<CentroTrabajo> getListaCentroTrabajo()
/* 190:    */   {
/* 191:346 */     return this.listaCentroTrabajo;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setListaCentroTrabajo(List<CentroTrabajo> listaCentroTrabajo)
/* 195:    */   {
/* 196:356 */     this.listaCentroTrabajo = listaCentroTrabajo;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public List<RubroDepartamentoCuentaContable> getListaRubroDepartamentoCuentaContable()
/* 200:    */   {
/* 201:365 */     return this.listaRubroDepartamentoCuentaContable;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setListaRubroDepartamentoCuentaContable(List<RubroDepartamentoCuentaContable> listaRubroDepartamentoCuentaContable)
/* 205:    */   {
/* 206:375 */     this.listaRubroDepartamentoCuentaContable = listaRubroDepartamentoCuentaContable;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public int getId()
/* 210:    */   {
/* 211:385 */     return this.idDepartamento;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public List<AprobacionPagoRol> getListaAprobacionPagoRol()
/* 215:    */   {
/* 216:389 */     return this.listaAprobacionPagoRol;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setListaAprobacionPagoRol(List<AprobacionPagoRol> listaAprobacionPagoRol)
/* 220:    */   {
/* 221:393 */     this.listaAprobacionPagoRol = listaAprobacionPagoRol;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public BigDecimal getTotalEgresos()
/* 225:    */   {
/* 226:397 */     return this.totalEgresos;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setTotalEgresos(BigDecimal totalEgresos)
/* 230:    */   {
/* 231:401 */     this.totalEgresos = totalEgresos;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public BigDecimal getTotalIngresos()
/* 235:    */   {
/* 236:405 */     return this.totalIngresos;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setTotalIngresos(BigDecimal totalIngresos)
/* 240:    */   {
/* 241:409 */     this.totalIngresos = totalIngresos;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public BigDecimal getTotalIngresosT2()
/* 245:    */   {
/* 246:413 */     return this.totalIngresosT2;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setTotalIngresosT2(BigDecimal totalIngresosT2)
/* 250:    */   {
/* 251:417 */     this.totalIngresosT2 = totalIngresosT2;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public BigDecimal getTotalIngresosT1()
/* 255:    */   {
/* 256:421 */     return this.totalIngresosT1;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void setTotalIngresosT1(BigDecimal totalIngresosT1)
/* 260:    */   {
/* 261:425 */     this.totalIngresosT1 = totalIngresosT1;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public int getNumeroEmpleadosPorAprobar()
/* 265:    */   {
/* 266:429 */     return this.numeroEmpleadosPorAprobar;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setNumeroEmpleadosPorAprobar(int numeroEmpleadosPorAprobar)
/* 270:    */   {
/* 271:433 */     this.numeroEmpleadosPorAprobar = numeroEmpleadosPorAprobar;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public List<TurnoDepartamento> getListaTurnoDepartamento()
/* 275:    */   {
/* 276:440 */     return this.listaTurnoDepartamento;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setListaTurnoDepartamento(List<TurnoDepartamento> listaTurnoDepartamento)
/* 280:    */   {
/* 281:448 */     this.listaTurnoDepartamento = listaTurnoDepartamento;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public Empleado getSupervisor()
/* 285:    */   {
/* 286:455 */     return this.supervisor;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setSupervisor(Empleado supervisor)
/* 290:    */   {
/* 291:463 */     this.supervisor = supervisor;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public Empleado getSupervisor2()
/* 295:    */   {
/* 296:467 */     return this.supervisor2;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setSupervisor2(Empleado supervisor2)
/* 300:    */   {
/* 301:471 */     this.supervisor2 = supervisor2;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public List<DocumentoDigitalizadoDepartamento> getListaDocumentoDigitalizadoDepartamento()
/* 305:    */   {
/* 306:475 */     return this.listaDocumentoDigitalizadoDepartamento;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setListaDocumentoDigitalizadoDepartamento(List<DocumentoDigitalizadoDepartamento> listaDocumentoDigitalizadoDepartamento)
/* 310:    */   {
/* 311:479 */     this.listaDocumentoDigitalizadoDepartamento = listaDocumentoDigitalizadoDepartamento;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public List<DepartamentoRubro> getListaDepartamentoRubro()
/* 315:    */   {
/* 316:486 */     return this.listaDepartamentoRubro;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void setListaDepartamentoRubro(List<DepartamentoRubro> listaDepartamentoRubro)
/* 320:    */   {
/* 321:494 */     this.listaDepartamentoRubro = listaDepartamentoRubro;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public List<AprobacionPagoRol> getListaAprobacionPagoRolFiltrado()
/* 325:    */   {
/* 326:498 */     return this.listaAprobacionPagoRolFiltrado;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void setListaAprobacionPagoRolFiltrado(List<AprobacionPagoRol> listaAprobacionPagoRolFiltrado)
/* 330:    */   {
/* 331:502 */     this.listaAprobacionPagoRolFiltrado = listaAprobacionPagoRolFiltrado;
/* 332:    */   }
/* 333:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Departamento
 * JD-Core Version:    0.7.0.1
 */