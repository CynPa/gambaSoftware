/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.validation.constraints.Digits;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="detalle_cobro_forma_cobro")
/*  20:    */ public class DetalleCobroFormaCobro
/*  21:    */   extends EntidadBase
/*  22:    */   implements Serializable
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="detalle_cobro_forma_cobro", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_cobro_forma_cobro")
/*  28:    */   @Column(name="id_detalle_cobro_forma_cobro", unique=true, nullable=false)
/*  29:    */   private int idDetalleCobroFormaCobro;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_detalle_forma_cobro", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private DetalleFormaCobro detalleFormaCobro;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_detalle_cobro", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private DetalleCobro detalleCobro;
/*  42:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  43:    */   @Digits(integer=12, fraction=2)
/*  44: 63 */   private BigDecimal valor = BigDecimal.ZERO;
/*  45:    */   
/*  46:    */   public int getId()
/*  47:    */   {
/*  48: 74 */     return this.idDetalleCobroFormaCobro;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getIdDetalleCobroFormaCobro()
/*  52:    */   {
/*  53: 84 */     return this.idDetalleCobroFormaCobro;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setIdDetalleCobroFormaCobro(int idDetalleCobroFormaCobro)
/*  57:    */   {
/*  58: 88 */     this.idDetalleCobroFormaCobro = idDetalleCobroFormaCobro;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getIdOrganizacion()
/*  62:    */   {
/*  63: 92 */     return this.idOrganizacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setIdOrganizacion(int idOrganizacion)
/*  67:    */   {
/*  68: 96 */     this.idOrganizacion = idOrganizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getIdSucursal()
/*  72:    */   {
/*  73:100 */     return this.idSucursal;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIdSucursal(int idSucursal)
/*  77:    */   {
/*  78:104 */     this.idSucursal = idSucursal;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public DetalleFormaCobro getDetalleFormaCobro()
/*  82:    */   {
/*  83:108 */     return this.detalleFormaCobro;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setDetalleFormaCobro(DetalleFormaCobro detalleFormaCobro)
/*  87:    */   {
/*  88:112 */     this.detalleFormaCobro = detalleFormaCobro;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public DetalleCobro getDetalleCobro()
/*  92:    */   {
/*  93:116 */     return this.detalleCobro;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setDetalleCobro(DetalleCobro detalleCobro)
/*  97:    */   {
/*  98:120 */     this.detalleCobro = detalleCobro;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public BigDecimal getValor()
/* 102:    */   {
/* 103:124 */     return this.valor;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setValor(BigDecimal valor)
/* 107:    */   {
/* 108:128 */     this.valor = valor;
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleCobroFormaCobro
 * JD-Core Version:    0.7.0.1
 */