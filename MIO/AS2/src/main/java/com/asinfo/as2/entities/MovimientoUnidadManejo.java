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
/*  19:    */ import javax.persistence.OrderBy;
/*  20:    */ import javax.persistence.Table;
/*  21:    */ import javax.persistence.TableGenerator;
/*  22:    */ import javax.persistence.Temporal;
/*  23:    */ import javax.persistence.TemporalType;
/*  24:    */ import javax.persistence.Transient;
/*  25:    */ import javax.validation.constraints.NotNull;
/*  26:    */ import javax.validation.constraints.Size;
/*  27:    */ 
/*  28:    */ @Entity
/*  29:    */ @Table(name="movimiento_unidad_manejo")
/*  30:    */ public class MovimientoUnidadManejo
/*  31:    */   extends EntidadBase
/*  32:    */   implements Serializable
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35:    */   @Id
/*  36:    */   @TableGenerator(name="movimiento_unidad_manejo", initialValue=0, allocationSize=50)
/*  37:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="movimiento_unidad_manejo")
/*  38:    */   @Column(name="id_movimiento_unidad_manejo", unique=true, nullable=false)
/*  39:    */   private int idMovimientoUnidadManejo;
/*  40:    */   @Column(name="id_organizacion", nullable=false)
/*  41:    */   private int idOrganizacion;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_documento", nullable=false)
/*  44:    */   @NotNull
/*  45:    */   private Documento documento;
/*  46:    */   @Column(name="numero", nullable=false, length=20)
/*  47:    */   @NotNull
/*  48:    */   @Size(max=20)
/*  49:    */   private String numero;
/*  50:    */   @Temporal(TemporalType.DATE)
/*  51:    */   @Column(name="fecha", nullable=false)
/*  52:    */   @NotNull
/*  53:    */   private Date fecha;
/*  54:    */   @Temporal(TemporalType.TIME)
/*  55:    */   @Column(name="hora", nullable=false)
/*  56:    */   @NotNull
/*  57:    */   private Date hora;
/*  58:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_motivo_ajuste_unidad_manejo", nullable=true)
/*  60:    */   private MotivoAjusteUnidadManejo motivoAjusteUnidadManejo;
/*  61:    */   @Column(name="descripcion", length=500)
/*  62:    */   @Size(max=500)
/*  63:    */   private String descripcion;
/*  64:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  65:    */   @JoinColumn(name="id_transportista", nullable=true)
/*  66:    */   private Transportista transportista;
/*  67:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  68:    */   @JoinColumn(name="id_sucursal", nullable=true)
/*  69:    */   private Sucursal sucursal;
/*  70:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  71:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  72:    */   private Empresa empresa;
/*  73:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  74:    */   @JoinColumn(name="id_subempresa", nullable=true)
/*  75:    */   private Subempresa subempresa;
/*  76:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="movimientoUnidadManejo")
/*  77:    */   @OrderBy("idDetalleMovimientoUnidadManejo")
/*  78:104 */   private List<DetalleMovimientoUnidadManejo> detalleMovimientoUnidadManejo = new ArrayList();
/*  79:    */   @Enumerated(EnumType.ORDINAL)
/*  80:    */   @Column(name="estado", nullable=false)
/*  81:    */   @NotNull
/*  82:    */   private Estado estado;
/*  83:    */   @Column(name="indicador_ajuste_unidad_manejo", nullable=true, insertable=true)
/*  84:    */   private boolean indicadorAjusteUnidadManejo;
/*  85:    */   @Transient
/*  86:    */   private int total;
/*  87:    */   
/*  88:    */   public int getId()
/*  89:    */   {
/*  90:131 */     return this.idMovimientoUnidadManejo;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getIdMovimientoUnidadManejo()
/*  94:    */   {
/*  95:138 */     return this.idMovimientoUnidadManejo;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setIdMovimientoUnidadManejo(int idMovimientoUnidadManejo)
/*  99:    */   {
/* 100:146 */     this.idMovimientoUnidadManejo = idMovimientoUnidadManejo;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int getIdOrganizacion()
/* 104:    */   {
/* 105:153 */     return this.idOrganizacion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setIdOrganizacion(int idOrganizacion)
/* 109:    */   {
/* 110:161 */     this.idOrganizacion = idOrganizacion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Documento getDocumento()
/* 114:    */   {
/* 115:168 */     return this.documento;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setDocumento(Documento documento)
/* 119:    */   {
/* 120:176 */     this.documento = documento;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String getNumero()
/* 124:    */   {
/* 125:183 */     return this.numero;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setNumero(String numero)
/* 129:    */   {
/* 130:191 */     this.numero = numero;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Date getFecha()
/* 134:    */   {
/* 135:198 */     return this.fecha;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setFecha(Date fecha)
/* 139:    */   {
/* 140:206 */     this.fecha = fecha;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public MotivoAjusteUnidadManejo getMotivoAjusteUnidadManejo()
/* 144:    */   {
/* 145:213 */     return this.motivoAjusteUnidadManejo;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setMotivoAjusteUnidadManejo(MotivoAjusteUnidadManejo motivoAjusteUnidadManejo)
/* 149:    */   {
/* 150:221 */     this.motivoAjusteUnidadManejo = motivoAjusteUnidadManejo;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String getDescripcion()
/* 154:    */   {
/* 155:228 */     return this.descripcion;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setDescripcion(String descripcion)
/* 159:    */   {
/* 160:236 */     this.descripcion = descripcion;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Transportista getTransportista()
/* 164:    */   {
/* 165:243 */     return this.transportista;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setTransportista(Transportista transportista)
/* 169:    */   {
/* 170:251 */     this.transportista = transportista;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Sucursal getSucursal()
/* 174:    */   {
/* 175:258 */     return this.sucursal;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setSucursal(Sucursal sucursal)
/* 179:    */   {
/* 180:266 */     this.sucursal = sucursal;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public Empresa getEmpresa()
/* 184:    */   {
/* 185:273 */     return this.empresa;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setEmpresa(Empresa empresa)
/* 189:    */   {
/* 190:281 */     this.empresa = empresa;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Subempresa getSubempresa()
/* 194:    */   {
/* 195:288 */     return this.subempresa;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setSubempresa(Subempresa subempresa)
/* 199:    */   {
/* 200:296 */     this.subempresa = subempresa;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public List<DetalleMovimientoUnidadManejo> getDetalleMovimientoUnidadManejo()
/* 204:    */   {
/* 205:303 */     return this.detalleMovimientoUnidadManejo;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setDetalleMovimientoUnidadManejo(List<DetalleMovimientoUnidadManejo> detalleMovimientoUnidadManejo)
/* 209:    */   {
/* 210:311 */     this.detalleMovimientoUnidadManejo = detalleMovimientoUnidadManejo;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public Estado getEstado()
/* 214:    */   {
/* 215:318 */     return this.estado;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setEstado(Estado estado)
/* 219:    */   {
/* 220:326 */     this.estado = estado;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public int getTotal()
/* 224:    */   {
/* 225:333 */     this.total = 0;
/* 226:334 */     for (DetalleMovimientoUnidadManejo detalleMovimientoUnidadManejo : getDetalleMovimientoUnidadManejo()) {
/* 227:335 */       if (!detalleMovimientoUnidadManejo.isEliminado()) {
/* 228:336 */         this.total += detalleMovimientoUnidadManejo.getCantidad();
/* 229:    */       }
/* 230:    */     }
/* 231:339 */     return this.total;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setTotal(int total)
/* 235:    */   {
/* 236:347 */     this.total = total;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public boolean isIndicadorAjusteUnidadManejo()
/* 240:    */   {
/* 241:354 */     return this.indicadorAjusteUnidadManejo;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setIndicadorAjusteUnidadManejo(boolean indicadorAjusteUnidadManejo)
/* 245:    */   {
/* 246:362 */     this.indicadorAjusteUnidadManejo = indicadorAjusteUnidadManejo;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public Date getHora()
/* 250:    */   {
/* 251:369 */     return this.hora;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setHora(Date hora)
/* 255:    */   {
/* 256:377 */     this.hora = hora;
/* 257:    */   }
/* 258:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.MovimientoUnidadManejo
 * JD-Core Version:    0.7.0.1
 */