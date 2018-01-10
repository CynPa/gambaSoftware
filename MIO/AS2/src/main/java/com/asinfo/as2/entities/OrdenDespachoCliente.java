/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.io.Serializable;
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
/*  23:    */ import javax.validation.constraints.NotNull;
/*  24:    */ import javax.validation.constraints.Size;
/*  25:    */ 
/*  26:    */ @Entity
/*  27:    */ @Table(name="orden_despacho_cliente", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_documento", "numero"})})
/*  28:    */ public class OrdenDespachoCliente
/*  29:    */   extends EntidadBase
/*  30:    */   implements Serializable
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="orden_despacho_cliente", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="orden_despacho_cliente")
/*  36:    */   @Column(name="id_orden_despacho_cliente", unique=true, nullable=false)
/*  37:    */   private int idOrdenDespachoCliente;
/*  38:    */   @Column(name="id_organizacion", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private int idOrganizacion;
/*  41:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  42:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  43:    */   @NotNull
/*  44:    */   private Sucursal sucursal;
/*  45:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  46:    */   @JoinColumn(name="id_documento", nullable=false)
/*  47:    */   @NotNull
/*  48:    */   private Documento documento;
/*  49:    */   @Column(name="numero", nullable=false, length=20)
/*  50:    */   @NotNull
/*  51:    */   @Size(max=20)
/*  52:    */   private String numero;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_tipo_orden_despacho", nullable=false)
/*  55:    */   @NotNull
/*  56:    */   private TipoOrdenDespacho tipoOrdenDespacho;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_tipo_presentacion_producto", nullable=true)
/*  59:    */   private TipoPresentacionProducto tipoPresentacionProducto;
/*  60:    */   @Temporal(TemporalType.DATE)
/*  61:    */   @Column(name="fecha", nullable=false)
/*  62:    */   @NotNull
/*  63:    */   private Date fecha;
/*  64:    */   @Column(name="descripcion", length=200, nullable=true)
/*  65:    */   @Size(max=200)
/*  66:    */   private String descripcion;
/*  67:    */   @Column(name="estado", nullable=false)
/*  68:    */   @Enumerated(EnumType.ORDINAL)
/*  69:    */   private Estado estado;
/*  70:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ordenDespachoCliente")
/*  71:107 */   private List<DetalleOrdenDespachoCliente> listaDetalleOrdenDespachoCliente = new ArrayList();
/*  72:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  73:    */   @JoinColumn(name="id_pedido_cliente", nullable=true)
/*  74:    */   private PedidoCliente pedidoCliente;
/*  75:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ordenDespachoCliente")
/*  76:114 */   private List<MovimientoInventario> listaTransformacionProducto = new ArrayList();
/*  77:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ordenDespachoCliente")
/*  78:117 */   private List<DespachoCliente> listaDespachoCliente = new ArrayList();
/*  79:    */   
/*  80:    */   public int getId()
/*  81:    */   {
/*  82:128 */     return this.idOrdenDespachoCliente;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdOrdenDespachoCliente()
/*  86:    */   {
/*  87:132 */     return this.idOrdenDespachoCliente;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdOrdenDespachoCliente(int idOrdenDespachoCliente)
/*  91:    */   {
/*  92:136 */     this.idOrdenDespachoCliente = idOrdenDespachoCliente;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getIdOrganizacion()
/*  96:    */   {
/*  97:140 */     return this.idOrganizacion;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setIdOrganizacion(int idOrganizacion)
/* 101:    */   {
/* 102:144 */     this.idOrganizacion = idOrganizacion;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Sucursal getSucursal()
/* 106:    */   {
/* 107:148 */     return this.sucursal;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setSucursal(Sucursal sucursal)
/* 111:    */   {
/* 112:152 */     this.sucursal = sucursal;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Documento getDocumento()
/* 116:    */   {
/* 117:156 */     return this.documento;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setDocumento(Documento documento)
/* 121:    */   {
/* 122:160 */     this.documento = documento;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public TipoOrdenDespacho getTipoOrdenDespacho()
/* 126:    */   {
/* 127:164 */     return this.tipoOrdenDespacho;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setTipoOrdenDespacho(TipoOrdenDespacho tipoOrdenDespacho)
/* 131:    */   {
/* 132:168 */     this.tipoOrdenDespacho = tipoOrdenDespacho;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Date getFecha()
/* 136:    */   {
/* 137:172 */     return this.fecha;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setFecha(Date fecha)
/* 141:    */   {
/* 142:176 */     this.fecha = fecha;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String getDescripcion()
/* 146:    */   {
/* 147:180 */     return this.descripcion;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setDescripcion(String descripcion)
/* 151:    */   {
/* 152:184 */     this.descripcion = descripcion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Estado getEstado()
/* 156:    */   {
/* 157:188 */     return this.estado;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setEstado(Estado estado)
/* 161:    */   {
/* 162:192 */     this.estado = estado;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List<DetalleOrdenDespachoCliente> getListaDetalleOrdenDespachoCliente()
/* 166:    */   {
/* 167:196 */     return this.listaDetalleOrdenDespachoCliente;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setListaDetalleOrdenDespachoCliente(List<DetalleOrdenDespachoCliente> listaDetalleOrdenDespachoCliente)
/* 171:    */   {
/* 172:200 */     this.listaDetalleOrdenDespachoCliente = listaDetalleOrdenDespachoCliente;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public PedidoCliente getPedidoCliente()
/* 176:    */   {
/* 177:204 */     return this.pedidoCliente;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setPedidoCliente(PedidoCliente pedidoCliente)
/* 181:    */   {
/* 182:208 */     this.pedidoCliente = pedidoCliente;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String getNumero()
/* 186:    */   {
/* 187:212 */     return this.numero;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setNumero(String numero)
/* 191:    */   {
/* 192:216 */     this.numero = numero;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public List<MovimientoInventario> getListaTransformacionProducto()
/* 196:    */   {
/* 197:220 */     return this.listaTransformacionProducto;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setListaTransformacionProducto(List<MovimientoInventario> listaTransformacionProducto)
/* 201:    */   {
/* 202:224 */     this.listaTransformacionProducto = listaTransformacionProducto;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public List<DespachoCliente> getListaDespachoCliente()
/* 206:    */   {
/* 207:228 */     return this.listaDespachoCliente;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setListaDespachoCliente(List<DespachoCliente> listaDespachoCliente)
/* 211:    */   {
/* 212:232 */     this.listaDespachoCliente = listaDespachoCliente;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public TipoPresentacionProducto getTipoPresentacionProducto()
/* 216:    */   {
/* 217:236 */     return this.tipoPresentacionProducto;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setTipoPresentacionProducto(TipoPresentacionProducto tipoPresentacionProducto)
/* 221:    */   {
/* 222:240 */     this.tipoPresentacionProducto = tipoPresentacionProducto;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public int getCantidadDespachosMostrar()
/* 226:    */   {
/* 227:244 */     int cantidad = 0;
/* 228:245 */     if (this.listaDespachoCliente.size() > 10) {
/* 229:246 */       cantidad = 10;
/* 230:    */     } else {
/* 231:248 */       cantidad = this.listaDespachoCliente.size();
/* 232:    */     }
/* 233:250 */     return cantidad;
/* 234:    */   }
/* 235:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.OrdenDespachoCliente
 * JD-Core Version:    0.7.0.1
 */