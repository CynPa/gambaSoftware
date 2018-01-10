/*  1:   */ package com.asinfo.as2.entities;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.produccion.Maquina;
/*  4:   */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
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
/* 16:   */ import javax.persistence.Transient;
/* 17:   */ import javax.validation.constraints.NotNull;
/* 18:   */ 
/* 19:   */ @Entity
/* 20:   */ @Table(name="orden_fabricacion_maquina", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_orden_fabricacion", "id_maquina"})})
/* 21:   */ public class OrdenFabricacionMaquina
/* 22:   */   extends EntidadBase
/* 23:   */   implements Serializable
/* 24:   */ {
/* 25:   */   private static final long serialVersionUID = 1L;
/* 26:   */   @Id
/* 27:   */   @TableGenerator(name="orden_fabricacion_maquina", initialValue=0, allocationSize=50)
/* 28:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="orden_fabricacion_maquina")
/* 29:   */   @Column(name="id_orden_fabricacion_maquina")
/* 30:   */   private int idOrdenFabricacionMaquina;
/* 31:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 32:   */   @JoinColumn(name="id_maquina", nullable=false)
/* 33:   */   @NotNull
/* 34:   */   private Maquina maquina;
/* 35:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 36:   */   @JoinColumn(name="id_orden_fabricacion", nullable=true)
/* 37:   */   private OrdenFabricacion ordenFabricacion;
/* 38:   */   @Transient
/* 39:   */   private boolean seleccionado;
/* 40:   */   
/* 41:   */   public int getId()
/* 42:   */   {
/* 43:58 */     return this.idOrdenFabricacionMaquina;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public int getIdOrdenFabricacionMaquina()
/* 47:   */   {
/* 48:66 */     return this.idOrdenFabricacionMaquina;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void setIdOrdenFabricacionMaquina(int idOrdenFabricacionMaquina)
/* 52:   */   {
/* 53:70 */     this.idOrdenFabricacionMaquina = idOrdenFabricacionMaquina;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public Maquina getMaquina()
/* 57:   */   {
/* 58:74 */     return this.maquina;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void setMaquina(Maquina maquina)
/* 62:   */   {
/* 63:78 */     this.maquina = maquina;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public OrdenFabricacion getOrdenFabricacion()
/* 67:   */   {
/* 68:82 */     return this.ordenFabricacion;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/* 72:   */   {
/* 73:86 */     this.ordenFabricacion = ordenFabricacion;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public boolean isSeleccionado()
/* 77:   */   {
/* 78:90 */     return this.seleccionado;
/* 79:   */   }
/* 80:   */   
/* 81:   */   public void setSeleccionado(boolean seleccionado)
/* 82:   */   {
/* 83:94 */     this.seleccionado = seleccionado;
/* 84:   */   }
/* 85:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.OrdenFabricacionMaquina
 * JD-Core Version:    0.7.0.1
 */