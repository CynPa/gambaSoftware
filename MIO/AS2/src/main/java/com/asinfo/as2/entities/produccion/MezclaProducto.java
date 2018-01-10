/*  1:   */ package com.asinfo.as2.entities.produccion;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.EntidadBase;
/*  4:   */ import com.asinfo.as2.entities.Producto;
/*  5:   */ import java.io.Serializable;
/*  6:   */ import java.math.BigDecimal;
/*  7:   */ import javax.persistence.Column;
/*  8:   */ import javax.persistence.Entity;
/*  9:   */ import javax.persistence.FetchType;
/* 10:   */ import javax.persistence.GeneratedValue;
/* 11:   */ import javax.persistence.GenerationType;
/* 12:   */ import javax.persistence.Id;
/* 13:   */ import javax.persistence.JoinColumn;
/* 14:   */ import javax.persistence.ManyToOne;
/* 15:   */ import javax.persistence.Table;
/* 16:   */ import javax.persistence.TableGenerator;
/* 17:   */ import javax.validation.constraints.Digits;
/* 18:   */ import javax.validation.constraints.Min;
/* 19:   */ import javax.validation.constraints.NotNull;
/* 20:   */ 
/* 21:   */ @Entity
/* 22:   */ @Table(name="mezcla_producto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_producto", "id_producto_mezcla"})})
/* 23:   */ public class MezclaProducto
/* 24:   */   extends EntidadBase
/* 25:   */   implements Serializable
/* 26:   */ {
/* 27:   */   private static final long serialVersionUID = 1L;
/* 28:   */   @Id
/* 29:   */   @TableGenerator(name="mezcla_producto", initialValue=0, allocationSize=50)
/* 30:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="mezcla_producto")
/* 31:   */   @Column(name="id_mezcla_producto", unique=true, nullable=false)
/* 32:   */   private int idMezclaProducto;
/* 33:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 34:   */   @JoinColumn(name="id_producto", nullable=true)
/* 35:   */   private Producto producto;
/* 36:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 37:   */   @JoinColumn(name="id_producto_mezcla", nullable=false)
/* 38:   */   @NotNull
/* 39:   */   private Producto productoMezcla;
/* 40:   */   @Column(name="porcentaje", nullable=false, precision=12, scale=2)
/* 41:   */   @Digits(integer=10, fraction=2)
/* 42:   */   @Min(0L)
/* 43:47 */   private BigDecimal porcentaje = BigDecimal.ZERO;
/* 44:   */   
/* 45:   */   public int getId()
/* 46:   */   {
/* 47:54 */     return this.idMezclaProducto;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public int getIdMezclaProducto()
/* 51:   */   {
/* 52:58 */     return this.idMezclaProducto;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void setIdMezclaProducto(int idMezclaProducto)
/* 56:   */   {
/* 57:62 */     this.idMezclaProducto = idMezclaProducto;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public Producto getProductoMezcla()
/* 61:   */   {
/* 62:66 */     return this.productoMezcla;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void setProductoMezcla(Producto productoMezcla)
/* 66:   */   {
/* 67:70 */     this.productoMezcla = productoMezcla;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public Producto getProducto()
/* 71:   */   {
/* 72:74 */     return this.producto;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public void setProducto(Producto producto)
/* 76:   */   {
/* 77:78 */     this.producto = producto;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public BigDecimal getPorcentaje()
/* 81:   */   {
/* 82:82 */     return this.porcentaje;
/* 83:   */   }
/* 84:   */   
/* 85:   */   public void setPorcentaje(BigDecimal porcentaje)
/* 86:   */   {
/* 87:86 */     this.porcentaje = porcentaje;
/* 88:   */   }
/* 89:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.MezclaProducto
 * JD-Core Version:    0.7.0.1
 */