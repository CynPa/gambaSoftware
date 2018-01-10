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
/*  14:    */ import javax.validation.constraints.Min;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="detalle_denominacion_forma_cobro")
/*  19:    */ public class DetalleDenominacionFormaCobro
/*  20:    */   extends EntidadBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="detalle_denominacion_forma_cobro", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_denominacion_forma_cobro")
/*  26:    */   @Column(name="id_detalle_denominacion_forma_cobro")
/*  27:    */   private int idDetalleDenominacionFormaCobro;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   @NotNull
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   @NotNull
/*  33:    */   private int idSucursal;
/*  34:    */   @Column(name="cantidad", nullable=false)
/*  35:    */   @Min(0L)
/*  36:    */   @NotNull
/*  37:    */   private int cantidad;
/*  38:    */   @Column(name="total", nullable=false, precision=12, scale=2)
/*  39:    */   @Min(0L)
/*  40:    */   @NotNull
/*  41: 54 */   private BigDecimal total = BigDecimal.ZERO;
/*  42:    */   @Column(name="id_dispositivo_sincronizacion", nullable=true)
/*  43:    */   private Integer idDispositivoSincronizacion;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_cierre_caja", nullable=false)
/*  46:    */   private CierreCaja cierreCaja;
/*  47:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  48:    */   @JoinColumn(name="id_denominacion_forma_cobro", nullable=false)
/*  49:    */   private DenominacionFormaCobro denominacionFormaCobro;
/*  50:    */   
/*  51:    */   public int getId()
/*  52:    */   {
/*  53: 76 */     return this.idDetalleDenominacionFormaCobro;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdDetalleDenominacionFormaCobro()
/*  57:    */   {
/*  58: 80 */     return this.idDetalleDenominacionFormaCobro;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdDetalleDenominacionFormaCobro(int idDetalleDenominacionFormaCobro)
/*  62:    */   {
/*  63: 84 */     this.idDetalleDenominacionFormaCobro = idDetalleDenominacionFormaCobro;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdOrganizacion()
/*  67:    */   {
/*  68: 88 */     return this.idOrganizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdOrganizacion(int idOrganizacion)
/*  72:    */   {
/*  73: 92 */     this.idOrganizacion = idOrganizacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdSucursal()
/*  77:    */   {
/*  78: 96 */     return this.idSucursal;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdSucursal(int idSucursal)
/*  82:    */   {
/*  83:100 */     this.idSucursal = idSucursal;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public BigDecimal getTotal()
/*  87:    */   {
/*  88:104 */     return this.total;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setTotal(BigDecimal total)
/*  92:    */   {
/*  93:108 */     this.total = total;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public CierreCaja getCierreCaja()
/*  97:    */   {
/*  98:112 */     return this.cierreCaja;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setCierreCaja(CierreCaja cierreCaja)
/* 102:    */   {
/* 103:116 */     this.cierreCaja = cierreCaja;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public DenominacionFormaCobro getDenominacionFormaCobro()
/* 107:    */   {
/* 108:120 */     return this.denominacionFormaCobro;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setDenominacionFormaCobro(DenominacionFormaCobro denominacionFormaCobro)
/* 112:    */   {
/* 113:124 */     this.denominacionFormaCobro = denominacionFormaCobro;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int getCantidad()
/* 117:    */   {
/* 118:128 */     return this.cantidad;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setCantidad(int cantidad)
/* 122:    */   {
/* 123:132 */     this.cantidad = cantidad;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Integer getIdDispositivoSincronizacion()
/* 127:    */   {
/* 128:136 */     return this.idDispositivoSincronizacion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 132:    */   {
/* 133:140 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 134:    */   }
/* 135:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleDenominacionFormaCobro
 * JD-Core Version:    0.7.0.1
 */