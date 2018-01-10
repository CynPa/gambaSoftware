/*  1:   */ package com.asinfo.as2.entities.calidad;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.RegistroPeso;
/*  4:   */ import javax.persistence.Column;
/*  5:   */ import javax.persistence.Entity;
/*  6:   */ import javax.persistence.FetchType;
/*  7:   */ import javax.persistence.GeneratedValue;
/*  8:   */ import javax.persistence.GenerationType;
/*  9:   */ import javax.persistence.Id;
/* 10:   */ import javax.persistence.JoinColumn;
/* 11:   */ import javax.persistence.ManyToOne;
/* 12:   */ import javax.persistence.Table;
/* 13:   */ import javax.persistence.TableGenerator;
/* 14:   */ import javax.validation.constraints.NotNull;
/* 15:   */ 
/* 16:   */ @Entity
/* 17:   */ @Table(name="variable_calidad_registro_peso")
/* 18:   */ public class VariableCalidadRegistroPeso
/* 19:   */   extends VariableCalidadDetalleProceso
/* 20:   */ {
/* 21:   */   private static final long serialVersionUID = 1L;
/* 22:   */   @Id
/* 23:   */   @TableGenerator(name="variable_calidad_registro_peso", initialValue=0, allocationSize=50)
/* 24:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="variable_calidad_registro_peso")
/* 25:   */   @Column(name="id_variable_calidad_registro_peso")
/* 26:   */   private int idVariableCalidadRegistroPeso;
/* 27:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 28:   */   @JoinColumn(name="id_registro_peso", nullable=false)
/* 29:   */   @NotNull
/* 30:   */   private RegistroPeso registroPeso;
/* 31:   */   
/* 32:   */   public int getId()
/* 33:   */   {
/* 34:62 */     return this.idVariableCalidadRegistroPeso;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public int getIdVariableCalidadRegistroPeso()
/* 38:   */   {
/* 39:69 */     return this.idVariableCalidadRegistroPeso;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setIdVariableCalidadRegistroPeso(int idVariableCalidadRegistroPeso)
/* 43:   */   {
/* 44:77 */     this.idVariableCalidadRegistroPeso = idVariableCalidadRegistroPeso;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public RegistroPeso getRegistroPeso()
/* 48:   */   {
/* 49:84 */     return this.registroPeso;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setRegistroPeso(RegistroPeso registroPeso)
/* 53:   */   {
/* 54:92 */     this.registroPeso = registroPeso;
/* 55:   */   }
/* 56:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.calidad.VariableCalidadRegistroPeso
 * JD-Core Version:    0.7.0.1
 */