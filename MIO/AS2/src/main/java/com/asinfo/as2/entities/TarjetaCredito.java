/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Date;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.OneToMany;
/*  15:    */ import javax.persistence.OneToOne;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.persistence.Temporal;
/*  19:    */ import javax.persistence.TemporalType;
/*  20:    */ import javax.persistence.Transient;
/*  21:    */ import javax.validation.constraints.NotNull;
/*  22:    */ import javax.validation.constraints.Size;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="tarjeta_credito")
/*  26:    */ public class TarjetaCredito
/*  27:    */   extends EntidadBase
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="tarjeta_credito", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tarjeta_credito")
/*  33:    */   @Column(name="id_tarjeta_credito")
/*  34:    */   private int idTarjetaCredito;
/*  35:    */   @Column(name="id_organizacion", nullable=false)
/*  36:    */   private int idOrganizacion;
/*  37:    */   @Column(name="id_sucursal", nullable=false)
/*  38:    */   private int idSucursal;
/*  39:    */   @Column(name="nombre", length=50, nullable=false)
/*  40:    */   @NotNull
/*  41:    */   @Size(min=2, max=50)
/*  42:    */   private String nombre;
/*  43:    */   @Column(name="descripcion", length=200)
/*  44:    */   @Size(max=200)
/*  45:    */   private String descripcion;
/*  46:    */   @Column(name="activo", nullable=false)
/*  47:    */   private boolean activo;
/*  48:    */   @Column(name="predeterminado", nullable=false)
/*  49:    */   private boolean predeterminado;
/*  50:    */   @Temporal(TemporalType.DATE)
/*  51:    */   @Column(name="fecha_desde", nullable=true, length=23)
/*  52:    */   private Date fechaDesde;
/*  53:    */   @Temporal(TemporalType.DATE)
/*  54:    */   @Column(name="fecha_hasta", nullable=true, length=23)
/*  55:    */   private Date fechaHasta;
/*  56:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  57:    */   @JoinColumn(name="id_tipo_tarjeta_credito", nullable=true)
/*  58:    */   private TipoTarjetaCredito tipoTarjetaCredito;
/*  59:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  60:    */   @JoinColumn(name="id_banco", nullable=true)
/*  61:    */   private Banco banco;
/*  62:    */   @NotNull
/*  63:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  64:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion", nullable=false)
/*  65:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  66:    */   @OneToOne(fetch=FetchType.LAZY)
/*  67:    */   @JoinColumn(name="id_cuenta_contable_retentencion_fuente", nullable=true)
/*  68:    */   private CuentaContable cuentaContableRetencionFuente;
/*  69:    */   @OneToOne(fetch=FetchType.LAZY)
/*  70:    */   @JoinColumn(name="id_cuenta_contable_retentencion_iva", nullable=true)
/*  71:    */   private CuentaContable cuentaContableRetencionIva;
/*  72:    */   @OneToOne(fetch=FetchType.LAZY)
/*  73:    */   @JoinColumn(name="id_cuenta_contable_iva_comision", nullable=true)
/*  74:    */   private CuentaContable cuentaContableIvaComision;
/*  75:    */   @OneToMany(cascade={javax.persistence.CascadeType.DETACH}, fetch=FetchType.LAZY, mappedBy="tarjetaCredito")
/*  76:116 */   private List<VersionComision> listaVersionComision = new ArrayList();
/*  77:    */   @Transient
/*  78:119 */   protected List<PlanTarjetaCredito> listaPlanTarjetaCredito = new ArrayList();
/*  79:    */   
/*  80:    */   public int getIdTarjetaCredito()
/*  81:    */   {
/*  82:124 */     return this.idTarjetaCredito;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getId()
/*  86:    */   {
/*  87:129 */     return this.idTarjetaCredito;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdTarjetaCredito(int idTarjetaCredito)
/*  91:    */   {
/*  92:133 */     this.idTarjetaCredito = idTarjetaCredito;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getIdOrganizacion()
/*  96:    */   {
/*  97:137 */     return this.idOrganizacion;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setIdOrganizacion(int idOrganizacion)
/* 101:    */   {
/* 102:141 */     this.idOrganizacion = idOrganizacion;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int getIdSucursal()
/* 106:    */   {
/* 107:145 */     return this.idSucursal;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setIdSucursal(int idSucursal)
/* 111:    */   {
/* 112:149 */     this.idSucursal = idSucursal;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String getNombre()
/* 116:    */   {
/* 117:161 */     return this.nombre;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setNombre(String nombre)
/* 121:    */   {
/* 122:165 */     this.nombre = nombre;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String getDescripcion()
/* 126:    */   {
/* 127:169 */     return this.descripcion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setDescripcion(String descripcion)
/* 131:    */   {
/* 132:173 */     this.descripcion = descripcion;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public boolean isActivo()
/* 136:    */   {
/* 137:177 */     return this.activo;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setActivo(boolean activo)
/* 141:    */   {
/* 142:181 */     this.activo = activo;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean isPredeterminado()
/* 146:    */   {
/* 147:185 */     return this.predeterminado;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setPredeterminado(boolean predeterminado)
/* 151:    */   {
/* 152:189 */     this.predeterminado = predeterminado;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Date getFechaDesde()
/* 156:    */   {
/* 157:193 */     return this.fechaDesde;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setFechaDesde(Date fechaDesde)
/* 161:    */   {
/* 162:197 */     this.fechaDesde = fechaDesde;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public Date getFechaHasta()
/* 166:    */   {
/* 167:201 */     return this.fechaHasta;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setFechaHasta(Date fechaHasta)
/* 171:    */   {
/* 172:205 */     this.fechaHasta = fechaHasta;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String toString()
/* 176:    */   {
/* 177:210 */     return this.nombre;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public TipoTarjetaCredito getTipoTarjetaCredito()
/* 181:    */   {
/* 182:214 */     return this.tipoTarjetaCredito;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setTipoTarjetaCredito(TipoTarjetaCredito tipoTarjetaCredito)
/* 186:    */   {
/* 187:218 */     this.tipoTarjetaCredito = tipoTarjetaCredito;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public Banco getBanco()
/* 191:    */   {
/* 192:222 */     return this.banco;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setBanco(Banco banco)
/* 196:    */   {
/* 197:226 */     this.banco = banco;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 201:    */   {
/* 202:230 */     return this.cuentaBancariaOrganizacion;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 206:    */   {
/* 207:234 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public List<VersionComision> getListaVersionComision()
/* 211:    */   {
/* 212:238 */     return this.listaVersionComision;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setListaVersionComision(List<VersionComision> listaVersionComision)
/* 216:    */   {
/* 217:242 */     this.listaVersionComision = listaVersionComision;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public List<PlanTarjetaCredito> getListaPlanTarjetaCredito()
/* 221:    */   {
/* 222:246 */     return this.listaPlanTarjetaCredito;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setListaPlanTarjetaCredito(List<PlanTarjetaCredito> listaPlanTarjetaCredito)
/* 226:    */   {
/* 227:250 */     this.listaPlanTarjetaCredito = listaPlanTarjetaCredito;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public CuentaContable getCuentaContableRetencionFuente()
/* 231:    */   {
/* 232:254 */     return this.cuentaContableRetencionFuente;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setCuentaContableRetencionFuente(CuentaContable cuentaContableRetencionFuente)
/* 236:    */   {
/* 237:258 */     this.cuentaContableRetencionFuente = cuentaContableRetencionFuente;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public CuentaContable getCuentaContableRetencionIva()
/* 241:    */   {
/* 242:262 */     return this.cuentaContableRetencionIva;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setCuentaContableRetencionIva(CuentaContable cuentaContableRetencionIva)
/* 246:    */   {
/* 247:266 */     this.cuentaContableRetencionIva = cuentaContableRetencionIva;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public CuentaContable getCuentaContableIvaComision()
/* 251:    */   {
/* 252:270 */     return this.cuentaContableIvaComision;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setCuentaContableIvaComision(CuentaContable cuentaContableIvaComision)
/* 256:    */   {
/* 257:274 */     this.cuentaContableIvaComision = cuentaContableIvaComision;
/* 258:    */   }
/* 259:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TarjetaCredito
 * JD-Core Version:    0.7.0.1
 */