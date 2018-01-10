/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.Date;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.persistence.Temporal;
/*  14:    */ import javax.persistence.TemporalType;
/*  15:    */ import javax.validation.constraints.DecimalMin;
/*  16:    */ import javax.validation.constraints.Digits;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="tipo_orden_despacho", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  22:    */ public class TipoOrdenDespacho
/*  23:    */   extends EntidadBase
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="tipo_orden_despacho", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_orden_despacho")
/*  30:    */   @Column(name="id_tipo_orden_despacho", unique=true, nullable=false)
/*  31:    */   private int idTipoOrdenDespacho;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   private int idSucursal;
/*  36:    */   @Column(name="codigo", nullable=false, length=10)
/*  37:    */   @NotNull
/*  38:    */   @Size(min=1, max=10)
/*  39:    */   private String codigo;
/*  40:    */   @Column(name="nombre", nullable=false, length=50)
/*  41:    */   @NotNull
/*  42:    */   @Size(min=1, max=50)
/*  43:    */   private String nombre;
/*  44:    */   @Column(name="descripcion", nullable=true, length=300)
/*  45:    */   @Size(max=300)
/*  46:    */   private String descripcion;
/*  47:    */   @Column(name="activo", nullable=false)
/*  48:    */   @NotNull
/*  49: 69 */   private boolean activo = true;
/*  50:    */   @Column(name="predeterminado", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private boolean predeterminado;
/*  53:    */   @Temporal(TemporalType.TIME)
/*  54:    */   @Column(name="hora_maxima_registro_pedido", nullable=true)
/*  55:    */   private Date horaMaximaRegistroPedido;
/*  56:    */   @Column(name="indicador_edita_cantidad_factura", nullable=false)
/*  57:    */   @NotNull
/*  58: 81 */   private boolean indicadorEditaCantidadFactura = true;
/*  59:    */   @Column(name="indicador_aplica_porciento_adicional_pedidos", nullable=true)
/*  60: 86 */   private Boolean indicadorAplicaPorcientoAdicionalPedidos = Boolean.valueOf(false);
/*  61:    */   @Column(name="monto_minimo_pedido", nullable=true, precision=12, scale=2)
/*  62:    */   @Digits(integer=10, fraction=2)
/*  63:    */   @DecimalMin("0.00")
/*  64: 88 */   private BigDecimal montoMinimoPedido = BigDecimal.ZERO;
/*  65:    */   @Column(name="dias_despacho", nullable=false)
/*  66:    */   @NotNull
/*  67:    */   private int diasDespacho;
/*  68:    */   @Column(name="indicador_guardar_sin_detalle", nullable=false)
/*  69:    */   @NotNull
/*  70:    */   private boolean indicadorGuardarSinDetalle;
/*  71:    */   
/*  72:    */   public TipoOrdenDespacho() {}
/*  73:    */   
/*  74:    */   public TipoOrdenDespacho(int idTipoOrdenDespacho, String codigo, String nombre)
/*  75:    */   {
/*  76:113 */     this.idTipoOrdenDespacho = idTipoOrdenDespacho;
/*  77:114 */     this.codigo = codigo;
/*  78:115 */     this.nombre = nombre;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getId()
/*  82:    */   {
/*  83:120 */     return this.idTipoOrdenDespacho;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getIdTipoOrdenDespacho()
/*  87:    */   {
/*  88:129 */     return this.idTipoOrdenDespacho;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIdTipoOrdenDespacho(int idTipoOrdenDespacho)
/*  92:    */   {
/*  93:139 */     this.idTipoOrdenDespacho = idTipoOrdenDespacho;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getIdOrganizacion()
/*  97:    */   {
/*  98:148 */     return this.idOrganizacion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setIdOrganizacion(int idOrganizacion)
/* 102:    */   {
/* 103:158 */     this.idOrganizacion = idOrganizacion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getIdSucursal()
/* 107:    */   {
/* 108:167 */     return this.idSucursal;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setIdSucursal(int idSucursal)
/* 112:    */   {
/* 113:177 */     this.idSucursal = idSucursal;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getCodigo()
/* 117:    */   {
/* 118:186 */     return this.codigo;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setCodigo(String codigo)
/* 122:    */   {
/* 123:196 */     this.codigo = codigo;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String getNombre()
/* 127:    */   {
/* 128:205 */     return this.nombre;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setNombre(String nombre)
/* 132:    */   {
/* 133:215 */     this.nombre = nombre;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String getDescripcion()
/* 137:    */   {
/* 138:224 */     return this.descripcion;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setDescripcion(String descripcion)
/* 142:    */   {
/* 143:234 */     this.descripcion = descripcion;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public boolean isActivo()
/* 147:    */   {
/* 148:243 */     return this.activo;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setActivo(boolean activo)
/* 152:    */   {
/* 153:253 */     this.activo = activo;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public boolean isPredeterminado()
/* 157:    */   {
/* 158:262 */     return this.predeterminado;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setPredeterminado(boolean predeterminado)
/* 162:    */   {
/* 163:272 */     this.predeterminado = predeterminado;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Date getHoraMaximaRegistroPedido()
/* 167:    */   {
/* 168:276 */     return this.horaMaximaRegistroPedido;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setHoraMaximaRegistroPedido(Date horaMaximaRegistroPedido)
/* 172:    */   {
/* 173:280 */     this.horaMaximaRegistroPedido = horaMaximaRegistroPedido;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean isIndicadorEditaCantidadFactura()
/* 177:    */   {
/* 178:284 */     return this.indicadorEditaCantidadFactura;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setIndicadorEditaCantidadFactura(boolean indicadorEditaCantidadFactura)
/* 182:    */   {
/* 183:288 */     this.indicadorEditaCantidadFactura = indicadorEditaCantidadFactura;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public BigDecimal getMontoMinimoPedido()
/* 187:    */   {
/* 188:292 */     if (this.montoMinimoPedido == null) {
/* 189:293 */       this.montoMinimoPedido = BigDecimal.ZERO;
/* 190:    */     }
/* 191:295 */     return this.montoMinimoPedido;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setMontoMinimoPedido(BigDecimal montoMinimoPedido)
/* 195:    */   {
/* 196:299 */     this.montoMinimoPedido = montoMinimoPedido;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public Boolean getIndicadorAplicaPorcientoAdicionalPedidos()
/* 200:    */   {
/* 201:303 */     if (this.indicadorAplicaPorcientoAdicionalPedidos == null) {
/* 202:304 */       this.indicadorAplicaPorcientoAdicionalPedidos = Boolean.valueOf(false);
/* 203:    */     }
/* 204:306 */     return this.indicadorAplicaPorcientoAdicionalPedidos;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setIndicadorAplicaPorcientoAdicionalPedidos(Boolean indicadorAplicaPorcientoAdicionalPedidos)
/* 208:    */   {
/* 209:310 */     if (indicadorAplicaPorcientoAdicionalPedidos == null) {
/* 210:311 */       indicadorAplicaPorcientoAdicionalPedidos = Boolean.valueOf(false);
/* 211:    */     }
/* 212:313 */     this.indicadorAplicaPorcientoAdicionalPedidos = indicadorAplicaPorcientoAdicionalPedidos;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public int getDiasDespacho()
/* 216:    */   {
/* 217:317 */     return this.diasDespacho;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setDiasDespacho(int diasDespacho)
/* 221:    */   {
/* 222:321 */     this.diasDespacho = diasDespacho;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public boolean isIndicadorGuardarSinDetalle()
/* 226:    */   {
/* 227:325 */     return this.indicadorGuardarSinDetalle;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setIndicadorGuardarSinDetalle(boolean indicadorGuardarSinDetalle)
/* 231:    */   {
/* 232:329 */     this.indicadorGuardarSinDetalle = indicadorGuardarSinDetalle;
/* 233:    */   }
/* 234:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TipoOrdenDespacho
 * JD-Core Version:    0.7.0.1
 */