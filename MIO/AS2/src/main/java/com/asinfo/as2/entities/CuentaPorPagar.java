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
/*  18:    */ import javax.persistence.Transient;
/*  19:    */ import javax.validation.constraints.DecimalMin;
/*  20:    */ import javax.validation.constraints.Digits;
/*  21:    */ import javax.validation.constraints.NotNull;
/*  22:    */ import org.hibernate.annotations.ColumnDefault;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="cuenta_por_pagar", indexes={@javax.persistence.Index(columnList="id_factura_proveedor"), @javax.persistence.Index(columnList="fecha_vencimiento")})
/*  26:    */ public class CuentaPorPagar
/*  27:    */   extends EntidadBase
/*  28:    */   implements Serializable
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = -8207552336883378289L;
/*  31:    */   @Id
/*  32:    */   @TableGenerator(name="cuenta_por_pagar", initialValue=0, allocationSize=50)
/*  33:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="cuenta_por_pagar")
/*  34:    */   @Column(name="id_cuenta_por_pagar")
/*  35:    */   private int idCuentaPorPagar;
/*  36:    */   @Column(name="id_organizacion", nullable=false)
/*  37:    */   @NotNull
/*  38:    */   private int idOrganizacion;
/*  39:    */   @Column(name="id_sucursal", nullable=true)
/*  40:    */   private int idSucursal;
/*  41:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  42:    */   @JoinColumn(name="id_factura_proveedor", nullable=true)
/*  43:    */   private FacturaProveedor facturaProveedor;
/*  44:    */   @Column(name="numero_cuota", nullable=false)
/*  45:    */   @NotNull
/*  46:    */   private int numeroCuota;
/*  47:    */   @Temporal(TemporalType.DATE)
/*  48:    */   @Column(name="fecha_vencimiento", nullable=false)
/*  49:    */   @NotNull
/*  50:    */   private Date fechaVencimiento;
/*  51:    */   @Column(name="valor", nullable=false, precision=12, scale=2)
/*  52:    */   @NotNull
/*  53:    */   @Digits(integer=12, fraction=2)
/*  54:    */   @DecimalMin("0.01")
/*  55:    */   private BigDecimal valor;
/*  56:    */   @Column(name="saldo", nullable=false, precision=12, scale=2)
/*  57:    */   @NotNull
/*  58:    */   @Digits(integer=12, fraction=2)
/*  59:    */   private BigDecimal saldo;
/*  60:    */   @Column(name="indicador_bloqueada", nullable=false)
/*  61:    */   private boolean indicadorBloqueada;
/*  62:    */   @Column(name="indicador_en_orden_pago_proveedor", nullable=false)
/*  63:    */   @ColumnDefault("'0'")
/*  64:    */   @NotNull
/*  65:    */   private boolean indicadorEnOrdenPagoProveedor;
/*  66:    */   @Transient
/*  67:    */   private OrdenPagoProveedor ordenPagoProveedor;
/*  68:    */   
/*  69:    */   public int getId()
/*  70:    */   {
/*  71:108 */     return this.idCuentaPorPagar;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdCuentaPorPagar()
/*  75:    */   {
/*  76:116 */     return this.idCuentaPorPagar;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdCuentaPorPagar(int idCuentaPorPagar)
/*  80:    */   {
/*  81:120 */     this.idCuentaPorPagar = idCuentaPorPagar;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getIdOrganizacion()
/*  85:    */   {
/*  86:124 */     return this.idOrganizacion;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setIdOrganizacion(int idOrganizacion)
/*  90:    */   {
/*  91:128 */     this.idOrganizacion = idOrganizacion;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getIdSucursal()
/*  95:    */   {
/*  96:132 */     return this.idSucursal;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setIdSucursal(int idSucursal)
/* 100:    */   {
/* 101:136 */     this.idSucursal = idSucursal;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public FacturaProveedor getFacturaProveedor()
/* 105:    */   {
/* 106:140 */     return this.facturaProveedor;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setFacturaProveedor(FacturaProveedor facturaProveedor)
/* 110:    */   {
/* 111:144 */     this.facturaProveedor = facturaProveedor;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getNumeroCuota()
/* 115:    */   {
/* 116:148 */     return this.numeroCuota;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setNumeroCuota(int numeroCuota)
/* 120:    */   {
/* 121:152 */     this.numeroCuota = numeroCuota;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Date getFechaVencimiento()
/* 125:    */   {
/* 126:156 */     return this.fechaVencimiento;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setFechaVencimiento(Date fechaVencimiento)
/* 130:    */   {
/* 131:160 */     this.fechaVencimiento = fechaVencimiento;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public BigDecimal getValor()
/* 135:    */   {
/* 136:164 */     return this.valor;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setValor(BigDecimal valor)
/* 140:    */   {
/* 141:168 */     this.valor = valor;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public BigDecimal getSaldo()
/* 145:    */   {
/* 146:172 */     return this.saldo;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setSaldo(BigDecimal saldo)
/* 150:    */   {
/* 151:176 */     this.saldo = saldo;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public boolean isIndicadorBloqueada()
/* 155:    */   {
/* 156:185 */     return this.indicadorBloqueada;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setIndicadorBloqueada(boolean indicadorBloqueada)
/* 160:    */   {
/* 161:195 */     this.indicadorBloqueada = indicadorBloqueada;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public String toString()
/* 165:    */   {
/* 166:205 */     return "CuentaPorPagar [idCuentaPorPagar=" + this.idCuentaPorPagar + ", idOrganizacion=" + this.idOrganizacion + ", idSucursal=" + this.idSucursal + ", facturaProveedor=" + this.facturaProveedor + ", numeroCuota=" + this.numeroCuota + ", fechaVencimiento=" + this.fechaVencimiento + ", valor=" + this.valor + ", saldo=" + this.saldo + "]";
/* 167:    */   }
/* 168:    */   
/* 169:    */   public OrdenPagoProveedor getOrdenPagoProveedor()
/* 170:    */   {
/* 171:211 */     return this.ordenPagoProveedor;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setOrdenPagoProveedor(OrdenPagoProveedor ordenPagoProveedor)
/* 175:    */   {
/* 176:215 */     this.ordenPagoProveedor = ordenPagoProveedor;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public boolean isIndicadorEnOrdenPagoProveedor()
/* 180:    */   {
/* 181:219 */     return this.indicadorEnOrdenPagoProveedor;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setIndicadorEnOrdenPagoProveedor(boolean indicadorEnOrdenPagoProveedor)
/* 185:    */   {
/* 186:223 */     this.indicadorEnOrdenPagoProveedor = indicadorEnOrdenPagoProveedor;
/* 187:    */   }
/* 188:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CuentaPorPagar
 * JD-Core Version:    0.7.0.1
 */