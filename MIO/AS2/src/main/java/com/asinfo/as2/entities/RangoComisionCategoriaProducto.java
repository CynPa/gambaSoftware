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
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="rango_comision_categoria_producto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_rango_comision", "id_categoria_producto", "id_canal"})})
/*  20:    */ public class RangoComisionCategoriaProducto
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="rango_comision_categoria_producto", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="rango_comision_categoria_producto")
/*  27:    */   @Column(name="id_rango_comision_categoria_producto")
/*  28:    */   private int idRangoComisionCategoriaProducto;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @Column(name="porcentaje_comision", nullable=false, precision=12, scale=2)
/*  34:    */   @NotNull
/*  35:    */   @Digits(integer=12, fraction=2)
/*  36:    */   @Min(0L)
/*  37: 58 */   private BigDecimal porcentajeComision = BigDecimal.ZERO;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_rango_comision", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private RangoComision rangoComision;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_categoria_producto", nullable=false)
/*  44:    */   @NotNull
/*  45:    */   private CategoriaProducto categoriaProducto;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_canal", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   private Canal canal;
/*  50:    */   
/*  51:    */   public int getIdRangoComisionCategoriaProducto()
/*  52:    */   {
/*  53:102 */     return this.idRangoComisionCategoriaProducto;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setIdRangoComisionCategoriaProducto(int idRangoComisionCategoriaProducto)
/*  57:    */   {
/*  58:111 */     this.idRangoComisionCategoriaProducto = idRangoComisionCategoriaProducto;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getIdOrganizacion()
/*  62:    */   {
/*  63:119 */     return this.idOrganizacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setIdOrganizacion(int idOrganizacion)
/*  67:    */   {
/*  68:127 */     this.idOrganizacion = idOrganizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getIdSucursal()
/*  72:    */   {
/*  73:135 */     return this.idSucursal;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIdSucursal(int idSucursal)
/*  77:    */   {
/*  78:143 */     this.idSucursal = idSucursal;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public BigDecimal getPorcentajeComision()
/*  82:    */   {
/*  83:151 */     return this.porcentajeComision;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setPorcentajeComision(BigDecimal porcentajeComision)
/*  87:    */   {
/*  88:159 */     this.porcentajeComision = porcentajeComision;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public RangoComision getRangoComision()
/*  92:    */   {
/*  93:167 */     return this.rangoComision;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setRangoComision(RangoComision rangoComision)
/*  97:    */   {
/*  98:175 */     this.rangoComision = rangoComision;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public CategoriaProducto getCategoriaProducto()
/* 102:    */   {
/* 103:183 */     return this.categoriaProducto;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 107:    */   {
/* 108:191 */     this.categoriaProducto = categoriaProducto;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Canal getCanal()
/* 112:    */   {
/* 113:199 */     return this.canal;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setCanal(Canal canal)
/* 117:    */   {
/* 118:207 */     this.canal = canal;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getId()
/* 122:    */   {
/* 123:215 */     return this.idRangoComisionCategoriaProducto;
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.RangoComisionCategoriaProducto
 * JD-Core Version:    0.7.0.1
 */