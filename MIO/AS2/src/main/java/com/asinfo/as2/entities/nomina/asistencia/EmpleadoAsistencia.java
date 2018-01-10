/*   1:    */ package com.asinfo.as2.entities.nomina.asistencia;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Rubro;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.persistence.Transient;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="tmp_empleado_asistencia")
/*  19:    */ public class EmpleadoAsistencia
/*  20:    */   implements Serializable
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = -5067816832914433772L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="tmp_empleado_asistencia", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_empleado_asistencia")
/*  26:    */   @Column(name="id_tmp_empleado_asistencia", unique=true, nullable=false)
/*  27:    */   private int idEmpleado;
/*  28:    */   @Transient
/*  29:    */   private String nombres;
/*  30:    */   @Transient
/*  31:    */   private String apellidos;
/*  32:    */   @Transient
/*  33:    */   private String identificacion;
/*  34:    */   @Transient
/*  35:    */   private String departamento;
/*  36:    */   @Transient
/*  37:    */   private Rubro rubro25;
/*  38:    */   @Transient
/*  39:    */   private Rubro rubro50;
/*  40:    */   @Transient
/*  41:    */   private Rubro rubro100;
/*  42:    */   @Transient
/*  43: 79 */   private BigDecimal totalHoras25 = BigDecimal.ZERO;
/*  44:    */   @Transient
/*  45: 81 */   private BigDecimal totalHoras25Aprobadas = BigDecimal.ZERO;
/*  46:    */   @Transient
/*  47: 84 */   private BigDecimal totalHoras50 = BigDecimal.ZERO;
/*  48:    */   @Transient
/*  49: 86 */   private BigDecimal totalHoras50Aprobadas = BigDecimal.ZERO;
/*  50:    */   @Transient
/*  51: 89 */   private BigDecimal totalHoras100 = BigDecimal.ZERO;
/*  52:    */   @Transient
/*  53: 91 */   private BigDecimal totalHoras100Feriado = BigDecimal.ZERO;
/*  54:    */   @Transient
/*  55: 93 */   private BigDecimal totalHoras100FeriadoAprobadas = BigDecimal.ZERO;
/*  56:    */   @Transient
/*  57: 95 */   private BigDecimal totalHoras100Aprobadas = BigDecimal.ZERO;
/*  58:    */   @Transient
/*  59: 98 */   private BigDecimal totalHorasPermiso = BigDecimal.ZERO;
/*  60:    */   @Transient
/*  61:101 */   private BigDecimal totalHorasFalta = BigDecimal.ZERO;
/*  62:    */   @Transient
/*  63:104 */   private BigDecimal totalHorasSubsidio = BigDecimal.ZERO;
/*  64:    */   @Transient
/*  65:107 */   private int totalDiasVacaciones = 0;
/*  66:    */   @Transient
/*  67:110 */   private int diaMes = 0;
/*  68:    */   @Transient
/*  69:113 */   private int cantidadDiasFalta = 0;
/*  70:    */   @Transient
/*  71:    */   private TipoFalta tipoFalta;
/*  72:    */   @Transient
/*  73:    */   private int idTipoFalta;
/*  74:    */   @Transient
/*  75:123 */   private List<Integer> listaDiasFalta = new ArrayList();
/*  76:    */   @Transient
/*  77:126 */   private List<Asistencia> listaAsistencia = new ArrayList();
/*  78:    */   
/*  79:    */   public EmpleadoAsistencia() {}
/*  80:    */   
/*  81:    */   public EmpleadoAsistencia(Integer idEmpleado, String identificacion, String nombres, String apellidos, String departamento, BigDecimal totalHoras25Aprobadas, BigDecimal totalHoras50Aprobadas, BigDecimal totalHoras100Aprobadas)
/*  82:    */   {
/*  83:139 */     this.idEmpleado = idEmpleado.intValue();
/*  84:140 */     this.identificacion = identificacion;
/*  85:141 */     this.nombres = nombres;
/*  86:142 */     this.apellidos = apellidos;
/*  87:143 */     this.departamento = departamento;
/*  88:144 */     this.totalHoras25Aprobadas = totalHoras25Aprobadas;
/*  89:145 */     this.totalHoras50Aprobadas = totalHoras50Aprobadas;
/*  90:146 */     this.totalHoras100Aprobadas = totalHoras100Aprobadas;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public EmpleadoAsistencia(Integer idEmpleado, String identificacion, String nombres, String apellidos, String departamento, int diaMes, int idTipoFalta)
/*  94:    */   {
/*  95:151 */     this.idEmpleado = idEmpleado.intValue();
/*  96:152 */     this.identificacion = identificacion;
/*  97:153 */     this.nombres = nombres;
/*  98:154 */     this.apellidos = apellidos;
/*  99:155 */     this.departamento = departamento;
/* 100:156 */     this.diaMes = diaMes;
/* 101:157 */     this.idTipoFalta = idTipoFalta;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int getIdEmpleado()
/* 105:    */   {
/* 106:161 */     return this.idEmpleado;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setIdEmpleado(int idEmpleado)
/* 110:    */   {
/* 111:165 */     this.idEmpleado = idEmpleado;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getId()
/* 115:    */   {
/* 116:169 */     return this.idEmpleado;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getNombres()
/* 120:    */   {
/* 121:173 */     return this.nombres;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setNombres(String nombres)
/* 125:    */   {
/* 126:177 */     this.nombres = nombres;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String getApellidos()
/* 130:    */   {
/* 131:181 */     return this.apellidos;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setApellidos(String apellidos)
/* 135:    */   {
/* 136:185 */     this.apellidos = apellidos;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public BigDecimal getTotalHoras25()
/* 140:    */   {
/* 141:189 */     return this.totalHoras25;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setTotalHoras25(BigDecimal totalHoras25)
/* 145:    */   {
/* 146:193 */     this.totalHoras25 = totalHoras25;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public BigDecimal getTotalHoras25Aprobadas()
/* 150:    */   {
/* 151:197 */     return this.totalHoras25Aprobadas;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setTotalHoras25Aprobadas(BigDecimal totalHoras25Aprobadas)
/* 155:    */   {
/* 156:201 */     this.totalHoras25Aprobadas = totalHoras25Aprobadas;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public BigDecimal getTotalHoras50()
/* 160:    */   {
/* 161:205 */     return this.totalHoras50;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setTotalHoras50(BigDecimal totalHoras50)
/* 165:    */   {
/* 166:209 */     this.totalHoras50 = totalHoras50;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public BigDecimal getTotalHoras50Aprobadas()
/* 170:    */   {
/* 171:213 */     return this.totalHoras50Aprobadas;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setTotalHoras50Aprobadas(BigDecimal totalHoras50Aprobadas)
/* 175:    */   {
/* 176:217 */     this.totalHoras50Aprobadas = totalHoras50Aprobadas;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public BigDecimal getTotalHoras100()
/* 180:    */   {
/* 181:221 */     return this.totalHoras100;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setTotalHoras100(BigDecimal totalHoras100)
/* 185:    */   {
/* 186:225 */     this.totalHoras100 = totalHoras100;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public BigDecimal getTotalHoras100Feriado()
/* 190:    */   {
/* 191:229 */     return this.totalHoras100Feriado;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setTotalHoras100Feriado(BigDecimal totalHoras100Feriado)
/* 195:    */   {
/* 196:233 */     this.totalHoras100Feriado = totalHoras100Feriado;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public BigDecimal getTotalHoras100Aprobadas()
/* 200:    */   {
/* 201:237 */     return this.totalHoras100Aprobadas;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setTotalHoras100Aprobadas(BigDecimal totalHoras100Aprobadas)
/* 205:    */   {
/* 206:241 */     this.totalHoras100Aprobadas = totalHoras100Aprobadas;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public BigDecimal getTotalHorasPermiso()
/* 210:    */   {
/* 211:245 */     return this.totalHorasPermiso;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setTotalHorasPermiso(BigDecimal totalHorasPermiso)
/* 215:    */   {
/* 216:249 */     this.totalHorasPermiso = totalHorasPermiso;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public BigDecimal getTotalHorasSubsidio()
/* 220:    */   {
/* 221:253 */     return this.totalHorasSubsidio;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setTotalHorasSubsidio(BigDecimal totalHorasSubsidio)
/* 225:    */   {
/* 226:257 */     this.totalHorasSubsidio = totalHorasSubsidio;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public int getTotalDiasVacaciones()
/* 230:    */   {
/* 231:261 */     return this.totalDiasVacaciones;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setTotalDiasVacaciones(int totalDiasVacaciones)
/* 235:    */   {
/* 236:265 */     this.totalDiasVacaciones = totalDiasVacaciones;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public List<Asistencia> getListaAsistencia()
/* 240:    */   {
/* 241:269 */     return this.listaAsistencia;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setListaAsistencia(List<Asistencia> listaAsistencia)
/* 245:    */   {
/* 246:273 */     this.listaAsistencia = listaAsistencia;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public BigDecimal getTotalHorasFalta()
/* 250:    */   {
/* 251:277 */     return this.totalHorasFalta;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setTotalHorasFalta(BigDecimal totalHorasFalta)
/* 255:    */   {
/* 256:281 */     this.totalHorasFalta = totalHorasFalta;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public String getIdentificacion()
/* 260:    */   {
/* 261:285 */     return this.identificacion;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setIdentificacion(String identificacion)
/* 265:    */   {
/* 266:289 */     this.identificacion = identificacion;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public String getDepartamento()
/* 270:    */   {
/* 271:293 */     return this.departamento;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setDepartamento(String departamento)
/* 275:    */   {
/* 276:297 */     this.departamento = departamento;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public Rubro getRubro25()
/* 280:    */   {
/* 281:301 */     return this.rubro25;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setRubro25(Rubro rubro25)
/* 285:    */   {
/* 286:305 */     this.rubro25 = rubro25;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public Rubro getRubro50()
/* 290:    */   {
/* 291:309 */     return this.rubro50;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setRubro50(Rubro rubro50)
/* 295:    */   {
/* 296:313 */     this.rubro50 = rubro50;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public Rubro getRubro100()
/* 300:    */   {
/* 301:317 */     return this.rubro100;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setRubro100(Rubro rubro100)
/* 305:    */   {
/* 306:321 */     this.rubro100 = rubro100;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public int getDiaMes()
/* 310:    */   {
/* 311:325 */     return this.diaMes;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void setDiaMes(int diaMes)
/* 315:    */   {
/* 316:329 */     this.diaMes = diaMes;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public TipoFalta getTipoFalta()
/* 320:    */   {
/* 321:333 */     return this.tipoFalta;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void setTipoFalta(TipoFalta tipoFalta)
/* 325:    */   {
/* 326:337 */     this.tipoFalta = tipoFalta;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public int getCantidadDiasFalta()
/* 330:    */   {
/* 331:341 */     return this.cantidadDiasFalta;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void setCantidadDiasFalta(int cantidadDiasFalta)
/* 335:    */   {
/* 336:345 */     this.cantidadDiasFalta = cantidadDiasFalta;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public int getIdTipoFalta()
/* 340:    */   {
/* 341:349 */     return this.idTipoFalta;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void setIdTipoFalta(int idTipoFalta)
/* 345:    */   {
/* 346:353 */     this.idTipoFalta = idTipoFalta;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public List<Integer> getListaDiasFalta()
/* 350:    */   {
/* 351:357 */     return this.listaDiasFalta;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void setListaDiasFalta(List<Integer> listaDiasFalta)
/* 355:    */   {
/* 356:361 */     this.listaDiasFalta = listaDiasFalta;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public BigDecimal getTotalHoras100FeriadoAprobadas()
/* 360:    */   {
/* 361:365 */     return this.totalHoras100FeriadoAprobadas;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void setTotalHoras100FeriadoAprobadas(BigDecimal totalHoras100FeriadoAprobadas)
/* 365:    */   {
/* 366:369 */     this.totalHoras100FeriadoAprobadas = totalHoras100FeriadoAprobadas;
/* 367:    */   }
/* 368:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.nomina.asistencia.EmpleadoAsistencia
 * JD-Core Version:    0.7.0.1
 */