/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="plan_mantenimiento_equipo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_plan_mantenimiento", "id_equipo"})})
/*  19:    */ public class PlanMantenimientoEquipo
/*  20:    */   extends EntidadBase
/*  21:    */   implements Serializable
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="plan_mantenimiento_equipo", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="plan_mantenimiento_equipo")
/*  27:    */   @Column(name="id_plan_mantenimiento_equipo")
/*  28:    */   private int idPlanMantenimientoEquipo;
/*  29:    */   @Column(name="id_organizacion")
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal")
/*  32:    */   private int idSucursal;
/*  33:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  34:    */   @JoinColumn(name="id_plan_mantenimiento", nullable=false)
/*  35:    */   @NotNull
/*  36:    */   private PlanMantenimiento planMantenimiento;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_equipo", nullable=true)
/*  39:    */   private Equipo equipo;
/*  40:    */   
/*  41:    */   public int getId()
/*  42:    */   {
/*  43: 68 */     return this.idPlanMantenimientoEquipo;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getIdPlanMantenimientoEquipo()
/*  47:    */   {
/*  48: 72 */     return this.idPlanMantenimientoEquipo;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setIdPlanMantenimientoEquipo(int idPlanMantenimientoEquipo)
/*  52:    */   {
/*  53: 76 */     this.idPlanMantenimientoEquipo = idPlanMantenimientoEquipo;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdOrganizacion()
/*  57:    */   {
/*  58: 80 */     return this.idOrganizacion;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdOrganizacion(int idOrganizacion)
/*  62:    */   {
/*  63: 84 */     this.idOrganizacion = idOrganizacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdSucursal()
/*  67:    */   {
/*  68: 88 */     return this.idSucursal;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdSucursal(int idSucursal)
/*  72:    */   {
/*  73: 92 */     this.idSucursal = idSucursal;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public PlanMantenimiento getPlanMantenimiento()
/*  77:    */   {
/*  78: 96 */     return this.planMantenimiento;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setPlanMantenimiento(PlanMantenimiento planMantenimiento)
/*  82:    */   {
/*  83:100 */     this.planMantenimiento = planMantenimiento;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Equipo getEquipo()
/*  87:    */   {
/*  88:104 */     return this.equipo;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setEquipo(Equipo equipo)
/*  92:    */   {
/*  93:108 */     this.equipo = equipo;
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.PlanMantenimientoEquipo
 * JD-Core Version:    0.7.0.1
 */