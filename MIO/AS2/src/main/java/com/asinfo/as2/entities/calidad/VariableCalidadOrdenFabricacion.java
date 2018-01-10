/*  1:   */ package com.asinfo.as2.entities.calidad;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.produccion.HistoricoCalidadOrdenFabricacion;
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
/* 17:   */ @Table(name="variable_calidad_orden_fabricacion")
/* 18:   */ public class VariableCalidadOrdenFabricacion
/* 19:   */   extends VariableCalidadDetalleProceso
/* 20:   */ {
/* 21:   */   private static final long serialVersionUID = 1L;
/* 22:   */   @Id
/* 23:   */   @TableGenerator(name="variable_calidad_orden_fabricacion", initialValue=0, allocationSize=50)
/* 24:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="variable_calidad_orden_fabricacion")
/* 25:   */   @Column(name="id_variable_calidad_orden_fabricacion")
/* 26:   */   private int idVariableCalidadOrdenFabricacion;
/* 27:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 28:   */   @JoinColumn(name="id_historico_calidad_orden_fabricacion", nullable=false)
/* 29:   */   @NotNull
/* 30:   */   private HistoricoCalidadOrdenFabricacion historicoCalidadOrdenFabricacion;
/* 31:   */   
/* 32:   */   public int getId()
/* 33:   */   {
/* 34:62 */     return this.idVariableCalidadOrdenFabricacion;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public int getIdVariableCalidadOrdenFabricacion()
/* 38:   */   {
/* 39:69 */     return this.idVariableCalidadOrdenFabricacion;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setIdVariableCalidadOrdenFabricacion(int idVariableCalidadOrdenFabricacion)
/* 43:   */   {
/* 44:77 */     this.idVariableCalidadOrdenFabricacion = idVariableCalidadOrdenFabricacion;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public HistoricoCalidadOrdenFabricacion getHistoricoCalidadOrdenFabricacion()
/* 48:   */   {
/* 49:81 */     return this.historicoCalidadOrdenFabricacion;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setHistoricoCalidadOrdenFabricacion(HistoricoCalidadOrdenFabricacion historicoCalidadOrdenFabricacion)
/* 53:   */   {
/* 54:85 */     this.historicoCalidadOrdenFabricacion = historicoCalidadOrdenFabricacion;
/* 55:   */   }
/* 56:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.calidad.VariableCalidadOrdenFabricacion
 * JD-Core Version:    0.7.0.1
 */