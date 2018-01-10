/*   1:    */ package com.asinfo.as2.entities.clases;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.ProductoMaterial;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ 
/*  10:    */ @Entity
/*  11:    */ @Table(name="tmp_proyeccion_kid")
/*  12:    */ public class ProyeccionKid
/*  13:    */ {
/*  14:    */   @Id
/*  15:    */   @Column(name="id_proyeccion_kid")
/*  16:    */   private int idProyeccionKid;
/*  17:    */   @Column(name="producto_material")
/*  18:    */   private ProductoMaterial productoMaterial;
/*  19:    */   @Column(name="stock")
/*  20:    */   private BigDecimal stock;
/*  21:    */   @Column(name="kid")
/*  22:    */   private BigDecimal kid;
/*  23:    */   @Column(name="saldo")
/*  24:    */   private BigDecimal saldo;
/*  25:    */   
/*  26:    */   public int getIdProyeccionKid()
/*  27:    */   {
/*  28: 54 */     return this.idProyeccionKid;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setIdProyeccionKid(int idProyeccionKid)
/*  32:    */   {
/*  33: 64 */     this.idProyeccionKid = idProyeccionKid;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public ProductoMaterial getProductoMaterial()
/*  37:    */   {
/*  38: 73 */     return this.productoMaterial;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setProductoMaterial(ProductoMaterial productoMaterial)
/*  42:    */   {
/*  43: 83 */     this.productoMaterial = productoMaterial;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public BigDecimal getStock()
/*  47:    */   {
/*  48: 92 */     return this.stock;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setStock(BigDecimal stock)
/*  52:    */   {
/*  53:102 */     this.stock = stock;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public BigDecimal getKid()
/*  57:    */   {
/*  58:111 */     return this.kid;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setKid(BigDecimal kid)
/*  62:    */   {
/*  63:121 */     this.kid = kid;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public BigDecimal getSaldo()
/*  67:    */   {
/*  68:130 */     return this.saldo;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setSaldo(BigDecimal saldo)
/*  72:    */   {
/*  73:140 */     this.saldo = saldo;
/*  74:    */   }
/*  75:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.clases.ProyeccionKid
 * JD-Core Version:    0.7.0.1
 */