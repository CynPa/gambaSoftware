/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.FetchType;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.validation.constraints.Size;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="producto_sustituto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_producto", "id_producto_sustituto"})})
/*  17:    */ public class ProductoSustituto
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="producto_sustituto", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="producto_sustituto")
/*  24:    */   @Column(name="id_producto_sustituto")
/*  25:    */   private int idProductoSustituto;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal", nullable=false)
/*  29:    */   private int idSucursal;
/*  30:    */   @Column(name="descripcion", nullable=true, length=200)
/*  31:    */   @Size(max=200)
/*  32:    */   private String descripcion;
/*  33:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  34:    */   @JoinColumn(name="id_producto", nullable=true)
/*  35:    */   private Producto producto;
/*  36:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_sustituto", nullable=false)
/*  38:    */   private Producto sustituto;
/*  39:    */   
/*  40:    */   public int getId()
/*  41:    */   {
/*  42: 88 */     return this.idProductoSustituto;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int getIdProductoSustituto()
/*  46:    */   {
/*  47: 94 */     return this.idProductoSustituto;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setIdProductoSustituto(int idProductoSustituto)
/*  51:    */   {
/*  52:100 */     this.idProductoSustituto = idProductoSustituto;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getIdOrganizacion()
/*  56:    */   {
/*  57:110 */     return this.idOrganizacion;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setIdOrganizacion(int idOrganizacion)
/*  61:    */   {
/*  62:118 */     this.idOrganizacion = idOrganizacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdSucursal()
/*  66:    */   {
/*  67:126 */     return this.idSucursal;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdSucursal(int idSucursal)
/*  71:    */   {
/*  72:134 */     this.idSucursal = idSucursal;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getDescripcion()
/*  76:    */   {
/*  77:142 */     return this.descripcion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setDescripcion(String descripcion)
/*  81:    */   {
/*  82:150 */     this.descripcion = descripcion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Producto getProducto()
/*  86:    */   {
/*  87:158 */     return this.producto;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setProducto(Producto producto)
/*  91:    */   {
/*  92:166 */     this.producto = producto;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Producto getSustituto()
/*  96:    */   {
/*  97:174 */     return this.sustituto;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setSustituto(Producto sustituto)
/* 101:    */   {
/* 102:182 */     this.sustituto = sustituto;
/* 103:    */   }
/* 104:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ProductoSustituto
 * JD-Core Version:    0.7.0.1
 */