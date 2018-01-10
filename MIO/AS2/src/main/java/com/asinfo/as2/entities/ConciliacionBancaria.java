/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.EnumType;
/*  11:    */ import javax.persistence.Enumerated;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.OneToMany;
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.TableGenerator;
/*  21:    */ import javax.persistence.Temporal;
/*  22:    */ import javax.persistence.TemporalType;
/*  23:    */ import javax.persistence.Transient;
/*  24:    */ import javax.validation.constraints.Digits;
/*  25:    */ import javax.validation.constraints.NotNull;
/*  26:    */ 
/*  27:    */ @Entity
/*  28:    */ @Table(name="conciliacion_bancaria")
/*  29:    */ public class ConciliacionBancaria
/*  30:    */   extends EntidadBase
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="conciliacion_bancaria", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="conciliacion_bancaria")
/*  36:    */   @Column(name="id_conciliacion_bancaria")
/*  37:    */   private int idConciliacionBancaria;
/*  38:    */   @Column(name="id_organizacion", nullable=false)
/*  39:    */   private int idOrganizacion;
/*  40:    */   @ManyToOne
/*  41:    */   @JoinColumn(name="id_sucursal")
/*  42:    */   private Sucursal sucursal;
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion")
/*  45:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  48:    */   private Asiento asiento;
/*  49:    */   @OneToMany(mappedBy="conciliacionBancaria")
/*  50: 73 */   private List<MovimientoBancario> listaMovimientoBancario = new ArrayList();
/*  51:    */   @Temporal(TemporalType.DATE)
/*  52:    */   @Column(name="fecha", nullable=false)
/*  53:    */   @NotNull
/*  54:    */   private Date fecha;
/*  55:    */   @Column(name="saldo_bancos", nullable=false, precision=12, scale=2)
/*  56:    */   @Digits(integer=12, fraction=2)
/*  57: 81 */   private BigDecimal saldoBancos = BigDecimal.ZERO;
/*  58:    */   @Column(name="estado", nullable=false)
/*  59:    */   @Enumerated(EnumType.ORDINAL)
/*  60:    */   private Estado estado;
/*  61:    */   @Transient
/*  62:    */   private String traBanco;
/*  63:    */   @Transient
/*  64:    */   private String traNumeroCuentaBancariaOrganizacion;
/*  65:    */   @Transient
/*  66:    */   private int traIdCuentaContableBanco;
/*  67:    */   @Transient
/*  68:    */   private boolean traEditable;
/*  69:    */   
/*  70:    */   public ConciliacionBancaria() {}
/*  71:    */   
/*  72:    */   public ConciliacionBancaria(int idConciliacionBancaria, BigDecimal saldoBancos, Date fecha)
/*  73:    */   {
/*  74:106 */     this.idConciliacionBancaria = idConciliacionBancaria;
/*  75:107 */     this.fecha = fecha;
/*  76:108 */     this.saldoBancos = saldoBancos;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public ConciliacionBancaria(int idConciliacionBancaria, Date fecha, BigDecimal saldoBancos, String traBanco, String traNumeroCuentaBancariaOrganizacion, int traIdCuentaContableBanco, Estado estado)
/*  80:    */   {
/*  81:122 */     this.idConciliacionBancaria = idConciliacionBancaria;
/*  82:123 */     this.fecha = fecha;
/*  83:124 */     this.saldoBancos = saldoBancos;
/*  84:125 */     this.traBanco = traBanco;
/*  85:126 */     this.traNumeroCuentaBancariaOrganizacion = traNumeroCuentaBancariaOrganizacion;
/*  86:127 */     this.traIdCuentaContableBanco = traIdCuentaContableBanco;
/*  87:128 */     this.estado = estado;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getIdConciliacionBancaria()
/*  91:    */   {
/*  92:133 */     return this.idConciliacionBancaria;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setIdConciliacionBancaria(int idConciliacionBancaria)
/*  96:    */   {
/*  97:137 */     this.idConciliacionBancaria = idConciliacionBancaria;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Sucursal getSucursal()
/* 101:    */   {
/* 102:141 */     return this.sucursal;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setSucursal(Sucursal sucursal)
/* 106:    */   {
/* 107:145 */     this.sucursal = sucursal;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public List<MovimientoBancario> getListaMovimientoBancario()
/* 111:    */   {
/* 112:149 */     return this.listaMovimientoBancario;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setListaMovimientoBancario(List<MovimientoBancario> listaMovimientoBancario)
/* 116:    */   {
/* 117:154 */     this.listaMovimientoBancario = listaMovimientoBancario;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Date getFecha()
/* 121:    */   {
/* 122:158 */     return this.fecha;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setFecha(Date fecha)
/* 126:    */   {
/* 127:162 */     this.fecha = fecha;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public BigDecimal getSaldoBancos()
/* 131:    */   {
/* 132:166 */     return this.saldoBancos;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setSaldoBancos(BigDecimal saldoBancos)
/* 136:    */   {
/* 137:170 */     this.saldoBancos = saldoBancos;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 141:    */   {
/* 142:174 */     return this.cuentaBancariaOrganizacion;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 146:    */   {
/* 147:179 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Estado getEstado()
/* 151:    */   {
/* 152:183 */     return this.estado;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setEstado(Estado estado)
/* 156:    */   {
/* 157:187 */     this.estado = estado;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String getTraBanco()
/* 161:    */   {
/* 162:191 */     return this.traBanco;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setTraBanco(String traBanco)
/* 166:    */   {
/* 167:195 */     this.traBanco = traBanco;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String getTraNumeroCuentaBancariaOrganizacion()
/* 171:    */   {
/* 172:199 */     return this.traNumeroCuentaBancariaOrganizacion;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setTraNumeroCuentaBancariaOrganizacion(String traNumeroCuentaBancariaOrganizacion)
/* 176:    */   {
/* 177:204 */     this.traNumeroCuentaBancariaOrganizacion = traNumeroCuentaBancariaOrganizacion;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public int getTraIdCuentaContableBanco()
/* 181:    */   {
/* 182:208 */     return this.traIdCuentaContableBanco;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setTraIdCuentaContableBanco(int traIdCuentaContableBanco)
/* 186:    */   {
/* 187:212 */     this.traIdCuentaContableBanco = traIdCuentaContableBanco;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean isTraEditable()
/* 191:    */   {
/* 192:216 */     if (getEstado() == Estado.ELABORADO) {
/* 193:217 */       this.traEditable = true;
/* 194:    */     } else {
/* 195:219 */       this.traEditable = false;
/* 196:    */     }
/* 197:221 */     return this.traEditable;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setTraEditable(boolean traEditable)
/* 201:    */   {
/* 202:225 */     this.traEditable = traEditable;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public int getId()
/* 206:    */   {
/* 207:235 */     return getIdConciliacionBancaria();
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Asiento getAsiento()
/* 211:    */   {
/* 212:239 */     return this.asiento;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setAsiento(Asiento asiento)
/* 216:    */   {
/* 217:243 */     this.asiento = asiento;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public int getIdOrganizacion()
/* 221:    */   {
/* 222:247 */     return this.idOrganizacion;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setIdOrganizacion(int idOrganizacion)
/* 226:    */   {
/* 227:251 */     this.idOrganizacion = idOrganizacion;
/* 228:    */   }
/* 229:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ConciliacionBancaria
 * JD-Core Version:    0.7.0.1
 */