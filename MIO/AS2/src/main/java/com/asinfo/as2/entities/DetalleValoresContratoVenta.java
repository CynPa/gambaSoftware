/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.Date;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.persistence.Temporal;
/*  17:    */ import javax.persistence.TemporalType;
/*  18:    */ import javax.validation.constraints.Digits;
/*  19:    */ import javax.validation.constraints.Min;
/*  20:    */ import javax.validation.constraints.NotNull;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="detalle_valores_contrato_venta")
/*  24:    */ public class DetalleValoresContratoVenta
/*  25:    */   extends EntidadBase
/*  26:    */   implements Serializable
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -1847869211434882233L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="detalle_valores_contrato_venta", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_valores_contrato_venta")
/*  32:    */   @Column(name="id_detalle_valores_contrato_venta")
/*  33:    */   private int idDetalleValoresContratoVenta;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   @NotNull
/*  36:    */   private int idSucursal;
/*  37:    */   @Column(name="id_organizacion", nullable=false)
/*  38:    */   @NotNull
/*  39:    */   private int idOrganizacion;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_contrato_venta", nullable=true)
/*  42:    */   private ContratoVenta contratoVenta;
/*  43:    */   @Column(name="valor", nullable=false, precision=12, scale=2)
/*  44:    */   @NotNull
/*  45:    */   @Digits(integer=12, fraction=2)
/*  46:    */   @Min(0L)
/*  47: 56 */   private BigDecimal valor = BigDecimal.ZERO;
/*  48:    */   @Column(name="valor_devengar", nullable=true, precision=12, scale=2)
/*  49:    */   @Digits(integer=12, fraction=2)
/*  50: 63 */   private BigDecimal valorDevengar = BigDecimal.ZERO;
/*  51:    */   @Column(name="valor_nota_credito", nullable=true, precision=12, scale=2)
/*  52:    */   @Digits(integer=12, fraction=2)
/*  53: 68 */   private BigDecimal valorNotaCredito = BigDecimal.ZERO;
/*  54:    */   @Temporal(TemporalType.DATE)
/*  55:    */   @Column(name="fecha", nullable=false)
/*  56:    */   @NotNull
/*  57:    */   private Date fecha;
/*  58:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_interfaz_contable_proceso", nullable=true)
/*  60:    */   private InterfazContableProceso interfazContableProceso;
/*  61:    */   @Column(name="indicador_devengado", nullable=true)
/*  62:    */   private Boolean indicadorDevengado;
/*  63:    */   @Column(name="indicador_activo", nullable=false)
/*  64:    */   private boolean indicadorActivo;
/*  65:    */   @Column(name="indicador_facturado", nullable=true)
/*  66:    */   private Boolean indicadorFacturado;
/*  67:    */   @Column(name="indicador_contabilizado", nullable=true)
/*  68:    */   private Boolean indicadorContabilizado;
/*  69:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  70:    */   @JoinColumn(name="id_factura_cliente", nullable=true)
/*  71:    */   private FacturaCliente facturaCliente;
/*  72:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  73:    */   @JoinColumn(name="id_nota_credito_cliente", nullable=true)
/*  74:    */   private FacturaCliente notaCreditoCliente;
/*  75:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  76:    */   @JoinColumn(name="id_comprobante", nullable=true)
/*  77:    */   private FacturaCliente comprobante;
/*  78:    */   
/*  79:    */   public int getIdDetalleValoresContratoVenta()
/*  80:    */   {
/*  81:120 */     return this.idDetalleValoresContratoVenta;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setIdDetalleValoresContratoVenta(int idDetalleValoresContratoVenta)
/*  85:    */   {
/*  86:124 */     this.idDetalleValoresContratoVenta = idDetalleValoresContratoVenta;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getIdSucursal()
/*  90:    */   {
/*  91:128 */     return this.idSucursal;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdSucursal(int idSucursal)
/*  95:    */   {
/*  96:132 */     this.idSucursal = idSucursal;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getIdOrganizacion()
/* 100:    */   {
/* 101:136 */     return this.idOrganizacion;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setIdOrganizacion(int idOrganizacion)
/* 105:    */   {
/* 106:140 */     this.idOrganizacion = idOrganizacion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public ContratoVenta getContratoVenta()
/* 110:    */   {
/* 111:144 */     return this.contratoVenta;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setContratoVenta(ContratoVenta contratoVenta)
/* 115:    */   {
/* 116:148 */     this.contratoVenta = contratoVenta;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public int getId()
/* 120:    */   {
/* 121:153 */     return this.idDetalleValoresContratoVenta;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public BigDecimal getValor()
/* 125:    */   {
/* 126:157 */     return this.valor;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setValor(BigDecimal valor)
/* 130:    */   {
/* 131:161 */     this.valor = valor;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Date getFecha()
/* 135:    */   {
/* 136:165 */     return this.fecha;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setFecha(Date fecha)
/* 140:    */   {
/* 141:169 */     this.fecha = fecha;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public boolean isIndicadorDevengado()
/* 145:    */   {
/* 146:173 */     if (this.indicadorDevengado == null) {
/* 147:174 */       this.indicadorDevengado = Boolean.valueOf(false);
/* 148:    */     }
/* 149:176 */     return this.indicadorDevengado.booleanValue();
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setIndicadorDevengado(boolean indicadorDevengado)
/* 153:    */   {
/* 154:180 */     this.indicadorDevengado = Boolean.valueOf(indicadorDevengado);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public boolean isIndicadorActivo()
/* 158:    */   {
/* 159:184 */     return this.indicadorActivo;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setIndicadorActivo(boolean indicadorActivo)
/* 163:    */   {
/* 164:188 */     this.indicadorActivo = indicadorActivo;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public InterfazContableProceso getInterfazContableProceso()
/* 168:    */   {
/* 169:192 */     return this.interfazContableProceso;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/* 173:    */   {
/* 174:196 */     this.interfazContableProceso = interfazContableProceso;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public Boolean getIndicadorFacturado()
/* 178:    */   {
/* 179:200 */     if (null == this.indicadorFacturado) {
/* 180:201 */       this.indicadorFacturado = Boolean.valueOf(false);
/* 181:    */     }
/* 182:204 */     return this.indicadorFacturado;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setIndicadorFacturado(Boolean indicadorFacturado)
/* 186:    */   {
/* 187:208 */     this.indicadorFacturado = indicadorFacturado;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public BigDecimal getValorDevengar()
/* 191:    */   {
/* 192:212 */     if (this.valorDevengar == null) {
/* 193:213 */       this.valorDevengar = BigDecimal.ZERO;
/* 194:    */     }
/* 195:215 */     return this.valorDevengar;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setValorDevengar(BigDecimal valorDevengar)
/* 199:    */   {
/* 200:219 */     this.valorDevengar = valorDevengar;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public Boolean getIndicadorContabilizado()
/* 204:    */   {
/* 205:229 */     if (this.indicadorContabilizado == null) {
/* 206:230 */       this.indicadorContabilizado = Boolean.valueOf(false);
/* 207:    */     }
/* 208:232 */     return this.indicadorContabilizado;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setIndicadorContabilizado(Boolean indicadorContabilizado)
/* 212:    */   {
/* 213:236 */     this.indicadorContabilizado = indicadorContabilizado;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public BigDecimal getValorNotaCredito()
/* 217:    */   {
/* 218:240 */     if (this.valorNotaCredito == null) {
/* 219:241 */       this.valorNotaCredito = BigDecimal.ZERO;
/* 220:    */     }
/* 221:243 */     return this.valorNotaCredito;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setValorNotaCredito(BigDecimal valorNotaCredito)
/* 225:    */   {
/* 226:247 */     this.valorNotaCredito = valorNotaCredito;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public FacturaCliente getFacturaCliente()
/* 230:    */   {
/* 231:257 */     return this.facturaCliente;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setFacturaCliente(FacturaCliente facturaCliente)
/* 235:    */   {
/* 236:261 */     this.facturaCliente = facturaCliente;
/* 237:262 */     if (this.notaCreditoCliente == null) {
/* 238:263 */       this.comprobante = facturaCliente;
/* 239:    */     }
/* 240:    */   }
/* 241:    */   
/* 242:    */   public FacturaCliente getNotaCredito()
/* 243:    */   {
/* 244:268 */     return this.notaCreditoCliente;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setNotaCredito(FacturaCliente notaCreditoCliente)
/* 248:    */   {
/* 249:272 */     this.notaCreditoCliente = notaCreditoCliente;
/* 250:273 */     this.comprobante = notaCreditoCliente;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public FacturaCliente getComprobante()
/* 254:    */   {
/* 255:277 */     return this.comprobante;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setComprobante(FacturaCliente comprobante)
/* 259:    */   {
/* 260:281 */     this.comprobante = comprobante;
/* 261:    */   }
/* 262:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleValoresContratoVenta
 * JD-Core Version:    0.7.0.1
 */