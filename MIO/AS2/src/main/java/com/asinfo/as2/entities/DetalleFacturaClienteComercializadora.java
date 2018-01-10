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
/*  14:    */ import javax.validation.constraints.Digits;
/*  15:    */ import javax.validation.constraints.Min;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ import javax.validation.constraints.Size;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="detalle_factura_cliente_comercializadora", indexes={@javax.persistence.Index(columnList="id_factura_cliente")})
/*  21:    */ public class DetalleFacturaClienteComercializadora
/*  22:    */   extends EntidadBase
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 7669111981845687691L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="detalle_factura_cliente_comercializadora", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_factura_cliente_comercializadora")
/*  28:    */   @Column(name="id_detalle_factura_cliente_comercializadora")
/*  29:    */   private int idDetalleFacturaClienteComercializadora;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @Column(name="valor", precision=12, scale=2)
/*  35:    */   @Digits(integer=12, fraction=2)
/*  36:    */   @Min(0L)
/*  37: 58 */   private BigDecimal valor = BigDecimal.ZERO;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_factura_cliente", nullable=true)
/*  40:    */   private FacturaCliente facturaCliente;
/*  41:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  42:    */   @JoinColumn(name="id_cuenta_contable", nullable=false)
/*  43:    */   @NotNull
/*  44:    */   private CuentaContable cuentaContable;
/*  45:    */   @NotNull
/*  46:    */   @Column(name="recargo", length=50)
/*  47:    */   @Size(min=1, max=50)
/*  48:    */   private String recargo;
/*  49:    */   
/*  50:    */   public int getId()
/*  51:    */   {
/*  52: 79 */     return this.idDetalleFacturaClienteComercializadora;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getIdDetalleFacturaClienteComercializadora()
/*  56:    */   {
/*  57: 91 */     return this.idDetalleFacturaClienteComercializadora;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setIdDetalleFacturaClienteComercializadora(int idDetalleFacturaClienteComercializadora)
/*  61:    */   {
/*  62:101 */     this.idDetalleFacturaClienteComercializadora = idDetalleFacturaClienteComercializadora;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdOrganizacion()
/*  66:    */   {
/*  67:110 */     return this.idOrganizacion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdOrganizacion(int idOrganizacion)
/*  71:    */   {
/*  72:120 */     this.idOrganizacion = idOrganizacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdSucursal()
/*  76:    */   {
/*  77:129 */     return this.idSucursal;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdSucursal(int idSucursal)
/*  81:    */   {
/*  82:139 */     this.idSucursal = idSucursal;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public FacturaCliente getFacturaCliente()
/*  86:    */   {
/*  87:148 */     return this.facturaCliente;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setFacturaCliente(FacturaCliente facturaCliente)
/*  91:    */   {
/*  92:158 */     this.facturaCliente = facturaCliente;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public BigDecimal getValor()
/*  96:    */   {
/*  97:167 */     return this.valor;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setValor(BigDecimal valor)
/* 101:    */   {
/* 102:177 */     this.valor = valor;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getRecargo()
/* 106:    */   {
/* 107:184 */     return this.recargo;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setRecargo(String recargo)
/* 111:    */   {
/* 112:192 */     this.recargo = recargo;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public CuentaContable getCuentaContable()
/* 116:    */   {
/* 117:201 */     return this.cuentaContable;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 121:    */   {
/* 122:211 */     this.cuentaContable = cuentaContable;
/* 123:    */   }
/* 124:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleFacturaClienteComercializadora
 * JD-Core Version:    0.7.0.1
 */