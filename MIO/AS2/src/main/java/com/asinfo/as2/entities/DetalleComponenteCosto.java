/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.ValoresCalculo;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.EnumType;
/*   8:    */ import javax.persistence.Enumerated;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="detalle_componente_costo")
/*  21:    */ public class DetalleComponenteCosto
/*  22:    */   extends EntidadBase
/*  23:    */   implements Serializable
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 6041597607233226319L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="detalle_componente_costo", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_componente_costo")
/*  29:    */   @Column(name="id_detalle_componente_costo", unique=true, nullable=false)
/*  30:    */   private int idDetalleComponenteCosto;
/*  31:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  32:    */   @JoinColumn(name="id_componente_costo", nullable=true)
/*  33:    */   private ComponenteCosto componenteCosto;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_cuenta_contable", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private CuentaContable cuentaContable;
/*  38:    */   @Enumerated(EnumType.ORDINAL)
/*  39:    */   @Column(name="valor_calculo", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private ValoresCalculo valorCalculo;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_cuenta_contable_cierre", nullable=true)
/*  44:    */   private CuentaContable cuentaContableCierre;
/*  45:    */   @Column(name="id_organizacion", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private int idOrganizacion;
/*  48:    */   @Column(name="id_sucursal", nullable=false)
/*  49:    */   @NotNull
/*  50:    */   private int idSucursal;
/*  51:    */   
/*  52:    */   public int getId()
/*  53:    */   {
/*  54: 79 */     return this.idDetalleComponenteCosto;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getIdDetalleComponenteCosto()
/*  58:    */   {
/*  59: 83 */     return this.idDetalleComponenteCosto;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setIdDetalleComponenteCosto(int idDetalleComponenteCosto)
/*  63:    */   {
/*  64: 87 */     this.idDetalleComponenteCosto = idDetalleComponenteCosto;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public ComponenteCosto getComponenteCosto()
/*  68:    */   {
/*  69: 91 */     return this.componenteCosto;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setComponenteCosto(ComponenteCosto componenteCosto)
/*  73:    */   {
/*  74: 95 */     this.componenteCosto = componenteCosto;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public CuentaContable getCuentaContable()
/*  78:    */   {
/*  79: 99 */     return this.cuentaContable;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setCuentaContable(CuentaContable cuentaContable)
/*  83:    */   {
/*  84:103 */     this.cuentaContable = cuentaContable;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public ValoresCalculo getValorCalculo()
/*  88:    */   {
/*  89:107 */     return this.valorCalculo;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setValorCalculo(ValoresCalculo valorCalculo)
/*  93:    */   {
/*  94:111 */     this.valorCalculo = valorCalculo;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public CuentaContable getCuentaContableCierre()
/*  98:    */   {
/*  99:115 */     return this.cuentaContableCierre;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setCuentaContableCierre(CuentaContable cuentaContableCierre)
/* 103:    */   {
/* 104:119 */     this.cuentaContableCierre = cuentaContableCierre;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int getIdOrganizacion()
/* 108:    */   {
/* 109:123 */     return this.idOrganizacion;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setIdOrganizacion(int idOrganizacion)
/* 113:    */   {
/* 114:127 */     this.idOrganizacion = idOrganizacion;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int getIdSucursal()
/* 118:    */   {
/* 119:131 */     return this.idSucursal;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setIdSucursal(int idSucursal)
/* 123:    */   {
/* 124:135 */     this.idSucursal = idSucursal;
/* 125:    */   }
/* 126:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleComponenteCosto
 * JD-Core Version:    0.7.0.1
 */