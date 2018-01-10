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
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="detalle_liquidacion_anticipo_proveedor", indexes={@javax.persistence.Index(columnList="id_liquidacion_anticipo_proveedor"), @javax.persistence.Index(columnList="id_cuenta_por_pagar")})
/*  17:    */ public class DetalleLiquidacionAnticipoProveedor
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="detalle_liquidacion_anticipo_proveedor", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_liquidacion_anticipo_proveedor")
/*  24:    */   @Column(name="id_detalle_liquidacion_anticipo_proveedor")
/*  25:    */   private int idDetalleLiquidacionAnticipoProveedor;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal", nullable=false)
/*  29:    */   private int idSucursal;
/*  30:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  31:    */   @JoinColumn(name="id_liquidacion_anticipo_proveedor", nullable=true)
/*  32:    */   private LiquidacionAnticipoProveedor liquidacionAnticipoProveedor;
/*  33:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  34:    */   @JoinColumn(name="id_cuenta_por_pagar", nullable=false)
/*  35:    */   private CuentaPorPagar cuentaPorPagar;
/*  36:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  37:    */   private BigDecimal valor;
/*  38:    */   
/*  39:    */   public int getId()
/*  40:    */   {
/*  41: 66 */     return this.idDetalleLiquidacionAnticipoProveedor;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int getIdDetalleLiquidacionAnticipoProveedor()
/*  45:    */   {
/*  46: 75 */     return this.idDetalleLiquidacionAnticipoProveedor;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setIdDetalleLiquidacionAnticipoProveedor(int idDetalleLiquidacionAnticipoProveedor)
/*  50:    */   {
/*  51: 87 */     this.idDetalleLiquidacionAnticipoProveedor = idDetalleLiquidacionAnticipoProveedor;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getIdOrganizacion()
/*  55:    */   {
/*  56: 96 */     return this.idOrganizacion;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIdOrganizacion(int idOrganizacion)
/*  60:    */   {
/*  61:106 */     this.idOrganizacion = idOrganizacion;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdSucursal()
/*  65:    */   {
/*  66:115 */     return this.idSucursal;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdSucursal(int idSucursal)
/*  70:    */   {
/*  71:125 */     this.idSucursal = idSucursal;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public LiquidacionAnticipoProveedor getLiquidacionAnticipoProveedor()
/*  75:    */   {
/*  76:134 */     return this.liquidacionAnticipoProveedor;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setLiquidacionAnticipoProveedor(LiquidacionAnticipoProveedor liquidacionAnticipoProveedor)
/*  80:    */   {
/*  81:145 */     this.liquidacionAnticipoProveedor = liquidacionAnticipoProveedor;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public CuentaPorPagar getCuentaPorPagar()
/*  85:    */   {
/*  86:154 */     return this.cuentaPorPagar;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setCuentaPorPagar(CuentaPorPagar cuentaPorPagar)
/*  90:    */   {
/*  91:164 */     this.cuentaPorPagar = cuentaPorPagar;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public BigDecimal getValor()
/*  95:    */   {
/*  96:173 */     return this.valor;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setValor(BigDecimal valor)
/* 100:    */   {
/* 101:183 */     this.valor = valor;
/* 102:    */   }
/* 103:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleLiquidacionAnticipoProveedor
 * JD-Core Version:    0.7.0.1
 */