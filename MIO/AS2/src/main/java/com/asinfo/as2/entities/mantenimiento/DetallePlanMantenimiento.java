/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.enumeraciones.PrioridadEnum;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.EnumType;
/*  12:    */ import javax.persistence.Enumerated;
/*  13:    */ import javax.persistence.FetchType;
/*  14:    */ import javax.persistence.GeneratedValue;
/*  15:    */ import javax.persistence.GenerationType;
/*  16:    */ import javax.persistence.Id;
/*  17:    */ import javax.persistence.JoinColumn;
/*  18:    */ import javax.persistence.ManyToOne;
/*  19:    */ import javax.persistence.OneToMany;
/*  20:    */ import javax.persistence.Table;
/*  21:    */ import javax.persistence.TableGenerator;
/*  22:    */ import javax.validation.constraints.Min;
/*  23:    */ import javax.validation.constraints.NotNull;
/*  24:    */ 
/*  25:    */ @Entity
/*  26:    */ @Table(name="detalle_plan_mantenimiento", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_plan_mantenimiento", "id_componente_equipo", "id_actividad_mantenimiento"})})
/*  27:    */ public class DetallePlanMantenimiento
/*  28:    */   extends EntidadBase
/*  29:    */   implements Serializable
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @Id
/*  33:    */   @TableGenerator(name="detalle_plan_mantenimiento", initialValue=0, allocationSize=50)
/*  34:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_plan_mantenimiento")
/*  35:    */   @Column(name="id_detalle_plan_mantenimiento")
/*  36:    */   private int idDetallePlanMantenimiento;
/*  37:    */   @Column(name="id_organizacion")
/*  38:    */   private int idOrganizacion;
/*  39:    */   @Column(name="id_sucursal")
/*  40:    */   private int idSucursal;
/*  41:    */   @Column(name="duracion", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   @Min(0L)
/*  44:    */   private BigDecimal duracion;
/*  45:    */   @Column(name="prioridad", nullable=false)
/*  46:    */   @Enumerated(EnumType.ORDINAL)
/*  47:    */   @NotNull
/*  48:    */   private PrioridadEnum prioridad;
/*  49:    */   @Column(name="requiere_paro", nullable=false)
/*  50:    */   private boolean requiereParo;
/*  51:    */   @Column(name="horas_paro", nullable=true)
/*  52:    */   @Min(0L)
/*  53:    */   private BigDecimal horasParo;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_plan_mantenimiento", nullable=false)
/*  56:    */   @NotNull
/*  57:    */   private PlanMantenimiento planMantenimiento;
/*  58:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_componente_equipo", nullable=false)
/*  60:    */   @NotNull
/*  61:    */   private ComponenteEquipo componente;
/*  62:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  63:    */   @JoinColumn(name="id_actividad_mantenimiento", nullable=false)
/*  64:    */   @NotNull
/*  65:    */   private ActividadMantenimiento actividad;
/*  66:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detallePlanMantenimiento")
/*  67: 89 */   private List<DetallePlanMantenimientoFrecuencia> listaDetallePlanMantenimientoFrecuencia = new ArrayList();
/*  68:    */   
/*  69:    */   public int getId()
/*  70:    */   {
/*  71:102 */     return this.idDetallePlanMantenimiento;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdDetallePlanMantenimiento()
/*  75:    */   {
/*  76:109 */     return this.idDetallePlanMantenimiento;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdDetallePlanMantenimiento(int idDetallePlanMantenimiento)
/*  80:    */   {
/*  81:117 */     this.idDetallePlanMantenimiento = idDetallePlanMantenimiento;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getIdOrganizacion()
/*  85:    */   {
/*  86:124 */     return this.idOrganizacion;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setIdOrganizacion(int idOrganizacion)
/*  90:    */   {
/*  91:132 */     this.idOrganizacion = idOrganizacion;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getIdSucursal()
/*  95:    */   {
/*  96:139 */     return this.idSucursal;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setIdSucursal(int idSucursal)
/* 100:    */   {
/* 101:147 */     this.idSucursal = idSucursal;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public BigDecimal getDuracion()
/* 105:    */   {
/* 106:154 */     return this.duracion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setDuracion(BigDecimal duracion)
/* 110:    */   {
/* 111:162 */     this.duracion = duracion;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public PrioridadEnum getPrioridad()
/* 115:    */   {
/* 116:169 */     return this.prioridad;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setPrioridad(PrioridadEnum prioridad)
/* 120:    */   {
/* 121:177 */     this.prioridad = prioridad;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean isRequiereParo()
/* 125:    */   {
/* 126:184 */     return this.requiereParo;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setRequiereParo(boolean requiereParo)
/* 130:    */   {
/* 131:192 */     this.requiereParo = requiereParo;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public BigDecimal getHorasParo()
/* 135:    */   {
/* 136:196 */     return this.horasParo;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setHorasParo(BigDecimal horasParo)
/* 140:    */   {
/* 141:200 */     this.horasParo = horasParo;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public PlanMantenimiento getPlanMantenimiento()
/* 145:    */   {
/* 146:207 */     return this.planMantenimiento;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setPlanMantenimiento(PlanMantenimiento planMantenimiento)
/* 150:    */   {
/* 151:215 */     this.planMantenimiento = planMantenimiento;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public ComponenteEquipo getComponente()
/* 155:    */   {
/* 156:222 */     return this.componente;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setComponente(ComponenteEquipo componente)
/* 160:    */   {
/* 161:230 */     this.componente = componente;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public ActividadMantenimiento getActividad()
/* 165:    */   {
/* 166:237 */     return this.actividad;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setActividad(ActividadMantenimiento actividad)
/* 170:    */   {
/* 171:245 */     this.actividad = actividad;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public List<DetallePlanMantenimientoFrecuencia> getListaDetallePlanMantenimientoFrecuenciaView()
/* 175:    */   {
/* 176:249 */     List<DetallePlanMantenimientoFrecuencia> lresult = new ArrayList();
/* 177:250 */     for (DetallePlanMantenimientoFrecuencia rf : getListaDetallePlanMantenimientoFrecuencia()) {
/* 178:251 */       if (!rf.isEliminado()) {
/* 179:252 */         lresult.add(rf);
/* 180:    */       }
/* 181:    */     }
/* 182:256 */     return lresult;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public List<DetallePlanMantenimientoFrecuencia> getListaDetallePlanMantenimientoFrecuencia()
/* 186:    */   {
/* 187:260 */     return this.listaDetallePlanMantenimientoFrecuencia;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setListaDetallePlanMantenimientoFrecuencia(List<DetallePlanMantenimientoFrecuencia> listaDetallePlanMantenimientoFrecuencia)
/* 191:    */   {
/* 192:264 */     this.listaDetallePlanMantenimientoFrecuencia = listaDetallePlanMantenimientoFrecuencia;
/* 193:    */   }
/* 194:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.DetallePlanMantenimiento
 * JD-Core Version:    0.7.0.1
 */