/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.Date;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.persistence.Temporal;
/*  17:    */ import javax.persistence.TemporalType;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="calendario_mantenimiento", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_equipo", "id_detalle_plan_mantenimiento"})})
/*  22:    */ public class CalendarioMantenimientoEntidad
/*  23:    */   extends EntidadBase
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="calendario_mantenimiento", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="calendario_mantenimiento")
/*  30:    */   @Column(name="id_calendario_mantenimiento")
/*  31:    */   private int idCalendarioMantenimiento;
/*  32:    */   @Column(name="id_organizacion")
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal")
/*  35:    */   private int idSucursal;
/*  36:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_equipo", nullable=false)
/*  38:    */   @NotNull
/*  39:    */   private Equipo equipo;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_detalle_plan_mantenimiento", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private DetallePlanMantenimiento detallePlanMantenimiento;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_detalle_orden_trabajo_mantenimiento", nullable=true)
/*  46:    */   private DetalleOrdenTrabajoMantenimiento detalleOrdenTrabajoMantenimiento;
/*  47:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  48:    */   @JoinColumn(name="id_lectura_mantenimiento", nullable=true)
/*  49:    */   private LecturaMantenimiento lecturaMantenimiento;
/*  50:    */   @Temporal(TemporalType.DATE)
/*  51:    */   @Column(name="fecha", nullable=false)
/*  52:    */   @NotNull
/*  53:    */   private Date fecha;
/*  54:    */   @Temporal(TemporalType.DATE)
/*  55:    */   @Column(name="fecha_modificada", nullable=false)
/*  56:    */   @NotNull
/*  57:    */   private Date fechaModificada;
/*  58:    */   
/*  59:    */   public int getId()
/*  60:    */   {
/*  61: 70 */     return this.idCalendarioMantenimiento;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdCalendarioMantenimiento()
/*  65:    */   {
/*  66: 74 */     return this.idCalendarioMantenimiento;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdCalendarioMantenimiento(int idCalendarioMantenimiento)
/*  70:    */   {
/*  71: 78 */     this.idCalendarioMantenimiento = idCalendarioMantenimiento;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdOrganizacion()
/*  75:    */   {
/*  76: 82 */     return this.idOrganizacion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdOrganizacion(int idOrganizacion)
/*  80:    */   {
/*  81: 86 */     this.idOrganizacion = idOrganizacion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getIdSucursal()
/*  85:    */   {
/*  86: 90 */     return this.idSucursal;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setIdSucursal(int idSucursal)
/*  90:    */   {
/*  91: 94 */     this.idSucursal = idSucursal;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Equipo getEquipo()
/*  95:    */   {
/*  96: 98 */     return this.equipo;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setEquipo(Equipo equipo)
/* 100:    */   {
/* 101:102 */     this.equipo = equipo;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public DetallePlanMantenimiento getDetallePlanMantenimiento()
/* 105:    */   {
/* 106:106 */     return this.detallePlanMantenimiento;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setDetallePlanMantenimiento(DetallePlanMantenimiento detallePlanMantenimiento)
/* 110:    */   {
/* 111:110 */     this.detallePlanMantenimiento = detallePlanMantenimiento;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Date getFecha()
/* 115:    */   {
/* 116:114 */     return this.fecha;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setFecha(Date fecha)
/* 120:    */   {
/* 121:118 */     this.fecha = fecha;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Date getFechaModificada()
/* 125:    */   {
/* 126:122 */     return this.fechaModificada;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setFechaModificada(Date fechaModificada)
/* 130:    */   {
/* 131:126 */     this.fechaModificada = fechaModificada;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public DetalleOrdenTrabajoMantenimiento getDetalleOrdenTrabajoMantenimiento()
/* 135:    */   {
/* 136:130 */     return this.detalleOrdenTrabajoMantenimiento;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setDetalleOrdenTrabajoMantenimiento(DetalleOrdenTrabajoMantenimiento detalleOrdenTrabajoMantenimiento)
/* 140:    */   {
/* 141:134 */     this.detalleOrdenTrabajoMantenimiento = detalleOrdenTrabajoMantenimiento;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public LecturaMantenimiento getLecturaMantenimiento()
/* 145:    */   {
/* 146:138 */     return this.lecturaMantenimiento;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setLecturaMantenimiento(LecturaMantenimiento lecturaMantenimiento)
/* 150:    */   {
/* 151:142 */     this.lecturaMantenimiento = lecturaMantenimiento;
/* 152:    */   }
/* 153:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.CalendarioMantenimientoEntidad
 * JD-Core Version:    0.7.0.1
 */