/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.FetchType;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.persistence.Transient;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="detalle_movimiento_unidad_manejo")
/*  19:    */ public class DetalleMovimientoUnidadManejo
/*  20:    */   extends EntidadBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="detalle_movimiento_unidad_manejo", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_movimiento_unidad_manejo")
/*  26:    */   @Column(name="id_detalle_movimiento_unidad_manejo", unique=true, nullable=false)
/*  27:    */   private int idDetalleMovimientoUnidadManejo;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   private int idOrganizacion;
/*  30:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  31:    */   @JoinColumn(name="id_movimiento_unidad_manejo", nullable=true)
/*  32:    */   private MovimientoUnidadManejo movimientoUnidadManejo;
/*  33:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  34:    */   @JoinColumn(name="id_transportista", nullable=true)
/*  35:    */   private Transportista transportista;
/*  36:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_sucursal", nullable=true)
/*  38:    */   private Sucursal sucursal;
/*  39:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  41:    */   private Empresa empresa;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_subempresa", nullable=true)
/*  44:    */   private Subempresa subempresa;
/*  45:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  46:    */   @JoinColumn(name="id_unidad_manejo", nullable=false)
/*  47:    */   @NotNull
/*  48:    */   private UnidadManejo unidadManejo;
/*  49:    */   @Column(name="cantidad", nullable=false)
/*  50:    */   private int cantidad;
/*  51:    */   @Column(name="operacion", nullable=false)
/*  52:    */   private int operacion;
/*  53:    */   @Column(name="descripcion", length=500)
/*  54:    */   @Size(max=500)
/*  55:    */   private String descripcion;
/*  56:    */   @Column(name="cantidad_recibida")
/*  57:    */   private int cantidadRecibida;
/*  58:    */   @Transient
/*  59:    */   private int cantidadCliente;
/*  60:    */   @Transient
/*  61:    */   private int saldo;
/*  62:    */   @Transient
/*  63:    */   private boolean indicadorCreado;
/*  64:    */   
/*  65:    */   public int getId()
/*  66:    */   {
/*  67: 96 */     return this.idDetalleMovimientoUnidadManejo;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdDetalleMovimientoUnidadManejo()
/*  71:    */   {
/*  72:103 */     return this.idDetalleMovimientoUnidadManejo;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdDetalleMovimientoUnidadManejo(int idDetalleMovimientoUnidadManejo)
/*  76:    */   {
/*  77:111 */     this.idDetalleMovimientoUnidadManejo = idDetalleMovimientoUnidadManejo;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdOrganizacion()
/*  81:    */   {
/*  82:118 */     return this.idOrganizacion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdOrganizacion(int idOrganizacion)
/*  86:    */   {
/*  87:126 */     this.idOrganizacion = idOrganizacion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public MovimientoUnidadManejo getMovimientoUnidadManejo()
/*  91:    */   {
/*  92:133 */     return this.movimientoUnidadManejo;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setMovimientoUnidadManejo(MovimientoUnidadManejo movimientoUnidadManejo)
/*  96:    */   {
/*  97:141 */     this.movimientoUnidadManejo = movimientoUnidadManejo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public UnidadManejo getUnidadManejo()
/* 101:    */   {
/* 102:148 */     return this.unidadManejo;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setUnidadManejo(UnidadManejo unidadManejo)
/* 106:    */   {
/* 107:156 */     this.unidadManejo = unidadManejo;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int getCantidad()
/* 111:    */   {
/* 112:163 */     return this.cantidad;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setCantidad(int cantidad)
/* 116:    */   {
/* 117:171 */     this.cantidad = cantidad;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getDescripcion()
/* 121:    */   {
/* 122:178 */     return this.descripcion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setDescripcion(String descripcion)
/* 126:    */   {
/* 127:186 */     this.descripcion = descripcion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getSaldo()
/* 131:    */   {
/* 132:190 */     return this.saldo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setSaldo(int saldo)
/* 136:    */   {
/* 137:194 */     this.saldo = saldo;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getCantidadCliente()
/* 141:    */   {
/* 142:198 */     return this.cantidadCliente;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setCantidadCliente(int cantidadCliente)
/* 146:    */   {
/* 147:202 */     this.cantidadCliente = cantidadCliente;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int getOperacion()
/* 151:    */   {
/* 152:206 */     return this.operacion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setOperacion(int operacion)
/* 156:    */   {
/* 157:210 */     this.operacion = operacion;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public int getCantidadRecibida()
/* 161:    */   {
/* 162:214 */     return this.cantidadRecibida;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setCantidadRecibida(int cantidadRecibida)
/* 166:    */   {
/* 167:218 */     this.cantidadRecibida = cantidadRecibida;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Transportista getTransportista()
/* 171:    */   {
/* 172:222 */     return this.transportista;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setTransportista(Transportista transportista)
/* 176:    */   {
/* 177:226 */     this.transportista = transportista;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Sucursal getSucursal()
/* 181:    */   {
/* 182:230 */     return this.sucursal;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setSucursal(Sucursal sucursal)
/* 186:    */   {
/* 187:234 */     this.sucursal = sucursal;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public Empresa getEmpresa()
/* 191:    */   {
/* 192:238 */     return this.empresa;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setEmpresa(Empresa empresa)
/* 196:    */   {
/* 197:242 */     this.empresa = empresa;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Subempresa getSubempresa()
/* 201:    */   {
/* 202:246 */     return this.subempresa;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setSubempresa(Subempresa subempresa)
/* 206:    */   {
/* 207:250 */     this.subempresa = subempresa;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public boolean isIndicadorCreado()
/* 211:    */   {
/* 212:254 */     return this.indicadorCreado;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setIndicadorCreado(boolean indicadorCreado)
/* 216:    */   {
/* 217:258 */     this.indicadorCreado = indicadorCreado;
/* 218:    */   }
/* 219:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleMovimientoUnidadManejo
 * JD-Core Version:    0.7.0.1
 */