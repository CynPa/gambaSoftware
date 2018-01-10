/*   1:    */ package com.asinfo.as2.entities.nomina.asistencia;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Departamento;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import java.io.Serializable;
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
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="turno_departamento", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_departamento", "id_turno"})})
/*  19:    */ public class TurnoDepartamento
/*  20:    */   extends EntidadBase
/*  21:    */   implements Serializable
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="turno_departamento", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="turno_departamento")
/*  27:    */   @Column(name="id_turno_departamento", unique=true, nullable=false)
/*  28:    */   private int idTurnoDepartamento;
/*  29:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  30:    */   @JoinColumn(name="id_departamento")
/*  31:    */   private Departamento departamento;
/*  32:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  33:    */   @JoinColumn(name="id_turno")
/*  34:    */   private Turno turno;
/*  35:    */   
/*  36:    */   public int getId()
/*  37:    */   {
/*  38: 59 */     return this.idTurnoDepartamento;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getIdTurnoDepartamento()
/*  42:    */   {
/*  43: 66 */     return this.idTurnoDepartamento;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setIdTurnoDepartamento(int idTurnoDepartamento)
/*  47:    */   {
/*  48: 74 */     this.idTurnoDepartamento = idTurnoDepartamento;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Departamento getDepartamento()
/*  52:    */   {
/*  53: 81 */     return this.departamento;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setDepartamento(Departamento departamento)
/*  57:    */   {
/*  58: 89 */     this.departamento = departamento;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Turno getTurno()
/*  62:    */   {
/*  63: 96 */     return this.turno;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setTurno(Turno turno)
/*  67:    */   {
/*  68:104 */     this.turno = turno;
/*  69:    */   }
/*  70:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.nomina.asistencia.TurnoDepartamento
 * JD-Core Version:    0.7.0.1
 */