/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   4:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.Date;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.OneToOne;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.persistence.Transient;
/*  19:    */ import javax.validation.constraints.Digits;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="detalle_cierre_caja", indexes={@javax.persistence.Index(columnList="id_detalle_forma_cobro"), @javax.persistence.Index(columnList="id_cierre_caja")})
/*  23:    */ public class DetalleCierreCaja
/*  24:    */   extends EntidadBase
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="detalle_cierre_caja", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_cierre_caja")
/*  30:    */   @Column(name="id_detalle_cierre_caja")
/*  31:    */   private int idDetalleCierreCaja;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   private int idSucursal;
/*  36:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  37:    */   @Digits(integer=12, fraction=2)
/*  38: 63 */   private BigDecimal valor = BigDecimal.ZERO;
/*  39:    */   @Column(name="id_dispositivo_sincronizacion", nullable=true)
/*  40:    */   private Integer idDispositivoSincronizacion;
/*  41:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  42:    */   @JoinColumn(name="id_cierre_caja", nullable=true)
/*  43:    */   private CierreCaja cierreCaja;
/*  44:    */   @OneToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_detalle_forma_cobro", nullable=true)
/*  46:    */   private DetalleFormaCobro detalleFormaCobro;
/*  47:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  48:    */   @JoinColumn(name="id_anticipo_cliente", nullable=true)
/*  49:    */   private AnticipoCliente anticipoCliente;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_interfaz_contable_proceso", nullable=true)
/*  52:    */   private InterfazContableProceso interfazContableProceso;
/*  53:    */   @Transient
/*  54:    */   private boolean seleccionado;
/*  55:    */   
/*  56:    */   public DetalleCierreCaja() {}
/*  57:    */   
/*  58:    */   public DetalleCierreCaja(Integer idDetalleCierreCaja, int idSucursal, int idOrganizacion, BigDecimal valor, Integer idInterfazContableProceso, String numeroICP, DocumentoBase doc, Integer idCierreCaja, BigDecimal valorCC, Date fechaHasta, String numeroCC, Integer idDetalleFormaCobro, String documentoReferencia, Integer idCobro, Date fechaCobro, String numeroCobro, Integer idEmpresa, String nombreFiscal, Integer idFormaPago, String nombreFormaPago, Integer idUsuario, String nombreUsuario)
/*  59:    */   {
/*  60:117 */     this.idDetalleCierreCaja = idDetalleCierreCaja.intValue();
/*  61:118 */     this.idSucursal = idSucursal;
/*  62:119 */     this.idOrganizacion = idOrganizacion;
/*  63:120 */     this.valor = valor;
/*  64:121 */     if (idInterfazContableProceso != null)
/*  65:    */     {
/*  66:122 */       InterfazContableProceso interfazContableProceso = new InterfazContableProceso();
/*  67:123 */       interfazContableProceso.setIdOrganizacion(idOrganizacion);
/*  68:124 */       interfazContableProceso.setIdOrganizacion(idOrganizacion);
/*  69:125 */       interfazContableProceso.setIdInterfazContableProceso(idInterfazContableProceso.intValue());
/*  70:126 */       interfazContableProceso.setNumero(numeroICP);
/*  71:127 */       interfazContableProceso.setDocumentoBase(doc);
/*  72:128 */       this.interfazContableProceso = interfazContableProceso;
/*  73:    */     }
/*  74:130 */     if (idCierreCaja != null)
/*  75:    */     {
/*  76:131 */       CierreCaja cc = new CierreCaja();
/*  77:132 */       cc.setIdOrganizacion(idOrganizacion);
/*  78:133 */       cc.setIdSucursal(idSucursal);
/*  79:134 */       cc.setIdCierreCaja(idCierreCaja.intValue());
/*  80:135 */       cc.setValor(valorCC);
/*  81:136 */       cc.setFechaHasta(fechaHasta);
/*  82:137 */       cc.setNumero(numeroCC);
/*  83:    */       
/*  84:139 */       EntidadUsuario usuario = new EntidadUsuario();
/*  85:140 */       usuario.setIdUsuario(idUsuario.intValue());
/*  86:141 */       usuario.setNombreUsuario(nombreUsuario);
/*  87:    */       
/*  88:143 */       cc.setUsuario(usuario);
/*  89:    */       
/*  90:145 */       this.cierreCaja = cc;
/*  91:    */     }
/*  92:148 */     if (idDetalleFormaCobro != null)
/*  93:    */     {
/*  94:149 */       Cobro cobro = null;
/*  95:150 */       if (idCobro != null)
/*  96:    */       {
/*  97:151 */         cobro = new Cobro();
/*  98:152 */         cobro.setIdOrganizacion(idOrganizacion);
/*  99:153 */         cobro.setIdCobro(idCobro.intValue());
/* 100:154 */         cobro.setFecha(fechaCobro);
/* 101:155 */         cobro.setNumero(numeroCobro);
/* 102:    */         
/* 103:157 */         Empresa empresa = new Empresa();
/* 104:158 */         empresa.setIdOrganizacion(idOrganizacion);
/* 105:159 */         empresa.setIdSucursal(idSucursal);
/* 106:160 */         empresa.setIdEmpresa(idEmpresa.intValue());
/* 107:161 */         empresa.setNombreFiscal(nombreFiscal);
/* 108:162 */         cobro.setEmpresa(empresa);
/* 109:    */       }
/* 110:165 */       FormaPago formaPago = new FormaPago();
/* 111:166 */       formaPago.setIdOrganizacion(idOrganizacion);
/* 112:167 */       formaPago.setIdSucursal(idSucursal);
/* 113:168 */       formaPago.setIdFormaPago(idFormaPago.intValue());
/* 114:169 */       formaPago.setNombre(nombreFormaPago);
/* 115:    */       
/* 116:171 */       DetalleFormaCobro detalleFormaCobro = new DetalleFormaCobro();
/* 117:172 */       detalleFormaCobro.setIdSucursal(idSucursal);
/* 118:173 */       detalleFormaCobro.setIdOrganizacion(idOrganizacion);
/* 119:174 */       detalleFormaCobro.setIdDetalleFormaCobro(idDetalleFormaCobro.intValue());
/* 120:175 */       detalleFormaCobro.setDocumentoReferencia(documentoReferencia);
/* 121:176 */       detalleFormaCobro.setCobro(cobro);
/* 122:177 */       detalleFormaCobro.setFormaPago(formaPago);
/* 123:178 */       this.detalleFormaCobro = detalleFormaCobro;
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public DetalleCierreCaja(Integer idDetalleCierreCaja, int idSucursal, int idOrganizacion, BigDecimal valor, Integer idInterfazContableProceso, String numeroICP, DocumentoBase doc, Integer idCierreCaja, BigDecimal valorCC, Date fechaHasta, String numeroCC, Integer idAnticipoCliente, String documentoReferencia, Date fechaAnticipo, String numeroAnticipo, Integer idEmpresa, String nombreFiscal, Integer idFormaPago, String nombreFormaPago, Integer idUsuario, String nombreUsuario)
/* 128:    */   {
/* 129:188 */     this.idDetalleCierreCaja = idDetalleCierreCaja.intValue();
/* 130:189 */     this.valor = valor;
/* 131:190 */     this.idOrganizacion = idOrganizacion;
/* 132:191 */     this.idSucursal = idSucursal;
/* 133:192 */     if (idInterfazContableProceso != null)
/* 134:    */     {
/* 135:193 */       InterfazContableProceso interfazContableProceso = new InterfazContableProceso();
/* 136:194 */       interfazContableProceso.setIdInterfazContableProceso(idInterfazContableProceso.intValue());
/* 137:195 */       interfazContableProceso.setNumero(numeroICP);
/* 138:196 */       interfazContableProceso.setDocumentoBase(doc);
/* 139:197 */       this.interfazContableProceso = interfazContableProceso;
/* 140:    */     }
/* 141:199 */     if (idCierreCaja != null)
/* 142:    */     {
/* 143:200 */       CierreCaja cc = new CierreCaja();
/* 144:201 */       cc.setIdCierreCaja(idCierreCaja.intValue());
/* 145:202 */       cc.setValor(valorCC);
/* 146:203 */       cc.setNumero(numeroCC);
/* 147:204 */       cc.setFechaHasta(fechaHasta);
/* 148:    */       
/* 149:206 */       EntidadUsuario usuario = new EntidadUsuario();
/* 150:207 */       usuario.setIdUsuario(idUsuario.intValue());
/* 151:208 */       usuario.setNombreUsuario(nombreUsuario);
/* 152:    */       
/* 153:210 */       this.cierreCaja = cc;
/* 154:    */     }
/* 155:212 */     if (idAnticipoCliente != null)
/* 156:    */     {
/* 157:213 */       Empresa empresa = new Empresa();
/* 158:214 */       empresa.setIdEmpresa(idEmpresa.intValue());
/* 159:215 */       empresa.setNombreFiscal(nombreFiscal);
/* 160:    */       
/* 161:217 */       FormaPago formaPago = new FormaPago();
/* 162:218 */       formaPago.setIdFormaPago(idFormaPago.intValue());
/* 163:219 */       formaPago.setNombre(nombreFormaPago);
/* 164:    */       
/* 165:221 */       AnticipoCliente anticipo = new AnticipoCliente();
/* 166:222 */       anticipo.setIdAnticipoCliente(idAnticipoCliente.intValue());
/* 167:223 */       anticipo.setDocumentoReferencia(documentoReferencia);
/* 168:224 */       anticipo.setFecha(fechaAnticipo);
/* 169:225 */       anticipo.setNumero(numeroAnticipo);
/* 170:226 */       anticipo.setEmpresa(empresa);
/* 171:227 */       anticipo.setFormaPago(formaPago);
/* 172:228 */       this.anticipoCliente = anticipo;
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public int getId()
/* 177:    */   {
/* 178:243 */     return this.idDetalleCierreCaja;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public int getIdDetalleCierreCaja()
/* 182:    */   {
/* 183:252 */     return this.idDetalleCierreCaja;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setIdDetalleCierreCaja(int idDetalleCierreCaja)
/* 187:    */   {
/* 188:262 */     this.idDetalleCierreCaja = idDetalleCierreCaja;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public int getIdOrganizacion()
/* 192:    */   {
/* 193:271 */     return this.idOrganizacion;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setIdOrganizacion(int idOrganizacion)
/* 197:    */   {
/* 198:281 */     this.idOrganizacion = idOrganizacion;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public int getIdSucursal()
/* 202:    */   {
/* 203:290 */     return this.idSucursal;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setIdSucursal(int idSucursal)
/* 207:    */   {
/* 208:300 */     this.idSucursal = idSucursal;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public CierreCaja getCierreCaja()
/* 212:    */   {
/* 213:309 */     return this.cierreCaja;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setCierreCaja(CierreCaja cierreCaja)
/* 217:    */   {
/* 218:319 */     this.cierreCaja = cierreCaja;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public DetalleFormaCobro getDetalleFormaCobro()
/* 222:    */   {
/* 223:328 */     return this.detalleFormaCobro;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setDetalleFormaCobro(DetalleFormaCobro detalleFormaCobro)
/* 227:    */   {
/* 228:338 */     this.detalleFormaCobro = detalleFormaCobro;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public InterfazContableProceso getInterfazContableProceso()
/* 232:    */   {
/* 233:347 */     return this.interfazContableProceso;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/* 237:    */   {
/* 238:357 */     this.interfazContableProceso = interfazContableProceso;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public BigDecimal getValor()
/* 242:    */   {
/* 243:366 */     return this.valor;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setValor(BigDecimal valor)
/* 247:    */   {
/* 248:376 */     this.valor = valor;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public boolean isSeleccionado()
/* 252:    */   {
/* 253:385 */     return this.seleccionado;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setSeleccionado(boolean seleccionado)
/* 257:    */   {
/* 258:395 */     this.seleccionado = seleccionado;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public AnticipoCliente getAnticipoCliente()
/* 262:    */   {
/* 263:404 */     return this.anticipoCliente;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setAnticipoCliente(AnticipoCliente anticipoCliente)
/* 267:    */   {
/* 268:414 */     this.anticipoCliente = anticipoCliente;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public String getDocumentoReferencia()
/* 272:    */   {
/* 273:418 */     return this.detalleFormaCobro != null ? this.detalleFormaCobro.getDocumentoReferencia() : this.anticipoCliente.getDocumentoReferencia();
/* 274:    */   }
/* 275:    */   
/* 276:    */   public Integer getIdDispositivoSincronizacion()
/* 277:    */   {
/* 278:422 */     return this.idDispositivoSincronizacion;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 282:    */   {
/* 283:426 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 284:    */   }
/* 285:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleCierreCaja
 * JD-Core Version:    0.7.0.1
 */