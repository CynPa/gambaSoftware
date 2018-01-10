/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TipoAtributo;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.EnumType;
/*   9:    */ import javax.persistence.Enumerated;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.OneToMany;
/*  15:    */ import javax.persistence.OrderBy;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.persistence.Transient;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="atributo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  24:    */ public class Atributo
/*  25:    */   extends EntidadBase
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="atributo", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="atributo")
/*  31:    */   @Column(name="id_atributo", unique=true, nullable=false)
/*  32:    */   private int idAtributo;
/*  33:    */   @Column(name="id_organizacion", nullable=false)
/*  34:    */   private int idOrganizacion;
/*  35:    */   @Column(name="id_sucursal", nullable=false)
/*  36:    */   private int idSucursal;
/*  37:    */   @Column(name="codigo", nullable=false, length=10)
/*  38:    */   @Size(min=2, max=10)
/*  39:    */   private String codigo;
/*  40:    */   @Column(name="nombre", nullable=false, length=50)
/*  41:    */   @Size(min=2, max=50)
/*  42:    */   private String nombre;
/*  43:    */   @Column(name="tipo_atributo", nullable=false)
/*  44:    */   @Enumerated(EnumType.ORDINAL)
/*  45:    */   @NotNull
/*  46:    */   private TipoAtributo tipoAtributo;
/*  47:    */   @Column(name="indicador_requerido", nullable=false)
/*  48:    */   private boolean indicadorRequerido;
/*  49:    */   @Column(name="indicador_impresion", nullable=false)
/*  50:    */   private boolean indicadorImpresion;
/*  51:    */   @Column(name="descripcion", length=200)
/*  52:    */   @Size(max=200)
/*  53:    */   private String descripcion;
/*  54:    */   @Column(name="indicador_instancia", nullable=false)
/*  55:    */   @NotNull
/*  56:    */   private boolean indicadorInstancia;
/*  57:    */   @Column(name="activo", nullable=false)
/*  58:    */   @NotNull
/*  59:    */   private boolean activo;
/*  60:    */   @Column(name="predeterminado", nullable=false)
/*  61:    */   @NotNull
/*  62:    */   private boolean predeterminado;
/*  63:    */   @Column(name="indicador_producto")
/*  64:    */   private boolean indicadorProducto;
/*  65:    */   @Column(name="indicador_cliente")
/*  66:    */   private boolean indicadorCliente;
/*  67:    */   @Column(name="indicador_proveedor")
/*  68:    */   private boolean indicadorProveedor;
/*  69:    */   @OrderBy("nombre")
/*  70:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="atributo")
/*  71:102 */   private List<ValorAtributo> listaValorAtributo = new ArrayList();
/*  72:    */   @Transient
/*  73:    */   private boolean indicadorLista;
/*  74:    */   
/*  75:    */   public Atributo() {}
/*  76:    */   
/*  77:    */   public Atributo(int idAtributo, String codigo, String nombre)
/*  78:    */   {
/*  79:119 */     this.idAtributo = idAtributo;
/*  80:120 */     this.codigo = codigo;
/*  81:121 */     this.nombre = nombre;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getId()
/*  85:    */   {
/*  86:126 */     return this.idAtributo;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getIdAtributo()
/*  90:    */   {
/*  91:130 */     return this.idAtributo;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdAtributo(int idAtributo)
/*  95:    */   {
/*  96:134 */     this.idAtributo = idAtributo;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getIdOrganizacion()
/* 100:    */   {
/* 101:138 */     return this.idOrganizacion;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setIdOrganizacion(int idOrganizacion)
/* 105:    */   {
/* 106:142 */     this.idOrganizacion = idOrganizacion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int getIdSucursal()
/* 110:    */   {
/* 111:146 */     return this.idSucursal;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setIdSucursal(int idSucursal)
/* 115:    */   {
/* 116:150 */     this.idSucursal = idSucursal;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getCodigo()
/* 120:    */   {
/* 121:154 */     return this.codigo;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setCodigo(String codigo)
/* 125:    */   {
/* 126:158 */     this.codigo = codigo;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String getNombre()
/* 130:    */   {
/* 131:162 */     return this.nombre;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setNombre(String nombre)
/* 135:    */   {
/* 136:166 */     this.nombre = nombre;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String getDescripcion()
/* 140:    */   {
/* 141:170 */     return this.descripcion;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setDescripcion(String descripcion)
/* 145:    */   {
/* 146:174 */     this.descripcion = descripcion;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public boolean getActivo()
/* 150:    */   {
/* 151:178 */     return this.activo;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setActivo(boolean activo)
/* 155:    */   {
/* 156:182 */     this.activo = activo;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public boolean getPredeterminado()
/* 160:    */   {
/* 161:186 */     return this.predeterminado;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setPredeterminado(boolean predeterminado)
/* 165:    */   {
/* 166:190 */     this.predeterminado = predeterminado;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public boolean isIndicadorRequerido()
/* 170:    */   {
/* 171:199 */     return this.indicadorRequerido;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setIndicadorRequerido(boolean indicadorRequerido)
/* 175:    */   {
/* 176:209 */     this.indicadorRequerido = indicadorRequerido;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String toString()
/* 180:    */   {
/* 181:214 */     return this.nombre;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public TipoAtributo getTipoAtributo()
/* 185:    */   {
/* 186:223 */     return this.tipoAtributo;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setTipoAtributo(TipoAtributo tipoAtributo)
/* 190:    */   {
/* 191:233 */     this.tipoAtributo = tipoAtributo;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public List<ValorAtributo> getListaValorAtributo()
/* 195:    */   {
/* 196:242 */     return this.listaValorAtributo;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setListaValorAtributo(List<ValorAtributo> listaValorAtributo)
/* 200:    */   {
/* 201:252 */     this.listaValorAtributo = listaValorAtributo;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public boolean isIndicadorInstancia()
/* 205:    */   {
/* 206:261 */     return this.indicadorInstancia;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setIndicadorInstancia(boolean indicadorInstancia)
/* 210:    */   {
/* 211:271 */     this.indicadorInstancia = indicadorInstancia;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public boolean isIndicadorLista()
/* 215:    */   {
/* 216:280 */     this.indicadorLista = (this.tipoAtributo == TipoAtributo.LISTA);
/* 217:281 */     return this.indicadorLista;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setIndicadorLista(boolean indicadorLista)
/* 221:    */   {
/* 222:291 */     this.indicadorLista = indicadorLista;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public boolean isIndicadorImpresion()
/* 226:    */   {
/* 227:295 */     return this.indicadorImpresion;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setIndicadorImpresion(boolean indicadorImpresion)
/* 231:    */   {
/* 232:299 */     this.indicadorImpresion = indicadorImpresion;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public boolean isIndicadorProducto()
/* 236:    */   {
/* 237:306 */     return this.indicadorProducto;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setIndicadorProducto(boolean indicadorProducto)
/* 241:    */   {
/* 242:314 */     this.indicadorProducto = indicadorProducto;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public boolean isIndicadorCliente()
/* 246:    */   {
/* 247:321 */     return this.indicadorCliente;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setIndicadorCliente(boolean indicadorCliente)
/* 251:    */   {
/* 252:329 */     this.indicadorCliente = indicadorCliente;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public boolean isIndicadorProveedor()
/* 256:    */   {
/* 257:336 */     return this.indicadorProveedor;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setIndicadorProveedor(boolean indicadorProveedor)
/* 261:    */   {
/* 262:344 */     this.indicadorProveedor = indicadorProveedor;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public ValorAtributo getValorAtributoPredeterminado()
/* 266:    */   {
/* 267:348 */     ValorAtributo valorAtributo = this.listaValorAtributo.size() != 0 ? (ValorAtributo)this.listaValorAtributo.get(0) : null;
/* 268:349 */     for (ValorAtributo va : this.listaValorAtributo) {
/* 269:350 */       if (va.isPredeterminado())
/* 270:    */       {
/* 271:351 */         valorAtributo = va;
/* 272:352 */         break;
/* 273:    */       }
/* 274:    */     }
/* 275:355 */     return valorAtributo;
/* 276:    */   }
/* 277:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Atributo
 * JD-Core Version:    0.7.0.1
 */