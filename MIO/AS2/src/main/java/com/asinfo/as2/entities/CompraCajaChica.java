/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*   4:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.JoinColumn;
/*  16:    */ import javax.persistence.ManyToOne;
/*  17:    */ import javax.persistence.OneToMany;
/*  18:    */ import javax.persistence.OneToOne;
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.TableGenerator;
/*  21:    */ import javax.persistence.Temporal;
/*  22:    */ import javax.persistence.TemporalType;
/*  23:    */ import javax.validation.constraints.Digits;
/*  24:    */ import javax.validation.constraints.Min;
/*  25:    */ import javax.validation.constraints.NotNull;
/*  26:    */ import javax.validation.constraints.Size;
/*  27:    */ import org.hibernate.annotations.ColumnDefault;
/*  28:    */ 
/*  29:    */ @Entity
/*  30:    */ @Table(name="compra_caja_chica")
/*  31:    */ public class CompraCajaChica
/*  32:    */   extends EntidadBase
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35:    */   @Id
/*  36:    */   @TableGenerator(name="compra_caja_chica", initialValue=0, allocationSize=50)
/*  37:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="compra_caja_chica")
/*  38:    */   @Column(name="id_compra_caja_chica")
/*  39:    */   private int idCompraCajaChica;
/*  40:    */   @Column(name="id_organizacion")
/*  41:    */   private int idOrganizacion;
/*  42:    */   @ManyToOne
/*  43:    */   @JoinColumn(name="id_sucursal")
/*  44:    */   private Sucursal sucursal;
/*  45:    */   @OneToOne(mappedBy="compraCajaChica", fetch=FetchType.EAGER)
/*  46:    */   private FacturaProveedorSRI facturaProveedorSRI;
/*  47:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  48:    */   @JoinColumn(name="id_caja_chica", nullable=false)
/*  49:    */   @NotNull
/*  50:    */   private CajaChica cajaChica;
/*  51:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  52:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  53:    */   private Empresa empresa;
/*  54:    */   @ManyToOne
/*  55:    */   @JoinColumn(name="id_direccion_empresa", nullable=true)
/*  56:    */   private DireccionEmpresa direccionEmpresa;
/*  57:    */   @Column(name="descripcion", length=200)
/*  58:    */   @Size(max=200)
/*  59:    */   private String descripcion;
/*  60:    */   @Temporal(TemporalType.DATE)
/*  61:    */   @Column(name="fecha", nullable=false)
/*  62:    */   @NotNull
/*  63:    */   private Date fecha;
/*  64:    */   @Temporal(TemporalType.DATE)
/*  65:    */   @Column(name="fecha_contabilizacion", nullable=true)
/*  66:    */   private Date fechaContabilizacion;
/*  67:    */   @Column(name="estado", nullable=true)
/*  68:    */   private Estado estado;
/*  69:    */   @Column(name="indicador_factura", nullable=true)
/*  70:    */   private boolean indicadorFactura;
/*  71:    */   @Column(name="documento_referencia", nullable=false, length=20)
/*  72:    */   @NotNull
/*  73:    */   @Size(max=20)
/*  74:    */   private String documentoReferencia;
/*  75:    */   @Column(name="valor", nullable=false, precision=12, scale=2)
/*  76:    */   @NotNull
/*  77:    */   @Digits(integer=12, fraction=2)
/*  78:108 */   private BigDecimal valor = BigDecimal.ZERO;
/*  79:    */   @Column(name="archivo", nullable=true)
/*  80:    */   @Size(max=50)
/*  81:    */   private String archivo;
/*  82:    */   @Column(name="descuento_impuesto", nullable=false, precision=12, scale=2)
/*  83:    */   @NotNull
/*  84:    */   @Digits(integer=12, fraction=2)
/*  85:    */   @Min(0L)
/*  86:    */   @ColumnDefault("0")
/*  87:117 */   private BigDecimal descuentoImpuesto = BigDecimal.ZERO;
/*  88:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="compraCajaChica")
/*  89:124 */   private List<DetalleCompraCajaChica> listaDetalleCompraCajaChica = new ArrayList();
/*  90:    */   
/*  91:    */   public int getIdCompraCajaChica()
/*  92:    */   {
/*  93:131 */     return this.idCompraCajaChica;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setIdCompraCajaChica(int idCompraCajaChica)
/*  97:    */   {
/*  98:135 */     this.idCompraCajaChica = idCompraCajaChica;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Sucursal getSucursal()
/* 102:    */   {
/* 103:139 */     return this.sucursal;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setSucursal(Sucursal sucursal)
/* 107:    */   {
/* 108:143 */     this.sucursal = sucursal;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public FacturaProveedorSRI getFacturaProveedorSRI()
/* 112:    */   {
/* 113:147 */     return this.facturaProveedorSRI;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setFacturaProveedorSRI(FacturaProveedorSRI facturaProveedorSRI)
/* 117:    */   {
/* 118:151 */     this.facturaProveedorSRI = facturaProveedorSRI;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public CajaChica getCajaChica()
/* 122:    */   {
/* 123:155 */     return this.cajaChica;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setCajaChica(CajaChica cajaChica)
/* 127:    */   {
/* 128:159 */     this.cajaChica = cajaChica;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Empresa getEmpresa()
/* 132:    */   {
/* 133:163 */     return this.empresa;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setEmpresa(Empresa empresa)
/* 137:    */   {
/* 138:167 */     this.empresa = empresa;
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
/* 151:    */   public Date getFecha()
/* 152:    */   {
/* 153:179 */     return this.fecha;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setFecha(Date fecha)
/* 157:    */   {
/* 158:183 */     this.fecha = fecha;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public Date getFechaContabilizacion()
/* 162:    */   {
/* 163:187 */     return this.fechaContabilizacion;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/* 167:    */   {
/* 168:191 */     this.fechaContabilizacion = fechaContabilizacion;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Estado getEstado()
/* 172:    */   {
/* 173:195 */     return this.estado;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setEstado(Estado estado)
/* 177:    */   {
/* 178:199 */     this.estado = estado;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String getDocumentoReferencia()
/* 182:    */   {
/* 183:203 */     return this.documentoReferencia;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setDocumentoReferencia(String documentoReferencia)
/* 187:    */   {
/* 188:207 */     this.documentoReferencia = documentoReferencia;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public BigDecimal getValor()
/* 192:    */   {
/* 193:211 */     return this.valor;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setValor(BigDecimal valor)
/* 197:    */   {
/* 198:215 */     this.valor = valor;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public List<DetalleCompraCajaChica> getListaDetalleCompraCajaChica()
/* 202:    */   {
/* 203:219 */     return this.listaDetalleCompraCajaChica;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setListaDetalleCompraCajaChica(List<DetalleCompraCajaChica> listaDetalleCompraCajaChica)
/* 207:    */   {
/* 208:223 */     this.listaDetalleCompraCajaChica = listaDetalleCompraCajaChica;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public DireccionEmpresa getDireccionEmpresa()
/* 212:    */   {
/* 213:227 */     if (this.direccionEmpresa == null) {
/* 214:228 */       this.direccionEmpresa = new DireccionEmpresa();
/* 215:    */     }
/* 216:230 */     return this.direccionEmpresa;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setDireccionEmpresa(DireccionEmpresa direccionEmpresa)
/* 220:    */   {
/* 221:234 */     this.direccionEmpresa = direccionEmpresa;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public boolean isIndicadorFactura()
/* 225:    */   {
/* 226:238 */     return this.indicadorFactura;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setIndicadorFactura(boolean indicadorFactura)
/* 230:    */   {
/* 231:242 */     this.indicadorFactura = indicadorFactura;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public int getId()
/* 235:    */   {
/* 236:252 */     return getIdCompraCajaChica();
/* 237:    */   }
/* 238:    */   
/* 239:    */   public int getIdOrganizacion()
/* 240:    */   {
/* 241:256 */     return this.idOrganizacion;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setIdOrganizacion(int idOrganizacion)
/* 245:    */   {
/* 246:260 */     this.idOrganizacion = idOrganizacion;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public String getArchivo()
/* 250:    */   {
/* 251:264 */     return this.archivo;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setArchivo(String archivo)
/* 255:    */   {
/* 256:268 */     this.archivo = archivo;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public BigDecimal getDescuentoImpuesto()
/* 260:    */   {
/* 261:272 */     return this.descuentoImpuesto;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setDescuentoImpuesto(BigDecimal descuentoImpuesto)
/* 265:    */   {
/* 266:276 */     this.descuentoImpuesto = descuentoImpuesto;
/* 267:    */   }
/* 268:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CompraCajaChica
 * JD-Core Version:    0.7.0.1
 */