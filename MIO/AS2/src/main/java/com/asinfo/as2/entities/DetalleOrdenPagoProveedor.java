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
/*  17:    */ import javax.validation.constraints.Size;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="detalle_orden_pago_proveedor")
/*  21:    */ public class DetalleOrdenPagoProveedor
/*  22:    */   extends EntidadBase
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 7337657570519425441L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="detalle_orden_pago_proveedor", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_orden_pago_proveedor")
/*  28:    */   @Column(name="id_detalle_orden_pago_proveedor")
/*  29:    */   private int idDetalleOrdenPagoProveedor;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_orden_pago_proveedor", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private OrdenPagoProveedor ordenPagoProveedor;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.DETACH})
/*  39:    */   @JoinColumn(name="id_cuenta_por_pagar", nullable=true)
/*  40:    */   private CuentaPorPagar cuentaPorPagar;
/*  41:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  42:    */   @Digits(integer=12, fraction=2)
/*  43:    */   @Min(0L)
/*  44: 64 */   private BigDecimal valor = BigDecimal.ZERO;
/*  45:    */   @Column(name="valor_aprobado", precision=12, scale=2, nullable=false)
/*  46:    */   @Digits(integer=12, fraction=2)
/*  47:    */   @Min(0L)
/*  48: 69 */   private BigDecimal valorAprobado = BigDecimal.ZERO;
/*  49:    */   @Column(name="valor_pagado", precision=12, scale=2, nullable=false)
/*  50:    */   @Digits(integer=12, fraction=2)
/*  51:    */   @Min(0L)
/*  52: 74 */   private BigDecimal valorPagado = BigDecimal.ZERO;
/*  53:    */   @Column(name="descripcion", nullable=true, length=200)
/*  54:    */   @Size(max=200)
/*  55:    */   private String descripcion;
/*  56:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  57:    */   @JoinColumn(name="id_proveedor", nullable=false)
/*  58:    */   @NotNull
/*  59:    */   private Proveedor proveedor;
/*  60:    */   @Column(name="indicador_anticipo", nullable=false)
/*  61:    */   @NotNull
/*  62:    */   private boolean indicadorAnticipo;
/*  63:    */   @Column(name="indicador_aprobacion_manual", nullable=false)
/*  64:    */   @NotNull
/*  65:    */   private boolean indicadorAprobacionManual;
/*  66:    */   @Column(name="dias_vencidos", nullable=false)
/*  67:    */   @NotNull
/*  68:    */   private int diasVencidos;
/*  69:    */   @Column(name="indicador_pagado", nullable=false)
/*  70:    */   @NotNull
/*  71:    */   private boolean indicadorPagado;
/*  72:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  73:    */   @JoinColumn(name="id_persona_responsable", nullable=true)
/*  74:    */   private PersonaResponsable personaResponsable;
/*  75:    */   
/*  76:    */   public int getId()
/*  77:    */   {
/*  78:119 */     return this.idDetalleOrdenPagoProveedor;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getIdDetalleOrdenPagoProveedor()
/*  82:    */   {
/*  83:123 */     return this.idDetalleOrdenPagoProveedor;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setIdDetalleOrdenPagoProveedor(int idDetalleOrdenPagoProveedor)
/*  87:    */   {
/*  88:127 */     this.idDetalleOrdenPagoProveedor = idDetalleOrdenPagoProveedor;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int getIdOrganizacion()
/*  92:    */   {
/*  93:131 */     return this.idOrganizacion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setIdOrganizacion(int idOrganizacion)
/*  97:    */   {
/*  98:135 */     this.idOrganizacion = idOrganizacion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public int getIdSucursal()
/* 102:    */   {
/* 103:139 */     return this.idSucursal;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setIdSucursal(int idSucursal)
/* 107:    */   {
/* 108:143 */     this.idSucursal = idSucursal;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public OrdenPagoProveedor getOrdenPagoProveedor()
/* 112:    */   {
/* 113:147 */     return this.ordenPagoProveedor;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setOrdenPagoProveedor(OrdenPagoProveedor ordenPagoProveedor)
/* 117:    */   {
/* 118:151 */     this.ordenPagoProveedor = ordenPagoProveedor;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public CuentaPorPagar getCuentaPorPagar()
/* 122:    */   {
/* 123:155 */     return this.cuentaPorPagar;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setCuentaPorPagar(CuentaPorPagar cuentaPorPagar)
/* 127:    */   {
/* 128:159 */     this.cuentaPorPagar = cuentaPorPagar;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public BigDecimal getValor()
/* 132:    */   {
/* 133:163 */     return this.valor;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setValor(BigDecimal valor)
/* 137:    */   {
/* 138:167 */     this.valor = valor;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String getDescripcion()
/* 142:    */   {
/* 143:171 */     return this.descripcion;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setDescripcion(String descripcion)
/* 147:    */   {
/* 148:175 */     this.descripcion = descripcion;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public int getDiasVencidos()
/* 152:    */   {
/* 153:179 */     return this.diasVencidos;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setDiasVencidos(int diasVencidos)
/* 157:    */   {
/* 158:183 */     this.diasVencidos = diasVencidos;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public Proveedor getProveedor()
/* 162:    */   {
/* 163:187 */     return this.proveedor;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setProveedor(Proveedor proveedor)
/* 167:    */   {
/* 168:191 */     this.proveedor = proveedor;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean isIndicadorAnticipo()
/* 172:    */   {
/* 173:195 */     return this.indicadorAnticipo;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setIndicadorAnticipo(boolean indicadorAnticipo)
/* 177:    */   {
/* 178:199 */     this.indicadorAnticipo = indicadorAnticipo;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public BigDecimal getValorAprobado()
/* 182:    */   {
/* 183:203 */     return this.valorAprobado;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setValorAprobado(BigDecimal valorAprobado)
/* 187:    */   {
/* 188:207 */     this.valorAprobado = valorAprobado;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public BigDecimal getDiferenciaPendiente()
/* 192:    */   {
/* 193:211 */     BigDecimal pendiente = BigDecimal.ZERO;
/* 194:212 */     if (getCuentaPorPagar() != null) {
/* 195:213 */       pendiente = getCuentaPorPagar().getSaldo();
/* 196:    */     }
/* 197:215 */     return pendiente.subtract(getValor());
/* 198:    */   }
/* 199:    */   
/* 200:    */   public BigDecimal getDiferenciaAprobado()
/* 201:    */   {
/* 202:219 */     return getValor().subtract(getValorAprobado());
/* 203:    */   }
/* 204:    */   
/* 205:    */   public boolean isIndicadorAprobacionManual()
/* 206:    */   {
/* 207:223 */     return this.indicadorAprobacionManual;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setIndicadorAprobacionManual(boolean indicadorAprobacionManual)
/* 211:    */   {
/* 212:227 */     this.indicadorAprobacionManual = indicadorAprobacionManual;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public boolean isIndicadorPagado()
/* 216:    */   {
/* 217:231 */     return this.indicadorPagado;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setIndicadorPagado(boolean indicadorPagado)
/* 221:    */   {
/* 222:235 */     this.indicadorPagado = indicadorPagado;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public BigDecimal getValorPagado()
/* 226:    */   {
/* 227:239 */     return this.valorPagado;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setValorPagado(BigDecimal valorPagado)
/* 231:    */   {
/* 232:243 */     this.valorPagado = valorPagado;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public PersonaResponsable getPersonaResponsable()
/* 236:    */   {
/* 237:247 */     return this.personaResponsable;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setPersonaResponsable(PersonaResponsable personaResponsable)
/* 241:    */   {
/* 242:251 */     this.personaResponsable = personaResponsable;
/* 243:    */   }
/* 244:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleOrdenPagoProveedor
 * JD-Core Version:    0.7.0.1
 */