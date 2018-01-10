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
/*  23:    */ import javax.validation.constraints.Min;
/*  24:    */ import javax.validation.constraints.NotNull;
/*  25:    */ import javax.validation.constraints.Size;
/*  26:    */ 
/*  27:    */ @Entity
/*  28:    */ @Table(name="pago_empleado")
/*  29:    */ public class PagoEmpleado
/*  30:    */   extends EntidadBase
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 3672034730713552089L;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="pago_empleado", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="pago_empleado")
/*  36:    */   @Column(name="id_pago_empleado")
/*  37:    */   private int idPagoEmpleado;
/*  38:    */   @Column(name="id_organizacion", nullable=false)
/*  39:    */   private int idOrganizacion;
/*  40:    */   @Column(name="id_sucursal", nullable=false)
/*  41:    */   private int idSucursal;
/*  42:    */   @Column(name="numero", nullable=false, length=20)
/*  43: 69 */   private String numero = "";
/*  44:    */   @Column(name="pdf", nullable=true)
/*  45:    */   @Size(max=50)
/*  46:    */   private String pdf;
/*  47:    */   @Temporal(TemporalType.DATE)
/*  48:    */   @Column(name="fecha", nullable=false)
/*  49:    */   @NotNull
/*  50:    */   private Date fecha;
/*  51:    */   @Temporal(TemporalType.DATE)
/*  52:    */   @Column(name="fecha_contabilizar", nullable=true)
/*  53:    */   private Date fechaContabilizar;
/*  54:    */   @Column(name="estado", nullable=false)
/*  55:    */   @Enumerated(EnumType.ORDINAL)
/*  56:    */   private Estado estado;
/*  57:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  58:    */   @Min(0L)
/*  59:    */   private BigDecimal valor;
/*  60:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  61:    */   @JoinColumn(name="id_documento", nullable=false)
/*  62:    */   @NotNull
/*  63:    */   private Documento documento;
/*  64:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  65:    */   @JoinColumn(name="id_pago_rol", nullable=false)
/*  66:    */   @NotNull
/*  67:    */   private PagoRol pagoRol;
/*  68:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  69:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  70:    */   private Asiento asiento;
/*  71:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  72:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion", nullable=false)
/*  73:    */   @NotNull
/*  74:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  75:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  76:    */   @JoinColumn(name="id_forma_pago", nullable=false)
/*  77:    */   @NotNull
/*  78:    */   private FormaPago formaPago;
/*  79:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="pagoEmpleado")
/*  80:120 */   private List<PagoRolEmpleado> listaPagoRolEmpleado = new ArrayList();
/*  81:    */   @Column(name="nombres", nullable=true, length=100)
/*  82:    */   private String nombres;
/*  83:    */   @Column(name="apellidos", nullable=true, length=100)
/*  84:    */   private String apellidos;
/*  85:    */   @Column(name="identificacion", nullable=true, length=50)
/*  86:    */   private String identificacion;
/*  87:    */   
/*  88:    */   public int getIdPagoEmpleado()
/*  89:    */   {
/*  90:150 */     return this.idPagoEmpleado;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setIdPagoEmpleado(int idPagoEmpleado)
/*  94:    */   {
/*  95:160 */     this.idPagoEmpleado = idPagoEmpleado;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Date getFecha()
/*  99:    */   {
/* 100:169 */     return this.fecha;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setFecha(Date fecha)
/* 104:    */   {
/* 105:179 */     this.fecha = fecha;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Date getFechaContabilizar()
/* 109:    */   {
/* 110:188 */     return this.fechaContabilizar;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setFechaContabilizar(Date fechaContabilizar)
/* 114:    */   {
/* 115:198 */     this.fechaContabilizar = fechaContabilizar;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Estado getEstado()
/* 119:    */   {
/* 120:207 */     return this.estado;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setEstado(Estado estado)
/* 124:    */   {
/* 125:217 */     this.estado = estado;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Documento getDocumento()
/* 129:    */   {
/* 130:226 */     return this.documento;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setDocumento(Documento documento)
/* 134:    */   {
/* 135:236 */     this.documento = documento;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public PagoRol getPagoRol()
/* 139:    */   {
/* 140:245 */     return this.pagoRol;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setPagoRol(PagoRol pagoRol)
/* 144:    */   {
/* 145:255 */     this.pagoRol = pagoRol;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Asiento getAsiento()
/* 149:    */   {
/* 150:264 */     return this.asiento;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setAsiento(Asiento asiento)
/* 154:    */   {
/* 155:274 */     this.asiento = asiento;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public List<PagoRolEmpleado> getListaPagoRolEmpleado()
/* 159:    */   {
/* 160:283 */     return this.listaPagoRolEmpleado;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setListaPagoRolEmpleado(List<PagoRolEmpleado> listaPagoRolEmpleado)
/* 164:    */   {
/* 165:293 */     this.listaPagoRolEmpleado = listaPagoRolEmpleado;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public int getIdOrganizacion()
/* 169:    */   {
/* 170:302 */     return this.idOrganizacion;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setIdOrganizacion(int idOrganizacion)
/* 174:    */   {
/* 175:312 */     this.idOrganizacion = idOrganizacion;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public int getIdSucursal()
/* 179:    */   {
/* 180:321 */     return this.idSucursal;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setIdSucursal(int idSucursal)
/* 184:    */   {
/* 185:331 */     this.idSucursal = idSucursal;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public BigDecimal getValor()
/* 189:    */   {
/* 190:340 */     return this.valor;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setValor(BigDecimal valor)
/* 194:    */   {
/* 195:350 */     this.valor = valor;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public String getNumero()
/* 199:    */   {
/* 200:359 */     return this.numero;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setNumero(String numero)
/* 204:    */   {
/* 205:369 */     this.numero = numero;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 209:    */   {
/* 210:378 */     return this.cuentaBancariaOrganizacion;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 214:    */   {
/* 215:388 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public FormaPago getFormaPago()
/* 219:    */   {
/* 220:397 */     return this.formaPago;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setFormaPago(FormaPago formaPago)
/* 224:    */   {
/* 225:407 */     this.formaPago = formaPago;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public int getId()
/* 229:    */   {
/* 230:417 */     return this.idPagoEmpleado;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public String getPdf()
/* 234:    */   {
/* 235:421 */     return this.pdf;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setPdf(String pdf)
/* 239:    */   {
/* 240:425 */     this.pdf = pdf;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public String getNombres()
/* 244:    */   {
/* 245:429 */     return this.nombres;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setNombres(String nombres)
/* 249:    */   {
/* 250:433 */     this.nombres = nombres;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public String getApellidos()
/* 254:    */   {
/* 255:437 */     return this.apellidos;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setApellidos(String apellidos)
/* 259:    */   {
/* 260:441 */     this.apellidos = apellidos;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public String getIdentificacion()
/* 264:    */   {
/* 265:445 */     return this.identificacion;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void setIdentificacion(String identificacion)
/* 269:    */   {
/* 270:449 */     this.identificacion = identificacion;
/* 271:    */   }
/* 272:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.PagoEmpleado
 * JD-Core Version:    0.7.0.1
 */