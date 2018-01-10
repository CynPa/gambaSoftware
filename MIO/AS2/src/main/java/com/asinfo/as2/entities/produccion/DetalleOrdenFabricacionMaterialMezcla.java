/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.entities.Producto;
/*   5:    */ import java.math.BigDecimal;
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
/*  16:    */ import javax.validation.constraints.DecimalMin;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import org.hibernate.annotations.ColumnDefault;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="detalle_orden_fabricacion_material_mezcla")
/*  22:    */ public class DetalleOrdenFabricacionMaterialMezcla
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="detalle_orden_fabricacion_material_mezcla", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_orden_fabricacion_material_mezcla")
/*  29:    */   @Column(name="id_detalle_orden_fabricacion_material_mezcla")
/*  30:    */   private int idDetalleOrdenFabricacionMaterialMezcla;
/*  31:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  32:    */   @JoinColumn(name="id_detalle_orden_fabricacion_material", nullable=true)
/*  33:    */   private DetalleOrdenFabricacionMaterial detalleOrdenFabricacionMaterial;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_material", nullable=true)
/*  36:    */   private Producto producto;
/*  37:    */   @DecimalMin("0.0000")
/*  38:    */   @Column(name="cantidad", nullable=false, precision=14, scale=4)
/*  39:    */   @NotNull
/*  40: 44 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  41:    */   @DecimalMin("0.00")
/*  42:    */   @Column(name="porcentaje", nullable=false, precision=12, scale=2)
/*  43:    */   @NotNull
/*  44: 49 */   private BigDecimal porcentaje = BigDecimal.ZERO;
/*  45:    */   @DecimalMin("0.0000")
/*  46:    */   @Column(name="cantidad_batch", nullable=false, precision=12, scale=4)
/*  47:    */   @NotNull
/*  48:    */   @ColumnDefault("0")
/*  49: 54 */   private BigDecimal cantidadPorCadaBatch = BigDecimal.ZERO;
/*  50:    */   
/*  51:    */   public int getId()
/*  52:    */   {
/*  53: 62 */     return this.idDetalleOrdenFabricacionMaterialMezcla;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdDetalleOrdenFabricacionMaterialMezcla()
/*  57:    */   {
/*  58: 66 */     return this.idDetalleOrdenFabricacionMaterialMezcla;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdDetalleOrdenFabricacionMaterialMezcla(int idDetalleOrdenFabricacionMaterialMezcla)
/*  62:    */   {
/*  63: 70 */     this.idDetalleOrdenFabricacionMaterialMezcla = idDetalleOrdenFabricacionMaterialMezcla;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public DetalleOrdenFabricacionMaterial getDetalleOrdenFabricacionMaterial()
/*  67:    */   {
/*  68: 74 */     return this.detalleOrdenFabricacionMaterial;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setDetalleOrdenFabricacionMaterial(DetalleOrdenFabricacionMaterial detalleOrdenFabricacionMaterial)
/*  72:    */   {
/*  73: 78 */     this.detalleOrdenFabricacionMaterial = detalleOrdenFabricacionMaterial;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Producto getProducto()
/*  77:    */   {
/*  78: 82 */     return this.producto;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setProducto(Producto producto)
/*  82:    */   {
/*  83: 86 */     this.producto = producto;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public BigDecimal getCantidad()
/*  87:    */   {
/*  88: 90 */     return this.cantidad;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setCantidad(BigDecimal cantidad)
/*  92:    */   {
/*  93: 94 */     this.cantidad = cantidad;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public BigDecimal getPorcentaje()
/*  97:    */   {
/*  98: 98 */     return this.porcentaje;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setPorcentaje(BigDecimal porcentaje)
/* 102:    */   {
/* 103:102 */     this.porcentaje = porcentaje;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public BigDecimal getCantidadPorCadaBatch()
/* 107:    */   {
/* 108:106 */     return this.cantidadPorCadaBatch;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setCantidadPorCadaBatch(BigDecimal cantidadPorCadaBatch)
/* 112:    */   {
/* 113:110 */     this.cantidadPorCadaBatch = cantidadPorCadaBatch;
/* 114:    */   }
/* 115:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.DetalleOrdenFabricacionMaterialMezcla
 * JD-Core Version:    0.7.0.1
 */