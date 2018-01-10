/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.OneToMany;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ import javax.validation.constraints.Size;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="subcategoria_producto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  21:    */ public class SubcategoriaProducto
/*  22:    */   extends EntidadBase
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="subcategoria_producto", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="subcategoria_producto")
/*  28:    */   @Column(name="id_subcategoria_producto", unique=true, nullable=false)
/*  29:    */   private int idSubcategoriaProducto;
/*  30:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  31:    */   @JoinColumn(name="id_categoria_producto", nullable=false)
/*  32:    */   @NotNull
/*  33:    */   private CategoriaProducto categoriaProducto;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal", nullable=false)
/*  37:    */   private int idSucursal;
/*  38:    */   @Column(name="codigo", nullable=false, length=10)
/*  39:    */   @NotNull
/*  40:    */   @Size(max=10)
/*  41:    */   private String codigo;
/*  42:    */   @Column(name="nombre", nullable=false, length=50)
/*  43:    */   @NotNull
/*  44:    */   @Size(max=50)
/*  45:    */   private String nombre;
/*  46:    */   @Column(name="descripcion", length=200)
/*  47:    */   @Size(max=200)
/*  48:    */   private String descripcion;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_cuenta_contable_inventario", nullable=true)
/*  51:    */   private CuentaContable cuentaContableInventario;
/*  52:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_cuenta_contable_costo", nullable=true)
/*  54:    */   private CuentaContable cuentaContableCosto;
/*  55:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  56:    */   @JoinColumn(name="id_cuenta_contable_gasto", nullable=true)
/*  57:    */   private CuentaContable cuentaContableGasto;
/*  58:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_cuenta_contable_mercaderia_por_recibir", nullable=true)
/*  60:    */   private CuentaContable cuentaContableMercaderiaPorRecibir;
/*  61:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  62:    */   @JoinColumn(name="id_cuenta_contable_mercaderia_por_despachar", nullable=true)
/*  63:    */   private CuentaContable cuentaContableMercaderiaPorDespachar;
/*  64:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  65:    */   @JoinColumn(name="id_cuenta_contable_ingreso", nullable=true)
/*  66:    */   private CuentaContable cuentaContableIngreso;
/*  67:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  68:    */   @JoinColumn(name="id_cuenta_contable_descuento_venta", nullable=true)
/*  69:    */   private CuentaContable cuentaContableDescuentoVenta;
/*  70:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  71:    */   @JoinColumn(name="id_cuenta_contable_devolucion_venta", nullable=true)
/*  72:    */   private CuentaContable cuentaContableDevolucionVenta;
/*  73:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  74:    */   @JoinColumn(name="id_cuenta_contable_descuento_compra", nullable=true)
/*  75:    */   private CuentaContable cuentaContableDescuentoCompra;
/*  76:    */   @Deprecated
/*  77:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  78:    */   @JoinColumn(name="id_cuenta_contable_devolucion_compra", nullable=true)
/*  79:    */   private CuentaContable cuentaContableDevolucionCompra;
/*  80:    */   @Column(name="activo", nullable=false)
/*  81:    */   @NotNull
/*  82:    */   private boolean activo;
/*  83:    */   @Column(name="predeterminado", nullable=false)
/*  84:    */   @NotNull
/*  85:    */   private boolean predeterminado;
/*  86:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="subcategoriaProducto")
/*  87:124 */   private List<UnidadConversion> listaUnidadConversion = new ArrayList();
/*  88:    */   
/*  89:    */   public SubcategoriaProducto() {}
/*  90:    */   
/*  91:    */   public SubcategoriaProducto(int idSubcategoriaProducto, String codigo, String nombre)
/*  92:    */   {
/*  93:132 */     this.idSubcategoriaProducto = idSubcategoriaProducto;
/*  94:133 */     this.codigo = codigo;
/*  95:134 */     this.nombre = nombre;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getId()
/*  99:    */   {
/* 100:139 */     return this.idSubcategoriaProducto;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int getIdSubcategoriaProducto()
/* 104:    */   {
/* 105:143 */     return this.idSubcategoriaProducto;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setIdSubcategoriaProducto(int idSubcategoriaProducto)
/* 109:    */   {
/* 110:147 */     this.idSubcategoriaProducto = idSubcategoriaProducto;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public CategoriaProducto getCategoriaProducto()
/* 114:    */   {
/* 115:151 */     return this.categoriaProducto;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 119:    */   {
/* 120:155 */     this.categoriaProducto = categoriaProducto;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public int getIdOrganizacion()
/* 124:    */   {
/* 125:159 */     return this.idOrganizacion;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setIdOrganizacion(int idOrganizacion)
/* 129:    */   {
/* 130:163 */     this.idOrganizacion = idOrganizacion;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public int getIdSucursal()
/* 134:    */   {
/* 135:167 */     return this.idSucursal;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setIdSucursal(int idSucursal)
/* 139:    */   {
/* 140:171 */     this.idSucursal = idSucursal;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String getCodigo()
/* 144:    */   {
/* 145:175 */     return this.codigo;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setCodigo(String codigo)
/* 149:    */   {
/* 150:179 */     this.codigo = codigo;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String getNombre()
/* 154:    */   {
/* 155:183 */     return this.nombre;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setNombre(String nombre)
/* 159:    */   {
/* 160:187 */     this.nombre = nombre;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String getDescripcion()
/* 164:    */   {
/* 165:191 */     return this.descripcion;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setDescripcion(String descripcion)
/* 169:    */   {
/* 170:195 */     this.descripcion = descripcion;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public boolean getActivo()
/* 174:    */   {
/* 175:199 */     return this.activo;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setActivo(boolean activo)
/* 179:    */   {
/* 180:203 */     this.activo = activo;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public boolean getPredeterminado()
/* 184:    */   {
/* 185:207 */     return this.predeterminado;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setPredeterminado(boolean predeterminado)
/* 189:    */   {
/* 190:211 */     this.predeterminado = predeterminado;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public CuentaContable getCuentaContableCosto()
/* 194:    */   {
/* 195:215 */     return this.cuentaContableCosto;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setCuentaContableCosto(CuentaContable cuentaContableCosto)
/* 199:    */   {
/* 200:219 */     this.cuentaContableCosto = cuentaContableCosto;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public CuentaContable getCuentaContableGasto()
/* 204:    */   {
/* 205:223 */     return this.cuentaContableGasto;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setCuentaContableGasto(CuentaContable cuentaContableGasto)
/* 209:    */   {
/* 210:227 */     this.cuentaContableGasto = cuentaContableGasto;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public CuentaContable getCuentaContableIngreso()
/* 214:    */   {
/* 215:231 */     return this.cuentaContableIngreso;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setCuentaContableIngreso(CuentaContable cuentaContableIngreso)
/* 219:    */   {
/* 220:235 */     this.cuentaContableIngreso = cuentaContableIngreso;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public CuentaContable getCuentaContableInventario()
/* 224:    */   {
/* 225:239 */     return this.cuentaContableInventario;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setCuentaContableInventario(CuentaContable cuentaContableInventario)
/* 229:    */   {
/* 230:243 */     this.cuentaContableInventario = cuentaContableInventario;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public String toString()
/* 234:    */   {
/* 235:248 */     return this.nombre;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public CuentaContable getCuentaContableMercaderiaPorRecibir()
/* 239:    */   {
/* 240:257 */     return this.cuentaContableMercaderiaPorRecibir;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setCuentaContableMercaderiaPorRecibir(CuentaContable cuentaContableMercaderiaPorRecibir)
/* 244:    */   {
/* 245:267 */     this.cuentaContableMercaderiaPorRecibir = cuentaContableMercaderiaPorRecibir;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public CuentaContable getCuentaContableMercaderiaPorDespachar()
/* 249:    */   {
/* 250:276 */     return this.cuentaContableMercaderiaPorDespachar;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setCuentaContableMercaderiaPorDespachar(CuentaContable cuentaContableMercaderiaPorDespachar)
/* 254:    */   {
/* 255:286 */     this.cuentaContableMercaderiaPorDespachar = cuentaContableMercaderiaPorDespachar;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public CuentaContable getCuentaContableDescuentoVenta()
/* 259:    */   {
/* 260:290 */     return this.cuentaContableDescuentoVenta;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setCuentaContableDescuentoVenta(CuentaContable cuentaContableDescuentoVenta)
/* 264:    */   {
/* 265:294 */     this.cuentaContableDescuentoVenta = cuentaContableDescuentoVenta;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public CuentaContable getCuentaContableDevolucionVenta()
/* 269:    */   {
/* 270:298 */     return this.cuentaContableDevolucionVenta;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setCuentaContableDevolucionVenta(CuentaContable cuentaContableDevolucionVenta)
/* 274:    */   {
/* 275:302 */     this.cuentaContableDevolucionVenta = cuentaContableDevolucionVenta;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public CuentaContable getCuentaContableDescuentoCompra()
/* 279:    */   {
/* 280:311 */     return this.cuentaContableDescuentoCompra;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setCuentaContableDescuentoCompra(CuentaContable cuentaContableDescuentoCompra)
/* 284:    */   {
/* 285:321 */     this.cuentaContableDescuentoCompra = cuentaContableDescuentoCompra;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public CuentaContable getCuentaContableDevolucionCompra()
/* 289:    */   {
/* 290:330 */     return this.cuentaContableDevolucionCompra;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setCuentaContableDevolucionCompra(CuentaContable cuentaContableDevolucionCompra)
/* 294:    */   {
/* 295:340 */     this.cuentaContableDevolucionCompra = cuentaContableDevolucionCompra;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public List<UnidadConversion> getListaUnidadConversion()
/* 299:    */   {
/* 300:349 */     return this.listaUnidadConversion;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void setListaUnidadConversion(List<UnidadConversion> listaUnidadConversion)
/* 304:    */   {
/* 305:359 */     this.listaUnidadConversion = listaUnidadConversion;
/* 306:    */   }
/* 307:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.SubcategoriaProducto
 * JD-Core Version:    0.7.0.1
 */