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
/*  19:    */ import javax.validation.constraints.Digits;
/*  20:    */ import javax.validation.constraints.NotNull;
/*  21:    */ import javax.validation.constraints.Size;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="detalle_forma_pago")
/*  25:    */ public class DetalleFormaPago
/*  26:    */   extends EntidadBase
/*  27:    */   implements Serializable
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="detalle_forma_pago", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_forma_pago")
/*  33:    */   @Column(name="id_detalle_forma_pago", unique=true, nullable=false)
/*  34:    */   private int idDetalleFormaPago;
/*  35:    */   @Column(name="id_organizacion", nullable=false)
/*  36:    */   private int idOrganizacion;
/*  37:    */   @Column(name="id_sucursal", nullable=false)
/*  38:    */   private int idSucursal;
/*  39:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  40:    */   @JoinColumn(name="id_pago", nullable=true)
/*  41:    */   private Pago pago;
/*  42:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  43:    */   @JoinColumn(name="id_forma_pago", nullable=false)
/*  44:    */   @NotNull
/*  45:    */   private FormaPago formaPago;
/*  46:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  47:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion", nullable=true)
/*  48:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  49:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  50:    */   @Digits(integer=12, fraction=2)
/*  51: 71 */   private BigDecimal valor = BigDecimal.ZERO;
/*  52:    */   @Column(name="documento_referencia", nullable=true, length=20)
/*  53:    */   @Size(max=20)
/*  54:    */   private String documentoReferencia;
/*  55:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  56:    */   @JoinColumn(name="id_cuenta_contable_forma_pago", nullable=true)
/*  57:    */   private CuentaContable cuentaContableFormaPago;
/*  58:    */   @Column(name="indicador_cheque_posfechado", nullable=false)
/*  59:    */   private boolean indicadorChequePosfechado;
/*  60:    */   @Temporal(TemporalType.DATE)
/*  61:    */   @Column(name="fecha_posfechado", nullable=true)
/*  62:    */   private Date fechaPosfechado;
/*  63:    */   @Column(name="descripcion", nullable=true, length=200)
/*  64:    */   @Size(max=200)
/*  65:    */   private String descripcion;
/*  66:    */   @Transient
/*  67:    */   private Secuencia secuencia;
/*  68:    */   
/*  69:    */   public int getId()
/*  70:    */   {
/*  71: 99 */     return this.idDetalleFormaPago;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdDetalleFormaPago()
/*  75:    */   {
/*  76:111 */     return this.idDetalleFormaPago;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdDetalleFormaPago(int idDetalleFormaPago)
/*  80:    */   {
/*  81:121 */     this.idDetalleFormaPago = idDetalleFormaPago;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getIdOrganizacion()
/*  85:    */   {
/*  86:130 */     return this.idOrganizacion;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setIdOrganizacion(int idOrganizacion)
/*  90:    */   {
/*  91:140 */     this.idOrganizacion = idOrganizacion;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getIdSucursal()
/*  95:    */   {
/*  96:149 */     return this.idSucursal;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setIdSucursal(int idSucursal)
/* 100:    */   {
/* 101:159 */     this.idSucursal = idSucursal;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public FormaPago getFormaPago()
/* 105:    */   {
/* 106:168 */     if (this.formaPago == null) {
/* 107:169 */       this.formaPago = new FormaPago();
/* 108:    */     }
/* 109:171 */     return this.formaPago;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setFormaPago(FormaPago formaPago)
/* 113:    */   {
/* 114:181 */     this.formaPago = formaPago;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 118:    */   {
/* 119:190 */     return this.cuentaBancariaOrganizacion;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 123:    */   {
/* 124:200 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public BigDecimal getValor()
/* 128:    */   {
/* 129:209 */     return this.valor;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setValor(BigDecimal valor)
/* 133:    */   {
/* 134:219 */     this.valor = valor;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Pago getPago()
/* 138:    */   {
/* 139:228 */     return this.pago;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setPago(Pago pago)
/* 143:    */   {
/* 144:238 */     this.pago = pago;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getDocumentoReferencia()
/* 148:    */   {
/* 149:247 */     return this.documentoReferencia;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setDocumentoReferencia(String documentoReferencia)
/* 153:    */   {
/* 154:257 */     this.documentoReferencia = documentoReferencia;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Secuencia getSecuencia()
/* 158:    */   {
/* 159:266 */     return this.secuencia;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setSecuencia(Secuencia secuencia)
/* 163:    */   {
/* 164:276 */     this.secuencia = secuencia;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public CuentaContable getCuentaContableFormaPago()
/* 168:    */   {
/* 169:280 */     return this.cuentaContableFormaPago;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setCuentaContableFormaPago(CuentaContable cuentaContableFormaPago)
/* 173:    */   {
/* 174:284 */     this.cuentaContableFormaPago = cuentaContableFormaPago;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public boolean isIndicadorChequePosfechado()
/* 178:    */   {
/* 179:291 */     return this.indicadorChequePosfechado;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setIndicadorChequePosfechado(boolean indicadorChequePosfechado)
/* 183:    */   {
/* 184:299 */     this.indicadorChequePosfechado = indicadorChequePosfechado;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public Date getFechaPosfechado()
/* 188:    */   {
/* 189:306 */     return this.fechaPosfechado;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setFechaPosfechado(Date fechaPosfechado)
/* 193:    */   {
/* 194:314 */     this.fechaPosfechado = fechaPosfechado;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String getDescripcion()
/* 198:    */   {
/* 199:321 */     return this.descripcion;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setDescripcion(String descripcion)
/* 203:    */   {
/* 204:329 */     this.descripcion = descripcion;
/* 205:    */   }
/* 206:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleFormaPago
 * JD-Core Version:    0.7.0.1
 */