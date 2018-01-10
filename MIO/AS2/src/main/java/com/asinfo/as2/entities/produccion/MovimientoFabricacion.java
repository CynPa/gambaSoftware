/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Bodega;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import com.asinfo.as2.entities.Producto;
/*   6:    */ import com.asinfo.as2.entities.Unidad;
/*   7:    */ import java.math.BigDecimal;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.validation.constraints.DecimalMin;
/*  19:    */ import javax.validation.constraints.Digits;
/*  20:    */ import javax.validation.constraints.NotNull;
/*  21:    */ import javax.validation.constraints.Size;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="movimiento_fabricacion")
/*  25:    */ public class MovimientoFabricacion
/*  26:    */   extends EntidadBase
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="movimiento_fabricacion", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="movimiento_fabricacion")
/*  32:    */   @Column(name="id_movimiento_fabricacion")
/*  33:    */   private int idMovimientoFabricacion;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal", nullable=false)
/*  37:    */   private int idSucursal;
/*  38:    */   @Column(name="cantidad", nullable=false, precision=12, scale=2)
/*  39:    */   @Digits(integer=12, fraction=2)
/*  40:    */   @DecimalMin("0.00")
/*  41: 64 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  42:    */   @Column(name="operacion", nullable=false, insertable=false, updatable=false)
/*  43:    */   private int operacion;
/*  44:    */   @Column(name="descripcion", nullable=true, length=200)
/*  45:    */   @Size(max=200)
/*  46:    */   private String descripcion;
/*  47:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  48:    */   @JoinColumn(name="id_orden_fabricacion", nullable=true)
/*  49:    */   protected OrdenFabricacion ordenFabricacion;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_producto", nullable=true)
/*  52:    */   private Producto producto;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_unidad", nullable=false)
/*  55:    */   private Unidad unidad;
/*  56:    */   @NotNull
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_bodega", nullable=false)
/*  59:    */   private Bodega bodega;
/*  60:    */   
/*  61:    */   public int getId()
/*  62:    */   {
/*  63:118 */     return this.idMovimientoFabricacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Bodega getBodega()
/*  67:    */   {
/*  68:122 */     return this.bodega;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setBodega(Bodega bodega)
/*  72:    */   {
/*  73:126 */     this.bodega = bodega;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdMovimientoFabricacion()
/*  77:    */   {
/*  78:130 */     return this.idMovimientoFabricacion;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdMovimientoFabricacion(int idMovimientoFabricacion)
/*  82:    */   {
/*  83:134 */     this.idMovimientoFabricacion = idMovimientoFabricacion;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getIdOrganizacion()
/*  87:    */   {
/*  88:138 */     return this.idOrganizacion;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIdOrganizacion(int idOrganizacion)
/*  92:    */   {
/*  93:142 */     this.idOrganizacion = idOrganizacion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getIdSucursal()
/*  97:    */   {
/*  98:146 */     return this.idSucursal;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setIdSucursal(int idSucursal)
/* 102:    */   {
/* 103:150 */     this.idSucursal = idSucursal;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public BigDecimal getCantidad()
/* 107:    */   {
/* 108:154 */     return this.cantidad;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setCantidad(BigDecimal cantidad)
/* 112:    */   {
/* 113:158 */     this.cantidad = cantidad;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getDescripcion()
/* 117:    */   {
/* 118:162 */     return this.descripcion;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setDescripcion(String descripcion)
/* 122:    */   {
/* 123:166 */     this.descripcion = descripcion;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public int getOperacion()
/* 127:    */   {
/* 128:170 */     return this.operacion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setOperacion(int operacion)
/* 132:    */   {
/* 133:174 */     this.operacion = operacion;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public OrdenFabricacion getOrdenFabricacion()
/* 137:    */   {
/* 138:178 */     return this.ordenFabricacion;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/* 142:    */   {
/* 143:182 */     this.ordenFabricacion = ordenFabricacion;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Producto getProducto()
/* 147:    */   {
/* 148:186 */     return this.producto;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setProducto(Producto producto)
/* 152:    */   {
/* 153:190 */     this.producto = producto;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Unidad getUnidad()
/* 157:    */   {
/* 158:194 */     return this.unidad;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setUnidad(Unidad unidad)
/* 162:    */   {
/* 163:198 */     this.unidad = unidad;
/* 164:    */   }
/* 165:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.MovimientoFabricacion
 * JD-Core Version:    0.7.0.1
 */