/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.math.BigDecimal;
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
/*  23:    */ import javax.validation.constraints.Digits;
/*  24:    */ import javax.validation.constraints.Min;
/*  25:    */ import javax.validation.constraints.NotNull;
/*  26:    */ import javax.validation.constraints.Size;
/*  27:    */ 
/*  28:    */ @Entity
/*  29:    */ @Table(name="permiso_empleado")
/*  30:    */ public class PermisoEmpleado
/*  31:    */   extends EntidadBase
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 499577215800767417L;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal", nullable=false)
/*  37:    */   private int idSucursal;
/*  38:    */   @Id
/*  39:    */   @TableGenerator(name="permiso_empleado", initialValue=0, allocationSize=50)
/*  40:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="permiso_empleado")
/*  41:    */   @Column(name="id_permiso_empleado")
/*  42:    */   private int idPermisoEmpleado;
/*  43:    */   @Column(name="fecha_permiso", nullable=false)
/*  44:    */   @Temporal(TemporalType.DATE)
/*  45:    */   @NotNull
/*  46:    */   private Date fechaPermiso;
/*  47:    */   @Column(name="horas", nullable=false, precision=12, scale=2)
/*  48:    */   @Min(0L)
/*  49:    */   @Digits(integer=12, fraction=2)
/*  50: 75 */   private BigDecimal horas = BigDecimal.ZERO;
/*  51:    */   @Column(name="estado", nullable=false)
/*  52:    */   @Enumerated(EnumType.ORDINAL)
/*  53:    */   private Estado estado;
/*  54:    */   @Column(name="indicador_cargo_vacacion", nullable=false)
/*  55:    */   private boolean indicadorCargoVacacion;
/*  56:    */   @Column(name="descripcion", length=200, nullable=true)
/*  57:    */   @Size(max=200)
/*  58:    */   private String descripcion;
/*  59:    */   @Column(name="numero", nullable=false, length=20)
/*  60:    */   private String numero;
/*  61:    */   @Column(name="dia_desde", nullable=true)
/*  62:    */   @Temporal(TemporalType.DATE)
/*  63:    */   private Date diaDesde;
/*  64:    */   @Column(name="dia_hasta", nullable=true)
/*  65:    */   @Temporal(TemporalType.DATE)
/*  66:    */   private Date diaHasta;
/*  67:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  68:    */   @JoinColumn(name="id_documento", nullable=false)
/*  69:    */   @NotNull
/*  70:    */   private Documento documento;
/*  71:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  72:    */   @JoinColumn(name="id_tipo_permiso_empleado", nullable=true)
/*  73:    */   private TipoPermisoEmpleado tipoPermisoEmpleado;
/*  74:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  75:    */   @JoinColumn(name="id_vacacion", nullable=true)
/*  76:    */   private Vacacion vacacion;
/*  77:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  78:    */   @JoinColumn(name="id_historico_empleado", nullable=false)
/*  79:    */   private HistoricoEmpleado historicoEmpleado;
/*  80:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="permisoEmpleado")
/*  81:132 */   private List<DetallePermisoEmpleado> listaDetallePermisoEmpleado = new ArrayList();
/*  82:    */   
/*  83:    */   public int getIdPermisoEmpleado()
/*  84:    */   {
/*  85:153 */     return this.idPermisoEmpleado;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdPermisoEmpleado(int idPermisoEmpleado)
/*  89:    */   {
/*  90:163 */     this.idPermisoEmpleado = idPermisoEmpleado;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Date getFechaPermiso()
/*  94:    */   {
/*  95:172 */     return this.fechaPermiso;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setFechaPermiso(Date fechaPermiso)
/*  99:    */   {
/* 100:182 */     this.fechaPermiso = fechaPermiso;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Vacacion getVacacion()
/* 104:    */   {
/* 105:191 */     return this.vacacion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setVacacion(Vacacion vacacion)
/* 109:    */   {
/* 110:201 */     this.vacacion = vacacion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public HistoricoEmpleado getHistoricoEmpleado()
/* 114:    */   {
/* 115:210 */     return this.historicoEmpleado;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setHistoricoEmpleado(HistoricoEmpleado historicoEmpleado)
/* 119:    */   {
/* 120:220 */     this.historicoEmpleado = historicoEmpleado;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public int getIdOrganizacion()
/* 124:    */   {
/* 125:229 */     return this.idOrganizacion;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setIdOrganizacion(int idOrganizacion)
/* 129:    */   {
/* 130:239 */     this.idOrganizacion = idOrganizacion;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public int getIdSucursal()
/* 134:    */   {
/* 135:248 */     return this.idSucursal;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setIdSucursal(int idSucursal)
/* 139:    */   {
/* 140:258 */     this.idSucursal = idSucursal;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public BigDecimal getHoras()
/* 144:    */   {
/* 145:267 */     return this.horas;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setHoras(BigDecimal horas)
/* 149:    */   {
/* 150:277 */     this.horas = horas;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public int getId()
/* 154:    */   {
/* 155:287 */     return this.idPermisoEmpleado;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public Estado getEstado()
/* 159:    */   {
/* 160:296 */     return this.estado;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setEstado(Estado estado)
/* 164:    */   {
/* 165:306 */     this.estado = estado;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public TipoPermisoEmpleado getTipoPermisoEmpleado()
/* 169:    */   {
/* 170:315 */     return this.tipoPermisoEmpleado;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setTipoPermisoEmpleado(TipoPermisoEmpleado tipoPermisoEmpleado)
/* 174:    */   {
/* 175:325 */     this.tipoPermisoEmpleado = tipoPermisoEmpleado;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public boolean isIndicadorCargoVacacion()
/* 179:    */   {
/* 180:369 */     return this.indicadorCargoVacacion;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setIndicadorCargoVacacion(boolean indicadorCargoVacacion)
/* 184:    */   {
/* 185:379 */     this.indicadorCargoVacacion = indicadorCargoVacacion;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String getDescripcion()
/* 189:    */   {
/* 190:388 */     return this.descripcion;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setDescripcion(String descripcion)
/* 194:    */   {
/* 195:398 */     this.descripcion = descripcion;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Documento getDocumento()
/* 199:    */   {
/* 200:407 */     return this.documento;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setDocumento(Documento documento)
/* 204:    */   {
/* 205:417 */     this.documento = documento;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public String getNumero()
/* 209:    */   {
/* 210:426 */     return this.numero;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setNumero(String numero)
/* 214:    */   {
/* 215:436 */     this.numero = numero;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public Date getDiaDesde()
/* 219:    */   {
/* 220:440 */     return this.diaDesde;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setDiaDesde(Date diaDesde)
/* 224:    */   {
/* 225:444 */     this.diaDesde = diaDesde;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public Date getDiaHasta()
/* 229:    */   {
/* 230:448 */     return this.diaHasta;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setDiaHasta(Date diaHasta)
/* 234:    */   {
/* 235:452 */     this.diaHasta = diaHasta;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public List<DetallePermisoEmpleado> getListaDetallePermisoEmpleado()
/* 239:    */   {
/* 240:456 */     return this.listaDetallePermisoEmpleado;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setListaDetallePermisoEmpleado(List<DetallePermisoEmpleado> listaDetallePermisoEmpleado)
/* 244:    */   {
/* 245:460 */     this.listaDetallePermisoEmpleado = listaDetallePermisoEmpleado;
/* 246:    */   }
/* 247:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.PermisoEmpleado
 * JD-Core Version:    0.7.0.1
 */