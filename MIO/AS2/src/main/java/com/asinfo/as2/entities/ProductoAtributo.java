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
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="producto_atributo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_producto", "id_atributo"})})
/*  18:    */ public class ProductoAtributo
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="producto_atributo", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="producto_atributo")
/*  25:    */   @Column(name="id_producto_atributo", unique=true, nullable=false)
/*  26:    */   private int idProductoAtributo;
/*  27:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  28:    */   @JoinColumn(name="id_atributo", nullable=false)
/*  29:    */   @NotNull
/*  30:    */   private Atributo atributo;
/*  31:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  32:    */   @JoinColumn(name="id_producto", nullable=true)
/*  33:    */   private Producto producto;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   @NotNull
/*  36:    */   private int idOrganizacion;
/*  37:    */   @Column(name="id_sucursal", nullable=false)
/*  38:    */   @NotNull
/*  39:    */   private int idSucursal;
/*  40:    */   @Column(name="valor", length=50)
/*  41:    */   @Size(max=50)
/*  42: 61 */   private String valor = "";
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_valor_atributo", nullable=true)
/*  45:    */   private ValorAtributo valorAtributo;
/*  46:    */   
/*  47:    */   public ProductoAtributo() {}
/*  48:    */   
/*  49:    */   public ProductoAtributo(Producto producto, Atributo atributo)
/*  50:    */   {
/*  51: 73 */     this.producto = producto;
/*  52: 74 */     this.atributo = atributo;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getId()
/*  56:    */   {
/*  57: 79 */     return this.idProductoAtributo;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getIdProductoAtributo()
/*  61:    */   {
/*  62: 83 */     return this.idProductoAtributo;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdProductoAtributo(int idProductoAtributo)
/*  66:    */   {
/*  67: 87 */     this.idProductoAtributo = idProductoAtributo;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Atributo getAtributo()
/*  71:    */   {
/*  72: 91 */     return this.atributo;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setAtributo(Atributo atributo)
/*  76:    */   {
/*  77: 95 */     this.atributo = atributo;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Producto getProducto()
/*  81:    */   {
/*  82: 99 */     return this.producto;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setProducto(Producto producto)
/*  86:    */   {
/*  87:103 */     this.producto = producto;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getIdOrganizacion()
/*  91:    */   {
/*  92:107 */     return this.idOrganizacion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setIdOrganizacion(int idOrganizacion)
/*  96:    */   {
/*  97:111 */     this.idOrganizacion = idOrganizacion;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int getIdSucursal()
/* 101:    */   {
/* 102:115 */     return this.idSucursal;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setIdSucursal(int idSucursal)
/* 106:    */   {
/* 107:119 */     this.idSucursal = idSucursal;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getValor()
/* 111:    */   {
/* 112:123 */     return this.valor;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setValor(String valor)
/* 116:    */   {
/* 117:127 */     this.valor = valor;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public ValorAtributo getValorAtributo()
/* 121:    */   {
/* 122:136 */     return this.valorAtributo;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setValorAtributo(ValorAtributo valorAtributo)
/* 126:    */   {
/* 127:146 */     this.valorAtributo = valorAtributo;
/* 128:    */   }
/* 129:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ProductoAtributo
 * JD-Core Version:    0.7.0.1
 */