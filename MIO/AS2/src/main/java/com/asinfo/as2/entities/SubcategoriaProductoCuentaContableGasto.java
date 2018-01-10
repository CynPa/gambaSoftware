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
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="subcategoria_producto_cuenta_contable_gasto")
/*  16:    */ public class SubcategoriaProductoCuentaContableGasto
/*  17:    */   extends EntidadBase
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 4179963717886293236L;
/*  20:    */   @Id
/*  21:    */   @TableGenerator(name="subcategoria_producto_cuenta_contable_gasto", initialValue=0, allocationSize=50)
/*  22:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="subcategoria_producto_cuenta_contable_gasto")
/*  23:    */   @Column(name="id_subcategoria_producto_cuenta_contable_gasto")
/*  24:    */   private int idSubcategoriaProductoCuentaContableGasto;
/*  25:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  26:    */   @JoinColumn(name="id_subcategoria_producto", nullable=false)
/*  27:    */   private SubcategoriaProducto subcategoriaProducto;
/*  28:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  29:    */   @JoinColumn(name="id_cuenta_contable", nullable=false)
/*  30:    */   private CuentaContable cuentaContable;
/*  31:    */   
/*  32:    */   public int getIdSubcategoriaProductoCuentaContableGasto()
/*  33:    */   {
/*  34: 84 */     return this.idSubcategoriaProductoCuentaContableGasto;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setIdSubcategoriaProductoCuentaContableGasto(int idSubcategoriaProductoCuentaContableGasto)
/*  38:    */   {
/*  39: 94 */     this.idSubcategoriaProductoCuentaContableGasto = idSubcategoriaProductoCuentaContableGasto;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public SubcategoriaProducto getSubcategoriaProducto()
/*  43:    */   {
/*  44:103 */     return this.subcategoriaProducto;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/*  48:    */   {
/*  49:113 */     this.subcategoriaProducto = subcategoriaProducto;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public CuentaContable getCuentaContable()
/*  53:    */   {
/*  54:122 */     return this.cuentaContable;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setCuentaContable(CuentaContable cuentaContable)
/*  58:    */   {
/*  59:132 */     this.cuentaContable = cuentaContable;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getId()
/*  63:    */   {
/*  64:142 */     return this.idSubcategoriaProductoCuentaContableGasto;
/*  65:    */   }
/*  66:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.SubcategoriaProductoCuentaContableGasto
 * JD-Core Version:    0.7.0.1
 */