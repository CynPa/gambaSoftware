/*   1:    */ package com.asinfo.as2.entities.nomina.asistencia;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Departamento;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   6:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   7:    */ import java.io.Serializable;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Calendar;
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
/*  25:    */ import javax.persistence.Transient;
/*  26:    */ import javax.validation.constraints.Max;
/*  27:    */ import javax.validation.constraints.Min;
/*  28:    */ import javax.validation.constraints.NotNull;
/*  29:    */ import javax.validation.constraints.Size;
/*  30:    */ 
/*  31:    */ @Entity
/*  32:    */ @Table(name="plan_personalizado_horario_empleado", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_horario_empleado", "id_departamento", "anno", "mes"})})
/*  33:    */ public class PlanPersonalizadoHorarioEmpleado
/*  34:    */   extends EntidadBase
/*  35:    */   implements Serializable
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = 1L;
/*  38:    */   @Id
/*  39:    */   @TableGenerator(name="plan_personalizado_horario_empleado", initialValue=0, allocationSize=50)
/*  40:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="plan_personalizado_horario_empleado")
/*  41:    */   @Column(name="id_plan_personalizado_horario_empleado", unique=true, nullable=false)
/*  42:    */   private int idPlanPersonalizadoHorarioEmpleado;
/*  43:    */   @Column(name="id_organizacion", nullable=false)
/*  44:    */   private int idOrganizacion;
/*  45:    */   @Column(name="id_sucursal", nullable=false)
/*  46:    */   private int idSucursal;
/*  47:    */   @Column(name="descripcion", nullable=true, length=200)
/*  48:    */   @Size(max=200)
/*  49:    */   private String descripcion;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_horario_empleado", nullable=false)
/*  52:    */   @NotNull
/*  53:    */   private HorarioEmpleado horarioEmpleado;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_departamento", nullable=false)
/*  56:    */   @NotNull
/*  57:    */   private Departamento departamento;
/*  58:    */   @Column(name="anno", nullable=false)
/*  59:    */   @Min(2000L)
/*  60:    */   @Max(2500L)
/*  61:    */   @NotNull
/*  62:    */   private Integer anno;
/*  63:    */   @Column(name="mes", nullable=false)
/*  64:    */   @NotNull
/*  65:    */   @Enumerated(EnumType.ORDINAL)
/*  66:    */   private Mes mes;
/*  67:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="planPersonalizadoHorarioEmpleado")
/*  68: 93 */   private List<DetallePlanPersonalizadoHorarioEmpleado> listaDetallePlanPersonalizadoHorarioEmpleado = new ArrayList();
/*  69:    */   @Transient
/*  70:    */   Calendar primerDiaMes;
/*  71:    */   
/*  72:    */   public int getId()
/*  73:    */   {
/*  74:104 */     return this.idPlanPersonalizadoHorarioEmpleado;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getIdPlanPersonalizadoHorarioEmpleado()
/*  78:    */   {
/*  79:113 */     return this.idPlanPersonalizadoHorarioEmpleado;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIdPlanPersonalizadoHorarioEmpleado(int idPlanPersonalizadoHorarioEmpleado)
/*  83:    */   {
/*  84:123 */     this.idPlanPersonalizadoHorarioEmpleado = idPlanPersonalizadoHorarioEmpleado;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getIdOrganizacion()
/*  88:    */   {
/*  89:132 */     return this.idOrganizacion;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setIdOrganizacion(int idOrganizacion)
/*  93:    */   {
/*  94:142 */     this.idOrganizacion = idOrganizacion;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int getIdSucursal()
/*  98:    */   {
/*  99:151 */     return this.idSucursal;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setIdSucursal(int idSucursal)
/* 103:    */   {
/* 104:161 */     this.idSucursal = idSucursal;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getDescripcion()
/* 108:    */   {
/* 109:170 */     return this.descripcion;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setDescripcion(String descripcion)
/* 113:    */   {
/* 114:180 */     this.descripcion = descripcion;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public HorarioEmpleado getHorarioEmpleado()
/* 118:    */   {
/* 119:184 */     return this.horarioEmpleado;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setHorarioEmpleado(HorarioEmpleado horarioEmpleado)
/* 123:    */   {
/* 124:188 */     this.horarioEmpleado = horarioEmpleado;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Departamento getDepartamento()
/* 128:    */   {
/* 129:192 */     return this.departamento;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setDepartamento(Departamento departamento)
/* 133:    */   {
/* 134:196 */     this.departamento = departamento;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Integer getAnno()
/* 138:    */   {
/* 139:200 */     return this.anno;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setAnno(Integer anno)
/* 143:    */   {
/* 144:204 */     this.anno = anno;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Mes getMes()
/* 148:    */   {
/* 149:208 */     return this.mes;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setMes(Mes mes)
/* 153:    */   {
/* 154:212 */     this.mes = mes;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public List<DetallePlanPersonalizadoHorarioEmpleado> getListaDetallePlanPersonalizadoHorarioEmpleado()
/* 158:    */   {
/* 159:216 */     return this.listaDetallePlanPersonalizadoHorarioEmpleado;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setListaDetallePlanPersonalizadoHorarioEmpleado(List<DetallePlanPersonalizadoHorarioEmpleado> listaDetallePlanPersonalizadoHorarioEmpleado)
/* 163:    */   {
/* 164:220 */     this.listaDetallePlanPersonalizadoHorarioEmpleado = listaDetallePlanPersonalizadoHorarioEmpleado;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public Calendar getPrimerDiaMes()
/* 168:    */   {
/* 169:224 */     if ((this.mes != null) && (this.anno != null))
/* 170:    */     {
/* 171:225 */       this.primerDiaMes = Calendar.getInstance();
/* 172:226 */       this.primerDiaMes.setFirstDayOfWeek(1);
/* 173:227 */       this.primerDiaMes.set(this.anno.intValue(), this.mes.ordinal(), 1);
/* 174:    */     }
/* 175:    */     else
/* 176:    */     {
/* 177:229 */       this.primerDiaMes = null;
/* 178:    */     }
/* 179:231 */     return this.primerDiaMes;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public int cantidadDiasMes()
/* 183:    */   {
/* 184:235 */     if (getPrimerDiaMes() == null) {
/* 185:236 */       return 0;
/* 186:    */     }
/* 187:238 */     Date ultimoDiaMesD = FuncionesUtiles.getFechaFinMes(this.primerDiaMes.getTime());
/* 188:239 */     Calendar ultimoDiaMesC = Calendar.getInstance();
/* 189:240 */     ultimoDiaMesC.setTime(ultimoDiaMesD);
/* 190:241 */     return ultimoDiaMesC.get(5);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Integer getDiaMes(int numeroSemana, int diaSemana)
/* 194:    */   {
/* 195:245 */     if ((getPrimerDiaMes() == null) || (numeroSemana < 1) || (numeroSemana > 5) || (diaSemana < 1) || (diaSemana > 7)) {
/* 196:246 */       return null;
/* 197:    */     }
/* 198:248 */     int diaSemanaPrimerDiaMes = this.primerDiaMes.get(7);
/* 199:249 */     int diasPrimerSemana = 8 - diaSemanaPrimerDiaMes;
/* 200:251 */     if (numeroSemana == 1)
/* 201:    */     {
/* 202:252 */       if (diaSemana >= diaSemanaPrimerDiaMes) {
/* 203:253 */         return Integer.valueOf(diaSemana - diaSemanaPrimerDiaMes + 1);
/* 204:    */       }
/* 205:255 */       return null;
/* 206:    */     }
/* 207:259 */     int diaMes = (numeroSemana - 2) * 7 + diasPrimerSemana + diaSemana;
/* 208:261 */     if (diaMes > cantidadDiasMes()) {
/* 209:262 */       return null;
/* 210:    */     }
/* 211:264 */     return Integer.valueOf(diaMes);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setPrimerDiaMes(Calendar primerDiaMes)
/* 215:    */   {
/* 216:268 */     this.primerDiaMes = primerDiaMes;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public String toString()
/* 220:    */   {
/* 221:273 */     return this.idPlanPersonalizadoHorarioEmpleado + "";
/* 222:    */   }
/* 223:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.nomina.asistencia.PlanPersonalizadoHorarioEmpleado
 * JD-Core Version:    0.7.0.1
 */