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
/* 17:   */ @Table(name="categoria_rubro_rubro")
/* 18:   */ public class CategoriaRubroRubro
/* 19:   */   extends EntidadBase
/* 20:   */   implements Serializable
/* 21:   */ {
/* 22:   */   private static final long serialVersionUID = 1L;
/* 23:   */   @Id
/* 24:   */   @TableGenerator(name="categoria_rubro_rubro", initialValue=0, allocationSize=50)
/* 25:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="categoria_rubro_rubro")
/* 26:   */   @Column(name="id_categoria_rubro_rubro", unique=true, nullable=false)
/* 27:   */   private int idCategoriaRubroRubro;
/* 28:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 29:   */   @JoinColumn(name="id_rubro", nullable=false, insertable=true, updatable=false)
/* 30:   */   @NotNull
/* 31:   */   private Rubro rubro;
/* 32:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 33:   */   @JoinColumn(name="id_categoria_rubro", nullable=false, insertable=true, updatable=false)
/* 34:   */   @NotNull
/* 35:   */   private CategoriaRubro categoriaRubro;
/* 36:   */   
/* 37:   */   public int getIdCategoriaRubroRubro()
/* 38:   */   {
/* 39:48 */     return this.idCategoriaRubroRubro;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setIdCategoriaRubroRubro(int idCategoriaRubroRubro)
/* 43:   */   {
/* 44:52 */     this.idCategoriaRubroRubro = idCategoriaRubroRubro;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Rubro getRubro()
/* 48:   */   {
/* 49:59 */     return this.rubro;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setRubro(Rubro rubro)
/* 53:   */   {
/* 54:67 */     this.rubro = rubro;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public CategoriaRubro getCategoriaRubro()
/* 58:   */   {
/* 59:75 */     return this.categoriaRubro;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void setCategoriaRubro(CategoriaRubro categoriaRubro)
/* 63:   */   {
/* 64:84 */     this.categoriaRubro = categoriaRubro;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public int getId()
/* 68:   */   {
/* 69:92 */     return this.idCategoriaRubroRubro;
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CategoriaRubroRubro
 * JD-Core Version:    0.7.0.1
 */