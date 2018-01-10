/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Date;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.OneToMany;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.persistence.Temporal;
/*  18:    */ import javax.persistence.TemporalType;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="vacacion")
/*  22:    */ public class Vacacion
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = -5704139874255440892L;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal", nullable=false)
/*  29:    */   private int idSucursal;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="vacacion", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="vacacion")
/*  33:    */   @Column(name="id_vacacion")
/*  34:    */   private int idVacacion;
/*  35:    */   @Column(name="fecha_inicio_periodo", nullable=false)
/*  36:    */   @Temporal(TemporalType.DATE)
/*  37:    */   private Date fechaInicioPeriodo;
/*  38:    */   @Column(name="fecha_fin_periodo", nullable=false)
/*  39:    */   @Temporal(TemporalType.DATE)
/*  40:    */   private Date fechaFinPeriodo;
/*  41:    */   @Column(name="dias", nullable=false)
/*  42:    */   private int dias;
/*  43:    */   @Column(name="dias_adicionales", nullable=false)
/*  44:    */   private int diasAdicionales;
/*  45:    */   @Column(name="dias_tomados", nullable=false)
/*  46:    */   private int diasTomados;
/*  47:    */   @Column(name="indicador_anticipo_vacacion", nullable=false)
/*  48:    */   private boolean indicadorAnticipoVacacion;
/*  49:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="vacacion")
/*  50: 85 */   private List<DetalleVacacion> listaDetalleVacacion = new ArrayList();
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_historico_empleado", nullable=true)
/*  53:    */   private HistoricoEmpleado historicoEmpleado;
/*  54:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="vacacion")
/*  55: 92 */   private List<PermisoEmpleado> listaPermisoEmpleado = new ArrayList();
/*  56:    */   @Temporal(TemporalType.DATE)
/*  57:    */   private transient Date fechaIngreso;
/*  58:    */   
/*  59:    */   public Vacacion() {}
/*  60:    */   
/*  61:    */   public Vacacion(int idVacacion, Date fechaInicioPeriodo, Date fechaFinPeriodo, HistoricoEmpleado historicoEmpleado)
/*  62:    */   {
/*  63:111 */     this.fechaInicioPeriodo = fechaInicioPeriodo;
/*  64:112 */     this.fechaFinPeriodo = fechaFinPeriodo;
/*  65:113 */     this.historicoEmpleado = historicoEmpleado;
/*  66:114 */     this.idVacacion = idVacacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Vacacion(int idVacacion, Date fechaInicioPeriodo, Date fechaFinPeriodo, HistoricoEmpleado historicoEmpleado, Date fechaIngreso)
/*  70:    */   {
/*  71:118 */     this(idVacacion, fechaInicioPeriodo, fechaFinPeriodo, historicoEmpleado);
/*  72:119 */     this.fechaIngreso = fechaIngreso;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Vacacion(Date fechaInicioPeriodo, Date fechaFinPeriodo, int dias, int diasAdicionales, int diasTomados, HistoricoEmpleado historicoEmpleado)
/*  76:    */   {
/*  77:133 */     this.fechaInicioPeriodo = fechaInicioPeriodo;
/*  78:134 */     this.fechaFinPeriodo = fechaFinPeriodo;
/*  79:135 */     this.dias = dias;
/*  80:136 */     this.diasAdicionales = diasAdicionales;
/*  81:137 */     this.diasTomados = diasTomados;
/*  82:138 */     this.historicoEmpleado = historicoEmpleado;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdOrganizacion()
/*  86:    */   {
/*  87:151 */     return this.idOrganizacion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdOrganizacion(int idOrganizacion)
/*  91:    */   {
/*  92:161 */     this.idOrganizacion = idOrganizacion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getIdSucursal()
/*  96:    */   {
/*  97:170 */     return this.idSucursal;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setIdSucursal(int idSucursal)
/* 101:    */   {
/* 102:180 */     this.idSucursal = idSucursal;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int getIdVacacion()
/* 106:    */   {
/* 107:189 */     return this.idVacacion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setIdVacacion(int idVacacion)
/* 111:    */   {
/* 112:199 */     this.idVacacion = idVacacion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Date getFechaInicioPeriodo()
/* 116:    */   {
/* 117:208 */     return this.fechaInicioPeriodo;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setFechaInicioPeriodo(Date fechaInicioPeriodo)
/* 121:    */   {
/* 122:218 */     this.fechaInicioPeriodo = fechaInicioPeriodo;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Date getFechaFinPeriodo()
/* 126:    */   {
/* 127:227 */     return this.fechaFinPeriodo;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setFechaFinPeriodo(Date fechaFinPeriodo)
/* 131:    */   {
/* 132:237 */     this.fechaFinPeriodo = fechaFinPeriodo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int getDias()
/* 136:    */   {
/* 137:246 */     return this.dias;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setDias(int dias)
/* 141:    */   {
/* 142:256 */     this.dias = dias;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int getDiasAdicionales()
/* 146:    */   {
/* 147:265 */     return this.diasAdicionales;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setDiasAdicionales(int diasAdicionales)
/* 151:    */   {
/* 152:275 */     this.diasAdicionales = diasAdicionales;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public int getDiasTomados()
/* 156:    */   {
/* 157:284 */     return this.diasTomados;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setDiasTomados(int diasTomados)
/* 161:    */   {
/* 162:294 */     this.diasTomados = diasTomados;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List<DetalleVacacion> getListaDetalleVacacion()
/* 166:    */   {
/* 167:303 */     return this.listaDetalleVacacion;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setListaDetalleVacacion(List<DetalleVacacion> listaDetalleVacacion)
/* 171:    */   {
/* 172:313 */     this.listaDetalleVacacion = listaDetalleVacacion;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public HistoricoEmpleado getHistoricoEmpleado()
/* 176:    */   {
/* 177:322 */     return this.historicoEmpleado;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setHistoricoEmpleado(HistoricoEmpleado historicoEmpleado)
/* 181:    */   {
/* 182:332 */     this.historicoEmpleado = historicoEmpleado;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public List<PermisoEmpleado> getListaPermisoEmpleado()
/* 186:    */   {
/* 187:341 */     return this.listaPermisoEmpleado;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setListaPermisoEmpleado(List<PermisoEmpleado> listaPermisoEmpleado)
/* 191:    */   {
/* 192:351 */     this.listaPermisoEmpleado = listaPermisoEmpleado;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public boolean isIndicadorAnticipoVacacion()
/* 196:    */   {
/* 197:360 */     return this.indicadorAnticipoVacacion;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setIndicadorAnticipoVacacion(boolean indicadorAnticipoVacacion)
/* 201:    */   {
/* 202:370 */     this.indicadorAnticipoVacacion = indicadorAnticipoVacacion;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public String toString()
/* 206:    */   {
/* 207:380 */     return "Vacacion [idOrganizacion=" + this.idOrganizacion + ", idSucursal=" + this.idSucursal + ", idVacacion=" + this.idVacacion + ", fechaInicioPeriodo=" + this.fechaInicioPeriodo + ", fechaFinPeriodo=" + this.fechaFinPeriodo + ", dias=" + this.dias + ", diasAdicionales=" + this.diasAdicionales + ", diasTomados=" + this.diasTomados + ", listaDetalleVacacion=" + this.listaDetalleVacacion + ", historicoEmpleado=" + this.historicoEmpleado + ", listaPermisoEmpleado=" + this.listaPermisoEmpleado + ", fechaIngreso=" + "]";
/* 208:    */   }
/* 209:    */   
/* 210:    */   public int getId()
/* 211:    */   {
/* 212:393 */     return this.idVacacion;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Date getFechaIngreso()
/* 216:    */   {
/* 217:400 */     return this.fechaIngreso;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setFechaIngreso(Date fechaIngreso)
/* 221:    */   {
/* 222:408 */     this.fechaIngreso = fechaIngreso;
/* 223:    */   }
/* 224:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Vacacion
 * JD-Core Version:    0.7.0.1
 */