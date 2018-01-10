/*    1:     */ package com.asinfo.as2.entities.presupuesto;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.entities.CuentaContable;
/*    4:     */ import com.asinfo.as2.entities.DimensionContable;
/*    5:     */ import com.asinfo.as2.entities.EntidadBase;
/*    6:     */ import java.math.BigDecimal;
/*    7:     */ import java.util.ArrayList;
/*    8:     */ import java.util.List;
/*    9:     */ import javax.persistence.Column;
/*   10:     */ import javax.persistence.Entity;
/*   11:     */ import javax.persistence.FetchType;
/*   12:     */ import javax.persistence.GeneratedValue;
/*   13:     */ import javax.persistence.GenerationType;
/*   14:     */ import javax.persistence.Id;
/*   15:     */ import javax.persistence.JoinColumn;
/*   16:     */ import javax.persistence.ManyToOne;
/*   17:     */ import javax.persistence.OneToMany;
/*   18:     */ import javax.persistence.OneToOne;
/*   19:     */ import javax.persistence.Table;
/*   20:     */ import javax.persistence.TableGenerator;
/*   21:     */ import javax.validation.constraints.Digits;
/*   22:     */ import javax.validation.constraints.Min;
/*   23:     */ import javax.validation.constraints.Size;
/*   24:     */ import org.hibernate.annotations.Fetch;
/*   25:     */ import org.hibernate.annotations.FetchMode;
/*   26:     */ 
/*   27:     */ @Entity
/*   28:     */ @Table(name="detalle_presupuesto")
/*   29:     */ public class DetallePresupuesto
/*   30:     */   extends EntidadBase
/*   31:     */ {
/*   32:     */   private static final long serialVersionUID = 1L;
/*   33:     */   @Id
/*   34:     */   @TableGenerator(name="detalle_presupuesto", initialValue=0, allocationSize=50)
/*   35:     */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_presupuesto")
/*   36:     */   @Column(name="id_detalle_presupuesto")
/*   37:     */   private int idDetallePresupuesto;
/*   38:     */   @Column(name="id_sucursal", nullable=false)
/*   39:     */   private int idSucursal;
/*   40:     */   @Column(name="id_organizacion", nullable=false)
/*   41:     */   private int idOrganizacion;
/*   42:     */   @Column(name="valor_enero", precision=12, scale=2)
/*   43:     */   @Digits(integer=12, fraction=2)
/*   44:     */   @Min(0L)
/*   45:  54 */   private BigDecimal valorEnero = BigDecimal.ZERO;
/*   46:     */   @Column(name="transferencias_ingreso_enero", precision=12, scale=2)
/*   47:     */   @Digits(integer=12, fraction=2)
/*   48:     */   @Min(0L)
/*   49:  58 */   private BigDecimal transferenciasIngresoEnero = BigDecimal.ZERO;
/*   50:     */   @Column(name="transferencias_egreso_enero", precision=12, scale=2)
/*   51:     */   @Digits(integer=12, fraction=2)
/*   52:     */   @Min(0L)
/*   53:  62 */   private BigDecimal transferenciasEgresoEnero = BigDecimal.ZERO;
/*   54:     */   @Column(name="incrementos_enero", precision=12, scale=2)
/*   55:     */   @Digits(integer=12, fraction=2)
/*   56:     */   @Min(0L)
/*   57:  66 */   private BigDecimal incrementosEnero = BigDecimal.ZERO;
/*   58:     */   @Column(name="decrementos_enero", precision=12, scale=2)
/*   59:     */   @Digits(integer=12, fraction=2)
/*   60:     */   @Min(0L)
/*   61:  70 */   private BigDecimal decrementosEnero = BigDecimal.ZERO;
/*   62:     */   @Column(name="saldo_comprometido_enero", precision=12, scale=2)
/*   63:     */   @Digits(integer=12, fraction=2)
/*   64:     */   @Min(0L)
/*   65:  74 */   private BigDecimal saldoComprometidoEnero = BigDecimal.ZERO;
/*   66:     */   @Column(name="valor_febrero", precision=12, scale=2)
/*   67:     */   @Digits(integer=12, fraction=2)
/*   68:     */   @Min(0L)
/*   69:  79 */   private BigDecimal valorFebrero = BigDecimal.ZERO;
/*   70:     */   @Column(name="transferencias_ingreso_febrero", precision=12, scale=2)
/*   71:     */   @Digits(integer=12, fraction=2)
/*   72:     */   @Min(0L)
/*   73:  83 */   private BigDecimal transferenciasIngresoFebrero = BigDecimal.ZERO;
/*   74:     */   @Column(name="transferencias_egreso_febrero", precision=12, scale=2)
/*   75:     */   @Digits(integer=12, fraction=2)
/*   76:     */   @Min(0L)
/*   77:  87 */   private BigDecimal transferenciasEgresoFebrero = BigDecimal.ZERO;
/*   78:     */   @Column(name="incrementos_febrero", precision=12, scale=2)
/*   79:     */   @Digits(integer=12, fraction=2)
/*   80:     */   @Min(0L)
/*   81:  91 */   private BigDecimal incrementosFebrero = BigDecimal.ZERO;
/*   82:     */   @Column(name="decrementos_febrero", precision=12, scale=2)
/*   83:     */   @Digits(integer=12, fraction=2)
/*   84:     */   @Min(0L)
/*   85:  95 */   private BigDecimal decrementosFebrero = BigDecimal.ZERO;
/*   86:     */   @Column(name="saldo_comprometido_febrero", precision=12, scale=2)
/*   87:     */   @Digits(integer=12, fraction=2)
/*   88:     */   @Min(0L)
/*   89:  99 */   private BigDecimal saldoComprometidoFebrero = BigDecimal.ZERO;
/*   90:     */   @Column(name="valor_marzo", precision=12, scale=2)
/*   91:     */   @Digits(integer=12, fraction=2)
/*   92:     */   @Min(0L)
/*   93: 104 */   private BigDecimal valorMarzo = BigDecimal.ZERO;
/*   94:     */   @Column(name="transferencias_ingreso_marzo", precision=12, scale=2)
/*   95:     */   @Digits(integer=12, fraction=2)
/*   96:     */   @Min(0L)
/*   97: 108 */   private BigDecimal transferenciasIngresoMarzo = BigDecimal.ZERO;
/*   98:     */   @Column(name="transferencias_egreso_marzo", precision=12, scale=2)
/*   99:     */   @Digits(integer=12, fraction=2)
/*  100:     */   @Min(0L)
/*  101: 112 */   private BigDecimal transferenciasEgresoMarzo = BigDecimal.ZERO;
/*  102:     */   @Column(name="incrementos_marzo", precision=12, scale=2)
/*  103:     */   @Digits(integer=12, fraction=2)
/*  104:     */   @Min(0L)
/*  105: 116 */   private BigDecimal incrementosMarzo = BigDecimal.ZERO;
/*  106:     */   @Column(name="decrementos_marzo", precision=12, scale=2)
/*  107:     */   @Digits(integer=12, fraction=2)
/*  108:     */   @Min(0L)
/*  109: 120 */   private BigDecimal decrementosMarzo = BigDecimal.ZERO;
/*  110:     */   @Column(name="saldo_comprometido_marzo", precision=12, scale=2)
/*  111:     */   @Digits(integer=12, fraction=2)
/*  112:     */   @Min(0L)
/*  113: 124 */   private BigDecimal saldoComprometidoMarzo = BigDecimal.ZERO;
/*  114:     */   @Column(name="valor_abril", precision=12, scale=2)
/*  115:     */   @Digits(integer=12, fraction=2)
/*  116:     */   @Min(0L)
/*  117: 129 */   private BigDecimal valorAbril = BigDecimal.ZERO;
/*  118:     */   @Column(name="transferencias_ingreso_abril", precision=12, scale=2)
/*  119:     */   @Digits(integer=12, fraction=2)
/*  120:     */   @Min(0L)
/*  121: 133 */   private BigDecimal transferenciasIngresoAbril = BigDecimal.ZERO;
/*  122:     */   @Column(name="transferencias_egreso_abril", precision=12, scale=2)
/*  123:     */   @Digits(integer=12, fraction=2)
/*  124:     */   @Min(0L)
/*  125: 137 */   private BigDecimal transferenciasEgresoAbril = BigDecimal.ZERO;
/*  126:     */   @Column(name="incrementos_abril", precision=12, scale=2)
/*  127:     */   @Digits(integer=12, fraction=2)
/*  128:     */   @Min(0L)
/*  129: 141 */   private BigDecimal incrementosAbril = BigDecimal.ZERO;
/*  130:     */   @Column(name="decrementos_abril", precision=12, scale=2)
/*  131:     */   @Digits(integer=12, fraction=2)
/*  132:     */   @Min(0L)
/*  133: 145 */   private BigDecimal decrementosAbril = BigDecimal.ZERO;
/*  134:     */   @Column(name="saldo_comprometido_abril", precision=12, scale=2)
/*  135:     */   @Digits(integer=12, fraction=2)
/*  136:     */   @Min(0L)
/*  137: 149 */   private BigDecimal saldoComprometidoAbril = BigDecimal.ZERO;
/*  138:     */   @Column(name="valor_mayo", precision=12, scale=2)
/*  139:     */   @Digits(integer=12, fraction=2)
/*  140:     */   @Min(0L)
/*  141: 154 */   private BigDecimal valorMayo = BigDecimal.ZERO;
/*  142:     */   @Column(name="transferencias_ingreso_mayo", precision=12, scale=2)
/*  143:     */   @Digits(integer=12, fraction=2)
/*  144:     */   @Min(0L)
/*  145: 158 */   private BigDecimal transferenciasIngresoMayo = BigDecimal.ZERO;
/*  146:     */   @Column(name="transferencias_egreso_mayo", precision=12, scale=2)
/*  147:     */   @Digits(integer=12, fraction=2)
/*  148:     */   @Min(0L)
/*  149: 162 */   private BigDecimal transferenciasEgresoMayo = BigDecimal.ZERO;
/*  150:     */   @Column(name="incrementos_mayo", precision=12, scale=2)
/*  151:     */   @Digits(integer=12, fraction=2)
/*  152:     */   @Min(0L)
/*  153: 166 */   private BigDecimal incrementosMayo = BigDecimal.ZERO;
/*  154:     */   @Column(name="decrementos_mayo", precision=12, scale=2)
/*  155:     */   @Digits(integer=12, fraction=2)
/*  156:     */   @Min(0L)
/*  157: 170 */   private BigDecimal decrementosMayo = BigDecimal.ZERO;
/*  158:     */   @Column(name="saldo_comprometido_mayo", precision=12, scale=2)
/*  159:     */   @Digits(integer=12, fraction=2)
/*  160:     */   @Min(0L)
/*  161: 174 */   private BigDecimal saldoComprometidoMayo = BigDecimal.ZERO;
/*  162:     */   @Column(name="valor_junio", precision=12, scale=2)
/*  163:     */   @Digits(integer=12, fraction=2)
/*  164:     */   @Min(0L)
/*  165: 179 */   private BigDecimal valorJunio = BigDecimal.ZERO;
/*  166:     */   @Column(name="transferencias_ingreso_junio", precision=12, scale=2)
/*  167:     */   @Digits(integer=12, fraction=2)
/*  168:     */   @Min(0L)
/*  169: 183 */   private BigDecimal transferenciasIngresoJunio = BigDecimal.ZERO;
/*  170:     */   @Column(name="transferencias_egreso_junio", precision=12, scale=2)
/*  171:     */   @Digits(integer=12, fraction=2)
/*  172:     */   @Min(0L)
/*  173: 187 */   private BigDecimal transferenciasEgresoJunio = BigDecimal.ZERO;
/*  174:     */   @Column(name="incrementos_junio", precision=12, scale=2)
/*  175:     */   @Digits(integer=12, fraction=2)
/*  176:     */   @Min(0L)
/*  177: 191 */   private BigDecimal incrementosJunio = BigDecimal.ZERO;
/*  178:     */   @Column(name="decrementos_junio", precision=12, scale=2)
/*  179:     */   @Digits(integer=12, fraction=2)
/*  180:     */   @Min(0L)
/*  181: 195 */   private BigDecimal decrementosJunio = BigDecimal.ZERO;
/*  182:     */   @Column(name="saldo_comprometido_junio", precision=12, scale=2)
/*  183:     */   @Digits(integer=12, fraction=2)
/*  184:     */   @Min(0L)
/*  185: 199 */   private BigDecimal saldoComprometidoJunio = BigDecimal.ZERO;
/*  186:     */   @Column(name="valor_julio", precision=12, scale=2)
/*  187:     */   @Digits(integer=12, fraction=2)
/*  188:     */   @Min(0L)
/*  189: 204 */   private BigDecimal valorJulio = BigDecimal.ZERO;
/*  190:     */   @Column(name="transferencias_ingreso_julio", precision=12, scale=2)
/*  191:     */   @Digits(integer=12, fraction=2)
/*  192:     */   @Min(0L)
/*  193: 208 */   private BigDecimal transferenciasIngresoJulio = BigDecimal.ZERO;
/*  194:     */   @Column(name="transferencias_egreso_julio", precision=12, scale=2)
/*  195:     */   @Digits(integer=12, fraction=2)
/*  196:     */   @Min(0L)
/*  197: 212 */   private BigDecimal transferenciasEgresoJulio = BigDecimal.ZERO;
/*  198:     */   @Column(name="incrementos_julio", precision=12, scale=2)
/*  199:     */   @Digits(integer=12, fraction=2)
/*  200:     */   @Min(0L)
/*  201: 216 */   private BigDecimal incrementosJulio = BigDecimal.ZERO;
/*  202:     */   @Column(name="decrementos_julio", precision=12, scale=2)
/*  203:     */   @Digits(integer=12, fraction=2)
/*  204:     */   @Min(0L)
/*  205: 220 */   private BigDecimal decrementosJulio = BigDecimal.ZERO;
/*  206:     */   @Column(name="saldo_comprometido_julio", precision=12, scale=2)
/*  207:     */   @Digits(integer=12, fraction=2)
/*  208:     */   @Min(0L)
/*  209: 224 */   private BigDecimal saldoComprometidoJulio = BigDecimal.ZERO;
/*  210:     */   @Column(name="valor_agosto", precision=12, scale=2)
/*  211:     */   @Digits(integer=12, fraction=2)
/*  212:     */   @Min(0L)
/*  213: 229 */   private BigDecimal valorAgosto = BigDecimal.ZERO;
/*  214:     */   @Column(name="transferencias_ingreso_agosto", precision=12, scale=2)
/*  215:     */   @Digits(integer=12, fraction=2)
/*  216:     */   @Min(0L)
/*  217: 233 */   private BigDecimal transferenciasIngresoAgosto = BigDecimal.ZERO;
/*  218:     */   @Column(name="transferencias_egreso_agosto", precision=12, scale=2)
/*  219:     */   @Digits(integer=12, fraction=2)
/*  220:     */   @Min(0L)
/*  221: 237 */   private BigDecimal transferenciasEgresoAgosto = BigDecimal.ZERO;
/*  222:     */   @Column(name="incrementos_agosto", precision=12, scale=2)
/*  223:     */   @Digits(integer=12, fraction=2)
/*  224:     */   @Min(0L)
/*  225: 241 */   private BigDecimal incrementosAgosto = BigDecimal.ZERO;
/*  226:     */   @Column(name="decrementos_agosto", precision=12, scale=2)
/*  227:     */   @Digits(integer=12, fraction=2)
/*  228:     */   @Min(0L)
/*  229: 245 */   private BigDecimal decrementosAgosto = BigDecimal.ZERO;
/*  230:     */   @Column(name="saldo_comprometido_agosto", precision=12, scale=2)
/*  231:     */   @Digits(integer=12, fraction=2)
/*  232:     */   @Min(0L)
/*  233: 249 */   private BigDecimal saldoComprometidoAgosto = BigDecimal.ZERO;
/*  234:     */   @Column(name="valor_septiembre", precision=12, scale=2)
/*  235:     */   @Digits(integer=12, fraction=2)
/*  236:     */   @Min(0L)
/*  237: 254 */   private BigDecimal valorSeptiembre = BigDecimal.ZERO;
/*  238:     */   @Column(name="transferencias_ingreso_septiembre", precision=12, scale=2)
/*  239:     */   @Digits(integer=12, fraction=2)
/*  240:     */   @Min(0L)
/*  241: 258 */   private BigDecimal transferenciasIngresoSeptiembre = BigDecimal.ZERO;
/*  242:     */   @Column(name="transferencias_egreso_septiembre", precision=12, scale=2)
/*  243:     */   @Digits(integer=12, fraction=2)
/*  244:     */   @Min(0L)
/*  245: 262 */   private BigDecimal transferenciasEgresoSeptiembre = BigDecimal.ZERO;
/*  246:     */   @Column(name="incrementos_septiembre", precision=12, scale=2)
/*  247:     */   @Digits(integer=12, fraction=2)
/*  248:     */   @Min(0L)
/*  249: 266 */   private BigDecimal incrementosSeptiembre = BigDecimal.ZERO;
/*  250:     */   @Column(name="decrementos_septiembre", precision=12, scale=2)
/*  251:     */   @Digits(integer=12, fraction=2)
/*  252:     */   @Min(0L)
/*  253: 270 */   private BigDecimal decrementosSeptiembre = BigDecimal.ZERO;
/*  254:     */   @Column(name="saldo_comprometido_septiembre", precision=12, scale=2)
/*  255:     */   @Digits(integer=12, fraction=2)
/*  256:     */   @Min(0L)
/*  257: 274 */   private BigDecimal saldoComprometidoSeptiembre = BigDecimal.ZERO;
/*  258:     */   @Column(name="valor_octubre", precision=12, scale=2)
/*  259:     */   @Digits(integer=12, fraction=2)
/*  260:     */   @Min(0L)
/*  261: 279 */   private BigDecimal valorOctubre = BigDecimal.ZERO;
/*  262:     */   @Column(name="transferencias_ingreso_octubre", precision=12, scale=2)
/*  263:     */   @Digits(integer=12, fraction=2)
/*  264:     */   @Min(0L)
/*  265: 283 */   private BigDecimal transferenciasIngresoOctubre = BigDecimal.ZERO;
/*  266:     */   @Column(name="transferencias_egreso_octubre", precision=12, scale=2)
/*  267:     */   @Digits(integer=12, fraction=2)
/*  268:     */   @Min(0L)
/*  269: 287 */   private BigDecimal transferenciasEgresoOctubre = BigDecimal.ZERO;
/*  270:     */   @Column(name="incrementos_octubre", precision=12, scale=2)
/*  271:     */   @Digits(integer=12, fraction=2)
/*  272:     */   @Min(0L)
/*  273: 291 */   private BigDecimal incrementosOctubre = BigDecimal.ZERO;
/*  274:     */   @Column(name="decrementos_octubre", precision=12, scale=2)
/*  275:     */   @Digits(integer=12, fraction=2)
/*  276:     */   @Min(0L)
/*  277: 295 */   private BigDecimal decrementosOctubre = BigDecimal.ZERO;
/*  278:     */   @Column(name="saldo_comprometido_octubre", precision=12, scale=2)
/*  279:     */   @Digits(integer=12, fraction=2)
/*  280:     */   @Min(0L)
/*  281: 299 */   private BigDecimal saldoComprometidoOctubre = BigDecimal.ZERO;
/*  282:     */   @Column(name="valor_noviembre", precision=12, scale=2)
/*  283:     */   @Digits(integer=12, fraction=2)
/*  284:     */   @Min(0L)
/*  285: 304 */   private BigDecimal valorNoviembre = BigDecimal.ZERO;
/*  286:     */   @Column(name="transferencias_ingreso_noviembre", precision=12, scale=2)
/*  287:     */   @Digits(integer=12, fraction=2)
/*  288:     */   @Min(0L)
/*  289: 308 */   private BigDecimal transferenciasIngresoNoviembre = BigDecimal.ZERO;
/*  290:     */   @Column(name="transferencias_egreso_noviembre", precision=12, scale=2)
/*  291:     */   @Digits(integer=12, fraction=2)
/*  292:     */   @Min(0L)
/*  293: 312 */   private BigDecimal transferenciasEgresoNoviembre = BigDecimal.ZERO;
/*  294:     */   @Column(name="incrementos_noviembre", precision=12, scale=2)
/*  295:     */   @Digits(integer=12, fraction=2)
/*  296:     */   @Min(0L)
/*  297: 316 */   private BigDecimal incrementosNoviembre = BigDecimal.ZERO;
/*  298:     */   @Column(name="decrementos_noviembre", precision=12, scale=2)
/*  299:     */   @Digits(integer=12, fraction=2)
/*  300:     */   @Min(0L)
/*  301: 320 */   private BigDecimal decrementosNoviembre = BigDecimal.ZERO;
/*  302:     */   @Column(name="saldo_comprometido_noviembre", precision=12, scale=2)
/*  303:     */   @Digits(integer=12, fraction=2)
/*  304:     */   @Min(0L)
/*  305: 324 */   private BigDecimal saldoComprometidoNoviembre = BigDecimal.ZERO;
/*  306:     */   @Column(name="valor_diciembre", precision=12, scale=2)
/*  307:     */   @Digits(integer=12, fraction=2)
/*  308:     */   @Min(0L)
/*  309: 329 */   private BigDecimal valorDiciembre = BigDecimal.ZERO;
/*  310:     */   @Column(name="transferencias_ingreso_diciembre", precision=12, scale=2)
/*  311:     */   @Digits(integer=12, fraction=2)
/*  312:     */   @Min(0L)
/*  313: 333 */   private BigDecimal transferenciasIngresoDiciembre = BigDecimal.ZERO;
/*  314:     */   @Column(name="transferencias_egreso_diciembre", precision=12, scale=2)
/*  315:     */   @Digits(integer=12, fraction=2)
/*  316:     */   @Min(0L)
/*  317: 337 */   private BigDecimal transferenciasEgresoDiciembre = BigDecimal.ZERO;
/*  318:     */   @Column(name="incrementos_diciembre", precision=12, scale=2)
/*  319:     */   @Digits(integer=12, fraction=2)
/*  320:     */   @Min(0L)
/*  321: 341 */   private BigDecimal incrementosDiciembre = BigDecimal.ZERO;
/*  322:     */   @Column(name="decrementos_diciembre", precision=12, scale=2)
/*  323:     */   @Digits(integer=12, fraction=2)
/*  324:     */   @Min(0L)
/*  325: 345 */   private BigDecimal decrementosDiciembre = BigDecimal.ZERO;
/*  326:     */   @Column(name="saldo_comprometido_diciembre", precision=12, scale=2)
/*  327:     */   @Digits(integer=12, fraction=2)
/*  328:     */   @Min(0L)
/*  329: 349 */   private BigDecimal saldoComprometidoDiciembre = BigDecimal.ZERO;
/*  330:     */   @Column(name="saldo_real_enero", precision=12, scale=2)
/*  331:     */   @Digits(integer=12, fraction=2)
/*  332:     */   @Min(0L)
/*  333: 354 */   private BigDecimal saldoRealEnero = BigDecimal.ZERO;
/*  334:     */   @Column(name="saldo_real_febrero", precision=12, scale=2)
/*  335:     */   @Digits(integer=12, fraction=2)
/*  336:     */   @Min(0L)
/*  337: 358 */   private BigDecimal saldoRealFebrero = BigDecimal.ZERO;
/*  338:     */   @Column(name="saldo_real_marzo", precision=12, scale=2)
/*  339:     */   @Digits(integer=12, fraction=2)
/*  340:     */   @Min(0L)
/*  341: 362 */   private BigDecimal saldoRealMarzo = BigDecimal.ZERO;
/*  342:     */   @Column(name="saldo_real_abril", precision=12, scale=2)
/*  343:     */   @Digits(integer=12, fraction=2)
/*  344:     */   @Min(0L)
/*  345: 366 */   private BigDecimal saldoRealAbril = BigDecimal.ZERO;
/*  346:     */   @Column(name="saldo_real_mayo", precision=12, scale=2)
/*  347:     */   @Digits(integer=12, fraction=2)
/*  348:     */   @Min(0L)
/*  349: 370 */   private BigDecimal saldoRealMayo = BigDecimal.ZERO;
/*  350:     */   @Column(name="saldo_real_junio", precision=12, scale=2)
/*  351:     */   @Digits(integer=12, fraction=2)
/*  352:     */   @Min(0L)
/*  353: 374 */   private BigDecimal saldoRealJunio = BigDecimal.ZERO;
/*  354:     */   @Column(name="saldo_real_julio", precision=12, scale=2)
/*  355:     */   @Digits(integer=12, fraction=2)
/*  356:     */   @Min(0L)
/*  357: 378 */   private BigDecimal saldoRealJulio = BigDecimal.ZERO;
/*  358:     */   @Column(name="saldo_real_agosto", precision=12, scale=2)
/*  359:     */   @Digits(integer=12, fraction=2)
/*  360:     */   @Min(0L)
/*  361: 382 */   private BigDecimal saldoRealAgosto = BigDecimal.ZERO;
/*  362:     */   @Column(name="saldo_real_septiembre", precision=12, scale=2)
/*  363:     */   @Digits(integer=12, fraction=2)
/*  364:     */   @Min(0L)
/*  365: 386 */   private BigDecimal saldoRealSeptiembre = BigDecimal.ZERO;
/*  366:     */   @Column(name="saldo_real_octubre", precision=12, scale=2)
/*  367:     */   @Digits(integer=12, fraction=2)
/*  368:     */   @Min(0L)
/*  369: 390 */   private BigDecimal saldoRealOctubre = BigDecimal.ZERO;
/*  370:     */   @Column(name="saldo_real_noviembre", precision=12, scale=2)
/*  371:     */   @Digits(integer=12, fraction=2)
/*  372:     */   @Min(0L)
/*  373: 394 */   private BigDecimal saldoRealNoviembre = BigDecimal.ZERO;
/*  374:     */   @Column(name="saldo_real_diciembre", precision=12, scale=2)
/*  375:     */   @Digits(integer=12, fraction=2)
/*  376:     */   @Min(0L)
/*  377: 398 */   private BigDecimal saldoRealDiciembre = BigDecimal.ZERO;
/*  378:     */   @Column(name="descripcion", nullable=true, length=200)
/*  379:     */   @Size(max=200)
/*  380:     */   private String descripcion;
/*  381:     */   @Column(name="indicador_validar_distribucion", nullable=false)
/*  382:     */   private boolean indicadorValidarDistribucion;
/*  383:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  384:     */   @JoinColumn(name="id_presupuesto", nullable=true)
/*  385:     */   private Presupuesto presupuesto;
/*  386:     */   @ManyToOne(fetch=FetchType.LAZY)
/*  387:     */   @JoinColumn(name="id_partida_presupuestaria", nullable=true)
/*  388:     */   private PartidaPresupuestaria partidaPresupuestaria;
/*  389:     */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detallePresupuesto")
/*  390: 422 */   private List<DetallePresupuestoCentroCosto> listaDetallePresupuestoCentroCosto = new ArrayList();
/*  391:     */   @OneToOne
/*  392:     */   @JoinColumn(name="id_cuenta_contable", nullable=false)
/*  393:     */   @Fetch(FetchMode.JOIN)
/*  394:     */   private CuentaContable cuentaContable;
/*  395:     */   @OneToOne
/*  396:     */   @JoinColumn(name="id_dimension_contable", nullable=false)
/*  397:     */   @Fetch(FetchMode.JOIN)
/*  398:     */   private DimensionContable dimensionContable;
/*  399:     */   
/*  400:     */   public BigDecimal getValorCalculadoEnero()
/*  401:     */   {
/*  402: 445 */     return this.valorEnero.add(this.incrementosEnero).add(this.transferenciasIngresoEnero).subtract(this.decrementosEnero).subtract(this.transferenciasEgresoEnero);
/*  403:     */   }
/*  404:     */   
/*  405:     */   public BigDecimal getValorCalculadoFebrero()
/*  406:     */   {
/*  407: 449 */     return this.valorFebrero.add(this.incrementosFebrero).add(this.transferenciasIngresoFebrero).subtract(this.decrementosFebrero).subtract(this.transferenciasEgresoFebrero);
/*  408:     */   }
/*  409:     */   
/*  410:     */   public BigDecimal getValorCalculadoMarzo()
/*  411:     */   {
/*  412: 453 */     return this.valorMarzo.add(this.incrementosMarzo).add(this.transferenciasIngresoMarzo).subtract(this.decrementosMarzo).subtract(this.transferenciasEgresoMarzo);
/*  413:     */   }
/*  414:     */   
/*  415:     */   public BigDecimal getValorCalculadoAbril()
/*  416:     */   {
/*  417: 457 */     return this.valorAbril.add(this.incrementosAbril).add(this.transferenciasIngresoAbril).subtract(this.decrementosAbril).subtract(this.transferenciasEgresoAbril);
/*  418:     */   }
/*  419:     */   
/*  420:     */   public BigDecimal getValorCalculadoMayo()
/*  421:     */   {
/*  422: 461 */     return this.valorMayo.add(this.incrementosMayo).add(this.transferenciasIngresoMayo).subtract(this.decrementosMayo).subtract(this.transferenciasEgresoMayo);
/*  423:     */   }
/*  424:     */   
/*  425:     */   public BigDecimal getValorCalculadoJunio()
/*  426:     */   {
/*  427: 465 */     return this.valorJunio.add(this.incrementosJunio).add(this.transferenciasIngresoJunio).subtract(this.decrementosJunio).subtract(this.transferenciasEgresoJunio);
/*  428:     */   }
/*  429:     */   
/*  430:     */   public BigDecimal getValorCalculadoJulio()
/*  431:     */   {
/*  432: 469 */     return this.valorJulio.add(this.incrementosJulio).add(this.transferenciasIngresoJulio).subtract(this.decrementosJulio).subtract(this.transferenciasEgresoJulio);
/*  433:     */   }
/*  434:     */   
/*  435:     */   public BigDecimal getValorCalculadoAgosto()
/*  436:     */   {
/*  437: 473 */     return this.valorAgosto.add(this.incrementosAgosto).add(this.transferenciasIngresoAgosto).subtract(this.decrementosAgosto).subtract(this.transferenciasEgresoAgosto);
/*  438:     */   }
/*  439:     */   
/*  440:     */   public BigDecimal getValorCalculadoSeptiembre()
/*  441:     */   {
/*  442: 477 */     return this.valorSeptiembre.add(this.incrementosSeptiembre).add(this.transferenciasIngresoSeptiembre).subtract(this.decrementosSeptiembre).subtract(this.transferenciasEgresoSeptiembre);
/*  443:     */   }
/*  444:     */   
/*  445:     */   public BigDecimal getValorCalculadoOctubre()
/*  446:     */   {
/*  447: 481 */     return this.valorOctubre.add(this.incrementosOctubre).add(this.transferenciasIngresoOctubre).subtract(this.decrementosOctubre).subtract(this.transferenciasEgresoOctubre);
/*  448:     */   }
/*  449:     */   
/*  450:     */   public BigDecimal getValorCalculadoNoviembre()
/*  451:     */   {
/*  452: 485 */     return this.valorNoviembre.add(this.incrementosNoviembre).add(this.transferenciasIngresoNoviembre).subtract(this.decrementosNoviembre).subtract(this.transferenciasEgresoNoviembre);
/*  453:     */   }
/*  454:     */   
/*  455:     */   public BigDecimal getValorCalculadoDiciembre()
/*  456:     */   {
/*  457: 489 */     return this.valorDiciembre.add(this.incrementosDiciembre).add(this.transferenciasIngresoDiciembre).subtract(this.decrementosDiciembre).subtract(this.transferenciasEgresoDiciembre);
/*  458:     */   }
/*  459:     */   
/*  460:     */   public int getIdDetallePresupuesto()
/*  461:     */   {
/*  462: 506 */     return this.idDetallePresupuesto;
/*  463:     */   }
/*  464:     */   
/*  465:     */   public void setIdDetallePresupuesto(int idDetallePresupuesto)
/*  466:     */   {
/*  467: 516 */     this.idDetallePresupuesto = idDetallePresupuesto;
/*  468:     */   }
/*  469:     */   
/*  470:     */   public int getIdSucursal()
/*  471:     */   {
/*  472: 525 */     return this.idSucursal;
/*  473:     */   }
/*  474:     */   
/*  475:     */   public void setIdSucursal(int idSucursal)
/*  476:     */   {
/*  477: 535 */     this.idSucursal = idSucursal;
/*  478:     */   }
/*  479:     */   
/*  480:     */   public int getIdOrganizacion()
/*  481:     */   {
/*  482: 544 */     return this.idOrganizacion;
/*  483:     */   }
/*  484:     */   
/*  485:     */   public void setIdOrganizacion(int idOrganizacion)
/*  486:     */   {
/*  487: 554 */     this.idOrganizacion = idOrganizacion;
/*  488:     */   }
/*  489:     */   
/*  490:     */   public Presupuesto getPresupuesto()
/*  491:     */   {
/*  492: 563 */     return this.presupuesto;
/*  493:     */   }
/*  494:     */   
/*  495:     */   public void setPresupuesto(Presupuesto presupuesto)
/*  496:     */   {
/*  497: 573 */     this.presupuesto = presupuesto;
/*  498:     */   }
/*  499:     */   
/*  500:     */   public PartidaPresupuestaria getPartidaPresupuestaria()
/*  501:     */   {
/*  502: 582 */     return this.partidaPresupuestaria;
/*  503:     */   }
/*  504:     */   
/*  505:     */   public void setPartidaPresupuestaria(PartidaPresupuestaria partidaPresupuestaria)
/*  506:     */   {
/*  507: 592 */     this.partidaPresupuestaria = partidaPresupuestaria;
/*  508:     */   }
/*  509:     */   
/*  510:     */   public List<DetallePresupuestoCentroCosto> getListaDetallePresupuestoCentroCosto()
/*  511:     */   {
/*  512: 601 */     return this.listaDetallePresupuestoCentroCosto;
/*  513:     */   }
/*  514:     */   
/*  515:     */   public void setListaDetallePresupuestoCentroCosto(List<DetallePresupuestoCentroCosto> listaDetallePresupuestoCentroCosto)
/*  516:     */   {
/*  517: 611 */     this.listaDetallePresupuestoCentroCosto = listaDetallePresupuestoCentroCosto;
/*  518:     */   }
/*  519:     */   
/*  520:     */   public String getDescripcion()
/*  521:     */   {
/*  522: 620 */     return this.descripcion;
/*  523:     */   }
/*  524:     */   
/*  525:     */   public void setDescripcion(String descripcion)
/*  526:     */   {
/*  527: 630 */     this.descripcion = descripcion;
/*  528:     */   }
/*  529:     */   
/*  530:     */   public boolean isIndicadorValidarDistribucion()
/*  531:     */   {
/*  532: 639 */     return this.indicadorValidarDistribucion;
/*  533:     */   }
/*  534:     */   
/*  535:     */   public void setIndicadorValidarDistribucion(boolean indicadorValidarDistribucion)
/*  536:     */   {
/*  537: 649 */     this.indicadorValidarDistribucion = indicadorValidarDistribucion;
/*  538:     */   }
/*  539:     */   
/*  540:     */   public int getId()
/*  541:     */   {
/*  542: 659 */     return this.idDetallePresupuesto;
/*  543:     */   }
/*  544:     */   
/*  545:     */   public BigDecimal getValorEnero()
/*  546:     */   {
/*  547: 663 */     return this.valorEnero;
/*  548:     */   }
/*  549:     */   
/*  550:     */   public void setValorEnero(BigDecimal valorEnero)
/*  551:     */   {
/*  552: 667 */     this.valorEnero = valorEnero;
/*  553:     */   }
/*  554:     */   
/*  555:     */   public BigDecimal getValorFebrero()
/*  556:     */   {
/*  557: 671 */     return this.valorFebrero;
/*  558:     */   }
/*  559:     */   
/*  560:     */   public void setValorFebrero(BigDecimal valorFebrero)
/*  561:     */   {
/*  562: 675 */     this.valorFebrero = valorFebrero;
/*  563:     */   }
/*  564:     */   
/*  565:     */   public BigDecimal getValorMarzo()
/*  566:     */   {
/*  567: 679 */     return this.valorMarzo;
/*  568:     */   }
/*  569:     */   
/*  570:     */   public void setValorMarzo(BigDecimal valorMarzo)
/*  571:     */   {
/*  572: 683 */     this.valorMarzo = valorMarzo;
/*  573:     */   }
/*  574:     */   
/*  575:     */   public BigDecimal getValorAbril()
/*  576:     */   {
/*  577: 687 */     return this.valorAbril;
/*  578:     */   }
/*  579:     */   
/*  580:     */   public void setValorAbril(BigDecimal valorAbril)
/*  581:     */   {
/*  582: 691 */     this.valorAbril = valorAbril;
/*  583:     */   }
/*  584:     */   
/*  585:     */   public BigDecimal getValorMayo()
/*  586:     */   {
/*  587: 695 */     return this.valorMayo;
/*  588:     */   }
/*  589:     */   
/*  590:     */   public void setValorMayo(BigDecimal valorMayo)
/*  591:     */   {
/*  592: 699 */     this.valorMayo = valorMayo;
/*  593:     */   }
/*  594:     */   
/*  595:     */   public BigDecimal getValorJunio()
/*  596:     */   {
/*  597: 703 */     return this.valorJunio;
/*  598:     */   }
/*  599:     */   
/*  600:     */   public void setValorJunio(BigDecimal valorJunio)
/*  601:     */   {
/*  602: 707 */     this.valorJunio = valorJunio;
/*  603:     */   }
/*  604:     */   
/*  605:     */   public BigDecimal getValorJulio()
/*  606:     */   {
/*  607: 711 */     return this.valorJulio;
/*  608:     */   }
/*  609:     */   
/*  610:     */   public void setValorJulio(BigDecimal valorJulio)
/*  611:     */   {
/*  612: 715 */     this.valorJulio = valorJulio;
/*  613:     */   }
/*  614:     */   
/*  615:     */   public BigDecimal getValorAgosto()
/*  616:     */   {
/*  617: 719 */     return this.valorAgosto;
/*  618:     */   }
/*  619:     */   
/*  620:     */   public void setValorAgosto(BigDecimal valorAgosto)
/*  621:     */   {
/*  622: 723 */     this.valorAgosto = valorAgosto;
/*  623:     */   }
/*  624:     */   
/*  625:     */   public BigDecimal getValorSeptiembre()
/*  626:     */   {
/*  627: 727 */     return this.valorSeptiembre;
/*  628:     */   }
/*  629:     */   
/*  630:     */   public void setValorSeptiembre(BigDecimal valorSeptiembre)
/*  631:     */   {
/*  632: 731 */     this.valorSeptiembre = valorSeptiembre;
/*  633:     */   }
/*  634:     */   
/*  635:     */   public BigDecimal getValorOctubre()
/*  636:     */   {
/*  637: 735 */     return this.valorOctubre;
/*  638:     */   }
/*  639:     */   
/*  640:     */   public void setValorOctubre(BigDecimal valorOctubre)
/*  641:     */   {
/*  642: 739 */     this.valorOctubre = valorOctubre;
/*  643:     */   }
/*  644:     */   
/*  645:     */   public BigDecimal getValorNoviembre()
/*  646:     */   {
/*  647: 743 */     return this.valorNoviembre;
/*  648:     */   }
/*  649:     */   
/*  650:     */   public void setValorNoviembre(BigDecimal valorNoviembre)
/*  651:     */   {
/*  652: 747 */     this.valorNoviembre = valorNoviembre;
/*  653:     */   }
/*  654:     */   
/*  655:     */   public BigDecimal getValorDiciembre()
/*  656:     */   {
/*  657: 751 */     return this.valorDiciembre;
/*  658:     */   }
/*  659:     */   
/*  660:     */   public void setValorDiciembre(BigDecimal valorDiciembre)
/*  661:     */   {
/*  662: 755 */     this.valorDiciembre = valorDiciembre;
/*  663:     */   }
/*  664:     */   
/*  665:     */   public CuentaContable getCuentaContable()
/*  666:     */   {
/*  667: 759 */     return this.cuentaContable;
/*  668:     */   }
/*  669:     */   
/*  670:     */   public void setCuentaContable(CuentaContable cuentaContable)
/*  671:     */   {
/*  672: 763 */     this.cuentaContable = cuentaContable;
/*  673:     */   }
/*  674:     */   
/*  675:     */   public DimensionContable getDimensionContable()
/*  676:     */   {
/*  677: 767 */     return this.dimensionContable;
/*  678:     */   }
/*  679:     */   
/*  680:     */   public void setDimensionContable(DimensionContable dimensionContable)
/*  681:     */   {
/*  682: 771 */     this.dimensionContable = dimensionContable;
/*  683:     */   }
/*  684:     */   
/*  685:     */   public BigDecimal getIncrementosEnero()
/*  686:     */   {
/*  687: 775 */     return this.incrementosEnero;
/*  688:     */   }
/*  689:     */   
/*  690:     */   public void setIncrementosEnero(BigDecimal incrementosEnero)
/*  691:     */   {
/*  692: 779 */     this.incrementosEnero = incrementosEnero;
/*  693:     */   }
/*  694:     */   
/*  695:     */   public BigDecimal getDecrementosEnero()
/*  696:     */   {
/*  697: 783 */     return this.decrementosEnero;
/*  698:     */   }
/*  699:     */   
/*  700:     */   public void setDecrementosEnero(BigDecimal decrementosEnero)
/*  701:     */   {
/*  702: 787 */     this.decrementosEnero = decrementosEnero;
/*  703:     */   }
/*  704:     */   
/*  705:     */   public BigDecimal getIncrementosFebrero()
/*  706:     */   {
/*  707: 791 */     return this.incrementosFebrero;
/*  708:     */   }
/*  709:     */   
/*  710:     */   public void setIncrementosFebrero(BigDecimal incrementosFebrero)
/*  711:     */   {
/*  712: 795 */     this.incrementosFebrero = incrementosFebrero;
/*  713:     */   }
/*  714:     */   
/*  715:     */   public BigDecimal getDecrementosFebrero()
/*  716:     */   {
/*  717: 799 */     return this.decrementosFebrero;
/*  718:     */   }
/*  719:     */   
/*  720:     */   public void setDecrementosFebrero(BigDecimal decrementosFebrero)
/*  721:     */   {
/*  722: 803 */     this.decrementosFebrero = decrementosFebrero;
/*  723:     */   }
/*  724:     */   
/*  725:     */   public BigDecimal getIncrementosMarzo()
/*  726:     */   {
/*  727: 807 */     return this.incrementosMarzo;
/*  728:     */   }
/*  729:     */   
/*  730:     */   public void setIncrementosMarzo(BigDecimal incrementosMarzo)
/*  731:     */   {
/*  732: 811 */     this.incrementosMarzo = incrementosMarzo;
/*  733:     */   }
/*  734:     */   
/*  735:     */   public BigDecimal getDecrementosMarzo()
/*  736:     */   {
/*  737: 815 */     return this.decrementosMarzo;
/*  738:     */   }
/*  739:     */   
/*  740:     */   public void setDecrementosMarzo(BigDecimal decrementosMarzo)
/*  741:     */   {
/*  742: 819 */     this.decrementosMarzo = decrementosMarzo;
/*  743:     */   }
/*  744:     */   
/*  745:     */   public BigDecimal getIncrementosAbril()
/*  746:     */   {
/*  747: 823 */     return this.incrementosAbril;
/*  748:     */   }
/*  749:     */   
/*  750:     */   public void setIncrementosAbril(BigDecimal incrementosAbril)
/*  751:     */   {
/*  752: 827 */     this.incrementosAbril = incrementosAbril;
/*  753:     */   }
/*  754:     */   
/*  755:     */   public BigDecimal getDecrementosAbril()
/*  756:     */   {
/*  757: 831 */     return this.decrementosAbril;
/*  758:     */   }
/*  759:     */   
/*  760:     */   public void setDecrementosAbril(BigDecimal decrementosAbril)
/*  761:     */   {
/*  762: 835 */     this.decrementosAbril = decrementosAbril;
/*  763:     */   }
/*  764:     */   
/*  765:     */   public BigDecimal getIncrementosMayo()
/*  766:     */   {
/*  767: 839 */     return this.incrementosMayo;
/*  768:     */   }
/*  769:     */   
/*  770:     */   public void setIncrementosMayo(BigDecimal incrementosMayo)
/*  771:     */   {
/*  772: 843 */     this.incrementosMayo = incrementosMayo;
/*  773:     */   }
/*  774:     */   
/*  775:     */   public BigDecimal getDecrementosMayo()
/*  776:     */   {
/*  777: 847 */     return this.decrementosMayo;
/*  778:     */   }
/*  779:     */   
/*  780:     */   public void setDecrementosMayo(BigDecimal decrementosMayo)
/*  781:     */   {
/*  782: 851 */     this.decrementosMayo = decrementosMayo;
/*  783:     */   }
/*  784:     */   
/*  785:     */   public BigDecimal getIncrementosJunio()
/*  786:     */   {
/*  787: 855 */     return this.incrementosJunio;
/*  788:     */   }
/*  789:     */   
/*  790:     */   public void setIncrementosJunio(BigDecimal incrementosJunio)
/*  791:     */   {
/*  792: 859 */     this.incrementosJunio = incrementosJunio;
/*  793:     */   }
/*  794:     */   
/*  795:     */   public BigDecimal getDecrementosJunio()
/*  796:     */   {
/*  797: 863 */     return this.decrementosJunio;
/*  798:     */   }
/*  799:     */   
/*  800:     */   public void setDecrementosJunio(BigDecimal decrementosJunio)
/*  801:     */   {
/*  802: 867 */     this.decrementosJunio = decrementosJunio;
/*  803:     */   }
/*  804:     */   
/*  805:     */   public BigDecimal getIncrementosJulio()
/*  806:     */   {
/*  807: 871 */     return this.incrementosJulio;
/*  808:     */   }
/*  809:     */   
/*  810:     */   public void setIncrementosJulio(BigDecimal incrementosJulio)
/*  811:     */   {
/*  812: 875 */     this.incrementosJulio = incrementosJulio;
/*  813:     */   }
/*  814:     */   
/*  815:     */   public BigDecimal getDecrementosJulio()
/*  816:     */   {
/*  817: 879 */     return this.decrementosJulio;
/*  818:     */   }
/*  819:     */   
/*  820:     */   public void setDecrementosJulio(BigDecimal decrementosJulio)
/*  821:     */   {
/*  822: 883 */     this.decrementosJulio = decrementosJulio;
/*  823:     */   }
/*  824:     */   
/*  825:     */   public BigDecimal getIncrementosAgosto()
/*  826:     */   {
/*  827: 887 */     return this.incrementosAgosto;
/*  828:     */   }
/*  829:     */   
/*  830:     */   public void setIncrementosAgosto(BigDecimal incrementosAgosto)
/*  831:     */   {
/*  832: 891 */     this.incrementosAgosto = incrementosAgosto;
/*  833:     */   }
/*  834:     */   
/*  835:     */   public BigDecimal getDecrementosAgosto()
/*  836:     */   {
/*  837: 895 */     return this.decrementosAgosto;
/*  838:     */   }
/*  839:     */   
/*  840:     */   public void setDecrementosAgosto(BigDecimal decrementosAgosto)
/*  841:     */   {
/*  842: 899 */     this.decrementosAgosto = decrementosAgosto;
/*  843:     */   }
/*  844:     */   
/*  845:     */   public BigDecimal getIncrementosSeptiembre()
/*  846:     */   {
/*  847: 903 */     return this.incrementosSeptiembre;
/*  848:     */   }
/*  849:     */   
/*  850:     */   public void setIncrementosSeptiembre(BigDecimal incrementosSeptiembre)
/*  851:     */   {
/*  852: 907 */     this.incrementosSeptiembre = incrementosSeptiembre;
/*  853:     */   }
/*  854:     */   
/*  855:     */   public BigDecimal getDecrementosSeptiembre()
/*  856:     */   {
/*  857: 911 */     return this.decrementosSeptiembre;
/*  858:     */   }
/*  859:     */   
/*  860:     */   public void setDecrementosSeptiembre(BigDecimal decrementosSeptiembre)
/*  861:     */   {
/*  862: 915 */     this.decrementosSeptiembre = decrementosSeptiembre;
/*  863:     */   }
/*  864:     */   
/*  865:     */   public BigDecimal getIncrementosOctubre()
/*  866:     */   {
/*  867: 919 */     return this.incrementosOctubre;
/*  868:     */   }
/*  869:     */   
/*  870:     */   public void setIncrementosOctubre(BigDecimal incrementosOctubre)
/*  871:     */   {
/*  872: 923 */     this.incrementosOctubre = incrementosOctubre;
/*  873:     */   }
/*  874:     */   
/*  875:     */   public BigDecimal getDecrementosOctubre()
/*  876:     */   {
/*  877: 927 */     return this.decrementosOctubre;
/*  878:     */   }
/*  879:     */   
/*  880:     */   public void setDecrementosOctubre(BigDecimal decrementosOctubre)
/*  881:     */   {
/*  882: 931 */     this.decrementosOctubre = decrementosOctubre;
/*  883:     */   }
/*  884:     */   
/*  885:     */   public BigDecimal getIncrementosNoviembre()
/*  886:     */   {
/*  887: 935 */     return this.incrementosNoviembre;
/*  888:     */   }
/*  889:     */   
/*  890:     */   public void setIncrementosNoviembre(BigDecimal incrementosNoviembre)
/*  891:     */   {
/*  892: 939 */     this.incrementosNoviembre = incrementosNoviembre;
/*  893:     */   }
/*  894:     */   
/*  895:     */   public BigDecimal getDecrementosNoviembre()
/*  896:     */   {
/*  897: 943 */     return this.decrementosNoviembre;
/*  898:     */   }
/*  899:     */   
/*  900:     */   public void setDecrementosNoviembre(BigDecimal decrementosNoviembre)
/*  901:     */   {
/*  902: 947 */     this.decrementosNoviembre = decrementosNoviembre;
/*  903:     */   }
/*  904:     */   
/*  905:     */   public BigDecimal getIncrementosDiciembre()
/*  906:     */   {
/*  907: 951 */     return this.incrementosDiciembre;
/*  908:     */   }
/*  909:     */   
/*  910:     */   public void setIncrementosDiciembre(BigDecimal incrementosDiciembre)
/*  911:     */   {
/*  912: 955 */     this.incrementosDiciembre = incrementosDiciembre;
/*  913:     */   }
/*  914:     */   
/*  915:     */   public BigDecimal getDecrementosDiciembre()
/*  916:     */   {
/*  917: 959 */     return this.decrementosDiciembre;
/*  918:     */   }
/*  919:     */   
/*  920:     */   public void setDecrementosDiciembre(BigDecimal decrementosDiciembre)
/*  921:     */   {
/*  922: 963 */     this.decrementosDiciembre = decrementosDiciembre;
/*  923:     */   }
/*  924:     */   
/*  925:     */   public BigDecimal getTransferenciasIngresoEnero()
/*  926:     */   {
/*  927: 967 */     return this.transferenciasIngresoEnero;
/*  928:     */   }
/*  929:     */   
/*  930:     */   public void setTransferenciasIngresoEnero(BigDecimal transferenciasIngresoEnero)
/*  931:     */   {
/*  932: 971 */     this.transferenciasIngresoEnero = transferenciasIngresoEnero;
/*  933:     */   }
/*  934:     */   
/*  935:     */   public BigDecimal getTransferenciasEgresoEnero()
/*  936:     */   {
/*  937: 975 */     return this.transferenciasEgresoEnero;
/*  938:     */   }
/*  939:     */   
/*  940:     */   public void setTransferenciasEgresoEnero(BigDecimal transferenciasEgresoEnero)
/*  941:     */   {
/*  942: 979 */     this.transferenciasEgresoEnero = transferenciasEgresoEnero;
/*  943:     */   }
/*  944:     */   
/*  945:     */   public BigDecimal getTransferenciasIngresoFebrero()
/*  946:     */   {
/*  947: 983 */     return this.transferenciasIngresoFebrero;
/*  948:     */   }
/*  949:     */   
/*  950:     */   public void setTransferenciasIngresoFebrero(BigDecimal transferenciasIngresoFebrero)
/*  951:     */   {
/*  952: 987 */     this.transferenciasIngresoFebrero = transferenciasIngresoFebrero;
/*  953:     */   }
/*  954:     */   
/*  955:     */   public BigDecimal getTransferenciasEgresoFebrero()
/*  956:     */   {
/*  957: 991 */     return this.transferenciasEgresoFebrero;
/*  958:     */   }
/*  959:     */   
/*  960:     */   public void setTransferenciasEgresoFebrero(BigDecimal transferenciasEgresoFebrero)
/*  961:     */   {
/*  962: 995 */     this.transferenciasEgresoFebrero = transferenciasEgresoFebrero;
/*  963:     */   }
/*  964:     */   
/*  965:     */   public BigDecimal getTransferenciasIngresoMarzo()
/*  966:     */   {
/*  967: 999 */     return this.transferenciasIngresoMarzo;
/*  968:     */   }
/*  969:     */   
/*  970:     */   public void setTransferenciasIngresoMarzo(BigDecimal transferenciasIngresoMarzo)
/*  971:     */   {
/*  972:1003 */     this.transferenciasIngresoMarzo = transferenciasIngresoMarzo;
/*  973:     */   }
/*  974:     */   
/*  975:     */   public BigDecimal getTransferenciasEgresoMarzo()
/*  976:     */   {
/*  977:1007 */     return this.transferenciasEgresoMarzo;
/*  978:     */   }
/*  979:     */   
/*  980:     */   public void setTransferenciasEgresoMarzo(BigDecimal transferenciasEgresoMarzo)
/*  981:     */   {
/*  982:1011 */     this.transferenciasEgresoMarzo = transferenciasEgresoMarzo;
/*  983:     */   }
/*  984:     */   
/*  985:     */   public BigDecimal getTransferenciasIngresoAbril()
/*  986:     */   {
/*  987:1015 */     return this.transferenciasIngresoAbril;
/*  988:     */   }
/*  989:     */   
/*  990:     */   public void setTransferenciasIngresoAbril(BigDecimal transferenciasIngresoAbril)
/*  991:     */   {
/*  992:1019 */     this.transferenciasIngresoAbril = transferenciasIngresoAbril;
/*  993:     */   }
/*  994:     */   
/*  995:     */   public BigDecimal getTransferenciasEgresoAbril()
/*  996:     */   {
/*  997:1023 */     return this.transferenciasEgresoAbril;
/*  998:     */   }
/*  999:     */   
/* 1000:     */   public void setTransferenciasEgresoAbril(BigDecimal transferenciasEgresoAbril)
/* 1001:     */   {
/* 1002:1027 */     this.transferenciasEgresoAbril = transferenciasEgresoAbril;
/* 1003:     */   }
/* 1004:     */   
/* 1005:     */   public BigDecimal getTransferenciasIngresoMayo()
/* 1006:     */   {
/* 1007:1031 */     return this.transferenciasIngresoMayo;
/* 1008:     */   }
/* 1009:     */   
/* 1010:     */   public void setTransferenciasIngresoMayo(BigDecimal transferenciasIngresoMayo)
/* 1011:     */   {
/* 1012:1035 */     this.transferenciasIngresoMayo = transferenciasIngresoMayo;
/* 1013:     */   }
/* 1014:     */   
/* 1015:     */   public BigDecimal getTransferenciasEgresoMayo()
/* 1016:     */   {
/* 1017:1039 */     return this.transferenciasEgresoMayo;
/* 1018:     */   }
/* 1019:     */   
/* 1020:     */   public void setTransferenciasEgresoMayo(BigDecimal transferenciasEgresoMayo)
/* 1021:     */   {
/* 1022:1043 */     this.transferenciasEgresoMayo = transferenciasEgresoMayo;
/* 1023:     */   }
/* 1024:     */   
/* 1025:     */   public BigDecimal getTransferenciasIngresoJunio()
/* 1026:     */   {
/* 1027:1047 */     return this.transferenciasIngresoJunio;
/* 1028:     */   }
/* 1029:     */   
/* 1030:     */   public void setTransferenciasIngresoJunio(BigDecimal transferenciasIngresoJunio)
/* 1031:     */   {
/* 1032:1051 */     this.transferenciasIngresoJunio = transferenciasIngresoJunio;
/* 1033:     */   }
/* 1034:     */   
/* 1035:     */   public BigDecimal getTransferenciasEgresoJunio()
/* 1036:     */   {
/* 1037:1055 */     return this.transferenciasEgresoJunio;
/* 1038:     */   }
/* 1039:     */   
/* 1040:     */   public void setTransferenciasEgresoJunio(BigDecimal transferenciasEgresoJunio)
/* 1041:     */   {
/* 1042:1059 */     this.transferenciasEgresoJunio = transferenciasEgresoJunio;
/* 1043:     */   }
/* 1044:     */   
/* 1045:     */   public BigDecimal getTransferenciasIngresoJulio()
/* 1046:     */   {
/* 1047:1063 */     return this.transferenciasIngresoJulio;
/* 1048:     */   }
/* 1049:     */   
/* 1050:     */   public void setTransferenciasIngresoJulio(BigDecimal transferenciasIngresoJulio)
/* 1051:     */   {
/* 1052:1067 */     this.transferenciasIngresoJulio = transferenciasIngresoJulio;
/* 1053:     */   }
/* 1054:     */   
/* 1055:     */   public BigDecimal getTransferenciasEgresoJulio()
/* 1056:     */   {
/* 1057:1071 */     return this.transferenciasEgresoJulio;
/* 1058:     */   }
/* 1059:     */   
/* 1060:     */   public void setTransferenciasEgresoJulio(BigDecimal transferenciasEgresoJulio)
/* 1061:     */   {
/* 1062:1075 */     this.transferenciasEgresoJulio = transferenciasEgresoJulio;
/* 1063:     */   }
/* 1064:     */   
/* 1065:     */   public BigDecimal getTransferenciasIngresoAgosto()
/* 1066:     */   {
/* 1067:1079 */     return this.transferenciasIngresoAgosto;
/* 1068:     */   }
/* 1069:     */   
/* 1070:     */   public void setTransferenciasIngresoAgosto(BigDecimal transferenciasIngresoAgosto)
/* 1071:     */   {
/* 1072:1083 */     this.transferenciasIngresoAgosto = transferenciasIngresoAgosto;
/* 1073:     */   }
/* 1074:     */   
/* 1075:     */   public BigDecimal getTransferenciasEgresoAgosto()
/* 1076:     */   {
/* 1077:1087 */     return this.transferenciasEgresoAgosto;
/* 1078:     */   }
/* 1079:     */   
/* 1080:     */   public void setTransferenciasEgresoAgosto(BigDecimal transferenciasEgresoAgosto)
/* 1081:     */   {
/* 1082:1091 */     this.transferenciasEgresoAgosto = transferenciasEgresoAgosto;
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   public BigDecimal getTransferenciasIngresoSeptiembre()
/* 1086:     */   {
/* 1087:1095 */     return this.transferenciasIngresoSeptiembre;
/* 1088:     */   }
/* 1089:     */   
/* 1090:     */   public void setTransferenciasIngresoSeptiembre(BigDecimal transferenciasIngresoSeptiembre)
/* 1091:     */   {
/* 1092:1099 */     this.transferenciasIngresoSeptiembre = transferenciasIngresoSeptiembre;
/* 1093:     */   }
/* 1094:     */   
/* 1095:     */   public BigDecimal getTransferenciasEgresoSeptiembre()
/* 1096:     */   {
/* 1097:1103 */     return this.transferenciasEgresoSeptiembre;
/* 1098:     */   }
/* 1099:     */   
/* 1100:     */   public void setTransferenciasEgresoSeptiembre(BigDecimal transferenciasEgresoSeptiembre)
/* 1101:     */   {
/* 1102:1107 */     this.transferenciasEgresoSeptiembre = transferenciasEgresoSeptiembre;
/* 1103:     */   }
/* 1104:     */   
/* 1105:     */   public BigDecimal getTransferenciasIngresoOctubre()
/* 1106:     */   {
/* 1107:1111 */     return this.transferenciasIngresoOctubre;
/* 1108:     */   }
/* 1109:     */   
/* 1110:     */   public void setTransferenciasIngresoOctubre(BigDecimal transferenciasIngresoOctubre)
/* 1111:     */   {
/* 1112:1115 */     this.transferenciasIngresoOctubre = transferenciasIngresoOctubre;
/* 1113:     */   }
/* 1114:     */   
/* 1115:     */   public BigDecimal getTransferenciasEgresoOctubre()
/* 1116:     */   {
/* 1117:1119 */     return this.transferenciasEgresoOctubre;
/* 1118:     */   }
/* 1119:     */   
/* 1120:     */   public void setTransferenciasEgresoOctubre(BigDecimal transferenciasEgresoOctubre)
/* 1121:     */   {
/* 1122:1123 */     this.transferenciasEgresoOctubre = transferenciasEgresoOctubre;
/* 1123:     */   }
/* 1124:     */   
/* 1125:     */   public BigDecimal getTransferenciasIngresoNoviembre()
/* 1126:     */   {
/* 1127:1127 */     return this.transferenciasIngresoNoviembre;
/* 1128:     */   }
/* 1129:     */   
/* 1130:     */   public void setTransferenciasIngresoNoviembre(BigDecimal transferenciasIngresoNoviembre)
/* 1131:     */   {
/* 1132:1131 */     this.transferenciasIngresoNoviembre = transferenciasIngresoNoviembre;
/* 1133:     */   }
/* 1134:     */   
/* 1135:     */   public BigDecimal getTransferenciasEgresoNoviembre()
/* 1136:     */   {
/* 1137:1135 */     return this.transferenciasEgresoNoviembre;
/* 1138:     */   }
/* 1139:     */   
/* 1140:     */   public void setTransferenciasEgresoNoviembre(BigDecimal transferenciasEgresoNoviembre)
/* 1141:     */   {
/* 1142:1139 */     this.transferenciasEgresoNoviembre = transferenciasEgresoNoviembre;
/* 1143:     */   }
/* 1144:     */   
/* 1145:     */   public BigDecimal getTransferenciasIngresoDiciembre()
/* 1146:     */   {
/* 1147:1143 */     return this.transferenciasIngresoDiciembre;
/* 1148:     */   }
/* 1149:     */   
/* 1150:     */   public void setTransferenciasIngresoDiciembre(BigDecimal transferenciasIngresoDiciembre)
/* 1151:     */   {
/* 1152:1147 */     this.transferenciasIngresoDiciembre = transferenciasIngresoDiciembre;
/* 1153:     */   }
/* 1154:     */   
/* 1155:     */   public BigDecimal getTransferenciasEgresoDiciembre()
/* 1156:     */   {
/* 1157:1151 */     return this.transferenciasEgresoDiciembre;
/* 1158:     */   }
/* 1159:     */   
/* 1160:     */   public void setTransferenciasEgresoDiciembre(BigDecimal transferenciasEgresoDiciembre)
/* 1161:     */   {
/* 1162:1155 */     this.transferenciasEgresoDiciembre = transferenciasEgresoDiciembre;
/* 1163:     */   }
/* 1164:     */   
/* 1165:     */   public BigDecimal getSaldoComprometidoEnero()
/* 1166:     */   {
/* 1167:1159 */     return this.saldoComprometidoEnero;
/* 1168:     */   }
/* 1169:     */   
/* 1170:     */   public void setSaldoComprometidoEnero(BigDecimal saldoComprometidoEnero)
/* 1171:     */   {
/* 1172:1163 */     this.saldoComprometidoEnero = saldoComprometidoEnero;
/* 1173:     */   }
/* 1174:     */   
/* 1175:     */   public BigDecimal getSaldoComprometidoFebrero()
/* 1176:     */   {
/* 1177:1167 */     return this.saldoComprometidoFebrero;
/* 1178:     */   }
/* 1179:     */   
/* 1180:     */   public void setSaldoComprometidoFebrero(BigDecimal saldoComprometidoFebrero)
/* 1181:     */   {
/* 1182:1171 */     this.saldoComprometidoFebrero = saldoComprometidoFebrero;
/* 1183:     */   }
/* 1184:     */   
/* 1185:     */   public BigDecimal getSaldoComprometidoMarzo()
/* 1186:     */   {
/* 1187:1175 */     return this.saldoComprometidoMarzo;
/* 1188:     */   }
/* 1189:     */   
/* 1190:     */   public void setSaldoComprometidoMarzo(BigDecimal saldoComprometidoMarzo)
/* 1191:     */   {
/* 1192:1179 */     this.saldoComprometidoMarzo = saldoComprometidoMarzo;
/* 1193:     */   }
/* 1194:     */   
/* 1195:     */   public BigDecimal getSaldoComprometidoAbril()
/* 1196:     */   {
/* 1197:1183 */     return this.saldoComprometidoAbril;
/* 1198:     */   }
/* 1199:     */   
/* 1200:     */   public void setSaldoComprometidoAbril(BigDecimal saldoComprometidoAbril)
/* 1201:     */   {
/* 1202:1187 */     this.saldoComprometidoAbril = saldoComprometidoAbril;
/* 1203:     */   }
/* 1204:     */   
/* 1205:     */   public BigDecimal getSaldoComprometidoMayo()
/* 1206:     */   {
/* 1207:1191 */     return this.saldoComprometidoMayo;
/* 1208:     */   }
/* 1209:     */   
/* 1210:     */   public void setSaldoComprometidoMayo(BigDecimal saldoComprometidoMayo)
/* 1211:     */   {
/* 1212:1195 */     this.saldoComprometidoMayo = saldoComprometidoMayo;
/* 1213:     */   }
/* 1214:     */   
/* 1215:     */   public BigDecimal getSaldoComprometidoJunio()
/* 1216:     */   {
/* 1217:1199 */     return this.saldoComprometidoJunio;
/* 1218:     */   }
/* 1219:     */   
/* 1220:     */   public void setSaldoComprometidoJunio(BigDecimal saldoComprometidoJunio)
/* 1221:     */   {
/* 1222:1203 */     this.saldoComprometidoJunio = saldoComprometidoJunio;
/* 1223:     */   }
/* 1224:     */   
/* 1225:     */   public BigDecimal getSaldoComprometidoJulio()
/* 1226:     */   {
/* 1227:1207 */     return this.saldoComprometidoJulio;
/* 1228:     */   }
/* 1229:     */   
/* 1230:     */   public void setSaldoComprometidoJulio(BigDecimal saldoComprometidoJulio)
/* 1231:     */   {
/* 1232:1211 */     this.saldoComprometidoJulio = saldoComprometidoJulio;
/* 1233:     */   }
/* 1234:     */   
/* 1235:     */   public BigDecimal getSaldoComprometidoAgosto()
/* 1236:     */   {
/* 1237:1215 */     return this.saldoComprometidoAgosto;
/* 1238:     */   }
/* 1239:     */   
/* 1240:     */   public void setSaldoComprometidoAgosto(BigDecimal saldoComprometidoAgosto)
/* 1241:     */   {
/* 1242:1219 */     this.saldoComprometidoAgosto = saldoComprometidoAgosto;
/* 1243:     */   }
/* 1244:     */   
/* 1245:     */   public BigDecimal getSaldoComprometidoSeptiembre()
/* 1246:     */   {
/* 1247:1223 */     return this.saldoComprometidoSeptiembre;
/* 1248:     */   }
/* 1249:     */   
/* 1250:     */   public void setSaldoComprometidoSeptiembre(BigDecimal saldoComprometidoSeptiembre)
/* 1251:     */   {
/* 1252:1227 */     this.saldoComprometidoSeptiembre = saldoComprometidoSeptiembre;
/* 1253:     */   }
/* 1254:     */   
/* 1255:     */   public BigDecimal getSaldoComprometidoOctubre()
/* 1256:     */   {
/* 1257:1231 */     return this.saldoComprometidoOctubre;
/* 1258:     */   }
/* 1259:     */   
/* 1260:     */   public void setSaldoComprometidoOctubre(BigDecimal saldoComprometidoOctubre)
/* 1261:     */   {
/* 1262:1235 */     this.saldoComprometidoOctubre = saldoComprometidoOctubre;
/* 1263:     */   }
/* 1264:     */   
/* 1265:     */   public BigDecimal getSaldoComprometidoNoviembre()
/* 1266:     */   {
/* 1267:1239 */     return this.saldoComprometidoNoviembre;
/* 1268:     */   }
/* 1269:     */   
/* 1270:     */   public void setSaldoComprometidoNoviembre(BigDecimal saldoComprometidoNoviembre)
/* 1271:     */   {
/* 1272:1243 */     this.saldoComprometidoNoviembre = saldoComprometidoNoviembre;
/* 1273:     */   }
/* 1274:     */   
/* 1275:     */   public BigDecimal getSaldoComprometidoDiciembre()
/* 1276:     */   {
/* 1277:1247 */     return this.saldoComprometidoDiciembre;
/* 1278:     */   }
/* 1279:     */   
/* 1280:     */   public void setSaldoComprometidoDiciembre(BigDecimal saldoComprometidoDiciembre)
/* 1281:     */   {
/* 1282:1251 */     this.saldoComprometidoDiciembre = saldoComprometidoDiciembre;
/* 1283:     */   }
/* 1284:     */   
/* 1285:     */   public BigDecimal getSaldoRealEnero()
/* 1286:     */   {
/* 1287:1255 */     return this.saldoRealEnero;
/* 1288:     */   }
/* 1289:     */   
/* 1290:     */   public void setSaldoRealEnero(BigDecimal saldoRealEnero)
/* 1291:     */   {
/* 1292:1259 */     this.saldoRealEnero = saldoRealEnero;
/* 1293:     */   }
/* 1294:     */   
/* 1295:     */   public BigDecimal getSaldoRealFebrero()
/* 1296:     */   {
/* 1297:1263 */     return this.saldoRealFebrero;
/* 1298:     */   }
/* 1299:     */   
/* 1300:     */   public void setSaldoRealFebrero(BigDecimal saldoRealFebrero)
/* 1301:     */   {
/* 1302:1267 */     this.saldoRealFebrero = saldoRealFebrero;
/* 1303:     */   }
/* 1304:     */   
/* 1305:     */   public BigDecimal getSaldoRealMarzo()
/* 1306:     */   {
/* 1307:1271 */     return this.saldoRealMarzo;
/* 1308:     */   }
/* 1309:     */   
/* 1310:     */   public void setSaldoRealMarzo(BigDecimal saldoRealMarzo)
/* 1311:     */   {
/* 1312:1275 */     this.saldoRealMarzo = saldoRealMarzo;
/* 1313:     */   }
/* 1314:     */   
/* 1315:     */   public BigDecimal getSaldoRealAbril()
/* 1316:     */   {
/* 1317:1279 */     return this.saldoRealAbril;
/* 1318:     */   }
/* 1319:     */   
/* 1320:     */   public void setSaldoRealAbril(BigDecimal saldoRealAbril)
/* 1321:     */   {
/* 1322:1283 */     this.saldoRealAbril = saldoRealAbril;
/* 1323:     */   }
/* 1324:     */   
/* 1325:     */   public BigDecimal getSaldoRealMayo()
/* 1326:     */   {
/* 1327:1287 */     return this.saldoRealMayo;
/* 1328:     */   }
/* 1329:     */   
/* 1330:     */   public void setSaldoRealMayo(BigDecimal saldoRealMayo)
/* 1331:     */   {
/* 1332:1291 */     this.saldoRealMayo = saldoRealMayo;
/* 1333:     */   }
/* 1334:     */   
/* 1335:     */   public BigDecimal getSaldoRealJunio()
/* 1336:     */   {
/* 1337:1295 */     return this.saldoRealJunio;
/* 1338:     */   }
/* 1339:     */   
/* 1340:     */   public void setSaldoRealJunio(BigDecimal saldoRealJunio)
/* 1341:     */   {
/* 1342:1299 */     this.saldoRealJunio = saldoRealJunio;
/* 1343:     */   }
/* 1344:     */   
/* 1345:     */   public BigDecimal getSaldoRealJulio()
/* 1346:     */   {
/* 1347:1303 */     return this.saldoRealJulio;
/* 1348:     */   }
/* 1349:     */   
/* 1350:     */   public void setSaldoRealJulio(BigDecimal saldoRealJulio)
/* 1351:     */   {
/* 1352:1307 */     this.saldoRealJulio = saldoRealJulio;
/* 1353:     */   }
/* 1354:     */   
/* 1355:     */   public BigDecimal getSaldoRealAgosto()
/* 1356:     */   {
/* 1357:1311 */     return this.saldoRealAgosto;
/* 1358:     */   }
/* 1359:     */   
/* 1360:     */   public void setSaldoRealAgosto(BigDecimal saldoRealAgosto)
/* 1361:     */   {
/* 1362:1315 */     this.saldoRealAgosto = saldoRealAgosto;
/* 1363:     */   }
/* 1364:     */   
/* 1365:     */   public BigDecimal getSaldoRealSeptiembre()
/* 1366:     */   {
/* 1367:1319 */     return this.saldoRealSeptiembre;
/* 1368:     */   }
/* 1369:     */   
/* 1370:     */   public void setSaldoRealSeptiembre(BigDecimal saldoRealSeptiembre)
/* 1371:     */   {
/* 1372:1323 */     this.saldoRealSeptiembre = saldoRealSeptiembre;
/* 1373:     */   }
/* 1374:     */   
/* 1375:     */   public BigDecimal getSaldoRealOctubre()
/* 1376:     */   {
/* 1377:1327 */     return this.saldoRealOctubre;
/* 1378:     */   }
/* 1379:     */   
/* 1380:     */   public void setSaldoRealOctubre(BigDecimal saldoRealOctubre)
/* 1381:     */   {
/* 1382:1331 */     this.saldoRealOctubre = saldoRealOctubre;
/* 1383:     */   }
/* 1384:     */   
/* 1385:     */   public BigDecimal getSaldoRealNoviembre()
/* 1386:     */   {
/* 1387:1335 */     return this.saldoRealNoviembre;
/* 1388:     */   }
/* 1389:     */   
/* 1390:     */   public void setSaldoRealNoviembre(BigDecimal saldoRealNoviembre)
/* 1391:     */   {
/* 1392:1339 */     this.saldoRealNoviembre = saldoRealNoviembre;
/* 1393:     */   }
/* 1394:     */   
/* 1395:     */   public BigDecimal getSaldoRealDiciembre()
/* 1396:     */   {
/* 1397:1343 */     return this.saldoRealDiciembre;
/* 1398:     */   }
/* 1399:     */   
/* 1400:     */   public void setSaldoRealDiciembre(BigDecimal saldoRealDiciembre)
/* 1401:     */   {
/* 1402:1347 */     this.saldoRealDiciembre = saldoRealDiciembre;
/* 1403:     */   }
/* 1404:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.presupuesto.DetallePresupuesto
 * JD-Core Version:    0.7.0.1
 */