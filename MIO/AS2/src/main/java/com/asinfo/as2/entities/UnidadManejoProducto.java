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
/*  15:    */ import javax.validation.constraints.Min;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="unidad_manejo_producto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_producto", "id_unidad_manejo"})})
/*  20:    */ public class UnidadManejoProducto
/*  21:    */   extends EntidadBase
/*  22:    */   implements Serializable
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="unidad_manejo_producto", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="unidad_manejo_producto")
/*  28:    */   @Column(name="id_unidad_manejo_producto", unique=true, nullable=false)
/*  29:    */   private int idUnidadManejoProducto;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_unidad_manejo", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private UnidadManejo unidadManejo;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_producto", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private Producto producto;
/*  42:    */   @Column(name="peso_promedio", nullable=false, precision=12, scale=2)
/*  43:    */   @NotNull
/*  44:    */   @Min(0L)
/*  45: 63 */   private BigDecimal pesoPromedio = BigDecimal.ZERO;
/*  46:    */   @Column(name="peso_minimo", nullable=false, precision=12, scale=2)
/*  47:    */   @NotNull
/*  48:    */   @Min(0L)
/*  49: 68 */   private BigDecimal pesoMinimo = BigDecimal.ZERO;
/*  50:    */   @Column(name="peso_maximo", nullable=false, precision=12, scale=2)
/*  51:    */   @NotNull
/*  52:    */   @Min(0L)
/*  53: 73 */   private BigDecimal pesoMaximo = BigDecimal.ZERO;
/*  54:    */   @Column(name="cantidad", nullable=true)
/*  55:    */   @Min(0L)
/*  56: 80 */   private Integer cantidad = Integer.valueOf(0);
/*  57:    */   
/*  58:    */   public int getId()
/*  59:    */   {
/*  60: 87 */     return this.idUnidadManejoProducto;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getIdUnidadManejoProducto()
/*  64:    */   {
/*  65: 91 */     return this.idUnidadManejoProducto;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdUnidadManejoProducto(int idUnidadManejoProducto)
/*  69:    */   {
/*  70: 95 */     this.idUnidadManejoProducto = idUnidadManejoProducto;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdOrganizacion()
/*  74:    */   {
/*  75: 99 */     return this.idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdOrganizacion(int idOrganizacion)
/*  79:    */   {
/*  80:103 */     this.idOrganizacion = idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdSucursal()
/*  84:    */   {
/*  85:107 */     return this.idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdSucursal(int idSucursal)
/*  89:    */   {
/*  90:111 */     this.idSucursal = idSucursal;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public UnidadManejo getUnidadManejo()
/*  94:    */   {
/*  95:116 */     return this.unidadManejo;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setUnidadManejo(UnidadManejo unidadManejo)
/*  99:    */   {
/* 100:120 */     this.unidadManejo = unidadManejo;
/* 101:121 */     setEditado(true);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Producto getProducto()
/* 105:    */   {
/* 106:125 */     return this.producto;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setProducto(Producto producto)
/* 110:    */   {
/* 111:129 */     this.producto = producto;
/* 112:130 */     setEditado(true);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public BigDecimal getPesoPromedio()
/* 116:    */   {
/* 117:134 */     return this.pesoPromedio;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setPesoPromedio(BigDecimal pesoPromedio)
/* 121:    */   {
/* 122:138 */     this.pesoPromedio = pesoPromedio;
/* 123:139 */     setEditado(true);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public BigDecimal getPesoMinimo()
/* 127:    */   {
/* 128:143 */     return this.pesoMinimo;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setPesoMinimo(BigDecimal pesoMinimo)
/* 132:    */   {
/* 133:147 */     this.pesoMinimo = pesoMinimo;
/* 134:148 */     setEditado(true);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public BigDecimal getPesoMaximo()
/* 138:    */   {
/* 139:152 */     return this.pesoMaximo;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setPesoMaximo(BigDecimal pesoMaximo)
/* 143:    */   {
/* 144:156 */     this.pesoMaximo = pesoMaximo;
/* 145:157 */     setEditado(true);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Integer getCantidad()
/* 149:    */   {
/* 150:161 */     return this.cantidad;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setCantidad(Integer cantidad)
/* 154:    */   {
/* 155:165 */     this.cantidad = cantidad;
/* 156:    */   }
/* 157:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.UnidadManejoProducto
 * JD-Core Version:    0.7.0.1
 */