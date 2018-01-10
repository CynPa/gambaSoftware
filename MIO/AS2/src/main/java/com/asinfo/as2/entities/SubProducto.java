/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.Date;
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
/*  15:    */ import javax.persistence.Temporal;
/*  16:    */ import javax.persistence.TemporalType;
/*  17:    */ import javax.validation.constraints.Max;
/*  18:    */ import javax.validation.constraints.Min;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="sub_producto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_producto_padre", "id_producto"})})
/*  24:    */ public class SubProducto
/*  25:    */   extends EntidadBase
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = -6587908952680115901L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="sub_producto", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="sub_producto")
/*  31:    */   @Column(name="id_sub_producto")
/*  32:    */   private int idSubProducto;
/*  33:    */   @Column(name="id_organizacion", nullable=false)
/*  34:    */   private int idOrganizacion;
/*  35:    */   @Column(name="id_sucursal", nullable=false)
/*  36:    */   private int idSucursal;
/*  37:    */   @Temporal(TemporalType.DATE)
/*  38:    */   @Column(name="fecha_desde", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private Date fechaDesde;
/*  41:    */   @Temporal(TemporalType.DATE)
/*  42:    */   @Column(name="fecha_hasta", nullable=true)
/*  43:    */   private Date fechaHasta;
/*  44:    */   @Column(name="descripcion", nullable=true, length=200)
/*  45:    */   @Size(max=200)
/*  46:    */   private String descripcion;
/*  47:    */   @Column(name="porcentaje", nullable=false, precision=12, scale=2)
/*  48:    */   @Min(0L)
/*  49:    */   @Max(100L)
/*  50: 78 */   private BigDecimal porcentaje = BigDecimal.ZERO;
/*  51:    */   @Column(name="porcentaje_costo", nullable=false, precision=12, scale=2)
/*  52:    */   @Min(0L)
/*  53:    */   @Max(100L)
/*  54: 83 */   private BigDecimal porcentajeCosto = BigDecimal.ZERO;
/*  55:    */   @Column(name="cantidad", nullable=false, precision=12, scale=2)
/*  56:    */   @Min(0L)
/*  57: 88 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  58:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_producto_padre", nullable=true)
/*  60:    */   private Producto productoPadre;
/*  61:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  62:    */   @JoinColumn(name="id_producto", nullable=false)
/*  63:    */   private Producto producto;
/*  64:    */   
/*  65:    */   public int getId()
/*  66:    */   {
/*  67:122 */     return this.idSubProducto;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdSubProducto()
/*  71:    */   {
/*  72:131 */     return this.idSubProducto;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdSubProducto(int idSubProducto)
/*  76:    */   {
/*  77:141 */     this.idSubProducto = idSubProducto;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdOrganizacion()
/*  81:    */   {
/*  82:150 */     return this.idOrganizacion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdOrganizacion(int idOrganizacion)
/*  86:    */   {
/*  87:160 */     this.idOrganizacion = idOrganizacion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getIdSucursal()
/*  91:    */   {
/*  92:169 */     return this.idSucursal;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setIdSucursal(int idSucursal)
/*  96:    */   {
/*  97:179 */     this.idSucursal = idSucursal;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Date getFechaDesde()
/* 101:    */   {
/* 102:188 */     return this.fechaDesde;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setFechaDesde(Date fechaDesde)
/* 106:    */   {
/* 107:198 */     this.fechaDesde = fechaDesde;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Date getFechaHasta()
/* 111:    */   {
/* 112:207 */     return this.fechaHasta;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setFechaHasta(Date fechaHasta)
/* 116:    */   {
/* 117:217 */     this.fechaHasta = fechaHasta;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getDescripcion()
/* 121:    */   {
/* 122:226 */     return this.descripcion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setDescripcion(String descripcion)
/* 126:    */   {
/* 127:236 */     this.descripcion = descripcion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public BigDecimal getPorcentaje()
/* 131:    */   {
/* 132:245 */     return this.porcentaje;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setPorcentaje(BigDecimal porcentaje)
/* 136:    */   {
/* 137:255 */     this.porcentaje = porcentaje;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public BigDecimal getPorcentajeCosto()
/* 141:    */   {
/* 142:263 */     return this.porcentajeCosto;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setPorcentajeCosto(BigDecimal porcentajeCosto)
/* 146:    */   {
/* 147:271 */     this.porcentajeCosto = porcentajeCosto;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public BigDecimal getCantidad()
/* 151:    */   {
/* 152:280 */     return this.cantidad;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setCantidad(BigDecimal cantidad)
/* 156:    */   {
/* 157:290 */     this.cantidad = cantidad;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Producto getProductoPadre()
/* 161:    */   {
/* 162:299 */     return this.productoPadre;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setProductoPadre(Producto productoPadre)
/* 166:    */   {
/* 167:309 */     this.productoPadre = productoPadre;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Producto getProducto()
/* 171:    */   {
/* 172:318 */     return this.producto;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setProducto(Producto producto)
/* 176:    */   {
/* 177:328 */     this.producto = producto;
/* 178:    */   }
/* 179:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.SubProducto
 * JD-Core Version:    0.7.0.1
 */