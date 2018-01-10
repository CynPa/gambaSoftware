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
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="producto_bodega", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_producto", "id_bodega"})})
/*  18:    */ public class ProductoBodega
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="producto_bodega", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="producto_bodega")
/*  25:    */   @Column(name="id_producto_bodega", unique=true, nullable=false)
/*  26:    */   private int idProductoBodega;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  30:    */   @JoinColumn(name="id_sucursal", nullable=true)
/*  31:    */   private Sucursal sucursal;
/*  32:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  33:    */   @JoinColumn(name="id_producto", nullable=false)
/*  34:    */   @NotNull
/*  35:    */   private Producto producto;
/*  36:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_bodega", nullable=false)
/*  38:    */   @NotNull
/*  39:    */   private Bodega bodega;
/*  40:    */   @Column(name="saldo_minimo", nullable=false, precision=12, scale=4)
/*  41:    */   private BigDecimal saldoMinimo;
/*  42:    */   @Column(name="saldo_maximo", nullable=false, precision=12, scale=4)
/*  43:    */   private BigDecimal saldoMaximo;
/*  44:    */   
/*  45:    */   public int getId()
/*  46:    */   {
/*  47: 62 */     return this.idProductoBodega;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getIdProductoBodega()
/*  51:    */   {
/*  52: 66 */     return this.idProductoBodega;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setIdProductoBodega(int idProductoBodega)
/*  56:    */   {
/*  57: 70 */     this.idProductoBodega = idProductoBodega;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getIdOrganizacion()
/*  61:    */   {
/*  62: 74 */     return this.idOrganizacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdOrganizacion(int idOrganizacion)
/*  66:    */   {
/*  67: 78 */     this.idOrganizacion = idOrganizacion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Sucursal getSucursal()
/*  71:    */   {
/*  72: 82 */     return this.sucursal;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setSucursal(Sucursal sucursal)
/*  76:    */   {
/*  77: 86 */     this.sucursal = sucursal;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Producto getProducto()
/*  81:    */   {
/*  82: 90 */     return this.producto;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setProducto(Producto producto)
/*  86:    */   {
/*  87: 94 */     this.producto = producto;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Bodega getBodega()
/*  91:    */   {
/*  92: 98 */     return this.bodega;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setBodega(Bodega bodega)
/*  96:    */   {
/*  97:102 */     this.bodega = bodega;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public BigDecimal getSaldoMinimo()
/* 101:    */   {
/* 102:106 */     return this.saldoMinimo;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setSaldoMinimo(BigDecimal saldoMinimo)
/* 106:    */   {
/* 107:110 */     this.saldoMinimo = saldoMinimo;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public BigDecimal getSaldoMaximo()
/* 111:    */   {
/* 112:114 */     return this.saldoMaximo;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setSaldoMaximo(BigDecimal saldoMaximo)
/* 116:    */   {
/* 117:118 */     this.saldoMaximo = saldoMaximo;
/* 118:    */   }
/* 119:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ProductoBodega
 * JD-Core Version:    0.7.0.1
 */