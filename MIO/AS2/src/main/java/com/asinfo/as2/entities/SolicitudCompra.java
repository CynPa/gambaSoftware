 package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.EstadoSolicitudCompraEnum;
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
/*  27:    */ @Table(name="solicitud_compra", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero"})})
/*  28:    */ public class SolicitudCompra
/*  29:    */   extends EntidadBase
/*  30:    */   implements Serializable
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = -1L;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="solicitud_compra", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="solicitud_compra")
/*  36:    */   @Column(name="id_solicitud_compra")
/*  37:    */   private int idSolicitudCompra;
/*  38:    */   @Column(name="id_organizacion", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private int idOrganizacion;
/*  41:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  42:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  43:    */   private Sucursal sucursal;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_empleado", nullable=true)
/*  46:    */   private Empleado empleado;
/*  47:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  48:    */   @JoinColumn(name="id_documento", nullable=false)
/*  49:    */   @NotNull
/*  50:    */   private Documento documento;
/*  51:    */   @Temporal(TemporalType.DATE)
/*  52:    */   @Column(name="fecha", nullable=false)
/*  53:    */   @NotNull
/*  54:    */   private Date fecha;
/*  55:    */   @Column(name="numero", nullable=false, length=20)
/*  56:    */   @NotNull
/*  57:    */   @Size(max=20)
/*  58:    */   private String numero;
/*  59:    */   @Column(name="descripcion", nullable=true, length=500)
/*  60:    */   @Size(max=500)
/*  61:    */   private String descripcion;
/*  62:    */   @Enumerated(EnumType.ORDINAL)
/*  63:    */   @Column(name="estado", nullable=false, length=20)
/*  64:    */   private EstadoSolicitudCompraEnum estado;
/*  65:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="solicitudCompra")
/*  66: 83 */   private List<DetalleSolicitudCompra> listaDetalleSolicitudCompra = new ArrayList();
/*  67:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="solicitudCompra")
/*  68: 86 */   private List<DetalleSolicitudCompraProveedor> listaDetalleSolicitudCompraProveedor = new ArrayList();
/*  69:    */   @Column(name="indicador_consolidado", nullable=false)
/*  70:    */   private boolean indicadorConsolidado;
/*  71:    */   
/*  72:    */   public int getId()
/*  73:    */   {
/*  74: 97 */     return this.idSolicitudCompra;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getIdOrganizacion()
/*  78:    */   {
/*  79:102 */     return this.idOrganizacion;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIdOrganizacion(int idOrganizacion)
/*  83:    */   {
/*  84:106 */     this.idOrganizacion = idOrganizacion;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Sucursal getSucursal()
/*  88:    */   {
/*  89:110 */     return this.sucursal;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setSucursal(Sucursal sucursal)
/*  93:    */   {
/*  94:114 */     this.sucursal = sucursal;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Empleado getEmpleado()
/*  98:    */   {
/*  99:118 */     return this.empleado;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setEmpleado(Empleado empleado)
/* 103:    */   {
/* 104:122 */     this.empleado = empleado;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Documento getDocumento()
/* 108:    */   {
/* 109:126 */     return this.documento;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setDocumento(Documento documento)
/* 113:    */   {
/* 114:130 */     this.documento = documento;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Date getFecha()
/* 118:    */   {
/* 119:134 */     return this.fecha;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setFecha(Date fecha)
/* 123:    */   {
/* 124:138 */     this.fecha = fecha;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getNumero()
/* 128:    */   {
/* 129:142 */     return this.numero;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setNumero(String numero)
/* 133:    */   {
/* 134:146 */     this.numero = numero;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String getDescripcion()
/* 138:    */   {
/* 139:150 */     return this.descripcion;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setDescripcion(String descripcion)
/* 143:    */   {
/* 144:154 */     this.descripcion = descripcion;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getUsuarioCreacion()
/* 148:    */   {
/* 149:158 */     return this.usuarioCreacion;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setUsuarioCreacion(String usuarioCreacion)
/* 153:    */   {
/* 154:162 */     this.usuarioCreacion = usuarioCreacion;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Date getFechaCreacion()
/* 158:    */   {
/* 159:166 */     return this.fechaCreacion;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setFechaCreacion(Date fechaCreacion)
/* 163:    */   {
/* 164:170 */     this.fechaCreacion = fechaCreacion;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public Date getFechaModificacion()
/* 168:    */   {
/* 169:174 */     return this.fechaModificacion;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setFechaModificacion(Date fechaModificacion)
/* 173:    */   {
/* 174:178 */     this.fechaModificacion = fechaModificacion;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String getUsuarioModificacion()
/* 178:    */   {
/* 179:182 */     return this.usuarioModificacion;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setUsuarioModificacion(String usuarioModificacion)
/* 183:    */   {
/* 184:186 */     this.usuarioModificacion = usuarioModificacion;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public EstadoSolicitudCompraEnum getEstado()
/* 188:    */   {
/* 189:190 */     return this.estado;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setEstado(EstadoSolicitudCompraEnum estado)
/* 193:    */   {
/* 194:194 */     this.estado = estado;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public List<DetalleSolicitudCompra> getListaDetalleSolicitudCompra()
/* 198:    */   {
/* 199:198 */     return this.listaDetalleSolicitudCompra;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setListaDetalleSolicitudCompra(List<DetalleSolicitudCompra> listaDetalleSolicitudCompra)
/* 203:    */   {
/* 204:202 */     this.listaDetalleSolicitudCompra = listaDetalleSolicitudCompra;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String toString()
/* 208:    */   {
/* 209:207 */     return this.numero;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public int getIdSolicitudCompra()
/* 213:    */   {
/* 214:211 */     return this.idSolicitudCompra;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setIdSolicitudCompra(int idSolicitudCompra)
/* 218:    */   {
/* 219:215 */     this.idSolicitudCompra = idSolicitudCompra;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public boolean isIndicadorConsolidado()
/* 223:    */   {
/* 224:219 */     return this.indicadorConsolidado;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setIndicadorConsolidado(boolean indicadorConsolidado)
/* 228:    */   {
/* 229:223 */     this.indicadorConsolidado = indicadorConsolidado;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public List<DetalleSolicitudCompraProveedor> getListaDetalleSolicitudCompraProveedor()
/* 233:    */   {
/* 234:227 */     return this.listaDetalleSolicitudCompraProveedor;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setListaDetalleSolicitudCompraProveedor(List<DetalleSolicitudCompraProveedor> listaDetalleSolicitudCompraProveedor)
/* 238:    */   {
/* 239:231 */     this.listaDetalleSolicitudCompraProveedor = listaDetalleSolicitudCompraProveedor;
/* 240:    */   }
/* 241:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.SolicitudCompra
 * JD-Core Version:    0.7.0.1
 */