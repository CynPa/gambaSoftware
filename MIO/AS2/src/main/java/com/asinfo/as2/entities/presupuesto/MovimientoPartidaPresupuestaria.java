/*   1:    */ package com.asinfo.as2.entities.presupuesto;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Documento;
/*   4:    */ import com.asinfo.as2.entities.Ejercicio;
/*   5:    */ import com.asinfo.as2.entities.EntidadBase;
/*   6:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   7:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   8:    */ import java.math.BigDecimal;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.List;
/*  12:    */ import javax.persistence.Column;
/*  13:    */ import javax.persistence.Entity;
/*  14:    */ import javax.persistence.EnumType;
/*  15:    */ import javax.persistence.Enumerated;
/*  16:    */ import javax.persistence.FetchType;
/*  17:    */ import javax.persistence.GeneratedValue;
/*  18:    */ import javax.persistence.GenerationType;
/*  19:    */ import javax.persistence.Id;
/*  20:    */ import javax.persistence.JoinColumn;
/*  21:    */ import javax.persistence.ManyToOne;
/*  22:    */ import javax.persistence.OneToMany;
/*  23:    */ import javax.persistence.Table;
/*  24:    */ import javax.persistence.TableGenerator;
/*  25:    */ import javax.persistence.Temporal;
/*  26:    */ import javax.persistence.TemporalType;
/*  27:    */ import javax.persistence.Transient;
/*  28:    */ import javax.validation.constraints.DecimalMin;
/*  29:    */ import javax.validation.constraints.Digits;
/*  30:    */ import javax.validation.constraints.NotNull;
/*  31:    */ import javax.validation.constraints.Size;
/*  32:    */ 
/*  33:    */ @Entity
/*  34:    */ @Table(name="movimiento_partida_presupuestaria")
/*  35:    */ public class MovimientoPartidaPresupuestaria
/*  36:    */   extends EntidadBase
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = -511555088901130670L;
/*  39:    */   @Id
/*  40:    */   @TableGenerator(name="movimiento_partida_presupuestaria", initialValue=0, allocationSize=50)
/*  41:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="movimiento_partida_presupuestaria")
/*  42:    */   @Column(name="id_movimiento_partida_presupuestaria")
/*  43:    */   private int idMovimientoPartidaPresupuestaria;
/*  44:    */   @Column(name="id_sucursal", nullable=true)
/*  45:    */   private int idSucursal;
/*  46:    */   @Column(name="id_organizacion", nullable=false)
/*  47:    */   @NotNull
/*  48:    */   private int idOrganizacion;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_ejercicio", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private Ejercicio ejercicio;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_documento", nullable=false)
/*  55:    */   @NotNull
/*  56:    */   private Documento documento;
/*  57:    */   @OneToMany(mappedBy="movimientoPartidaPresupuestaria", fetch=FetchType.LAZY)
/*  58: 84 */   private List<DetalleMovimientoPartidaPresupuestaria> listaDetalleMovimientoPartidaPresupuestaria = new ArrayList();
/*  59:    */   @Temporal(TemporalType.DATE)
/*  60:    */   @Column(name="fecha", nullable=false)
/*  61:    */   @NotNull
/*  62: 87 */   private Date fecha = new Date();
/*  63:    */   @Column(name="descripcion", length=1000)
/*  64:    */   @Size(max=1000)
/*  65:    */   private String descripcion;
/*  66:    */   @Column(name="descripcion_aprobacion", length=1000)
/*  67:    */   @Size(max=1000)
/*  68:    */   private String descripcionAprobacion;
/*  69:    */   @Enumerated(EnumType.ORDINAL)
/*  70:    */   @Column(name="estado", nullable=false)
/*  71:    */   @NotNull
/*  72:    */   private Estado estado;
/*  73:    */   @Enumerated(EnumType.ORDINAL)
/*  74:    */   @Column(name="mes_origen", nullable=false)
/*  75:    */   @NotNull
/*  76:    */   private Mes mesOrigen;
/*  77:    */   @Enumerated(EnumType.ORDINAL)
/*  78:    */   @Column(name="mes_destino", nullable=true)
/*  79:    */   private Mes mesDestino;
/*  80:    */   @Column(name="numero", nullable=false, length=20)
/*  81:    */   @NotNull
/*  82:    */   @Size(max=20)
/*  83:    */   private String numero;
/*  84:    */   @Column(name="valor_total", nullable=false, precision=12, scale=2)
/*  85:    */   @Digits(integer=12, fraction=2)
/*  86:    */   @DecimalMin("0.00")
/*  87:    */   private BigDecimal valorTotal;
/*  88:    */   @Transient
/*  89:    */   private boolean traSeleccionado;
/*  90:    */   
/*  91:    */   public int getIdMovimientoPartidaPresupuestaria()
/*  92:    */   {
/*  93:128 */     return this.idMovimientoPartidaPresupuestaria;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setIdMovimientoPartidaPresupuestaria(int idMovimientoPartidaPresupuestaria)
/*  97:    */   {
/*  98:132 */     this.idMovimientoPartidaPresupuestaria = idMovimientoPartidaPresupuestaria;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public int getIdSucursal()
/* 102:    */   {
/* 103:136 */     return this.idSucursal;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setIdSucursal(int idSucursal)
/* 107:    */   {
/* 108:140 */     this.idSucursal = idSucursal;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public int getIdOrganizacion()
/* 112:    */   {
/* 113:144 */     return this.idOrganizacion;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setIdOrganizacion(int idOrganizacion)
/* 117:    */   {
/* 118:148 */     this.idOrganizacion = idOrganizacion;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Ejercicio getEjercicio()
/* 122:    */   {
/* 123:152 */     return this.ejercicio;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setEjercicio(Ejercicio ejercicio)
/* 127:    */   {
/* 128:156 */     this.ejercicio = ejercicio;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Date getFecha()
/* 132:    */   {
/* 133:160 */     return this.fecha;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setFecha(Date fecha)
/* 137:    */   {
/* 138:164 */     this.fecha = fecha;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String getDescripcion()
/* 142:    */   {
/* 143:168 */     return this.descripcion;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setDescripcion(String descripcion)
/* 147:    */   {
/* 148:172 */     this.descripcion = descripcion;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Estado getEstado()
/* 152:    */   {
/* 153:176 */     return this.estado;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setEstado(Estado estado)
/* 157:    */   {
/* 158:180 */     this.estado = estado;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public Documento getDocumento()
/* 162:    */   {
/* 163:184 */     return this.documento;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setDocumento(Documento documento)
/* 167:    */   {
/* 168:188 */     this.documento = documento;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Mes getMesOrigen()
/* 172:    */   {
/* 173:192 */     return this.mesOrigen;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setMesOrigen(Mes mesOrigen)
/* 177:    */   {
/* 178:196 */     this.mesOrigen = mesOrigen;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public Mes getMesDestino()
/* 182:    */   {
/* 183:200 */     return this.mesDestino;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setMesDestino(Mes mesDestino)
/* 187:    */   {
/* 188:204 */     this.mesDestino = mesDestino;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public List<DetalleMovimientoPartidaPresupuestaria> getListaDetalleMovimientoPartidaPresupuestaria()
/* 192:    */   {
/* 193:208 */     return this.listaDetalleMovimientoPartidaPresupuestaria;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setListaDetalleMovimientoPartidaPresupuestaria(List<DetalleMovimientoPartidaPresupuestaria> listaDetalleMovimientoPartidaPresupuestaria)
/* 197:    */   {
/* 198:212 */     this.listaDetalleMovimientoPartidaPresupuestaria = listaDetalleMovimientoPartidaPresupuestaria;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public String getDescripcionAprobacion()
/* 202:    */   {
/* 203:216 */     return this.descripcionAprobacion;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setDescripcionAprobacion(String descripcionAprobacion)
/* 207:    */   {
/* 208:220 */     this.descripcionAprobacion = descripcionAprobacion;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public String getNumero()
/* 212:    */   {
/* 213:224 */     return this.numero;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setNumero(String numero)
/* 217:    */   {
/* 218:228 */     this.numero = numero;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public BigDecimal getValorTotal()
/* 222:    */   {
/* 223:233 */     return this.valorTotal;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setValorTotal(BigDecimal valorTotal)
/* 227:    */   {
/* 228:237 */     this.valorTotal = valorTotal;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public int getId()
/* 232:    */   {
/* 233:242 */     return this.idMovimientoPartidaPresupuestaria;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public boolean isTraSeleccionado()
/* 237:    */   {
/* 238:246 */     return this.traSeleccionado;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setTraSeleccionado(boolean traSeleccionado)
/* 242:    */   {
/* 243:250 */     this.traSeleccionado = traSeleccionado;
/* 244:    */   }
/* 245:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.presupuesto.MovimientoPartidaPresupuestaria
 * JD-Core Version:    0.7.0.1
 */