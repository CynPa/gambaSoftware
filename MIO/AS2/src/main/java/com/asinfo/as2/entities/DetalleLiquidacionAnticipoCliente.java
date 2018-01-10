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
/*  16:    */ @Table(name="detalle_liquidacion_anticipo_cliente")
/*  17:    */ public class DetalleLiquidacionAnticipoCliente
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="detalle_liquidacion_anticipo_cliente", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_liquidacion_anticipo_cliente")
/*  24:    */   @Column(name="id_detalle_liquidacion_anticipo_cliente")
/*  25:    */   private int idDetalleLiquidacionAnticipoCliente;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal", nullable=false)
/*  29:    */   private int idSucursal;
/*  30:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  31:    */   @JoinColumn(name="id_liquidacion_anticipo_cliente", nullable=true)
/*  32:    */   private LiquidacionAnticipoCliente liquidacionAnticipoCliente;
/*  33:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  34:    */   @JoinColumn(name="id_cuenta_por_cobrar", nullable=false)
/*  35:    */   private CuentaPorCobrar cuentaPorCobrar;
/*  36:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  37:    */   private BigDecimal valor;
/*  38:    */   @Column(name="id_dispositivo_sincronizacion", nullable=true)
/*  39:    */   private Integer idDispositivoSincronizacion;
/*  40:    */   
/*  41:    */   public int getId()
/*  42:    */   {
/*  43: 67 */     return this.idDetalleLiquidacionAnticipoCliente;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getIdDetalleLiquidacionAnticipoCliente()
/*  47:    */   {
/*  48: 74 */     return this.idDetalleLiquidacionAnticipoCliente;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setIdDetalleLiquidacionAnticipoCliente(int idDetalleLiquidacionAnticipoCliente)
/*  52:    */   {
/*  53: 79 */     this.idDetalleLiquidacionAnticipoCliente = idDetalleLiquidacionAnticipoCliente;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdOrganizacion()
/*  57:    */   {
/*  58: 83 */     return this.idOrganizacion;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdOrganizacion(int idOrganizacion)
/*  62:    */   {
/*  63: 87 */     this.idOrganizacion = idOrganizacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdSucursal()
/*  67:    */   {
/*  68: 91 */     return this.idSucursal;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdSucursal(int idSucursal)
/*  72:    */   {
/*  73: 95 */     this.idSucursal = idSucursal;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public LiquidacionAnticipoCliente getLiquidacionAnticipoCliente()
/*  77:    */   {
/*  78: 99 */     return this.liquidacionAnticipoCliente;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setLiquidacionAnticipoCliente(LiquidacionAnticipoCliente liquidacionAnticipoCliente)
/*  82:    */   {
/*  83:104 */     this.liquidacionAnticipoCliente = liquidacionAnticipoCliente;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public CuentaPorCobrar getCuentaPorCobrar()
/*  87:    */   {
/*  88:108 */     return this.cuentaPorCobrar;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setCuentaPorCobrar(CuentaPorCobrar cuentaPorCobrar)
/*  92:    */   {
/*  93:112 */     this.cuentaPorCobrar = cuentaPorCobrar;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public BigDecimal getValor()
/*  97:    */   {
/*  98:116 */     return this.valor;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setValor(BigDecimal valor)
/* 102:    */   {
/* 103:120 */     this.valor = valor;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Integer getIdDispositivoSincronizacion()
/* 107:    */   {
/* 108:124 */     return this.idDispositivoSincronizacion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 112:    */   {
/* 113:128 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 114:    */   }
/* 115:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleLiquidacionAnticipoCliente
 * JD-Core Version:    0.7.0.1
 */