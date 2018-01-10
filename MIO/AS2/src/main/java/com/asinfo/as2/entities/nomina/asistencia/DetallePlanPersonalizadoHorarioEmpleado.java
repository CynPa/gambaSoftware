/*    1:     */ package com.asinfo.as2.entities.nomina.asistencia;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.entities.Empleado;
/*    4:     */ import com.asinfo.as2.entities.EntidadBase;
/*    5:     */ import java.io.PrintStream;
/*    6:     */ import java.io.Serializable;
/*    7:     */ import java.lang.reflect.Method;
/*    8:     */ import javax.persistence.Column;
/*    9:     */ import javax.persistence.Entity;
/*   10:     */ import javax.persistence.FetchType;
/*   11:     */ import javax.persistence.GeneratedValue;
/*   12:     */ import javax.persistence.GenerationType;
/*   13:     */ import javax.persistence.Id;
/*   14:     */ import javax.persistence.JoinColumn;
/*   15:     */ import javax.persistence.ManyToOne;
/*   16:     */ import javax.persistence.Table;
/*   17:     */ import javax.persistence.TableGenerator;
/*   18:     */ import javax.persistence.Transient;
/*   19:     */ import javax.validation.constraints.NotNull;
/*   20:     */ import org.hibernate.annotations.ColumnDefault;
/*   21:     */ 
/*   22:     */ @Entity
/*   23:     */ @Table(name="detalle_plan_personalizado_horario_empleado", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_empleado", "id_plan_personalizado_horario_empleado"})})
/*   24:     */ public class DetallePlanPersonalizadoHorarioEmpleado
/*   25:     */   extends EntidadBase
/*   26:     */   implements Serializable
/*   27:     */ {
/*   28:     */   private static final long serialVersionUID = 1L;
/*   29:     */   @Id
/*   30:     */   @TableGenerator(name="detalle_plan_personalizado_horario_empleado", initialValue=0, allocationSize=50)
/*   31:     */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_plan_personalizado_horario_empleado")
/*   32:     */   @Column(name="id_detalle_plan_personalizado_horario_empleado", unique=true, nullable=false)
/*   33:     */   private int idDetallePlanPersonalizadoHorarioEmpleado;
/*   34:     */   @Column(name="id_organizacion", nullable=false)
/*   35:     */   private int idOrganizacion;
/*   36:     */   @Column(name="id_sucursal", nullable=false)
/*   37:     */   private int idSucursal;
/*   38:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   39:     */   @JoinColumn(name="id_plan_personalizado_horario_empleado", nullable=false)
/*   40:     */   @NotNull
/*   41:     */   private PlanPersonalizadoHorarioEmpleado planPersonalizadoHorarioEmpleado;
/*   42:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   43:     */   @JoinColumn(name="id_empleado", nullable=false)
/*   44:     */   @NotNull
/*   45:     */   private Empleado empleado;
/*   46:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   47:     */   @JoinColumn(name="id_turno1", nullable=true)
/*   48:     */   private Turno turno1;
/*   49:     */   @Column(name="indicador_dia_descanso1", nullable=true)
/*   50:  74 */   private Boolean indicadorDiaDescanso1 = Boolean.valueOf(false);
/*   51:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   52:     */   @JoinColumn(name="id_turno2", nullable=true)
/*   53:     */   private Turno turno2;
/*   54:     */   @Column(name="indicador_dia_descanso2", nullable=true)
/*   55:  81 */   private Boolean indicadorDiaDescanso2 = Boolean.valueOf(false);
/*   56:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   57:     */   @JoinColumn(name="id_turno3", nullable=true)
/*   58:     */   private Turno turno3;
/*   59:     */   @Column(name="indicador_dia_descanso3", nullable=true)
/*   60:  88 */   private Boolean indicadorDiaDescanso3 = Boolean.valueOf(false);
/*   61:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   62:     */   @JoinColumn(name="id_turno4", nullable=true)
/*   63:     */   private Turno turno4;
/*   64:     */   @Column(name="indicador_dia_descanso4", nullable=true)
/*   65:  95 */   private Boolean indicadorDiaDescanso4 = Boolean.valueOf(false);
/*   66:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   67:     */   @JoinColumn(name="id_turno5", nullable=true)
/*   68:     */   private Turno turno5;
/*   69:     */   @Column(name="indicador_dia_descanso5", nullable=true)
/*   70: 102 */   private Boolean indicadorDiaDescanso5 = Boolean.valueOf(false);
/*   71:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   72:     */   @JoinColumn(name="id_turno6", nullable=true)
/*   73:     */   private Turno turno6;
/*   74:     */   @Column(name="indicador_dia_descanso6", nullable=true)
/*   75: 109 */   private Boolean indicadorDiaDescanso6 = Boolean.valueOf(false);
/*   76:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   77:     */   @JoinColumn(name="id_turno7", nullable=true)
/*   78:     */   private Turno turno7;
/*   79:     */   @Column(name="indicador_dia_descanso7", nullable=true)
/*   80: 116 */   private Boolean indicadorDiaDescanso7 = Boolean.valueOf(false);
/*   81:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   82:     */   @JoinColumn(name="id_turno8", nullable=true)
/*   83:     */   private Turno turno8;
/*   84:     */   @Column(name="indicador_dia_descanso8", nullable=true)
/*   85: 123 */   private Boolean indicadorDiaDescanso8 = Boolean.valueOf(false);
/*   86:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   87:     */   @JoinColumn(name="id_turno9", nullable=true)
/*   88:     */   private Turno turno9;
/*   89:     */   @Column(name="indicador_dia_descanso9", nullable=true)
/*   90: 130 */   private Boolean indicadorDiaDescanso9 = Boolean.valueOf(false);
/*   91:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   92:     */   @JoinColumn(name="id_turno10", nullable=true)
/*   93:     */   private Turno turno10;
/*   94:     */   @Column(name="indicador_dia_descanso10", nullable=true)
/*   95: 137 */   private Boolean indicadorDiaDescanso10 = Boolean.valueOf(false);
/*   96:     */   @ManyToOne(fetch=FetchType.LAZY)
/*   97:     */   @JoinColumn(name="id_turno11", nullable=true)
/*   98:     */   private Turno turno11;
/*   99:     */   @Column(name="indicador_dia_descanso11", nullable=true)
/*  100: 144 */   private Boolean indicadorDiaDescanso11 = Boolean.valueOf(false);
/*  101:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  102:     */   @JoinColumn(name="id_turno12", nullable=true)
/*  103:     */   private Turno turno12;
/*  104:     */   @Column(name="indicador_dia_descanso12", nullable=true)
/*  105: 151 */   private Boolean indicadorDiaDescanso12 = Boolean.valueOf(false);
/*  106:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  107:     */   @JoinColumn(name="id_turno13", nullable=true)
/*  108:     */   private Turno turno13;
/*  109:     */   @Column(name="indicador_dia_descanso13", nullable=true)
/*  110: 158 */   private Boolean indicadorDiaDescanso13 = Boolean.valueOf(false);
/*  111:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  112:     */   @JoinColumn(name="id_turno14", nullable=true)
/*  113:     */   private Turno turno14;
/*  114:     */   @Column(name="indicador_dia_descanso14", nullable=true)
/*  115: 165 */   private Boolean indicadorDiaDescanso14 = Boolean.valueOf(false);
/*  116:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  117:     */   @JoinColumn(name="id_turno15", nullable=true)
/*  118:     */   private Turno turno15;
/*  119:     */   @Column(name="indicador_dia_descanso15", nullable=true)
/*  120: 172 */   private Boolean indicadorDiaDescanso15 = Boolean.valueOf(false);
/*  121:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  122:     */   @JoinColumn(name="id_turno16", nullable=true)
/*  123:     */   private Turno turno16;
/*  124:     */   @Column(name="indicador_dia_descanso16", nullable=true)
/*  125: 179 */   private Boolean indicadorDiaDescanso16 = Boolean.valueOf(false);
/*  126:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  127:     */   @JoinColumn(name="id_turno17", nullable=true)
/*  128:     */   private Turno turno17;
/*  129:     */   @Column(name="indicador_dia_descanso17", nullable=true)
/*  130: 186 */   private Boolean indicadorDiaDescanso17 = Boolean.valueOf(false);
/*  131:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  132:     */   @JoinColumn(name="id_turno18", nullable=true)
/*  133:     */   private Turno turno18;
/*  134:     */   @Column(name="indicador_dia_descanso18", nullable=true)
/*  135: 193 */   private Boolean indicadorDiaDescanso18 = Boolean.valueOf(false);
/*  136:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  137:     */   @JoinColumn(name="id_turno19", nullable=true)
/*  138:     */   private Turno turno19;
/*  139:     */   @Column(name="indicador_dia_descanso19", nullable=true)
/*  140: 200 */   private Boolean indicadorDiaDescanso19 = Boolean.valueOf(false);
/*  141:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  142:     */   @JoinColumn(name="id_turno20", nullable=true)
/*  143:     */   private Turno turno20;
/*  144:     */   @Column(name="indicador_dia_descanso20", nullable=true)
/*  145: 207 */   private Boolean indicadorDiaDescanso20 = Boolean.valueOf(false);
/*  146:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  147:     */   @JoinColumn(name="id_turno21", nullable=true)
/*  148:     */   private Turno turno21;
/*  149:     */   @Column(name="indicador_dia_descanso21", nullable=true)
/*  150: 214 */   private Boolean indicadorDiaDescanso21 = Boolean.valueOf(false);
/*  151:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  152:     */   @JoinColumn(name="id_turno22", nullable=true)
/*  153:     */   private Turno turno22;
/*  154:     */   @Column(name="indicador_dia_descanso22", nullable=true)
/*  155: 221 */   private Boolean indicadorDiaDescanso22 = Boolean.valueOf(false);
/*  156:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  157:     */   @JoinColumn(name="id_turno23", nullable=true)
/*  158:     */   private Turno turno23;
/*  159:     */   @Column(name="indicador_dia_descanso23", nullable=true)
/*  160: 228 */   private Boolean indicadorDiaDescanso23 = Boolean.valueOf(false);
/*  161:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  162:     */   @JoinColumn(name="id_turno24", nullable=true)
/*  163:     */   private Turno turno24;
/*  164:     */   @Column(name="indicador_dia_descanso24", nullable=true)
/*  165: 235 */   private Boolean indicadorDiaDescanso24 = Boolean.valueOf(false);
/*  166:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  167:     */   @JoinColumn(name="id_turno25", nullable=true)
/*  168:     */   private Turno turno25;
/*  169:     */   @Column(name="indicador_dia_descanso25", nullable=true)
/*  170: 242 */   private Boolean indicadorDiaDescanso25 = Boolean.valueOf(false);
/*  171:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  172:     */   @JoinColumn(name="id_turno26", nullable=true)
/*  173:     */   private Turno turno26;
/*  174:     */   @Column(name="indicador_dia_descanso26", nullable=true)
/*  175: 249 */   private Boolean indicadorDiaDescanso26 = Boolean.valueOf(false);
/*  176:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  177:     */   @JoinColumn(name="id_turno27", nullable=true)
/*  178:     */   private Turno turno27;
/*  179:     */   @Column(name="indicador_dia_descanso27", nullable=true)
/*  180: 256 */   private Boolean indicadorDiaDescanso27 = Boolean.valueOf(false);
/*  181:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  182:     */   @JoinColumn(name="id_turno28", nullable=true)
/*  183:     */   private Turno turno28;
/*  184:     */   @Column(name="indicador_dia_descanso28", nullable=true)
/*  185: 263 */   private Boolean indicadorDiaDescanso28 = Boolean.valueOf(false);
/*  186:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  187:     */   @JoinColumn(name="id_turno29", nullable=true)
/*  188:     */   private Turno turno29;
/*  189:     */   @Column(name="indicador_dia_descanso29", nullable=true)
/*  190: 270 */   private Boolean indicadorDiaDescanso29 = Boolean.valueOf(false);
/*  191:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  192:     */   @JoinColumn(name="id_turno30", nullable=true)
/*  193:     */   private Turno turno30;
/*  194:     */   @Column(name="indicador_dia_descanso30", nullable=true)
/*  195: 277 */   private Boolean indicadorDiaDescanso30 = Boolean.valueOf(false);
/*  196:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  197:     */   @JoinColumn(name="id_turno31", nullable=true)
/*  198:     */   private Turno turno31;
/*  199:     */   @Column(name="indicador_dia_descanso31", nullable=true)
/*  200: 284 */   private Boolean indicadorDiaDescanso31 = Boolean.valueOf(false);
/*  201:     */   @Column(name="indicador_dia_complementario1", nullable=false)
/*  202:     */   @NotNull
/*  203:     */   @ColumnDefault("'0'")
/*  204: 289 */   private Boolean indicadorDiaComplementario1 = Boolean.valueOf(false);
/*  205:     */   @Column(name="indicador_dia_complementario2", nullable=false)
/*  206:     */   @NotNull
/*  207:     */   @ColumnDefault("'0'")
/*  208: 294 */   private Boolean indicadorDiaComplementario2 = Boolean.valueOf(false);
/*  209:     */   @Column(name="indicador_dia_complementario3", nullable=false)
/*  210:     */   @NotNull
/*  211:     */   @ColumnDefault("'0'")
/*  212: 299 */   private Boolean indicadorDiaComplementario3 = Boolean.valueOf(false);
/*  213:     */   @Column(name="indicador_dia_complementario4", nullable=false)
/*  214:     */   @NotNull
/*  215:     */   @ColumnDefault("'0'")
/*  216: 304 */   private Boolean indicadorDiaComplementario4 = Boolean.valueOf(false);
/*  217:     */   @Column(name="indicador_dia_complementario5", nullable=false)
/*  218:     */   @NotNull
/*  219:     */   @ColumnDefault("'0'")
/*  220: 309 */   private Boolean indicadorDiaComplementario5 = Boolean.valueOf(false);
/*  221:     */   @Column(name="indicador_dia_complementario6", nullable=false)
/*  222:     */   @NotNull
/*  223:     */   @ColumnDefault("'0'")
/*  224: 314 */   private Boolean indicadorDiaComplementario6 = Boolean.valueOf(false);
/*  225:     */   @Column(name="indicador_dia_complementario7", nullable=false)
/*  226:     */   @NotNull
/*  227:     */   @ColumnDefault("'0'")
/*  228: 319 */   private Boolean indicadorDiaComplementario7 = Boolean.valueOf(false);
/*  229:     */   @Column(name="indicador_dia_complementario8", nullable=false)
/*  230:     */   @NotNull
/*  231:     */   @ColumnDefault("'0'")
/*  232: 324 */   private Boolean indicadorDiaComplementario8 = Boolean.valueOf(false);
/*  233:     */   @Column(name="indicador_dia_complementario9", nullable=false)
/*  234:     */   @NotNull
/*  235:     */   @ColumnDefault("'0'")
/*  236: 329 */   private Boolean indicadorDiaComplementario9 = Boolean.valueOf(false);
/*  237:     */   @Column(name="indicador_dia_complementario10", nullable=false)
/*  238:     */   @NotNull
/*  239:     */   @ColumnDefault("'0'")
/*  240: 334 */   private Boolean indicadorDiaComplementario10 = Boolean.valueOf(false);
/*  241:     */   @Column(name="indicador_dia_complementario11", nullable=false)
/*  242:     */   @NotNull
/*  243:     */   @ColumnDefault("'0'")
/*  244: 339 */   private Boolean indicadorDiaComplementario11 = Boolean.valueOf(false);
/*  245:     */   @Column(name="indicador_dia_complementario12", nullable=false)
/*  246:     */   @NotNull
/*  247:     */   @ColumnDefault("'0'")
/*  248: 344 */   private Boolean indicadorDiaComplementario12 = Boolean.valueOf(false);
/*  249:     */   @Column(name="indicador_dia_complementario13", nullable=false)
/*  250:     */   @NotNull
/*  251:     */   @ColumnDefault("'0'")
/*  252: 349 */   private Boolean indicadorDiaComplementario13 = Boolean.valueOf(false);
/*  253:     */   @Column(name="indicador_dia_complementario14", nullable=false)
/*  254:     */   @NotNull
/*  255:     */   @ColumnDefault("'0'")
/*  256: 354 */   private Boolean indicadorDiaComplementario14 = Boolean.valueOf(false);
/*  257:     */   @Column(name="indicador_dia_complementario15", nullable=false)
/*  258:     */   @NotNull
/*  259:     */   @ColumnDefault("'0'")
/*  260: 359 */   private Boolean indicadorDiaComplementario15 = Boolean.valueOf(false);
/*  261:     */   @Column(name="indicador_dia_complementario16", nullable=false)
/*  262:     */   @NotNull
/*  263:     */   @ColumnDefault("'0'")
/*  264: 364 */   private Boolean indicadorDiaComplementario16 = Boolean.valueOf(false);
/*  265:     */   @Column(name="indicador_dia_complementario17", nullable=false)
/*  266:     */   @NotNull
/*  267:     */   @ColumnDefault("'0'")
/*  268: 369 */   private Boolean indicadorDiaComplementario17 = Boolean.valueOf(false);
/*  269:     */   @Column(name="indicador_dia_complementario18", nullable=false)
/*  270:     */   @NotNull
/*  271:     */   @ColumnDefault("'0'")
/*  272: 374 */   private Boolean indicadorDiaComplementario18 = Boolean.valueOf(false);
/*  273:     */   @Column(name="indicador_dia_complementario19", nullable=false)
/*  274:     */   @NotNull
/*  275:     */   @ColumnDefault("'0'")
/*  276: 379 */   private Boolean indicadorDiaComplementario19 = Boolean.valueOf(false);
/*  277:     */   @Column(name="indicador_dia_complementario20", nullable=false)
/*  278:     */   @NotNull
/*  279:     */   @ColumnDefault("'0'")
/*  280: 384 */   private Boolean indicadorDiaComplementario20 = Boolean.valueOf(false);
/*  281:     */   @Column(name="indicador_dia_complementario21", nullable=false)
/*  282:     */   @NotNull
/*  283:     */   @ColumnDefault("'0'")
/*  284: 389 */   private Boolean indicadorDiaComplementario21 = Boolean.valueOf(false);
/*  285:     */   @Column(name="indicador_dia_complementario22", nullable=false)
/*  286:     */   @NotNull
/*  287:     */   @ColumnDefault("'0'")
/*  288: 394 */   private Boolean indicadorDiaComplementario22 = Boolean.valueOf(false);
/*  289:     */   @Column(name="indicador_dia_complementario23", nullable=false)
/*  290:     */   @NotNull
/*  291:     */   @ColumnDefault("'0'")
/*  292: 399 */   private Boolean indicadorDiaComplementario23 = Boolean.valueOf(false);
/*  293:     */   @Column(name="indicador_dia_complementario24", nullable=false)
/*  294:     */   @NotNull
/*  295:     */   @ColumnDefault("'0'")
/*  296: 404 */   private Boolean indicadorDiaComplementario24 = Boolean.valueOf(false);
/*  297:     */   @Column(name="indicador_dia_complementario25", nullable=false)
/*  298:     */   @NotNull
/*  299:     */   @ColumnDefault("'0'")
/*  300: 409 */   private Boolean indicadorDiaComplementario25 = Boolean.valueOf(false);
/*  301:     */   @Column(name="indicador_dia_complementario26", nullable=false)
/*  302:     */   @NotNull
/*  303:     */   @ColumnDefault("'0'")
/*  304: 414 */   private Boolean indicadorDiaComplementario26 = Boolean.valueOf(false);
/*  305:     */   @Column(name="indicador_dia_complementario27", nullable=false)
/*  306:     */   @NotNull
/*  307:     */   @ColumnDefault("'0'")
/*  308: 419 */   private Boolean indicadorDiaComplementario27 = Boolean.valueOf(false);
/*  309:     */   @Column(name="indicador_dia_complementario28", nullable=false)
/*  310:     */   @NotNull
/*  311:     */   @ColumnDefault("'0'")
/*  312: 424 */   private Boolean indicadorDiaComplementario28 = Boolean.valueOf(false);
/*  313:     */   @Column(name="indicador_dia_complementario29", nullable=false)
/*  314:     */   @NotNull
/*  315:     */   @ColumnDefault("'0'")
/*  316: 429 */   private Boolean indicadorDiaComplementario29 = Boolean.valueOf(false);
/*  317:     */   @Column(name="indicador_dia_complementario30", nullable=false)
/*  318:     */   @NotNull
/*  319:     */   @ColumnDefault("'0'")
/*  320: 434 */   private Boolean indicadorDiaComplementario30 = Boolean.valueOf(false);
/*  321:     */   @Column(name="indicador_dia_complementario31", nullable=false)
/*  322:     */   @NotNull
/*  323:     */   @ColumnDefault("'0'")
/*  324: 439 */   private Boolean indicadorDiaComplementario31 = Boolean.valueOf(false);
/*  325:     */   @Transient
/*  326:     */   private Turno turno_1_1;
/*  327:     */   @Transient
/*  328:     */   private Turno turno_1_2;
/*  329:     */   @Transient
/*  330:     */   private Turno turno_1_3;
/*  331:     */   @Transient
/*  332:     */   private Turno turno_1_4;
/*  333:     */   @Transient
/*  334:     */   private Turno turno_1_5;
/*  335:     */   @Transient
/*  336:     */   private Turno turno_1_6;
/*  337:     */   @Transient
/*  338:     */   private Turno turno_1_7;
/*  339:     */   @Transient
/*  340:     */   private Turno turno_2_1;
/*  341:     */   @Transient
/*  342:     */   private Turno turno_2_2;
/*  343:     */   @Transient
/*  344:     */   private Turno turno_2_3;
/*  345:     */   @Transient
/*  346:     */   private Turno turno_2_4;
/*  347:     */   @Transient
/*  348:     */   private Turno turno_2_5;
/*  349:     */   @Transient
/*  350:     */   private Turno turno_2_6;
/*  351:     */   @Transient
/*  352:     */   private Turno turno_2_7;
/*  353:     */   @Transient
/*  354:     */   private Turno turno_3_1;
/*  355:     */   @Transient
/*  356:     */   private Turno turno_3_2;
/*  357:     */   @Transient
/*  358:     */   private Turno turno_3_3;
/*  359:     */   @Transient
/*  360:     */   private Turno turno_3_4;
/*  361:     */   @Transient
/*  362:     */   private Turno turno_3_5;
/*  363:     */   @Transient
/*  364:     */   private Turno turno_3_6;
/*  365:     */   @Transient
/*  366:     */   private Turno turno_3_7;
/*  367:     */   @Transient
/*  368:     */   private Turno turno_4_1;
/*  369:     */   @Transient
/*  370:     */   private Turno turno_4_2;
/*  371:     */   @Transient
/*  372:     */   private Turno turno_4_3;
/*  373:     */   @Transient
/*  374:     */   private Turno turno_4_4;
/*  375:     */   @Transient
/*  376:     */   private Turno turno_4_5;
/*  377:     */   @Transient
/*  378:     */   private Turno turno_4_6;
/*  379:     */   @Transient
/*  380:     */   private Turno turno_4_7;
/*  381:     */   @Transient
/*  382:     */   private Turno turno_5_1;
/*  383:     */   @Transient
/*  384:     */   private Turno turno_5_2;
/*  385:     */   @Transient
/*  386:     */   private Turno turno_5_3;
/*  387:     */   @Transient
/*  388:     */   private Turno turno_5_4;
/*  389:     */   @Transient
/*  390:     */   private Turno turno_5_5;
/*  391:     */   @Transient
/*  392:     */   private Turno turno_5_6;
/*  393:     */   @Transient
/*  394:     */   private Turno turno_5_7;
/*  395:     */   
/*  396:     */   public int getId()
/*  397:     */   {
/*  398: 556 */     return this.idDetallePlanPersonalizadoHorarioEmpleado;
/*  399:     */   }
/*  400:     */   
/*  401:     */   public int getIdDetallePlanPersonalizadoHorarioEmpleado()
/*  402:     */   {
/*  403: 560 */     return this.idDetallePlanPersonalizadoHorarioEmpleado;
/*  404:     */   }
/*  405:     */   
/*  406:     */   public void setIdDetallePlanPersonalizadoHorarioEmpleado(int idDetallePlanPersonalizadoHorarioEmpleado)
/*  407:     */   {
/*  408: 564 */     this.idDetallePlanPersonalizadoHorarioEmpleado = idDetallePlanPersonalizadoHorarioEmpleado;
/*  409:     */   }
/*  410:     */   
/*  411:     */   public int getIdOrganizacion()
/*  412:     */   {
/*  413: 573 */     return this.idOrganizacion;
/*  414:     */   }
/*  415:     */   
/*  416:     */   public void setIdOrganizacion(int idOrganizacion)
/*  417:     */   {
/*  418: 583 */     this.idOrganizacion = idOrganizacion;
/*  419:     */   }
/*  420:     */   
/*  421:     */   public int getIdSucursal()
/*  422:     */   {
/*  423: 592 */     return this.idSucursal;
/*  424:     */   }
/*  425:     */   
/*  426:     */   public void setIdSucursal(int idSucursal)
/*  427:     */   {
/*  428: 602 */     this.idSucursal = idSucursal;
/*  429:     */   }
/*  430:     */   
/*  431:     */   public PlanPersonalizadoHorarioEmpleado getPlanPersonalizadoHorarioEmpleado()
/*  432:     */   {
/*  433: 606 */     return this.planPersonalizadoHorarioEmpleado;
/*  434:     */   }
/*  435:     */   
/*  436:     */   public void setPlanPersonalizadoHorarioEmpleado(PlanPersonalizadoHorarioEmpleado planPersonalizadoHorarioEmpleado)
/*  437:     */   {
/*  438: 610 */     this.planPersonalizadoHorarioEmpleado = planPersonalizadoHorarioEmpleado;
/*  439:     */   }
/*  440:     */   
/*  441:     */   public Empleado getEmpleado()
/*  442:     */   {
/*  443: 614 */     return this.empleado;
/*  444:     */   }
/*  445:     */   
/*  446:     */   public void setEmpleado(Empleado empleado)
/*  447:     */   {
/*  448: 618 */     this.empleado = empleado;
/*  449:     */   }
/*  450:     */   
/*  451:     */   public Turno getTurno1()
/*  452:     */   {
/*  453: 622 */     return this.turno1;
/*  454:     */   }
/*  455:     */   
/*  456:     */   public void setTurno1(Turno turno1)
/*  457:     */   {
/*  458: 626 */     this.turno1 = turno1;
/*  459:     */   }
/*  460:     */   
/*  461:     */   public Turno getTurno2()
/*  462:     */   {
/*  463: 630 */     return this.turno2;
/*  464:     */   }
/*  465:     */   
/*  466:     */   public void setTurno2(Turno turno2)
/*  467:     */   {
/*  468: 634 */     this.turno2 = turno2;
/*  469:     */   }
/*  470:     */   
/*  471:     */   public Turno getTurno3()
/*  472:     */   {
/*  473: 638 */     return this.turno3;
/*  474:     */   }
/*  475:     */   
/*  476:     */   public void setTurno3(Turno turno3)
/*  477:     */   {
/*  478: 642 */     this.turno3 = turno3;
/*  479:     */   }
/*  480:     */   
/*  481:     */   public Turno getTurno4()
/*  482:     */   {
/*  483: 646 */     return this.turno4;
/*  484:     */   }
/*  485:     */   
/*  486:     */   public void setTurno4(Turno turno4)
/*  487:     */   {
/*  488: 650 */     this.turno4 = turno4;
/*  489:     */   }
/*  490:     */   
/*  491:     */   public Turno getTurno5()
/*  492:     */   {
/*  493: 654 */     return this.turno5;
/*  494:     */   }
/*  495:     */   
/*  496:     */   public void setTurno5(Turno turno5)
/*  497:     */   {
/*  498: 658 */     this.turno5 = turno5;
/*  499:     */   }
/*  500:     */   
/*  501:     */   public Turno getTurno6()
/*  502:     */   {
/*  503: 662 */     return this.turno6;
/*  504:     */   }
/*  505:     */   
/*  506:     */   public void setTurno6(Turno turno6)
/*  507:     */   {
/*  508: 666 */     this.turno6 = turno6;
/*  509:     */   }
/*  510:     */   
/*  511:     */   public Turno getTurno7()
/*  512:     */   {
/*  513: 670 */     return this.turno7;
/*  514:     */   }
/*  515:     */   
/*  516:     */   public void setTurno7(Turno turno7)
/*  517:     */   {
/*  518: 674 */     this.turno7 = turno7;
/*  519:     */   }
/*  520:     */   
/*  521:     */   public Turno getTurno8()
/*  522:     */   {
/*  523: 678 */     return this.turno8;
/*  524:     */   }
/*  525:     */   
/*  526:     */   public void setTurno8(Turno turno8)
/*  527:     */   {
/*  528: 682 */     this.turno8 = turno8;
/*  529:     */   }
/*  530:     */   
/*  531:     */   public Turno getTurno9()
/*  532:     */   {
/*  533: 686 */     return this.turno9;
/*  534:     */   }
/*  535:     */   
/*  536:     */   public void setTurno9(Turno turno9)
/*  537:     */   {
/*  538: 690 */     this.turno9 = turno9;
/*  539:     */   }
/*  540:     */   
/*  541:     */   public Turno getTurno10()
/*  542:     */   {
/*  543: 694 */     return this.turno10;
/*  544:     */   }
/*  545:     */   
/*  546:     */   public void setTurno10(Turno turno10)
/*  547:     */   {
/*  548: 698 */     this.turno10 = turno10;
/*  549:     */   }
/*  550:     */   
/*  551:     */   public Turno getTurno11()
/*  552:     */   {
/*  553: 702 */     return this.turno11;
/*  554:     */   }
/*  555:     */   
/*  556:     */   public void setTurno11(Turno turno11)
/*  557:     */   {
/*  558: 706 */     this.turno11 = turno11;
/*  559:     */   }
/*  560:     */   
/*  561:     */   public Turno getTurno12()
/*  562:     */   {
/*  563: 710 */     return this.turno12;
/*  564:     */   }
/*  565:     */   
/*  566:     */   public void setTurno12(Turno turno12)
/*  567:     */   {
/*  568: 714 */     this.turno12 = turno12;
/*  569:     */   }
/*  570:     */   
/*  571:     */   public Turno getTurno13()
/*  572:     */   {
/*  573: 718 */     return this.turno13;
/*  574:     */   }
/*  575:     */   
/*  576:     */   public void setTurno13(Turno turno13)
/*  577:     */   {
/*  578: 722 */     this.turno13 = turno13;
/*  579:     */   }
/*  580:     */   
/*  581:     */   public Turno getTurno14()
/*  582:     */   {
/*  583: 726 */     return this.turno14;
/*  584:     */   }
/*  585:     */   
/*  586:     */   public void setTurno14(Turno turno14)
/*  587:     */   {
/*  588: 730 */     this.turno14 = turno14;
/*  589:     */   }
/*  590:     */   
/*  591:     */   public Turno getTurno15()
/*  592:     */   {
/*  593: 734 */     return this.turno15;
/*  594:     */   }
/*  595:     */   
/*  596:     */   public void setTurno15(Turno turno15)
/*  597:     */   {
/*  598: 738 */     this.turno15 = turno15;
/*  599:     */   }
/*  600:     */   
/*  601:     */   public Turno getTurno16()
/*  602:     */   {
/*  603: 742 */     return this.turno16;
/*  604:     */   }
/*  605:     */   
/*  606:     */   public void setTurno16(Turno turno16)
/*  607:     */   {
/*  608: 746 */     this.turno16 = turno16;
/*  609:     */   }
/*  610:     */   
/*  611:     */   public Turno getTurno17()
/*  612:     */   {
/*  613: 750 */     return this.turno17;
/*  614:     */   }
/*  615:     */   
/*  616:     */   public void setTurno17(Turno turno17)
/*  617:     */   {
/*  618: 754 */     this.turno17 = turno17;
/*  619:     */   }
/*  620:     */   
/*  621:     */   public Turno getTurno18()
/*  622:     */   {
/*  623: 758 */     return this.turno18;
/*  624:     */   }
/*  625:     */   
/*  626:     */   public void setTurno18(Turno turno18)
/*  627:     */   {
/*  628: 762 */     this.turno18 = turno18;
/*  629:     */   }
/*  630:     */   
/*  631:     */   public Turno getTurno19()
/*  632:     */   {
/*  633: 766 */     return this.turno19;
/*  634:     */   }
/*  635:     */   
/*  636:     */   public void setTurno19(Turno turno19)
/*  637:     */   {
/*  638: 770 */     this.turno19 = turno19;
/*  639:     */   }
/*  640:     */   
/*  641:     */   public Turno getTurno20()
/*  642:     */   {
/*  643: 774 */     return this.turno20;
/*  644:     */   }
/*  645:     */   
/*  646:     */   public void setTurno20(Turno turno20)
/*  647:     */   {
/*  648: 778 */     this.turno20 = turno20;
/*  649:     */   }
/*  650:     */   
/*  651:     */   public Turno getTurno21()
/*  652:     */   {
/*  653: 782 */     return this.turno21;
/*  654:     */   }
/*  655:     */   
/*  656:     */   public void setTurno21(Turno turno21)
/*  657:     */   {
/*  658: 786 */     this.turno21 = turno21;
/*  659:     */   }
/*  660:     */   
/*  661:     */   public Turno getTurno22()
/*  662:     */   {
/*  663: 790 */     return this.turno22;
/*  664:     */   }
/*  665:     */   
/*  666:     */   public void setTurno22(Turno turno22)
/*  667:     */   {
/*  668: 794 */     this.turno22 = turno22;
/*  669:     */   }
/*  670:     */   
/*  671:     */   public Turno getTurno23()
/*  672:     */   {
/*  673: 798 */     return this.turno23;
/*  674:     */   }
/*  675:     */   
/*  676:     */   public void setTurno23(Turno turno23)
/*  677:     */   {
/*  678: 802 */     this.turno23 = turno23;
/*  679:     */   }
/*  680:     */   
/*  681:     */   public Turno getTurno24()
/*  682:     */   {
/*  683: 806 */     return this.turno24;
/*  684:     */   }
/*  685:     */   
/*  686:     */   public void setTurno24(Turno turno24)
/*  687:     */   {
/*  688: 810 */     this.turno24 = turno24;
/*  689:     */   }
/*  690:     */   
/*  691:     */   public Turno getTurno25()
/*  692:     */   {
/*  693: 814 */     return this.turno25;
/*  694:     */   }
/*  695:     */   
/*  696:     */   public void setTurno25(Turno turno25)
/*  697:     */   {
/*  698: 818 */     this.turno25 = turno25;
/*  699:     */   }
/*  700:     */   
/*  701:     */   public Turno getTurno26()
/*  702:     */   {
/*  703: 822 */     return this.turno26;
/*  704:     */   }
/*  705:     */   
/*  706:     */   public void setTurno26(Turno turno26)
/*  707:     */   {
/*  708: 826 */     this.turno26 = turno26;
/*  709:     */   }
/*  710:     */   
/*  711:     */   public Turno getTurno27()
/*  712:     */   {
/*  713: 830 */     return this.turno27;
/*  714:     */   }
/*  715:     */   
/*  716:     */   public void setTurno27(Turno turno27)
/*  717:     */   {
/*  718: 834 */     this.turno27 = turno27;
/*  719:     */   }
/*  720:     */   
/*  721:     */   public Turno getTurno28()
/*  722:     */   {
/*  723: 838 */     return this.turno28;
/*  724:     */   }
/*  725:     */   
/*  726:     */   public void setTurno28(Turno turno28)
/*  727:     */   {
/*  728: 842 */     this.turno28 = turno28;
/*  729:     */   }
/*  730:     */   
/*  731:     */   public Turno getTurno29()
/*  732:     */   {
/*  733: 846 */     return this.turno29;
/*  734:     */   }
/*  735:     */   
/*  736:     */   public void setTurno29(Turno turno29)
/*  737:     */   {
/*  738: 850 */     this.turno29 = turno29;
/*  739:     */   }
/*  740:     */   
/*  741:     */   public Turno getTurno30()
/*  742:     */   {
/*  743: 854 */     return this.turno30;
/*  744:     */   }
/*  745:     */   
/*  746:     */   public void setTurno30(Turno turno30)
/*  747:     */   {
/*  748: 858 */     this.turno30 = turno30;
/*  749:     */   }
/*  750:     */   
/*  751:     */   public Turno getTurno31()
/*  752:     */   {
/*  753: 862 */     return this.turno31;
/*  754:     */   }
/*  755:     */   
/*  756:     */   public void setTurno31(Turno turno31)
/*  757:     */   {
/*  758: 866 */     this.turno31 = turno31;
/*  759:     */   }
/*  760:     */   
/*  761:     */   public String toString()
/*  762:     */   {
/*  763: 871 */     return this.idDetallePlanPersonalizadoHorarioEmpleado + "";
/*  764:     */   }
/*  765:     */   
/*  766:     */   public Turno getTurnoDiaMes(Integer diaMes)
/*  767:     */   {
/*  768: 875 */     if ((diaMes == null) || (diaMes.intValue() < 1) || (diaMes.intValue() > 31)) {
/*  769: 876 */       return null;
/*  770:     */     }
/*  771: 878 */     Turno turno = null;
/*  772:     */     try
/*  773:     */     {
/*  774: 881 */       Object[] parametros = null;
/*  775: 882 */       Method metodo = getClass().getMethod("getTurno" + diaMes, new Class[0]);
/*  776: 883 */       turno = (Turno)metodo.invoke(this, parametros);
/*  777:     */     }
/*  778:     */     catch (Exception e)
/*  779:     */     {
/*  780: 885 */       System.out.println(e.getMessage());
/*  781: 886 */       e.printStackTrace();
/*  782:     */     }
/*  783: 889 */     return turno;
/*  784:     */   }
/*  785:     */   
/*  786:     */   public Boolean getIndicador(Integer diaMes, String atributo)
/*  787:     */   {
/*  788: 893 */     if ((diaMes == null) || (diaMes.intValue() < 1) || (diaMes.intValue() > 31)) {
/*  789: 894 */       return Boolean.valueOf(false);
/*  790:     */     }
/*  791: 896 */     Boolean indicador = Boolean.valueOf(false);
/*  792:     */     try
/*  793:     */     {
/*  794: 899 */       Object[] parametros = null;
/*  795: 900 */       Method metodo = getClass().getMethod(atributo + diaMes, new Class[0]);
/*  796: 901 */       indicador = (Boolean)metodo.invoke(this, parametros);
/*  797:     */     }
/*  798:     */     catch (Exception e)
/*  799:     */     {
/*  800: 903 */       System.out.println(e.getMessage());
/*  801: 904 */       e.printStackTrace();
/*  802:     */     }
/*  803: 907 */     return indicador;
/*  804:     */   }
/*  805:     */   
/*  806:     */   public void setIndicador(Integer diaMes, Boolean indicadorDiaDescanso, String atributo)
/*  807:     */   {
/*  808: 911 */     if ((diaMes == null) || (diaMes.intValue() < 1) || (diaMes.intValue() > 31)) {
/*  809: 912 */       return;
/*  810:     */     }
/*  811:     */     try
/*  812:     */     {
/*  813: 916 */       Object[] parametros = new Object[1];
/*  814: 917 */       parametros[0] = indicadorDiaDescanso;
/*  815: 918 */       Method metodo = getClass().getMethod(atributo + diaMes, new Class[] { Boolean.class });
/*  816: 919 */       metodo.invoke(this, parametros);
/*  817:     */     }
/*  818:     */     catch (Exception e)
/*  819:     */     {
/*  820: 921 */       System.out.println(e.getMessage());
/*  821: 922 */       e.printStackTrace();
/*  822:     */     }
/*  823:     */   }
/*  824:     */   
/*  825:     */   public void setTurnoDiaMes(Integer diaMes, Turno turno)
/*  826:     */   {
/*  827: 927 */     if ((diaMes == null) || (diaMes.intValue() < 1) || (diaMes.intValue() > 31)) {
/*  828: 928 */       return;
/*  829:     */     }
/*  830:     */     try
/*  831:     */     {
/*  832: 932 */       Object[] parametros = new Object[1];
/*  833: 933 */       parametros[0] = turno;
/*  834: 934 */       Method metodo = getClass().getMethod("setTurno" + diaMes, new Class[] { Turno.class });
/*  835: 935 */       metodo.invoke(this, parametros);
/*  836:     */     }
/*  837:     */     catch (Exception e)
/*  838:     */     {
/*  839: 937 */       System.out.println(e.getMessage());
/*  840: 938 */       e.printStackTrace();
/*  841:     */     }
/*  842:     */   }
/*  843:     */   
/*  844:     */   public Turno getTurno_1_1()
/*  845:     */   {
/*  846: 943 */     this.turno_1_1 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(1, 1));
/*  847: 944 */     return this.turno_1_1;
/*  848:     */   }
/*  849:     */   
/*  850:     */   public void setTurno_1_1(Turno turno_1_1)
/*  851:     */   {
/*  852: 948 */     this.turno_1_1 = turno_1_1;
/*  853: 949 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(1, 1), turno_1_1);
/*  854:     */   }
/*  855:     */   
/*  856:     */   public Turno getTurno_1_2()
/*  857:     */   {
/*  858: 953 */     this.turno_1_2 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(1, 2));
/*  859: 954 */     return this.turno_1_2;
/*  860:     */   }
/*  861:     */   
/*  862:     */   public void setTurno_1_2(Turno turno_1_2)
/*  863:     */   {
/*  864: 958 */     this.turno_1_2 = turno_1_2;
/*  865: 959 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(1, 2), turno_1_2);
/*  866:     */   }
/*  867:     */   
/*  868:     */   public Turno getTurno_1_3()
/*  869:     */   {
/*  870: 963 */     this.turno_1_3 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(1, 3));
/*  871: 964 */     return this.turno_1_3;
/*  872:     */   }
/*  873:     */   
/*  874:     */   public void setTurno_1_3(Turno turno_1_3)
/*  875:     */   {
/*  876: 968 */     this.turno_1_3 = turno_1_3;
/*  877: 969 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(1, 3), turno_1_3);
/*  878:     */   }
/*  879:     */   
/*  880:     */   public Turno getTurno_1_4()
/*  881:     */   {
/*  882: 973 */     this.turno_1_4 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(1, 4));
/*  883: 974 */     return this.turno_1_4;
/*  884:     */   }
/*  885:     */   
/*  886:     */   public void setTurno_1_4(Turno turno_1_4)
/*  887:     */   {
/*  888: 978 */     this.turno_1_4 = turno_1_4;
/*  889: 979 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(1, 4), turno_1_4);
/*  890:     */   }
/*  891:     */   
/*  892:     */   public Turno getTurno_1_5()
/*  893:     */   {
/*  894: 983 */     this.turno_1_5 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(1, 5));
/*  895: 984 */     return this.turno_1_5;
/*  896:     */   }
/*  897:     */   
/*  898:     */   public void setTurno_1_5(Turno turno_1_5)
/*  899:     */   {
/*  900: 988 */     this.turno_1_5 = turno_1_5;
/*  901: 989 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(1, 5), turno_1_5);
/*  902:     */   }
/*  903:     */   
/*  904:     */   public Turno getTurno_1_6()
/*  905:     */   {
/*  906: 993 */     this.turno_1_6 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(1, 6));
/*  907: 994 */     return this.turno_1_6;
/*  908:     */   }
/*  909:     */   
/*  910:     */   public void setTurno_1_6(Turno turno_1_6)
/*  911:     */   {
/*  912: 998 */     this.turno_1_6 = turno_1_6;
/*  913: 999 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(1, 6), turno_1_6);
/*  914:     */   }
/*  915:     */   
/*  916:     */   public Turno getTurno_1_7()
/*  917:     */   {
/*  918:1003 */     this.turno_1_7 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(1, 7));
/*  919:1004 */     return this.turno_1_7;
/*  920:     */   }
/*  921:     */   
/*  922:     */   public void setTurno_1_7(Turno turno_1_7)
/*  923:     */   {
/*  924:1008 */     this.turno_1_7 = turno_1_7;
/*  925:1009 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(1, 7), turno_1_7);
/*  926:     */   }
/*  927:     */   
/*  928:     */   public Turno getTurno_2_1()
/*  929:     */   {
/*  930:1013 */     this.turno_2_1 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(2, 1));
/*  931:1014 */     return this.turno_2_1;
/*  932:     */   }
/*  933:     */   
/*  934:     */   public void setTurno_2_1(Turno turno_2_1)
/*  935:     */   {
/*  936:1018 */     this.turno_2_1 = turno_2_1;
/*  937:1019 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(2, 1), turno_2_1);
/*  938:     */   }
/*  939:     */   
/*  940:     */   public Turno getTurno_2_2()
/*  941:     */   {
/*  942:1023 */     this.turno_2_2 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(2, 2));
/*  943:1024 */     return this.turno_2_2;
/*  944:     */   }
/*  945:     */   
/*  946:     */   public void setTurno_2_2(Turno turno_2_2)
/*  947:     */   {
/*  948:1028 */     this.turno_2_2 = turno_2_2;
/*  949:1029 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(2, 2), turno_2_2);
/*  950:     */   }
/*  951:     */   
/*  952:     */   public Turno getTurno_2_3()
/*  953:     */   {
/*  954:1033 */     this.turno_2_3 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(2, 3));
/*  955:1034 */     return this.turno_2_3;
/*  956:     */   }
/*  957:     */   
/*  958:     */   public void setTurno_2_3(Turno turno_2_3)
/*  959:     */   {
/*  960:1038 */     this.turno_2_3 = turno_2_3;
/*  961:1039 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(2, 3), turno_2_3);
/*  962:     */   }
/*  963:     */   
/*  964:     */   public Turno getTurno_2_4()
/*  965:     */   {
/*  966:1043 */     this.turno_2_4 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(2, 4));
/*  967:1044 */     return this.turno_2_4;
/*  968:     */   }
/*  969:     */   
/*  970:     */   public void setTurno_2_4(Turno turno_2_4)
/*  971:     */   {
/*  972:1048 */     this.turno_2_4 = turno_2_4;
/*  973:1049 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(2, 4), turno_2_4);
/*  974:     */   }
/*  975:     */   
/*  976:     */   public Turno getTurno_2_5()
/*  977:     */   {
/*  978:1053 */     this.turno_2_5 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(2, 5));
/*  979:1054 */     return this.turno_2_5;
/*  980:     */   }
/*  981:     */   
/*  982:     */   public void setTurno_2_5(Turno turno_2_5)
/*  983:     */   {
/*  984:1058 */     this.turno_2_5 = turno_2_5;
/*  985:1059 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(2, 5), turno_2_5);
/*  986:     */   }
/*  987:     */   
/*  988:     */   public Turno getTurno_2_6()
/*  989:     */   {
/*  990:1063 */     this.turno_2_6 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(2, 6));
/*  991:1064 */     return this.turno_2_6;
/*  992:     */   }
/*  993:     */   
/*  994:     */   public void setTurno_2_6(Turno turno_2_6)
/*  995:     */   {
/*  996:1068 */     this.turno_2_6 = turno_2_6;
/*  997:1069 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(2, 6), turno_2_6);
/*  998:     */   }
/*  999:     */   
/* 1000:     */   public Turno getTurno_2_7()
/* 1001:     */   {
/* 1002:1073 */     this.turno_2_7 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(2, 7));
/* 1003:1074 */     return this.turno_2_7;
/* 1004:     */   }
/* 1005:     */   
/* 1006:     */   public void setTurno_2_7(Turno turno_2_7)
/* 1007:     */   {
/* 1008:1078 */     this.turno_2_7 = turno_2_7;
/* 1009:1079 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(2, 7), turno_2_7);
/* 1010:     */   }
/* 1011:     */   
/* 1012:     */   public Turno getTurno_3_1()
/* 1013:     */   {
/* 1014:1083 */     this.turno_3_1 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(3, 1));
/* 1015:1084 */     return this.turno_3_1;
/* 1016:     */   }
/* 1017:     */   
/* 1018:     */   public void setTurno_3_1(Turno turno_3_1)
/* 1019:     */   {
/* 1020:1088 */     this.turno_3_1 = turno_3_1;
/* 1021:1089 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(3, 1), turno_3_1);
/* 1022:     */   }
/* 1023:     */   
/* 1024:     */   public Turno getTurno_3_2()
/* 1025:     */   {
/* 1026:1093 */     this.turno_3_2 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(3, 2));
/* 1027:1094 */     return this.turno_3_2;
/* 1028:     */   }
/* 1029:     */   
/* 1030:     */   public void setTurno_3_2(Turno turno_3_2)
/* 1031:     */   {
/* 1032:1098 */     this.turno_3_2 = turno_3_2;
/* 1033:1099 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(3, 2), turno_3_2);
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public Turno getTurno_3_3()
/* 1037:     */   {
/* 1038:1103 */     this.turno_3_3 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(3, 3));
/* 1039:1104 */     return this.turno_3_3;
/* 1040:     */   }
/* 1041:     */   
/* 1042:     */   public void setTurno_3_3(Turno turno_3_3)
/* 1043:     */   {
/* 1044:1108 */     this.turno_3_3 = turno_3_3;
/* 1045:1109 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(3, 3), turno_3_3);
/* 1046:     */   }
/* 1047:     */   
/* 1048:     */   public Turno getTurno_3_4()
/* 1049:     */   {
/* 1050:1113 */     this.turno_3_4 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(3, 4));
/* 1051:1114 */     return this.turno_3_4;
/* 1052:     */   }
/* 1053:     */   
/* 1054:     */   public void setTurno_3_4(Turno turno_3_4)
/* 1055:     */   {
/* 1056:1118 */     this.turno_3_4 = turno_3_4;
/* 1057:1119 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(3, 4), turno_3_4);
/* 1058:     */   }
/* 1059:     */   
/* 1060:     */   public Turno getTurno_3_5()
/* 1061:     */   {
/* 1062:1123 */     this.turno_3_5 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(3, 5));
/* 1063:1124 */     return this.turno_3_5;
/* 1064:     */   }
/* 1065:     */   
/* 1066:     */   public void setTurno_3_5(Turno turno_3_5)
/* 1067:     */   {
/* 1068:1128 */     this.turno_3_5 = turno_3_5;
/* 1069:1129 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(3, 5), turno_3_5);
/* 1070:     */   }
/* 1071:     */   
/* 1072:     */   public Turno getTurno_3_6()
/* 1073:     */   {
/* 1074:1133 */     this.turno_3_6 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(3, 6));
/* 1075:1134 */     return this.turno_3_6;
/* 1076:     */   }
/* 1077:     */   
/* 1078:     */   public void setTurno_3_6(Turno turno_3_6)
/* 1079:     */   {
/* 1080:1138 */     this.turno_3_6 = turno_3_6;
/* 1081:1139 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(3, 6), turno_3_6);
/* 1082:     */   }
/* 1083:     */   
/* 1084:     */   public Turno getTurno_3_7()
/* 1085:     */   {
/* 1086:1143 */     this.turno_3_7 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(3, 7));
/* 1087:1144 */     return this.turno_3_7;
/* 1088:     */   }
/* 1089:     */   
/* 1090:     */   public void setTurno_3_7(Turno turno_3_7)
/* 1091:     */   {
/* 1092:1148 */     this.turno_3_7 = turno_3_7;
/* 1093:1149 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(3, 7), turno_3_7);
/* 1094:     */   }
/* 1095:     */   
/* 1096:     */   public Turno getTurno_4_1()
/* 1097:     */   {
/* 1098:1153 */     this.turno_4_1 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(4, 1));
/* 1099:1154 */     return this.turno_4_1;
/* 1100:     */   }
/* 1101:     */   
/* 1102:     */   public void setTurno_4_1(Turno turno_4_1)
/* 1103:     */   {
/* 1104:1158 */     this.turno_4_1 = turno_4_1;
/* 1105:1159 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(4, 1), turno_4_1);
/* 1106:     */   }
/* 1107:     */   
/* 1108:     */   public Turno getTurno_4_2()
/* 1109:     */   {
/* 1110:1163 */     this.turno_4_2 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(4, 2));
/* 1111:1164 */     return this.turno_4_2;
/* 1112:     */   }
/* 1113:     */   
/* 1114:     */   public void setTurno_4_2(Turno turno_4_2)
/* 1115:     */   {
/* 1116:1168 */     this.turno_4_2 = turno_4_2;
/* 1117:1169 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(4, 2), turno_4_2);
/* 1118:     */   }
/* 1119:     */   
/* 1120:     */   public Turno getTurno_4_3()
/* 1121:     */   {
/* 1122:1173 */     this.turno_4_3 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(4, 3));
/* 1123:1174 */     return this.turno_4_3;
/* 1124:     */   }
/* 1125:     */   
/* 1126:     */   public void setTurno_4_3(Turno turno_4_3)
/* 1127:     */   {
/* 1128:1178 */     this.turno_4_3 = turno_4_3;
/* 1129:1179 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(4, 3), turno_4_3);
/* 1130:     */   }
/* 1131:     */   
/* 1132:     */   public Turno getTurno_4_4()
/* 1133:     */   {
/* 1134:1183 */     this.turno_4_4 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(4, 4));
/* 1135:1184 */     return this.turno_4_4;
/* 1136:     */   }
/* 1137:     */   
/* 1138:     */   public void setTurno_4_4(Turno turno_4_4)
/* 1139:     */   {
/* 1140:1188 */     this.turno_4_4 = turno_4_4;
/* 1141:1189 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(4, 4), turno_4_4);
/* 1142:     */   }
/* 1143:     */   
/* 1144:     */   public Turno getTurno_4_5()
/* 1145:     */   {
/* 1146:1193 */     this.turno_4_5 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(4, 5));
/* 1147:1194 */     return this.turno_4_5;
/* 1148:     */   }
/* 1149:     */   
/* 1150:     */   public void setTurno_4_5(Turno turno_4_5)
/* 1151:     */   {
/* 1152:1198 */     this.turno_4_5 = turno_4_5;
/* 1153:1199 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(4, 5), turno_4_5);
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   public Turno getTurno_4_6()
/* 1157:     */   {
/* 1158:1203 */     this.turno_4_6 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(4, 6));
/* 1159:1204 */     return this.turno_4_6;
/* 1160:     */   }
/* 1161:     */   
/* 1162:     */   public void setTurno_4_6(Turno turno_4_6)
/* 1163:     */   {
/* 1164:1208 */     this.turno_4_6 = turno_4_6;
/* 1165:1209 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(4, 6), turno_4_6);
/* 1166:     */   }
/* 1167:     */   
/* 1168:     */   public Turno getTurno_4_7()
/* 1169:     */   {
/* 1170:1213 */     this.turno_4_7 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(4, 7));
/* 1171:1214 */     return this.turno_4_7;
/* 1172:     */   }
/* 1173:     */   
/* 1174:     */   public void setTurno_4_7(Turno turno_4_7)
/* 1175:     */   {
/* 1176:1218 */     this.turno_4_7 = turno_4_7;
/* 1177:1219 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(4, 7), turno_4_7);
/* 1178:     */   }
/* 1179:     */   
/* 1180:     */   public Turno getTurno_5_1()
/* 1181:     */   {
/* 1182:1223 */     this.turno_5_1 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(5, 1));
/* 1183:1224 */     return this.turno_5_1;
/* 1184:     */   }
/* 1185:     */   
/* 1186:     */   public void setTurno_5_1(Turno turno_5_1)
/* 1187:     */   {
/* 1188:1228 */     this.turno_5_1 = turno_5_1;
/* 1189:1229 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(5, 1), turno_5_1);
/* 1190:     */   }
/* 1191:     */   
/* 1192:     */   public Turno getTurno_5_2()
/* 1193:     */   {
/* 1194:1233 */     this.turno_5_2 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(5, 2));
/* 1195:1234 */     return this.turno_5_2;
/* 1196:     */   }
/* 1197:     */   
/* 1198:     */   public void setTurno_5_2(Turno turno_5_2)
/* 1199:     */   {
/* 1200:1238 */     this.turno_5_2 = turno_5_2;
/* 1201:1239 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(5, 2), turno_5_2);
/* 1202:     */   }
/* 1203:     */   
/* 1204:     */   public Turno getTurno_5_3()
/* 1205:     */   {
/* 1206:1243 */     this.turno_5_3 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(5, 3));
/* 1207:1244 */     return this.turno_5_3;
/* 1208:     */   }
/* 1209:     */   
/* 1210:     */   public void setTurno_5_3(Turno turno_5_3)
/* 1211:     */   {
/* 1212:1248 */     this.turno_5_3 = turno_5_3;
/* 1213:1249 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(5, 3), turno_5_3);
/* 1214:     */   }
/* 1215:     */   
/* 1216:     */   public Turno getTurno_5_4()
/* 1217:     */   {
/* 1218:1253 */     this.turno_5_4 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(5, 4));
/* 1219:1254 */     return this.turno_5_4;
/* 1220:     */   }
/* 1221:     */   
/* 1222:     */   public void setTurno_5_4(Turno turno_5_4)
/* 1223:     */   {
/* 1224:1258 */     this.turno_5_4 = turno_5_4;
/* 1225:1259 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(5, 4), turno_5_4);
/* 1226:     */   }
/* 1227:     */   
/* 1228:     */   public Turno getTurno_5_5()
/* 1229:     */   {
/* 1230:1263 */     this.turno_5_5 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(5, 5));
/* 1231:1264 */     return this.turno_5_5;
/* 1232:     */   }
/* 1233:     */   
/* 1234:     */   public void setTurno_5_5(Turno turno_5_5)
/* 1235:     */   {
/* 1236:1268 */     this.turno_5_5 = turno_5_5;
/* 1237:1269 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(5, 5), turno_5_5);
/* 1238:     */   }
/* 1239:     */   
/* 1240:     */   public Turno getTurno_5_6()
/* 1241:     */   {
/* 1242:1273 */     this.turno_5_6 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(5, 6));
/* 1243:1274 */     return this.turno_5_6;
/* 1244:     */   }
/* 1245:     */   
/* 1246:     */   public void setTurno_5_6(Turno turno_5_6)
/* 1247:     */   {
/* 1248:1278 */     this.turno_5_6 = turno_5_6;
/* 1249:1279 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(5, 6), turno_5_6);
/* 1250:     */   }
/* 1251:     */   
/* 1252:     */   public Turno getTurno_5_7()
/* 1253:     */   {
/* 1254:1283 */     this.turno_5_7 = getTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(5, 7));
/* 1255:1284 */     return this.turno_5_7;
/* 1256:     */   }
/* 1257:     */   
/* 1258:     */   public void setTurno_5_7(Turno turno_5_7)
/* 1259:     */   {
/* 1260:1288 */     this.turno_5_7 = turno_5_7;
/* 1261:1289 */     setTurnoDiaMes(this.planPersonalizadoHorarioEmpleado.getDiaMes(5, 7), turno_5_7);
/* 1262:     */   }
/* 1263:     */   
/* 1264:     */   public Boolean getIndicadorDiaDescanso1()
/* 1265:     */   {
/* 1266:1293 */     if (this.indicadorDiaDescanso1 == null) {
/* 1267:1294 */       this.indicadorDiaDescanso1 = Boolean.valueOf(false);
/* 1268:     */     }
/* 1269:1296 */     return this.indicadorDiaDescanso1;
/* 1270:     */   }
/* 1271:     */   
/* 1272:     */   public void setIndicadorDiaDescanso1(Boolean indicadorDiaDescanso1)
/* 1273:     */   {
/* 1274:1300 */     this.indicadorDiaDescanso1 = indicadorDiaDescanso1;
/* 1275:     */   }
/* 1276:     */   
/* 1277:     */   public Boolean getIndicadorDiaDescanso2()
/* 1278:     */   {
/* 1279:1304 */     if (this.indicadorDiaDescanso2 == null) {
/* 1280:1305 */       this.indicadorDiaDescanso2 = Boolean.valueOf(false);
/* 1281:     */     }
/* 1282:1307 */     return this.indicadorDiaDescanso2;
/* 1283:     */   }
/* 1284:     */   
/* 1285:     */   public void setIndicadorDiaDescanso2(Boolean indicadorDiaDescanso2)
/* 1286:     */   {
/* 1287:1311 */     this.indicadorDiaDescanso2 = indicadorDiaDescanso2;
/* 1288:     */   }
/* 1289:     */   
/* 1290:     */   public Boolean getIndicadorDiaDescanso3()
/* 1291:     */   {
/* 1292:1315 */     if (this.indicadorDiaDescanso3 == null) {
/* 1293:1316 */       this.indicadorDiaDescanso3 = Boolean.valueOf(false);
/* 1294:     */     }
/* 1295:1318 */     return this.indicadorDiaDescanso3;
/* 1296:     */   }
/* 1297:     */   
/* 1298:     */   public void setIndicadorDiaDescanso3(Boolean indicadorDiaDescanso3)
/* 1299:     */   {
/* 1300:1322 */     this.indicadorDiaDescanso3 = indicadorDiaDescanso3;
/* 1301:     */   }
/* 1302:     */   
/* 1303:     */   public Boolean getIndicadorDiaDescanso4()
/* 1304:     */   {
/* 1305:1326 */     if (this.indicadorDiaDescanso4 == null) {
/* 1306:1327 */       this.indicadorDiaDescanso4 = Boolean.valueOf(false);
/* 1307:     */     }
/* 1308:1329 */     return this.indicadorDiaDescanso4;
/* 1309:     */   }
/* 1310:     */   
/* 1311:     */   public void setIndicadorDiaDescanso4(Boolean indicadorDiaDescanso4)
/* 1312:     */   {
/* 1313:1333 */     this.indicadorDiaDescanso4 = indicadorDiaDescanso4;
/* 1314:     */   }
/* 1315:     */   
/* 1316:     */   public Boolean getIndicadorDiaDescanso5()
/* 1317:     */   {
/* 1318:1337 */     if (this.indicadorDiaDescanso5 == null) {
/* 1319:1338 */       this.indicadorDiaDescanso5 = Boolean.valueOf(false);
/* 1320:     */     }
/* 1321:1340 */     return this.indicadorDiaDescanso5;
/* 1322:     */   }
/* 1323:     */   
/* 1324:     */   public void setIndicadorDiaDescanso5(Boolean indicadorDiaDescanso5)
/* 1325:     */   {
/* 1326:1344 */     this.indicadorDiaDescanso5 = indicadorDiaDescanso5;
/* 1327:     */   }
/* 1328:     */   
/* 1329:     */   public Boolean getIndicadorDiaDescanso6()
/* 1330:     */   {
/* 1331:1348 */     if (this.indicadorDiaDescanso6 == null) {
/* 1332:1349 */       this.indicadorDiaDescanso6 = Boolean.valueOf(false);
/* 1333:     */     }
/* 1334:1351 */     return this.indicadorDiaDescanso6;
/* 1335:     */   }
/* 1336:     */   
/* 1337:     */   public void setIndicadorDiaDescanso6(Boolean indicadorDiaDescanso6)
/* 1338:     */   {
/* 1339:1355 */     this.indicadorDiaDescanso6 = indicadorDiaDescanso6;
/* 1340:     */   }
/* 1341:     */   
/* 1342:     */   public Boolean getIndicadorDiaDescanso7()
/* 1343:     */   {
/* 1344:1359 */     if (this.indicadorDiaDescanso7 == null) {
/* 1345:1360 */       this.indicadorDiaDescanso7 = Boolean.valueOf(false);
/* 1346:     */     }
/* 1347:1362 */     return this.indicadorDiaDescanso7;
/* 1348:     */   }
/* 1349:     */   
/* 1350:     */   public void setIndicadorDiaDescanso7(Boolean indicadorDiaDescanso7)
/* 1351:     */   {
/* 1352:1366 */     this.indicadorDiaDescanso7 = indicadorDiaDescanso7;
/* 1353:     */   }
/* 1354:     */   
/* 1355:     */   public Boolean getIndicadorDiaDescanso8()
/* 1356:     */   {
/* 1357:1370 */     if (this.indicadorDiaDescanso8 == null) {
/* 1358:1371 */       this.indicadorDiaDescanso8 = Boolean.valueOf(false);
/* 1359:     */     }
/* 1360:1373 */     return this.indicadorDiaDescanso8;
/* 1361:     */   }
/* 1362:     */   
/* 1363:     */   public void setIndicadorDiaDescanso8(Boolean indicadorDiaDescanso8)
/* 1364:     */   {
/* 1365:1377 */     this.indicadorDiaDescanso8 = indicadorDiaDescanso8;
/* 1366:     */   }
/* 1367:     */   
/* 1368:     */   public Boolean getIndicadorDiaDescanso9()
/* 1369:     */   {
/* 1370:1381 */     if (this.indicadorDiaDescanso9 == null) {
/* 1371:1382 */       this.indicadorDiaDescanso9 = Boolean.valueOf(false);
/* 1372:     */     }
/* 1373:1384 */     return this.indicadorDiaDescanso9;
/* 1374:     */   }
/* 1375:     */   
/* 1376:     */   public void setIndicadorDiaDescanso9(Boolean indicadorDiaDescanso9)
/* 1377:     */   {
/* 1378:1388 */     this.indicadorDiaDescanso9 = indicadorDiaDescanso9;
/* 1379:     */   }
/* 1380:     */   
/* 1381:     */   public Boolean getIndicadorDiaDescanso10()
/* 1382:     */   {
/* 1383:1392 */     if (this.indicadorDiaDescanso10 == null) {
/* 1384:1393 */       this.indicadorDiaDescanso10 = Boolean.valueOf(false);
/* 1385:     */     }
/* 1386:1395 */     return this.indicadorDiaDescanso10;
/* 1387:     */   }
/* 1388:     */   
/* 1389:     */   public void setIndicadorDiaDescanso10(Boolean indicadorDiaDescanso10)
/* 1390:     */   {
/* 1391:1399 */     this.indicadorDiaDescanso10 = indicadorDiaDescanso10;
/* 1392:     */   }
/* 1393:     */   
/* 1394:     */   public Boolean getIndicadorDiaDescanso11()
/* 1395:     */   {
/* 1396:1403 */     if (this.indicadorDiaDescanso11 == null) {
/* 1397:1404 */       this.indicadorDiaDescanso11 = Boolean.valueOf(false);
/* 1398:     */     }
/* 1399:1406 */     return this.indicadorDiaDescanso11;
/* 1400:     */   }
/* 1401:     */   
/* 1402:     */   public void setIndicadorDiaDescanso11(Boolean indicadorDiaDescanso11)
/* 1403:     */   {
/* 1404:1410 */     this.indicadorDiaDescanso11 = indicadorDiaDescanso11;
/* 1405:     */   }
/* 1406:     */   
/* 1407:     */   public Boolean getIndicadorDiaDescanso12()
/* 1408:     */   {
/* 1409:1414 */     if (this.indicadorDiaDescanso12 == null) {
/* 1410:1415 */       this.indicadorDiaDescanso12 = Boolean.valueOf(false);
/* 1411:     */     }
/* 1412:1417 */     return this.indicadorDiaDescanso12;
/* 1413:     */   }
/* 1414:     */   
/* 1415:     */   public void setIndicadorDiaDescanso12(Boolean indicadorDiaDescanso12)
/* 1416:     */   {
/* 1417:1421 */     this.indicadorDiaDescanso12 = indicadorDiaDescanso12;
/* 1418:     */   }
/* 1419:     */   
/* 1420:     */   public Boolean getIndicadorDiaDescanso13()
/* 1421:     */   {
/* 1422:1425 */     if (this.indicadorDiaDescanso13 == null) {
/* 1423:1426 */       this.indicadorDiaDescanso13 = Boolean.valueOf(false);
/* 1424:     */     }
/* 1425:1428 */     return this.indicadorDiaDescanso13;
/* 1426:     */   }
/* 1427:     */   
/* 1428:     */   public void setIndicadorDiaDescanso13(Boolean indicadorDiaDescanso13)
/* 1429:     */   {
/* 1430:1432 */     this.indicadorDiaDescanso13 = indicadorDiaDescanso13;
/* 1431:     */   }
/* 1432:     */   
/* 1433:     */   public Boolean getIndicadorDiaDescanso14()
/* 1434:     */   {
/* 1435:1436 */     if (this.indicadorDiaDescanso14 == null) {
/* 1436:1437 */       this.indicadorDiaDescanso14 = Boolean.valueOf(false);
/* 1437:     */     }
/* 1438:1439 */     return this.indicadorDiaDescanso14;
/* 1439:     */   }
/* 1440:     */   
/* 1441:     */   public void setIndicadorDiaDescanso14(Boolean indicadorDiaDescanso14)
/* 1442:     */   {
/* 1443:1443 */     this.indicadorDiaDescanso14 = indicadorDiaDescanso14;
/* 1444:     */   }
/* 1445:     */   
/* 1446:     */   public Boolean getIndicadorDiaDescanso15()
/* 1447:     */   {
/* 1448:1447 */     if (this.indicadorDiaDescanso15 == null) {
/* 1449:1448 */       this.indicadorDiaDescanso15 = Boolean.valueOf(false);
/* 1450:     */     }
/* 1451:1450 */     return this.indicadorDiaDescanso15;
/* 1452:     */   }
/* 1453:     */   
/* 1454:     */   public void setIndicadorDiaDescanso15(Boolean indicadorDiaDescanso15)
/* 1455:     */   {
/* 1456:1454 */     this.indicadorDiaDescanso15 = indicadorDiaDescanso15;
/* 1457:     */   }
/* 1458:     */   
/* 1459:     */   public Boolean getIndicadorDiaDescanso16()
/* 1460:     */   {
/* 1461:1458 */     if (this.indicadorDiaDescanso16 == null) {
/* 1462:1459 */       this.indicadorDiaDescanso16 = Boolean.valueOf(false);
/* 1463:     */     }
/* 1464:1461 */     return this.indicadorDiaDescanso16;
/* 1465:     */   }
/* 1466:     */   
/* 1467:     */   public void setIndicadorDiaDescanso16(Boolean indicadorDiaDescanso16)
/* 1468:     */   {
/* 1469:1465 */     this.indicadorDiaDescanso16 = indicadorDiaDescanso16;
/* 1470:     */   }
/* 1471:     */   
/* 1472:     */   public Boolean getIndicadorDiaDescanso17()
/* 1473:     */   {
/* 1474:1469 */     if (this.indicadorDiaDescanso17 == null) {
/* 1475:1470 */       this.indicadorDiaDescanso17 = Boolean.valueOf(false);
/* 1476:     */     }
/* 1477:1472 */     return this.indicadorDiaDescanso17;
/* 1478:     */   }
/* 1479:     */   
/* 1480:     */   public void setIndicadorDiaDescanso17(Boolean indicadorDiaDescanso17)
/* 1481:     */   {
/* 1482:1476 */     this.indicadorDiaDescanso17 = indicadorDiaDescanso17;
/* 1483:     */   }
/* 1484:     */   
/* 1485:     */   public Boolean getIndicadorDiaDescanso18()
/* 1486:     */   {
/* 1487:1480 */     if (this.indicadorDiaDescanso18 == null) {
/* 1488:1481 */       this.indicadorDiaDescanso18 = Boolean.valueOf(false);
/* 1489:     */     }
/* 1490:1483 */     return this.indicadorDiaDescanso18;
/* 1491:     */   }
/* 1492:     */   
/* 1493:     */   public void setIndicadorDiaDescanso18(Boolean indicadorDiaDescanso18)
/* 1494:     */   {
/* 1495:1487 */     this.indicadorDiaDescanso18 = indicadorDiaDescanso18;
/* 1496:     */   }
/* 1497:     */   
/* 1498:     */   public Boolean getIndicadorDiaDescanso19()
/* 1499:     */   {
/* 1500:1491 */     if (this.indicadorDiaDescanso19 == null) {
/* 1501:1492 */       this.indicadorDiaDescanso19 = Boolean.valueOf(false);
/* 1502:     */     }
/* 1503:1494 */     return this.indicadorDiaDescanso19;
/* 1504:     */   }
/* 1505:     */   
/* 1506:     */   public void setIndicadorDiaDescanso19(Boolean indicadorDiaDescanso19)
/* 1507:     */   {
/* 1508:1498 */     this.indicadorDiaDescanso19 = indicadorDiaDescanso19;
/* 1509:     */   }
/* 1510:     */   
/* 1511:     */   public Boolean getIndicadorDiaDescanso20()
/* 1512:     */   {
/* 1513:1502 */     if (this.indicadorDiaDescanso20 == null) {
/* 1514:1503 */       this.indicadorDiaDescanso20 = Boolean.valueOf(false);
/* 1515:     */     }
/* 1516:1505 */     return this.indicadorDiaDescanso20;
/* 1517:     */   }
/* 1518:     */   
/* 1519:     */   public void setIndicadorDiaDescanso20(Boolean indicadorDiaDescanso20)
/* 1520:     */   {
/* 1521:1509 */     this.indicadorDiaDescanso20 = indicadorDiaDescanso20;
/* 1522:     */   }
/* 1523:     */   
/* 1524:     */   public Boolean getIndicadorDiaDescanso21()
/* 1525:     */   {
/* 1526:1513 */     if (this.indicadorDiaDescanso21 == null) {
/* 1527:1514 */       this.indicadorDiaDescanso21 = Boolean.valueOf(false);
/* 1528:     */     }
/* 1529:1516 */     return this.indicadorDiaDescanso21;
/* 1530:     */   }
/* 1531:     */   
/* 1532:     */   public void setIndicadorDiaDescanso21(Boolean indicadorDiaDescanso21)
/* 1533:     */   {
/* 1534:1520 */     this.indicadorDiaDescanso21 = indicadorDiaDescanso21;
/* 1535:     */   }
/* 1536:     */   
/* 1537:     */   public Boolean getIndicadorDiaDescanso22()
/* 1538:     */   {
/* 1539:1524 */     if (this.indicadorDiaDescanso22 == null) {
/* 1540:1525 */       this.indicadorDiaDescanso22 = Boolean.valueOf(false);
/* 1541:     */     }
/* 1542:1527 */     return this.indicadorDiaDescanso22;
/* 1543:     */   }
/* 1544:     */   
/* 1545:     */   public void setIndicadorDiaDescanso22(Boolean indicadorDiaDescanso22)
/* 1546:     */   {
/* 1547:1531 */     this.indicadorDiaDescanso22 = indicadorDiaDescanso22;
/* 1548:     */   }
/* 1549:     */   
/* 1550:     */   public Boolean getIndicadorDiaDescanso23()
/* 1551:     */   {
/* 1552:1535 */     if (this.indicadorDiaDescanso23 == null) {
/* 1553:1536 */       this.indicadorDiaDescanso23 = Boolean.valueOf(false);
/* 1554:     */     }
/* 1555:1538 */     return this.indicadorDiaDescanso23;
/* 1556:     */   }
/* 1557:     */   
/* 1558:     */   public void setIndicadorDiaDescanso23(Boolean indicadorDiaDescanso23)
/* 1559:     */   {
/* 1560:1542 */     this.indicadorDiaDescanso23 = indicadorDiaDescanso23;
/* 1561:     */   }
/* 1562:     */   
/* 1563:     */   public Boolean getIndicadorDiaDescanso24()
/* 1564:     */   {
/* 1565:1546 */     if (this.indicadorDiaDescanso24 == null) {
/* 1566:1547 */       this.indicadorDiaDescanso24 = Boolean.valueOf(false);
/* 1567:     */     }
/* 1568:1549 */     return this.indicadorDiaDescanso24;
/* 1569:     */   }
/* 1570:     */   
/* 1571:     */   public void setIndicadorDiaDescanso24(Boolean indicadorDiaDescanso24)
/* 1572:     */   {
/* 1573:1553 */     this.indicadorDiaDescanso24 = indicadorDiaDescanso24;
/* 1574:     */   }
/* 1575:     */   
/* 1576:     */   public Boolean getIndicadorDiaDescanso25()
/* 1577:     */   {
/* 1578:1557 */     if (this.indicadorDiaDescanso25 == null) {
/* 1579:1558 */       this.indicadorDiaDescanso25 = Boolean.valueOf(false);
/* 1580:     */     }
/* 1581:1560 */     return this.indicadorDiaDescanso25;
/* 1582:     */   }
/* 1583:     */   
/* 1584:     */   public void setIndicadorDiaDescanso25(Boolean indicadorDiaDescanso25)
/* 1585:     */   {
/* 1586:1564 */     this.indicadorDiaDescanso25 = indicadorDiaDescanso25;
/* 1587:     */   }
/* 1588:     */   
/* 1589:     */   public Boolean getIndicadorDiaDescanso26()
/* 1590:     */   {
/* 1591:1568 */     if (this.indicadorDiaDescanso26 == null) {
/* 1592:1569 */       this.indicadorDiaDescanso26 = Boolean.valueOf(false);
/* 1593:     */     }
/* 1594:1571 */     return this.indicadorDiaDescanso26;
/* 1595:     */   }
/* 1596:     */   
/* 1597:     */   public void setIndicadorDiaDescanso26(Boolean indicadorDiaDescanso26)
/* 1598:     */   {
/* 1599:1575 */     this.indicadorDiaDescanso26 = indicadorDiaDescanso26;
/* 1600:     */   }
/* 1601:     */   
/* 1602:     */   public Boolean getIndicadorDiaDescanso27()
/* 1603:     */   {
/* 1604:1579 */     if (this.indicadorDiaDescanso27 == null) {
/* 1605:1580 */       this.indicadorDiaDescanso27 = Boolean.valueOf(false);
/* 1606:     */     }
/* 1607:1582 */     return this.indicadorDiaDescanso27;
/* 1608:     */   }
/* 1609:     */   
/* 1610:     */   public void setIndicadorDiaDescanso27(Boolean indicadorDiaDescanso27)
/* 1611:     */   {
/* 1612:1586 */     this.indicadorDiaDescanso27 = indicadorDiaDescanso27;
/* 1613:     */   }
/* 1614:     */   
/* 1615:     */   public Boolean getIndicadorDiaDescanso28()
/* 1616:     */   {
/* 1617:1590 */     if (this.indicadorDiaDescanso28 == null) {
/* 1618:1591 */       this.indicadorDiaDescanso28 = Boolean.valueOf(false);
/* 1619:     */     }
/* 1620:1593 */     return this.indicadorDiaDescanso28;
/* 1621:     */   }
/* 1622:     */   
/* 1623:     */   public void setIndicadorDiaDescanso28(Boolean indicadorDiaDescanso28)
/* 1624:     */   {
/* 1625:1597 */     this.indicadorDiaDescanso28 = indicadorDiaDescanso28;
/* 1626:     */   }
/* 1627:     */   
/* 1628:     */   public Boolean getIndicadorDiaDescanso29()
/* 1629:     */   {
/* 1630:1601 */     if (this.indicadorDiaDescanso29 == null) {
/* 1631:1602 */       this.indicadorDiaDescanso29 = Boolean.valueOf(false);
/* 1632:     */     }
/* 1633:1604 */     return this.indicadorDiaDescanso29;
/* 1634:     */   }
/* 1635:     */   
/* 1636:     */   public void setIndicadorDiaDescanso29(Boolean indicadorDiaDescanso29)
/* 1637:     */   {
/* 1638:1608 */     this.indicadorDiaDescanso29 = indicadorDiaDescanso29;
/* 1639:     */   }
/* 1640:     */   
/* 1641:     */   public Boolean getIndicadorDiaDescanso30()
/* 1642:     */   {
/* 1643:1612 */     if (this.indicadorDiaDescanso30 == null) {
/* 1644:1613 */       this.indicadorDiaDescanso30 = Boolean.valueOf(false);
/* 1645:     */     }
/* 1646:1615 */     return this.indicadorDiaDescanso30;
/* 1647:     */   }
/* 1648:     */   
/* 1649:     */   public void setIndicadorDiaDescanso30(Boolean indicadorDiaDescanso30)
/* 1650:     */   {
/* 1651:1619 */     this.indicadorDiaDescanso30 = indicadorDiaDescanso30;
/* 1652:     */   }
/* 1653:     */   
/* 1654:     */   public Boolean getIndicadorDiaDescanso31()
/* 1655:     */   {
/* 1656:1623 */     if (this.indicadorDiaDescanso31 == null) {
/* 1657:1624 */       this.indicadorDiaDescanso31 = Boolean.valueOf(false);
/* 1658:     */     }
/* 1659:1626 */     return this.indicadorDiaDescanso31;
/* 1660:     */   }
/* 1661:     */   
/* 1662:     */   public void setIndicadorDiaDescanso31(Boolean indicadorDiaDescanso31)
/* 1663:     */   {
/* 1664:1630 */     this.indicadorDiaDescanso31 = indicadorDiaDescanso31;
/* 1665:     */   }
/* 1666:     */   
/* 1667:     */   public Boolean getIndicadorDiaComplementario1()
/* 1668:     */   {
/* 1669:1634 */     return this.indicadorDiaComplementario1;
/* 1670:     */   }
/* 1671:     */   
/* 1672:     */   public void setIndicadorDiaComplementario1(Boolean indicadorDiaComplementario1)
/* 1673:     */   {
/* 1674:1638 */     this.indicadorDiaComplementario1 = indicadorDiaComplementario1;
/* 1675:     */   }
/* 1676:     */   
/* 1677:     */   public Boolean getIndicadorDiaComplementario2()
/* 1678:     */   {
/* 1679:1642 */     return this.indicadorDiaComplementario2;
/* 1680:     */   }
/* 1681:     */   
/* 1682:     */   public void setIndicadorDiaComplementario2(Boolean indicadorDiaComplementario2)
/* 1683:     */   {
/* 1684:1646 */     this.indicadorDiaComplementario2 = indicadorDiaComplementario2;
/* 1685:     */   }
/* 1686:     */   
/* 1687:     */   public Boolean getIndicadorDiaComplementario3()
/* 1688:     */   {
/* 1689:1650 */     return this.indicadorDiaComplementario3;
/* 1690:     */   }
/* 1691:     */   
/* 1692:     */   public void setIndicadorDiaComplementario3(Boolean indicadorDiaComplementario3)
/* 1693:     */   {
/* 1694:1654 */     this.indicadorDiaComplementario3 = indicadorDiaComplementario3;
/* 1695:     */   }
/* 1696:     */   
/* 1697:     */   public Boolean getIndicadorDiaComplementario4()
/* 1698:     */   {
/* 1699:1658 */     return this.indicadorDiaComplementario4;
/* 1700:     */   }
/* 1701:     */   
/* 1702:     */   public void setIndicadorDiaComplementario4(Boolean indicadorDiaComplementario4)
/* 1703:     */   {
/* 1704:1662 */     this.indicadorDiaComplementario4 = indicadorDiaComplementario4;
/* 1705:     */   }
/* 1706:     */   
/* 1707:     */   public Boolean getIndicadorDiaComplementario5()
/* 1708:     */   {
/* 1709:1666 */     return this.indicadorDiaComplementario5;
/* 1710:     */   }
/* 1711:     */   
/* 1712:     */   public void setIndicadorDiaComplementario5(Boolean indicadorDiaComplementario5)
/* 1713:     */   {
/* 1714:1670 */     this.indicadorDiaComplementario5 = indicadorDiaComplementario5;
/* 1715:     */   }
/* 1716:     */   
/* 1717:     */   public Boolean getIndicadorDiaComplementario6()
/* 1718:     */   {
/* 1719:1674 */     return this.indicadorDiaComplementario6;
/* 1720:     */   }
/* 1721:     */   
/* 1722:     */   public void setIndicadorDiaComplementario6(Boolean indicadorDiaComplementario6)
/* 1723:     */   {
/* 1724:1678 */     this.indicadorDiaComplementario6 = indicadorDiaComplementario6;
/* 1725:     */   }
/* 1726:     */   
/* 1727:     */   public Boolean getIndicadorDiaComplementario7()
/* 1728:     */   {
/* 1729:1682 */     return this.indicadorDiaComplementario7;
/* 1730:     */   }
/* 1731:     */   
/* 1732:     */   public void setIndicadorDiaComplementario7(Boolean indicadorDiaComplementario7)
/* 1733:     */   {
/* 1734:1686 */     this.indicadorDiaComplementario7 = indicadorDiaComplementario7;
/* 1735:     */   }
/* 1736:     */   
/* 1737:     */   public Boolean getIndicadorDiaComplementario8()
/* 1738:     */   {
/* 1739:1690 */     return this.indicadorDiaComplementario8;
/* 1740:     */   }
/* 1741:     */   
/* 1742:     */   public void setIndicadorDiaComplementario8(Boolean indicadorDiaComplementario8)
/* 1743:     */   {
/* 1744:1694 */     this.indicadorDiaComplementario8 = indicadorDiaComplementario8;
/* 1745:     */   }
/* 1746:     */   
/* 1747:     */   public Boolean getIndicadorDiaComplementario9()
/* 1748:     */   {
/* 1749:1698 */     return this.indicadorDiaComplementario9;
/* 1750:     */   }
/* 1751:     */   
/* 1752:     */   public void setIndicadorDiaComplementario9(Boolean indicadorDiaComplementario9)
/* 1753:     */   {
/* 1754:1702 */     this.indicadorDiaComplementario9 = indicadorDiaComplementario9;
/* 1755:     */   }
/* 1756:     */   
/* 1757:     */   public Boolean getIndicadorDiaComplementario10()
/* 1758:     */   {
/* 1759:1706 */     return this.indicadorDiaComplementario10;
/* 1760:     */   }
/* 1761:     */   
/* 1762:     */   public void setIndicadorDiaComplementario10(Boolean indicadorDiaComplementario10)
/* 1763:     */   {
/* 1764:1710 */     this.indicadorDiaComplementario10 = indicadorDiaComplementario10;
/* 1765:     */   }
/* 1766:     */   
/* 1767:     */   public Boolean getIndicadorDiaComplementario11()
/* 1768:     */   {
/* 1769:1714 */     return this.indicadorDiaComplementario11;
/* 1770:     */   }
/* 1771:     */   
/* 1772:     */   public void setIndicadorDiaComplementario11(Boolean indicadorDiaComplementario11)
/* 1773:     */   {
/* 1774:1718 */     this.indicadorDiaComplementario11 = indicadorDiaComplementario11;
/* 1775:     */   }
/* 1776:     */   
/* 1777:     */   public Boolean getIndicadorDiaComplementario12()
/* 1778:     */   {
/* 1779:1722 */     return this.indicadorDiaComplementario12;
/* 1780:     */   }
/* 1781:     */   
/* 1782:     */   public void setIndicadorDiaComplementario12(Boolean indicadorDiaComplementario12)
/* 1783:     */   {
/* 1784:1726 */     this.indicadorDiaComplementario12 = indicadorDiaComplementario12;
/* 1785:     */   }
/* 1786:     */   
/* 1787:     */   public Boolean getIndicadorDiaComplementario13()
/* 1788:     */   {
/* 1789:1730 */     return this.indicadorDiaComplementario13;
/* 1790:     */   }
/* 1791:     */   
/* 1792:     */   public void setIndicadorDiaComplementario13(Boolean indicadorDiaComplementario13)
/* 1793:     */   {
/* 1794:1734 */     this.indicadorDiaComplementario13 = indicadorDiaComplementario13;
/* 1795:     */   }
/* 1796:     */   
/* 1797:     */   public Boolean getIndicadorDiaComplementario14()
/* 1798:     */   {
/* 1799:1738 */     return this.indicadorDiaComplementario14;
/* 1800:     */   }
/* 1801:     */   
/* 1802:     */   public void setIndicadorDiaComplementario14(Boolean indicadorDiaComplementario14)
/* 1803:     */   {
/* 1804:1742 */     this.indicadorDiaComplementario14 = indicadorDiaComplementario14;
/* 1805:     */   }
/* 1806:     */   
/* 1807:     */   public Boolean getIndicadorDiaComplementario15()
/* 1808:     */   {
/* 1809:1746 */     return this.indicadorDiaComplementario15;
/* 1810:     */   }
/* 1811:     */   
/* 1812:     */   public void setIndicadorDiaComplementario15(Boolean indicadorDiaComplementario15)
/* 1813:     */   {
/* 1814:1750 */     this.indicadorDiaComplementario15 = indicadorDiaComplementario15;
/* 1815:     */   }
/* 1816:     */   
/* 1817:     */   public Boolean getIndicadorDiaComplementario16()
/* 1818:     */   {
/* 1819:1754 */     return this.indicadorDiaComplementario16;
/* 1820:     */   }
/* 1821:     */   
/* 1822:     */   public void setIndicadorDiaComplementario16(Boolean indicadorDiaComplementario16)
/* 1823:     */   {
/* 1824:1758 */     this.indicadorDiaComplementario16 = indicadorDiaComplementario16;
/* 1825:     */   }
/* 1826:     */   
/* 1827:     */   public Boolean getIndicadorDiaComplementario17()
/* 1828:     */   {
/* 1829:1762 */     return this.indicadorDiaComplementario17;
/* 1830:     */   }
/* 1831:     */   
/* 1832:     */   public void setIndicadorDiaComplementario17(Boolean indicadorDiaComplementario17)
/* 1833:     */   {
/* 1834:1766 */     this.indicadorDiaComplementario17 = indicadorDiaComplementario17;
/* 1835:     */   }
/* 1836:     */   
/* 1837:     */   public Boolean getIndicadorDiaComplementario18()
/* 1838:     */   {
/* 1839:1770 */     return this.indicadorDiaComplementario18;
/* 1840:     */   }
/* 1841:     */   
/* 1842:     */   public void setIndicadorDiaComplementario18(Boolean indicadorDiaComplementario18)
/* 1843:     */   {
/* 1844:1774 */     this.indicadorDiaComplementario18 = indicadorDiaComplementario18;
/* 1845:     */   }
/* 1846:     */   
/* 1847:     */   public Boolean getIndicadorDiaComplementario19()
/* 1848:     */   {
/* 1849:1778 */     return this.indicadorDiaComplementario19;
/* 1850:     */   }
/* 1851:     */   
/* 1852:     */   public void setIndicadorDiaComplementario19(Boolean indicadorDiaComplementario19)
/* 1853:     */   {
/* 1854:1782 */     this.indicadorDiaComplementario19 = indicadorDiaComplementario19;
/* 1855:     */   }
/* 1856:     */   
/* 1857:     */   public Boolean getIndicadorDiaComplementario20()
/* 1858:     */   {
/* 1859:1786 */     return this.indicadorDiaComplementario20;
/* 1860:     */   }
/* 1861:     */   
/* 1862:     */   public void setIndicadorDiaComplementario20(Boolean indicadorDiaComplementario20)
/* 1863:     */   {
/* 1864:1790 */     this.indicadorDiaComplementario20 = indicadorDiaComplementario20;
/* 1865:     */   }
/* 1866:     */   
/* 1867:     */   public Boolean getIndicadorDiaComplementario21()
/* 1868:     */   {
/* 1869:1794 */     return this.indicadorDiaComplementario21;
/* 1870:     */   }
/* 1871:     */   
/* 1872:     */   public void setIndicadorDiaComplementario21(Boolean indicadorDiaComplementario21)
/* 1873:     */   {
/* 1874:1798 */     this.indicadorDiaComplementario21 = indicadorDiaComplementario21;
/* 1875:     */   }
/* 1876:     */   
/* 1877:     */   public Boolean getIndicadorDiaComplementario22()
/* 1878:     */   {
/* 1879:1802 */     return this.indicadorDiaComplementario22;
/* 1880:     */   }
/* 1881:     */   
/* 1882:     */   public void setIndicadorDiaComplementario22(Boolean indicadorDiaComplementario22)
/* 1883:     */   {
/* 1884:1806 */     this.indicadorDiaComplementario22 = indicadorDiaComplementario22;
/* 1885:     */   }
/* 1886:     */   
/* 1887:     */   public Boolean getIndicadorDiaComplementario23()
/* 1888:     */   {
/* 1889:1810 */     return this.indicadorDiaComplementario23;
/* 1890:     */   }
/* 1891:     */   
/* 1892:     */   public void setIndicadorDiaComplementario23(Boolean indicadorDiaComplementario23)
/* 1893:     */   {
/* 1894:1814 */     this.indicadorDiaComplementario23 = indicadorDiaComplementario23;
/* 1895:     */   }
/* 1896:     */   
/* 1897:     */   public Boolean getIndicadorDiaComplementario24()
/* 1898:     */   {
/* 1899:1818 */     return this.indicadorDiaComplementario24;
/* 1900:     */   }
/* 1901:     */   
/* 1902:     */   public void setIndicadorDiaComplementario24(Boolean indicadorDiaComplementario24)
/* 1903:     */   {
/* 1904:1822 */     this.indicadorDiaComplementario24 = indicadorDiaComplementario24;
/* 1905:     */   }
/* 1906:     */   
/* 1907:     */   public Boolean getIndicadorDiaComplementario25()
/* 1908:     */   {
/* 1909:1826 */     return this.indicadorDiaComplementario25;
/* 1910:     */   }
/* 1911:     */   
/* 1912:     */   public void setIndicadorDiaComplementario25(Boolean indicadorDiaComplementario25)
/* 1913:     */   {
/* 1914:1830 */     this.indicadorDiaComplementario25 = indicadorDiaComplementario25;
/* 1915:     */   }
/* 1916:     */   
/* 1917:     */   public Boolean getIndicadorDiaComplementario26()
/* 1918:     */   {
/* 1919:1834 */     return this.indicadorDiaComplementario26;
/* 1920:     */   }
/* 1921:     */   
/* 1922:     */   public void setIndicadorDiaComplementario26(Boolean indicadorDiaComplementario26)
/* 1923:     */   {
/* 1924:1838 */     this.indicadorDiaComplementario26 = indicadorDiaComplementario26;
/* 1925:     */   }
/* 1926:     */   
/* 1927:     */   public Boolean getIndicadorDiaComplementario27()
/* 1928:     */   {
/* 1929:1842 */     return this.indicadorDiaComplementario27;
/* 1930:     */   }
/* 1931:     */   
/* 1932:     */   public void setIndicadorDiaComplementario27(Boolean indicadorDiaComplementario27)
/* 1933:     */   {
/* 1934:1846 */     this.indicadorDiaComplementario27 = indicadorDiaComplementario27;
/* 1935:     */   }
/* 1936:     */   
/* 1937:     */   public Boolean getIndicadorDiaComplementario28()
/* 1938:     */   {
/* 1939:1850 */     return this.indicadorDiaComplementario28;
/* 1940:     */   }
/* 1941:     */   
/* 1942:     */   public void setIndicadorDiaComplementario28(Boolean indicadorDiaComplementario28)
/* 1943:     */   {
/* 1944:1854 */     this.indicadorDiaComplementario28 = indicadorDiaComplementario28;
/* 1945:     */   }
/* 1946:     */   
/* 1947:     */   public Boolean getIndicadorDiaComplementario29()
/* 1948:     */   {
/* 1949:1858 */     return this.indicadorDiaComplementario29;
/* 1950:     */   }
/* 1951:     */   
/* 1952:     */   public void setIndicadorDiaComplementario29(Boolean indicadorDiaComplementario29)
/* 1953:     */   {
/* 1954:1862 */     this.indicadorDiaComplementario29 = indicadorDiaComplementario29;
/* 1955:     */   }
/* 1956:     */   
/* 1957:     */   public Boolean getIndicadorDiaComplementario30()
/* 1958:     */   {
/* 1959:1866 */     return this.indicadorDiaComplementario30;
/* 1960:     */   }
/* 1961:     */   
/* 1962:     */   public void setIndicadorDiaComplementario30(Boolean indicadorDiaComplementario30)
/* 1963:     */   {
/* 1964:1870 */     this.indicadorDiaComplementario30 = indicadorDiaComplementario30;
/* 1965:     */   }
/* 1966:     */   
/* 1967:     */   public Boolean getIndicadorDiaComplementario31()
/* 1968:     */   {
/* 1969:1874 */     return this.indicadorDiaComplementario31;
/* 1970:     */   }
/* 1971:     */   
/* 1972:     */   public void setIndicadorDiaComplementario31(Boolean indicadorDiaComplementario31)
/* 1973:     */   {
/* 1974:1878 */     this.indicadorDiaComplementario31 = indicadorDiaComplementario31;
/* 1975:     */   }
/* 1976:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.nomina.asistencia.DetallePlanPersonalizadoHorarioEmpleado
 * JD-Core Version:    0.7.0.1
 */