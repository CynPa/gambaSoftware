/*   1:    */ package com.asinfo.as2.entities.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DatoFiltro;
/*   4:    */ import com.asinfo.as2.entities.CuentaContable;
/*   5:    */ import com.asinfo.as2.entities.EntidadBase;
/*   6:    */ import com.asinfo.as2.enumeraciones.TipoConceptoRetencion;
/*   7:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*   8:    */ import java.math.BigDecimal;
/*   9:    */ import java.text.SimpleDateFormat;
/*  10:    */ import java.util.Date;
/*  11:    */ import javax.persistence.Column;
/*  12:    */ import javax.persistence.Entity;
/*  13:    */ import javax.persistence.EnumType;
/*  14:    */ import javax.persistence.Enumerated;
/*  15:    */ import javax.persistence.FetchType;
/*  16:    */ import javax.persistence.GeneratedValue;
/*  17:    */ import javax.persistence.GenerationType;
/*  18:    */ import javax.persistence.Id;
/*  19:    */ import javax.persistence.JoinColumn;
/*  20:    */ import javax.persistence.ManyToOne;
/*  21:    */ import javax.persistence.Table;
/*  22:    */ import javax.persistence.TableGenerator;
/*  23:    */ import javax.persistence.Temporal;
/*  24:    */ import javax.persistence.TemporalType;
/*  25:    */ import javax.validation.constraints.Max;
/*  26:    */ import javax.validation.constraints.Min;
/*  27:    */ import javax.validation.constraints.NotNull;
/*  28:    */ import javax.validation.constraints.Size;
/*  29:    */ 
/*  30:    */ @Entity
/*  31:    */ @Table(name="concepto_retencionSRI")
/*  32:    */ public class ConceptoRetencionSRI
/*  33:    */   extends EntidadBase
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = 1L;
/*  36:    */   @Id
/*  37:    */   @TableGenerator(name="concepto_retencionSRI", initialValue=0, allocationSize=50)
/*  38:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="concepto_retencionSRI")
/*  39:    */   @Column(name="id_concepto_retencionSRI")
/*  40:    */   private int idConceptoRetencionSRI;
/*  41:    */   @Column(name="id_organizacion", nullable=false)
/*  42:    */   private int idOrganizacion;
/*  43:    */   @Column(name="id_sucursal", nullable=false)
/*  44:    */   private int idSucursal;
/*  45:    */   @Column(name="codigo", length=5, nullable=false)
/*  46:    */   @NotNull
/*  47:    */   @Size(min=1, max=5)
/*  48:    */   private String codigo;
/*  49:    */   @Column(name="nombre", length=200, nullable=false)
/*  50:    */   @NotNull
/*  51:    */   @Size(min=2, max=200)
/*  52:    */   private String nombre;
/*  53:    */   @Column(name="descripcion", length=200)
/*  54:    */   @Size(max=200)
/*  55:    */   private String descripcion;
/*  56:    */   @Column(name="activo", nullable=false)
/*  57:    */   private boolean activo;
/*  58:    */   @Column(name="predeterminado", nullable=false)
/*  59:    */   private boolean predeterminado;
/*  60:    */   @Temporal(TemporalType.DATE)
/*  61:    */   @Column(name="fecha_desde", nullable=false, length=23)
/*  62:    */   @NotNull
/*  63:    */   private Date fechaDesde;
/*  64:    */   @Temporal(TemporalType.DATE)
/*  65:    */   @Column(name="fecha_hasta", nullable=true, length=23)
/*  66:    */   private Date fechaHasta;
/*  67:    */   @Column(name="porcentaje")
/*  68:    */   @Min(0L)
/*  69:    */   @Max(100L)
/*  70:    */   private BigDecimal porcentaje;
/*  71:    */   @Column(name="ingresa_porcentaje", nullable=false)
/*  72:    */   private boolean ingresaPorcentaje;
/*  73:    */   @Column(name="tipo_concepto_retencion", nullable=false)
/*  74:    */   @Enumerated(EnumType.ORDINAL)
/*  75:    */   private TipoConceptoRetencion tipoConceptoRetencion;
/*  76:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  77:    */   @JoinColumn(name="id_cuenta_contable", nullable=true)
/*  78:    */   private CuentaContable cuentaContable;
/*  79:    */   @Enumerated(EnumType.ORDINAL)
/*  80:    */   @Column(name="tipo_producto", nullable=true)
/*  81:    */   private TipoProducto tipoProducto;
/*  82:    */   
/*  83:    */   public ConceptoRetencionSRI() {}
/*  84:    */   
/*  85:    */   public ConceptoRetencionSRI(int idConceptoRetencionSRI, String codigo, String nombre, BigDecimal porcentaje, boolean ingresaPorcentaje)
/*  86:    */   {
/*  87:119 */     this.idConceptoRetencionSRI = idConceptoRetencionSRI;
/*  88:120 */     this.codigo = codigo;
/*  89:121 */     this.nombre = nombre;
/*  90:122 */     this.porcentaje = porcentaje;
/*  91:123 */     this.ingresaPorcentaje = ingresaPorcentaje;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getIdConceptoRetencionSRI()
/*  95:    */   {
/*  96:128 */     return this.idConceptoRetencionSRI;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setIdConceptoRetencionSRI(int idConceptoRetencionSRI)
/* 100:    */   {
/* 101:132 */     this.idConceptoRetencionSRI = idConceptoRetencionSRI;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int getIdOrganizacion()
/* 105:    */   {
/* 106:136 */     return this.idOrganizacion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setIdOrganizacion(int idOrganizacion)
/* 110:    */   {
/* 111:140 */     this.idOrganizacion = idOrganizacion;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getIdSucursal()
/* 115:    */   {
/* 116:144 */     return this.idSucursal;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setIdSucursal(int idSucursal)
/* 120:    */   {
/* 121:148 */     this.idSucursal = idSucursal;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String getCodigo()
/* 125:    */   {
/* 126:152 */     return this.codigo;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setCodigo(String codigo)
/* 130:    */   {
/* 131:156 */     this.codigo = codigo;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String getNombre()
/* 135:    */   {
/* 136:160 */     return this.nombre;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setNombre(String nombre)
/* 140:    */   {
/* 141:164 */     this.nombre = nombre;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String getDescripcion()
/* 145:    */   {
/* 146:168 */     return this.descripcion;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setDescripcion(String descripcion)
/* 150:    */   {
/* 151:172 */     this.descripcion = descripcion;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public boolean isActivo()
/* 155:    */   {
/* 156:176 */     return this.activo;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setActivo(boolean activo)
/* 160:    */   {
/* 161:180 */     this.activo = activo;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public boolean isPredeterminado()
/* 165:    */   {
/* 166:184 */     return this.predeterminado;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setPredeterminado(boolean predeterminado)
/* 170:    */   {
/* 171:188 */     this.predeterminado = predeterminado;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Date getFechaDesde()
/* 175:    */   {
/* 176:192 */     return this.fechaDesde;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setFechaDesde(Date fechaDesde)
/* 180:    */   {
/* 181:196 */     this.fechaDesde = fechaDesde;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public Date getFechaHasta()
/* 185:    */   {
/* 186:200 */     return this.fechaHasta;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setFechaHasta(Date fechaHasta)
/* 190:    */   {
/* 191:204 */     this.fechaHasta = fechaHasta;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public BigDecimal getPorcentaje()
/* 195:    */   {
/* 196:208 */     return this.porcentaje;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setPorcentaje(BigDecimal porcentaje)
/* 200:    */   {
/* 201:212 */     this.porcentaje = porcentaje;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public boolean isIngresaPorcentaje()
/* 205:    */   {
/* 206:216 */     return this.ingresaPorcentaje;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setIngresaPorcentaje(boolean ingresaPorcentaje)
/* 210:    */   {
/* 211:220 */     this.ingresaPorcentaje = ingresaPorcentaje;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public TipoConceptoRetencion getTipoConceptoRetencion()
/* 215:    */   {
/* 216:229 */     return this.tipoConceptoRetencion;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setTipoConceptoRetencion(TipoConceptoRetencion tipoConceptoRetencion)
/* 220:    */   {
/* 221:240 */     this.tipoConceptoRetencion = tipoConceptoRetencion;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public CuentaContable getCuentaContable()
/* 225:    */   {
/* 226:249 */     return this.cuentaContable;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 230:    */   {
/* 231:259 */     this.cuentaContable = cuentaContable;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public int getId()
/* 235:    */   {
/* 236:264 */     return getIdConceptoRetencionSRI();
/* 237:    */   }
/* 238:    */   
/* 239:    */   public String toString()
/* 240:    */   {
/* 241:269 */     return this.codigo;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public TipoProducto getTipoProducto()
/* 245:    */   {
/* 246:273 */     return this.tipoProducto;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setTipoProducto(TipoProducto tipoProducto)
/* 250:    */   {
/* 251:277 */     this.tipoProducto = tipoProducto;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public String getClave()
/* 255:    */   {
/* 256:281 */     return getCodigo() + (this.tipoProducto == null ? "" : new StringBuilder().append("~").append(this.tipoProducto.toString()).toString());
/* 257:    */   }
/* 258:    */   
/* 259:    */   public String getClaveUpdateXML()
/* 260:    */   {
/* 261:285 */     return getCodigo() + " - " + getIdOrganizacion() + " - " + DatoFiltro.dateFormat.format(getFechaDesde());
/* 262:    */   }
/* 263:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.ConceptoRetencionSRI
 * JD-Core Version:    0.7.0.1
 */