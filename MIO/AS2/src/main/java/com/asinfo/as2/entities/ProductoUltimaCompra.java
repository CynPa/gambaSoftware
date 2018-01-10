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
/*  17:    */ import javax.persistence.Transient;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ import org.hibernate.annotations.ColumnDefault;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="producto_ultima_compra", uniqueConstraints={@javax.persistence.UniqueConstraint(name="indice_emp_pro", columnNames={"id_empresa", "id_producto"})})
/*  23:    */ public class ProductoUltimaCompra
/*  24:    */   extends EntidadBase
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="producto_ultima_compra", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="producto_ultima_compra")
/*  30:    */   @Column(name="id_producto_ultima_compra")
/*  31:    */   private int idProductoUltimaCompra;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   @NotNull
/*  34:    */   private int idOrganizacion;
/*  35:    */   @Column(name="id_sucursal", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private int idSucursal;
/*  38:    */   @Temporal(TemporalType.DATE)
/*  39:    */   @Column(name="fecha", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private Date fecha;
/*  42:    */   @Column(name="precio", nullable=false, precision=12, scale=4)
/*  43: 67 */   private BigDecimal precio = BigDecimal.ZERO;
/*  44:    */   @Column(name="precio_pactado", nullable=false, precision=12, scale=6)
/*  45:    */   @NotNull
/*  46:    */   @ColumnDefault("0.00")
/*  47: 70 */   private BigDecimal precioPactado = BigDecimal.ZERO;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  50:    */   @NotNull
/*  51:    */   private Empresa empresa;
/*  52:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_producto", nullable=false)
/*  54:    */   @NotNull
/*  55:    */   private Producto producto;
/*  56:    */   @Transient
/*  57:    */   private boolean indicadorGuardar;
/*  58:    */   @Transient
/*  59:    */   private boolean indicadorNuevo;
/*  60:    */   @Transient
/*  61: 98 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  62:    */   
/*  63:    */   public int getIdProductoUltimaCompra()
/*  64:    */   {
/*  65:115 */     return this.idProductoUltimaCompra;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdProductoUltimaCompra(int idProductoUltimaCompra)
/*  69:    */   {
/*  70:125 */     this.idProductoUltimaCompra = idProductoUltimaCompra;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdOrganizacion()
/*  74:    */   {
/*  75:134 */     return this.idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdOrganizacion(int idOrganizacion)
/*  79:    */   {
/*  80:144 */     this.idOrganizacion = idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdSucursal()
/*  84:    */   {
/*  85:153 */     return this.idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdSucursal(int idSucursal)
/*  89:    */   {
/*  90:163 */     this.idSucursal = idSucursal;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Date getFecha()
/*  94:    */   {
/*  95:172 */     return this.fecha;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setFecha(Date fecha)
/*  99:    */   {
/* 100:182 */     this.fecha = fecha;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public BigDecimal getPrecio()
/* 104:    */   {
/* 105:191 */     return this.precio;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setPrecio(BigDecimal precio)
/* 109:    */   {
/* 110:201 */     this.precio = precio;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Empresa getEmpresa()
/* 114:    */   {
/* 115:210 */     return this.empresa;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setEmpresa(Empresa empresa)
/* 119:    */   {
/* 120:220 */     this.empresa = empresa;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Producto getProducto()
/* 124:    */   {
/* 125:229 */     return this.producto;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setProducto(Producto producto)
/* 129:    */   {
/* 130:239 */     this.producto = producto;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public int getId()
/* 134:    */   {
/* 135:249 */     return this.idProductoUltimaCompra;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public BigDecimal getPrecioPactado()
/* 139:    */   {
/* 140:253 */     return this.precioPactado;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setPrecioPactado(BigDecimal precioPactado)
/* 144:    */   {
/* 145:257 */     this.precioPactado = precioPactado;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean isIndicadorGuardar()
/* 149:    */   {
/* 150:261 */     return this.indicadorGuardar;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setIndicadorGuardar(boolean indicadorGuardar)
/* 154:    */   {
/* 155:265 */     this.indicadorGuardar = indicadorGuardar;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean isIndicadorNuevo()
/* 159:    */   {
/* 160:269 */     return this.indicadorNuevo;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setIndicadorNuevo(boolean indicadorNuevo)
/* 164:    */   {
/* 165:273 */     this.indicadorNuevo = indicadorNuevo;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public BigDecimal getCantidad()
/* 169:    */   {
/* 170:277 */     if (this.cantidad == null) {
/* 171:278 */       this.cantidad = BigDecimal.ZERO;
/* 172:    */     }
/* 173:280 */     return this.cantidad;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setCantidad(BigDecimal cantidad)
/* 177:    */   {
/* 178:284 */     this.cantidad = cantidad;
/* 179:    */   }
/* 180:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ProductoUltimaCompra
 * JD-Core Version:    0.7.0.1
 */