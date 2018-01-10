/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.OneToMany;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.persistence.Transient;
/*  19:    */ import javax.validation.constraints.Min;
/*  20:    */ import javax.validation.constraints.NotNull;
/*  21:    */ import javax.validation.constraints.Size;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="unidad_manejo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  25:    */ public class UnidadManejo
/*  26:    */   extends EntidadBase
/*  27:    */   implements Serializable
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="unidad_manejo", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="unidad_manejo")
/*  33:    */   @Column(name="id_unidad_manejo", unique=true, nullable=false)
/*  34:    */   private int idUnidadManejo;
/*  35:    */   @Column(name="id_organizacion", nullable=false)
/*  36:    */   private int idOrganizacion;
/*  37:    */   @Column(name="id_sucursal", nullable=false)
/*  38:    */   private int idSucursal;
/*  39:    */   @Column(name="codigo", nullable=false, length=10)
/*  40:    */   @NotNull
/*  41:    */   @Size(min=1, max=10)
/*  42:    */   private String codigo;
/*  43:    */   @Column(name="nombre", nullable=false, length=50)
/*  44:    */   @NotNull
/*  45:    */   @Size(min=1, max=50)
/*  46:    */   private String nombre;
/*  47:    */   @Column(name="descripcion", nullable=true, length=300)
/*  48:    */   @Size(max=300)
/*  49:    */   private String descripcion;
/*  50:    */   @Column(name="activo", nullable=false)
/*  51:    */   @NotNull
/*  52: 74 */   private boolean activo = true;
/*  53:    */   @Column(name="predeterminado", nullable=false)
/*  54:    */   @NotNull
/*  55:    */   private boolean predeterminado;
/*  56:    */   @Column(name="peso", nullable=false, precision=12, scale=2)
/*  57:    */   @NotNull
/*  58:    */   @Min(0L)
/*  59: 82 */   private BigDecimal peso = BigDecimal.ZERO;
/*  60:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  61:    */   @JoinColumn(name="id_categoria_unidad_manejo", nullable=false)
/*  62:    */   @NotNull
/*  63:    */   private CategoriaUnidadManejo categoriaUnidadManejo;
/*  64:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="unidadManejo")
/*  65: 92 */   private List<UnidadManejoProducto> listaUnidadManejoProducto = new ArrayList();
/*  66:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  67:    */   @JoinColumn(name="id_unidad", nullable=false)
/*  68:    */   @NotNull
/*  69:    */   private Unidad unidad;
/*  70:    */   @Column(name="indicador_digitar_cantidad", nullable=false)
/*  71:    */   @NotNull
/*  72:    */   private boolean indicadorDigitarCantidad;
/*  73:    */   @Transient
/*  74:    */   private int traCantidad;
/*  75:    */   @Transient
/*  76:107 */   private List<DetalleMovimientoUnidadManejo> listaDetalleMovimientoUnidadManejo = new ArrayList();
/*  77:    */   
/*  78:    */   public int getId()
/*  79:    */   {
/*  80:114 */     return this.idUnidadManejo;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdUnidadManejo()
/*  84:    */   {
/*  85:123 */     return this.idUnidadManejo;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdUnidadManejo(int idUnidadManejo)
/*  89:    */   {
/*  90:133 */     this.idUnidadManejo = idUnidadManejo;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getIdOrganizacion()
/*  94:    */   {
/*  95:142 */     return this.idOrganizacion;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setIdOrganizacion(int idOrganizacion)
/*  99:    */   {
/* 100:152 */     this.idOrganizacion = idOrganizacion;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int getIdSucursal()
/* 104:    */   {
/* 105:161 */     return this.idSucursal;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setIdSucursal(int idSucursal)
/* 109:    */   {
/* 110:171 */     this.idSucursal = idSucursal;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getCodigo()
/* 114:    */   {
/* 115:180 */     return this.codigo;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setCodigo(String codigo)
/* 119:    */   {
/* 120:190 */     this.codigo = codigo;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String getNombre()
/* 124:    */   {
/* 125:199 */     return this.nombre;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setNombre(String nombre)
/* 129:    */   {
/* 130:209 */     this.nombre = nombre;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String getDescripcion()
/* 134:    */   {
/* 135:218 */     return this.descripcion;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setDescripcion(String descripcion)
/* 139:    */   {
/* 140:228 */     this.descripcion = descripcion;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public boolean isActivo()
/* 144:    */   {
/* 145:237 */     return this.activo;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setActivo(boolean activo)
/* 149:    */   {
/* 150:247 */     this.activo = activo;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public boolean isPredeterminado()
/* 154:    */   {
/* 155:256 */     return this.predeterminado;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setPredeterminado(boolean predeterminado)
/* 159:    */   {
/* 160:266 */     this.predeterminado = predeterminado;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public BigDecimal getPeso()
/* 164:    */   {
/* 165:270 */     return this.peso;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setPeso(BigDecimal peso)
/* 169:    */   {
/* 170:274 */     this.peso = peso;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public CategoriaUnidadManejo getCategoriaUnidadManejo()
/* 174:    */   {
/* 175:278 */     return this.categoriaUnidadManejo;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setCategoriaUnidadManejo(CategoriaUnidadManejo categoriaUnidadManejo)
/* 179:    */   {
/* 180:282 */     this.categoriaUnidadManejo = categoriaUnidadManejo;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public List<UnidadManejoProducto> getListaUnidadManejoProducto()
/* 184:    */   {
/* 185:286 */     return this.listaUnidadManejoProducto;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setListaUnidadManejoProducto(List<UnidadManejoProducto> listaUnidadManejoProducto)
/* 189:    */   {
/* 190:290 */     this.listaUnidadManejoProducto = listaUnidadManejoProducto;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Unidad getUnidad()
/* 194:    */   {
/* 195:294 */     return this.unidad;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setUnidad(Unidad unidad)
/* 199:    */   {
/* 200:298 */     this.unidad = unidad;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public boolean isIndicadorDigitarCantidad()
/* 204:    */   {
/* 205:302 */     return this.indicadorDigitarCantidad;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setIndicadorDigitarCantidad(boolean indicadorDigitarCantidad)
/* 209:    */   {
/* 210:306 */     this.indicadorDigitarCantidad = indicadorDigitarCantidad;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public int getTraCantidad()
/* 214:    */   {
/* 215:313 */     return this.traCantidad;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setTraCantidad(int traCantidad)
/* 219:    */   {
/* 220:321 */     this.traCantidad = traCantidad;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public List<DetalleMovimientoUnidadManejo> getListaDetalleMovimientoUnidadManejo()
/* 224:    */   {
/* 225:325 */     return this.listaDetalleMovimientoUnidadManejo;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setListaDetalleMovimientoUnidadManejo(List<DetalleMovimientoUnidadManejo> listaDetalleMovimientoUnidadManejo)
/* 229:    */   {
/* 230:329 */     this.listaDetalleMovimientoUnidadManejo = listaDetalleMovimientoUnidadManejo;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public String toString()
/* 234:    */   {
/* 235:334 */     return this.nombre;
/* 236:    */   }
/* 237:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.UnidadManejo
 * JD-Core Version:    0.7.0.1
 */