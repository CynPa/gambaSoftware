/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.entities.Producto;
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
/*  18:    */ @Table(name="producto_ruta_fabricacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_producto", "id_ruta_fabricacion"})})
/*  19:    */ public class ProductoRutaFabricacion
/*  20:    */   extends EntidadBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 4707199315707304297L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="producto_ruta_fabricacion", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="producto_ruta_fabricacion")
/*  26:    */   @Column(name="id_producto_ruta_fabricacion")
/*  27:    */   private int idProductoRutaFabricacion;
/*  28:    */   @NotNull
/*  29:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  30:    */   @JoinColumn(name="id_producto", nullable=false)
/*  31:    */   private Producto producto;
/*  32:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  33:    */   @JoinColumn(name="id_ruta_fabricacion", nullable=true)
/*  34:    */   private RutaFabricacion rutaFabricacion;
/*  35:    */   
/*  36:    */   public int getIdProductoRutaFabricacion()
/*  37:    */   {
/*  38: 83 */     return this.idProductoRutaFabricacion;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setIdProductoRutaFabricacion(int idProductoRutaFabricacion)
/*  42:    */   {
/*  43: 93 */     this.idProductoRutaFabricacion = idProductoRutaFabricacion;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Producto getProducto()
/*  47:    */   {
/*  48:102 */     return this.producto;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setProducto(Producto producto)
/*  52:    */   {
/*  53:112 */     this.producto = producto;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public RutaFabricacion getRutaFabricacion()
/*  57:    */   {
/*  58:121 */     return this.rutaFabricacion;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setRutaFabricacion(RutaFabricacion rutaFabricacion)
/*  62:    */   {
/*  63:131 */     this.rutaFabricacion = rutaFabricacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getId()
/*  67:    */   {
/*  68:141 */     return this.idProductoRutaFabricacion;
/*  69:    */   }
/*  70:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.ProductoRutaFabricacion
 * JD-Core Version:    0.7.0.1
 */