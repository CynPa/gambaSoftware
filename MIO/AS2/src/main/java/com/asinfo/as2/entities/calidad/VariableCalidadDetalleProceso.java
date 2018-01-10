/*   1:    */ package com.asinfo.as2.entities.calidad;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.JoinColumn;
/*   8:    */ import javax.persistence.ManyToOne;
/*   9:    */ import javax.persistence.MappedSuperclass;
/*  10:    */ import javax.persistence.Transient;
/*  11:    */ import javax.validation.constraints.Digits;
/*  12:    */ import javax.validation.constraints.NotNull;
/*  13:    */ import javax.validation.constraints.Size;
/*  14:    */ 
/*  15:    */ @MappedSuperclass
/*  16:    */ public abstract class VariableCalidadDetalleProceso
/*  17:    */   extends EntidadBase
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 1L;
/*  20:    */   @Column(name="id_organizacion", nullable=false)
/*  21:    */   private int idOrganizacion;
/*  22:    */   @Column(name="id_sucursal", nullable=false)
/*  23:    */   private int idSucursal;
/*  24:    */   @Column(name="valor_nir", nullable=false, precision=12, scale=6)
/*  25:    */   @Digits(integer=12, fraction=6)
/*  26:    */   @NotNull
/*  27: 38 */   private BigDecimal valorNir = BigDecimal.ZERO;
/*  28:    */   @Column(name="valor_unico", nullable=true)
/*  29:    */   private String valorUnico;
/*  30:    */   @Column(name="observacion", nullable=true, length=200)
/*  31:    */   @Size(max=200)
/*  32:    */   private String observacion;
/*  33:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  34:    */   @JoinColumn(name="id_variable_calidad_producto", nullable=false)
/*  35:    */   @NotNull
/*  36:    */   private VariableCalidadProducto variableCalidadProducto;
/*  37:    */   @Transient
/*  38:    */   private boolean aceptable;
/*  39:    */   
/*  40:    */   public int getIdOrganizacion()
/*  41:    */   {
/*  42: 76 */     return this.idOrganizacion;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setIdOrganizacion(int idOrganizacion)
/*  46:    */   {
/*  47: 84 */     this.idOrganizacion = idOrganizacion;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getIdSucursal()
/*  51:    */   {
/*  52: 91 */     return this.idSucursal;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setIdSucursal(int idSucursal)
/*  56:    */   {
/*  57: 99 */     this.idSucursal = idSucursal;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public BigDecimal getValorNir()
/*  61:    */   {
/*  62:106 */     return this.valorNir;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setValorNir(BigDecimal valorNir)
/*  66:    */   {
/*  67:114 */     this.valorNir = valorNir;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getObservacion()
/*  71:    */   {
/*  72:121 */     return this.observacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setObservacion(String observacion)
/*  76:    */   {
/*  77:129 */     this.observacion = observacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public VariableCalidadProducto getVariableCalidadProducto()
/*  81:    */   {
/*  82:136 */     return this.variableCalidadProducto;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setVariableCalidadProducto(VariableCalidadProducto variableCalidadProducto)
/*  86:    */   {
/*  87:144 */     this.variableCalidadProducto = variableCalidadProducto;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean isAceptable()
/*  91:    */   {
/*  92:151 */     return this.aceptable;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setAceptable(boolean aceptable)
/*  96:    */   {
/*  97:159 */     this.aceptable = aceptable;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public abstract int getId();
/* 101:    */   
/* 102:    */   public int getComparacionRangoValor()
/* 103:    */   {
/* 104:167 */     if ((this.valorNir == null) || (this.variableCalidadProducto == null) || (this.valorNir.compareTo(BigDecimal.ZERO) == 0)) {
/* 105:168 */       return 0;
/* 106:    */     }
/* 107:170 */     if (this.valorNir.compareTo(this.variableCalidadProducto.getValorMinimo()) < 0) {
/* 108:171 */       return -1;
/* 109:    */     }
/* 110:172 */     if (this.valorNir.compareTo(this.variableCalidadProducto.getValorMaximo()) > 0) {
/* 111:173 */       return 1;
/* 112:    */     }
/* 113:175 */     return 0;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getValorUnico()
/* 117:    */   {
/* 118:181 */     return this.valorUnico;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setValorUnico(String valorUnico)
/* 122:    */   {
/* 123:185 */     this.valorUnico = valorUnico;
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.calidad.VariableCalidadDetalleProceso
 * JD-Core Version:    0.7.0.1
 */