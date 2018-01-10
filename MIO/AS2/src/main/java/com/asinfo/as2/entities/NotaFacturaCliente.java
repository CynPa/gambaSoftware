/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
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
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="nota_factura_cliente")
/*  19:    */ public class NotaFacturaCliente
/*  20:    */   extends EntidadBase
/*  21:    */   implements Serializable
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="nota_factura_cliente", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="nota_factura_cliente")
/*  27:    */   @Column(name="id_nota_factura_cliente")
/*  28:    */   private int idNotaFacturaCliente;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  34:    */   @JoinColumn(name="id_factura_cliente", nullable=false)
/*  35:    */   @NotNull
/*  36:    */   private FacturaCliente facturaCliente;
/*  37:    */   @Column(name="descripcion", length=200, nullable=false)
/*  38:    */   @Size(max=200)
/*  39:    */   private String descripcion;
/*  40:    */   
/*  41:    */   public int getId()
/*  42:    */   {
/*  43: 65 */     return this.idNotaFacturaCliente;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getIdNotaFacturaCliente()
/*  47:    */   {
/*  48: 69 */     return this.idNotaFacturaCliente;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setIdNotaFacturaCliente(int idNotaFacturaCliente)
/*  52:    */   {
/*  53: 73 */     this.idNotaFacturaCliente = idNotaFacturaCliente;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdOrganizacion()
/*  57:    */   {
/*  58: 77 */     return this.idOrganizacion;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdOrganizacion(int idOrganizacion)
/*  62:    */   {
/*  63: 81 */     this.idOrganizacion = idOrganizacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdSucursal()
/*  67:    */   {
/*  68: 85 */     return this.idSucursal;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdSucursal(int idSucursal)
/*  72:    */   {
/*  73: 89 */     this.idSucursal = idSucursal;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public FacturaCliente getFacturaCliente()
/*  77:    */   {
/*  78: 93 */     return this.facturaCliente;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setFacturaCliente(FacturaCliente facturaCliente)
/*  82:    */   {
/*  83: 97 */     this.facturaCliente = facturaCliente;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getDescripcion()
/*  87:    */   {
/*  88:101 */     return this.descripcion;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setDescripcion(String descripcion)
/*  92:    */   {
/*  93:105 */     this.descripcion = descripcion;
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.NotaFacturaCliente
 * JD-Core Version:    0.7.0.1
 */