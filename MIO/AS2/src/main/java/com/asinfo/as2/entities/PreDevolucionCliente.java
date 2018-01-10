/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   4:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.persistence.Column;
/*  11:    */ import javax.persistence.Entity;
/*  12:    */ import javax.persistence.EnumType;
/*  13:    */ import javax.persistence.Enumerated;
/*  14:    */ import javax.persistence.FetchType;
/*  15:    */ import javax.persistence.GeneratedValue;
/*  16:    */ import javax.persistence.GenerationType;
/*  17:    */ import javax.persistence.Id;
/*  18:    */ import javax.persistence.JoinColumn;
/*  19:    */ import javax.persistence.ManyToOne;
/*  20:    */ import javax.persistence.OneToMany;
/*  21:    */ import javax.persistence.OneToOne;
/*  22:    */ import javax.persistence.Table;
/*  23:    */ import javax.persistence.TableGenerator;
/*  24:    */ import javax.persistence.Temporal;
/*  25:    */ import javax.persistence.TemporalType;
/*  26:    */ import javax.persistence.Transient;
/*  27:    */ import javax.validation.constraints.Digits;
/*  28:    */ import javax.validation.constraints.Min;
/*  29:    */ import javax.validation.constraints.NotNull;
/*  30:    */ import javax.validation.constraints.Size;
/*  31:    */ 
/*  32:    */ @Entity
/*  33:    */ @Table(name="pre_devolucion_cliente", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_pre_devolucion_cliente"})})
/*  34:    */ public class PreDevolucionCliente
/*  35:    */   extends EntidadBase
/*  36:    */   implements Serializable
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = 1L;
/*  39:    */   @Id
/*  40:    */   @TableGenerator(name="pre_devolucion_cliente", initialValue=0, allocationSize=50)
/*  41:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="pre_devolucion_cliente")
/*  42:    */   @Column(name="id_pre_devolucion_cliente")
/*  43:    */   private int idPreDevolucionCliente;
/*  44:    */   @Column(name="id_organizacion", nullable=false)
/*  45:    */   @NotNull
/*  46:    */   private int idOrganizacion;
/*  47:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  48:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  49:    */   private Sucursal sucursal;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_empresa", nullable=false)
/*  52:    */   @NotNull
/*  53:    */   private Empresa empresa;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_subempresa", nullable=true)
/*  56:    */   private Subempresa subempresa;
/*  57:    */   @OneToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_transportista", nullable=true)
/*  59:    */   private EntidadUsuario transportista;
/*  60:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  61:    */   @JoinColumn(name="id_motivo_nota_credito_cliente", nullable=true)
/*  62:    */   private MotivoNotaCreditoCliente motivoNotaCreditoCliente;
/*  63:    */   @Column(name="estado", nullable=false)
/*  64:    */   @Enumerated(EnumType.ORDINAL)
/*  65:    */   private Estado estado;
/*  66:    */   @Temporal(TemporalType.DATE)
/*  67:    */   @Column(name="fecha", nullable=false)
/*  68:    */   @NotNull
/*  69:    */   private Date fecha;
/*  70:    */   @Temporal(TemporalType.DATE)
/*  71:    */   @Column(name="fecha_recepcion", nullable=true)
/*  72:    */   private Date fechaRecepcion;
/*  73:    */   @Column(name="total", nullable=false, precision=12, scale=2)
/*  74:    */   @Digits(integer=12, fraction=2)
/*  75:    */   @Min(0L)
/*  76: 90 */   private BigDecimal total = BigDecimal.ZERO;
/*  77:    */   @Column(name="nota_transportista", nullable=true, length=500)
/*  78:    */   @Size(max=500)
/*  79:    */   private String notaTransportista;
/*  80:    */   @Column(name="nota", nullable=true, length=500)
/*  81:    */   @Size(max=500)
/*  82:    */   private String nota;
/*  83:    */   @Column(name="referencia8", nullable=true, length=500)
/*  84:    */   @Size(max=500)
/*  85:    */   private String referencia8;
/*  86:    */   @Column(name="codigo_movil", nullable=true, length=200)
/*  87:    */   @Size(max=200)
/*  88:    */   private String codigoMovil;
/*  89:    */   @Column(name="id_factura_cliente_padre", nullable=true)
/*  90:    */   private Integer idFacturaClientePadre;
/*  91:    */   @Transient
/*  92:    */   private boolean indicadorProcesar;
/*  93:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  94:    */   @JoinColumn(name="id_recepcion_devolucion_transportista", nullable=true)
/*  95:    */   private RecepcionDevolucionTransportista recepcionDevolucionTransportista;
/*  96:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="preDevolucionCliente")
/*  97:123 */   private List<DetallePreDevolucionCliente> listaDetallePreDevolucionCliente = new ArrayList();
/*  98:    */   
/*  99:    */   public int getId()
/* 100:    */   {
/* 101:139 */     return this.idPreDevolucionCliente;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int getIdPreDevolucionCliente()
/* 105:    */   {
/* 106:146 */     return this.idPreDevolucionCliente;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setIdPreDevolucionCliente(int idPreDevolucionCliente)
/* 110:    */   {
/* 111:154 */     this.idPreDevolucionCliente = idPreDevolucionCliente;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getIdOrganizacion()
/* 115:    */   {
/* 116:161 */     return this.idOrganizacion;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setIdOrganizacion(int idOrganizacion)
/* 120:    */   {
/* 121:169 */     this.idOrganizacion = idOrganizacion;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Sucursal getSucursal()
/* 125:    */   {
/* 126:176 */     return this.sucursal;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setSucursal(Sucursal sucursal)
/* 130:    */   {
/* 131:184 */     this.sucursal = sucursal;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Empresa getEmpresa()
/* 135:    */   {
/* 136:191 */     return this.empresa;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setEmpresa(Empresa empresa)
/* 140:    */   {
/* 141:199 */     this.empresa = empresa;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public Subempresa getSubempresa()
/* 145:    */   {
/* 146:206 */     return this.subempresa;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setSubempresa(Subempresa subempresa)
/* 150:    */   {
/* 151:214 */     this.subempresa = subempresa;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public EntidadUsuario getTransportista()
/* 155:    */   {
/* 156:221 */     return this.transportista;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setTransportista(EntidadUsuario transportista)
/* 160:    */   {
/* 161:229 */     this.transportista = transportista;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public MotivoNotaCreditoCliente getMotivoNotaCreditoCliente()
/* 165:    */   {
/* 166:236 */     return this.motivoNotaCreditoCliente;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setMotivoNotaCreditoCliente(MotivoNotaCreditoCliente motivoNotaCreditoCliente)
/* 170:    */   {
/* 171:244 */     this.motivoNotaCreditoCliente = motivoNotaCreditoCliente;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Estado getEstado()
/* 175:    */   {
/* 176:251 */     return this.estado;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setEstado(Estado estado)
/* 180:    */   {
/* 181:259 */     this.estado = estado;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public Date getFecha()
/* 185:    */   {
/* 186:266 */     return this.fecha;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setFecha(Date fecha)
/* 190:    */   {
/* 191:274 */     this.fecha = fecha;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public Date getFechaRecepcion()
/* 195:    */   {
/* 196:281 */     return this.fechaRecepcion;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setFechaRecepcion(Date fechaRecepcion)
/* 200:    */   {
/* 201:289 */     this.fechaRecepcion = fechaRecepcion;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public BigDecimal getTotal()
/* 205:    */   {
/* 206:296 */     return this.total;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setTotal(BigDecimal total)
/* 210:    */   {
/* 211:304 */     this.total = total;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public String getNotaTransportista()
/* 215:    */   {
/* 216:311 */     return this.notaTransportista;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setNotaTransportista(String notaTransportista)
/* 220:    */   {
/* 221:319 */     this.notaTransportista = notaTransportista;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public String getNota()
/* 225:    */   {
/* 226:326 */     return this.nota;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setNota(String nota)
/* 230:    */   {
/* 231:334 */     this.nota = nota;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public List<DetallePreDevolucionCliente> getListaDetallePreDevolucionCliente()
/* 235:    */   {
/* 236:341 */     return this.listaDetallePreDevolucionCliente;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setListaDetallePreDevolucionCliente(List<DetallePreDevolucionCliente> listaDetallePreDevolucionCliente)
/* 240:    */   {
/* 241:349 */     this.listaDetallePreDevolucionCliente = listaDetallePreDevolucionCliente;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public String getCodigoMovil()
/* 245:    */   {
/* 246:353 */     return this.codigoMovil;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setCodigoMovil(String codigoMovil)
/* 250:    */   {
/* 251:357 */     this.codigoMovil = codigoMovil;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public Integer getIdFacturaClientePadre()
/* 255:    */   {
/* 256:361 */     return this.idFacturaClientePadre;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void setIdFacturaClientePadre(Integer idFacturaClientePadre)
/* 260:    */   {
/* 261:365 */     this.idFacturaClientePadre = idFacturaClientePadre;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public boolean isIndicadorProcesar()
/* 265:    */   {
/* 266:369 */     return this.indicadorProcesar;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setIndicadorProcesar(boolean indicadorProcesar)
/* 270:    */   {
/* 271:373 */     this.indicadorProcesar = indicadorProcesar;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public RecepcionDevolucionTransportista getRecepcionDevolucionTransportista()
/* 275:    */   {
/* 276:377 */     return this.recepcionDevolucionTransportista;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setRecepcionDevolucionTransportista(RecepcionDevolucionTransportista recepcionDevolucionTransportista)
/* 280:    */   {
/* 281:381 */     this.recepcionDevolucionTransportista = recepcionDevolucionTransportista;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public String getReferencia8()
/* 285:    */   {
/* 286:385 */     return this.referencia8;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setReferencia8(String referencia8)
/* 290:    */   {
/* 291:389 */     this.referencia8 = referencia8;
/* 292:    */   }
/* 293:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.PreDevolucionCliente
 * JD-Core Version:    0.7.0.1
 */