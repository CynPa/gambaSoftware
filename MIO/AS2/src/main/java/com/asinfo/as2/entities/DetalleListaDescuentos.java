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
/*  14:    */ import javax.validation.constraints.Max;
/*  15:    */ import javax.validation.constraints.Min;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="detalle_lista_descuentos", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_producto", "id_version_lista_descuentos"})})
/*  19:    */ public class DetalleListaDescuentos
/*  20:    */   extends EntidadBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 9205333404954120114L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="detalle_lista_descuentos", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_lista_descuentos")
/*  26:    */   @Column(name="id_detalle_lista_descuentos")
/*  27:    */   private int idDetalleListaDescuentos;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   private int idOrganizacion;
/*  30:    */   @Column(name="id_sucursal", nullable=false)
/*  31:    */   private int idSucursal;
/*  32:    */   @Column(name="porcentaje_descuento_maximo", nullable=false, precision=5, scale=2)
/*  33:    */   @Min(0L)
/*  34:    */   @Max(100L)
/*  35: 55 */   private BigDecimal porcentajeDescuentoMaximo = BigDecimal.ZERO;
/*  36:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_version_lista_descuentos", nullable=true)
/*  38:    */   private VersionListaDescuentos versionListaDescuentos;
/*  39:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_producto")
/*  41:    */   private Producto producto;
/*  42:    */   
/*  43:    */   public int getId()
/*  44:    */   {
/*  45: 70 */     return this.idDetalleListaDescuentos;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getIdDetalleListaDescuentos()
/*  49:    */   {
/*  50: 77 */     return this.idDetalleListaDescuentos;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setIdDetalleListaDescuentos(int idDetalleListaDescuentos)
/*  54:    */   {
/*  55: 81 */     this.idDetalleListaDescuentos = idDetalleListaDescuentos;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdOrganizacion()
/*  59:    */   {
/*  60: 85 */     return this.idOrganizacion;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdOrganizacion(int idOrganizacion)
/*  64:    */   {
/*  65: 89 */     this.idOrganizacion = idOrganizacion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdSucursal()
/*  69:    */   {
/*  70: 93 */     return this.idSucursal;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdSucursal(int idSucursal)
/*  74:    */   {
/*  75: 97 */     this.idSucursal = idSucursal;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public BigDecimal getPorcentajeDescuentoMaximo()
/*  79:    */   {
/*  80:101 */     return this.porcentajeDescuentoMaximo;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setPorcentajeDescuentoMaximo(BigDecimal porcentajeDescuentoMaximo)
/*  84:    */   {
/*  85:105 */     this.porcentajeDescuentoMaximo = porcentajeDescuentoMaximo;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public VersionListaDescuentos getVersionListaDescuentos()
/*  89:    */   {
/*  90:109 */     return this.versionListaDescuentos;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setVersionListaDescuentos(VersionListaDescuentos versionListaDescuentos)
/*  94:    */   {
/*  95:113 */     this.versionListaDescuentos = versionListaDescuentos;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Producto getProducto()
/*  99:    */   {
/* 100:117 */     return this.producto;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setProducto(Producto producto)
/* 104:    */   {
/* 105:121 */     this.producto = producto;
/* 106:    */   }
/* 107:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleListaDescuentos
 * JD-Core Version:    0.7.0.1
 */