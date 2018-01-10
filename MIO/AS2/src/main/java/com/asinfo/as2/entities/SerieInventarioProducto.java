/*  1:   */ package com.asinfo.as2.entities;
/*  2:   */ 
/*  3:   */ import javax.persistence.Column;
/*  4:   */ import javax.persistence.Entity;
/*  5:   */ import javax.persistence.FetchType;
/*  6:   */ import javax.persistence.GeneratedValue;
/*  7:   */ import javax.persistence.GenerationType;
/*  8:   */ import javax.persistence.Id;
/*  9:   */ import javax.persistence.JoinColumn;
/* 10:   */ import javax.persistence.ManyToOne;
/* 11:   */ import javax.persistence.Table;
/* 12:   */ import javax.persistence.TableGenerator;
/* 13:   */ import javax.validation.constraints.NotNull;
/* 14:   */ 
/* 15:   */ @Entity
/* 16:   */ @Table(name="serie_inventario_producto")
/* 17:   */ public class SerieInventarioProducto
/* 18:   */   extends EntidadBase
/* 19:   */ {
/* 20:   */   private static final long serialVersionUID = 1L;
/* 21:   */   @Id
/* 22:   */   @TableGenerator(name="serie_inventario_producto", initialValue=0, allocationSize=50)
/* 23:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="serie_inventario_producto")
/* 24:   */   @Column(name="id_serie_inventario_producto")
/* 25:   */   private int idSerieInventarioProducto;
/* 26:   */   @ManyToOne
/* 27:   */   @JoinColumn(name="id_inventario_producto")
/* 28:   */   private InventarioProducto inventarioProducto;
/* 29:   */   @NotNull
/* 30:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 31:   */   @JoinColumn(name="id_serie_producto", nullable=false)
/* 32:   */   private SerieProducto serieProducto;
/* 33:   */   
/* 34:   */   public int getId()
/* 35:   */   {
/* 36:51 */     return this.idSerieInventarioProducto;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public int getIdSerieInventarioProducto()
/* 40:   */   {
/* 41:59 */     return this.idSerieInventarioProducto;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void setIdSerieInventarioProducto(int idSerieInventarioProducto)
/* 45:   */   {
/* 46:63 */     this.idSerieInventarioProducto = idSerieInventarioProducto;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public InventarioProducto getInventarioProducto()
/* 50:   */   {
/* 51:67 */     return this.inventarioProducto;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void setInventarioProducto(InventarioProducto inventarioProducto)
/* 55:   */   {
/* 56:71 */     this.inventarioProducto = inventarioProducto;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public SerieProducto getSerieProducto()
/* 60:   */   {
/* 61:75 */     return this.serieProducto;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void setSerieProducto(SerieProducto serieProducto)
/* 65:   */   {
/* 66:79 */     this.serieProducto = serieProducto;
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.SerieInventarioProducto
 * JD-Core Version:    0.7.0.1
 */