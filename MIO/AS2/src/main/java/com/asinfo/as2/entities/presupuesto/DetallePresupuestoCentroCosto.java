/*   1:    */ package com.asinfo.as2.entities.presupuesto;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CentroCosto;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.validation.constraints.Digits;
/*  17:    */ import javax.validation.constraints.Min;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="detalle_presupuesto_centro_costo")
/*  22:    */ public class DetallePresupuestoCentroCosto
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="detalle_presupuesto_centro_costo", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_presupuesto_centro_costo")
/*  29:    */   @Column(name="id_detalle_presupuesto_centro_costo")
/*  30:    */   private int idDetallePresupuestoCentroCosto;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @Column(name="id_organizacion", nullable=false)
/*  34:    */   private int idOrganizacion;
/*  35:    */   @Column(name="valor", nullable=false, precision=12, scale=2)
/*  36:    */   @Digits(integer=12, fraction=2)
/*  37:    */   @Min(0L)
/*  38: 59 */   private BigDecimal valor = BigDecimal.ZERO;
/*  39:    */   @Column(name="descripcion", nullable=true, length=200)
/*  40:    */   @Size(max=200)
/*  41:    */   private String descripcion;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_centro_costo", nullable=false)
/*  44:    */   private CentroCosto centroCosto;
/*  45:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  46:    */   @JoinColumn(name="id_detalle_presupuesto", nullable=true)
/*  47:    */   private DetallePresupuesto detallePresupuesto;
/*  48:    */   
/*  49:    */   public int getIdDetallePresupuestoCentroCosto()
/*  50:    */   {
/*  51: 97 */     return this.idDetallePresupuestoCentroCosto;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIdDetallePresupuestoCentroCosto(int idDetallePresupuestoCentroCosto)
/*  55:    */   {
/*  56:107 */     this.idDetallePresupuestoCentroCosto = idDetallePresupuestoCentroCosto;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getIdSucursal()
/*  60:    */   {
/*  61:116 */     return this.idSucursal;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIdSucursal(int idSucursal)
/*  65:    */   {
/*  66:126 */     this.idSucursal = idSucursal;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getIdOrganizacion()
/*  70:    */   {
/*  71:135 */     return this.idOrganizacion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdOrganizacion(int idOrganizacion)
/*  75:    */   {
/*  76:145 */     this.idOrganizacion = idOrganizacion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public BigDecimal getValor()
/*  80:    */   {
/*  81:154 */     return this.valor;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setValor(BigDecimal valor)
/*  85:    */   {
/*  86:164 */     this.valor = valor;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getDescripcion()
/*  90:    */   {
/*  91:173 */     return this.descripcion;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setDescripcion(String descripcion)
/*  95:    */   {
/*  96:183 */     this.descripcion = descripcion;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public CentroCosto getCentroCosto()
/* 100:    */   {
/* 101:192 */     return this.centroCosto;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setCentroCosto(CentroCosto centroCosto)
/* 105:    */   {
/* 106:202 */     this.centroCosto = centroCosto;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public DetallePresupuesto getDetallePresupuesto()
/* 110:    */   {
/* 111:211 */     return this.detallePresupuesto;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setDetallePresupuesto(DetallePresupuesto detallePresupuesto)
/* 115:    */   {
/* 116:221 */     this.detallePresupuesto = detallePresupuesto;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public int getId()
/* 120:    */   {
/* 121:231 */     return this.idDetallePresupuestoCentroCosto;
/* 122:    */   }
/* 123:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.presupuesto.DetallePresupuestoCentroCosto
 * JD-Core Version:    0.7.0.1
 */