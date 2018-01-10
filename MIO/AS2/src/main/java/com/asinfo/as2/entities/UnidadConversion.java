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
/*  15:    */ import javax.validation.constraints.DecimalMin;
/*  16:    */ import javax.validation.constraints.Digits;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="unidad_conversion")
/*  21:    */ public class UnidadConversion
/*  22:    */   extends EntidadBase
/*  23:    */   implements Serializable
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="unidad_conversion", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="unidad_conversion")
/*  29:    */   @Column(name="id_unidad_conversion")
/*  30:    */   private int idUnidadConversion;
/*  31:    */   @Column(name="id_organizacion", nullable=false)
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   private int idSucursal;
/*  35:    */   @Column(name="valor_conversion", nullable=false, precision=12, scale=6)
/*  36:    */   @Digits(integer=12, fraction=6)
/*  37:    */   @DecimalMin("0.000001")
/*  38:    */   private BigDecimal valorConversion;
/*  39:    */   @NotNull
/*  40:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  41:    */   @JoinColumn(name="id_unidad_origen", nullable=false)
/*  42:    */   private Unidad unidadOrigen;
/*  43:    */   @NotNull
/*  44:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  45:    */   @JoinColumn(name="id_unidad_destino", nullable=false)
/*  46:    */   private Unidad unidadDestino;
/*  47:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  48:    */   @JoinColumn(name="id_subcategoria_producto", nullable=true)
/*  49:    */   private SubcategoriaProducto subcategoriaProducto;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_producto", nullable=true)
/*  52:    */   private Producto producto;
/*  53:    */   
/*  54:    */   public UnidadConversion() {}
/*  55:    */   
/*  56:    */   public UnidadConversion(int idUnidadConversion, BigDecimal valorConversion, Unidad unidadDestino)
/*  57:    */   {
/*  58: 78 */     this.idUnidadConversion = idUnidadConversion;
/*  59: 79 */     this.valorConversion = valorConversion;
/*  60: 80 */     this.unidadDestino = unidadDestino;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getId()
/*  64:    */   {
/*  65: 98 */     return this.idUnidadConversion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdUnidadConversion()
/*  69:    */   {
/*  70:107 */     return this.idUnidadConversion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdUnidadConversion(int idUnidadConversion)
/*  74:    */   {
/*  75:117 */     this.idUnidadConversion = idUnidadConversion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public BigDecimal getValorConversion()
/*  79:    */   {
/*  80:126 */     if (this.valorConversion == null) {
/*  81:127 */       this.valorConversion = BigDecimal.ZERO;
/*  82:    */     }
/*  83:129 */     return this.valorConversion;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setValorConversion(BigDecimal valorConversion)
/*  87:    */   {
/*  88:139 */     this.valorConversion = valorConversion;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Unidad getUnidadOrigen()
/*  92:    */   {
/*  93:148 */     if (this.unidadOrigen == null) {
/*  94:149 */       this.unidadOrigen = new Unidad();
/*  95:    */     }
/*  96:151 */     return this.unidadOrigen;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setUnidadOrigen(Unidad unidadOrigen)
/* 100:    */   {
/* 101:161 */     this.unidadOrigen = unidadOrigen;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Unidad getUnidadDestino()
/* 105:    */   {
/* 106:170 */     return this.unidadDestino;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setUnidadDestino(Unidad unidadDestino)
/* 110:    */   {
/* 111:180 */     this.unidadDestino = unidadDestino;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getIdOrganizacion()
/* 115:    */   {
/* 116:189 */     return this.idOrganizacion;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setIdOrganizacion(int idOrganizacion)
/* 120:    */   {
/* 121:199 */     this.idOrganizacion = idOrganizacion;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int getIdSucursal()
/* 125:    */   {
/* 126:208 */     return this.idSucursal;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setIdSucursal(int idSucursal)
/* 130:    */   {
/* 131:218 */     this.idSucursal = idSucursal;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 135:    */   {
/* 136:227 */     return this.subcategoriaProducto;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 140:    */   {
/* 141:237 */     this.subcategoriaProducto = subcategoriaProducto;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public Producto getProducto()
/* 145:    */   {
/* 146:246 */     return this.producto;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setProducto(Producto producto)
/* 150:    */   {
/* 151:256 */     this.producto = producto;
/* 152:    */   }
/* 153:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.UnidadConversion
 * JD-Core Version:    0.7.0.1
 */