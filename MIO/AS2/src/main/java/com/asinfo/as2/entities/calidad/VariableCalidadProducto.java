/*   1:    */ package com.asinfo.as2.entities.calidad;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.entities.Producto;
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
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="variable_calidad_producto")
/*  21:    */ public class VariableCalidadProducto
/*  22:    */   extends EntidadBase
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="variable_calidad_producto", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="variable_calidad_producto")
/*  28:    */   @Column(name="id_variable_calidad_producto")
/*  29:    */   private int idVariableCalidadProducto;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @Column(name="valor_minimo", nullable=false, precision=12, scale=4)
/*  35:    */   @Digits(integer=12, fraction=4)
/*  36:    */   @NotNull
/*  37: 53 */   private BigDecimal valorMinimo = BigDecimal.ZERO;
/*  38:    */   @Column(name="valor_maximo", nullable=false, precision=12, scale=4)
/*  39:    */   @Digits(integer=12, fraction=4)
/*  40:    */   @NotNull
/*  41: 58 */   private BigDecimal valorMaximo = BigDecimal.ZERO;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_producto", nullable=false)
/*  44:    */   @NotNull
/*  45:    */   private Producto producto;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_variable_calidad", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   private VariableCalidad variableCalidad;
/*  50:    */   
/*  51:    */   public int getId()
/*  52:    */   {
/*  53: 95 */     return this.idVariableCalidadProducto;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdVariableCalidadProducto()
/*  57:    */   {
/*  58:102 */     return this.idVariableCalidadProducto;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdVariableCalidadProducto(int idVariableCalidadProducto)
/*  62:    */   {
/*  63:110 */     this.idVariableCalidadProducto = idVariableCalidadProducto;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdOrganizacion()
/*  67:    */   {
/*  68:117 */     return this.idOrganizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdOrganizacion(int idOrganizacion)
/*  72:    */   {
/*  73:125 */     this.idOrganizacion = idOrganizacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdSucursal()
/*  77:    */   {
/*  78:132 */     return this.idSucursal;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdSucursal(int idSucursal)
/*  82:    */   {
/*  83:140 */     this.idSucursal = idSucursal;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public BigDecimal getValorMinimo()
/*  87:    */   {
/*  88:147 */     return this.valorMinimo;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setValorMinimo(BigDecimal valorMinimo)
/*  92:    */   {
/*  93:155 */     this.valorMinimo = valorMinimo;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public BigDecimal getValorMaximo()
/*  97:    */   {
/*  98:162 */     return this.valorMaximo;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setValorMaximo(BigDecimal valorMaximo)
/* 102:    */   {
/* 103:170 */     this.valorMaximo = valorMaximo;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Producto getProducto()
/* 107:    */   {
/* 108:177 */     return this.producto;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setProducto(Producto producto)
/* 112:    */   {
/* 113:185 */     this.producto = producto;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public VariableCalidad getVariableCalidad()
/* 117:    */   {
/* 118:192 */     return this.variableCalidad;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setVariableCalidad(VariableCalidad variableCalidad)
/* 122:    */   {
/* 123:200 */     this.variableCalidad = variableCalidad;
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.calidad.VariableCalidadProducto
 * JD-Core Version:    0.7.0.1
 */