/*  1:   */ package com.asinfo.as2.entities.mantenimiento;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.EntidadBase;
/*  4:   */ import com.asinfo.as2.entities.Especialidad;
/*  5:   */ import java.io.Serializable;
/*  6:   */ import javax.persistence.Column;
/*  7:   */ import javax.persistence.Entity;
/*  8:   */ import javax.persistence.FetchType;
/*  9:   */ import javax.persistence.GeneratedValue;
/* 10:   */ import javax.persistence.GenerationType;
/* 11:   */ import javax.persistence.Id;
/* 12:   */ import javax.persistence.JoinColumn;
/* 13:   */ import javax.persistence.ManyToOne;
/* 14:   */ import javax.persistence.Table;
/* 15:   */ import javax.persistence.TableGenerator;
/* 16:   */ import javax.validation.constraints.NotNull;
/* 17:   */ 
/* 18:   */ @Entity
/* 19:   */ @Table(name="actividad_mantenimiento_especialidad", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_actividad_mantenimiento", "id_especialidad"})})
/* 20:   */ public class ActividadMantenimientoEspecialidad
/* 21:   */   extends EntidadBase
/* 22:   */   implements Serializable
/* 23:   */ {
/* 24:   */   private static final long serialVersionUID = 1L;
/* 25:   */   @Id
/* 26:   */   @TableGenerator(name="actividad_mantenimiento_especialidad", initialValue=0, allocationSize=50)
/* 27:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="actividad_mantenimiento_especialidad")
/* 28:   */   @Column(name="id_actividad_mantenimiento_especialidad")
/* 29:   */   private int idActividadMantenimientoEspecialidad;
/* 30:   */   @Column(name="id_sucursal", nullable=false)
/* 31:   */   @NotNull
/* 32:   */   private int idSucursal;
/* 33:   */   @Column(name="id_organizacion", nullable=false)
/* 34:   */   @NotNull
/* 35:   */   private int idOrganizacion;
/* 36:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 37:   */   @JoinColumn(name="id_actividad_mantenimiento", nullable=false)
/* 38:   */   @NotNull
/* 39:   */   private ActividadMantenimiento actividadMantenimiento;
/* 40:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 41:   */   @JoinColumn(name="id_especialidad", nullable=false)
/* 42:   */   @NotNull
/* 43:   */   private Especialidad especialidad;
/* 44:   */   
/* 45:   */   public int getId()
/* 46:   */   {
/* 47:56 */     return this.idActividadMantenimientoEspecialidad;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public int getIdActividadMantenimientoEspecialidad()
/* 51:   */   {
/* 52:60 */     return this.idActividadMantenimientoEspecialidad;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void setIdActividadMantenimientoEspecialidad(int idActividadMantenimientoEspecialidad)
/* 56:   */   {
/* 57:64 */     this.idActividadMantenimientoEspecialidad = idActividadMantenimientoEspecialidad;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public int getIdSucursal()
/* 61:   */   {
/* 62:68 */     return this.idSucursal;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void setIdSucursal(int idSucursal)
/* 66:   */   {
/* 67:72 */     this.idSucursal = idSucursal;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public int getIdOrganizacion()
/* 71:   */   {
/* 72:76 */     return this.idOrganizacion;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public void setIdOrganizacion(int idOrganizacion)
/* 76:   */   {
/* 77:80 */     this.idOrganizacion = idOrganizacion;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public ActividadMantenimiento getActividadMantenimiento()
/* 81:   */   {
/* 82:84 */     return this.actividadMantenimiento;
/* 83:   */   }
/* 84:   */   
/* 85:   */   public void setActividadMantenimiento(ActividadMantenimiento actividadMantenimiento)
/* 86:   */   {
/* 87:88 */     this.actividadMantenimiento = actividadMantenimiento;
/* 88:   */   }
/* 89:   */   
/* 90:   */   public Especialidad getEspecialidad()
/* 91:   */   {
/* 92:92 */     return this.especialidad;
/* 93:   */   }
/* 94:   */   
/* 95:   */   public void setEspecialidad(Especialidad especialidad)
/* 96:   */   {
/* 97:96 */     this.especialidad = especialidad;
/* 98:   */   }
/* 99:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.ActividadMantenimientoEspecialidad
 * JD-Core Version:    0.7.0.1
 */