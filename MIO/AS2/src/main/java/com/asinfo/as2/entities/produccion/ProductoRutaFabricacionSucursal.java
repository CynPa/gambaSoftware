/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.entities.Producto;
/*   5:    */ import com.asinfo.as2.entities.Sucursal;
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
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="producto_ruta_fabricacion_sucursal", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_producto", "id_ruta_fabricacion", "id_sucursal"})})
/*  20:    */ public class ProductoRutaFabricacionSucursal
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 4707199315707304297L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="producto_ruta_fabricacion_sucursal", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="producto_ruta_fabricacion_sucursal")
/*  27:    */   @Column(name="id_producto_ruta_fabricacion_sucursal")
/*  28:    */   private int idProductoRutaFabricacionSucursal;
/*  29:    */   @NotNull
/*  30:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  31:    */   @JoinColumn(name="id_producto", nullable=true)
/*  32:    */   private Producto producto;
/*  33:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  34:    */   @JoinColumn(name="id_ruta_fabricacion", nullable=false)
/*  35:    */   private RutaFabricacion rutaFabricacion;
/*  36:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  38:    */   @NotNull
/*  39:    */   private Sucursal sucursal;
/*  40:    */   
/*  41:    */   public int getId()
/*  42:    */   {
/*  43: 91 */     return this.idProductoRutaFabricacionSucursal;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getIdProductoRutaFabricacionSucursal()
/*  47:    */   {
/*  48: 95 */     return this.idProductoRutaFabricacionSucursal;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setIdProductoRutaFabricacionSucursal(int idProductoRutaFabricacionSucursal)
/*  52:    */   {
/*  53: 99 */     this.idProductoRutaFabricacionSucursal = idProductoRutaFabricacionSucursal;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Producto getProducto()
/*  57:    */   {
/*  58:103 */     return this.producto;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setProducto(Producto producto)
/*  62:    */   {
/*  63:107 */     this.producto = producto;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public RutaFabricacion getRutaFabricacion()
/*  67:    */   {
/*  68:111 */     return this.rutaFabricacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setRutaFabricacion(RutaFabricacion rutaFabricacion)
/*  72:    */   {
/*  73:115 */     this.rutaFabricacion = rutaFabricacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Sucursal getSucursal()
/*  77:    */   {
/*  78:119 */     return this.sucursal;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setSucursal(Sucursal sucursal)
/*  82:    */   {
/*  83:123 */     this.sucursal = sucursal;
/*  84:    */   }
/*  85:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.ProductoRutaFabricacionSucursal
 * JD-Core Version:    0.7.0.1
 */