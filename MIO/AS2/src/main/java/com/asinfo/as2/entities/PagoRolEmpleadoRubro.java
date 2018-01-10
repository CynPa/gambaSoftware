/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.Digits;
/*  15:    */ import javax.validation.constraints.Min;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ import org.hibernate.annotations.ColumnDefault;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="pago_rol_empleado_rubro", indexes={@javax.persistence.Index(columnList="id_pago_rol_empleado"), @javax.persistence.Index(columnList="id_rubro"), @javax.persistence.Index(name="IX_pago_rol_empleado_rubro_automatico", columnList="indicador_automatico"), @javax.persistence.Index(name="IX_pago_rol_empleado_rubro_padre", columnList="id_pago_rol_empleado_padre")})
/*  21:    */ public class PagoRolEmpleadoRubro
/*  22:    */   extends EntidadBase
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="pago_rol_empleado_rubro", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="pago_rol_empleado_rubro")
/*  28:    */   @Column(name="id_pago_rol_empleado_rubro")
/*  29:    */   private int idPagoRolEmpleadoRubro;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @Column(name="tiempo", precision=12, scale=2)
/*  35:    */   @Digits(integer=12, fraction=2)
/*  36:    */   @Min(0L)
/*  37:    */   private BigDecimal tiempo;
/*  38:    */   @Column(name="valor", precision=12, scale=2)
/*  39:    */   @Digits(integer=12, fraction=2)
/*  40:    */   private BigDecimal valor;
/*  41:    */   @Column(name="indicador_tiempo", nullable=true)
/*  42:    */   private boolean indicadorTiempo;
/*  43:    */   @Column(name="indicador_provision", nullable=true)
/*  44:    */   private boolean indicadorProvision;
/*  45:    */   @Column(name="indicador_impresion_sobre", nullable=true)
/*  46:    */   private boolean indicadorImpresionSobre;
/*  47:    */   @Column(name="indicador_calculo_IESS", nullable=true)
/*  48:    */   private boolean indicadorCalculoIESS;
/*  49:    */   @Column(name="indicador_calculo_impuesto_renta", nullable=false)
/*  50:    */   private boolean indicadorCalculoImpuestoRenta;
/*  51:    */   @Column(name="indicador_manual", nullable=true)
/*  52:    */   private boolean indicadorManual;
/*  53:    */   @NotNull
/*  54:    */   @ColumnDefault("'0'")
/*  55:    */   @Column(name="indicador_no_procesado", nullable=false)
/*  56:    */   private boolean indicadorNoProcesado;
/*  57:    */   @Column(name="indicador_automatico", nullable=false)
/*  58: 96 */   private boolean indicadorAutomatico = false;
/*  59:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  60:    */   @JoinColumn(name="id_pago_rol_empleado", nullable=true)
/*  61:    */   private PagoRolEmpleado pagoRolEmpleado;
/*  62:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  63:    */   @JoinColumn(name="id_rubro", nullable=true)
/*  64:    */   private Rubro rubro;
/*  65:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  66:    */   @JoinColumn(name="id_pago_rol_empleado_padre", nullable=true)
/*  67:    */   private PagoRolEmpleadoRubro pagoRolEmpleadoRubroPadre;
/*  68:    */   
/*  69:    */   public PagoRolEmpleadoRubro() {}
/*  70:    */   
/*  71:    */   public PagoRolEmpleadoRubro(int idPagoRolEmpleadoRubro, BigDecimal tiempo, BigDecimal valor, boolean indicadorTiempo, boolean indicadorProvision, boolean indicadorImpresionSobre, boolean indicadorCalculoIESS, boolean indicadorCalculoImpuestoRenta, Rubro rubro)
/*  72:    */   {
/*  73:139 */     this.idPagoRolEmpleadoRubro = idPagoRolEmpleadoRubro;
/*  74:140 */     this.tiempo = tiempo;
/*  75:141 */     this.valor = valor;
/*  76:142 */     this.indicadorTiempo = indicadorTiempo;
/*  77:143 */     this.indicadorProvision = indicadorProvision;
/*  78:144 */     this.indicadorImpresionSobre = indicadorImpresionSobre;
/*  79:145 */     this.indicadorCalculoIESS = indicadorCalculoIESS;
/*  80:146 */     this.indicadorCalculoImpuestoRenta = indicadorCalculoImpuestoRenta;
/*  81:147 */     this.rubro = rubro;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public PagoRolEmpleadoRubro(int idPagoRolEmpleadoRubro, BigDecimal tiempo, BigDecimal valor, boolean indicadorTiempo, boolean indicadorProvision, boolean indicadorImpresionSobre, boolean indicadorCalculoIESS, boolean indicadorCalculoImpuestoRenta, Rubro rubro, boolean indicadorAutomatico)
/*  85:    */   {
/*  86:153 */     this(idPagoRolEmpleadoRubro, tiempo, valor, indicadorTiempo, indicadorProvision, indicadorImpresionSobre, indicadorCalculoIESS, indicadorCalculoImpuestoRenta, rubro);
/*  87:    */     
/*  88:155 */     this.indicadorAutomatico = indicadorAutomatico;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public PagoRolEmpleadoRubro(int idPagoRolEmpleadoRubro, BigDecimal tiempo, BigDecimal valor, boolean indicadorTiempo, boolean indicadorProvision, boolean indicadorImpresionSobre, boolean indicadorCalculoIESS, boolean indicadorCalculoImpuestoRenta, Rubro rubro, boolean indicadorAutomatico, int idPagoRolEmpleado, boolean indicadorNoProcesado, PagoRolEmpleadoRubro pagoRolEmpleadoRubroPadre)
/*  92:    */   {
/*  93:161 */     this(idPagoRolEmpleadoRubro, tiempo, valor, indicadorTiempo, indicadorProvision, indicadorImpresionSobre, indicadorCalculoIESS, indicadorCalculoImpuestoRenta, rubro, indicadorAutomatico);
/*  94:    */     
/*  95:163 */     this.pagoRolEmpleado = new PagoRolEmpleado();
/*  96:164 */     this.pagoRolEmpleado.setIdPagoRolEmpleado(idPagoRolEmpleado);
/*  97:165 */     this.indicadorNoProcesado = indicadorNoProcesado;
/*  98:166 */     this.pagoRolEmpleadoRubroPadre = pagoRolEmpleadoRubroPadre;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public int getIdPagoRolEmpleadoRubro()
/* 102:    */   {
/* 103:179 */     return this.idPagoRolEmpleadoRubro;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setIdPagoRolEmpleadoRubro(int idPagoRolEmpleadoRubro)
/* 107:    */   {
/* 108:189 */     this.idPagoRolEmpleadoRubro = idPagoRolEmpleadoRubro;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public int getIdOrganizacion()
/* 112:    */   {
/* 113:198 */     return this.idOrganizacion;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setIdOrganizacion(int idOrganizacion)
/* 117:    */   {
/* 118:208 */     this.idOrganizacion = idOrganizacion;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getIdSucursal()
/* 122:    */   {
/* 123:217 */     return this.idSucursal;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setIdSucursal(int idSucursal)
/* 127:    */   {
/* 128:227 */     this.idSucursal = idSucursal;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public BigDecimal getTiempo()
/* 132:    */   {
/* 133:236 */     return this.tiempo;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setTiempo(BigDecimal tiempo)
/* 137:    */   {
/* 138:246 */     this.tiempo = tiempo;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public BigDecimal getValor()
/* 142:    */   {
/* 143:255 */     return this.valor;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setValor(BigDecimal valor)
/* 147:    */   {
/* 148:265 */     this.valor = valor;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean isIndicadorTiempo()
/* 152:    */   {
/* 153:274 */     return this.indicadorTiempo;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setIndicadorTiempo(boolean indicadorTiempo)
/* 157:    */   {
/* 158:284 */     this.indicadorTiempo = indicadorTiempo;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public boolean isIndicadorProvision()
/* 162:    */   {
/* 163:293 */     return this.indicadorProvision;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setIndicadorProvision(boolean indicadorProvision)
/* 167:    */   {
/* 168:303 */     this.indicadorProvision = indicadorProvision;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean isIndicadorImpresionSobre()
/* 172:    */   {
/* 173:312 */     return this.indicadorImpresionSobre;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setIndicadorImpresionSobre(boolean indicadorImpresionSobre)
/* 177:    */   {
/* 178:322 */     this.indicadorImpresionSobre = indicadorImpresionSobre;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean isIndicadorCalculoIESS()
/* 182:    */   {
/* 183:331 */     return this.indicadorCalculoIESS;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setIndicadorCalculoIESS(boolean indicadorCalculoIESS)
/* 187:    */   {
/* 188:341 */     this.indicadorCalculoIESS = indicadorCalculoIESS;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean isIndicadorCalculoImpuestoRenta()
/* 192:    */   {
/* 193:350 */     return this.indicadorCalculoImpuestoRenta;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setIndicadorCalculoImpuestoRenta(boolean indicadorCalculoImpuestoRenta)
/* 197:    */   {
/* 198:360 */     this.indicadorCalculoImpuestoRenta = indicadorCalculoImpuestoRenta;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public PagoRolEmpleado getPagoRolEmpleado()
/* 202:    */   {
/* 203:369 */     return this.pagoRolEmpleado;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setPagoRolEmpleado(PagoRolEmpleado pagoRolEmpleado)
/* 207:    */   {
/* 208:379 */     this.pagoRolEmpleado = pagoRolEmpleado;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public Rubro getRubro()
/* 212:    */   {
/* 213:388 */     return this.rubro;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setRubro(Rubro rubro)
/* 217:    */   {
/* 218:398 */     this.rubro = rubro;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public boolean isIndicadorManual()
/* 222:    */   {
/* 223:402 */     return this.indicadorManual;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setIndicadorManual(boolean indicadorManual)
/* 227:    */   {
/* 228:406 */     this.indicadorManual = indicadorManual;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public boolean isIndicadorAutomatico()
/* 232:    */   {
/* 233:413 */     return this.indicadorAutomatico;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setIndicadorAutomatico(boolean indicadorAutomatico)
/* 237:    */   {
/* 238:420 */     this.indicadorAutomatico = indicadorAutomatico;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public int getId()
/* 242:    */   {
/* 243:430 */     return this.idPagoRolEmpleadoRubro;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public boolean isIndicadorNoProcesado()
/* 247:    */   {
/* 248:434 */     return this.indicadorNoProcesado;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setIndicadorNoProcesado(boolean indicadorNoProcesado)
/* 252:    */   {
/* 253:438 */     this.indicadorNoProcesado = indicadorNoProcesado;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public PagoRolEmpleadoRubro getPagoRolEmpleadoRubroPadre()
/* 257:    */   {
/* 258:442 */     return this.pagoRolEmpleadoRubroPadre;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setPagoRolEmpleadoRubroPadre(PagoRolEmpleadoRubro pagoRolEmpleadoRubroPadre)
/* 262:    */   {
/* 263:446 */     this.pagoRolEmpleadoRubroPadre = pagoRolEmpleadoRubroPadre;
/* 264:    */   }
/* 265:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.PagoRolEmpleadoRubro
 * JD-Core Version:    0.7.0.1
 */