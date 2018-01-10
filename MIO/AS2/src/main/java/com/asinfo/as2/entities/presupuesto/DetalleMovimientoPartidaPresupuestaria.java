/*   1:    */ package com.asinfo.as2.entities.presupuesto;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CuentaContable;
/*   4:    */ import com.asinfo.as2.entities.DimensionContable;
/*   5:    */ import com.asinfo.as2.entities.EntidadBase;
/*   6:    */ import java.math.BigDecimal;
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
/*  17:    */ import javax.validation.constraints.DecimalMin;
/*  18:    */ import javax.validation.constraints.Digits;
/*  19:    */ import javax.validation.constraints.Size;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="detalle_movimiento_partida_presupuestaria")
/*  23:    */ public class DetalleMovimientoPartidaPresupuestaria
/*  24:    */   extends EntidadBase
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 5576894464358798922L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="detalle_movimiento_partida_presupuestaria", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_movimiento_partida_presupuestaria")
/*  30:    */   @Column(name="id_detalle_movimiento_partida_presupuestaria")
/*  31:    */   private int idDetalleMovimientoPartidaPresupuestaria;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="descripcion", nullable=true, length=200)
/*  37:    */   @Size(max=200)
/*  38:    */   private String descripcion;
/*  39:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_movimiento_partida_presupuestaria", nullable=true)
/*  41:    */   private MovimientoPartidaPresupuestaria movimientoPartidaPresupuestaria;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_cuenta_contable_origen", nullable=true)
/*  44:    */   private CuentaContable cuentaContableOrigen;
/*  45:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  46:    */   @JoinColumn(name="id_cuenta_contable_destino", nullable=true)
/*  47:    */   private CuentaContable cuentaContableDestino;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_dimension_contable_origen", nullable=true)
/*  50:    */   private DimensionContable dimensionContableOrigen;
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_dimension_contable_destino", nullable=true)
/*  53:    */   private DimensionContable dimensionContableDestino;
/*  54:    */   @Column(name="saldo_origen", nullable=true, precision=12, scale=2)
/*  55:    */   @Digits(integer=12, fraction=2)
/*  56:    */   @DecimalMin("0.00")
/*  57:    */   private BigDecimal saldoOrigen;
/*  58:    */   @Column(name="saldo_destino", nullable=true, precision=12, scale=2)
/*  59:    */   @Digits(integer=12, fraction=2)
/*  60:    */   @DecimalMin("0.00")
/*  61:    */   private BigDecimal saldoDestino;
/*  62:    */   @Column(name="valor", nullable=false, precision=12, scale=2)
/*  63:    */   @Digits(integer=12, fraction=2)
/*  64:    */   @DecimalMin("0.00")
/*  65:    */   private BigDecimal valor;
/*  66:    */   
/*  67:    */   public int getIdDetalleMovimientoPartidaPresupuestaria()
/*  68:    */   {
/*  69: 94 */     return this.idDetalleMovimientoPartidaPresupuestaria;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setIdDetalleMovimientoPartidaPresupuestaria(int idDetalleMovimientoPartidaPresupuestaria)
/*  73:    */   {
/*  74: 98 */     this.idDetalleMovimientoPartidaPresupuestaria = idDetalleMovimientoPartidaPresupuestaria;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getIdSucursal()
/*  78:    */   {
/*  79:102 */     return this.idSucursal;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIdSucursal(int idSucursal)
/*  83:    */   {
/*  84:106 */     this.idSucursal = idSucursal;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getIdOrganizacion()
/*  88:    */   {
/*  89:110 */     return this.idOrganizacion;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setIdOrganizacion(int idOrganizacion)
/*  93:    */   {
/*  94:114 */     this.idOrganizacion = idOrganizacion;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String getDescripcion()
/*  98:    */   {
/*  99:118 */     return this.descripcion;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setDescripcion(String descripcion)
/* 103:    */   {
/* 104:122 */     this.descripcion = descripcion;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public MovimientoPartidaPresupuestaria getMovimientoPartidaPresupuestaria()
/* 108:    */   {
/* 109:126 */     return this.movimientoPartidaPresupuestaria;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setMovimientoPartidaPresupuestaria(MovimientoPartidaPresupuestaria movimientoPartidaPresupuestaria)
/* 113:    */   {
/* 114:130 */     this.movimientoPartidaPresupuestaria = movimientoPartidaPresupuestaria;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public CuentaContable getCuentaContableOrigen()
/* 118:    */   {
/* 119:134 */     return this.cuentaContableOrigen;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setCuentaContableOrigen(CuentaContable cuentaContableOrigen)
/* 123:    */   {
/* 124:138 */     this.cuentaContableOrigen = cuentaContableOrigen;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public CuentaContable getCuentaContableDestino()
/* 128:    */   {
/* 129:142 */     return this.cuentaContableDestino;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setCuentaContableDestino(CuentaContable cuentaContableDestino)
/* 133:    */   {
/* 134:146 */     this.cuentaContableDestino = cuentaContableDestino;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public DimensionContable getDimensionContableOrigen()
/* 138:    */   {
/* 139:150 */     return this.dimensionContableOrigen;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setDimensionContableOrigen(DimensionContable dimensionContableOrigen)
/* 143:    */   {
/* 144:154 */     this.dimensionContableOrigen = dimensionContableOrigen;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public DimensionContable getDimensionContableDestino()
/* 148:    */   {
/* 149:158 */     return this.dimensionContableDestino;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setDimensionContableDestino(DimensionContable dimensionContableDestino)
/* 153:    */   {
/* 154:162 */     this.dimensionContableDestino = dimensionContableDestino;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public BigDecimal getSaldoOrigen()
/* 158:    */   {
/* 159:166 */     return this.saldoOrigen;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setSaldoOrigen(BigDecimal saldoOrigen)
/* 163:    */   {
/* 164:170 */     this.saldoOrigen = saldoOrigen;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public BigDecimal getSaldoDestino()
/* 168:    */   {
/* 169:174 */     return this.saldoDestino;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setSaldoDestino(BigDecimal saldoDestino)
/* 173:    */   {
/* 174:178 */     this.saldoDestino = saldoDestino;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public BigDecimal getValor()
/* 178:    */   {
/* 179:182 */     return this.valor;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setValor(BigDecimal valor)
/* 183:    */   {
/* 184:186 */     this.valor = valor;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public int getId()
/* 188:    */   {
/* 189:191 */     return this.idDetalleMovimientoPartidaPresupuestaria;
/* 190:    */   }
/* 191:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.presupuesto.DetalleMovimientoPartidaPresupuestaria
 * JD-Core Version:    0.7.0.1
 */