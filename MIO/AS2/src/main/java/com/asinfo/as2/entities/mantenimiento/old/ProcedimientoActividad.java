/*   1:    */ package com.asinfo.as2.entities.mantenimiento.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="procedimiento_actividad")
/*  18:    */ public class ProcedimientoActividad
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 8516955878929372485L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="procedimiento_actividad", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="procedimiento_actividad")
/*  25:    */   @Column(name="id_procedimiento_actividad")
/*  26:    */   private int idProcedimientoActividad;
/*  27:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  28:    */   @JoinColumn(name="id_procedimiento", nullable=true)
/*  29:    */   private Procedimiento procedimiento;
/*  30:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  31:    */   @JoinColumn(name="id_actividad", nullable=false)
/*  32:    */   @NotNull
/*  33: 59 */   private Actividad actividad = new Actividad();
/*  34:    */   
/*  35:    */   public int getIdProcedimientoActividad()
/*  36:    */   {
/*  37: 89 */     return this.idProcedimientoActividad;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setIdProcedimientoActividad(int idProcedimientoActividad)
/*  41:    */   {
/*  42: 99 */     this.idProcedimientoActividad = idProcedimientoActividad;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Procedimiento getProcedimiento()
/*  46:    */   {
/*  47:108 */     return this.procedimiento;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setProcedimiento(Procedimiento procedimiento)
/*  51:    */   {
/*  52:118 */     this.procedimiento = procedimiento;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Actividad getActividad()
/*  56:    */   {
/*  57:127 */     return this.actividad;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setActividad(Actividad actividad)
/*  61:    */   {
/*  62:137 */     this.actividad = actividad;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getId()
/*  66:    */   {
/*  67:147 */     return this.idProcedimientoActividad;
/*  68:    */   }
/*  69:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.old.ProcedimientoActividad
 * JD-Core Version:    0.7.0.1
 */