/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.OneToMany;
/*  17:    */ import javax.persistence.Table;
/*  18:    */ import javax.persistence.TableGenerator;
/*  19:    */ import javax.persistence.Temporal;
/*  20:    */ import javax.persistence.TemporalType;
/*  21:    */ import javax.persistence.Transient;
/*  22:    */ import javax.validation.constraints.Digits;
/*  23:    */ import javax.validation.constraints.Min;
/*  24:    */ import javax.validation.constraints.NotNull;
/*  25:    */ 
/*  26:    */ @Entity
/*  27:    */ @Table(name="ajuste_prefactura_cliente")
/*  28:    */ public class AjustePrefacturaCliente
/*  29:    */   extends EntidadBase
/*  30:    */   implements Serializable
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 7989525345607764577L;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="ajuste_prefactura_cliente", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="ajuste_prefactura_cliente")
/*  36:    */   @Column(name="id_ajuste_prefactura_cliente")
/*  37:    */   private int idAjustePrefacturaCliente;
/*  38:    */   @Column(name="id_organizacion", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private int idOrganizacion;
/*  41:    */   @Column(name="id_sucursal", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private int idSucursal;
/*  44:    */   @Temporal(TemporalType.DATE)
/*  45:    */   @Column(name="fecha", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private Date fecha;
/*  48:    */   @Column(name="fecha_contabilizacion", nullable=true)
/*  49:    */   private Date fechaContabilizacion;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  52:    */   private Asiento asiento;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_prefactura_cliente", nullable=true)
/*  55:    */   private PrefacturaCliente prefacturaCliente;
/*  56:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  57:    */   @JoinColumn(name="id_ajuste_prefactura_cliente_padre", nullable=true)
/*  58:    */   private AjustePrefacturaCliente ajustePrefacturaClientePadre;
/*  59:    */   @Column(name="activo", nullable=false)
/*  60:    */   private boolean activo;
/*  61:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ajustePrefacturaCliente")
/*  62: 85 */   private List<DetalleAjustePrefacturaCliente> listaDetalleAjustePrefacturaCliente = new ArrayList();
/*  63:    */   @Column(name="total", nullable=false, precision=12, scale=2)
/*  64:    */   @NotNull
/*  65:    */   @Digits(integer=12, fraction=2)
/*  66:    */   @Min(0L)
/*  67: 88 */   private BigDecimal total = BigDecimal.ZERO;
/*  68:    */   @Column(name="descuento", nullable=false, precision=12, scale=2)
/*  69:    */   @NotNull
/*  70:    */   @Digits(integer=12, fraction=2)
/*  71:    */   @Min(0L)
/*  72: 94 */   private BigDecimal descuento = BigDecimal.ZERO;
/*  73:    */   @Column(name="impuesto", nullable=false, precision=12, scale=2)
/*  74:    */   @NotNull
/*  75:    */   @Digits(integer=12, fraction=2)
/*  76:    */   @Min(0L)
/*  77:100 */   private BigDecimal impuesto = BigDecimal.ZERO;
/*  78:    */   @Transient
/*  79:    */   @Min(0L)
/*  80:106 */   private BigDecimal totalPrefactura = BigDecimal.ZERO;
/*  81:    */   
/*  82:    */   public int getId()
/*  83:    */   {
/*  84:117 */     return this.idAjustePrefacturaCliente;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getIdAjustePrefacturaCliente()
/*  88:    */   {
/*  89:126 */     return this.idAjustePrefacturaCliente;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setIdAjustePrefacturaCliente(int idAjustePrefacturaCliente)
/*  93:    */   {
/*  94:136 */     this.idAjustePrefacturaCliente = idAjustePrefacturaCliente;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int getIdOrganizacion()
/*  98:    */   {
/*  99:145 */     return this.idOrganizacion;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setIdOrganizacion(int idOrganizacion)
/* 103:    */   {
/* 104:155 */     this.idOrganizacion = idOrganizacion;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int getIdSucursal()
/* 108:    */   {
/* 109:164 */     return this.idSucursal;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setIdSucursal(int idSucursal)
/* 113:    */   {
/* 114:174 */     this.idSucursal = idSucursal;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Date getFecha()
/* 118:    */   {
/* 119:183 */     return this.fecha;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setFecha(Date fecha)
/* 123:    */   {
/* 124:193 */     this.fecha = fecha;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Asiento getAsiento()
/* 128:    */   {
/* 129:202 */     return this.asiento;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setAsiento(Asiento asiento)
/* 133:    */   {
/* 134:212 */     this.asiento = asiento;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public PrefacturaCliente getPrefacturaCliente()
/* 138:    */   {
/* 139:221 */     return this.prefacturaCliente;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setPrefacturaCliente(PrefacturaCliente prefacturaCliente)
/* 143:    */   {
/* 144:231 */     this.prefacturaCliente = prefacturaCliente;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean isActivo()
/* 148:    */   {
/* 149:240 */     return this.activo;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setActivo(boolean activo)
/* 153:    */   {
/* 154:250 */     this.activo = activo;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public List<DetalleAjustePrefacturaCliente> getListaDetalleAjustePrefacturaCliente()
/* 158:    */   {
/* 159:259 */     return this.listaDetalleAjustePrefacturaCliente;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setListaDetalleAjustePrefacturaCliente(List<DetalleAjustePrefacturaCliente> listaDetalleAjustePrefacturaCliente)
/* 163:    */   {
/* 164:269 */     this.listaDetalleAjustePrefacturaCliente = listaDetalleAjustePrefacturaCliente;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public BigDecimal getDescuento()
/* 168:    */   {
/* 169:278 */     return this.descuento;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setDescuento(BigDecimal descuento)
/* 173:    */   {
/* 174:288 */     this.descuento = descuento;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public BigDecimal getImpuesto()
/* 178:    */   {
/* 179:297 */     return this.impuesto;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setImpuesto(BigDecimal impuesto)
/* 183:    */   {
/* 184:307 */     this.impuesto = impuesto;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public BigDecimal getTotalPrefactura()
/* 188:    */   {
/* 189:316 */     this.totalPrefactura = getTotal().subtract(getDescuento()).add(getImpuesto());
/* 190:317 */     return this.totalPrefactura;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setTotalPrefactura(BigDecimal totalPrefactura)
/* 194:    */   {
/* 195:327 */     this.totalPrefactura = totalPrefactura;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public BigDecimal getTotal()
/* 199:    */   {
/* 200:336 */     return this.total;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setTotal(BigDecimal total)
/* 204:    */   {
/* 205:346 */     this.total = total;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public Date getFechaContabilizacion()
/* 209:    */   {
/* 210:355 */     return this.fechaContabilizacion;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/* 214:    */   {
/* 215:365 */     this.fechaContabilizacion = fechaContabilizacion;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public AjustePrefacturaCliente getAjustePrefacturaClientePadre()
/* 219:    */   {
/* 220:374 */     return this.ajustePrefacturaClientePadre;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setAjustePrefacturaClientePadre(AjustePrefacturaCliente ajustePrefacturaClientePadre)
/* 224:    */   {
/* 225:384 */     this.ajustePrefacturaClientePadre = ajustePrefacturaClientePadre;
/* 226:    */   }
/* 227:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.AjustePrefacturaCliente
 * JD-Core Version:    0.7.0.1
 */