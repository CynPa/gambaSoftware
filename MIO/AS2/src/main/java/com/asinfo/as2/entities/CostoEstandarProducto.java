/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.Min;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="costo_estandar_producto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_producto", "id_rango_costo_estandar_producto"})})
/*  18:    */ public class CostoEstandarProducto
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="costo_estandar_producto", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="costo_estandar_producto")
/*  25:    */   @Column(name="id_costo_estandar_producto")
/*  26:    */   private int idCostoEstandarProducto;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Column(name="costo", nullable=false, precision=14, scale=6)
/*  32:    */   @Min(0L)
/*  33: 52 */   private BigDecimal costo = BigDecimal.ZERO;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_rango_costo_estandar_producto", nullable=true)
/*  36:    */   private RangoCostoEstandarProducto rangoCostoEstandarProducto;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_producto")
/*  39:    */   private Producto producto;
/*  40:    */   
/*  41:    */   public int getId()
/*  42:    */   {
/*  43: 71 */     return this.idCostoEstandarProducto;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getIdCostoEstandarProducto()
/*  47:    */   {
/*  48: 80 */     return this.idCostoEstandarProducto;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setIdCostoEstandarProducto(int idCostoEstandarProducto)
/*  52:    */   {
/*  53: 90 */     this.idCostoEstandarProducto = idCostoEstandarProducto;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdOrganizacion()
/*  57:    */   {
/*  58: 99 */     return this.idOrganizacion;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdOrganizacion(int idOrganizacion)
/*  62:    */   {
/*  63:109 */     this.idOrganizacion = idOrganizacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdSucursal()
/*  67:    */   {
/*  68:118 */     return this.idSucursal;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdSucursal(int idSucursal)
/*  72:    */   {
/*  73:128 */     this.idSucursal = idSucursal;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public BigDecimal getCosto()
/*  77:    */   {
/*  78:137 */     return this.costo;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setCosto(BigDecimal costo)
/*  82:    */   {
/*  83:147 */     this.costo = costo;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public RangoCostoEstandarProducto getRangoCostoEstandarProducto()
/*  87:    */   {
/*  88:156 */     return this.rangoCostoEstandarProducto;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setRangoCostoEstandarProducto(RangoCostoEstandarProducto rangoCostoEstandarProducto)
/*  92:    */   {
/*  93:166 */     this.rangoCostoEstandarProducto = rangoCostoEstandarProducto;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Producto getProducto()
/*  97:    */   {
/*  98:175 */     return this.producto;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setProducto(Producto producto)
/* 102:    */   {
/* 103:185 */     this.producto = producto;
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CostoEstandarProducto
 * JD-Core Version:    0.7.0.1
 */