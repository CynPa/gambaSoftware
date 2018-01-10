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
/*  14:    */ import javax.persistence.Transient;
/*  15:    */ import javax.validation.constraints.Min;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="detalle_version_lista_precios", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_producto", "id_version_lista_precios"})})
/*  19:    */ public class DetalleVersionListaPrecios
/*  20:    */   extends EntidadBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="detalle_version_lista_precios", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_version_lista_precios")
/*  26:    */   @Column(name="id_detalle_version_lista_precios")
/*  27:    */   private int idDetalleVersionListaPrecios;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   private int idOrganizacion;
/*  30:    */   @Column(name="id_sucursal", nullable=false)
/*  31:    */   private int idSucursal;
/*  32:    */   @Column(name="precio_unitario", nullable=false, precision=14, scale=6)
/*  33:    */   @Min(0L)
/*  34: 54 */   private BigDecimal precioUnitario = BigDecimal.ZERO;
/*  35:    */   @Column(name="precio_unitario_cliente_final", nullable=false, precision=14, scale=6)
/*  36:    */   @Min(0L)
/*  37: 58 */   private BigDecimal precioUnitarioClienteFinal = BigDecimal.ZERO;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_version_lista_precios", nullable=true)
/*  40:    */   private VersionListaPrecios versionListaPrecios;
/*  41:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  42:    */   @JoinColumn(name="id_producto")
/*  43:    */   private Producto producto;
/*  44:    */   @Transient
/*  45:    */   private String traNombreProducto;
/*  46:    */   @Transient
/*  47:    */   private String traCodigoProducto;
/*  48:    */   @Transient
/*  49:    */   private String traPrecioReferencial;
/*  50:    */   
/*  51:    */   public int getId()
/*  52:    */   {
/*  53: 81 */     return this.idDetalleVersionListaPrecios;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public DetalleVersionListaPrecios() {}
/*  57:    */   
/*  58:    */   public DetalleVersionListaPrecios(int idDetalleVersionListaPrecios, BigDecimal precioUnitario, BigDecimal porcentajeDescuentoMaximo, String traNombreProducto, String traCodigoProducto, String traPrecioReferencial)
/*  59:    */   {
/*  60: 91 */     this.idDetalleVersionListaPrecios = idDetalleVersionListaPrecios;
/*  61: 92 */     this.precioUnitario = precioUnitario;
/*  62: 93 */     this.traNombreProducto = traNombreProducto;
/*  63: 94 */     this.traCodigoProducto = traCodigoProducto;
/*  64: 95 */     this.traPrecioReferencial = traPrecioReferencial;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getIdDetalleVersionListaPrecios()
/*  68:    */   {
/*  69:104 */     return this.idDetalleVersionListaPrecios;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setIdDetalleVersionListaPrecios(int idDetalleVersionListaPrecios)
/*  73:    */   {
/*  74:114 */     this.idDetalleVersionListaPrecios = idDetalleVersionListaPrecios;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getIdOrganizacion()
/*  78:    */   {
/*  79:123 */     return this.idOrganizacion;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIdOrganizacion(int idOrganizacion)
/*  83:    */   {
/*  84:133 */     this.idOrganizacion = idOrganizacion;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getIdSucursal()
/*  88:    */   {
/*  89:142 */     return this.idSucursal;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setIdSucursal(int idSucursal)
/*  93:    */   {
/*  94:152 */     this.idSucursal = idSucursal;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public VersionListaPrecios getVersionListaPrecios()
/*  98:    */   {
/*  99:161 */     return this.versionListaPrecios;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setVersionListaPrecios(VersionListaPrecios versionListaPrecios)
/* 103:    */   {
/* 104:171 */     this.versionListaPrecios = versionListaPrecios;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Producto getProducto()
/* 108:    */   {
/* 109:180 */     return this.producto;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setProducto(Producto producto)
/* 113:    */   {
/* 114:190 */     this.producto = producto;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public BigDecimal getPrecioUnitario()
/* 118:    */   {
/* 119:194 */     return this.precioUnitario;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setPrecioUnitario(BigDecimal precioUnitario)
/* 123:    */   {
/* 124:198 */     this.precioUnitario = precioUnitario;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getTraNombreProducto()
/* 128:    */   {
/* 129:202 */     return this.traNombreProducto;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setTraNombreProducto(String traNombreProducto)
/* 133:    */   {
/* 134:206 */     this.traNombreProducto = traNombreProducto;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String getTraCodigoProducto()
/* 138:    */   {
/* 139:210 */     return this.traCodigoProducto;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setTraCodigoProducto(String traCodigoProducto)
/* 143:    */   {
/* 144:214 */     this.traCodigoProducto = traCodigoProducto;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getTraPrecioReferencial()
/* 148:    */   {
/* 149:218 */     return this.traPrecioReferencial;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setTraPrecioReferencial(String traPrecioReferencial)
/* 153:    */   {
/* 154:222 */     this.traPrecioReferencial = traPrecioReferencial;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public BigDecimal getPrecioUnitarioClienteFinal()
/* 158:    */   {
/* 159:229 */     return this.precioUnitarioClienteFinal;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setPrecioUnitarioClienteFinal(BigDecimal precioUnitarioClienteFinal)
/* 163:    */   {
/* 164:236 */     this.precioUnitarioClienteFinal = precioUnitarioClienteFinal;
/* 165:    */   }
/* 166:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleVersionListaPrecios
 * JD-Core Version:    0.7.0.1
 */