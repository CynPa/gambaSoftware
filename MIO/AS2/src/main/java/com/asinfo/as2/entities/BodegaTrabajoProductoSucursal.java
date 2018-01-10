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
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="bodega_trabajo_producto_sucursal", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_producto", "id_sucursal", "id_bodega_trabajo"})})
/*  18:    */ public class BodegaTrabajoProductoSucursal
/*  19:    */   extends EntidadBase
/*  20:    */   implements Serializable
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="bodega_trabajo_producto_sucursal", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="bodega_trabajo_producto_sucursal")
/*  26:    */   @Column(name="id_bodega_trabajo_producto_sucursal", unique=true, nullable=false)
/*  27:    */   private int idBodegaTrabajoProductoSucursal;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   @NotNull
/*  30:    */   private int idOrganizacion;
/*  31:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  32:    */   @JoinColumn(name="id_producto", nullable=false)
/*  33:    */   @NotNull
/*  34:    */   private Producto producto;
/*  35:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  37:    */   @NotNull
/*  38:    */   private Sucursal sucursal;
/*  39:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_bodega_trabajo", nullable=false)
/*  41:    */   @NotNull
/*  42:    */   private Bodega bodegaTrabajo;
/*  43:    */   
/*  44:    */   public int getId()
/*  45:    */   {
/*  46: 68 */     return this.idBodegaTrabajoProductoSucursal;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getIdBodegaTrabajoProductoSucursal()
/*  50:    */   {
/*  51: 72 */     return this.idBodegaTrabajoProductoSucursal;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIdBodegaTrabajoProductoSucursal(int idBodegaTrabajoProductoSucursal)
/*  55:    */   {
/*  56: 76 */     this.idBodegaTrabajoProductoSucursal = idBodegaTrabajoProductoSucursal;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Producto getProducto()
/*  60:    */   {
/*  61: 80 */     return this.producto;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setProducto(Producto producto)
/*  65:    */   {
/*  66: 84 */     this.producto = producto;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Sucursal getSucursal()
/*  70:    */   {
/*  71: 88 */     return this.sucursal;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setSucursal(Sucursal sucursal)
/*  75:    */   {
/*  76: 92 */     this.sucursal = sucursal;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Bodega getBodegaTrabajo()
/*  80:    */   {
/*  81: 96 */     return this.bodegaTrabajo;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setBodegaTrabajo(Bodega bodegaTrabajo)
/*  85:    */   {
/*  86:100 */     this.bodegaTrabajo = bodegaTrabajo;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getIdOrganizacion()
/*  90:    */   {
/*  91:104 */     return this.idOrganizacion;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdOrganizacion(int idOrganizacion)
/*  95:    */   {
/*  96:108 */     this.idOrganizacion = idOrganizacion;
/*  97:    */   }
/*  98:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.BodegaTrabajoProductoSucursal
 * JD-Core Version:    0.7.0.1
 */