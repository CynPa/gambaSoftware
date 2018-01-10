/*  1:   */ package com.asinfo.as2.entities;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
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
/* 17:   */ @Table(name="departamento_rubro")
/* 18:   */ public class DepartamentoRubro
/* 19:   */   extends EntidadBase
/* 20:   */   implements Serializable
/* 21:   */ {
/* 22:   */   private static final long serialVersionUID = 1L;
/* 23:   */   @Id
/* 24:   */   @TableGenerator(name="departamento_rubro", initialValue=0, allocationSize=50)
/* 25:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="departamento_rubro")
/* 26:   */   @Column(name="id_departamento_rubro", unique=true, nullable=false)
/* 27:   */   private int idDepartamentoRubro;
/* 28:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 29:   */   @JoinColumn(name="id_rubro", nullable=false, insertable=true, updatable=false)
/* 30:   */   @NotNull
/* 31:   */   private Rubro rubro;
/* 32:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 33:   */   @JoinColumn(name="id_departamento", nullable=false, insertable=true, updatable=false)
/* 34:   */   @NotNull
/* 35:   */   private Departamento departamento;
/* 36:   */   
/* 37:   */   public int getIdDepartamentoRubro()
/* 38:   */   {
/* 39:50 */     return this.idDepartamentoRubro;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setIdDepartamentoRubro(int idDepartamentoRubro)
/* 43:   */   {
/* 44:58 */     this.idDepartamentoRubro = idDepartamentoRubro;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Rubro getRubro()
/* 48:   */   {
/* 49:65 */     return this.rubro;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setRubro(Rubro rubro)
/* 53:   */   {
/* 54:73 */     this.rubro = rubro;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public Departamento getDepartamento()
/* 58:   */   {
/* 59:80 */     return this.departamento;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void setDepartamento(Departamento departamento)
/* 63:   */   {
/* 64:88 */     this.departamento = departamento;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public int getId()
/* 68:   */   {
/* 69:96 */     return this.idDepartamentoRubro;
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DepartamentoRubro
 * JD-Core Version:    0.7.0.1
 */