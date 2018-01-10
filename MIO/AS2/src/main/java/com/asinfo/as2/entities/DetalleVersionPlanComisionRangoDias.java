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
/*  14:    */ import javax.validation.constraints.DecimalMin;
/*  15:    */ import javax.validation.constraints.Digits;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="detalle_version_plan_comision_rango_dias", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_detalle_version_plan_comision", "id_rango_dias_cobro"})})
/*  20:    */ public class DetalleVersionPlanComisionRangoDias
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="detalle_version_plan_comision_rango_dias", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_version_plan_comision_rango_dias")
/*  27:    */   @Column(name="id_detalle_version_plan_comision_rango_dias")
/*  28:    */   private int idDetalleVersionPlanComisionRangoDias;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  34:    */   @JoinColumn(name="id_detalle_version_plan_comision", nullable=false)
/*  35:    */   @NotNull
/*  36:    */   private DetalleVersionPlanComision detalleVersionPlanComision;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_rango_dias_cobro", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private RangoDiasComision rangoDiasCobro;
/*  41:    */   @Column(name="valor", nullable=true, precision=12, scale=2)
/*  42:    */   @Digits(integer=12, fraction=2)
/*  43:    */   @DecimalMin("0.00")
/*  44: 72 */   private BigDecimal valor = BigDecimal.ZERO;
/*  45:    */   
/*  46:    */   public int getId()
/*  47:    */   {
/*  48: 94 */     return this.idDetalleVersionPlanComisionRangoDias;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getIdDetalleVersionPlanComisionRangoDias()
/*  52:    */   {
/*  53: 98 */     return this.idDetalleVersionPlanComisionRangoDias;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setIdDetalleVersionPlanComisionRangoDias(int idDetalleVersionPlanComisionRangoDias)
/*  57:    */   {
/*  58:102 */     this.idDetalleVersionPlanComisionRangoDias = idDetalleVersionPlanComisionRangoDias;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getIdOrganizacion()
/*  62:    */   {
/*  63:106 */     return this.idOrganizacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setIdOrganizacion(int idOrganizacion)
/*  67:    */   {
/*  68:110 */     this.idOrganizacion = idOrganizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getIdSucursal()
/*  72:    */   {
/*  73:114 */     return this.idSucursal;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIdSucursal(int idSucursal)
/*  77:    */   {
/*  78:118 */     this.idSucursal = idSucursal;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public RangoDiasComision getRangoDiasCobro()
/*  82:    */   {
/*  83:122 */     return this.rangoDiasCobro;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setRangoDiasCobro(RangoDiasComision rangoDiasCobro)
/*  87:    */   {
/*  88:126 */     this.rangoDiasCobro = rangoDiasCobro;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public DetalleVersionPlanComision getDetalleVersionPlanComision()
/*  92:    */   {
/*  93:130 */     return this.detalleVersionPlanComision;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setDetalleVersionPlanComision(DetalleVersionPlanComision detalleVersionPlanComision)
/*  97:    */   {
/*  98:134 */     this.detalleVersionPlanComision = detalleVersionPlanComision;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public BigDecimal getValor()
/* 102:    */   {
/* 103:138 */     return this.valor;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setValor(BigDecimal valor)
/* 107:    */   {
/* 108:142 */     this.valor = valor;
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleVersionPlanComisionRangoDias
 * JD-Core Version:    0.7.0.1
 */